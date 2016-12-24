package com.android.trail.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;

import com.android.trail.R;
import com.android.trail.util.BitmapUtil;
import com.android.trail.view.DragImageView;

import java.io.InputStream;

import qiu.niorgai.StatusBarCompat;

/**
 * Created by Lenovo on 2016/12/1.
 */

public class JieShao2 extends Activity {

    private int window_width, window_height;// 控件宽度
    private DragImageView dragImageView;// 自定义控件
    private int state_height;// 状态栏的高度

    private ViewTreeObserver viewTreeObserver;

    private Button back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jie_shao_2);
        StatusBarCompat.setStatusBarColor(this, Color.BLUE,255);

        back = (Button)findViewById(R.id.jshao2_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent();
                back.setClass(JieShao2.this,JieShaoActivity.class);
                startActivity(back);
                finish();
            }
        });

/** 获取可見区域高度 **/
        WindowManager manager = getWindowManager();
        window_width = manager.getDefaultDisplay().getWidth();
        window_height = manager.getDefaultDisplay().getHeight();

        dragImageView = (DragImageView) findViewById(R.id.div_main);
        Bitmap bmp = BitmapUtil.ReadBitmapById(this, R.drawable.lishi_yange, window_width, window_height);
        // 设置图片
        dragImageView.setImageBitmap(bmp);
        dragImageView.setmActivity(this);//注入Activity.
        /** 测量状态栏高度 **/
        viewTreeObserver = dragImageView.getViewTreeObserver();
        viewTreeObserver
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        if (state_height == 0) {
                            // 获取状况栏高度
                            Rect frame = new Rect();
                            getWindow().getDecorView()
                                    .getWindowVisibleDisplayFrame(frame);
                            state_height = frame.top;
                            dragImageView.setScreen_H(window_height - state_height);
                            dragImageView.setScreen_W(window_width);
                        }

                    }
                });

    }

    /**
     * 读取本地资源的图片
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap ReadBitmapById(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

}