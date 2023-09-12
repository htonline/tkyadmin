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
* @author Zuohaitao
* @date 2023-09-11
**/
@Data
public class RadarAcquisitionUploadDto implements Serializable {

    /** 文件id */
    private Integer id;

    /** 文件名 */
    private String fileName;

    /** 文件存储路径 */
    private String filePath;

    /** 创建时间 */
    private Timestamp creatTime;

    /** 更新时间 */
    private Timestamp updateTime;

    /** 上传者 */
    private String byUser;

    /** 备注1 */
    private String beizhu1;

    /** 备注2 */
    private String beizhu2;

    /** 备注3 */
    private String beizhu3;
}