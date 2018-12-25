package com.iyoyogo.android.utils.imagepicker.util;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.iyoyogo.android.utils.imagepicker.bean.ImageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eric on 2017/12/3.
 */

public class ImageFinder {
    public static final String NONE = "none";
    public static final String TYPE_GIF = "image/gif";
    public static final String TYPE_JPEG = "image/jpeg";
    public static final String TYPE_jpg = "image/jpg";
    public static final String TYPE_PNG = "image/png";

    //第二个参数是配置需要屏蔽的图片格式
    public static List<ImageBean> getImages(Context context, String typeShield){
        String shield = typeShield;
        List<ImageBean> list = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            String type = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE));

            if (type.equals(shield)) continue;
            list.add(0,new ImageBean(path,name));
        }
        return list;
    } public static List<ImageBean> getVideo(Context context){
        List<ImageBean> list = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            String type = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE));
            long duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
            list.add(0,new ImageBean(path,name,duration));
        }
        return list;
    }
}
