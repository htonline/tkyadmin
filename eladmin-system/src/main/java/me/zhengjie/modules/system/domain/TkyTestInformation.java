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
package me.zhengjie.modules.system.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
* @website https://el-admin.vip
* @description /
* @author wuxiaoxuan
* @date 2022-05-31
**/
@Entity
@Data
@Table(name="tky_test_information")
public class TkyTestInformation implements Serializable {

    @Id
    @Column(name = "id")
    @ApiModelProperty(value = "报验单id")
    private String id;

    @Column(name = "xmid")
    @ApiModelProperty(value = "项目id")
    private String xmid;

    @Column(name = "xmname")
    @ApiModelProperty(value = "项目名称")
    private String xmname;

    @Column(name = "bdid")
    @ApiModelProperty(value = "标段id")
    private String bdid;

    @Column(name = "bdname")
    @ApiModelProperty(value = "标段名称")
    private String bdname;

    @Column(name = "gzwid")
    @ApiModelProperty(value = "构筑物id")
    private String gzwid;

    @Column(name = "gzwname")
    @ApiModelProperty(value = "构筑物名称")
    private String gzwname;

    @Column(name = "gdid")
    @ApiModelProperty(value = "工点id")
    private String gdid;

    @Column(name = "gdname")
    @ApiModelProperty(value = "工点名称")
    private String gdname;

    @Column(name = "gcid")
    @ApiModelProperty(value = "工程id")
    private String gcid;

    @Column(name = "gcname")
    @ApiModelProperty(value = "工程名称")
    private String gcname;

    @Column(name = "sgdwid")
    @ApiModelProperty(value = "施工单位id")
    private String sgdwid;

    @Column(name = "sgdwname")
    @ApiModelProperty(value = "施工单位名称")
    private String sgdwname;

    @Column(name = "jldwid")
    @ApiModelProperty(value = "监理单位id")
    private String jldwid;

    @Column(name = "jldwname")
    @ApiModelProperty(value = "监理单位名称")
    private String jldwname;

    @Column(name = "jcdwid")
    @ApiModelProperty(value = "检测单位id")
    private String jcdwid;

    @Column(name = "jcdwname")
    @ApiModelProperty(value = "检测单位名称")
    private String jcdwname;

    @Column(name = "jcff")
    @ApiModelProperty(value = "检测方法")
    private String jcff;

    @Column(name = "syff")
    @ApiModelProperty(value = "试验方法")
    private String syff;

    @Column(name = "lxr")
    @ApiModelProperty(value = "联系人")
    private String lxr;

    @Column(name = "lxdh")
    @ApiModelProperty(value = "联系电话")
    private String lxdh;

    @Column(name = "byrq")
    @ApiModelProperty(value = "预约检测日期")
    private String byrq;

    @Column(name = "jcsb")
    @ApiModelProperty(value = "检测设备")
    private String jcsb;

    @Column(name = "jxrq")
    @ApiModelProperty(value = "校验日期")
    private String jxrq;

    @Column(name = "jxzq")
    @ApiModelProperty(value = "校验周期")
    private String jxzq;

    @Column(name = "sgfzr")
    @ApiModelProperty(value = "施工负责人")
    private String sgfzr;

    @Column(name = "jcqslcbm")
    @ApiModelProperty(value = "检测起始里程编码")
    private String jcqslcbm;

    @Column(name = "jcqslc1")
    @ApiModelProperty(value = "检测起始里程1")
    private String jcqslc1;

    @Column(name = "jcqslc2")
    @ApiModelProperty(value = "检测起始里程2")
    private String jcqslc2;

    @Column(name = "jcjslcbm")
    @ApiModelProperty(value = "检测起始里程编码")
    private String jcjslcbm;

    @Column(name = "jcjslc1")
    @ApiModelProperty(value = "检测结束里程1")
    private String jcjslc1;

    @Column(name = "jcjslc2")
    @ApiModelProperty(value = "检测结束里程2")
    private String jcjslc2;

    @Column(name = "jccd")
    @ApiModelProperty(value = "检测长度")
    private String jccd;

    @Column(name = "cqsl")
    @ApiModelProperty(value = "数量")
    private String cqsl;

    @Column(name = "jcbw")
    @ApiModelProperty(value = "检测部位")
    private String jcbw;

    @Column(name = "jcfs")
    @ApiModelProperty(value = "检测方式")
    private String jcfs;

    @Column(name = "jclx")
    @ApiModelProperty(value = "检测类型")
    private String jclx;

    @Column(name = "zxbw")
    @ApiModelProperty(value = "钻芯部位")
    private String zxbw;

    @Column(name = "sjhd")
    @ApiModelProperty(value = "设计厚度(cm)/仰拱厚度")
    private String sjhd;

    @Column(name = "tchd")
    @ApiModelProperty(value = "填充厚度")
    private String tchd;

    @Column(name = "sjqd")
    @ApiModelProperty(value = "设计强度")
    private String sjqd;

    @Column(name = "mgbh")
    @ApiModelProperty(value = "锚杆编号")
    private String mgbh;

    @Column(name = "mgzj")
    @ApiModelProperty(value = "锚杆直径(mm)")
    private String mgzj;

    @Column(name = "mglx")
    @ApiModelProperty(value = "锚杆类型")
    private String mglx;

    @Column(name = "mgsl")
    @ApiModelProperty(value = "锚杆数量")
    private String mgsl;

    @Column(name = "wydj")
    @ApiModelProperty(value = "围岩等级")
    private String wydj;

    @Column(name = "sjmgcd")
    @ApiModelProperty(value = "设计锚杆长度(m)")
    private String sjmgcd;

    @Column(name = "sjmgkbl")
    @ApiModelProperty(value = "设计锚杆抗拔力(KN)")
    private String sjmgkbl;

    @Column(name = "jzrq")
    @ApiModelProperty(value = "浇筑日期(施工日期)")
    private String jzrq;

    @Column(name = "shejmgcd")
    @ApiModelProperty(value = "设计锚杆长度（m)")
    private String shejmgcd;

    @Column(name = "shejmgkbl")
    @ApiModelProperty(value = "设计锚杆抗拔力（KN)")
    private String shejmgkbl;

    @Column(name = "lqqdbg")
    @ApiModelProperty(value = "龄期强度报告")
    private String lqqdbg;

    @Column(name = "syt")
    @ApiModelProperty(value = "示意图")
    private String syt;

    @Column(name = "zjbg")
    @ApiModelProperty(value = "中间报告")
    private String zjbg;

    @Column(name = "zjbgjcyj")
    @ApiModelProperty(value = "中间报告检测依据")
    private String zjbgjcyj;

    @Column(name = "zjbgjj")
    @ApiModelProperty(value = "中间报告简介")
    private String zjbgjj;

    @Column(name = "zjbgjy")
    @ApiModelProperty(value = "中间报告校验")
    private String zjbgjy;

    @Column(name = "jcsjfj")
    @ApiModelProperty(value = "检测数据附件")
    private String jcsjfj;

    @Column(name = "jcsjfjscsj")
    @ApiModelProperty(value = "检测数据附件上传时间")
    private String jcsjfjscsj;

    @Column(name = "sgdwfbzt")
    @ApiModelProperty(value = "施工单位发布状态")
    private String sgdwfbzt;

    @Column(name = "lczt")
    @ApiModelProperty(value = "流程状态")
    private String lczt;

    @Column(name = "tjr")
    @ApiModelProperty(value = "提交人（施工单位）")
    private String tjr;

    @Column(name = "tjbz")
    @ApiModelProperty(value = "提交备注（施工单位）")
    private String tjbz;

    @Column(name = "tjsj")
    @ApiModelProperty(value = "提交时间（施工单位）")
    private String tjsj;

    @Column(name = "spyj")
    @ApiModelProperty(value = "审批意见")
    private String spyj;

    @Column(name = "spr")
    @ApiModelProperty(value = "审批人（监理单位）")
    private String spr;

    @Column(name = "spbz")
    @ApiModelProperty(value = "审批备注（监理单位）")
    private String spbz;

    @Column(name = "spsj")
    @ApiModelProperty(value = "审批时间（监理单位）")
    private String spsj;

    @Column(name = "bjfw")
    @ApiModelProperty(value = "报检范围")
    private String bjfw;

    @Column(name = "bjsl")
    @ApiModelProperty(value = "报检数量")
    private String bjsl;

    @Column(name = "bydbh")
    @ApiModelProperty(value = "报检单编号")
    private String bydbh;

    @Column(name = "jcfxsj")
    @ApiModelProperty(value = "检测分析时间")
    private String jcfxsj;

    @Column(name = "jcfxr")
    @ApiModelProperty(value = "检测分析人")
    private String jcfxr;

    @Column(name = "jcyj")
    @ApiModelProperty(value = "检测意见")
    private String jcyj;

    @Column(name = "jcr")
    @ApiModelProperty(value = "检测人（检测单位）")
    private String jcr;

    @Column(name = "jcbz")
    @ApiModelProperty(value = "检测备注（检测单位）")
    private String jcbz;

    @Column(name = "jcsj")
    @ApiModelProperty(value = "检测时间（检测单位）")
    private String jcsj;

    @Column(name = "jcjdzt")
    @ApiModelProperty(value = "检测进度状态")
    private String jcjdzt;

    @Column(name = "jcjlbz")
    @ApiModelProperty(value = "检测结论备注")
    private String jcjlbz;

    @Column(name = "jcjl")
    @ApiModelProperty(value = "检测结论")
    private String jcjl;

    @Column(name = "jcjlfj")
    @ApiModelProperty(value = "检测结论附件")
    private String jcjlfj;

    @Column(name = "fhjcjg")
    @ApiModelProperty(value = "复核检测结果")
    private String fhjcjg;

    @Column(name = "fhspyj")
    @ApiModelProperty(value = "复核审批意见")
    private String fhspyj;

    @Column(name = "fhspr")
    @ApiModelProperty(value = "复核审批人（监理单位）")
    private String fhspr;

    @Column(name = "fhspbz")
    @ApiModelProperty(value = "复核审批备注（监理单位）")
    private String fhspbz;

    @Column(name = "fhspsj")
    @ApiModelProperty(value = "复核审批时间（监理单位）")
    private String fhspsj;

    @Column(name = "zt")
    @ApiModelProperty(value = "发布状态")
    private String zt;

    @Column(name = "fbsj")
    @ApiModelProperty(value = "发布时间")
    private String fbsj;

    @Column(name = "fbr")
    @ApiModelProperty(value = "发布人")
    private String fbr;

    @Column(name = "test_type")
    @ApiModelProperty(value = "报检单类型")
    private String testType;

    @Column(name = "hole_method")
    @ApiModelProperty(value = "成孔方式")
    private String holeMethod;

    @Column(name = "concrete_strength")
    @ApiModelProperty(value = "混凝土强度等级")
    private String concreteStrength;

    @Column(name = "desigh_org")
    @ApiModelProperty(value = "设计单位id")
    private String desighOrg;

    @Column(name = "desigh_org_name")
    @ApiModelProperty(value = "设计单位名称")
    private String desighOrgName;

    @Column(name = "inspection_org")
    @ApiModelProperty(value = "检测单位id")
    private String inspectionOrg;

    @Column(name = "inspection_org_name")
    @ApiModelProperty(value = "检测单位名称")
    private String inspectionOrgName;

    @Column(name = "buildtype")
    @ApiModelProperty(value = "buildType")
    private String buildtype;

    @Column(name = "create_date")
    @ApiModelProperty(value = "createdate")
    private String createdate;

    @Column(name = "create_dateStr")
    @ApiModelProperty(value = "createdatestr")
    private String createdatestr;

    @Column(name = "zjbgjc")
    @ApiModelProperty(value = "中间报告检测")
    private String zjbgjc;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TkyTestInformation that = (TkyTestInformation) o;
        return Objects.equals(xmid, that.xmid) && Objects.equals(xmname, that.xmname) && Objects.equals(bdid, that.bdid) && Objects.equals(bdname, that.bdname) && Objects.equals(gzwid, that.gzwid) && Objects.equals(gzwname, that.gzwname) && Objects.equals(gdid, that.gdid) && Objects.equals(gdname, that.gdname) && Objects.equals(gcid, that.gcid) && Objects.equals(gcname, that.gcname) && Objects.equals(sgdwid, that.sgdwid) && Objects.equals(sgdwname, that.sgdwname) && Objects.equals(jldwid, that.jldwid) && Objects.equals(jldwname, that.jldwname) && Objects.equals(jcdwid, that.jcdwid) && Objects.equals(jcdwname, that.jcdwname) && Objects.equals(jcff, that.jcff) && Objects.equals(syff, that.syff) && Objects.equals(lxr, that.lxr) && Objects.equals(lxdh, that.lxdh) && Objects.equals(byrq, that.byrq) && Objects.equals(jcsb, that.jcsb) && Objects.equals(jxrq, that.jxrq) && Objects.equals(jxzq, that.jxzq) && Objects.equals(sgfzr, that.sgfzr) && Objects.equals(jcqslcbm, that.jcqslcbm) && Objects.equals(jcqslc1, that.jcqslc1) && Objects.equals(jcqslc2, that.jcqslc2) && Objects.equals(jcjslcbm, that.jcjslcbm) && Objects.equals(jcjslc1, that.jcjslc1) && Objects.equals(jcjslc2, that.jcjslc2) && Objects.equals(jccd, that.jccd) && Objects.equals(cqsl, that.cqsl) && Objects.equals(jcbw, that.jcbw) && Objects.equals(jcfs, that.jcfs) && Objects.equals(jclx, that.jclx) && Objects.equals(zxbw, that.zxbw) && Objects.equals(sjhd, that.sjhd) && Objects.equals(tchd, that.tchd) && Objects.equals(sjqd, that.sjqd) && Objects.equals(mgbh, that.mgbh) && Objects.equals(mgzj, that.mgzj) && Objects.equals(mglx, that.mglx) && Objects.equals(mgsl, that.mgsl) && Objects.equals(wydj, that.wydj) && Objects.equals(sjmgcd, that.sjmgcd) && Objects.equals(sjmgkbl, that.sjmgkbl) && Objects.equals(jzrq, that.jzrq) && Objects.equals(shejmgcd, that.shejmgcd) && Objects.equals(shejmgkbl, that.shejmgkbl) && Objects.equals(lqqdbg, that.lqqdbg) && Objects.equals(syt, that.syt) && Objects.equals(zjbg, that.zjbg) && Objects.equals(zjbgjcyj, that.zjbgjcyj) && Objects.equals(zjbgjj, that.zjbgjj) && Objects.equals(zjbgjy, that.zjbgjy) && Objects.equals(jcsjfj, that.jcsjfj) && Objects.equals(jcsjfjscsj, that.jcsjfjscsj) && Objects.equals(sgdwfbzt, that.sgdwfbzt) && Objects.equals(lczt, that.lczt) && Objects.equals(tjr, that.tjr) && Objects.equals(tjbz, that.tjbz) && Objects.equals(tjsj, that.tjsj) && Objects.equals(spyj, that.spyj) && Objects.equals(spr, that.spr) && Objects.equals(spbz, that.spbz) && Objects.equals(spsj, that.spsj) && Objects.equals(bjfw, that.bjfw) && Objects.equals(bjsl, that.bjsl) && Objects.equals(bydbh, that.bydbh) && Objects.equals(jcfxsj, that.jcfxsj) && Objects.equals(jcfxr, that.jcfxr) && Objects.equals(jcyj, that.jcyj) && Objects.equals(jcr, that.jcr) && Objects.equals(jcbz, that.jcbz) && Objects.equals(jcsj, that.jcsj) && Objects.equals(jcjdzt, that.jcjdzt) && Objects.equals(jcjlbz, that.jcjlbz) && Objects.equals(jcjl, that.jcjl) && Objects.equals(jcjlfj, that.jcjlfj) && Objects.equals(fhjcjg, that.fhjcjg) && Objects.equals(fhspyj, that.fhspyj) && Objects.equals(fhspr, that.fhspr) && Objects.equals(fhspbz, that.fhspbz) && Objects.equals(fhspsj, that.fhspsj) && Objects.equals(zt, that.zt) && Objects.equals(fbsj, that.fbsj) && Objects.equals(fbr, that.fbr) && Objects.equals(testType, that.testType) && Objects.equals(holeMethod, that.holeMethod) && Objects.equals(concreteStrength, that.concreteStrength) && Objects.equals(desighOrg, that.desighOrg) && Objects.equals(desighOrgName, that.desighOrgName) && Objects.equals(inspectionOrg, that.inspectionOrg) && Objects.equals(inspectionOrgName, that.inspectionOrgName) && Objects.equals(buildtype, that.buildtype) && Objects.equals(createdate, that.createdate) && Objects.equals(createdatestr, that.createdatestr) && Objects.equals(zjbgjc, that.zjbgjc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xmid, xmname, bdid, bdname, gzwid, gzwname, gdid, gdname, gcid, gcname, sgdwid, sgdwname, jldwid, jldwname, jcdwid, jcdwname, jcff, syff, lxr, lxdh, byrq, jcsb, jxrq, jxzq, sgfzr, jcqslcbm, jcqslc1, jcqslc2, jcjslcbm, jcjslc1, jcjslc2, jccd, cqsl, jcbw, jcfs, jclx, zxbw, sjhd, tchd, sjqd, mgbh, mgzj, mglx, mgsl, wydj, sjmgcd, sjmgkbl, jzrq, shejmgcd, shejmgkbl, lqqdbg, syt, zjbg, zjbgjcyj, zjbgjj, zjbgjy, jcsjfj, jcsjfjscsj, sgdwfbzt, lczt, tjr, tjbz, tjsj, spyj, spr, spbz, spsj, bjfw, bjsl, bydbh, jcfxsj, jcfxr, jcyj, jcr, jcbz, jcsj, jcjdzt, jcjlbz, jcjl, jcjlfj, fhjcjg, fhspyj, fhspr, fhspbz, fhspsj, zt, fbsj, fbr, testType, holeMethod, concreteStrength, desighOrg, desighOrgName, inspectionOrg, inspectionOrgName, buildtype, createdate, createdatestr, zjbgjc);
    }

    public void copy(TkyTestInformation source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}