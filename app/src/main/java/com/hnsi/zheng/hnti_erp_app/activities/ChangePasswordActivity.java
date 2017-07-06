package com.hnsi.zheng.hnti_erp_app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.app.MyApplication;
import com.hnsi.zheng.hnti_erp_app.app.NetConstants;
import com.hnsi.zheng.hnti_erp_app.http.MyJsonObjectRequest;
import com.hnsi.zheng.hnti_erp_app.http.VolleyHelper;
import com.hnsi.zheng.hnti_erp_app.utils.SharedPrefUtils;
import com.hnsi.zheng.hnti_erp_app.utils.Tools;
import com.hnsi.zheng.hnti_erp_app.widgets.progressDialog.BaseProgressDialog;
import com.hnsi.zheng.hnti_erp_app.widgets.progressDialog.ProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 密码修改界面
 * Created by Zheng on 2016/5/3.
 */
public class ChangePasswordActivity extends MyBaseActivity implements View.OnClickListener{
    //标题头返回按钮
    ImageButton mBackBtn;
    //页面标题
    TextView mTitleTv;
    //原密码输入框
    EditText mOldPasswordEt;
    //新密码输入框
    EditText mNewPasswordEt;
    //再一次新密码输入框
    EditText mNewAgainPasswordEt;
    //完成按钮
    Button mCompleteBtn;

    //加载进度框
    BaseProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        intiUI();
    }

    /**
     * 初始化界面
     */
    private void intiUI() {
        initTitleBar();

        //加载原密码输入框
        mOldPasswordEt= (EditText) findViewById(R.id.change_password_et_old);

        //加载新密码输入框
        mNewPasswordEt= (EditText) findViewById(R.id.change_password_et_new);

        //加载新密码再一次输入框
        mNewAgainPasswordEt= (EditText) findViewById(R.id.change_password_et_new_again);

        //加载并为完成按钮设置点击事件
        mCompleteBtn= (Button) findViewById(R.id.change_password_btn_complete);
        mCompleteBtn.setOnClickListener(this);

        dialog=new ProgressDialog(this);
        dialog.setLabel("请稍等...");
    }

    /**
     * 初始化标题头
     */
    private void initTitleBar() {
        //加载并为返回按钮设置点击事件
        mBackBtn= (ImageButton) findViewById(R.id.titlebar_ibtn_back);
        mBackBtn.setOnClickListener(this);

        //加载并设置页面标题
        mTitleTv= (TextView) findViewById(R.id.titlebar_tv_title);
        mTitleTv.setText("修改密码");
    }

    /**
     * 设置本页面的点击事件
     * @param v 被点击的控件
     */
    @Override
    public void onClick(View v) {
        //以控件ID作为标识
        int vId=v.getId();

        switch (vId){
            case R.id.titlebar_ibtn_back:{//控件点击事件（返回按钮）
                finish();
            }break;
            case R.id.change_password_btn_complete:{//控件点击事件（完成按钮）
                String oldPassword=mOldPasswordEt.getText().toString().trim();
                String newPassword=mNewPasswordEt.getText().toString().trim();
                String newAgainPassword=mNewAgainPasswordEt.getText().toString().trim();

                if(newPassword.equals(newAgainPassword)){

                    dialog.show();

                    String url= Tools.jointIpAddress()
                            + NetConstants.CHANGE_PASSWORD_PORT
                            + "?"
                            + NetConstants.CHANGE_PASSWORD_PARAM_ONE
                            + "="
                            + oldPassword
                            + "&"
                            + NetConstants.CHANGE_PASSWORD_PARAM_TWO
                            + "="
                            + newAgainPassword;

                    MyJsonObjectRequest jsonObjectRequest=new MyJsonObjectRequest(url,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    try {
                                        if(jsonObject.getBoolean("success")){
                                            dialog.dismiss();
                                            Toast.makeText(ChangePasswordActivity.this,"密码修改成功！",Toast.LENGTH_SHORT).show();
                                            finish();
                                        }else {
                                            dialog.dismiss();
                                            Toast.makeText(ChangePasswordActivity.this,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            dialog.dismiss();
                            Toast.makeText(ChangePasswordActivity.this,"网络请求失败，请检查网络",Toast.LENGTH_SHORT).show();
                        }
                    });

                    //设置网络请求超时时间
                    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 1, 1.0f));

                    VolleyHelper.getInstance(ChangePasswordActivity.this).addRequestTask(jsonObjectRequest, NetConstants.TAG_CHANGE_PASSWORD);

                }else {
                    Toast.makeText(ChangePasswordActivity.this,"请确认两次输入的新密码一致",Toast.LENGTH_SHORT).show();
                }
            }break;
        }
    }

    /**
     * 显示进度框的方法
     */
//    public void showProgressDialog(){
//        mDialog=Tools.createLoadingProgressDialog(ChangePasswordActivity.this, "正在请求服务器...", new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                /** 取消网络请求，Toast网络请求已中断 */
//                VolleyHelper.getInstance(ChangePasswordActivity.this).cancelRequestByTag(NetConstants.TAG_CHANGE_PASSWORD);
//                Toast.makeText(ChangePasswordActivity.this,"网络请求已取消",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        mDialog.show();
//    }

    /**
     *取消进度框的方法
     */
//    public void dismissProgressDialog(){
//        if(mDialog==null){
//            return;
//        }else{
//            mDialog.dismiss();
//        }
//    }

}
