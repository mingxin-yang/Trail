package com.android.trail.Map;

/**
 * Created by mingx_000 on 2016/11/29 0029.
 */

public class shop {
    private String src;
    private String Id;
    private String Name;
    private String Message;

    public shop() {
        super();
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public shop(String src, String id, String name, String message) {
        this.src = src;
        Id = id;
        Name = name;
        Message = message;
    }

    @Override
    public String toString() {
        return "shop{" +
                "src='" + src + '\'' +
                ", Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                ", Message='" + Message + '\'' +
                '}';
    }
}
