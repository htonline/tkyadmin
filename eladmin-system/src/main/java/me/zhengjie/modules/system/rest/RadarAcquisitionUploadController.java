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
package me.zhengjie.modules.system.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.system.domain.RadarAcquisitionUpload;
import me.zhengjie.modules.system.service.RadarAcquisitionUploadService;
import me.zhengjie.modules.system.service.dto.RadarAcquisitionUploadQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author Zuohaitao
* @date 2023-09-11
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "api/radar_acquisition_upload管理")
@RequestMapping("/api/radarAcquisitionUpload")
public class RadarAcquisitionUploadController {

    private final RadarAcquisitionUploadService radarAcquisitionUploadService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('radarAcquisitionUpload:list')")
    public void exportRadarAcquisitionUpload(HttpServletResponse response, RadarAcquisitionUploadQueryCriteria criteria) throws IOException {
        radarAcquisitionUploadService.download(radarAcquisitionUploadService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询api/radar_acquisition_upload")
    @ApiOperation("查询api/radar_acquisition_upload")
    @PreAuthorize("@el.check('radarAcquisitionUpload:list')")
    public ResponseEntity<Object> queryRadarAcquisitionUpload(RadarAcquisitionUploadQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(radarAcquisitionUploadService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增api/radar_acquisition_upload")
    @ApiOperation("新增api/radar_acquisition_upload")
    @PreAuthorize("@el.check('radarAcquisitionUpload:add')")
    public ResponseEntity<Object> createRadarAcquisitionUpload(@Validated @RequestBody RadarAcquisitionUpload resources){
        return new ResponseEntity<>(radarAcquisitionUploadService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改api/radar_acquisition_upload")
    @ApiOperation("修改api/radar_acquisition_upload")
    @PreAuthorize("@el.check('radarAcquisitionUpload:edit')")
    public ResponseEntity<Object> updateRadarAcquisitionUpload(@Validated @RequestBody RadarAcquisitionUpload resources){
        radarAcquisitionUploadService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除api/radar_acquisition_upload")
    @ApiOperation("删除api/radar_acquisition_upload")
    @PreAuthorize("@el.check('radarAcquisitionUpload:del')")
    public ResponseEntity<Object> deleteRadarAcquisitionUpload(@RequestBody Integer[] ids) {
        radarAcquisitionUploadService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("上传雷达采集数据")
    @PostMapping(value = "/uploadFile")
    public ResponseEntity<Object> uploadPicture(@RequestParam String author,
                                                @RequestParam("file") MultipartFile file){
        RadarAcquisitionUpload radarAcquisitionUpload = new RadarAcquisitionUpload();
        radarAcquisitionUpload.setByUser(author);
        RadarAcquisitionUpload saveResult = radarAcquisitionUploadService.uploadFile(radarAcquisitionUpload, file);
        return new ResponseEntity<>(saveResult,HttpStatus.OK);
    }
}
