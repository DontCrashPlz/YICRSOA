package com.hnsi.zheng.hnti_erp_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.activities.TableViewActivity;
import com.hnsi.zheng.hnti_erp_app.app.AppConstants;
import com.hnsi.zheng.hnti_erp_app.shareclass.LinkedHashMap2TableViewActivity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 审批详情页中TableView数据的adapter
 * Created by Zheng on 2016/6/28.
 */
public class TableViewAdapter extends BaseAdapter{

    private Context mContext;
    private List mData;
    private String mTitle;
    private LayoutInflater inflater;

    public TableViewAdapter(Context context,List data,String title){
        mContext=context;
        mData=data;
        mTitle=title;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView( final int position, View convertView, ViewGroup parent) {
        View view=inflater.inflate(R.layout.item_tableview,parent,false);

        TextView mNameTv= (TextView) view.findViewById(R.id.item_tableview_name);
        mNameTv.setText(mTitle+"(第"+(position+1)+"张)");
        RelativeLayout mPanelRly= (RelativeLayout) view.findViewById(R.id.item_rly_tableview_panel);
        mPanelRly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                LinkedHashMap2TableViewActivity.getInstance().put((LinkedHashMap) mData.get(position));
                intent.putExtra(AppConstants.MATTER_TABLEVIEW_TITLE,mTitle);
                intent.setClass(mContext, TableViewActivity.class);
                mContext.startActivity(intent);
            }
        });

        return view;
    }
}
