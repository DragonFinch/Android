package com.iyoyogo.android.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

@SuppressLint("AppCompatCustomView")
public class AeDITEXT extends EditText {
    public AeDITEXT(Context context) {
        super(context);
    }

    public AeDITEXT(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AeDITEXT(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //让父类不不拦截自己的触摸事件
        if (hasFocus()){
            getParent().requestDisallowInterceptTouchEvent(true);
        }else {
            getParent().requestDisallowInterceptTouchEvent(false);
        }
        return super.dispatchTouchEvent(event);
    }

}
