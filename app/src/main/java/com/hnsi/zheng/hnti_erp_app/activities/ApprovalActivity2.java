package com.hnsi.zheng.hnti_erp_app.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.fragments.FinishedMatterFragment2;
import com.hnsi.zheng.hnti_erp_app.fragments.UnFinishedMatterFragment2;

import java.util.ArrayList;

/**
 * 审批界面
 * Created by Zheng on 2016/5/3.
 */
public class ApprovalActivity2 extends MyBaseActivity implements View.OnClickListener{//,GestureDetector.OnGestureListener
    //标题头返回按钮
    private ImageButton mBackBtn;
    //页面标题
    private TextView mTitleTv;
    //RadioGroup
    private RadioGroup mRadioGroup;
    //待办事项radiobutton
    private RadioButton mUnFinishedRbtn;
    //已办事项radiobutton
    private RadioButton mFinishedRbtn;
    //ViewPager
    private ViewPager mContentVp;
    //fragment集合
    private ArrayList<Fragment>fragments=new ArrayList<>();

//    private GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval2);

//        detector=new GestureDetector(this,this);

        initUI();
    }

    /**
     * 初始化界面
     */
    private void initUI() {
        mBackBtn= (ImageButton) findViewById(R.id.titlebar_ibtn_back);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleTv= (TextView) findViewById(R.id.titlebar_tv_title);
        mTitleTv.setText("我的审批");

//        mRadioGroup= (RadioGroup) findViewById(R.id.approval_rp);
//        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if (checkedId == R.id.approval_btn_unfinished) {
//                    mContentVp.setCurrentItem(0);
//                } else if (checkedId == R.id.approval_btn_finished) {
//                    mContentVp.setCurrentItem(1);
//                }
//            }
//        });

        mUnFinishedRbtn = (RadioButton) findViewById(R.id.approval_btn_unfinished);
        mUnFinishedRbtn.setOnClickListener(this);
        mFinishedRbtn = (RadioButton) findViewById(R.id.approval_btn_finished);
        mFinishedRbtn.setOnClickListener(this);

        mContentVp = (ViewPager) findViewById(R.id.approval_content_viewpager);
        fragments.add(new UnFinishedMatterFragment2());
        fragments.add(new FinishedMatterFragment2());
        mContentVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }
            @Override
            public int getCount() {
                return fragments.size();
            }
        });

//        mContentVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                if (position == 0) {
//                    onClick(mUnFinishedRbtn);
//                } else if (position == 1) {
//                    onClick(mFinishedRbtn);
//                }
//            }
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });
        mContentVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    onClick(mUnFinishedRbtn);
                } else if (position == 1) {
                    onClick(mFinishedRbtn);
                }
            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        onClick(mUnFinishedRbtn);
    }

    @Override
    public void onClick(View v) {
        final int vid = v.getId();
        if (vid == R.id.approval_btn_unfinished) {
            mContentVp.setCurrentItem(0);
            mUnFinishedRbtn.setChecked(true);
        } else if (vid == R.id.approval_btn_finished) {
            mContentVp.setCurrentItem(1);
            mFinishedRbtn.setChecked(true);
        }
    }

    private boolean isApprovaled=false;
    public void setIsApprovaled(boolean value){
        isApprovaled=value;
    }
    public boolean getIsApproval(){
        return  isApprovaled;
    }

    /** 把Activity的触摸事件交给GestureDecector处理 */
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return detector.onTouchEvent(event);
//    }
//    @Override
//    public boolean onDown(MotionEvent e) {
//        return false;
//    }
//    @Override
//    public void onShowPress(MotionEvent e) {
//    }
//    @Override
//    public boolean onSingleTapUp(MotionEvent e) {
//        return false;
//    }
//    @Override
//    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//        return false;
//    }
//    @Override
//    public void onLongPress(MotionEvent e) {
//    }
//    @Override
//    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//        return false;
//    }

}