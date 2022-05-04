package me.zhengjie.modules.system.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.config.FileProperties;
import me.zhengjie.modules.security.service.UserCacheClean;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.repository.UserRepository;
import me.zhengjie.modules.system.service.UploadService;
import me.zhengjie.utils.*;
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
        User user = userRepository.findByUsername(SecurityUtils.getCurrentUsername());
        String oldPath = user.getAvatarPath();
        File file = FileUtil.upload(multipartFile, properties.getPath().getPath());
        user.setAvatarName(Objects.requireNonNull(file).getPath());
        user.setAvatarName(file.getName());
        String filename = file.getName();

        userRepository.save(user);
        if (StringUtils.isNotBlank(oldPath)) {
            //FileUtil.del(oldPath);
        }
        @NotBlank String username = user.getUsername();
        flushCache(username);
        String [] strs = filename.split("\\.");
        if (strs[strs.length -1].equals("part")){
            ArrayList<File> partFiles = FileUtils.getDirFilesForPrefix("C:\\eladmin\\file",
                    strs[0]+"."+strs[1]+".");
            if (partFiles.size() == Integer.valueOf(strs[strs.length -2])){
                Collections.sort(partFiles, new FileUtils.FileComparator());
                FileUtils utils = new FileUtils();
                try {
                    utils.mergePartFiles("C:\\eladmin\\file",
                            strs[0]+"."+strs[1]+".",
                            partFiles.get(0).length(),
                            "C:\\eladmin\\file\\"+strs[0]+"."+strs[1]
                    );
                    return new HashMap<String, String>(1) {{
                        put("avatar", strs[0]+"."+strs[1]);
                    }};
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return new HashMap<String, String>(1) {{
            put("avatar", file.getName());
        }};
    }
    private void flushCache(String username) {
        userCacheClean.cleanUserCache(username);
    }
}
