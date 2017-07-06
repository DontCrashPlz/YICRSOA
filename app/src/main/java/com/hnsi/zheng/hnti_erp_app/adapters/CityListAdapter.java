package com.hnsi.zheng.hnti_erp_app.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.utils.SharedPrefUtils;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * 城市列表数据Adapter
 * Created by Zheng on 2016/8/31.
 */
public class CityListAdapter extends BaseAdapter{

    private Context mContext = null;

    private LayoutInflater inflater = null;

    private JSONArray mData;

    private String mProvince;

    public CityListAdapter(Context context, JSONArray data,String provinceStr) {
        this.mContext = context;
        this.inflater = LayoutInflater.from(mContext);
        mData=data;
        mProvince=provinceStr;
    }

    @Override
    public int getCount() {
        return mData.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return mData.getString(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView,ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=inflater.inflate(R.layout.item_citylist,parent,false);
            holder.mNameTv= (TextView) convertView.findViewById(R.id.item_citylist_name);
            convertView.setTag(holder);
        }
        holder= (ViewHolder) convertView.getTag();
        try {
            holder.mNameTv.setText(mData.getString(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.mNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("provnice", mProvince);
                SharedPrefUtils.put(mContext, "province", mProvince);
                try {
                    Log.e("city",mData.getString(position));
                    SharedPrefUtils.put(mContext,"city",mData.getString(position));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ((Activity)mContext).finish();
            }
        });
        return convertView;
    }



    class ViewHolder{
        TextView mNameTv;
    }
}
