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
@Table(name="detection_information")
public class DetectionInformation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detection_id")
    @ApiModelProperty(value = "ID")
    private Long detectionId;

    @Column(name = "project_name")
    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @Column(name = "section_name")
    @ApiModelProperty(value = "标段名称")
    private String sectionName;

    @Column(name = "tunnel_id")
    @ApiModelProperty(value = "隧道编号")
    private String tunnelId;

    @Column(name = "tunnel_name")
    @ApiModelProperty(value = "隧道名称")
    private String tunnelName;

    @Column(name = "worksite_name")
    @ApiModelProperty(value = "工点名称")
    private String worksiteName;

    @Column(name = "detection_starting_distance")
    @ApiModelProperty(value = "检测起点")
    private String detectionStartingDistance;

    @Column(name = "detection_ending_distance")
    @ApiModelProperty(value = "检测终点")
    private String detectionEndingDistance;

    @Column(name = "detection_length")
    @ApiModelProperty(value = "检测长度")
    private String detectionLength;

    @Column(name = "time")
    @ApiModelProperty(value = "数据新增时间")
    private Timestamp time;

    @Column(name = "test_id")
    @ApiModelProperty(value = "报检号")
    private String testId;

    @Column(name = "detection_line_biaohao")
    @ApiModelProperty(value = "测线编号")
    private String detectionLineBiaohao;

    @Column(name = "detection_data")
    @ApiModelProperty(value = "检测数据-上传/下载")
    private String detectionData;

    @Column(name = "detection_photos")
    @ApiModelProperty(value = "现场照片-上传/下载")
    private String detectionPhotos;

    @Column(name = "radar_photos")
    @ApiModelProperty(value = "雷达图谱-上传/下载")
    private String radarPhotos;

    @Column(name = "detection_summary")
    @ApiModelProperty(value = "检测报告_上传/下载")
    private String detectionSummary;

    @Column(name = "others")
    @ApiModelProperty(value = "其他附件_上传/下载")
    private String others;

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

    public void copy(DetectionInformation source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}