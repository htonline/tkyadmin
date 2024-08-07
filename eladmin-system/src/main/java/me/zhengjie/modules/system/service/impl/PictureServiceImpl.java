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
package me.zhengjie.modules.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.system.domain.Picture;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.PictureRepository;
import me.zhengjie.modules.system.service.PictureService;
import me.zhengjie.modules.system.service.dto.PictureDto;
import me.zhengjie.modules.system.service.dto.PictureQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.PictureMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author Zuohaitao
* @date 2023-11-01
**/
@Service
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;
    private final PictureMapper pictureMapper;

    @Override
    public Map<String,Object> queryAll(PictureQueryCriteria criteria, Pageable pageable){
        Page<Picture> page = pictureRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(pictureMapper::toDto));
    }

    @Override
    public List<PictureDto> queryAll(PictureQueryCriteria criteria){
        return pictureMapper.toDto(pictureRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public PictureDto findById(Integer pictureId) {
        Picture picture = pictureRepository.findById(pictureId).orElseGet(Picture::new);
        ValidationUtil.isNull(picture.getPictureId(),"Picture","pictureId",pictureId);
        return pictureMapper.toDto(picture);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PictureDto create(Picture resources) {
//        return pictureMapper.toDto(pictureRepository.save(resources));
        /**
         * 点击上传图片中的上传文件按钮之后，图片就已经上传完成了；
         * 再点击新增的确定按钮，会往数据库中添加一条没有图片名的数据，这条没有文件名的数据，会让大屏展示那里-图片展示失效
         * 所以这里改成: 只要点击新增按钮，都会显示新增成功，但并不会添加没有图片名的数据；
         * 添加只会在方法uploadScenePicture()中实现
         */
        return pictureMapper.toDto(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Picture resources) {
        Picture picture = pictureRepository.findById(resources.getPictureId()).orElseGet(Picture::new);
        ValidationUtil.isNull( picture.getPictureId(),"Picture","id",resources.getPictureId());
        picture.copy(resources);
        pictureRepository.save(picture);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer pictureId : ids) {
            pictureRepository.deleteById(pictureId);
        }
    }

    @Override
    public void download(List<PictureDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (PictureDto picture : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("文件路径", picture.getUrl());
            map.put("文件对应的隧道id", picture.getDisNumber());
            map.put("备注", picture.getRemark());
            map.put("备注", picture.getRemark1());
            map.put("备注", picture.getRemark2());
            map.put("备注", picture.getRemark3());
            map.put("备注", picture.getRemark4());
            map.put("备注", picture.getRemark5());
            map.put("备注", picture.getRemark6());
            map.put("备注", picture.getRemark7());
            map.put("备注", picture.getRemark8());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public void uploadScenePicture(Picture picture, MultipartFile multipartFile) throws IOException {

//        文件上传一份之后会清空，所以在这复制一份，上传两个地方
        MultipartFile multipartFile_1 = createCopyMultipartFile(multipartFile);

//        上传到这个位置，是因为大屏展示的前端 访问的就是这个位置
        String filePath = "D:\\WorkSpace\\JavaProject\\tky\\IofTV-Screen-web\\src\\assets\\img\\pictures\\scene"+ File.separator + picture.getUrl() + File.separator;
        File file = FileUtil.upload(multipartFile, filePath);
        //        5.如果上传文件失败（file为空），则抛出一个BadRequestException异常，提示上传失败。
        if (ObjectUtil.isNull(file)) {
            throw new BadRequestException("上传失败");
        }

 //        上传到这个位置，是因为卡片导出中的雷达图谱, 需要一个可以访问url地址(但为什么只有file文件里面的才可以访问呢？)
        String filePath_1 = "D:\\eladmin\\file\\pictures\\scene"+ File.separator + picture.getUrl() + File.separator;
        File file_1 = FileUtil.upload(multipartFile_1, filePath_1);
        //        5.如果上传文件失败（file为空），则抛出一个BadRequestException异常，提示上传失败。
        if (ObjectUtil.isNull(file_1)) {
            throw new BadRequestException("上传失败");
        }

        //        准备存入数据库
        picture.setRemark(multipartFile.getOriginalFilename()); // 设置文件名称
        picture.setUrl(picture.getUrl()+"/"+multipartFile.getOriginalFilename());   // 设置文件存储路径
        pictureRepository.save(picture);
    }

    public MultipartFile createCopyMultipartFile(MultipartFile sourceMultipartFile) throws IOException {
        // 获取原始文件名
        String originalFilename = sourceMultipartFile.getOriginalFilename();

        // 获取文件内容类型
        String contentType = sourceMultipartFile.getContentType();

        // 获取文件字节数组
        byte[] bytes = sourceMultipartFile.getBytes();

        // 创建一个新的MultipartFile对象
        MultipartFile newMultipartFile = new MultipartFile() {
            @Override
            public String getName() {
                return sourceMultipartFile.getName();
            }

            @Override
            public String getOriginalFilename() {
                return originalFilename;
            }

            @Override
            public String getContentType() {
                return contentType;
            }

            @Override
            public boolean isEmpty() {
                return bytes.length == 0;
            }

            @Override
            public long getSize() {
                return bytes.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return bytes;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(bytes);
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                try (OutputStream outputStream = new FileOutputStream(dest)) {
                    outputStream.write(bytes);
                }
            }
        };

        return newMultipartFile;
    }
}
