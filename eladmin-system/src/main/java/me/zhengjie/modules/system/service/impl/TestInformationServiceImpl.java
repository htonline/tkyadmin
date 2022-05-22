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

import me.zhengjie.modules.system.domain.TestInformation;
import me.zhengjie.modules.system.service.dto.TestInformationDto;
import me.zhengjie.modules.system.service.dto.TestInformationQueryCriteria;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.TestInformationRepository;
import me.zhengjie.modules.system.service.TestInformationService;
import me.zhengjie.modules.system.service.mapstruct.TestInformationMapper;
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
public class TestInformationServiceImpl implements TestInformationService {

    private final TestInformationRepository testInformationRepository;
    private final TestInformationMapper testInformationMapper;

    @Override
    public Map<String,Object> queryAll(TestInformationQueryCriteria criteria, Pageable pageable){
        Page<TestInformation> page = testInformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(testInformationMapper::toDto));
    }

    @Override
    public List<TestInformationDto> queryAll(TestInformationQueryCriteria criteria){
        return testInformationMapper.toDto(testInformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public TestInformationDto findById(Long testInforId) {
        TestInformation testInformation = testInformationRepository.findById(testInforId).orElseGet(TestInformation::new);
        ValidationUtil.isNull(testInformation.getTestInforId(),"TestInformation","testInforId",testInforId);
        return testInformationMapper.toDto(testInformation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TestInformationDto create(TestInformation resources) {
        return testInformationMapper.toDto(testInformationRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(TestInformation resources) {
        TestInformation testInformation = testInformationRepository.findById(resources.getTestInforId()).orElseGet(TestInformation::new);
        ValidationUtil.isNull( testInformation.getTestInforId(),"TestInformation","id",resources.getTestInforId());
        testInformation.copy(resources);
        testInformationRepository.save(testInformation);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long testInforId : ids) {
            testInformationRepository.deleteById(testInforId);
        }
    }

    @Override
    public void download(List<TestInformationDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TestInformationDto testInformation : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("报检号", testInformation.getTestId());
            map.put("申请检测时间", testInformation.getTestTime());
            map.put("待检区段起始里程", testInformation.getTestStartingDistance());
            map.put("待检区段结束里程", testInformation.getTestEndingDistance());
            map.put("待检区段长度", testInformation.getTestLength());
            map.put("围岩类型", testInformation.getWallRockType());
            map.put("初支厚度", testInformation.getSupportTickness());
            map.put("间距(m/榀)", testInformation.getSeparationDistance());
            map.put("钢筋网间距", testInformation.getMeshDistance());
            map.put("环向钢筋间距", testInformation.getAnnularBarDistance());
            map.put("钢筋保护厚度", testInformation.getReinforPrtTickness());
            map.put("二次衬砌厚度-拱部", testInformation.getSecLineArchTickness());
            map.put("二次衬砌厚度-边墙", testInformation.getSecLineWallTickness());
            map.put("二次衬砌厚度-仰拱", testInformation.getSecLineInverArchTickness());
            map.put("二次衬砌厚度-填充", testInformation.getSecLineFilerTickness());
            map.put("项目名称", testInformation.getProjectName());
            map.put("标段名称", testInformation.getSectionName());
            map.put("隧道名称", testInformation.getTunnelName());
            map.put("工点名称", testInformation.getWorksiteName());
            map.put("状态", testInformation.getStatute());
            map.put(" beizhu1",  testInformation.getBeizhu1());
            map.put(" beizhu2",  testInformation.getBeizhu2());
            map.put(" beizhu3",  testInformation.getBeizhu3());
            map.put(" beizhu4",  testInformation.getBeizhu4());
            map.put(" beizhu5",  testInformation.getBeizhu5());
            map.put(" beizhu6",  testInformation.getBeizhu6());
            map.put(" beizhu7",  testInformation.getBeizhu7());
            map.put(" beizhu8",  testInformation.getBeizhu8());
            map.put(" beizhu9",  testInformation.getBeizhu9());
            map.put(" beizhu10",  testInformation.getBeizhu10());
            map.put(" beizhu11",  testInformation.getBeizhu11());
            map.put(" beizhu12",  testInformation.getBeizhu12());
            map.put(" beizhu13",  testInformation.getBeizhu13());
            map.put(" beizhu14",  testInformation.getBeizhu14());
            map.put(" beizhu15",  testInformation.getBeizhu15());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}