package com.hnsi.zheng.hnti_erp_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.beans.CityListEntity;

import java.util.List;

/**
 * 省份列表数据Adapter
 * Created by Zheng on 2016/8/31.
 */
public class ProvinceListAdapter extends MyBaseAdapter{

    public ProvinceListAdapter(Context context, List date) {
        super(context, date);
    }

    @Override
    public View createView(LayoutInflater inflater, int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if(convertView==null){
                    holder=new ViewHolder();
                    convertView=inflater.inflate(R.layout.item_provincelist,parent,false);
                holder.mProvinceTv= (TextView) convertView.findViewById(R.id.item_provincelist_province);
                    holder.mCityListLv= (ListView) convertView.findViewById(R.id.item_provincelist_lv);
                    convertView.setTag(holder);
            }
            CityListEntity entity= (CityListEntity) mData.get(position);
            holder= (ViewHolder) convertView.getTag();
            holder.mProvinceTv.setText(entity.getProvince());
            holder.mCityListLv.setAdapter(new CityListAdapter(getContext(),entity.getCityListArray(),entity.getProvince()));

            return convertView;
    }


        class ViewHolder{
                TextView mProvinceTv;
                ListView mCityListLv;
        }
}
