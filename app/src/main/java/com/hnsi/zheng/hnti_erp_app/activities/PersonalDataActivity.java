package com.hnsi.zheng.hnti_erp_app.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.app.AppConstants;
import com.hnsi.zheng.hnti_erp_app.app.MyApplication;
import com.hnsi.zheng.hnti_erp_app.beans.UserEntity;
import com.hnsi.zheng.hnti_erp_app.http.UpLoadingHeadimgRequest;
import com.hnsi.zheng.hnti_erp_app.http.VolleyHelper;
import com.hnsi.zheng.hnti_erp_app.utils.DensityUtil;
import com.hnsi.zheng.hnti_erp_app.utils.FileUtils;
import com.hnsi.zheng.hnti_erp_app.utils.PhotoUtils;
import com.hnsi.zheng.hnti_erp_app.utils.ScreenUtils;
import com.hnsi.zheng.hnti_erp_app.utils.Tools;
import com.hnsi.zheng.hnti_erp_app.utils.UserIconDisplayOptionsFactory;
import com.hnsi.zheng.hnti_erp_app.widgets.CircleImageView;
import com.hnsi.zheng.hnti_erp_app.widgets.progressDialog.BaseProgressDialog;
import com.hnsi.zheng.hnti_erp_app.widgets.progressDialog.ProgressDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * 个人资料页面
 * Created by Zheng on 2016/8/1.
 */
public class PersonalDataActivity extends MyBaseActivity{
    private ImageButton mBackIBtn;
    private TextView mTitleTv;
    private RelativeLayout mChangeIconRly;
    private CircleImageView mIconIv;
    private TextView mNameTv;
    private TextView mSexTv;
    private TextView mDepartmentTv;
    private TextView mPostTv;
    private TextView mMobilePhoneTv;
    private TextView mTelephoneTv;
    private TextView mEmailTv;

    private final int REQUEST_CODE_TAKE_PICTURE = 1001;// 拍照
    private final int REQUEST_CODE_PICK_PICTURE = 1002;// 相册选择
    private final int REQUEST_CODE_PHOTO_RESULT = 1003;// 结果

    // 拍照图片名称
    private String photoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personaldata);

        initUI();

//        String url= Tools.jointIpAddress()+MyApplication.mUser.getHeadimg();
//
//        VolleyHelper.getInstance(this).LoadImage(url, mIconIv);
    }

    /**
     * 初始化界面
     */
    private void initUI() {
        initTitleBar();

        mChangeIconRly= (RelativeLayout) findViewById(R.id.personaldata_rly_change_icon);
        mIconIv= (CircleImageView) findViewById(R.id.personaldata_civ_user_icon);
        mNameTv= (TextView) findViewById(R.id.personaldata_tv_username);
        mSexTv= (TextView) findViewById(R.id.personaldata_tv_usersex);
        mDepartmentTv= (TextView) findViewById(R.id.personaldata_tv_userdepartment);
        mPostTv= (TextView) findViewById(R.id.personaldata_tv_userpost);
        mMobilePhoneTv= (TextView) findViewById(R.id.personaldata_tv_usermobilephone);
        mTelephoneTv= (TextView) findViewById(R.id.personaldata_tv_usertelephone);
        mEmailTv= (TextView) findViewById(R.id.personaldata_tv_useremail);

        UserEntity entity= MyApplication.mUser;

        if(!"null".equals(entity.getEmpname())){
            mNameTv.setText(entity.getEmpname());
        }
        if(!"null".equals(entity.getSex())){
            mSexTv.setText(entity.getSex());
        }
        if(!"null".equals(entity.getOrgname())){
            mDepartmentTv.setText(entity.getOrgname());
        }
        if(!"null".equals(entity.getPosiname())){
            mPostTv.setText(entity.getPosiname());
        }
        if(!"null".equals(entity.getMobileno())){
            mMobilePhoneTv.setText(entity.getMobileno());
        }
        if(!"null".equals(entity.getOtel())){
            mTelephoneTv.setText(entity.getOtel());
        }
        if(!"null".equals(entity.getOemail())){
            mEmailTv.setText(entity.getOemail());
        }
        if(!"".equals(MyApplication.mUser.getHeadimg())&&!(MyApplication.mUser.getHeadimg()).endsWith("null")){
            ImageLoader.getInstance().displayImage(
                    MyApplication.mUser.getHeadimg(),mIconIv, UserIconDisplayOptionsFactory.getListOptions());
        }

        mChangeIconRly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectMenu();
            }
        });

    }

    /**
     * 初始化标题头
     */
    private void initTitleBar() {
        mBackIBtn= (ImageButton) findViewById(R.id.titlebar_ibtn_back);
        mBackIBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTitleTv= (TextView) findViewById(R.id.titlebar_tv_title);
        mTitleTv.setText("个人资料");
    }

    /**
     * 弹出图片选择方式菜单
     */
    private void showSelectMenu(){
        View view = LayoutInflater.from(this).inflate(R.layout.layout_change_icon_menu, null);
        // 设置style 控制默认dialog带来的边距问题
        final Dialog dialog = new Dialog(this, R.style.common_dialog);
        dialog.setContentView(view);
        dialog.show();

        // 拍照按钮监听
        view.findViewById(R.id.icon_menu_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                //构造输出文件路径
                photoName = PhotoUtils.createJPEGTempFileName();
                FileUtils.mkdirs(AppConstants.APP_COVER_IMAGE_FOLDER);
                File picture = new File(AppConstants.APP_COVER_IMAGE_FOLDER, photoName);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picture));

                startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
            }
        });

        // 从相册选取按钮监听
        view.findViewById(R.id.icon_menu_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //使用Intent调用系统相册或者文件管理器 获取内容
                dialog.dismiss();

                //构造输出文件路径
                photoName = PhotoUtils.createJPEGTempFileName();
                FileUtils.mkdirs(AppConstants.APP_COVER_IMAGE_FOLDER);
                File picture = new File(AppConstants.APP_COVER_IMAGE_FOLDER, photoName);

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picture));

                startActivityForResult(intent, REQUEST_CODE_PICK_PICTURE);
            }
        });

        // 删除当前头像监听
//        view.findViewById(R.id.icon_menu_delete).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //使用Intent调用系统相册或者文件管理器 获取内容
//                dialog.dismiss();
//
//            }
//        });



        // 取消按钮监听
        view.findViewById(R.id.icon_menu_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // 设置相关位置，一定要在 show()之后
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 刷新页面数据

        if (requestCode == REQUEST_CODE_PICK_PICTURE) {//选择图片页返回
            if (resultCode == Activity.RESULT_OK) {

                // 构造裁剪输出路径
                photoName = PhotoUtils.createJPEGTempFileName();
                FileUtils.mkdirs(AppConstants.APP_COVER_IMAGE_FOLDER);
                File picture = new File(AppConstants.APP_COVER_IMAGE_FOLDER, photoName);

                if (Build.VERSION.SDK_INT < 19) {
                    if (data != null) {
                        // 获取源图片路径
                        Uri uri = data.getData();
                        startPhotoZoom(uri, Uri.fromFile(picture));
                    }
                } else {
                    // 获取源图片路径
                    Uri uri = data.getData();
                    String thePath = PhotoUtils.getPath(this, uri);
                    Uri resUri = Uri.fromFile(new File(thePath));
                    startPhotoZoom(resUri, Uri.fromFile(picture));
                }
            }
        }else if (requestCode == REQUEST_CODE_TAKE_PICTURE) {//照相界面返回
            if (resultCode == Activity.RESULT_OK) {
                File picture = new File(AppConstants.APP_COVER_IMAGE_FOLDER, photoName);

                if (picture.exists()) {
                    Uri uri = Uri.fromFile(picture);
                    startPhotoZoom(uri, uri);
                }
            }

        } else if (requestCode == REQUEST_CODE_PHOTO_RESULT) {//裁剪之后返回的结果
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    final BaseProgressDialog mDialog=new ProgressDialog(this);
                    mDialog.setLabel("正在上传...");
                    mDialog.show();
                    File picture = new File(AppConstants.APP_COVER_IMAGE_FOLDER, photoName);
                    // 展示在当前页面
                    ImageLoader.getInstance()
                            .displayImage(Uri.fromFile(picture).toString(), mIconIv);

                    /** 向服务器上传头像 */
                    VolleyHelper.getInstance(this).addRequestTask(new UpLoadingHeadimgRequest(
                            picture.getPath(),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    try {
                                        if(jsonObject.getBoolean("success")){
                                            if(!(jsonObject.getString("img").endsWith("null"))){
                                                mDialog.dismiss();
                                                Log.e("uploading",jsonObject.toString());
                                                Toast.makeText(PersonalDataActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
                                                String headimgUrl=jsonObject.getString("img").replaceAll("'\'","");
                                                Log.e("headimgUrl", headimgUrl);
                                                MyApplication.mUser.setHeadimg(Tools.jointIpAddress() + headimgUrl);
                                            }else {
                                                mDialog.dismiss();
                                                Log.e("uploading",jsonObject.toString());
                                                Toast.makeText(PersonalDataActivity.this,"上传的 图片无效",Toast.LENGTH_SHORT).show();
                                            }
                                        }else{
                                            mDialog.dismiss();
                                            Log.e("uploading",jsonObject.toString());
                                            Toast.makeText(PersonalDataActivity.this,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    mDialog.dismiss();
                                    Toast.makeText(PersonalDataActivity.this,"上传失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                    ),"TAG");

//                    // 设置到当前播放列表对象中
//                    mCurrentPlaylist.setCoverUrl(picture.getAbsolutePath());
//                    // 插入数据库
//                    mPlaylistTableHelper.changePlaylistCover(mCurrentPlaylist);
                }
            }
        }
    }


    /**
     * 裁剪图片方法实现
     * @param uri
     */
    public void startPhotoZoom(Uri uri, Uri output) {
        /*
         * 至于下面这个Intent的ACTION是怎么知道的，大家可以看下自己路径下的如下网页
         * yourself_sdk_path/docs/reference/android/content/Intent.html
         */
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        /** 这里定义 */
//        int width = ScreenUtils.getScreenWidth(this);
        int width = 0;
        if (mIconIv != null) {
            width = mIconIv.getWidth();
        } else {
            width = DensityUtil.dp2px(this, 65);
        }

        int height = 0;
        if (mIconIv != null) {
            height = mIconIv.getHeight();
        } else {
            height = DensityUtil.dp2px(this, 65);
        }

        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", width);
        intent.putExtra("aspectY", height);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        // 设置图片输出路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", "true");
        intent.putExtra("return-data", "true");

        startActivityForResult(intent, REQUEST_CODE_PHOTO_RESULT);
    }

}
