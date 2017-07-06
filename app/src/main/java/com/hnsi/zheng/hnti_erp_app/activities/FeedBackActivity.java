package com.hnsi.zheng.hnti_erp_app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.app.MyApplication;
import com.hnsi.zheng.hnti_erp_app.interfaces.IAsyncLoadListener;
import com.hnsi.zheng.hnti_erp_app.utils.SendMail;
import com.hnsi.zheng.hnti_erp_app.widgets.progressDialog.BaseProgressDialog;
import com.hnsi.zheng.hnti_erp_app.widgets.progressDialog.ProgressDialog;

/**
 * 意见反馈界面
 * Created by Zheng on 2016/5/3.
 */
public class FeedBackActivity extends MyBaseActivity implements View.OnClickListener{
    //标题头返回按钮
    ImageButton mBackBtn;
    //页面标题
    TextView mTitleTv;
    //标题头功能按钮
    TextView mFunctionTv;
    //建议输入框
    EditText mSuggestEt;
    //加载框
    BaseProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        initUI();
    }

    /**
     * 初始化界面
     */
    private void initUI() {
        //加载并为控件设置点击事件（返回按钮）
        mBackBtn= (ImageButton) findViewById(R.id.titlebar_ibtn_back);

        //加载并为界面设置标题
        mTitleTv= (TextView) findViewById(R.id.titlebar_tv_title);

        //加载并为控件设置点击事件（标题头功能按钮）
        mFunctionTv= (TextView) findViewById(R.id.titlebar_tv_function);

        //加载建议输入EditText
        mSuggestEt= (EditText) findViewById(R.id.feed_back_et_suggest);

        mBackBtn.setOnClickListener(this);
        mTitleTv.setText("意见反馈");
        mFunctionTv.setText("发送");
        mFunctionTv.setOnClickListener(this);

        dialog=new ProgressDialog(this);
        dialog.setLabel("发送中...");
    }



    /**
     * 设置本页面的点击事件
     * @param v 被点击的控件
     */
    @Override
    public void onClick(View v) {
        //以控件ID作为标识
        int vId=v.getId();

        switch (vId){

            case R.id.titlebar_ibtn_back:{//控件点击事件（返回按钮）
                finish();
            }
            break;

            case R.id.titlebar_tv_function:{//控件点击事件（发送按钮）
                String mSuggestStr=mSuggestEt.getText().toString();
                if(TextUtils.isEmpty(mSuggestStr)){
                    Toast.makeText(FeedBackActivity.this,"请填写您的意见",Toast.LENGTH_SHORT).show();
                }else{

                    dialog.show();

                    SendMail.sendmail(mSuggestStr, new IAsyncLoadListener<Void>() {

                        @Override
                        public void onSuccess(Void data) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "发送成功!感谢您的反馈!", Toast.LENGTH_SHORT).show();
                                    FeedBackActivity.this.finish();
                                }
                            });
                        }

                        @Override
                        public void onFailure(String error) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "发送失败!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    });
                }
            }
            break;

        }
    }
}
