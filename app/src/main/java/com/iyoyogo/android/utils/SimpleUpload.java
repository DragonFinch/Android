package com.iyoyogo.android.utils;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

/**
 * ************************************************
 * PROJECT_NAME : ecar
 * PACKAGE_NAME : com.irongbei.ecar.framework.utils
 * AUTHOR : yanglinchuan
 * DATA : 2018/10/18
 * TIME : 11:21
 * MSG :简单上传oss图片
 * ************************************************
 */

public class SimpleUpload {

    private static SimpleUpload mSimpleUpload = null;

    private SimpleUpload() {
    }

    public static SimpleUpload getInstance() {
        synchronized (SimpleUpload.class) {
            if (mSimpleUpload == null) {
                mSimpleUpload = new SimpleUpload();
            }
        }
        return mSimpleUpload;
    }

    /**
     * 阿里云初始化
     */
    public static OSS intoss(Context context) {
        //http://51easycar.oss-cn-hangzhou.aliyuncs.com/test/img.kkk}
        OSS oss;
        String access_id = "LTAInRzzjv0TZcA5";
        String access_key = "jQZXJDYzAU7Ki0DfZvfIoU3PxazsLy";
        String endpoint = "http://oss-cn-beijing.aliyuncs.com/";
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(access_id, access_key);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(8); // 最大并发请求数，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        oss = new OSSClient(context, endpoint, credentialProvider, conf);
        return oss;
    }

    public void uploadOss(String path, OSS oss,final UploadImgListener uploadImgListener ) {
        // 构造上传请求
        String key = "android/"+"yoxiu"+".jpeg";
        PutObjectRequest put = new PutObjectRequest("xzdtest", key, path);
        // 异步上传时可以设置进度回调
        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentType("image/jpeg");
        put.setMetadata(objectMeta);
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {

            }
        });

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {

                uploadImgListener.uploadSuccess(request,result);
                Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());
                LogUtils.debug("OSSAsyncTask", result.getETag());
                LogUtils.debug("OSSAsyncTask", result.getRequestId());
                LogUtils.debug("OSSAsyncTask", result.getServerCallbackReturnBody());
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                uploadImgListener.uploadFailure(request);
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
    }

    public interface UploadImgListener {

        void uploadSuccess(PutObjectRequest request, PutObjectResult result);

        void uploadFailure(PutObjectRequest request);
    }


}
