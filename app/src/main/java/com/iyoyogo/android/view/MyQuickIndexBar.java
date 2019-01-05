package com.iyoyogo.android.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.parseColor;

/**
 * 爱生活，爱代码
 * 创建于：2018/11/8 14:10
 * 作 者：T
 * 微信：704003376
 */
public class MyQuickIndexBar extends View {
    private String[] mStrArr = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "k", "L", "M", "N", "O", "p", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public MyQuickIndexBar(Context context) {
        this(context, null);
    }

    public MyQuickIndexBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyQuickIndexBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private int mWidth;
    private float mCellHeight;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mCellHeight = getMeasuredHeight() * 1F / mStrArr.length;
    }

    private Paint mPaint;

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#FA800A"));
        //以文本的中心线为起始点开始绘制文本
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(30);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < mStrArr.length; i++) {
            float x = mWidth / 2;
            float y = mCellHeight / 2 + getTextHeight(mStrArr[i]) / 2 + i * mCellHeight + 10;
            mPaint.setColor(mLastIndex == i ? parseColor("#ffffff") : BLACK);
            canvas.drawText(mStrArr[i], x, y, mPaint);
        }
        super.onDraw(canvas);
    }
	
    private float getTextHeight(String text) {
        Rect rect = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), rect);
        return rect.bottom;
    }


    private int mLastIndex = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int index = (int) (event.getY() / mCellHeight);
                if (mLastIndex != index) {
                    Log.e("wew", mStrArr[index] + "");
                    if (mListenner != null) {
                        mListenner.getOnImtemPosition(mStrArr[index]);
                    }
                }
                mLastIndex = index;
                break;
            case MotionEvent.ACTION_UP:
                Log.e("assaas", "action_up");
                mLastIndex = -1;
                break;

        }
        //重绘 (重新绘制View)
        invalidate();
        return true;
    }


    private onItemClickListenner mListenner;


    public interface onItemClickListenner {
        void getOnImtemPosition(String letter);
    }


    public void setOnItemtClickListenner(onItemClickListenner listenner) {
        this.mListenner = listenner;
    }
}
