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

import me.zhengjie.modules.system.domain.TkyDetectionInformation;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.TkyDetectionInformationRepository;
import me.zhengjie.modules.system.service.TkyDetectionInformationService;
import me.zhengjie.modules.system.service.dto.TkyDetectionInformationDto;
import me.zhengjie.modules.system.service.dto.TkyDetectionInformationQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.TkyDetectionInformationMapper;
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
* @author wuxiaoxuan
* @date 2022-06-05
**/
@Service
@RequiredArgsConstructor
public class TkyDetectionInformationServiceImpl implements TkyDetectionInformationService {

    private final TkyDetectionInformationRepository tkyDetectionInformationRepository;
    private final TkyDetectionInformationMapper tkyDetectionInformationMapper;

    @Override
    public Map<String,Object> queryAll(TkyDetectionInformationQueryCriteria criteria, Pageable pageable){
        Page<TkyDetectionInformation> page = tkyDetectionInformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(tkyDetectionInformationMapper::toDto));
    }

    @Override
    public List<TkyDetectionInformationDto> queryAll(TkyDetectionInformationQueryCriteria criteria){
        return tkyDetectionInformationMapper.toDto(tkyDetectionInformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public TkyDetectionInformationDto findById(Integer id) {
        TkyDetectionInformation tkyDetectionInformation = tkyDetectionInformationRepository.findById(id).orElseGet(TkyDetectionInformation::new);
        ValidationUtil.isNull(tkyDetectionInformation.getId(),"TkyDetectionInformation","id",id);
        return tkyDetectionInformationMapper.toDto(tkyDetectionInformation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TkyDetectionInformationDto create(TkyDetectionInformation resources) {
        return tkyDetectionInformationMapper.toDto(tkyDetectionInformationRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(TkyDetectionInformation resources) {
        TkyDetectionInformation tkyDetectionInformation = tkyDetectionInformationRepository.findById(resources.getId()).orElseGet(TkyDetectionInformation::new);
        ValidationUtil.isNull( tkyDetectionInformation.getId(),"TkyDetectionInformation","id",resources.getId());
        tkyDetectionInformation.copy(resources);
        tkyDetectionInformationRepository.save(tkyDetectionInformation);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            tkyDetectionInformationRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<TkyDetectionInformationDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TkyDetectionInformationDto tkyDetectionInformation : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("报验单编号", tkyDetectionInformation.getBydbh());
            map.put("用户账号", tkyDetectionInformation.getAccount());
            map.put("试验类型", tkyDetectionInformation.getTestType());
            map.put("实际检测开始里程", tkyDetectionInformation.getSjstartMile());
            map.put("实际检测结束里程", tkyDetectionInformation.getSjstopMile());
            map.put("厚度数据", tkyDetectionInformation.getAppFileTypeRadar());
            map.put("现场照片", tkyDetectionInformation.getAppFileTypePhoto());
            map.put("备注1", tkyDetectionInformation.getBeizhu1());
            map.put(" beizhu2",  tkyDetectionInformation.getBeizhu2());
            map.put(" beizhu3",  tkyDetectionInformation.getBeizhu3());
            map.put(" beizhu4",  tkyDetectionInformation.getBeizhu4());
            map.put(" beizhu5",  tkyDetectionInformation.getBeizhu5());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}