package com.android.trail.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.trail.R;
import com.android.trail.adapter.discussAdapter;
import com.android.trail.util.discussAdata;
import com.android.trail.util.BBSRequestJson;
import com.android.trail.view.RefreshableView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import qiu.niorgai.StatusBarCompat;


/**
 * Created by Loktar on 2016/11/22.
 */
public class Discuss extends Activity {
    List<Map<String, String>> list;
    private List<discussAdata> lb = new ArrayList<>();
    private EditText DiscussEdt;
    private Button btn;
    private Button btn_put;
    private discussAdapter discussAdapter;
    private RefreshableView refreshableView;
    //声明ListView控件
    private ListView mListView;
    // 声明数组链表，其装载的类型是ListItem(封装了一个Drawable和一个String的类)
    private List<discussAdata> mList = new ArrayList<>();


    /**
     * Activity的入口方法
     */
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discuss);
        //沉浸式状态栏
        StatusBarCompat.setStatusBarColor(this, Color.BLACK);
        DiscussEdt = (EditText)findViewById(R.id.discuss_edt);
        btn_put = (Button)findViewById(R.id.btn_put);
        //下拉刷新
        refreshableView = (RefreshableView) findViewById(R.id.fresh_view);
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

        btn = (Button)findViewById(R.id.btn_discuss);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Discuss.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //获取listview对象
        mListView = (ListView) findViewById(R.id.discuss_list);

        btn_put.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.i("mylog", "请求结果为-->" + val);
            // UI界面的更新等相关操作
            discussAdapter = new discussAdapter(Discuss.this,lb);
            mListView.setAdapter(discussAdapter);
        }
    };

    /**
     * 网络操作相关的子线程
     */
    Runnable networkTask = new Runnable() {
        @Override
        public void run() {
            // TODO获取网络资源
            final String path = "http://192.168.168.101:8992/bbs/json";
            try {
                list = BBSRequestJson.getJSONObject(path);
                if (lb.size() != 0) {
                    lb.clear();
                }
                for(int i = 0;i < list.size();i++){
                    lb.add(new discussAdata(
                            Integer.valueOf(list.get(i).get("id")).intValue(),
                            list.get(i).get("username"),
                            list.get(i).get("headsculpture"),
                            list.get(i).get("bulletin")));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 在这里进行 http request.网络请求相关操
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value", "请求结果");
            msg.setData(data);
            handler.sendEmptyMessage(0);
        }
    };
}
