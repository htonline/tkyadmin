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

import me.zhengjie.modules.system.domain.Hotfix;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.HotfixRepository;
import me.zhengjie.modules.system.service.HotfixService;
import me.zhengjie.modules.system.service.dto.HotfixDto;
import me.zhengjie.modules.system.service.dto.HotfixQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.HotfixMapper;
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
* @date 2022-08-24
**/
@Service
@RequiredArgsConstructor
public class HotfixServiceImpl implements HotfixService {

    private final HotfixRepository hotfixRepository;
    private final HotfixMapper hotfixMapper;

    @Override
    public Map<String,Object> queryAll(HotfixQueryCriteria criteria, Pageable pageable){
        Page<Hotfix> page = hotfixRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(hotfixMapper::toDto));
    }

    @Override
    public List<HotfixDto> queryAll(HotfixQueryCriteria criteria){
        return hotfixMapper.toDto(hotfixRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public HotfixDto findById(Integer id) {
        Hotfix hotfix = hotfixRepository.findById(id).orElseGet(Hotfix::new);
        ValidationUtil.isNull(hotfix.getId(),"Hotfix","id",id);
        return hotfixMapper.toDto(hotfix);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HotfixDto create(Hotfix resources) {
        return hotfixMapper.toDto(hotfixRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Hotfix resources) {
        Hotfix hotfix = hotfixRepository.findById(resources.getId()).orElseGet(Hotfix::new);
        ValidationUtil.isNull( hotfix.getId(),"Hotfix","id",resources.getId());
        hotfix.copy(resources);
        hotfixRepository.save(hotfix);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            hotfixRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<HotfixDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (HotfixDto hotfix : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("热修复状态", hotfix.getState());
            map.put("热修复差分包", hotfix.getFilepath());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}