package com.android.trail.map.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.trail.R;
import com.android.trail.map.shop;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mingx_000 on 2016/11/29 0029.
 */

public class MyAdapter extends BaseAdapter{
    private Context context;
    private List<shop> mshop=new ArrayList<>();
    private LayoutInflater layoutInflater;

    public MyAdapter(Context context, List<shop> mshop) {
        this.context = context;
        this.mshop = mshop;
        this.layoutInflater =LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mshop.size();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        shop s=null;
        if (view==null){
            s=new shop();
            view = layoutInflater.inflate(R.layout.map_shop_adapter, null);

            view.setTag(s);

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
        return i;
    }
}
