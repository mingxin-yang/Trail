package com.android.trail.xizheng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.trail.R;

/**
 * Created by dell on 2016/12/5.
 */

public class BasicDataActivity extends Activity {
    private Button keep = null;
    private TextView realname;
    private TextView birthday;
    private TextView school;
    private TextView enrollment;
    private TextView love;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datachange2);

        realname = (TextView) findViewById(R.id.tv_change1);
        birthday = (TextView) findViewById(R.id.tv_change2);
        school = (TextView) findViewById(R.id.tv_change3);
        enrollment = (TextView) findViewById(R.id.tv_change4);
        love = (TextView) findViewById(R.id.tv_change5);
        keep = (Button) findViewById(R.id.btn_keep);
        keep.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String num1 = realname.getText().toString();
                String num2 = birthday.getText().toString();
                String num3 = school.getText().toString();
                String num4 = enrollment.getText().toString();
                String num5 = love.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("徐歌阳",num1);
                intent.putExtra("1996年8月30日", num2);
                intent.putExtra("河北师范大学",num3);
                intent.putExtra("保密",num4);
                intent.putExtra("保密",num5);

                intent.setClass(BasicDataActivity.this, PersonalActivity.class);
                startActivity(intent);
            }
        });

    }

}
