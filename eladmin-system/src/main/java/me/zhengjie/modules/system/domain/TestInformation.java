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
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author LJL
* @date 2022-04-19
**/
@Entity
@Data
@Table(name="test_information")
public class TestInformation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_infor_id")
    @ApiModelProperty(value = "ID")
    private Long testInforId;

    @Column(name = "test_id")
    @ApiModelProperty(value = "报检号")
    private String testId;

    @Column(name = "test_time")
    @ApiModelProperty(value = "申请检测时间")
    private Timestamp testTime;

    @Column(name = "test_starting_distance")
    @ApiModelProperty(value = "待检区段起始里程")
    private String testStartingDistance;

    @Column(name = "test_ending_distance")
    @ApiModelProperty(value = "待检区段结束里程")
    private String testEndingDistance;

    @Column(name = "test_length")
    @ApiModelProperty(value = "待检区段长度")
    private String testLength;

    @Column(name = "wall_rock_type")
    @ApiModelProperty(value = "围岩类型")
    private String wallRockType;

    @Column(name = "support_tickness")
    @ApiModelProperty(value = "初支厚度")
    private String supportTickness;

    @Column(name = "separation_distance")
    @ApiModelProperty(value = "间距(m/榀)")
    private String separationDistance;

    @Column(name = "mesh_distance")
    @ApiModelProperty(value = "钢筋网间距")
    private String meshDistance;

    @Column(name = "annular_bar_distance")
    @ApiModelProperty(value = "环向钢筋间距")
    private String annularBarDistance;

    @Column(name = "reinfor_prt_tickness")
    @ApiModelProperty(value = "钢筋保护厚度")
    private String reinforPrtTickness;

    @Column(name = "sec_line_arch_tickness")
    @ApiModelProperty(value = "二次衬砌厚度-拱部")
    private String secLineArchTickness;

    @Column(name = "sec_line_wall_tickness")
    @ApiModelProperty(value = "二次衬砌厚度-边墙")
    private String secLineWallTickness;

    @Column(name = "sec_line_inver_arch_tickness")
    @ApiModelProperty(value = "二次衬砌厚度-仰拱")
    private String secLineInverArchTickness;

    @Column(name = "sec_line_filer_tickness")
    @ApiModelProperty(value = "二次衬砌厚度-填充")
    private String secLineFilerTickness;

    @Column(name = "project_name")
    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @Column(name = "section_name")
    @ApiModelProperty(value = "标段名称")
    private String sectionName;

    @Column(name = "tunnel_name")
    @ApiModelProperty(value = "隧道名称")
    private String tunnelName;

    @Column(name = "worksite_name")
    @ApiModelProperty(value = "工点名称")
    private String worksiteName;

    @Column(name = "statute")
    @ApiModelProperty(value = "状态")
    private String statute;

    @Column(name = "beizhu1")
    @ApiModelProperty(value = "beizhu1")
    private String beizhu1;

    @Column(name = "beizhu2")
    @ApiModelProperty(value = "beizhu2")
    private String beizhu2;

    @Column(name = "beizhu3")
    @ApiModelProperty(value = "beizhu3")
    private String beizhu3;

    @Column(name = "beizhu4")
    @ApiModelProperty(value = "beizhu4")
    private String beizhu4;

    @Column(name = "beizhu5")
    @ApiModelProperty(value = "beizhu5")
    private String beizhu5;

    @Column(name = "beizhu6")
    @ApiModelProperty(value = "beizhu6")
    private String beizhu6;

    @Column(name = "beizhu7")
    @ApiModelProperty(value = "beizhu7")
    private String beizhu7;

    @Column(name = "beizhu8")
    @ApiModelProperty(value = "beizhu8")
    private String beizhu8;

    @Column(name = "beizhu9")
    @ApiModelProperty(value = "beizhu9")
    private String beizhu9;

    @Column(name = "beizhu10")
    @ApiModelProperty(value = "beizhu10")
    private String beizhu10;

    @Column(name = "beizhu11")
    @ApiModelProperty(value = "beizhu11")
    private String beizhu11;

    @Column(name = "beizhu12")
    @ApiModelProperty(value = "beizhu12")
    private String beizhu12;

    @Column(name = "beizhu13")
    @ApiModelProperty(value = "beizhu13")
    private String beizhu13;

    @Column(name = "beizhu14")
    @ApiModelProperty(value = "beizhu14")
    private String beizhu14;

    @Column(name = "beizhu15")
    @ApiModelProperty(value = "beizhu15")
    private String beizhu15;

    public void copy(TestInformation source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}