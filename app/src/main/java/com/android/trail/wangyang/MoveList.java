package com.android.trail.wangyang;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.trail.R;

public class MoveList extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.model);

        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("photo");
        String journey = bundle.getString("journey");
        ImageView Iv = (ImageView) findViewById(R.id.Iv);
        Iv.setImageResource(id);
        TextView tv = (TextView) findViewById(R.id.tv_message);
        tv.setText(journey);




    }

}
