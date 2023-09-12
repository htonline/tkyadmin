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
* @author Zuohaitao
* @date 2023-09-11
**/
@Data
public class RadarAcquisitionUploadQueryCriteria{

    /** 精确 */
    @Query
    private Integer id;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String fileName;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String filePath;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private Timestamp creatTime;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private Timestamp updateTime;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String byUser;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String beizhu1;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String beizhu2;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String beizhu3;
}