package com.hnsi.zheng.hnti_erp_app.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.beans.DepartmentEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门列表数据Adapter(已弃用，改为使用DepartmentAdapter2)
 * Created by Zheng on 2016/5/16.
 */
@Deprecated
public class DepartmentAdapter extends MyBaseAdapter{
    private Context mContext;
    private ArrayList<DepartmentEntity>mDate=new ArrayList();

    /** 定义一个数组，用来保存每个条目的ListView的状态 */
    boolean[] isOpen;

    public DepartmentAdapter(Context context, ArrayList data) {
        super(context, data);
        mContext=context;
        mDate=data;
        isOpen=new boolean[data.size()];
        Log.e("isopen",isOpen.toString());
    }

    @Override
    public View createView(LayoutInflater inflater, int position, View convertView, ViewGroup parent) {
        View view=inflater.inflate(R.layout.item_contacts_department,parent,false);

        RelativeLayout mPanelRly= (RelativeLayout) view.findViewById(R.id.item_contacts_rly);
        ImageView mIconIv= (ImageView) view.findViewById(R.id.item_contacts_iv_icon);
        TextView mDepartmentNameTv= (TextView) view.findViewById(R.id.item_contacts_tv_department);
        TextView mNumOfPersonTv= (TextView) view.findViewById(R.id.item_contacts_tv_num_of_person);
        ListView mDepartmentDetailLv= (ListView) view.findViewById(R.id.item_contacts_lv);
        ListView mDepartmentPersonLv= (ListView) view.findViewById(R.id.item_contacts_person_lv);

        /** 判断一级部门下是否有二级部门，如果有，展开二级部门列表，如果没有，直接显示该部门下的人员信息 */
        if(mDate.get(position).getChildDepartmentList().size()==0){//该一级部门下没有二级部门
            mDepartmentDetailLv.setAdapter(new DepartmentDetailAdapter(mContext, mDate.get(position).getDetailList()));
            mDepartmentPersonLv.setAdapter(new DepartmentDetailAdapter(mContext,new ArrayList()));
        } else {//该一级部门下面有二级部门
            mDepartmentDetailLv.setAdapter(new DepartmentChildAdapter(mContext, mDate.get(position).getChildDepartmentList()));
            if(!(mDate.get(position).getDetailList().size()==0)){
                mDepartmentPersonLv.setAdapter(new DepartmentDetailAdapter(mContext,mDate.get(position).getDetailList()));
            }
        }

        mPanelRly.setOnClickListener(new MyOnClickListener(mDepartmentDetailLv,mDepartmentPersonLv, mIconIv, position));

        mDepartmentNameTv.setText(mDate.get(position).getOrgname());

//        mNumOfPersonTv.setText(""+mDate.get(position).getDetailList().size());
        mNumOfPersonTv.setText("");

//        mDepartmentDetailLv.setAdapter(new DepartmentDetailAdapter(mContext, mDate.get(position).getDetailList()));

        if(isOpen[position]) {
            mDepartmentDetailLv.setVisibility(View.VISIBLE);
        }

        return view;
    }

    class MyOnClickListener implements View.OnClickListener{
        /** item下的第一个ListView */
        private ListView mFirstListView;
        /** item下的第二个ListView */
        private ListView mSecondListView;

        private ImageView mImageView;

        private int position;

        public MyOnClickListener(ListView flv,ListView slv,ImageView iv,int pos){
            mFirstListView=flv;
            mSecondListView=slv;
            mImageView=iv;
            position=pos;
        }

        @Override
        public void onClick(View v) {
            if(!isOpen[position]){
                Log.e("isopen1",""+isOpen[position]);
                mImageView.setImageResource(R.mipmap.detail_open);
                mFirstListView.setVisibility(View.VISIBLE);
                mSecondListView.setVisibility(View.VISIBLE);
                isOpen[position]=true;
                Log.e("isopen11",""+isOpen[position]);
            }else{
                Log.e("isopen2",""+isOpen[position]);
                mImageView.setImageResource(R.mipmap.detail_close);
                mFirstListView.setVisibility(View.GONE);
                mSecondListView.setVisibility(View.GONE);
                isOpen[position]=false;
                Log.e("isopen22",""+isOpen[position]);
            }
        }
    }

}
