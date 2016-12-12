package com.android.trail.map.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.trail.R;

import java.util.List;
import java.util.Map;

/**
 * Created by mingx_000 on 2016/12/5 0005.
 */

public class Adapter_Detail extends BaseAdapter {
    private Context context;
    private List<Map<String,Object>> data;
    private LayoutInflater layoutInflater;

    public Adapter_Detail(Context context, List<Map<String, Object>> data) {
        this.context = context;
        this.data = data;
        this.layoutInflater =LayoutInflater.from(context);
    }

    public final class Detail{
        public ImageView image;
        public TextView title;
        public TextView info;
        public LinearLayout images;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       Detail detail=null;
        if(view==null){
            detail=new Detail();
            view=layoutInflater.inflate(R.layout.map_detail_adapter,null);
            detail.image=(ImageView)view.findViewById(R.id.image);
            detail.title=(TextView)view.findViewById(R.id.title);
            detail.info=(TextView)view.findViewById(R.id.info);
            detail.images=(LinearLayout)view.findViewById(R.id.images);
            view.setTag(detail);
        }else {
            detail=(Detail)view.getTag();
        }
        return view;
    }
}
