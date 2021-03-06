package com.hnsi.zheng.hnti_erp_app.http;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;

import com.android.volley.toolbox.ImageLoader.ImageCache;

/**
 * LruCache实现的缓存类
 * 用于为ImageLoader设置图片缓存大小
 * 针对不同的屏幕设置缓存大小（GetVolley）
 */
public class LruBitmapCache extends LruCache<String, Bitmap> implements ImageCache {
    
	private LruBitmapCache(int maxSize)
    {
        super(maxSize);
    }

    public LruBitmapCache(Context ctx)
    {
        this(getCacheSize(ctx));
    }

    @Override
    protected int sizeOf(String key, Bitmap value)
    {
        return value.getRowBytes() * value.getHeight();
    }

    @Override
    public Bitmap getBitmap(String url)
    {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap)
    {
        put(url, bitmap);
    }

    // Returns a cache size equal to approximately three screens worth of images.
    // 返回一个相当于三个屏幕大小的图片的缓存
    private static int getCacheSize(Context ctx)
    {
        final DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();
        final int screenWidth = displayMetrics.widthPixels;
        final int screenHeight = displayMetrics.heightPixels;
        // 4 bytes per pixel
        final int screenBytes = screenWidth * screenHeight * 4;

        return screenBytes * 3;
    }

}
