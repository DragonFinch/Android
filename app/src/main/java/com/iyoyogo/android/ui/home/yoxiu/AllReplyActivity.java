package com.iyoyogo.android.ui.home.yoxiu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.collection.AddCollectionBean;
import com.iyoyogo.android.bean.collection.CollectionFolderBean;
import com.iyoyogo.android.bean.comment.CommentBean;
import com.iyoyogo.android.bean.yoxiu.YoXiuDetailBean;
import com.iyoyogo.android.contract.YoXiuDetailContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.presenter.YoXiuDetailPresenter;
import com.iyoyogo.android.ui.home.yoji.UserHomepageActivity;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 回复  R.layout.item_recyclerview_all_comment
 */
public class AllReplyActivity extends BaseActivity<YoXiuDetailContract.Presenter> implements YoXiuDetailContract.View {

    @BindView(R.id.all_reply_tv)
    TextView allReplyTv;
    @BindView(R.id.all_reply_RecyclerView)
    RecyclerView allReplyRecyclerView;
    @BindView(R.id.all_comment_title)
    TextView allCommentTitle;
    @BindView(R.id.all_comment_zan)
    TextView allCommentZan;
    @BindView(R.id.all_comment_time)
    TextView allCommentTime;
    @BindView(R.id.all_comment_content)
    TextView allCommentContent;
    @BindView(R.id.edit_reply)
    EditText editComment;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.tv_collection)
    TextView tvCollection;
    @BindView(R.id.send_emoji)
    ImageView sendEmoji;
    private String user_id;
    private String user_token;
    private int id;
    private List<YoXiuDetailBean.DataBean> dataBeans;
    private PopupWindow popup;
    TextView tv_message;
    ImageView img_tip;
    TextView tv_message_two;
    TextView tv_message_three;
    private int yo_id;
    private List<YoXiuDetailBean.DataBean> collection_list;
    private int yo_user_id;
    private int yo_attention_id;
    private int is_my_collect;
    private int is_my_attention;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_all_reply;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        dataBeans = new ArrayList<>();
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        String name = intent.getStringExtra("name");
        String time = intent.getStringExtra("time");
        String zan = intent.getStringExtra("zan");
        String content = intent.getStringExtra("content");
        user_id = SpUtils.getString(this, "user_id", null);
        user_token = SpUtils.getString(this, "user_token", null);
        mPresenter.getCommentList(user_id, user_token, 1, id, 0);
        allCommentContent.setText(content);
        allCommentTime.setText(time);
        allCommentTitle.setText(name);
        allCommentZan.setText(zan);
    }

    @Override
    protected YoXiuDetailContract.Presenter createPresenter() {
        return new YoXiuDetailPresenter(this);
    }

    @Override
    public void getDetailSuccess(YoXiuDetailBean.DataBean data) {
        initPopup();
        yo_id = data.getId();
        allReplyTv.setText("评论" + "(" + data.getCount_comment() + ")");
        collection_list = new ArrayList<>();
        collection_list.add(data);
        yo_user_id = data.getId();
        yo_attention_id = data.getUser_id();
        dataBeans.add(data);
        collections();
        is_my_collect = data.getIs_my_collect();
        is_my_attention = data.getIs_my_attention();
        Log.d("is_my_attention", "is_my_attention:" + is_my_attention);
        String create_time = data.getCreate_time();
        String time = create_time.replaceAll("-", ".");
//        tvTime.setText(time);
        int count_collect = data.getCount_collect();
        tvCollection.setText(count_collect + "");
        int count_praise = data.getCount_praise();
        tvLike.setText(count_praise + "");
        int count_view = data.getCount_view();
//        numLook.setText(count_view + "");
//        file_desc = data.getFile_desc();
//        textDesc.setText(file_desc);
//        path = data.getFile_path();
        RequestOptions requestOptions = new RequestOptions();
        if (editComment.getText().toString().length() > 0) {
            mPresenter.addComment(this.user_id, user_token, 0, this.id, editComment.getText().toString().trim());
            closeInputMethod();
//            yoXiuDetailAdapter.notifyDataSetChanged();
//            refresh();
        } else {

        }
//        ViewGroup.LayoutParams layoutParams = imgLogo.getLayoutParams();
//        int width = layoutParams.width;
//        requestOptions.placeholder(R.mipmap.default_ic).error(R.mipmap.default_ic);
//        Glide.with(this).load(path)
//                .apply(requestOptions)
//                .into(imgLogo);
//        int file_type = data.getFile_type();
//        if (file_type == 2) {
//            imgVideo.setVisibility(View.VISIBLE);
//        } else if (file_type == 1) {
//            imgVideo.setVisibility(View.GONE);
//        }
//        String user_logo = data.getUser_logo();
//        RequestOptions requestOption = new RequestOptions();
//        requestOption.error(R.mipmap.default_touxiang).placeholder(R.mipmap.default_touxiang);
//        Glide.with(this).load(user_logo).apply(requestOption).into(imgUserIcon);
//        String position_name = data.getPosition_name();
//        tvDesc.setText(position_name);
//        String user_nickname = data.getUser_nickname();
//        userName.setText(user_nickname);
//        praise();
    }

    private void collections() {
        Drawable collection = getResources().getDrawable(
                R.mipmap.shoucang_xiangqing);
        Drawable collectioned = getResources().getDrawable(
                R.mipmap.yishoucang_xiangqing);
        if (dataBeans.get(0).getIs_my_collect() == 0) {

            tvCollection.setCompoundDrawablesWithIntrinsicBounds(null,
                    collection, null, null);

        } else {
            tvCollection.setCompoundDrawablesWithIntrinsicBounds(null,
                    collectioned, null, null);
        }

        tvCollection.setCompoundDrawablesWithIntrinsicBounds(null,
                dataBeans.get(0).getIs_my_collect() == 0 ? collection : collectioned, null, null);

    }

    public void initPopup() {
        View view = LayoutInflater.from(AllReplyActivity.this).inflate(R.layout.like_layout, null);
        popup = new PopupWindow(view, DensityUtil.dp2px(AllReplyActivity.this, 300), DensityUtil.dp2px(AllReplyActivity.this, 145), true);
        popup.setOutsideTouchable(true);
        popup.setBackgroundDrawable(new ColorDrawable());
        tv_message = view.findViewById(R.id.tv_message);
        tv_message_two = view.findViewById(R.id.tv_message_two);

        tv_message_three = view.findViewById(R.id.tv_message_three);
        img_tip = view.findViewById(R.id.tip_img);

        popup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //点击空白处时，隐藏掉pop窗口


        //添加pop窗口关闭事件
        popup.setOnDismissListener(new poponDismissListener());
    }

    @Override
    public void getCommentListSuccess(CommentBean.DataBean data) {
        List<CommentBean.DataBean.ListBean> list = data.getList();
        int size = list.size();
        allReplyTv.setText("全部评论(" + size + ")");
        ReplyListAdapter adapter = new ReplyListAdapter(R.layout.item_recyclerview_reply, list);
        allReplyRecyclerView.setAdapter(adapter);
        allReplyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    @Override
    public void addCommentSuccess(BaseBean baseBean) {

    }

    @Override
    public void addAttentionSuccess(AttentionBean.DataBean data) {

    }

    @Override
    public void deleteAttentionSuccess(BaseBean baseBean) {

    }

    @Override
    public void createFolderSuccess(BaseBean baseBean) {

    }

    @Override
    public void getCollectionFolderSuccess(CollectionFolderBean.DataBean collectionFolderBean) {

    }

    @Override
    public void addCollectionSuccess(AddCollectionBean.DataBean data) {

    }

    @Override
    public void deleteCollectionSuccess(BaseBean baseBean) {

    }
    public void loadMore() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_more, null);
        PopupWindow popup_more = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popup_more.setBackgroundDrawable(new ColorDrawable());
        popup_more.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popup_more.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //点击空白处时，隐藏掉pop窗口
        //广告信息
        TextView tv_advert = view.findViewById(R.id.tv_advert);
        tv_advert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_more.dismiss();
                String s = tv_advert.getText().toString();
                DataManager.getFromRemote()
                        .report(user_id, user_token, id, 0, s)
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {

                            }
                        });
            }
        });
        //有害信息
        TextView tv_harm = view.findViewById(R.id.tv_harm);
        tv_harm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_more.dismiss();
                String s = tv_harm.getText().toString();
                DataManager.getFromRemote()
                        .report(user_id, user_token, id, 0, s)
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {

                            }
                        });
            }
        });
        //违法违规
        TextView tv_violate = view.findViewById(R.id.tv_violate);
        tv_violate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_more.dismiss();
                String s = tv_violate.getText().toString();
                DataManager.getFromRemote()
                        .report(user_id, user_token, id, 0, s)
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {

                            }
                        });
            }
        });
        //其他
        TextView tv_else = view.findViewById(R.id.tv_else);
        tv_else.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_more.dismiss();
                String s = tv_else.getText().toString();
                DataManager.getFromRemote()
                        .report(user_id, user_token, id, 0, s)
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {

                            }
                        });
            }
        });
        //取消
        TextView tv_cancel = view.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_more.dismiss();
            }
        });
        backgroundAlpha(0.6f);
        popup_more.setOnDismissListener(new poponDismissListener());
        //添加pop窗口关闭事件
        popup_more.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    @OnClick({R.id.tv_like, R.id.tv_collection, R.id.send_emoji, R.id.all_reply_back_id, R.id.all_comment_more_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_like:
                Drawable like = getResources().getDrawable(
                        R.mipmap.xihuan_xiangqing);
                Drawable liked = getResources().getDrawable(
                        R.mipmap.yixihuan_xiangqing);
                tvLike.setCompoundDrawablesWithIntrinsicBounds(null, dataBeans.get(0).getIs_my_like() > 0 ? liked : like, null, null);
                int count_praise = dataBeans.get(0).getCount_praise();

                Log.d("Test", "dataBeans.get(0).getIs_my_like():" + dataBeans.get(0).getIs_my_like());
                if (dataBeans.get(0).getIs_my_like() > 0) {
                    //由喜欢变为不喜欢，亮变暗
                    tvLike.setCompoundDrawablesWithIntrinsicBounds(null,
                            like, null, null);
                    count_praise -= 1;
                    //设置点赞的数量
                    tvLike.setText(count_praise + "");
                    dataBeans.get(0).setIs_my_like(0);
                    dataBeans.get(0).setCount_praise(count_praise);
                    like();
                    popup.showAtLocation(findViewById(R.id.activity_yoxiu_detail), Gravity.CENTER, 0, 0);
                } else {
                    //由不喜欢变为喜欢，暗变亮
                    tvLike.setCompoundDrawablesWithIntrinsicBounds(null,
                            liked, null, null);
                    count_praise += 1;
                    //设置点赞的数量
                    tvLike.setText(count_praise + "");
                    dataBeans.get(0).setIs_my_like(1);
                    dataBeans.get(0).setCount_praise(count_praise);
                }
                DataManager.getFromRemote().praise(user_id, user_token, dataBeans.get(0).getId(), 0)
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {
                            }
                        });
                break;
            case R.id.tv_collection:
                break;
            case R.id.send_emoji:
                break;
            case R.id.all_reply_back_id:
                finish();
                break;
            case R.id.all_comment_more_img:
                loadMore();
                break;
        }
    }

    //隐藏事件PopupWindow
    private class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1.0f);
        }
    }

    public void backgroundAlpha(float bgAlpha) {

        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        this.getWindow().setAttributes(lp); //act 是上下文context

    }

    @Override
    protected void initView() {
        super.initView();

        editComment.setImeOptions(EditorInfo.IME_ACTION_SEND);
        editComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().equals("#")) {
                    startActivity(new Intent(AllReplyActivity.this, MoreTopicActivity.class));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editComment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (editComment.getText().toString().length() > 0) {
                        mPresenter.addComment(user_id, user_token, 0, id, editComment.getText().toString().trim());
                        closeInputMethod();
                        mPresenter.getCommentList(user_id, user_token, 1, id, 0);
                        editComment.clearFocus();
                        editComment.setFocusable(false);
//                        yoXiuDetailAdapter.notifyItemInserted(dataBeans.size());
                    } else {
                    }
                    return true;
                }
                return false;

            }
        });
        editComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editComment.setFocusable(true);
                editComment.setFocusableInTouchMode(true);
                editComment.requestFocus();
            }
        });
    }

    private void closeInputMethod() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        if (isOpen) {
            // imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//没有显示则显示
            imm.hideSoftInputFromWindow(editComment.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void like() {

        tv_message.setTextColor(Color.parseColor("#FA800A"));
        tv_message_two.setTextColor(Color.parseColor("#FA800A"));
        tv_message_three.setTextColor(Color.parseColor("#FA800A"));
        backgroundAlpha(0.6f);
        tv_message.setText("Hi~");
        img_tip.setImageResource(R.mipmap.stamo_heart);
        tv_message_two.setText("谢谢喜欢~");
        tv_message_three.setText("给你小心心");
        popup.showAtLocation(findViewById(R.id.activity_yoxiu_detail), Gravity.CENTER, 0, 0);
    }


    /***
     * 回复—适配器
     */
    class ReplyListAdapter extends BaseQuickAdapter<CommentBean.DataBean.ListBean, BaseViewHolder> {
        public ReplyListAdapter(int layoutResId, @Nullable List<CommentBean.DataBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CommentBean.DataBean.ListBean item) {
            helper.setText(R.id.all_reply_title, item.getUser_nickname());
            helper.setText(R.id.all_reply_time, item.getCreate_time());
            helper.setText(R.id.all_reply_zan, item.getCount_comment() + "");
            Glide.with(mContext).load(item.getUser_logo()).into((ImageView) helper.getView(R.id.all_reply_img_user_icon));
            //点击头像
            CircleImageView all_comment_img_user_icon = helper.getView(R.id.all_reply_img_user_icon);
            all_comment_img_user_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, UserHomepageActivity.class);
                    intent.putExtra("yo_user_id", item.getUser_id() + "");
                    Log.d(TAG, "onClick: " + item.getUser_id());
                    startActivity(intent);
                }
            });

        }
    }
}
