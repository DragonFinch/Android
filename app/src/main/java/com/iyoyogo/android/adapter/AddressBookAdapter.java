package com.iyoyogo.android.adapter;


import android.database.Cursor;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.collection.AddressBookBean;
import com.iyoyogo.android.bean.collection.AddressBookPhoneInfoBean;
import com.iyoyogo.android.ui.mine.homepage.CircleImageView;
import com.iyoyogo.android.utils.GlideRoundTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * 通讯录的适配器
 */
public class AddressBookAdapter extends BaseQuickAdapter<AddressBookBean.DataBean.ListBean, BaseViewHolder> {

    public AddressBookAdapter(int layoutResId, @Nullable List<AddressBookBean.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressBookBean.DataBean.ListBean item) {
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.user_nickname, item.getUser_nickname());

        RequestOptions requestOptions = new RequestOptions().centerCrop()
                .transform(new GlideRoundTransform(mContext, 8));
        requestOptions.placeholder(R.mipmap.default_touxiang);
        requestOptions.error(R.mipmap.default_touxiang);
        Glide.with(mContext).load(item.getUser_logo()).apply(requestOptions).into((CircleImageView) helper.getView(R.id.user_logo));

        TextView btu_guanzhu = helper.getView(R.id.btu_guanzhu);
        LinearLayout phone = helper.getView(R.id.phone);
        TextView user_nickname = helper.getView(R.id.user_nickname);
        TextView yaoqing = helper.getView(R.id.yaoqing);

        helper.addOnClickListener(R.id.btu_guanzhu);
        int status = item.getStatus();
        if (status == 0) {//未关注
            btu_guanzhu.setBackgroundResource(R.drawable.bg_collection);
            btu_guanzhu.setText("+关注");
            btu_guanzhu.setTextColor(Color.parseColor("#ffffff"));
        }
        if (status == 1) {//已关注
            btu_guanzhu.setBackgroundResource(R.drawable.bg_delete_yoji);
            btu_guanzhu.setText("已关注");
            btu_guanzhu.setTextColor(Color.parseColor("#888888"));
        }
        if (status == 2) {//互相关注
            btu_guanzhu.setBackgroundResource(R.drawable.bg_delete_yoji);
            btu_guanzhu.setText("互相关注");
            btu_guanzhu.setTextColor(Color.parseColor("#888888"));
        }
        if (item.getUser_id() == 0) {//没有注册的人
            yaoqing.setVisibility(View.VISIBLE);
            phone.setVisibility(View.GONE);
            user_nickname.setVisibility(View.GONE);
            btu_guanzhu.setBackgroundResource(R.drawable.bg_blue);
            btu_guanzhu.setText("邀请");
            btu_guanzhu.setTextColor(Color.parseColor("#5BCBF5"));
            helper.setText(R.id.yaoqing, item.getName());
            Glide.with(mContext).load(R.mipmap.yingshi).into((CircleImageView) helper.getView(R.id.user_logo));
        }
    }
}
