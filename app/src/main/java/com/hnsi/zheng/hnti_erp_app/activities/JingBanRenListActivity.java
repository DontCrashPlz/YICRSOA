package com.hnsi.zheng.hnti_erp_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.ebusbeans.JingBanRenInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 经办人列表界面
 * Created by Zheng on 2016/10/27.
 */
public class JingBanRenListActivity extends MyBaseActivity{

    private int empid;
    private String jingBanRenName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jingbanrenlist);
        EventBus.getDefault().register(this);
    }

    public final int RETURN_TO_MATTERINFO=120;

    public void returnResult(){
        Intent intent=this.getIntent();
        intent.putExtra("empid",empid);
        intent.putExtra("empname",jingBanRenName);
        Log.e("empid", "" + empid);
        setResult(RETURN_TO_MATTERINFO,intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void disposeInfo(JingBanRenInfo info){
        setEmpid(info.getEmpId());
        setJingBanRenName(info.getEmpName());
        returnResult();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void setEmpid(int value){
        empid=value;
    }

    public void setJingBanRenName(String value){
        jingBanRenName=value;
    }

}
