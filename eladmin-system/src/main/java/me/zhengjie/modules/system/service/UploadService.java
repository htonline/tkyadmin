package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.FileMD5;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface UploadService {

    Map<String, String> uploadPhoto(MultipartFile multipartFile);

    Map<String, String> uploadfile(MultipartFile multipartFile);

    Map<String, String> uploadFileBySplit(MultipartFile multipartFile);

    Map<String, String> verifyFile(FileMD5 fileMD5);

}
