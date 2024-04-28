package me.zhengjie.modules.system.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.quartz.utils.GPSTransToAMapUtil;
import me.zhengjie.modules.system.domain.*;
import me.zhengjie.modules.system.repository.*;
import me.zhengjie.modules.system.service.UploaderService;
import me.zhengjie.modules.system.service.dto.FileChunkDTO;
import me.zhengjie.modules.system.service.dto.FileChunkResultDTO;
import me.zhengjie.utils.SecurityUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.net.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Zuohaitao
 * @date 2023-11-03 09:58
 * @describe 实现大文件的分片上传;
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
    @Resource
    private PictureRealtimeRadarSpectrumRepository pictureRealtimeRadarSpectrumRepository;

    @Resource
    private TunnelRepository tunnelRepository;      // 将压缩包数据传入tunnel表

    @Resource
    private DiseaseInformationRepository diseaseInformationRepository;      // 将压缩包数据传入diseaseInformation表

    @Resource
    private PictureRadarSpectrumRepository pictureRadarSpectrumRepository;

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


                //=================================Socket实现往服务器发送数据====================================================================================================


                sendRadarDataToServer(newFileRepeat);


                //=================================解压缩文件夹，读取识别后的数据，存入数据库=================================
                unZipAndExtractAndStoreData(newFile.getAbsolutePath(), newFile.getParent());



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

//=================================Socket实现往服务器发送数据====================================================================================================

            sendRadarDataToServer(one);


//=================================解压缩文件夹，读取识别后的数据，存入数据库=================================
            unZipAndExtractAndStoreData(filePath, fileFolderPath);


            return true;
        }

        return false;
    }

    //            解压缩文件夹，读取识别后的数据，存入数据库
    private void unZipAndExtractAndStoreData(String filePath, String fileFolderPath) {
        String outDir = unzip_hutool(filePath, fileFolderPath);// 将上传的压缩包解压到上传目录
        // 创建File对象表示文件夹
        File folder = new File(outDir);

        // 检查文件夹是否存在
        if (folder.exists() && folder.isDirectory()) {
            String lastFolderName = folder.getName();        // 获取最后一个文件夹的名字(即解压缩后的文件夹名)
            String result = "result";

            if (lastFolderName.toLowerCase().contains(result.toLowerCase())) {        // 判断文件夹名中是否包含"result"这个单词

                // 获取该文件夹下的所有图片文件和txt文件
                List<String> imgFolders = new ArrayList<>();
                List<String> txtFilePaths = new ArrayList<>();
                scanFolder(folder, imgFolders, txtFilePaths);

                for (String txtFilePath : txtFilePaths) {           // 遍历txt文件

                    File txtFile = new File(txtFilePath);           // 将txt文件路径变成一个File对象

                    if (txtFile != null) {                          // 读取txt中的内容，将每行的数据都插入到数据库中

                        try {
                            BufferedReader reader = new BufferedReader(new FileReader(txtFile));    // 读取txt文件内容
                            String line;

                            while ((line = reader.readLine()) != null) {                            // 循环读取每一行的内容

                                System.out.println("Read line from the txt file: " + line);         // 输出每一行的内容

                                String[] eachRowValueArray = line.split(";");

                                if (eachRowValueArray.length == 17) {
                                    DiseaseInformation data = new DiseaseInformation();

                                    String imgName = eachRowValueArray[0];
                                    data.setImgName(imgName);
                                    String disNo = eachRowValueArray[1];
                                    data.setDisNumber(disNo);
                                    String disRoadName = eachRowValueArray[2];
                                    data.setDisRoadName(disRoadName);
                                    String disType = eachRowValueArray[3];
                                    data.setDisType(disType);
                                    String disFile = eachRowValueArray[4];
                                    data.setDisFile(disFile);

                                    Double disLat = convertGPGGAtoGPS(eachRowValueArray[5]);
                                    Double disLon = convertGPGGAtoGPS(eachRowValueArray[6]);
                                    GPSTransToAMapUtil.AMap aMap = GPSTransToAMapUtil.transform(disLon, disLat);
                                    data.setDisLat(Double.toString(aMap.getLatitude()));
                                    data.setDisLon(Double.toString(aMap.getLongitude()));

                                    String disStartMileage = eachRowValueArray[7];
                                    data.setDisStartMileage(disStartMileage);
                                    String disEndMileage = eachRowValueArray[8];
                                    data.setDisEndMileage(disEndMileage);
                                    String disTopDepth = eachRowValueArray[9];
                                    data.setDisTopDepth(disTopDepth);
                                    String disBottomDepth = eachRowValueArray[10];
                                    data.setDisBottomDepth(disBottomDepth);
                                    String disStartWidth = eachRowValueArray[11];
                                    data.setDisStartWidth(disStartWidth);
                                    String disEndWidth = eachRowValueArray[12];
                                    data.setDisEndWidth(disEndWidth);
                                    String disSizeInfo = eachRowValueArray[13].replace(",", "*"); // 将','替换成'*'号
                                    data.setDisSizeInfor(disSizeInfo);
                                    String disOpSuggestion = eachRowValueArray[14];
                                    data.setDisOpSuggestion(disOpSuggestion);

                                    String roadStartLatLon = eachRowValueArray[15];
                                    String[] roadStartLatLonSplits = roadStartLatLon.split(",");
                                    GPSTransToAMapUtil.AMap roadStartMap = GPSTransToAMapUtil.transform(convertGPGGAtoGPS(roadStartLatLonSplits[1]), convertGPGGAtoGPS(roadStartLatLonSplits[0]));
                                    data.setRoadStartLat(Double.toString(roadStartMap.getLatitude()));
                                    data.setRoadStartLon(Double.toString(roadStartMap.getLongitude()));
                                    String roadEndLatLon = eachRowValueArray[16];
                                    String[] roadEndLatLonSplits = roadEndLatLon.split(",");
                                    GPSTransToAMapUtil.AMap roadEndMap = GPSTransToAMapUtil.transform(convertGPGGAtoGPS(roadEndLatLonSplits[1]), convertGPGGAtoGPS(roadEndLatLonSplits[0]));
                                    data.setRoadEndLat(Double.toString(roadEndMap.getLatitude()));
                                    data.setRoadEndLon(Double.toString(roadEndMap.getLongitude()));

//                                    设置用户
                                    UserDetails currentUser = SecurityUtils.getCurrentUser();
                                    data.setUserName(currentUser.getUsername());



                                    DiseaseInformation saved = diseaseInformationRepository.save(data);

                                    // 存入之后,获取它的ID
                                    Integer diseaseInfoID = saved.getId();

                                    /**
                                     * 将图片路径放到数据库中, 外键为diseaseInfoID
                                     */
                                    PictureRadarSpectrum save = new PictureRadarSpectrum();
                                    if (imgName.toLowerCase().endsWith(".bmp")) {
                                        String pngName = imgName.replace(".bmp", ".png");
                                        save.setFileUrl(lastFolderName + "/" + pngName);
                                    } else {
                                        save.setFileUrl(lastFolderName + "/" + imgName);
                                    }
                                    save.setTunnelId(diseaseInfoID);
                                    pictureRadarSpectrumRepository.save(save);

                                    /**
                                     * 将图片复制到对应的地方
                                     */
                                    for (String imgFolder : imgFolders) {
                                        File imgFile = new File(imgFolder);
                                        if (imgFile.getName().equals(imgName)) {
                                            if (imgFile.isFile() && isImageFile(imgFile)) {
                                                String targetFolderPath = "D:\\WorkFile\\FrontCode\\IofTV-Screen-web\\src\\assets\\img\\pictures\\radarSpectrum";

                                                if (imgFile.getAbsolutePath().endsWith(".bmp")) {       // 如果是bmp格式的文件，将bmp格式的图片文件转储PNG格式
                                                    try {
                                                        String pngImgFilePath = imgFile.getAbsolutePath().replace(".bmp", ".png");
                                                        // 读取BMP文件
                                                        BufferedImage image = ImageIO.read(new File(imgFile.getAbsolutePath()));

                                                        // 将图像写入PNG文件
                                                        File pngImg = new File(pngImgFilePath);
                                                        ImageIO.write(image, "png", pngImg);

                                                        copyImage(pngImg,  targetFolderPath + File.separator + lastFolderName + File.separator);

                                                        System.out.println("转换完成！");
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                } else {
                                                    // 复制图片到目标路径
                                                    copyImage(imgFile,  targetFolderPath + File.separator + lastFolderName + File.separator);
                                                }
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            reader.close();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }




            }

        }

    }

    private static void scanFolder(File folder, List<String> imgFolders, List<String> txtFilePaths) {
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    scanFolder(file, imgFolders, txtFilePaths);
                } else {
                    String fileName = file.getName().toLowerCase();
                    if (fileName.endsWith(".txt")) {
                        txtFilePaths.add(file.getAbsolutePath());
                    } else if (isImageFile(fileName)) {
                        imgFolders.add(file.getAbsolutePath());
                    }
                }
            }
        }
    }

    private static boolean isImageFile(String fileName) {
        String[] imageExtensions = {".jpg", ".jpeg", ".png", ".gif", ".bmp"};

        for (String extension : imageExtensions) {
            if (fileName.endsWith(extension)) {
                return true;
            }
        }

        return false;
    }

    // 将传过来的经纬度转成GPS
    private static double convertGPGGAtoGPS(String gpggaValue) {
        // Assuming format is DDMM.MMMMMM
        double value = Double.parseDouble(gpggaValue);

        // Extract degrees
        int degrees = (int) value / 100;

        // Extract minutes
        double minutes = value % 100;

        // Convert to GPS format (degrees + minutes/60)
        return degrees + minutes / 60;
    }

    // 判断文件是否为图片
    private boolean isImageFile(File file) {
        try {
            BufferedImage img = ImageIO.read(file);
            return img != null;
        } catch (IOException e) {
            return false;
        }
    }

    private void sendRadarDataToServer(RadarAcquisitionUpload data) {
        if (!data.getFileName().contains("result")) {
            final String serverAddress = "124.204.60.82";
            final int port = 2021; // 服务器端口

            try (Socket socket = new Socket(serverAddress, port)) {
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                writer.println(data.toString());

                System.out.println("发送到服务器的消息：" + data);
            } catch (UnknownHostException e) {
                System.err.println("无法连接到服务器：" + e.getMessage());
            } catch (IOException e) {
                System.err.println("I/O 错误：" + e.getMessage());
            }
        }
    }

    // 复制图片到目标路径
    private void copyImage(File sourceImage, String targetFolderPath) {
        try {
            // 创建目标文件夹
            Files.createDirectories(Paths.get(targetFolderPath));

            Path sourcePath = sourceImage.toPath();
            Path targetPath = Paths.get(targetFolderPath, sourceImage.getName());
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getNewFileName(String originalFileName) {
        int dotIndex = originalFileName.lastIndexOf('.');
        String name = originalFileName.substring(0, dotIndex);
        String extension = originalFileName.substring(dotIndex);
        long currentTimeMillis = System.currentTimeMillis();
        Date currentTime = new Date(currentTimeMillis);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String formattedTime = dateFormat.format(currentTime);

        return name + "_"+formattedTime + extension;
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
     * @return String 返回解压后的文件夹路径
     * @author
     * @date 2022/8/3 17:28
     */
    public static String unzip_hutool(String zipFile, String outDir) {
        // 使用反斜杠 "\" 进行分割
        String[] pathParts = zipFile.split("\\\\");
        // 获取倒数第一个元素，即 "test.zip"
        String targetPart = pathParts[pathParts.length - 1];

        // 将 "test.zip" 分割以获取 "test"
        String[] fileNameParts = targetPart.split("\\.");
        String targetName = fileNameParts[0];
        outDir += File.separator + targetName;
        cn.hutool.core.util.ZipUtil.unzip(zipFile, outDir);
        return outDir;
    }
}
