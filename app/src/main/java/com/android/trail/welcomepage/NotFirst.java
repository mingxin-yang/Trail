package com.android.trail.welcomepage;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.android.trail.R;
import com.android.trail.homepage.MainActivity;

import qiu.niorgai.StatusBarCompat;


public class NotFirst extends Activity {
    private SharedPreferences sharepreferences;     //实例化 SharedPreferences
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_first);
        StatusBarCompat.translucentStatusBar(this,false);
        sharepreferences=this.getSharedPreferences("check", MODE_PRIVATE);// 初始化 SharedPreferences 储存
        editor=sharepreferences.edit();//将SharedPreferences 储存 可编辑化
        boolean fristload=sharepreferences.getBoolean("fristload", true);//从SharedPreferences中获取是否第一次启动   默认为true
        if(fristload){
            new Handler().postDelayed(f, 1000);// 1秒后关闭，并跳转到欢迎页
            editor.putBoolean("fristload", false);//第一次启动后，将firstload 置为false 以便以后直接进入主界面不再显示欢迎界面
            editor.commit();   //提交，执行操作
        }else{
            new Handler().postDelayed(s, 2000);  //2秒后关闭,并跳转到首页
        }


    }
    //首次进入跳转
    Runnable f = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(NotFirst.this, First.class);
            startActivity(intent);
            finish();
        }
    };
    //非首次进入跳转
    Runnable s = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent();
            intent.setClass(NotFirst.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    };
}
