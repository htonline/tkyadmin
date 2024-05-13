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
import me.zhengjie.modules.system.domain.PictureRadarSpectrum;
import me.zhengjie.utils.*;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.PictureRadarSpectrumRepository;
import me.zhengjie.modules.system.service.PictureRadarSpectrumService;
import me.zhengjie.modules.system.service.dto.PictureRadarSpectrumDto;
import me.zhengjie.modules.system.service.dto.PictureRadarSpectrumQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.PictureRadarSpectrumMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

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
public class PictureRadarSpectrumServiceImpl implements PictureRadarSpectrumService {

    private final PictureRadarSpectrumRepository pictureRadarSpectrumRepository;
    private final PictureRadarSpectrumMapper pictureRadarSpectrumMapper;

    @Override
    public Map<String,Object> queryAll(PictureRadarSpectrumQueryCriteria criteria, Pageable pageable){
        Page<PictureRadarSpectrum> page = pictureRadarSpectrumRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(pictureRadarSpectrumMapper::toDto));
    }

    @Override
    public List<PictureRadarSpectrumDto> queryAll(PictureRadarSpectrumQueryCriteria criteria){
        return pictureRadarSpectrumMapper.toDto(pictureRadarSpectrumRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public PictureRadarSpectrumDto findById(Integer spectrumId) {
        PictureRadarSpectrum pictureRadarSpectrum = pictureRadarSpectrumRepository.findById(spectrumId).orElseGet(PictureRadarSpectrum::new);
        ValidationUtil.isNull(pictureRadarSpectrum.getSpectrumId(),"PictureRadarSpectrum","spectrumId",spectrumId);
        return pictureRadarSpectrumMapper.toDto(pictureRadarSpectrum);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PictureRadarSpectrumDto create(PictureRadarSpectrum resources) {
//        return pictureRadarSpectrumMapper.toDto(pictureRadarSpectrumRepository.save(resources));
            return pictureRadarSpectrumMapper.toDto(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PictureRadarSpectrum resources) {
        PictureRadarSpectrum pictureRadarSpectrum = pictureRadarSpectrumRepository.findById(resources.getSpectrumId()).orElseGet(PictureRadarSpectrum::new);
        ValidationUtil.isNull( pictureRadarSpectrum.getSpectrumId(),"PictureRadarSpectrum","id",resources.getSpectrumId());
        pictureRadarSpectrum.copy(resources);
        pictureRadarSpectrumRepository.save(pictureRadarSpectrum);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer spectrumId : ids) {
            pictureRadarSpectrumRepository.deleteById(spectrumId);
        }
    }

    @Override
    public void download(List<PictureRadarSpectrumDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (PictureRadarSpectrumDto pictureRadarSpectrum : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("文件存储路径", pictureRadarSpectrum.getFileUrl());
            map.put("外键，关联tunel表", pictureRadarSpectrum.getTunnelId());
            map.put("备注", pictureRadarSpectrum.getRemark1());
            map.put("备注", pictureRadarSpectrum.getRemark2());
            map.put("备注", pictureRadarSpectrum.getRemark3());
            map.put("备注", pictureRadarSpectrum.getRemark4());
            map.put("备注", pictureRadarSpectrum.getRemark5());
            map.put("备注", pictureRadarSpectrum.getRemark6());
            map.put("备注", pictureRadarSpectrum.getRemark7());
            map.put("备注", pictureRadarSpectrum.getRemark8());
            map.put("备注", pictureRadarSpectrum.getRemark());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public void uploadSpectrumPicture(PictureRadarSpectrum pictureRadarSpectrum, MultipartFile multipartFile) throws IOException {

        /**
         *  在Spring框架中，MultipartFile 对象通常在文件上传完成后会被清空。
         *  因此，在上传文件到第一个目录之后,无法再次使用相同的 MultipartFile 对象上传到第二个目录。
         *  为了解决这个问题，您可以在第一个上传后创建一个新的 MultipartFile 对象，然后使用它上传到第二个目录。
         */
        // 创建新的 MultipartFile 对象
        MultipartFile multipartFile_1 = createCopyMultipartFile(multipartFile);


        // 上传到这个位置，是因为大屏展示的前端只能访问这个
        String filePath = "D:\\WorkSpace\\JavaProject\\tky\\IofTV-Screen-web\\src\\assets\\img\\pictures\\radarSpectrum" + File.separator + pictureRadarSpectrum.getFileUrl() + File.separator;
        File file = uploadFile(multipartFile, filePath);

        // 上传到这个位置，是因为卡片导出中的雷达图谱, 需要一个可以访问url地址(但为什么只有file文件里面的才可以访问呢？)
        String filePath_1 = "D:\\eladmin\\file\\pictures\\radarSpectrum" + File.separator + pictureRadarSpectrum.getFileUrl() + File.separator;
        File file_1 = uploadFile(multipartFile_1, filePath_1);


//        5.如果上传文件失败（file为空），则抛出一个BadRequestException异常，提示上传失败。
        if (ObjectUtil.isNull(file) || ObjectUtil.isNull(file_1)) {
            throw new BadRequestException("上传失败");
        }

        pictureRadarSpectrum.setRemark(multipartFile.getOriginalFilename()); // 设置文件名称
        pictureRadarSpectrum.setFileUrl(pictureRadarSpectrum.getFileUrl()+"/"+multipartFile.getOriginalFilename());   // 设置文件存储路径
        pictureRadarSpectrumRepository.save(pictureRadarSpectrum);
    }

    private File uploadFile(MultipartFile multipartFile, String filePath) {
        return FileUtil.upload(multipartFile, filePath);
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
