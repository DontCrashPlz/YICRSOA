package com.hnsi.zheng.hnti_erp_app.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.activities.ApprovalActivity2;
import com.hnsi.zheng.hnti_erp_app.activities.MessageActivity;
import com.hnsi.zheng.hnti_erp_app.adapters.NoticeAdapter2;
import com.hnsi.zheng.hnti_erp_app.app.AppConstants;
import com.hnsi.zheng.hnti_erp_app.app.NetConstants;
import com.hnsi.zheng.hnti_erp_app.beans.NewsEntity;
import com.hnsi.zheng.hnti_erp_app.http.MyJsonObjectRequest;
import com.hnsi.zheng.hnti_erp_app.http.VolleyHelper;
import com.hnsi.zheng.hnti_erp_app.utils.Tools;
import com.hnsi.zheng.hnti_erp_app.widgets.PromotedActionsLibrary2;
import com.hnsi.zheng.hnti_erp_app.widgets.progressDialog.BaseProgressDialog;
import com.hnsi.zheng.hnti_erp_app.widgets.progressDialog.ProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 碎片界面（信息）
 * Created by Zheng on 2016/4/28.
 */
public class MessageFragment extends Fragment{
    //返回按钮
    private ImageButton mBackBtn;
    //页面标题
    private TextView mTitleTv;

//    //天气显示
//    private LinearLayout mWeatherLly;
//    //实时天气
//    private TextView mTempuratureTv;
//    //天气图标
//    private ImageView mClimateIv;

    //新闻公告列表
    private ListView mNoticeLv;
    //加载框
    private BaseProgressDialog dialog;

    //列表数据Adapter
    private NoticeAdapter2 adapter;

    //新闻列表数据
//    ArrayList<NewsEntity> mNewsList=new ArrayList<>();

    //待办审批数量
    private int approvalNum;
    //消息数量
    private int messageNum;
    //上次待办数量
    private int lastApprovalNum;
    //上次消息数量
    private int lastMessageNum;

    private PromotedActionsLibrary2 promotedActionsLibrary;

    private FrameLayout frameLayout;

    private Handler mHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mRootView=inflater.inflate(R.layout.fragment_msg,container,false);

        initUI(mRootView);

        initFlowMenu(mRootView);

        return mRootView;
    }

    /**
     * 加载流程菜单
     * @param mRootView 根视图
     */
    private void initFlowMenu(View mRootView) {

        Log.e("initMenuGo","In");

        frameLayout = (FrameLayout) mRootView.findViewById(R.id.container);

        promotedActionsLibrary = new PromotedActionsLibrary2();

        promotedActionsLibrary.setup(getActivity().getApplicationContext(), frameLayout);

//        promotedActionsLibrary.addItem(getResources().getDrawable(R.mipmap.shenpi), R.mipmap.shenpi_back, approvalNum, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                /** 跳转到待办事项列表页 */
//                Intent intent = new Intent();
//                intent.setClass(getContext(), ApprovalActivity2.class);
//                getContext().startActivity(intent);
//            }
//        });
//
//        promotedActionsLibrary.addItem(getResources().getDrawable(R.mipmap.xiaoxi), R.mipmap.xiaoxi_back, messageNum, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                /** 跳转到系统信息列表页 */
//                Intent intent = new Intent();
//                intent.setClass(getContext(), MessageActivity.class);
//                getContext().startActivity(intent);
//            }
//        });
//
//        promotedActionsLibrary.addMainItem(getResources().getDrawable(R.mipmap.menu), R.mipmap.menu_back, approvalNum + messageNum);

        mHandler=new Handler(){

            @Override
            public void handleMessage(Message msg) {
                refreshFlowMenu();
//                if(msg.what==0){
//                    Log.e("what=0","In");
//                    if(approvalNum+messageNum!=0){
//                        Log.e("what=0","Refresh");
//                        refreshFlowMenu();
//                        lastApprovalNum=approvalNum;
//                        lastMessageNum=messageNum;
//                    }
//                }else if(msg.what==1){
//                    Log.e("what=1","In");
//                    if(lastApprovalNum!=approvalNum||lastMessageNum!=messageNum){
//                        Log.e("what=1","Refresh");
//                        refreshFlowMenu();
//                    }
//                }
            }

        };

    }

    /**
     * 刷新流程菜单
     */
    private void refreshFlowMenu(){

        Log.e("refreshFlowNum","gone");

        promotedActionsLibrary.clearAllItem();

        promotedActionsLibrary.addItem(ResourcesCompat.getDrawable(getResources(),R.mipmap.shenpi,null), R.mipmap.shenpi_back, approvalNum, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** 跳转到待办事项列表页 */
                Intent intent=new Intent();
                intent.setClass(getContext(),ApprovalActivity2.class);
                getContext().startActivity(intent);
            }
        });

        promotedActionsLibrary.addItem(ResourcesCompat.getDrawable(getResources(),R.mipmap.xiaoxi,null), R.mipmap.xiaoxi_back, messageNum, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** 跳转到系统信息列表页 */
                Intent intent = new Intent();
                intent.setClass(getContext(), MessageActivity.class);
                getContext().startActivity(intent);
            }
        });

        promotedActionsLibrary.addMainItem(ResourcesCompat.getDrawable(getResources(),R.mipmap.menu,null), R.mipmap.menu_back, approvalNum + messageNum);

//        promotedActionsLibrary.lastState();
    }

    /**
     * 在OnResume中加载数量
     */
    @Override
    public void onResume() {
        super.onResume();

        Log.e("onResume","gone");

        loadNum();
    }

    /**
     * 初始化界面
     * @param mRootView 根视图
     */
    private void initUI(final View mRootView) {

        Log.e("initUIGo","In");

        dialog=new ProgressDialog(getContext());
        dialog.setLabel("请稍等...");

        //加载并隐藏返回按钮
        mBackBtn= (ImageButton) mRootView.findViewById(R.id.titlebar_ibtn_back);
        mBackBtn.setVisibility(View.INVISIBLE);

        //加载并设置页面标题
        mTitleTv= (TextView) mRootView.findViewById(R.id.titlebar_tv_title);
        mTitleTv.setText("信息");

//        mWeatherLly= (LinearLayout) mRootView.findViewById(R.id.msg_titlebar_weather_panel);
//        mWeatherLly.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Tools.skipActivity(getContext(), WeatherActivity.class);
//            }
//        });
//
//        //加载实时天气
//        mTempuratureTv= (TextView) mRootView.findViewById(R.id.msg_titlebar_weather);
//
//        //加载天气图标
//        mClimateIv= (ImageView) mRootView.findViewById(R.id.msg_titlebar_weather_icon);

//        //加载并为待办流程设置数量
//        mUnFinishedRly= (RelativeLayout) mRootView.findViewById(R.id.msg_frag_rly_unfinished);
//        mUnFinishedRly.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                /** 跳转到待办事项列表页 */
//                Intent intent=new Intent();
//                intent.setClass(getContext(),ApprovalActivity2.class);
//                getContext().startActivity(intent);
//            }
//        });
//
//        //加载并为公文传阅设置数量
//        mOfficialRly= (RelativeLayout) mRootView.findViewById(R.id.msg_frag_rly_official);
//        mOfficialRly.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                /** 跳转到公文传阅列表页 */
//            }
//        });
//
//        //加载并为系统信息设置数量
//        mSystemRly= (RelativeLayout) mRootView.findViewById(R.id.msg_frag_rly_system);
//        mSystemRly.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                /** 跳转到系统信息列表页 */
//                Intent intent=new Intent();
//                intent.setClass(getContext(),MessageActivity.class);
//                getContext().startActivity(intent);
//            }
//        });
//
//        mUnFinishedNumTv= (TextView) mRootView.findViewById(R.id.msg_frag_tv_unfinished_num);
//        mOfficialNumTv= (TextView) mRootView.findViewById(R.id.msg_frag_tv_official_num);
//        mSystemNumTv= (TextView) mRootView.findViewById(R.id.msg_frag_tv_system_num);

//        frameLayout = (FrameLayout) mRootView.findViewById(R.id.container);
//        promotedActionsLibrary = new PromotedActionsLibrary2();
//        promotedActionsLibrary.setup(getActivity().getApplicationContext(), frameLayout);

//        mHandler=new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//
//                if(msg.what==0){
////                    FrameLayout frameLayout = (FrameLayout) mRootView.findViewById(R.id.container);
////                    promotedActionsLibrary = new PromotedActionsLibrary2();
////                    promotedActionsLibrary.setup(getActivity().getApplicationContext(), frameLayout);
//                    if(promotedActionsLibrary!=null){
//
//                        promotedActionsLibrary.clearAllItem();
//
//                        promotedActionsLibrary.addItem(getResources().getDrawable(R.mipmap.shenpi), R.mipmap.shenpi_back, approvalNum, new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                /** 跳转到待办事项列表页 */
//                                Intent intent=new Intent();
//                                intent.setClass(getContext(),ApprovalActivity2.class);
//                                getContext().startActivity(intent);
//                            }
//                        });
//
//                        promotedActionsLibrary.addItem(getResources().getDrawable(R.mipmap.xiaoxi), R.mipmap.xiaoxi_back, messageNum, new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                /** 跳转到系统信息列表页 */
//                                Intent intent = new Intent();
//                                intent.setClass(getContext(), MessageActivity.class);
//                                getContext().startActivity(intent);
//                            }
//                        });
//                        promotedActionsLibrary.addMainItem(getResources().getDrawable(R.mipmap.menu), R.mipmap.menu_back, approvalNum+messageNum );
//                    }
//                }
//            }
//        };

        dialog.show();

        //加载并为公告列表设置数据
        mNoticeLv= (ListView) mRootView.findViewById(R.id.msg_frag_lv);
        adapter=new NoticeAdapter2(getContext(),new ArrayList());
        mNoticeLv.setAdapter(adapter);

        /** 请求网络获取新闻公告列表pageIndex=1 */
        String urlStringNews = Tools.jointIpAddress()
                + NetConstants.NEWS_LIST_PORT
                + "?"
                + NetConstants.NEWS_LIST_PARAM_ONE
                + "="
                + 1
                + "&"
                + NetConstants.NEWS_LIST_PARAM_TWO
                + "="
                + AppConstants.NOTICE_NEWS_ALL
                + "&"
                + NetConstants.NEWS_LIST_PARAM_THREE
                + "="
                + AppConstants.NEWS_TYPE;

        MyJsonObjectRequest jsonObjectRequest2=new MyJsonObjectRequest(
                urlStringNews,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            if(jsonObject.getBoolean("success")){
                                ArrayList newsList=new ArrayList();
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
                                    newsList.add(entity);
                                }
                                adapter.addItems(newsList);

                                dialog.dismiss();
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
        jsonObjectRequest2.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 1, 1.0f));

        VolleyHelper.getInstance(getContext())
                .addRequestTask(jsonObjectRequest2, NetConstants.TAG_NEWS_LIST);


        //公告列表url
        String urlStringNotice = Tools.jointIpAddress()
                + NetConstants.NEWS_LIST_PORT
                + "?"
                + NetConstants.NEWS_LIST_PARAM_ONE
                + "="
                + 1
                + "&"
                + NetConstants.NEWS_LIST_PARAM_TWO
                + "="
                + AppConstants.NOTICE_NEWS_ALL
                + "&"
                + NetConstants.NEWS_LIST_PARAM_THREE
                + "="
                + AppConstants.NOTICE_TYPE;

        MyJsonObjectRequest jsonObjectRequest3=new MyJsonObjectRequest(
                urlStringNotice,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            if(jsonObject.getBoolean("success")){
                                ArrayList noticeList=new ArrayList();
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
                                    noticeList.add(entity);
                                }
                                adapter.addItems(noticeList);

                                dialog.dismiss();
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
        jsonObjectRequest3.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 1, 1.0f));

        VolleyHelper.getInstance(getContext())
                .addRequestTask(jsonObjectRequest3, NetConstants.TAG_NEWS_LIST);


        /** 加载天气 */
//        final String province= (String) SharedPrefUtils.get(getContext(), "province", "河南");
//        final String city= (String) SharedPrefUtils.get(getContext(),"city","郑州");
//        String weatherUrl= Tools.buildWeatherUrl(province,city);
//        Log.e("weatherUrl", weatherUrl);
//        String url="http://c.3g.163.com/nc/weather/%E6%B2%B3%E5%8D%97%7C%E9%83%91%E5%B7%9E.html";
//
//        MyJsonObjectRequest request=new MyJsonObjectRequest(url,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject jsonObject) {
//                        try {
//                            JSONArray weatherArray=jsonObject.getJSONArray(province+"|"+city);
//
//                            //设置整体背景和今日天气数据
//                            JSONObject todayWeather=weatherArray.getJSONObject(0);
//                            String todayClimate=todayWeather.getString("climate");
//                            if("晴".equals(todayClimate)){
//                                mClimateIv.setImageResource(R.mipmap.weather_wicon_sunny);
//                            }else if("多云".equals(todayClimate)){
//                                mClimateIv.setImageResource(R.mipmap.weather_wicon_cloudy);
//                            }else if("阴".equals(todayClimate)){
//                                mClimateIv.setImageResource(R.mipmap.weather_wicon_overcast);
//                            }else if("小雨".equals(todayClimate)
//                                    |"中雨".equals(todayClimate)
//                                    |"大雨".equals(todayClimate)
//                                    |"暴雨".equals(todayClimate)
//                                    |"阵雨".equals(todayClimate)){
//                                mClimateIv.setImageResource(R.mipmap.weather_wicon_rainy);
//                            }else if("雷阵雨".equals(todayClimate)){
//                                mClimateIv.setImageResource(R.mipmap.weather_wicon_thunderstorm);
//                            }else if("小雪".equals(todayClimate)
//                                    |"中雪".equals(todayClimate)
//                                    |"大雪".equals(todayClimate)
//                                    |"暴雪".equals(todayClimate)
//                                    |"雨夹雪".equals(todayClimate)){
//                                mClimateIv.setImageResource(R.mipmap.weather_wicon_snowy);
//                            }else if("沙尘暴".equals(todayClimate)){
//                                mClimateIv.setImageResource(R.mipmap.weather_wicon_sandstorm);
//                            }
//                            mTempuratureTv.setText(jsonObject.getInt("rt_temperature")+"°");
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Toast.makeText(getContext(),"天气服务器异常，请稍后再试",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        //设置网络请求超时时间
//        request.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 1, 1.0f));
//
//        VolleyHelper.getInstance(getContext())
//                .addRequestTask(request, weatherUrl);

    }

    /**
     * 请求网络加载数量信息
     */
    private void loadNum(){

        /** 请求网络获取消息数量信息 */
        String url= Tools.jointIpAddress()
                + NetConstants.MATTER_NUM_PORT;
        MyJsonObjectRequest jsonObjectRequest=new MyJsonObjectRequest(
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            if(jsonObject.getBoolean("success")){
                                approvalNum=jsonObject.getInt("flowNum");
                                messageNum=jsonObject.getInt("msgNum");
                                //数量加载完成后发送通知，延迟1s发送等待界面刷新
                                mHandler.sendEmptyMessageAtTime(0,1*1000);
                            }else{
                                Toast.makeText(getContext(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"流程数量请求失败，请检查网络",Toast.LENGTH_SHORT).show();
            }
        });
        //设置网络请求超时时间
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 1, 1.0f));
        VolleyHelper.getInstance(getContext())
                .addRequestTask(jsonObjectRequest, url);
    }
}
