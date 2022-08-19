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
import me.zhengjie.domain.LocalStorage;
import me.zhengjie.modules.system.domain.ParamTunnel;
import me.zhengjie.modules.system.domain.TunnelInformation;
import me.zhengjie.modules.system.service.DefectInformationService;
import me.zhengjie.modules.system.service.TestInformationService;
import me.zhengjie.modules.system.service.TunnelInformationService;
import me.zhengjie.modules.system.service.UserService;
import me.zhengjie.modules.system.service.dto.*;
import me.zhengjie.service.LocalStorageService;
import me.zhengjie.service.dto.LocalStorageDto;
import me.zhengjie.service.dto.LocalStorageQueryCriteria;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.utils.SecurityUtils;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author LJL
* @date 2022-04-19
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "api/tunnel_information管理")
@RequestMapping("/api/tunnelInformation")
public class TunnelInformationController {

    private final TunnelInformationService tunnelInformationService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('tunnelInformation:list')")
    public void exportTunnelInformation(HttpServletResponse response, TunnelInformationQueryCriteria criteria) throws IOException {
        tunnelInformationService.download(tunnelInformationService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询api/tunnel_information")
    @ApiOperation("查询api/tunnel_information")
    @PreAuthorize("@el.check('tunnelInformation:list')")
    public ResponseEntity<Object> queryTunnelInformation(TunnelInformationQueryCriteria criteria, Pageable pageable){
        if(SecurityUtils.getCurrentUsername().equals("admin")){
            return new ResponseEntity<>(tunnelInformationService.queryAll(criteria,pageable),HttpStatus.OK);
        }else{
            criteria.setUserId(SecurityUtils.getCurrentUserId());
            criteria.setUserName(SecurityUtils.getCurrentUsername());
            return new ResponseEntity<>(tunnelInformationService.queryAll(criteria,pageable),HttpStatus.OK);
        }
    }

    @PostMapping
    @Log("新增api/tunnel_information")
    @ApiOperation("新增api/tunnel_information")
    @PreAuthorize("@el.check('tunnelInformation:add')")
    public ResponseEntity<Object> createTunnelInformation(@Validated @RequestBody TunnelInformation resources){
        resources.setBeizhu4("未发布");
        resources.setUserId(SecurityUtils.getCurrentUserId());
        resources.setUserName(SecurityUtils.getCurrentUsername());
        resources.setTunnelStartingDistance("DK"+resources.getBeizhu1()+"+"+resources.getBeizhu5());
        resources.setTunnelEndingDistance("DK"+resources.getBeizhu2()+"+"+resources.getBeizhu6());
        return new ResponseEntity<>(tunnelInformationService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改api/tunnel_information")
    @ApiOperation("修改api/tunnel_information")
    @PreAuthorize("@el.check('tunnelInformation:edit')")
    public ResponseEntity<Object> updateTunnelInformation(@Validated @RequestBody TunnelInformation resources){
        resources.setTunnelStartingDistance("DK"+resources.getBeizhu1()+"+"+resources.getBeizhu5());
        resources.setTunnelEndingDistance("DK"+resources.getBeizhu2()+"+"+resources.getBeizhu6());
        tunnelInformationService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除api/tunnel_information")
    @ApiOperation("删除api/tunnel_information")
    @PreAuthorize("@el.check('tunnelInformation:del')")
    public ResponseEntity<Object> deleteTunnelInformation(@RequestBody Long[] ids) {
        tunnelInformationService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private final LocalStorageService localStorageService;
    private final UserService userService;

    @PostMapping("/select")
    @Log("查询文件api/select")
    @ApiOperation("查询文件")
    public ResponseEntity<Object> select(@Validated @RequestBody TunnelInformation resources){
        LocalStorageQueryCriteria LScriteria = new LocalStorageQueryCriteria();
        if( resources.getBeizhu3()==null){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            LScriteria.setId(Long.valueOf(resources.getBeizhu3()));
            return new ResponseEntity<>(localStorageService.queryAll(LScriteria),HttpStatus.OK);
        }

    }
    @PostMapping("/downloadFile")
    @Log("下载文件api/downloadFile")
    @ApiOperation("下载文件")
    public ResponseEntity<Object> downloadFile(@Validated @RequestBody LocalStorage resources, HttpServletResponse response, HttpServletRequest request) throws IOException {

        tunnelInformationService.downloadFile(resources, response,request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/changeStatue")
    @Log("更改状态api/changeStatue")
    @ApiOperation("更改状态")
    @PreAuthorize("@el.check('tunnelInformation:updateStatus')")
    public ResponseEntity<Object> changeStatue(@Validated @RequestBody TunnelInformation resources){
        if(resources.getBeizhu4().equals("未发布")){
            resources.setBeizhu4("已发布");
            tunnelInformationService.update(resources);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            resources.setBeizhu4("未发布");
            tunnelInformationService.update(resources);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }
    @GetMapping("/selectByStatue")
    @Log("状态条件查询api/selectByStatue")
    @ApiOperation("状态条件查询")
    public ResponseEntity<Object> selectByStatue(TunnelInformationQueryCriteria criteria, Pageable pageable){
        criteria.setBeizhu4("已发布");
        if(SecurityUtils.getCurrentUsername().equals("admin")){
            return new ResponseEntity<>(tunnelInformationService.queryAll(criteria),HttpStatus.OK);
        }else{
            criteria.setUserId(SecurityUtils.getCurrentUserId());
            criteria.setUserName(SecurityUtils.getCurrentUsername());
            return new ResponseEntity<>(tunnelInformationService.queryAll(criteria),HttpStatus.OK);
        }

    }
    @GetMapping("/selectByStatueFenye")
    @Log("状态条件查询（分页）api/selectByStatueFenye")
    @ApiOperation("状态条件查询（分页）api/selectByStatueFenye")
    @PreAuthorize("@el.check('tunnelInformation:list')")
    public ResponseEntity<Object> selectByStatueFenye(TunnelInformationQueryCriteria criteria, Pageable pageable){
        criteria.setBeizhu4("已发布");
        if(SecurityUtils.getCurrentUsername().equals("admin")){
            return new ResponseEntity<>(tunnelInformationService.queryAll(criteria,pageable),HttpStatus.OK);
        }else{
            criteria.setUserId(SecurityUtils.getCurrentUserId());
            criteria.setUserName(SecurityUtils.getCurrentUsername());
            return new ResponseEntity<>(tunnelInformationService.queryAll(criteria,pageable),HttpStatus.OK);
        }

    }
    @PostMapping("/selectByTunnelName")
    @Log("隧道名称条件查询（分页）api/selectByTunnelName")
    @ApiOperation("隧道名称条件查询（分页）api/selectByTunnelName")
    @PreAuthorize("@el.check('tunnelInformation:list')")
    public ResponseEntity<Object>  selectByTunnelName(@RequestBody String tunnelName) {
        TunnelInformationQueryCriteria criteria = new TunnelInformationQueryCriteria();
        criteria.setBeizhu4("已发布");
        criteria.setTunnelName(tunnelName);
        if(SecurityUtils.getCurrentUsername().equals("admin")){
            return new ResponseEntity<>(tunnelInformationService.queryAll(criteria),HttpStatus.OK);
        }else{
            criteria.setUserId(SecurityUtils.getCurrentUserId());
            criteria.setUserName(SecurityUtils.getCurrentUsername());
            return new ResponseEntity<>(tunnelInformationService.queryAll(criteria),HttpStatus.OK);
        }

    }

    private final TestInformationService testInformationService;
    private final DefectInformationService defectInformationService;

    //@PreAuthorize("@el.check('tunnelInformation:list')")
    @PostMapping("/selectParam")
    @Log("首页参数 api/selectParam")
    @ApiOperation("首页参数 api/selectParam")
    public ResponseEntity<Object> selectParam() {
        TunnelInformationQueryCriteria criteria = new TunnelInformationQueryCriteria();
        List<TunnelInformationDto> LTDto = tunnelInformationService.queryAll(criteria);
        int tn = LTDto.size();
        int tl=0;
        for(int i=0;i<tn;i++){
            String tunnelLength = LTDto.get(i).getTunnelLength();
            if (tunnelLength.contains(".")){
                tunnelLength = tunnelLength.split("\\.")[0];
            }
            tl=tl+Integer.parseInt(tunnelLength);
        }
        criteria.setBeizhu4("已发布");
        List<TunnelInformationDto> LTDto1 = tunnelInformationService.queryAll(criteria);
        int tn1 = LTDto1.size();
        int fl=0;
        for(int i=0;i<tn1;i++){
            String tunnelLength = LTDto1.get(i).getTunnelLength();
            if (tunnelLength.contains(".")){
                tunnelLength = tunnelLength.split("\\.")[0];
            }
            fl=fl+Integer.parseInt(tunnelLength);
        }
        TestInformationQueryCriteria resou = new  TestInformationQueryCriteria();
        List<TestInformationDto> TDto =testInformationService.queryAll(resou);
        int bl=1;
        for(int i=0;i<TDto.size();i++){
            String testLength = TDto.get(i).getTestLength();
            if (testLength.contains(".")){
                testLength = testLength.split("\\.")[0];
            }
            bl=bl+Integer.parseInt(testLength);
        }
        resou.setStatute("报检单已审批");
        List<TestInformationDto> TDto1 =testInformationService.queryAll(resou);
        int yl=0;
        for(int i=0;i<TDto1.size();i++){
            String testLength = TDto1.get(i).getTestLength();
            if (testLength.contains(".")){
                testLength = testLength.split("\\.")[0];
            }
            yl=yl+Integer.parseInt(testLength);
        }
        DefectInformationQueryCriteria DefectInformationQueryCriteria = new DefectInformationQueryCriteria();
        DefectInformationQueryCriteria.setBeizhu1("已处理");
        int yq = defectInformationService.queryAll(DefectInformationQueryCriteria).size();
        DefectInformationQueryCriteria.setBeizhu1("未处理");
        int wq = defectInformationService.queryAll(DefectInformationQueryCriteria).size();
        double jl = ((double)yl/(double)bl)*100;
        ParamTunnel paramTunnel = new ParamTunnel(tn,tl,fl,bl,yl,yq,wq,jl);
        return new ResponseEntity<>(paramTunnel,HttpStatus.OK);


    }

}