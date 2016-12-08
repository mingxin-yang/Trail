package com.android.trail.wangyang;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.trail.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ccx on 2016/12/8.
 */

public class BstopAdapter extends BaseAdapter{
    private Bitmap bm;
    private LoadPicture lp;

    private Context context;
    private List<bstop> lbstop= new ArrayList<>();
    public BstopAdapter(Context c,List<bstop> ls)
    {
        context = c;
        lbstop = ls;
    }

    @Override
    public int getCount() {
        return lbstop.size();
    }

    @Override
    public Object getItem(int position) {
        return lbstop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lbstop.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (null == convertView){
            convertView = LayoutInflater.from(context).inflate(R.layout.item,null);
        }
        //车辆图片
        final ImageView BstopImage = (ImageView)convertView.findViewById(R.id.tv_photo);
        final Handler handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case 0:
                        BstopImage.setImageBitmap(bm);
                        break;
                    default:
                        break;
                }
            }
        };

        new Thread(){
            public void run(){
                try{
                    bm = lp.getBitmap(lbstop.get(position).getSrc());
                    handler.sendEmptyMessage(0);
                }catch (IOException e){
                    e.getMessage();
                }
            }
        }.start();

        //车辆名字
        TextView BstopName = (TextView) convertView.findViewById(R.id.tv_journey);
        BstopName.setText(lbstop.get(position).getBstop_name());
        //首班车时间
        TextView FirstTime = (TextView) convertView.findViewById(R.id.tv_begin);
        FirstTime.setText(lbstop.get(position).getFirst_time());
        //末班车时间
        TextView LastTime = (TextView)convertView.findViewById(R.id.tv_stop);
        LastTime.setText(lbstop.get(position).getLast_time());
        //票价
        TextView Prcie = (TextView)convertView.findViewById(R.id.tv_price);
        Prcie.setText(lbstop.get(position).getPrice());

        return convertView;
    }

    public Bitmap getBitmap(String url) {
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            int length = http.getContentLength();

            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

}
