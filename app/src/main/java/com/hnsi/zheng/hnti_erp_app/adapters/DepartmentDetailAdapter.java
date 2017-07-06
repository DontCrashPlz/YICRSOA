package com.hnsi.zheng.hnti_erp_app.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.activities.JingBanRenListActivity;
import com.hnsi.zheng.hnti_erp_app.activities.PersonInfoActivity;
import com.hnsi.zheng.hnti_erp_app.activities.SearchActivity;
import com.hnsi.zheng.hnti_erp_app.app.MyApplication;
import com.hnsi.zheng.hnti_erp_app.beans.DepartmentDetailEntity;
import com.hnsi.zheng.hnti_erp_app.ebusbeans.JingBanRenInfo;
import com.hnsi.zheng.hnti_erp_app.utils.SharedPrefUtils;
import com.hnsi.zheng.hnti_erp_app.utils.UserIconDisplayOptionsFactory;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门人员详情列表
 * Created by Zheng on 2016/5/9.
 */
public class DepartmentDetailAdapter extends MyBaseAdapter{
    private Context mContext;
    private List<DepartmentDetailEntity>mData=new ArrayList<>();
    public DepartmentDetailAdapter(Context context, List data) {
        super(context, data);
        mContext=context;
        mData=data;
    }
    @Override
    public View createView(LayoutInflater inflater,  final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_department_detail,parent,false);
            holder=new ViewHolder();
            holder.mDetailRly= (RelativeLayout) convertView.findViewById(R.id.item_department_rly);
            holder.mUserIconTv= (TextView) convertView.findViewById(R.id.item_circle_textview);
            holder.mUserIconIv= (ImageView) convertView.findViewById(R.id.item_circle_imageview);
            holder.mUserNameTv= (TextView) convertView.findViewById(R.id.item_department_username);
            holder.mUserPositionTv= (TextView) convertView.findViewById(R.id.item_department_userposition);
            convertView.setTag(holder);
        }
        holder= (ViewHolder) convertView.getTag();
        /** 此处处理通讯录用户的点击事件 */
        holder.mDetailRly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(MyApplication.getSingleton().isActivityExist(JingBanRenListActivity.class)){
                    EventBus.getDefault().post(new JingBanRenInfo(mData.get(position).getEmpid(),mData.get(position).getEmpname()));
                    ((SearchActivity)mContext).finish();
                }else{
                    Intent intent = new Intent();
                    intent.putExtra("name", mData.get(position).getEmpname());
                    intent.putExtra("sex", mData.get(position).getSex());
                    intent.putExtra("phoneNum", mData.get(position).getMobileno());
                    intent.putExtra("department", mData.get(position).getOrgname());
                    intent.putExtra("position", mData.get(position).getPosiname());
                    intent.putExtra("linePhone", mData.get(position).getOtel());
                    intent.putExtra("email", mData.get(position).getOemail());
                    intent.putExtra("headimgUrl", mData.get(position).getHeadimgUrl());
                    intent.setClass(mContext, PersonInfoActivity.class);
                    mContext.startActivity(intent);
                }

            }
        });

        String headimgUrl=mData.get(position).getHeadimgUrl();

        if("男".equals(mData.get(position).getSex())){
            holder.mUserIconTv.setBackgroundResource(R.drawable.circle_background_blue);
        }else if("女".equals(mData.get(position).getSex())){
            holder.mUserIconTv.setBackgroundResource(R.drawable.circle_background_pink);
        }

        if(!"".equals(headimgUrl)) {
            holder.mUserIconTv.setVisibility(View.GONE);
            holder.mUserIconIv.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(headimgUrl, holder.mUserIconIv, UserIconDisplayOptionsFactory.getListOptions());
        }else{
            holder.mUserIconTv.setVisibility(View.VISIBLE);
            holder.mUserIconIv.setVisibility(View.GONE);
        }

        String name=mData.get(position).getEmpname();
        holder.mUserIconTv.setText(name.substring(name.length() - 1));
        holder.mUserNameTv.setText(name);
        holder.mUserPositionTv.setText(mData.get(position).getPosiname());
        return convertView;
    }
    class ViewHolder{
        RelativeLayout mDetailRly;
        TextView mUserIconTv;
        ImageView mUserIconIv;
        TextView mUserNameTv;
        TextView mUserPositionTv;
    }
}
