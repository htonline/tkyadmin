package me.zhengjie.modules.system.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface UploadService {

    Map<String, String> uploadPhoto(MultipartFile multipartFile);

    Map<String, String> uploadfile(MultipartFile multipartFile);

    Map<String, String> uploadFileBySplit(MultipartFile multipartFile);
}
