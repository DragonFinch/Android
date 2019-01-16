package com.iyoyogo.android.ui.home.yoji;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.githang.statusbar.StatusBarCompat;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.ReplyDiscussAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.comment.CommentBean;
import com.iyoyogo.android.contract.ReplyDiscussContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.presenter.ReplyDiscussPresenter;
import com.iyoyogo.android.ui.home.yoxiu.MoreTopicActivity;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.StatusBarUtils;
import com.iyoyogo.android.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.medal)
    ImageView medal;
    private String user_id;
    private String user_token;
    private CommentBean.DataBean.ListBean listBean;
    private String user_nickname;
    private ReplyDiscussAdapter replyDiscussAdapter;
    private TextView tv_message;
    private TextView tv_message_two;
    private TextView tv_message_three;
    private ImageView img_tip;
    private PopupWindow popup;
    private View view;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reply_discuss;
    }

    public void loadMore(int yo_id) {
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_more, null);
        PopupWindow popup_more = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popup_more.setBackgroundDrawable(new ColorDrawable());
        popup_more.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popup_more.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //点击空白处时，隐藏掉pop窗口
        //广告信息
        TextView tv_advert = view.findViewById(R.id.tv_advert);

        user_token = SpUtils.getString(getApplicationContext(), "user_token", null);
        tv_advert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_more.dismiss();
                report();

                DataManager.getFromRemote().report(user_id, user_token, yo_id, 0, tv_advert.getText().toString())
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {
                                if (baseBean.getMsg().equals("success")) {

                                } else {


                                }
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
                report();
                DataManager.getFromRemote().report(user_id, user_token, yo_id, 0, tv_harm.getText().toString())
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {
                                if (baseBean.getMsg().equals("success")) {

                                } else {


                                }
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
                report();
                DataManager.getFromRemote().report(user_id, user_token, yo_id, 0, tv_violate.getText().toString())
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {
                                if (baseBean.getMsg().equals("success")) {

                                } else {


                                }
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
                report();
                DataManager.getFromRemote().report(user_id, user_token, yo_id, 0, tv_else.getText().toString())
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {
                                if (baseBean.getMsg().equals("success")) {

                                } else {
                                    Toast.makeText(getApplicationContext(), baseBean.getMsg(), Toast.LENGTH_SHORT).show();

                                }
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
        popup_more.showAtLocation(findViewById(R.id.activity_reply_discuss), Gravity.BOTTOM, 0, 0);
    }

    public void comment() {
        backgroundAlpha(0.6f);
        tv_message.setText("Hi~");
        tv_message.setTextColor(Color.parseColor("#FA800A"));
        tv_message_two.setTextColor(Color.parseColor("#FA800A"));
        tv_message_three.setTextColor(Color.parseColor("#FA800A"));
        img_tip.setImageResource(R.mipmap.stamo_heart);
        tv_message_two.setText("谢谢评论~");
        tv_message_three.setText("给你小心心");
        popup.showAtLocation(findViewById(R.id.activity_reply_discuss), Gravity.CENTER, 0, 0);
    }


    private void initDelete(int yo_id) {
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.popup_delete_or_report, null);
        PopupWindow popupWindow = new PopupWindow(view, DensityUtil.dp2px(getApplicationContext(), 125), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setOutsideTouchable(true);
        String user_id = SpUtils.getString(getApplicationContext(), "user_id", null);
        String user_token = SpUtils.getString(getApplicationContext(), "user_token", null);
        TextView tv_delete = view.findViewById(R.id.tv_delete);
        TextView tv_report = view.findViewById(R.id.tv_report);
        Log.d("YoJiDetailCommentAdapte", user_id);

//            tv_delete.setVisibility(View.VISIBLE);
        tv_report.setVisibility(View.GONE);

//            tv_delete.setVisibility(View.GONE);
        tv_report.setVisibility(View.VISIBLE);

        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupWindow.dismiss();
            }
        });
        tv_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                loadMore(yo_id);
            }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        backgroundAlpha(0.6f);
        popupWindow.setOnDismissListener(new poponDismissListener());
        popupWindow.showAsDropDown(imgFunction, DensityUtil.dp2px(getApplicationContext(), -95), DensityUtil.dp2px(getApplicationContext(), 5));
    }


    //隐藏事件PopupWindow
    private class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1.0f);
        }
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
    }

    @Override
    protected void initView() {
        super.initView();
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initPopup();
        StatusBarUtils.setWindowStatusBarColor(this, Color.WHITE);
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
                        replyDiscussAdapter.notifyDataSetChanged();
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

        medal.setVisibility(View.VISIBLE);
        int partner_type = listBean.getPartner_type();
        if (partner_type == 0) {
            listBean.setPartner_type(0);
            medal.setVisibility(View.INVISIBLE);
        } else if (partner_type == 1) {
            listBean.setPartner_type(1);
            medal.setImageResource(R.mipmap.daren);
        } else if (partner_type == 2) {
            listBean.setPartner_type(2);
            medal.setImageResource(R.mipmap.hongren);
        } else if (partner_type == 3) {
            listBean.setPartner_type(3);
            medal.setImageResource(R.mipmap.kol);
        } else {
            medal.setVisibility(listBean.getPartner_type() == 0 ? View.INVISIBLE : View.VISIBLE);
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

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
        if (listBean.getIs_my_praise() == 0) {
            imgCommentLike.setImageResource(R.mipmap.zan_select);
        } else {
            imgCommentLike.setImageResource(R.mipmap.zan_selected);
        }
        imgCommentLike.setImageResource(listBean.getIs_my_praise() == 0 ? R.mipmap.zan_select : R.mipmap.zan_selected);
        imgCommentLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id = SpUtils.getString(getApplicationContext(), "user_id", null);
                String user_token = SpUtils.getString(getApplicationContext(), "user_token", null);

                int count_praise = listBean.getCount_praise();
                listBean.setIs_my_praise(listBean.getIs_my_praise() == 1 ? 0 : 1);
                if (listBean.getIs_my_praise() == 1) {
                    count_praise += 1;
                    tvCommentLikeNum.setText(count_praise + "");
                } else {
                    count_praise -= 1;
                    tvCommentLikeNum.setText(count_praise + "");
                }
                listBean.setCount_praise(count_praise);
                imgCommentLike.setImageResource(listBean.getIs_my_praise() == 0 ? R.mipmap.zan_select : R.mipmap.zan_selected);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DataManager.getFromRemote().praise(user_id, user_token, 0, listBean.getId())
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
                initDelete(String.valueOf(listBean.getUser_id()), listBean.getId(), listBean.getYo_id());
//                initDelete(listBean.getYo_id());
                break;
            case R.id.recycler:

                break;
            case R.id.input_expression:

                break;
        }
    }

    private void initDelete(String yo_user_id, int comment_id, int yo_id) {
        View view = LayoutInflater.from(this).inflate(R.layout.popup_delete_or_report, null);
        PopupWindow popupWindow = new PopupWindow(view, DensityUtil.dp2px(this, 125), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        user_id = SpUtils.getString(ReplyDiscussActivity.this, "user_id", null);
        user_token = SpUtils.getString(ReplyDiscussActivity.this, "user_token", null);
        TextView tv_delete = view.findViewById(R.id.tv_delete);
        TextView tv_report = view.findViewById(R.id.tv_report);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        backgroundAlpha(0.6f);
        Log.d("YoXiuDetailAdapter", yo_user_id);
        if (yo_user_id.equals(user_id)) {
            tv_delete.setVisibility(View.VISIBLE);
            tv_report.setVisibility(View.GONE);
        } else {
            tv_delete.setVisibility(View.GONE);
            tv_report.setVisibility(View.VISIBLE);
        }
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                DataManager.getFromRemote().deleteComment(user_id, user_token, comment_id)
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {
                                if (deleteOnClickListener != null) {
                                    deleteOnClickListener.delete();
                                    DataManager.getFromRemote().getComment(user_id, user_token, 1, yo_id, 0)
                                            .subscribe(new Consumer<CommentBean>() {
                                                @Override
                                                public void accept(CommentBean commentBean) throws Exception {

                                                }
                                            });
                                }
                                int code = baseBean.getCode();
                                if (code == 200){
                                    replyDiscussAdapter.notifyDataSetChanged();
                                    Toast.makeText(ReplyDiscussActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });
                popupWindow.dismiss();
            }
        });
        tv_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                loadMore(comment_id);
            }
        });

        popupWindow.setOutsideTouchable(true);

        popupWindow.setOnDismissListener(new poponDismissListener());
        popupWindow.showAsDropDown(imgFunction, DensityUtil.dp2px(this, -95), DensityUtil.dp2px(this, 5));
    }


    public void backgroundAlpha(float bgAlpha) {

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        getWindow().setAttributes(lp); //act 是上下文context

    }


    public void report() {
        tv_message.setText("举报成功");
        tv_message.setTextColor(Color.parseColor("#333333"));
        tv_message_two.setTextColor(Color.parseColor("#888888"));
        tv_message_three.setTextColor(Color.parseColor("#888888"));
        img_tip.setImageResource(R.mipmap.stamp_report);
        tv_message_two.setText("打击恶势力小分队");
        tv_message_three.setText("已前去为您扫清障碍~");
        popup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //点击空白处时，隐藏掉pop窗口

        backgroundAlpha(0.6f);

        //添加pop窗口关闭事件
        popup.setOnDismissListener(new poponDismissListener());
        popup.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    public void initPopup() {
        view = LayoutInflater.from(this).inflate(R.layout.like_layout, null);
        popup = new PopupWindow(view, DensityUtil.dp2px(this, 300), DensityUtil.dp2px(this, 145), true);
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

    DeleteOnClickListener deleteOnClickListener;

    public void setDeleteOnClickListener(DeleteOnClickListener deleteOnClickListener) {
        this.deleteOnClickListener = deleteOnClickListener;
    }

    public interface DeleteOnClickListener {
        void delete();
    }
}
