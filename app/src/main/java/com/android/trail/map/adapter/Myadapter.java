package com.android.trail.Map.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.trail.Map.shop;
import com.android.trail.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mingx_000 on 2016/11/29 0029.
 */

public class MyAdapter extends BaseAdapter{
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){

            view = LayoutInflater.from(context).inflate(R.layout.map_shop, null);

        }

        ImageView Ivsrc=(ImageView)view.findViewById(R.id.img);
        Ivsrc.setImageResource(Integer.parseInt(mshop.get(i).getSrc()));
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
}
