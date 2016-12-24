package com.android.trail.util;

/**
 * Created by Lenovo on 2016/12/22.
 */

public class discussAdata {
    private int id;
    private String bulletin;
    private String username;
    private String headsculpture;

    public discussAdata(int id,String username, String headsculpture, String bulletin) {
        this.username = username;
        this.headsculpture = headsculpture;
        this.bulletin = bulletin;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBulletin() {
        return bulletin;
    }

    public void setBulletin(String bulletin) {
        this.bulletin = bulletin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeadsculpture() {
        return headsculpture;
    }

    public void setHeadsculpture(String headsculpture) {
        this.headsculpture = headsculpture;
    }
}
