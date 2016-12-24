package com.android.trail.activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.trail.R;
import com.android.trail.util.testSketch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Share extends AppCompatActivity implements View.OnClickListener{
    private ImageView imageView;
    private Button share_back;
    private Button share_btn;

    private ImageView mImageView;
    private EditText mREditView;
    private Button mDisColorBtn;
    private Button mDodgeBtn;
    private Button mGaussBlurBtn;
    private Button mConvertBtn;

    private ProgressDialog mDialog;
    private Bitmap mSourceBitmap;
    private Bitmap mConvertedBitmap;

    private static final int TYPE_DISCOLOR = 0;
    private static final int TYPE_DODGE = 1;
    private static final int TYPE_GAUSSBLUR = 2;
    private static final int TYPE_CONVERT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        imageView = (ImageView)findViewById(R.id.share_img);
        Intent intent =getIntent();
        if (intent != null){
            byte []bis = intent.getByteArrayExtra("bitmap");
            Bitmap bitmap = BitmapFactory.decodeByteArray(bis,0,bis.length);
            imageView.setImageBitmap(bitmap);
        }

        share_back = (Button)findViewById(R.id.share_back);
        share_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Share.this,Scan.class);
                startActivity(intent);
                finish();
            }
        });

        mImageView = (ImageView) findViewById(R.id.share_img);
        mImageView.setOnClickListener(this);

        mREditView = (EditText) findViewById(R.id.share_edittext);

        mDisColorBtn = (Button) findViewById(R.id.share_discolor_btn);
        mDisColorBtn.setOnClickListener(this);

        mDodgeBtn = (Button) findViewById(R.id.share_dodge_btn);
        mDodgeBtn.setOnClickListener(this);

        mGaussBlurBtn = (Button) findViewById(R.id.share_guauss_btn);
        mGaussBlurBtn.setOnClickListener(this);

        mConvertBtn = (Button) findViewById(R.id.share_convert_btn);
        mConvertBtn.setOnClickListener(this);

        share_btn =(Button)findViewById(R.id.share_btn);
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImageToGallery(Share.this,((BitmapDrawable)mImageView.getDrawable()).getBitmap());
                Toast.makeText(Share.this,"已保存到本地",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mConvertedBitmap != null) {
            mConvertedBitmap.recycle();
            mConvertedBitmap = null;
        }
    }

    @Override
    public void onClick(View v) {
        String rStr = mREditView.getText().toString();
        int r;
        if(rStr != null && rStr.length() != 0) {
            r = Integer.parseInt(rStr);
        }else{
            r =10;
        }
        if (v == mConvertBtn) {
            new ConvertTask().execute(new Integer[] { TYPE_CONVERT, r });
        } else if (v == mDisColorBtn) {
            new ConvertTask().execute(new Integer[] { TYPE_DISCOLOR, r });
        } else if (v == mDodgeBtn) {
            new ConvertTask().execute(new Integer[] { TYPE_DODGE, r });
        } else if (v == mGaussBlurBtn) {
            new ConvertTask().execute(new Integer[] { TYPE_GAUSSBLUR, r });
        } else if (v == mImageView) {
            if (mSourceBitmap != null) {
                mImageView.setImageBitmap(mSourceBitmap);
            }
        }
    }

    private class ConvertTask extends AsyncTask<Integer, Void, Bitmap> {

        @Override
        protected void onPostExecute(Bitmap result) {
            mDialog.dismiss();
            if (result != null) {
                mConvertedBitmap = result;
                mImageView.setImageBitmap(result);
            }

        }

        @Override
        protected void onPreExecute() {
            if (mDialog == null) {
                mDialog = new ProgressDialog(Share.this);
            }
            mDialog.show();
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            int type = params[0];
            int r = params[1];
            if (mSourceBitmap == null) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) mImageView.getDrawable();
                mSourceBitmap = bitmapDrawable.getBitmap();
            } else if (mConvertedBitmap != null) {
                mConvertedBitmap.recycle();
                mConvertedBitmap = null;
            }

            Bitmap result = null;
            switch (type) {
                case TYPE_DISCOLOR:
                    result = testSketch.testDiscolor(mSourceBitmap);
                    break;
                case TYPE_DODGE:
                    result = testSketch.testReverseColor(mSourceBitmap);
                    break;
                case TYPE_GAUSSBLUR:
                    result = testSketch.testSingleGaussBlur(mSourceBitmap, r, r / 3);
                    break;
                case TYPE_CONVERT:
                    result = testSketch.testGaussBlur(mSourceBitmap, r, r / 3);
                    break;
            }

            return result;
        }

    }

    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File("/sdcard/Boohee/image.jpg"))));

    }
}
