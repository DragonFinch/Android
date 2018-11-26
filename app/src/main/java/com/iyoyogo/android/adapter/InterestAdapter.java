package com.iyoyogo.android.adapter;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.login.interest.InterestBean;
import com.iyoyogo.android.camera.utils.DensityUtil;
import com.iyoyogo.android.camera.utils.ScreenUtils;

import java.util.List;

public class InterestAdapter extends BaseQuickAdapter<InterestBean.DataBean.ListBean, BaseViewHolder> {


    public InterestAdapter( List<InterestBean.DataBean.ListBean> data) {
        super(R.layout.item_classify, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InterestBean.DataBean.ListBean item) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) helper.itemView.getLayoutParams();
        int screenWidth = ScreenUtils.getScreenWidth(mContext);
        int itemWith = (screenWidth - DensityUtil.dip2px(mContext, 70)) / 4;
        layoutParams.width = itemWith;
//        RelativeLayout rl_img_content = helper.getView(R.id.rl_img_content);
//        ViewGroup.LayoutParams imgContentLayoutParams = rl_img_content.getLayoutParams();
//        imgContentLayoutParams.height = itemWith;
//        imgContentLayoutParams.width = itemWith;

        final ImageView img = helper.getView(R.id.img);
        ImageView choice = helper.getView(R.id.choice_icon);


        RelativeLayout.LayoutParams imgLayoutParams = (RelativeLayout.LayoutParams) img.getLayoutParams();
        int imgMargin = DensityUtil.dip2px(mContext, 5);
        imgLayoutParams.setMargins(imgMargin, imgMargin, imgMargin, imgMargin);
        imgLayoutParams.width = itemWith - 2 * imgMargin;
        imgLayoutParams.height = itemWith - 2 * imgMargin;
//        helper.setVisible(R.id.choice_icon, item.isCheck());
        Glide.with(mContext).load(item.getLogo()).apply(new RequestOptions().circleCrop()).into(img);
        helper.addOnClickListener(R.id.choice_icon);
        if (item.isFlag()){
            choice.setVisibility(View.VISIBLE);
        }else {
            choice.setVisibility(View.INVISIBLE);
        }

        helper.setText(R.id.tag_name, item.getInterest());
    }
}
