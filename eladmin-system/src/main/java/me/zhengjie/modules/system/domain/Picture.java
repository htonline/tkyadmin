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
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author Zuohaitao
* @date 2023-11-01
**/
@Entity
@Data
@Table(name="picture")
public class Picture implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_id")
    @ApiModelProperty(value = "文件id")
    private Integer pictureId;

    @Column(name = "url")
    @ApiModelProperty(value = "文件路径")
    private String url;

    @Column(name = "dis_number")
    @ApiModelProperty(value = "文件对应的隧道id")
    private String disNumber;

    @Column(name = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    @Column(name = "remark1")
    @ApiModelProperty(value = "备注")
    private String remark1;

    @Column(name = "remark2")
    @ApiModelProperty(value = "备注")
    private String remark2;

    @Column(name = "remark3")
    @ApiModelProperty(value = "备注")
    private String remark3;

    @Column(name = "remark4")
    @ApiModelProperty(value = "备注")
    private String remark4;

    @Column(name = "remark5")
    @ApiModelProperty(value = "备注")
    private String remark5;

    @Column(name = "remark6")
    @ApiModelProperty(value = "备注")
    private String remark6;

    @Column(name = "remark7")
    @ApiModelProperty(value = "备注")
    private String remark7;

    @Column(name = "remark8")
    @ApiModelProperty(value = "备注")
    private String remark8;

    public void copy(Picture source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
