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

import com.alibaba.fastjson.annotation.JSONField;
import com.sun.jna.WString;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import me.zhengjie.base.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@Entity
public class ParamTunnel  {

    private Long id;
    private int tunnelNumner; //隧道数量
    private int tunnelLength;//隧道长度
    private int finishlength;//已施工长度
    private int baojiandanLength;//已报检长度
    private int yijianceLength;//已检测长度
    private int yichuliquexian; //已处理缺陷
    private int weichuliquexian;//未处理缺陷
    private double jiancelv;//检测率

    public ParamTunnel(int tunnelNumner, int tunnelLength, int finishlength, int baojiandanLength, int yijianceLength, int yichuliquexian, int weichuliquexian, double jiancelv) {
        this.tunnelNumner = tunnelNumner;
        this.tunnelLength = tunnelLength;
        this.finishlength = finishlength;
        this.baojiandanLength = baojiandanLength;
        this.yijianceLength = yijianceLength;
        this.yichuliquexian = yichuliquexian;
        this.weichuliquexian = weichuliquexian;
        this.jiancelv = jiancelv;
    }

    public ParamTunnel() {

    }

    public int getTunnelNumner() {
        return tunnelNumner;
    }

    public void setTunnelNumner(int tunnelNumner) {
        this.tunnelNumner = tunnelNumner;
    }

    public int getTunnelLength() {
        return tunnelLength;
    }

    public void setTunnelLength(int tunnelLength) {
        this.tunnelLength = tunnelLength;
    }

    public int getFinishlength() {
        return finishlength;
    }

    public void setFinishlength(int finishlength) {
        this.finishlength = finishlength;
    }

    public int getBaojiandanLength() {
        return baojiandanLength;
    }

    public void setBaojiandanLength(int baojiandanLength) {
        this.baojiandanLength = baojiandanLength;
    }

    public int getYijianceLength() {
        return yijianceLength;
    }

    public void setYijianceLength(int yijianceLength) {
        this.yijianceLength = yijianceLength;
    }

    public int getYichuliquexian() {
        return yichuliquexian;
    }

    public void setYichuliquexian(int yichuliquexian) {
        this.yichuliquexian = yichuliquexian;
    }

    public int getWeichuliquexian() {
        return weichuliquexian;
    }

    public void setWeichuliquexian(int weichuliquexian) {
        this.weichuliquexian = weichuliquexian;
    }

    public double getJiancelv() {
        return jiancelv;
    }

    public void setJiancelv(double jiancelv) {
        this.jiancelv = jiancelv;
    }




    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}