package com.android.trail.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.android.trail.R;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;

import qiu.niorgai.StatusBarCompat;

/**
 * Created by mingx_000 on 2016/11/23 0023.
 */

public class Map extends Activity{
    private TextureMapView mMapView=null;
    private BaiduMap mBaiduMap=null;
    private UiSettings mUiSettings = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.map);
        StatusBarCompat.translucentStatusBar(this,false);
        // 获取地图控件引用
        initBaiduMap();
        addMarkerOverlay();
        addElseMarker();

        ImageView imageView=(ImageView)findViewById(R.id.rate_img);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Map.this,Rounting.class);
                startActivity(intent);
            }
        });

    }

    private void initBaiduMap() {
        mMapView = (TextureMapView) findViewById(R.id.amapView);
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
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(19.0f);
        mBaiduMap.setMapStatus(msu);
        mUiSettings = mBaiduMap.getUiSettings();

        mUiSettings.setCompassEnabled(true);
        // 设置标注覆盖物的监听
        setMarkerListener();
    }

    /**
     * 设置标注覆盖物监听
     */
    private void setMarkerListener() {
        // 调用BaiduMap对象的setOnMarkerClickListener方法
        // 设置marker点击事件的监听
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker) {
                // 点击处理
                int value = marker.getExtraInfo().getInt("short");
                switch (value){
                    case 1:
                        Toast.makeText(Map.this,
                                marker.getTitle(),
                                Toast.LENGTH_SHORT)
                                .show();
                        Intent intent=new Intent(Map.this,Map_list.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });

        // 调用BaiduMap对象的setOnMarkerDragListener方法
        // 设置marker拖拽事件的监听
        mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            private boolean bFirst = true;
            public void onMarkerDrag(Marker marker) {
                // 拖拽中
                if(bFirst) {
                    Toast.makeText(Map.this,
                            "拖拽中",
                            Toast.LENGTH_SHORT)
                            .show();
                    bFirst = false;
                }
            }
            public void onMarkerDragEnd(Marker marker) {
                // 拖拽结束
                Toast.makeText(Map.this,
                        "拖拽结束",
                        Toast.LENGTH_SHORT)
                        .show();
                bFirst = true;
            }
            public void onMarkerDragStart(Marker marker) {
                // 开始拖拽
                Toast.makeText(Map.this,
                        "开始拖拽",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    /**
     * 添加标注覆盖物
     */
    private void addMarkerOverlay() {
        // 定义Maker坐标点
        LatLng point = new LatLng(38.001797,114.524474);
        // 构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.marker);
        //使用bundle来标识不同marker
        Bundle bunshop = new Bundle();
        bunshop.putInt("short",1);
        // 构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .extraInfo(bunshop)   //设置marker标识
                .position(point)    // 设置marker的位置
                .draggable(true)    // 设置是否允许拖拽
                .title("商业区")       // 设置marker的title
                .icon(bitmap);      // 必须设置marker图标
        //在地图上添加Marker，并显示
        Marker marker = (Marker) mBaiduMap.addOverlay(option);
    }
    private void addElseMarker() {
        // 定义Maker坐标点
        LatLng point = new LatLng(38.002053,114.526945);
        // 构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.marker);
        //使用bundle来标识不同marker
        Bundle bunshop = new Bundle();
        bunshop.putInt("short",1);
        // 构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .extraInfo(bunshop)   //设置marker标识
                .position(point)    // 设置marker的位置
                .draggable(true)    // 设置是否允许拖拽
                .title("公教楼")       // 设置marker的title
                .icon(bitmap);      // 必须设置marker图标
        //在地图上添加Marker，并显示
        Marker marker = (Marker) mBaiduMap.addOverlay(option);
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
