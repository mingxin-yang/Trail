package com.android.trail.util;

import java.io.Serializable;

/**
 * Created by mingx_000 on 2016/12/21 0021.
 */
@SuppressWarnings("serial")
public class Detail implements Serializable {
    private String username;//昵称
    private String headphoto;//头像
    private String content;//发布内容
    private String images;//图片

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getHeadphoto() {
        return headphoto;
    }
    public void setHeadphoto(String headphoto) {
        this.headphoto = headphoto;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }
    public void setImages(String images) {
        this.images = images;
    }
}
