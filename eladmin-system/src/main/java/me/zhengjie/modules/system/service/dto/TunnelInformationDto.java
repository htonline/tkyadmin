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
* @author LJL
* @date 2022-04-19
**/
@Data
public class TunnelInformationDto implements Serializable {

    /** ID */
    private Long tunnelInforId;

    /** 项目名称 */
    private String projectName;

    /** 标段名称 */
    private String sectionName;

    /** 隧道编号 */
    private String tunnelId;

    /** 隧道名称 */
    private String tunnelName;

    /** 工点名称 */
    private String worksiteName;

    /** 隧道起始里程 */
    private String tunnelStartingDistance;

    /** 隧道结束里程 */
    private String tunnelEndingDistance;

    /** 隧道长度 */
    private String tunnelLength;

    /** 建设单位 */
    private String repairCompany;

    /** 监理单位 */
    private String supervisionCompany;

    /** 检测单位 */
    private String detectionCompany;

    /** 施工单位 */
    private String constructionCompany;

    /** 用户ID */
    private Long userId;

    /** 用户名 */
    private String userName;

    /** 用户权限 */
    private Long deptId;

    /** 数据新增时间 */
    private Timestamp time;

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