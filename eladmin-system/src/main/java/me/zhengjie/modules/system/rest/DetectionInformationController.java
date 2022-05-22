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
import me.zhengjie.modules.system.domain.DefectInformation;
import me.zhengjie.modules.system.domain.DetectionInformation;
import me.zhengjie.modules.system.service.DetectionInformationService;
import me.zhengjie.modules.system.service.UploadService;
import me.zhengjie.modules.system.service.dto.DetectionInformationQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author LJL
 * @website https://el-admin.vip
 * @date 2022-04-19
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "api/detection_information管理")
@RequestMapping("/api/detectionInformation")
public class DetectionInformationController {

    private final DetectionInformationService detectionInformationService;
    @Autowired
    private final UploadService uploadService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('detectionInformation:list')")
    public void exportDetectionInformation(HttpServletResponse response, DetectionInformationQueryCriteria criteria) throws IOException {
        detectionInformationService.download(detectionInformationService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询api/detection_information")
    @ApiOperation("查询api/detection_information")
    @PreAuthorize("@el.check('detectionInformation:list')")
    public ResponseEntity<Object> queryDetectionInformation(DetectionInformationQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(detectionInformationService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增api/detection_information")
    @ApiOperation("新增api/detection_information")
    @PreAuthorize("@el.check('detectionInformation:add')")
    public ResponseEntity<Object> createDetectionInformation(@Validated @RequestBody DetectionInformation resources) {
        return new ResponseEntity<>(detectionInformationService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改api/detection_information")
    @ApiOperation("修改api/detection_information")
    @PreAuthorize("@el.check('detectionInformation:edit')")
    public ResponseEntity<Object> updateDetectionInformation(@Validated @RequestBody DetectionInformation resources) {
        detectionInformationService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除api/detection_information")
    @ApiOperation("删除api/detection_information")
    @PreAuthorize("@el.check('detectionInformation:del')")
    public ResponseEntity<Object> deleteDetectionInformation(@RequestBody Long[] ids) {
        detectionInformationService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("下载文件")
    @PostMapping(value = "/{type}")
    public ResponseEntity<Object> generator(@Validated @RequestBody DetectionInformation resources, @PathVariable Integer type, HttpServletRequest request, HttpServletResponse response) {
        if (type > 0) {
            try {
                detectionInformationService.downloadFile(resources.getDetectionData(), resources.getDetectionPhotos(), resources.getRadarPhotos(), resources.getDetectionSummary(),
                        resources.getOthers(), request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (type == -1) {
            try {
                detectionInformationService.downloadOneFile(resources.getRadarPhotos(), request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (type == -2) {
            try {
                detectionInformationService.downloadOneFile(resources.getDetectionSummary(), request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (type == -3) {
            try {
                detectionInformationService.downloadOneFile(resources.getOthers(), request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            throw new BadRequestException("没有这个选项!");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation("更新雷达图谱")
    @PostMapping(value = "/updateRadarPhoto")
    public ResponseEntity<Object> updateRadarPhoto(@RequestParam("file") MultipartFile multipartFile, @RequestParam String data) {
//        System.out.println(data);
        Map<String, String> uploadfile = uploadService.uploadfile(multipartFile);
        String avatar = uploadfile.get("avatar");
        detectionInformationService.updateRadarPhoto(Long.valueOf(data), avatar);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("更新检测报告")
    @PostMapping(value = "/updateDetectionSummary")
    public ResponseEntity<Object> updateDetectionSummary(@RequestParam("file") MultipartFile multipartFile, @RequestParam String data) {
//        System.out.println(data);
        Map<String, String> uploadfile = uploadService.uploadfile(multipartFile);
        String avatar = uploadfile.get("avatar");
        detectionInformationService.updateDetectionSummary(Long.valueOf(data), avatar);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("更新其他附件")
    @PostMapping(value = "/uploadOthers")
    public ResponseEntity<Object> uploadOthers(@RequestParam("file") MultipartFile multipartFile, @RequestParam String data) {
        System.out.println(data);
        Map<String, String> uploadfile = uploadService.uploadfile(multipartFile);
        String avatar = uploadfile.get("avatar");
        detectionInformationService.uploadOthers(Long.valueOf(data), avatar);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}