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
import java.util.List;
import me.zhengjie.annotation.Query;

/**
* @website https://el-admin.vip
* @author wuxiaoxuan
* @date 2022-06-05
**/
@Data
public class TkyDetectionInformationQueryCriteria{

    /** 精确 */
    @Query
    private String bydbh;

    /** 精确 */
    @Query
    private String account;

    /** 精确 */
    @Query
    private String testType;

    /** 精确 */
    @Query
    private String sjstartMile;

    /** 精确 */
    @Query
    private String sjstopMile;

    /** 精确 */
    @Query
    private String appFileTypeRadar;

    /** 精确 */
    @Query
    private String appFileTypePhoto;

    /** 精确 */
    @Query
    private String beizhu1;

    /** 精确 */
    @Query
    private Integer id;
}