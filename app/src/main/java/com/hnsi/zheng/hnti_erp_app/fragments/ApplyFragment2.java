package com.hnsi.zheng.hnti_erp_app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.activities.ApprovalActivity2;
import com.hnsi.zheng.hnti_erp_app.activities.MessageActivity;
import com.hnsi.zheng.hnti_erp_app.activities.NewsActivity;
import com.hnsi.zheng.hnti_erp_app.activities.NoticeActivity;
import com.hnsi.zheng.hnti_erp_app.app.NetConstants;
import com.hnsi.zheng.hnti_erp_app.http.MyJsonObjectRequest;
import com.hnsi.zheng.hnti_erp_app.http.VolleyHelper;
import com.hnsi.zheng.hnti_erp_app.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 新版应用界面
 * Created by Zheng on 2016/7/22.
 */
public class ApplyFragment2 extends Fragment implements View.OnClickListener{
    //标题头返回按钮
    private ImageButton mBackBtn;
    //页面标题
    private TextView mTitleTv;

    //新闻界面按钮
    private RelativeLayout mNewBtn;
    //新闻界面数量
    private TextView mNewNum;

    //公告界面按钮
    private RelativeLayout mNoticeBtn;
    //公告界面数量
    private TextView mNoticeNum;

    //消息界面按钮
    private RelativeLayout mMsgBtn;
    //消息界面数量
    private TextView mMsgNum;

    //传阅界面按钮
    private RelativeLayout mPassBtn;
    //传阅界面数量
    private TextView mPassNum;

    //审批界面按钮
    private RelativeLayout mApprovalBtn;
    //审批界面数量
    private TextView mApprovalNum;

    //办公界面按钮
    private RelativeLayout mOfficeBtn;
    //办公界面数量
    private TextView mOfficeNum;

    //售前界面按钮
    private RelativeLayout mPresalesBtn;
    //售前界面数量
    private TextView mPresalesNum;

    //生产界面按钮
    private RelativeLayout mProductionBtn;
    //生产界面数量
    private TextView mProductionNum;

    //合同界面按钮
    private RelativeLayout mContractBtn;
    //合同界面数量
    private TextView mContractNum;

    //事务界面按钮
    private RelativeLayout mBusinessBtn;
    //事务界面数量
    private TextView mBusinessNum;

    //备忘录界面按钮
    private RelativeLayout mNoteBtn;
    //备忘录界面数量
    private TextView mNoteNum;

    //投票界面按钮
    private RelativeLayout mVoteBtn;
    //投票界面数量
    private TextView mVoteNum;

    //决策界面按钮
    private RelativeLayout mDecisionBtn;
    //决策界面数量
    private TextView mDecisionNum;

    //天气界面按钮
    private RelativeLayout mWeatherBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRootView=inflater.inflate(R.layout.fragment_apply2,container,false);

        initUI(mRootView);

//        loadFlowNum();

        return mRootView;
    }

    /**
     * 在onResume方法里加载流程数量
     * 解决流程处理后返回应用界面流程数量显示错误的问题
     */
    @Override
    public void onResume() {
        super.onResume();
        loadFlowNum();
    }

    /**
     * 加载流程数量
     */
    private void loadFlowNum() {

        Log.e("loadFlowNum","loadFlowNum");

        String url=Tools.jointIpAddress()
                + NetConstants.FLOW_NUM_PORT;

        MyJsonObjectRequest jsonObjectRequest=new MyJsonObjectRequest(
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            if(jsonObject.getBoolean("success")){

                                Log.e("flowNum",jsonObject.toString());

                                mApprovalNum.setVisibility(View.VISIBLE);
                                mApprovalNum.setText(""+jsonObject.getInt("c0"));

//                                mOfficeNum.setVisibility(View.VISIBLE);
//                                mOfficeNum.setText(""+jsonObject.getInt("c1"));
//
//                                mPresalesNum.setVisibility(View.VISIBLE);
//                                mPresalesNum.setText(""+jsonObject.getInt("c2"));
//
//                                mProductionNum.setVisibility(View.VISIBLE);
//                                mProductionNum.setText(""+jsonObject.getInt("c3"));
//
//                                mContractNum.setVisibility(View.VISIBLE);
//                                mContractNum.setText(""+jsonObject.getInt("c4"));
//
//                                mBusinessNum.setVisibility(View.VISIBLE);
//                                mBusinessNum.setText(""+jsonObject.getInt("c5"));
                            }else{
                                Toast.makeText(getContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"无法加载流程数量，请检查网络",Toast.LENGTH_SHORT).show();
            }
        });

        //设置网络请求超时时间
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 1, 1.0f));

        VolleyHelper.getInstance(getContext())
                .addRequestTask(jsonObjectRequest, NetConstants.TAG_NEWS_LIST);
    }

    /**
     * 初始化界面
     * @param mRootView
     */
    private void initUI(View mRootView) {

        initTitleBar(mRootView);

        //加载并为新闻按钮设置点击事件
        mNewBtn= (RelativeLayout) mRootView.findViewById(R.id.apply_btn_new);
        mNewBtn.setOnClickListener(this);
        mNewNum= (TextView) mRootView.findViewById(R.id.apply_tv_num_news);

        //加载并为公告按钮设置点击事件
        mNoticeBtn= (RelativeLayout) mRootView.findViewById(R.id.apply_btn_notice);
        mNoticeBtn.setOnClickListener(this);
        mNoticeNum= (TextView) mRootView.findViewById(R.id.apply_tv_num_notice);

        //加载并为消息按钮设置点击事件
        mMsgBtn= (RelativeLayout) mRootView.findViewById(R.id.apply_btn_msg);
        mMsgBtn.setOnClickListener(this);
        mMsgNum= (TextView) mRootView.findViewById(R.id.apply_tv_num_msg);

        //加载并为传阅按钮设置点击事件
        mPassBtn= (RelativeLayout) mRootView.findViewById(R.id.apply_btn_pass);
        mPassBtn.setOnClickListener(this);
        mPassNum= (TextView) mRootView.findViewById(R.id.apply_tv_num_pass);

        //加载并为审批按钮设置点击事件
        mApprovalBtn= (RelativeLayout) mRootView.findViewById(R.id.apply_btn_approval);
        mApprovalBtn.setOnClickListener(this);
        mApprovalNum= (TextView) mRootView.findViewById(R.id.apply_tv_num_approval);

        //加载并为办公按钮设置点击事件
        mOfficeBtn= (RelativeLayout) mRootView.findViewById(R.id.apply_btn_office);
        mOfficeBtn.setOnClickListener(this);
        mOfficeNum= (TextView) mRootView.findViewById(R.id.apply_tv_num_office);

        //加载并为售前按钮设置点击事件
        mPresalesBtn= (RelativeLayout) mRootView.findViewById(R.id.apply_btn_presales);
        mPresalesBtn.setOnClickListener(this);
        mPresalesNum= (TextView) mRootView.findViewById(R.id.apply_tv_num_presales);

        //加载并为生产按钮设置点击事件
        mProductionBtn= (RelativeLayout) mRootView.findViewById(R.id.apply_btn_production);
        mProductionBtn.setOnClickListener(this);
        mProductionNum= (TextView) mRootView.findViewById(R.id.apply_tv_num_production);

        //加载并为合同按钮设置点击事件
        mContractBtn= (RelativeLayout) mRootView.findViewById(R.id.apply_btn_contract);
        mContractBtn.setOnClickListener(this);
        mContractNum= (TextView) mRootView.findViewById(R.id.apply_tv_num_contract);

        //加载并为事务按钮设置点击事件
        mBusinessBtn= (RelativeLayout) mRootView.findViewById(R.id.apply_btn_business);
        mBusinessBtn.setOnClickListener(this);
        mBusinessNum= (TextView) mRootView.findViewById(R.id.apply_tv_num_business);

        //加载并为备忘录按钮设置点击事件
        mNoteBtn= (RelativeLayout) mRootView.findViewById(R.id.apply_btn_note);
        mNoteBtn.setOnClickListener(this);
        mNoteNum= (TextView) mRootView.findViewById(R.id.apply_tv_num_note);

        //加载并为投票按钮设置点击事件
        mVoteBtn= (RelativeLayout) mRootView.findViewById(R.id.apply_btn_vote);
        mVoteBtn.setOnClickListener(this);
        mVoteNum= (TextView) mRootView.findViewById(R.id.apply_tv_num_vote);

        //加载并为决策按钮设置点击事件
        mDecisionBtn= (RelativeLayout) mRootView.findViewById(R.id.apply_btn_decision);
        mDecisionBtn.setOnClickListener(this);
        mDecisionNum= (TextView) mRootView.findViewById(R.id.apply_tv_num_decision);

        //加载并为天气按钮设置点击事件
        mWeatherBtn= (RelativeLayout) mRootView.findViewById(R.id.apply_btn_weather);
        mWeatherBtn.setOnClickListener(this);

    }

    /**
     * 初始化标题头
     * @param rootView 根视图
     */
    private void initTitleBar(View rootView) {
        //将标题头返回按钮隐藏
        mBackBtn= (ImageButton) rootView.findViewById(R.id.titlebar_ibtn_back);
        mBackBtn.setVisibility(View.INVISIBLE);

        //加载并设置页面标题
        mTitleTv= (TextView) rootView.findViewById(R.id.titlebar_tv_title);
        mTitleTv.setText("应用");
    }

    /**
     * 定义按钮的点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        int vId=v.getId();
        switch (vId){
            case R.id.apply_btn_new:{//新闻按钮点击事件
                Tools.skipActivity(getContext(), NewsActivity.class);
            }break;
            case R.id.apply_btn_notice:{//公告按钮点击事件
                Tools.skipActivity(getContext(), NoticeActivity.class);
            }break;
            case R.id.apply_btn_msg:{//消息按钮点击事件
                Tools.skipActivity(getContext(), MessageActivity.class);
            }break;
            case R.id.apply_btn_pass:{//传阅按钮点击事件
                Tools.showDevelopingDialog(getContext());
            }break;
            case R.id.apply_btn_approval:{//审批按钮点击事件
                Tools.skipActivity(getContext(), ApprovalActivity2.class);
            }break;
            case R.id.apply_btn_office:{//办公按钮点击事件
                Tools.showDevelopingDialog(getContext());
            }break;
            case R.id.apply_btn_presales:{//售前按钮点击事件
                Tools.showDevelopingDialog(getContext());
            }break;
            case R.id.apply_btn_production:{//生产按钮点击事件
                Tools.showDevelopingDialog(getContext());
            }break;
            case R.id.apply_btn_contract:{//合同按钮点击事件
                Tools.showDevelopingDialog(getContext());
            }break;
            case R.id.apply_btn_business:{//事务按钮点击事件
                Tools.showDevelopingDialog(getContext());
            }break;
            case R.id.apply_btn_note:{//备忘录按钮点击事件
                Tools.showDevelopingDialog(getContext());
            }break;
            case R.id.apply_btn_vote:{//投票按钮点击事件
                Tools.showDevelopingDialog(getContext());
            }break;
            case R.id.apply_btn_decision:{//决策按钮点击事件
                Tools.showDevelopingDialog(getContext());
            }break;
            case R.id.apply_btn_weather:{//天气按钮点击事件
                Tools.skipActivity(getContext(),WeatherActivity.class);
            }break;
        }
    }


}
