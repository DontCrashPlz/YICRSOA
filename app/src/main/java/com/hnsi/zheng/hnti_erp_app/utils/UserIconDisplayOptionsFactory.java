package com.hnsi.zheng.hnti_erp_app.utils;

import android.graphics.Bitmap;

import com.hnsi.zheng.hnti_erp_app.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class UserIconDisplayOptionsFactory {
    /** 头像图片加载配置 */
    public static DisplayImageOptions getListOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // // 设置图片在下载期间显示的图片
                .showImageOnLoading(R.mipmap.user_icon)
                // // 设置图片Uri为空或是错误的时候显示的图片
                .showImageForEmptyUri(R.mipmap.user_icon)
                // // 设置图片加载/解码过程中错误时候显示的图片
                .showImageOnFail(R.mipmap.user_icon)
                .cacheInMemory(true)
                // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)
                // 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)
                // 设置图片以如何的编码方式显示
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//
                // 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型
                // .decodingOptions(android.graphics.BitmapFactory.Options
                // decodingOptions)//设置图片的解码配置
                .considerExifParams(true)
                // 设置图片下载前的延迟
                // .delayBeforeLoading(int delayInMillis)//int
                // delayInMillis为你设置的延迟时间
                // 设置图片加入缓存前，对bitmap进行设置
                // 。preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                // .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))// 淡入
                .build();
        return options;
    }
}
