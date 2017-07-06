package com.hnsi.zheng.hnti_erp_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter的基类
 * Created by Zheng on 2016/5/6.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {

    protected Context mContext = null;

    private LayoutInflater inflater = null;

    protected List<T> mData = new ArrayList<T>();

    public MyBaseAdapter(Context context) {
        this(context, null);
    }

    public MyBaseAdapter(Context context, List<T> data) {
        this.mContext = context;
        this.inflater = LayoutInflater.from(mContext);
        if (data != null) {
            this.mData.addAll(data);
        }
    }

    public Context getContext(){
        return mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 添加一个数据
     * @param item
     */
    public void addItem(T item){
        mData.add(item);
        notifyDataSetChanged();
    }

    /**
     * 添加一组数据
     * @param items
     */
    public void addItems(List<T> items){
        mData.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * 添加一个数据到指定位置
     * @param item
     * @param position
     */
    public void addItem(T item, int position){
        if (position >= 0 && position < getCount()) {
            mData.add(position, item);
            notifyDataSetChanged();
        }
    }

    /**
     * 清空数据
     */
    public void clear(){
        mData.clear();
        notifyDataSetChanged();
    }

    /**
     * 移除一个数据
     * @param item
     */
    public void removeItem(T item){
        mData.remove(item);
        notifyDataSetChanged();
    }

    /**
     * 移除一个数据
     * @param position
     */
    public void removeItem(int position){
        if (position >= 0 && position < getCount()) {
            mData.remove(position);
            notifyDataSetChanged();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createView(inflater, position, convertView, parent);
    }

    public abstract View createView(LayoutInflater inflater,
                                    int position, View convertView, ViewGroup parent);

}
