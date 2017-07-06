package com.hnsi.zheng.hnti_erp_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;

import java.util.ArrayList;

/**
 * TableView附表详情数据Adapter
 * Created by Zheng on 2016/6/28.
 */
public class TableViewDetailAdapter extends BaseAdapter{

    private Context mContext;
    private ArrayList<String> mKeyList;
    private ArrayList<String> mValueList;
    private LayoutInflater inflater;

    public TableViewDetailAdapter(Context context,ArrayList<String> keyList,ArrayList<String> valueList){
        mContext=context;
        mKeyList=keyList;
        mValueList=valueList;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mKeyList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=inflater.inflate(R.layout.item_base_text_textarea_date_int,parent,false);
        TextView mTitleTv= (TextView) view.findViewById(R.id.item_base_name);
        TextView mValueTv= (TextView) view.findViewById(R.id.item_base_value);

        mTitleTv.setText(mKeyList.get(position));

        if("null".equals(mValueList.get(position))){
            mValueTv.setText("无");
        }else{
            mValueTv.setText(mValueList.get(position));
        }

        return view;
    }
}
