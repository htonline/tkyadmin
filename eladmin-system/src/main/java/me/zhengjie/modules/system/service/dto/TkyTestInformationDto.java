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
package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author wuxiaoxuan
* @date 2022-05-31
**/
@Data
public class TkyTestInformationDto implements Serializable {

    /** 报验单id */
    private String id;

    /** 项目id */
    private String xmid;

    /** 项目名称 */
    private String xmname;

    /** 标段id */
    private String bdid;

    /** 标段名称 */
    private String bdname;

    /** 
构筑物id */
    private String gzwid;

    /** 构筑物名称 */
    private String gzwname;

    /** 工点id */
    private String gdid;

    /** 工点名称 */
    private String gdname;

    /** 工程id */
    private String gcid;

    /** 工程名称 */
    private String gcname;

    /** 施工单位id */
    private String sgdwid;

    /** 施工单位名称 */
    private String sgdwname;

    /** 监理单位id */
    private String jldwid;

    /** 监理单位名称 */
    private String jldwname;

    /** 检测单位id */
    private String jcdwid;

    /** 检测单位名称 */
    private String jcdwname;

    /** 检测方法 */
    private String jcff;

    /** 试验方法 */
    private String syff;

    /** 联系人 */
    private String lxr;

    /** 联系电话 */
    private String lxdh;

    /** 预约检测日期 */
    private String byrq;

    /** 检测设备 */
    private String jcsb;

    /** 校验日期 */
    private String jxrq;

    /** 校验周期 */
    private String jxzq;

    /** 施工负责人 */
    private String sgfzr;

    /** 检测起始里程编码 */
    private String jcqslcbm;

    /** 检测起始里程1 */
    private String jcqslc1;

    /** 检测起始里程2 */
    private String jcqslc2;

    /** 检测起始里程编码 */
    private String jcjslcbm;

    /** 检测结束里程1 */
    private String jcjslc1;

    /** 检测结束里程2 */
    private String jcjslc2;

    /** 检测长度 */
    private String jccd;

    /** 数量 */
    private String cqsl;

    /** 检测部位 */
    private String jcbw;

    /** 检测方式 */
    private String jcfs;

    /** 检测类型 */
    private String jclx;

    /** 钻芯部位 */
    private String zxbw;

    /** 设计厚度(cm)/仰拱厚度 */
    private String sjhd;

    /** 填充厚度 */
    private String tchd;

    /** 设计强度 */
    private String sjqd;

    /** 锚杆编号 */
    private String mgbh;

    /** 锚杆直径(mm) */
    private String mgzj;

    /** 锚杆类型 */
    private String mglx;

    /** 锚杆数量 */
    private String mgsl;

    /** 围岩等级 */
    private String wydj;

    /** 设计锚杆长度(m) */
    private String sjmgcd;

    /** 设计锚杆抗拔力(KN) */
    private String sjmgkbl;

    /** 浇筑日期(施工日期) */
    private String jzrq;

    /** 设计锚杆长度（m) */
    private String shejmgcd;

    /** 设计锚杆抗拔力（KN) */
    private String shejmgkbl;

    /** 龄期强度报告 */
    private String lqqdbg;

    /** 示意图 */
    private String syt;

    /** 中间报告 */
    private String zjbg;

    /** 中间报告检测依据 */
    private String zjbgjcyj;

    /** 中间报告简介 */
    private String zjbgjj;

    /** 中间报告校验 */
    private String zjbgjy;

    /** 检测数据附件 */
    private String jcsjfj;

    /** 检测数据附件上传时间 */
    private String jcsjfjscsj;

    /** 施工单位发布状态 */
    private String sgdwfbzt;

    /** 流程状态 */
    private String lczt;

    /** 提交人（施工单位） */
    private String tjr;

    /** 提交备注（施工单位） */
    private String tjbz;

    /** 提交时间（施工单位） */
    private String tjsj;

    /** 审批意见 */
    private String spyj;

    /** 审批人（监理单位） */
    private String spr;

    /** 审批备注（监理单位） */
    private String spbz;

    /** 审批时间（监理单位） */
    private String spsj;

    /** 报检范围 */
    private String bjfw;

    /** 报检数量 */
    private String bjsl;

    /** 报检单编号 */
    private String bydbh;

    /** 检测分析时间 */
    private String jcfxsj;

    /** 检测分析人 */
    private String jcfxr;

    /** 检测意见 */
    private String jcyj;

    /** 检测人（检测单位） */
    private String jcr;

    /** 检测备注（检测单位） */
    private String jcbz;

    /** 检测时间（检测单位） */
    private String jcsj;

    /** 检测进度状态 */
    private String jcjdzt;

    /** 检测结论备注 */
    private String jcjlbz;

    /** 检测结论 */
    private String jcjl;

    /** 检测结论附件 */
    private String jcjlfj;

    /** 复核检测结果 */
    private String fhjcjg;

    /** 复核审批意见 */
    private String fhspyj;

    /** 复核审批人（监理单位） */
    private String fhspr;

    /** 复核审批备注（监理单位） */
    private String fhspbz;

    /** 复核审批时间（监理单位） */
    private String fhspsj;

    /** 发布状态 */
    private String zt;

    /** 发布时间 */
    private String fbsj;

    /** 发布人 */
    private String fbr;

    /** 报检单类型 */
    private String testType;

    /** 成孔方式 */
    private String holeMethod;

    /** 混凝土强度等级 */
    private String concreteStrength;

    /** 设计单位id */
    private String desighOrg;

    /** 设计单位名称 */
    private String desighOrgName;

    /** 检测单位id */
    private String inspectionOrg;

    /** 检测单位名称 */
    private String inspectionOrgName;

    /** buildType */
    private String buildtype;

    private String createdate;

    private String createdatestr;

    /** 中间报告检测 */
    private String zjbgjc;
}