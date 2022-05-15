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
* @date 2022-05-06
**/
@Data
public class TestInformationDto implements Serializable {

    /** ID */
    private Long testInforId;

    /** 报检号 */
    private String testId;

    /** 申请检测时间 */
    private Timestamp testTime;

    /** 待检区段起始里程 */
    private String testStartingDistance;

    /** 待检区段结束里程 */
    private String testEndingDistance;

    /** 待检区段长度 */
    private String testLength;

    /** 围岩类型 */
    private String wallRockType;

    /** 初支厚度 */
    private String supportTickness;

    /** 间距(m/榀) */
    private String separationDistance;

    /** 钢筋网间距 */
    private String meshDistance;

    /** 环向钢筋间距 */
    private String annularBarDistance;

    /** 钢筋保护厚度 */
    private String reinforPrtTickness;

    /** 二次衬砌厚度-拱部 */
    private String secLineArchTickness;

    /** 二次衬砌厚度-边墙 */
    private String secLineWallTickness;

    /** 二次衬砌厚度-仰拱 */
    private String secLineInverArchTickness;

    /** 二次衬砌厚度-填充 */
    private String secLineFilerTickness;

    /** 项目名称 */
    private String projectName;

    /** 标段名称 */
    private String sectionName;

    /** 隧道名称 */
    private String tunnelName;

    /** 工点名称 */
    private String worksiteName;

    /** 状态 */
    private String statute;

    /** 隧道编号 */
    private String beizhu1;

    /** 隧道起始里程 */
    private String beizhu2;

    /** 隧道结束里程 */
    private String beizhu3;

    /** 隧道长度 */
    private String beizhu4;

    /** 施工单位 */
    private String beizhu5;

    /** 联系人 */
    private String beizhu6;

    /** 联系方式 */
    private String beizhu7;

    /** 检测单位 */
    private String beizhu8;

    /** 监理单位 */
    private String beizhu9;

    /** 检测方式 */
    private String beizhu10;

    /** 检测方法 */
    private String beizhu11;

    /** 检测部位 */
    private String beizhu12;

    /** 浇筑时间 */
    private String beizhu13;

    private String beizhu14;

    private String beizhu15;

    /** 浇筑时间 */
    private String beizhu16;

    private String beizhu17;

    private String beizhu18;

    private String beizhu19;

    private String beizhu20;

    private String beizhu21;

    private String beizhu22;

    private String beizhu23;

    private String beizhu24;

    private String beizhu25;

    private String beizhu26;

    private String beizhu27;

    private String beizhu28;

    private String beizhu29;

    private String beizhu30;

    private String beizhu31;

    private String beizhu32;

    private String beizhu33;

    private String beizhu34;

    private String beizhu35;

    private String beizhu36;

    private String beizhu37;

    private String beizhu38;

    private String beizhu39;

    private String beizhu40;
}