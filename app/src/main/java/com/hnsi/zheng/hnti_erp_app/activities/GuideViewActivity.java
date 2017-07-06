package com.hnsi.zheng.hnti_erp_app.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.app.AppConstants;
import com.hnsi.zheng.hnti_erp_app.app.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页
 * Created by Zheng on 2016/8/5.
 */
public class GuideViewActivity extends MyBaseActivity{
    private ViewPager mVp;
    private List<Fragment> mFgPages;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guideview);
        mIntent=getIntent();
        mVp=(ViewPager)findViewById(R.id.guideview_vp);
        mFgPages = new ArrayList<>();
        Fragment pag1= new GuidePageFragment(1);
        Fragment pag2= new GuidePageFragment(2);
        Fragment pag3= new GuidePageFragment(3);
        mFgPages.add(pag1);
        mFgPages.add(pag2);
        mFgPages.add(pag3);

        mVp.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), mFgPages));
        mVp.setCurrentItem(0);
    }



    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> list;
        public MyFragmentPagerAdapter(FragmentManager fm,List<Fragment> pages) {
            super(fm);
            this.list = pages;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Fragment getItem(int arg0) {
            return list.get(arg0);
        }
    }

    @SuppressLint("ValidFragment")
    class GuidePageFragment extends Fragment {
        private int index;

        public GuidePageFragment(int index){
            this.index=index;
        }

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container,
                                 Bundle savedInstanceState) {
            View v= LayoutInflater.from(getActivity()).inflate(R.layout.fragment_guideview, container,false);
            RelativeLayout mRl=(RelativeLayout)v.findViewById(R.id.rl_guidepage_back);
            TextView btn=(TextView)v.findViewById(R.id.btn_guidepage_start);
            if(index==1){
                mRl.setBackgroundResource(R.mipmap.page1);
            }else if(index==2){
                mRl.setBackgroundResource(R.mipmap.page2);
            }else{
                mRl.setBackgroundResource(R.mipmap.page3);
                btn.setVisibility(View.VISIBLE);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        mIntent.setClass(getActivity(), LoginActivity.class);
//                        intent.putExtra(AppConstants.UPDATE_INFO_KEY,mIntent.getStringExtra(AppConstants.UPDATE_INFO_KEY));
                        MyApplication.isNotFirst();
                        startActivity(mIntent);
                        getActivity().finish();
                    }
                });
            }
            return v;
        }
    }


}
