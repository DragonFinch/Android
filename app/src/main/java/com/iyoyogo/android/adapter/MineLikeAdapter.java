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
import com.iyoyogo.android.bean.mine.PraiseBean;

import java.util.List;

public class MineLikeAdapter extends RecyclerView.Adapter<MineLikeAdapter.ViewHolder> {
    private Context context;
    List<PraiseBean.DataBean.ListBean> mList;
    public MineLikeAdapter(Context context, List<PraiseBean.DataBean.ListBean> mList) {
        this.context=context;
        this.mList=mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(context).inflate(R.layout.item_like_mine, null);

        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(mList.get(position).getFile_path()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
        }
    }
}
