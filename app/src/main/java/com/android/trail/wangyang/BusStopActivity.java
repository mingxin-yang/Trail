package com.android.trail.wangyang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.trail.R;


public class BusStopActivity extends Activity {
    private ListView Lv = null;
    private Button btn1,btn2,btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stop);

        Lv = (ListView) findViewById(R.id.Lv);

        final String[] journey = new String[] { "南焦客运站-->市二院","南焦客运站-->解放广场" ,
                "南焦客运站-->白佛客运站","南焦客运站-->北站","南焦客运站-->海世界",
                "南焦客运站-->大正驾校","南焦客运站-->园博园","程上·太阳城-->南三条",
                "大众驾校-->省图书馆","栾城公交枢纽站-->火车站(东广场)",
                "东尹村(CS基地)-->解放广场","东尹村(CS基地)-->火车站(西广场)"  };
        final String[] begin = new String[] {"05:30","06:00","06:00","06:00","06:00","06:00",
                "06:00","06:00","06:00","06:00","06:00","06:00"};
        final String[] stop = new String[]{"22:00","22:30","20:30","21:00","21:30","20:30",
                "20:00","20:30","20:30","20:00","20:30","21:00"};
        final String[] price = new String[]{"1元","1元","1元","1元","1元","1元","1元","1元",
                "1元","2元","1元","1元"};

        final int[] photo = new int[] { R.drawable.bus_30, R.drawable.bus_35, R.drawable.bus_65,
                R.drawable.bus_75, R.drawable.bus_79, R.drawable.bus_77, R.drawable.bus_177,
                R.drawable.bus_50, R.drawable.bus_69, R.drawable.bus_215, R.drawable.bus_53,
                R.drawable.bus_72};
        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BusStopActivity.this,WDActivity.class);
                startActivity(intent);

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BusStopActivity.this,BGActivity.class);
                startActivity(intent);

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BusStopActivity.this,HCZActivity.class);
                startActivity(intent);

            }
        });




        List<Map<String, Object>> data = new ArrayList<>();
        for(int i=0;i<journey.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("photo", photo[i]);
            map.put("journey", journey[i]);
            map.put("begin", begin[i]);
            map.put("stop", stop[i]);
            map.put("price", price[i]);
            data.add(map);
        }


        Lv.setAdapter(new SimpleAdapter(this, data, R.layout.item,
                new String[] { "photo", "journey","begin","stop","price" },
                new int[] { R.id.tv_photo,R.id.tv_journey,R.id.tv_begin,R.id.tv_stop,R.id.tv_price }));
        Lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {

                Bundle bundle = new Bundle();
                bundle.putInt("photo", photo[arg2]);
                bundle.putString("journey", journey[arg2]);
                bundle.putString("begin", begin[arg2]);
                bundle.putString("stop", stop[arg2]);
                bundle.putString("price", price[arg2]);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(BusStopActivity.this, MoveList.class);
                Log.i("journey", journey[arg2]);
                startActivity(intent);
            }
        });

    }

}