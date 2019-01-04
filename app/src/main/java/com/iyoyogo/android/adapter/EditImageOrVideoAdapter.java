package com.iyoyogo.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.iyoyogo.android.R;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * @author zhuhui
 * @date 2019/1/3
 * @description
 */
public class EditImageOrVideoAdapter extends PagerAdapter {

    private List<LocalMedia> mData;
    private Context          mContext;

    public EditImageOrVideoAdapter(List<LocalMedia> data, Context context) {
        this.mData = data;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View      content = LayoutInflater.from(mContext).inflate(R.layout.item_edit_image_or_video, view, false);
        ImageView iv      = content.findViewById(R.id.iv);
        Glide.with(mContext).load(TextUtils.isEmpty(mData.get(position).getCompressPath()) ? mData.get(position).getPath() : mData.get(position).getCompressPath()).into(iv);
        view.addView(content);
        return content;
    }
}
