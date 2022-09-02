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

import com.google.gson.Gson;
import me.zhengjie.modules.system.domain.TkyTestInformation;
import me.zhengjie.modules.system.verify.LoginDataBean;
import me.zhengjie.modules.system.verify.ResultCode;
import me.zhengjie.modules.system.verify.RsaUtil;
import me.zhengjie.modules.system.verify.TkyTestInfoBean;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.TkyTestInformationRepository;
import me.zhengjie.modules.system.service.TkyTestInformationService;
import me.zhengjie.modules.system.service.dto.TkyTestInformationDto;
import me.zhengjie.modules.system.service.dto.TkyTestInformationQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.TkyTestInformationMapper;
import okhttp3.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.util.IdUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;

import java.security.PublicKey;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static me.zhengjie.modules.system.verify.RsaUtil.jwt;

/**
 * @author wuxiaoxuan
 * @website https://el-admin.vip
 * @description 服务实现
 * @date 2022-05-31
 **/
@Service
@RequiredArgsConstructor
public class TkyTestInformationServiceImpl implements TkyTestInformationService {
    public static String pubilcDefaultKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCEJiVVdmEzgRk/Lqg+I8JyavmI6eRZCv9d3rJ/yDGUS0BbxrrUrhdK4t/hLZUjkjtCsX80eLxecn6HkMBHaEM0tQYmZyxpB5NBPHioggQHDrJVskzDdqliHobem+X54INPznlXXwLtRl4vh1/ducI7laD59bVg/I+h/SrepgOvqwIDAQAB";
    //    private final TkyTestInformationService tkyTestInformationService;
    private final TkyTestInformationRepository tkyTestInformationRepository;
    private final TkyTestInformationMapper tkyTestInformationMapper;
    private OkHttpClient client;
    private Gson gson;

    @Override
    public Map<String, Object> queryAll(TkyTestInformationQueryCriteria criteria, Pageable pageable) {
        Page<TkyTestInformation> page = tkyTestInformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(tkyTestInformationMapper::toDto));
    }

    @Override
    public List<TkyTestInformationDto> queryAll(TkyTestInformationQueryCriteria criteria) {
        return tkyTestInformationMapper.toDto(tkyTestInformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    @Transactional
    public TkyTestInformationDto findById(String id) {
        TkyTestInformation tkyTestInformation = tkyTestInformationRepository.findById(id).orElseGet(TkyTestInformation::new);
        ValidationUtil.isNull(tkyTestInformation.getId(), "TkyTestInformation", "id", id);
        return tkyTestInformationMapper.toDto(tkyTestInformation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TkyTestInformationDto create(TkyTestInformation resources) {
        resources.setId(IdUtil.simpleUUID());
        return tkyTestInformationMapper.toDto(tkyTestInformationRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(TkyTestInformation resources) {
        TkyTestInformation tkyTestInformation = tkyTestInformationRepository.findById(resources.getId()).orElseGet(TkyTestInformation::new);
        ValidationUtil.isNull(tkyTestInformation.getId(), "TkyTestInformation", "id", resources.getId());
        tkyTestInformation.copy(resources);
        tkyTestInformationRepository.save(tkyTestInformation);
    }

    @Override
    public void deleteAll(String[] ids) {
        for (String id : ids) {
            tkyTestInformationRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<TkyTestInformationDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TkyTestInformationDto tkyTestInformation : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("项目id", tkyTestInformation.getXmid());
            map.put("项目名称", tkyTestInformation.getXmname());
            map.put("标段id", tkyTestInformation.getBdid());
            map.put("标段名称", tkyTestInformation.getBdname());
            map.put("构筑物id", tkyTestInformation.getGzwid());
            map.put("构筑物名称", tkyTestInformation.getGzwname());
            map.put("工点id", tkyTestInformation.getGdid());
            map.put("工点名称", tkyTestInformation.getGdname());
            map.put("工程id", tkyTestInformation.getGcid());
            map.put("工程名称", tkyTestInformation.getGcname());
            map.put("施工单位id", tkyTestInformation.getSgdwid());
            map.put("施工单位名称", tkyTestInformation.getSgdwname());
            map.put("监理单位id", tkyTestInformation.getJldwid());
            map.put("监理单位名称", tkyTestInformation.getJldwname());
            map.put("检测单位id", tkyTestInformation.getJcdwid());
            map.put("检测单位名称", tkyTestInformation.getJcdwname());
            map.put("检测方法", tkyTestInformation.getJcff());
            map.put("试验方法", tkyTestInformation.getSyff());
            map.put("联系人", tkyTestInformation.getLxr());
            map.put("联系电话", tkyTestInformation.getLxdh());
            map.put("预约检测日期", tkyTestInformation.getByrq());
            map.put("检测设备", tkyTestInformation.getJcsb());
            map.put("校验日期", tkyTestInformation.getJxrq());
            map.put("校验周期", tkyTestInformation.getJxzq());
            map.put("施工负责人", tkyTestInformation.getSgfzr());
            map.put("检测起始里程编码", tkyTestInformation.getJcqslcbm());
            map.put("检测起始里程1", tkyTestInformation.getJcqslc1());
            map.put("检测起始里程2", tkyTestInformation.getJcqslc2());
            map.put("检测起始里程编码", tkyTestInformation.getJcjslcbm());
            map.put("检测结束里程1", tkyTestInformation.getJcjslc1());
            map.put("检测结束里程2", tkyTestInformation.getJcjslc2());
            map.put("检测长度", tkyTestInformation.getJccd());
            map.put("数量", tkyTestInformation.getCqsl());
            map.put("检测部位", tkyTestInformation.getJcbw());
            map.put("检测方式", tkyTestInformation.getJcfs());
            map.put("检测类型", tkyTestInformation.getJclx());
            map.put("钻芯部位", tkyTestInformation.getZxbw());
            map.put("设计厚度(cm)/仰拱厚度", tkyTestInformation.getSjhd());
            map.put("填充厚度", tkyTestInformation.getTchd());
            map.put("设计强度", tkyTestInformation.getSjqd());
            map.put("锚杆编号", tkyTestInformation.getMgbh());
            map.put("锚杆直径(mm)", tkyTestInformation.getMgzj());
            map.put("锚杆类型", tkyTestInformation.getMglx());
            map.put("锚杆数量", tkyTestInformation.getMgsl());
            map.put("围岩等级", tkyTestInformation.getWydj());
            map.put("设计锚杆长度(m)", tkyTestInformation.getSjmgcd());
            map.put("设计锚杆抗拔力(KN)", tkyTestInformation.getSjmgkbl());
            map.put("浇筑日期(施工日期)", tkyTestInformation.getJzrq());
            map.put("设计锚杆长度（m)", tkyTestInformation.getShejmgcd());
            map.put("设计锚杆抗拔力（KN)", tkyTestInformation.getShejmgkbl());
            map.put("龄期强度报告", tkyTestInformation.getLqqdbg());
            map.put("示意图", tkyTestInformation.getSyt());
            map.put("中间报告", tkyTestInformation.getZjbg());
            map.put("中间报告检测依据", tkyTestInformation.getZjbgjcyj());
            map.put("中间报告简介", tkyTestInformation.getZjbgjj());
            map.put("中间报告校验", tkyTestInformation.getZjbgjy());
            map.put("检测数据附件", tkyTestInformation.getJcsjfj());
            map.put("检测数据附件上传时间", tkyTestInformation.getJcsjfjscsj());
            map.put("施工单位发布状态", tkyTestInformation.getSgdwfbzt());
            map.put("流程状态", tkyTestInformation.getLczt());
            map.put("提交人（施工单位）", tkyTestInformation.getTjr());
            map.put("提交备注（施工单位）", tkyTestInformation.getTjbz());
            map.put("提交时间（施工单位）", tkyTestInformation.getTjsj());
            map.put("审批意见", tkyTestInformation.getSpyj());
            map.put("审批人（监理单位）", tkyTestInformation.getSpr());
            map.put("审批备注（监理单位）", tkyTestInformation.getSpbz());
            map.put("审批时间（监理单位）", tkyTestInformation.getSpsj());
            map.put("报检范围", tkyTestInformation.getBjfw());
            map.put("报检数量", tkyTestInformation.getBjsl());
            map.put("报检单编号", tkyTestInformation.getBydbh());
            map.put("检测分析时间", tkyTestInformation.getJcfxsj());
            map.put("检测分析人", tkyTestInformation.getJcfxr());
            map.put("检测意见", tkyTestInformation.getJcyj());
            map.put("检测人（检测单位）", tkyTestInformation.getJcr());
            map.put("检测备注（检测单位）", tkyTestInformation.getJcbz());
            map.put("检测时间（检测单位）", tkyTestInformation.getJcsj());
            map.put("检测进度状态", tkyTestInformation.getJcjdzt());
            map.put("检测结论备注", tkyTestInformation.getJcjlbz());
            map.put("检测结论", tkyTestInformation.getJcjl());
            map.put("检测结论附件", tkyTestInformation.getJcjlfj());
            map.put("复核检测结果", tkyTestInformation.getFhjcjg());
            map.put("复核审批意见", tkyTestInformation.getFhspyj());
            map.put("复核审批人（监理单位）", tkyTestInformation.getFhspr());
            map.put("复核审批备注（监理单位）", tkyTestInformation.getFhspbz());
            map.put("复核审批时间（监理单位）", tkyTestInformation.getFhspsj());
            map.put("发布状态", tkyTestInformation.getZt());
            map.put("发布时间", tkyTestInformation.getFbsj());
            map.put("发布人", tkyTestInformation.getFbr());
            map.put("报检单类型", tkyTestInformation.getTestType());
            map.put("成孔方式", tkyTestInformation.getHoleMethod());
            map.put("混凝土强度等级", tkyTestInformation.getConcreteStrength());
            map.put("设计单位id", tkyTestInformation.getDesighOrg());
            map.put("设计单位名称", tkyTestInformation.getDesighOrgName());
            map.put("检测单位id", tkyTestInformation.getInspectionOrg());
            map.put("检测单位名称", tkyTestInformation.getInspectionOrgName());
            map.put("buildType", tkyTestInformation.getBuildtype());
            map.put(" createdate", tkyTestInformation.getCreatedate());
            map.put(" createdatestr", tkyTestInformation.getCreatedatestr());
            map.put("中间报告检测", tkyTestInformation.getZjbgjc());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public void syntkydata() {
        try {
            Request request1 = new Request.Builder()
                    .url("https://apps.r93535.com/services/ZL02203/sdljzljcapi/sd_projectinspectiondeclaration/getWaitSubmitList")
                    .method("GET", null)
                    .addHeader("Cookie", "CRBIMSSOJWT=" + jwt)
                    .build();
            Response response1 = null;

            response1 = client.newCall(request1).execute();

            TkyTestInfoBean tkyTestInfoBean = gson.fromJson(response1.body().string(), TkyTestInfoBean.class);
            List<TkyTestInformation> tkyTestInformationList = tkyTestInfoBean.getData();
            TkyTestInformationQueryCriteria criteria = new TkyTestInformationQueryCriteria();
            List<String> listids = new ArrayList<>();
            List<TkyTestInformationDto> tkyTestInformationDtos = queryAll(criteria);
            for (TkyTestInformationDto tkyTestInformationDto : tkyTestInformationDtos) {
                listids.add(tkyTestInformationDto.getBydbh());
            }
//            String[] lsids = new String[tkyTestInformationDtos.size()];
//            deleteAll(listids.toArray(lsids));
//        List<TkyTestInformation> tkyTestInformationList = JSON.parseArray(o.toString(), TkyTestInformation.class);
            for (TkyTestInformation tkyTestInformation : tkyTestInformationList) {
                if (!listids.contains(tkyTestInformation.getBydbh())) {
                    create(tkyTestInformation);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void login() {
        client = new OkHttpClient().newBuilder()
                .build();
        LoginDataBean loginDataBean = new LoginDataBean();
        loginDataBean.setAccount("wgwangy");
        loginDataBean.setPassword("jXY3aFLf5TZlGyterIkUzg==");
        loginDataBean.setAppname("地质雷达");
        String currentMillus = System.currentTimeMillis() + "";
        loginDataBean.setTimestamp(currentMillus);
        PublicKey publicKey = null;
        try {
            publicKey = RsaUtil.string2PublicKey(pubilcDefaultKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String byte2Base64 = RsaUtil.encipher((new Gson()).toJson(loginDataBean).getBytes(), publicKey, 117);
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("appname", "r")
                .addFormDataPart("account", "wgwangy")
                .addFormDataPart("password", "jXY3aFLf5TZlGyterIkUzg==")
                .addFormDataPart("timestamp", currentMillus)
                .addFormDataPart("token", byte2Base64)
                .build();
        Request request = new Request.Builder()
                .url("http://sso.r93535.com/api/v1.0/login")
                .method("POST", body)
//                .addHeader("Cookie", "CRBIMSSOJWT=value; acw_tc=784e2c9416620949727877649e096239e5a4e7d27661eea17ed0785eb002ff; BIGipServerpool_srxtjk=!3I0ue8NPOHZfdNqb/CULM79eIdI1FHSQoPKGBsb5YlU9iVzON0FyT+g1dQIOSApSFW9V+m/jkuVWvvA=")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String s = response.body().string();
//            System.out.println(s);
            gson = new Gson();
            ResultCode resultCode = gson.fromJson(s, ResultCode.class);
//            System.out.println(resultCode.getJwt());
            String jwt = resultCode.getJwt();
            RsaUtil.jwt = jwt;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}