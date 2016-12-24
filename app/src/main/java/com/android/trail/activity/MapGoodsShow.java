package com.android.trail.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.trail.R;
import com.android.trail.util.GoodsRequestJson;
import com.android.trail.util.ShopevaluateRequestJson;
import com.android.trail.adapter.DetailListViewAdapter;
import com.android.trail.view.MyListView;
import com.android.trail.util.Detail;
import com.android.trail.util.LoadPicture;

import java.io.IOException;
import java.util.*;

import qiu.niorgai.StatusBarCompat;

import static android.media.CamcorderProfile.get;


/**
 * Created by mingx_000 on 2016/12/5 0005.
 */

public class MapGoodsShow extends Activity {
    MyListView listView=null;
    List<java.util.Map<String, String>> list;
    private Bitmap bm;
    private LoadPicture lp;
    List<Detail> detailList;
    private DetailListViewAdapter listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_shop_show);
        StatusBarCompat.setStatusBarColor(this, Color.BLUE,255);
        ScrollView mScrollView=(ScrollView)findViewById(R.id.scroll);
        mScrollView.smoothScrollTo(0,20);

        listView=(MyListView)findViewById(R.id.list);
        detailList=new ArrayList<Detail>();

        new Thread(networkTask).start();
        new Thread(networkTask2).start();
        new Thread(networkTask3).start();
//        String img="http://avatar.csdn.net/3/B/9/1_baiyuliang2013.jpg#http://avatar.csdn.net/3/B/9/1_baiyuliang2013.jpg";
//        for (int i=0;i<10;i++) {
//            Detail detail = new Detail();
//            detail.setHeadphoto("http://img.jsqq.net/uploads/allimg/150228/1_150228191103_1.jpg");
//            detail.setUsername("jjj");
//            detail.setContent("jsdiofjiwoejfiowejfiowjfioj");
//            detail.setImages(img);
//            detailList.add(detail);
//        }
//        listViewAdapter=new DetailListViewAdapter(this,detailList);
//        listView.setAdapter(listViewAdapter);

        //图片获取
        final ImageView Image = (ImageView)findViewById(R.id.imageView);
        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        Image.setImageBitmap(bm);
                        break;
                    default:
                        break;
                }
            }
        };

        new Thread(){
            public void run(){
                final String path = "http://10.7.88.94:8992/goods/json";
                Intent in=getIntent();
                String goodsname=in.getStringExtra("goodsname");
                try{
                    list = GoodsRequestJson.getJSONObject(path);
                    int i;
                    for(i=0;i < list.size();i++) {
                        if (goodsname.equals(list.get(i).get("goodsname"))) {
                            break;

                        }
                    }
                    final String picurl=list.get(i).get("goodspicurl");
                    bm = lp.getBitmap(picurl);
                    handler.sendEmptyMessage(0);
                }catch (IOException e){
                    e.getMessage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();








    }

    //Goods展示图片，介绍，价格
    Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.i("mylog", "请求结果为-->" + val);
            // UI界面的更新等相关操作


        }
    };

    /**
     * 网络操作相关的子线程
     */
    Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            //获取网络资源
            final String path = "http://10.7.88.94:8992/goods/json";
            Intent in=getIntent();
            String goodsname=in.getStringExtra("goodsname");
            try {
                list = GoodsRequestJson.getJSONObject(path);
                int i;
                for(i=0;i < list.size();i++) {
                    if (goodsname.equals(list.get(i).get("goodsname"))) {
                        String evalaute=list.get(i).get("evaluate");
                        TextView textView2=(TextView)findViewById(R.id.textView2);

                        textView2.setText(evalaute);

                    }
                }



            } catch (Exception e) {
                e.getMessage();
            }
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value", "");
            msg.setData(data);
            handler.sendMessage(msg);




        }
    };
    Handler handler3 =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.i("mylog", "请求结果为-->" + val);
            // UI界面的更新等相关操作


        }
    };

    /**
     * 网络操作相关的子线程
     */
    Runnable networkTask3 = new Runnable() {

        @Override
        public void run() {
            //获取网络资源
            final String path = "http://10.7.88.94:8992/goods/json";
            Intent in=getIntent();
            String goodsname=in.getStringExtra("goodsname");
            try {
                list = GoodsRequestJson.getJSONObject(path);
                int i;
                for(i=0;i < list.size();i++) {
                    if (goodsname.equals(list.get(i).get("goodsname"))) {
                        String charge=list.get(i).get("charge");
                        TextView charge2=(TextView)findViewById(R.id.charge1);

                        charge2.setText(charge);

                    }
                }



            } catch (Exception e) {
                e.getMessage();
            }
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value", "");
            msg.setData(data);
            handler3.sendMessage(msg);




        }
    };
    Handler handler2 =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.i("mylog", "请求结果为-->" + val);
            // UI界面的更新等相关操作
            listViewAdapter=new DetailListViewAdapter(MapGoodsShow.this,detailList);
            listView.setAdapter(listViewAdapter);

        }
    };

    /**
     * 网络操作相关的子线程
     */
    Runnable networkTask2 = new Runnable() {

        @Override
        public void run() {
            //获取网络资源
            final String path = "http://10.7.88.94:8992/shopevaluate/json";
            Intent in = getIntent();
            String goodsname = in.getStringExtra("goodsname");
            String shopname=in.getStringExtra("shopname");
            Detail detail=new Detail();
            try {
                list = ShopevaluateRequestJson.getJSONObject(path);
                detailList=new ArrayList<Detail>();
                int i;
                for (i = 0; i < list.size(); i++) {
                    if (goodsname.equals(list.get(i).get("goodsname"))&&shopname.equals(list.get(i).
                            get("shopname"))) {
                        String headsculpture=list.get(i).get("headsculpture");
                        String username=list.get(i).get("username");
                        String evaluate=list.get(i).get("evaluate");
                        String epic1=list.get(i).get("epic1");
                        String epic2=list.get(i).get("epic2");
                        String epic3=list.get(i).get("epic3");
                        String epic4=list.get(i).get("epic4");
                        String epic5=list.get(i).get("epic5");
                        String epic6=list.get(i).get("epic6");
                        String epic=epic1+"#"+epic2+"#"+epic3+"#"+epic4+"#"+epic5+"#"+epic6;
                        detail.setHeadphoto(headsculpture);
                        detail.setUsername(username);
                        detail.setContent(evaluate);
                        detail.setImages(epic);
                        detailList.add(detail);
                    }

                }
            } catch (Exception e) {
                e.getMessage();
            }
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value", "请求结果");
            msg.setData(data);
            handler2.sendMessage(msg);




        }
    };

}
