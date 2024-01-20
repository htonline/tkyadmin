package me.zhengjie.modules.system.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Zuohaitao
 * @date 2023-12-19 20:59
 * @describe
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "api/upload管理")
@RequestMapping("/realTimeUpload")
public class RealTimeController {
    @Autowired
    private final UploadService uploadService;

    @ApiOperation("上传文件")
    @PostMapping(value = "/updateFile")
    public ResponseEntity<Object> uploadfile(@RequestParam("file") MultipartFile multipartFile){
        return new ResponseEntity<>(uploadService.uploadfile(multipartFile), HttpStatus.OK);
    }

    @ApiOperation("上传图片")
    @PostMapping(value = "/updatePhoto")
    public ResponseEntity<Object> uploadPhoto(@RequestParam MultipartFile multipartFile){
        return new ResponseEntity<>(uploadService.uploadPhoto(multipartFile), HttpStatus.OK);
    }

}
