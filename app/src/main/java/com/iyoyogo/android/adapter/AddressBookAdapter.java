package com.iyoyogo.android.adapter;


import android.database.Cursor;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.collection.AddCollectionBean1;
import com.iyoyogo.android.bean.collection.AddressBookBean;
import com.iyoyogo.android.bean.collection.AddressBookPhoneInfoBean;
import com.iyoyogo.android.ui.mine.homepage.CircleImageView;

import java.util.ArrayList;
import java.util.List;

public class AddressBookAdapter extends BaseQuickAdapter<AddressBookBean.DataBean.ListBean, BaseViewHolder> {

    private String name;

    public AddressBookAdapter(int layoutResId, @Nullable List<AddressBookBean.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressBookBean.DataBean.ListBean item) {
        List<AddressBookPhoneInfoBean> list = new ArrayList<AddressBookPhoneInfoBean>();
        Cursor cursor = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        //moveToNext方法返回的是一个boolean类型的数据
        while (cursor.moveToNext()) {
            //读取通讯录的姓名
            name = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            //读取通讯录的号码
            String number = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            AddressBookPhoneInfoBean phoneInfo = new AddressBookPhoneInfoBean(name, number);
            list.add(phoneInfo);
            helper.setText(R.id.name, name);
            Glide.with(mContext).load(item.getUser_logo()).into((CircleImageView) helper.getView(R.id.user_logo));
        }

        TextView btu_guanzhu = helper.getView(R.id.btu_guanzhu);
        LinearLayout phone = helper.getView(R.id.phone);
        int status = item.getStatus();
        if (status == 0) {//未关注
            btu_guanzhu.setBackgroundResource(R.drawable.bg_collection);
            btu_guanzhu.setText("+关注");
            btu_guanzhu.setTextColor(Color.parseColor("#fff"));
        }
        if (status == 1) {//关注
            btu_guanzhu.setBackgroundResource(R.drawable.bg_delete_yoji);
            btu_guanzhu.setText("关注");
            btu_guanzhu.setTextColor(Color.parseColor("#888"));
        }
        if (status == 2) {//互相关注
            btu_guanzhu.setBackgroundResource(R.drawable.bg_delete_yoji);
            btu_guanzhu.setText("互相关注");
            btu_guanzhu.setTextColor(Color.parseColor("#888"));
        }
        if (item.getUser_id() == 0) {//没有注册的人
            btu_guanzhu.setBackgroundResource(R.drawable.bg_blue);
            btu_guanzhu.setText("邀请");
            btu_guanzhu.setTextColor(Color.parseColor("#5BCBF5"));
            helper.setText(R.id.user_nickname, name);
            phone.setVisibility(View.GONE);
            Glide.with(mContext).load(R.mipmap.yingshi).into((CircleImageView) helper.getView(R.id.user_logo));
        }
    }
}
