package com.hnsi.zheng.hnti_erp_app.activities;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;

/**
 * 关于界面
 * Created by Zheng on 2016/5/3.
 */
public class AboutUsActivity extends MyBaseActivity implements View.OnClickListener{
    //标题头返回按钮
    ImageButton mBackBtn;
    //页面标题
    TextView mTitleTv;
    //版本信息
    TextView mVersionTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        initUI();
    }

    /**
     * 初始化界面
     */
    private void initUI() {
        initTitleBar();
        //加载并设置版本信息
        mVersionTv= (TextView) findViewById(R.id.about_tv_version);
        mVersionTv.setText(getVersion());
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
        mTitleTv.setText("关于");
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
        }
    }

    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return String.format(this.getString(R.string.version),version);
        } catch (Exception e) {
            e.printStackTrace();
            return this.getString(R.string.can_not_find_version_name);
        }
    }
}
