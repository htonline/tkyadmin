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
import me.zhengjie.config.FileProperties;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.system.domain.RadarAcquisitionUpload;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.RadarAcquisitionUploadRepository;
import me.zhengjie.modules.system.service.RadarAcquisitionUploadService;
import me.zhengjie.modules.system.service.dto.RadarAcquisitionUploadDto;
import me.zhengjie.modules.system.service.dto.RadarAcquisitionUploadQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.RadarAcquisitionUploadMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author Zuohaitao
* @date 2023-09-11
**/
@Service
@RequiredArgsConstructor
public class RadarAcquisitionUploadServiceImpl implements RadarAcquisitionUploadService {

    private final RadarAcquisitionUploadRepository radarAcquisitionUploadRepository;
    private final RadarAcquisitionUploadMapper radarAcquisitionUploadMapper;
    private final FileProperties properties;

    @Override
    public Map<String,Object> queryAll(RadarAcquisitionUploadQueryCriteria criteria, Pageable pageable){
        Page<RadarAcquisitionUpload> page = radarAcquisitionUploadRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(radarAcquisitionUploadMapper::toDto));
    }

    @Override
    public List<RadarAcquisitionUploadDto> queryAll(RadarAcquisitionUploadQueryCriteria criteria){
        return radarAcquisitionUploadMapper.toDto(radarAcquisitionUploadRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public RadarAcquisitionUploadDto findById(Integer id) {
        RadarAcquisitionUpload radarAcquisitionUpload = radarAcquisitionUploadRepository.findById(id).orElseGet(RadarAcquisitionUpload::new);
        ValidationUtil.isNull(radarAcquisitionUpload.getId(),"RadarAcquisitionUpload","id",id);
        return radarAcquisitionUploadMapper.toDto(radarAcquisitionUpload);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RadarAcquisitionUploadDto create(RadarAcquisitionUpload resources) {
        return radarAcquisitionUploadMapper.toDto(radarAcquisitionUploadRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(RadarAcquisitionUpload resources) {
        RadarAcquisitionUpload radarAcquisitionUpload = radarAcquisitionUploadRepository.findById(resources.getId()).orElseGet(RadarAcquisitionUpload::new);
        ValidationUtil.isNull( radarAcquisitionUpload.getId(),"RadarAcquisitionUpload","id",resources.getId());
        radarAcquisitionUpload.copy(resources);
        radarAcquisitionUploadRepository.save(radarAcquisitionUpload);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            radarAcquisitionUploadRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<RadarAcquisitionUploadDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (RadarAcquisitionUploadDto radarAcquisitionUpload : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("文件名", radarAcquisitionUpload.getFileName());
            map.put("文件存储路径", radarAcquisitionUpload.getFilePath());
            map.put("创建时间", radarAcquisitionUpload.getCreatTime());
            map.put("更新时间", radarAcquisitionUpload.getUpdateTime());
            map.put("上传者", radarAcquisitionUpload.getByUser());
            map.put("备注1", radarAcquisitionUpload.getBeizhu1());
            map.put("备注2", radarAcquisitionUpload.getBeizhu2());
            map.put("备注3", radarAcquisitionUpload.getBeizhu3());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    //    ↓表示该方法在执行过程中将被包装在一个事务中，并且在出现异常时进行回滚。
    @Transactional(rollbackFor = Exception.class)
    public RadarAcquisitionUpload uploadFile(RadarAcquisitionUpload radarAcquisitionUpload, MultipartFile multipartFile) {
        //String id = radarAcquisitionUpload.getId().toString();
        // 1.上传文件的大小是否超过了设定的最大值。properties.getMaxSize()获取了最大文件大小的配置信息。
        FileUtil.checkSize(properties.getMaxSize(), multipartFile.getSize());
        // 2.获取上传文件的后缀名。(jpg, prj, dat, raw...)
        String suffix = FileUtil.getExtensionName(multipartFile.getOriginalFilename());
        // 3.获取用户名
        String author = radarAcquisitionUpload.getByUser();
        // 4.文件写入磁盘（将上传的文件保存到指定路径中。）获取了文件保存路径的配置信息。
        //      File.separator 是一个表示文件路径分隔符的常量，用于在不同操作系统上正确处理文件路径的分隔符。使得你的代码可以在不同的操作系统上运行而不需要手动更改文件路径分隔符。
        //      properties.getPath().getPath(): D:\eladmin\file\radarFile\
        // filePath: D:\eladmin\file\radarFile\作者
        String filePath = properties.getPath().getPath() + author;

        // 获取当前时间戳(sql)
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        // 使用SimpleDateFormat定义日期和时间的格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
        // 将时间戳格式化为所需的字符串格式
        String folderName = dateFormat.format(currentTime);
//        D:\eladmin\file\radarFile\作者\上传时间
        filePath += File.separator + folderName;

        // 加分隔符; filePath: D:\eladmin\file\radarFile\作者\上传时间\
        filePath += File.separator;
        File file = FileUtil.upload(multipartFile, filePath);
        // 5.如果上传文件失败（file为空），则抛出一个BadRequestException异常，提示上传失败。
        if (ObjectUtil.isNull(file)) {
            throw new BadRequestException("上传失败");
        }
        try {
//            6.将文件录入数据库
            RadarAcquisitionUpload radarAcquisitionUpload1 = new RadarAcquisitionUpload();
            //radarAcquisitionUpload1.setId(Integer.parseInt(id));              // 文件id就不用设置,直接自增
            String fileName = multipartFile.getOriginalFilename();
            radarAcquisitionUpload1.setFileName(fileName);                  // 文件名
            radarAcquisitionUpload1.setFilePath(filePath);                  // 文件路径
            radarAcquisitionUpload1.setCreatTime(currentTime);
            radarAcquisitionUpload1.setByUser(author);                      // 设置作者
            return radarAcquisitionUploadRepository.save(radarAcquisitionUpload1);
        } catch (Exception e) {
//            如果在保存过程中出现异常，即catch块中的代码被执行，调用FileUtil.del(file)删除之前上传的文件，并将异常继续抛出
            FileUtil.del(file);
            throw e;
        }
    }
}
