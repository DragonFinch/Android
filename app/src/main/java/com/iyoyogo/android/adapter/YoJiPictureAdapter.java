package com.iyoyogo.android.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.R;
import com.iyoyogo.android.ui.home.yoji.YoJiPictureActivity;
import com.iyoyogo.android.utils.DensityUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class YoJiPictureAdapter extends PagerAdapter {
    private ArrayList<String> images;
    private Context context;
    String image;
    ImageView imageView;

    public YoJiPictureAdapter(Context context, ArrayList<String> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images == null ? 0 : images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.detail_item, null);
        imageView = view.findViewById(R.id.imageView);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.default_ic).error(R.mipmap.default_ic);
        Glide.with(context)
                .load(images.get(position))
                .apply(requestOptions)
                .into(imageView);
        image = images.get(position);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                Image();
                return false;
            }
        });

//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setClass(context,PhotoActivity.class);
//                intent.putExtra("url",images.get(position));
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        });
        container.addView(view);
        return view;
    }

    private void Image() {
        View view = LayoutInflater.from(context).inflate(R.layout.pupup_up_image_download, null);
        PopupWindow popup = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        TextView tv_img_download = view.findViewById(R.id.tv_img_download);
        TextView tv_img_cancel = view.findViewById(R.id.tv_img_cancel);
        tv_img_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                merge();
                popup.dismiss();
            }
        });
        tv_img_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });

        popup.showAtLocation(view, Gravity.BOTTOM, 0, 0);

    }

    private void merge() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
////                File zhang = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "zhang.jpg");
//                File zhang = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), image);
//
//                try {
//                    Bitmap bitmap1 = BitmapFactory.decodeStream(new FileInputStream(zhang));
//
//                    File zhangphil = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), image);
////                    File zhangphil = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "zhangphil.jpg");
//                    if (!zhangphil.exists())
//                        zhangphil.createNewFile();
//
//                    int textSize = 60;
//
//                    //中间高度位置添加水印文字。
//                    Bitmap bitmap2 = addTextWatermark(bitmap1, "blog.csdn.net/zhangphil", textSize, Color.RED, 0, bitmap1.getHeight() / 2, true);
//                    save(bitmap2, zhangphil, Bitmap.CompressFormat.JPEG, true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                File zhang = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), image);
                try {
                    //原图片。
                    Bitmap bitmap1 = BitmapFactory.decodeStream(new FileInputStream(zhang));
                    //水印图。
                    Bitmap bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo);
                    //原图片添加水印后形成新的文件。
                    File zhangphil = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), image);
                    if (!zhangphil.exists())
                        zhangphil.createNewFile();
                    //原图片添加水印后形成的新Bitmap。在原图片的最左边和做顶部开始添加。
                    //如果是中间或者底部需要计算x,y的坐标位置。
                    Bitmap newbitmap = addImageWatermark(bitmap1, bitmap2, 0, 0);
                    //把添加水印后的Bitmap保存到文件。
                    save(newbitmap, zhangphil, Bitmap.CompressFormat.JPEG, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 添加图片水印。
     *
     * @param src  源图片
     * @param watermark 图片水印
     * @param x   起始坐标x
     * @param y   起始坐标y
     * @return 带有图片水印的图片
     */
    public static Bitmap addImageWatermark(Bitmap src, Bitmap watermark, int x, int y) {
        Bitmap retBmp = src.copy(src.getConfig(), true);
        Canvas canvas = new Canvas(retBmp);
        canvas.drawBitmap(watermark, x, y, null);
        return retBmp;
    }


    /**
     * 给一张Bitmap添加水印文字。
     *
     * @param src      源图片
     * @param content  水印文本
     * @param textSize 水印字体大小 ，单位pix。
     * @param color    水印字体颜色。
     * @param x        起始坐标x
     * @param y        起始坐标y
     * @param recycle  是否回收
     * @return 已经添加水印后的Bitmap。
     */
    public static Bitmap addTextWatermark(Bitmap src, String content, int textSize, int color, float x, float y, boolean recycle) {
        if (isEmptyBitmap(src) || content == null)
            return null;
        Bitmap ret = src.copy(src.getConfig(), true);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Canvas canvas = new Canvas(ret);
        paint.setColor(color);
        paint.setTextSize(textSize);
        Rect bounds = new Rect();
        paint.getTextBounds(content, 0, content.length(), bounds);
        canvas.drawText(content, x, y, paint);
        if (recycle && !src.isRecycled())
            src.recycle();
        return ret;
    }

    /**
     * 保存图片到文件File。
     *
     * @param src     源图片
     * @param file    要保存到的文件
     * @param format  格式
     * @param recycle 是否回收
     * @return true 成功 false 失败
     */
    public static boolean save(Bitmap src, File file, Bitmap.CompressFormat format, boolean recycle) {
        if (isEmptyBitmap(src))
            return false;

        OutputStream os;
        boolean ret = false;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            ret = src.compress(format, 100, os);
            if (recycle && !src.isRecycled())
                src.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * Bitmap对象是否为空。
     */
    public static boolean isEmptyBitmap(Bitmap src) {
        return src == null || src.getWidth() == 0 || src.getHeight() == 0;
    }

}
