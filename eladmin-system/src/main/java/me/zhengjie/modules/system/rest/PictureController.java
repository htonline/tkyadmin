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
import me.zhengjie.modules.system.domain.Picture;
import me.zhengjie.modules.system.domain.RadarDiseasetypePictures;
import me.zhengjie.modules.system.domain.RadarPicture;
import me.zhengjie.modules.system.service.PictureService;
import me.zhengjie.modules.system.service.dto.PictureQueryCriteria;
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
* @date 2023-11-01 现场图片上传的Controller
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "api/picture管理")
@RequestMapping("/api/picture")
public class PictureController {

    private final PictureService pictureService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('picture:list')")
    public void exportPicture(HttpServletResponse response, PictureQueryCriteria criteria) throws IOException {
        pictureService.download(pictureService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询api/picture")
    @ApiOperation("查询api/picture")
    @PreAuthorize("@el.check('picture:list')")
    public ResponseEntity<Object> queryPicture(PictureQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(pictureService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增api/picture")
    @ApiOperation("新增api/picture")
    @PreAuthorize("@el.check('picture:add')")
    public ResponseEntity<Object> createPicture(@Validated @RequestBody Picture resources){
        return new ResponseEntity<>(pictureService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改api/picture")
    @ApiOperation("修改api/picture")
    @PreAuthorize("@el.check('picture:edit')")
    public ResponseEntity<Object> updatePicture(@Validated @RequestBody Picture resources){
        pictureService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除api/picture")
    @ApiOperation("删除api/picture")
    @PreAuthorize("@el.check('picture:del')")
    public ResponseEntity<Object> deletePicture(@RequestBody Integer[] ids) {
        pictureService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    上传现场图片，将图片与病害数据关联
    @ApiOperation("上传图片")
    @PostMapping(value = "/uploadScenePicture")
    public ResponseEntity<Object> uploadPicture(@RequestParam String url,
                                                @RequestParam String tunnelId, @RequestParam String remark,
                                                @RequestParam String remark1, @RequestParam String remark2,
                                                @RequestParam String remark3, @RequestParam String remark4,
                                                @RequestParam String remark5, @RequestParam String remark6,
                                                @RequestParam String remark7, @RequestParam String remark8,
                                                @RequestParam("file") MultipartFile file){
        Picture picture = new Picture();
        picture.setUrl(url);
        picture.setTunnelId(Integer.parseInt(tunnelId));
        picture.setRemark(remark);
        picture.setRemark1(remark1);
        picture.setRemark2(remark2);
        picture.setRemark3(remark3);
        picture.setRemark4(remark4);
        picture.setRemark5(remark5);
        picture.setRemark6(remark6);
        picture.setRemark7(remark7);
        picture.setRemark8(remark8);

        try {
            pictureService.uploadScenePicture(picture, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
