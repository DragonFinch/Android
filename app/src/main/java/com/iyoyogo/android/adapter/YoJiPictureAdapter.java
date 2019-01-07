package com.iyoyogo.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.home.HomeBean;
import com.iyoyogo.android.ui.common.VideoActivity;
import com.iyoyogo.android.ui.common.WebActivity;
import com.iyoyogo.android.ui.home.yoji.PhotoActivity;
import com.iyoyogo.android.utils.RoundTransform;

import java.util.ArrayList;
import java.util.List;

public class YoJiPictureAdapter extends PagerAdapter {
    private ArrayList<String> images;
    private Context context;

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
        ImageView imageView = view.findViewById(R.id.imageView);
      RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.default_ic).error(R.mipmap.default_ic);
        Glide.with(context)
                .load(images.get(position))
                .apply(requestOptions)
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context,PhotoActivity.class);
                intent.putExtra("url",images.get(position));
                context.startActivity(intent);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
