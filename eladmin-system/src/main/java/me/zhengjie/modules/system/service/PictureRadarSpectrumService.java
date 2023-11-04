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
package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.PictureRadarSpectrum;
import me.zhengjie.modules.system.service.dto.PictureRadarSpectrumDto;
import me.zhengjie.modules.system.service.dto.PictureRadarSpectrumQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @description 服务接口
* @author Zuohaitao
* @date 2023-11-01
**/
public interface PictureRadarSpectrumService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(PictureRadarSpectrumQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<PictureRadarSpectrumDto>
    */
    List<PictureRadarSpectrumDto> queryAll(PictureRadarSpectrumQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param spectrumId ID
     * @return PictureRadarSpectrumDto
     */
    PictureRadarSpectrumDto findById(Integer spectrumId);

    /**
    * 创建
    * @param resources /
    * @return PictureRadarSpectrumDto
    */
    PictureRadarSpectrumDto create(PictureRadarSpectrum resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(PictureRadarSpectrum resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Integer[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<PictureRadarSpectrumDto> all, HttpServletResponse response) throws IOException;

    void uploadSpectrumPicture(PictureRadarSpectrum pictureRadarSpectrum, MultipartFile file) throws IOException;
}
