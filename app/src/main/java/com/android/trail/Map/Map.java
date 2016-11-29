package com.android.trail.map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ZoomControls;

import com.android.trail.R;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;

/**
 * Created by mingx_000 on 2016/11/23 0023.
 */

public class Map extends Activity{
    MapView mMapView=null;
    BaiduMap mBaiduMap=null;
    private UiSettings mUiSettings = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.map);
        // 获取地图控件引用
        initBaiduMap();

    }

    private void initBaiduMap() {
        mMapView = (MapView) findViewById(R.id.amapView);
        mBaiduMap = mMapView.getMap();
        LatLng southwestLatLng = new LatLng(37.998882,114.519803);
        LatLng northeastLatLng = new LatLng(38.0069,114.535829);
        LatLngBounds latLngBounds = new LatLngBounds.Builder().include(southwestLatLng)
                .include(northeastLatLng).build();
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngBounds(latLngBounds);
        mBaiduMap.setMapStatus(u);
        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(17.0f);
        mBaiduMap.setMapStatus(msu);
        mUiSettings = mBaiduMap.getUiSettings();
        //显示指南针
        mUiSettings.setCompassEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause(); }
}
