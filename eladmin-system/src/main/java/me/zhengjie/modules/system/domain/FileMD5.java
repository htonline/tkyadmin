package me.zhengjie.modules.system.domain;

import me.zhengjie.base.BaseEntity;

import javax.persistence.Entity;
import java.io.Serializable;

public class FileMD5 extends BaseEntity implements Serializable {
    String filename;
    String md5;
    long len;

    public long getLen() {
        return len;
    }

    public void setLen(long len) {
        this.len = len;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Override
    public String toString() {
        return "FileMD5{" +
                "filename='" + filename + '\'' +
                ", md5='" + md5 + '\'' +
                ", len=" + len +
                '}';
    }
}
