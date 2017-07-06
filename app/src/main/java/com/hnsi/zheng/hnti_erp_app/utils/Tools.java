package com.hnsi.zheng.hnti_erp_app.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.activities.LoginActivity;
import com.hnsi.zheng.hnti_erp_app.app.MyApplication;
import com.hnsi.zheng.hnti_erp_app.app.NetConstants;
import com.hnsi.zheng.hnti_erp_app.beans.UserEntity;
import com.hnsi.zheng.hnti_erp_app.http.MyJsonObjectRequest;
import com.hnsi.zheng.hnti_erp_app.http.VolleyHelper;
import com.hnsi.zheng.hnti_erp_app.widgets.progressDialog.BaseProgressDialog;
import com.hnsi.zheng.hnti_erp_app.widgets.progressDialog.ProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 全局工具类
 * 这里定义一些全局使用的工具方法
 * Created by Zheng on 2016/5/4.
 */
public class Tools {

    /**
     * 界面跳转的方法
     * @param context 上下文环境
     * @param cls 要跳转到的界面
     */
    public static void skipActivity(Context context,Class cls){
        Intent intent=new Intent();
        intent.setClass(context,cls);
        context.startActivity(intent);
    }

    /**
     * 拼接IP地址的方法，将用户输入的ip地址和端口号拼接在一起
     * @return
     */
    public static String jointIpAddress(){
        StringBuilder builder=new StringBuilder();
        builder.append("http://");
        builder.append((String) SharedPrefUtils.get(MyApplication.getSingleton().getApplicationContext(), "ipAddress", "192.168.1.68"));
        builder.append(":");
        builder.append((String)SharedPrefUtils.get(MyApplication.getSingleton().getApplicationContext(),"portNum","80"));
        String ipAddress=builder.toString();
        return ipAddress;
    }

    /**
     * 构建规章制度中附件HTML代码的方法
     * @param urlId
     * @param titleName
     * @return
     */
    public static String buildRulesFilesHTML(String urlId,String titleName){
        StringBuilder builder=new StringBuilder();
        builder.append("<p style=\"line-height: 16px;\"><img style=\"vertical-align: middle; margin-right: 2px;\" src=\"http://192.168.1.68:80/default/erp/extention/UeEditor/dialogs/attachment/fileTypeImages/icon_doc.gif\"/><a style=\"font-size:12px; color:#0066cc;\" href=\"http://192.168.1.68:80/default/erp/common/fileUpload/download.jsp?isOpen=false&fileid=");
        builder.append(urlId);
        builder.append("\">");
        builder.append(titleName);
        builder.append("</a></p>");
        String rulesFilesHTMLStr=builder.toString();
        return rulesFilesHTMLStr;
    }

    /**
     * 组建天气接口url
     * @param province
     * @param city
     * @return
     */
    public static String buildWeatherUrl(String province,String city) {

        try {
            StringBuilder builder=new StringBuilder();
            builder.append(NetConstants.WEATHER_PORT);
            builder.append(URLEncoder.encode(province,"utf-8"));
            builder.append(URLEncoder.encode("|","utf-8"));
            builder.append(URLEncoder.encode(city,"utf-8"));
//            String address=URLEncoder.encode(province+" | "+city, "utf-8");
//            builder.append(address);
//            builder.append(province);
//            builder.append("|");
//            builder.append(city);
            builder.append(".html");
//            String address=URLEncoder.encode(builder.toString(), "utf-8");
            return builder.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 显示正在开发弹窗
     */
    public static void showDevelopingDialog(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_developing_dialog, null);
        // 设置style 控制默认dialog带来的边距问题
        final Dialog dialog = new Dialog(context, R.style.common_dialog);
        dialog.setContentView(view);
        dialog.show();

        // 监听
        view.findViewById(R.id.layout_developing_dialog_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // 设置相关位置，一定要在 show()之后
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    /**
     * 显示退出应用弹窗
     */
    public static void showExitDialog(Activity activity){
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_exit_dialog, null);
        // 设置style 控制默认dialog带来的边距问题
        final Dialog dialog = new Dialog(activity, R.style.common_dialog);
        dialog.setContentView(view);
        dialog.show();

        // 取消按钮监听
        view.findViewById(R.id.layout_exit_dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // 确定按钮监听
        view.findViewById(R.id.layout_exit_dialog_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                MyApplication.getSingleton().AppExit();
                System.exit(0);
            }
        });

        // 设置相关位置，一定要在 show()之后
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    /**
     * 显示注销弹窗
     */
    public static void showLogoutDialog(final Fragment fragment){
        //进度加载框
        final BaseProgressDialog mDialog=new ProgressDialog(fragment.getContext());
        mDialog.setLabel("注销中...");
        View view = LayoutInflater.from(fragment.getContext()).inflate(R.layout.layout_logout_dialog, null);
        // 设置style 控制默认dialog带来的边距问题
        final Dialog dialog = new Dialog(fragment.getContext(), R.style.common_dialog);
        dialog.setContentView(view);
        dialog.show();

        // 取消按钮监听
        view.findViewById(R.id.layout_logout_dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // 确定按钮监听
        view.findViewById(R.id.layout_logout_dialog_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消弹窗
                dialog.dismiss();
                //显示加载转动条
                mDialog.show();

                String url = Tools.jointIpAddress()
                        + NetConstants.LOGOUT_PORT;

                MyJsonObjectRequest request = new MyJsonObjectRequest(url,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                try {
                                    if (jsonObject.getBoolean("success")) {
                                        Intent intent = new Intent();
                                        intent.setClass(fragment.getContext(), LoginActivity.class);

                                        mDialog.dismiss();

                                        fragment.startActivity(intent);
                                        Toast.makeText(fragment.getContext(), "注销成功", Toast.LENGTH_SHORT).show();
                                        fragment.getActivity().finish();
                                        MyApplication.JSESSIONID = "";
                                        MyApplication.mUser = new UserEntity();
                                    } else {
                                        mDialog.dismiss();
                                        Toast.makeText(fragment.getContext(), "注销失败", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        mDialog.dismiss();
                        Toast.makeText(fragment.getContext(), "网络请求失败，请检查网络", Toast.LENGTH_SHORT).show();
                    }
                });

                request.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 1, 1.0f));

                VolleyHelper.getInstance(fragment.getContext()).addRequestTask(request, NetConstants.TAG_LOGOUT);
            }
        });

        // 设置相关位置，一定要在 show()之后
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }


    /**
     * 显示清除缓存或附件的提示弹窗
     * @param context
     * @param textView 此控件用于清除后重新计算大小并显示
     * @param tag 此标记用于判断清除缓存或清除附件，0=缓存，1=附件
     */
    public static void showCleanCacheDialog(final Context context, final TextView textView, final int tag){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_cleancache_dialog, null);
        // 设置style 控制默认dialog带来的边距问题
        final Dialog dialog = new Dialog(context, R.style.common_dialog);
        dialog.setContentView(view);
        dialog.show();

        if(0==tag){
            ((TextView)view.findViewById(R.id.layout_cleancache_dialog_tip))
                    .setText(context.getResources().getString(R.string.clean_caches_tip));
        }else if(1==tag){
            ((TextView)view.findViewById(R.id.layout_cleancache_dialog_tip))
                    .setText(context.getResources().getString(R.string.clean_files_tip));
        }

        // 取消按钮监听
        view.findViewById(R.id.layout_cleancache_dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // 确定按钮监听
        view.findViewById(R.id.layout_cleancache_dialog_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(0==tag){
                    DataCleanManager.cleanAppCache(context);
                    textView.setText(CommonUtils.getFormatSize(DataCleanManager.getAppCacheSize(context)));
                }else if(1==tag){
                    FilesCleanManager.cleanAppCache(context);
                    textView.setText(CommonUtils.getFormatSize(FilesCleanManager.getAppFilesSize()));
                }

            }
        });

        // 设置相关位置，一定要在 show()之后
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

//    /**
//     * 获取手机SD卡路径
//     * @return
//     */
//    @SuppressLint("SdCardPath")
//    public static String getSDCardPath() {
//        String sdcard_path = null;
//        String sd_default = Environment.getExternalStorageDirectory()
//                .getAbsolutePath();
//        Log.e("text", sd_default);
//        if (sd_default.endsWith("/")) {
//            sd_default = sd_default.substring(0, sd_default.length() - 1);
//        }
//        // 得到路径
//        try {
//            Runtime runtime = Runtime.getRuntime();
//            Process proc = runtime.exec("mount");
//            InputStream is = proc.getInputStream();
//            InputStreamReader isr = new InputStreamReader(is);
//            String line;
//            BufferedReader br = new BufferedReader(isr);
//            while ((line = br.readLine()) != null) {
//                if (line.contains("secure"))
//                    continue;
//                if (line.contains("asec"))
//                    continue;
//                if (line.contains("fat") && line.contains("/mnt/")) {
//                    String columns[] = line.split(" ");
//                    if (columns != null && columns.length > 1) {
//                        if (sd_default.trim().equals(columns[1].trim())) {
//                            continue;
//                        }
//                        sdcard_path = columns[1];
//                    }
//                } else if (line.contains("fuse") && line.contains("/mnt/")) {
//                    String columns[] = line.split(" ");
//                    if (columns != null && columns.length > 1) {
//                        if (sd_default.trim().equals(columns[1].trim())) {
//                            continue;
//                        }
//                        sdcard_path = columns[1];
//                    }
//                }
//            }
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        Log.e("text", sdcard_path);
//        return sdcard_path;
//    }

}
