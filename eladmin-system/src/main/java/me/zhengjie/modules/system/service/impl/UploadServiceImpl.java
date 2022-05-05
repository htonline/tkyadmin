package me.zhengjie.modules.system.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.config.FileProperties;
import me.zhengjie.modules.security.service.UserCacheClean;
import me.zhengjie.modules.system.domain.FileMD5;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.repository.UserRepository;
import me.zhengjie.modules.system.service.UploadService;
import me.zhengjie.utils.*;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.File;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {
    private final UserRepository userRepository;
    private final FileProperties properties;
    private final UserCacheClean userCacheClean;
    private FileUtils utils = new FileUtils();

    @Override
    public Map<String, String> uploadPhoto(MultipartFile multipartFile) {
        User user = userRepository.findByUsername(SecurityUtils.getCurrentUsername());
        String oldPath = user.getAvatarPath();
        File file = FileUtil.upload(multipartFile, properties.getPath().getAvatar());
        user.setAvatarPath(Objects.requireNonNull(file).getPath());
        user.setAvatarName(file.getName());
        userRepository.save(user);
        if (StringUtils.isNotBlank(oldPath)) {
            // FileUtil.del(oldPath);
        }
        @NotBlank String username = user.getUsername();
        flushCache(username);
        return new HashMap<String, String>(1) {{
            put("avatar", file.getName());
        }};
    }

    @Override
    public Map<String, String> uploadfile(MultipartFile multipartFile) {
        User user = userRepository.findByUsername(SecurityUtils.getCurrentUsername());
        String oldPath = user.getAvatarPath();
        File file = FileUtil.upload(multipartFile, properties.getPath().getPath());
        user.setAvatarName(Objects.requireNonNull(file).getPath());
        user.setAvatarName(file.getName());
        userRepository.save(user);
        if (StringUtils.isNotBlank(oldPath)) {
            //FileUtil.del(oldPath);
        }
        @NotBlank String username = user.getUsername();
        flushCache(username);
        return new HashMap<String, String>(1) {{
            put("avatar", file.getName());
        }};
    }

    @Override
    public Map<String, String> uploadFileBySplit(MultipartFile multipartFile) {
        String uploaddir = properties.getPath().getAvatar();
        User user = userRepository.findByUsername(SecurityUtils.getCurrentUsername());
        String oldPath = user.getAvatarPath();
        String[] sts = multipartFile.getOriginalFilename().split("\\.");
        String fileName = sts[0] + "." + sts[1];
        File file = FileUtil.uploadRaw(multipartFile, properties.getPath().getPath() + fileName + "_");

        user.setAvatarName(Objects.requireNonNull(file).getPath());
        user.setAvatarName(file.getName());
        String filename = file.getName();

        userRepository.save(user);
        if (StringUtils.isNotBlank(oldPath)) {
            //FileUtil.del(oldPath);
        }
        @NotBlank String username = user.getUsername();
        flushCache(username);
        boolean down = utils.doFile(file);
        return new HashMap<String, String>(1) {{
            put("avatar", file.getName() + "." + down);
        }};
    }

    @Override
    public Map<String, String> verifyFile(FileMD5 fileMD5) {
        String filename = fileMD5.getFilename();
        String filemd5 = fileMD5.getMd5();
        long length = fileMD5.getLen();
        String path = properties.getPath().getPath();
        String exits = null;
        File file = new File(path, filename);
        int count = 0;
        if (file.exists()) {
//            String md5 = FileUtil.getMd5(file);
//            if (filemd5.equals(md5)) {
                exits = "file";
//            } else {
//                exits = "nomd5";
//            }
        } else if (length <= 70 * 1024 * 1024) {
            exits = "nofile";
        } else {
            File fileDi = new File(path, filename + "_");
            if (fileDi.exists() && fileDi.isDirectory()) {
                exits = "directory";
                count = FileUtils.countFiles(fileDi);
            } else {
                if (!fileDi.exists()) {
                    fileDi.mkdir();
                    exits = "directory";
                }
            }
        }
        String finalExits = exits;
        int finalCount = count;
        return new HashMap<String, String>(3) {{
            put("avatar", file.getName());
            put("exists", finalExits);
            put("info", "" + finalCount);
        }};
    }

    private void flushCache(String username) {
        userCacheClean.cleanUserCache(username);
    }
}
