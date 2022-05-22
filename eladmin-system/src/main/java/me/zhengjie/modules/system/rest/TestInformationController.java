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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.zhengjie.annotation.Log;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.system.domain.DetectionInformation;
import me.zhengjie.modules.system.domain.TestInformation;
import me.zhengjie.modules.system.domain.TunnelInformation;
import me.zhengjie.modules.system.service.*;
import me.zhengjie.modules.system.service.dto.*;
import me.zhengjie.modules.system.service.mapstruct.TestInformationMapper;
import me.zhengjie.utils.SecurityUtils;
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private final UploadService uploadService;
    @Autowired
    private  DeptService deptService;
    @Autowired
    private  UserService userService;



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
    public ResponseEntity<Object> queryTestInformation(TestInformationQueryCriteria criteria, Pageable pageable) {

        if(SecurityUtils.getCurrentUsername().equals("admin")) {
            return new ResponseEntity<>(testInformationService.queryAll(criteria, pageable), HttpStatus.OK);
        }else {
           // System.out.println("ffffffffffffffffffffffffffffff"+userService.findById(SecurityUtils.getCurrentUserId()).toString());
            if(userService.findById(SecurityUtils.getCurrentUserId()).getDept().getName().equals("施工单位")){
                criteria.setBeizhu5(userService.findById(SecurityUtils.getCurrentUserId()).getNickName());
                return new ResponseEntity<>(testInformationService.queryAll(criteria, pageable), HttpStatus.OK);
            }else if(userService.findById(SecurityUtils.getCurrentUserId()).getDept().getName().equals("检测单位")){
                criteria.setBeizhu8(userService.findById(SecurityUtils.getCurrentUserId()).getNickName());
                return new ResponseEntity<>(testInformationService.queryAll(criteria, pageable), HttpStatus.OK);
            }else if(userService.findById(SecurityUtils.getCurrentUserId()).getDept().getName().equals("监理单位")){
                criteria.setBeizhu9(userService.findById(SecurityUtils.getCurrentUserId()).getNickName());
                return new ResponseEntity<>(testInformationService.queryAll(criteria, pageable), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.OK);
            }


        }
    }

    @PostMapping
    @Log("新增api/test_information")
    @ApiOperation("新增api/test_information")
    @PreAuthorize("@el.check('testInformation:add')")
    public ResponseEntity<Object> createTestInformation(@Validated @RequestBody TestInformation resources) {
        return new ResponseEntity<>(testInformationService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改api/test_information")
    @ApiOperation("修改api/test_information")
    @PreAuthorize("@el.check('testInformation:edit')")
    public ResponseEntity<Object> updateTestInformation(@Validated @RequestBody TestInformation resources) {
        resources.setTestStartingDistance("DK" + resources.getBeizhu16() + "+" + resources.getBeizhu14());
        resources.setTestEndingDistance("DK" + resources.getBeizhu17() + "+" + resources.getBeizhu15());
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        resources.setBeizhu18(format.format(date));
        resources.setStatute("监理单位待审批");
        testInformationService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
    @PutMapping("/jiliupdateTestInformation")
    @Log("修改api/test_information")
    @ApiOperation("修改api/test_information")
    @PreAuthorize("@el.check('testInformation:jiliupdateTestInformation')")
    public ResponseEntity<Object> jiliupdateTestInformation(@Validated @RequestBody TestInformation resources) {
        resources.setTestStartingDistance("DK" + resources.getBeizhu16() + "+" + resources.getBeizhu14());
        resources.setTestEndingDistance("DK" + resources.getBeizhu17() + "+" + resources.getBeizhu15());
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        resources.setBeizhu21(format.format(date));
        resources.setStatute("检测单位待审批");
        testInformationService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
    @PutMapping("/jianceupdateTestInformation")
    @Log("修改api/test_information")
    @ApiOperation("修改api/test_information")
    @PreAuthorize("@el.check('testInformation:jianceupdateTestInformation')")
    public ResponseEntity<Object> jianceupdateTestInformation(@Validated @RequestBody TestInformation resources) {
        resources.setTestStartingDistance("DK" + resources.getBeizhu16() + "+" + resources.getBeizhu14());
        resources.setTestEndingDistance("DK" + resources.getBeizhu17() + "+" + resources.getBeizhu15());
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        resources.setBeizhu23(format.format(date));
        resources.setStatute("报检单已审批");
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

    private final TunnelInformationService tunnelInformationService;
    private final TestInformationMapper testInformationMapper;

    @PostMapping("/createTest")
    @Log("更改状态api/test_information")
    @ApiOperation("更改状态")
    @PreAuthorize("@el.check('testInformation:createTest')")
    public ResponseEntity<Object> createTest(@RequestBody TunnelInformation resources){


        TestInformationQueryCriteria resou = new  TestInformationQueryCriteria();
        resou.setTunnelName(resources.getTunnelName());
        resou.setProjectName(resources.getProjectName());
        resou.setSectionName(resources.getSectionName());
        resou.setWorksiteName(resources.getWorksiteName());
        resou.setBeizhu1(resources.getTunnelId());
        resou.setBeizhu2(resources.getTunnelStartingDistance());
        resou.setBeizhu3(resources.getTunnelEndingDistance());
        resou.setBeizhu4(resources.getTunnelLength());
        List<TestInformationDto> lsDto = testInformationService.queryAll(resou);
        if(lsDto.size()>0){
            resou.setTestId(resou.getBeizhu1()+"-"+(lsDto.size()+1));
        }else{
            resou.setTestId(resou.getBeizhu1()+"-"+(lsDto.size()+1));
        }
        TestInformation res = new  TestInformation();
        res.setTunnelName(resou.getTunnelName());
        res.setProjectName(resou.getProjectName());
        res.setSectionName(resou.getSectionName());
        res.setWorksiteName(resou.getWorksiteName());
        res.setBeizhu1(resou.getBeizhu1());
        res.setBeizhu2(resou.getBeizhu2());
        res.setBeizhu3(resou.getBeizhu3());
        res.setBeizhu4(resou.getBeizhu4());
        res.setTestId(resou.getTestId());
        testInformationService.create(res);
        List<TestInformationDto> lsDto1 = testInformationService.queryAll(resou);

        return new ResponseEntity<>(lsDto1.get(0),HttpStatus.CREATED);

    }
    @PostMapping("/delForm")
    @Log("删除api/test_information")
    @ApiOperation("删除api/test_information")
    @PreAuthorize("@el.check('testInformation:delForm')")
    public ResponseEntity<Object> delForm(@RequestBody Long ids) {
        Long[] id = new Long[1];
        id[0]=ids;
        testInformationService.deleteAll(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private final UserInfoService userInfoService;

    @PostMapping("/listDishesInfo")
    @Log("查询检测单位api/test_information")
    @ApiOperation("查询检测单位api/test_information")
    @PreAuthorize("@el.check('testInformation:listDishesInfo')")
    public ResponseEntity<Object> listDishesInfo() throws Exception {


        UserInfoQueryCriteria  criteria = new UserInfoQueryCriteria();
        criteria.setDeptName("检测单位");
        userInfoService.queryAll(criteria);

        return new ResponseEntity<>(userInfoService.queryAll(criteria),HttpStatus.OK);
    }
    @PostMapping("/listDishesInfo1")
    @Log("查询监理单位api/test_information")
    @ApiOperation("查询监理单位api/test_information")
    @PreAuthorize("@el.check('testInformation:listDishesInfo1')")
    public ResponseEntity<Object> listDishesInfo1() throws Exception {
        /**
        UserQueryCriteria userQueryCriteria = new UserQueryCriteria();
        DeptQueryCriteria criteria = new  DeptQueryCriteria();
        criteria.setName("监理单位");
        List<DeptDto> lis = deptService.queryAllUser(criteria, true);
        Set<Long> deptIds = new HashSet<>();
        deptIds.add(lis.get(0).getId());
        userQueryCriteria.setDeptIds(deptIds);
        List<UserDto> userli = userService.queryAll(userQueryCriteria);
         return new ResponseEntity<>(userli,HttpStatus.OK);
         **/
        UserInfoQueryCriteria  criteria = new UserInfoQueryCriteria();
        criteria.setDeptName("监理单位");
        return new ResponseEntity<>(userInfoService.queryAll(criteria),HttpStatus.OK);

    }
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/createErweima")
    @Log("二维码api/test_information")
    @ApiOperation("二维码api/test_information")
    @PreAuthorize("@el.check('testInformation:createErweima')")
    public ResponseEntity<Object> createErweima(@Validated @RequestBody TestInformation resources) throws JsonProcessingException {
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        //  System.out.println(dateFormat.format(date));
        String erweimaPath = "C:/erweima/二维码.jpg";

        File generate = QrCodeUtil.generate(objectMapper.writeValueAsString(resources),600,600,new File(erweimaPath)); //QrCodeUtil.generate(objectMapper.writeValueAsString(resources), 600, 600, new File(erweimaPath));
        String encode = Base64.encode(generate);

        return new ResponseEntity<>("data:image/png;base64,"+encode,HttpStatus.CREATED);
    }

    private final DeviceInformationService deviceInformationService;

    @PostMapping("/listDishesInfoTianxian")
    @Log("查询api/test_information")
    @ApiOperation("查询api/test_information")
    @PreAuthorize("@el.check('testInformation:listDishesInfoTianxian')")
    public ResponseEntity<Object> listDishesInfoTianxian() throws Exception {

        DeviceInformationQueryCriteria criteria = new  DeviceInformationQueryCriteria();
        criteria.setDeviceType("天线");
        return new ResponseEntity<>(deviceInformationService.queryAll(criteria),HttpStatus.OK);
    }
    @PostMapping("/listDishesInfoZhuji")
    @Log("查询api/test_information")
    @ApiOperation("查询api/test_information")
    @PreAuthorize("@el.check('testInformation:listDishesInfoZhuji')")
    public ResponseEntity<Object> listDishesInfoZhuji() throws Exception {

        DeviceInformationQueryCriteria criteria = new  DeviceInformationQueryCriteria();
        criteria.setDeviceType("主机");
        return new ResponseEntity<>(deviceInformationService.queryAll(criteria),HttpStatus.OK);
    }

    @ApiOperation("更新BeiZhu26即检测报告")
    @PostMapping(value = "/updateBeiZhu26")
    public ResponseEntity<Object> updateBeiZhu26(@RequestParam("file") MultipartFile multipartFile, @RequestParam String data) {
//        System.out.println(data);
        Map<String, String> uploadfile = uploadService.uploadfile(multipartFile);
        String avatar = uploadfile.get("avatar");
        testInformationService.updateDetectionSummary(Long.valueOf(data), avatar);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation("下载文件")
    @PostMapping(value = "/{type}")
    public ResponseEntity<Object> generator(@Validated @RequestBody TestInformation resources, @PathVariable Integer type, HttpServletRequest request, HttpServletResponse response) {
        if (type == -2) {
            try {
                testInformationService.downloadFile(resources.getBeizhu26(), request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            throw new BadRequestException("没有这个选项!");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}