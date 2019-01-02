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

/**
 * 我的喜欢的适配器
 */
public class MineLikeAdapter extends RecyclerView.Adapter<MineLikeAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    List<PraiseBean.DataBean.ListBean> mList;

    public MineLikeAdapter(Context context, List<PraiseBean.DataBean.ListBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_like_mine, null);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(mList.get(position).getFile_path()).into(holder.img);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnClickListenerListener {
        void setOnClickListener(View v, int position);
    }

    OnClickListenerListener onClickListenerListener;

    @Override
    public void onClick(View v) {
        if (onClickListenerListener != null) {
            onClickListenerListener.setOnClickListener(v, (Integer) v.getTag());
        }
    }

    public void setOnItemClickListener(OnClickListenerListener onItemClickListener) {
        this.onClickListenerListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
        }
    }
}
