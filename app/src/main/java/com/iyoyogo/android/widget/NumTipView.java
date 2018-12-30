package com.iyoyogo.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.iyoyogo.android.R;
import com.iyoyogo.android.utils.ShapeSelectorUtil;
import com.iyoyogo.android.utils.UnitConverter;

public class NumTipView extends AppCompatImageView {
    private Context context;
    private int bgColor, textColor;
    private int maxNum, minNum, num;
    private String textContent;
    private float textSize;
    private boolean minGone;
    private float horPadding, verPadding;


    public NumTipView(Context context) {
        this(context, null);
    }

    public NumTipView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumTipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        bgColor = context.getResources().getColor(R.color.red);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NumTipView);
        maxNum = ta.getInteger(R.styleable.NumTipView_max_num, 99);
        minNum = ta.getInteger(R.styleable.NumTipView_min_num, 0);
        num = minNum;
        textContent = "" + num;
        textSize = ta.getDimension(R.styleable.NumTipView_text_size, UnitConverter.dip2px(context, 14));
        textColor = ta.getColor(R.styleable.NumTipView_text_color, Color.WHITE);
        minGone = ta.getBoolean(R.styleable.NumTipView_min_gone, true);
        horPadding = ta.getDimension(R.styleable.NumTipView_hor_padding, 4f);
        verPadding = ta.getDimension(R.styleable.NumTipView_ver_padding, 3f);
        ta.recycle();

        if (attrs != null) {
            for (int i = 0, size = attrs.getAttributeCount(); i < size; i++) {
                String name = attrs.getAttributeName(i);
                String value = attrs.getAttributeValue(i);
                if ("background".equals(name)) {
                    if (value.startsWith("@")) {
                        int bgResId = Integer.parseInt(value.substring(1));
                        bgColor = context.getResources().getColor(bgResId);
                    } else if (value.startsWith("#")) {
                        bgColor = Color.parseColor(value);
                    }
                }
            }
        }

//        setBackgroundResource(R.drawable.ic_red);
//        postInvalidate();
        GradientDrawable bg = ShapeSelectorUtil.getDrawable(null, new ShapeSelectorUtil.CornersWrapper().setRadius(200), bgColor, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(bg);
        } else {
            setBackgroundDrawable(bg);
        }

        setNum(num);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setTextSize(textSize);
        paint.setColor(textColor);

        int textWidth = getTextWidth(paint, textContent);
//        Log.d("textWidth==" + textWidth);


//        Bitmap bmp_9 = BitmapFactory.decodeResource(getResources(),
//                R.drawable.ic_red);
//        NinePatch np = new NinePatch(bmp_9, bmp_9.getNinePatchChunk(), null);
//        Rect rect = new Rect(0, 0, getWidth(), getHeight());
//        np.draw(canvas, rect);
        canvas.drawText(textContent, (getWidth() - textWidth) / 2, (getHeight() + 2 * textSize / 3) / 2, paint);

    }

    private int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

    /**
     * 调整控件宽度
     */
    private void adjustWidth() {
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        int textWidth = getTextWidth(paint, textContent);
        if (textContent.length() == 1) {
            textWidth *= 1.7;
        }
        setWidthHeight(textWidth + UnitConverter.dip2px(context, horPadding * 2), (int) (textSize + UnitConverter.dip2px(context, verPadding * 2)));
    }

    /**
     * 设置控件宽高
     *
     * @param width
     * @param height
     */
    private void setWidthHeight(int width, int height) {
        ViewGroup.LayoutParams lp = getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(width, height);
        } else {
            lp.width = width;
            lp.height = height;
        }
        setLayoutParams(lp);
    }

    /**
     * 设置数值
     *
     * @param num
     */
    public void setNum(int num) {
        if (num < minNum) {
            this.num = minNum;
            textContent = "" + this.num;
        } else if (num > maxNum) {
            this.num = maxNum;
            textContent = this.num + "+";
        } else {
            this.num = num;
            textContent = "" + this.num;
        }

        if (num == minNum && minGone) {
            setVisibility(View.GONE);
        } else {
            setVisibility(View.VISIBLE);
        }
        adjustWidth();
        postInvalidate();
    }

    public void setText(String content) {
        textContent = content;
        adjustWidth();
        postInvalidate();
    }

    /**
     * 数值+1
     */
    public void increase() {
        setNum(num + 1);
    }

    /**
     * 数值-1
     */
    public void reduce() {
        setNum(num - 1);
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(@ColorInt int bgColor) {
        this.bgColor = bgColor;
        postInvalidate();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        postInvalidate();
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        if (maxNum >= minNum) {
            this.maxNum = maxNum;
            setNum(num);
        }
    }

    public int getMinNum() {
        return minNum;
    }

    public void setMinNum(int minNum) {
        if (minNum <= maxNum) {
            this.minNum = minNum;
            setNum(num);
        }
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        postInvalidate();
    }

    public boolean isMinGone() {
        return minGone;
    }

    public void setMinGone(boolean minGone) {
        this.minGone = minGone;
        postInvalidate();
    }
}
