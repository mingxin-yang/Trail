package com.android.trail.map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.trail.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import qiu.niorgai.StatusBarCompat;

/**
 * Created by mingx_000 on 2016/11/29 0029.
 */

public class Map_list extends Activity{
    private PullToRefreshListView mPullToRefreshListView;
    private ListView lv;
    private MyAdapter myadapter;
    private LinkedList<String> mListItems;
    private List<shop> ls=new ArrayList<shop>();
    private String[] data={"测试数据1","测试数据2","测试数据3","测试数据4",
            "测试数据5","测试数据6","测试数据7","测试数据8","测试数据9","测试数据10","测试数据11"};
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_list);
        StatusBarCompat.setStatusBarColor(this, Color.BLUE,255);
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview);
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                new GetDataTask().execute();
            }
        });
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        //5.得到listview对象，并设置adapter
        getData();
        mListItems = new LinkedList<String>();
        mListItems.addAll(Arrays.asList(data));
        myadapter=new MyAdapter(this,ls);
        lv=mPullToRefreshListView.getRefreshableView();
        lv.setAdapter(myadapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>=0){
                    Intent intent=new Intent(Map_list.this,MapShow_list.class);
                    startActivity(intent);
                }
            }
        });
    }
    private class GetDataTask extends AsyncTask<Void, Void, Void> {

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
            mListItems.addFirst("Added after refresh...");
            myadapter.notifyDataSetChanged();

            mPullToRefreshListView.onRefreshComplete();
            super.onPostExecute(result);
        }
    }

    public void getData(){
//        List<shop> ls=new ArrayList<shop>();
//        for (int i=0;i<10;i++){
//            shop s=new shop();
//            ls.add(s);
//        }
        for(int i=0;i<15;i++) {
            ls.add(new shop(String.valueOf(R.drawable.shop_list1), String.valueOf(0), "四大名著", "￥115 免运费 北京"));
        }
    }
}
