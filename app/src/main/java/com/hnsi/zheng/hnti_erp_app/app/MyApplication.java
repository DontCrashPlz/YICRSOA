package com.hnsi.zheng.hnti_erp_app.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.hnsi.zheng.hnti_erp_app.beans.UserEntity;
import com.hnsi.zheng.hnti_erp_app.http.VolleyHelper;
import com.hnsi.zheng.hnti_erp_app.interfaces.IAsyncLoadListener;
import com.hnsi.zheng.hnti_erp_app.utils.SharedPrefUtils;
import com.hnsi.zheng.hnti_erp_app.utils.Tools;
import com.liulishuo.filedownloader.FileDownloader;
import com.nostra13.universalimageloader.cache.disc.impl.ext.LruDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 全局Application类
 * Created by Zheng on 2016/5/12.
 */
public class MyApplication extends Application{
    //存储全局用户信息
    public static UserEntity mUser=new UserEntity();
    //Application是全局单例
    private static MyApplication singleton;
    //获取Application的方法
    public static MyApplication getSingleton(){
        return singleton;
    }
    //与服务器的连接保持
    public static String JSESSIONID="";

    @Override
    public void onCreate() {
        super.onCreate();
        singleton=this;

        //init MyUncatchExceptionHandler
        MyUncatchExceptionHandler mUncatchExceptionHandler=MyUncatchExceptionHandler.getInstance();
        mUncatchExceptionHandler.init(this);

        initImageloader();

        // 初始化ACache，可以考虑在此初始化时将缓存地址更改为磁盘SD卡(网络请求的缓存默认放在data/data目录下了)
        //此处初始化Acache创建放置图片缓存的文件夹
        File cacheDir = new File(getApplicationContext().getFilesDir().getAbsolutePath()+"/myCache");
        ACache.get(cacheDir, 20 * 1024 * 1024, Integer.MAX_VALUE);

        Log.e("fileDir", cacheDir.getAbsolutePath());

        FileDownloader.init(getApplicationContext());

    }

    /**
     * 获取应用图片缓存地址
     * @return
     */
    public String getAppImageCacheFolder(){
        String imageCache = (String) SharedPrefUtils.get(
                this,
                "image_cache",
                AppConstants.APP_COVER_IMAGE_FOLDER);

        return imageCache;
    }

    /**
     * 配置ImageLoader
     */
    private void initImageloader(){

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(this);
        // 设置线程池大小
        config.threadPoolSize(3);
        // 设置线程优先级
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        // 设置对于同一个URL在内存中仅保存一份数据
        config.denyCacheImageMultipleSizesInMemory();
        try {
            config.diskCache(new LruDiskCache(
                    new File(""+getAppImageCacheFolder()),// 设置磁盘图片缓存地址
                    new Md5FileNameGenerator(), // 名称生成器
                    50 * 1024 * 1024));//磁盘缓存大小
        } catch (IOException e) {
            e.printStackTrace();
        }
        //config.writeDebugLogs();

        // 将config设置给ImageLoader
        ImageLoader.getInstance().init(config.build());

    }

    //判断是不是第一次打开
    public static boolean isFirst(){
        return (boolean) SharedPrefUtils.get(getSingleton(),"ISFIRST", true);
    }

    //初次打开之后改变标识
    public static void isNotFirst(){
        SharedPrefUtils.put(getSingleton(), "ISFIRST", false);
    }

    public static boolean isLoadContacts(){
        return (boolean) SharedPrefUtils.get(getSingleton(),"is_contacts_loaded", true);
    }

    public static void isNotLoadContacts(){
        SharedPrefUtils.put(getSingleton(), "is_contacts_loaded", false);
    }

    /**
     * 应用终止时释放FileDownloader
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /** 用于纪录当前正在下载队列中的附件下载任务集合 */
    private Map<String,Integer> DownloadQueue=new HashMap();
    /** 向下载队列中添加一条记录 */
    public void put2Queue(String key,Integer value){
        DownloadQueue.put(key, value);
    }
    /** 向下载队列中添加一条记录 */
    public Integer getDownloadId(String key){
        return DownloadQueue.get(key);
    }
    /** 移除下载队列中的一条记录 */
    public void removeFromQueue(String key){
        DownloadQueue.remove(key);
    }
    /** 查询下载队列中是否包含指定元素 */
    public boolean containInQueue(String key){
        return DownloadQueue.containsKey(key);
    }
    public Map getMap(){
        return DownloadQueue;
    }


    /**
     * 此方法用于登录
     * @param context
     * @param userName
     * @param password
     * @param listener 异步监听，成功回调
     */
    public void doLogin(final Context context,
                           final String userName,
                           final String password,
                           final IAsyncLoadListener<UserEntity>listener){

        if ("".equals(userName)) {
            Toast.makeText(context, "请输入用户名", Toast.LENGTH_SHORT).show();
            listener.onFailure("");
        } else if ("".equals(password)) {
            Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
            listener.onFailure("");
        } else {

            String url = Tools.jointIpAddress()
                    + NetConstants.LOGIN_PORT
                    + "?"
                    + NetConstants.LOGIN_PARAM_ONE
                    + "="
                    + userName
                    + "&"
                    + NetConstants.LOGIN_PARAM_TWO
                    + "="
                    + password;
            Log.e("url", url);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {

                    try {
                        if (jsonObject.getBoolean("success")) {

                            SharedPrefUtils.put(context, "username", userName);
                            if ((boolean) SharedPrefUtils.get(context, "RemeberPassword", false)) {
                                SharedPrefUtils.put(context, "password", password);
                            }

                            JSONObject userInfo = jsonObject.getJSONObject("userInfo");

                            try{
                                if(userInfo.getString("empname")!=null){
                                    MyApplication.mUser.setEmpname(userInfo.getString("empname"));
                                }else{
                                    MyApplication.mUser.setEmpname("未知");
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                                MyApplication.mUser.setEmpname("未知");
                            }

                            try{
                                if(userInfo.getString("mobileno")!=null){
                                    MyApplication.mUser.setMobileno(userInfo.getString("mobileno"));
                                }else{
                                    MyApplication.mUser.setMobileno("未知");
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                                MyApplication.mUser.setMobileno("未知");
                            }

                            try{
                                if(userInfo.getString("otel")!=null){
                                    MyApplication.mUser.setOtel(userInfo.getString("otel"));
                                }else{
                                    MyApplication.mUser.setOtel("未知");
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                                MyApplication.mUser.setOtel("未知");
                            }

                            try{
                                if(userInfo.getString("oemail")!=null){
                                    MyApplication.mUser.setOemail(userInfo.getString("oemail"));
                                }else{
                                    MyApplication.mUser.setOemail("未知");
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                                MyApplication.mUser.setOemail("未知");
                            }

                            try{
                                if(userInfo.getString("sex")!=null){
                                    MyApplication.mUser.setSex(userInfo.getString("sex"));
                                }else{
                                    MyApplication.mUser.setSex("未知");
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                                MyApplication.mUser.setSex("未知");
                            }

                            try{
                                if(userInfo.getString("orgname")!=null){
                                    MyApplication.mUser.setOrgname(userInfo.getString("orgname"));
                                }else{
                                    MyApplication.mUser.setOrgname("未知");
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                                MyApplication.mUser.setOrgname("未知");
                            }

                            try{
                                if(userInfo.getString("posiname")!=null){
                                    MyApplication.mUser.setPosiname(userInfo.getString("posiname"));
                                }else{
                                    MyApplication.mUser.setPosiname("未知");
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                                MyApplication.mUser.setPosiname("未知");
                            }

                            try{
                                if(userInfo.getString("headimg")!=null){
                                    String headimgUrl = userInfo.getString("headimg").replaceAll("'\'", "");
                                    Log.e("headimgUrl", headimgUrl);
                                    MyApplication.mUser.setHeadimg(Tools.jointIpAddress() + headimgUrl);
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }

                            listener.onSuccess(null);

                        } else {
                            Toast.makeText(context, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            listener.onFailure("");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        listener.onFailure("");
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(context, "网络请求失败，请检查网络", Toast.LENGTH_SHORT).show();
                    listener.onFailure("");
                }
            }) {
                /**初次登录时将网络请求响应的响应头中的Cookie值取出来存在MyApplication中，
                 * 以后的每次网络请求都将这个Cookie值加到请求头中，用来使服务器识别当前用户*/
                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

                    Map<String, String> responseHeaders = response.headers;
                    String rawCookies = responseHeaders.get("Set-Cookie");
                    Log.e("Cookie", rawCookies);
                    MyApplication.JSESSIONID = rawCookies;
                    return super.parseNetworkResponse(response);
                }
            };
            //设置网络请求超时时间
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 1, 1.0f));

            VolleyHelper.getInstance(context).addRequestTask(jsonObjectRequest, NetConstants.TAG_LOGIN);
        }

    }

    /********************************* ActivityManager ***********************************/

    //定义全局Activity栈
    private static Stack <Activity> activityStack = new Stack<>();

    /**
     * add Activity 添加Activity到栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * get current Activity 获取当前Activity（栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 指定Activity是否存在栈中
     * @param cls
     * @return
     */
    public boolean isActivityExist(Class<?> cls){
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            // 销毁所有activity
            finishAllActivity();
        } catch (Exception e) {
            // do noting.
        }
    }

}
