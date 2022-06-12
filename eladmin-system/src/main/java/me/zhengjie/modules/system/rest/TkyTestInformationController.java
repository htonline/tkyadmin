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

import cn.hutool.core.codec.Base64;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.system.domain.TestInformation;
import me.zhengjie.modules.system.domain.TkyTestInformation;
import me.zhengjie.modules.system.service.TkyTestInformationService;
import me.zhengjie.modules.system.service.dto.TkyTestInformationDto;
import me.zhengjie.modules.system.service.dto.TkyTestInformationQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

/**
* @website https://el-admin.vip
* @author wuxiaoxuan
* @date 2022-05-31
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "api/tky_test_information管理")
@RequestMapping("/api/tkyTestInformation")
public class TkyTestInformationController {

    private final TkyTestInformationService tkyTestInformationService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('tkyTestInformation:list')")
    public void exportTkyTestInformation(HttpServletResponse response, TkyTestInformationQueryCriteria criteria) throws IOException {
        tkyTestInformationService.download(tkyTestInformationService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询api/tky_test_information")
    @ApiOperation("查询api/tky_test_information")
    @PreAuthorize("@el.check('tkyTestInformation:list')")
    public ResponseEntity<Object> queryTkyTestInformation(TkyTestInformationQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(tkyTestInformationService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增api/tky_test_information")
    @ApiOperation("新增api/tky_test_information")
    @PreAuthorize("@el.check('tkyTestInformation:add')")
    public ResponseEntity<Object> createTkyTestInformation(@Validated @RequestBody TkyTestInformation resources){
        return new ResponseEntity<>(tkyTestInformationService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改api/tky_test_information")
    @ApiOperation("修改api/tky_test_information")
    @PreAuthorize("@el.check('tkyTestInformation:edit')")
    public ResponseEntity<Object> updateTkyTestInformation(@Validated @RequestBody TkyTestInformation resources){
        tkyTestInformationService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除api/tky_test_information")
    @ApiOperation("删除api/tky_test_information")
    @PreAuthorize("@el.check('tkyTestInformation:del')")
    public ResponseEntity<Object> deleteTkyTestInformation(@RequestBody String[] ids) {
        tkyTestInformationService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation("更新铁科院数据")
    @PostMapping(value = "/syntkydata")
    public ResponseEntity<Object> syntkydata(@RequestBody Object o) {
//        TkyTestInformationQueryCriteria criteria = new TkyTestInformationQueryCriteria();
//        List<String> listids = new ArrayList<>();
//        List<TkyTestInformationDto> tkyTestInformationDtos = tkyTestInformationService.queryAll(criteria);
//        for (TkyTestInformationDto tkyTestInformationDto : tkyTestInformationDtos) {
//            listids.add(tkyTestInformationDto.getId());
//        }
//        String[] lsids = new String[tkyTestInformationDtos.size()];
//        tkyTestInformationService.deleteAll(listids.toArray(lsids));
        List<TkyTestInformation> tkyTestInformationList = JSON.parseArray(o.toString(), TkyTestInformation.class);
        for (TkyTestInformation tkyTestInformation : tkyTestInformationList) {
            tkyTestInformationService.create(tkyTestInformation);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Autowired
    private ObjectMapper objectMapper;
    @PostMapping("/createErweima")
    public ResponseEntity<Object> createErweima(@Validated @RequestBody TkyTestInformation resources) throws JsonProcessingException {
        System.out.println("123");
        String erweimaPath = "C:/erweima/二维码.jpg";
        File generate = QrCodeUtil.generate(objectMapper.writeValueAsString(resources),600,600,new File(erweimaPath)); //QrCodeUtil.generate(objectMapper.writeValueAsString(resources), 600, 600, new File(erweimaPath));
        String encode = Base64.encode(generate);

        return new ResponseEntity<>("data:image/png;base64,"+encode,HttpStatus.CREATED);
    }

}