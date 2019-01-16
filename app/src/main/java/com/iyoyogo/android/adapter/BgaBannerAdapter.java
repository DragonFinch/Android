package com.iyoyogo.android.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by ZhuHui on 18/6/25.
 */

public class BgaBannerAdapter implements BGABanner.Adapter<ImageView, Object> {

    private Context mContext;

    public BgaBannerAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public void fillBannerItem(BGABanner bgaBanner, ImageView imageView, @Nullable Object s, int i) {
        Glide.with(mContext).load(s).apply(new RequestOptions().centerCrop()).into(imageView);
    }
}
