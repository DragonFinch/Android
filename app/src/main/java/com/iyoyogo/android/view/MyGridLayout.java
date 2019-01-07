package com.iyoyogo.android.view;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;

import java.util.List;

/**
 * 爱生活，爱代码
 * 创建于：2018/11/6 15:05
 * 作 者：T
 * 微信：704003376
 */
public class MyGridLayout extends GridLayout implements View.OnLongClickListener {

    public MyGridLayout(Context context) {
        this(context, null);
    }

    public MyGridLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private int mGridCount = 4;

    private void init() {
        setColumnCount(mGridCount);
        setLayoutTransition(new LayoutTransition());
    }

    public void addItems(List<String> items) {
        for (String strItem : items) {
            addStrText(strItem);
        }
    }

    private int mMargin = 10;

    public void addStrText(String str) {
        final TextView tv = new TextView(getContext());
        tv.setText(str);
        tv.setBackgroundResource(R.drawable.gridlayout_tv_bg);
        tv.setTextSize(12);
        LayoutParams params = new LayoutParams();
        int width = getResources().getDisplayMetrics().widthPixels / mGridCount - (mMargin + 3) * 2;
        tv.setWidth(width);
        params.setMargins(mMargin + 3, mMargin + 5, mMargin + 3, mMargin + 5);
        tv.setLayoutParams(params);
        tv.setPadding(mMargin + 5, mMargin, mMargin + 5, mMargin);
        tv.setGravity(Gravity.CENTER);
        addView(tv);

        tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListenner != null) {
                    itemClickListenner.setOnItemClickListenner(tv);
                }
            }
        });

        if (mDragAble) {
            tv.setOnLongClickListener(this);
        } else
            tv.setOnLongClickListener(null);

    }

    private View mDragedView;

    @Override
    public boolean onLongClick(View v) {
        //开始拖拽，拖拽的时候标签有阴影
        startDrag(null, new DragShadowBuilder(v), null, 0);
        this.mDragedView = v;
        return true;
    }


    private boolean mDragAble;

    //设置拖拽监听器
    public void setDragAble(boolean isDragAble) {
        mDragAble = isDragAble;
        if (isDragAble)
            setOnDragListener(listener);
        else {
            setOnDragListener(null);
        }
    }


    private OnDragListener listener = new OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {

                case DragEvent.ACTION_DRAG_STARTED:
                    initRects();
                    break;


                case DragEvent.ACTION_DRAG_LOCATION:
                    int index = dragChange(event);
                    if (index > -1 && mDragedView != null && mDragedView != getChildAt(index)) {
                        removeView(mDragedView);
                        Log.e("sadsfgh", "---------------");
                        addView(mDragedView, index);
                    } else {
                        Log.e("sadsfgh", "-----小于等于-1----------" + index);
                    }
                    break;

            }
            return true;
        }
    };


    //实时判断 被拖拽的条目是否达到某个条目范围内
    private int dragChange(DragEvent event) {
        for (int i = 0; i < mRectArr.length; i++) {
            Log.e("qwert", "=============" + i);
            if (mRectArr[i].contains((int) event.getX(), (int) event.getY())) {
                return i;
            }
        }
        return -1;
    }


    private Rect[] mRectArr;

    //将所有的条目都封装成矩形对象，存到数组中
    private void initRects() {
        int itemCount = getChildCount();
        if (mRectArr == null)
            mRectArr = new Rect[itemCount];
        for (int i = 0; i < mRectArr.length; i++) {
            View view = getChildAt(i);
            mRectArr[i] = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        }
    }

    private OnItemClickListenner itemClickListenner;

    public interface OnItemClickListenner {
        void setOnItemClickListenner(View view);
    }

    public void setOnItemtClickListenner(OnItemClickListenner listenner) {
        this.itemClickListenner = listenner;
    }


}
