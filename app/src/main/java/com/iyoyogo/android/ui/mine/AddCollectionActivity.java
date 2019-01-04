package com.iyoyogo.android.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.AddCollectionAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.collection.AddCollectionBean1;
import com.iyoyogo.android.contract.AddCollectionContract;
import com.iyoyogo.android.presenter.AddCollectionPresenter;
import com.iyoyogo.android.ui.mine.homepage.AddressBookFriendsActivity;
import com.iyoyogo.android.utils.SpUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***
 * 个人主页—关注—添加关注
 */
public class AddCollectionActivity extends BaseActivity<AddCollectionContract.Presenter> implements AddCollectionContract.View {

    @BindView(R.id.collection_my_RecyclerView)
    RecyclerView collectionMyRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_collection;
    }

    protected void initView() {
        super.initView();

    }

    @Override
    protected AddCollectionContract.Presenter createPresenter() {
        return new AddCollectionPresenter(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        String user_id = SpUtils.getString(this, "user_id", null);
        String user_token = SpUtils.getString(this, "user_token", null);
        mPresenter.getAddCollection(user_id, user_token, 1 + "", 20 + "");
    }

    @Override
    public void getAddCollectionSuccess(AddCollectionBean1 addCollectionBean1) {
        List<AddCollectionBean1.DataBean.ListBean> list = addCollectionBean1.getData().getList();
        AddCollectionAdapter adpter = new AddCollectionAdapter(R.layout.item_addconcern_recycleview, list);
        collectionMyRecyclerView.setAdapter(adpter);
        collectionMyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @OnClick({R.id.add_address_book_friends, R.id.message_center_back_im_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_address_book_friends:
                startActivity(new Intent(this, AddressBookFriendsActivity.class));
                break;
            case R.id.message_center_back_im_id:
                finish();
                break;
        }
    }
}
