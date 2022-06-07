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
* @date 2022-06-05
**/
@Data
public class TkyDetectionInformationDto implements Serializable {

    /** 报验单编号 */
    private String bydbh;

    /** 用户账号 */
    private String account;

    /** 试验类型 */
    private String testType;

    /** 实际检测开始里程 */
    private String sjstartMile;

    /** 实际检测结束里程 */
    private String sjstopMile;

    /** 厚度数据 */
    private String appFileTypeRadar;

    /** 现场照片 */
    private String appFileTypePhoto;

    /** 备注1 */
    private String beizhu1;

    private String beizhu2;

    private String beizhu3;

    private String beizhu4;

    private String beizhu5;

    /** 检测数据id */
    private Integer id;
}