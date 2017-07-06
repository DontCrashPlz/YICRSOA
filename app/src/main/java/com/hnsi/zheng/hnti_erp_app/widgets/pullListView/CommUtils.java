package com.hnsi.zheng.hnti_erp_app.widgets.pullListView;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

public class CommUtils {
	public static void measureView(View v){
		if(v == null){
			return;
		}
		int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); 
	    int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
	    v.measure(w, h);
	}
	
	public static String dateFormatYMDHMS = "yyyy-MM-dd HH:mm:ss";
	
	public static String dateFormatYMD = "yyyy-MM-dd";
	
	public static String dateFormatYM = "yyyy-MM";
	
	public static String dateFormatYMDHM = "yyyy-MM-dd HH:mm";
	
	public static String dateFormatMDHM = "MM月dd日 HH:mm";
	
	public static String dateFormatMD = "MM/dd";
	
	public static String dateFormatHMS = "HH:mm:ss";
	
	public static String dateFormatHM = "HH:mm";

	public static String getCurrentDate(String format) {
		
		String curDateTime = null;
		try {
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
			Calendar c = new GregorianCalendar();
			curDateTime = mSimpleDateFormat.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return curDateTime;
	}
	
	
	public static Bitmap getBitmapFormSrc(String src){
		Bitmap bit = null;
		try {
			bit = BitmapFactory.decodeStream(CommUtils.class.getResourceAsStream(src));
	    } catch (Exception e) {
		}
		return bit;
	}
}
