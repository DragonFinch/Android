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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.CollectionFolderAdapter;
import com.iyoyogo.android.adapter.YoXiuDetailAdapter;
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
import com.umeng.debug.log.I;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 所有评论
 */
public class AllCommentActivity extends BaseActivity<YoXiuDetailContract.Presenter> implements YoXiuDetailContract.View {


    @BindView(R.id.all_comment_tv)
    TextView allCommentTv;
    @BindView(R.id.all_comment_RecyclerView)
    RecyclerView allCommentRecyclerView;
    @BindView(R.id.edit_comment)
    EditText editComment;
    @BindView(R.id.img_brow)
    ImageView imgBrow;
    @BindView(R.id.comment_layout)
    RelativeLayout commentLayout;
    private List<CommentBean.DataBean.ListBean> list;
    private int size;
    private int id;
    private String user_id;
    private String user_token;
    private PopupWindow popup;
    TextView tv_message;
    ImageView img_tip;
    TextView tv_message_two;
    TextView tv_message_three;
    private int open = 2;
    private boolean isOpen;
    private RecyclerView recycler_collection;
    private YoXiuDetailAdapter yoXiuDetailAdapter;
    private List<CollectionFolderBean.DataBean.ListBean> mList;
    private int id1;
    private int yo_user_id;
    private int yo_attention_id;
    private int count_collect;
    private List<YoXiuDetailBean.DataBean> dataBeans;
    private View view;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_all_comment;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        dataBeans = new ArrayList<>();
        Intent intent = getIntent();
        id1 = intent.getIntExtra("id", 0);
        user_id = SpUtils.getString(this, "user_id", null);
        user_token = SpUtils.getString(this, "user_token", null);
        mPresenter.getCommentList(user_id, user_token, 1, id1, 0);
        editComment.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //获得焦点
                    editComment.setHint("码字不容易，留个评论鼓励下嘛~");
                    editComment.setHintTextColor(Color.parseColor("#888888"));
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    layoutParams.setMargins(0, 0, DensityUtil.dp2px(AllCommentActivity.this, 40), 0);
                    editComment.setLayoutParams(layoutParams);

                } else {
                    //失去焦点
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(DensityUtil.dp2px(AllCommentActivity.this, 230), ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, DensityUtil.dp2px(AllCommentActivity.this, 20), 0, 0);
                    editComment.setLayoutParams(layoutParams);
                    editComment.setHint("再不评论 , 你会被抓去写作业的~");
                    editComment.setHintTextColor(Color.parseColor("#888888"));

                }
            }
        });
    }

    @Override
    protected YoXiuDetailContract.Presenter createPresenter() {
        return new YoXiuDetailPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getCommentList(user_id, user_token, 1, id1, 0);
    }

    /***
     * 返回
     */
    @OnClick({R.id.all_comment_back_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.all_comment_back_id:
                finish();
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

    //背景透明
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        getWindow().setAttributes(lp); //act 是上下文context

    }

    public void initPopup() {
        view = LayoutInflater.from(AllCommentActivity.this).inflate(R.layout.like_layout, null);
        popup = new PopupWindow(view, DensityUtil.dp2px(AllCommentActivity.this, 300), DensityUtil.dp2px(AllCommentActivity.this, 145), true);
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

    public void like() {
        tv_message.setTextColor(Color.parseColor("#FA800A"));
        tv_message_two.setTextColor(Color.parseColor("#FA800A"));
        tv_message_three.setTextColor(Color.parseColor("#FA800A"));
        backgroundAlpha(0.6f);
        tv_message.setText("Hi~");
        img_tip.setImageResource(R.mipmap.stamo_heart);
        tv_message_two.setText("谢谢喜欢~");
        tv_message_three.setText("给你小心心");
        popup.showAtLocation(view, Gravity.CENTER, 0, 0);
    }


    @Override
    public void getDetailSuccess(YoXiuDetailBean.DataBean data) {

    }

    @Override
    public void getCommentListSuccess(CommentBean.DataBean data) {//评论列表
        list = data.getList();
        size = list.size();
        allCommentTv.setText("全部评论(" + size + ")");
        yoXiuDetailAdapter = new YoXiuDetailAdapter(AllCommentActivity.this, list);
        allCommentRecyclerView.setAdapter(yoXiuDetailAdapter);
        allCommentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        yoXiuDetailAdapter.setDeleteOnClickListener(new YoXiuDetailAdapter.DeleteOnClickListener() {
            @Override
            public void delete() {
                mPresenter.getCommentList(user_id, user_token, 1, id, 0);
            }
        });
    }

    @Override
    protected void initView() {
        super.initView();
        //输入框
        editComment.setImeOptions(EditorInfo.IME_ACTION_SEND);
        editComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().equals("#")) {
                    startActivity(new Intent(AllCommentActivity.this, MoreTopicActivity.class));
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
                        mPresenter.addComment(user_id, user_token, 0, id1, editComment.getText().toString().trim());
                        closeInputMethod();
                        mPresenter.getCommentList(user_id, user_token, 1, id1, 0);
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

    @Override
    public void addCommentSuccess(BaseBean baseBean) {
        String msg = baseBean.getMsg();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        editComment.setText("");
        yoXiuDetailAdapter.notifyDataSetChanged();
    }


    @Override
    public void addAttentionSuccess(AttentionBean.DataBean data) {

    }

    @Override
    public void deleteAttentionSuccess(BaseBean baseBean) {

    }

    @Override
    public void createFolderSuccess(BaseBean baseBean) {
        Toast.makeText(this, baseBean.getMsg(), Toast.LENGTH_SHORT).show();
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
}
