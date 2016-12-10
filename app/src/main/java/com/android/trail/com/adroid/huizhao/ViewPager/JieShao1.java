package com.android.trail.com.adroid.huizhao.ViewPager;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.android.trail.R;

import qiu.niorgai.StatusBarCompat;

/**
 * Created by Lenovo on 2016/12/1.
 */

public class JieShao1 extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jie_shao_1);
        StatusBarCompat.setStatusBarColor(this, Color.BLUE,255);
    }
}
