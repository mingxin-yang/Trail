package com.android.trail.xizheng;

/**
 * Created by Lenovo on 2016/12/17.
 */

public class Userdata {
    private int id;
    private String qq;
    private String vchat;
    private String passward;
    private String hobbies;
    private String school;
    private String getdate;
    private String headsculpture;
    private String username;
    private String realname;
    private String gone;

    public Userdata(int id, String qq, String vchat, String passward, String hobbies, String school, String getdate, String headsculpture, String username, String realname, String gone) {
        this.id = id;
        this.qq = qq;
        this.vchat = vchat;
        this.passward = passward;
        this.hobbies = hobbies;
        this.school = school;
        this.getdate = getdate;
        this.headsculpture = headsculpture;
        this.username = username;
        this.realname = realname;
        this.gone = gone;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getVchat() {
        return vchat;
    }

    public void setVchat(String vchat) {
        this.vchat = vchat;
    }

    public String getPassward() {
        return passward;
    }

    public void setPassward(String passward) {
        this.passward = passward;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getGetdate() {
        return getdate;
    }

    public void setGetdate(String getdate) {
        this.getdate = getdate;
    }

    public String getHeadsculpture() {
        return headsculpture;
    }

    public void setHeadsculpture(String headsculpture) {
        this.headsculpture = headsculpture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getGone() {
        return gone;
    }

    public void setGone(String gone) {
        this.gone = gone;
    }
}
