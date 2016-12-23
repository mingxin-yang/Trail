package com.android.trail.xizheng;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.trail.R;
import com.android.trail.homepage.MainActivity;

/**
 * Created by dell on 2016/12/20.
 */

public class PersonalData2Activity extends Activity{

    private Button pback;                            //返回按钮

    private Button save;

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
        save = (Button)findViewById(R.id.btn_persondata2keep);

        SharedPreferences share=getSharedPreferences("user", MainActivity.MODE_WORLD_WRITEABLE);


        String realname = share.getString("realname",null);
        String school = share.getString("school",null);
        String enrollment = share.getString("getdate",null);
        String gone = share.getString("gone",null);
        String hobbies = share.getString("hobbies",null);

        et_realname.setText(realname);
        et_birthday.setText(gone);
        et_school.setText(school);
        et_love.setText(hobbies);
        et_Enrollment.setText(enrollment);

        //返回
        pback = (Button)findViewById(R.id.persondata2_back);
        pback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences share=getSharedPreferences("user", MainActivity.MODE_WORLD_WRITEABLE);
                SharedPreferences.Editor editor = share.edit();
                editor.putString("realname",et_realname.getText().toString());
                editor.putString("gone",et_birthday.getText().toString());
                editor.putString("school",et_school.getText().toString());
                editor.putString("hobbies",et_love.getText().toString());
                editor.putString("enrollment",et_Enrollment.getText().toString());
                editor.commit();
                Intent pback = new Intent();
                pback.setClass(PersonalData2Activity.this, PersonalActivity.class);
                startActivity(pback);
                finish();
            }
        });
    }
}
