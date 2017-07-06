package com.hnsi.zheng.hnti_erp_app.activities;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.app.AppConstants;
import com.hnsi.zheng.hnti_erp_app.app.MyApplication;
import com.hnsi.zheng.hnti_erp_app.beans.UpdateInfoEntity;
import com.hnsi.zheng.hnti_erp_app.beans.UserEntity;
import com.hnsi.zheng.hnti_erp_app.interfaces.IAsyncLoadListener;
import com.hnsi.zheng.hnti_erp_app.utils.DownloadManager;
import com.hnsi.zheng.hnti_erp_app.utils.SharedPrefUtils;
import com.hnsi.zheng.hnti_erp_app.widgets.progressDialog.BaseProgressDialog;
import com.hnsi.zheng.hnti_erp_app.widgets.progressDialog.ProgressDialog;

import java.io.File;

/**
 * 登录界面
 * Created by Zheng on 2016/4/27.
 */
public class LoginActivity extends MyAppCompatActivity{
    //设置服务器信息界面按钮
    private ImageButton mSettingIBtn;
    //登录按钮
    private Button mLoginBtn;
    //用户名输入框
    private EditText mUserNameEt;
    private ImageView mDeleteUsername;
    //密码输入框
    private EditText mPasswordEt;
    private ImageView mDeletePassword;
    //忘记密码按钮
    private TextView mForgetPasswordTv;
    //记住密码复选框
    private CheckBox mRemeberCb;
    //自动登录复选框
    private CheckBox mAutomaticCb;
    //进度框
//    ProgressDialog mDialog;
    private BaseProgressDialog dialog;

    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        mIntent=getIntent();

        initUI();

        String updateInfo=mIntent.getStringExtra(AppConstants.UPDATE_INFO_KEY);
        if(AppConstants.UPDATE_NEED.equals(updateInfo)){
            UpdateInfoEntity info= (UpdateInfoEntity) mIntent.getSerializableExtra(AppConstants.UPDATE_INFO_ENTITY_KEY);
            showUpdateDialog(info);
        }else if(AppConstants.UPDATE_EXCEPTION.equals(updateInfo)){
            Toast.makeText(LoginActivity.this,"更新服务出现异常",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 初始化界面
    */
    private void initUI() {

        dialog=new ProgressDialog(this);
        dialog.setLabel("登录中...");

        //加载用户名输入框
        mUserNameEt= (EditText) findViewById(R.id.login_et_username);

        mDeleteUsername= (ImageView) findViewById(R.id.activity_login_iv_delete_username);

        mUserNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if (mUserNameEt.getText().toString() != null
                        && !mUserNameEt.getText().toString().equals("")
                        && mUserNameEt.hasFocus()) {
                    mDeleteUsername.setVisibility(View.VISIBLE);
                } else {
                    mDeleteUsername.setVisibility(View.INVISIBLE);
                }
            }
        });

        mUserNameEt.setText((String) SharedPrefUtils.get(LoginActivity.this,"username",""));

        mDeleteUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUserNameEt.setText("");
            }
        });

        //加载密码输入框
        mPasswordEt= (EditText) findViewById(R.id.login_et_password);
        mDeletePassword= (ImageView) findViewById(R.id.activity_login_iv_delete_password);

        mPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if (mPasswordEt.getText().toString() != null
                        && !mPasswordEt.getText().toString().equals("")) {
                    mDeletePassword.setVisibility(View.VISIBLE);
                } else {
                    mDeletePassword.setVisibility(View.INVISIBLE);
                }
            }
        });

        mDeletePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPasswordEt.setText("");
            }
        });

        //加载记住密码复选框
        mRemeberCb= (CheckBox) findViewById(R.id.login_cb_remeber_password);
        mRemeberCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPrefUtils.put(LoginActivity.this, "RemeberPassword", true);
                } else {
                    SharedPrefUtils.put(LoginActivity.this, "RemeberPassword", false);
                }
            }
        });
        //加载自动登录复选框
        mAutomaticCb= (CheckBox) findViewById(R.id.login_cb_automatic);
        mAutomaticCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    SharedPrefUtils.put(LoginActivity.this, "AutomaticLogin", true);
                    mAutomaticCb.setChecked(true);
                }else{
                    SharedPrefUtils.put(LoginActivity.this, "AutomaticLogin", false);
                }
            }
        });

        //如果之前选择记住密码
        if((boolean)SharedPrefUtils.get(LoginActivity.this,"RemeberPassword",false)){
            mPasswordEt.setText((String)SharedPrefUtils.get(LoginActivity.this,"password",""));
            mRemeberCb.setChecked(true);
        }

        //如果之前选择自动登录
        if((boolean)SharedPrefUtils.get(LoginActivity.this,"AutomaticLogin",false)){
            mAutomaticCb.setChecked(true);
        }

        //加载并为忘记密码按钮设置点击事件
        mForgetPasswordTv= (TextView) findViewById(R.id.login_tv_forget_password);
        mForgetPasswordTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** 跳转到忘记密码界面 */
            }
        });

        //加载并为设置按钮设置点击事件
        mSettingIBtn= (ImageButton) findViewById(R.id.login_ibtn_setting);
        mSettingIBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, ServerSettingActivity.class);
                startActivity(intent);
            }
        });

        //加载并为登录按钮设置点击事件
        mLoginBtn= (Button) findViewById(R.id.login_btn_login);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();

                String userName = mUserNameEt.getText().toString().trim();
                String password = mPasswordEt.getText().toString().trim();

                MyApplication.getSingleton().doLogin(
                        LoginActivity.this,
                        userName,
                        password,
                        new IAsyncLoadListener<UserEntity>() {
                    @Override
                    public void onSuccess(UserEntity userEntity) {
                        dialog.dismiss();
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
                    }

                    @Override
                    public void onFailure(String msg) {
                        dialog.dismiss();
                    }
                });

            }
        });
    }

    /**
     * 显示升级弹窗
     */
    public void showUpdateDialog(final UpdateInfoEntity info){
        View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.layout_update_dialog, null);
        // 设置style 控制默认dialog带来的边距问题
        final Dialog dialog = new Dialog(LoginActivity.this, R.style.common_dialog);
        dialog.setContentView(view);
        dialog.show();

        ((TextView)(view.findViewById(R.id.update_info_tv))).setText(info.getDescription());

        // 取消按钮监听
        view.findViewById(R.id.layout_exit_dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // 确定按钮监听
        view.findViewById(R.id.layout_exit_dialog_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                downLoadApk(info);
            }
        });

        // 设置相关位置，一定要在 show()之后
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    /*
     * 从服务器中下载APK
     */
    protected void downLoadApk(final UpdateInfoEntity info) {
        //进度条对话框
        final android.app.ProgressDialog progressDialog = new  android.app.ProgressDialog(this);
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("正在下载更新");
        progressDialog.show();
        new Thread(){
            @Override
            public void run() {
                try {
                    File file = DownloadManager.getFileFromServer(info.getUrl(), progressDialog);
//                    sleep(3000);
                    installApk(file);
                    progressDialog.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    progressDialog.dismiss(); //结束掉进度条对话框
                    Looper.prepare();
                    Toast.makeText(LoginActivity.this,"安装包下载失败",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                    e.printStackTrace();
                }
            }}.start();
    }

    //安装apk
    protected void installApk(File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
    }

    /*****************************EditText处理********************************/

    /**
     * 传入EditText的Id
     * 没有传入的EditText不做处理
     *
     * @return id 数组
     */
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.login_et_username, R.id.login_et_password};
        return ids;
    }

    /**
     * 传入要过滤的View
     * 过滤之后点击将不会有隐藏软键盘的操作
     *
     * @return id 数组
     */
    public View[] filterViewByIds() {
        View[] views = {mRemeberCb, mAutomaticCb,mSettingIBtn,mLoginBtn};
        return views;
    }

}
