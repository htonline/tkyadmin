package me.zhengjie.modules.system.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.quartz.utils.GPSTransToAMapUtil;
import me.zhengjie.modules.system.domain.*;
import me.zhengjie.modules.system.repository.*;
import me.zhengjie.modules.system.service.UploaderService;
import me.zhengjie.modules.system.service.dto.FileChunkDTO;
import me.zhengjie.modules.system.service.dto.FileChunkResultDTO;
import me.zhengjie.utils.SecurityUtils;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import java.time.Instant;
import java.util.*;
import java.net.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.atomic.AtomicReference;

import static me.zhengjie.modules.quartz.utils.getAddressUtils.getAddressByGPS;
import static me.zhengjie.modules.quartz.utils.getAddressUtils.getStartEndAddressByGPS;

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
    private PictureRadarSpectrumRepository pictureRadarSpectrumRepository;  // 雷达图片

    @Resource
    private PictureRepository pictureRepository;                // 现场图片

    @Resource
    private DiseaseModelRepository diseaseModelRepository;      // 病害模型


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


                sendRadarDataToServer(newFileRepeat, chunkDTO.getSelectedThresholdValue());


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
    public boolean mergeChunk(String identifier, String fileName, Integer totalChunks, String selectedThresholdValue) throws IOException {
        return mergeChunks(identifier, fileName, totalChunks, selectedThresholdValue);
    }

    /**
     * 合并分片
     *
     * @param identifier
     * @param filename
     */
    private boolean mergeChunks(String identifier, String filename, Integer totalChunks, String selectedThresholdValue) {
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

            sendRadarDataToServer(one, selectedThresholdValue);


//=================================解压缩文件夹，读取识别后的数据，存入数据库=================================
            unZipAndExtractAndStoreData(filePath, fileFolderPath);


            return true;
        }

        return false;
    }

    //            解压缩文件夹，读取识别后的数据，存入数据库
    private void unZipAndExtractAndStoreData(String filePath, String fileFolderPath) {
        String outDir = extract(filePath, fileFolderPath);// 将上传的压缩包解压到上传目录
        // 创建File对象表示文件夹
        File folder = new File(outDir);

        // 检查文件夹是否存在
        if (folder.exists() && folder.isDirectory()) {
            String lastFolderName = folder.getName();        // 获取最后一个文件夹的名字(即解压缩后的文件夹名)

            String sceneImg = "(附现场";
            if (lastFolderName.contains(sceneImg)) {
                List<String> sceneImgFiles = new ArrayList<>();
                List<String> txtFilePaths = new ArrayList<>();
                scanFolder(folder, sceneImgFiles, txtFilePaths);

                for (String sceneImgFile : sceneImgFiles) {
                    File imgFile = new File(sceneImgFile);
                    if (imgFile.isFile() && isImageFile(imgFile)) {
                        String targetFolderPath = "D:\\WorkSpace\\JavaProject\\tky\\IofTV-Screen-web\\src\\assets\\img\\pictures\\scene";

                        // 获取图片文件的上一层文件夹名称
                        Path sourcePath = Paths.get(sceneImgFile);
                        Path parentFolderName = sourcePath.getParent().getFileName();

                        // 构建目标路径
                        Path targetPath = Paths.get(targetFolderPath, parentFolderName.toString(), imgFile.getName());

                        try {
                            // 确保目标文件夹存在
                            Files.createDirectories(targetPath.getParent());
                            // 复制文件;
                            // StandardCopyOption.REPLACE_EXISTING复制文件并替换已有文件（如果存在）。
                            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                            System.out.println("文件复制成功：" + targetPath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            String result = "result";
            // 判断文件夹名中是否包含"result"这个单词
            if (lastFolderName.toLowerCase().contains(result.toLowerCase())) {
                // 获取该文件夹下的所有图片文件和txt文件
                List<String> imgFolders = new ArrayList<>();
                List<String> txtFilePaths = new ArrayList<>();
                scanFolder(folder, imgFolders, txtFilePaths);

                String gprFile = "gpr0-diseaseInfo.txt";
                File targetFileObj = null;

//                判断是否包含gpr0-diseaseInfo.txt文件
                for (String txtFilePath : txtFilePaths) {
                    if (txtFilePath.endsWith(gprFile)) {
                        targetFileObj = new File(txtFilePath);
                        break;
                    }
                }

                if (targetFileObj != null && targetFileObj.exists()) {
                    File txtFile = targetFileObj;
                    if (txtFile != null) {
                        try {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(txtFile), "GB2312"));    // 读取txt文件内容
                            String line;

                            while ((line = reader.readLine()) != null) {
                                System.out.println("Read line from the txt file: " + line);         // 输出每一行的内容
                                String[] eachRowValueArray = line.split(";");
                                if (eachRowValueArray.length == 18) {
                                    DiseaseInformation data = new DiseaseInformation();
                                    String imgName= eachRowValueArray[0];
                                    data.setImgName(eachRowValueArray[0]);
                                    data.setDisNumber(eachRowValueArray[1]);
                                    data.setDisRoadName(eachRowValueArray[2]);
                                    data.setDisType(eachRowValueArray[3]);
                                    data.setDisFile(eachRowValueArray[4]);
//                                    将GPGGA转成GPS; GPS保留小数点后六位
                                    data.setDisLat(String.format("%.6f", convertGPGGAtoGPS(eachRowValueArray[5])));
                                    data.setDisLon(String.format("%.6f", convertGPGGAtoGPS(eachRowValueArray[6])));
                                    data.setDisStartMileage(eachRowValueArray[7]);
                                    data.setDisEndMileage(eachRowValueArray[8]);
                                    data.setDisTopDepth(eachRowValueArray[9]);
                                    data.setDisBottomDepth(eachRowValueArray[10]);
                                    String disSizeInfo = eachRowValueArray[13].replace(",", "*"); // 将','替换成'*'号
                                    if (disSizeInfo.contains("*-9999")) {
                                        disSizeInfo = disSizeInfo.replace("*-9999", "");
                                    }
                                    data.setDisSizeInfor(disSizeInfo);

                                    String fileName = generateModel(disSizeInfo);
                                    DiseaseModel model = new DiseaseModel();
                                    model.setMdelUrl(fileName);
                                    model.setDisNumber(eachRowValueArray[1]);
                                    diseaseModelRepository.save(model);

                                    DiseaseModel model_road = new DiseaseModel();
                                    model_road.setMdelUrl("road_1.gltf");
                                    model_road.setDisNumber(eachRowValueArray[1]);
                                    diseaseModelRepository.save(model_road);

                                    data.setDisOpSuggestion(eachRowValueArray[14]);
                                    String roadStartLatLon = eachRowValueArray[15];
                                    String[] roadStartLatLonSplits = roadStartLatLon.split(",");
                                    data.setRoadStartLat(String.format("%.6f", convertGPGGAtoGPS(roadStartLatLonSplits[0])));
                                    data.setRoadStartLon(String.format("%.6f", convertGPGGAtoGPS(roadStartLatLonSplits[1])));
                                    String roadEndLatLon = eachRowValueArray[16];
                                    String[] roadEndLatLonSplits = roadEndLatLon.split(",");
                                    data.setRoadEndLat(String.format("%.6f", convertGPGGAtoGPS(roadEndLatLonSplits[0])));
                                    data.setRoadEndLon(String.format("%.6f", convertGPGGAtoGPS(roadEndLatLonSplits[1])));
                                    UserDetails currentUser = SecurityUtils.getCurrentUser();
                                    data.setUserName(currentUser.getUsername());

                                    HashMap<String, String > address = getAddressByGPS(data.getDisLon(), data.getDisLat());
                                    data.setProvince(address.get("省份"));
                                    data.setCity(address.get("城市"));
                                    data.setDistrict(address.get("区县"));
                                    data.setSpare5(address.get("道路"));

                                    DiseaseInformation saved = diseaseInformationRepository.save(data);

//                                    1.存入雷达图谱
                                    PictureRadarSpectrum B_scan_img = null;
                                    String[] imgNames = imgName.split(",");
                                    for (int i = 0; i < imgNames.length; i++) {
                                        String img = imgNames[i];
                                        PictureRadarSpectrum pictureRadarSpectrum = new PictureRadarSpectrum();
//                                        将图片路径存入数据库
                                        if (img.toLowerCase().endsWith(".bmp")) {
                                            String pngName = img.replace(".bmp", ".png");
                                            pictureRadarSpectrum.setFileUrl(lastFolderName + "/" + pngName);
                                        } else {
                                            pictureRadarSpectrum.setFileUrl(lastFolderName + "/" + img);
                                        }
                                        pictureRadarSpectrum.setDisNumber(saved.getDisNumber());
//                                       如果是第一张,就是B-scan;如果是第二张,就是C-scan
                                        if (i == 0) {
                                            pictureRadarSpectrum.setRemark("B-scan");
                                            B_scan_img = pictureRadarSpectrumRepository.save(pictureRadarSpectrum);
                                        } else if (i == 1) {
                                            pictureRadarSpectrum.setRemark("C-scan");
                                            pictureRadarSpectrumRepository.save(pictureRadarSpectrum);
                                        }
                                    }

//                                    2.将图片复制到对应的位置
                                    for (String img : imgNames) {
                                        for (String imgFolder : imgFolders) {
                                            File imgFile = new File(imgFolder);
                                            if (imgFile.getName().equals(img)) {
                                                if (imgFile.isFile() && isImageFile(imgFile)) {
                                                    String targetFolderPath = "D:\\WorkSpace\\JavaProject\\tky\\IofTV-Screen-web\\src\\assets\\img\\pictures\\radarSpectrum";
                                                    String targetFolderPath2 = "D:\\eladmin\\file\\图片\\病害图片\\radarSpectrum";

                                                    if (imgFile.getAbsolutePath().endsWith(".bmp")) {       // 如果是bmp格式的文件，将bmp格式的图片文件转储PNG格式
                                                        try {
                                                            String pngImgFilePath = imgFile.getAbsolutePath().replace(".bmp", ".png");
                                                            // 读取BMP文件
                                                            BufferedImage image = ImageIO.read(new File(imgFile.getAbsolutePath()));

                                                            // 将图像写入PNG文件
                                                            File pngImg = new File(pngImgFilePath);
                                                            ImageIO.write(image, "png", pngImg);

                                                            copyImage(pngImg,  targetFolderPath + File.separator + lastFolderName + File.separator);
                                                            copyImage(pngImg,  targetFolderPath2 + File.separator + lastFolderName + File.separator);

                                                            System.out.println("转换完成！");
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                    } else {
                                                        // 复制图片到目标路径
                                                        copyImage(imgFile,  targetFolderPath + File.separator + lastFolderName + File.separator);
                                                        copyImage(imgFile,  targetFolderPath2 + File.separator + lastFolderName + File.separator);
                                                    }
                                                    break;
                                                }
                                            }
                                        }
                                    }

//                                    3.写入excel传到对接平台
                                    exportPlatformDataToExcelByDisNumber(saved, B_scan_img);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else {
//                    不包含，二维的
                    for (String txtFilePath : txtFilePaths) {           // 遍历txt文件

                        File txtFile = new File(txtFilePath);           // 将txt文件路径变成一个File对象

                        if (txtFile != null) {                          // 读取txt中的内容，将每行的数据都插入到数据库中

                            try {
                                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(txtFile), "GB2312"));    // 读取txt文件内容
                                String line;

                                while ((line = reader.readLine()) != null) {                            // 循环读取每一行的内容

                                    System.out.println("Read line from the txt file: " + line);         // 输出每一行的内容

                                    String[] eachRowValueArray = line.split(";");

                                    if (eachRowValueArray.length == 18) {
                                        DiseaseInformation data = new DiseaseInformation();
                                        String imgName = eachRowValueArray[0];
                                        data.setImgName(eachRowValueArray[0]);
                                        data.setDisNumber(eachRowValueArray[1]);
                                        data.setDisRoadName(eachRowValueArray[2]);
                                        data.setDisType(eachRowValueArray[3]);
                                        data.setDisFile(eachRowValueArray[4]);

//                                    GPSTransToAMapUtil.AMap disMap = GPSTransToAMapUtil.transform(convertGPGGAtoGPS(eachRowValueArray[6]), convertGPGGAtoGPS(eachRowValueArray[5]));
                                        data.setDisLat(String.format("%.6f", convertGPGGAtoGPS(eachRowValueArray[5])));
                                        data.setDisLon(String.format("%.6f", convertGPGGAtoGPS(eachRowValueArray[6])));


                                        data.setDisStartMileage(eachRowValueArray[7]);
                                        data.setDisEndMileage(eachRowValueArray[8]);
                                        data.setDisTopDepth(eachRowValueArray[9]);
                                        data.setDisBottomDepth(eachRowValueArray[10]);

                                        String disSizeInfo = eachRowValueArray[13].replace(",", "*"); // 将','替换成'*'号
                                        if (disSizeInfo.contains("*-9999")) {
                                            disSizeInfo = disSizeInfo.replace("*-9999", "");
                                        }
                                        data.setDisSizeInfor(disSizeInfo);

//                                    输入长宽高，导出glb模型。将病害模型数据注入数据库
                                        String fileName = generateModel(disSizeInfo);
                                        DiseaseModel model = new DiseaseModel();
                                        model.setMdelUrl(fileName);
                                        model.setDisNumber(eachRowValueArray[1]);
                                        diseaseModelRepository.save(model);

                                        DiseaseModel model_road = new DiseaseModel();
                                        model_road.setMdelUrl("road_1.gltf");
                                        model_road.setDisNumber(eachRowValueArray[1]);
                                        diseaseModelRepository.save(model_road);


                                        String disOpSuggestion = eachRowValueArray[14];
                                        data.setDisOpSuggestion(disOpSuggestion);

                                        String roadStartLatLon = eachRowValueArray[15];
                                        String[] roadStartLatLonSplits = roadStartLatLon.split(",");
//                                    GPSTransToAMapUtil.AMap roadStartMap = GPSTransToAMapUtil.transform(convertGPGGAtoGPS(roadStartLatLonSplits[1]), convertGPGGAtoGPS(roadStartLatLonSplits[0]));
                                        data.setRoadStartLat(String.format("%.6f", convertGPGGAtoGPS(roadStartLatLonSplits[0])));
                                        data.setRoadStartLon(String.format("%.6f", convertGPGGAtoGPS(roadStartLatLonSplits[1])));
                                        String roadEndLatLon = eachRowValueArray[16];
                                        String[] roadEndLatLonSplits = roadEndLatLon.split(",");
//                                    GPSTransToAMapUtil.AMap roadEndMap = GPSTransToAMapUtil.transform(convertGPGGAtoGPS(roadEndLatLonSplits[1]), convertGPGGAtoGPS(roadEndLatLonSplits[0]));
                                        data.setRoadEndLat(String.format("%.6f", convertGPGGAtoGPS(roadEndLatLonSplits[0])));
                                        data.setRoadEndLon(String.format("%.6f", convertGPGGAtoGPS(roadEndLatLonSplits[1])));

//                                    设置用户
                                        UserDetails currentUser = SecurityUtils.getCurrentUser();
                                        data.setUserName(currentUser.getUsername());

                                        HashMap<String, String > address = getAddressByGPS(data.getDisLon(), data.getDisLat());
                                        data.setProvince(address.get("省份"));
                                        data.setCity(address.get("城市"));
                                        data.setDistrict(address.get("区县"));
                                        data.setSpare5(address.get("道路"));

                                        DiseaseInformation saved = diseaseInformationRepository.save(data);

                                        /**
                                         * 将现场图片存入数据库中
                                         */
                                        Picture picture = new Picture();
                                        picture.setDisNumber(saved.getDisNumber());

                                        String dir = saved.getDisNumber().split("_")[0];
                                        picture.setUrl(dir+"/"+eachRowValueArray[17]);
                                        pictureRepository.save(picture);

                                        /**
                                         * 将雷达图片路径放到数据库中, 外键为disNumber
                                         */
                                        PictureRadarSpectrum save = new PictureRadarSpectrum();
                                        if (imgName.toLowerCase().endsWith(".bmp")) {
                                            String pngName = imgName.replace(".bmp", ".png");
                                            save.setFileUrl(lastFolderName + "/" + pngName);
                                        } else {
                                            save.setFileUrl(lastFolderName + "/" + imgName);
                                        }
                                        save.setDisNumber(saved.getDisNumber());
                                        save.setRemark("B-scan");
                                        PictureRadarSpectrum pictureRadarSpectrum = pictureRadarSpectrumRepository.save(save);

                                        /**
                                         * 将图片复制到对应的地方
                                         */
                                        for (String imgFolder : imgFolders) {
                                            File imgFile = new File(imgFolder);
                                            if (imgFile.getName().equals(imgName)) {
                                                if (imgFile.isFile() && isImageFile(imgFile)) {
                                                    String targetFolderPath = "D:\\WorkSpace\\JavaProject\\tky\\IofTV-Screen-web\\src\\assets\\img\\pictures\\radarSpectrum";
                                                    String targetFolderPath2 = "D:\\eladmin\\file\\图片\\病害图片\\radarSpectrum";


                                                    if (imgFile.getAbsolutePath().endsWith(".bmp")) {       // 如果是bmp格式的文件，将bmp格式的图片文件转储PNG格式
                                                        try {
                                                            String pngImgFilePath = imgFile.getAbsolutePath().replace(".bmp", ".png");
                                                            // 读取BMP文件
                                                            BufferedImage image = ImageIO.read(new File(imgFile.getAbsolutePath()));

                                                            // 将图像写入PNG文件
                                                            File pngImg = new File(pngImgFilePath);
                                                            ImageIO.write(image, "png", pngImg);

                                                            copyImage(pngImg,  targetFolderPath + File.separator + lastFolderName + File.separator);
                                                            copyImage(pngImg,  targetFolderPath2 + File.separator + lastFolderName + File.separator);

                                                            System.out.println("转换完成！");
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                    } else {
                                                        // 复制图片到目标路径
                                                        copyImage(imgFile,  targetFolderPath + File.separator + lastFolderName + File.separator);
                                                        copyImage(imgFile,  targetFolderPath2 + File.separator + lastFolderName + File.separator);
                                                    }
                                                    break;
                                                }
                                            }
                                        }

//                                    3.写入excel传到对接平台
                                        exportPlatformDataToExcelByDisNumber(saved, pictureRadarSpectrum);
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

    }

    //   病害数据上报平台
    private void exportPlatformDataToExcelByDisNumber(DiseaseInformation saved, PictureRadarSpectrum pictureRadarSpectrum) throws IOException {
        //        将excel写入到输出文件中
        String outputFilePath = "D:\\eladmin\\excel" + File.separator + "excel_" + saved.getDisNumber() + ".xlsx";
        File outputFile = new File(outputFilePath);
        // 确保目录存在
        File parentDir = outputFile.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs(); // 创建目录，包括任何必要但不存在的父目录
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("病害上报");     // 第一张表
        Row headerRow = sheet.createRow(0);                // 第一行
        String[] headers = {"日期", "病害编号","省","市","区/县","所属道路","所属路段","病害来源","设施类型","设施小类","病害部位", "病害类型", "数量", "严重程度", "位置", "车道", "病害图片", "坐标", "病害顶深（m）", "病害底深（m）", "病害尺寸信息长*宽（m）", "验证状态", "验证方式", "周边管线"};
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }

        int rowCount = sheet.getLastRowNum(); // 获取当前表格的最后一行行号
        Row newRow = sheet.createRow(rowCount + 1); // 创建新行
        newRow.createCell(0).setCellValue(saved.getCreateTime() != null ? saved.getCreateTime().toString() : "");
        newRow.createCell(1).setCellValue(saved.getDisNumber() != null ? saved.getDisNumber() : "");
        newRow.createCell(2).setCellValue(saved.getProvince() != null ? saved.getProvince() : "");
        newRow.createCell(3).setCellValue(saved.getCity() != null ? saved.getCity() : "");
        newRow.createCell(4).setCellValue(saved.getDistrict() != null ? saved.getDistrict() : "");
        newRow.createCell(5).setCellValue(saved.getSpare5() != null ? saved.getSpare5() : "");
        HashMap<String, String> startEndAddressByGPS = getStartEndAddressByGPS(saved.getRoadStartLon(), saved.getRoadStartLat(), saved.getRoadEndLon(), saved.getRoadEndLat());
        newRow.createCell(6).setCellValue(startEndAddressByGPS.get("起点道路") + "-" + startEndAddressByGPS.get("终点道路"));
        newRow.createCell(7).setCellValue("探地雷达");
        newRow.createCell(8).setCellValue("机动车道");
        newRow.createCell(9).setCellValue("机动车道");
        newRow.createCell(10).setCellValue("路基");
        newRow.createCell(11).setCellValue(saved.getDisType() != null ? saved.getDisType() : "");
        newRow.createCell(12).setCellValue("1");
        newRow.createCell(13).setCellValue("一般");
        newRow.createCell(14).setCellValue("机动车道");
        newRow.createCell(15).setCellValue("车道1");
        //TODO: 病害图片（根据disNumber）
        newRow.createCell(16).setCellValue("http://120.46.140.233:8001/file/%E5%9B%BE%E7%89%87/%E7%97%85%E5%AE%B3%E5%9B%BE%E7%89%87/radarSpectrum/"+pictureRadarSpectrum.getFileUrl());
        newRow.createCell(17).setCellValue("["+saved.getDisLon() + "," + saved.getDisLat()+"]");
        newRow.createCell(18).setCellValue(saved.getDisTopDepth() != null ? saved.getDisTopDepth() : "");
        newRow.createCell(19).setCellValue(saved.getDisBottomDepth() != null ? saved.getDisBottomDepth() : "");
        newRow.createCell(20).setCellValue(saved.getDisSizeInfor() != null ? saved.getDisSizeInfor() : "");
        newRow.createCell(21).setCellValue(new Random().nextInt() % 2 == 0 ? "是" : "否");
        newRow.createCell(22).setCellValue(new Random().nextInt() % 2 == 0 ? "微芯随钻" : "人工钻孔");
        newRow.createCell(23).setCellValue(new Random().nextInt() % 2 == 0 ? "污水管" : "雨水管");

        // 将 Excel 写入输出文件
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            workbook.write(fos);
            System.out.println("Excel 文件已成功创建: " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String generateModel(String disSizeInfo) {

        String[] parts = disSizeInfo.split("\\*");

        if (parts.length != 3 && parts.length != 2) {
            // 长度不等于3时跳过生成过程并返回空字符串
            return "";
        }
        String length = "";
        String width = "";
        String height = "";

        if (parts.length == 3) {
            length = parts[0];
            width = parts[1];
            height = parts[2];
        } else if (parts.length == 2) {
            length = parts[0];
            height = parts[1];
        }

        // 定义Python脚本的路径
        String pythonScriptPath = "D:\\tky_model\\generate5.py";

        // 生成唯一的文件名
        String uniqueId = UUID.randomUUID().toString();
        String fileName = "reconstructed_mesh_" + uniqueId + ".glb";
        String outputFile = "D:\\tky_model\\road_" + uniqueId + ".csv";

        // 调用生成三维坐标的模式
        String generateMode = "generate";
        String numPoints = "8000";

        // 调用重建曲面的模式
        String reconstructMode = "reconstruct";
        String inputFile = outputFile;

        String outputGLBFile = "D:\\WorkSpace\\JavaProject\\tky\\IofTV-Screen-web\\src\\assets\\models" + File.separator + fileName;

        // 指定 Python 可执行文件的完整路径
        String pythonExePath = "D:\\Software\\anaconda3\\envs\\PyLab\\python.exe";

        // 生成三维坐标命令
        String[] generateCommand;
        if (width.isEmpty()) {
            generateCommand = new String[] {
                    pythonExePath,
                    pythonScriptPath,
                    generateMode,
                    length,
                    height,
                    numPoints,
                    outputFile
            };
        } else {
            generateCommand = new String[] {
                    pythonExePath,
                    pythonScriptPath,
                    generateMode,
                    length,
                    width,
                    height,
                    numPoints,
                    outputFile
            };
        }

        // 重建曲面并导出为GLB命令
        String[] reconstructCommand = new String[] {
                pythonExePath,
                pythonScriptPath,
                reconstructMode,
                inputFile,
                outputGLBFile
        };

        // 执行生成三维坐标的命令
        executePythonScript(generateCommand);

        // 执行重建曲面并导出为GLB的命令
        executePythonScript(reconstructCommand);

        return fileName;

    }
    private static void executePythonScript(String[] command) {
        try {
            // 启动进程
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectErrorStream(true);
            Process process = pb.start();

            // 读取脚本输出
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();

            // 等待脚本执行完成
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Script executed successfully");
            } else {
                System.out.println("Script execution failed with exit code " + exitCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    private void sendRadarDataToServer(RadarAcquisitionUpload data, String selectedThresholdValue) {
        // 将文件名转换为小写
        String fileName = data.getFileName().toLowerCase();

        if (!fileName.contains("result")) {
            final String serverAddress = "124.204.60.82";
            final int port = 2021; // 服务器端口

            try (Socket socket = new Socket(serverAddress, port)) {
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                writer.println(data.toString()+selectedThresholdValue);

                System.out.println("发送到服务器的消息：" + data + selectedThresholdValue);
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
    public static String extract(String zipFilePath, String outDir) {
        try {
            // 使用 Paths 来处理路径
            Path zipFile = Paths.get(zipFilePath);
            // 获取压缩文件的文件名（不带扩展名）
            String fileNameWithoutExtension = zipFile.getFileName().toString().replaceFirst("[.][^.]+$", "");
            // 创建输出目录
            Path outputDir = Paths.get(outDir, fileNameWithoutExtension);

            // 确保输出目录存在
            File outputDirFile = outputDir.toFile();
            if (!outputDirFile.exists()) {
                outputDirFile.mkdirs();
            }

            // 使用 Zip4j 进行解压
            ZipFile zip = new ZipFile(zipFile.toFile());
//            用户上传的时候，如果是使用window系统压缩，编码类型为gbk，如果是使用linux或者mac压缩，那么编码类型为utf-8
//            解决办法：https://blog.csdn.net/m0_37719874/article/details/120909497
            zip.setFileNameCharset("GBK");
            zip.extractAll(outputDir.toString());

            return outputDir.toString();
        } catch (ZipException e) {
            e.printStackTrace();
            return null;
        }
    }

    //    生成病害的序号(disNumber): 将UUID的一部分与当前时间戳结合使用，以减少重复的可能性。
    private String generateUniqueShortID() {
        String uuidPart = UUID.randomUUID().toString().substring(0, 8);
        String timestamp = Long.toHexString(Instant.now().toEpochMilli());
        return uuidPart + timestamp;
    }
}
