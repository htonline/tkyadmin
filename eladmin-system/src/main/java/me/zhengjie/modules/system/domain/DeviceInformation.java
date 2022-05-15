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
* @author LJL
* @date 2022-04-19
**/
@Entity
@Data
@Table(name="device_information")
public class DeviceInformation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    @ApiModelProperty(value = "ID")
    private Long deviceId;

    @Column(name = "device_type")
    @ApiModelProperty(value = "设备类型")
    private String deviceType;

    @Column(name = "device_model")
    @ApiModelProperty(value = "设备型号")
    private String deviceModel;

    @Column(name = "device_bianhao")
    @ApiModelProperty(value = "设备编号")
    private String deviceBianhao;

    @Column(name = "device_photos")
    @ApiModelProperty(value = "设备照片")
    private String devicePhotos;

    @Column(name = "device_certificate")
    @ApiModelProperty(value = "设备证书")
    private String deviceCertificate;

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

    public void copy(DeviceInformation source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}