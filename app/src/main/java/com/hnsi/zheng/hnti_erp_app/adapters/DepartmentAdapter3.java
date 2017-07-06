package com.hnsi.zheng.hnti_erp_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.beans.DepartmentEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门列表数据Adapter（优化版）
 * Created by Zheng on 2016/5/16.
 */
public class DepartmentAdapter3 extends MyBaseAdapter {

    /**
     * 定义一个数组，用来保存每个条目的ListView的状态
     */
    boolean[] isOpen;

    public DepartmentAdapter3(Context context, ArrayList<DepartmentEntity> data) {
        super(context, data);
    }

    @Override
    public void addItems(List items) {
        super.addItems(items);
        isOpen=new boolean[items.size()];
    }

    @Override
    public View createView(LayoutInflater inflater, final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView==null){
            holder=new ViewHolder();
            convertView=inflater.inflate(R.layout.item_contacts_department, parent, false);
            holder.mPanelRly= (RelativeLayout) convertView.findViewById(R.id.item_contacts_rly);
            holder.mDepNameTv= (TextView) convertView.findViewById(R.id.item_contacts_tv_department);
            holder.mIconIv= (ImageView) convertView.findViewById(R.id.item_contacts_iv_icon);
            holder.mPersonNumTv= (TextView) convertView.findViewById(R.id.item_contacts_tv_num_of_person);
            holder.mFirstLv= (ListView) convertView.findViewById(R.id.item_contacts_lv);
            holder.mSecondLv= (ListView) convertView.findViewById(R.id.item_contacts_person_lv);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        /** 通讯录分组的展开动画 */
//        LayoutTransition transition=new LayoutTransition();
//        transition.setAnimator(LayoutTransition.CHANGE_APPEARING,
//                transition.getAnimator(LayoutTransition.CHANGE_APPEARING));
//        transition.setAnimator(LayoutTransition.APPEARING,
//                transition.getAnimator(LayoutTransition.APPEARING));
//        transition.setAnimator(LayoutTransition.DISAPPEARING,
//                transition.getAnimator(LayoutTransition.DISAPPEARING));
//        transition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING,
//                transition.getAnimator(LayoutTransition.CHANGE_DISAPPEARING));
//        ((ViewGroup)holder.mPanelRly.getParent()).setLayoutTransition(transition);


        final ListView mDepartmentDetailLv = holder.mFirstLv;
        final ListView mDepartmentPersonLv = holder.mSecondLv;
        final ImageView mIconIv=holder.mIconIv;

        if(isOpen.length>0) {
            if (isOpen[position]) {
                mDepartmentDetailLv.setVisibility(View.VISIBLE);
                mDepartmentPersonLv.setVisibility(View.VISIBLE);
                mIconIv.setImageResource(R.mipmap.arrow_open);
            } else {
                mDepartmentDetailLv.setVisibility(View.GONE);
                mDepartmentPersonLv.setVisibility(View.GONE);
                mIconIv.setImageResource(R.mipmap.arrow_close);
            }
        }

        DepartmentEntity entity= (DepartmentEntity) mData.get(position);
        if (entity.getChildDepartmentList().size() == 0) {//如果没有二级部门
            mDepartmentDetailLv.setAdapter(new DepartmentDetailAdapter(getContext(), entity.getDetailList()));
            mDepartmentPersonLv.setAdapter(new DepartmentDetailAdapter2(getContext(), new ArrayList()));
            holder.mPersonNumTv.setText("" + entity.getDetailList().size());
        } else if (entity.getChildDepartmentList().size() > 0) {//如果有二级部门
            mDepartmentDetailLv.setAdapter(new DepartmentChildAdapter(getContext(), entity.getChildDepartmentList()));
            mDepartmentPersonLv.setAdapter(new DepartmentDetailAdapter2(getContext(), entity.getDetailList()));

            int mPersonNum=entity.getDetailList().size();
            for(int i=0;i<entity.getChildDepartmentList().size();i++){
                mPersonNum += entity.getChildDepartmentList().get(i).getDetailList().size();
            }

            holder.mPersonNumTv.setText("" + mPersonNum);
        }

        holder.mDepNameTv.setText(entity.getOrgname());

        holder.mPanelRly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen[position]){
                    isOpen[position]=false;
//                    Toast.makeText(getContext(),"change "+position+" false",Toast.LENGTH_SHORT).show();
                    mDepartmentDetailLv.setVisibility(View.GONE);
                    mDepartmentPersonLv.setVisibility(View.GONE);
                    mIconIv.setImageResource(R.mipmap.arrow_close);
                }else{
                    isOpen[position]=true;
//                    Toast.makeText(getContext(),"change "+position+" true",Toast.LENGTH_SHORT).show();
                    mDepartmentDetailLv.setVisibility(View.VISIBLE);
                    mDepartmentPersonLv.setVisibility(View.VISIBLE);
                    mIconIv.setImageResource(R.mipmap.arrow_open);
                }
            }
        });

        return convertView;
    }

    class ViewHolder{
        RelativeLayout mPanelRly;
        TextView mDepNameTv;
        ImageView mIconIv;
        TextView mPersonNumTv;
        ListView mFirstLv;
        ListView mSecondLv;
    }

}