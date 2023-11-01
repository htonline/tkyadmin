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

import me.zhengjie.modules.system.domain.Tunnel;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.TunnelRepository;
import me.zhengjie.modules.system.service.TunnelService;
import me.zhengjie.modules.system.service.dto.TunnelDto;
import me.zhengjie.modules.system.service.dto.TunnelQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.TunnelMapper;
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
* @author zuohaitao
* @date 2023-11-01
**/
@Service
@RequiredArgsConstructor
public class TunnelServiceImpl implements TunnelService {

    private final TunnelRepository tunnelRepository;
    private final TunnelMapper tunnelMapper;

    @Override
    public Map<String,Object> queryAll(TunnelQueryCriteria criteria, Pageable pageable){
        Page<Tunnel> page = tunnelRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(tunnelMapper::toDto));
    }

    @Override
    public List<TunnelDto> queryAll(TunnelQueryCriteria criteria){
        return tunnelMapper.toDto(tunnelRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public TunnelDto findById(Integer tunnelId) {
        Tunnel tunnel = tunnelRepository.findById(tunnelId).orElseGet(Tunnel::new);
        ValidationUtil.isNull(tunnel.getTunnelId(),"Tunnel","tunnelId",tunnelId);
        return tunnelMapper.toDto(tunnel);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TunnelDto create(Tunnel resources) {
        return tunnelMapper.toDto(tunnelRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Tunnel resources) {
        Tunnel tunnel = tunnelRepository.findById(resources.getTunnelId()).orElseGet(Tunnel::new);
        ValidationUtil.isNull( tunnel.getTunnelId(),"Tunnel","id",resources.getTunnelId());
        tunnel.copy(resources);
        tunnelRepository.save(tunnel);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer tunnelId : ids) {
            tunnelRepository.deleteById(tunnelId);
        }
    }

    @Override
    public void download(List<TunnelDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TunnelDto tunnel : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("检测位置的名称", tunnel.getDetectLocation());
            map.put("检测位置的经度(标红点的位置)", tunnel.getDetectLocationLng());
            map.put("检测位置的纬度(标红点的位置)", tunnel.getDetectLocationLat());
            map.put("检测位置所属的省份", tunnel.getProvince());
            map.put("城市", tunnel.getCity());
            map.put("区域", tunnel.getDistrict());
            map.put("隧道的病害类型", tunnel.getDiseaseType());
            map.put("病害描述", tunnel.getDiseaseDescription());
            map.put("病害的修复方法", tunnel.getRepairMethod());
            map.put("该隧道病害的修复状态（1：紧急；2：一般；3：正在修复；4：已修复）", tunnel.getTunnelState());
            map.put("隧道起点的经度", tunnel.getTunnelStartPointLng());
            map.put("隧道起点的纬度", tunnel.getTunnelStartPointLat());
            map.put("隧道终点的经度", tunnel.getTunnelStopPointLng());
            map.put("隧道终点的纬度", tunnel.getTunnelStopPointLat());
            map.put("创建时间", tunnel.getCreateTime());
            map.put("备注", tunnel.getRemark());
            map.put("备注", tunnel.getRemark1());
            map.put("备注", tunnel.getRemark2());
            map.put("备注", tunnel.getRemark3());
            map.put("备注", tunnel.getRemark4());
            map.put("备注", tunnel.getRemark5());
            map.put("备注", tunnel.getRemark6());
            map.put("备注", tunnel.getRemark7());
            map.put("备注", tunnel.getRemark8());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}