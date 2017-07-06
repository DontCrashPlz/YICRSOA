package com.hnsi.zheng.hnti_erp_app.widgets;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hnsi.zheng.hnti_erp_app.activities.LoginActivity;
import com.hnsi.zheng.hnti_erp_app.app.MyApplication;
import com.hnsi.zheng.hnti_erp_app.app.NetConstants;
import com.hnsi.zheng.hnti_erp_app.beans.UserEntity;
import com.hnsi.zheng.hnti_erp_app.http.MyJsonObjectRequest;
import com.hnsi.zheng.hnti_erp_app.http.VolleyHelper;
import com.hnsi.zheng.hnti_erp_app.utils.Tools;
import com.hnsi.zheng.hnti_erp_app.widgets.progressDialog.BaseProgressDialog;
import com.hnsi.zheng.hnti_erp_app.widgets.progressDialog.ProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 退出弹窗
 * Created by Zheng on 2016/7/25.
 */
public class LogoutDialog extends AlertDialog{

    //进度加载框
    private static BaseProgressDialog mDialog;

    protected LogoutDialog(Fragment fragment) {
        super(fragment.getContext());
    }

    public static LogoutDialog showDialog(final Fragment fragment) {
        mDialog=new ProgressDialog(fragment.getContext());
        mDialog.setLabel("注销中...");
        final LogoutDialog mDialogInstance = new LogoutDialog(fragment);
        mDialogInstance.setCancelable(true);
        mDialogInstance.setTitle("注销");
        mDialogInstance.setMessage("您确定要注销吗？");
        mDialogInstance.setButton(DialogInterface.BUTTON_NEGATIVE,
                "取消",
                new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {}
                });
        mDialogInstance.setButton(
                DialogInterface.BUTTON_POSITIVE,
                "注销",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDialogInstance.dismiss();
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
        mDialogInstance.show();
        return mDialogInstance;
    }

}
