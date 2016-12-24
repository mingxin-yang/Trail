package com.android.trail.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.trail.util.OnDoubleClickListener;
import com.android.trail.R;
import com.android.trail.view.FlowLayout;

import java.net.URL;

import qiu.niorgai.StatusBarCompat;


/**
 * Created by Lenovo on 2016/11/25.
 */

public class PersonalActivity extends Activity {
    private ImageView imge1;
    private Button pback;
    private LinearLayout ll_basicdata;

    private TextView tv_username;
    private TextView tv_realname;
    private TextView tv_birthday;
    private TextView tv_school;
    private TextView tv_love;
    private TextView tv_Enrollment;
    private TextView tv_qianming;
    private TextView tv_vchat;
    private TextView tv_qq;
    private TextView tv_sina;

    private String urlPath;
    private URL url = null;

    private String[] mVals = new String[] { "苹果手机", "笔记本电脑", "电饭煲 ", "腊肉",
            "特产", "剃须刀", "宝宝", "康佳" , "腊肉",
            "特产", "剃须刀", "宝宝", "康佳"};
    private LayoutInflater mInflater;
    private FlowLayout mFlowLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalipd);


        //获取个性签名控件
        TextView qianming_tv = (TextView)findViewById(R.id.tv_qianming);
        //注册个性签名点击事件
        qianming_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //处理单击事件
                final TextView tv_qianming = (TextView) findViewById(R.id.tv_qianming);

                LinearLayout change3 = (LinearLayout) getLayoutInflater().inflate(R.layout.datachange3,null);
                final EditText et_cgqianming = (EditText) change3.findViewById(R.id.et_cgqianming);
                new AlertDialog.Builder(PersonalActivity.this)
                        //设置对话框的标题
                        .setTitle("个性签名修改")
                        //设置对话框显示的view对象
                        .setView(change3)
                        //为对话框设置保存按钮
                        .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //此处可执行保存操作
                                tv_qianming.setText(et_cgqianming.getText().toString());
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //此处可执行取消操作，不做任何事情
                            }
                        })
                        //创建并显示对话框
                        .create()
                        .show();
            }
        });


        //带数据跳转
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String username = intent.getStringExtra("username");
        String realname = intent.getStringExtra("realname");
        String birthday = intent.getStringExtra("birthday");
        String school = intent.getStringExtra("school");
        String love = intent.getStringExtra("gone");
        String vchat = intent.getStringExtra("vchat");
        String qianming = intent.getStringExtra("qianming");
        String enrollment = intent.getStringExtra("getdate");
        String gone = intent.getStringExtra("gone");
        String qq  = intent.getStringExtra("qq");
        String sina  = intent.getStringExtra("sina");
        String hobbies = intent.getStringExtra("hobbies");

        tv_username = (TextView)findViewById(R.id.t3);
        tv_realname = (TextView)findViewById(R.id.tv_realname);
        tv_birthday = (TextView)findViewById(R.id.tv_birthday);
        tv_school = (TextView)findViewById(R.id.tv_school);
        tv_Enrollment = (TextView)findViewById(R.id.tv_Enrollment);
        tv_love = (TextView)findViewById(R.id.tv_love);
        tv_qianming = (TextView)findViewById(R.id.tv_qianming);
        tv_vchat = (TextView)findViewById(R.id.tv_vchat);
        tv_qq = (TextView)findViewById(R.id.tv_qq);
        tv_sina = (TextView)findViewById(R.id.tv_sina);

        tv_username.setText(username);
        tv_realname.setText(realname);
        tv_birthday.setText(birthday);
        tv_school.setText(school);
        tv_love.setText(love);
        tv_qianming.setText(qianming);
        tv_Enrollment.setText(enrollment);
        tv_username.setText(username);
        tv_vchat.setText(vchat);
        tv_qq.setText(qq);
        tv_sina.setText(sina);

        ll_basicdata = (LinearLayout) findViewById(R.id.ll_basicdata);
        ll_basicdata.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String realname = tv_realname.getText().toString();
                String birthday = tv_birthday.getText().toString();
                String school = tv_school.getText().toString();
                String love = tv_love.getText().toString();
                String enrollment = tv_Enrollment.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("realname", realname);
                intent.putExtra("realname", birthday);
                intent.putExtra("school", school);
                intent.putExtra("love", love);
                intent.putExtra("enrollment", enrollment);
                intent.setClass(PersonalActivity.this, PersonalData2Activity.class);
                startActivity(intent);
            }
        });


        StatusBarCompat.setStatusBarColor(this, Color.BLUE,255);
        mInflater = LayoutInflater.from(this);
        mFlowLayout = (FlowLayout) findViewById(R.id.id_flowlayout);
        initData();

        //返回
        pback = (Button)findViewById(R.id.person_back);
        pback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pback = new Intent();
                pback.setClass(PersonalActivity.this, MainActivity.class);
                startActivity(pback);
            }
        });

        //获取屏幕比例
        WindowManager wm = (WindowManager)PersonalActivity.this.getSystemService(Context.WINDOW_SERVICE);
        int h = wm.getDefaultDisplay().getHeight();
        int w = wm.getDefaultDisplay().getWidth();

        imge1 = (ImageView)findViewById(R.id.t2);
        imge1.setY(h/4);

//        txtView = (TextView)findViewById(R.id.txttime);
//
//        timer.schedule(task, 1000, 1000);       // timeTask


        /*
        * 昵称修改
        * */
        //获取昵称控件
        TextView nick_tv = (TextView)findViewById(R.id.t3);
        //注册昵称点击事件监听器
        nick_tv.setOnTouchListener(new OnDoubleClickListener(new OnDoubleClickListener.DoubleClickCallback() {
            @Override
            public void onDoubleClick() {
                //处理双击事件
                final TextView tv_nick = (TextView) findViewById(R.id.t3);

                LinearLayout change1 = (LinearLayout) getLayoutInflater().inflate(R.layout.datachange1,null);
                final EditText et_nick = (EditText) change1.findViewById(R.id.et_cgnick);
                new AlertDialog.Builder(PersonalActivity.this)
                        //设置对话框的标题
                        .setTitle("昵称修改")
                        //设置对话框显示的view对象
                        .setView(change1)
                        //为对话框设置保存按钮
                        .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //此处可执行保存操作
                                tv_nick.setText(et_nick.getText().toString());
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //此处可执行取消操作，不做任何事情
                            }
                        })
                        //创建并显示对话框
                        .create()
                        .show();
            }
        }));
    }



    public void initData() {
        /**
         * 找到搜索标签的控件
         */
        for (int i = 0; i < mVals.length; i++) {
            TextView tv = (TextView) mInflater.inflate(
                    R.layout.search_label_tv, mFlowLayout, false);
            tv.setText(mVals[i]);
            final String str = tv.getText().toString();
            //点击事件
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            mFlowLayout.addView(tv);//添加到父View
        }
    }

}
