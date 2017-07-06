package com.hnsi.zheng.hnti_erp_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.activities.NoticeDetailActivity;
import com.hnsi.zheng.hnti_erp_app.beans.NewsEntity;
import com.hnsi.zheng.hnti_erp_app.widgets.NewsItemView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 公告数据Adapter
 * Created by Zheng on 2016/5/6.
 */
public class NoticeAdapter2 extends MyBaseAdapter{

    public NoticeAdapter2(Context context, List data) {
        super(context, data);
    }

    @Override
    public void addItems(List items) {
        mData.addAll(items);
        Collections.sort(mData, new Comparator<NewsEntity>() {
            @Override
            public int compare(NewsEntity lhs, NewsEntity rhs) {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date1 = null;
                Date date2 = null;
                try{
                    date1=format.parse(lhs.getStartDate().substring(0, 10));
                    date2=format.parse(rhs.getStartDate().substring(0, 10));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return (int) (date2.getTime()/100000-date1.getTime()/100000);
            }
        });
        notifyDataSetChanged();
    }

    @Override
    public View createView(LayoutInflater inflater, final int position, View convertView, ViewGroup parent) {
        NewsItemView newsItemView;
        if (convertView == null) {
            newsItemView = new NewsItemView(getContext());
            convertView = newsItemView;
        } else {
            newsItemView = (NewsItemView) convertView;
        }

        // 设置数据
        newsItemView.setData((NewsEntity) mData.get(position));

        return convertView;
    }

}
