package com.android.trail.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.trail.R;
import com.android.trail.util.UploadUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class PhotoCamera extends AppCompatActivity implements View.OnClickListener, UploadUtil.OnUploadProcessListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pohoto_camera);
        initView();

    }

    private void initView() {
        mActivityMain = (RelativeLayout) findViewById(R.id.activity_main);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mBtnPhoto = (Button) findViewById(R.id.btn_photo);
        mBtnPhoto.setOnClickListener(this);
        mBtnCamera = (Button) findViewById(R.id.btn_camera);
        mBtnCamera.setOnClickListener(this);
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
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
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
        uploadUtil.setOnUploadProcessListener(PhotoCamera.this); //设置监听器监听上传状态

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
}
