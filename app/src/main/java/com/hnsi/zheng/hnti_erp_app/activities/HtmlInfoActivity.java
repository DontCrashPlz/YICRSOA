package com.hnsi.zheng.hnti_erp_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.app.AppConstants;
import com.hnsi.zheng.hnti_erp_app.utils.Tools;

/**
 * 用于显示html详情信息的界面
 * Created by Zheng on 2016/7/1.
 */
public class HtmlInfoActivity extends MyBaseActivity{
    private TextView mTitleTv;
    private ImageButton mBackBtn;
    private WebView mContentWv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html_info);

        Intent intent=getIntent();

        String contentStr=intent.getStringExtra(AppConstants.HTML_CONTENT)
                .replaceAll("img src=\"", "img style=\" width:100%%; height:auto;\" src=\"" + Tools.jointIpAddress())
                .replaceAll("href=\"","href=\""+Tools.jointIpAddress());

        mTitleTv= (TextView) findViewById(R.id.titlebar_tv_title);
        mTitleTv.setText("详情信息");

        mBackBtn= (ImageButton) findViewById(R.id.titlebar_ibtn_back);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mContentWv= (WebView) findViewById(R.id.html_wv_content);
        mContentWv.loadDataWithBaseURL("",contentStr, "text/html", "UTF-8",null);
    }
}
