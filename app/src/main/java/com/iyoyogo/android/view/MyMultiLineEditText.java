package com.iyoyogo.android.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;

import com.iyoyogo.android.utils.DensityUtil;

/**
 * 创建时间：2018/6/one_one
 * 描述：
 */
public class MyMultiLineEditText extends android.support.v7.widget.AppCompatEditText {
    private static final String TAG = "MyMultiLineEditText";
    private Paint linePaint;
    private float margin;
    private int paperColor;
    private Rect mRect;
    private Context context;

    public MyMultiLineEditText(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        this.context = paramContext;
        mRect = new Rect();
        linePaint = new Paint();

    }

    public void onDraw(Canvas paramCanvas) {
        //设置画笔的颜色
        paramCanvas.drawColor(this.paperColor);
        Rect r = mRect;
        //获取text的行数这个行数是基于当前输入的文本的内容的行数
        int lineCount = getLineCount();
        //获取EditText组件的高度
        int height = getHeight();
        //获取EditText组件一行的高度
        int lineHeight = getLineHeight();
        int m = 1 + height / lineHeight;
        Log.i("mzh", "linCount:" + lineCount + ":---m：" + m);
        if (lineCount < m)
            lineCount = m;
        //返回指定的的baseline这里的baseline指的是0-getLineHeight()-1;
        //getLineBounds返回的值是baseline的y坐标
        int n = getLineBounds(0, r) + DensityUtil.dp2px(context, 10);
        paramCanvas.drawLine(0.0F, n, getRight(), n, this.linePaint);
        for (int i = 0; ; i++) {
            if (i >= lineCount + 1) {
                setPadding(10 + (int) this.margin, 0, 0, 0);
                super.onDraw(paramCanvas);
                paramCanvas.restore();
                return;
            }
            //绘制每一行的分割线
            n += lineHeight;
            paramCanvas.drawLine(0.0F, n, getRight(), n, this.linePaint);
            paramCanvas.save();
        }

    }
}
