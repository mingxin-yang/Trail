package com.android.trail.zhenfeng;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.trail.R;
import com.android.trail.zhenfeng.fourTabViews.fouthFrag;
import com.android.trail.zhenfeng.fourTabViews.fristFrag;
import com.android.trail.zhenfeng.fourTabViews.secndFrag;

import qiu.niorgai.StatusBarCompat;

/**
 * Created by 20160316 on 2016/11/23.
 */

public class FJTabview extends AppCompatActivity {
    //记录标记时间
    private long exitTime = 0;
    //四个Fragment  ID
    private RelativeLayout r1;
    private RelativeLayout r2;

    private RelativeLayout r4;

    //四个Img图片 底部Tab
    private ImageView img1;
    private ImageView img2;

    private ImageView img4;

    //初始化fragment
    private fristFrag fragment1 = new fristFrag();
    private secndFrag fragment2 = new secndFrag();

    private fouthFrag fragment4 = new fouthFrag();
    //判断Fragment是否加载过
    private Fragment mTempFragment;
    private FragmentActivity oneTempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scency_frag);

        //开始界面第一个Fragment
        fragment1 = new fristFrag();
        mTempFragment = fragment1;
        switchFragment(fragment1);


        getRelativeID();
        setListenerID();

        //首页的图片切换
        img1.setImageResource(R.drawable.guide_tfaccount_k);
        StatusBarCompat.setStatusBarColor(this, Color.BLUE,255);

    }


    private void switchFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment != mTempFragment) {
            if (!fragment.isAdded()) {
                transaction.hide(mTempFragment).add(R.id.content, fragment).commit();
            } else {
                transaction.hide(mTempFragment).show(fragment).commit();
            }
            mTempFragment = fragment;
        }
        else {
            if(!fragment.isAdded()){
                transaction.add(R.id.content, fragment);
                transaction.commit();
            }
        }
    }
   /* private void switchFragmentActivity(FragmentActivity fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment != oneTempFragment) {
            if (!fragment.isAdded()) {
                transaction.hide(oneTempFragment).add(R.id.content, fragment).commit();
            } else {
                transaction.hide(oneTempFragment).show(fragment).commit();
            }
            oneTempFragment = fragment;
        }
        else {
            if(!fragment.isAdded()){
                transaction.add(R.id.content, fragment);
                transaction.commit();
            }
        }
    }*/


    /**
     * 获取ID和设置监听器
     * **/
    public void getRelativeID(){
        r1 = (RelativeLayout)findViewById(R.id.first_layout);
        r2 = (RelativeLayout)findViewById(R.id.second_layout);

        r4 = (RelativeLayout)findViewById(R.id.fourth_layout);

        img1 = (ImageView)findViewById(R.id.first_image);
        img2 = (ImageView)findViewById(R.id.second_image);

        img4 = (ImageView)findViewById(R.id.fourth_image);
    }
    public void setListenerID(){
        r2.setOnClickListener(mylistener);

        r4.setOnClickListener(mylistener);
        r1.setOnClickListener(mylistener);
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
*/

    //四个界面的切换选择ID
    View.OnClickListener mylistener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.first_layout:
                    img1.setImageResource(R.drawable.guide_tfaccount_k);
                    img2.setImageResource(R.drawable.guide_discover_g);

                    img4.setImageResource(R.drawable.guide_account_g);
                    switchFragment(fragment1);
                    break;
                case R.id.second_layout:
                    img1.setImageResource(R.drawable.guide_tfaccount_g);
                    img2.setImageResource(R.drawable.guide_discover_k);

                    img4.setImageResource(R.drawable.guide_account_g);
                    switchFragment(fragment2);
                    break;
               /* case R.id.third_layout:
                    img1.setImageResource(R.drawable.guide_tfaccount_g);
                    img2.setImageResource(R.drawable.guide_discover_g);
                    img3.setImageResource(R.drawable.guide_cart_k);
                    img4.setImageResource(R.drawable.guide_account_g);
                    switchFragment(fragment3);
                    break;*/
                case R.id.fourth_layout:
                    img1.setImageResource(R.drawable.guide_tfaccount_g);
                    img2.setImageResource(R.drawable.guide_discover_g);

                    img4.setImageResource(R.drawable.guide_account_k);
                    switchFragment(fragment4);
                    break;
                default:
                    break;

            }
        }
    };

   /* //实现双击退出
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            // 判断是否在两秒之内连续点击返回键，是则退出，否则不退出
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                // 将系统当前的时间赋值给exitTime
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/

}
