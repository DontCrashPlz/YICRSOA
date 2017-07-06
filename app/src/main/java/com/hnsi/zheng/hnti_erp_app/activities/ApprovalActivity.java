package com.hnsi.zheng.hnti_erp_app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;

/**
 * 审批界面（该页面暂时弃置，改为使用ApprovalActivity2）
 * Created by Zheng on 2016/5/3.
 */
@Deprecated
public class ApprovalActivity extends MyBaseActivity implements View.OnClickListener{
    //标题头返回按钮
    ImageButton mBackBtn;
    //页面标题
    TextView mTitleTv;
    //我的申请按钮
    Button mApplyBtn;
    //我的审批按钮
    Button mApprovalBtn;
    //新建申请按钮
    TextView mNewApplyTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval);

        initUI();
    }

    /**
     * 初始化界面
     */
    private void initUI() {
        initTitleBar();

        //加载并为控件设置点击事件（我的申请）
        mApplyBtn= (Button) findViewById(R.id.approval_btn_topBtn);
        mApplyBtn.setOnClickListener(this);

        //加载并为控件设置点击事件（我的审批）
        mApprovalBtn= (Button) findViewById(R.id.approval_btn_middleBtn);
        mApprovalBtn.setOnClickListener(this);

        //加载并为控件设置点击事件（新建申请）
        mNewApplyTv= (TextView) findViewById(R.id.approval_tv_new);
        mNewApplyTv.setOnClickListener(this);
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
        mTitleTv.setText("审批");
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
            case R.id.approval_btn_topBtn:{//控件点击事件（我的申请）
            }break;
            case R.id.approval_btn_middleBtn:{//控件点击事件（我的审批）
            }break;
            case R.id.approval_tv_new:{//控件点击事件（新建申请）
            }break;
        }
    }
}
