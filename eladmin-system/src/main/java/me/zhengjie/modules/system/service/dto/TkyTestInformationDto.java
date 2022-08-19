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
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TkyTestInformationDto that = (TkyTestInformationDto) o;
        return Objects.equals(xmid, that.xmid) && Objects.equals(xmname, that.xmname) && Objects.equals(bdid, that.bdid) && Objects.equals(bdname, that.bdname) && Objects.equals(gzwid, that.gzwid) && Objects.equals(gzwname, that.gzwname) && Objects.equals(gdid, that.gdid) && Objects.equals(gdname, that.gdname) && Objects.equals(gcid, that.gcid) && Objects.equals(gcname, that.gcname) && Objects.equals(sgdwid, that.sgdwid) && Objects.equals(sgdwname, that.sgdwname) && Objects.equals(jldwid, that.jldwid) && Objects.equals(jldwname, that.jldwname) && Objects.equals(jcdwid, that.jcdwid) && Objects.equals(jcdwname, that.jcdwname) && Objects.equals(jcff, that.jcff) && Objects.equals(syff, that.syff) && Objects.equals(lxr, that.lxr) && Objects.equals(lxdh, that.lxdh) && Objects.equals(byrq, that.byrq) && Objects.equals(jcsb, that.jcsb) && Objects.equals(jxrq, that.jxrq) && Objects.equals(jxzq, that.jxzq) && Objects.equals(sgfzr, that.sgfzr) && Objects.equals(jcqslcbm, that.jcqslcbm) && Objects.equals(jcqslc1, that.jcqslc1) && Objects.equals(jcqslc2, that.jcqslc2) && Objects.equals(jcjslcbm, that.jcjslcbm) && Objects.equals(jcjslc1, that.jcjslc1) && Objects.equals(jcjslc2, that.jcjslc2) && Objects.equals(jccd, that.jccd) && Objects.equals(cqsl, that.cqsl) && Objects.equals(jcbw, that.jcbw) && Objects.equals(jcfs, that.jcfs) && Objects.equals(jclx, that.jclx) && Objects.equals(zxbw, that.zxbw) && Objects.equals(sjhd, that.sjhd) && Objects.equals(tchd, that.tchd) && Objects.equals(sjqd, that.sjqd) && Objects.equals(mgbh, that.mgbh) && Objects.equals(mgzj, that.mgzj) && Objects.equals(mglx, that.mglx) && Objects.equals(mgsl, that.mgsl) && Objects.equals(wydj, that.wydj) && Objects.equals(sjmgcd, that.sjmgcd) && Objects.equals(sjmgkbl, that.sjmgkbl) && Objects.equals(jzrq, that.jzrq) && Objects.equals(shejmgcd, that.shejmgcd) && Objects.equals(shejmgkbl, that.shejmgkbl) && Objects.equals(lqqdbg, that.lqqdbg) && Objects.equals(syt, that.syt) && Objects.equals(zjbg, that.zjbg) && Objects.equals(zjbgjcyj, that.zjbgjcyj) && Objects.equals(zjbgjj, that.zjbgjj) && Objects.equals(zjbgjy, that.zjbgjy) && Objects.equals(jcsjfj, that.jcsjfj) && Objects.equals(jcsjfjscsj, that.jcsjfjscsj) && Objects.equals(sgdwfbzt, that.sgdwfbzt) && Objects.equals(lczt, that.lczt) && Objects.equals(tjr, that.tjr) && Objects.equals(tjbz, that.tjbz) && Objects.equals(tjsj, that.tjsj) && Objects.equals(spyj, that.spyj) && Objects.equals(spr, that.spr) && Objects.equals(spbz, that.spbz) && Objects.equals(spsj, that.spsj) && Objects.equals(bjfw, that.bjfw) && Objects.equals(bjsl, that.bjsl) && Objects.equals(bydbh, that.bydbh) && Objects.equals(jcfxsj, that.jcfxsj) && Objects.equals(jcfxr, that.jcfxr) && Objects.equals(jcyj, that.jcyj) && Objects.equals(jcr, that.jcr) && Objects.equals(jcbz, that.jcbz) && Objects.equals(jcsj, that.jcsj) && Objects.equals(jcjdzt, that.jcjdzt) && Objects.equals(jcjlbz, that.jcjlbz) && Objects.equals(jcjl, that.jcjl) && Objects.equals(jcjlfj, that.jcjlfj) && Objects.equals(fhjcjg, that.fhjcjg) && Objects.equals(fhspyj, that.fhspyj) && Objects.equals(fhspr, that.fhspr) && Objects.equals(fhspbz, that.fhspbz) && Objects.equals(fhspsj, that.fhspsj) && Objects.equals(zt, that.zt) && Objects.equals(fbsj, that.fbsj) && Objects.equals(fbr, that.fbr) && Objects.equals(testType, that.testType) && Objects.equals(holeMethod, that.holeMethod) && Objects.equals(concreteStrength, that.concreteStrength) && Objects.equals(desighOrg, that.desighOrg) && Objects.equals(desighOrgName, that.desighOrgName) && Objects.equals(inspectionOrg, that.inspectionOrg) && Objects.equals(inspectionOrgName, that.inspectionOrgName) && Objects.equals(buildtype, that.buildtype) && Objects.equals(createdate, that.createdate) && Objects.equals(createdatestr, that.createdatestr) && Objects.equals(zjbgjc, that.zjbgjc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xmid, xmname, bdid, bdname, gzwid, gzwname, gdid, gdname, gcid, gcname, sgdwid, sgdwname, jldwid, jldwname, jcdwid, jcdwname, jcff, syff, lxr, lxdh, byrq, jcsb, jxrq, jxzq, sgfzr, jcqslcbm, jcqslc1, jcqslc2, jcjslcbm, jcjslc1, jcjslc2, jccd, cqsl, jcbw, jcfs, jclx, zxbw, sjhd, tchd, sjqd, mgbh, mgzj, mglx, mgsl, wydj, sjmgcd, sjmgkbl, jzrq, shejmgcd, shejmgkbl, lqqdbg, syt, zjbg, zjbgjcyj, zjbgjj, zjbgjy, jcsjfj, jcsjfjscsj, sgdwfbzt, lczt, tjr, tjbz, tjsj, spyj, spr, spbz, spsj, bjfw, bjsl, bydbh, jcfxsj, jcfxr, jcyj, jcr, jcbz, jcsj, jcjdzt, jcjlbz, jcjl, jcjlfj, fhjcjg, fhspyj, fhspr, fhspbz, fhspsj, zt, fbsj, fbr, testType, holeMethod, concreteStrength, desighOrg, desighOrgName, inspectionOrg, inspectionOrgName, buildtype, createdate, createdatestr, zjbgjc);
    }
}