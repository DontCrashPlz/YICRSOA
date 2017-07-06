package com.hnsi.zheng.hnti_erp_app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
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
import com.hnsi.zheng.hnti_erp_app.shareclass.HashMap2MatterParam;
import com.hnsi.zheng.hnti_erp_app.utils.KeyBoardUtils;
import com.hnsi.zheng.hnti_erp_app.utils.Tools;
import com.hnsi.zheng.hnti_erp_app.widgets.progressDialog.BaseProgressDialog;
import com.hnsi.zheng.hnti_erp_app.widgets.progressDialog.ProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 待办事项详情显示界面
 * Created by Zheng on 2016/6/23.
 */
public class UnFinishedMatterActivity extends MyBaseActivity{
    /** 网络请求得到的数据 */
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
    //提交按钮
    private Button mCommitBtn;
    //processInstID
    private int processInstID;
    //加载框
    private BaseProgressDialog dialog;

    private MatterInfoAdapter adapter;

    private ScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unfinished_matter);

        Intent intent=getIntent();

        processInstID=intent.getIntExtra(AppConstants.MATTER_PROCESSINSTID, 0);

        if(processInstID==0){
            Toast.makeText(this,"审批数据错误，请重试！",Toast.LENGTH_SHORT);
            finish();
        }

        String jsonStr=intent.getStringExtra(AppConstants.UNFINISHED_MATTER_ENTITY);
        Log.e("jsonStr", jsonStr);

        try {
            mMatterInfo= new JSONObject(jsonStr);
            Log.e("mMatterInfo",mMatterInfo.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this,"审批数据错误，请重试！",Toast.LENGTH_SHORT);
            finish();
        }

        initUI();

        createCommitParamMap();

        createData();

        requestHistoryData();

    }

    /**
     * 请求历史纪录数据
     */
    private void requestHistoryData() {
        String url=Tools.jointIpAddress()
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
                                MatterHistoryListAdapter historyListAdapter=new MatterHistoryListAdapter(UnFinishedMatterActivity.this,matterHistoryList);
                                mHistoryLv.setAdapter(historyListAdapter);
                            }else{
                                Toast.makeText(UnFinishedMatterActivity.this,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(UnFinishedMatterActivity.this,"审批记录数据错误！",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UnFinishedMatterActivity.this,"审批记录请求失败，请检查网络",Toast.LENGTH_SHORT).show();
            }
        });

        //设置网络请求超时时间
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 1, 1.0f));

        VolleyHelper.getInstance(UnFinishedMatterActivity.this)
                .addRequestTask(jsonObjectRequest, url);
    }

    /**
     * 构建提交审批时需要提交的参数集合
     */
    private void createCommitParamMap() {
        try {
            JSONArray childArray=mMatterInfo.getJSONArray("ctlList");
            HashMap2MatterParam.getInstance().get().clear();
            for(int i=0;i<childArray.length();i++){
                JSONObject obj=childArray.getJSONObject(i);
                if(obj.getBoolean("required")){
                    HashMap2MatterParam.getInstance().putEle(obj.getString("key").replace(".","/"), AppConstants.VALUE_NULL_IN_MAP);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this,"审批数据无效！",Toast.LENGTH_SHORT);
            finish();
        }
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
                    e.printStackTrace();
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
            adapter=new MatterInfoAdapter(UnFinishedMatterActivity.this,matterGroupList);
            mInfoLv.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this,"审批数据无效！",Toast.LENGTH_SHORT);
            finish();
        }
    }

    /**
     * 初始化界面
     */
    private void initUI() {
        initTitleBar();

        mScrollView= (ScrollView) findViewById(R.id.scrollView);
        mScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()==MotionEvent.ACTION_MOVE)
                    KeyBoardUtils.hideInputForce(UnFinishedMatterActivity.this);

                return false;
            }
        });

        dialog=new ProgressDialog(this);
        dialog.setLabel("请稍等...");

        mHistoryTv= (TextView) findViewById(R.id.matter_tv_history);

        mInfoLv= (ListView) findViewById(R.id.matter_lv_info);

        mHistoryLv= (ListView) findViewById(R.id.matter_lv_history);

        mCommitBtn= (Button) findViewById(R.id.matter_btn_commit);

        mCommitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!"".equals(getIsConnect())){//如果纪录的有switch状态，根据状态值做处理
                    if("0".equals(getIsConnect())){//如果switch为关闭状态，从Map中删除与switch关联的key字段
                        HashMap2MatterParam.getInstance().get().remove(getConnectKey());
                    }
                }

                if(!"".equals(getMutualStatus())){//如果纪录的有互斥开关的状态，根据状态值做处理
                    if("0".equals(getMutualStatus())){
                        HashMap2MatterParam.getInstance().get().remove(getSecondMutualKey());
                    }else if("1".equals(getMutualStatus())){
                        HashMap2MatterParam.getInstance().get().remove(getFristMutualKey());
                    }
                }

                Log.e("Map", HashMap2MatterParam.getInstance().get().size()+HashMap2MatterParam.getInstance().get().toString());

                final HashMap paramMap=HashMap2MatterParam.getInstance().get();
                if(paramMap.values().contains(AppConstants.VALUE_NULL_IN_MAP)){
                    Toast.makeText(UnFinishedMatterActivity.this,"您有未编辑的审批项",Toast.LENGTH_SHORT).show();
                    Log.e("map",paramMap.toString());
                }else{
                    dialog.show();

                    String url="";
                    try {
                        url= Tools.jointIpAddress()
                                +mMatterInfo.getString("url");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.e("commitUrl",url);

                    MyJsonObjectRequest jsonObjectRequest=new MyJsonObjectRequest(
                            Request.Method.POST,
                            url,
                            new JSONObject(paramMap),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    try {
                                        if(jsonObject.getBoolean("success")){
                                            dialog.dismiss();
                                            Toast.makeText(UnFinishedMatterActivity.this,"提交成功",Toast.LENGTH_SHORT).show();
                                            setResult(AppConstants.UNFINISHED_RESULTCODE);
                                            finish();
                                        }else{
                                            dialog.dismiss();
                                            Toast.makeText(UnFinishedMatterActivity.this,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog.dismiss();
                            Toast.makeText(UnFinishedMatterActivity.this,"网络请求失败，请检查网络",Toast.LENGTH_SHORT).show();
                        }
                    });

                    //设置网络请求超时时间
                    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 1, 1.0f));

                    VolleyHelper.getInstance(UnFinishedMatterActivity.this)
                            .addRequestTask(jsonObjectRequest, url);

                }
            }
        });
    }

    /**
     * 初始化标题头
     */
    private void initTitleBar() {
        mTitleTv= (TextView) findViewById(R.id.titlebar_tv_title);
        mTitleTv.setText("待办审批");

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

    /** 在adapter中获取Activity实例 */
    public UnFinishedMatterActivity getThisActivity(){
        return UnFinishedMatterActivity.this;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //从经办人选择界面返回时将选择的经办人姓名显示在mJingBanRenTv中，并将回传的empid拼接到提交时上传的参数中
        if(data!=null){
            setJingBanRenStr(data.getStringExtra("empname"));
            int value=data.getIntExtra("empid", 0);
            if(value!=0){
                setJingBanRenId(""+value);
            }
            adapter.notifyDataSetChanged();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /** 用于储存经办人姓名的变量 */
    private String mJingBanRenName="";
    public void setJingBanRenStr(String str){
        mJingBanRenName=str;
    }
    public String getJingBanRenStr(){
        return mJingBanRenName;
    }

    /** 用于储存经办人empid的变量 */
    private String mJingBanRenId="";
    public void setJingBanRenId(String id){
        mJingBanRenId=id;
    }
    public String getJingBanRenId(){
        return mJingBanRenId;
    }

    /** 与switch开关关联项的key */
    private String connectKey="";
    public void setConnectKey(String key){
        connectKey=key;
    }
    public String getConnectKey(){
        return connectKey;
    }

    /** 储存switch开关当前状态 */
    private String isConnect="";
    public void setIsConnect(String value){
        isConnect=value;
    }
    public String getIsConnect(){
        return isConnect;
    }

    /** 互斥开关的第一个参数 */
    private String fristMutualKey="";
    public void setFristMutualKey(String key){
        fristMutualKey=key;
    }
    public String getFristMutualKey(){
        return fristMutualKey;
    }

    /** 互斥开关的第二个参数 */
    private String secondMutualKey="";
    public void setSecondMutualKey(String key){
        secondMutualKey=key;
    }
    public String getSecondMutualKey(){
        return secondMutualKey;
    }

    /** 储存互斥开关开关当前状态 */
    private String mutualStatus="";
    public void setMutualStatus(String value){
        mutualStatus=value;
    }
    public String getMutualStatus(){
        return mutualStatus;
    }

}
