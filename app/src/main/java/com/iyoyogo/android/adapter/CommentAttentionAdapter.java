package com.iyoyogo.android.adapter;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.collection.CommendAttentionBean;
import com.iyoyogo.android.ui.home.yoji.UserHomepageActivity;
import com.iyoyogo.android.ui.mine.homepage.CircleImageView;
import com.iyoyogo.android.utils.GlideRoundTransform;

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
        helper.setText(R.id.yoji, item.getCount_yoj()+"");
        helper.setText(R.id.yoxiu, item.getCount_yox()+"");
        helper.addOnClickListener(R.id.btu_guanzhu)
              .addOnClickListener(R.id.user_logo);
        Glide.with(mContext).load(item.getUser_logo()).into((CircleImageView) helper.getView(R.id.user_logo));
        List<CommendAttentionBean.DataBean.ListBean.List4Bean> list_4 = item.getList_4();
        for (int i = 0; i < list_4.size(); i++) {
            file_path = list_4.get(i).getFile_path();
        }

        RequestOptions requestOptions = new RequestOptions().centerCrop()
                .transform(new GlideRoundTransform(mContext, 8));
        requestOptions.placeholder(R.mipmap.default_ic);
        requestOptions.error(R.mipmap.default_ic);

        Glide.with(mContext).load(file_path).apply(requestOptions).into((ImageView) helper.getView(R.id.img_attention_one));
        CircleImageView user_logo = helper.getView(R.id.user_logo);
        user_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用户主页
                Intent intent = new Intent(mContext, UserHomepageActivity.class);
                intent.putExtra("yo_user_id", item.getUser_id());
                mContext.startActivity(intent);
            }
        });

    }
}
