package com.android.trail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ZoomControls;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.UiSettings;

public class MainActivity extends AppCompatActivity {

    private MapView mMapView = null;
    //MapView 不能用 换为Textture
    private BaiduMap mBaiduMap =null;
    private UiSettings mUiSettings = null;
    //定位客户端
    private LocationClient mLocationClient=null;
    //定位监听器
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.trail_scenery);

        initBaiduMap();
        
    }


    //1.显示基本地图
    private void initBaiduMap(){
        // 获取地图控件引用

        mMapView = (MapView) findViewById(R.id.bmapView);
        //获取百度地图控制器
        mBaiduMap=mMapView.getMap();
        // 设置比例尺为 500M
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);
        // 获取地图 UI 控制器
        mUiSettings = mBaiduMap.getUiSettings();
        // 隐藏指南针
        mUiSettings.setCompassEnabled(true);


        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }
        // 设置标注覆盖物的监听
       // setMarkerListener();

    }
    
    
}
