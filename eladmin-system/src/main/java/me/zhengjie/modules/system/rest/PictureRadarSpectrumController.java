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
import me.zhengjie.modules.system.domain.PictureRadarSpectrum;
import me.zhengjie.modules.system.service.PictureRadarSpectrumService;
import me.zhengjie.modules.system.service.dto.PictureRadarSpectrumQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author Zuohaitao
* @date 2023-11-01
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "api/picture_radar_spectrum管理")
@RequestMapping("/api/pictureRadarSpectrum")
public class PictureRadarSpectrumController {

    private final PictureRadarSpectrumService pictureRadarSpectrumService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('pictureRadarSpectrum:list')")
    public void exportPictureRadarSpectrum(HttpServletResponse response, PictureRadarSpectrumQueryCriteria criteria) throws IOException {
        pictureRadarSpectrumService.download(pictureRadarSpectrumService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询api/picture_radar_spectrum")
    @ApiOperation("查询api/picture_radar_spectrum")
    @PreAuthorize("@el.check('pictureRadarSpectrum:list')")
    public ResponseEntity<Object> queryPictureRadarSpectrum(PictureRadarSpectrumQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(pictureRadarSpectrumService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增api/picture_radar_spectrum")
    @ApiOperation("新增api/picture_radar_spectrum")
    @PreAuthorize("@el.check('pictureRadarSpectrum:add')")
    public ResponseEntity<Object> createPictureRadarSpectrum(@Validated @RequestBody PictureRadarSpectrum resources){
        return new ResponseEntity<>(pictureRadarSpectrumService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改api/picture_radar_spectrum")
    @ApiOperation("修改api/picture_radar_spectrum")
    @PreAuthorize("@el.check('pictureRadarSpectrum:edit')")
    public ResponseEntity<Object> updatePictureRadarSpectrum(@Validated @RequestBody PictureRadarSpectrum resources){
        pictureRadarSpectrumService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除api/picture_radar_spectrum")
    @ApiOperation("删除api/picture_radar_spectrum")
    @PreAuthorize("@el.check('pictureRadarSpectrum:del')")
    public ResponseEntity<Object> deletePictureRadarSpectrum(@RequestBody Integer[] ids) {
        pictureRadarSpectrumService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}