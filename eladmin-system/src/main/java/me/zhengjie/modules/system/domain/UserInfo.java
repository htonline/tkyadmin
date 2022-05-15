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
* @author LJL
* @date 2022-05-09
**/
@Entity
@Data
@Table(name="user_info")
public class UserInfo implements Serializable {

    @Id
    @Column(name = "user_id")
    @ApiModelProperty(value = "userId")
    private Long userId;

    @Column(name = "dept_id")
    @ApiModelProperty(value = "deptId")
    private Long deptId;

    @Column(name = "username")
    @ApiModelProperty(value = "username")
    private String username;

    @Column(name = "nick_name")
    @ApiModelProperty(value = "nickName")
    private String nickName;

    @Column(name = "dept_name")
    @ApiModelProperty(value = "deptName")
    private String deptName;

    @Column(name = "beizhu1")
    @ApiModelProperty(value = "beizhu1")
    private String beizhu1;

    @Column(name = "beizhu2")
    @ApiModelProperty(value = "beizhu2")
    private String beizhu2;

    @Column(name = "beizhu3")
    @ApiModelProperty(value = "beizhu3")
    private String beizhu3;

    @Column(name = "beizhu4")
    @ApiModelProperty(value = "beizhu4")
    private String beizhu4;

    @Column(name = "beizhu5")
    @ApiModelProperty(value = "beizhu5")
    private String beizhu5;

    @Column(name = "beizhu6")
    @ApiModelProperty(value = "beizhu6")
    private String beizhu6;

    @Column(name = "beizhu7")
    @ApiModelProperty(value = "beizhu7")
    private String beizhu7;

    public void copy(UserInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}