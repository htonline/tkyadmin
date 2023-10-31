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

import com.deepoove.poi.XWPFTemplate;
import me.zhengjie.modules.system.domain.Disease;
import me.zhengjie.modules.system.service.dto.RadarDiseasetypePicturesDto;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.DiseaseRepository;
import me.zhengjie.modules.system.service.DiseaseService;
import me.zhengjie.modules.system.service.dto.DiseaseDto;
import me.zhengjie.modules.system.service.dto.DiseaseQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.DiseaseMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;

import java.io.*;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author Zuo Haitao
* @date 2023-05-16
**/
@Service
@RequiredArgsConstructor
public class DiseaseServiceImpl implements DiseaseService {

    private final DiseaseRepository diseaseRepository;
    private final DiseaseMapper diseaseMapper;

    @Override
    public Map<String,Object> queryAll(DiseaseQueryCriteria criteria, Pageable pageable){
        Page<Disease> page = diseaseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(diseaseMapper::toDto));
    }

    @Override
    public List<DiseaseDto> queryAll(DiseaseQueryCriteria criteria){
        return diseaseMapper.toDto(diseaseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public DiseaseDto findById(Long diseaseId) {
        Disease disease = diseaseRepository.findById(diseaseId).orElseGet(Disease::new);
        ValidationUtil.isNull(disease.getDiseaseId(),"Disease","diseaseId",diseaseId);
        return diseaseMapper.toDto(disease);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DiseaseDto create(Disease resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setDiseaseId(snowflake.nextId());
        return diseaseMapper.toDto(diseaseRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Disease resources) {
        Disease disease = diseaseRepository.findById(resources.getDiseaseId()).orElseGet(Disease::new);
        ValidationUtil.isNull( disease.getDiseaseId(),"Disease","id",resources.getDiseaseId());
        disease.copy(resources);
        diseaseRepository.save(disease);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long diseaseId : ids) {
            diseaseRepository.deleteById(diseaseId);
        }
    }

    @Override
    public void download(List<DiseaseDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DiseaseDto disease : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("雷达文件名称", disease.getRadarName());
            map.put("岩土性质", disease.getSoilProperty());
            map.put("病害种类", disease.getDiseaseType());
            map.put("起始深度（采样点数）", disease.getStartDepth());
            map.put("终止深度（采样点数）", disease.getEndDepth());
            map.put("净空高", disease.getFreeHeight());
            map.put("宽度开始通道数", disease.getStartWidth());
            map.put("宽度结束通道数", disease.getEndWidth());
            map.put("尺寸", disease.getDiseaseSize());
            map.put("中心点经纬度坐标", disease.getLongLat());
            map.put("删除标志", disease.getDelFlag());
            map.put("创建者", disease.getCreateBy());
            map.put("创建时间", disease.getCreateTime());
            map.put("更新者", disease.getUpdateBy());
            map.put("备注", disease.getRemark());
            map.put("备注", disease.getRemark1());
            map.put("备注", disease.getRemark2());
            map.put("备注", disease.getRemark3());
            map.put("备注", disease.getRemark4());
            map.put("备注", disease.getRemark5());
            map.put("备注", disease.getRemark6());
            map.put("更新时间", disease.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public void downloadWord(DiseaseQueryCriteria criteria, HttpServletResponse response) throws IOException {

        // 给文档内的变量设值
        Map<String, Object> map = new HashMap<>();
        map.put("name", "李明");
        map.put("age", "18");
        map.put("date", "2023-07-01");

        try {

            //InputStream inputStream = getClass().getClassLoader().getResourceAsStream("template/doc/templateDoc.docx");

            ClassPathResource resource = new ClassPathResource("template/doc/templateDoc.docx");
            File sourceFile = resource.getFile();
            InputStream inputStream = resource.getInputStream();


            XWPFTemplate template = XWPFTemplate.compile(inputStream);

            // 假设 'map' 是一个包含要替换模板中值的 Map<String, Object>
            template.render(map);

            //=================生成文件保存在本地D盘某目录下=================
            String tempDir ="D:/eladmin"+File.separator+"file/word/";    // 生成临时文件存放地址
            Long time = new Date().getTime();                           // 生成文件名
            String fileName = time + ".docx";                           // 拼接后的文件名
            FileOutputStream fos = new FileOutputStream(tempDir+fileName);
            template.write(fos);

            // 设置强制下载不打开
            response.setContentType("application/force-download");
            // 设置文件名
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            OutputStream out = response.getOutputStream();
            template.write(out);
            out.flush();
            out.close();

            // 渲染模板后关闭模板
            template.close();
        } catch (IOException e) {
            // 处理可能发生的读取资源或渲染模板时的任何 IOException
            e.printStackTrace();
        }

    }
}
