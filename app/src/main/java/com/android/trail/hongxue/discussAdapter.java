package com.android.trail.hongxue;

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
import com.android.trail.wangyang.LoadPicture;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2016/12/22.
 */

public class discussAdapter extends BaseAdapter {
    private Bitmap bm;
 
    private LoadPicture lp;
 
    private Context context;
    private List<discussAdata> lbstop= new ArrayList<>();

    
    public discussAdapter(Context context, List<discussAdata> discussAdata) {
        this.context = context;
        this.lbstop = discussAdata;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        if (null == view){
            view = LayoutInflater.from(context).inflate(R.layout.bbsitem,null);
        }
        //车辆图片
        final ImageView bbsImage = (ImageView)view.findViewById(R.id.bbs_photo);
        TextView bbsText=(TextView)view.findViewById(R.id.bbsText);
        TextView bbLiuyan=(TextView)view.findViewById(R.id.bbsLiuyan);

        bbsText.setText(lbstop.get(position).getUsername());
        bbsText.setText(lbstop.get(position).getBulletin());

        final Handler handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case 0:
                        bbsImage.setImageBitmap(bm);
                        break;
                    default:
                        break;
                }
            }
        };

        new Thread(){
            public void run(){
                try{
                    bm = lp.getBitmap(lbstop.get(position).getHeadsculpture());
                    handler.sendEmptyMessage(0);
                }catch (IOException e){
                    e.getMessage();
                }
            }
        }.start();
        return view;
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
