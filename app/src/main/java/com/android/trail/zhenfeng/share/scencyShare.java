package com.android.trail.zhenfeng.share;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.trail.R;
import com.android.trail.zhenfeng.hzfLei.AndroidShare;

/**
 * Created by 20160316 on 2016/12/13.
 */

public class scencyShare extends AppCompatActivity {

    private Button buShare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scency_share);


        buShare=(Button) findViewById(R.id.buShare);
        buShare.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                AndroidShare as = new AndroidShare(
                        scencyShare.this,
                        "哈哈---超方便的分享！！！来自allen",
                        "http://img6.cache.netease.com/cnews/news2012/img/logo_news.png");
                as.show();
            }
        });

      /*  fanhui = (Button)findViewById(R.id.fanhui);

        fanhui.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });*/

    }
}
