package com.hnsi.zheng.hnti_erp_app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.adapters.ProvinceListAdapter;
import com.hnsi.zheng.hnti_erp_app.utils.CityListParser;
import com.hnsi.zheng.hnti_erp_app.utils.SharedPrefUtils;
import com.hnsi.zheng.hnti_erp_app.widgets.SearchView;

/**
 * 城市列表界面
 * Created by Zheng on 2016/8/31.
 */
@Deprecated
public class CityListActivity extends AppCompatActivity{

    private ImageButton mBackBtn;
    private TextView mTitleTv;
//    private SearchView mSearchView;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citylist);

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
        mTitleTv.setText("当前-"+(String) SharedPrefUtils.get(CityListActivity.this, "city", "未知"));

//        mSearchView= (SearchView) findViewById(R.id.citylist_sv);
//        mSearchView.setSearchViewListener(this);

        mListView= (ListView) findViewById(R.id.citylist_lv);
        mListView.setAdapter(new ProvinceListAdapter(this, CityListParser.parseCityList()));
    }

//    @Override
//    public void onRefreshAutoComplete(String text) {
//
//    }
//
//    @Override
//    public void onSearch(int position) {
//
//    }
}
