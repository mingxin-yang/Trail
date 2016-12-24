package com.android.trail.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;

import com.android.trail.R;
import com.dalong.carrousellayout.CarrouselLayout;
import com.dalong.carrousellayout.CarrouselRotateDirection;
import com.dalong.carrousellayout.OnCarrouselItemClickListener;
import com.dalong.carrousellayout.OnCarrouselItemSelectedListener;

import java.io.ByteArrayOutputStream;

import qiu.niorgai.StatusBarCompat;

public class Scan extends AppCompatActivity {
    private final  String TAG=Scan.class.getSimpleName();
    private SeekBar mSeekBarR;
    private SeekBar mSeekBarX;
    private SeekBar mSeekBarZ;
    private CheckBox mCheckbox;
    private Switch mSwitchLeftright;
    private int width;
    private CarrouselLayout carrousel;
    private SeekBar mSeekBarTime;

    private Button share_goback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        //沉浸式状态栏
        StatusBarCompat.setStatusBarColor(this, Color.BLACK);
        initView();
        initLinstener();
    }

    private void initView() {
        carrousel= (CarrouselLayout) findViewById(R.id.carrousel);
        mSeekBarR = (SeekBar) findViewById(R.id.seekBarR);
        mSeekBarX = (SeekBar) findViewById(R.id.seekBarX);
        mSeekBarZ = (SeekBar) findViewById(R.id.seekBarZ);
        mSeekBarTime = (SeekBar) findViewById(R.id.seekBarTime);
        mCheckbox = (CheckBox) findViewById(R.id.checkbox);
        mSwitchLeftright = (Switch) findViewById(R.id.switchLeftright);
        mSeekBarX.setProgress(mSeekBarX.getMax() / 2);
        mSeekBarZ.setProgress(mSeekBarZ.getMax() / 2);

        share_goback = (Button)findViewById(R.id.share_first_back);
        share_goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Scan.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        width=dm.widthPixels;
        carrousel.setR(width/3)//设置R的大小
                .setAutoRotation(false)//是否自动切换
                .setAutoRotationTime(1500);//自动切换的时间  单位毫秒
    }


    @Override
    protected void onResume() {
        super.onResume();
        //开启自动
        carrousel.resumeAutoRotation();
    }


    @Override
    protected void onStop() {
        super.onStop();
        //停止自动
        carrousel.stopAutoRotation();
    }

    private void initLinstener() {
        carrousel.setOnCarrouselItemClickListener(new OnCarrouselItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                view.setDrawingCacheEnabled(true);
                Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache());
                view.setDrawingCacheEnabled(false);
                //Bitmap bmp = Bitmap.createBitmap(view.getWidth(),view.getHeight(),Bitmap.Config.ARGB_8888);
                Intent intent = new Intent();
                intent.setClass(Scan.this,Share.class);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG,100,baos);
                byte [] bitmapByte = baos.toByteArray();
                intent.putExtra("bitmap",bitmapByte);
                startActivity(intent);
            }

        });
        /**
         * 选中回调
         */
        carrousel.setOnCarrouselItemSelectedListener(new OnCarrouselItemSelectedListener() {

            @Override
            public void selected(View view, int position) {
                Log.v(TAG,"position:"+position);
            }
        });
        /**
         * 设置旋转事件间隔
         */
        mSeekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                long time=(long) (1.0f*progress/seekBar.getMax()*800);
                if(time<=100)time=100;
                carrousel.setAutoRotationTime(time);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        /**
         * 设置子半径R
         */
        mSeekBarR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float r=1.f*progress/seekBar.getMax()*width;
                carrousel.setR(r<=0?1:r);
                carrousel.refreshLayout();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        /**
         * X轴旋转
         */
        mSeekBarX.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                carrousel.setRotationX(progress - seekBar.getMax() / 2);
                carrousel.refreshLayout();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        /**
         * Z轴旋转
         */
        mSeekBarZ.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                carrousel.setRotationZ(progress - seekBar.getMax() / 2);
                carrousel.refreshLayout();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        /**
         * 设置是否自动旋转
         */
        mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                carrousel.setAutoRotation(isChecked);//启动LoopViewPager自动切换
            }
        });
        /**
         * 设置向左还是向右自动旋转
         */
        mSwitchLeftright.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                carrousel.setAutoScrollDirection(isChecked?CarrouselRotateDirection.anticlockwise
                        :CarrouselRotateDirection.clockwise);
            }
        });
    }
}
