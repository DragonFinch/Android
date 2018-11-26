package com.iyoyogo.android.net;


import android.support.annotation.NonNull;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.app.UserInfo;
import com.iyoyogo.android.utils.AuthUtils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wgheng on 2018/7/18.
 * Description :
 */
public class RequestInterceptor implements Interceptor {

    private static final String PUBLIC_PARAM_OH = "oh";
    private static final String PUBLIC_PARAM_USER_TOKEN = "token";

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl.Builder builder = request.url().newBuilder();
        //添加userToken公共参数
        if (App.isLogin) {
            String userToken = UserInfo.getInstance().getUserToken();
            builder.addQueryParameter(PUBLIC_PARAM_USER_TOKEN, userToken);
        }

        //添加oh公共参数
        HttpUrl httpUrl = builder
                .addQueryParameter(PUBLIC_PARAM_OH, AuthUtils.getAuth())
                .build();

        request = request.newBuilder().url(httpUrl).build();
        return chain.proceed(request);
    }

}
