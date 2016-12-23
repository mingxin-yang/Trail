package com.android.trail.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.trail.R;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.navisdk.adapter.BNOuterTTSPlayerCallback;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BaiduNaviManager;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by mingx_000 on 2016/12/15 0015.
 */

public class Navi extends Activity implements View.OnClickListener{

    /* 地图控件 */
    private TextureMapView mMapView = null;
    /* 地图实例 */
    private BaiduMap mBaiduMap;
    /* 定位的客户端 */

    private LocationClient mLocationClient;
    /* 定位的监听器 */
    public MyLocationListener mMyLocationListener;
    /* 当前定位的模式 */
    private MyLocationConfiguration.LocationMode mCurrentMode
            = MyLocationConfiguration.LocationMode.NORMAL;
    /* 是否是第一次定位 */
    private volatile boolean isFristLocation = true;

    /* 最新一次的经纬度 */
    private double mCurrentLantitude;
    private double mCurrentLongitude;

    private LatLng mDestLocationData;

    public static List<Activity> activityList = new LinkedList<Activity>();

    private static final String APP_FOLDER_NAME = "Trail";
    private String mSDCardPath = null;
    public static final String ROUTE_PLAN_NODE = "routePlanNode";

    Button mocknav;
    Button realnav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.map_navi);
        //初始化导航相关

        if (initDirs()) {
            initNavi();
        }
        // 初始化BaiduMap相关
        initBaiduMap();

        // 初始化百度定位客户端
        initMyLocation();

        mocknav=(Button)findViewById(R.id.mocknav);
        realnav=(Button)findViewById(R.id.realnav);

        mocknav.setOnClickListener(this);
        realnav.setOnClickListener(this);

        mBaiduMap.setOnMapLongClickListener(new BaiduMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng arg0) {
                Toast.makeText(Navi.this,"设置目的地成功",Toast.LENGTH_SHORT).show();
                mDestLocationData=arg0;
                addDestInfoOverlay(arg0);

            }
        });



    }




    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    String authinfo = null;

    /**
     * 内部TTS播报状态回传handler
     */
    private Handler ttsHandler = new Handler() {
        public void handleMessage(Message msg) {
            int type = msg.what;
            switch (type) {
                case BaiduNaviManager.TTSPlayMsgType.PLAY_START_MSG: {
                    showToastMsg("Handler : TTS play start");
                    break;
                }
                case BaiduNaviManager.TTSPlayMsgType.PLAY_END_MSG: {
                    showToastMsg("Handler : TTS play end");
                    break;
                }
                default :
                    break;
            }
        }
    };

    /**
     * 内部TTS播报状态回调接口
     */
    private BaiduNaviManager.TTSPlayStateListener ttsPlayStateListener = new BaiduNaviManager.TTSPlayStateListener() {

        @Override
        public void playEnd() {
//            showToastMsg("TTSPlayStateListener : TTS play end");
        }

        @Override
        public void playStart() {
//            showToastMsg("TTSPlayStateListener : TTS play start");
        }
    };

    public void showToastMsg(final String msg) {
        Navi.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(Navi.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initNavi() {

        BNOuterTTSPlayerCallback ttsCallback = null;

        BaiduNaviManager.getInstance().init(this, mSDCardPath, APP_FOLDER_NAME, new BaiduNaviManager.NaviInitListener() {
            @Override
            public void onAuthResult(int status, String msg) {
                if (0 == status) {
                    authinfo = "key校验成功!";
                } else {
                    authinfo = "key校验失败, " + msg;
                }
                Navi.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(Navi.this, authinfo, Toast.LENGTH_LONG).show();
                    }
                });
            }

            public void initSuccess() {
                Toast.makeText(Navi.this, "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
//                initSetting();
            }

            public void initStart() {
                Toast.makeText(Navi.this, "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
            }

            public void initFailed() {
                Toast.makeText(Navi.this, "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
            }


        },  null, ttsHandler, null);

    }

    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    private void addDestInfoOverlay(LatLng destInfo) {
        mBaiduMap.clear();
        OverlayOptions options=new MarkerOptions().position(destInfo)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_en))
                .zIndex(5);
        mBaiduMap.addOverlay(options);
    }


    /**
     * 初始化百度地图
     */
    private void initBaiduMap() {
        mMapView = (TextureMapView)findViewById(R.id.amapView);
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);
    }

    /**
     * 初始化定位相关代码
     */
    private void initMyLocation() {
        // 定位SDK初始化
        mLocationClient = new LocationClient(getApplicationContext());

        // 设置定位的相关配置
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);      // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);     // 自动定位间隔
        option.setIsNeedAddress(true);// 是否需要地址
        option.setIsNeedLocationPoiList(true);

        // 定位模式
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        // 根据配置信息对定位客户端进行设置
        mLocationClient.setLocOption(option);

        // 注册定位监听
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);

        // 设置定位图标
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.drawable.marker);
        MyLocationConfiguration config = new MyLocationConfiguration(
                mCurrentMode, true, mCurrentMarker);
        mBaiduMap.setMyLocationConfigeration(config);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mocknav:
                if (mDestLocationData==null){
                    Toast.makeText(Navi.this,"长按地图设置目标地点",Toast.LENGTH_SHORT).show();
                    return;
                }
                routeplanToNavi(false);
                break;
            case R.id.realnav:
                if (mDestLocationData==null){
                    Toast.makeText(Navi.this,"长按地图设置目标地点",Toast.LENGTH_SHORT).show();
                    return;
                }
                routeplanToNavi(true);
                break;
        }
    }

    /**
     * 实现实位回调监听
     */
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {

            // mapView 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;

            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();

            // 设置BaiduMap的定位数据
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



    /**
     * BaiduMap移动到我的位置
     */
    private void center2myLoc() {
        LatLng ll = new LatLng(mCurrentLantitude, mCurrentLongitude);
        // 设置当前定位位置为BaiduMap的中心点，并移动到定位位置
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
        mBaiduMap.animateMapStatus(u);
    }

    private void routeplanToNavi(boolean mock) {
        BNRoutePlanNode.CoordinateType coType=BNRoutePlanNode.CoordinateType.GCJ02;
        BNRoutePlanNode sNode = null;
        BNRoutePlanNode eNode = null;



        sNode = new BNRoutePlanNode(mCurrentLongitude, mCurrentLantitude, "我的地点", null, coType);
        eNode = new BNRoutePlanNode(mDestLocationData.longitude, mDestLocationData.latitude
                , "目标地点", null, coType);

        if (sNode != null && eNode != null) {
            List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
            list.add(sNode);
            list.add(eNode);
            BaiduNaviManager.getInstance().launchNavigator(this, list, 1, mock, new DemoRoutePlanListener(sNode));
        }
    }

    public class DemoRoutePlanListener implements BaiduNaviManager.RoutePlanListener {

        private BNRoutePlanNode mBNRoutePlanNode = null;

        public DemoRoutePlanListener(BNRoutePlanNode node) {
            mBNRoutePlanNode = node;
        }

        @Override
        public void onJumpToNavigator() {
			/*
			 * 设置途径点以及resetEndNode会回调该接口
			 */

            for (Activity ac : activityList) {

                if (ac.getClass().getName().endsWith("BNDemoGuideActivity")) {

                    return;
                }
            }
            Intent intent = new Intent(Navi.this, BNDemoGuideActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ROUTE_PLAN_NODE, (BNRoutePlanNode) mBNRoutePlanNode);
            intent.putExtras(bundle);
            startActivity(intent);

        }

        @Override
        public void onRoutePlanFailed() {
            // TODO Auto-generated method stub
            Toast.makeText(Navi.this, "算路失败", Toast.LENGTH_SHORT).show();
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
        mMapView.onPause();
    }
}
