package com.hnsi.zheng.hnti_erp_app.widgets;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.utils.DensityUtil;

import java.util.ArrayList;

/**
 * Created by Jim on 2015/2/3.
 * Updated by Zheng on 2016/11/1.
 * 适配数量显示.
 */
public class PromotedActionsLibrary2 {
    Context context;

    FrameLayout frameLayout;

    ImageButton mainImageButton;

    RotateAnimation rotateOpenAnimation;

    RotateAnimation rotateCloseAnimation;

    ArrayList<RelativeLayout> promotedActions;

//    ArrayList<TextView> mAddedTextViews;

    ObjectAnimator objectAnimator[];

    private int px;

    private static final int ANIMATION_TIME = 200;

    private boolean isMenuOpened;

    public void setup(Context activityContext, FrameLayout layout) {
        context = activityContext;
        promotedActions = new ArrayList<>();
//        mAddedTextViews = new ArrayList<>();
        frameLayout = layout;
        px = (int) context.getResources().getDimension(R.dimen.dim56dp) + 10;
        openRotation();
        closeRotation();
    }

    public void clearAllItem(){
        frameLayout.removeAllViews();
        mainImageButton=null;
        promotedActions.clear();
        isMenuOpened = false;
    }

    public void lastState(){
        if (isMenuOpened) {
            closePromotedActions().start();
            isMenuOpened = false;
        } else {
            isMenuOpened = true;
            openPromotedActions().start();
        }
    }

    public RelativeLayout addMainItem(Drawable drawable,int newMsgNum) {

        RelativeLayout relativeLayout= (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.main_promoted_action_relative,frameLayout,false);

        ImageButton button = (ImageButton) relativeLayout.findViewById(R.id.main_promoted_imagebutton);

        TextView textView= (TextView) relativeLayout.findViewById(R.id.main_promoted_textview);

        button.setImageDrawable(drawable);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isMenuOpened) {
                    closePromotedActions().start();
                    isMenuOpened = false;
                } else {
                    isMenuOpened = true;
                    openPromotedActions().start();
                }
            }
        });

        textView.setText("" + newMsgNum);

        if(newMsgNum<1){
            textView.setVisibility(View.INVISIBLE);
        }else if(newMsgNum>99){
            textView.setText("99+");
        }

        frameLayout.addView(relativeLayout);

        mainImageButton = button;

        return relativeLayout;
    }

    public RelativeLayout addMainItem(Drawable drawable,int back,int newMsgNum) {

        RelativeLayout relativeLayout= (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.main_promoted_action_relative,frameLayout,false);

        ImageButton button = (ImageButton) relativeLayout.findViewById(R.id.main_promoted_imagebutton);

        TextView textView= (TextView) relativeLayout.findViewById(R.id.main_promoted_textview);

        button.setImageDrawable(drawable);
        button.setBackgroundResource(back);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isMenuOpened) {
                    closePromotedActions().start();
                    isMenuOpened = false;
                } else {
                    isMenuOpened = true;
                    openPromotedActions().start();
                }
            }
        });

        textView.setText("" + newMsgNum);

        if(newMsgNum<1){
            textView.setVisibility(View.INVISIBLE);
        }else if(newMsgNum>99){
            textView.setText("99+");
        }

        frameLayout.addView(relativeLayout);

        mainImageButton = button;

        return relativeLayout;
    }

    public void addItem(Drawable drawable, int newMsgNum, View.OnClickListener onClickListener ) {

        RelativeLayout relativeLayout= (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.promoted_action_relative,frameLayout,false);

        ImageButton button = (ImageButton) relativeLayout.findViewById(R.id.promoted_imagebutton);

        TextView textView= (TextView) relativeLayout.findViewById(R.id.promoted_textview);

        button.setImageDrawable(drawable);

        button.setOnClickListener(onClickListener);

        textView.setText("" + newMsgNum);

        if(newMsgNum<1){
            textView.setVisibility(View.INVISIBLE);
        }else if(newMsgNum>99){
            textView.setText("99+");
        }

        promotedActions.add(relativeLayout);

        frameLayout.addView(relativeLayout);
        return;
    }

    public void addItem(Drawable drawable, int backimage , int newMsgNum, View.OnClickListener onClickListener ) {

        RelativeLayout relativeLayout= (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.promoted_action_relative,frameLayout,false);

        ImageButton button = (ImageButton) relativeLayout.findViewById(R.id.promoted_imagebutton);

        TextView textView= (TextView) relativeLayout.findViewById(R.id.promoted_textview);

        button.setImageDrawable(drawable);
        button.setBackgroundResource(backimage);

        button.setOnClickListener(onClickListener);

        textView.setText("" + newMsgNum);

        if(newMsgNum<1){
            textView.setVisibility(View.INVISIBLE);
        }else if(newMsgNum>99){
            textView.setText("99+");
        }

        promotedActions.add(relativeLayout);

        frameLayout.addView(relativeLayout);
        return;
    }


    /**
     * Set close animation for promoted actions
     */
    public AnimatorSet closePromotedActions() {

        if (objectAnimator == null){
            objectAnimatorSetup();
        }

        AnimatorSet animation = new AnimatorSet();

        for (int i = 0; i < promotedActions.size(); i++) {

            objectAnimator[i] = setCloseAnimation(promotedActions.get(i), i);
        }

        if (objectAnimator.length == 0) {
            objectAnimator = null;
        }

        animation.playTogether(objectAnimator);
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mainImageButton.startAnimation(rotateCloseAnimation);
                mainImageButton.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mainImageButton.setClickable(true);
                hidePromotedActions();
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                mainImageButton.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });

        return animation;
    }

    public AnimatorSet openPromotedActions() {

        if (objectAnimator == null){
            objectAnimatorSetup();
        }



        AnimatorSet animation = new AnimatorSet();

        for (int i = 0; i < promotedActions.size(); i++) {

            objectAnimator[i] = setOpenAnimation(promotedActions.get(i), i);
        }

        if (objectAnimator.length == 0) {
            objectAnimator = null;
        }

        animation.playTogether(objectAnimator);
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mainImageButton.startAnimation(rotateOpenAnimation);
                mainImageButton.setClickable(false);
                showPromotedActions();
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mainImageButton.setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                mainImageButton.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {}
        });


        return animation;
    }

    private void objectAnimatorSetup() {

        objectAnimator = new ObjectAnimator[promotedActions.size()];
    }


    /**
     * Set close animation for single button
     *
     * @param promotedAction
     * @param position
     * @return objectAnimator
     */
    private ObjectAnimator setCloseAnimation(RelativeLayout promotedAction, int position) {

        ObjectAnimator objectAnimator;

        if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            objectAnimator = ObjectAnimator.ofFloat(promotedAction, View.TRANSLATION_Y, -px * (promotedActions.size() - position), 0f);
            objectAnimator.setRepeatCount(0);
            objectAnimator.setDuration(ANIMATION_TIME * (promotedActions.size() - position));

        } else {

            objectAnimator = ObjectAnimator.ofFloat(promotedAction, View.TRANSLATION_X, -px * (promotedActions.size() - position), 0f);
            objectAnimator.setRepeatCount(0);
            objectAnimator.setDuration(ANIMATION_TIME * (promotedActions.size() - position));
        }

        return objectAnimator;
    }

    /**
     * Set open animation for single button
     *
     * @param promotedAction
     * @param position
     * @return objectAnimator
     */
    private ObjectAnimator setOpenAnimation(RelativeLayout promotedAction, int position) {

        ObjectAnimator objectAnimator;

        if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            objectAnimator = ObjectAnimator.ofFloat(promotedAction, View.TRANSLATION_Y, 0f, -px * (promotedActions.size() - position));
            objectAnimator.setRepeatCount(0);
            objectAnimator.setDuration(ANIMATION_TIME * (promotedActions.size() - position));

        } else {
            objectAnimator = ObjectAnimator.ofFloat(promotedAction, View.TRANSLATION_X, 0f, -px * (promotedActions.size() - position));
            objectAnimator.setRepeatCount(0);
            objectAnimator.setDuration(ANIMATION_TIME * (promotedActions.size() - position));
        }

        return objectAnimator;
    }

    private void hidePromotedActions() {

        for (int i = 0; i < promotedActions.size(); i++) {
            promotedActions.get(i).setVisibility(View.GONE);
        }
    }

    private void showPromotedActions() {

        for (int i = 0; i < promotedActions.size(); i++) {
            promotedActions.get(i).setVisibility(View.VISIBLE);
        }
    }

    /** 主按钮打开动画 */
    private void openRotation() {
        rotateOpenAnimation = new RotateAnimation(0, 45, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateOpenAnimation.setFillAfter(true);
        rotateOpenAnimation.setFillEnabled(true);
        rotateOpenAnimation.setDuration(ANIMATION_TIME);
    }

    /** 主按钮关闭动画 */
    private void closeRotation() {
        rotateCloseAnimation = new RotateAnimation(45, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateCloseAnimation.setFillAfter(true);
        rotateCloseAnimation.setFillEnabled(true);
        rotateCloseAnimation.setDuration(ANIMATION_TIME);
    }

}
