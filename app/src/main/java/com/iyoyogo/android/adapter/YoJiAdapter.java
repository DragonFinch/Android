package com.iyoyogo.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.R;
import com.iyoyogo.android.utils.RoundTransform;

import java.util.List;

public class YoJiAdapter extends RecyclerView.Adapter<YoJiAdapter.Holder> {
    private List<String> mList;
    private Context context;

    public YoJiAdapter(Context context,List<String> data ) {
        this.mList = data;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.yoji_item_recycler, viewGroup, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.transform(new RoundTransform(context, 8));
//        Glide.with(context).load(mList.get(i).getThumbnail_pic_s()).apply(requestOptions).into(holder.zuji_image);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView zuji_image;

        public Holder(@NonNull View itemView) {
            super(itemView);
            zuji_image = itemView.findViewById(R.id.zuji_image);
        }
    }
}
