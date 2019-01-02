package com.iyoyogo.android.adapter;


import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.collection.CommendAttentionBean;
import com.iyoyogo.android.ui.mine.homepage.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐 给我 要关注的人
 */

public class CommentAttentionAdapter extends BaseQuickAdapter<CommendAttentionBean.DataBean.ListBean, BaseViewHolder> {

    private String file_path;

    public CommentAttentionAdapter(int layoutResId, @Nullable List<CommendAttentionBean.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommendAttentionBean.DataBean.ListBean item) {
        helper.setText(R.id.user_nick_name, item.getUser_nickname());
        helper.addOnClickListener(R.id.btu_guanzhu);
        Glide.with(mContext).load(item.getUser_logo()).into((CircleImageView) helper.getView(R.id.user_logo));
        List<CommendAttentionBean.DataBean.ListBean.List4Bean> list_4 = item.getList_4();
        for (int i = 0; i < list_4.size(); i++) {
            file_path = list_4.get(i).getFile_path();
        }
        Glide.with(mContext).load(file_path).into((ImageView) helper.getView(R.id.img_attention_one));
    }
}
