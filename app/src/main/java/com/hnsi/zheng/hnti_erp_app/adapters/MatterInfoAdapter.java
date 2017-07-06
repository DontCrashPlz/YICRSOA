package com.hnsi.zheng.hnti_erp_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.beans.MatterGroupEntity;

import java.util.List;

/**
 * 事件详情数据
 * Created by Zheng on 2016/6/27.
 */
public class MatterInfoAdapter extends MyBaseAdapter{

    public MatterInfoAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    public View createView(LayoutInflater inflater, int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=inflater.inflate(R.layout.item_matter_info,parent,false);
            holder.mNameTv= (TextView) convertView.findViewById(R.id.item_matter_name);
            holder.mInfoLv= (ListView) convertView.findViewById(R.id.item_matter_info);
            convertView.setTag(holder);
        }
        holder= (ViewHolder) convertView.getTag();

        MatterGroupEntity entity= (MatterGroupEntity) mData.get(position);
        holder.mNameTv.setText(entity.getLabel());
        holder.mInfoLv.setAdapter(new MatterInfoListAdapter2(getContext(),entity.getChildList()));

        return convertView;
    }

    private class ViewHolder{
        TextView mNameTv;
        ListView mInfoLv;
    }
}
