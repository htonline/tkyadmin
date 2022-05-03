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
public class TestInformationQueryCriteria{

    /** 精确 */
    @Query
    private Long testInforId;

    /** 精确 */
    @Query
    private String testId;

    /** 精确 */
    @Query
    private Timestamp testTime;

    /** 精确 */
    @Query
    private String testStartingDistance;

    /** 精确 */
    @Query
    private String testEndingDistance;

    /** 精确 */
    @Query
    private String testLength;

    /** 精确 */
    @Query
    private String wallRockType;

    /** 精确 */
    @Query
    private String supportTickness;

    /** 精确 */
    @Query
    private String separationDistance;

    /** 精确 */
    @Query
    private String meshDistance;

    /** 精确 */
    @Query
    private String annularBarDistance;

    /** 精确 */
    @Query
    private String reinforPrtTickness;

    /** 精确 */
    @Query
    private String secLineArchTickness;

    /** 精确 */
    @Query
    private String secLineWallTickness;

    /** 精确 */
    @Query
    private String secLineInverArchTickness;

    /** 精确 */
    @Query
    private String secLineFilerTickness;

    /** 精确 */
    @Query
    private String projectName;

    /** 精确 */
    @Query
    private String sectionName;

    /** 精确 */
    @Query
    private String tunnelName;

    /** 精确 */
    @Query
    private String worksiteName;

    /** 精确 */
    @Query
    private String statute;
}