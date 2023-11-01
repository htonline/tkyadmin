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
* @author Zuohaitao
* @date 2023-11-01
**/
@Data
public class PictureRadarSpectrumDto implements Serializable {

    /** 雷达图谱id */
    private Integer spectrumId;

    /** 文件存储路径 */
    private String fileUrl;

    /** 外键，关联tunel表 */
    private Integer tunnelId;

    /** 备注 */
    private String remark1;

    /** 备注 */
    private String remark2;

    /** 备注 */
    private String remark3;

    /** 备注 */
    private String remark4;

    /** 备注 */
    private String remark5;

    /** 备注 */
    private String remark6;

    /** 备注 */
    private String remark7;

    /** 备注 */
    private String remark8;

    /** 备注 */
    private String remark;
}