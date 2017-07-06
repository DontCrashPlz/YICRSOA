package com.hnsi.zheng.hnti_erp_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.adapters.MatterHistoryListAdapter;
import com.hnsi.zheng.hnti_erp_app.adapters.MatterInfoAdapter;
import com.hnsi.zheng.hnti_erp_app.app.AppConstants;
import com.hnsi.zheng.hnti_erp_app.app.NetConstants;
import com.hnsi.zheng.hnti_erp_app.beans.MatterGroupEntity;
import com.hnsi.zheng.hnti_erp_app.beans.MatterHistoryEntity;
import com.hnsi.zheng.hnti_erp_app.http.MyJsonObjectRequest;
import com.hnsi.zheng.hnti_erp_app.http.VolleyHelper;
import com.hnsi.zheng.hnti_erp_app.utils.Tools;
import com.hnsi.zheng.hnti_erp_app.widgets.progressDialog.BaseProgressDialog;
import com.hnsi.zheng.hnti_erp_app.widgets.progressDialog.ProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 已办事项详情显示界面
 * Created by Zheng on 2016/7/1.
 */
public class FinishedMatterActivity extends MyBaseActivity{

    //网络请求得到的数据
    private JSONObject mMatterInfo;
    //页面标题
    private TextView mTitleTv;
    //返回按钮
    private ImageButton mBackBtn;
    //查看审批记录按钮
    private TextView mRecordsTv;
    //审批记录标头
    private TextView mHistoryTv;
    //详情列表
    private ListView mInfoLv;
    //历史纪录列表
    private ListView mHistoryLv;
    //事件详情信息列表
    private ArrayList<MatterGroupEntity>matterGroupList=new ArrayList<>();
    //历史纪录信息列表
    private ArrayList<MatterHistoryEntity>matterHistoryList=new ArrayList<>();
    //processInstID
    private String processInstID;
    //加载框
    private BaseProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_matter);

        dialog=new ProgressDialog(this);
        dialog.setLabel("请稍等...");

        Intent intent=getIntent();

        processInstID=intent.getStringExtra(AppConstants.MATTER_PROCESSINSTID);

        String jsonStr=intent.getStringExtra(AppConstants.FINISHED_MATTER_ENTITY);
        Log.e("jsonStr", jsonStr);

        try {
            mMatterInfo= new JSONObject(jsonStr);
            Log.e("mMatterInfo",mMatterInfo.toString());

//            processInstID=mMatterInfo.getString("processInstId");
//            matterTitle=mMatterInfo.getString("processInstName");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        initUI();

        createData();

        requestHistoryData();

    }

    /**
     * 请求历史纪录数据
     */
    private void requestHistoryData() {

        dialog.show();

        String url= Tools.jointIpAddress()
                + NetConstants.APPROVAL_HISTORY_PORT
                + "?"
                + NetConstants.APPROVAL_HISTORY_PARAM
                + "="
                + processInstID;

        Log.e("Url",url);

        MyJsonObjectRequest jsonObjectRequest=new MyJsonObjectRequest(
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            if(jsonObject.getBoolean("success")){
                                JSONArray array=jsonObject.getJSONArray("list");
                                for(int i=0;i<array.length();i++){
                                    JSONObject obj=array.getJSONObject(i);
                                    MatterHistoryEntity entity=new MatterHistoryEntity();
                                    entity.setName(obj.getString("name"));
                                    entity.setActivityName(obj.getString("activityName"));
                                    entity.setEndTime(obj.getString("endTime"));
                                    entity.setDecision(obj.getString("decision"));
                                    entity.setContent(obj.getString("content"));
                                    matterHistoryList.add(entity);
                                }
                                if(matterHistoryList.size()<=0){
                                    mHistoryTv.setVisibility(View.GONE);
                                }

                                //历史纪录数据Adapter
                                MatterHistoryListAdapter historyListAdapter=new MatterHistoryListAdapter(FinishedMatterActivity.this,matterHistoryList);
                                mHistoryLv.setAdapter(historyListAdapter);

                                dialog.dismiss();
                            }else{
                                dialog.dismiss();
                                Toast.makeText(FinishedMatterActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(FinishedMatterActivity.this,"网络请求失败，请检查网络",Toast.LENGTH_SHORT).show();
            }
        });

        //设置网络请求超时时间
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 1, 1.0f));

        VolleyHelper.getInstance(FinishedMatterActivity.this)
                .addRequestTask(jsonObjectRequest, url);
    }

    /**
     * 对数据进行处理
     */
    private void createData() {
        try {
            JSONArray groupArray=mMatterInfo.getJSONArray("groupList");

            for(int i=0;i<groupArray.length();i++){
                MatterGroupEntity entity=new MatterGroupEntity();
                String groupKey=groupArray.getJSONObject(i).getString("groupKey");
                entity.setGroupKey(groupKey);
                entity.setLabel(groupArray.getJSONObject(i).getString("label"));
                try{
                    entity.setAudit(groupArray.getJSONObject(i).getBoolean("audit"));
                }catch (JSONException e){
                    entity.setAudit(groupArray.getJSONObject(i).getBoolean("isAudit"));
                }

                ArrayList<JSONObject> childList=new ArrayList<>();
                JSONArray childArray=mMatterInfo.getJSONArray("ctlList");
                for(int j=0;j<childArray.length();j++){
                    String childGroupKey=childArray.getJSONObject(j).getString("groupKey");
                    if(childGroupKey.equals(groupKey)){
                        childList.add(childArray.getJSONObject(j));
                    }
                }
                entity.setChildList(childList);
                matterGroupList.add(entity);
            }
            Log.e("list", matterGroupList.toString());

            //事件详情数据Adapter
            MatterInfoAdapter adapter=new MatterInfoAdapter(this,matterGroupList);
            mInfoLv.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 初始化界面
     */
    private void initUI() {
        initTitleBar();

        mHistoryTv= (TextView) findViewById(R.id.matter_tv_history);

        mInfoLv= (ListView) findViewById(R.id.matter_lv_info);
//        //事件详情数据Adapter
//        MatterInfoAdapter adapter=new MatterInfoAdapter(this,matterGroupList);
//        mInfoLv.setAdapter(adapter);

        mHistoryLv= (ListView) findViewById(R.id.matter_lv_history);
//        //历史纪录数据Adapter
//        MatterHistoryListAdapter historyListAdapter=new MatterHistoryListAdapter(this,matterHistoryList);
//        mHistoryLv.setAdapter(historyListAdapter);

    }

    /**
     * 初始化标题头
     */
    private void initTitleBar() {
        mTitleTv= (TextView) findViewById(R.id.titlebar_tv_title);
        mTitleTv.setText("已办审批");

        mBackBtn= (ImageButton) findViewById(R.id.titlebar_ibtn_back);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRecordsTv= (TextView) findViewById(R.id.titlebar_tv_function);
        mRecordsTv.setText("");
    }

}
