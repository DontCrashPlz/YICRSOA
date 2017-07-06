package com.hnsi.zheng.hnti_erp_app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hnsi.zheng.hnti_erp_app.app.MyApplication;

/**
 * Created by Zheng on 2017/5/25.
 */

public class MyBaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getSingleton().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getSingleton().finishActivity(this);
    }
}
