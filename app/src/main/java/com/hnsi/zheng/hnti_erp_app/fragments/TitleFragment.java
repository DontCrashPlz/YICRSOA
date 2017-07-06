package com.hnsi.zheng.hnti_erp_app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hnsi.zheng.hnti_erp_app.R;

/**
 * 定义标题Fragment——此处是一个空的Fragment,只用于被其他Fragmrnt替换
 * Created by Zheng on 2016/6/6.
 */
public class TitleFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRootView=inflater.inflate(R.layout.fragment_titlefragment_null,container,false);
        return mRootView;
    }
}
