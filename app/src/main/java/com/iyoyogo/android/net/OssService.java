package com.iyoyogo.android.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.CompleteMultipartUploadResult;
import com.alibaba.sdk.android.oss.model.CreateBucketRequest;
import com.alibaba.sdk.android.oss.model.DeleteBucketRequest;
import com.alibaba.sdk.android.oss.model.DeleteBucketResult;
import com.alibaba.sdk.android.oss.model.DeleteObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;
import com.alibaba.sdk.android.oss.model.HeadObjectRequest;
import com.alibaba.sdk.android.oss.model.HeadObjectResult;
import com.alibaba.sdk.android.oss.model.ListObjectsRequest;
import com.alibaba.sdk.android.oss.model.ListObjectsResult;
import com.alibaba.sdk.android.oss.model.MultipartUploadRequest;
import com.alibaba.sdk.android.oss.model.OSSRequest;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.alibaba.sdk.android.oss.model.ResumableUploadRequest;
import com.alibaba.sdk.android.oss.model.ResumableUploadResult;
import com.iyoyogo.android.R;
import com.iyoyogo.android.app.App;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.util.DateUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mOss on 2015/12/7 0007.
 * 支持普通上传，普通下载
 */
public class OssService {

    public         OSS    mOss;
    private        String mBucket             = "iyoyogo";
    private        String mCallbackAddress;
    private static String mResumableObjectKey = "resumableObject";

    String endpoint        = "oss-cn-beijing.aliyuncs.com";
    String bucketName      = "iyoyogo";
    String accessKeyId     = "LTAIql2brWD0qbEN";
    String accessKeySecret = "C74lDBcL1AqzEdIvHZkYMJlSNmRtby";

    private UploadImageListener mUploadImageListener;

    public OssService(UploadImageListener uploadImageListener) {
        this.mUploadImageListener=uploadImageListener;
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        // 异步上传
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000);
        conf.setSocketTimeout(15 * 1000);
        conf.setMaxConcurrentRequest(8);
        conf.setMaxErrorRetry(2);
        this.mOss = new OSSClient(App.getmContext(), endpoint, credentialProvider, conf);
    }

    public void setBucketName(String bucket) {
        this.mBucket = bucket;
    }

    public void initOss(OSS _oss) {
        this.mOss = _oss;
    }

    public void setCallbackAddress(String callbackAddress) {
        this.mCallbackAddress = callbackAddress;
    }

    public void asyncGetImage(String object) {
        final long get_start = System.currentTimeMillis();
        OSSLog.logDebug("get start");
        if ((object == null) || object.equals("")) {
            Log.w("AsyncGetImage", "ObjectNull");
            return;
        }

        OSSLog.logDebug("create GetObjectRequest");
        GetObjectRequest get = new GetObjectRequest(mBucket, object);
        get.setCRC64(OSSRequest.CRC64Config.YES);
        get.setProgressListener(new OSSProgressCallback<GetObjectRequest>() {
            @Override
            public void onProgress(GetObjectRequest request, long currentSize, long totalSize) {
                Log.d("GetObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });
        OSSLog.logDebug("asyncGetObject");
        OSSAsyncTask task = mOss.asyncGetObject(get, new OSSCompletedCallback<GetObjectRequest, GetObjectResult>() {
            @Override
            public void onSuccess(GetObjectRequest request, GetObjectResult result) {
                // 请求成功
                InputStream inputStream = result.getObjectContent();
                Bitmap      bm          = BitmapFactory.decodeStream(inputStream);
            }

            @Override
            public void onFailure(GetObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                String info = "";
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                    info = clientExcepion.toString();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                    info = serviceException.toString();
                }
            }
        });
    }


    public void asyncPutImage(String localFile ,int position) {
        final long upload_start = System.currentTimeMillis();
        OSSLog.logDebug("upload start");

        File file = new File(localFile);
        if (!file.exists()) {
            Log.w("AsyncPutImage", "FileNotExist");
            Log.w("LocalFile", localFile);
            return;
        }
        String userId = SpUtils.getString(App.getmContext(), "user_id", null);
        String date   = DateUtils.stampToDate(new Date(), "yyyy/MM/dd");
        int    num    = new Random().nextInt(99999) % (99999 - 10000 + 1) + 10000;
        String name   = userId + "/yoj/" + date + "/" + System.currentTimeMillis() + num + ".jpg";

        // 构造上传请求
        OSSLog.logDebug("create PutObjectRequest ");
        PutObjectRequest put = new PutObjectRequest(mBucket, name, localFile);
        put.setCRC64(OSSRequest.CRC64Config.YES);
        if (mCallbackAddress != null) {
            // 传入对应的上传回调参数，这里默认使用OSS提供的公共测试回调服务器地址
            put.setCallbackParam(new HashMap<String, String>() {
                {
                    put("callbackUrl", mCallbackAddress);
                    //callbackBody可以自定义传入的信息
                    put("callbackBody", "filename=${object}");
                }
            });
        }

        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });

        OSSLog.logDebug(" asyncPutObject ");
        OSSAsyncTask task = mOss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                String url = "https://" + bucketName + "." + endpoint + "/" + name;
                Log.d("OssService", url);
                mUploadImageListener.onUploadSuccess(url,position);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                String info = "";
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                    info = clientExcepion.toString();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                    info = serviceException.toString();
                    mUploadImageListener.onUploadError(serviceException);
                }
            }
        });
    }

    // Downloads the files with specified prefix in the asynchronous way.
    public void asyncListObjectsWithBucketName() {
        ListObjectsRequest listObjects = new ListObjectsRequest(mBucket);
        // Sets the prefix
        listObjects.setPrefix("android");
        listObjects.setDelimiter("/");
        // Sets the success and failure callback. calls the Async API
        OSSAsyncTask task = mOss.asyncListObjects(listObjects, new OSSCompletedCallback<ListObjectsRequest, ListObjectsResult>() {
            @Override
            public void onSuccess(ListObjectsRequest request, ListObjectsResult result) {
                String info = "";
                OSSLog.logDebug("AyncListObjects", "Success!");
                for (int i = 0; i < result.getObjectSummaries().size(); i++) {
                    info += "\n" + String.format("object: %s %s %s", result.getObjectSummaries().get(i).getKey(), result.getObjectSummaries().get(i).getETag(), result.getObjectSummaries().get(i).getLastModified().toString());
                    OSSLog.logDebug("AyncListObjects", info);
                }
            }

            @Override
            public void onFailure(ListObjectsRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // request exception
                if (clientExcepion != null) {
                    // client side exception such as network exception
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // service side exception.
                    OSSLog.logError("ErrorCode", serviceException.getErrorCode());
                    OSSLog.logError("RequestId", serviceException.getRequestId());
                    OSSLog.logError("HostId", serviceException.getHostId());
                    OSSLog.logError("RawMessage", serviceException.getRawMessage());
                }
            }
        });
    }

    // Gets file's metadata
    public void headObject(String objectKey) {
        // Creates a request to get the file's metadata
        HeadObjectRequest head = new HeadObjectRequest(mBucket, objectKey);

        OSSAsyncTask task = mOss.asyncHeadObject(head, new OSSCompletedCallback<HeadObjectRequest, HeadObjectResult>() {
            @Override
            public void onSuccess(HeadObjectRequest request, HeadObjectResult result) {
                OSSLog.logDebug("headObject", "object Size: " + result.getMetadata().getContentLength());
                OSSLog.logDebug("headObject", "object Content Type: " + result.getMetadata().getContentType());

            }

            @Override
            public void onFailure(HeadObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // request exception
                if (clientExcepion != null) {
                    // client side exception,  such as network exception
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // service side exception
                    OSSLog.logError("ErrorCode", serviceException.getErrorCode());
                    OSSLog.logError("RequestId", serviceException.getRequestId());
                    OSSLog.logError("HostId", serviceException.getHostId());
                    OSSLog.logError("RawMessage", serviceException.getRawMessage());
                }
            }
        });
    }

    public void asyncMultipartUpload(String uploadKey, String uploadFilePath) {
        MultipartUploadRequest request = new MultipartUploadRequest(mBucket, uploadKey,
                uploadFilePath);
        request.setCRC64(OSSRequest.CRC64Config.YES);
        request.setProgressCallback(new OSSProgressCallback<MultipartUploadRequest>() {

            @Override
            public void onProgress(MultipartUploadRequest request, long currentSize, long totalSize) {
                OSSLog.logDebug("[testMultipartUpload] - " + currentSize + " " + totalSize, false);
            }
        });
        mOss.asyncMultipartUpload(request, new OSSCompletedCallback<MultipartUploadRequest, CompleteMultipartUploadResult>() {
            @Override
            public void onSuccess(MultipartUploadRequest request, CompleteMultipartUploadResult result) {
            }

            @Override
            public void onFailure(MultipartUploadRequest request, ClientException clientException, ServiceException serviceException) {

            }
        });
    }

    public void asyncResumableUpload(String resumableFilePath) {
        ResumableUploadRequest request = new ResumableUploadRequest(mBucket, mResumableObjectKey, resumableFilePath);
        request.setProgressCallback(new OSSProgressCallback<ResumableUploadRequest>() {
            @Override
            public void onProgress(ResumableUploadRequest request, long currentSize, long totalSize) {
                Log.d("GetObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
                int progress = (int) (100 * currentSize / totalSize);
            }
        });
        OSSAsyncTask task = mOss.asyncResumableUpload(request, new OSSCompletedCallback<ResumableUploadRequest, ResumableUploadResult>() {
            @Override
            public void onSuccess(ResumableUploadRequest request, ResumableUploadResult result) {
            }

            @Override
            public void onFailure(ResumableUploadRequest request, ClientException clientException, ServiceException serviceException) {
            }
        });
    }

    // If the bucket is private, the signed URL is required for the access.
    // Expiration time is specified in the signed URL.
    public void presignURLWithBucketAndKey(final String objectKey) {
        if (objectKey == null || objectKey == "") {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Gets the signed url, the expiration time is 5 minute
                    String url = mOss.presignConstrainedObjectURL(mBucket, objectKey, 5 * 60);
                    OSSLog.logDebug("signContrainedURL", "get url: " + url);
                    // 访问该url
                    Request  request = new Request.Builder().url(url).build();
                    Response resp    = null;

                    resp = new OkHttpClient().newCall(request).execute();

                    if (resp.code() == 200) {
                        OSSLog.logDebug("signContrainedURL", "object size: " + resp.body().contentLength());
                    } else {
                        OSSLog.logDebug("signContrainedURL", "get object failed, error code: " + resp.code()
                                + "error message: " + resp.message());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClientException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Delete a non-empty bucket.
     * Create a bucket, and add files into it.
     * Try to delete the bucket and failure is expected.
     * Then delete file and then delete bucket
     */
    public void deleteNotEmptyBucket(final String bucket, final String filePath) {
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucket);
        // 创建bucket
        try {
            mOss.createBucket(createBucketRequest);
        } catch (ClientException clientException) {
            clientException.printStackTrace();
        } catch (ServiceException serviceException) {
            serviceException.printStackTrace();
        }

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, "test-file", filePath);
        try {
            mOss.putObject(putObjectRequest);
        } catch (ClientException clientException) {
            clientException.printStackTrace();
        } catch (ServiceException serviceException) {
            serviceException.printStackTrace();
        }
        final DeleteBucketRequest deleteBucketRequest = new DeleteBucketRequest(bucket);
        OSSAsyncTask deleteBucketTask = mOss.asyncDeleteBucket(deleteBucketRequest, new OSSCompletedCallback<DeleteBucketRequest, DeleteBucketResult>() {
            @Override
            public void onSuccess(DeleteBucketRequest request, DeleteBucketResult result) {
                OSSLog.logDebug("DeleteBucket", "Success!");
            }

            @Override
            public void onFailure(DeleteBucketRequest request, ClientException clientException, ServiceException serviceException) {
                // request exception
                if (clientException != null) {
                    // client side exception,  such as network exception
                    clientException.printStackTrace();
                }
                if (serviceException != null) {
                    // The bucket to delete is not empty.
                    if (serviceException.getStatusCode() == 409) {
                        // Delete files
                        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, "test-file");
                        try {
                            mOss.deleteObject(deleteObjectRequest);
                        } catch (ClientException clientexception) {
                            clientexception.printStackTrace();
                        } catch (ServiceException serviceexception) {
                            serviceexception.printStackTrace();
                        }
                        // Delete bucket again
                        DeleteBucketRequest deleteBucketRequest1 = new DeleteBucketRequest(bucket);
                        try {
                            mOss.deleteBucket(deleteBucketRequest1);
                        } catch (ClientException clientexception) {
                            clientexception.printStackTrace();
                            return;
                        } catch (ServiceException serviceexception) {
                            serviceexception.printStackTrace();
                            return;
                        }
                        OSSLog.logDebug("DeleteBucket", "Success!");
                    }
                }
            }
        });
    }

    public interface UploadImageListener {
        void onUploadSuccess(String url,int position);

        void onUploadError(ServiceException e);
    }

}
