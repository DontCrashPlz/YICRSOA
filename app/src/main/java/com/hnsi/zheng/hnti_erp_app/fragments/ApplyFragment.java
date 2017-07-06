package com.hnsi.zheng.hnti_erp_app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.activities.ApprovalActivity2;
import com.hnsi.zheng.hnti_erp_app.activities.NoteActivity;
import com.hnsi.zheng.hnti_erp_app.activities.NewsActivity;
import com.hnsi.zheng.hnti_erp_app.activities.VoteActivity;
import com.hnsi.zheng.hnti_erp_app.utils.Tools;

/**
 * 碎片界面（应用）
 * Created by Zheng on 2016/4/29.
 */
@Deprecated
public class ApplyFragment extends Fragment implements View.OnClickListener{
    //标题头返回按钮
    ImageButton mBackBtn;
    //页面标题
    TextView mTitleTv;
    //公告按钮
    ImageView mNoticeIv;
    //审批按钮
    ImageView mApprovalIv;
    //投票按钮
    ImageView mVoteIv;
    //备忘录按钮
    ImageView mNoteIv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mRootView=inflater.inflate(R.layout.fragment_apply,container,false);

        initUI(mRootView);

        return mRootView;
    }

    /**
     * 初始化界面
     * @param rootView 根视图
     */
    private void initUI(View rootView) {
        initTitleBar(rootView);

        //加载按钮并设置点击事件（公告）
        mNoticeIv= (ImageView) rootView.findViewById(R.id.apply_iv_notice);
        mNoticeIv.setOnClickListener(this);

        //加载按钮并设置点击事件（审批）
        mApprovalIv= (ImageView) rootView.findViewById(R.id.apply_iv_approval);
        mApprovalIv.setOnClickListener(this);

        //加载按钮并设置点击事件（投票）
        mVoteIv= (ImageView) rootView.findViewById(R.id.apply_iv_vote);
        mVoteIv.setOnClickListener(this);

        //加载按钮并设置点击事件（备忘录）
        mNoteIv= (ImageView) rootView.findViewById(R.id.apply_iv_note);
        mNoteIv.setOnClickListener(this);
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
     * 设置本页面的点击事件
     * @param v 被点击的控件
     */
    @Override
    public void onClick(View v) {
        //以控件ID作为标识
        int vId=v.getId();

        switch (vId){
            case R.id.apply_iv_notice:{//控件点击事件（公告）
                Tools.skipActivity(getContext(),NewsActivity.class);
            }break;
            case R.id.apply_iv_approval:{//控件点击事件（审批）
                Tools.skipActivity(getContext(), ApprovalActivity2.class);
            }break;
            case R.id.apply_iv_vote:{//控件点击事件（投票）
                Tools.skipActivity(getContext(), VoteActivity.class);
            }break;
            case R.id.apply_iv_note:{//控件点击事件（备忘录）
                Tools.skipActivity(getContext(), NoteActivity.class);
            }break;
        }
    }

}
