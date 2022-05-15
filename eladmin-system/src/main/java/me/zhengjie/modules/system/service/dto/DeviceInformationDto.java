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
* @author LJL
* @date 2022-04-19
**/
@Data
public class DeviceInformationDto implements Serializable {

    /** ID */
    private Long deviceId;

    /** 设备类型 */
    private String deviceType;

    /** 设备型号 */
    private String deviceModel;

    /** 设备编号 */
    private String deviceBianhao;

    /** 设备照片 */
    private String devicePhotos;

    /** 设备证书 */
    private String deviceCertificate;

    private String beizhu1;

    private String beizhu2;

    private String beizhu3;

    private String beizhu4;

    private String beizhu5;

    private String beizhu6;

    private String beizhu7;

    private String beizhu8;

    private String beizhu9;

    private String beizhu10;

    private String beizhu11;

    private String beizhu12;

    private String beizhu13;

    private String beizhu14;

    private String beizhu15;
}