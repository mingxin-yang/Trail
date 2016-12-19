package com.android.trail.xizheng;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.trail.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Lenovo on 2016/12/17.
 */

public class Userlogin extends Activity implements View.OnClickListener,View.OnLongClickListener {

    private String urlPath,urlPath2;
    private URL url = null;

    // 声明控件对象
    private EditText et_name, et_pass;
    private Button mLoginButton,mLoginError,mRegister,ONLYTEST;
    int selectIndex=1;
    int tempSelect=selectIndex;
    boolean isReLogin=false;
    private int SERVER_FLAG=0;
    private RelativeLayout countryselect;
    private TextView coutry_phone_sn, coutryName;//
    // private String [] coutry_phone_sn_array,coutry_name_array;
    public final static int LOGIN_ENABLE=0x01;    //注册完毕了
    public final static int LOGIN_UNABLE=0x02;    //注册完毕了
    public final static int PASS_ERROR=0x03;      //注册完毕了
    public final static int NAME_ERROR=0x04;      //注册完毕了

    final Handler UiMangerHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch(msg.what){
                case LOGIN_ENABLE:
                    mLoginButton.setClickable(true);
                //    mLoginButton.setText(R.string.login);
                    break;
                case LOGIN_UNABLE:
                    mLoginButton.setClickable(false);
                    break;
                case PASS_ERROR:

                    break;
                case NAME_ERROR:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private Button bt_username_clear;
    private Button bt_pwd_clear;
    private Button bt_pwd_eye;
    private TextWatcher username_watcher;
    private TextWatcher password_watcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  requestWindowFeature(Window.FEATURE_NO_TITLE);
        //不显示系统的标题栏
        //  getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //    WindowManager.LayoutParams.FLAG_FULLSCREEN );

        setContentView(R.layout.login);
        et_name = (EditText) findViewById(R.id.username);
        et_pass = (EditText) findViewById(R.id.password);

        bt_username_clear = (Button)findViewById(R.id.bt_username_clear);
        bt_pwd_clear = (Button)findViewById(R.id.bt_pwd_clear);
        bt_pwd_eye = (Button)findViewById(R.id.bt_pwd_eye);
        bt_username_clear.setOnClickListener(this);
        bt_pwd_clear.setOnClickListener(this);
        bt_pwd_eye.setOnClickListener(this);
        initWatcher();
        et_name.addTextChangedListener(username_watcher);
        et_pass.addTextChangedListener(password_watcher);

        mLoginButton = (Button) findViewById(R.id.login);
        mLoginError  = (Button) findViewById(R.id.login_error);
        mRegister    = (Button) findViewById(R.id.register);//register
        ONLYTEST     = (Button) findViewById(R.id.registfer);
        ONLYTEST.setOnClickListener(this);
        ONLYTEST.setOnLongClickListener((View.OnLongClickListener) this);
        mLoginButton.setOnClickListener(this);
        mLoginError.setOnClickListener(this);
        mRegister.setOnClickListener(this);

        //  countryselect=(RelativeLayout) findViewById(R.id.countryselect_layout);
        //  countryselect.setOnClickListener(this);
        //  coutry_phone_sn=(TextView) findViewById(R.id.contry_sn);
        //  coutryName=(TextView) findViewById(R.id.country_name);

        //  coutryName.setText(coutry_name_array[selectIndex]);    //默认为1
        //  coutry_phone_sn.setText("+"+coutry_phone_sn_array[selectIndex]);
    }
    /**
     * 手机号，密码输入控件公用这一个watcher
     */
    private void initWatcher() {
        username_watcher = new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
            public void afterTextChanged(Editable s) {
                et_pass.setText("");
                if(s.toString().length()>0){
                    bt_username_clear.setVisibility(View.VISIBLE);
                }else{
                    bt_username_clear.setVisibility(View.INVISIBLE);
                }
            }
        };
        password_watcher = new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
            public void afterTextChanged(Editable s) {
                if(s.toString().length()>0){
                    bt_pwd_clear.setVisibility(View.VISIBLE);
                }else{
                    bt_pwd_clear.setVisibility(View.INVISIBLE);
                }
            }
        };
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.login:  //登陆
//   login();
                Login();
                break;
            case R.id.login_error: //无法登陆(忘记密码了吧)
//   Intent login_error_intent=new Intent();
//   login_error_intent.setClass(LoginActivity.this, ForgetCodeActivity.class);
//   startActivity(login_error_intent);
                break;
            case R.id.register:    //注册新的用户
                CreateRegisterAlert();
//   Intent intent=new Intent();
//   intent.setClass(LoginActivity.this, ValidatePhoneNumActivity.class);
//   startActivity(intent);

                break;

            case R.id.registfer:
                if(SERVER_FLAG>10){
                    Toast.makeText(this, "[内部测试--谨慎操作]", Toast.LENGTH_SHORT).show();
                }
                SERVER_FLAG++;
                break;
            case R.id.bt_username_clear:
                et_name.setText("");
                et_pass.setText("");
                break;
            case R.id.bt_pwd_clear:
                et_pass.setText("");
                break;
            case R.id.bt_pwd_eye:
                if(et_pass.getInputType() == (InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD)){
                    bt_pwd_eye.setBackgroundResource(R.drawable.button_clear);
                    et_pass.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_NORMAL);
                }else{
                    bt_pwd_eye.setBackgroundResource(R.drawable.button_clear);
                    et_pass.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                et_pass.setSelection(et_pass.getText().toString().length());
                break;
        }
    }

    /**
     * 登陆
     */
    @Override
    public boolean onLongClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.registfer:
                if(SERVER_FLAG>9){

                }
                //   SERVER_FLAG++;
                break;
        }
        return true;
    }
    /**
     * 监听Back键按下事件,方法2:
     * 注意:
     * 返回值表示:是否能完全处理该事件
     * 在此处返回false,所以会继续传播该事件.
     * 在具体项目中此处的返回值视情况而定.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if(isReLogin){
                Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
                mHomeIntent.addCategory(Intent.CATEGORY_HOME);
                mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                Userlogin.this.startActivity(mHomeIntent);
            }else{
                Userlogin.this.finish();
            }
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

    //登陆
    private void Login(){
        try {
            urlPath2 = "http://192.168.1.103:8992/user/?obj=1&passward="+et_pass.getText().toString()
                    +"&username="+URLEncoder.encode(et_name.getText().toString(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        login.sleep(1000);
    }

    /*创建注册对话框*/
    private AlertDialog m_Dialog;
    private void CreateRegisterAlert()
    {

        final LinearLayout change1 = (LinearLayout) getLayoutInflater().inflate(R.layout.zhuce_login,null);
        AlertDialog.Builder ad =new AlertDialog.Builder(this);
        ad.setTitle("注册账号");
        ad.setView(change1);
        ad.setNegativeButton("取消", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            //此处可执行取消操作，不做任何事情
        }
        });
        ad.setPositiveButton("注册", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //此处可执行注册操作
                EditText passward=    (EditText)change1.findViewById(R.id.txt_password);
                EditText realname =(EditText)change1.findViewById(R.id.txt_username);
                EditText username =(EditText)change1.findViewById(R.id.txt_nicename);
                try {
                    urlPath = "http://192.168.1.103:8992/user/?obj=0&passward="+passward.getText().toString()
                                        +"&realname="+URLEncoder.encode(realname.getText().toString(),"UTF-8")
                                        +"&username="+URLEncoder.encode(username.getText().toString(),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                mRegsiterHandler.sleep(1000);
            }
        });
        ad.create();
        ad.show();
    }
    /*
    *定时注册程序
    * */
    Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            if (msg.what == 0x123) {
                Toast.makeText(Userlogin.this, "发送成功", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setClass(Userlogin.this,PersonalActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(Userlogin.this, "发送失败", Toast.LENGTH_LONG).show();
            }
        }
    };

    Handler handler2 = new Handler()
    {
        public void handleMessage(Message msg)
        {
            if (msg.what == 0x123) {
                Toast.makeText(Userlogin.this, "登陆成功", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setClass(Userlogin.this,PersonalActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(Userlogin.this, "登陆失败", Toast.LENGTH_LONG).show();
            }
        }
    };

    private RegsiterHandler mRegsiterHandler = new RegsiterHandler();
    private loginData login = new loginData();

    class loginData extends Handler{

        @Override
        public void handleMessage(Message msg) {

            new Thread()
            {
                public void run()
                {
                    try
                    {
                        url = new URL(urlPath2);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        if (conn.getResponseCode() == 200) {
                            // 获得服务器响应的数据
                            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                            // 数据
                            String retData = null;
                            String responseData = "";
                            while ((retData = in.readLine()) != null) {
                                responseData += retData;
                            }
                            in.close();
                            if (responseData.equals("ture")) {
                                handler2.sendEmptyMessage(0x123);
                            }
                        } else {
                            handler2.sendEmptyMessage(0x122);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        public void sleep(long delayMillis) {
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }

    }

    class RegsiterHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            new Thread()
            {
                public void run()
                {
                    try
                    {
                        url = new URL(urlPath);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        if (conn.getResponseCode() == 200) {
                            handler.sendEmptyMessage(0x123);
                        } else {
                            handler.sendEmptyMessage(0x122);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        public void sleep(long delayMillis) {
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    }

}
