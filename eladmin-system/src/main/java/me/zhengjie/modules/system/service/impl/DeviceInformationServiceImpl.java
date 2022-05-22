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

import me.zhengjie.modules.system.domain.DeviceInformation;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.DeviceInformationRepository;
import me.zhengjie.modules.system.service.DeviceInformationService;
import me.zhengjie.modules.system.service.dto.DeviceInformationDto;
import me.zhengjie.modules.system.service.dto.DeviceInformationQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.DeviceInformationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;

import java.util.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author LJL
* @date 2022-04-19
**/
@Service
@RequiredArgsConstructor
public class DeviceInformationServiceImpl implements DeviceInformationService {

    private final DeviceInformationRepository deviceInformationRepository;
    private final DeviceInformationMapper deviceInformationMapper;

    @Override
    public Map<String,Object> queryAll(DeviceInformationQueryCriteria criteria, Pageable pageable){
        Page<DeviceInformation> page = deviceInformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(deviceInformationMapper::toDto));
    }

    @Override
    public List<DeviceInformationDto> queryAll(DeviceInformationQueryCriteria criteria){
        return deviceInformationMapper.toDto(deviceInformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public DeviceInformationDto findById(Long deviceId) {
        DeviceInformation deviceInformation = deviceInformationRepository.findById(deviceId).orElseGet(DeviceInformation::new);
        ValidationUtil.isNull(deviceInformation.getDeviceId(),"DeviceInformation","deviceId",deviceId);
        return deviceInformationMapper.toDto(deviceInformation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeviceInformationDto create(DeviceInformation resources) {
        return deviceInformationMapper.toDto(deviceInformationRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DeviceInformation resources) {
        DeviceInformation deviceInformation = deviceInformationRepository.findById(resources.getDeviceId()).orElseGet(DeviceInformation::new);
        ValidationUtil.isNull( deviceInformation.getDeviceId(),"DeviceInformation","id",resources.getDeviceId());
        deviceInformation.copy(resources);
        deviceInformationRepository.save(deviceInformation);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long deviceId : ids) {
            deviceInformationRepository.deleteById(deviceId);
        }
    }

    @Override
    public void download(List<DeviceInformationDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DeviceInformationDto deviceInformation : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("设备类型", deviceInformation.getDeviceType());
            map.put("设备型号", deviceInformation.getDeviceModel());
            map.put("设备编号", deviceInformation.getDeviceBianhao());
            map.put("设备照片", deviceInformation.getDevicePhotos());
            map.put("设备证书", deviceInformation.getDeviceCertificate());
            map.put(" beizhu1",  deviceInformation.getBeizhu1());
            map.put(" beizhu2",  deviceInformation.getBeizhu2());
            map.put(" beizhu3",  deviceInformation.getBeizhu3());
            map.put(" beizhu4",  deviceInformation.getBeizhu4());
            map.put(" beizhu5",  deviceInformation.getBeizhu5());
            map.put(" beizhu6",  deviceInformation.getBeizhu6());
            map.put(" beizhu7",  deviceInformation.getBeizhu7());
            map.put(" beizhu8",  deviceInformation.getBeizhu8());
            map.put(" beizhu9",  deviceInformation.getBeizhu9());
            map.put(" beizhu10",  deviceInformation.getBeizhu10());
            map.put(" beizhu11",  deviceInformation.getBeizhu11());
            map.put(" beizhu12",  deviceInformation.getBeizhu12());
            map.put(" beizhu13",  deviceInformation.getBeizhu13());
            map.put(" beizhu14",  deviceInformation.getBeizhu14());
            map.put(" beizhu15",  deviceInformation.getBeizhu15());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public Object queryByDeviceId(String id) {
        Long aLong = Long.valueOf(id);
        DeviceInformation deviceInformation = deviceInformationRepository.findById(aLong).orElseGet(DeviceInformation::new);
        ValidationUtil.isNull(deviceInformation.getDeviceId(),"DeviceInformation","deviceId",aLong);
        HashMap<String,String> map = new HashMap<>();
        map.put("deviceType",deviceInformation.getDeviceType());
        map.put("deviceModel",deviceInformation.getDeviceModel());
        map.put("deviceBianhao",deviceInformation.getDeviceBianhao());
        return map;
    }
}