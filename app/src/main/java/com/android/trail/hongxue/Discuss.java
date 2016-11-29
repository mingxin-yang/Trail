package com.android.trail.hongxue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.trail.R;


/**
 * Created by Loktar on 2016/11/22.
 */
public class Discuss extends AppCompatActivity {
    private Button btn;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discuss);
        btn = (Button)findViewById(R.id.btn_discuss);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                startActivity(intent);
            }
        });
    }
}
