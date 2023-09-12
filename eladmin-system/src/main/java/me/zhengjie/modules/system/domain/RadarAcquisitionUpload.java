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
package me.zhengjie.modules.system.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author Zuohaitao
* @date 2023-09-11
**/
@Entity
@Data
@Table(name="radar_acquisition_upload")
public class RadarAcquisitionUpload implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "文件id")
    private Integer id;

    @Column(name = "file_name")
    @ApiModelProperty(value = "文件名")
    private String fileName;

    @Column(name = "file_path")
    @ApiModelProperty(value = "文件存储路径")
    private String filePath;

    @Column(name = "creat_time")
    @CreationTimestamp
    @ApiModelProperty(value = "创建时间")
    private Timestamp creatTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    @Column(name = "by_user")
    @ApiModelProperty(value = "上传者")
    private String byUser;

    @Column(name = "beizhu1")
    @ApiModelProperty(value = "备注1")
    private String beizhu1;

    @Column(name = "beizhu2")
    @ApiModelProperty(value = "备注2")
    private String beizhu2;

    @Column(name = "beizhu3")
    @ApiModelProperty(value = "备注3")
    private String beizhu3;

    public void copy(RadarAcquisitionUpload source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}