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
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.system.domain.DetectionInformation;
import me.zhengjie.modules.system.domain.TkyDetectionInformation;
import me.zhengjie.modules.system.service.TkyDetectionInformationService;
import me.zhengjie.modules.system.service.dto.TkyDetectionInformationDto;
import me.zhengjie.modules.system.service.dto.TkyDetectionInformationQueryCriteria;
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
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author wuxiaoxuan
* @date 2022-06-05
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "api/tky_detection_information管理")
@RequestMapping("/api/tkyDetectionInformation")
public class TkyDetectionInformationController {

    private final TkyDetectionInformationService tkyDetectionInformationService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('tkyDetectionInformation:list')")
    public void exportTkyDetectionInformation(HttpServletResponse response, TkyDetectionInformationQueryCriteria criteria) throws IOException {
        tkyDetectionInformationService.download(tkyDetectionInformationService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询api/tky_detection_information")
    @ApiOperation("查询api/tky_detection_information")
    @PreAuthorize("@el.check('tkyDetectionInformation:list')")
    public ResponseEntity<Object> queryTkyDetectionInformation(TkyDetectionInformationQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(tkyDetectionInformationService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增api/tky_detection_information")
    @ApiOperation("新增api/tky_detection_information")
    @PreAuthorize("@el.check('tkyDetectionInformation:add')")
    public ResponseEntity<Object> createTkyDetectionInformation(@Validated @RequestBody TkyDetectionInformation resources){
        TkyDetectionInformationQueryCriteria criteria = new TkyDetectionInformationQueryCriteria();
        criteria.setBydbh(resources.getBydbh());
        criteria.setAppFileTypeRadar(resources.getAppFileTypeRadar());
        criteria.setBeizhu1(resources.getBeizhu1());
        List<TkyDetectionInformationDto> tkyDetectionInformationDtos = tkyDetectionInformationService.queryAll(criteria);
        if (tkyDetectionInformationDtos.size() == 0){
            tkyDetectionInformationService.create(resources);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    tkyDetectionInformationService.uploadDZSdata(resources.getBydbh(),resources.getSjstartMile(),
                            resources.getSjstopMile(),resources.getAppFileTypePhoto(),"0",resources.getAccount(),resources.getBeizhu1());
                    tkyDetectionInformationService.uploadDZSdata(resources.getBydbh(),resources.getSjstartMile(),
                            resources.getSjstopMile(),resources.getAppFileTypeRadar(),"3",resources.getAccount(),resources.getBeizhu1());
                }
            }).start();
            return new ResponseEntity<>(null,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(null,HttpStatus.CREATED);
        }
    }

    @PutMapping
    @Log("修改api/tky_detection_information")
    @ApiOperation("修改api/tky_detection_information")
    public ResponseEntity<Object> updateTkyDetectionInformation(@Validated @RequestBody TkyDetectionInformation resources){
        tkyDetectionInformationService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除api/tky_detection_information")
    @ApiOperation("删除api/tky_detection_information")
    @PreAuthorize("@el.check('tkyDetectionInformation:del')")
    public ResponseEntity<Object> deleteTkyDetectionInformation(@RequestBody Integer[] ids) {
        tkyDetectionInformationService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation("更新其他附件")
    @PostMapping(value = "/uploadDZSdata")
    public ResponseEntity<Object> uploadDZSdata(@RequestBody TkyDetectionInformation resources) {
        tkyDetectionInformationService.uploadDZSdata(resources.getBydbh(),resources.getSjstartMile(),
                resources.getSjstopMile(),resources.getAppFileTypePhoto(),"0",resources.getAccount(),resources.getBeizhu1());
        tkyDetectionInformationService.uploadDZSdata(resources.getBydbh(),resources.getSjstartMile(),
                resources.getSjstopMile(),resources.getAppFileTypeRadar(),"3",resources.getAccount(),resources.getBeizhu1());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("下载文件")
    @PostMapping(value = "/{type}")
    public ResponseEntity<Object> generator(@Validated @RequestBody TkyDetectionInformation resources, @PathVariable Integer type, HttpServletRequest request, HttpServletResponse response) {
        tkyDetectionInformationService.downloadFile(resources.getAppFileTypeRadar(), resources.getAppFileTypePhoto(), response);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}