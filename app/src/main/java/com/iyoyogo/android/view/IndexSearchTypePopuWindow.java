package com.iyoyogo.android.view;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.iyoyogo.android.R;
import com.luck.picture.lib.tools.ScreenUtils;

/**
 * 创建时间：2018/5/31
 * 描述：
 */
public class IndexSearchTypePopuWindow extends PopupWindow {
    private Context context;
    private View mContentView;
    private RelativeLayout rl_meipai;
    private RelativeLayout rl_zuji;

    public IndexSearchTypePopuWindow(Context context, SearchListener listener) {
        super(context);
        this.context = context;
        init(listener);
    }

    private void init(SearchListener listener) {
        mContentView = LayoutInflater.from(context).inflate(R.layout.search_type_popuwindow, null);
        setContentView(mContentView);
        setTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable());
        // 重写onKeyListener
        mContentView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });
        rl_meipai = mContentView.findViewById(R.id.rl_meipai);
        rl_zuji = mContentView.findViewById(R.id.rl_zuji);
        rl_meipai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (null != listener) {
                    listener.onSearchListener(false);
                }
            }
        });

        rl_zuji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (null != listener) {
                    listener.onSearchListener(true);
                }
            }
        });
    }

    public View getmContentView() {
        return mContentView;
    }

    /**
     * 计算出来的位置，y方向就在anchorView的上面和下面对齐显示，x方向就是与屏幕右边对齐显示
     * 如果anchorView的位置有变化，就可以适当自己额外加入偏移来修正
     *
     * @param anchorView  呼出window的view
     * @param contentView window的内容布局
     * @return window显示的左上角的xOff, yOff坐标
     */
    public static int[] calculatePopWindowPos(final View anchorView, final View contentView) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        // 获取屏幕的高宽
        final int screenHeight = ScreenUtils.getScreenHeight(anchorView.getContext());
        final int screenWidth = ScreenUtils.getScreenWidth(anchorView.getContext());
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        final int windowHeight = contentView.getMeasuredHeight();
        final int windowWidth = contentView.getMeasuredWidth();
        // 判断需要向上弹出还是向下弹出显示
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
        if (isNeedShowUp) {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] - windowHeight;
        } else {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] + anchorHeight;
        }
        return windowPos;
    }

    public interface SearchListener {
        void onSearchListener(boolean isFootStep);
    }
}
