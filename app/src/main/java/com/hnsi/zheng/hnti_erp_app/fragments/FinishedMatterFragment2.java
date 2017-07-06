package com.hnsi.zheng.hnti_erp_app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.activities.ApprovalActivity2;
import com.hnsi.zheng.hnti_erp_app.activities.FinishedMatterActivity;
import com.hnsi.zheng.hnti_erp_app.activities.UnFinishedMatterActivity;
import com.hnsi.zheng.hnti_erp_app.adapters.FinishedListAdapter;
import com.hnsi.zheng.hnti_erp_app.adapters.UnFinishedListAdapter;
import com.hnsi.zheng.hnti_erp_app.app.AppConstants;
import com.hnsi.zheng.hnti_erp_app.app.NetConstants;
import com.hnsi.zheng.hnti_erp_app.beans.FinishedEntity;
import com.hnsi.zheng.hnti_erp_app.beans.UnFinishedEntity;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 已办事项列表页面
 * Created by Zheng on 2016/7/20.
 */
public class FinishedMatterFragment2 extends Fragment implements AdapterView.OnItemClickListener{

    private PullToRefreshLayout pullToRefreshLayout;
    private ListView listView;

    //请求已办列表url（使用时需要拼接参数值）
    private String mFinishedListUrl=Tools.jointIpAddress()
            + NetConstants.FINISHED_LIST_PORT
            + "?"
            + NetConstants.FINISHED_LIST_PARAM
            + "=";

    //加载框
    private BaseProgressDialog dialog;
    //已办列表数据Adapter
    private FinishedListAdapter mFinishedListAdapter;
    //目前的页数索引
    private int mPageIndex=1;
    //最大页数
    private int mMaxIndex;
    //urlMap
    private Map<String,String> mUrlMap=new HashMap<>();
    //待办事项列表
    private ArrayList<FinishedEntity> mFinishedList;
    //已加载的事项的集合
    private ArrayList mLoadedList=new ArrayList();

    //列表无数据提醒
    private TextView noDateTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRootView=inflater.inflate(R.layout.fragment_unfinished_matter,container,false);

        initUI(mRootView);

        return mRootView;
    }

    private void initUI(View mRootView) {

        noDateTv= (TextView) mRootView.findViewById(R.id.refresh_no_date);

        pullToRefreshLayout= (PullToRefreshLayout) mRootView.findViewById(R.id.refresh_view);
        pullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                refresh(pullToRefreshLayout);
            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                loadMore(pullToRefreshLayout);
            }
        });
        listView= (ListView) mRootView.findViewById(R.id.content_view);
        mFinishedListAdapter=new FinishedListAdapter(getContext(),new ArrayList());
        listView.setAdapter(mFinishedListAdapter);
        listView.setOnItemClickListener(this);

        dialog =new ProgressDialog(getContext());
        dialog.setLabel("加载中...");

        initData();
    }


    /**
     * 初始化数据
     */
    private void initData() {
        dialog.show();

        String initUrl=mFinishedListUrl+"1";

        Log.e("initURL",initUrl);

        MyJsonObjectRequest jsonObjectRequest=new MyJsonObjectRequest(
                initUrl,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            if(jsonObject.getBoolean("success")){
                                mMaxIndex=jsonObject.getInt("totalPage");

                                //把urlMap中的数据装到map中
                                Iterator it=jsonObject.getJSONObject("urlMap").keys();
                                while(it.hasNext()){
                                    String key=(String) it.next();
                                    mUrlMap.put(key,jsonObject.getJSONObject("urlMap").getString(key));
                                }

                                Log.e("map",mUrlMap.toString());

                                mFinishedList=new ArrayList<>();
                                FinishedEntity entity;
                                JSONArray list=jsonObject.getJSONArray("list");
                                for(int i=0;i<list.length();i++){
                                    entity=new FinishedEntity();
                                    entity.setProcessChName(list.getJSONObject(i).getString("processChName"));
                                    entity.setProcessInstName(list.getJSONObject(i).getString("processInstName"));
                                    entity.setStartTime(list.getJSONObject(i).getString("startTime"));
                                    entity.setProcessInstId(list.getJSONObject(i).getString("processInstId"));
                                    entity.setProcessDefName(list.getJSONObject(i).getString("processDefName"));
                                    mFinishedList.add(entity);
                                }
                                mFinishedListAdapter.addItems(mFinishedList);
                                mLoadedList.addAll(mFinishedList);

                                if(mFinishedList.size()>0){
                                    noDateTv.setVisibility(View.GONE);
                                }else{
                                    noDateTv.setVisibility(View.VISIBLE);
                                }

                                dialog.dismiss();
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
                .addRequestTask(jsonObjectRequest, initUrl);
    }


    /**
     * 实际加载数据的方法
     * @param listUrl
     */
    private void loadData(final String listUrl, final PullToRefreshLayout pullToRefreshLayout) {
        Log.e("loadData", listUrl);

        MyJsonObjectRequest jsonObjectRequest=new MyJsonObjectRequest(
                listUrl,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            if(jsonObject.getBoolean("success")){
                                mMaxIndex=jsonObject.getInt("totalPage");

                                //把urlMap中的数据装到map中
                                Iterator it=jsonObject.getJSONObject("urlMap").keys();
                                while(it.hasNext()){
                                    String key=(String) it.next();
                                    mUrlMap.put(key,jsonObject.getJSONObject("urlMap").getString(key));
                                }

                                Log.e("map",mUrlMap.toString());

                                mFinishedList=new ArrayList<>();
                                FinishedEntity entity;
                                JSONArray list=jsonObject.getJSONArray("list");
                                for(int i=0;i<list.length();i++){
                                    entity=new FinishedEntity();
                                    entity.setProcessChName(list.getJSONObject(i).getString("processChName"));
                                    entity.setProcessInstName(list.getJSONObject(i).getString("processInstName"));
                                    entity.setStartTime(list.getJSONObject(i).getString("startTime"));
                                    entity.setProcessInstId(list.getJSONObject(i).getString("processInstId"));
                                    entity.setProcessDefName(list.getJSONObject(i).getString("processDefName"));
                                    mFinishedList.add(entity);
                                }
                                mFinishedListAdapter.addItems(mFinishedList);
                                mLoadedList.addAll(mFinishedList);

                                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
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
                .addRequestTask(jsonObjectRequest, listUrl);
    }


    /**
     * 获取URL
     * @param index
     * @return
     */
    public String getListUrl(int index) {
        String urlString = mFinishedListUrl+index;
        Log.e("finishedList",urlString);
        return urlString;
    }

    /**
     * 下拉刷新的方法
     */
    public void refresh(PullToRefreshLayout pullToRefreshLayout) {
        //清空待办事项列表数据
        mFinishedListAdapter.clear();
        //清空已加载集合
        mLoadedList.clear();
        //清空urlMap
        mUrlMap.clear();
        //加载网络数据
        mPageIndex = 1;
        loadData(getListUrl(mPageIndex),pullToRefreshLayout);
    }

    /**
     * 上拉加载更多的方法
     */
    public void loadMore(PullToRefreshLayout pullToRefreshLayout) {
        mPageIndex += 1;
        Log.e("onLoadMore", "" + mPageIndex);
        if(mPageIndex<=mMaxIndex){
            loadData(getListUrl(mPageIndex),pullToRefreshLayout);
        }else{
            pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
            Toast.makeText(getContext(),"已加载到最后一页",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 列表点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        dialog.show();

        final FinishedEntity entity= (FinishedEntity) mLoadedList.get(position);

        String url=Tools.jointIpAddress()
                + mUrlMap.get(entity.getProcessDefName())
                + "?"
                + "processInstID"
                + "="
                + entity.getProcessInstId();

        Log.e("url",url);

        MyJsonObjectRequest jsonObjectRequest=new MyJsonObjectRequest(
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            if(jsonObject.getBoolean("success")){
                                String jsonStr=jsonObject.toString();
                                Log.e("String", jsonStr);
                                Intent intent=new Intent();
                                intent.putExtra(AppConstants.FINISHED_MATTER_ENTITY,jsonStr);
                                intent.putExtra(AppConstants.MATTER_PROCESSINSTID,entity.getProcessInstId());
                                intent.setClass(getContext(), FinishedMatterActivity.class);

                                dialog.dismiss();

                                startActivity(intent);
                            }else{
                                dialog.dismiss();
                                Toast.makeText(getContext(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
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
                .addRequestTask(jsonObjectRequest, url);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(((ApprovalActivity2)getActivity()).getIsApproval()){
            if(pullToRefreshLayout!=null){
                refresh(pullToRefreshLayout);
                ((ApprovalActivity2)getActivity()).setIsApprovaled(false);
            }
        }
    }
}
