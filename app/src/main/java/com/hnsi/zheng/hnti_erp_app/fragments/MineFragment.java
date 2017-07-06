package com.hnsi.zheng.hnti_erp_app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.activities.AboutUsActivity;
import com.hnsi.zheng.hnti_erp_app.activities.ChangePasswordActivity;
import com.hnsi.zheng.hnti_erp_app.activities.FeedBackActivity;
import com.hnsi.zheng.hnti_erp_app.activities.NewMsgActivity;
import com.hnsi.zheng.hnti_erp_app.activities.PersonalDataActivity;
import com.hnsi.zheng.hnti_erp_app.app.MyApplication;
import com.hnsi.zheng.hnti_erp_app.utils.CommonUtils;
import com.hnsi.zheng.hnti_erp_app.utils.DataCleanManager;
import com.hnsi.zheng.hnti_erp_app.utils.FilesCleanManager;
import com.hnsi.zheng.hnti_erp_app.utils.Tools;
import com.hnsi.zheng.hnti_erp_app.utils.UserIconDisplayOptionsFactory;
import com.hnsi.zheng.hnti_erp_app.widgets.CircleImageView;
import com.hnsi.zheng.hnti_erp_app.widgets.CleanCacheDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 碎片界面（我的）
 * Created by Zheng on 2016/4/29.
 */
public class MineFragment extends Fragment implements View.OnClickListener{
    //标题头返回按钮
    private ImageButton mBackBtn;
    //页面标题
    private TextView mTitleTv;
    //用户名显示
    private TextView mUserNameTv;
    //用户头像显示
    private CircleImageView mUserIconCiv;
    //新消息通知按钮
    private RelativeLayout mNewMsgBtn;
    //修改密码按钮
    private RelativeLayout mChangePasswordBtn;
    //清除缓存按钮
    private RelativeLayout mCleanCacheBtn;
    //缓存大小显示TextView
    private TextView mCacheSizeTv;
    //清除附件缓存按钮
    private RelativeLayout mCleanFilesBtn;
    //附件缓存大小显示TextView
    private TextView mFilesSizeTv;
    //意见反馈按钮
    private RelativeLayout mFeedBackBtn;
    //关于按钮
    private RelativeLayout mAboutUsBtn;
    //注销按钮
    private Button mLogoutBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mRootView=inflater.inflate(R.layout.fragment_mine,container,false);

        initUI(mRootView);

        return mRootView;
    }

    @Override
    public void onResume() {
        //用户头像显示
        /** 在此处设置显示的用户头像 */
        if(!"".equals(MyApplication.mUser.getHeadimg())&&!(MyApplication.mUser.getHeadimg()).endsWith("null")){
            ImageLoader.getInstance().displayImage(
                    MyApplication.mUser.getHeadimg(),mUserIconCiv, UserIconDisplayOptionsFactory.getListOptions());
        }
        super.onResume();
    }

    /**
     * 初始化界面
     * @param mRootView 根视图
     */
    private void initUI(View mRootView) {
        initTitleBar(mRootView);

        //用户名显示
        mUserNameTv= (TextView) mRootView.findViewById(R.id.mine_tv_username);
        /** 在此处设置显示的用户名 */
        mUserNameTv.setText(MyApplication.mUser.getEmpname());

        //用户头像显示
        mUserIconCiv= (CircleImageView) mRootView.findViewById(R.id.mine_civ_user_icon);
        /** 在此处设置显示的用户头像 */
        if(!"".equals(MyApplication.mUser.getHeadimg())&&!(MyApplication.mUser.getHeadimg()).endsWith("null")){
            ImageLoader.getInstance().displayImage(
                    MyApplication.mUser.getHeadimg(),mUserIconCiv, UserIconDisplayOptionsFactory.getListOptions());
        }
        mUserIconCiv.setOnClickListener(this);

        //新消息通知按钮
        mNewMsgBtn= (RelativeLayout) mRootView.findViewById(R.id.mine_rly_new_msg);
        mNewMsgBtn.setOnClickListener(this);

        //修改密码按钮
        mChangePasswordBtn= (RelativeLayout) mRootView.findViewById(R.id.mine_rly_change_password);
        mChangePasswordBtn.setOnClickListener(this);

        //清除缓存按钮
        mCleanCacheBtn= (RelativeLayout) mRootView.findViewById(R.id.mine_rly_clean_cache);
        mCleanCacheBtn.setOnClickListener(this);

        //缓存大小
        mCacheSizeTv= (TextView) mRootView.findViewById(R.id.mine_tv_clean_cache);
        mCacheSizeTv.setText(CommonUtils.getFormatSize(DataCleanManager.getAppCacheSize(getContext())));

        //清除附件缓存按钮
        mCleanFilesBtn= (RelativeLayout) mRootView.findViewById(R.id.mine_rly_clear_files);
        mCleanFilesBtn.setOnClickListener(this);

        //附件缓存大小
        mFilesSizeTv= (TextView) mRootView.findViewById(R.id.mine_tv_clear_files);
        mFilesSizeTv.setText(CommonUtils.getFormatSize(FilesCleanManager.getAppFilesSize()));

        //意见反馈按钮
        mFeedBackBtn= (RelativeLayout) mRootView.findViewById(R.id.mine_rly_feed_back);
        mFeedBackBtn.setOnClickListener(this);

        //关于按钮
        mAboutUsBtn= (RelativeLayout) mRootView.findViewById(R.id.mine_rly_about_us);
        mAboutUsBtn.setOnClickListener(this);

        //注销按钮
        mLogoutBtn= (Button) mRootView.findViewById(R.id.mine_btn_logout);
        mLogoutBtn.setOnClickListener(this);
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
        mTitleTv.setText("我");
    }

    /**
     * 设置本页面的点击事件
     * @param v 被点击的控件
     */
    @Override
    public void onClick(View v) {
        //以控件ID作为标识
        int vId=v.getId();

        switch (vId) {
            case R.id.mine_rly_new_msg: {//控件点击事件（新消息通知）
                Tools.skipActivity(getContext(),NewMsgActivity.class);
            }
            break;
            case R.id.mine_rly_change_password: {//控件点击事件（修改密码）
                Tools.skipActivity(getContext(), ChangePasswordActivity.class);
            }
            break;
            case R.id.mine_rly_clean_cache: {//控件点击事件（清除缓存）
                /** 在此处弹出清除缓存的弹窗 */
                Tools.showCleanCacheDialog(getContext(), mCacheSizeTv, 0);
            }
            break;
            case R.id.mine_rly_clear_files: {//控件点击事件（清除附件缓存）
                /** 在此处弹出清除附件的弹窗 */
                Tools.showCleanCacheDialog(getContext(), mFilesSizeTv, 1);
            }
            break;
            case R.id.mine_rly_feed_back: {//控件点击事件（反馈）
                Tools.skipActivity(getContext(), FeedBackActivity.class);
            }
            break;
            case R.id.mine_rly_about_us: {//控件点击事件（关于）
                Tools.skipActivity(getContext(), AboutUsActivity.class);
            }
            break;
            case R.id.mine_btn_logout: {//注销按钮
                /** 在此处进行注销操作 */
                Tools.showLogoutDialog(this);
            }
            break;
            case R.id.mine_civ_user_icon: {//点击头像
                /** 点击用户头像跳转到个人资料页面 */
                Tools.skipActivity(getContext(), PersonalDataActivity.class);

            }
            break;
        }
    }

}
