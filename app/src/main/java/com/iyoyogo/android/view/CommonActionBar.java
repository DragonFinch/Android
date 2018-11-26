package com.iyoyogo.android.view;


import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;

/**
 * 创建时间：2018/6/3
 * 描述：
 */
public class CommonActionBar extends FrameLayout {
    private Context context;
    private View view;
    private LinearLayout ll_left_content;
    private LinearLayout ll_right_content;
    private TextView title;
    private ImageButton img_btn;
    private CommonActionBarCallBack commonActionBarCallBack;

    public interface CommonActionBarCallBack {
//        void onLeftClick();
//
//        void onRightClick();

        void onBackClick();
    }

    public void setCommonActionBarCallBack(CommonActionBarCallBack commonActionBarCallBack) {
        this.commonActionBarCallBack = commonActionBarCallBack;
    }

    public LinearLayout getLl_left_content() {
        return ll_left_content;
    }

    public LinearLayout getLl_right_content() {
        return ll_right_content;
    }

    public CommonActionBar(Context context) {
        this(context, null);
    }

    public CommonActionBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
        initListener();
    }

    private void initListener() {
        img_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commonActionBarCallBack != null) {
                    commonActionBarCallBack.onBackClick();
                }
            }
        });
    }

    private void init() {
        view = LayoutInflater.from(context).inflate(R.layout.common_title_bar, null);
        addView(view);
        ll_left_content = findViewById(R.id.ll_left_content);
        ll_right_content = findViewById(R.id.ll_right_content);
        title = findViewById(R.id.title);
        img_btn = findViewById(R.id.img_btn);


    }

    public void setTitle(String text) {
        if (title != null && !TextUtils.isEmpty(text)) {
            title.setText(text);
        }
    }


}
