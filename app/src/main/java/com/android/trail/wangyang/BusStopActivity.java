package com.android.trail.wangyang;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.android.trail.R;
import com.android.trail.homepage.MainActivity;
import com.android.trail.json.BusRequestJson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import qiu.niorgai.StatusBarCompat;


public class BusStopActivity extends Activity {

    //返回
    private Button bstop_back,bstop_btn1,bstop_btn2,bstop_btn3;
    //下拉刷新
    private PullToRefreshListView bsopPullToRefreshListView;

    //自定义 adapter 从数据库获取数据
    private List<bstop> lb =new ArrayList<>();
    private BstopAdapter bstop_adapter;
    private ListView bstop_lv;

    private BusRequestJson brj;
    List<Map<String, String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stop);

        //监听
        bstop_back = (Button)findViewById(R.id.bstop_back);
        bstop_btn1 = (Button)findViewById(R.id.bstop_btn1);
        bstop_btn2 = (Button)findViewById(R.id.bstop_btn2);
        bstop_btn3 = (Button)findViewById(R.id.bstop_btn3);
        bstop_back.setOnClickListener(bstop_onclick);
        bstop_btn1.setOnClickListener(bstop_onclick);
        bstop_btn2.setOnClickListener(bstop_onclick);
        bstop_btn3.setOnClickListener(bstop_onclick);


        //下拉刷新
        bsopPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview_bstop);
        bsopPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String btsoplabel = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                new bstopGetDataTask().execute();
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(btsoplabel);
            }
        });
        getData();
        bstop_adapter = new BstopAdapter(this,lb);
        bstop_lv = bsopPullToRefreshListView.getRefreshableView();
        bstop_lv.setAdapter(bstop_adapter);
        bstop_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent bstop_btn_intent= new Intent();
                bstop_btn_intent.setClass(BusStopActivity.this, MoveList.class);
                startActivity(bstop_btn_intent);
            }
        });
    }

    private void getData(){
        final Handler handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case 0:
                        for(int i = 0;i < list.size();i++){
                            lb.add(new bstop(Integer.valueOf(list.get(i).get("id")).intValue(),list.get(i).get("busimage"),list.get(i).get("busnumber"), list.get(i).get("firsttime"),list.get(i).get("enftime"),list.get(i).get("charge")));
                        }
                        break;
                    default:
                        break;
                }
            }
        };
        //获取网络资源
        final String path = "http://10.7.88.7:8989/bus/json";//
        new Thread() {                           //创建子线程进行网络访问的操作
            public void run() {
                try {
                    list = brj.getJSONObject(path);
                    handler.sendEmptyMessage(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    public View.OnClickListener bstop_onclick =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent bstop_btn_intent= new Intent();
            bstop_btn_intent.setClass(BusStopActivity.this, MoveList.class);
            switch (v.getId()){
                case R.id.bstop_back:
                    Intent bstop_back_intent= new Intent();
                    bstop_back_intent.setClass(BusStopActivity.this, MainActivity.class);
                    startActivity(bstop_back_intent);
                    finish();
                    break;
                case R.id.bstop_btn1:
                    startActivity(bstop_btn_intent);
                    break;
                case R.id.bstop_btn2:
                    startActivity(bstop_btn_intent);
                    break;
                case R.id.bstop_btn3:;
                    startActivity(bstop_btn_intent);
                    break;
            }
        }
    };


    private class bstopGetDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            // TODO 自动生成的方法存根
            super.onPostExecute(result);
            getData();
            // 通知数据改变了
            bstop_adapter.notifyDataSetChanged();
            // 加载完成后停止刷新
            bsopPullToRefreshListView.onRefreshComplete();
        }
    }
}