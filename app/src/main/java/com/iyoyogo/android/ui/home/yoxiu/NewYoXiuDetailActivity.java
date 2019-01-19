package com.iyoyogo.android.ui.home.yoxiu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.CommentAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.LikeBean;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.collection.AddCollectionBean;
import com.iyoyogo.android.bean.collection.CollectionFolderBean;
import com.iyoyogo.android.bean.comment.CommentBean;
import com.iyoyogo.android.bean.yoxiu.YoXiuDetailBean;
import com.iyoyogo.android.contract.NewYoXiuDetailContract;
import com.iyoyogo.android.contract.YoXiuDetailContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.presenter.NewYoXiuDetailPresenter;
import com.iyoyogo.android.presenter.YoXiuDetailPresenter;
import com.iyoyogo.android.ui.home.yoji.ReplyDiscussActivity;
import com.iyoyogo.android.ui.mine.homepage.Personal_homepage_Activity;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.util.EmoticonsKeyboardUtils;
import com.iyoyogo.android.utils.util.KeyboardChangeListener;
import com.iyoyogo.android.widget.AddCollectPopup;
import com.iyoyogo.android.widget.CommentPopup;
import com.iyoyogo.android.widget.CommonPopup;
import com.iyoyogo.android.widget.ReportPopup;
import com.iyoyogo.android.widget.SharePopup;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class NewYoXiuDetailActivity extends BaseActivity<NewYoXiuDetailPresenter> implements NewYoXiuDetailContract.View, BaseQuickAdapter.OnItemChildClickListener, AddCollectPopup.AddCollectListener, CommonPopup.OnCommonClick, ReportPopup.OnReportListener, CommentPopup.OnSendComment, KeyboardChangeListener.KeyBoardListener {

    @BindView(R.id.tv_title)
    TextView     mTvTitle;
    @BindView(R.id.iv_back)
    ImageView    mIvBack;
    @BindView(R.id.iv_share)
    ImageView    mIvShare;
    @BindView(R.id.iv_cover)
    ImageView    mIvCover;
    @BindView(R.id.tv_select_address)
    TextView     mTvSelectAddress;
    @BindView(R.id.iv_user_pic)
    ImageView    mIvUserPic;
    @BindView(R.id.tv_user_name)
    TextView     mTvUserName;
    @BindView(R.id.tv_follow)
    TextView     mTvFollow;
    @BindView(R.id.tv_content)
    TextView     mTvContent;
    @BindView(R.id.tv_read_num)
    TextView     mTvReadNum;
    @BindView(R.id.tv_time)
    TextView     mTvTime;
    @BindView(R.id.tv_comment_num)
    TextView     mTvCommentNum;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_comment)
    TextView     mTvComment;
    @BindView(R.id.iv_emoji)
    ImageView    mIvEmoji;
    @BindView(R.id.iv_like)
    ImageView    mIvLike;
    @BindView(R.id.tv_like_num)
    TextView     mTvLikeNum;
    @BindView(R.id.ll_like)
    LinearLayout mLlLike;
    @BindView(R.id.iv_collect)
    ImageView    mIvCollect;
    @BindView(R.id.tv_collect_num)
    TextView     mTvCollectNum;
    @BindView(R.id.ll_collect)
    LinearLayout mLlCollect;
    @BindView(R.id.ll_more_comment)
    LinearLayout mLlMoreComment;

    private CommentAdapter mCommentAdapter;
    private String         userId;
    private String         token;

    private int id;

    private YoXiuDetailBean.DataBean            mData;
    private List<CommentBean.DataBean.ListBean> commentData;

    private AddCollectPopup mAddCollectPopup;
    private CommonPopup     mCommonPopup;
    private SharePopup      mSharePopup;
    private ReportPopup     mReportPopup;

    private CommentPopup mCommentPopup;

    private int                    optionPosition = 0;
    private KeyboardChangeListener keyboardChangeListener;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_yo_xiu_detail;
    }

    @Override
    protected NewYoXiuDetailPresenter createPresenter() {
        return new NewYoXiuDetailPresenter(NewYoXiuDetailActivity.this,this);
    }

    @Override
    protected void initView() {
        super.initView();
        keyboardChangeListener = new KeyboardChangeListener(this);
        keyboardChangeListener.setKeyBoardListener(this);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCommentAdapter = new CommentAdapter(R.layout.item_new_comment);
        mCommentAdapter.setOnItemChildClickListener(this);
        mCommentAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.item_empty, null));
        mRecyclerView.setAdapter(mCommentAdapter);
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        mAddCollectPopup = new AddCollectPopup(this);
        mAddCollectPopup.setAddCollectListener(this);

        mCommonPopup = new CommonPopup(this);
        mCommonPopup.setOnCommonClick(this);

        mSharePopup = new SharePopup(this);

        mReportPopup = new ReportPopup(this);
        mReportPopup.setOnReportListener(this);

        mCommentPopup = new CommentPopup(this);
        mCommentPopup.setOnSendComment(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        id = getIntent().getIntExtra("id", 0);
        userId = SpUtils.getString(this, "user_id", null);
        token = SpUtils.getString(this, "user_token", null);
        mPresenter.getDetail(NewYoXiuDetailActivity.this,userId, token, id);
        mPresenter.getCommentList(NewYoXiuDetailActivity.this,userId, token, 1, id, 0);
    }


    @OnClick({R.id.iv_back, R.id.iv_share, R.id.iv_cover, R.id.tv_select_address, R.id.iv_user_pic, R.id.tv_user_name, R.id.tv_follow, R.id.ll_more_comment, R.id.tv_comment, R.id.iv_emoji, R.id.ll_like, R.id.ll_collect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:
                mSharePopup.setTitle(mData.getFile_desc())
                        .setDesc(mData.getFile_desc())
                        .setImage(mData.getFile_path())
                        .setYoId(id)
                        .showPopupWindow();
                break;
            case R.id.iv_cover:
                break;
            case R.id.tv_select_address:
                break;
            case R.id.iv_user_pic:
            case R.id.tv_user_name:
                startActivity(new Intent(this, Personal_homepage_Activity.class)
                        .putExtra("yo_user_id", mData.getUser_id()));
                break;
            case R.id.tv_follow:
                mPresenter.addAttention(NewYoXiuDetailActivity.this,userId, token, Integer.valueOf(mData.getUser_id()));
                break;
            case R.id.ll_more_comment:
                startActivity(new Intent(this, AllCommentActivity.class).putExtra("id", id));
                break;
            case R.id.tv_comment:
                mCommentPopup.setShowInputMethod(true);
                mCommentPopup.showPopupWindow();
                break;
            case R.id.iv_emoji:
                mCommentPopup.setShowInputMethod(false);
                mCommentPopup.showPopupWindow();
                break;
            case R.id.ll_like:
                optionPosition = -1;
                mPresenter.addLike(NewYoXiuDetailActivity.this,userId, token, id, 0);
                break;
            case R.id.ll_collect:
                if (mData.getIs_my_collect() == 0) {
                    mAddCollectPopup.setYoId(id);
                    mAddCollectPopup.showPopupWindow();
                } else {
                    mCommonPopup.setContent("定要取消收藏吗？", "这将从所有收藏夹中删除，", "您真的想好了？")
                            .setContentColor(Color.parseColor("#FA800A"), Color.parseColor("#333333"), Color.parseColor("#333333"))
                            .setContentSize(15, 15, 15)
                            .setShowOption(true)
                            .showPopupWindow();
                }
                break;
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        optionPosition = position;
        switch (view.getId()) {
            case R.id.ll_like:
                mPresenter.addLike(NewYoXiuDetailActivity.this,userId, token, id, commentData.get(position).getId());
                break;
            case R.id.ll_replay:
                startActivityForResult(new Intent(this, ReplyDiscussActivity.class).putExtra("data", commentData.get(position)),0);
                break;
            case R.id.iv_more:
                mReportPopup.showPopupWindow();
                break;
        }
    }

    @Override
    public void onAddCollectSuccess(String id) {
        mIvCollect.setImageResource(R.mipmap.yishoucang);
        mData.setIs_my_collect(1);
        mData.setCount_collect(String.valueOf(Integer.valueOf(mData.getCount_collect()) + 1));
        mTvCollectNum.setText(mData.getCount_collect() + "");
    }

    @Override
    public void onCommonClick(View v) {
        if (v.getId() == R.id.tv_done) {
            mPresenter.deleteCollection(NewYoXiuDetailActivity.this,userId, token, id);
        }
    }

    @Override
    public void onReport(String content) {
        mPresenter.report(NewYoXiuDetailActivity.this,userId, token, id, 0, content);
    }

    @Override
    public void onDetailSuccess(YoXiuDetailBean.DataBean data) {
        mData = data;
        Glide.with(this).load(data.getFile_path()).apply(new RequestOptions().centerCrop()).into(mIvCover);
        Glide.with(this).load(data.getUser_logo()).apply(new RequestOptions().circleCrop()).into(mIvUserPic);
        mTvSelectAddress.setText(data.getPosition_name());
        mTvUserName.setText(data.getUser_nickname());
        mTvContent.setText(data.getFile_desc());
        mTvReadNum.setText(data.getCount_view() + "");
        mTvTime.setText(data.getCreate_time());
        mTvCommentNum.setText("评论 （" + data.getCount_comment() + ")");
        mIvLike.setImageResource(data.getIs_my_like() == 1 ? R.mipmap.yixihuan_2 : R.mipmap.xihuan);
        mIvCollect.setImageResource(data.getIs_my_collect() == 1 ? R.mipmap.yishoucang : R.mipmap.shoucang);
        mTvLikeNum.setText(data.getCount_praise());
        mTvCollectNum.setText(data.getCount_collect());
        mTvFollow.setText(data.getIs_my_attention() == 1 ? "已关注" : "+ 关注");
    }

    @Override
    public void onCommentListSuccess(CommentBean.DataBean data) {
        commentData = data.getList();
        mCommentAdapter.setNewData(commentData);
        mLlMoreComment.setVisibility(data.getList() != null && data.getList().size() > 0 ? View.VISIBLE : View.GONE);

    }

    @Override
    public void onAddCommentSuccess(BaseBean baseBean) {
        mPresenter.getCommentList(NewYoXiuDetailActivity.this,userId, token, 1, id, 0);
        mData.setCount_comment(String.valueOf(Integer.valueOf(mData.getCount_comment()) + 1));
        mTvCommentNum.setText("评论 （" + mData.getCount_comment() + ")");
        mCommonPopup.setContent("Hi～", "谢谢评论～", "给你小心心")
                .setContentColor(Color.parseColor("#FA800A"), Color.parseColor("#FA800A"), Color.parseColor("#FA800A"))
                .setContentSize(18, 18, 18)
                .setShowOption(false)
                .showPopupWindow();
    }

    @Override
    public void onAddAttentionSuccess(AttentionBean.DataBean data) {
        mTvFollow.setText(data.getStatus() == 1 ? "已关注" : "+ 关注");
    }

    @Override
    public void onDeleteCollectionSuccess(BaseBean baseBean) {
        mIvCollect.setImageResource(R.mipmap.shoucang);
        mData.setIs_my_collect(0);
        mData.setCount_collect(String.valueOf(Integer.valueOf(mData.getCount_collect()) - 1));
        mTvCollectNum.setText(mData.getCount_collect() + "");
    }

    @Override
    public void onAddLikeSuccess(LikeBean baseBean) {
        if (optionPosition == -1) {
            mIvLike.setImageResource(baseBean.getData().getStatus() == 1 ? R.mipmap.yixihuan_2 : R.mipmap.xihuan);
            mData.setIs_my_like(baseBean.getData().getStatus());
            mData.setCount_praise(String.valueOf(Integer.valueOf(mData.getCount_praise()) + (baseBean.getData().getStatus() == 0 ? -1 : 1)));
            mTvLikeNum.setText(mData.getCount_praise() + "");
        } else {
            commentData.get(optionPosition).setIs_my_praise(baseBean.getData().getStatus());
            commentData.get(optionPosition).setCount_praise(commentData.get(optionPosition).getCount_praise() + (baseBean.getData().getStatus() == 0 ? -1 : 1));
            mCommentAdapter.notifyItemChanged(optionPosition);
        }
        if (baseBean.getData().getStatus() == 1) {
            mCommonPopup.setContent("Hi～", "谢谢喜欢～", "给你小心心")
                    .setContentColor(Color.parseColor("#FA800A"), Color.parseColor("#FA800A"), Color.parseColor("#FA800A"))
                    .setContentSize(18, 18, 18)
                    .setShowOption(false)
                    .showPopupWindow();
        }
    }

    @Override
    public void onReportSuccess(BaseBean baseBean) {
        if (baseBean.getCode() == 200) {
            mCommonPopup.setContent("举报成功！", "打击恶势力小分队", "已前去为您扫清障碍～")
                    .setContentColor(Color.parseColor("#333333"), Color.parseColor("#888888"), Color.parseColor("#888888"))
                    .setContentSize(15, 12, 12)
                    .setShowOption(false)
                    .showPopupWindow();
        }
    }

    @Override
    public void onSendComment(String comment) {
        mPresenter.addComment(NewYoXiuDetailActivity.this,userId, token, 0, id, comment);
//        Toast.makeText(this, comment, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onKeyboardChange(boolean isShow, int keyboardHeight) {
        EmoticonsKeyboardUtils.setDefKeyboardHeight(this, keyboardHeight);
        if (mCommentPopup != null) {
            mCommentPopup.setKeyboardChange(isShow, keyboardHeight);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        keyboardChangeListener.destroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0){
            mPresenter.getDetail(NewYoXiuDetailActivity.this,userId, token, id);
            mPresenter.getCommentList(NewYoXiuDetailActivity.this,userId, token, 1, id, 0);
        }
    }
}
