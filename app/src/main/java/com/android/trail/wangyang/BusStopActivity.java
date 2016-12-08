package com.android.trail.wangyang;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.android.trail.R;
import com.android.trail.homepage.MainActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;


public class BusStopActivity extends Activity {

    //返回
    private Button bstop_back,bstop_btn1,bstop_btn2,bstop_btn3;
    //下拉刷新
    private PullToRefreshListView bsopPullToRefreshListView;

    //自定义 adapter 从数据库获取数据
    private List<bstop> lb =new ArrayList<>();
    private BstopAdapter bstop_adapter;
    private ListView bstop_lv;
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
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(btsoplabel);
                new bstopGetDataTask().execute();
            }
        });

        getData();
        bstop_adapter = new BstopAdapter(this,lb);
        bstop_lv = bsopPullToRefreshListView.getRefreshableView();
        bstop_lv.setAdapter(bstop_adapter);
    }

    private void getData() {
        lb.add(new bstop(0L,"http://img.dongqiudi.com/uploads/avatar/2014/10/20/8MCTb0WBFG_thumb_1413805282863.jpg","ssss","1.00","12.00","1"));
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
            bsopPullToRefreshListView.onRefreshComplete();
            super.onPostExecute(result);
        }
    }
}