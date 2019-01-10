package com.iyoyogo.android.ui.mine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.githang.statusbar.StatusBarCompat;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.AddCollectionAdapter;
import com.iyoyogo.android.app.Constants;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.collection.AddCollectionBean1;
import com.iyoyogo.android.contract.AddCollectionContract;
import com.iyoyogo.android.presenter.AddCollectionPresenter;
import com.iyoyogo.android.ui.mine.homepage.AddressBookFriendsActivity;
import com.iyoyogo.android.ui.mine.homepage.Personal_homepage_Activity;
import com.iyoyogo.android.utils.SpUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

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
    String user_id;
    String yo_user_id;
    String user_nickname;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_collection;
    }

    protected void initView() {
        super.initView();
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);

    }

    @Override
    protected AddCollectionContract.Presenter createPresenter() {
        return new AddCollectionPresenter(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        user_id = SpUtils.getString(this, "user_id", null);
        String user_token = SpUtils.getString(this, "user_token", null);
        mPresenter.getAddCollection(user_id, user_token, 1 + "", 20 + "");
    }

    @Override
    public void getAddCollectionSuccess(AddCollectionBean1 addCollectionBean1) {
        List<AddCollectionBean1.DataBean.ListBean> list = addCollectionBean1.getData().getList();
        AddCollectionAdapter adpter = new AddCollectionAdapter(R.layout.item_addconcern_recycleview, list);
        collectionMyRecyclerView.setAdapter(adpter);
        collectionMyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        yo_user_id = list.get(0).getUser_id();
        user_nickname = list.get(0).getUser_nickname();
    }

    @OnClick({R.id.add_address_book_friends, R.id.message_center_back_im_id, R.id.Invite_Wechat_Friends, R.id.Invite_Weibo_Friends, R.id.Invite_QQ_Friends})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_address_book_friends://通讯录
                startActivity(new Intent(this, AddressBookFriendsActivity.class));
                break;
            case R.id.message_center_back_im_id:
                finish();
                break;
            case R.id.Invite_Wechat_Friends://微信
                shareWeb(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.Invite_Weibo_Friends://微博
                shareWeb(SHARE_MEDIA.SINA);
                break;
            case R.id.Invite_QQ_Friends://QQ
                shareWeb(SHARE_MEDIA.QQ);
                break;
        }
    }
    private void shareWeb(SHARE_MEDIA share_media) {
        /*80002/yo_id/4143*/
        String url = Constants.BASE_URL+ "index.php/home/share/download_all.html";
        UMWeb web = new UMWeb(url);
        web.setTitle("YoYoGo");//标题
        UMImage thumb = new UMImage(getApplicationContext(), R.mipmap.logo);
        web.setThumb(thumb);  //缩略图

        web.setDescription("");//描述

        new ShareAction(AddCollectionActivity.this)
                .withMedia(web)
                .setPlatform(share_media)
                .share();
    }

}
