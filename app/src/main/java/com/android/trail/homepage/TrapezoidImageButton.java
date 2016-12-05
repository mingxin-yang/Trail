package com.android.trail.homepage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageButton;

/**
 * Created by Ccx on 2016/11/24.
 */

public class TrapezoidImageButton extends ImageButton {
    public TrapezoidImageButton(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
}

    public TrapezoidImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TrapezoidImageButton(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isTouchPointInView(event.getX(),event.getY())||
                event.getAction() != MotionEvent.ACTION_DOWN){
            return super.onTouchEvent(event);
        }else{
            return false;
        }
    }

    protected boolean isTouchPointInView(float localX, float localY){
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        draw(canvas);
        int x = (int)localX;
        int y = (int)localY;
        if (x < 0 || x >= getWidth())
            return false;
        if (y < 0 || y >= getHeight())
            return false;
        int pixel = bitmap.getPixel(x,y);
        if ((pixel&0xff000000) != 0){
            return true;
        }else{
            return false;
        }
    }
}
