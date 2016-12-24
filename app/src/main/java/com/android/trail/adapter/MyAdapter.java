package com.android.trail.adapter;

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

import com.android.trail.util.shop;
import com.android.trail.util.LoadPicture;

/**
 * Created by mingx_000 on 2016/11/29 0029.
 */

public class MyAdapter extends BaseAdapter {
    private Bitmap bm;
    private LoadPicture lp;
    private Context context;
    private List<shop> mshop=new ArrayList<>();

    public MyAdapter(Context context, List<shop> mshop) {
        this.context = context;
        this.mshop = mshop;
    }

    @Override
    public int getCount() {
        return mshop.size();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view==null){

            view = LayoutInflater.from(context).inflate(R.layout.map_shop_adapter, null);

        }
        //图片获取
        final ImageView Image = (ImageView)view.findViewById(R.id.img);
        final Handler handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case 0:
                        Image.setImageBitmap(bm);
                        break;
                    default:
                        break;
                }
            }
        };

        new Thread(){
            public void run(){
                try{
                    bm = lp.getBitmap(mshop.get(i).getSrc());
                    handler.sendEmptyMessage(0);
                }catch (IOException e){
                    e.getMessage();
                }
            }
        }.start();

        TextView Tvname=(TextView)view.findViewById(R.id.name);
        Tvname.setText(mshop.get(i).getName());
        TextView Tvauther=(TextView)view.findViewById(R.id.message);
        Tvauther.setText(mshop.get(i).getMessage());
        return view;
    }

    @Override
    public Object getItem(int i) {
        return mshop.get(i);
    }

    @Override
    public long getItemId(int i) {
        return Long.parseLong(mshop.get(i).getId());
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

