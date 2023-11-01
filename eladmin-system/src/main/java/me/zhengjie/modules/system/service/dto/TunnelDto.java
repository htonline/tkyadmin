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
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author zuohaitao
* @date 2023-11-01
**/
@Data
public class TunnelDto implements Serializable {

    /** 隧道ID */
    private Integer tunnelId;

    /** 检测位置的名称 */
    private String detectLocation;

    /** 检测位置的经度(标红点的位置) */
    private String detectLocationLng;

    /** 检测位置的纬度(标红点的位置) */
    private String detectLocationLat;

    /** 检测位置所属的省份 */
    private String province;

    /** 城市 */
    private String city;

    /** 区域 */
    private String district;

    /** 隧道的病害类型 */
    private String diseaseType;

    /** 病害描述 */
    private String diseaseDescription;

    /** 病害的修复方法 */
    private String repairMethod;

    /** 该隧道病害的修复状态（1：紧急；2：一般；3：正在修复；4：已修复） */
    private Integer tunnelState;

    /** 隧道起点的经度 */
    private String tunnelStartPointLng;

    /** 隧道起点的纬度 */
    private String tunnelStartPointLat;

    /** 隧道终点的经度 */
    private String tunnelStopPointLng;

    /** 隧道终点的纬度 */
    private String tunnelStopPointLat;

    /** 创建时间 */
    private Timestamp createTime;

    /** 备注 */
    private String remark;

    /** 备注 */
    private String remark1;

    /** 备注 */
    private String remark2;

    /** 备注 */
    private String remark3;

    /** 备注 */
    private String remark4;

    /** 备注 */
    private String remark5;

    /** 备注 */
    private String remark6;

    /** 备注 */
    private String remark7;

    /** 备注 */
    private String remark8;
}