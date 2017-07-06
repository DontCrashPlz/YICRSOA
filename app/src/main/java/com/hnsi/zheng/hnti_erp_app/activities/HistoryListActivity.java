package com.hnsi.zheng.hnti_erp_app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.adapters.MatterHistoryListAdapter;
import com.hnsi.zheng.hnti_erp_app.app.AppConstants;
import com.hnsi.zheng.hnti_erp_app.app.MyApplication;
import com.hnsi.zheng.hnti_erp_app.beans.MatterHistoryEntity;

import java.util.ArrayList;

/**
 * 审批记录列表页面(审批记录列表显示位置变动，此页面废弃)
 * Created by Zheng on 2016/7/4.
 */
@Deprecated
public class HistoryListActivity extends MyBaseActivity{
    private TextView mTitleTv;
    private ImageButton mBackBtn;
    private ListView mHistoryListLv;
    private ArrayList<MatterHistoryEntity>list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);


        Intent intent=getIntent();
        list= (ArrayList<MatterHistoryEntity>) intent.getSerializableExtra(AppConstants.HISTORY_LIST);
//        matterTitle=intent.getStringExtra(AppConstants.MATTER_TITLE);
        initUI();
    }

    /**
     * 初始化界面
     */
    private void initUI() {
        mTitleTv= (TextView) findViewById(R.id.titlebar_tv_title);
        mTitleTv.setText("审批记录");
        mBackBtn= (ImageButton) findViewById(R.id.titlebar_ibtn_back);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mHistoryListLv= (ListView) findViewById(R.id.history_list_lv);
        mHistoryListLv.setAdapter(new MatterHistoryListAdapter(this,list));
    }
}
