package com.hnsi.zheng.hnti_erp_app.widgets.pullListView;
/*
 * Copyright (C) 2013 www.418log.org
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hnsi.zheng.hnti_erp_app.R;

/**
 * The Class AbListViewHeader.
 */
public class ListViewHeader extends LinearLayout {

	/** The header view. */
	private LinearLayout headerView;

	/** The arrow image view. */
	private ImageView arrowImageView;

	/** The header progress bar. */
	private ProgressBar headerProgressBar;
	
	/** progressBar上的圆孔图片. */
	private ImageView progressBarMask;

	/** The tips textview. */
	private TextView tipsTextview;

	/** The header time view. */
	private TextView headerTimeView;

	/** The m state. */
	private int mState = -1;

	/** The m rotate up anim. */
	private Animation mRotateUpAnim;

	/** The m rotate down anim. */
	private Animation mRotateDownAnim;

	/** The rotate anim duration. */
	private final int ROTATE_ANIM_DURATION = 180;

	/** The Constant STATE_NORMAL. */
	public final static int STATE_NORMAL = 0;

	/** The Constant STATE_READY. */
	public final static int STATE_READY = 1;

	/** The Constant STATE_REFRESHING. */
	public final static int STATE_REFRESHING = 2;

	private String lastRefreshTime = null;

	/** The head content height. */
	private int headerHeight;

	/**
	 * Instantiates a new ab list view header.
	 * 
	 * @param context
	 *            the context
	 */
	public ListViewHeader(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * Instantiates a new ab list view header.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 */
	public ListViewHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	/**
	 * Inits the view.
	 * 
	 * @param context
	 *            the context
	 */
	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void initView(Context context) {

		headerView = new LinearLayout(context);
		headerView.setOrientation(LinearLayout.HORIZONTAL);
		// setBackgroundColor(Color.rgb(225, 225,225));
		headerView.setGravity(Gravity.CENTER);
		headerView.setPadding(0, 5, 0, 5);

		// 箭头、进度条----------begin
		FrameLayout headImage = new FrameLayout(context);
		arrowImageView = new ImageView(context);
		arrowImageView.setImageResource(R.mipmap.up_arrows);
		//if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
		Bitmap bitmap = ((BitmapDrawable) context.getResources().getDrawable(R.mipmap.up_arrows)).getBitmap();
		Matrix matrix = new Matrix(); 
		// 设置旋转角度  
        matrix.setRotate(180f);  
        // 重新绘制Bitmap  
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),bitmap.getHeight(), matrix, true);  
        arrowImageView.setImageBitmap(bitmap); 
		
		//arrowImageView.setRotation(180f);
		//}

		headerProgressBar = new ProgressBar(context, null,
				android.R.attr.progressBarStyleSmall);
		headerProgressBar.setIndeterminateDrawable(getResources().getDrawable(
				R.drawable.progressbar_circle));
		headerProgressBar.setVisibility(View.GONE);
		
		progressBarMask = new ImageView(context);
		progressBarMask.setImageResource(R.mipmap.updating2);
		progressBarMask.setVisibility(View.GONE);

		FrameLayout.LayoutParams layoutParamsWW = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParamsWW.gravity = Gravity.CENTER;
		layoutParamsWW.width = 36;
		layoutParamsWW.height = 36;
		headImage.addView(headerProgressBar, layoutParamsWW);
		//headImage.addView(progressBarMask, layoutParamsWW);
		FrameLayout.LayoutParams layoutParamsWW_arrow = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParamsWW_arrow.gravity = Gravity.CENTER;
		layoutParamsWW_arrow.width = 36;
		layoutParamsWW_arrow.height = 36;
		headImage.addView(arrowImageView, layoutParamsWW_arrow);
		// 箭头、进度条----------end

		// 文字提示---------begin
		LinearLayout headTextLayout = new LinearLayout(context);
		tipsTextview = new TextView(context);// 提示信息
		headerTimeView = new TextView(context);// 时间
//		headerTimeView.setVisibility(View.GONE);// 使时间不可见(董波)
		headTextLayout.setOrientation(LinearLayout.VERTICAL);
		headTextLayout.setGravity(Gravity.CENTER_VERTICAL);
		headTextLayout.setPadding(12, 0, 0, 0);
		LinearLayout.LayoutParams layoutParamsWW2 = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		headTextLayout.addView(tipsTextview, layoutParamsWW2);
		headTextLayout.addView(headerTimeView, layoutParamsWW2);
		tipsTextview.setTextColor(Color.rgb(0x7e, 0x7e, 0x7e));
		headerTimeView.setTextColor(Color.rgb(0x7e, 0x7e, 0x7e));
		tipsTextview.setTextSize(15);
		headerTimeView.setTextSize(14);

		LinearLayout.LayoutParams layoutParamsWW3 = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParamsWW3.gravity = Gravity.CENTER;
		layoutParamsWW3.bottomMargin = 5;
		layoutParamsWW3.topMargin = 5;

		LinearLayout headerLayout = new LinearLayout(context);
		headerLayout.setOrientation(LinearLayout.HORIZONTAL);
		headerLayout.setGravity(Gravity.CENTER);

		headerLayout.addView(headImage, layoutParamsWW3);
		headerLayout.addView(headTextLayout, layoutParamsWW3);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		lp.gravity = Gravity.BOTTOM;
		headerView.addView(headerLayout, lp);

		this.addView(headerView, lp);
		CommUtils.measureView(this);
		headerHeight = this.getMeasuredHeight();
		headerView.setPadding(0, -1 * headerHeight, 0, 0);

		mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateUpAnim.setFillAfter(true);
		mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateDownAnim.setFillAfter(true);

		setState(STATE_NORMAL);
	}

	/**
	 * Sets the state.
	 * 
	 * @param state
	 *            the new state
	 */
	public void setState(int state) {
		if (state == mState)
			return;

		if (state == STATE_REFRESHING) {
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.INVISIBLE);
			headerProgressBar.setVisibility(View.VISIBLE);
			progressBarMask.setVisibility(View.VISIBLE);
		} else {
			arrowImageView.setVisibility(View.VISIBLE);
			headerProgressBar.setVisibility(View.INVISIBLE);
			progressBarMask.setVisibility(View.INVISIBLE);
		}

		switch (state) {
		case STATE_NORMAL:
			if (mState == STATE_READY) {
				arrowImageView.startAnimation(mRotateDownAnim);
			}
			if (mState == STATE_REFRESHING) {
				arrowImageView.clearAnimation();
			}
			tipsTextview.setText("下拉刷新...");

			if (lastRefreshTime == null) {
				lastRefreshTime = CommUtils
						.getCurrentDate(CommUtils.dateFormatMDHM);
				headerTimeView.setText("上次刷新：" + lastRefreshTime);
			} else {
				headerTimeView.setText("上次刷新：" + lastRefreshTime);
			}

			break;
		case STATE_READY:
			if (mState != STATE_READY) {
				arrowImageView.clearAnimation();
				arrowImageView.startAnimation(mRotateUpAnim);
				tipsTextview.setText("松开立即刷新...");
				headerTimeView.setText("上次刷新：" + lastRefreshTime);
				lastRefreshTime = CommUtils
						.getCurrentDate(CommUtils.dateFormatMDHM);

			}
			break;
		case STATE_REFRESHING:
			tipsTextview.setText("加载中...");
			headerTimeView.setText("本次刷新：" + lastRefreshTime);
			break;
		default:
		}

		mState = state;
	}

	/**
	 * Sets the visiable height.
	 * 
	 * @param height
	 *            the new visiable height
	 */
	public void setVisiableHeight(int height) {
		if (height < 0)
			height = 0;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) headerView
				.getLayoutParams();
		lp.height = height;
		headerView.setLayoutParams(lp);
	}

	/**
	 * Gets the visiable height.
	 * 
	 * @return the visiable height
	 */
	public int getVisiableHeight() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) headerView
				.getLayoutParams();
		return lp.height;
	}

	public LinearLayout getHeaderView() {
		return headerView;
	}

	/**
	 * set last refresh time.
	 * 
	 * @param time
	 *            the new refresh time
	 */
	public void setRefreshTime(String time) {
		headerTimeView.setText(time);
	}

	/**
	 * Gets the header height.
	 * 
	 * @return the header height
	 */
	public int getHeaderHeight() {
		return headerHeight;
	}

	public void setTextColor(int color) {
		tipsTextview.setTextColor(color);
		headerTimeView.setTextColor(color);
	}

	public void setBackgroundColor(int color) {
		headerView.setBackgroundColor(color);
	}

	public ProgressBar getHeaderProgressBar() {
		return headerProgressBar;
	}

	public void setHeaderProgressBarDrawable(Drawable indeterminateDrawable) {
		headerProgressBar.setIndeterminateDrawable(indeterminateDrawable);
	}

}
