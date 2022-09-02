package me.zhengjie.modules.system.verify;

import me.zhengjie.modules.system.domain.TkyTestInformation;

import java.io.Serializable;
import java.util.List;

public class TkyTestInfoBean implements Serializable {
    String code;
    List<TkyTestInformation> data;
    String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<TkyTestInformation> getData() {
        return data;
    }

    public void setData(List<TkyTestInformation> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
