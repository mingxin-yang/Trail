package com.android.trail.wangyang;

/**
 * Created by WANGYANG-PC on 2016/11/24.
 */

public class Bus {
    private String OT;
    private String Start;
    private String Last;
    private int Price;

    public Bus (String ot,String start,String last ,int price){
        OT = ot;
        Start = start;
        Last = last;
        Price = price;
    }

    public String getOT() {
        return OT;
    }
    public void setOT(String ot)
    {
        OT = ot;
    }
    public String getStart() {
        return Start;
    }
    public void setStart(String start)
    {
        Start = start;
    }
    public String getLast() {
        return Last;
    }
    public void setLast(String last)
    {
        Last = last;
    }
    public int getPrice() {
        return Price;
    }
    public void setPrice(int price)
    {
        Price = price;
    }
}
