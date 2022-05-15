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

import me.zhengjie.modules.system.domain.DetectionInformation;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.DetectionInformationRepository;
import me.zhengjie.modules.system.service.DetectionInformationService;
import me.zhengjie.modules.system.service.dto.DetectionInformationDto;
import me.zhengjie.modules.system.service.dto.DetectionInformationQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.DetectionInformationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author LJL
* @date 2022-04-19
**/
@Service
@RequiredArgsConstructor
public class DetectionInformationServiceImpl implements DetectionInformationService {

    private final DetectionInformationRepository detectionInformationRepository;
    private final DetectionInformationMapper detectionInformationMapper;

    @Override
    public Map<String,Object> queryAll(DetectionInformationQueryCriteria criteria, Pageable pageable){
        Page<DetectionInformation> page = detectionInformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(detectionInformationMapper::toDto));
    }

    @Override
    public List<DetectionInformationDto> queryAll(DetectionInformationQueryCriteria criteria){
        return detectionInformationMapper.toDto(detectionInformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public DetectionInformationDto findById(Long detectionId) {
        DetectionInformation detectionInformation = detectionInformationRepository.findById(detectionId).orElseGet(DetectionInformation::new);
        ValidationUtil.isNull(detectionInformation.getDetectionId(),"DetectionInformation","detectionId",detectionId);
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
        ValidationUtil.isNull( detectionInformation.getDetectionId(),"DetectionInformation","id",resources.getDetectionId());
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
            Map<String,Object> map = new LinkedHashMap<>();
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
            map.put(" beizhu1",  detectionInformation.getBeizhu1());
            map.put(" beizhu2",  detectionInformation.getBeizhu2());
            map.put(" beizhu3",  detectionInformation.getBeizhu3());
            map.put(" beizhu4",  detectionInformation.getBeizhu4());
            map.put(" beizhu5",  detectionInformation.getBeizhu5());
            map.put(" beizhu6",  detectionInformation.getBeizhu6());
            map.put(" beizhu7",  detectionInformation.getBeizhu7());
            map.put(" beizhu8",  detectionInformation.getBeizhu8());
            map.put(" beizhu9",  detectionInformation.getBeizhu9());
            map.put(" beizhu10",  detectionInformation.getBeizhu10());
            map.put(" beizhu11",  detectionInformation.getBeizhu11());
            map.put(" beizhu12",  detectionInformation.getBeizhu12());
            map.put(" beizhu13",  detectionInformation.getBeizhu13());
            map.put(" beizhu14",  detectionInformation.getBeizhu14());
            map.put(" beizhu15",  detectionInformation.getBeizhu15());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}