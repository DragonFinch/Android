package com.iyoyogo.android.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.collection.CollectionFolderBean;

/**
 * @author zhuhui
 * @date 2019/1/17
 * @description
 */
public class AddCollectPopupAdapter extends BaseQuickAdapter<CollectionFolderBean.DataBean.ListBean, BaseViewHolder> {

    public AddCollectPopupAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectionFolderBean.DataBean.ListBean item) {
        helper.setText(R.id.tv, item.getName());
    }
}
