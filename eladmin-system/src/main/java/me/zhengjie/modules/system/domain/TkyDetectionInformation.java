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

/**
* @website https://el-admin.vip
* @description /
* @author wuxiaoxuan
* @date 2022-06-05
**/
@Entity
@Data
@Table(name="tky_detection_information")
public class TkyDetectionInformation implements Serializable {

    @Column(name = "bydbh",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "报验单编号")
    private String bydbh;

    @Column(name = "account")
    @ApiModelProperty(value = "用户账号")
    private String account;

    @Column(name = "test_type")
    @ApiModelProperty(value = "试验类型")
    private String testType;

    @Column(name = "sjstart_mile")
    @ApiModelProperty(value = "实际检测开始里程")
    private String sjstartMile;

    @Column(name = "sjstop_mile")
    @ApiModelProperty(value = "实际检测结束里程")
    private String sjstopMile;

    @Column(name = "app_file_type_radar")
    @ApiModelProperty(value = "厚度数据")
    private String appFileTypeRadar;

    @Column(name = "app_file_type_photo")
    @ApiModelProperty(value = "现场照片")
    private String appFileTypePhoto;

    @Column(name = "beizhu1")
    @ApiModelProperty(value = "备注1")
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "检测数据id")
    private Integer id;

    public void copy(TkyDetectionInformation source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}