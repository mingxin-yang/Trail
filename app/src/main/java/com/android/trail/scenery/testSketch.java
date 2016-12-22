package com.android.trail.scenery;

/**
 * Created by Ccx on 2016/12/22.
 */

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

public class testSketch {

    public static Bitmap testSingleGaussBlur(Bitmap src, int r, int fai) {
        int picHeight = src.getHeight();
        int picWidth = src.getWidth();

        int[] pixels = new int[picWidth * picHeight];
        src.getPixels(pixels, 0, picWidth, 0, 0, picWidth, picHeight);

        Sketch.gaussBlur(pixels, picWidth, picHeight, r, fai);
        Bitmap bitmap = Bitmap.createBitmap(pixels, picWidth, picHeight,
                Config.RGB_565);
        return bitmap;

    }

    public static Bitmap testGaussBlur(Bitmap src, int r, int fai) {

        int width = src.getWidth();
        int height = src.getHeight();

        int[] pixels = Sketch.discolor(src);
        int[] copixels = Sketch.simpleReverseColor(pixels);
        Sketch.simpleGaussBlur(copixels, width, height, r, fai);
        Sketch.simpleColorDodge(pixels, copixels);

        Bitmap bitmap = Bitmap.createBitmap(pixels, width, height,
                Config.RGB_565);
        return bitmap;

    }

    public static Bitmap testDiscolor(Bitmap srcBitmap) {

        int width = srcBitmap.getWidth();
        int height = srcBitmap.getHeight();
        int[] pixels = Sketch.discolor(srcBitmap);
        Bitmap bitmap = Bitmap.createBitmap(pixels, width, height,
                Config.RGB_565);
        return bitmap;

    }

    public static Bitmap testReverseColor(Bitmap src) {

        int width = src.getWidth();
        int height = src.getHeight();
        int[] pixels = Sketch.discolor(src);
        int[] reversels = Sketch.simpleReverseColor(pixels);
        Bitmap bitmap = Bitmap.createBitmap(reversels, width, height,
                Config.RGB_565);
        return bitmap;

    }

    public static Bitmap testColorDodge(Bitmap src) {

        int width = src.getWidth();
        int height = src.getHeight();
        int[] pixels = Sketch.discolor(src);
        int[] mixels = Sketch.simpleReverseColor(pixels);
        Sketch.colorDodge(pixels, mixels);
        Bitmap bitmap = Bitmap.createBitmap(pixels, width, height,
                Config.RGB_565);
        return bitmap;

    }
}