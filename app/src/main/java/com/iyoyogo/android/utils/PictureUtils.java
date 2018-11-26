package com.iyoyogo.android.utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.iyoyogo.android.app.App;

import java.util.List;

public class PictureUtils {
    /**
     * 	  本次查询的就是针对 相机里面的图片进行搜查,获得最近一排的一张照片,的路径
     *  =/storage/emulated/0/DCIM/Camera  默认值; 路径为空时：默认值
     *  =head.jpg						这个不能为空
     *
     * e  eg:=  /storage/emulated/0/DCIM/Camera/head.jpg
     * @return
     */

    public  void getLocalVideo(List<String> list) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Uri mImageUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = App.getmContext().getContentResolver();
                //只查询jpeg和png的图片  
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Video.Media.MIME_TYPE + "=? or "
                                + MediaStore.Video.Media.MIME_TYPE + "=?",
                        new String[]{"video/mp4"}, MediaStore.Video.Media.DATE_MODIFIED);
                if (mCursor == null) {
                    return;
                }
                while (mCursor.moveToNext()) {
                    //获取图片的路径  
                    String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Video.Media.DATA));
                    if (!TextUtils.isEmpty(path)) {
                        list.add(path);
                    }
                }
                mCursor.close();
            }
        }).start();
    }
    public  void getLocalPhoto(List<String> list) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = App.getmContext().getContentResolver();
                //只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);
                if (mCursor == null) {
                    return;
                }
                while (mCursor.moveToNext()) {
                    //获取图片的路径
                    String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    if (!TextUtils.isEmpty(path)) {
                        list.add(path);
                    }
                }
                mCursor.close();
            }
        }).start();
    }

}
