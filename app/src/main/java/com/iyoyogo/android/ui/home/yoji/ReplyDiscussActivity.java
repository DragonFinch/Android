package com.iyoyogo.android.ui.home.yoji;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.ReplyDiscussAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.comment.CommentBean;
import com.iyoyogo.android.contract.ReplyDiscussContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.presenter.ReplyDiscussPresenter;
import com.iyoyogo.android.ui.home.yoxiu.MoreTopicActivity;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 回复评论
 */
public class ReplyDiscussActivity extends BaseActivity<ReplyDiscussContract.Presenter> implements ReplyDiscussContract.View {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.bar)
    RelativeLayout bar;
    @BindView(R.id.img_user_icon)
    CircleImageView imgUserIcon;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.img_comment_like)
    ImageView imgCommentLike;
    @BindView(R.id.tv_comment_like_num)
    TextView tvCommentLikeNum;
    @BindView(R.id.img_function)
    ImageView imgFunction;
    @BindView(R.id.layout_function)
    LinearLayout layoutFunction;
    @BindView(R.id.discuss_layout)
    RelativeLayout discussLayout;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.input_expression)
    ImageView inputExpression;
    @BindView(R.id.edit_layout)
    RelativeLayout editLayout;
    @BindView(R.id.edit_comment)
    EditText editComment;
    private String user_id;
    private String user_token;
    private CommentBean.DataBean.ListBean listBean;
    private String user_nickname;
    private ReplyDiscussAdapter replyDiscussAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reply_discuss;
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
    protected void setSetting() {
        super.setSetting();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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
                    startActivity(new Intent(ReplyDiscussActivity.this, MoreTopicActivity.class));
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
                        mPresenter.addComment(user_id, user_token, listBean.getId(), 0, editComment.getText().toString().trim());
                        closeInputMethod();
                        mPresenter.getCommentList(user_id, user_token, 1, 0, listBean.getId());
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

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        user_id = SpUtils.getString(ReplyDiscussActivity.this, "user_id", null);
        user_token = SpUtils.getString(ReplyDiscussActivity.this, "user_token", null);
        Intent intent = getIntent();
        listBean = (CommentBean.DataBean.ListBean) intent.getSerializableExtra("data");
        String user_logo = listBean.getUser_logo();
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.default_touxiang).error(R.mipmap.default_touxiang);
        Glide.with(this).load(user_logo).apply(requestOptions).into(imgUserIcon);
        userName.setText(listBean.getUser_nickname());
        tvContent.setText(listBean.getContent());
        tvTime.setText(listBean.getCreate_time());
        tvCommentLikeNum.setText(listBean.getCount_praise() + "");
        user_nickname = listBean.getUser_nickname();
        mPresenter.getCommentList(user_id, user_token, 1, 0, listBean.getId());
    }

    @Override
    protected ReplyDiscussContract.Presenter createPresenter() {
        return new ReplyDiscussPresenter(this);
    }

    @Override
    public void getCommentListSuccess(CommentBean.DataBean data) {
        List<CommentBean.DataBean.ListBean> mList = new ArrayList<>();
        mList.addAll(data.getList());
        replyDiscussAdapter = new ReplyDiscussAdapter(getApplicationContext(), mList, user_nickname);
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycler.setAdapter(replyDiscussAdapter);
        if (listBean.getIs_my_praise()==0){
           imgCommentLike.setImageResource(R.mipmap.zan_select);
        }else {
           imgCommentLike.setImageResource(R.mipmap.zan_selected);
        }
       imgCommentLike.setImageResource(listBean.getIs_my_praise() == 0 ? R.mipmap.zan_select : R.mipmap.zan_selected);
       imgCommentLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id = SpUtils.getString(getApplicationContext(), "user_id", null);
                String user_token = SpUtils.getString(getApplicationContext(), "user_token", null);

                int count_praise = listBean.getIs_my_praise();
                listBean.setIs_my_praise(listBean.getIs_my_praise() == 1 ? 0 : 1);
                if (listBean.getIs_my_praise() == 1) {
                    count_praise += 1;
                } else if (count_praise > 0) {
                    count_praise -= 1;
                }
                listBean.setCount_praise(count_praise);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DataManager.getFromRemote().praise(user_id, user_token,0, listBean.getId())
                                .subscribe(new Consumer<BaseBean>() {
                                    @Override
                                    public void accept(BaseBean baseBean) throws Exception {
                                      
                                    }
                                });
                    }
                }).start();

            }
        });
    }

    @Override
    public void addCommentSuccess(BaseBean baseBean) {
        editComment.setText("");
        replyDiscussAdapter.notifyDataSetChanged();
        mPresenter.getCommentList(user_id, user_token, 1, 0, listBean.getId());
    }


    @OnClick({R.id.back, R.id.img_user_icon, R.id.img_comment_like, R.id.img_function, R.id.recycler, R.id.input_expression})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.img_user_icon:
                break;
            case R.id.img_comment_like:
                break;
            case R.id.img_function:
                break;
            case R.id.recycler:
                break;
            case R.id.input_expression:
                break;
        }
    }
}
