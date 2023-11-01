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

import me.zhengjie.modules.system.domain.PictureRadarSpectrum;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.PictureRadarSpectrumRepository;
import me.zhengjie.modules.system.service.PictureRadarSpectrumService;
import me.zhengjie.modules.system.service.dto.PictureRadarSpectrumDto;
import me.zhengjie.modules.system.service.dto.PictureRadarSpectrumQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.PictureRadarSpectrumMapper;
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
* @author Zuohaitao
* @date 2023-11-01
**/
@Service
@RequiredArgsConstructor
public class PictureRadarSpectrumServiceImpl implements PictureRadarSpectrumService {

    private final PictureRadarSpectrumRepository pictureRadarSpectrumRepository;
    private final PictureRadarSpectrumMapper pictureRadarSpectrumMapper;

    @Override
    public Map<String,Object> queryAll(PictureRadarSpectrumQueryCriteria criteria, Pageable pageable){
        Page<PictureRadarSpectrum> page = pictureRadarSpectrumRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(pictureRadarSpectrumMapper::toDto));
    }

    @Override
    public List<PictureRadarSpectrumDto> queryAll(PictureRadarSpectrumQueryCriteria criteria){
        return pictureRadarSpectrumMapper.toDto(pictureRadarSpectrumRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public PictureRadarSpectrumDto findById(Integer spectrumId) {
        PictureRadarSpectrum pictureRadarSpectrum = pictureRadarSpectrumRepository.findById(spectrumId).orElseGet(PictureRadarSpectrum::new);
        ValidationUtil.isNull(pictureRadarSpectrum.getSpectrumId(),"PictureRadarSpectrum","spectrumId",spectrumId);
        return pictureRadarSpectrumMapper.toDto(pictureRadarSpectrum);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PictureRadarSpectrumDto create(PictureRadarSpectrum resources) {
        return pictureRadarSpectrumMapper.toDto(pictureRadarSpectrumRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PictureRadarSpectrum resources) {
        PictureRadarSpectrum pictureRadarSpectrum = pictureRadarSpectrumRepository.findById(resources.getSpectrumId()).orElseGet(PictureRadarSpectrum::new);
        ValidationUtil.isNull( pictureRadarSpectrum.getSpectrumId(),"PictureRadarSpectrum","id",resources.getSpectrumId());
        pictureRadarSpectrum.copy(resources);
        pictureRadarSpectrumRepository.save(pictureRadarSpectrum);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer spectrumId : ids) {
            pictureRadarSpectrumRepository.deleteById(spectrumId);
        }
    }

    @Override
    public void download(List<PictureRadarSpectrumDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (PictureRadarSpectrumDto pictureRadarSpectrum : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("文件存储路径", pictureRadarSpectrum.getFileUrl());
            map.put("外键，关联tunel表", pictureRadarSpectrum.getTunnelId());
            map.put("备注", pictureRadarSpectrum.getRemark1());
            map.put("备注", pictureRadarSpectrum.getRemark2());
            map.put("备注", pictureRadarSpectrum.getRemark3());
            map.put("备注", pictureRadarSpectrum.getRemark4());
            map.put("备注", pictureRadarSpectrum.getRemark5());
            map.put("备注", pictureRadarSpectrum.getRemark6());
            map.put("备注", pictureRadarSpectrum.getRemark7());
            map.put("备注", pictureRadarSpectrum.getRemark8());
            map.put("备注", pictureRadarSpectrum.getRemark());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}