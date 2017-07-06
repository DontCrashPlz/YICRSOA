package com.hnsi.zheng.hnti_erp_app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.activities.JingBanRenListActivity;
import com.hnsi.zheng.hnti_erp_app.activities.SearchActivity;
import com.hnsi.zheng.hnti_erp_app.adapters.DepartmentAdapter3;
import com.hnsi.zheng.hnti_erp_app.app.ACache;
import com.hnsi.zheng.hnti_erp_app.app.AppConstants;
import com.hnsi.zheng.hnti_erp_app.app.MyApplication;
import com.hnsi.zheng.hnti_erp_app.app.NetConstants;
import com.hnsi.zheng.hnti_erp_app.beans.DepartmentDetailEntity;
import com.hnsi.zheng.hnti_erp_app.beans.DepartmentEntity;
import com.hnsi.zheng.hnti_erp_app.database.ConstactsInfoTableHelper;
import com.hnsi.zheng.hnti_erp_app.http.MyJsonObjectRequest;
import com.hnsi.zheng.hnti_erp_app.http.VolleyHelper;
import com.hnsi.zheng.hnti_erp_app.utils.SharedPrefUtils;
import com.hnsi.zheng.hnti_erp_app.utils.Tools;
import com.hnsi.zheng.hnti_erp_app.widgets.progressDialog.BaseProgressDialog;
import com.hnsi.zheng.hnti_erp_app.widgets.progressDialog.ProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 碎片界面（通讯录）
 * Created by Zheng on 2016/4/29.
 */
public class ContactsFragment extends Fragment{
    //标题头返回按钮
    private ImageButton mBackBtn;
    //页面标题
    private TextView mTitleTv;
    //刷新按钮
//    private ImageView mRefreshIv;
    //部门列表
    private ListView mDepartmentLv;
    //部门列表数据Adapter
    private DepartmentAdapter3 mDepartmentAdapter;
    //加载进度框
    private BaseProgressDialog dialog;
    //定义通讯录在缓存中的标识key
    private final static String CONTACTS_CACHE_KEY="CONTACTS_CACHE_KEY";
    //定义通讯录列表数据集合
    private ArrayList<DepartmentEntity> mRootDepaetmentList;

    //ListView的添加脚footView
    private TextView mContactSumTv;
    private View mFootView;
    private int contactSum;

    //数据库助手类
    private ConstactsInfoTableHelper mContactsInfoTableHelper;

    private SwipeRefreshLayout mRefreshLayout;

    /** 搜索框 */
    private TextView searchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mRootView=inflater.inflate(R.layout.fragment_contacts,container,false);

        initUI(mRootView);

        mFootView=inflater.inflate(R.layout.layout_num_of_contact,mDepartmentLv,false);
        mContactSumTv= (TextView) mFootView.findViewById(R.id.contacts_sum);
        mDepartmentLv.addFooterView(mFootView);

        mDepartmentAdapter=new DepartmentAdapter3(getActivity(),new ArrayList());

        /** 在此处进行判断本地缓存中是否有有效的通讯录缓存数据，如果没有就加载网络通讯录数据，如果有就加载缓存中的通讯录数据 */
        if(ACache.get(getContext()).getAsObject(CONTACTS_CACHE_KEY)!=null){
            dialog.show();

            mRootDepaetmentList= (ArrayList<DepartmentEntity>) ACache.get(getContext()).getAsObject(CONTACTS_CACHE_KEY);
            Log.e("Contacts", mRootDepaetmentList.toString());
            mDepartmentAdapter.clear();
            mDepartmentAdapter.addItems(mRootDepaetmentList);
            mDepartmentLv.setAdapter(mDepartmentAdapter);

            contactSum= (int) SharedPrefUtils.get(getContext(), AppConstants.CONTACTS_SUM, 0);
            notifyActivity();

            dialog.dismiss();

        }else{//请求网络
            dialog.show();
            loadNetData();
        }

        return mRootView;
    }

    /**
     * 加载网络数据的方法
     */
    private void loadNetData(){
//        dialog.show();
        mRefreshLayout.setRefreshing(true);

        /** 在此处进行网络数据请求 */
        final String url= Tools.jointIpAddress()
                + NetConstants.CONTACTS_PORT;

        MyJsonObjectRequest request=new MyJsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            if(jsonObject.getBoolean("success")){
                                //初始化联系人信息表助手类
                                mContactsInfoTableHelper=new ConstactsInfoTableHelper(getContext());

                                //加载数据前清空数据库数据和内存数据
                                mDepartmentAdapter.clear();
                                mContactsInfoTableHelper.deleteAllContacts();

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
                                    }
                                    emplist.add(entity);
                                }
                                mContactsInfoTableHelper.insertAll(emplist);

                                ArrayList <DepartmentEntity> orgList=new ArrayList();
                                JSONArray orgArray=jsonObject.getJSONArray("orgList");
                                for(int i=0;i<orgArray.length();i++){

                                    //提取部门信息
                                    JSONObject departmentObj=orgArray.getJSONObject(i);
                                    DepartmentEntity departmentEntity=new DepartmentEntity();
                                    departmentEntity.setOrgid(departmentObj.getInt("orgid"));
                                    departmentEntity.setOrgname(departmentObj.getString("orgname"));
                                    if(departmentObj.getInt("orgid")==1){
                                        departmentEntity.setParentorgid(0);
                                    }else{
                                        departmentEntity.setParentorgid(departmentObj.getInt("parentorgid"));
                                    }

                                    ArrayList<DepartmentDetailEntity> empList=
                                            mContactsInfoTableHelper.queryAllOrgidContacts(departmentObj.getInt("orgid"));

                                    if(empList!=null){
                                        departmentEntity.setDetailList(empList);
                                    }
                                    orgList.add(departmentEntity);
                                }

                                /** 在此处纪录网络请求下的通讯录的人员数量 */
                                contactSum=jsonObject.getJSONArray("empList").length();
                                Log.e("contactsum",""+contactSum);
                                SharedPrefUtils.put(getContext(), AppConstants.CONTACTS_SUM, contactSum);

                                notifyActivity();

                                //一级部门列表
                                mRootDepaetmentList=new ArrayList<>();
                                for(int a=0;a<orgList.size();a++){
                                    if(orgList.get(a).getParentorgid()==1){
                                        mRootDepaetmentList.add(orgList.get(a));
                                    }
                                }

                                //二级部门列表
                                ArrayList<DepartmentEntity>mChildDepartmentList=new ArrayList<>();
                                for(int b=0;b<orgList.size();b++){
                                    if(orgList.get(b).getParentorgid()>1){
                                        mChildDepartmentList.add(orgList.get(b));
                                    }
                                }

                                //把二级部门装到对应的一级部门中
                                for(int c=0;c<mRootDepaetmentList.size();c++){
                                    int parentId=mRootDepaetmentList.get(c).getOrgid();
                                    for(int d=0;d<mChildDepartmentList.size();d++){
                                        if(mChildDepartmentList.get(d).getParentorgid()==parentId){
                                            mRootDepaetmentList.get(c).getChildDepartmentList().add(mChildDepartmentList.get(d));
                                        }
                                    }
                                }

                                /** 在此处将网络请求的数据存入缓存中,设置过期时间为七天 */
                                ACache cache=ACache.get(getContext());
                                cache.put(CONTACTS_CACHE_KEY, mRootDepaetmentList, ACache.TIME_DAY * 7);

                                //把封装好的一级部门列表分发给适配器
                                mDepartmentAdapter.addItems(mRootDepaetmentList);
                                mDepartmentAdapter.notifyDataSetChanged();
                                mDepartmentLv.setAdapter(mDepartmentAdapter);

                                mRefreshLayout.setRefreshing(false);
                                dialog.dismiss();

                            }else{
                                mRefreshLayout.setRefreshing(false);
                                dialog.dismiss();
                                Toast.makeText(getContext(),jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            mRefreshLayout.setRefreshing(false);
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mRefreshLayout.setRefreshing(false);
                dialog.dismiss();
                Toast.makeText(getContext(),"网络请求失败，请检查网络",Toast.LENGTH_SHORT).show();
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(10*1000,1,1.0f));

        VolleyHelper.getInstance(getContext()).addRequestTask(request, NetConstants.TAG_CONTACTS);
    }

    /**
     * 初始化界面
     * @param mRootView 根视图
     */
    private void initUI(View mRootView) {

        dialog=new ProgressDialog(getContext());
        dialog.setLabel("请稍等...");

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

        mDepartmentLv= (ListView) mRootView.findViewById(R.id.contacts_lv_content);

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

    /**
     * 更新界面
     */
    public void notifyActivity() {
        mContactSumTv.setText(String.format(getString(R.string.num_of_contact),""+contactSum));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}