package com.iyoyogo.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.iyoyogo.android.R;

import java.util.ArrayList;
import java.util.List;

public class YoJiInnerAdapter extends RecyclerView.Adapter<YoJiInnerAdapter.ViewHolder> {
    Context context;
    List<String> path_list;

    public YoJiInnerAdapter(Context context, ArrayList<String> path_list) {
        this.context = context;
        this.path_list = path_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_release_yo_ji_inner_content, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(path_list.get(position)).into(holder.iv_image);
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                path_list.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return path_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_image, iv_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_delete = itemView.findViewById(R.id.iv_image_delete);
            iv_image = itemView.findViewById(R.id.iv_image);
        }
    }
}
