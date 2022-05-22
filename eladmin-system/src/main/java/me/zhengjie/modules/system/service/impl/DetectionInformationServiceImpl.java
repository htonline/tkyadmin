/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.zhengjie.modules.system.service.impl;

import me.zhengjie.config.FileProperties;
import me.zhengjie.modules.system.domain.DetectionInformation;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.DetectionInformationRepository;
import me.zhengjie.modules.system.service.DetectionInformationService;
import me.zhengjie.modules.system.service.dto.DetectionInformationDto;
import me.zhengjie.modules.system.service.dto.DetectionInformationQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.DetectionInformationMapper;
import org.apache.hc.core5.util.TextUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author LJL
 * @website https://el-admin.vip
 * @description 服务实现
 * @date 2022-04-19
 **/
@Service
@RequiredArgsConstructor
public class DetectionInformationServiceImpl implements DetectionInformationService {

    private final DetectionInformationRepository detectionInformationRepository;
    private final DetectionInformationMapper detectionInformationMapper;
    private final FileProperties properties;

    @Override
    public Map<String, Object> queryAll(DetectionInformationQueryCriteria criteria, Pageable pageable) {
        Page<DetectionInformation> page = detectionInformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(detectionInformationMapper::toDto));
    }

    @Override
    public List<DetectionInformationDto> queryAll(DetectionInformationQueryCriteria criteria) {
        return detectionInformationMapper.toDto(detectionInformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    @Transactional
    public DetectionInformationDto findById(Long detectionId) {
        DetectionInformation detectionInformation = detectionInformationRepository.findById(detectionId).orElseGet(DetectionInformation::new);
        ValidationUtil.isNull(detectionInformation.getDetectionId(), "DetectionInformation", "detectionId", detectionId);
        return detectionInformationMapper.toDto(detectionInformation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DetectionInformationDto create(DetectionInformation resources) {
        return detectionInformationMapper.toDto(detectionInformationRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DetectionInformation resources) {
        DetectionInformation detectionInformation = detectionInformationRepository.findById(resources.getDetectionId()).orElseGet(DetectionInformation::new);
        ValidationUtil.isNull(detectionInformation.getDetectionId(), "DetectionInformation", "id", resources.getDetectionId());
        detectionInformation.copy(resources);
        detectionInformationRepository.save(detectionInformation);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long detectionId : ids) {
            detectionInformationRepository.deleteById(detectionId);
        }
    }

    @Override
    public void download(List<DetectionInformationDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DetectionInformationDto detectionInformation : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("项目名称", detectionInformation.getProjectName());
            map.put("标段名称", detectionInformation.getSectionName());
            map.put("隧道编号", detectionInformation.getTunnelId());
            map.put("隧道名称", detectionInformation.getTunnelName());
            map.put("工点名称", detectionInformation.getWorksiteName());
            map.put("检测起点", detectionInformation.getDetectionStartingDistance());
            map.put("检测终点", detectionInformation.getDetectionEndingDistance());
            map.put("检测长度", detectionInformation.getDetectionLength());
            map.put("数据新增时间", detectionInformation.getTime());
            map.put("报检号", detectionInformation.getTestId());
            map.put("测线编号", detectionInformation.getDetectionLineBiaohao());
            map.put("检测数据-上传/下载", detectionInformation.getDetectionData());
            map.put("现场照片-上传/下载", detectionInformation.getDetectionPhotos());
            map.put("雷达图谱-上传/下载", detectionInformation.getRadarPhotos());
            map.put("检测报告_上传/下载", detectionInformation.getDetectionSummary());
            map.put("其他附件_上传/下载", detectionInformation.getOthers());
            map.put(" beizhu1", detectionInformation.getBeizhu1());
            map.put(" beizhu2", detectionInformation.getBeizhu2());
            map.put(" beizhu3", detectionInformation.getBeizhu3());
            map.put(" beizhu4", detectionInformation.getBeizhu4());
            map.put(" beizhu5", detectionInformation.getBeizhu5());
            map.put(" beizhu6", detectionInformation.getBeizhu6());
            map.put(" beizhu7", detectionInformation.getBeizhu7());
            map.put(" beizhu8", detectionInformation.getBeizhu8());
            map.put(" beizhu9", detectionInformation.getBeizhu9());
            map.put(" beizhu10", detectionInformation.getBeizhu10());
            map.put(" beizhu11", detectionInformation.getBeizhu11());
            map.put(" beizhu12", detectionInformation.getBeizhu12());
            map.put(" beizhu13", detectionInformation.getBeizhu13());
            map.put(" beizhu14", detectionInformation.getBeizhu14());
            map.put(" beizhu15", detectionInformation.getBeizhu15());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }


    @Override
    public void downloadFile(String detectionData, String detectionPhotos, String radarPhotos, String detectionSummary, String others, HttpServletRequest request, HttpServletResponse response) {
        List<File> files = new ArrayList<File>();
        if (!TextUtils.isEmpty(detectionData)) {
            files.add(new File(properties.getPath().getPath(), detectionData));
        }
        if (!TextUtils.isEmpty(detectionPhotos)) {
            files.add(new File(properties.getPath().getAvatar(), detectionPhotos));
        }
        if (!TextUtils.isEmpty(radarPhotos)) {
            files.add(new File(properties.getPath().getPath(), radarPhotos));
        }
        if (!TextUtils.isEmpty(detectionSummary)) {
            files.add(new File(properties.getPath().getPath(), detectionSummary));
        }
        if (!TextUtils.isEmpty(others)) {
            files.add(new File(properties.getPath().getPath(), others));
        }
        // files.add(new File("C:\\eladmin\\avatar\\"+tupianPath2));
        //files.add(new File("C:\\eladmin\\avatar\\"+tupianPath3));
//        files.add(new File("C:\\eladmin\\file\\"+wenjianPath));
//        files.add(new File())
        //  String zipPath = "C:\\eladmin\\采集数据"+ ".zip";
        File zf = new File("D:\\eladmin\\采集数据" + ".zip");
        byte[] buf = new byte[1024];
        // 获取输出流
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            response.reset(); // 重点突出
            // 不同类型的文件对应不同的MIME类型
            response.setContentType("application/x-msdownload");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + zf.getName());

            // ZipOutputStream类：完成文件或文件夹的压缩
            ZipOutputStream out = new ZipOutputStream(bos);
            for (int i = 0; i < files.size(); i++) {
                FileInputStream in = new FileInputStream(files.get(i));
                // 给列表中的文件单独命名
                out.putNextEntry(new ZipEntry(files.get(i).getName()));
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.closeEntry();
                in.close();
            }
            out.close();
            bos.close();
            System.out.println("压缩完成.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //  FileUtil.downloadFile(request, response, zf, true);
    }

    @Override
    public void updateRadarPhoto(Long detectionInfomationId, String avatar) {
        DetectionInformation detectionInformation = detectionInformationRepository.findById(detectionInfomationId).orElseGet(DetectionInformation::new);
        ValidationUtil.isNull(detectionInformation.getDetectionId(), "DetectionInformation", "id", detectionInfomationId);
        detectionInformation.setRadarPhotos(avatar);
        detectionInformationRepository.save(detectionInformation);
    }


    @Override
    public void updateDetectionSummary(Long detectionInfomationId, String avatar) {
        DetectionInformation detectionInformation = detectionInformationRepository.findById(detectionInfomationId).orElseGet(DetectionInformation::new);
        ValidationUtil.isNull(detectionInformation.getDetectionId(), "DetectionInformation", "id", detectionInfomationId);
        detectionInformation.setDetectionSummary(avatar);
        detectionInformationRepository.save(detectionInformation);
    }

    @Override
    public void uploadOthers(Long detectionInfomationId, String avatar) {
        DetectionInformation detectionInformation = detectionInformationRepository.findById(detectionInfomationId).orElseGet(DetectionInformation::new);
        ValidationUtil.isNull(detectionInformation.getDetectionId(), "DetectionInformation", "id", detectionInfomationId);
        detectionInformation.setOthers(avatar);
        detectionInformationRepository.save(detectionInformation);
    }

    @Override
    public void downloadOneFile(String oneFile, HttpServletRequest request, HttpServletResponse response) {
        File file = new File(properties.getPath().getPath(),oneFile);
        if (!file.exists()){
            return;
        }
        byte[] buf = new byte[1024];
        // 获取输出流
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            response.reset(); // 重点突出
            // 不同类型的文件对应不同的MIME类型
            response.setContentType("applicatoin/octet-stream");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
            FileInputStream in = new FileInputStream(file);
            // 给列表中的文件单独命名
            int len;
            while ((len = in.read(buf)) > 0) {
                bos.write(buf, 0, len);
            }
            in.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //  FileUtil.downloadFile(request, response, zf, true);
    }
}