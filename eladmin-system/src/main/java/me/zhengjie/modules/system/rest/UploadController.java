package me.zhengjie.modules.system.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.domain.FileMD5;
import me.zhengjie.modules.system.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Api(tags = "api/upload管理")
@RequestMapping("/api/upload")
public class UploadController {

    @Autowired
    private final UploadService uploadService;

    @ApiOperation("验证文件")
    @PostMapping(value = "/verifyFile")
    public ResponseEntity<Object> verifyFile(@Validated @RequestBody FileMD5 fileMD5,HttpServletRequest request){
        return new ResponseEntity<>(uploadService.verifyFile(fileMD5), HttpStatus.OK);
    }

    @ApiOperation("上传文件")
    @PostMapping(value = "/updateFile")
    public ResponseEntity<Object> uploadfile(@RequestParam("file") MultipartFile multipartFile){
        return new ResponseEntity<>(uploadService.uploadfile(multipartFile), HttpStatus.OK);
    }

    @ApiOperation("上传文件by分片")
    @PostMapping(value = "/updateFileBySplit")
    public ResponseEntity<Object> uploadFileBySplit(@RequestParam("file") MultipartFile multipartFile){
        return new ResponseEntity<>(uploadService.uploadFileBySplit(multipartFile), HttpStatus.OK);
    }

    @ApiOperation("上传图片")
    @PostMapping(value = "/updatePhoto")
    public ResponseEntity<Object> uploadPhoto(@RequestParam MultipartFile multipartFile){
        return new ResponseEntity<>(uploadService.uploadPhoto(multipartFile), HttpStatus.OK);
    }
}
