package com.android.trail.com.adroid.huizhao.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.trail.R;

import qiu.niorgai.StatusBarCompat;

public class JieShao4 extends AppCompatActivity {

    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jie_shao4);
        StatusBarCompat.setStatusBarColor(this, Color.BLUE,255);

        back = (Button)findViewById(R.id.jshao4_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent();
                back.setClass(JieShao4.this,JieShaoActivity.class);
                startActivity(back);
                finish();
            }
        });
    }
}
