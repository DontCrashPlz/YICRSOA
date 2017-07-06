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
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.activities.UnFinishedMatterActivity;
import com.hnsi.zheng.hnti_erp_app.adapters.UnFinishedListAdapter;
import com.hnsi.zheng.hnti_erp_app.app.AppConstants;
import com.hnsi.zheng.hnti_erp_app.app.NetConstants;
import com.hnsi.zheng.hnti_erp_app.beans.UnFinishedEntity;
import com.hnsi.zheng.hnti_erp_app.http.MyJsonObjectRequest;
import com.hnsi.zheng.hnti_erp_app.http.VolleyHelper;
import com.hnsi.zheng.hnti_erp_app.utils.Tools;
import com.hnsi.zheng.hnti_erp_app.widgets.CircularProgressView;
import com.hnsi.zheng.hnti_erp_app.widgets.progressDialog.BaseProgressDialog;
import com.hnsi.zheng.hnti_erp_app.widgets.progressDialog.ProgressDialog;
import com.hnsi.zheng.hnti_erp_app.widgets.pullListView.OnListViewListener;
import com.hnsi.zheng.hnti_erp_app.widgets.pullListView.PullListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 待办事项碎片界面
 * Created by Zheng on 2016/7/7.
 */
@Deprecated
public class UnFinishedMatterFragment extends Fragment implements OnListViewListener,AdapterView.OnItemClickListener{

    //请求待办列表url（使用时需要拼接参数值）
    private String mUnFinishedListUrl= Tools.jointIpAddress()
            + NetConstants.UNFINISHED_LIST_PORT
            + "?"
            + NetConstants.UNFINISHED_LIST_PARAM
            + "=";

    //内容列表
    private PullListView mContentPlv;
    //加载进度条
    private CircularProgressView mCircularPregress;
    //加载框
    private BaseProgressDialog dialog;
    //待办列表数据Adapter
    private UnFinishedListAdapter mUnFinishedListAdapter;
    //目前的页数索引
    private int mPageIndex=1;
    //最大页数
    private int mMaxIndex;
    //urlMap
    private Map<String,String> mUrlMap=new HashMap<>();
    //待办事项列表
    private ArrayList<UnFinishedEntity> mUnFinishedList;
    //已加载的事项的集合
    private ArrayList mLoadedList=new ArrayList();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mRootView=inflater.inflate(R.layout.fragment_unfinished_matter,container,false);

        initUI(mRootView);

        initData();

        return mRootView;
    }

    /**
     * 初始化界面
     * @param mRootView
     */
    private void initUI(View mRootView) {

        mContentPlv= (PullListView) mRootView.findViewById(R.id.approval_plv_content);
        mContentPlv.setOnListViewListener(this);
        mContentPlv.setOnItemClickListener(this);

        mUnFinishedListAdapter=new UnFinishedListAdapter(getContext(),new ArrayList());
        mContentPlv.setAdapter(mUnFinishedListAdapter);

        dialog =new ProgressDialog(getContext());
        dialog.setLabel("加载中...");
//        mCircularPregress= (CircularProgressView) mRootView.findViewById(R.id.circular_progress);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        loadData(getListUrl(mPageIndex));
    }

    /**
     * 实际加载数据的方法
     * @param listUrl
     */
    private void loadData(final String listUrl) {
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

                                    mUnFinishedList=new ArrayList<UnFinishedEntity>();
                                    UnFinishedEntity entity;
                                    JSONArray list=jsonObject.getJSONArray("taskList");
                                    for(int i=0;i<list.length();i++){
                                        entity=new UnFinishedEntity();
                                        entity.setProcessChName(list.getJSONObject(i).getString("processChName"));
                                        entity.setProcessInstName(list.getJSONObject(i).getString("processInstName"));
                                        entity.setStartTime(list.getJSONObject(i).getString("startTime"));
                                        entity.setWorkItemID(list.getJSONObject(i).getInt("workItemID"));
                                        entity.setActivityDefID(list.getJSONObject(i).getString("activityDefID"));
                                        entity.setProcessInstID(list.getJSONObject(i).getInt("processInstID"));
                                        entity.setProcessDefName(list.getJSONObject(i).getString("processDefName"));
                                        mUnFinishedList.add(entity);
                                    }
                                    mUnFinishedListAdapter.addItems(mUnFinishedList);
                                    mLoadedList.addAll(mUnFinishedList);
                                    mContentPlv.stopRefresh();
                                }else{
                                    Toast.makeText(getContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                                    mContentPlv.stopRefresh();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"网络请求失败，请检查网络",Toast.LENGTH_SHORT).show();
                mContentPlv.stopRefresh();
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
        String urlString = mUnFinishedListUrl+index;
        return urlString;
    }

    /**
     * 下拉刷新的方法
     */
    @Override
    public void onRefresh() {
        //清空待办事项列表数据
        mUnFinishedListAdapter.clear();
        //清空已加载集合
        mLoadedList.clear();
        //清空urlMap
        mUrlMap.clear();
        //加载网络数据
        mPageIndex = 1;
        loadData(getListUrl(mPageIndex));
    }

    /**
     * 上拉加载更多的方法
     */
    @Override
    public void onLoadMore() {
        mPageIndex += 1;
        Log.e("onLoadMore", "" + mPageIndex);
        if(mPageIndex<=mMaxIndex){
            loadData(getListUrl(mPageIndex));
        }else{
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

//            startAnimationThreadStuff(100);
            dialog.show();

            final UnFinishedEntity entity= (UnFinishedEntity) mLoadedList.get(position-1);

            String url=Tools.jointIpAddress()
                    + mUrlMap.get(entity.getProcessDefName())
                    + "?"
                    + "workItemID"
                    + "="
                    + entity.getWorkItemID()
                    + "&"
                    + "activityDefID"
                    + "="
                    + entity.getActivityDefID()
                    + "&"
                    + "processInstID"
                    + "="
                    + entity.getProcessInstID();

            Log.e("url",url);

            MyJsonObjectRequest jsonObjectRequest=new MyJsonObjectRequest(
                    url,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                if(jsonObject.getBoolean("success")){
//                                    MatterInfoEntity matterInfoEntity=new MatterInfoEntity();
//                                    matterInfoEntity.setJsonObject(jsonObject);
                                    String jsonStr=jsonObject.toString();
                                    Log.e("String", jsonStr);
                                    Intent intent=new Intent();
                                    intent.putExtra(AppConstants.UNFINISHED_MATTER_ENTITY,jsonStr);
                                    intent.putExtra(AppConstants.MATTER_PROCESSINSTID, entity.getProcessInstID());
                                    intent.setClass(getContext(), UnFinishedMatterActivity.class);

                                    dialog.dismiss();

                                    startActivity(intent);
//                                    mCircularPregress.setVisibility(View.GONE);
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

//    /**
//     * 延时打开环形进度条
//     */
//    private Thread updateThread;
//    private void startAnimationThreadStuff(long delay)
//    {
//        if(updateThread != null && updateThread.isAlive())
//            updateThread.interrupt();
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            public void run() {
//                mCircularPregress.setVisibility(View.VISIBLE);
//                mCircularPregress.setProgress(0f);
//                mCircularPregress.startAnimation(); // Alias for resetAnimation, it's all the same
//                updateThread = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        while (mCircularPregress.getProgress() < mCircularPregress.getMaxProgress() && !Thread.interrupted()) {
//                            handler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    mCircularPregress.setProgress(mCircularPregress.getProgress() + 10);
//                                }
//                            });
//                            SystemClock.sleep(250);
//                        }
//                    }
//                });
//                updateThread.start();
//            }
//        }, delay);
//    }

}
