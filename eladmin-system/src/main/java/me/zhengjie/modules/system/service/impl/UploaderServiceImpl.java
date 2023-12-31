package me.zhengjie.modules.system.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.domain.RadarAcquisitionUpload;
import me.zhengjie.modules.system.repository.RadarAcquisitionUploadRepository;
import me.zhengjie.modules.system.service.UploaderService;
import me.zhengjie.modules.system.service.dto.FileChunkDTO;
import me.zhengjie.modules.system.service.dto.FileChunkResultDTO;
import me.zhengjie.utils.SecurityUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.net.*;

/**
 * @author Zuohaitao
 * @date 2023-11-03 09:58
 * @describe 实现大文件的分片上传;
 * 🚀 特别鸣谢：Powered by the incredible genius, the one and only, the coding wizard - 史文幸学姐! 🌟
 *  1. 请注意，这段代码中蕴含了史文幸学姐的智慧和魔法，对它保持敬畏之心！💥
 *  2. 只有史文幸学姐有权解释这段神秘代码，普通人请勿尝试！🧙‍♀️
 *  3. 本代码不是一个普通代码，而是史文幸学姐的传奇之作，它会随着时间推移而变得越来越强大！🔮
 */
@Service
@SuppressWarnings("all")
@RequiredArgsConstructor
public class UploaderServiceImpl implements UploaderService {
    private Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Value("D:\\eladmin\\file\\radarAcquisitionUpload\\")
    private String uploadFolder;

    @Resource
    private RadarAcquisitionUploadRepository radarAcquisitionUploadRepository;

    /**
     * 检查文件是否存在，如果存在则跳过该文件的上传，如果不存在，返回需要上传的分片集合
     *
     * 检测文件有没有传，有的话，传了多少块（实现暂停续传）
     *
     * @param chunkDTO
     * @return
     */
    @Override
    public FileChunkResultDTO checkChunkExist(FileChunkDTO chunkDTO) {
        //1.检查文件是否已上传过
        //1.1)检查在磁盘中是否存在
        // chunkDTO.getIdentifier(): MD5;
        // fileFolderPath: D:\eladmin\file\radarAcquisitionUpload\admin\MD5\
        String fileFolderPath = getFileFolderPath(chunkDTO.getIdentifier());

        logger.info("fileFolderPath-->{}", fileFolderPath);

        // chunkDTO.getFilename(): test.zip
        // 上传文件的存储位置: filePath:D:\eladmin\file\radarAcquisitionUpload\test.zip
        String filePath = getFilePath(chunkDTO.getIdentifier(), chunkDTO.getFilename());

//        判断要上传的文件test.zip是否存在
        File file = new File(filePath);
        boolean exists = file.exists();

        //1.2)检查Redis中是否存在,并且所有分片已经上传完成。
//        将缓存中所有的块取出, 放入集合uploaded中
        Set<Integer> uploaded = (Set<Integer>) redisTemplate.opsForHash().get(chunkDTO.getIdentifier(), "uploaded");
//        块不等于空:说明之前上传过这个文件（缓存中有它的分块文件） && 缓存中分块的数量等于文件的总块数:说明这个块已经上传完了 && 文件已经存在(有可能上传完但没合并)
        if (uploaded != null && uploaded.size() == chunkDTO.getTotalChunks() && exists) {
//            满足这三个条件: 实现秒传（不用传了）
            String parentPath = file.getParent();
            String fileName = file.getName();
            String newName = getNewFileName(fileName);
            File newFile = new File(parentPath, newName);
            try {
                FileInputStream fis = new FileInputStream(file);
                FileOutputStream fos = new FileOutputStream(newFile);
                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = fis.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }

                fis.close();
                fos.close();

                System.out.println("文件复制成功！");
                // ==================================================
                RadarAcquisitionUpload newFileRepeat = new RadarAcquisitionUpload();
                newFileRepeat.setFileName(newFile.getName());
                newFileRepeat.setFilePath(newFile.getPath());
                UserDetails currentUser = SecurityUtils.getCurrentUser();           // 获取当前用户名
                newFileRepeat.setByUser(currentUser.getUsername());
                radarAcquisitionUploadRepository.save(newFileRepeat);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return new FileChunkResultDTO(true);
        }

//        判断用户文件夹是否存在; 否的话, 创建.
        File fileFolder = new File(fileFolderPath);
        if (!fileFolder.exists()) {
            boolean mkdirs = fileFolder.mkdirs();
            logger.info("准备工作,创建文件夹,fileFolderPath:{},mkdirs:{}", fileFolderPath, mkdirs);
        }
        // 断点续传，返回已上传的分片
        return new FileChunkResultDTO(false, uploaded);
    }


    /**
     * 上传分片
     *
     * @param chunkDTO
     */
    @Override
    public void uploadChunk(FileChunkDTO chunkDTO) {
        //分块的目录
        String chunkFileFolderPath = getChunkFileFolderPath(chunkDTO.getIdentifier(), chunkDTO.getFilename());
        logger.info("分块的目录 -> {}", chunkFileFolderPath);
        File chunkFileFolder = new File(chunkFileFolderPath);
        if (!chunkFileFolder.exists()) {
            boolean mkdirs = chunkFileFolder.mkdirs();
            logger.info("创建分片文件夹:{}", mkdirs);
        }
        //写入分片
        try (
                InputStream inputStream = chunkDTO.getFile().getInputStream();
                FileOutputStream outputStream = new FileOutputStream(new File(chunkFileFolderPath + chunkDTO.getChunkNumber()))
        ) {
            IOUtils.copy(inputStream, outputStream);
            logger.info("文件标识:{},chunkNumber:{}", chunkDTO.getIdentifier(), chunkDTO.getChunkNumber());
            //将该分片写入redis
            long size = saveToRedis(chunkDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean mergeChunk(String identifier, String fileName, Integer totalChunks) throws IOException {
        return mergeChunks(identifier, fileName, totalChunks);
    }

    /**
     * 合并分片
     *
     * @param identifier
     * @param filename
     */
    private boolean mergeChunks(String identifier, String filename, Integer totalChunks) {
        String fileFolderPath = getFileFolderPath(identifier);
        String chunkFileFolderPath = getChunkFileFolderPath(identifier, filename);
        String filePath = getFilePath(identifier, filename);
        // 检查分片是否都存在
        if (checkChunks(chunkFileFolderPath, totalChunks)) {
            File chunkFileFolder = new File(chunkFileFolderPath);
            File mergeFile = new File(filePath);
            File[] chunks = chunkFileFolder.listFiles();
            //排序
            List fileList = Arrays.asList(chunks);
            Collections.sort(fileList, (Comparator<File>) (o1, o2) -> {
                return Integer.parseInt(o1.getName()) - (Integer.parseInt(o2.getName()));
            });
            try {
                RandomAccessFile randomAccessFileWriter = new RandomAccessFile(mergeFile, "rw");
                byte[] bytes = new byte[1024];
                for (File chunk : chunks) {
                    RandomAccessFile randomAccessFileReader = new RandomAccessFile(chunk, "r");
                    int len;
                    while ((len = randomAccessFileReader.read(bytes)) != -1) {
                        randomAccessFileWriter.write(bytes, 0, len);
                    }
                    randomAccessFileReader.close();

//                    // 删除已合并的分片文件
//                    if (chunk.exists()) {
//                        chunk.delete();
//                    }

                }
                randomAccessFileWriter.close();
            } catch (Exception e) {
                return false;
            }


// ==================================如果文件上传完成,就往radar_acquisition_upload数据库里加一条数据==================================================================================
            RadarAcquisitionUpload one = new RadarAcquisitionUpload();
            one.setFileName(filename);
            one.setFilePath(filePath);
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());  // 获取当前时间戳(sql)
            one.setCreatTime(currentTime);
            UserDetails currentUser = SecurityUtils.getCurrentUser();           // 获取当前用户名
            one.setByUser(currentUser.getUsername());
            radarAcquisitionUploadRepository.save(one);

            //unzip_hutool(filePath, fileFolderPath);     // 将上传的压缩包解压到上传目录

//=================================Socket实现往服务器发送数据====================================================================================================
            final String serverAddress = "192.168.1.112";
            final int port = 2020; // 服务器端口号

            try (Socket socket = new Socket(serverAddress, port)) {
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                writer.println(one.toString());

                System.out.println("发送到服务器的消息：" + one);
            } catch (UnknownHostException e) {
                System.err.println("无法连接到服务器：" + e.getMessage());
            } catch (IOException e) {
                System.err.println("I/O 错误：" + e.getMessage());
            }

            return true;
        }

        return false;
    }

    private static String getNewFileName(String originalFileName) {
        int dotIndex = originalFileName.lastIndexOf('.');
        String name = originalFileName.substring(0, dotIndex);
        String extension = originalFileName.substring(dotIndex);
        long currentTimeMillis = System.currentTimeMillis();
        Date currentTime = new Date(currentTimeMillis);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String formattedTime = dateFormat.format(currentTime);

        return name + "("+formattedTime+")" + extension;
    }

    /**
     * 检查分片是否都存在(在合并之前调用的)
     * @param chunkFileFolderPath
     * @param totalChunks
     * @return
     */
    private boolean checkChunks(String chunkFileFolderPath, Integer totalChunks) {
        try {
            for (int i = 1; i <= totalChunks + 1; i++) {
                File file = new File(chunkFileFolderPath + File.separator + i);
                if (file.exists()) {
                    continue;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 分片写入Redis
     *
     * @param chunkDTO
     */
    private synchronized long saveToRedis(FileChunkDTO chunkDTO) {
        Set<Integer> uploaded = (Set<Integer>) redisTemplate.opsForHash().get(chunkDTO.getIdentifier(), "uploaded");
        if (uploaded == null) {
            uploaded = new HashSet<>(Arrays.asList(chunkDTO.getChunkNumber()));
            HashMap<String, Object> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("uploaded", uploaded);
            objectObjectHashMap.put("totalChunks", chunkDTO.getTotalChunks());
            objectObjectHashMap.put("totalSize", chunkDTO.getTotalSize());
            objectObjectHashMap.put("path", chunkDTO.getFilename());
            redisTemplate.opsForHash().putAll(chunkDTO.getIdentifier(), objectObjectHashMap);
        } else {
            uploaded.add(chunkDTO.getChunkNumber());
            redisTemplate.opsForHash().put(chunkDTO.getIdentifier(), "uploaded", uploaded);
        }
        return uploaded.size();
    }

    /**
     * 得到文件的绝对路径
     *
     * @param identifier
     * @param filename
     * @return D:\eladmin\file\radarAcquisitionUpload\UserName\test.zip
     */
    private String getFilePath(String identifier, String filename) {
        UserDetails currentUser = SecurityUtils.getCurrentUser();
        String username = currentUser.getUsername();

        return uploadFolder + username + File.separator + filename;
    }


    /**
     * 得到分块文件所属的目录
     *
     * @param identifier
     * @return D:\eladmin\file\radarAcquisitionUpload\UserName\chunks\文件名\MD5\
     */
    private String getChunkFileFolderPath(String identifier, String filename) {
        UserDetails currentUser = SecurityUtils.getCurrentUser();
        String username = currentUser.getUsername();
        return uploadFolder + username + File.separator + "chunks" + File.separator +filename+File.separator + identifier + File.separator;
    }

    /**
     *
     * @param identifier
     * @return D:\eladmin\file\radarAcquisitionUpload\UserName
     */
    private String getFileFolderPath(String identifier) {
        //    获取当前用户名
        UserDetails currentUser = SecurityUtils.getCurrentUser();
        String username = currentUser.getUsername();

        return uploadFolder + username + File.separator;
    }

    /**
     * 功能描述：解压 （利用hutool）
     *
     * @param zipFile 压缩包路径               例如："D:\eladmin\file\radarAcquisitionUpload\UserName\test.zip";
     * @param outDir  解压到的目录(支持创建)     例如："D:\eladmin\file\radarAcquisitionUpload\UserName"
     * @return void
     * @author
     * @date 2022/8/3 17:28
     */
    public static void unzip_hutool(String zipFile, String outDir) {
        // 使用反斜杠 "\" 进行分割
        String[] pathParts = zipFile.split("\\\\");
        // 获取倒数第一个元素，即 "test.zip"
        String targetPart = pathParts[pathParts.length - 1];

        // 将 "test.zip" 分割以获取 "test"
        String[] fileNameParts = targetPart.split("\\.");
        String targetName = fileNameParts[0];
        outDir += File.separator + targetName;
        cn.hutool.core.util.ZipUtil.unzip(zipFile, outDir);
    }
}
