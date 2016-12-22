package com.android.trail.xizheng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.trail.R;

/**
 * Created by dell on 2016/12/20.
 */

public class PersonalData2Activity extends Activity{

    private Button pback;                            //返回按钮
    private LayoutInflater mInflater;
    private FlowLayout mFlowLayout;

    private EditText et_realname;
    private EditText et_birthday;
    private EditText et_school;
    private EditText et_love;
    private EditText et_Enrollment;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datachange2);
        //获取承载值的控件
        et_realname = (EditText)findViewById(R.id.et_realname);
        et_birthday = (EditText)findViewById(R.id.et_birthday);
        et_school = (EditText)findViewById(R.id.et_school);
        et_love = (EditText) findViewById(R.id.et_love);
        et_Enrollment = (EditText)findViewById(R.id.et_Enrollment);

        Intent intent = getIntent();
        String realname = intent.getStringExtra("realname");
        String birthday = intent.getStringExtra("birthday");
        String school = intent.getStringExtra("school");
        String love = intent.getStringExtra("love");
        String enrollment = intent.getStringExtra("enrollment");

        et_realname.setText(realname);
        et_birthday.setText(birthday);
        et_school.setText(school);
        et_love.setText(love);
        et_Enrollment.setText(enrollment);

        //返回
        pback = (Button)findViewById(R.id.persondata2_back);
        pback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pback = new Intent();
                pback.setClass(PersonalData2Activity.this, PersonalActivity.class);
                startActivity(pback);
                finish();
            }
        });
    }
}
