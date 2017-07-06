package com.hnsi.zheng.hnti_erp_app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.app.MyApplication;

/**
 * 新信息提醒界面
 * Created by Zheng on 2016/5/4.
 */
@Deprecated
public class NewMsgActivity extends MyBaseActivity{
    //标题头返回按钮
    ImageButton mBackBtn;
    //页面标题
    TextView mTitleTv;
    //状态开关（新消息通知）
    ToggleButton mNotificationTbtn;
    //状态开关（声音提醒）
    ToggleButton mSoundTbtn;
    //状态开关（震动提醒）
    ToggleButton mShockTbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_msg);

        initUI();
    }

    /**
     * 初始化界面
     */
    private void initUI() {
        initTitleBar();

        //加载并为控件设置点击事件（新消息提醒）
        mNotificationTbtn= (ToggleButton) findViewById(R.id.new_msg_tbtn_notification);
        mNotificationTbtn.setOnCheckedChangeListener(Listener);

        //加载并为控件设置点击事件（声音提醒）
        mSoundTbtn= (ToggleButton) findViewById(R.id.new_msg_tbtn_sound);
        mSoundTbtn.setOnCheckedChangeListener(Listener);

        //加载并为控件设置点击事件（震动提醒）
        mShockTbtn= (ToggleButton) findViewById(R.id.new_msg_tbtn_shock);
        mShockTbtn.setOnCheckedChangeListener(Listener);
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

        //加载并设置页面标题
        mTitleTv= (TextView) findViewById(R.id.titlebar_tv_title);
        mTitleTv.setText("新消息提醒");
    }

    CompoundButton.OnCheckedChangeListener Listener=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            //以ButtonId作为唯一标识
            int btnId=buttonView.getId();

            switch (btnId){
                case R.id.new_msg_tbtn_notification:{//状态开关（新消息提醒）
                    if(isChecked){
                        /**  状态开关（新消息提醒）被选中的事件处理  */
                        Toast.makeText(NewMsgActivity.this,"1-1",Toast.LENGTH_LONG).show();
                    }else{
                        /**  状态开关（新消息提醒）被取消选中的事件处理  */
                        Toast.makeText(NewMsgActivity.this,"1-0",Toast.LENGTH_LONG).show();
                    }
                }break;
                case R.id.new_msg_tbtn_sound:{//状态开关（声音提醒）
                    if(isChecked){
                        /**  状态开关（声音提醒）被选中的事件处理  */
                        Toast.makeText(NewMsgActivity.this,"2-1",Toast.LENGTH_LONG).show();
                    }else{
                        /**  状态开关（声音提醒）被取消选中的事件处理  */
                        Toast.makeText(NewMsgActivity.this,"2-0",Toast.LENGTH_LONG).show();
                    }
                }break;
                case R.id.new_msg_tbtn_shock:{//状态开关（震动提醒）
                    if(isChecked){
                        /**  状态开关（震动提醒）被选中的事件处理  */
                        Toast.makeText(NewMsgActivity.this,"3-1",Toast.LENGTH_LONG).show();
                    }else{
                        /**  状态开关（震动提醒）被取消选中的事件处理  */
                        Toast.makeText(NewMsgActivity.this,"3-0",Toast.LENGTH_LONG).show();
                    }
                }break;
            }

        }
    };
}
