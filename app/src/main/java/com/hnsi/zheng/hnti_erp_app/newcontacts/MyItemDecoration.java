package com.hnsi.zheng.hnti_erp_app.newcontacts;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Simple ItemDecoration for list divide.
 * color #e6e6e6 → 230,230,230
 * Created by Zheng on 2017/6/16.
 */

public class MyItemDecoration extends RecyclerView.ItemDecoration {
    private Paint mPaint;

    public MyItemDecoration() {
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.rgb(230,230,230));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view)!=0){
            outRect.top=1;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount=parent.getChildCount();
        for (int i=0;i<childCount;i++){
            View view=parent.getChildAt(i);
            int position=parent.getChildAdapterPosition(view);
            if (position==0) continue;
            c.drawRect(parent.getPaddingLeft(),
                    view.getTop()-1,
                    parent.getWidth()-parent.getPaddingRight(),
                    view.getTop(),
                    mPaint);
        }
    }
}
