package com.hnsi.zheng.hnti_erp_app.newcontacts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.activities.MyBaseActivity;
import com.hnsi.zheng.hnti_erp_app.beans.DepartmentDetailEntity;
import com.hnsi.zheng.hnti_erp_app.database.ConstactsInfoTableHelper;
import com.hnsi.zheng.hnti_erp_app.utils.UserIconDisplayOptionsFactory;
import com.hnsi.zheng.hnti_erp_app.widgets.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 个人信息界面
 * Created by Zheng on 2016/5/11.
 */
public class PersonInfoActivity2 extends MyBaseActivity implements View.OnClickListener{
    //标题头返回按钮
    private ImageButton mBackBtn;
    //页面标题
    private TextView mTitleTv;
    //用户头像
    private CircleImageView mUserIconCiv;
    //用户名
    private TextView mUserNameTv;
    //用户性别
    private TextView mUserSexTv;
    //用户电话
    private TextView mPhoneTv;
    //用户部门
    private TextView mDepartmentTv;
    //用户职务
    private TextView mPositionTv;
    //用户固定电话
    private TextView mLinePhoneTv;
    //用户电子邮箱
    private TextView mEmailTv;
    //Intent
    private Intent intent;
    //发送信息按钮
    private Button mSendMsgBtn;
    private int mEmpid;
    private DepartmentDetailEntity entity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personinfo);

        intent=getIntent();
        mEmpid=intent.getIntExtra("Empid",0);

        if(mEmpid==0){
            Toast.makeText(this,"联系人数据无效，请刷新通讯录",Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        ConstactsInfoTableHelper helper=new ConstactsInfoTableHelper(this);
        entity=helper.searchByEmpid(mEmpid);

        if(entity==null){
            finish();
            return;
        }

        initUI();

    }

    /**
     * 初始化界面
     */
    private void initUI() {
        initTitleBar();

        mUserIconCiv= (CircleImageView) findViewById(R.id.personinfo_civ_user_icon);
        //mUserIconCiv.setImageBitmap();
        String headimgUrl=entity.getHeadimgUrl();
        if(!"".equals(headimgUrl)){
            ImageLoader.getInstance().displayImage(headimgUrl,mUserIconCiv, UserIconDisplayOptionsFactory.getListOptions());
        }

        mUserNameTv= (TextView) findViewById(R.id.personinfo_tv_username);
        if(!entity.getEmpname().equals("null")){
            mUserNameTv.setText(entity.getEmpname());
        }else {
            mUserNameTv.setText("未知");
        }

        mUserSexTv= (TextView) findViewById(R.id.personinfo_tv_sex);
        if(!entity.getSex().equals("null")){
            mUserSexTv.setText(entity.getSex());
        }else {
            mUserSexTv.setText("未知");
        }

        mPhoneTv= (TextView) findViewById(R.id.personinfo_tv_phone);
        if(!entity.getMobileno().equals("null")){
            mPhoneTv.setText(entity.getMobileno());
            mPhoneTv.setOnClickListener(this);
        }else {
            mPhoneTv.setText("未知");
        }

        mDepartmentTv= (TextView) findViewById(R.id.personinfo_tv_department);
        if(!entity.getOrgname().equals("null")){
            mDepartmentTv.setText(entity.getOrgname());
        }else {
            mDepartmentTv.setText("未知");
        }

        mPositionTv= (TextView) findViewById(R.id.personinfo_tv_position);
        if(!entity.getPosiname().equals("null")){
            mPositionTv.setText(entity.getPosiname());
        }else {
            mPositionTv.setText("未知");
        }

        mLinePhoneTv= (TextView) findViewById(R.id.personinfo_tv_linephone);
        if(!entity.getOtel().equals("null")){
            mLinePhoneTv.setText(entity.getOtel());
            mLinePhoneTv.setOnClickListener(this);
        }else {
            mLinePhoneTv.setText("未知");
        }

        mEmailTv= (TextView) findViewById(R.id.personinfo_tv_email);
        if(!entity.getOemail().equals("null")){
            mEmailTv.setText(entity.getOemail());
            mEmailTv.setOnClickListener(this);
        }else {
            mEmailTv.setText("未知");
        }

        mSendMsgBtn= (Button) findViewById(R.id.personinfo_btn_sendmsg);
        mSendMsgBtn.setOnClickListener(this);
    }

    /**
     * 初始化标题头
     */
    private void initTitleBar() {
        mBackBtn= (ImageButton) findViewById(R.id.titlebar_ibtn_back);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTitleTv= (TextView) findViewById(R.id.titlebar_tv_title);
        mTitleTv.setText("个人资料");
    }

    @Override
    public void onClick(View v) {
        int vId=v.getId();
        switch (vId){
            case R.id.personinfo_tv_phone:{//手机号码的点击事件
                intent =new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + ((TextView)v).getText()));
                startActivity(intent);
            }break;
            case R.id.personinfo_tv_linephone:{//固定电话的点击事件
                intent =new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+((TextView)v).getText()));
                startActivity(intent);
            }break;
            case R.id.personinfo_tv_email:{//邮箱地址的点击事件
                Uri uri = Uri.parse("mailto:"+((TextView)v).getText());
                intent = new Intent(Intent.ACTION_SENDTO, uri);
                startActivity(Intent.createChooser(intent, "请选择邮件类应用"));
            }break;
            case R.id.personinfo_btn_sendmsg:{//发送信息的点击事件
                if(!mPhoneTv.getText().equals("未知")){
                    intent =new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("smsto:" + mPhoneTv.getText()));
                    startActivity(intent);
                }else {
                    Toast.makeText(PersonInfoActivity2.this,"此人没有手机号码信息",Toast.LENGTH_SHORT).show();
                }
            }break;
        }
    }
}
