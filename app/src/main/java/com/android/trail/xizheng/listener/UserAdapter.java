package com.android.trail.xizheng.listener;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.trail.wangyang.LoadPicture;
import com.android.trail.xizheng.Userdata;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2016/12/17.
 */

public class UserAdapter extends BaseAdapter {

    private Bitmap bm;
    private LoadPicture lp;

    private Context context;
    private List<Userdata> lbstop= new ArrayList<>();
    public UserAdapter(Context c,List<Userdata> ls)
    {
        context = c;
        lbstop = ls;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }
}
