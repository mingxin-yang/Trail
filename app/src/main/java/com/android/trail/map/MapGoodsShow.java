package com.android.trail.map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.trail.R;
import com.android.trail.json.GoodsRequestJson;
import com.android.trail.json.ShopevaluateRequestJson;
import com.android.trail.map.adapter.DetailListViewAdapter;
import com.android.trail.map.util.Detail;
import com.android.trail.wangyang.LoadPicture;

import java.io.IOException;
import java.util.*;
import java.util.Map;

import qiu.niorgai.StatusBarCompat;

import static android.media.CamcorderProfile.get;


/**
 * Created by mingx_000 on 2016/12/5 0005.
 */

public class MapGoodsShow extends Activity {
    ListView listView=null;
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

        listView=(ListView)findViewById(R.id.list);

        new Thread(networkTask).start();
        //图片获取
        final ImageView Image = (ImageView)findViewById(R.id.imageView);
        final Handler handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
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







        Button btn=(Button)findViewById(R.id.showBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MapGoodsShow.this,Rate.class);
                startActivity(intent);
            }
        });
    }

    //Goods展示图片，介绍，价格
    Handler handler =new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
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
    Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            // TODO
            //获取网络资源
            GoodsUpdate();
            DetailUpdate();
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value", "请求结果");
            msg.setData(data);
            handler.sendMessage(msg);


        }
    };
    //goods thread
    public void GoodsUpdate(){
        //获取网络资源
        final String path = "http://10.7.88.94:8992/goods/json";
        Intent in=getIntent();
        String goodsname=in.getStringExtra("goodsname");
        try {
            list = GoodsRequestJson.getJSONObject(path);
            int i;
            for(i=0;i < list.size();i++) {
                if (goodsname.equals(list.get(i).get("goodsname"))) {
                    break;

                }
            }
            String evalaute=list.get(i).get("evaluate");
            String charge=list.get(i).get("charge");
            TextView textView2=(TextView)findViewById(R.id.textView2);
            textView2.setText(evalaute);
            TextView charge2=(TextView)findViewById(R.id.charge);
            charge2.setText(charge);


        } catch (Exception e) {
            e.getMessage();
        }
    }
    //detail thread
    public void DetailUpdate() {
        final String path = "http://10.7.88.94:8992/shopvaluate/json";
        Intent in = getIntent();
        String goodsname = in.getStringExtra("goodsname");
        String shopname=in.getStringExtra("shopname");
        Detail detail=null;
        try {
            list = ShopevaluateRequestJson.getJSONObject(path);
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
    }
}
