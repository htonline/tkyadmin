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
import java.util.List;
import me.zhengjie.annotation.Query;

/**
* @website https://el-admin.vip
* @author LJL
* @date 2022-04-19
**/
@Data
public class DefectInformationQueryCriteria{

    /** 精确 */
    @Query
    private Long defectId;

    /** 精确 */
    @Query
    private String projectName;

    /** 精确 */
    @Query
    private String sectionName;

    /** 精确 */
    @Query
    private String tunnelId;

    /** 精确 */
    @Query
    private String tunnelName;

    /** 精确 */
    @Query
    private String worksiteName;

    /** 精确 */
    @Query
    private Timestamp time;

    /** 精确 */
    @Query
    private String testId;

    /** 精确 */
    @Query
    private String detectionLineBiaohao;

    /** 精确 */
    @Query
    private String detectionData;

    /** 精确 */
    @Query
    private String detectionPhotos;

    /** 精确 */
    @Query
    private String radarPhotos;

    /** 精确 */
    @Query
    private String detectionSummary;

    /** 精确 */
    @Query
    private String eliminateDefects;

    /** 精确 */
    @Query
    private String others;
    /** 精确 */
    @Query
    private String beizhu1;//缺陷状态
}