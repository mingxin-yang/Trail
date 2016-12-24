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
import android.widget.Button;
import android.widget.ListView;

import com.android.trail.R;
import com.android.trail.adapter.BstopAdapter;
import com.android.trail.activity.MainActivity;
import com.android.trail.util.BusRequestJson;
import com.android.trail.view.RefreshableView;
import com.android.trail.util.bstop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import qiu.niorgai.StatusBarCompat;


public class BusStopActivity extends Activity {

    //返回
    private Button bstop_back, bstop_btn1, bstop_btn2, bstop_btn3;

    //自定义 adapter 从数据库获取数据
    private List<bstop> lb = new ArrayList<>();
    private BstopAdapter bstop_adapter;
    private ListView bstop_lv;

    List<Map<String, String>> list;

    private RefreshableView refreshableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stop);
        //沉浸式状态栏
        StatusBarCompat.setStatusBarColor(this, Color.BLACK);

        //监听
        bstop_back = (Button) findViewById(R.id.bstop_back);
        bstop_btn1 = (Button) findViewById(R.id.bstop_btn1);
        bstop_btn2 = (Button) findViewById(R.id.bstop_btn2);
        bstop_btn3 = (Button) findViewById(R.id.bstop_btn3);
        bstop_back.setOnClickListener(bstop_onclick);
        bstop_btn1.setOnClickListener(bstop_onclick);
        bstop_btn2.setOnClickListener(bstop_onclick);
        bstop_btn3.setOnClickListener(bstop_onclick);

        bstop_lv = (ListView) findViewById(R.id.listview_bstop);
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

        new Thread(networkTask).start();

        //item的点击事件
        bstop_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent bstop_btn_intent = new Intent();
                bstop_btn_intent.setClass(BusStopActivity.this, MoveList.class);
                startActivity(bstop_btn_intent);
            }
        });
    }


    public View.OnClickListener bstop_onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent bstop_btn_intent = new Intent();
            bstop_btn_intent.setClass(BusStopActivity.this, MoveList.class);
            switch (v.getId()) {
                case R.id.bstop_back:
                    Intent bstop_back_intent = new Intent();
                    bstop_back_intent.setClass(BusStopActivity.this, MainActivity.class);
                    startActivity(bstop_back_intent);
                    finish();
                    break;
                case R.id.bstop_btn1:
                    Intent bstop_back_intent1 = new Intent();
                    bstop_back_intent1.setClass(BusStopActivity.this,WDActivity.class);
                    startActivity(bstop_back_intent1);
                    break;
                case R.id.bstop_btn2:
                    Intent bstop_back_intent2 = new Intent();
                    bstop_back_intent2.setClass(BusStopActivity.this, XBActivity.class);
                    startActivity(bstop_back_intent2);
                    break;
                case R.id.bstop_btn3:
                    ;
                    Intent bstop_back_intent3 = new Intent();
                    bstop_back_intent3.setClass(BusStopActivity.this,HCZActivity.class);
                    startActivity(bstop_back_intent3);
                    break;
            }
        }
    };
    Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.i("mylog", "请求结果为-->" + val);
            // UI界面的更新等相关操作
            bstop_adapter = new BstopAdapter(getBaseContext(),lb);
            bstop_lv.setAdapter(bstop_adapter);
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
            final String path = "http://10.7.88.94:8992/bus/json";
            try {
                list = BusRequestJson.getJSONObject(path);
                if (lb.size() != 0) {
                    lb.clear();
                }
                for(int i = 0;i < list.size();i++){
                    lb.add(new bstop(Integer.valueOf(list.get(i).get("id")).intValue(),list.get(i).get("busimage"),list.get(i).get("busnumber"), list.get(i).get("firsttime"),list.get(i).get("enftime"),list.get(i).get("charge")));
                }
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