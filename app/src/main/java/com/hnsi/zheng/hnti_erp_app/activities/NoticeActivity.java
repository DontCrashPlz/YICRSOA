package com.hnsi.zheng.hnti_erp_app.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.fragments.AllNoticeFragment;
import com.hnsi.zheng.hnti_erp_app.fragments.CompanyNoticeFragment;
import com.hnsi.zheng.hnti_erp_app.fragments.DepartmentNoticeFragment;
import com.hnsi.zheng.hnti_erp_app.fragments.RulesNoticeFragment;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

/**
 * 公告界面
 * Created by Zheng on 2016/5/3.
 */
public class NoticeActivity extends MyBaseActivity {
    //标题头返回按钮
    ImageButton mBackBtn;
    //页面标题
    TextView mTitleTv;

    private static final String[] TITLE = new String[] {"全部公告","公司通知","部门通知","规章制度"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice2);

        initUI();
    }

    /**
     * 初始化界面
     */
    private void initUI() {
        initTitleBar();

        FragmentPagerAdapter adapter = new TabPageIndicatorAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.notice_tab_Indicator);
        indicator.setViewPager(pager);

//        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            @Override
//            public void onPageSelected(int arg0) {
////                Toast.makeText(getApplicationContext(), TITLE[arg0], Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onPageScrolled(int arg0, float arg1, int arg2) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int arg0) {
//
//            }
//        });

    }

    /**
     * 初始化标题头
     */
    private void initTitleBar() {
        //加载并为返回按钮设置点击事件
        mBackBtn= (ImageButton) findViewById(R.id.titlebar_ibtn_back);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //加载并为界面设置标题
        mTitleTv= (TextView) findViewById(R.id.titlebar_tv_title);
        mTitleTv.setText("公告");
    }

    class TabPageIndicatorAdapter extends FragmentPagerAdapter {
        public TabPageIndicatorAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ArrayList<Fragment>fragments=new ArrayList<>();
            fragments.add(new AllNoticeFragment());
            fragments.add(new CompanyNoticeFragment());
            fragments.add(new DepartmentNoticeFragment());
            fragments.add(new RulesNoticeFragment());
//            Bundle args = new Bundle();
//            args.putString("arg", TITLE[position]);
//            fragment.setArguments(args);

            return fragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLE[position % TITLE.length];
        }

        @Override
        public int getCount() {
            return TITLE.length;
        }
    }

}
