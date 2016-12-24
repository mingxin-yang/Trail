package com.android.trail.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.trail.R;

import qiu.niorgai.StatusBarCompat;

import static com.android.trail.R.string.latitude3;
import static com.android.trail.R.string.longitude3;

/**
 * Created by mingx_000 on 2016/12/5 0005.
 */

public class Rounting extends Activity implements View.OnClickListener{
    Button btn;
    Button btn_serch;
    Button walk1;
    Button walk2;
    Button walk3;
    Button rount;
    Button luxian_back;
    String longitude1;
    String latitude1;
    String longitude2;
    String latitude2;

    Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_rounting_plan);

        StatusBarCompat.setStatusBarColor(this, Color.BLUE,255);


        btn=(Button)findViewById(R.id.walk);
        walk1=(Button)findViewById(R.id.walk1);
        walk2=(Button)findViewById(R.id.walk2);
        walk3=(Button)findViewById(R.id.walk3);
        luxian_back=(Button)findViewById(R.id.luxian_back);

//        searchButtonProcess(rount);

        walk1.setOnClickListener(this);
        walk2.setOnClickListener(this);
        walk3.setOnClickListener(this);
        luxian_back.setOnClickListener(this);




        btn_serch= (Button) findViewById(R.id.btn_serch);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                longitude1 = getResources().getString(R.string.longitude1);
                latitude1 = getResources().getString(R.string.latitude1);
                longitude2 = getResources().getString(R.string.longitude2);
                latitude2 = getResources().getString(R.string.latitude2);
                i=new Intent(Rounting.this,CustomRouteActivity.class);
                i.putExtra("l1",longitude1);
                i.putExtra("l2",latitude1);
                i.putExtra("l3",longitude2);
                i.putExtra("l4",latitude2);
                startActivity(i);
            }
        });
        btn_serch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(Rounting.this,Navi.class);
                startActivity(in);
            }
        });
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.walk1:
                longitude1 = getResources().getString(longitude3);
                latitude1 = getResources().getString(latitude3);
                longitude2 = getResources().getString(R.string.longitude4);
                latitude2 = getResources().getString(R.string.latitude4);
                Intent i=new Intent(Rounting.this,CustomRouteActivity.class);
                i.putExtra("l1",longitude1);
                i.putExtra("l2",latitude1);
                i.putExtra("l3",longitude2);
                i.putExtra("l4",latitude2);
                startActivity(i);
                break;
            case R.id.walk2:
                longitude1 = getResources().getString(longitude3);
                latitude1 = getResources().getString(latitude3);
                longitude2 = getResources().getString(R.string.longitude5);
                latitude2 = getResources().getString(R.string.latitude5);
                Intent in1=new Intent(Rounting.this,CustomRouteActivity.class);
                in1.putExtra("l1",longitude1);
                in1.putExtra("l2",latitude1);
                in1.putExtra("l3",longitude2);
                in1.putExtra("l4",latitude2);

                startActivity(in1);
                break;
            case R.id.walk3:
                longitude1 = getResources().getString(longitude3);
                latitude1 = getResources().getString(R.string.latitude1);
                longitude2 = getResources().getString(R.string.longitude6);
                latitude2 = getResources().getString(R.string.latitude6);
                Intent in=new Intent(Rounting.this,CustomRouteActivity.class);
                in.putExtra("l1",longitude1);
                in.putExtra("l2",latitude1);
                in.putExtra("l3",longitude2);
                in.putExtra("l4",latitude2);
                startActivity(in);
                break;

        }
    }
}
