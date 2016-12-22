package com.android.trail.com.adroid.huizhao.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.trail.R;
import com.android.trail.homepage.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import qiu.niorgai.StatusBarCompat;

/**
 * Created by Lenovo on 2016/11/24.
 */

public class JieShaoActivity extends Activity {

    private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv_more;
    private Button img;

    private ViewPager mViewPaper;
    private List<ImageView> images;
    private List<View> dots;
    private int currentItem;
    //记录上一次点的位置
    private int oldPosition = 0;
    //存放图片的id
    private  int[] imageIds = new int[]{
            R.drawable.jieshao_img2,
            R.drawable.jieshao_img1,
            R.drawable.jieshao_img3
    };
  //  private int[] imageIds = new int[]{
   //         R.drawable.p1,
    //        R.drawable.jieshao2,
    //        R.drawable.jieshao3
    //};

    private ViewPagerAdapter adapter;
    private ScheduledExecutorService scheduledExecutorService;

    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.jie_shao_t1:
                    Intent intent1 = new Intent();
                    intent1.setClass(JieShaoActivity.this,JieShao2.class);
                    startActivity(intent1);
                    break;
                case R.id.jie_shao_t2:
                    Intent intent2 = new Intent();
                    intent2.setClass(JieShaoActivity.this,JieShao1.class);
                    startActivity(intent2);
                    break;
                case R.id.jie_shao_t3:
                    Intent intent3 = new Intent();
                    intent3.setClass(JieShaoActivity.this,JieShao3.class);
                    startActivity(intent3);
                    break;
                case R.id.jie_shao_t4:
                    Intent intent4 = new Intent();
                    intent4.setClass(JieShaoActivity.this,JieShao4.class);
                    startActivity(intent4);
                    break;
                case R.id.jie_shao_t5:
                    Intent intent5 = new Intent();
                    intent5.setClass(JieShaoActivity.this,Jieshao5.class);
                    startActivity(intent5);
                    break;
                case R.id.jie_shao_t6:
                    Intent intent6 = new Intent();
                    intent6.setClass(JieShaoActivity.this,JieShao6.class);
                    startActivity(intent6);
                    break;
                case R.id.jie_shao_more:
                    Intent intentm = new Intent();
                    intentm.setClass(JieShaoActivity.this,JieShaoMore.class);
                    startActivity(intentm);
                    break;
                case R.id.jie_shao_back:
                    Intent intentb = new Intent();
                    intentb.setClass(JieShaoActivity.this,MainActivity.class);
                    startActivity(intentb);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    //介绍界面的跳转
    public void getID (){
        tv1 = (TextView)findViewById(R.id.jie_shao_t1);
        tv2 = (TextView)findViewById(R.id.jie_shao_t2);
        tv3 = (TextView)findViewById(R.id.jie_shao_t3);
        tv4 = (TextView)findViewById(R.id.jie_shao_t4);
        tv5 = (TextView)findViewById(R.id.jie_shao_t5);
        tv6 = (TextView)findViewById(R.id.jie_shao_t6);
        tv_more = (TextView)findViewById(R.id.jie_shao_more);

        tv1.setOnClickListener(listener);
        tv2.setOnClickListener(listener);
        tv3.setOnClickListener(listener);
        tv4.setOnClickListener(listener);
        tv5.setOnClickListener(listener);
        tv6.setOnClickListener(listener);
        tv_more.setOnClickListener(listener);

        img = (Button)findViewById(R.id.jie_shao_back);
        img.setOnClickListener(listener);


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jie_shao);
        mViewPaper = (ViewPager) findViewById(R.id.vp);
        //沉浸式状态栏
        StatusBarCompat.setStatusBarColor(this, Color.BLUE,255);
        getID();

        //显示的图片
        images = new ArrayList<ImageView>();
        for(int i = 0; i < imageIds.length; i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);
        }
        //显示的小点
//        dots = new ArrayList<View>();
//        dots.add(findViewById(R.id.dot_0));
//        dots.add(findViewById(R.id.dot_1));
//        dots.add(findViewById(R.id.dot_2));
//        dots.add(findViewById(R.id.dot_3));
//        dots.add(findViewById(R.id.dot_4));



        adapter = new ViewPagerAdapter();
        mViewPaper.setAdapter(adapter);

        mViewPaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageSelected(int position) {
               // title.setText(titles[position]);
//                dots.get(position).setBackgroundResource(R.drawable.dot_focused);
//                dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);

//                oldPosition = position;
//                currentItem = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    /**
     * 自定义Adapter
     * @author
     *
     */
    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            // TODO Auto-generated method stub
//          super.destroyItem(container, position, object);
//          view.removeView(view.getChildAt(position));
//          view.removeViewAt(position);
            view.removeView(images.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            // TODO Auto-generated method stub
            view.addView(images.get(position));
            return images.get(position);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return false;
    }

    /**
     * 利用线程池定时执行动画轮播
     */
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(
                new ViewPageTask(),
                2,
                2,
                TimeUnit.SECONDS);
    }


    /**
     * 图片轮播任务
     * @author liuyazhuang
     *
     */
    private class ViewPageTask implements Runnable{

        @Override
        public void run() {
            currentItem = (currentItem + 1) % imageIds.length;
            mHandler.sendEmptyMessage(0);
        }
    }

    /**
     * 接收子线程传递过来的数据
     */
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            mViewPaper.setCurrentItem(currentItem);
        };
    };
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if(scheduledExecutorService != null){
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }
    }

}
