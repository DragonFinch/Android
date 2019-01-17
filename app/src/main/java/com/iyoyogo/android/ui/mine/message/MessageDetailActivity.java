package com.iyoyogo.android.ui.mine.message;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.MessageDetailAdapter;
import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.mine.center.YoJiContentBean;
import com.iyoyogo.android.bean.mine.message.MessageBean;
import com.iyoyogo.android.bean.mine.message.ReadMessage;
import com.iyoyogo.android.contract.MessageContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;
import com.iyoyogo.android.presenter.MessagePresenter;
import com.iyoyogo.android.ui.home.EditImageOrVideoActivity;
import com.iyoyogo.android.ui.home.yoji.UserHomepageActivity;
import com.iyoyogo.android.ui.home.yoji.YoJiDetailActivity;
import com.iyoyogo.android.ui.home.yoxiu.AllCommentActivity;
import com.iyoyogo.android.ui.home.yoxiu.MoreTopicActivity;
import com.iyoyogo.android.ui.home.yoxiu.YoXiuDetailActivity;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.SoftKeyboardStateHelper;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.StatusBarUtils;
import com.iyoyogo.android.utils.refreshheader.MyRefreshAnimFooter;
import com.iyoyogo.android.utils.refreshheader.MyRefreshAnimHeader;
import com.iyoyogo.android.widget.CircleImageView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MessageDetailActivity extends BaseActivity<MessageContract.Presenter> implements MessageContract.View, SoftKeyboardStateHelper.SoftKeyboardStateListener {

    @BindView(R.id.message_center_back_im_id)
    ImageView messageCenterBackImId;
    @BindView(R.id.like_me_title_tv_id)
    TextView likeMeTitleTvId;
    @BindView(R.id.bar)
    RelativeLayout bar;
    @BindView(R.id.recycler_message)
    RecyclerView recyclerMessage;
    @BindView(R.id.img_stamp)
    ImageView imgStamp;
    @BindView(R.id.tv_stamp_title)
    TextView tvStampTitle;
    @BindView(R.id.publish_yoji)
    TextView publishYoji;
    @BindView(R.id.publish_yoxiu)
    TextView publishYoxiu;
    @BindView(R.id.like_layout)
    LinearLayout likeLayout;
    @BindView(R.id.edit_comment)
    EditText editComment;
    @BindView(R.id.input_expression)
    ImageView inputExpression;
    @BindView(R.id.edit_layout)
    RelativeLayout editLayout;
    @BindView(R.id.smart)
    SmartRefreshLayout smart;
    /**
     * 消息详情
     */

    private String user_id;
    private String user_token;
    private String title;
    private MyRefreshAnimFooter mRefreshAnimFooter;
    private MyRefreshAnimHeader mRefreshAnimHeader;
    private LikeMeAdapter likeMeAdapter;
    private SystemMessageAdapter systemMessageAdapter;
    private CommentMessageAdapter commentMessageAdapter;
    private FocusMessageAdapter focusMessageAdapter;
    private List<MessageBean.DataBean.ListBean> mList;
    private int currentPage;

    @Override
    protected void initView() {
        super.initView();
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        mList = new ArrayList<>();
//        statusbar();
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        likeMeTitleTvId.setText(title);
//        StatusBarUtils.setWindowStatusBarColor(MessageDetailActivity.this, R.color.white);
        smart.setRefreshFooter(new MyRefreshAnimFooter(this));
        mRefreshAnimHeader = new MyRefreshAnimHeader(this);
        setHeader(mRefreshAnimHeader);
    }

    private void setHeader(RefreshHeader header) {
        smart.setRefreshHeader(header);
    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_message_detail;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        mRefreshAnimHeader = new MyRefreshAnimHeader(this);
        mRefreshAnimFooter = new MyRefreshAnimFooter(this);
        smart.setRefreshFooter(mRefreshAnimFooter);
        smart.setRefreshHeader(mRefreshAnimHeader);
        user_id = SpUtils.getString(getApplicationContext(), "user_id", null);
        user_token = SpUtils.getString(getApplicationContext(), "user_token", null);
        if (title.equals("喜欢我的")) {
            mPresenter.getMessage(user_id, user_token, 2, 1);
        } else if (title.equals("系统消息")) {
            mPresenter.getMessage(user_id, user_token, 1, 1);
        } else if (title.equals("评论消息")) {
            mPresenter.getMessage(user_id, user_token, 3, 1);
        } else if (title.equals("关注消息")) {
            mPresenter.getMessage(user_id, user_token, 4, 1);
        }

        //下拉刷新
        smart.setEnableRefresh(true);
        smart.setRefreshFooter(new MyRefreshAnimFooter(this));
        smart.autoRefresh();
        smart.finishRefresh(1050);
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mList.clear();
                refreshLayout.finishRefresh(1050);
                if (title.equals("喜欢我的")) {
                    mPresenter.getMessage(user_id, user_token, 2, 1);
                } else if (title.equals("系统消息")) {
                    mPresenter.getMessage(user_id, user_token, 1, 1);
                } else if (title.equals("评论消息")) {
                    mPresenter.getMessage(user_id, user_token, 3, 1);
                } else if (title.equals("关注消息")) {
                    mPresenter.getMessage(user_id, user_token, 4, 1);
                }
            }
        });
        smart.setOnLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                currentPage++;
                if (title.equals("喜欢我的")) {
                    mPresenter.getMessage(user_id, user_token, 2, currentPage);
                    if (mList.size() != 0) {
                        likeMeAdapter.notifyItemChanged(mList.size());
                    }
                } else if (title.equals("系统消息")) {
                    mPresenter.getMessage(user_id, user_token, 1, currentPage);
                    if (mList.size() != 0) {
                        systemMessageAdapter.notifyItemChanged(mList.size());
                    }
                } else if (title.equals("评论消息")) {
                    mPresenter.getMessage(user_id, user_token, 3, currentPage);
                    if (mList.size() != 0) {
                        commentMessageAdapter.notifyItemChanged(mList.size());
                    }
                } else if (title.equals("关注消息")) {
                    mPresenter.getMessage(user_id, user_token, 4, currentPage);
                    if (mList.size() != 0) {
                        focusMessageAdapter.notifyItemChanged(mList.size());
                    }
                }
//                if (title.equals("喜欢我的")) {
//                    DataManager.getFromRemote()
//                            .getMessage(user_id,user_token,2,currentPage)
//                            .subscribe(new Observer<MessageBean>() {
//                                @Override
//                                public void onSubscribe(Disposable d) {
//
//                                }
//
//                                @Override
//                                public void onNext(MessageBean messageBean) {
//                                    List<MessageBean.DataBean.ListBean> list = messageBean.getData().getList();
//                                    mList.addAll(list);
//                                    if (mList.size()!=0){
//                                        likeMeAdapter.notifyItemChanged(mList.size());
//                                    }
//                                }
//
//                                @Override
//                                public void onError(Throwable e) {
//
//                                }
//
//                                @Override
//                                public void onComplete() {
//
//                                }
//                            });
//                } else if (title.equals("系统消息")) {
//                    DataManager.getFromRemote()
//                            .getMessage(user_id,user_token,1,currentPage)
//                            .subscribe(new Observer<MessageBean>() {
//                                @Override
//                                public void onSubscribe(Disposable d) {
//
//                                }
//
//                                @Override
//                                public void onNext(MessageBean messageBean) {
//                                    List<MessageBean.DataBean.ListBean> list = messageBean.getData().getList();
//                                    mList.addAll(list);
//                                    if (mList.size()!=0){
//                                        systemMessageAdapter.notifyItemChanged(mList.size());
//                                    }
//                                }
//
//                                @Override
//                                public void onError(Throwable e) {
//
//                                }
//
//                                @Override
//                                public void onComplete() {
//
//                                }
//                            });
//                } else if (title.equals("评论消息")) {
//                    DataManager.getFromRemote()
//                            .getMessage(user_id,user_token,3,currentPage)
//                            .subscribe(new Observer<MessageBean>() {
//                                @Override
//                                public void onSubscribe(Disposable d) {
//
//                                }
//
//                                @Override
//                                public void onNext(MessageBean messageBean) {
//                                    List<MessageBean.DataBean.ListBean> list = messageBean.getData().getList();
//                                    mList.addAll(list);
//                                    if (mList.size()!=0){
//                                        commentMessageAdapter.notifyItemChanged(mList.size());
//                                    }
//                                }
//
//                                @Override
//                                public void onError(Throwable e) {
//
//                                }
//
//                                @Override
//                                public void onComplete() {
//
//                                }
//                            });
//                } else if (title.equals("关注消息")) {
//                    DataManager.getFromRemote()
//                            .getMessage(user_id,user_token,4,currentPage)
//                            .subscribe(new Observer<MessageBean>() {
//                                @Override
//                                public void onSubscribe(Disposable d) {
//
//                                }
//
//                                @Override
//                                public void onNext(MessageBean messageBean) {
//                                    List<MessageBean.DataBean.ListBean> list = messageBean.getData().getList();
//                                    mList.addAll(list);
//                                    if (mList.size()!=0){
//                                        focusMessageAdapter.notifyItemChanged(mList.size());
//                                    }
//                                }
//
//                                @Override
//                                public void onError(Throwable e) {
//
//                                }
//
//                                @Override
//                                public void onComplete() {
//
//                                }
//                            });
//                }
                refreshLayout.finishLoadMore(2000);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
    }

    @Override
    protected MessageContract.Presenter createPresenter() {
        return new MessagePresenter(this);
    }


    @Override
    public void getMessageSuccess(List<MessageBean.DataBean.ListBean> list) {
        mList.addAll(list);
        if (list.size() == 0) {
            likeLayout.setVisibility(View.VISIBLE);
        } else {
            if (title.equals("喜欢我的")) {
                recyclerMessage.setLayoutManager(new LinearLayoutManager(MessageDetailActivity.this));
                likeMeAdapter = new LikeMeAdapter(R.layout.activity_like_my, list);//喜欢我的
                recyclerMessage.setAdapter(likeMeAdapter);
                likeMeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if (list.get(position).getIs_read() == 0) {
                            mPresenter.readMessage(user_id, user_token, list.get(position).getMessage_id() + "");
                        }
                    }
                });
            } else if (title.equals("系统消息")) {
                recyclerMessage.setLayoutManager(new LinearLayoutManager(MessageDetailActivity.this));
                systemMessageAdapter = new SystemMessageAdapter(R.layout.activity_system_message, list);//系统消息
                recyclerMessage.setAdapter(systemMessageAdapter);
                systemMessageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if (list.get(position).getIs_read() == 0) {
                            mPresenter.readMessage(user_id, user_token, list.get(position).getMessage_id() + "");
                        }
                    }
                });

            } else if (title.equals("评论消息")) {
                recyclerMessage.setLayoutManager(new LinearLayoutManager(MessageDetailActivity.this));
                commentMessageAdapter = new CommentMessageAdapter(R.layout.activity_comment_message, list);//评论消息
                recyclerMessage.setAdapter(commentMessageAdapter);
                commentMessageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if (list.get(position).getIs_read() == 0) {
                            mPresenter.readMessage(user_id, user_token, list.get(position).getMessage_id() + "");
                        }
                    }
                });
                commentMessageAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        editLayout.setVisibility(View.VISIBLE);
                        editComment.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                if (hasFocus) {
                                    //获得焦点
                                    editComment.setHint("码字不容易，留个评论鼓励下嘛~");
                                    editComment.setHintTextColor(Color.parseColor("#888888"));
                                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                    layoutParams.setMargins(0, 0, DensityUtil.dp2px(MessageDetailActivity.this, 40), 0);
                                    //   etSendmessage.setLayoutParams(layoutParams);

                                } else {
                                    //失去焦点
                                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(DensityUtil.dp2px(MessageDetailActivity.this, 230), ViewGroup.LayoutParams.WRAP_CONTENT);
                                    layoutParams.setMargins(0, DensityUtil.dp2px(MessageDetailActivity.this, 20), 0, 0);
                                    // etSendmessage.setLayoutParams(layoutParams);
                                    editComment.setHint("再不评论 , 你会被抓去写作业的~");
                                    editComment.setHintTextColor(Color.parseColor("#888888"));

                                }
                            }
                        });
                        //输入框
                        editComment.setImeOptions(EditorInfo.IME_ACTION_SEND);
                        editComment.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                if (s.toString().trim().equals("#")) {
                                    startActivity(new Intent(MessageDetailActivity.this, MoreTopicActivity.class));
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
                                        mPresenter.addComment(user_id, user_token, Integer.parseInt(list.get(position).getParam()), 0, editComment.getText().toString().trim());
                                        closeInputMethod();
                                        editComment.clearFocus();
                                        editComment.setFocusable(false);
                                        Toast.makeText(MessageDetailActivity.this, "回复成功", Toast.LENGTH_SHORT).show();
                                        editLayout.setVisibility(View.GONE);
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
                });

            } else if (title.equals("关注消息")) {
                recyclerMessage.setLayoutManager(new LinearLayoutManager(MessageDetailActivity.this));
                focusMessageAdapter = new FocusMessageAdapter(R.layout.activity_focus_message, list);//关注消息
                recyclerMessage.setAdapter(focusMessageAdapter);
                focusMessageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if (list.get(position).getIs_read() == 0) {
                            mPresenter.readMessage(user_id, user_token, list.get(position).getMessage_id() + "");
                        }
                    }
                });
            }
//            MessageDetailAdapter messageDetailAdapter = new MessageDetailAdapter(MessageDetailActivity.this, list);
//            recyclerMessage.setAdapter(messageDetailAdapter);
//            messageDetailAdapter.setOnClickListener(new MessageDetailAdapter.OnClickListener() {
//                @Override
//                public void setOnClickListener(View v, int position) {
//                    if (list.get(position).getYo_id().equals("")) {
//
//                    } else {
//                        if (list.get(position).getYo_type().equals("1")) {
//                            Intent intent = new Intent(MessageDetailActivity.this, YoXiuDetailActivity.class);
//                            intent.putExtra("id", Integer.parseInt(list.get(position).getYo_id()));
//                            startActivity(intent);
//                        } else {
//                            Intent intent = new Intent(MessageDetailActivity.this, YoJiDetailActivity.class);
//                            intent.putExtra("yo_id", Integer.parseInt(list.get(position).getYo_id()));
//                            startActivity(intent);
//                        }
//                    }
//
//                    mPresenter.readMessage(user_id, user_token, String.valueOf(list.get(position).getMessage_id()));
//                    messageDetailAdapter.notifyDataSetChanged();
//                }
//            });
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
    public void readMessageSuccess(ReadMessage.DataBean data) {

        if (title.equals("喜欢我的")) {
            mPresenter.getMessage(user_id, user_token, 2, 1);
        } else if (title.equals("系统消息")) {
            mPresenter.getMessage(user_id, user_token, 1, 1);
        } else if (title.equals("评论消息")) {
            mPresenter.getMessage(user_id, user_token, 3, 1);
        } else if (title.equals("关注消息")) {
            mPresenter.getMessage(user_id, user_token, 4, 1);
        }
    }

    @Override
    public void addCommentSuccess(BaseBean baseBean) {

    }


    @OnClick({R.id.publish_yoji, R.id.publish_yoxiu, R.id.message_center_back_im_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.publish_yoji:
                PictureSelector.create(MessageDetailActivity.this)
                        .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .maxSelectNum(9)// 最大图片选择数量 int
                        .minSelectNum(1)// 最小选择数量 int
                        .imageSpanCount(3)// 每行显示个数 int
                        .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                        .previewImage(true)// 是否可预览图片 true or false
                        .isCamera(true)// 是否显示拍照按钮 true or false
                        .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                        .compress(true)// 是否压缩 true or false
                        .isGif(true)// 是否显示gif图片 true or false
                        .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                        .minimumCompressSize(800)// 小于100kb的图片不压缩
                        .synOrAsy(false)//同步true或异步false 压缩 默认同步
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.publish_yoxiu:
                PictureSelector.create(MessageDetailActivity.this)
                        .openGallery(PictureMimeType.ofAll())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .imageSpanCount(3)// 每行显示个数 int
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                        .previewImage(true)// 是否可预览图片 true or false
                        .isCamera(true)// 是否显示拍照按钮 true or false
                        .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                        .compress(true)// 是否压缩 true or false
                        .isGif(true)// 是否显示gif图片 true or false
                        .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                        .minimumCompressSize(800)// 小于100kb的图片不压缩
                        .synOrAsy(false)//同步true或异步false 压缩 默认同步
                        .forResult(201);
                break;
            case R.id.message_center_back_im_id:
                finish();
                break;
        }
    }

    @Override
    public void onSoftKeyboardOpened(int keyboardHeightInPx) {
        editLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSoftKeyboardClosed() {
        editLayout.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                startActivity(data.setClass(MessageDetailActivity.this, EditImageOrVideoActivity.class).putExtra("type", 1));
            } else if (requestCode == 201) {
                startActivity(data.setClass(MessageDetailActivity.this, EditImageOrVideoActivity.class).putExtra("type", 2));
            }
        }
    }

}
