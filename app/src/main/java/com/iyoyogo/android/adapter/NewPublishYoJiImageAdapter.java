package com.iyoyogo.android.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.yoji.publish.PublishYoJiBean;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * @author zhuhui
 * @date 2019/1/2
 * @description
 */
public class NewPublishYoJiImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements BaseQuickAdapter.OnItemChildClickListener {


    public NewPublishYoJiImageAdapter(@Nullable List<String> data) {
        super(R.layout.item_release_yo_ji_inner_content, data);
        setOnItemChildClickListener(this);

    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.addOnClickListener(R.id.iv_image_delete);
        Glide.with(mContext).load(item).apply(new RequestOptions().centerCrop()).into((ImageView) helper.getView(R.id.iv_image));
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (mData.size() > 1) {
            remove(position);
        } else {
            Toast.makeText(mContext, "不能再少了", Toast.LENGTH_SHORT).show();
        }
    }
}
