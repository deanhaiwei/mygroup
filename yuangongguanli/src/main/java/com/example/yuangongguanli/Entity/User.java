package com.example.yuangongguanli.Entity;

public class User {
    private int id;
    private String loginname;
    private String password;
    private String realname;
    private String emial;

    public User() {

    }

    public User(int id, String loginname, String password, String realname, String emial) {
        this.id = id;
        this.loginname = loginname;
        this.password = password;
        this.realname = realname;
        this.emial = emial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getEmial() {
        return emial;
    }

    public void setEmial(String emial) {
        this.emial = emial;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", loginname='" + loginname + '\'' +
                ", password='" + password + '\'' +
                ", realname='" + realname + '\'' +
                ", emial='" + emial + '\'' +
                '}';
    }
}
