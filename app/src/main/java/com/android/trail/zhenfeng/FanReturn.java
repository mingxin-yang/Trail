package com.android.trail.zhenfeng;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.trail.R;

/**
 * Created by 20160316 on 2016/12/10.
 */

public class FanReturn extends AppCompatActivity{

    public Button fanhui;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title);

        fanhui = (Button)findViewById(R.id.fanhui);

        fanhui.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
