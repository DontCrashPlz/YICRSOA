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
import com.hnsi.zheng.hnti_erp_app.activities.RulesDetailActivity;
import com.hnsi.zheng.hnti_erp_app.beans.RulesEntity;

import java.util.ArrayList;

/**
 * 规章制度数据Adapter
 * Created by Zheng on 2016/7/27.
 */
public class RulesAdapter extends MyBaseAdapter{

    public RulesAdapter(Context context, ArrayList<RulesEntity> data) {
        super(context, data);
    }

    @Override
    public View createView(LayoutInflater inflater, final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_rules,parent,false);
            holder=new ViewHolder();
            holder.mNoticeRly= (RelativeLayout) convertView.findViewById(R.id.item_rules_rly);
            holder.mTitleTv= (TextView) convertView.findViewById(R.id.item_rules_title);
            holder.mTimeTv= (TextView) convertView.findViewById(R.id.item_rules_time);
            convertView.setTag(holder);
        }
        RulesEntity entity=(RulesEntity)mData.get(position);
        holder= (ViewHolder) convertView.getTag();
        holder.mNoticeRly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("param_id", ((RulesEntity) mData.get(position)).getId());
                intent.setClass(getContext(), RulesDetailActivity.class);
                getContext().startActivity(intent);
            }
        });
        holder.mTitleTv.setText(entity.getTitle());
        String startDate=entity.getOperationTime().substring(0,19);
        holder.mTimeTv.setText(startDate);
        return convertView;
    }

    class ViewHolder{
        RelativeLayout mNoticeRly;
        TextView mTitleTv;
        TextView mTimeTv;
    }
}