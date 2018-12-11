package com.iyoyogo.android.utils.imagepicker.component;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by 肖庆鸿 on 2017/12/4.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
        findViewById(itemView);
    }

    /**
     * 转发传入的OnItemChooseCallback与位置
     * @param chooseCallback 点击回调
     * @param position 位置
     */
    public void setChooseCallback(OnItemChooseCallback chooseCallback,int position) {
        if (chooseCallback != null){
            intOnItemChooseCallback(chooseCallback,position);
        }
    }

    /**
     * 传入Item的点击事件的监听器
     * @param listener
     */
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener, int position){
        if (listener != null){
            initOnItemClickListener(listener, position);
        }
    }

    /**
     * 传入Item的长按事件的监听器
     * @param longClickListener
     */
    public void setOnItemLongClickListener(OnRecyclerViewItemLongClickListener longClickListener, int position){
        if (longClickListener != null){
            iniOnItemLongClickListener(longClickListener,position);
        }
    }

    /**
     * 初始化点击事件（开发者自行实现）
     * @param chooseCallback 单项选择回调
     * @param position 当前点击的位置
     */
    abstract public void intOnItemChooseCallback(OnItemChooseCallback chooseCallback, int position);

    /**
     * 初始化Item的点击事件（开发者自行实现）
     * @param listener 监听器
     */
    abstract public void initOnItemClickListener(OnRecyclerViewItemClickListener listener, int position);

    /**
     * 初始化Item的长按事件（开发者自行实现）
     * @param longClickListener 监听器
     */
    abstract public void iniOnItemLongClickListener(OnRecyclerViewItemLongClickListener longClickListener, int position);
    /**
     * 通过id匹配控件（开发者自行实现）
     * @param itemView 父布局
     */
    abstract protected void findViewById(View itemView);

    /**
     * 用于装载数据（开发者自行实现）
     * @param position 当前位置
     */
    abstract public void onBind(int position);
}
