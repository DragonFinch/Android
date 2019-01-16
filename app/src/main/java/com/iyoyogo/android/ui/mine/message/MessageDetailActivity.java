package com.iyoyogo.android.ui.mine.message;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.MessageDetailAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.mine.message.MessageBean;
import com.iyoyogo.android.bean.mine.message.ReadMessage;
import com.iyoyogo.android.contract.MessageContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.presenter.MessagePresenter;
import com.iyoyogo.android.ui.home.EditImageOrVideoActivity;
import com.iyoyogo.android.ui.home.yoji.YoJiDetailActivity;
import com.iyoyogo.android.ui.home.yoxiu.YoXiuDetailActivity;
import com.iyoyogo.android.utils.SoftKeyboardStateHelper;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.StatusBarUtils;
import com.iyoyogo.android.utils.refreshheader.MyRefreshAnimFooter;
import com.iyoyogo.android.utils.refreshheader.MyRefreshAnimHeader;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    @Override
    protected void initView() {
        super.initView();
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
//        statusbar();
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        likeMeTitleTvId.setText(title);
        StatusBarUtils.setWindowStatusBarColor(MessageDetailActivity.this, R.color.white);
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
            DataManager.getFromRemote().getMessage(user_id, user_token, 2, 1).subscribe(new Consumer<MessageBean>() {
                @Override
                public void accept(MessageBean messageBean) throws Exception {
                    List<MessageBean.DataBean.ListBean> list = messageBean.getData().getList();
                    if (list.size() == 0) {
                        likeLayout.setVisibility(View.VISIBLE);
                    } else {
                        recyclerMessage.setLayoutManager(new LinearLayoutManager(MessageDetailActivity.this));
                        MessageDetailAdapter messageDetailAdapter = new MessageDetailAdapter(MessageDetailActivity.this, list);
                        recyclerMessage.setAdapter(messageDetailAdapter);
                        messageDetailAdapter.setOnClickListener(new MessageDetailAdapter.OnClickListener() {
                            @Override
                            public void setOnClickListener(View v, int position) {
                                if (list.get(position).getYo_id().equals("")) {

                                } else {
                                    if (list.get(position).getYo_type().equals("1")) {
                                        Intent intent = new Intent(MessageDetailActivity.this, YoXiuDetailActivity.class);
                                        intent.putExtra("id", Integer.parseInt(list.get(position).getYo_id()));
                                        startActivity(intent);
                                    } else {
                                        Intent intent = new Intent(MessageDetailActivity.this, YoJiDetailActivity.class);
                                        intent.putExtra("yo_id", Integer.parseInt(list.get(position).getYo_id()));
                                        startActivity(intent);
                                    }
                                }

                                mPresenter.readMessage(user_id, user_token, String.valueOf(list.get(position).getMessage_id()));
                                messageDetailAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            });
        } else if (title.equals("系统消息")) {
            mPresenter.getMessage(user_id, user_token, 1, 1);
        } else if (title.equals("评论消息")) {
            mPresenter.getMessage(user_id, user_token, 3, 1);
        } else if (title.equals("关注消息")) {
            mPresenter.getMessage(user_id, user_token, 4, 1);
        }


    }

    @Override
    protected MessageContract.Presenter createPresenter() {
        return new MessagePresenter(this);
    }


    @Override
    public void getMessageSuccess(List<MessageBean.DataBean.ListBean> list) {


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
