package com.android.trail.hongxue;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.trail.R;
import com.android.trail.wangyang.LoadPicture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2016/12/22.
 */

public class discussAdapter extends BaseAdapter {
    private Bitmap bm;
    private LoadPicture lp;

    private Context context;
    private List<discussAdata> lbstop = new ArrayList<>();

    public discussAdapter(Context c, List<discussAdata> ls) {
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
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.bbsitem, null);
        }
        //车辆图片
        final ImageView BstopImage = (ImageView) convertView.findViewById(R.id.bbs_photo);
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

        new Thread() {
            public void run() {
                try {
                    bm = lp.getBitmap(lbstop.get(position).getHeadsculpture());
                    handler.sendEmptyMessage(0);
                } catch (IOException e) {
                    e.getMessage();
                }
            }
        }.start();

        //车辆名字
        TextView BstopName = (TextView) convertView.findViewById(R.id.bbsLiuyan);
        BstopName.setText(lbstop.get(position).getBulletin());
        //首班车时间
        TextView FirstTime = (TextView) convertView.findViewById(R.id.bbsText);
        FirstTime.setText(lbstop.get(position).getUsername());
        return convertView;
    }
}