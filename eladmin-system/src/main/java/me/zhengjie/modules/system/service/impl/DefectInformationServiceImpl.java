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

import me.zhengjie.modules.system.domain.DefectInformation;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.DefectInformationRepository;
import me.zhengjie.modules.system.service.DefectInformationService;
import me.zhengjie.modules.system.service.dto.DefectInformationDto;
import me.zhengjie.modules.system.service.dto.DefectInformationQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.DefectInformationMapper;
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
public class DefectInformationServiceImpl implements DefectInformationService {

    private final DefectInformationRepository defectInformationRepository;
    private final DefectInformationMapper defectInformationMapper;

    @Override
    public Map<String,Object> queryAll(DefectInformationQueryCriteria criteria, Pageable pageable){
        Page<DefectInformation> page = defectInformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(defectInformationMapper::toDto));
    }

    @Override
    public List<DefectInformationDto> queryAll(DefectInformationQueryCriteria criteria){
        return defectInformationMapper.toDto(defectInformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public DefectInformationDto findById(Long defectId) {
        DefectInformation defectInformation = defectInformationRepository.findById(defectId).orElseGet(DefectInformation::new);
        ValidationUtil.isNull(defectInformation.getDefectId(),"DefectInformation","defectId",defectId);
        return defectInformationMapper.toDto(defectInformation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DefectInformationDto create(DefectInformation resources) {
        return defectInformationMapper.toDto(defectInformationRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DefectInformation resources) {
        DefectInformation defectInformation = defectInformationRepository.findById(resources.getDefectId()).orElseGet(DefectInformation::new);
        ValidationUtil.isNull( defectInformation.getDefectId(),"DefectInformation","id",resources.getDefectId());
        defectInformation.copy(resources);
        defectInformationRepository.save(defectInformation);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long defectId : ids) {
            defectInformationRepository.deleteById(defectId);
        }
    }

    @Override
    public void download(List<DefectInformationDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DefectInformationDto defectInformation : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("项目名称", defectInformation.getProjectName());
            map.put("标段名称", defectInformation.getSectionName());
            map.put("隧道编号", defectInformation.getTunnelId());
            map.put("隧道名称", defectInformation.getTunnelName());
            map.put("工点名称", defectInformation.getWorksiteName());
            map.put("检测起点", defectInformation.getDetectionStartingDistance());
            map.put("检测终点", defectInformation.getDetectionEndingDistance());
            map.put("检测长度", defectInformation.getDetectionLength());
            map.put("数据新增时间", defectInformation.getTime());
            map.put("报检号", defectInformation.getTestId());
            map.put("测线编号", defectInformation.getDetectionLineBiaohao());
            map.put("检测数据-上传/下载", defectInformation.getDetectionData());
            map.put("现场照片-上传/下载", defectInformation.getDetectionPhotos());
            map.put("雷达图谱-上传/下载", defectInformation.getRadarPhotos());
            map.put("检测报告_查看/下载", defectInformation.getDetectionSummary());
            map.put("消缺情况_上传/下载/查看", defectInformation.getEliminateDefects());
            map.put("其他附件_上传/下载", defectInformation.getOthers());
            map.put(" beizhu1",  defectInformation.getBeizhu1());
            map.put(" beizhu2",  defectInformation.getBeizhu2());
            map.put(" beizhu3",  defectInformation.getBeizhu3());
            map.put(" beizhu4",  defectInformation.getBeizhu4());
            map.put(" beizhu5",  defectInformation.getBeizhu5());
            map.put(" beizhu6",  defectInformation.getBeizhu6());
            map.put(" beizhu7",  defectInformation.getBeizhu7());
            map.put(" beizhu8",  defectInformation.getBeizhu8());
            map.put(" beizhu9",  defectInformation.getBeizhu9());
            map.put(" beizhu10",  defectInformation.getBeizhu10());
            map.put(" beizhu11",  defectInformation.getBeizhu11());
            map.put(" beizhu12",  defectInformation.getBeizhu12());
            map.put(" beizhu13",  defectInformation.getBeizhu13());
            map.put(" beizhu14",  defectInformation.getBeizhu14());
            map.put(" beizhu15",  defectInformation.getBeizhu15());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}