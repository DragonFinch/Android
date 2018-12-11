package com.iyoyogo.android.utils.imagepicker.component;

import android.support.v7.widget.RecyclerView;

/**
 * Created by 肖庆鸿 on 2017/12/4.
 */

public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private OnItemChooseCallback chooseCallback;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    private OnRecyclerViewItemLongClickListener onRecyclerViewItemLongClickListener;

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.setChooseCallback(chooseCallback,position);
        holder.setOnItemClickListener(onRecyclerViewItemClickListener,position);
        holder.setOnItemLongClickListener(onRecyclerViewItemLongClickListener,position);
        holder.onBind(position);
    }

    public void setChooseCallback(OnItemChooseCallback chooseCallback) {
        this.chooseCallback = chooseCallback;

    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public void setOnRecyclerViewItemLongClickListener(OnRecyclerViewItemLongClickListener onRecyclerViewItemLongClickListener) {
        this.onRecyclerViewItemLongClickListener = onRecyclerViewItemLongClickListener;
    }
}
