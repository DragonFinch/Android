package com.iyoyogo.android.adapter;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.flexbox.FlexboxLayout;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.yoji.publish.PublishYoJiBean;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuhui
 * @date 2019/1/2
 * @description
 */
public class NewPublishYoJiAdapter extends BaseQuickAdapter<PublishYoJiBean.DataBean.ListBean, BaseViewHolder> {


    public NewPublishYoJiAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PublishYoJiBean.DataBean.ListBean item) {
        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        List<String> list = new ArrayList<>();
        if (item.getLocalMedia() != null && item.getLocalMedia().size() > 0) {
            for (LocalMedia localMedia : item.getLocalMedia()) {
                list.add(TextUtils.isEmpty(localMedia.getCompressPath()) ? localMedia.getPath() : localMedia.getCompressPath());
            }
        } else {
            list.addAll(item.getLogos());
        }
        NewPublishYoJiImageAdapter adapter = new NewPublishYoJiImageAdapter(list);
        recyclerView.setAdapter(adapter);

        helper.addOnClickListener(R.id.tv_insert)
                .addOnClickListener(R.id.tv_delete)
                .addOnClickListener(R.id.iv_add_image)
                .addOnClickListener(R.id.tv_end_date)
                .addOnClickListener(R.id.tv_start_date)
                .addOnClickListener(R.id.ll_location)
                .addOnClickListener(R.id.ll_tag);

        helper.setText(R.id.tv_start_date, item.getStart_date()).setText(R.id.tv_end_date, item.getEnd_date());

        helper.setGone(R.id.tv_tag, item.getLabels() == null || item.getLabels().size() == 0);
        FlexboxLayout flexTag = helper.getView(R.id.flex_tag);
        flexTag.removeAllViews();
        if (item.getLabels() != null && item.getLabels().size() > 0) {
            for (PublishYoJiBean.DataBean.ListBean.LabelsBean labelsBean : item.getLabels()) {
                View         view = LayoutInflater.from(mContext).inflate(R.layout.item_public_yo_ji_tag, null);
                TextView     tv   = view.findViewById(R.id.tv);
                LinearLayout llBg = view.findViewById(R.id.ll_bg);
                tv.setText(labelsBean.getLabel());


                tv.setCompoundDrawablesWithIntrinsicBounds(labelsBean.getType() == 1 ?   mContext.getResources().getDrawable(R.mipmap.make): labelsBean.getType() == 2 ?   mContext.getResources().getDrawable(R.mipmap.donot) :   mContext.getResources().getDrawable(R.mipmap.self),null, null, null);

                tv.setTextColor(ContextCompat.getColor(mContext, labelsBean.getType() == 1 ? R.color.blue_color : labelsBean.getType() == 2 ? R.color.orgeen_color : R.color.color_exclusive));
                llBg.setBackgroundResource(labelsBean.getType() == 1 ? R.drawable.label_bg_blue : labelsBean.getType() == 2 ? R.drawable.label_bg_orange : R.drawable.label_bg_red);
                flexTag.addView(view);
            }
        }

        helper.setText(R.id.tv_location, item.getPosition_name());
    }


}
