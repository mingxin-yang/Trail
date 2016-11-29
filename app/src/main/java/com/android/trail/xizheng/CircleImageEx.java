package com.android.trail.xizheng;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by Lenovo on 2016/11/25.
 */

public class CircleImageEx extends ImageView {


    public CircleImageEx(Context context) {
        super(context);
    }

    public CircleImageEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        //获取图片，转化为Bitmap
        Bitmap b =  ((BitmapDrawable)drawable).getBitmap();
        if(null == b)
        {
            return;
        }
        //将图片转为32位ARGB位图，保证图片质量
        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
//	   //获取图片的宽 高
        int w = getWidth(), h = getHeight();

        //通过getCroppedBitmap函数，返回一个圆形图片
        Bitmap roundBitmap =  getCroppedBitmap(bitmap, w);
        //在自定义的CircleImageEx上展现
        canvas.drawBitmap(roundBitmap, 0,0, null);
    }

    public Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
        Bitmap p;
        //判断图片的大小与传入radius是否相等，如果不相等，那么
        //将图片设置成长 宽都是radius的图片
        if(bmp.getWidth() != radius || bmp.getHeight() != radius)
            p = Bitmap.createScaledBitmap(bmp, radius, radius, false);
        else
            p = bmp;
        //最后输出的图片信息
        Bitmap output = Bitmap.createBitmap(p.getWidth(),
                p.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xffa19774;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, p.getWidth(), p.getHeight());
        //画笔加上  抗锯齿标志，图像更加平滑
        paint.setAntiAlias(true);
        //如果该项设置为true,则图像在动画进行中会滤掉对Bitmap图像的优化操作,加快显示
        paint.setFilterBitmap(true);
        //防抖动
        paint.setDither(true);
        // 透明色
        canvas.drawARGB(0, 0, 0, 0);
        //画笔的颜色
        paint.setColor(Color.parseColor("#BAB399"));

        //  绘制一个多边形
        //paint.setColor(Color.BLACK);
        //得到当前屏幕宽度
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int w = wm.getDefaultDisplay().getWidth();
        int h = wm.getDefaultDisplay().getHeight();

        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(w, 0);
        path.lineTo(w,h/5);
//        path.lineTo(100, 400);
        path.lineTo(0, 2*h/5);
        path.close();
        canvas.drawPath(path, paint);

        //画出一个圆形
//        canvas.drawCircle(p.getWidth() / 2+0.7f, p.getHeight() / 2+0.7f,
//                p.getWidth() / 2+0.1f, paint);
        //设置两张图片相交时的模式 ，就是在画布上遮上圆形的图片信息
        paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(p, rect, rect, paint);
        return output;
    }

}
