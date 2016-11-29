package com.android.trail.wangyang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.trail.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusStop extends Activity {
    private ListView Lv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_stop);

        Lv = (ListView) findViewById(R.id.Lv);

        final String[] journey = new String[] { "南焦客运站-->新百广场", "南焦客运站-->白佛客运站", "南焦客运站-->北站" };
        final String[] begin = new String[] {"06:00","06:00","06:30"};
        final String[] stop = new String[]{"22:00","21:00","22:30"};
        final String[] price = new String[]{"1元","2元","1元"};
        final int[] photo = new int[] { R.drawable.bus_30, R.drawable.bus_65, R.drawable.bus_75 };
        List<Map<String, Object>> data = new ArrayList<>();

        Map<String, Object> map1 = new HashMap<>();
        map1.put("photo", R.drawable.bus_30);
        map1.put("journey", journey[0]);
        map1.put("begin", begin[0]);
        map1.put("stop", stop[0]);
        map1.put("price", price[0]);
        data.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("photo", R.drawable.bus_65);
        map1.put("journey", journey[1]);
        map1.put("begin", begin[1]);
        map1.put("stop", stop[1]);
        map1.put("price", price[1]);
        data.add(map2);

        Map<String, Object> map3 = new HashMap<>();
        map3.put("photo", R.drawable.bus_75);
        map1.put("journey", journey[2]);
        map1.put("begin", begin[2]);
        map1.put("stop", stop[2]);
        map1.put("price", price[2]);
        data.add(map3);

        Lv.setAdapter(new SimpleAdapter(this, data, R.layout.item,
                new String[] { "photo", "journey","begin","stop","price" },
                new int[] { R.id.tv_photo,R.id.tv_journey,R.id.tv_begin,R.id.tv_stop,R.id.tv_price }));
        Lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                Bundle bundle = new Bundle();
                bundle.putInt("photo", photo[arg2]);
                bundle.putString("journey", journey[arg2]);
                bundle.putString("begin", begin[arg2]);
                bundle.putString("stop", stop[arg2]);
                bundle.putString("price", price[arg2]);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(BusStop.this, MoveList.class);
                Log.i("journey", journey[arg2]);
                startActivity(intent);
            }
        });
    }

}
