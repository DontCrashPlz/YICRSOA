package com.hnsi.zheng.hnti_erp_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.beans.UnFinishedEntity;

import java.util.ArrayList;

/**
 * 待办事项列表数据Adapter
 * Created by Zheng on 2016/6/20.
 */
public class UnFinishedListAdapter extends MyBaseAdapter{

    public UnFinishedListAdapter(Context context, ArrayList<UnFinishedEntity> data) {
        super(context, data);
    }

    @Override
    public View createView(LayoutInflater inflater, int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=inflater.inflate(R.layout.item_approval_no_complete_matter,parent,false);
            holder.mClassTv= (TextView) convertView.findViewById(R.id.item_unfinished_tv_class);
            holder.mTitleTv= (TextView) convertView.findViewById(R.id.item_unfinished_tv_title);
            holder.mDateTv= (TextView) convertView.findViewById(R.id.item_unfinished_tv_starttime);
            convertView.setTag(holder);
        }
        holder= (ViewHolder) convertView.getTag();
        UnFinishedEntity entity= (UnFinishedEntity) mData.get(position);
        holder.mClassTv.setText(entity.getProcessChName());
        holder.mTitleTv.setText(entity.getProcessInstName());
        String startTime=entity.getStartTime().substring(0,19);
        holder.mDateTv.setText(startTime);

        return convertView;
    }

    private class ViewHolder{
        TextView mClassTv;
        TextView mTitleTv;
        TextView mDateTv;
    }
}
