package com.iyoyogo.android.base;

/**
 * Created by Stephen on 2018/9/10 16:42
 * Email: 895745843@qq.com
 */
public interface IBaseView {


    //设置加载中的视图
    void setLoadingView(boolean isShow);

    //加载失败
    void setLoadFailedView(boolean isShow);

    //加载空数据的视图
    void setNoDataView(boolean isShow);

    //长吐司
    void longToast(String content);

    //短吐司
    void shortToast(String content);

    //Token值错误
    void onUserTokenError();

    //连接服务区失败
    void onConnectServerError();





}
