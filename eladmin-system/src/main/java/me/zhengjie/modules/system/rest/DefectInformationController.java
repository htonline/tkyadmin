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
import me.zhengjie.modules.system.domain.DefectInformation;
import me.zhengjie.modules.system.service.DefectInformationService;
import me.zhengjie.modules.system.service.dto.DefectInformationQueryCriteria;
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
@Api(tags = "api/defect_information管理")
@RequestMapping("/api/defectInformation")
public class DefectInformationController {

    private final DefectInformationService defectInformationService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('defectInformation:list')")
    public void exportDefectInformation(HttpServletResponse response, DefectInformationQueryCriteria criteria) throws IOException {
        defectInformationService.download(defectInformationService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询api/defect_information")
    @ApiOperation("查询api/defect_information")
    @PreAuthorize("@el.check('defectInformation:list')")
    public ResponseEntity<Object> queryDefectInformation(DefectInformationQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(defectInformationService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增api/defect_information")
    @ApiOperation("新增api/defect_information")
    @PreAuthorize("@el.check('defectInformation:add')")
    public ResponseEntity<Object> createDefectInformation(@Validated @RequestBody DefectInformation resources){
        return new ResponseEntity<>(defectInformationService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改api/defect_information")
    @ApiOperation("修改api/defect_information")
    @PreAuthorize("@el.check('defectInformation:edit')")
    public ResponseEntity<Object> updateDefectInformation(@Validated @RequestBody DefectInformation resources){
        defectInformationService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除api/defect_information")
    @ApiOperation("删除api/defect_information")
    @PreAuthorize("@el.check('defectInformation:del')")
    public ResponseEntity<Object> deleteDefectInformation(@RequestBody Long[] ids) {
        defectInformationService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}