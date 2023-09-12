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

import me.zhengjie.modules.system.domain.RadarAcquisitionUpload;
import me.zhengjie.modules.system.service.dto.RadarAcquisitionUploadDto;
import me.zhengjie.modules.system.service.dto.RadarAcquisitionUploadQueryCriteria;
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
* @date 2023-09-11
**/
public interface RadarAcquisitionUploadService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(RadarAcquisitionUploadQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<RadarAcquisitionUploadDto>
    */
    List<RadarAcquisitionUploadDto> queryAll(RadarAcquisitionUploadQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return RadarAcquisitionUploadDto
     */
    RadarAcquisitionUploadDto findById(Integer id);

    /**
    * 创建
    * @param resources /
    * @return RadarAcquisitionUploadDto
    */
    RadarAcquisitionUploadDto create(RadarAcquisitionUpload resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(RadarAcquisitionUpload resources);

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
    void download(List<RadarAcquisitionUploadDto> all, HttpServletResponse response) throws IOException;

    RadarAcquisitionUpload uploadFile(RadarAcquisitionUpload radarAcquisitionUpload, MultipartFile file);

}
