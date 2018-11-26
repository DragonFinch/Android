package com.iyoyogo.android.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.iyoyogo.android.utils.DensityUtil;

/**
 * 创建时间：2018/6/23
 * 描述：
 */
public class AttentionGridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDividerDrawable;
    private int mDividerHeight = 1;//分割线的高度，默认为1
    private Paint mDividerPaint;//分割线的颜色

    /**
     * 使用自定义资源文件
     *
     * @param context
     * @param drawableId
     */
    public AttentionGridSpacingItemDecoration(Context context, int drawableId) {
        this.mDividerDrawable = ContextCompat.getDrawable(context, drawableId);
        mDividerHeight = mDividerDrawable.getIntrinsicHeight();
    }

    /**
     * 使用画笔画出分割线
     *
     * @param context
     * @param dividerHeight
     * @param dividerColor
     */
    public AttentionGridSpacingItemDecoration(Context context, int dividerHeight, int dividerColor) {
        this.mDividerHeight = dividerHeight;
        mDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDividerPaint.setColor(dividerColor);
        mDividerPaint.setStyle(Paint.Style.FILL);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State
            state) {
        super.getItemOffsets(outRect, view, parent, state);
        int totalCount = parent.getAdapter().getItemCount();
        int itemPosition = parent.getChildAdapterPosition(view);
//        RecommendAttentionAdapter recommendAttentionAdapter = (RecommendAttentionAdapter) parent.getAdapter();
        int SPANT_COUNT = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
        int eachWidth = (SPANT_COUNT - 1) * DensityUtil.dp2px(parent.getContext(), 10) / SPANT_COUNT;
        int dl = DensityUtil.dp2px(parent.getContext(), 10) - eachWidth;
        int left;
        int right;
        int bottom;
//        if (!recommendAttentionAdapter.isSectionHeaderPosition(parent.getChildAdapterPosition(view))) {
//            left = (recommendAttentionAdapter.getPositionWithinSection())[parent.getChildAdapterPosition(view)] % SPANT_COUNT * dl;
//            right = eachWidth - left;
//            bottom = view.getHeight() + DensityUtil.dip2px(parent.getContext(), 30);
//            Log.i("mzh", "onDraw view Height:" + view.getHeight());
//            outRect.set(left, 0, right, 0);
//        } else {
//            if (parent.getChildAdapterPosition(view) != 0) {
//                outRect.set(0, DensityUtil.dip2px(parent.getContext(), 30), 0, 0);
//            } else {
//                outRect.set(0, 0, 0, 0);
//            }
//        }
    }

    /**
     * 是否可以绘制分割线
     *
     * @param parent     当前的RecyclerView
     * @param itemView   当前的内容项
     * @param totalCount 适配器的item总数，可能大于RecyclerView的item总数
     * @return
     */
    private boolean isDraw(RecyclerView parent, View itemView, int totalCount) {
        int itemPosition = parent.getChildAdapterPosition(itemView);
        if (totalCount > 1 && itemPosition < totalCount) {
            /*if (parent.getAdapter() instanceof RecommendAttentionAdapter) {
                RecommendAttentionAdapter recommendAttentionAdapter = (RecommendAttentionAdapter) parent.getAdapter();
                int i = (recommendAttentionAdapter.getSectionForPosition())[itemPosition];
                int i1 = (recommendAttentionAdapter.getPositionWithinSection())[itemPosition];
                if (recommendAttentionAdapter.getSectionSizeForPostion(i) - 1 == i1) {
                    return true;
                }
            }*/
        }
        return false;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        drawHorizontal(c, parent);
    }

    /**
     * 绘制水平的分割线
     *
     * @param c
     * @param parent
     */
    private void drawHorizontal(Canvas c, RecyclerView parent) {

        int totalCount = parent.getAdapter().getItemCount();

        //获取当前可见的item的数量，半个也算
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            //获取当前可见的view
            View child = parent.getChildAt(i);
            if (isDraw(parent, child, totalCount)) {
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
//                int left = child.getBottom() + params.bottomMargin;

                int left = 0;//组件在容器X轴上的起点，需要注意，如果用户设置了left方向的Margin值，需要在取得itemViewleft属性后，将该margin抵消掉，因为，用户设置margin的意图明显不是想让分割线覆盖掉的
                int right = child.getRight() + params.rightMargin;
                Log.i("mzh", "onDraw child Height:" + child.getHeight());
                int top = child.getTop() + child.getHeight() + params.bottomMargin + DensityUtil.dp2px(child.getContext(), 10);//组件在容器Y轴上的起点
                int bottom = top + mDividerHeight;
                if (mDividerDrawable != null) {
                    mDividerDrawable.setBounds(left, top, right, bottom);
                    mDividerDrawable.draw(c);
                }
                if (mDividerPaint != null) {
                    c.drawRect(left, top, right, bottom, mDividerPaint);
                }
            }
        }
    }

}
