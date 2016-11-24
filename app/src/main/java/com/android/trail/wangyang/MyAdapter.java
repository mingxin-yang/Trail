package com.android.trail.wangyang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.trail.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WANGYANG-PC on 2016/11/24.
 */

abstract public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<Bus> mbus = new ArrayList<>();

    public MyAdapter(Context c,List<Bus> ls) {
        context = c;
        mbus = ls;
    }
    @Override
    public int getCount() {
        return mbus.size();
    }

    @Override
    public Object getItem(int i) {
        return mbus.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (null == view){
            view = LayoutInflater.from(context).inflate(R.layout.bus_stop,null);
        }
        TextView journey = (TextView) view.findViewById(R.id.bus_journey);
        journey.setText(mbus.get(i).getOT());
        TextView Start = (TextView) view.findViewById(R.id.bus_start);
        Start.setText(mbus.get(i).getStart());
        TextView Last = (TextView) view.findViewById(R.id.bus_last);
        Start.setText(mbus.get(i).getLast());
        TextView Price = (TextView) view.findViewById(R.id.bus_price);
        Price.setText(mbus.get(i).getPrice());

        return view;
    }
}
