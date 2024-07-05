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
* @author Zuohaitao
* @date 2023-11-01
**/
@Data
public class PictureRadarSpectrumQueryCriteria{

    /** 精确 */
    @Query
    private Integer spectrumId;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String fileUrl;

    /** 精确 */
    @Query
    private String disNumber;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String remark1;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String remark2;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String remark3;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String remark4;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String remark5;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String remark6;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String remark7;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String remark8;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String remark;
}
