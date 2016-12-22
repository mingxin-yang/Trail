package com.android.trail.xizheng;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.trail.R;
import com.android.trail.homepage.MainActivity;
import com.android.trail.xizheng.listener.OnDoubleClickListener;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import qiu.niorgai.StatusBarCompat;


/**
 * Created by Lenovo on 2016/11/25.
 */

public class PersonalActivity extends AppCompatActivity implements View.OnClickListener, UploadUtil.OnUploadProcessListener {

    private RelativeLayout mActivityMain;
    private ImageView mImageView;
    private Button mBtnPhoto;
    private Button mBtnCamera;
    private ProgressDialog pd;
    private File filepath;//返回的文件地址
    /**
     * 判断的标识
     */
    private static final int PHOTO_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    private static final int PHOTO_CLIP = 3;
    private static final int UPLOAD_INIT_PROCESS = 4;//上传初始化
    protected static final int UPLOAD_FILE_DONE = 2;//上传中
    private static final int UPLOAD_IN_PROCESS = 5;//上传文件响应

    private Bitmap ytouxiang;
    /*以上是头像修改*/

    private ImageView imge1,imge2;
    private Button pback;
    private LinearLayout ll_basicdata;

    private String urlPath2;



    private int h,w;


    private TextView tv_username;
    private TextView tv_realname;
    private TextView tv_birthday;
    private TextView tv_school;
    private TextView tv_love;
    private TextView tv_Enrollment;
    private TextView tv_qianming;
    private TextView tv_vchat;
    private TextView tv_qq;
    private TextView tv_sina;


    private URL url = null;

    private String[] mVals = new String[] { "苹果手机", "笔记本电脑", "电饭煲 ", "腊肉",
            "特产", "剃须刀", "宝宝", "康佳" , "腊肉",
            "特产", "剃须刀", "宝宝", "康佳"};
    private LayoutInflater mInflater;
    private FlowLayout mFlowLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalipd);



        //获取个性签名控件
        TextView qianming_tv = (TextView)findViewById(R.id.tv_qianming);
        //注册个性签名点击事件
        qianming_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //处理单击事件
                final TextView tv_qianming = (TextView) findViewById(R.id.tv_qianming);

                LinearLayout change3 = (LinearLayout) getLayoutInflater().inflate(R.layout.datachange3,null);
                final EditText et_cgqianming = (EditText) change3.findViewById(R.id.et_cgqianming);
                new AlertDialog.Builder(PersonalActivity.this)
                        //设置对话框的标题
                        .setTitle("个性签名修改")
                        //设置对话框显示的view对象
                        .setView(change3)
                        //为对话框设置保存按钮
                        .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //此处可执行保存操作
                                SharedPreferences share=getSharedPreferences("user", MainActivity.MODE_WORLD_WRITEABLE);
                                SharedPreferences.Editor editor = share.edit();
                                editor.putString("qianming", et_cgqianming.getText().toString());
                                editor.commit();
                                tv_qianming.setText(et_cgqianming.getText().toString());
                                upData();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //此处可执行取消操作，不做任何事情
                            }
                        })
                        //创建并显示对话框
                        .create()
                        .show();
            }
        });

        SharedPreferences share=getSharedPreferences("user", MainActivity.MODE_WORLD_WRITEABLE);
        //带数据跳转
        int id = share.getInt("id",0);
        String username = share.getString("username",null);
        String realname = share.getString("realname",null);
        String school = share.getString("school",null);
        String vchat = share.getString("vchat",null);
        String qianming = share.getString("qianming",null);
        String enrollment = share.getString("getdate",null);
        String gone = share.getString("gone",null);
        String qq  = share.getString("qq",null);
        String sina  = share.getString("sina",null);
        String hobbies = share.getString("hobbies",null);

        tv_username = (TextView)findViewById(R.id.t3);
        tv_realname = (TextView)findViewById(R.id.tv_realname);
        tv_birthday = (TextView)findViewById(R.id.tv_birthday);
        tv_school = (TextView)findViewById(R.id.tv_school);
        tv_Enrollment = (TextView)findViewById(R.id.tv_Enrollment);
        tv_love = (TextView)findViewById(R.id.tv_love);
        tv_qianming = (TextView)findViewById(R.id.tv_qianming);
        tv_vchat = (TextView)findViewById(R.id.tv_vchat);
        tv_qq = (TextView)findViewById(R.id.tv_qq);
        tv_sina = (TextView)findViewById(R.id.tv_sina);

        tv_username.setText(username);
        tv_realname.setText(realname);
        tv_birthday.setText(gone);
        tv_school.setText(school);
        tv_love.setText(hobbies);
        tv_qianming.setText(qianming);
        tv_Enrollment.setText(enrollment);
        tv_vchat.setText(vchat);
        tv_qq.setText(qq);
        tv_sina.setText(sina);

        ll_basicdata = (LinearLayout) findViewById(R.id.ll_basicdata);
        ll_basicdata.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PersonalActivity.this, PersonalData2Activity.class);
                startActivity(intent);
            }
        });


        StatusBarCompat.setStatusBarColor(this, Color.BLUE,255);
        mInflater = LayoutInflater.from(this);
        mFlowLayout = (FlowLayout) findViewById(R.id.id_flowlayout);
        initData();

        //返回
        pback = (Button)findViewById(R.id.person_back);
        pback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences share=getSharedPreferences("user", MainActivity.MODE_WORLD_WRITEABLE);
                SharedPreferences.Editor editor = share.edit();
                editor.putBoolean("USER", true);
                editor.commit();
                Intent pback = new Intent();
                pback.setClass(PersonalActivity.this, Userlogin.class);
                startActivity(pback);
            }
        });

        //获取屏幕比例
        WindowManager wm = (WindowManager)PersonalActivity.this.getSystemService(Context.WINDOW_SERVICE);
        h = wm.getDefaultDisplay().getHeight();
        w = wm.getDefaultDisplay().getWidth();

        imge1 = (ImageView)findViewById(R.id.t2);
        imge2 = (ImageView)findViewById(R.id.t1);
        imge1.setY(h/4);
        imge1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //处理单击事件   头像处理
                RelativeLayout change3 = (RelativeLayout) getLayoutInflater().inflate(R.layout.pohoto_camera,null);
                mActivityMain = (RelativeLayout)change3.findViewById(R.id.activity_main);
                mImageView = (ImageView)change3.findViewById(R.id.imageView);
                mBtnPhoto = (Button) change3.findViewById(R.id.btn_photo);
                mBtnPhoto.setOnClickListener(onclick);
                mBtnCamera = (Button)change3.findViewById(R.id.btn_camera);
                mBtnCamera.setOnClickListener(onclick);
                new AlertDialog.Builder(PersonalActivity.this)
                        //设置对话框的标题
                        .setTitle("头像修改")
                        //设置对话框显示的view对象
                        .setView(change3)
                        //为对话框设置保存按钮
                        .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //此处可执行保存操作
                                imge1.setImageBitmap(ytouxiang);
                                //imge2.setImageBitmap(ytouxiang);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //此处可执行取消操作，不做任何事情
                            }
                        })
                        //创建并显示对话框
                        .create()
                        .show();
            }
        });

        //头衔修改控件


        /*
        * 昵称修改
        * */
        //获取昵称控件
        TextView nick_tv = (TextView)findViewById(R.id.t3);
        //注册昵称点击事件监听器
        nick_tv.setOnTouchListener(new OnDoubleClickListener(new OnDoubleClickListener.DoubleClickCallback() {
            @Override
            public void onDoubleClick() {
                //处理双击事件
                final TextView tv_nick = (TextView) findViewById(R.id.t3);

                LinearLayout change1 = (LinearLayout) getLayoutInflater().inflate(R.layout.datachange1,null);
                final EditText et_nick = (EditText) change1.findViewById(R.id.et_cgnick);
                new AlertDialog.Builder(PersonalActivity.this)
                        //设置对话框的标题
                        .setTitle("昵称修改")
                        //设置对话框显示的view对象
                        .setView(change1)
                        //为对话框设置保存按钮
                        .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //此处可执行保存操作
                                tv_nick.setText(et_nick.getText().toString());

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //此处可执行取消操作，不做任何事情
                            }
                        })
                        //创建并显示对话框
                        .create()
                        .show();
            }
        }));
        upData();
    }



    public void initData() {
        /**
         * 找到搜索标签的控件
         */
        for (int i = 0; i < mVals.length; i++) {
            TextView tv = (TextView) mInflater.inflate(
                    R.layout.search_label_tv, mFlowLayout, false);
            tv.setText(mVals[i]);
            final String str = tv.getText().toString();
            //点击事件
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            mFlowLayout.addView(tv);//添加到父View
        }
    }

    Handler handler2 = new Handler()
    {
        public void handleMessage(Message msg)
        {
            if (msg.what == 0x123) {
                Toast.makeText(PersonalActivity.this, "刷新成功", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(PersonalActivity.this, "刷新失败", Toast.LENGTH_LONG).show();
            }
        }
    };


    public void upData(){
        SharedPreferences share=getSharedPreferences("user", MainActivity.MODE_WORLD_WRITEABLE);
        try {
            urlPath2 = "http://10.7.88.94:8992/user/?obj=3&realname="+ URLEncoder.encode(share.getString("realname",null),"UTF-8")
                    +"&school="+ URLEncoder.encode(share.getString("school",null),"UTF-8")
                    +"&hobbies="+ URLEncoder.encode(share.getString("hobbies",null),"UTF-8")
                    +"&id="+ share.getInt("id",0);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        login.sleep(1);
    }
    private loginData login = new loginData();

    class loginData extends Handler {

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
                            handler2.sendEmptyMessage(0x123);

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

    /**
     * 从相册选择图片来源
     */
    private void getPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, PHOTO_REQUEST);
    }

    /**
     * 从系统相机选择图片来源
     */
    private void getCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 下面这句指定调用相机拍照后的照片存储的路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "hand.jpg")));
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    /****
     * 调用系统自带切图工具对图片进行裁剪
     * 微信也是
     * @param uri
     */
    private void photoClip(Uri uri) {
        // 调用系统中自带的图片剪裁
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 2);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX",  2*h/5);
        intent.putExtra("outputY", 2*h/5);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_CLIP);
    }

    /**
     * 上传图片到服务器
     */
    private void toUploadFile() {
        pd = ProgressDialog.show(this, "", "正在上传文件...");
        pd.show();
        String fileKey = "avatarFile";
        UploadUtil uploadUtil = UploadUtil.getInstance();
        uploadUtil.setOnUploadProcessListener(PersonalActivity.this); //设置监听器监听上传状态

        Map<String, String> params = new HashMap<String, String>();//上传map对象
        params.put("userId", "");
        uploadUtil.uploadFile(filepath, fileKey, "http://10.7.88.95/bus", params);
        Toast.makeText(this, "上传成功", Toast.LENGTH_LONG).show();
    }

    /**
     * 上传服务器响应回调
     */
    @Override
    public void onUploadDone(int responseCode, String message) {
        //上传完成响应
        pd.dismiss();
        Message msg = Message.obtain();
        msg.what = UPLOAD_FILE_DONE;
        msg.arg1 = responseCode;
        msg.obj = message;
    }

    @Override
    public void onUploadProcess(int uploadSize) {
        //上传中
        Message msg = Message.obtain();
        msg.what = UPLOAD_IN_PROCESS;
        msg.arg1 = uploadSize;
    }

    @Override
    public void initUpload(int fileSize) {
        //准备上传
        Message msg = Message.obtain();
        msg.what = UPLOAD_INIT_PROCESS;
        msg.arg1 = fileSize;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_photo:
                getPhoto();
                break;
            case R.id.btn_camera:
                getCamera();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_REQUEST:
                switch (resultCode) {
                    case -1://-1表示拍照成功
                        File file = new File(Environment.getExternalStorageDirectory()
                                + "/hand.jpg");//保存图片
                        if (file.exists()) {
                            //对相机拍照照片进行裁剪
                            photoClip(Uri.fromFile(file));
                        }
                }
                break;

            case PHOTO_REQUEST://从相册取
                if (data != null) {
                    Uri uri = data.getData();
                    //对相册取出照片进行裁剪
                    photoClip(uri);

                }
                break;
            case PHOTO_CLIP:
                //完成
                if (data != null) {

                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap photo = extras.getParcelable("data");
                        //上传完成将照片写入imageview与用户进行交互
                        ytouxiang = extras.getParcelable("data");
                        mImageView.setImageBitmap(photo);
                        try {
                            //获得图片路径
                            filepath = UploadUtil.saveFile(photo, Environment.getExternalStorageDirectory().toString(), "hand.jpg");
                            //上传照片
                            toUploadFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
        }
    }

    public View.OnClickListener onclick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_photo:
                    getPhoto();
                    break;
                case R.id.btn_camera:
                    getCamera();
                    break;
            }
        }
    };

}
