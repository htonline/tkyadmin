package me.zhengjie.modules.system.verify;

public class LoginDataBean {
    private String timestamp;
    private String appname;
    private String password;
    private String account;

    public LoginDataBean() {
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAppname() {
        return this.appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
