package com.hnsi.zheng.hnti_erp_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.widgets.CircleImageView;

import java.util.List;

/**
 * 评论内容Adapter
 * Created by Zheng on 2016/5/11.
 */
public class CommentAdapter extends MyBaseAdapter{

    public CommentAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    public View createView(LayoutInflater inflater, int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_comment,parent,false);
            holder=new ViewHolder();
            holder.mUserIconCiv= (CircleImageView) convertView.findViewById(R.id.item_comment_civ_usericon);
            holder.mUserNameTv= (TextView) convertView.findViewById(R.id.item_comment_tv_name);
            holder.mTimeTv= (TextView) convertView.findViewById(R.id.item_comment_tv_time);
            holder.mCommentContentTv= (TextView) convertView.findViewById(R.id.item_comment_tv_content);
            convertView.setTag(holder);
        }
        holder= (ViewHolder) convertView.getTag();
//        holder.mUserIconCiv.setImageBitmap();
//        holder.mUserNameTv.setText("");
//        holder.mTimeTv.setText("");
//        holder.mCommentContentTv.setText("");
        return convertView;
    }

    class ViewHolder{
        CircleImageView mUserIconCiv;
        TextView mUserNameTv;
        TextView mTimeTv;
        TextView mCommentContentTv;
    }
}
