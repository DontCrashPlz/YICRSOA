package com.hnsi.zheng.hnti_erp_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.beans.MatterHistoryEntity;

import java.util.ArrayList;

/**
 * 审批记录列表数据Adapter
 * Created by Zheng on 2016/7/4.
 */
public class MatterHistoryListAdapter extends BaseAdapter{

    private Context mContext;
    private ArrayList<MatterHistoryEntity>mData;
    private LayoutInflater inflater;

    public MatterHistoryListAdapter(Context context,ArrayList<MatterHistoryEntity>data) {
        mContext=context;
        mData=data;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        MatterHistoryEntity entity=mData.get(position);
        String decision=entity.getDecision();
        View mView;
        if("0".equals(decision)){
            mView=inflater.inflate(R.layout.item_approval_history_decision0,parent,false);
        }else if("2".equals(decision)){
            mView=inflater.inflate(R.layout.item_approval_history_decision2,parent,false);
        }else if("3".equals(decision)){
            mView=inflater.inflate(R.layout.item_approval_history_decision3,parent,false);
        }else {
            mView=inflater.inflate(R.layout.item_approval_history_decision1,parent,false);
        }
        TextView mContentTv= (TextView) mView.findViewById(R.id.item_history_content);
        if("null".equals(entity.getContent())
                ||"".equals(entity.getContent())){
            mContentTv.setText("无处理意见");
        }else{
            mContentTv.setText(entity.getContent());
        }

        TextView mNameTv= (TextView) mView.findViewById(R.id.item_history_name);
        mNameTv.setText(entity.getName());

        TextView mDepartmentTv= (TextView) mView.findViewById(R.id.item_history_department);
        mDepartmentTv.setText(entity.getActivityName());

        TextView mEndTimeTv= (TextView) mView.findViewById(R.id.item_history_starttime);
        String endTime=entity.getEndTime().substring(0,19);
        mEndTimeTv.setText(endTime);

        return mView;
    }
}
