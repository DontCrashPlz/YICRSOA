package com.hnsi.zheng.hnti_erp_app.http;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;
import com.hnsi.zheng.hnti_erp_app.R;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Volley助手:
 * 1、管理全局的RequestQueue;
 * 2、使用OkHttp作为Volley的底层网络访问实现；
 * 3、其他的全局Volley配置
 * @author Zheng
 *
 */
public class VolleyHelper {

	private Context mAppContext;
	
	private static VolleyHelper mSingleInstance = null;
	//使用OKHttpClient作为网络底层实现
	private OkHttpClient mOkHttpClient = null;
	
	// 全局的请求队列
	private RequestQueue mAppRequestQueue = null;
	// 图片加载
	private ImageLoader mVolleyImageLoader = null;
	// 图片缓存实现
	private LruBitmapCache mLruBitmapCache = null;
	
	//VolleyHelper为全局单例
	private VolleyHelper(Context context){
		this.mAppContext = context.getApplicationContext();
		
		mAppRequestQueue = getAppRequestQueue();
		
		mVolleyImageLoader = getVolleyImageLoader();
		
	}

	public static VolleyHelper getInstance(Context context){
		if (mSingleInstance == null) {
			synchronized (VolleyHelper.class) {
				if (mSingleInstance == null) {
					mSingleInstance = new VolleyHelper(context);
				}
			}
		}
		return mSingleInstance;
	}
	
	/**
	 * 获取应用级请求队列
	 * @return
	 */
	public RequestQueue getAppRequestQueue() {
		if (mAppRequestQueue == null) {
			mOkHttpClient = new OkHttpClient();
			// 自定义OkHttpClient的配置
			mOkHttpClient.setConnectTimeout(5, TimeUnit.SECONDS);
			mOkHttpClient.setReadTimeout(10, TimeUnit.SECONDS);
			mOkHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);
			// 只有在此设置了OkHttpStack之后，
			// 我们的mAppRequestQueue才可以使用OKHttp来做真正的网络访问实现
			mAppRequestQueue = Volley.newRequestQueue(mAppContext,
					new OkHttpStack(mOkHttpClient));
		}
		return mAppRequestQueue;
	}
	
	/**
	 * 添加一个请求任务，即开始访问网络获取数据
	 * @param request 
	 * @param tag 该请求的标识，用来从队列中获取该请求，也用在取消该请求的时候
	 */
	public void addRequestTask(Request<?> request, Object tag){
		// 为请求添加Tag
		request.setTag(tag);
		// 添加到请求队列中
		mAppRequestQueue.add(request);
	}
	
	/**
	 * 取消由Tag标识的所有请求任务
	 * @param tag
	 */
	public void cancelRequestByTag(Object tag){
		mAppRequestQueue.cancelAll(tag);
	}
	
	/**
	 * 获取Volley库中的ImageLoader对象
	 * @return
	 */
	public ImageLoader getVolleyImageLoader() {
		if (mVolleyImageLoader == null) {
			mVolleyImageLoader = new ImageLoader(getAppRequestQueue(), getLruImageCache());
		}
		return mVolleyImageLoader;
	}

	/**
	 * 获取LruImageCache
	 * @return
	 */
	private ImageCache getLruImageCache() {
		 if(mLruBitmapCache == null){
			 mLruBitmapCache = new LruBitmapCache(mAppContext);
		 }
		 return mLruBitmapCache;
	}
	
	/**
	 * 自定义的图片加载方法，利用Volley的ImageLoader实现图片加载
	 * @param url 要加载的图片Url
	 * @param view 要加载到的布局
	 * @param width 图片宽度限制
	 * @param height 图片高度限制
	 */
	public void LoadImage(String url,ImageView view,int width,int height){
		
		if(mVolleyImageLoader==null){
			mVolleyImageLoader=getVolleyImageLoader();
		}
		
		@SuppressWarnings("static-access")
		ImageListener listener=mVolleyImageLoader
				.getImageListener(view, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
				
		mVolleyImageLoader.get(url, listener,width,height);
	}
	
	/**
	 * 自定义的图片加载方法，利用Volley的ImageLoader实现图片加载
	 * @param url 要加载的图片Url
	 * @param view 要加载到的布局
	 */
	public void LoadImage(String url,ImageView view){
		LoadImage(url, view, 0, 0);
	}

	/**
	 * 组装Url的方法（一个参数的情况）
	 * @return
	 */
	public String fullURL(String baseUrl,String paramKey,String paramValue){
		return baseUrl+"?"+paramKey+"="+paramValue;
	}

	/**
	 * 组装Url的方法（两个参数的情况）
	 * @return
	 */
	public String fullURL(String baseUrl,String firstParamKey,String firstParamValue,
						  				 String secondParamKey,String secondParamValue){
		return baseUrl+"?"+firstParamKey+"="+firstParamValue
				  	  +"&"+secondParamKey+"="+secondParamValue;
	}

	/**
	 * 组装Url的方法（三个参数的情况）
	 * @return
	 */
	public String fullURL(String baseUrl,String firstParamKey,String firstParamValue,
						  String secondParamKey,String secondParamValue,
	 					  String thirdParamKey,String thirdParamValue){
		return baseUrl+"?"+firstParamKey+"="+firstParamValue
					  +"&"+secondParamKey+"="+secondParamValue
					  +"&"+thirdParamKey+"="+thirdParamValue;
	}
	
}
