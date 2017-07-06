package com.hnsi.zheng.hnti_erp_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.activities.MessageDetailActivity;
import com.hnsi.zheng.hnti_erp_app.activities.NoticeDetailActivity;
import com.hnsi.zheng.hnti_erp_app.beans.MessageEntity;
import com.hnsi.zheng.hnti_erp_app.beans.NewsEntity;

import java.util.ArrayList;

/**
 * 消息数据Adapter
 * Created by Zheng on 2016/5/6.
 */
public class MessageAdapter extends MyBaseAdapter{

    public MessageAdapter(Context context, ArrayList<MessageEntity> data) {
        super(context, data);
    }

    @Override
    public View createView(LayoutInflater inflater, final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_notice,parent,false);
            holder=new ViewHolder();
            holder.mMsgRly= (RelativeLayout) convertView.findViewById(R.id.item_notice_rly);
            holder.mTitleTv= (TextView) convertView.findViewById(R.id.item_notice_title);
            holder.mMsgTypeTv= (TextView) convertView.findViewById(R.id.item_notice_department);
            holder.mSendEmpnameTv= (TextView) convertView.findViewById(R.id.item_notice_author);
            holder.mTimeTv= (TextView) convertView.findViewById(R.id.item_notice_time);
            convertView.setTag(holder);
        }
        MessageEntity entity=(MessageEntity)mData.get(position);
        holder= (ViewHolder) convertView.getTag();
        holder.mMsgRly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("newsId", ((MessageEntity) mData.get(position)).getId());
                intent.setClass(getContext(), MessageDetailActivity.class);
                getContext().startActivity(intent);
            }
        });
        holder.mTitleTv.setText(entity.getTitle());
        holder.mMsgTypeTv.setText(entity.getMsgType());
        holder.mSendEmpnameTv.setText(entity.getSendEmpname());
        String startDate=entity.getSendTime().substring(0,19);
        holder.mTimeTv.setText(startDate);
        return convertView;
    }

    class ViewHolder{
        RelativeLayout mMsgRly;
        TextView mTitleTv;
        TextView mMsgTypeTv;
        TextView mSendEmpnameTv;
        TextView mTimeTv;
    }
}
