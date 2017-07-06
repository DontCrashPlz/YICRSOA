package com.hnsi.zheng.hnti_erp_app.widgets;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.activities.NoticeDetailActivity;
import com.hnsi.zheng.hnti_erp_app.beans.NewsEntity;
import com.hnsi.zheng.hnti_erp_app.utils.NewsImgDisplayOptionsFactory;
import com.hnsi.zheng.hnti_erp_app.utils.Tools;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 新闻条目样式
 * Created by Zheng on 2016/10/10.
 */
public class NewsItemView extends LinearLayout{

    private RelativeLayout mNewsWithImgRly=null;
    private ImageView mNewsImgIv=null;
    private TextView mNewsImgTitleTv=null;
    private TextView mNewsImgKindTv=null;
    private TextView mNewsImgDepartmentTv=null;
    private TextView mNewsImgTimeTv=null;

    private RelativeLayout mNewsWithoutImgRly=null;
    private TextView mNewsTitleTv=null;
    private TextView mNewsKindTv=null;
    private TextView mNewsDepartmentTv=null;
    private TextView nNewsTimeTv=null;

    private ImageLoader mImageLoader = null;
    private DisplayImageOptions mImageOptions = null;

    public NewsItemView(Context context) {
        super(context);
        init();
    }

    public NewsItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NewsItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    protected void init(){
        mImageLoader = ImageLoader.getInstance();
        mImageOptions = NewsImgDisplayOptionsFactory.getListOptions();

        // 将布局资源文件加载到当前类中
        inflate(getContext(), R.layout.item_news_notices, this);

        mNewsWithImgRly= (RelativeLayout) findViewById(R.id.item_news_withimg);
        mNewsImgIv= (ImageView) findViewById(R.id.item_news_img);
        mNewsImgTitleTv= (TextView) findViewById(R.id.item_news_img_title);
        mNewsImgKindTv= (TextView) findViewById(R.id.item_news_img_kind);
        mNewsImgDepartmentTv= (TextView) findViewById(R.id.item_news_img_department);
        mNewsImgTimeTv= (TextView) findViewById(R.id.item_news_img_time);

        mNewsWithoutImgRly= (RelativeLayout) findViewById(R.id.item_news_withoutimg);
        mNewsTitleTv= (TextView) findViewById(R.id.item_news_title);
        mNewsKindTv= (TextView) findViewById(R.id.item_news_kind);
        mNewsDepartmentTv= (TextView) findViewById(R.id.item_news_department);
        nNewsTimeTv= (TextView) findViewById(R.id.item_news_time);

    }

    /**
     * 为item设置数据，根据数据，自动判断显示哪一个布局
     * @param entity
     */
    public void setData(final NewsEntity entity){
        if (entity == null) {
            throw new NullPointerException("ItemView's data may not be null.");
        }

        // 如果"imgsrc"字段值为null或以null结尾，则显示文字布局，否则显示图片布局
        if ("".equals(entity.getImgsrc())|"null".equals(entity.getImgsrc())|entity.getImgsrc().endsWith("null")) {
            mNewsWithImgRly.setVisibility(View.GONE);
            mNewsWithoutImgRly.setVisibility(View.VISIBLE);

            //设置点击事件
            mNewsWithoutImgRly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("newsId", (entity.getId()));
                    intent.setClass(getContext(), NoticeDetailActivity.class);
                    getContext().startActivity(intent);
                }
            });
            //设置标题
            mNewsTitleTv.setText(""+entity.getTitle());
            //设置种类
            mNewsKindTv.setText(""+entity.getClassname());
            //设置发布部门
            mNewsDepartmentTv.setText(""+entity.getOperationDeptName());
            //设置发布日期
            nNewsTimeTv.setText(""+entity.getStartDate());
        } else {
            mNewsWithImgRly.setVisibility(View.VISIBLE);
            mNewsWithoutImgRly.setVisibility(View.GONE);

            mNewsWithImgRly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("newsId", (entity.getId()));
                    intent.setClass(getContext(), NoticeDetailActivity.class);
                    getContext().startActivity(intent);
                }
            });
            mImageLoader.displayImage(Tools.jointIpAddress()+entity.getImgsrc(), mNewsImgIv, mImageOptions);
            mNewsImgTitleTv.setText(""+entity.getTitle());
            mNewsImgKindTv.setText("" + entity.getClassname());
            mNewsImgDepartmentTv.setText("" + entity.getOperationDeptName());
            mNewsImgTimeTv.setText("" + entity.getStartDate());
        }

    }

}
