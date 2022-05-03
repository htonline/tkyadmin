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

import cn.hutool.core.util.ZipUtil;
import me.zhengjie.domain.LocalStorage;
import me.zhengjie.modules.system.domain.TunnelInformation;
import me.zhengjie.utils.*;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.TunnelInformationRepository;
import me.zhengjie.modules.system.service.TunnelInformationService;
import me.zhengjie.modules.system.service.dto.TunnelInformationDto;
import me.zhengjie.modules.system.service.dto.TunnelInformationQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.TunnelInformationMapper;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author LJL
* @date 2022-04-19
**/
@Service
@RequiredArgsConstructor
public class TunnelInformationServiceImpl implements TunnelInformationService {

    private final TunnelInformationRepository tunnelInformationRepository;
    private final TunnelInformationMapper tunnelInformationMapper;

    @Override
    public Map<String,Object> queryAll(TunnelInformationQueryCriteria criteria, Pageable pageable){
        Page<TunnelInformation> page = tunnelInformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(tunnelInformationMapper::toDto));
    }

    @Override
    public List<TunnelInformationDto> queryAll(TunnelInformationQueryCriteria criteria){
        return tunnelInformationMapper.toDto(tunnelInformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public TunnelInformationDto findById(Long tunnelInforId) {
        TunnelInformation tunnelInformation = tunnelInformationRepository.findById(tunnelInforId).orElseGet(TunnelInformation::new);
        ValidationUtil.isNull(tunnelInformation.getTunnelInforId(),"TunnelInformation","tunnelInforId",tunnelInforId);
        return tunnelInformationMapper.toDto(tunnelInformation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TunnelInformationDto create(TunnelInformation resources) {
        return tunnelInformationMapper.toDto(tunnelInformationRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(TunnelInformation resources) {
        TunnelInformation tunnelInformation = tunnelInformationRepository.findById(resources.getTunnelInforId()).orElseGet(TunnelInformation::new);
        ValidationUtil.isNull( tunnelInformation.getTunnelInforId(),"TunnelInformation","id",resources.getTunnelInforId());
        tunnelInformation.copy(resources);
        tunnelInformationRepository.save(tunnelInformation);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long tunnelInforId : ids) {
            tunnelInformationRepository.deleteById(tunnelInforId);
        }
    }

    @Override
    public void download(List<TunnelInformationDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TunnelInformationDto tunnelInformation : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("项目名称", tunnelInformation.getProjectName());
            map.put("标段名称", tunnelInformation.getSectionName());
            map.put("隧道编号", tunnelInformation.getTunnelId());
            map.put("隧道名称", tunnelInformation.getTunnelName());
            map.put("工点名称", tunnelInformation.getWorksiteName());
            map.put("隧道起始里程", tunnelInformation.getTunnelStartingDistance());
            map.put("隧道结束里程", tunnelInformation.getTunnelEndingDistance());
            map.put("隧道长度", tunnelInformation.getTunnelLength());
            map.put("建设单位", tunnelInformation.getRepairCompany());
            map.put("监理单位", tunnelInformation.getSupervisionCompany());
            map.put("检测单位", tunnelInformation.getDetectionCompany());
            map.put("施工单位", tunnelInformation.getConstructionCompany());
            map.put("用户ID", tunnelInformation.getUserId());
            map.put("用户名", tunnelInformation.getUserName());
            map.put("用户权限", tunnelInformation.getDeptId());
            map.put("数据新增时间", tunnelInformation.getTime());
            map.put(" beizhu1",  tunnelInformation.getBeizhu1());
            map.put(" beizhu2",  tunnelInformation.getBeizhu2());
            map.put(" beizhu3",  tunnelInformation.getBeizhu3());
            map.put(" beizhu4",  tunnelInformation.getBeizhu4());
            map.put(" beizhu5",  tunnelInformation.getBeizhu5());
            map.put(" beizhu6",  tunnelInformation.getBeizhu6());
            map.put(" beizhu7",  tunnelInformation.getBeizhu7());
            map.put(" beizhu8",  tunnelInformation.getBeizhu8());
            map.put(" beizhu9",  tunnelInformation.getBeizhu9());
            map.put(" beizhu10",  tunnelInformation.getBeizhu10());
            map.put(" beizhu11",  tunnelInformation.getBeizhu11());
            map.put(" beizhu12",  tunnelInformation.getBeizhu12());
            map.put(" beizhu13",  tunnelInformation.getBeizhu13());
            map.put(" beizhu14",  tunnelInformation.getBeizhu14());
            map.put(" beizhu15",  tunnelInformation.getBeizhu15());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    @Override
    public void downloadFile(LocalStorage resources, HttpServletResponse response, HttpServletRequest request) throws IOException {
        File file = new File(resources.getPath());
        FileUtil.downloadFile(request, response, file, true);
    }

}