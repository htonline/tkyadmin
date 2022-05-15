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
import me.zhengjie.modules.system.domain.DeviceInformation;
import me.zhengjie.modules.system.domain.TunnelInformation;
import me.zhengjie.modules.system.service.DeviceInformationService;
import me.zhengjie.modules.system.service.dto.DeviceInformationQueryCriteria;
import me.zhengjie.service.LocalStorageService;
import me.zhengjie.service.dto.LocalStorageQueryCriteria;
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
* @author LJL
* @date 2022-04-19
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "api/device_information管理")
@RequestMapping("/api/deviceInformation")
public class DeviceInformationController {

    private final DeviceInformationService deviceInformationService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('deviceInformation:list')")
    public void exportDeviceInformation(HttpServletResponse response, DeviceInformationQueryCriteria criteria) throws IOException {
        deviceInformationService.download(deviceInformationService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询api/device_information")
    @ApiOperation("查询api/device_information")
    @PreAuthorize("@el.check('deviceInformation:list')")
    public ResponseEntity<Object> queryDeviceInformation(DeviceInformationQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(deviceInformationService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增api/device_information")
    @ApiOperation("新增api/device_information")
    @PreAuthorize("@el.check('deviceInformation:add')")
    public ResponseEntity<Object> createDeviceInformation(@Validated @RequestBody DeviceInformation resources){
        return new ResponseEntity<>(deviceInformationService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改api/device_information")
    @ApiOperation("修改api/device_information")
    @PreAuthorize("@el.check('deviceInformation:edit')")
    public ResponseEntity<Object> updateDeviceInformation(@Validated @RequestBody DeviceInformation resources){
        deviceInformationService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除api/device_information")
    @ApiOperation("删除api/device_information")
    @PreAuthorize("@el.check('deviceInformation:del')")
    public ResponseEntity<Object> deleteDeviceInformation(@RequestBody Long[] ids) {
        deviceInformationService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    private final LocalStorageService localStorageService;

    @PostMapping("/selectPhotos")
    @Log("查询文件api/selectPhotos")
    @ApiOperation("查询文件")
    public ResponseEntity<Object> selectPhotos(@Validated @RequestBody DeviceInformation resources){
        LocalStorageQueryCriteria LScriteria = new LocalStorageQueryCriteria();
        if( resources.getDevicePhotos()==null){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            LScriteria.setId(Long.valueOf(resources.getDevicePhotos()));
            return new ResponseEntity<>(localStorageService.queryAll(LScriteria),HttpStatus.OK);
        }

    }
    @PostMapping("/selectdeviceCertificate")
    @Log("查询文件api/selectdeviceCertificate")
    @ApiOperation("查询文件")
    public ResponseEntity<Object> selectdeviceCertificate(@Validated @RequestBody DeviceInformation resources){
        LocalStorageQueryCriteria LScriteria = new LocalStorageQueryCriteria();
        if( resources.getDeviceCertificate()==null){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            LScriteria.setId(Long.valueOf(resources.getDeviceCertificate()));
            return new ResponseEntity<>(localStorageService.queryAll(LScriteria),HttpStatus.OK);
        }

    }
}