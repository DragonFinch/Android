package com.iyoyogo.android.view;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.iyoyogo.android.R;
import com.iyoyogo.android.app.Constants;
import com.jakewharton.rxbinding2.view.RxView;
import com.luck.picture.lib.tools.ScreenUtils;

import java.util.concurrent.TimeUnit;

/**
 * 创建时间：2018/6/one_one
 * 描述：发布状态是否可见
 */
public class ShowPrivatePopuWindow extends PopupWindow {
    private Context context;
    private View mContentView;
    private ShowClickListener mClick;

    public ShowPrivatePopuWindow(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public View getmContentView() {
        return mContentView;
    }

    private void init() {
        mContentView = LayoutInflater.from(context).inflate(R.layout.zuji_status_popuwindow, null);
        RxView.clicks(mContentView.findViewById(R.id.dt_show))
                .throttleFirst(Constants.ClickDuration, TimeUnit.SECONDS)
                .subscribe(aVoid -> {
                    if (mClick != null) {
                        mClick.onHide(false);
                    }
                    dismiss();
                });
        RxView.clicks(mContentView.findViewById(R.id.dt_privete))
                .throttleFirst(Constants.ClickDuration, TimeUnit.SECONDS)
                .subscribe(aVoid -> {
                    if (mClick != null) {
                        mClick.onHide(true);
                    }
                    dismiss();
                });
        setContentView(mContentView);
        setTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable());
    }

    public void setmClick(ShowClickListener mClick) {
        this.mClick = mClick;
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

    public interface ShowClickListener {
        void onHide(boolean isPrivate);
    }
}
