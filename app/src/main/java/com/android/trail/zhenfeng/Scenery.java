package com.android.trail.zhenfeng;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.android.trail.R;
import com.android.trail.zhenfeng.fourTabViews.oneShare;
import com.android.trail.zhenfeng.share.scencyRate;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class Scenery extends AppCompatActivity {

    private TextureMapView mMapView = null;
    //MapView 不能用 换为Textture
    private BaiduMap mBaiduMap =null;
    private UiSettings mUiSettings = null;
    //定位客户端
    private LocationClient mLocationClient=null;
    //定位监听器
    //当前的定位的模式
    private MyLocationConfiguration.LocationMode mCurrentMode
            =MyLocationConfiguration.LocationMode.NORMAL;

    //是否第一次定位
    private volatile boolean isFristLocation =true;

    /* 定位的监听器*/
    public MyLocationListener mMyLocationListener = null;

    //最新一次经纬度
    private double mCurrentLantitude;
    private double mCurrentLongitude;
    /* 当前地图定位模式的Index */

    /* 地图定位的模式 */
    private String[] mStyles
            = new String[]{"地图模式【正常】",
            "地图模式【跟随】",
            "地图模式【罗盘】"};

    private int mCurrentStyle = 0;


    //弹出按钮实现
    private Context context = Scenery.this;
    private Handler mHandler = new Handler();
    private List<ImageView> list = new ArrayList<>();
    private Dialog dialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.trail_scenery);
    /*    //沉浸式状态栏
        StatusBarCompat.setStatusBarColor(this, Color.BLUE,255);*/
       // backg();
        initBaiduMap();
        initLocation();
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }




    //1.显示基本地图
    private void initBaiduMap(){
        // 获取地图控件引用

        mMapView = (TextureMapView) findViewById(R.id.bmapView);
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
        setMarkerListener();

    }


    private void initLocation(){
        mLocationClient = new LocationClient(getApplicationContext());

        LocationClientOption option =new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);
        option.setIsNeedLocationPoiList(true);

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        mLocationClient.setLocOption(option);
        // 注册定位监听
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);

        // 设置定位图标
        // BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
        // .fromResource(R.drawable.location);
        MyLocationConfiguration config = new MyLocationConfiguration(
                mCurrentMode, true, null);
        mBaiduMap.setMyLocationConfigeration(config);


    }
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            if (location ==null|| mMapView ==null)
                return;
            MyLocationData locData =new MyLocationData.Builder()
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            //构造定位数据

            mBaiduMap.setMyLocationData(locData);
            // 记录位置信息
            mCurrentLantitude = location.getLatitude();
            mCurrentLongitude = location.getLongitude();

            // 第一次定位时，将地图位置移动到当前位置
            if (isFristLocation) {
                isFristLocation = false;
                center2myLoc();
            }

            // Log记录位置信息
            StringBuffer sb = new StringBuffer(256);
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\naddress : ");
            sb.append(location.getAddrStr());

            for(int i=0; i<location.getPoiList().size(); i++) {
                Poi p = location.getPoiList().get(i);
                sb.append("\nPoi NO.");
                sb.append(i);
                sb.append(" : ");
                sb.append(p.getId());
                sb.append("-");
                sb.append(p.getName());
                sb.append("-");
                sb.append(p.getRank());
            }

            Log.i("BaiduLocationInfo", sb.toString());



        }
    }
    private void center2myLoc() {
        LatLng ll = new LatLng(mCurrentLantitude, mCurrentLongitude);
        // 设置当前定位位置为BaiduMap的中心点，并移动到定位位置
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
        mBaiduMap.animateMapStatus(u);
    }
    private void setMarkerListener() {
        // 调用BaiduMap对象的setOnMarkerClickListener方法
        // 设置marker点击事件的监听
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker) {
                // 点击处理
                Toast.makeText(Scenery.this,
                        marker.getTitle(),
                        Toast.LENGTH_SHORT)
                        .show();
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
                    Toast.makeText(Scenery.this,
                            "拖拽中",
                            Toast.LENGTH_SHORT)
                            .show();
                    bFirst = false;
                }
            }
            public void onMarkerDragEnd(Marker marker) {
                // 拖拽结束
                Toast.makeText(Scenery.this,
                        "拖拽结束",
                        Toast.LENGTH_SHORT)
                        .show();
                bFirst = true;
            }
            public void onMarkerDragStart(Marker marker) {
                // 开始拖拽
                Toast.makeText(Scenery.this,
                        "开始拖拽",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    //弹出按钮
    private void showDialog() {
        dialog = new Dialog(context, R.style.my_dialog_style);
        dialog.setCancelable(true);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog, null);
        dialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        dialog.show();
        final ImageView img01 = (ImageView) view.findViewById(R.id.scency_tpy);
        final ImageView img02 = (ImageView) view.findViewById(R.id.scency_fxy);
        final ImageView img03 = (ImageView) view.findViewById(R.id.scency_gjy);
        // 这几个show和close的操作千万不要用集合循环去操作，否则在显示dialog时会出现dialog闪一下就消失的情况
        showAnim(img01, 150);
        showAnim(img02, 200);
        showAnim(img03, 250);
        Button button = (Button) view.findViewById(R.id.btn_dialog);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAnim(img03, 100, 380);
                closeAnim(img02, 150, 430);
                closeAnim(img01, 200, 480);
            }
        });

//设置三个按钮

        img01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent();
                i.setClass(Scenery.this,oneShare.class);

                startActivity(i);

            }
        });

        img02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent();
                i.setClass(Scenery.this,scencyRate.class);
                startActivity(i);

            }
        });

    }

    private void showAnim(final ImageView i, int d) {
        i.setVisibility(View.INVISIBLE);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                i.setVisibility(View.VISIBLE);
                ValueAnimator fadeAnim = ObjectAnimator.ofFloat(i, "translationY", 1000, 0);
                fadeAnim.setDuration(600);
                KickBackAnimator kickAnimator = new KickBackAnimator();
                kickAnimator.setDuration(600);
                fadeAnim.setEvaluator(kickAnimator);
                fadeAnim.start();
                fadeAnim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {}
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        i.clearAnimation();
                    }
                    @Override
                    public void onAnimationCancel(Animator animation) {}
                    @Override
                    public void onAnimationRepeat(Animator animation) {}
                });
            }
        },  d);
    }

    private void closeAnim(final ImageView img, int i, int j) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ValueAnimator fadeAnim = ObjectAnimator.ofFloat(img, "translationY", 0, 1000);
                fadeAnim.setDuration(600);
                KickBackAnimator kickAnimator = new KickBackAnimator();
                kickAnimator.setDuration(600);
                fadeAnim.setEvaluator(kickAnimator);
                fadeAnim.start();
                fadeAnim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        // TODO Auto-generated method stub
                    }
                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        // TODO Auto-generated method stub
                    }
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        img.setVisibility(View.INVISIBLE);
                        img.clearAnimation();
                    }
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        // TODO Auto-generated method stub
                    }
                });
            }
        }, i);
        if(img.getId() == R.id.scency_tpy){
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    dialog.dismiss();
                }
            }, j);
        }
    }




    @Override
    protected void onStart()
    {
        // 开启图层定位
        mBaiduMap.setMyLocationEnabled(true);

        if (!mLocationClient.isStarted())
        {
            mLocationClient.start();
        }

        super.onStart();
    }
    @Override
    protected void onStop()
    {
        // 关闭图层定位
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();

        super.onStop();
    }
    @Override
    protected void onDestroy() {
        // 移除监听
      //  mRadarSearchManager.removeNearbyInfoListener(mRadarSerchListener);
        // 释放资源
     //   mRadarSearchManager.destroy();
     //   mRadarSearchManager = null;
        super.onDestroy();
        // 在 activity 执行 onDestroy时执行mMapView.onDestroy() ，实现地图生命周期管理
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
        mMapView.onPause();
    }
}
