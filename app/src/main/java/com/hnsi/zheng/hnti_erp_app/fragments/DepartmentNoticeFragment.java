package com.hnsi.zheng.hnti_erp_app.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.adapters.NoticeAdapter;
import com.hnsi.zheng.hnti_erp_app.adapters.NoticeAdapter2;
import com.hnsi.zheng.hnti_erp_app.app.AppConstants;
import com.hnsi.zheng.hnti_erp_app.app.NetConstants;
import com.hnsi.zheng.hnti_erp_app.beans.NewsEntity;
import com.hnsi.zheng.hnti_erp_app.http.MyJsonObjectRequest;
import com.hnsi.zheng.hnti_erp_app.http.VolleyHelper;
import com.hnsi.zheng.hnti_erp_app.utils.Tools;
import com.hnsi.zheng.hnti_erp_app.widgets.progressDialog.BaseProgressDialog;
import com.hnsi.zheng.hnti_erp_app.widgets.progressDialog.ProgressDialog;
import com.hnsi.zheng.hnti_erp_app.widgets.pullListView.OnListViewListener;
import com.hnsi.zheng.hnti_erp_app.widgets.pullListView.PullListView;
import com.hnsi.zheng.hnti_erp_app.widgets.pullToRefresh.PullToRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 新闻公告界面（部门新闻）
 * Created by Zheng on 2016/7/7.
 */
public class DepartmentNoticeFragment extends Fragment{
    //下拉列表
    private PullToRefreshLayout pullToRefreshLayout;
    private ListView listView;
    //列表数据Adapter
    NoticeAdapter2 adapter;
    //当前数据索引
    private int mPageIndex = 1;
    //新闻列表最大页数
    private int mMaxPageIndex;
    //新闻列表集合
    ArrayList<NewsEntity> mNewsList;
    //加载框
    BaseProgressDialog dialog;

    //列表无数据提醒
    private TextView noDateTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRootView=inflater.inflate(R.layout.fragment_unfinished_matter,container,false);

        initUI(mRootView);

        return mRootView;
    }

    /**
     * 初始化界面
     */
    private void initUI(View rootView) {

        noDateTv= (TextView) rootView.findViewById(R.id.refresh_no_date);

        //加载内容ListView
        pullToRefreshLayout= (PullToRefreshLayout) rootView.findViewById(R.id.refresh_view);
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
        listView= (ListView) rootView.findViewById(R.id.content_view);
        //初始化数据Adapter
        adapter=new NoticeAdapter2(getContext(), new ArrayList());
        listView.setAdapter(adapter);

        dialog =new ProgressDialog(getContext());
        dialog.setLabel("加载中...");

        initData();
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
                                mNewsList=new ArrayList<>();
                                NewsEntity entity;
                                JSONArray list=jsonObject.getJSONArray("list");
                                for(int i=0;i<list.length();i++){
                                    entity=new NewsEntity();
                                    entity.setId(list.getJSONObject(i).getInt("id"));
                                    entity.setNewsClass(list.getJSONObject(i).getInt("newsClass"));
                                    entity.setClassname(list.getJSONObject(i).getString("classname"));
                                    entity.setStartDate(list.getJSONObject(i).getString("startDate").substring(0, 10));
                                    entity.setOperatorName(list.getJSONObject(i).getString("operatorName"));
                                    entity.setTitle(list.getJSONObject(i).getString("title"));
                                    entity.setOperationDeptName(list.getJSONObject(i).getString("operationDeptName"));
                                    entity.setImgsrc(list.getJSONObject(i).getString("imgsrc"));
                                    mNewsList.add(entity);
                                }

                                Log.e("Notice1", "" + adapter.getCount());
                                adapter.addItems(mNewsList);
                                dialog.dismiss();
                                Log.e("Notice2", "" + adapter.getCount());

                                if(mNewsList.size()>0){
                                    noDateTv.setVisibility(View.GONE);
                                }else{
                                    noDateTv.setVisibility(View.VISIBLE);
                                }

                            }else{
                                dialog.dismiss();
                                Toast.makeText(getContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(getContext(),"网络请求失败，请检查网络",Toast.LENGTH_SHORT).show();
            }
        });

        //设置网络请求超时时间
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 1, 1.0f));

        VolleyHelper.getInstance(getContext())
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
                                mNewsList=new ArrayList<NewsEntity>();
                                NewsEntity entity;
                                JSONArray list=jsonObject.getJSONArray("list");
                                for(int i=0;i<list.length();i++){
                                    entity=new NewsEntity();
                                    entity.setId(list.getJSONObject(i).getInt("id"));
                                    entity.setNewsClass(list.getJSONObject(i).getInt("newsClass"));
                                    entity.setClassname(list.getJSONObject(i).getString("classname"));
                                    entity.setStartDate(list.getJSONObject(i).getString("startDate").substring(0, 10));
                                    entity.setOperatorName(list.getJSONObject(i).getString("operatorName"));
                                    entity.setTitle(list.getJSONObject(i).getString("title"));
                                    entity.setOperationDeptName(list.getJSONObject(i).getString("operationDeptName"));
                                    entity.setImgsrc(list.getJSONObject(i).getString("imgsrc"));
                                    mNewsList.add(entity);
                                }

                                Log.e("Notice1", "" + adapter.getCount());
                                adapter.addItems(mNewsList);

                                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);

                                Log.e("Notice2", "" + adapter.getCount());
                            }else{
                                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
                                Toast.makeText(getContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(),"网络请求失败，请检查网络",Toast.LENGTH_SHORT).show();
            }
        });

        //设置网络请求超时时间
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 1, 1.0f));

        VolleyHelper.getInstance(getContext())
                .addRequestTask(jsonObjectRequest, NetConstants.TAG_NEWS_LIST);
    }

    /**
     * 获取URL
     * @param index
     * @return
     */
    public String getNoticeUrl(int index) {
        String urlString = Tools.jointIpAddress()
                + NetConstants.NEWS_LIST_PORT
                + "?"
                + NetConstants.NEWS_LIST_PARAM_ONE
                + "="
                + index
                + "&"
                + NetConstants.NEWS_LIST_PARAM_TWO
                + "="
                + AppConstants.NOTICE_DEPARTMENT
                + "&"
                + NetConstants.NEWS_LIST_PARAM_THREE
                + "="
                + AppConstants.NOTICE_TYPE;
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
            Toast.makeText(getContext(),"已加载到最后一页",Toast.LENGTH_SHORT).show();
        }
    }

}
