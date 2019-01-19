package com.iyoyogo.android.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.comment.CommentBean;
import com.iyoyogo.android.utils.emoji.FaceConversionUtil;

/**
 * @author zhuhui
 * @date 2019/1/18
 * @description
 */
public class CommentAdapter extends BaseQuickAdapter<CommentBean.DataBean.ListBean, BaseViewHolder> {

    public CommentAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentBean.DataBean.ListBean item) {
        Glide.with(mContext).load(item.getUser_logo()).apply(new RequestOptions().circleCrop()).into((ImageView) helper.getView(R.id.iv_user_pic));
        helper.setText(R.id.tv_user_name, item.getUser_nickname())
                .setText(R.id.tv_time, item.getCreate_time())
                .setText(R.id.tv_comment_like_num, item.getCount_praise() + "")
                .setText(R.id.tv_replay_num, item.getCount_comment() + "")
                .setText(R.id.tv_content,FaceConversionUtil.getInstace().getExpressionString(mContext,item.getContent()))
                .setImageResource(R.id.iv_comment_like,item.getIs_my_praise()==1?R.mipmap.ic_comment_liked:R.mipmap.ic_comment_like)
                .addOnClickListener(R.id.ll_like)
                .addOnClickListener(R.id.ll_replay)
                .addOnClickListener(R.id.iv_more);
    }
}
