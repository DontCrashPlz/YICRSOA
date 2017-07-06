package com.hnsi.zheng.hnti_erp_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.beans.FinishedEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 已办事项列表数据Adapter
 * Created by Zheng on 2016/6/20.
 */
public class FinishedListAdapter extends MyBaseAdapter{

    public FinishedListAdapter(Context context, ArrayList<FinishedEntity> data) {
        super(context, data);
    }

    @Override
    public View createView(LayoutInflater inflater, int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_approval_complete_matter,parent,false);
            holder=new ViewHolder();
            holder.mTitleTv= (TextView) convertView.findViewById(R.id.item_finished_tv_title);
            holder.mClassTv= (TextView) convertView.findViewById(R.id.item_finished_tv_class);
            holder.mTimeTv= (TextView) convertView.findViewById(R.id.item_finished_tv_starttime);
            convertView.setTag(holder);
        }
        holder= (ViewHolder) convertView.getTag();
        FinishedEntity entity= (FinishedEntity) mData.get(position);
        holder.mTitleTv.setText(entity.getProcessInstName());
        holder.mClassTv.setText(entity.getProcessChName());
        String startTime=entity.getStartTime().substring(0,19);
        holder.mTimeTv.setText(startTime);

        return convertView;
    }

    class ViewHolder{
        TextView mTitleTv;
        TextView mClassTv;
        TextView mTimeTv;
    }
}
