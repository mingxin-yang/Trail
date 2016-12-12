package com.android.trail.wangyang;

/**
 * Created by Ccx on 2016/12/7.
 */

public class bstop {
    private String bstop_src;  //图片
    private int id;
    private  String bstop_name;
    private String first_time;   //首班车时间
    private String last_time;   //末班车时间
    private String price;    //车票价钱

    public bstop(){
        super();
    }

    public String getSrc() {
        return bstop_src;
    }

    public void setSrc(String src) {
        this.bstop_src = src;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBstop_name() {
        return bstop_name;
    }

    public void setBstop_name(String bstop_name) {
        this.bstop_name = bstop_name;
    }

    public String getFirst_time() {
        return first_time;
    }

    public void setFirst_time(String first_time) {
        this.first_time = first_time;
    }

    public String getLast_time() {
        return last_time;
    }

    public void setLast_time(String last_time) {
        this.last_time = last_time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public bstop(int id,String Bstop_src,String Bstop_name,String First_time,String Last_time,String Price){
        this.id = id;
        bstop_src = Bstop_src;
        bstop_name = Bstop_name;
        first_time = First_time;
        last_time = Last_time;
        price = Price;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
