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

public class YoJiDetailAdapter extends RecyclerView.Adapter<YoJiDetailAdapter.Holder> implements View.OnClickListener {
    private List<String> mList;
    private Context context;

    public YoJiDetailAdapter(Context context, List<String> data ) {
        this.mList = data;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_yoji_detail, viewGroup, false);
        Holder holder = new Holder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.transform(new RoundTransform(context, 8));
//        Glide.with(context).load(mList.get(i).getThumbnail_pic_s()).apply(requestOptions).into(holder.zuji_image);
        holder.itemView.setTag(position);
        if (position==mList.size()-1){
            holder.plane.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnClickListener {
        void onClick(View v, int position);
    }

    private OnClickListener onClickListener;

    public void setOnItemClickListener(OnClickListener onItemClickListener) {
        this.onClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (onClickListener != null) {
            onClickListener.onClick(v, (Integer) v.getTag());
        }
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView zuji_image,plane;

        public Holder(@NonNull View itemView) {
            super(itemView);
            zuji_image = itemView.findViewById(R.id.zuji_image);
            plane = itemView.findViewById(R.id.plane);
        }
    }
}
