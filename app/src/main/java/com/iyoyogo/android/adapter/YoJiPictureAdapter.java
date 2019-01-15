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
import android.support.annotation.NonNull;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.iyoyogo.android.R;
import com.iyoyogo.android.app.App;
import com.iyoyogo.android.bean.VipCenterBean;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;
import com.iyoyogo.android.ui.home.yoji.YoJiPictureActivity;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.SpUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class YoJiPictureAdapter extends PagerAdapter {
    private ArrayList<String> images;
    private Context context;
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
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String user_id = SpUtils.getString(context, "user_id", null);
                String user_token = SpUtils.getString(context, "user_token", null);
                DataManager.getFromRemote().setVipCenter(user_id, user_token)
                        .subscribe(new Observer<VipCenterBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(VipCenterBean vipCenterBean) {
                                List<VipCenterBean.DataBean.LevelBean> level = vipCenterBean.getData().getLevel();
                                int score = vipCenterBean.getData().getUser_info().getScore();
                                if (score >= 0 && score < level.get(1).getScore()) {
                                } else if (score >= level.get(1).getScore() && score < level.get(2).getScore()) {
                                } else if (score >= level.get(2).getScore() && score < level.get(3).getScore()) {
                                } else if (score >= level.get(3).getScore() && score < level.get(4).getScore()) {
                                } else if (score >= level.get(4).getScore() && score < level.get(5).getScore()) {
                                    View view = LayoutInflater.from(context).inflate(R.layout.pupup_up_image_download, null);
                                    PopupWindow popup = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                                    TextView tv_img_download = view.findViewById(R.id.tv_img_download);
                                    TextView tv_img_cancel = view.findViewById(R.id.tv_img_cancel);
                                    tv_img_download.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Glide.with(context).asBitmap().load(images.get(position)).into(new SimpleTarget<Bitmap>() {
                                                @Override
                                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                                    saveImage(resource);
                                                    Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
                                                }
                                            });
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
                                } else if (score == level.get(5).getScore()){
                                    View view = LayoutInflater.from(context).inflate(R.layout.pupup_up_image_download, null);
                                    PopupWindow popup = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                                    TextView tv_img_download = view.findViewById(R.id.tv_img_download);
                                    TextView tv_img_cancel = view.findViewById(R.id.tv_img_cancel);
                                    tv_img_download.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Glide.with(context).asBitmap().load(images.get(position)).into(new SimpleTarget<Bitmap>() {
                                                @Override
                                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                                    saveImage(resource);
                                                    Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
                                                }
                                            });
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
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                return false;
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public String saveImage(Bitmap bitmap) {
        File foder = new File(Environment.getExternalStorageDirectory() + "/Yoyogo/Image");
        if (!foder.exists()) {
            foder.mkdirs();
        }
        File file = new File(Environment.getExternalStorageDirectory() + "/Yoyogo/Image", "filter_" + System.currentTimeMillis() + ".png");
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            context.sendBroadcast(intent);
            return file.getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
