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

import me.zhengjie.modules.system.domain.UserInfo;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.UserInfoRepository;
import me.zhengjie.modules.system.service.UserInfoService;
import me.zhengjie.modules.system.service.dto.UserInfoDto;
import me.zhengjie.modules.system.service.dto.UserInfoQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.UserInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
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
* @date 2022-05-09
**/
@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final UserInfoMapper userInfoMapper;

    @Override
    public Map<String,Object> queryAll(UserInfoQueryCriteria criteria, Pageable pageable){
        Page<UserInfo> page = userInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(userInfoMapper::toDto));
    }

    @Override
    public List<UserInfoDto> queryAll(UserInfoQueryCriteria criteria){
        return userInfoMapper.toDto(userInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public UserInfoDto findById(Long userId) {
        UserInfo userInfo = userInfoRepository.findById(userId).orElseGet(UserInfo::new);
        ValidationUtil.isNull(userInfo.getUserId(),"UserInfo","userId",userId);
        return userInfoMapper.toDto(userInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserInfoDto create(UserInfo resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setUserId(snowflake.nextId()); 
        return userInfoMapper.toDto(userInfoRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserInfo resources) {
        UserInfo userInfo = userInfoRepository.findById(resources.getUserId()).orElseGet(UserInfo::new);
        ValidationUtil.isNull( userInfo.getUserId(),"UserInfo","id",resources.getUserId());
        userInfo.copy(resources);
        userInfoRepository.save(userInfo);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long userId : ids) {
            userInfoRepository.deleteById(userId);
        }
    }

    @Override
    public void download(List<UserInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserInfoDto userInfo : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" deptId",  userInfo.getDeptId());
            map.put(" username",  userInfo.getUsername());
            map.put(" nickName",  userInfo.getNickName());
            map.put(" deptName",  userInfo.getDeptName());
            map.put(" beizhu1",  userInfo.getBeizhu1());
            map.put(" beizhu2",  userInfo.getBeizhu2());
            map.put(" beizhu3",  userInfo.getBeizhu3());
            map.put(" beizhu4",  userInfo.getBeizhu4());
            map.put(" beizhu5",  userInfo.getBeizhu5());
            map.put(" beizhu6",  userInfo.getBeizhu6());
            map.put(" beizhu7",  userInfo.getBeizhu7());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}