package com.iyoyogo.android.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.collection.CollectionFolderBean;

import java.util.List;

/**
 * 收藏夹的适配器
 */
public class CollectionFolderAdapter extends BaseQuickAdapter<CollectionFolderBean.DataBean.ListBean,BaseViewHolder> {
    public CollectionFolderAdapter(List<CollectionFolderBean.DataBean.ListBean> data) {
        super(R.layout.collection_folder, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectionFolderBean.DataBean.ListBean item) {
        helper.setText(R.id.tv_folder,item.getName());
    }
}
