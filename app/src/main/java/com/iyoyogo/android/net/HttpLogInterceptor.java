package com.iyoyogo.android.net;


import android.support.annotation.NonNull;
import android.util.Log;


import com.iyoyogo.android.app.BuildConfig;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.GzipSource;

/**
 * Created by wgheng on 2018/7/10.
 * Description : 网络请求日志拦截器
 */
public class HttpLogInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        if (BuildConfig.DEBUG) {
            printRequestInfo(request);
        }
        Response response = chain.proceed(request);
        if (BuildConfig.DEBUG) {
            printResponseBody(request.url().toString(), response);
        }
        return response;
    }

    private void printRequestInfo(Request request) {
        Log.i("RequestUrl:",request.url().toString());
    }

    /**
     * 打印响应体
     */
    private void printResponseBody(String url, Response response) throws IOException {
        Headers headers = response.headers();
        ResponseBody responseBody = response.body();

        if (responseBody != null) {
            long contentLength = responseBody.contentLength();
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Long gzippedLength = null;
            if ("gzip".equalsIgnoreCase(headers.get("Content-Encoding"))) {
                gzippedLength = buffer.size();
                GzipSource gzippedResponseBody = null;
                try {
                    gzippedResponseBody = new GzipSource(buffer.clone());
                    buffer = new Buffer();
                    buffer.writeAll(gzippedResponseBody);
                } finally {
                    if (gzippedResponseBody != null) {
                        gzippedResponseBody.close();
                    }
                }
            }

            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(Charset.forName("UTF-8"));
            }

            if (contentLength != 0) {
                Log.i("url:" + url + "\nResponseBody:" ,buffer.clone().readString(charset));//打印响应的json
            }

            if (gzippedLength != null) {
                Log.i("url:" , url + "\n<-- END HTTP (" + buffer.size() + "-byte, "
                        + gzippedLength + "-gzipped-byte body)");
            } else {
                Log.i("url:" , url + "\n<-- END HTTP (" + buffer.size() + "-byte body)");
            }
        }
    }

}
