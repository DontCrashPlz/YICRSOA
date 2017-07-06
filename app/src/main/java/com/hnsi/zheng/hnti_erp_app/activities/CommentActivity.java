package com.hnsi.zheng.hnti_erp_app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.adapters.CommentAdapter;
import com.hnsi.zheng.hnti_erp_app.app.MyApplication;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 评论界面
 * Created by Zheng on 2016/5/11.
 */
@Deprecated
public class CommentActivity extends MyBaseActivity{
    //标题头返回按钮
    ImageButton mBackBtn;
    //页面标题
    TextView mTitleTv;
    //评论内容列表
    ListView mContentLv;
    //评论输入框
    EditText mCommentEt;
    //提交评论按钮
    Button mCommitBtn;
    //评论内容Adapter
    CommentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        initUI();
    }

    /**
     * 初始化界面
     */
    private void initUI() {
        initTitleBar();

        //加载并为评论列表设置数据
        mContentLv= (ListView) findViewById(R.id.comment_lv_content);
        mAdapter=new CommentAdapter(this,new ArrayList(Arrays.asList(1,2,3)));
        mContentLv.setAdapter(mAdapter);

        //加载评论输入框
        mCommentEt= (EditText) findViewById(R.id.comment_et_content);

        //加载并为评论提交按钮设置点击事件
        mCommitBtn= (Button) findViewById(R.id.comment_btn_commit);
        mCommitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
        mTitleTv.setText("评论");
    }
}
