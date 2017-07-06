package com.hnsi.zheng.hnti_erp_app.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.storage.StorageManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.app.AppConstants;
import com.hnsi.zheng.hnti_erp_app.app.MyApplication;
import com.hnsi.zheng.hnti_erp_app.app.NetConstants;
import com.hnsi.zheng.hnti_erp_app.beans.UpdateInfoEntity;
import com.hnsi.zheng.hnti_erp_app.beans.UserEntity;
import com.hnsi.zheng.hnti_erp_app.interfaces.IAsyncLoadListener;
import com.hnsi.zheng.hnti_erp_app.utils.SharedPrefUtils;
import com.hnsi.zheng.hnti_erp_app.utils.Tools;
import com.hnsi.zheng.hnti_erp_app.utils.UpdateInfoParser;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 闪屏页面
 * Created by Zheng on 2016/7/7.
 */
public class SplashActivity extends MyBaseActivity {
    private final int NEED_UPDATE=0x11;//需要更新
    private final int UNNEED_UPDATE=0x12;//不需要更新
    private final int UPDATE_SERVICE_EXCEPTION=0x13;//更新服务出现异常

    private UpdateInfoEntity info;

    private Handler mMainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            final Intent intent = new Intent(Intent.ACTION_MAIN);
            if (MyApplication.isFirst()) {
                intent.setClass(getApplication(), GuideViewActivity.class);
            } else {
                intent.setClass(getApplication(), LoginActivity.class);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            //根据message向intent中存入对应信息
            switch (msg.what) {
                case NEED_UPDATE://需要更新
                    intent.putExtra(AppConstants.UPDATE_INFO_KEY,AppConstants.UPDATE_NEED);
                    intent.putExtra(AppConstants.UPDATE_INFO_ENTITY_KEY,info);
                    startActivity(intent);
                    finish();
                    break;
                case UNNEED_UPDATE://不需要更新
                    intent.putExtra(AppConstants.UPDATE_INFO_KEY,AppConstants.UPDATE_UNNEED);
                    //如果不需要更新，且之前选择自动登录，就自动进行一次登陆操作
                    if((boolean)SharedPrefUtils.get(getApplication(),"AutomaticLogin",false)){
                        //Todo 如果之前选择自动登录，此处尝试登录，登录成功直接进入主界面，失败则进入登陆界面
                        String userName= (String) SharedPrefUtils.get(getApplication(),"username","");
                        String password= (String) SharedPrefUtils.get(getApplication(),"password","");
                        if(!"".equals(userName)
                                &!"null".equals(userName)
                                &!"".equals(password)
                                &!"null".equals(password)){
                            MyApplication.getSingleton().doLogin(
                                    getApplication(),
                                    userName,
                                    password,
                                    new IAsyncLoadListener<UserEntity>() {
                                @Override
                                public void onSuccess(UserEntity userEntity) {
                                    intent.setClass(getApplication(), HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void onFailure(String msg) {
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }
                    }else{//如果之前没有选择自动登录，正常进入登录界面
                        startActivity(intent);
                        finish();
                    }
                    break;
                case UPDATE_SERVICE_EXCEPTION://更新服务出现异常
                    intent.putExtra(AppConstants.UPDATE_INFO_KEY,AppConstants.UPDATE_EXCEPTION);
                    //如果更新服务器，且之前选择自动登录，就自动进行一次登陆操作
                    if((boolean)SharedPrefUtils.get(getApplication(),"AutomaticLogin",false)){
                        String userName= (String) SharedPrefUtils.get(getApplication(),"username","");
                        String password= (String) SharedPrefUtils.get(getApplication(),"password","");
                        if(!"".equals(userName)
                                &!"null".equals(userName)
                                &!"".equals(password)
                                &!"null".equals(password)){
                            MyApplication.getSingleton().doLogin(
                                    getApplication(),
                                    userName,
                                    password,
                                    new IAsyncLoadListener<UserEntity>() {
                                        @Override
                                        public void onSuccess(UserEntity userEntity) {
                                            intent.setClass(getApplication(), HomeActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }

                                        @Override
                                        public void onFailure(String msg) {
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                        }
                    }else{
                        startActivity(intent);
                        finish();
                    }
                    break;
            }

        }
    };

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.
                FLAG_FULLSCREEN);
        getWindow().setBackgroundDrawableResource(R.mipmap.splash);
//        mMainHandler.sendEmptyMessageDelayed(0, 2000);
        //延时一秒后执行更新检测线程
//        mMainHandler.postDelayed(new CheckVersionTask(), 1 * 1000);

        new Thread(new CheckVersionTask()).start();
    }

//    /**
//     * 获取手机当前挂载的所有储存路径
//     * @param context
//     * @return 存放所有路径的数组
//     */
//    public String[] listSDRootPath(Context context) {
//        try {
//            StorageManager manager = (StorageManager) context.getSystemService(Activity.STORAGE_SERVICE);
//            Method method = manager.getClass().getMethod("getVolumePaths");
//            return (String[]) method.invoke(manager);
//        } catch (Exception e) {
//            return null;
//        }
//    }

    @Override
    public void onBackPressed() {

    }


    /**
     * 从服务器获取xml解析并进行比对版本号
     */
    public class CheckVersionTask implements Runnable{
        public void run() {
            Message msg = new Message();
            try {
                Thread.sleep(2*1000);
                //从资源文件获取服务器 地址
                String path = Tools.jointIpAddress()+NetConstants.UPDATEINFOXML;
                Log.e("downloadUrl",path);
                //包装成url的对象
                URL url = new URL(path);
                HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                InputStream is =conn.getInputStream();
                info =  UpdateInfoParser.getUpdataInfo(is);

                if(info.getVersion().equals(getVersionName())){
                    msg.what=UNNEED_UPDATE;
                    mMainHandler.sendMessage(msg);
                }else{
                    msg.what=NEED_UPDATE;
                    mMainHandler.sendMessage(msg);
                }
            } catch (Exception e) {
                // 更新服务出现异常
                msg.what = UPDATE_SERVICE_EXCEPTION;
                mMainHandler.sendMessage(msg);
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取当前程序的版本号
     */
    private String getVersionName() throws Exception{
        //获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        return packInfo.versionName;
    }

}
