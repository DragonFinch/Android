package com.iyoyogo.android.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.iyoyogo.android.utils.map.DisplayUtil;


public class LetterListView extends View {

    public static String[] b = { "A", "B", "C", "D", "E", "F", "G", "H", "J", "K",
            "L", "M", "N", "P", "Q", "R", "S", "T", "W", "X", "Y", "Z"};
    int choose = -1;
    Paint paint = new Paint();
    boolean showBkg = false;
    private Context mContext;
    //在布局里面声明该控件并使用style属性的时候调用
    public LetterListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
    }
    //在布局里面声明的时候调用
    public LetterListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }
    //在代码里面new View 初始化的时候调用
    public LetterListView(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (showBkg) {
            canvas.drawColor(Color.parseColor("#40000000"));
        }
        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / b.length;
        for (int i = 0; i < b.length; i++) {
            paint.setColor(Color.parseColor("#FA800A"));
            paint.setTextSize(DisplayUtil.sp2px(mContext, 12));
            paint.setAntiAlias(true);
            float xPos = width / 2 - paint.measureText(b[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(b[i], xPos, yPos, paint);
            paint.reset();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();   // 动作点击事件
        final float y = event.getY();
        int changeIndex = -1;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int index = (int) (y / getHeight() * b.length);
        switch (action) {
            case MotionEvent.ACTION_DOWN:  // 当按下时
                showBkg = true;
                if (changeIndex != index && listener != null) {
                    if (index >= 0 && index < b.length) {
                        listener.onTouchingLetterChanged(b[index]);
                        choose = index;
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:   // 当移动时
                if (changeIndex != index && listener != null) {
                    if (index >= 0 && index < b.length) {
                        listener.onTouchingLetterChanged(b[index]);
                        choose = index;
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:  // 松开手时
                showBkg = false;
                choose = -1;
                invalidate();
                break;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    OnTouchingLetterChangedListener onTouchingLetterChangedListener;

    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String s);
    }

    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

}
