package com.iyoyogo.android.view;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;

/**
 * 创建时间：2018/5/27
 * 描述：
 */
public class YoyogoTopBarView extends RelativeLayout {
    private Context context;
    private ImageView searchIcon;
    private TextView loacktionTv;
    private YoyotopBarClickCallback yoyotopBarClickCallback;
    private TextView attendtion_tv;
    private TextView recommend_tv;
    private ImageView attentionImg;
    private ImageView recommendImg;
    private ImageView locationIcon;

    public void setYoyotopBarClickCallback(YoyotopBarClickCallback yoyotopBarClickCallback) {
        this.yoyotopBarClickCallback = yoyotopBarClickCallback;
    }

    public interface YoyotopBarClickCallback {
        void onSearchClick();

        void onLocationClick();

        void onRecommendClick();

        void onAttentionClick();

        void setOnClickListener();
    }

    public YoyogoTopBarView(Context context) {
        this(context, null);
    }

    public YoyogoTopBarView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public YoyogoTopBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater.from(context).inflate(R.layout.index_top_bar_layout, this, true);
        searchIcon = findViewById(R.id.search_icon);
        loacktionTv = findViewById(R.id.location);
        locationIcon = findViewById(R.id.location_icon);
        attendtion_tv = findViewById(R.id.attendtion_tv);
        recommend_tv = findViewById(R.id.recommend_tv);

        Drawable drawableLeft1 = getResources().getDrawable(
                R.mipmap.line_white);

        recommend_tv.setCompoundDrawablesWithIntrinsicBounds(null,
                null, null, drawableLeft1);
        attendtion_tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawableLeft1 = getResources().getDrawable(
                        R.mipmap.line_white);
                attendtion_tv.setCompoundDrawablesWithIntrinsicBounds(null,
                        null, null, drawableLeft1);
                recommend_tv.setCompoundDrawablesWithIntrinsicBounds(null,
                        null, null, null);
                attendtion_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                attendtion_tv.setTextColor(Color.parseColor("#ffffff"));
                recommend_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                recommend_tv.setTextColor(Color.parseColor("#ffffff"));
                if (yoyotopBarClickCallback != null) {
                    yoyotopBarClickCallback.onAttentionClick();
                }
            }
        });
        recommend_tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                attendtion_tv.setCompoundDrawablesWithIntrinsicBounds(null,
                        null, null, null);
                Drawable drawableLeft = getResources().getDrawable(
                        R.mipmap.line_white);
                recommend_tv.setCompoundDrawablesWithIntrinsicBounds(null,
                        null, null, drawableLeft);
                attendtion_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                attendtion_tv.setTextColor(Color.parseColor("#ffffff"));

                recommend_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                recommend_tv.setTextColor(Color.parseColor("#ffffff"));

                if (yoyotopBarClickCallback != null) {
                    yoyotopBarClickCallback.onRecommendClick();
                }
            }
        });
        searchIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yoyotopBarClickCallback != null) {
                    yoyotopBarClickCallback.onSearchClick();
                }
            }
        });
        locationIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yoyotopBarClickCallback != null) {
                    yoyotopBarClickCallback.onLocationClick();
                }
            }
        });
        loacktionTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yoyotopBarClickCallback != null){
                    yoyotopBarClickCallback.setOnClickListener();
                }
            }
        });
    }

    public void setLocationResult(String city) {
        if (!TextUtils.isEmpty(city)) {
            loacktionTv.setText(city);
        }
    }

    public void changeAtt(){
        Drawable drawableLeft1 = getResources().getDrawable(
                R.mipmap.line_white);
        attendtion_tv.setCompoundDrawablesWithIntrinsicBounds(null,
                null, null, drawableLeft1);
        recommend_tv.setCompoundDrawablesWithIntrinsicBounds(null,
                null, null, null);
        attendtion_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        attendtion_tv.setTextColor(Color.parseColor("#ffffff"));
        recommend_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        recommend_tv.setTextColor(Color.parseColor("#ffffff"));
        if (yoyotopBarClickCallback != null) {
            yoyotopBarClickCallback.onAttentionClick();
        }
    }
}
