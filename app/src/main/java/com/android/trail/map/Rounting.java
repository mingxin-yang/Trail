package com.android.trail.map;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.android.trail.R;
import com.baidu.platform.comapi.map.E;

import qiu.niorgai.StatusBarCompat;

/**
 * Created by mingx_000 on 2016/12/5 0005.
 */

public class Rounting extends Activity{
    Button mBtnWalk=null;//步行搜索
    EditText startCityText;
    EditText endCityText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_rounting_plan);
        StatusBarCompat.setStatusBarColor(this, Color.BLUE,255);
    }
}
