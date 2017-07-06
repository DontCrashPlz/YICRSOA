package com.hnsi.zheng.hnti_erp_app.activities;

import android.app.Activity;
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
import com.hnsi.zheng.hnti_erp_app.adapters.MessageAdapter;
import com.hnsi.zheng.hnti_erp_app.app.NetConstants;
import com.hnsi.zheng.hnti_erp_app.beans.MessageEntity;
import com.hnsi.zheng.hnti_erp_app.http.MyJsonObjectRequest;
import com.hnsi.zheng.hnti_erp_app.http.VolleyHelper;
import com.hnsi.zheng.hnti_erp_app.utils.Tools;
import com.hnsi.zheng.hnti_erp_app.widgets.progressDialog.BaseProgressDialog;
import com.hnsi.zheng.hnti_erp_app.widgets.progressDialog.ProgressDialog;
import com.hnsi.zheng.hnti_erp_app.widgets.pullToRefresh.PullToRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 消息界面
 * Created by Zheng on 2016/8/4.
 */
public class MessageActivity extends MyBaseActivity{

    private ImageButton mBackIBtn;
    private TextView mTitleTv;

    //下拉列表
    private PullToRefreshLayout pullToRefreshLayout;
    private ListView listView;
    //列表数据Adapter
    MessageAdapter adapter;
    //当前数据索引
    private int mPageIndex = 1;
    //新闻列表最大页数
    private int mMaxPageIndex;
    //新闻列表集合
    ArrayList<MessageEntity> mMessageList;
    //加载框
    BaseProgressDialog dialog;

    //列表无数据提醒
    private TextView noDateTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        initUI();
    }

    /**
     * 初始化界面
     */
    private void initUI() {

        initTitleBar();

        noDateTv= (TextView) findViewById(R.id.refresh_no_date);

        //加载内容ListView
        pullToRefreshLayout= (PullToRefreshLayout) findViewById(R.id.refresh_view);
        pullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                refresh(pullToRefreshLayout);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                loadMore(pullToRefreshLayout);
            }
        });
        listView= (ListView) findViewById(R.id.content_view);
        //初始化数据Adapter
        adapter=new MessageAdapter(this, new ArrayList());
        listView.setAdapter(adapter);

        dialog =new ProgressDialog(this);
        dialog.setLabel("加载中...");

        initData();
    }

    /**
     * 初始化标题头
     */
    private void initTitleBar() {
        mBackIBtn= (ImageButton) findViewById(R.id.titlebar_ibtn_back);
        mBackIBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTitleTv= (TextView) findViewById(R.id.titlebar_tv_title);
        mTitleTv.setText("消息");
    }

    /**
     * 初始化数据的方法
     */
    public void initData() {

        dialog.show();

        String refreshUrl=getNoticeUrl(mPageIndex);

        MyJsonObjectRequest jsonObjectRequest=new MyJsonObjectRequest(
                refreshUrl,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            if(jsonObject.getBoolean("success")){
                                mMaxPageIndex=jsonObject.getInt("totalPage");
                                mMessageList =new ArrayList<>();
                                MessageEntity entity;
                                JSONArray list=jsonObject.getJSONArray("list");
                                for(int i=0;i<list.length();i++){
                                    entity=new MessageEntity();
                                    entity.setId(list.getJSONObject(i).getInt("id"));
                                    entity.setTitle(list.getJSONObject(i).getString("title"));
                                    entity.setMsgType(list.getJSONObject(i).getString("msgType"));
                                    entity.setSendEmpname(list.getJSONObject(i).getString("sendEmpname"));
                                    entity.setSendTime(list.getJSONObject(i).getString("sendTime"));
                                    mMessageList.add(entity);
                                }

                                Log.e("Notice1", "" + adapter.getCount());
                                adapter.addItems(mMessageList);
                                dialog.dismiss();
                                Log.e("Notice2", "" + adapter.getCount());

                                if(mMessageList.size()>0){
                                    noDateTv.setVisibility(View.GONE);
                                }else{
                                    noDateTv.setVisibility(View.VISIBLE);
                                }

                            }else{
                                dialog.dismiss();
                                Toast.makeText(MessageActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(MessageActivity.this,"网络请求失败，请检查网络",Toast.LENGTH_SHORT).show();
            }
        });

        //设置网络请求超时时间
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 1, 1.0f));

        VolleyHelper.getInstance(this)
                .addRequestTask(jsonObjectRequest, NetConstants.TAG_NEWS_LIST);

    }

    /**
     * 实际加载数据的方法
     * @param noticeUrl
     */
    private void loadData(final String noticeUrl, final PullToRefreshLayout pullToRefreshLayout) {
        Log.e("loadData", noticeUrl);

        MyJsonObjectRequest jsonObjectRequest=new MyJsonObjectRequest(
                noticeUrl,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            if(jsonObject.getBoolean("success")){
                                mMaxPageIndex=jsonObject.getInt("totalPage");
                                mMessageList =new ArrayList<>();
                                MessageEntity entity;
                                JSONArray list=jsonObject.getJSONArray("list");
                                for(int i=0;i<list.length();i++){
                                    entity=new MessageEntity();
                                    entity.setId(list.getJSONObject(i).getInt("id"));
                                    entity.setTitle(list.getJSONObject(i).getString("title"));
                                    entity.setMsgType(list.getJSONObject(i).getString("msgType"));
                                    entity.setSendEmpname(list.getJSONObject(i).getString("sendEmpname"));
                                    entity.setSendTime(list.getJSONObject(i).getString("sendTime"));
                                    mMessageList.add(entity);
                                }

                                Log.e("Notice1", "" + adapter.getCount());
                                adapter.addItems(mMessageList);

                                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);

                                Log.e("Notice2", "" + adapter.getCount());
                            }else{
                                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
                                Toast.makeText(MessageActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
                Toast.makeText(MessageActivity.this,"网络请求失败，请检查网络",Toast.LENGTH_SHORT).show();
            }
        });

        //设置网络请求超时时间
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 1, 1.0f));

        VolleyHelper.getInstance(this)
                .addRequestTask(jsonObjectRequest, NetConstants.TAG_NEWS_LIST);
    }

    /**
     * 获取URL
     * @param index
     * @return
     */
    public String getNoticeUrl(int index) {
        String urlString = Tools.jointIpAddress()
                + NetConstants.MESSAGE_LIST_PORT
                + "?"
                + NetConstants.MESSAGE_LIST_PARAM
                + "="
                + index;
        return urlString;
    }

    /**
     * 下拉刷新
     */
    public void refresh(PullToRefreshLayout pullToRefreshLayout) {
        adapter.clear();
        mPageIndex = 1;
        loadData(getNoticeUrl(mPageIndex),pullToRefreshLayout);
    }

    /**
     * 底部加载更多
     */
    public void loadMore(PullToRefreshLayout pullToRefreshLayout) {
        mPageIndex += 1;
        Log.e("onLoadMore",""+mPageIndex);
        if(mPageIndex<=mMaxPageIndex){
            loadData(getNoticeUrl(mPageIndex),pullToRefreshLayout);
        }else{
            pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
            Toast.makeText(this,"已加载到最后一页",Toast.LENGTH_SHORT).show();
        }
    }


}
