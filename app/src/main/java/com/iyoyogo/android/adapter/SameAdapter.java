package com.iyoyogo.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.SameBean;
import com.iyoyogo.android.camera.utils.ScreenUtils;
import com.iyoyogo.android.utils.util.UiUtils;

/**
 * @author zhuhui
 * @date 2019/1/12
 * @description
 */
public class SameAdapter extends BaseQuickAdapter<SameBean.DataBean.ListBean, BaseViewHolder> {

    public SameAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, SameBean.DataBean.ListBean item) {
        int                            position     = helper.getLayoutPosition();
        ImageView                      iv           = helper.getView(R.id.iv);
        FrameLayout                 itemView     = helper.getView(R.id.item_view);
        GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) itemView.getLayoutParams();

        if (position == 0) {
            layoutParams.height = UiUtils.dip2px(211);
            layoutParams.width = ScreenUtils.getScreenWidth(mContext);

        } else if (position > 0 && position < 3) {
            layoutParams.height = (ScreenUtils.getScreenWidth(mContext) - UiUtils.dip2px(3)) / 2;
            layoutParams.width = layoutParams.height;
        } else {
            layoutParams.height = (ScreenUtils.getScreenWidth(mContext) - UiUtils.dip2px(6)) / 3;
            layoutParams.width = layoutParams.height;
        }
        iv.getLayoutParams().width=layoutParams.width;
        iv.getLayoutParams().height=layoutParams.height;

        int margins = (position + 1) % 3 != 0 && position != 0 ? UiUtils.dip2px(3) : 0;
        layoutParams.setMargins(0, 0, margins, UiUtils.dip2px(3));
        itemView.setLayoutParams(layoutParams);

        helper.setGone(R.id.iv_video, item.getFile_type() != 1)
                .setGone(R.id.ll_user, position <= 2)
                .setGone(R.id.iv_user_pic, position == 0)
                .setGone(R.id.tv_user_name, position == 0);

        helper.setText(R.id.tv_user_name, item.getUser_nickname());
        Glide.with(mContext).load(item.getUser_logo()).apply(new RequestOptions().circleCrop()).into((ImageView) helper.getView(R.id.iv_user_pic));

        helper.setImageResource(R.id.iv_ranking, position == 0 ? R.mipmap.ranking1 : position == 1 ? R.mipmap.ranking2 : R.mipmap.ranking3);
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) helper.getView(R.id.ll_user).getLayoutParams();
        if (position == 1 || position == 2) {
            lp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        } else {
            lp.width = RelativeLayout.LayoutParams.WRAP_CONTENT;

        }
        helper.getView(R.id.ll_user).setLayoutParams(lp);

        Glide.with(mContext).load(item.getFile_path()).apply(new RequestOptions().centerCrop()).into((ImageView) helper.getView(R.id.iv));
    }
}
