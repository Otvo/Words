package com.example.entity;

import java.util.Date;

public class Users {
    private String userid;

    private String password;

    private Integer learnnum;

    private Integer reviewnum;

    private String name;

    private String pronunciation;

    private Boolean node;

    private Date time;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getLearnnum() {
        return learnnum;
    }

    public void setLearnnum(Integer learnnum) {
        this.learnnum = learnnum;
    }

    public Integer getReviewnum() {
        return reviewnum;
    }

    public void setReviewnum(Integer reviewnum) {
        this.reviewnum = reviewnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation == null ? null : pronunciation.trim();
    }

    public Boolean getNode() {
        return node;
    }

    public void setNode(Boolean node) {
        this.node = node;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}