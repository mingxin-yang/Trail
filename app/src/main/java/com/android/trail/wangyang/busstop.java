package com.android.trail.wangyang;

import android.app.ListActivity;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.trail.R;

import java.util.ArrayList;
import java.util.List;

public class BusStop extends ListActivity {
    private List<Bus> ls = new ArrayList<Bus>();
    private ArrayAdapter<String> adapter;
    private MyAdapter myadapter;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_main);
        getData();
        lv = (ListView)findViewById(R.id.Lv);
        lv.setAdapter(myadapter);
    }

    private void getData() {
        ls.add(new Bus("南焦客运站-->市二院","05:30","22:00",1));
        ls.add(new Bus("南焦客运站-->市二院","05:30","22:00",1));
        ls.add(new Bus("南焦客运站-->市二院","05:30","22:00",1));
        ls.add(new Bus("南焦客运站-->市二院","05:30","22:00",1));
        ls.add(new Bus("南焦客运站-->市二院","05:30","22:00",1));
        ls.add(new Bus("南焦客运站-->市二院","05:30","22:00",1));
    }
}
