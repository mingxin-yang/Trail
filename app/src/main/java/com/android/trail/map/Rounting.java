package com.android.trail.map;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.android.trail.R;

import qiu.niorgai.StatusBarCompat;

/**
 * Created by mingx_000 on 2016/12/5 0005.
 */

public class Rounting extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_rounting_plan);
        StatusBarCompat.setStatusBarColor(this, Color.BLUE,255);
    }
}
