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

import cn.hutool.core.io.IoUtil;
import com.deepoove.poi.XWPFTemplate;
import me.zhengjie.modules.system.domain.RadarDiseasetypePictures;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.RadarDiseasetypePicturesRepository;
import me.zhengjie.modules.system.service.RadarDiseasetypePicturesService;
import me.zhengjie.modules.system.service.dto.RadarDiseasetypePicturesDto;
import me.zhengjie.modules.system.service.dto.RadarDiseasetypePicturesQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.RadarDiseasetypePicturesMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.IOException;
import java.io.File;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author zuohaitao
* @date 2023-06-30
**/
@Service
@RequiredArgsConstructor
public class RadarDiseasetypePicturesServiceImpl implements RadarDiseasetypePicturesService {

    private final RadarDiseasetypePicturesRepository radarDiseasetypePicturesRepository;
    private final RadarDiseasetypePicturesMapper radarDiseasetypePicturesMapper;

    @Override
    public Map<String,Object> queryAll(RadarDiseasetypePicturesQueryCriteria criteria, Pageable pageable){
        Page<RadarDiseasetypePictures> page = radarDiseasetypePicturesRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(radarDiseasetypePicturesMapper::toDto));
    }

    @Override
    public List<RadarDiseasetypePicturesDto> queryAll(RadarDiseasetypePicturesQueryCriteria criteria){
        return radarDiseasetypePicturesMapper.toDto(radarDiseasetypePicturesRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public RadarDiseasetypePicturesDto findById(Integer id) {
        RadarDiseasetypePictures radarDiseasetypePictures = radarDiseasetypePicturesRepository.findById(id).orElseGet(RadarDiseasetypePictures::new);
        ValidationUtil.isNull(radarDiseasetypePictures.getId(),"RadarDiseasetypePictures","id",id);
        return radarDiseasetypePicturesMapper.toDto(radarDiseasetypePictures);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RadarDiseasetypePicturesDto create(RadarDiseasetypePictures resources) {
        return radarDiseasetypePicturesMapper.toDto(radarDiseasetypePicturesRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(RadarDiseasetypePictures resources) {
        RadarDiseasetypePictures radarDiseasetypePictures = radarDiseasetypePicturesRepository.findById(resources.getId()).orElseGet(RadarDiseasetypePictures::new);
        ValidationUtil.isNull( radarDiseasetypePictures.getId(),"RadarDiseasetypePictures","id",resources.getId());
        radarDiseasetypePictures.copy(resources);
        radarDiseasetypePicturesRepository.save(radarDiseasetypePictures);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            radarDiseasetypePicturesRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<RadarDiseasetypePicturesDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (RadarDiseasetypePicturesDto radarDiseasetypePictures : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("Category1", radarDiseasetypePictures.getCategory1());
            map.put("Category2", radarDiseasetypePictures.getCategory2());
            map.put("Category3", radarDiseasetypePictures.getCategory3());
            map.put("Category4", radarDiseasetypePictures.getCategory4());
            map.put("Category5", radarDiseasetypePictures.getCategory5());
            map.put("Category6", radarDiseasetypePictures.getCategory6());
            map.put("Category7", radarDiseasetypePictures.getCategory7());
            map.put("Category8", radarDiseasetypePictures.getCategory8());
            map.put("Category9", radarDiseasetypePictures.getCategory9());
            map.put("Category10", radarDiseasetypePictures.getCategory10());
            map.put("Category11", radarDiseasetypePictures.getCategory11());
            map.put("Category12", radarDiseasetypePictures.getCategory12());
            map.put("Category13", radarDiseasetypePictures.getCategory13());
            map.put("Category14", radarDiseasetypePictures.getCategory14());
            map.put("Category15", radarDiseasetypePictures.getCategory15());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public void downloadWord(List<RadarDiseasetypePicturesDto> all, HttpServletResponse response) throws IOException {

        // 给文档内的变量设值
        Map<String, Object> map = new HashMap<>();
        map.put("name", "李明");
        map.put("age", "18");
        map.put("date", "2023-07-01");

        //word模板地址获取方式：这种方法不会在linux或者jar上失效(resources目录下)
        ClassPathResource classPathResource = new ClassPathResource("template/doc/templateDoc.docx");
        String resource = classPathResource.getURL().getPath();
        // 读取Word文件
        XWPFTemplate template = XWPFTemplate.compile(resource);
        // 将值付给文档
        template.render(map);

        //=================生成文件保存在本地D盘某目录下=================
        String temDir="D:/eladmin/"+File.separator+"file/word/";    // 生成临时文件存放地址
        Long time = new Date().getTime();                           // 生成文件名
        String formatSuffix = ".docx";                              // 生成的word格式
        String fileName = time + formatSuffix;                      // 拼接后的文件名
        FileOutputStream fos = new FileOutputStream(temDir+fileName);
        template.write(fos);
        //=================生成word到设置浏览默认下载地址=================
        // 设置强制下载不打开
        response.setContentType("application/force-download");
        // 设置文件名
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
        OutputStream out = response.getOutputStream();
        template.write(out);
        out.flush();
        out.close();
        template.close();

    }
}