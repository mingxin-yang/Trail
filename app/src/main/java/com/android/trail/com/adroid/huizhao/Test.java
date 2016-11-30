package com.android.trail.com.adroid.huizhao;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;

/**
 * Created by Lenovo on 2016/11/27.
 */

public class Test extends Activity{
    private int recLen = 11;
    private TextView txtView;
    Timer timer = new Timer();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//
//        txtView = (TextView)findViewById(R.id.txttime);

//        timer.schedule(task, 1000, 1000);       // timeTask

    }
//
//    TimerTask task = new TimerTask() {
//        @Override
//        public void run() {
//
//            runOnUiThread(new Runnable() {      // UI thread
//                @Override
//                public void run() {
//                    recLen--;
//                    txtView.setText(""+recLen);
//                    if(recLen < 0){
//                        timer.cancel();
//                        txtView.setVisibility(View.GONE);
//                    }
//                }
//            });
//        }
//    };

}
