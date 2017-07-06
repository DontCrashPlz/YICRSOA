package com.hnsi.zheng.hnti_erp_app.newcontacts;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;

/**
 * Created by Zheng on 2017/5/27.
 */

public class ParentViewHolder extends BaseViewHolder {

    private RelativeLayout pRly;//panel
    private TextView pTv;//department
    private ImageView pIv;//icon
    private TextView pTv2;//num

    public ParentViewHolder(View itemView) {
        super(itemView);
        pRly= (RelativeLayout) itemView.findViewById(R.id.item_contacts_rly);
        pTv= (TextView) itemView.findViewById(R.id.item_contacts_tv_department);
        pIv= (ImageView) itemView.findViewById(R.id.item_contacts_iv_icon);
        pTv2= (TextView) itemView.findViewById(R.id.item_contacts_tv_num_of_person);
    }

    public void bindData(final ItemData itemData, final int position, final ItemDataClickListener listener){
        pTv.setText(itemData.getText());
        pTv2.setText(itemData.getText2());
        if(itemData.isExpand()){
            pIv.setRotation(90);
        }else{
            pIv.setRotation(0);
        }
        pRly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    if(itemData.isExpand()){
                        listener.onHideChildren(position);
                        itemData.setExpand(false);
                        rotationExpandIcon(90,0);
                    }else{
                        listener.onExpandChildren(position);
                        itemData.setExpand(true);
                        rotationExpandIcon(0,90);
                    }
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void rotationExpandIcon(float from, float to) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
            valueAnimator.setDuration(150);
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    pIv.setRotation((Float) valueAnimator.getAnimatedValue());
                }
            });
            valueAnimator.start();
        }
    }

}
