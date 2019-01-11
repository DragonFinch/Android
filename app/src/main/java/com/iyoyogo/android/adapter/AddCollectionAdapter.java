package com.iyoyogo.android.adapter;


import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.collection.AddCollectionBean1;
import com.iyoyogo.android.ui.mine.homepage.CircleImageView;
import com.iyoyogo.android.utils.GlideRoundTransform;

import java.util.List;

/**
 * 添加收藏的适配器
 */
public class AddCollectionAdapter extends BaseQuickAdapter<AddCollectionBean1.DataBean.ListBean, BaseViewHolder> {

    public AddCollectionAdapter(int layoutResId, @Nullable List<AddCollectionBean1.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddCollectionBean1.DataBean.ListBean item) {
        helper.setText(R.id.user_nickname, item.getUser_nickname());
        helper.setText(R.id.count_yoj, item.getCount_yoj()+"");
        helper.setText(R.id.count_yox, item.getCount_yox()+"");
        RequestOptions requestOptions = new RequestOptions().centerCrop()
                .transform(new GlideRoundTransform(mContext, 8));
        requestOptions.placeholder(R.mipmap.default_touxiang);
        requestOptions.error(R.mipmap.default_touxiang);
        Glide.with(mContext).load(item.getUser_logo()).apply(requestOptions).into((CircleImageView) helper.getView(R.id.user_logo));
        helper.addOnClickListener(R.id.tv_guanzhu);
    }
}
