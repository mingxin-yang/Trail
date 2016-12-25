package com.android.trail.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.trail.R;
import com.android.trail.adapter.MyAdapter;
import com.android.trail.util.GoodsRequestJson;

import com.android.trail.util.shop;
import com.android.trail.view.RefreshableView;

import java.util.ArrayList;
import java.util.List;

import qiu.niorgai.StatusBarCompat;

/**
 * Created by mingx_000 on 2016/12/5 0005.
 */

public class MapShow_list extends Activity{
    private RefreshableView refreshableView;
    private ListView lv;
    private MyAdapter myadapter;
    private List<shop> ls=new ArrayList<shop>();

    List<java.util.Map<String, String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_list);
        StatusBarCompat.setStatusBarColor(this, Color.BLUE,255);

        new Thread(networkTask).start();

        lv=(ListView)findViewById(R.id.listview_map);
        refreshableView = (RefreshableView) findViewById(R.id.refreshable_view);
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep(3000);
                    new Thread(networkTask).start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshableView.finishRefreshing();
            }
        }, 0);



    }
    Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.i("mylog", "请求结果为-->" + val);
            // UI界面的更新等相关操作
            myadapter = new MyAdapter(getBaseContext(),ls);
            lv.setAdapter(myadapter);

        }
    };

    /**
     * 网络操作相关的子线程
     */
    Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            // TODO
            //获取网络资源
            final String path = "http://192.168.168.101:8992/goods/json";
            Intent in=getIntent();
            final String shopname=in.getStringExtra("shopname");
            try {
                list = GoodsRequestJson.getJSONObject(path);
                if (ls.size() != 0) {
                    ls.clear();
                }
                for(int i = 0;i < list.size();i++){
                    if(shopname.equals(list.get(i).get("shopname"))){
                        ls.add(new shop(list.get(i).get("goodspicurl"),list.get(i).get("id"),list.get(i).get("goodsname"), list.get(i).get("charge")));
                    }
                }
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        Intent intent=new Intent(MapShow_list.this,MapGoodsShow.class);
                        intent.putExtra("goodsname",ls.get(i).getName());
                        intent.putExtra("shopname",shopname);
                        startActivity(intent);

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 在这里进行 http request.网络请求相关操
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value", "请求结果");
            msg.setData(data);
            handler.sendMessage(msg);
        }
    };
}
