package me.zhengjie.modules.system.verify;

public class ResultCode {
    String code;
    String msg;
    String jwt;
    String expiredtime;
    String token;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getExpiredtime() {
        return expiredtime;
    }

    public void setExpiredtime(String expiredtime) {
        this.expiredtime = expiredtime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "ResultCode{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", jwt='" + jwt + '\'' +
                ", expiredtime='" + expiredtime + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
