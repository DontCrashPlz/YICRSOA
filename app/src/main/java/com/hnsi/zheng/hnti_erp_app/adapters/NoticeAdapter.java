package com.hnsi.zheng.hnti_erp_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.activities.NoticeDetailActivity;
import com.hnsi.zheng.hnti_erp_app.beans.NewsEntity;

import java.util.ArrayList;

/**
 * 公告数据Adapter
 * Created by Zheng on 2016/5/6.
 */
public class NoticeAdapter extends MyBaseAdapter{

    public NoticeAdapter(Context context, ArrayList<NewsEntity> data) {
        super(context, data);
    }

    @Override
    public View createView(LayoutInflater inflater, final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_notice,parent,false);
            holder=new ViewHolder();
            holder.mNoticeRly= (RelativeLayout) convertView.findViewById(R.id.item_notice_rly);
            holder.mTitleTv= (TextView) convertView.findViewById(R.id.item_notice_title);
            holder.mDepartmentTv= (TextView) convertView.findViewById(R.id.item_notice_department);
            holder.mAuthorTv= (TextView) convertView.findViewById(R.id.item_notice_author);
            holder.mTimeTv= (TextView) convertView.findViewById(R.id.item_notice_time);
            convertView.setTag(holder);
        }
        NewsEntity entity=(NewsEntity)mData.get(position);
        holder= (ViewHolder) convertView.getTag();
        holder.mNoticeRly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("newsId", ((NewsEntity) mData.get(position)).getId());
                intent.setClass(getContext(), NoticeDetailActivity.class);
                getContext().startActivity(intent);
            }
        });
        holder.mTitleTv.setText(entity.getTitle());
        holder.mDepartmentTv.setText(entity.getOperationDeptName());
        holder.mAuthorTv.setText(entity.getOperatorName());
        String startDate=entity.getStartDate().substring(0,10);
        holder.mTimeTv.setText(startDate);
        return convertView;
    }

    class ViewHolder{
        RelativeLayout mNoticeRly;
        TextView mTitleTv;
        TextView mDepartmentTv;
        TextView mAuthorTv;
        TextView mTimeTv;
    }
}
