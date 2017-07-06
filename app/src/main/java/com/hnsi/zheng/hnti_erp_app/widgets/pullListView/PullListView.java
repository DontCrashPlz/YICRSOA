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


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Scroller;

/**
 * The Class AbPullListView.
 */
@SuppressLint("ClickableViewAccessibility")
public class PullListView extends ListView implements OnScrollListener {

	/** The m last y. */
	private float mLastY = -1;

	/** The m scroller. */
	private Scroller mScroller;

	/** The m list view listener. */
	private OnListViewListener mListViewListener;

	/** The m header view. */
	private ListViewHeader mHeaderView;

	/** The m footer view. */
	private ListViewFooter mFooterView;

	/** The m header view height. */
	private int mHeaderViewHeight;

	/** The m footer view height. */
	private int mFooterViewHeight;

	/** The m enable pull refresh. */
	private boolean mEnablePullRefresh = true;

	/** The m enable pull load. */
	private boolean mEnablePullLoad = false;

	/** The m pull refreshing. */
	private boolean mPullRefreshing = false;

	/** The m pull loading. */
	private boolean mPullLoading;

	/** The m is footer ready. */
	private boolean mIsFooterReady = false;

	private int mTotalItemCount;

	/** The m scroll back. */
	private int mScrollBack;

	/** The Constant SCROLLBACK_HEADER. */
	private final static int SCROLLBACK_HEADER = 0;

	/** The Constant SCROLLBACK_FOOTER. */
	private final static int SCROLLBACK_FOOTER = 1;

	/** The Constant SCROLL_DURATION. */
	private final static int SCROLL_DURATION = 300;

	/** The Constant OFFSET_RADIO. */
	private final static float OFFSET_RADIO = 1.8f;

	private ListAdapter mAdapter = null;

	private int count = 0;

	/**
	 * 
	 * @param context
	 *            the context
	 * 
	 */

	public PullListView(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 */
	public PullListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public PullListView(Context context, AttributeSet attrs, int defStyle) {
		this(context, attrs);
	}

	private void initView(Context context) {

		mScroller = new Scroller(context, new DecelerateInterpolator());

		super.setOnScrollListener(this);

		// init header view
		mHeaderView = new ListViewHeader(context);

		// init header height
		mHeaderViewHeight = mHeaderView.getHeaderHeight();
		mHeaderView.setGravity(Gravity.BOTTOM);
		// float
		// paddingLeft=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
		// 52, context.getResources().getDisplayMetrics());
		mHeaderView.setPadding(0, 0, 0, 0);
		addHeaderView(mHeaderView);

		// init footer view
		mFooterView = new ListViewFooter(context);
		mFooterView.setPadding(0, 0, 0, 0);
		mFooterViewHeight = mFooterView.getFooterHeight();

		setPullRefreshEnable(true);
		setPullLoadEnable(true);
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		mAdapter = adapter;
		if (mIsFooterReady == false) {
			mIsFooterReady = true;
			mFooterView.setGravity(Gravity.TOP);
			addFooterView(mFooterView);
		}
		super.setAdapter(adapter);
	}

	public void setPullRefreshEnable(boolean enable) {
		mEnablePullRefresh = enable;
		if (!mEnablePullRefresh) {
			mHeaderView.setVisibility(View.INVISIBLE);
		} else {
			mHeaderView.setVisibility(View.VISIBLE);
		}
	}

	public void setPullLoadEnable(boolean enable) {
		mEnablePullLoad = enable;
		if (!mEnablePullLoad) {
			mFooterView.hide();
			mFooterView.setOnClickListener(null);
		} else {
			mPullLoading = false;
			mFooterView.setState(ListViewFooter.STATE_READY);
			mFooterView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startLoadMore();
				}
			});
		}
	}

	public void stopRefresh() {

		if (mPullRefreshing == true) {
			mPullRefreshing = false;
			resetHeaderHeight();
		}
		if (mAdapter != null) {
			count = mAdapter.getCount();
		}
		if (count > 0) {
			mFooterView.setState(ListViewFooter.STATE_READY);
		} else {
			mFooterView.setState(ListViewFooter.STATE_EMPTY);
		}
	}

	private void updateFooterHeight(float delta) {

		int newHeight = (int) delta + mFooterView.getVisiableHeight();
		mFooterView.setVisiableHeight(newHeight);

		if (mEnablePullLoad && !mPullLoading) {

			if (mFooterView.getVisiableHeight() >= mFooterViewHeight) {
				mFooterView.setState(ListViewHeader.STATE_READY);
			} else {
				mFooterView.setState(ListViewHeader.STATE_NORMAL);
			}
		}
		setSelection(mTotalItemCount - 1);
	}

	private void resetFooterHeight() {
		int height = mFooterView.getVisiableHeight();

		if (height < mFooterViewHeight || !mPullLoading) {
			mScrollBack = SCROLLBACK_FOOTER;
			mScroller.startScroll(0, height, 0, -1 * height, SCROLL_DURATION);
		} else if (height > mHeaderViewHeight || !mPullLoading) {
			mScrollBack = SCROLLBACK_FOOTER;
			mScroller.startScroll(0, height, 0, -(height - mFooterViewHeight),
					SCROLL_DURATION);
		}

		invalidate();
	}

	private void updateHeaderHeight(float delta) {

		int newHeight = (int) delta + mHeaderView.getVisiableHeight();
		mHeaderView.setVisiableHeight(newHeight);

		if (mEnablePullRefresh && !mPullRefreshing) {
			if (mHeaderView.getVisiableHeight() >= mHeaderViewHeight) {
				mHeaderView.setState(ListViewHeader.STATE_READY);
			} else {
				mHeaderView.setState(ListViewHeader.STATE_NORMAL);
			}
		}
		setSelection(0);
	}

	private void resetHeaderHeight() {
		int height = mHeaderView.getVisiableHeight();
		if (height < mHeaderViewHeight || !mPullRefreshing) {
			mScrollBack = SCROLLBACK_HEADER;
			mScroller.startScroll(0, height, 0, -1 * height, SCROLL_DURATION);
		} else if (height > mHeaderViewHeight || !mPullRefreshing) {
			mScrollBack = SCROLLBACK_HEADER;
			mScroller.startScroll(0, height, 0, -(height - mHeaderViewHeight),
					SCROLL_DURATION);
		}
		invalidate();
	}

	private void startLoadMore() {

		mPullLoading = true;

		mFooterView.setState(ListViewFooter.STATE_LOADING);

		if (mListViewListener != null) {
			mListViewListener.onLoadMore();
		}
	}

	public void stopLoadMore() {

		mFooterView.hide();

		if (mPullLoading == true) {
			mPullLoading = false;
			resetFooterHeight();
		}
	}

	/**
	 * @author dongbo 2014-6-23
	 */
	public interface MyTouchListener {
		void onTouchDown();

		void onTouchUp();
	}

	MyTouchListener myTouchListener;

	public MyTouchListener getMyTouchListener() {
		return myTouchListener;
	}

	public void setMyTouchListener(MyTouchListener myTouchListener) {
		this.myTouchListener = myTouchListener;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (mLastY == -1) {
			mLastY = ev.getRawY();
		}

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLastY = ev.getRawY();
			// add ����
			if (myTouchListener != null) {
				myTouchListener.onTouchDown();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			final float deltaY = ev.getRawY() - mLastY;
			mLastY = ev.getRawY();

			if (mEnablePullRefresh && getFirstVisiblePosition() == 0
					&& (mHeaderView.getVisiableHeight() > 0 || deltaY > 0)) {
				updateHeaderHeight(deltaY / OFFSET_RADIO);
			} else if (mEnablePullLoad && !mPullLoading
					&& getLastVisiblePosition() == mTotalItemCount - 1
					&& deltaY < 0) {

				if (mFooterView.getfooterView().getVisibility() != View.VISIBLE) {
					mFooterView.show();
				}

				updateFooterHeight(-deltaY / OFFSET_RADIO);
			}
			break;
		case MotionEvent.ACTION_UP:
			mLastY = -1;
			if (getFirstVisiblePosition() == 0) {
				if (myTouchListener != null) {
					myTouchListener.onTouchUp();
				}
				if (mEnablePullRefresh
						&& mHeaderView.getVisiableHeight() >= mHeaderViewHeight) {
					mPullRefreshing = true;
					mHeaderView.setState(ListViewHeader.STATE_REFRESHING);
					if (mListViewListener != null) {
						mListViewListener.onRefresh();
					}
				}

				if (mEnablePullRefresh) {
					resetHeaderHeight();
				}
			}

			if (getLastVisiblePosition() == mTotalItemCount - 1) {
				if (mEnablePullLoad
						&& mFooterView.getVisiableHeight() >= mFooterViewHeight) {
					mPullLoading = true;

					mFooterView.setState(ListViewFooter.STATE_LOADING);

					if (mListViewListener != null) {
						mListViewListener.onLoadMore();
					}
				}

				if (mEnablePullLoad) {
					resetFooterHeight();
				}
			}

			break;
		default:
			break;
		}
		return super.onTouchEvent(ev);
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			if (mScrollBack == SCROLLBACK_HEADER) {
				mHeaderView.setVisiableHeight(mScroller.getCurrY());
			}
			if (mScrollBack == SCROLLBACK_FOOTER) {
				if (mFooterView.getVisibility() != VISIBLE) {
					mFooterView.setVisibility(View.VISIBLE);
				}
				mFooterView.setVisiableHeight(mScroller.getCurrY());
			}

			postInvalidate();
		}
		super.computeScroll();
	}

	public void setOnListViewListener(OnListViewListener listViewListener) {
		mListViewListener = listViewListener;
	}

	public int scrolledX;
	public int scrolledY;

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if(scrollStateChangedListener!=null){
			scrollStateChangedListener.onScrollStateChanged(view, scrollState);
		}
		// ������ʱ���浱ǰ��������λ��
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			scrolledX = this.getScrollX();
			scrolledY = this.getScrollY();

			if (this.sCrollIdle == null)
				return;
			this.sCrollIdle.onScrollIdle(view);
		}
	}

	/***************** 向外部提供滑动状态 @dongbo ******************/
	public interface ScrollListener {
		void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount);
	}

	public interface ScrollIdle {
		void onScrollIdle(AbsListView view);
	}

	ScrollListener scrollListener;
	ScrollIdle sCrollIdle;

	public ScrollListener getScrollListener() {
		return scrollListener;
	}

	public void setScrollListener(ScrollListener scrollListener) {
		this.scrollListener = scrollListener;
	}

	public void setScrollIdle(ScrollIdle sCrollIdle) {
		this.sCrollIdle = sCrollIdle;
	}
	/**
	 * 滑动状态监听
	 * @author dongbo
	 *
	 */
	private ScrollStateChangedListener scrollStateChangedListener;
	public interface ScrollStateChangedListener {
		void onScrollStateChanged(AbsListView view, int scrollState);
	}

	public void setScrollStateChangedListener(
			ScrollStateChangedListener scrollStateChangedListener) {
		this.scrollStateChangedListener = scrollStateChangedListener;
	}
	
	/**
	 * Glide中图片预加载的滑动监听
	 */
//	private ListPreloader<String> preloader;
//
//	public void setPreloader(ListPreloader<String> preloader) {
//		this.preloader = preloader;
//	}

	/**************************************************************/

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		mTotalItemCount = totalItemCount;
		if (scrollListener != null) {
			scrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
					totalItemCount);
		}
//		if(preloader!=null){
//			preloader.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
//		}
	}

	public ListViewHeader getHeaderView() {
		return mHeaderView;
	}

	public ListViewFooter getFooterView() {
		return mFooterView;
	}

	public ProgressBar getHeaderProgressBar() {
		return mHeaderView.getHeaderProgressBar();
	}

	public ProgressBar getFooterProgressBar() {
		return mFooterView.getFooterProgressBar();
	}

	// 回滚到顶部重新刷新
	public void refresh() {
		updateHeaderHeight(4 * mHeaderViewHeight / OFFSET_RADIO);
		if (mEnablePullRefresh
				&& mHeaderView.getVisiableHeight() >= mHeaderViewHeight) {
			mPullRefreshing = true;
			mHeaderView.setState(ListViewHeader.STATE_REFRESHING);
			if (mListViewListener != null) {
				// 刷新
				mListViewListener.onRefresh();
			}
		}
		if (mEnablePullRefresh) {
			// 弹回
			resetHeaderHeight();
		}

	}
}
