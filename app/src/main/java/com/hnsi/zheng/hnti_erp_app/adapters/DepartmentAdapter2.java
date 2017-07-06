package com.hnsi.zheng.hnti_erp_app.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
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

/**
 * 部门列表数据Adapter
 * Created by Zheng on 2016/5/16.
 */
@Deprecated
public class DepartmentAdapter2 extends MyBaseAdapter {
    private Context mContext;
    private ArrayList<DepartmentEntity> mDate = new ArrayList();

    /**
     * 定义一个数组，用来保存每个条目的ListView的状态
     */
    boolean[] isOpen;

    public DepartmentAdapter2(Context context, ArrayList data) {
        super(context, data);
        mContext = context;
        mDate = data;
        Log.e("department2", data.toString());
        isOpen = new boolean[data.size()];
        Log.e("isopen", isOpen.toString());
    }

    @Override
    public View createView(LayoutInflater inflater, final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_contacts_department, parent, false);

        RelativeLayout mPanelRly = (RelativeLayout) view.findViewById(R.id.item_contacts_rly);
        final ImageView mIconIv = (ImageView) view.findViewById(R.id.item_contacts_iv_icon);
        TextView mDepartmentNameTv = (TextView) view.findViewById(R.id.item_contacts_tv_department);
        TextView mNumOfPersonTv = (TextView) view.findViewById(R.id.item_contacts_tv_num_of_person);

        //如果没有二级部门
        if (mDate.get(position).getChildDepartmentList().size() == 0) {
            final ListView mDepartmentDetailLv = (ListView) view.findViewById(R.id.item_contacts_lv);
            mDepartmentDetailLv.setAdapter(new DepartmentDetailAdapter(mContext, mDate.get(position).getDetailList()));
            mPanelRly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isOpen[position]) {
                        Log.e("isopen1", "" + isOpen[position]);
                        mIconIv.setImageResource(R.mipmap.detail_open);
                        mDepartmentDetailLv.setVisibility(View.VISIBLE);
                        isOpen[position] = true;
                        Log.e("isopen11", "" + isOpen[position]);
                    } else {
                        Log.e("isopen2", "" + isOpen[position]);
                        mIconIv.setImageResource(R.mipmap.detail_close);
                        mDepartmentDetailLv.setVisibility(View.GONE);
                        isOpen[position] = false;
                        Log.e("isopen22", "" + isOpen[position]);
                    }
                    if (isOpen[position]) {
                        mDepartmentDetailLv.setVisibility(View.VISIBLE);
                    }
                }
            });
            if (isOpen[position]) {
                mDepartmentDetailLv.setVisibility(View.VISIBLE);
                mIconIv.setImageResource(R.mipmap.detail_open);
            }
        } else if (mDate.get(position).getChildDepartmentList().size() > 0) {//如果有二级部门
            final ListView mDepartmentDetailLv = (ListView) view.findViewById(R.id.item_contacts_lv);
            final ListView mDepartmentPersonLv = (ListView) view.findViewById(R.id.item_contacts_person_lv);
            mDepartmentDetailLv.setAdapter(new DepartmentChildAdapter(mContext, mDate.get(position).getChildDepartmentList()));
            mDepartmentPersonLv.setAdapter(new DepartmentDetailAdapter2(mContext, mDate.get(position).getDetailList()));
            mPanelRly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isOpen[position]) {
                        Log.e("isopen1", "" + isOpen[position]);
                        mIconIv.setImageResource(R.mipmap.detail_open);
                        mDepartmentDetailLv.setVisibility(View.VISIBLE);
                        mDepartmentPersonLv.setVisibility(View.VISIBLE);
                        isOpen[position] = true;
                        Log.e("isopen11", "" + isOpen[position]);
                    } else {
                        Log.e("isopen2", "" + isOpen[position]);
                        mIconIv.setImageResource(R.mipmap.detail_close);
                        mDepartmentDetailLv.setVisibility(View.GONE);
                        mDepartmentPersonLv.setVisibility(View.GONE);
                        isOpen[position] = false;
                        Log.e("isopen22", "" + isOpen[position]);
                    }
                }
            });
            if (isOpen[position]) {
                mDepartmentDetailLv.setVisibility(View.VISIBLE);
                mDepartmentPersonLv.setVisibility(View.VISIBLE);
                mIconIv.setImageResource(R.mipmap.detail_open);
            }
        }

        mDepartmentNameTv.setText(mDate.get(position).getOrgname());
        mNumOfPersonTv.setText("");

        return view;
    }

}