package com.iyoyogo.android.base;

/**
 * Created by Stephen on 2018/9/10 16:42
 * Email: 895745843@qq.com
 */
public interface BaseView {
    void setTitle();

    void setLoadingView(boolean isShow);

    void setLoadFailedView(boolean isShow);

    void setNoDataView(boolean isShow);

    void longToast(String content);

    void shortToast(String content);

    void onUserTokenError();

    void onConnectServerError();
}
