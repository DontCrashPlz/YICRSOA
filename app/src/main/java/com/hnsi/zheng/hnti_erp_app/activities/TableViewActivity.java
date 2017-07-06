package com.hnsi.zheng.hnti_erp_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.adapters.TableViewDetailAdapter;
import com.hnsi.zheng.hnti_erp_app.app.AppConstants;
import com.hnsi.zheng.hnti_erp_app.shareclass.LinkedHashMap2TableViewActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 附表显示界面
 * Created by Zheng on 2016/6/28.
 */
public class TableViewActivity extends MyBaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tableview);

        Intent intent=getIntent();
        LinkedHashMap dataMap= LinkedHashMap2TableViewActivity.getInstance().get();


        ArrayList<String> mKeyList=new ArrayList();
        ArrayList<String> mValueList=new ArrayList();
        Iterator iter = dataMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            mKeyList.add((String) entry.getKey());
            mValueList.add((String) entry.getValue());
            }

        String title=intent.getStringExtra(AppConstants.MATTER_TABLEVIEW_TITLE);
        TextView mTitleTv= (TextView) findViewById(R.id.titlebar_tv_title);
        mTitleTv.setText(title);

        ImageButton mBackBtn= (ImageButton) findViewById(R.id.titlebar_ibtn_back);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ListView listView= (ListView) findViewById(R.id.tableview_lv);
        listView.setAdapter(new TableViewDetailAdapter(this,mKeyList,mValueList));
    }
}
