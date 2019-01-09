package com.iyoyogo.android.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iyoyogo.android.R;
import com.iyoyogo.android.camera.data.FilterItem;

import java.util.List;

/**
 * @author zhuhui
 * @date 2019/1/7
 * @description
 */
public class VideoFilterListAdapter extends BaseQuickAdapter<FilterItem, BaseViewHolder> {

    private int selectPosition = 0;

    public VideoFilterListAdapter(@Nullable List<FilterItem> data) {
        super(R.layout.item_video_filter_list, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, FilterItem item) {
        if (TextUtils.isEmpty(item.getImageUrl())) {
            helper.setImageResource(R.id.iv, item.getImageId());
        } else {
            Glide.with(mContext).load(item.getImageUrl()).apply(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE)).into((ImageView) helper.getView(R.id.iv));
        }
        helper.setGone(R.id.view_select, selectPosition == helper.getLayoutPosition());
        helper.setText(R.id.tv, item.getFilterName());
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }
}
