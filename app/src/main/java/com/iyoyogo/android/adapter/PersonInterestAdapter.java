package com.iyoyogo.android.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iyoyogo.android.R;

import java.util.List;

/**
 * 个人主页的兴趣
 */
public class PersonInterestAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public PersonInterestAdapter(List<String> data) {
        super(R.layout.item_interest_person, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_interest, item);
    }
}
