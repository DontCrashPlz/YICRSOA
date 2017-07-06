package com.hnsi.zheng.hnti_erp_app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.app.MyApplication;
import com.hnsi.zheng.hnti_erp_app.utils.SharedPrefUtils;

/**
 * 服务器设置页面
 * Created by Zheng on 2016/4/27.
 */
public class ServerSettingActivity extends MyBaseActivity{
    //标题头返回按钮
    ImageButton mBackBtn;
    //页面标题
    TextView mTitleTv;
    //主机IP地址输入框
    EditText mHostIpEt;
    //端口号输入框
    EditText mPortEt;
    //完成按钮
    Button mCompleteBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_setting);

        //加载并为返回按钮设置点击事件
        mBackBtn= (ImageButton) findViewById(R.id.titlebar_ibtn_back);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //加载并为页面设置标题
        mTitleTv= (TextView) findViewById(R.id.titlebar_tv_title);
        mTitleTv.setText("服务器设置");

        //加载服务器地址输入框
        mHostIpEt= (EditText) findViewById(R.id.server_et_ip_address);
        mHostIpEt.setText((String)SharedPrefUtils.get(ServerSettingActivity.this,"ipAddress","192.168.1.68"));

        //加载服务器端口输入框
        mPortEt= (EditText) findViewById(R.id.server_et_ip_port);
        mPortEt.setText((String)SharedPrefUtils.get(ServerSettingActivity.this,"portNum","80"));

        //加载并为完成按钮设置点击事件
        mCompleteBtn= (Button) findViewById(R.id.server_btn_complete);
        mCompleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hostIp=mHostIpEt.getText().toString().trim();
                String portNum=mPortEt.getText().toString().trim();

                if("".equals(hostIp)){
                    Toast.makeText(ServerSettingActivity.this,"请输入服务器地址",Toast.LENGTH_SHORT).show();
                }else if("".equals(portNum)){
                    Toast.makeText(ServerSettingActivity.this,"请输入端口号",Toast.LENGTH_SHORT).show();
                }else {
                    /** 此处应先使用正则表达式对服务器地址和端口号的格式进行检测，格式错误弹出提示 */

                    SharedPrefUtils.put(getApplicationContext(),"ipAddress",hostIp);
                    SharedPrefUtils.put(getApplicationContext(),"portNum",portNum);
                    Toast.makeText(ServerSettingActivity.this, "服务器ip设为"+hostIp+":"+portNum, Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        });
    }
}
