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
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author zuohaitao
* @date 2023-11-01
**/
@Entity
@Data
@Table(name="tunnel")
public class Tunnel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tunnel_id")
    @ApiModelProperty(value = "隧道ID")
    private Integer tunnelId;

    @Column(name = "detect_location")
    @ApiModelProperty(value = "检测位置的名称")
    private String detectLocation;

    @Column(name = "detect_location_lng")
    @ApiModelProperty(value = "检测位置的经度(标红点的位置)")
    private String detectLocationLng;

    @Column(name = "detect_location_lat")
    @ApiModelProperty(value = "检测位置的纬度(标红点的位置)")
    private String detectLocationLat;

    @Column(name = "province")
    @ApiModelProperty(value = "检测位置所属的省份")
    private String province;

    @Column(name = "city")
    @ApiModelProperty(value = "城市")
    private String city;

    @Column(name = "district")
    @ApiModelProperty(value = "区域")
    private String district;

    @Column(name = "disease_type")
    @ApiModelProperty(value = "隧道的病害类型")
    private String diseaseType;

    @Column(name = "disease_description")
    @ApiModelProperty(value = "病害描述")
    private String diseaseDescription;

    @Column(name = "repair_method")
    @ApiModelProperty(value = "病害的修复方法")
    private String repairMethod;

    @Column(name = "tunnel_state")
    @ApiModelProperty(value = "该隧道病害的修复状态（1：紧急；2：一般；3：正在修复；4：已修复）")
    private Integer tunnelState;

    @Column(name = "tunnel_start_point_lng")
    @ApiModelProperty(value = "隧道起点的经度")
    private String tunnelStartPointLng;

    @Column(name = "tunnel_start_point_lat")
    @ApiModelProperty(value = "隧道起点的纬度")
    private String tunnelStartPointLat;

    @Column(name = "tunnel_stop_point_lng")
    @ApiModelProperty(value = "隧道终点的经度")
    private String tunnelStopPointLng;

    @Column(name = "tunnel_stop_point_lat")
    @ApiModelProperty(value = "隧道终点的纬度")
    private String tunnelStopPointLat;

    @Column(name = "create_time")
    @CreationTimestamp
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @Column(name = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    @Column(name = "remark1")
    @ApiModelProperty(value = "备注")
    private String remark1;

    @Column(name = "remark2")
    @ApiModelProperty(value = "备注")
    private String remark2;

    @Column(name = "remark3")
    @ApiModelProperty(value = "备注")
    private String remark3;

    @Column(name = "remark4")
    @ApiModelProperty(value = "备注")
    private String remark4;

    @Column(name = "remark5")
    @ApiModelProperty(value = "备注")
    private String remark5;

    @Column(name = "remark6")
    @ApiModelProperty(value = "备注")
    private String remark6;

    @Column(name = "remark7")
    @ApiModelProperty(value = "备注")
    private String remark7;

    @Column(name = "remark8")
    @ApiModelProperty(value = "备注")
    private String remark8;

    public void copy(Tunnel source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}