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
 * 规章制度详情展示页
 * Created by Zheng on 2016/5/10.
 */
public class RulesDetailActivity extends MyBaseActivity{
    /** 标题头控件 */
    //标题头返回按钮
    private ImageButton mBackBtn;
    //页面标题
    private TextView mTitleTv;

    /** 规章制度内容显示区控件 */
    //规章制度标题
    private TextView mRulesTitleTv;
    //规章制度发布人
    private TextView mAuthorTv;
    //规章制度发布时间
    private TextView mTimeTv;
    //规章制度内容
    private WebView mContentWv;

    //Intent
    Intent intent;
    //加载进度框
    BaseProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_detail);

        intent=getIntent();

        initUI();

        dialog.show();

        int newId=intent.getIntExtra("param_id",0);

        String url= Tools.jointIpAddress()
                + NetConstants.RULES_DETAIL_PORT
                + "?"
                + NetConstants.RULES_DETAIL_PARAM
                + "="
                + newId;

        Log.e("rulesUrl",url);

        MyJsonObjectRequest request=new MyJsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONObject rule=jsonObject.getJSONObject("data");

                            mRulesTitleTv.setText(rule.getString("title"));
                            mAuthorTv.setText(rule.getString("operatorName"));
                            String addTime=rule.getString("operationTime").substring(0, 19);
                            mTimeTv.setText(addTime);

//                            StringBuilder rulesFilesHTMLStr=new StringBuilder();
//                            String fileStr=rule.getString("files");
//                            String[]files=fileStr.split(",");
//                            Log.e("files",files[0]);
//                            for(String file:files){
//                                String[]fileDetail=file.split("\\|");
//                                Log.e("fileDetail",file+"|"+fileDetail[0]+"|"+fileDetail[1]);
//                                rulesFilesHTMLStr.append(Tools.buildRulesFilesHTML(fileDetail[1],fileDetail[0]));
//                            }
//                            Log.e("fileStr",rulesFilesHTMLStr.toString());

                            String contentStr=rule.getString("content")
                                    .replaceAll("img src=\"", "img style=\" width:100%%; height:auto;\" src=\"" + Tools.jointIpAddress())
                                    .replaceAll("href=\"", "href=\"" + Tools.jointIpAddress());
//                                    +rulesFilesHTMLStr.toString();
//                                    .replaceAll("line-height:(.*?);", "line-height: 180%;")
//                                    .replaceAll("text-indent:(.*?);", "text-indent: 2em;")
//                                    .replaceAll("font-size:(.*?);", "font-size: 16px;");

                            Log.e("contentStr", contentStr);
//                            mContentWv.loadData(news.getString("content"), "text/html", "UTF-8");
                            mContentWv.loadDataWithBaseURL("", contentStr, "text/html", "UTF-8",null);
//                            mContentWv.loadUrl(Tools.jointIpAddress()+NetConstants.RULES_FILES_PORT+"16208");

                            dialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                Toast.makeText(RulesDetailActivity.this,"网络请求失败，请检查网络",Toast.LENGTH_SHORT).show();
            }
        });

        //设置网络请求超时时间
        request.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 1, 1.0f));

        VolleyHelper.getInstance(RulesDetailActivity.this).addRequestTask(request, NetConstants.TAG_NEWS_DETAIL);
    }

    /**
     * 初始化界面
     */
    private void initUI() {
        initTitleBar();

        initContentArea();

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
        mTitleTv.setText("规章制度详情");
    }

    /**
     * 初始化内容显示区
     */
    private void initContentArea() {
        //加载并设置公告标题
        mRulesTitleTv = (TextView) findViewById(R.id.rules_detail_tv_title);

        //加载并设置公告发布人
        mAuthorTv= (TextView) findViewById(R.id.rules_detail_tv_author);

        //加载并设置公告发布时间
        mTimeTv= (TextView) findViewById(R.id.rules_detail_tv_time);

        //加载并设置公告内容
        mContentWv= (WebView) findViewById(R.id.rules_detail_wv_content);
        // 设置WebView
        WebSettings settings = mContentWv.getSettings();
        // 设置可以运行JS脚本!!!!
        settings.setJavaScriptEnabled(true);
        settings.setDefaultFontSize(16);
        // 设置文本编码
        settings.setDefaultTextEncodingName("UTF-8");
		/*
		 * LayoutAlgorithm是一个枚举用来控制页面的布局，有三个类型：
		 * 1.NARROW_COLUMNS：可能的话使所有列的宽度不超过屏幕宽度
		 * 2.NORMAL：正常显示不做任何渲染
		 * 3.SINGLE_COLUMN：把所有内容放大webview等宽的一列中
		 * 用SINGLE_COLUMN类型可以设置页面居中显示，
		 * 页面可以放大缩小，但这种方法不怎么好，
		 * 有时候会让你的页面布局走样而且我测了一下，只能显示中间那一块，超出屏幕的部分都不能显示。
		 */
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(false);// 用于设置webview放大
        settings.setBuiltInZoomControls(false);

    }

}
