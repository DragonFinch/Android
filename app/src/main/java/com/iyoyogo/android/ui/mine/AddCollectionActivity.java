package com.iyoyogo.android.ui.mine;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.githang.statusbar.StatusBarCompat;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.AddCollectionAdapter;
import com.iyoyogo.android.app.Constants;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.collection.AddCollectionBean1;
import com.iyoyogo.android.bean.mine.MineMessageBean;
import com.iyoyogo.android.bean.mine.center.UserCenterBean;
import com.iyoyogo.android.contract.AddCollectionContract;
import com.iyoyogo.android.presenter.AddCollectionPresenter;
import com.iyoyogo.android.ui.mine.homepage.AddressBookFriendsActivity;
import com.iyoyogo.android.ui.mine.homepage.Personal_homepage_Activity;
import com.iyoyogo.android.utils.SpUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.editorpage.ShareActivity;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    String user_token;
    TextView btu_guanzhu;
    private String nickName;
    private String userLogo;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_collection;
    }

    protected void initView() {
        super.initView();
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.white));

    }

    @Override
    protected AddCollectionContract.Presenter createPresenter() {
        return new AddCollectionPresenter(AddCollectionActivity.this, this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        user_id = SpUtils.getString(this, "user_id", null);
        user_token = SpUtils.getString(this, "user_token", null);
        mPresenter.getAddCollection(AddCollectionActivity.this, user_id, user_token, 1 + "", 20 + "");
    }

    @Override
    public void getAddCollectionSuccess(AddCollectionBean1 addCollectionBean1) {
        List<AddCollectionBean1.DataBean.ListBean> list = addCollectionBean1.getData().getList();
        AddCollectionAdapter adpter = new AddCollectionAdapter(R.layout.item_addconcern_recycleview, list);
        collectionMyRecyclerView.setAdapter(adpter);
        collectionMyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adpter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                btu_guanzhu = view.findViewById(R.id.tv_guanzhu);
                mPresenter.addAttention(AddCollectionActivity.this, user_id, user_token, list.get(position).getUser_id());
            }
        });
    }

    @Override
    public void addAttentionSuccess(AttentionBean attentionBean) {
        int status = attentionBean.getData().getStatus();
        if (status == 1) {
            btu_guanzhu.setBackgroundResource(R.drawable.bg_delete_yoji);
            btu_guanzhu.setText("已关注");
            btu_guanzhu.setTextColor(Color.parseColor("#888888"));
        } else {
            btu_guanzhu.setBackgroundResource(R.drawable.bg_collection);
            btu_guanzhu.setText("+关注");
            btu_guanzhu.setTextColor(Color.parseColor("#ffffff"));
        }
    }

    @OnClick({R.id.add_address_book_friends, R.id.message_center_back_im_id, R.id.Invite_Wechat_Friends, R.id.Invite_Weibo_Friends, R.id.Invite_QQ_Friends})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_address_book_friends://通讯录
                if (Build.VERSION.SDK_INT >= 23) {
                    String[] mPermissionList = new String[]{
                            Manifest.permission.READ_CONTACTS};
                    ActivityCompat.requestPermissions(this, mPermissionList, 123);
                }
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
//                shareQQ(AddCollectionActivity.this, "记录旅行每一刻，快来yoyoGo跟我一起玩呀！" + Constants.BASE_URL + "index.php/home/share/download_all.html");
                break;
        }
    }

    private void shareWeb(SHARE_MEDIA share_media) {
        /*80002/yo_id/4143*/
        String url = Constants.BASE_URL + "index.php/home/share/download_all.html";
        UMWeb web = new UMWeb(url);
        web.setTitle("  ");//标题
        UMImage thumb = new UMImage(getApplicationContext(), R.mipmap.logo);
        web.setThumb(thumb);  //缩略图

        web.setDescription("记录旅行每一刻，快来yoyoGo来跟我一起玩呀，搜索" + "“" + nickName + "”" + "就能找到我！");//描述

        new ShareAction(AddCollectionActivity.this)
                .withMedia(web)
                .setPlatform(share_media)
                .share();
//        new ShareAction(AddCollectionActivity.this).setPlatform(share_media).withText("记录旅行每一刻，快来yoyoGo跟我一起玩呀！" + url).share();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void Name(MineMessageBean.DataBean dataBean) {
        nickName = dataBean.getUser_nickname();
        userLogo = dataBean.getUser_logo();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * @param mContext 上下文
     * @param content  要分享的文本
     */
    public static void shareQQ(Context mContext, String content) {
        if (isQQClientAvailable(mContext)) {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
            intent.putExtra(Intent.EXTRA_TEXT, content);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity"));
            mContext.startActivity(intent);
        } else {
            Toast.makeText(mContext, "您需要安装QQ客户端", Toast.LENGTH_LONG).show();
        }
    }

    // 是否存在QQ客户端
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
