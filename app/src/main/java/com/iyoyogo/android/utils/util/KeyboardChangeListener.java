package com.iyoyogo.android.utils.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * @author zhuhui
 * @date 2019/1/19
 * @description
 */
public class KeyboardChangeListener implements ViewTreeObserver.OnGlobalLayoutListener {

    private static final String           TAG = "ListenerHandler";
    private              View             mContentView;   // 当前界面的根视图
    private              int              mOriginHeight;   // 此时根视图的高度
    private              int              mPreHeight;   // 改变之前根视图的高度
    private              KeyBoardListener mKeyBoardListen;

    private Context mContext;

    private   int     mOldh              = -1;
    private   int     mNowh              = -1;
    protected int     mScreenHeight      = 0;
    protected boolean mIsSoftKeyboardPop = false;

    public KeyboardChangeListener(Activity activity) {
        if (activity == null) {
            Log.i(TAG, "contextObj is null");
            return;
        }
        mContext = activity;
        mContentView = findContentView(activity);
        if (mContentView != null) {
            addContentTreeObserver();
        }
    }

    private View findContentView(Activity activity) {
        return activity.findViewById(android.R.id.content);
    }

    private void addContentTreeObserver() {
        mContentView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        Rect r = new Rect();
        ((Activity) mContext).getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        if (mScreenHeight == 0) {
            mScreenHeight = r.bottom;
        }
        mNowh = mScreenHeight - r.bottom;
        if (mOldh != -1 && mNowh != mOldh) {
            if (mNowh > 0) {
                mIsSoftKeyboardPop = true;
                if (mKeyBoardListen != null) {
                    mKeyBoardListen.onKeyboardChange(true, mNowh);
                }
            } else {
                mIsSoftKeyboardPop = false;
                if (mKeyBoardListen != null) {
                    mKeyBoardListen.onKeyboardChange(false, mOldh);
                }
            }
        }
        mOldh = mNowh;


//        // 先获取到当前根视图的高度
//        int currHeight = mContentView.getHeight();
//        if (currHeight == 0) {
//            return;
//        }
//
//        boolean hasChange = false;
//        if (mPreHeight == 0) {
//            mPreHeight = currHeight;
//            mOriginHeight = currHeight;
//        } else {
//            if (mPreHeight != currHeight) {
//                hasChange = true;
//                mPreHeight = currHeight;
//            } else {
//                hasChange = false;
//            }
//        }
//        if (hasChange) {
//            boolean isShow;
//            int     keyboardHeight = 0;
//            // 当当前的根视图高度和初始化时的高度一样时，说明此时软键盘没有显示，是消失状态
//            if (mOriginHeight == currHeight) {
//                //hidden
//                isShow = false;
//            } else {
//                // 此时，根视图的高度减少了，而减少的部分就是软键盘的高度，软键盘显示状态
//                //show
//                keyboardHeight = mOriginHeight - currHeight;
//                isShow = true;
//            }
//
//            if (mKeyBoardListen != null) {
//                mKeyBoardListen.onKeyboardChange(isShow, keyboardHeight);
//            }
//        }
    }

    public void setKeyBoardListener(KeyBoardListener keyBoardListen) {
        this.mKeyBoardListen = keyBoardListen;
    }

    // 资源释放
    public void destroy() {
        if (mContentView != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mContentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        }
    }

    public interface KeyBoardListener {

        void onKeyboardChange(boolean isShow, int keyboardHeight);
    }
}