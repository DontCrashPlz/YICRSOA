package com.hnsi.zheng.hnti_erp_app.utils;

import android.content.Context;

import com.hnsi.zheng.hnti_erp_app.beans.DepartmentDetailEntity;

import java.util.List;

/**
 * Created by yetwish on 2015-05-11
 */
@Deprecated
public class SearchAdapter extends CommonAdapter<DepartmentDetailEntity>{

    public SearchAdapter(Context context, List<DepartmentDetailEntity> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, int position) {
//        holder.setImageResource(R.id.item_search_iv_icon,mData.get(position).getIconId())
//                .setText(R.id.item_search_tv_title,mData.get(position).getTitle())
//                .setText(R.id.item_search_tv_content,mData.get(position).getContent())
//                .setText(R.id.item_search_tv_comments,mData.get(position).getComments());
    }
}
