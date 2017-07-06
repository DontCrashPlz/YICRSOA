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
 * 子部门列表数据Adapter
 * Created by Zheng on 2016/5/16.
 */
public class DepartmentChildAdapter extends MyBaseAdapter{
    private Context mContext;
    private ArrayList<DepartmentEntity>mDate=new ArrayList();

    /** 定义一个数组，用来保存每个条目的ListView的状态 */
    boolean[] isOpen;

    public DepartmentChildAdapter(Context context, ArrayList data) {
        super(context, data);
        mContext=context;
        mDate=data;
        isOpen=new boolean[data.size()];
        Log.e("isopen",isOpen.toString());
    }

    @Override
    public View createView(LayoutInflater inflater, int position, View convertView, ViewGroup parent) {
        View view=inflater.inflate(R.layout.item_child_department,parent,false);

        RelativeLayout mPanelRly= (RelativeLayout) view.findViewById(R.id.item_contacts_rly);
        ImageView mIconIv= (ImageView) view.findViewById(R.id.item_contacts_iv_icon);
        TextView mDepartmentNameTv= (TextView) view.findViewById(R.id.item_contacts_tv_department);
        TextView mNumOfPersonTv= (TextView) view.findViewById(R.id.item_contacts_tv_num_of_person);
        ListView mDepartmentDetailLv= (ListView) view.findViewById(R.id.item_contacts_lv);

        mDepartmentDetailLv.setAdapter(new DepartmentDetailAdapter(mContext, mDate.get(position).getDetailList()));

        mPanelRly.setOnClickListener(new MyOnClickListener(mDepartmentDetailLv, mIconIv, position));

        mDepartmentNameTv.setText(mDate.get(position).getOrgname());

        mNumOfPersonTv.setText(""+mDate.get(position).getDetailList().size());

//        mDepartmentDetailLv.setAdapter(new DepartmentDetailAdapter(mContext, mDate.get(position).getDetailList()));

        if(isOpen[position]) {
            mDepartmentDetailLv.setVisibility(View.VISIBLE);
            mIconIv.setImageResource(R.mipmap.arrow_open);
        }

        return view;
    }

    class MyOnClickListener implements View.OnClickListener{

        private ListView mListView;

        private ImageView mImageView;

        private int position;

        public MyOnClickListener(ListView lv,ImageView iv,int pos){
            mListView=lv;
            mImageView=iv;
            position=pos;
        }

        @Override
        public void onClick(View v) {
            if(!isOpen[position]){
                Log.e("isopen1",""+isOpen[position]);
                mImageView.setImageResource(R.mipmap.arrow_open);
                mListView.setVisibility(View.VISIBLE);
                isOpen[position]=true;
                Log.e("isopen11",""+isOpen[position]);
            }else{
                Log.e("isopen2",""+isOpen[position]);
                mImageView.setImageResource(R.mipmap.arrow_close);
                mListView.setVisibility(View.GONE);
                isOpen[position]=false;
                Log.e("isopen22",""+isOpen[position]);
            }
        }
    }

}

