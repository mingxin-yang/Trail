package com.android.trail.zhenfeng.share.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.android.trail.R;
import com.android.trail.zhenfeng.share.scencyRate;

import java.util.HashMap;

/**
 * Created by Lenovo on 2016/12/18.
 */

public class oneShare extends Activity implements AdapterView.OnItemSelectedListener, ViewSwitcher.ViewFactory, AdapterView.OnItemClickListener{

    private Button scency_fenxiang;
    private Gallery gallery;
    private ImageSwitcher imageSwitcher;
    private oneShare.ImageAdapter imageAdapter;
    private int mCurrentPos = -1;// 当前的item
    private HashMap<Integer, ImageView> mViewMap;

    private int[] resIds = new int[]
            {R.drawable.p1, R.drawable.jieshao2, R.drawable.jieshao3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scency_image);

        scency_fenxiang = (Button) findViewById(R.id.scency_fenxiang);
        scency_fenxiang.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(oneShare.this, scencyRate.class);
                startActivity(i);
            }
        });


        gallery = (Gallery)findViewById(R.id.gallery);
        imageAdapter = new oneShare.ImageAdapter(oneShare.this, resIds.length);
        gallery.setAdapter(imageAdapter);
        gallery.setOnItemSelectedListener(this);
        gallery.setSelection(1);// 设置一加载Activity就显示的图片为第二张

        gallery.setOnItemClickListener(this);

        imageSwitcher = (ImageSwitcher)findViewById(R.id.imageswitcher);
        imageSwitcher.setFactory(this);

        // 设置动画效果 淡入淡出
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(oneShare.this, android.R.anim.fade_in));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(oneShare.this, android.R.anim.fade_out));
    }

    public class ImageAdapter extends BaseAdapter
    {
        int mGalleryItemBackground;
        private Context mContext;
        private int mCount;// 一共多少个item

        public ImageAdapter(Context context, int count)
        {
            mContext = context;
            mCount = count;
            mViewMap = new HashMap<Integer, ImageView>(count);
//            TypedArray typedArray = obtainStyledAttributes(R.styleable.Gallery);
            // 设置边框的样式
//            mGalleryItemBackground = typedArray.getResourceId(R.styleable.Gallery_android_galleryItemBackground, 0);
        }

        public int getCount()
        {
            return Integer.MAX_VALUE;
        }

        public Object getItem(int position)
        {
            return position;
        }

        public long getItemId(int position)
        {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            ImageView imageview = mViewMap.get(position % mCount);
            if (imageview == null)
            {
                imageview = new ImageView(mContext);
                imageview.setImageResource(resIds[position % resIds.length]);
                imageview.setScaleType(ImageView.ScaleType.FIT_XY);
                imageview.setLayoutParams(new Gallery.LayoutParams(136, 88));
                imageview.setBackgroundResource(mGalleryItemBackground);
            }
            return imageview;
        }
    }

    // 滑动Gallery的时候，ImageView不断显示当前的item
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        // imageSwitcher.setImageResource(resIds[position % resIds.length]);
    }

    // 设置点击Gallery的时候才切换到该图片
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        if (mCurrentPos == position)
        {
            // 如果在显示当前图片，再点击，就不再加载。
            return;
        }
        mCurrentPos = position;
        imageSwitcher.setImageResource(resIds[position % resIds.length]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
    }

    @Override
    public View makeView()
    {
        ImageView imageView = new ImageView(oneShare.this);
//        imageView.setBackgroundColor(0xFF000000);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new ImageSwitcher.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.FILL_PARENT));

        return imageView;
    }

}

