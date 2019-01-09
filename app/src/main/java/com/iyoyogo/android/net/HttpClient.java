package com.iyoyogo.android.net;


import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.iyoyogo.android.app.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wgheng on 2018/7/10.
 * Description : 网络框架封装
 */
public class HttpClient {

    private static volatile HttpClient instance;
    private ApiService mApiService;

    private HttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
              //  .addNetworkInterceptor(new StethoInterceptor())
//                .addInterceptor(new RequestInterceptor())
                .addInterceptor(new HttpLogInterceptor())
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.MINUTES) // write timeout
                .readTimeout(30, TimeUnit.MINUTES)// read timeout
                .build();



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        mApiService = retrofit.create(ApiService.class);
    }

    public static HttpClient getInstance() {
        if (instance == null) {
            synchronized (HttpClient.class) {
                if (instance == null) {
                    instance = new HttpClient();
                }
            }
        }
        return instance;
    }

    public static ApiService getApiService() {
        return getInstance().mApiService;
    }

    public static RequestBody createJsonBody(@NonNull Object request) {
        String json = new Gson().toJson(request);
        Log.i("RequestBody:",json);
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
    }



}
