package com.hnsi.zheng.hnti_erp_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.app.NetConstants;
import com.hnsi.zheng.hnti_erp_app.http.MyJsonObjectRequest;
import com.hnsi.zheng.hnti_erp_app.http.VolleyHelper;
import com.hnsi.zheng.hnti_erp_app.utils.Tools;
import com.hnsi.zheng.hnti_erp_app.widgets.progressDialog.BaseProgressDialog;
import com.hnsi.zheng.hnti_erp_app.widgets.progressDialog.ProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 消息详情展示页
 * Created by Zheng on 2016/5/10.
 */
public class MessageDetailActivity extends MyBaseActivity{
    //标题头返回按钮
    private ImageButton mBackBtn;
    //页面标题
    private TextView mTitleTv;

    //消息标题
    private TextView mMsgTitleTv;
    //消息类型
    private TextView mMsgTypeTv;
    //消息发送人
    private TextView mSendNameTv;
    //消息发送部门
    private TextView mDepartmentTv;
    //消息发送时间
    private TextView mSendTimeTv;
    //消息接收人
    private TextView mReceiveNameTv;
    //消息内容
    private TextView mContentTv;

    //Intent
    Intent intent;
    //加载进度框
    BaseProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);

        intent=getIntent();

        initUI();

        dialog.show();

        int newId=intent.getIntExtra("newsId",0);
        String url= Tools.jointIpAddress()
                + NetConstants.MESSAGE_DETAIL_PORT
                + "?"
                + NetConstants.MESSAGE_DETAIL_PARAM
                + "="
                + newId;

        MyJsonObjectRequest request=new MyJsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            if(jsonObject.getBoolean("success")){
                                JSONObject msgInfo=jsonObject.getJSONObject("data");

                                mMsgTitleTv.setText(msgInfo.getString("title"));

                                int msgType=msgInfo.getInt("msgType");
                                if(msgType==1){
                                    mMsgTypeTv.setText("普通消息");
                                }else if(msgType==2){
                                    mMsgTypeTv.setText("项目反馈");
                                }else if(msgType==3){
                                    mMsgTypeTv.setText("流程反馈");
                                }

                                mSendNameTv.setText(msgInfo.getString("sendEmpname"));

                                mDepartmentTv.setText(msgInfo.getString("sendDeptname"));

                                String startDate=msgInfo.getString("sendTime").substring(0,19);
                                mSendTimeTv.setText(startDate);

                                mReceiveNameTv.setText(msgInfo.getString("receiveEmpname"));

                                mContentTv.setText(msgInfo.getString("content"));

                                dialog.dismiss();

                            }else{
                                dialog.dismiss();
                                Toast.makeText(MessageDetailActivity.this,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                Toast.makeText(MessageDetailActivity.this,"网络请求失败，请检查网络",Toast.LENGTH_SHORT).show();
            }
        });

        //设置网络请求超时时间
        request.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 1, 1.0f));

        VolleyHelper.getInstance(MessageDetailActivity.this).addRequestTask(request, NetConstants.TAG_NEWS_DETAIL);
    }

    /**
     * 初始化界面
     */
    private void initUI() {
        initTitleBar();

        mMsgTitleTv= (TextView) findViewById(R.id.msg_detail_tv_title);
        mMsgTypeTv= (TextView) findViewById(R.id.msg_detail_tv_msgtype);
        mSendNameTv= (TextView) findViewById(R.id.msg_detail_tv_sendname);
        mDepartmentTv= (TextView) findViewById(R.id.msg_detail_tv_department);
        mSendTimeTv= (TextView) findViewById(R.id.msg_detail_tv_sendtime);
        mReceiveNameTv= (TextView) findViewById(R.id.msg_detail_tv_receivename);
        mContentTv= (TextView) findViewById(R.id.msg_detail_tv_content);

        dialog=new ProgressDialog(this);
        dialog.setLabel("请稍等...");
    }

    /**
     * 初始化标题头
     */
    private void initTitleBar() {
        //加载并为返回按钮设置点击事件
        mBackBtn= (ImageButton) findViewById(R.id.titlebar_ibtn_back);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //加载并设置页面标题
        mTitleTv= (TextView) findViewById(R.id.titlebar_tv_title);
        mTitleTv.setText("消息详情");
    }

}
