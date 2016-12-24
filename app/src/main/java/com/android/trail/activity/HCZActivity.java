package com.android.trail.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.trail.R;

import qiu.niorgai.StatusBarCompat;

public class HCZActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_hcz );
        //沉浸式状态栏
        StatusBarCompat.setStatusBarColor(this, Color.BLACK);
        Button bstop_back = (Button) findViewById(R.id.bstop_back);
        bstop_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bstop_back_intent = new Intent();
                bstop_back_intent.setClass(HCZActivity.this, BusStopActivity.class);
                startActivity(bstop_back_intent);
                finish();
            }
        });
    }
}
