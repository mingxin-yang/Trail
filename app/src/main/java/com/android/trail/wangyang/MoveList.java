package com.android.trail.wangyang;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.trail.R;

import qiu.niorgai.StatusBarCompat;

public class MoveList extends Activity {
    Button mlist_back;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.model);
        StatusBarCompat.setStatusBarColor(this, Color.BLUE,255);
        mlist_back = (Button)findViewById(R.id.mlist_back);
        mlist_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoveList.this, BusStopActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
