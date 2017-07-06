package com.hnsi.zheng.hnti_erp_app.newcontacts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.activities.JingBanRenListActivity;
import com.hnsi.zheng.hnti_erp_app.activities.PersonInfoActivity;
import com.hnsi.zheng.hnti_erp_app.activities.SearchActivity;
import com.hnsi.zheng.hnti_erp_app.app.MyApplication;
import com.hnsi.zheng.hnti_erp_app.ebusbeans.JingBanRenInfo;
import com.hnsi.zheng.hnti_erp_app.utils.UserIconDisplayOptionsFactory;
import com.hnsi.zheng.hnti_erp_app.widgets.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Zheng on 2017/5/27.
 */

public class ChildViewHolder extends BaseViewHolder {

    private RelativeLayout cRly;//panel
    private TextView cTv;//simpleicon
    private CircleImageView cCiv;//icon
    private TextView cTv2;//name
    private TextView cTv3;//position

    public ChildViewHolder(View itemView) {
        super(itemView);
        cRly= (RelativeLayout) itemView.findViewById(R.id.item_department_rly);
        cTv= (TextView) itemView.findViewById(R.id.item_circle_textview);
        cCiv= (CircleImageView) itemView.findViewById(R.id.item_circle_imageview);
        cTv2= (TextView) itemView.findViewById(R.id.item_department_username);
        cTv3= (TextView) itemView.findViewById(R.id.item_department_userposition);
    }

    public void bindData(final ItemData itemData, final Context context){
        cCiv.setVisibility(View.GONE);
        String iconUrl=itemData.getIconUrl();
        if(!"null".equals(iconUrl)&&!"".equals(iconUrl)){
            ImageLoader.getInstance().displayImage(iconUrl,cCiv, UserIconDisplayOptionsFactory.getListOptions());
            cCiv.setVisibility(View.VISIBLE);
        }
        final String name=itemData.getText();
        cTv.setText(name.substring(name.length()-1));
        if("男".equals(itemData.getSex())){
            cTv.setBackgroundResource(R.drawable.circle_background_blue);
        }else if("女".equals(itemData.getSex())){
            cTv.setBackgroundResource(R.drawable.circle_background_pink);
        }
        cTv2.setText(name);
        cTv3.setText(itemData.getText2());
        cRly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if("com.hnsi.zheng.hnti_erp_app.activities.JingBanRenListActivity".equals(((Activity)context).getClass().getName())){
                    ((JingBanRenListActivity)context).setEmpid(itemData.getEmpid());
                    ((JingBanRenListActivity)context).setJingBanRenName(name);
                    ((JingBanRenListActivity)context).returnResult();
                    ((JingBanRenListActivity)context).finish();
                }else{
                    Intent intent=new Intent();
                    intent.setClass(context, PersonInfoActivity2.class);
                    intent.putExtra("Empid",itemData.getEmpid());
                    context.startActivity(intent);
                }
            }
        });

    }
}
