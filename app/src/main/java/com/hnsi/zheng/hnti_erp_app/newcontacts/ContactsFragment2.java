package com.hnsi.zheng.hnti_erp_app.newcontacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.activities.SearchActivity;
import com.hnsi.zheng.hnti_erp_app.app.MyApplication;
import com.hnsi.zheng.hnti_erp_app.app.NetConstants;
import com.hnsi.zheng.hnti_erp_app.beans.DepartmentDetailEntity;
import com.hnsi.zheng.hnti_erp_app.beans.DepartmentEntity2;
import com.hnsi.zheng.hnti_erp_app.database.ConstactsInfoTableHelper;
import com.hnsi.zheng.hnti_erp_app.database.DepartmentInfoTableHelper;
import com.hnsi.zheng.hnti_erp_app.http.MyJsonObjectRequest;
import com.hnsi.zheng.hnti_erp_app.http.VolleyHelper;
import com.hnsi.zheng.hnti_erp_app.utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 碎片界面（通讯录）
 * Created by Zheng on 2016/4/29.
 */
public class ContactsFragment2 extends Fragment {
    //标题头返回按钮
    private ImageButton mBackBtn;
    //页面标题
    private TextView mTitleTv;
    //部门列表
    private RecyclerView mDepartmentRv;
    private LinearLayoutManager manager;
    private MyRecyclerAdapter adapter;

    //数据库助手类
    private ConstactsInfoTableHelper mContactsInfoTableHelper;
    private DepartmentInfoTableHelper mDepartmentInfoTableHelper;

    private SwipeRefreshLayout mRefreshLayout;

    /** 搜索框 */
    private TextView searchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mRootView=inflater.inflate(R.layout.aafragment_contacts,container,false);

        initUI(mRootView);

        if(MyApplication.isLoadContacts()){
            loadNetData();
            MyApplication.isNotLoadContacts();
        }else {
            loadUI();
        }

        return mRootView;
    }

    /**
     * 加载网络数据的方法
     */
    private void loadNetData(){
        mRefreshLayout.setRefreshing(true);

        /** 在此处进行网络数据请求 */
        String url= Tools.jointIpAddress()
                + NetConstants.CONTACTS_PORT;

        MyJsonObjectRequest request=new MyJsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            if(jsonObject.getBoolean("success")){

                                //加载数据前清空数据库数据和内存数据
                                mDepartmentInfoTableHelper.deleteAllDepartments();
                                mContactsInfoTableHelper.deleteAllContacts();
                                adapter.clearAdapter();

                                //把多有人员信息装进数据库
                                JSONArray contactArray=jsonObject.getJSONArray("empList");
                                List<DepartmentDetailEntity>emplist=new ArrayList<>();
                                for(int c=0;c<contactArray.length();c++){
                                    DepartmentDetailEntity entity=new DepartmentDetailEntity();
                                    JSONObject obj=contactArray.getJSONObject(c);
                                    entity.setEmpname(obj.getString("empname"));
                                    entity.setMobileno(obj.getString("mobileno"));
                                    entity.setOtel(obj.getString("otel"));
                                    entity.setOemail(obj.getString("oemail"));
                                    entity.setSex(obj.getString("sex"));
                                    entity.setOrgid(obj.getInt("orgid"));
                                    entity.setOrgname(obj.getString("orgname"));
                                    entity.setPosiname(obj.getString("posiname"));
                                    entity.setEmpid(obj.getInt("empid"));
                                    if(!"null".equals(obj.getString("headimg"))){
                                        entity.setHeadimgUrl(
                                                Tools.jointIpAddress()+obj.getString("headimg"));
                                    }else{
                                        entity.setHeadimgUrl(obj.getString("headimg"));
                                    }
                                    emplist.add(entity);
                                }
                                mContactsInfoTableHelper.insertAll(emplist);

                                JSONArray departmentArray=jsonObject.getJSONArray("orgList");
                                List<DepartmentEntity2>orglist=new ArrayList<>();
                                for(int a=0;a<departmentArray.length();a++){
                                    DepartmentEntity2 entity=new DepartmentEntity2();
                                    JSONObject object=departmentArray.getJSONObject(a);
                                    int orgid=object.getInt("orgid");
                                    entity.setOrgid(orgid);
                                    entity.setOrgname(object.getString("orgname"));
                                    try{
                                        entity.setParentorgid(object.getInt("parentorgid"));
                                    }catch (Exception e){
                                        e.printStackTrace();
                                        entity.setParentorgid(0);
                                    }
                                    entity.setChildrenNum(mContactsInfoTableHelper.queryContactNumByOrgid(orgid));
                                    orglist.add(entity);
                                }
                                mDepartmentInfoTableHelper.insertAll(orglist);

                                loadUI();

                                mRefreshLayout.setRefreshing(false);

                            }else{
                                mRefreshLayout.setRefreshing(false);
                                Toast.makeText(getContext(),jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            mRefreshLayout.setRefreshing(false);
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(),"网络请求失败，请检查网络",Toast.LENGTH_SHORT).show();
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(10*1000,1,1.0f));

        VolleyHelper.getInstance(getContext()).addRequestTask(request, NetConstants.TAG_CONTACTS);
    }

    private void loadUI(){
        ArrayList<ItemData>firstDepartmentList=mDepartmentInfoTableHelper.queryAllFirstDepartment();
        if (firstDepartmentList.size()>0)
            adapter.addAll(firstDepartmentList,0);
        else
            Toast.makeText(getActivity(),"数据无效，请下拉刷新",Toast.LENGTH_SHORT).show();
    }

    /**
     * 初始化界面
     * @param mRootView 根视图
     */
    private void initUI(View mRootView) {

        initTitleBar(mRootView);

        searchView = (TextView) mRootView.findViewById(R.id.main_search_layout);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        mRefreshLayout= (SwipeRefreshLayout) mRootView.findViewById(R.id.contacts_refresh_view);
        mRefreshLayout.setColorSchemeResources(R.color.backgroundBlue,R.color.holo_blue_bright);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNetData();
            }
        });

        mDepartmentRv= (RecyclerView) mRootView.findViewById(R.id.recyclerview);
        manager=new LinearLayoutManager(getActivity());
        mDepartmentRv.setLayoutManager(manager);

        mDepartmentRv.getItemAnimator().setAddDuration(200);
        mDepartmentRv.getItemAnimator().setRemoveDuration(200);
        mDepartmentRv.getItemAnimator().setMoveDuration(200);
        mDepartmentRv.getItemAnimator().setChangeDuration(200);
        adapter=new MyRecyclerAdapter(getActivity());
        mDepartmentRv.setAdapter(adapter);
        mDepartmentRv.addItemDecoration(new MyItemDecoration());

        mDepartmentInfoTableHelper=new DepartmentInfoTableHelper(getContext());
        //初始化联系人信息表助手类
        mContactsInfoTableHelper=new ConstactsInfoTableHelper(getContext());
    }

    /**
     * 初始化标题头
     * @param mRootView 根视图
     */
    private void initTitleBar(View mRootView) {
        mBackBtn= (ImageButton) mRootView.findViewById(R.id.titlebar_ibtn_back);
        mBackBtn.setVisibility(View.INVISIBLE);
        mTitleTv= (TextView) mRootView.findViewById(R.id.titlebar_tv_title);
        mTitleTv.setText("通讯录");
    }

}