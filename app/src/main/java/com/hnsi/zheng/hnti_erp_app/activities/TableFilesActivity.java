package com.hnsi.zheng.hnti_erp_app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.adapters.TableFilesAdapter;
import com.hnsi.zheng.hnti_erp_app.beans.FileEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * tablefiles附件详情界面
 * Created by Zheng on 2016/12/20.
 */
public class TableFilesActivity extends MyBaseActivity{

    private ImageButton mBackBtn;

    private TextView mTitleTv;

    private ListView mFileInfoLv;

    private JSONObject mFilesData;

    private JSONArray mDataArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablefiles);
        try {
            mFilesData=new JSONObject(getIntent().getStringExtra("filesInfo"));
            mDataArray=mFilesData.getJSONArray("tableData");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<FileEntity>mFiles=new ArrayList<>();
        for(int i=0;i<mDataArray.length();i++){
            try {
                JSONArray data=mDataArray.getJSONArray(i);
                FileEntity entity=new FileEntity();
                entity.setFileNum(data.getString(0));
                entity.setFileName(data.getString(1));
                entity.setFileUploadTime(data.getString(2));
                entity.setFilePath(data.getString(3));
                entity.setFileSize(data.getString(4));
                entity.setFileId(data.getString(5));
                mFiles.add(entity);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        mBackBtn= (ImageButton) findViewById(R.id.titlebar_ibtn_back);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTitleTv= (TextView) findViewById(R.id.titlebar_tv_title);
        try {
            mTitleTv.setText(mFilesData.getString("label"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mFileInfoLv= (ListView) findViewById(R.id.tablefiles_list);
        TableFilesAdapter adapter=new TableFilesAdapter(this,mFiles);
        mFileInfoLv.setAdapter(adapter);
    }
}
