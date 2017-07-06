package com.hnsi.zheng.hnti_erp_app.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.adapters.NoticeAdapter;
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
 * 公告详情展示页
 * Created by Zheng on 2016/5/10.
 */
public class NoticeDetailActivity extends MyBaseActivity{
    /** 标题头控件 */
    //标题头返回按钮
    ImageButton mBackBtn;
    //页面标题
    TextView mTitleTv;

    /** 公告内容显示区控件 */
    //公告标题
    TextView mNoticeTitleTv;
    //公告发布部门
    TextView mDepartmentTv;
    //公告发布人
    TextView mAuthorTv;
    //公告发布时间
    TextView mTimeTv;
    //公告内容
    WebView mContentWv;

    /** 底部按钮显示区控件 */
    //阅读人数按钮
    LinearLayout mReadButtonLly;
    //评论人数按钮
    LinearLayout mCommentButtonLly;
    //阅读按钮和评论按钮分隔符
    View mDivide;
    //阅读人数
    TextView mReadNumTv;
    //评论人数
    TextView mCommentNumTv;
    //Intent
    Intent intent;
    //加载进度框
    BaseProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);

        intent=getIntent();

        initUI();

        dialog.show();

        int newId=intent.getIntExtra("newsId",0);
        String url= Tools.jointIpAddress()
                + NetConstants.NEWS_DETAIL_PORT
                + "?"
                + NetConstants.NEWS_DETAIL_PARAM
                + "="
                + newId;

        Log.e("newsUrl",url);

        MyJsonObjectRequest request=new MyJsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONObject news=jsonObject.getJSONObject("news");
                            mNoticeTitleTv.setText(news.getString("title"));
                            mDepartmentTv.setText(news.getString("operationDeptName"));
                            mAuthorTv.setText(news.getString("operatorName"));
                            String addTime=news.getString("addTime").substring(0, 19);
                            mTimeTv.setText(addTime);

                            String contentStr=news.getString("content")
                                    .replaceAll("img src=\"", "img style=\" width:100%%; height:auto;\" src=\"" + Tools.jointIpAddress())
                                    .replaceAll("href=\"","href=\""+Tools.jointIpAddress());
//                                    .replaceAll("line-height:(.*?);", "line-height: 180%;")
//                                    .replaceAll("text-indent:(.*?);", "text-indent: 2em;")
//                                    .replaceAll("font-size:(.*?);", "font-size: 16px;");

                            Log.e("contentStr", contentStr);
//                            mContentWv.loadData(news.getString("content"), "text/html", "UTF-8");
                            mContentWv.loadDataWithBaseURL("", contentStr, "text/html", "UTF-8",null);
//                            mContentWv.loadData(contentStr,"text/html","UTF-8");

                            mReadNumTv.setText(String.format(NoticeDetailActivity.this.getString(R.string.num_of_read),""+news.getInt("readNum")));

                            mDivide.setVisibility(View.GONE);
                            mCommentButtonLly.setVisibility(View.GONE);
                            /** 暂时取消评论功能 */
//                            if(news.getInt("isComment")==0){
//                                mDivide.setVisibility(View.GONE);
//                                mCommentButtonLly.setVisibility(View.GONE);
//                            }else{
//                                mCommentNumTv.setText(String.format(NoticeDetailActivity.this.getString(R.string.num_of_comment),""+news.getInt("commentNum")));
//                            }

                            dialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                Toast.makeText(NoticeDetailActivity.this,"网络请求失败，请检查网络",Toast.LENGTH_SHORT).show();
            }
        });

        //设置网络请求超时时间
        request.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 1, 1.0f));

        VolleyHelper.getInstance(NoticeDetailActivity.this).addRequestTask(request, NetConstants.TAG_NEWS_DETAIL);
    }

    /**
     * 初始化界面
     */
    private void initUI() {
        initTitleBar();

        initContentArea();

        initBottomPanel();

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
        mTitleTv.setText("公告详情");
    }

    /**
     * 初始化内容显示区
     */
    private void initContentArea() {
        //加载并设置公告标题
        mNoticeTitleTv= (TextView) findViewById(R.id.notice_detail_tv_title);

        //加载并设置公告发布部门
        mDepartmentTv= (TextView) findViewById(R.id.notice_detail_tv_department);

        //加载并设置公告发布人
        mAuthorTv= (TextView) findViewById(R.id.notice_detail_tv_author);

        //加载并设置公告发布时间
        mTimeTv= (TextView) findViewById(R.id.notice_detail_tv_time);

        //加载并设置公告内容
        mContentWv= (WebView) findViewById(R.id.notice_detail_wv_content);
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

//        mContentWv.setWebViewClient(new MyWebViewClient());
    }


//    class MyWebViewClient extends WebViewClient{
////        @Override
////        public void onPageStarted(WebView view, String url, Bitmap favicon) {
////            view.getSettings().setJavaScriptEnabled(true);
////            super.onPageStarted(view, url, favicon);
////            mContentWv.loadUrl(
////                    "javascript:(function(){" +
////                            "var count = document.images.length;" +
////                            "var i_Width= window.screen.width-20;" +
////                            "for (var i = 0; i < count; i++)" +
////                            "{" +
////                            "var image = document.images[i];" +
////                            "var imgWidth= image.width;" +
////                            "var imgHeight= image.height;" +
////                            "var iRatio=imgHeight/imgWidth;" +
////                            "var targetHeight=i_Width*iRatio;" +
////                            "image.style.width=i_Width;" +
////                            "image.style.height=targetHeight;" +
////                            "}"+
////                            "})()"
////            );
////
////        }
//
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            view.getSettings().setJavaScriptEnabled(true);
//            super.onPageFinished(view, url);
//            mContentWv.loadUrl(
//                    "javascript:(function(){" +
//                            "var count = document.images.length;" +
//                            "var i_Width= window.screen.width-20;" +
//                            "for (var i = 0; i < count; i++)" +
//                            "{" +
//                            "var image = document.images[i];" +
//                            "var imgWidth= image.width" +
//                            "var imgHeight= image.height;" +
//                            "var iRatio=imgHeight/imgWidth;" +
//                            "var targetHeight=i_Width*iRatio;" +
//                            "image.style.width=i_Width;" +
//                            "image.style.height=targetHeight;" +
//                            "}"+
//                            "})()"
//            );
//        }
//
//    }


    /**
     * 初始化底部按钮区
     */
    private void initBottomPanel() {
        //加载并为阅读按钮设置点击事件
        mReadButtonLly= (LinearLayout) findViewById(R.id.notice_detail_lly_read);
        mReadButtonLly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //加载并为评论按钮设置点击事件
        mCommentButtonLly= (LinearLayout) findViewById(R.id.notice_detail_lly_comment);

        //加载两个按钮中间的分隔
        mDivide=findViewById(R.id.notice_detail_divide);

        //加载并设置阅读人数
        mReadNumTv= (TextView) findViewById(R.id.notice_detail_tv_readnum);

        //加载并设置评论人数
        mCommentNumTv= (TextView) findViewById(R.id.notice_detail_tv_commentnum);
    }

}
