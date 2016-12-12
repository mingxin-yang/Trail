package com.android.trail.map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;

import com.android.trail.R;
import com.android.trail.map.adapter.Adapter_Detail;

import java.util.*;
import java.util.Map;

import qiu.niorgai.StatusBarCompat;

import static com.baidu.location.d.j.S;

/**
 * Created by mingx_000 on 2016/12/5 0005.
 */

public class Detail extends Activity {
    private ListView listView=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_shop_show);
        StatusBarCompat.setStatusBarColor(this, Color.BLUE,255);
        ScrollView mScrollView=(ScrollView)findViewById(R.id.scroll);
        mScrollView.smoothScrollTo(0,20);


        listView=(ListView)findViewById(R.id.list);
        List<java.util.Map<String,Object>> list=getData();
        listView.setAdapter(new Adapter_Detail(this,list));

        Button btn=(Button)findViewById(R.id.showBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Detail.this,Rate.class);
                startActivity(intent);
            }
        });
    }
    public List<java.util.Map<String, Object>> getData(){
        List<java.util.Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        for (int i=0;i<10;i++){
            Map<String,Object> map=new HashMap<String, Object>();
            list.add(map);
        }
        return list;
    }
}
