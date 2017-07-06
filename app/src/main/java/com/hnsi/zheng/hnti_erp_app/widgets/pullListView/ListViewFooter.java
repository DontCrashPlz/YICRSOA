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


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
public class ListViewFooter extends LinearLayout {
	/** The header view. */
	private LinearLayout footerView;

	/** The arrow image view. */
	private ImageView arrowImageView;

	/** The header progress bar. */
	private ProgressBar footerProgressBar;
	
	/** progressBar上的圆孔图片. */
	private ImageView progressBarMask;

	/** The tips textview. */
	private TextView tipsTextview;

	/** The header time view. */
	private TextView footerTimeView;

	/** The m state. */
	private int mState = -1;

	/** The m rotate up anim. */
	private Animation mRotateUpAnim;

	/** The m rotate down anim. */
	private Animation mRotateDownAnim;

	/** The rotate anim duration. */
	private final int ROTATE_ANIM_DURATION = 300;

	/** The Constant STATE_NORMAL. */
	public final static int STATE_NORMAL = 0;

	/** The Constant STATE_READY. */
	public final static int STATE_READY = 1;

	/** The Constant STATE_REFRESHING. */
	public final static int STATE_REFRESHING = 2;

	public static final int STATE_EMPTY = 3;

	public static final int STATE_LOADING = 4;

	public static final int STATE_NO = 5;

	/** 保存上一次的加载数据时间. */
	private String lastRefreshTime = null;

	/** The head content height. */
	private int footerHeight;

	/**
	 * Instantiates a new ab list view header.
	 * 
	 * @param context
	 *            the context
	 */
	public ListViewFooter(Context context) {
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
	public ListViewFooter(Context context, AttributeSet attrs) {
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
	private void initView(Context context) {
		// 底部刷新栏整体内容
		footerView = new LinearLayout(context);
		footerView.setOrientation(LinearLayout.HORIZONTAL);
		footerView.setGravity(Gravity.CENTER);
		footerView.setPadding(0, 5, 0, 5);

		// 显示箭头与进度
		FrameLayout footerImage = new FrameLayout(context);
		arrowImageView = new ImageView(context);
		// 从包里获取的箭头图片
//		arrowImage = CommUtils.getBitmapFormSrc("image/arrow_footer.png");
		arrowImageView.setImageResource(R.mipmap.up_arrows);

		// style="?android:attr/progressBarStyleSmall"默认的样式
		footerProgressBar = new ProgressBar(context, null,
				android.R.attr.progressBarStyleSmall);
		footerProgressBar.setIndeterminateDrawable(getResources().getDrawable(
				R.drawable.progressbar_circle));
		footerProgressBar.setVisibility(View.GONE);
		
		progressBarMask = new ImageView(context);
		progressBarMask.setImageResource(R.mipmap.updating2);
		progressBarMask.setVisibility(View.GONE);

		FrameLayout.LayoutParams layoutParamsWW = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParamsWW.gravity = Gravity.CENTER;
		layoutParamsWW.width = 36;
		layoutParamsWW.height = 36;
		footerImage.addView(footerProgressBar, layoutParamsWW);
		//footerImage.addView(progressBarMask, layoutParamsWW);
		FrameLayout.LayoutParams layoutParamsWW_arrow = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParamsWW_arrow.gravity = Gravity.CENTER;
		layoutParamsWW_arrow.width = 36;
		layoutParamsWW_arrow.height = 36;
		footerImage.addView(arrowImageView, layoutParamsWW_arrow);

		// 底部加载文本内容
		LinearLayout headTextLayout = new LinearLayout(context);
		tipsTextview = new TextView(context);
		footerTimeView = new TextView(context);
//		footerTimeView.setVisibility(View.GONE);//不显示刷新时间
		headTextLayout.setOrientation(LinearLayout.VERTICAL);
		headTextLayout.setGravity(Gravity.CENTER_VERTICAL);
		headTextLayout.setPadding(12, 0, 0, 0);
		LinearLayout.LayoutParams layoutParamsWW2 = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		headTextLayout.addView(tipsTextview, layoutParamsWW2);
		headTextLayout.addView(footerTimeView, layoutParamsWW2);

		tipsTextview.setTextColor(Color.rgb(0x7e, 0x7e, 0x7e));
		footerTimeView.setTextColor(Color.rgb(0x7e, 0x7e, 0x7e));
		tipsTextview.setTextSize(15);
		footerTimeView.setTextSize(14);

		LinearLayout.LayoutParams layoutParamsWW3 = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParamsWW3.gravity = Gravity.CENTER;
		layoutParamsWW3.bottomMargin = 5;
		layoutParamsWW3.topMargin = 5;

		LinearLayout footerLayout = new LinearLayout(context);
		footerLayout.setOrientation(LinearLayout.HORIZONTAL);
		footerLayout.setGravity(Gravity.CENTER);

		footerLayout.addView(footerImage, layoutParamsWW3);
		footerLayout.addView(headTextLayout, layoutParamsWW3);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		lp.gravity = Gravity.BOTTOM;

		// 添加大布局
		footerView.addView(footerLayout, lp);

		this.addView(footerView, lp);
		// 获取View的高度
		//CommUtils.measureView(this);
		footerHeight = this.getMeasuredHeight();
		// 向上偏移隐藏起来
		footerView.setPadding(0, -1 * footerHeight, 0, 0);

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

		if (state == mState) {
			return;
		}
		if (state == STATE_LOADING) {
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.INVISIBLE);
			footerProgressBar.setVisibility(View.VISIBLE);
			progressBarMask.setVisibility(View.VISIBLE);
		} else {
			arrowImageView.setVisibility(View.VISIBLE);
			footerProgressBar.setVisibility(View.INVISIBLE);
			progressBarMask.setVisibility(View.INVISIBLE);
		}

		switch (state) {

		case STATE_NORMAL:

			if (mState == STATE_READY) {
				arrowImageView.startAnimation(mRotateDownAnim); // mRotateDownAnim
			}

			if (mState == STATE_LOADING) {
				arrowImageView.clearAnimation();
			}

			tipsTextview.setText("上拉加载更多...");
			
			if (lastRefreshTime == null) {
				lastRefreshTime = CommUtils
						.getCurrentDate(CommUtils.dateFormatMDHM);
				footerTimeView.setText("加载：" + lastRefreshTime);
			} else {
				footerTimeView.setText("上次加载：" + lastRefreshTime);
			}

			break;
		case STATE_READY:
			if (mState != STATE_READY) {
				arrowImageView.clearAnimation();
				arrowImageView.startAnimation(mRotateUpAnim);// mRotateUpAnim
				tipsTextview.setText("松开立即加载...");
				footerTimeView.setText("上次加载：" + lastRefreshTime);
				lastRefreshTime = CommUtils
						.getCurrentDate(CommUtils.dateFormatMDHM);
			}
			break;
		case STATE_LOADING:

			tipsTextview.setText("加载中...");
			footerTimeView.setText("本次加载：" + lastRefreshTime);
			
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
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) footerView
				.getLayoutParams();
		lp.height = height;
		footerView.setLayoutParams(lp);
	}

	/**
	 * Gets the visiable height.
	 * 
	 * @return the visiable height
	 */
	public int getVisiableHeight() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) footerView
				.getLayoutParams();
		return lp.height;
	}

	/**
	 * 描述：获取footerView.
	 * 
	 * @return the header view
	 */
	public LinearLayout getfooterView() {
		return footerView;
	}

	/**
	 * set last refresh time.
	 * 
	 * @param time
	 *            the new refresh time
	 */
	public void setRefreshTime(String time) {
		footerTimeView.setText(time);
	}

	/**
	 * Gets the header height.
	 * 
	 * @return the header height
	 */
	public int getFooterHeight() {
		return footerHeight;
	}

	/**
	 * 
	 *描述：设置字体颜色
	 * 
	 * @param color
	 * @throws
	 */
	public void setTextColor(int color) {
		tipsTextview.setTextColor(color);
		footerTimeView.setTextColor(color);
	}

	/**
	 * 
	 * 描述：设置背景颜色
	 * 
	 * @param color
	 * @throws
	 */
	public void setBackgroundColor(int color) {
		footerView.setBackgroundColor(color);
	}

	/**
	 * 
	 * 描述：获取Header ProgressBar，用于设置自定义样式
	 * 
	 * @return
	 * @throws
	 */
	public ProgressBar getFooterProgressBar() {
		return footerProgressBar;
	}

	/**
	 * 
	 * 描述：设置Header ProgressBar样式
	 * 
	 * @return
	 * @throws
	 */
	public void setFooterProgressBarDrawable(Drawable indeterminateDrawable) {
		footerProgressBar.setIndeterminateDrawable(indeterminateDrawable);
	}

	public void show() {
		footerView.setVisibility(View.VISIBLE);
	}

	public void hide() {
		footerView.setVisibility(View.GONE);
	}
}
