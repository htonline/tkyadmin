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
public class DetectionInformationDto implements Serializable {

    /** ID */
    private Long detectionId;

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

    /** 检测起点 */
    private String detectionStartingDistance;

    /** 检测终点 */
    private String detectionEndingDistance;

    /** 检测长度 */
    private String detectionLength;

    /** 数据新增时间 */
    private Timestamp time;

    /** 报检号 */
    private String testId;

    /** 测线编号 */
    private String detectionLineBiaohao;

    /** 检测数据-上传/下载 */
    private String detectionData;

    /** 现场照片-上传/下载 */
    private String detectionPhotos;

    /** 雷达图谱-上传/下载 */
    private String radarPhotos;

    /** 检测报告_上传/下载 */
    private String detectionSummary;

    /** 其他附件_上传/下载 */
    private String others;

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