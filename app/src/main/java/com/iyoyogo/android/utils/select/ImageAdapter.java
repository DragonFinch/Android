package com.iyoyogo.android.utils.select;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.iyoyogo.android.R;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Description：xx <br/>
 * Copyright (c) 2018<br/>
 * This program is protected by copyright laws <br/>
 * Date:2018-06-25 15:34
 *
 * @author 姜文莒
 * @version : 1.0
 */
public class ImageAdapter extends BaseAdapter {
    private int count;
    private Context context;
    private List<HashMap<String, String>> mImgPaths;

    private static List<String> mSelectImg = new LinkedList<>();

    public ImageAdapter(Context context, List<HashMap<String, String>> mDatas) {
        this.context = context;
        this.mImgPaths = mDatas;

    }

    @Override
    public int getCount() {
        return mImgPaths.size();
    }

    @Override
    public Object getItem(int position) {
        return mImgPaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            ;
            convertView = LayoutInflater.from(context).inflate(R.layout.item_check, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
     /*   vh.mImg.setImageResource(R.mipmap.default_error);
        vh.mSelect.setImageResource(R.mipmap.btn_unselected);*/
        vh.mImg.setColorFilter(null);
//        final String filePath=mDirPath+"/"+mImgPaths.get(position);
        //   new  ImageLoader(3, ImageLoader.Type.LIFO).loadImage(mDirPath + "/" + mImgPaths.get(position),vh.mImg);
//        ImageLoader.getInStance(3, ImageLoader.Type.LIFO).loadImage(mDirPath+"/"+mImgPaths.get(position),vh.mImg);
        String filePath = mImgPaths.get(position).get("_data");
        Glide.with(context).load(filePath).into(vh.mImg);
        final ViewHolder finalVh = vh;
        vh.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //已经被选择
                if (mSelectImg.contains(filePath)) {
                    mSelectImg.remove(filePath);
                    finalVh.mImg.setColorFilter(null);
                    finalVh.mSelect.setImageResource(R.mipmap.pic_wxz);
                    count--;
                } else {
                    //未被选中
                    mSelectImg.add(filePath);
                    finalVh.mImg.setColorFilter(Color.parseColor("#77000000"));
                    finalVh.mSelect.setImageResource(R.mipmap.xz);
                    count++;
                }

            }
        });
        return convertView;
    }

    public int setCount() {
        return count;
    }

    public List<String> selectPhoto() {
        if (!mSelectImg.isEmpty()) {
            return mSelectImg;
        }
        return null;
    }

    public static class ViewHolder {
        public View rootView;
        public ImageView mImg;
        public ImageButton mSelect;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mImg = (ImageView) rootView.findViewById(R.id.iv_item);
            this.mSelect = (ImageButton) rootView.findViewById(R.id.ib_select);
        }

    }
}
