package com.android.trail.xizheng;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.android.trail.R;

import java.nio.channels.DatagramChannel;

/**
 * Created by Lenovo on 2016/11/25.
 */

public class PersonalActivity extends Activity {
    private ImageView imge1;

    private String[] mVals = new String[] { "苹果手机", "笔记本电脑", "电饭煲 ", "腊肉",
            "特产", "剃须刀", "宝宝", "康佳" , "腊肉",
            "特产", "剃须刀", "宝宝", "康佳"};
    private LayoutInflater mInflater;
    private FlowLayout mFlowLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalipd);

        mInflater = LayoutInflater.from(this);
        mFlowLayout = (FlowLayout) findViewById(R.id.id_flowlayout);
        initData();

        //获取屏幕比例
        WindowManager wm = (WindowManager)PersonalActivity.this.getSystemService(Context.WINDOW_SERVICE);
        int h = wm.getDefaultDisplay().getHeight();
        int w = wm.getDefaultDisplay().getWidth();

        imge1 = (ImageView)findViewById(R.id.t2);
        imge1.setY(h/4);

//        txtView = (TextView)findViewById(R.id.txttime);
//
//        timer.schedule(task, 1000, 1000);       // timeTask

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
    //修改以对话框出现，未完成点击昵称监听器注册及定义
    public void customList(View source){
        final TextView tv_nick = (TextView) findViewById(R.id.t3);
        final EditText et_nick = (EditText) findViewById(R.id.et_cgnick);
        TableLayout change1 = (TableLayout) getLayoutInflater().inflate(R.layout.datachange1,null);
        new AlertDialog.Builder(this)
                //设置对话框的标题
                .setTitle("昵称修改")
                //设置对话框显示的view对象
                .setView(change1)
                //为对话框设置保存按钮
                .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //此处可执行保存操作
                        tv_nick.setText(et_nick.getText());
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

//    TimerTask task = new TimerTask() {
//        @Override
//        public void run() {
//
//            runOnUiThread(new Runnable() {      // UI thread
//                @Override
//                public void run() {
//                    recLen--;
//                    txtView.setText(""+recLen);
//                    if(recLen < 0){
//                        timer.cancel();
//                        txtView.setVisibility(View.GONE);
//                    }
//                }
//            });
//        }
//    };

//    //获取屏幕的宽度
//    public static int getScreenWidth(Context context) {
//        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        Display display = manager.getDefaultDisplay();
//        return display.getWidth();
//    }
//
//    //获取屏幕的宽度
//    public static int getScreenHeight(Context context) {
//        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        Display display = manager.getDefaultDisplay();
//        return display.getHeight();
//    }

}
