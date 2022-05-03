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
import me.zhengjie.modules.system.domain.TestInformation;
import me.zhengjie.modules.system.service.TestInformationService;
import me.zhengjie.modules.system.service.dto.TestInformationQueryCriteria;
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
@Api(tags = "api/test_information管理")
@RequestMapping("/api/testInformation")
public class TestInformationController {

    private final TestInformationService testInformationService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('testInformation:list')")
    public void exportTestInformation(HttpServletResponse response, TestInformationQueryCriteria criteria) throws IOException {
        testInformationService.download(testInformationService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询api/test_information")
    @ApiOperation("查询api/test_information")
    @PreAuthorize("@el.check('testInformation:list')")
    public ResponseEntity<Object> queryTestInformation(TestInformationQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(testInformationService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增api/test_information")
    @ApiOperation("新增api/test_information")
    @PreAuthorize("@el.check('testInformation:add')")
    public ResponseEntity<Object> createTestInformation(@Validated @RequestBody TestInformation resources){
        return new ResponseEntity<>(testInformationService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改api/test_information")
    @ApiOperation("修改api/test_information")
    @PreAuthorize("@el.check('testInformation:edit')")
    public ResponseEntity<Object> updateTestInformation(@Validated @RequestBody TestInformation resources){
        testInformationService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除api/test_information")
    @ApiOperation("删除api/test_information")
    @PreAuthorize("@el.check('testInformation:del')")
    public ResponseEntity<Object> deleteTestInformation(@RequestBody Long[] ids) {
        testInformationService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}