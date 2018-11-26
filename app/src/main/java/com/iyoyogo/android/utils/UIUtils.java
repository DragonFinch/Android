package com.iyoyogo.android.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.IBinder;

import android.support.design.widget.TabLayout;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.iyoyogo.android.R;

import java.lang.reflect.Field;

/**
 * Created by wgheng on 2018/7/13.
 * Description :
 */
public class UIUtils {

    /**
     * 通过反射修改TabLayout Indicator的宽度（仅在Android 4.2及以上生效）
     */
    public static void setUpIndicatorWidth(Context context, TabLayout tabLayout, int dpWidth) {
        Class<?> tabLayoutClass = tabLayout.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayoutClass.getDeclaredField("mTabStrip");
            tabStrip.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        LinearLayout layout = null;
        try {
            if (tabStrip != null) {
                layout = (LinearLayout) tabStrip.get(tabLayout);
            }
            for (int i = 0; i < layout.getChildCount(); i++) {
                View child = layout.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    params.setMarginStart(DensityUtil.dp2px(context, dpWidth));
                    params.setMarginEnd(DensityUtil.dp2px(context, dpWidth));
                }
                child.setBackgroundColor(context.getResources().getColor(R.color.theme_white));
                child.setLayoutParams(params);
                child.invalidate();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    /***
     * 通过反射修改TabLayout Indicator 宽度，使其跟随文字一样
     * 调用此方法的时候保证 tablayout 文字已经设置了，
     * @param tabLayout
     */
    public static void setTabLayoutIndicatorWidthAuto(final TabLayout tabLayout) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

                    int dp10 = DensityUtil.dp2px(tabLayout.getContext(), 10);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //字多宽线就多宽，测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = dp10;
                        params.rightMargin = dp10;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 设置富文本
     */
    public static Spanned toH5MultiText(String multiText) {
        Spanned result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            result = Html.fromHtml(multiText, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(multiText);
        }
        return result;
    }

    /**
     * 设置按钮状态
     */
    public static void setButtonStatus(TextView textView, boolean enable) {
        textView.setClickable(enable);
        if (enable) {
            textView.setBackgroundResource(R.drawable.bg_btn_enable);
        } else {
            textView.setBackgroundResource(R.drawable.bg_btn_disable);
        }
    }

    /**
     * 设置窗口透明度
     */
    public static void setWindowAlpha(Activity activity, float alpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alpha;
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(Context context, IBinder windowToken) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(windowToken, 0);// 强制隐藏软键盘
    }


    /***
     * 关闭软键盘
     * @param context
     */
    public static void hideSoftBoard(Activity context) {
        View focus = context.getCurrentFocus();
        if (focus != null) {
            hideSoftInput(context, focus.getWindowToken());
        }
    }


}
