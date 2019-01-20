package com.iyoyogo.android.ui.home.yoji;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ServiceException;
import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;

import com.githang.statusbar.StatusBarCompat;
import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.Bean;
import com.iyoyogo.android.adapter.NewPublishYoJiAdapter;
import com.iyoyogo.android.app.Constants;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.PublishSucessBean;
import com.iyoyogo.android.bean.yoji.publish.MessageBean;
import com.iyoyogo.android.bean.yoji.publish.PublishYoJiBean;
import com.iyoyogo.android.bean.yoxiu.topic.HotTopicBean;
import com.iyoyogo.android.contract.PublishYoJiContract;
import com.iyoyogo.android.contract.ShareContract;
import com.iyoyogo.android.model.RObject;
import com.iyoyogo.android.net.OssService;
import com.iyoyogo.android.presenter.PublishYoJiPresenter;
import com.iyoyogo.android.presenter.SharePresenter;
import com.iyoyogo.android.ui.common.SearchActivity;
import com.iyoyogo.android.ui.home.EditImageOrVideoActivity;
import com.iyoyogo.android.ui.home.yoxiu.ChannelActivity;
import com.iyoyogo.android.ui.home.yoxiu.MoreTopicActivity;
import com.iyoyogo.android.ui.home.yoxiu.NewPublishYoXiuActivity;
import com.iyoyogo.android.ui.home.yoxiu.PublishOpenPopup;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.StatusBarUtils;
import com.iyoyogo.android.utils.TextChangeListener;
import com.iyoyogo.android.utils.util.DateUtils;
import com.iyoyogo.android.view.LoadingDialog;
import com.iyoyogo.android.widget.REditText;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

public class NewPublishYoJiActivity extends BaseActivity<PublishYoJiPresenter> implements BaseQuickAdapter.OnItemChildClickListener, PublishYoJiContract.View, TextChangeListener.TextChange, OssService.UploadImageListener, PublishOpenPopup.OpenPopupClick, ShareContract.View {

    @BindView(R.id.tv_title)
    TextView      mTvTitle;
    @BindView(R.id.iv_back)
    ImageView     mIvBack;
    @BindView(R.id.tv_publish)
    TextView      mTvPublish;
    @BindView(R.id.iv_cover)
    ImageView     mIvCover;
    @BindView(R.id.et_title)
    EditText      mEtTitle;
    @BindView(R.id.tv_title_length)
    TextView      mTvTitleLength;
    @BindView(R.id.et_info)
    REditText     mEtInfo;
    @BindView(R.id.tv_info_length)
    TextView      mTvInfoLength;
    @BindView(R.id.flex_topic)
    FlexboxLayout mFlexboxLayout;
    @BindView(R.id.et_money)
    EditText      mEtMoney;
    @BindView(R.id.recyclerView)
    RecyclerView  mRecyclerView;
    @BindView(R.id.flex_channel)
    FlexboxLayout mFlexChannel;
    @BindView(R.id.tv_channel)
    TextView      mTvChannel;
    @BindView(R.id.rbtn_friend_circle)
    RadioButton   mRbtnFriendCircle;
    @BindView(R.id.rbtn_weibo)
    RadioButton   mRbtnWeibo;
    @BindView(R.id.rbtn_qq)
    RadioButton   mRbtnQq;
    @BindView(R.id.rbtn_wechat)
    RadioButton   mRbtnWechat;
    @BindView(R.id.rg_share)
    RadioGroup    mRgShare;
    @BindView(R.id.tv_publish_type)
    TextView      mTvPublishType;
    @BindView(R.id.ll_option)
    LinearLayout  mLlOption;

    private List<PublishYoJiBean.DataBean.ListBean> mData;


    private NewPublishYoJiAdapter mAdapter;

    private int    optionIndex = 0;
    private String userId;
    private String token;

    private int uploadIndex = 0;
    private int uploadSize  = 0;

    private OssService mOssService;

    private int id = 0;

    private String coverUrl  = "";
    private String coverPath = "";

    private int isOpen = 1;

    private int saveType = 1;

    private List<HotTopicBean.DataBean.ListBean> topicData;

    private ArrayList<Integer> channel_arrays;
    private ArrayList<String>  channel_list;
    private PopupWindow        popMenu;

    private PublishOpenPopup mPublishOpenPopup;
    private int              oldTextLength = 0;

    private SharePresenter mSharePresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_publish_yo_ji;
    }

    @Override
    protected void initView() {
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NewPublishYoJiAdapter(R.layout.item_publish_yo_ji);
        mAdapter.setOnItemChildClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        mEtTitle.addTextChangedListener(new TextChangeListener(mEtTitle, this));
        mEtInfo.addTextChangedListener(new TextChangeListener(mEtInfo, this));

        mPublishOpenPopup = new PublishOpenPopup(this);
        mPublishOpenPopup.setOpenPopupClick(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mData = new ArrayList<>();
        channel_arrays = new ArrayList<>();
        channel_list = new ArrayList<>();
        mSharePresenter = new SharePresenter(NewPublishYoJiActivity.this,this);
        mOssService = new OssService(this);
        userId = SpUtils.getString(this, "user_id", null);
        token = SpUtils.getString(this, "user_token", null);
        mPresenter.getRecommendTopic(NewPublishYoJiActivity.this,userId, token);

        id = getIntent().getIntExtra("id", 0);
        Log.d("NewPublishYoJiActivity", "id:" + id);
        if (id == 0) {
            List<LocalMedia>                  localMedia = PictureSelector.obtainMultipleResult(getIntent());
            PublishYoJiBean.DataBean.ListBean bean       = new PublishYoJiBean.DataBean.ListBean();
            bean.setLocalMedia(localMedia);
            String time = DateUtils.stampToDate(new Date(), "yyyy-MM-dd");
            bean.setStart_date(time);
            bean.setEnd_date(time);
            mData.add(bean);
            mAdapter.setNewData(mData);
            coverPath = TextUtils.isEmpty(localMedia.get(0).getCompressPath()) ? localMedia.get(0).getPath() : localMedia.get(0).getCompressPath();
            Glide.with(this).load(coverPath).apply(new RequestOptions().centerCrop()).into(mIvCover);
        } else {
            mPresenter.getYoJiData(NewPublishYoJiActivity.this,userId, token, id);
        }

    }

    @Override
    protected PublishYoJiPresenter createPresenter() {
        return new PublishYoJiPresenter(NewPublishYoJiActivity.this,this);
    }

    @OnClick({R.id.iv_back, R.id.tv_change_image, R.id.ll_channel, R.id.tv_publish, R.id.iv_cover, R.id.tv_more_topic, R.id.rbtn_friend_circle, R.id.rbtn_weibo, R.id.rbtn_qq, R.id.rbtn_wechat, R.id.tv_publish_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_change_image:
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .maxSelectNum(1)// 最大图片选择数量 int
                        .minSelectNum(1)// 最小选择数量 int
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
                        .minimumCompressSize(500)// 小于100kb的图片不压缩
                        .synOrAsy(false)//同步true或异步false 压缩 默认同步
                        .forResult(200);
                break;
            case R.id.tv_publish:
                if (isParamsEmpty()) {
                    saveType = 1;
                    publish();
                }
                break;
            case R.id.iv_cover:
                break;
            case R.id.tv_more_topic:
                startActivityForResult(new Intent(this, MoreTopicActivity.class).putExtra("type", 2), 2);
                break;
            case R.id.rbtn_friend_circle:
                break;
            case R.id.rbtn_weibo:
                break;
            case R.id.rbtn_qq:
                break;
            case R.id.rbtn_wechat:
                break;
            case R.id.tv_publish_type:
                mPublishOpenPopup.showPopupWindow(mLlOption);
                break;
            case R.id.ll_channel:
                startActivityForResult(new Intent(this, ChannelActivity.class)
                        .putIntegerArrayListExtra("data", channel_arrays), 1);
                break;
        }
    }

    private boolean isUploadImage() {
        for (PublishYoJiBean.DataBean.ListBean datum : mData) {
            if (datum.getLocalMedia() != null && datum.getLocalMedia().size() > 0) {
                return true;
            }
        }
        return !TextUtils.isEmpty(coverPath);
    }

    private boolean isParamsEmpty() {
        if (TextUtils.isEmpty(mEtTitle.getText())) {
            Toast.makeText(this, "标题不能为空，请输入", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(mEtMoney.getText())) {
            Toast.makeText(this, "人均消费不能为空，请输入", Toast.LENGTH_SHORT).show();
            return false;
        } else if (channel_arrays == null || channel_arrays.size() == 0) {
            Toast.makeText(this, "请选择频道", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private List<MessageBean> setParams() {
        List<MessageBean> list = new ArrayList<>();
        for (PublishYoJiBean.DataBean.ListBean datum : mData) {
            MessageBean data = new MessageBean();
            data.setStart_date(datum.getStart_date());
            data.setEnd_date(datum.getEnd_date());
            data.setLat(datum.getLat());
            data.setLng(datum.getLng());
            data.setPosition_name(datum.getPosition_name());
            data.setPosition_address(datum.getPosition_address());
            data.setPosition_areas(datum.getPosition_areas());
            data.setLogos((ArrayList<String>) datum.getLogos());
            List<Integer> ids = new ArrayList<>();
            if (datum.getLabels() != null && datum.getLabels().size() > 0) {
                for (PublishYoJiBean.DataBean.ListBean.LabelsBean labelsBean : datum.getLabels()) {
                    ids.add(labelsBean.getLabel_id());
                }
            }
            data.setLabel_ids(ids);
            list.add(data);
        }
        return list;
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        optionIndex = position;
        switch (view.getId()) {
            case R.id.iv_add_image:
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .maxSelectNum(9)// 最大图片选择数量 int
                        .minSelectNum(1)// 最小选择数量 int
                        .imageSpanCount(3)// 每行显示个数 int
                        .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                        .selectionMedia(mData.get(position).getLocalMedia())
                        .previewImage(true)// 是否可预览图片 true or false
                        .isCamera(true)// 是否显示拍照按钮 true or false
                        .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                        .compress(true)// 是否压缩 true or false
                        .isGif(true)// 是否显示gif图片 true or false
                        .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                        .minimumCompressSize(500)// 小于100kb的图片不压缩
                        .synOrAsy(false)//同步true或异步false 压缩 默认同步
                        .forResult(100);
                break;
            case R.id.tv_start_date:
                selectTime(position, 1);
                break;
            case R.id.tv_end_date:
                selectTime(position, 2);
                break;
            case R.id.tv_insert:
                PictureSelector.create(this)
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
                        .minimumCompressSize(500)// 小于100kb的图片不压缩
                        .synOrAsy(false)//同步true或异步false 压缩 默认同步
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.tv_delete:
                if (position > 0) {
                    mAdapter.remove(position);
                } else {
                    Toast.makeText(this, "不能再少了", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.ll_location:
                Intent intent = new Intent(this, SearchActivity.class);

                TextView tv_location = view.findViewById(R.id.tv_location);
                if (tv_location.getText().toString().trim().equals("")) {
                    intent.putExtra("latitude", "0");
                    intent.putExtra("yo_type", 2);
                    intent.putExtra("longitude", "0");
                    intent.putExtra("place", "添加位置");
                } else {
                    intent.putExtra("latitude", mData.get(optionIndex).getLat());
                    intent.putExtra("yo_type", 2);
                    intent.putExtra("longitude", mData.get(optionIndex).getLng());
                    intent.putExtra("place", tv_location.getText().toString());

                }
                startActivityForResult(intent, 1);
                break;

            case R.id.ll_tag:
                ArrayList<Integer> list = new ArrayList<>();
                if (mData.get(position).getLabels() != null) {
                    for (PublishYoJiBean.DataBean.ListBean.LabelsBean labelsBean : mData.get(position).getLabels()) {
                        list.add(labelsBean.getLabel_id());
                    }
                }
                Intent intent1 = new Intent(this, ChooseSignActivity.class).putIntegerArrayListExtra("data", list);
                startActivityForResult(intent1, 1);
                break;
        }
    }

    private void publish() {
        LoadingDialog.get().create(this).show();
        if (isUploadImage()) {
            uploadIndex = 0;
            if (!TextUtils.isEmpty(coverPath)) {
                uploadSize = getImageSize() + 1;
                mOssService.asyncPutImage(coverPath, -1);
            } else {
                uploadSize = getImageSize();
            }
            for (int i = 0; i < mData.size(); i++) {
                if (mData.get(i).getLocalMedia() != null && mData.get(i).getLocalMedia().size() > 0) {
                    mData.get(i).getLogos().clear();
                    for (LocalMedia localMedia : mData.get(i).getLocalMedia()) {
                        mOssService.asyncPutImage(TextUtils.isEmpty(localMedia.getCompressPath()) ? localMedia.getPath() : localMedia.getCompressPath(), i);
                    }
                }
            }
        } else {
            Log.d("NewPublishYoJiActivity", new Gson().toJson(setParams()).toString());
            mPresenter.publishYoJi(NewPublishYoJiActivity.this,userId, token, id, coverUrl, mEtTitle.getText().toString(), mEtInfo.getText().toString(), Integer.valueOf(mEtMoney.getText().toString()), isOpen, saveType, channel_arrays.toString().replace("[", "").replace("]", ""), new Gson().toJson(setParams()));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    if (resultCode == 200) {
                        List<LocalMedia>                  localMedia = PictureSelector.obtainMultipleResult(data);
                        PublishYoJiBean.DataBean.ListBean bean       = new PublishYoJiBean.DataBean.ListBean();
                        bean.setLocalMedia(localMedia);
                        String time = DateUtils.stampToDate(new Date(), "yyyy-MM-dd");
                        bean.setStart_date(time);
                        bean.setEnd_date(time);
                        mData.add(optionIndex + 1, bean);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        startActivityForResult(data.setClass(this, EditImageOrVideoActivity.class).putExtra("type", 3), PictureConfig.CHOOSE_REQUEST);
                    }
                    break;

                case 100:
                    if (resultCode == 200) {
                        List<LocalMedia> local = PictureSelector.obtainMultipleResult(data);
                        mData.get(optionIndex).setLocalMedia(local);
                        mData.get(optionIndex).getLogos().clear();
                        mAdapter.notifyItemChanged(optionIndex);
                    } else {
                        startActivityForResult(data.setClass(this, EditImageOrVideoActivity.class).putExtra("type", 3), 100);
                    }
                    break;
                case 200:
                    if (resultCode == 200) {
                        List<LocalMedia> path = PictureSelector.obtainMultipleResult(data);
                        if (path != null && path.size() >= 0) {
                            coverPath = TextUtils.isEmpty(path.get(0).getCompressPath()) ? path.get(0).getPath() : path.get(0).getCompressPath();
                            Glide.with(this).load(coverPath).apply(new RequestOptions().centerCrop()).into(mIvCover);
                        }
                    } else {
                        startActivityForResult(data.setClass(this, EditImageOrVideoActivity.class).putExtra("type", 3), 200);
                    }
                    break;
            }

            if (requestCode == 2 && resultCode == 6) {
                String topicName = data.getStringExtra("topic");
                int    type_id   = data.getIntExtra("type_id", 0);

                HotTopicBean.DataBean.ListBean bean = new HotTopicBean.DataBean.ListBean();
                bean.setId(type_id);
                bean.setTopic(topicName);
                topicData.add(bean);
                addTopic();
                RObject object = new RObject();
                object.setObjectRule("#");
                object.setObjectText(topicName);
                mEtInfo.setObject(object);
            } else if (requestCode == 1 && resultCode == 3) {
                double latitude  = data.getDoubleExtra("latitude", 0.0);
                String title     = data.getStringExtra("title");
                double longitude = data.getDoubleExtra("longitude", 0.0);
                mData.get(optionIndex).setPosition_areas(data.getStringExtra("area"));
                mData.get(optionIndex).setPosition_address(data.getStringExtra("address"));
                mData.get(optionIndex).setPosition_name(title);
                mData.get(optionIndex).setLat(latitude + "");
                mData.get(optionIndex).setLng(longitude + "");
                mAdapter.notifyItemChanged(optionIndex);
            } else if (requestCode == 1 && resultCode == 2) {
                double latitude  = data.getDoubleExtra("latitude", 0.0);
                String title     = data.getStringExtra("title");
                double longitude = data.getDoubleExtra("longitude", 0.0);
                mData.get(optionIndex).setPosition_areas(data.getStringExtra("area"));
                mData.get(optionIndex).setPosition_address(data.getStringExtra("address"));
                mData.get(optionIndex).setPosition_name(title);
                mData.get(optionIndex).setLat(latitude + "");
                mData.get(optionIndex).setLng(longitude + "");
                mAdapter.notifyItemChanged(optionIndex);
            } else if (requestCode == 1 && resultCode == 55) {
                ArrayList<Bean>                                    sign_list = (ArrayList<Bean>) data.getSerializableExtra("sign_list");
                List<PublishYoJiBean.DataBean.ListBean.LabelsBean> list      = new ArrayList<>();
                for (Bean bean : sign_list) {
                    PublishYoJiBean.DataBean.ListBean.LabelsBean labelsBean = new PublishYoJiBean.DataBean.ListBean.LabelsBean();
                    labelsBean.setLabel(bean.getLabel());
                    labelsBean.setLabel_id(bean.getLabel_id());
                    labelsBean.setType(bean.getType());
                    list.add(labelsBean);
                }
                mData.get(optionIndex).setLabels(list);
                mAdapter.notifyItemChanged(optionIndex);
            } else if (requestCode == 1 && resultCode == 1) {
                mFlexChannel.removeAllViews();
                channel_arrays = data.getIntegerArrayListExtra("channel_array");
                channel_list = data.getStringArrayListExtra("channel_list");
                if (channel_list != null && channel_list.size() > 0) {
                    for (String s : channel_list) {
                        View     view = LayoutInflater.from(this).inflate(R.layout.item_publish_channel, null);
                        TextView tv   = view.findViewById(R.id.tv);
                        tv.setText(s);
                        mFlexChannel.addView(view);
                    }
                }
                mTvChannel.setVisibility(channel_list != null && channel_list.size() > 0 ? View.GONE : View.VISIBLE);
            }
        }
    }

    public void selectTime(int position, int type) {
        TimePickerView.Builder centerLabel = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String time = DateUtils.stampToDate(date, "yyyy-MM-dd");
                if (type == 1) {
                    mData.get(position).setStart_date(time);
                } else {
                    mData.get(position).setEnd_date(time);
                }
                mAdapter.notifyItemChanged(position);
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .gravity(Gravity.CENTER)
                .isCenterLabel(false);//是否只显示中间选中项的label文字，false则每项item全部都带有label。
        if (type == 2) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(new Date(DateUtils.string2Milliseconds(mData.get(position).getStart_date(),"yyyy-MM-dd")));
            Calendar end = Calendar.getInstance();
            end.set(2100,12,31);
            centerLabel.setRangDate(instance,end);
        }
        centerLabel.build().show();
    }

    private int getImageSize() {
        int size = 0;
        for (PublishYoJiBean.DataBean.ListBean datum : mData) {
            if (datum.getLocalMedia() != null) {
                size += datum.getLocalMedia().size();
            }
        }
        return size;
    }

    @Override
    public void publishYoJiSuccess(PublishSucessBean baseBean) {
        LoadingDialog.get().close();
        if (saveType == 1) {
            switch (mRgShare.getCheckedRadioButtonId()) {
                case R.id.rbtn_friend_circle:
                    shareWeb(baseBean.getData().getYo_id(), SHARE_MEDIA.WEIXIN_CIRCLE);
                    break;
                case R.id.rbtn_weibo:
                    shareWeb(baseBean.getData().getYo_id(), SHARE_MEDIA.SINA);
                    break;
                case R.id.rbtn_qq:
                    shareWeb(baseBean.getData().getYo_id(), SHARE_MEDIA.QQ);
                    break;
                case R.id.rbtn_wechat:
                    shareWeb(baseBean.getData().getYo_id(), SHARE_MEDIA.WEIXIN);
                    break;
                default:
                    EventBus.getDefault().post("publish_success");
                    finish();
                    break;
            }
        } else {
            EventBus.getDefault().post("publish_success");
            finish();
        }
    }

    private void shareWeb(String id, SHARE_MEDIA share_media) {
        /*80002/yo_id/4143*/
        String url = Constants.BASE_URL + "home/share/details_yoj/share_user_id/" + userId + "/yo_id/" + id;
        UMWeb  web = new UMWeb(url);
        web.setTitle(mEtTitle.getText().toString());//标题
        UMImage thumb = new UMImage(getApplicationContext(), coverUrl);
        web.setThumb(thumb);  //缩略图
        if (!TextUtils.isEmpty(mEtInfo.getText().toString())) {
            web.setDescription(mEtInfo.getText().toString());//描述
        }

        new ShareAction(NewPublishYoJiActivity.this)
                .withMedia(web)
                .setPlatform(share_media)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        mSharePresenter.share(NewPublishYoJiActivity.this,userId, token, id);
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        finish();
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        finish();
                    }
                })
                .share();
    }

    @Override
    public void getRecommendTopicSuccess(List<HotTopicBean.DataBean.ListBean> list) {
        topicData = list;
        addTopic();
    }

    private void addTopic() {
        mFlexboxLayout.removeAllViews();
        for (HotTopicBean.DataBean.ListBean bean : topicData) {
            View     view = LayoutInflater.from(this).inflate(R.layout.item_public_yo_ji_topic, null);
            TextView tv   = view.findViewById(R.id.tv);
            tv.setText(bean.getTopic());
            view.setOnClickListener(v -> {
                //话题对象，可继承此类实现特定的业务逻辑
                RObject object = new RObject();
                //匹配规则
                object.setObjectRule("#");
                //话题内容
                object.setObjectText(bean.getTopic());
                mEtInfo.setObject(object);
            });
            mFlexboxLayout.addView(view);
        }
    }

    @Override
    public void onYoJiData(PublishYoJiBean data) {
        mData = data.getData().getList();
        coverUrl = data.getData().getLogo();
        channel_arrays = new ArrayList<>();
        channel_list = new ArrayList<>();
        mTvPublishType.setText(data.getData().getOpen() == 1 ? "公开" : "私密");
        isOpen = data.getData().getOpen();
        for (PublishYoJiBean.DataBean.ChannelsBean channelsBean : data.getData().getChannels()) {
            channel_list.add(channelsBean.getChannel());
            channel_arrays.add(channelsBean.getChannel_id());
            View     view = LayoutInflater.from(this).inflate(R.layout.item_publish_channel, null);
            TextView tv   = view.findViewById(R.id.tv);
            tv.setText(channelsBean.getChannel());
            mFlexChannel.addView(view);
        }
        mTvChannel.setVisibility(channel_list != null && channel_list.size() > 0 ? View.GONE : View.VISIBLE);

        Glide.with(this).load(coverUrl).apply(new RequestOptions().centerCrop()).into(mIvCover);
        mEtTitle.setText(data.getData().getTitle());
        mEtInfo.setText(data.getData().getDesc());
        mEtMoney.setText(data.getData().getCost());
        mAdapter.setNewData(data.getData().getList());
        setTopic(data.getData().getDesc());
    }

    @Override
    public void onError(String message) {
        LoadingDialog.get().close();
    }

    @Override
    public void onTextChange(View view, String s) {
        switch (view.getId()) {
            case R.id.et_title:
                mTvTitleLength.setText(s.length() + "/36");
                break;
            case R.id.et_info:
                mTvInfoLength.setText(s.length() + "/200");
                if (!TextUtils.isEmpty(s) && s.substring(s.length() - 1, s.length()).equals("#") && s.length() - oldTextLength == 1) {
                    startActivityForResult(new Intent(this, MoreTopicActivity.class).putExtra("type", 2), 2);
                    mEtInfo.setText(s.substring(0, s.length() - 1));
                    mEtInfo.setSelection(mEtTitle.getText().length());
                    oldTextLength = s.length() - 1;
                } else {
                    oldTextLength = s.length();
                }
                break;
        }
    }

    @Override
    public void onUploadSuccess(String url, int position) {
        Log.d("NewPublishYoJiActivity", "Looper.getMainLooper().getThread()== Thread.currentThread():" + (Looper.getMainLooper().getThread() == Thread.currentThread()));
        uploadIndex++;
        if (position < 0) {
            coverUrl = url;
        } else {
            mData.get(position).getLogos().add(url);
        }
        if (uploadIndex == uploadSize) {
            PictureFileUtils.deleteCacheDirFile(this);
            Log.d("NewPublishYoJiActivity", new Gson().toJson(mData));

            runOnUiThread(() -> mPresenter.publishYoJi(NewPublishYoJiActivity.this,userId,
                    token,
                    id,
                    coverUrl,
                    mEtTitle.getText().toString(),
                    mEtInfo.getText().toString(),
                    TextUtils.isEmpty(mEtMoney.getText()) ? 0 : Integer.valueOf(mEtMoney.getText().toString()),
                    isOpen,
                    saveType,
                    channel_arrays.toString().replace("[", "").replace("]", ""),
                    new Gson().toJson(setParams())));
        }
        Log.d("NewPublishYoJiActivity", "position:" + position);
        Log.d("NewPublishYoJiActivity", "uploadIndex:" + uploadIndex);
    }

    @Override
    public void onUploadError(ServiceException e) {
        LoadingDialog.get().close();
        Toast.makeText(this, "图片上传失败", Toast.LENGTH_SHORT).show();
    }

    private void setTopic(String content) {
        Pattern TAG_PATTERN = Pattern.compile("#([^\\#|.]+)#");
        Matcher m           = TAG_PATTERN.matcher(content);
        while (m.find()) {
            String tagNameMatch = m.group();
            mEtInfo.setText(mEtInfo.getText().toString().replace(tagNameMatch, ""));
            mEtInfo.setSelection(mEtInfo.getText().length());
            RObject object = new RObject();
            //匹配规则
            object.setObjectRule("#");
            //话题内容
            object.setObjectText(tagNameMatch.replace("#", "").replace("#", ""));
            mEtInfo.setObject(object);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initPopuptWindow() {
        View pop_view = View.inflate(this, R.layout.item_praise_popup, null);


        pop_view.findViewById(R.id.popup_im_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popMenu.dismiss();
            }
        });
        popMenu = new PopupWindow(pop_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popMenu.setFocusable(true);//设置pw中的控件能够获取焦点
        ColorDrawable dw = new ColorDrawable();
        popMenu.setBackgroundDrawable(dw);//设置mPopupWindow背景颜色或图片，这里设置半透明
        popMenu.setOutsideTouchable(true); //设置可以通过点击mPopupWindow外部关闭mPopupWindow
//        popMenu.setAnimationStyle(R.style.PopupAnimationAmount);//设置mPopupWindow的进出动画
        Button   popup_no_id    = pop_view.findViewById(R.id.popup_no_id);
        Button   popup_yes_id   = pop_view.findViewById(R.id.popup_yes_id);
        TextView pop_content_id = pop_view.findViewById(R.id.pop_content_id);
        TextView pop_title_id   = pop_view.findViewById(R.id.pop_title_id);
        pop_title_id.setText("码字「不易」");
        pop_content_id.setText("退出前是否要保存到草稿？");
        popup_no_id.setText("放弃");
        popup_no_id.setTextColor(Color.parseColor("#FA800A"));
        popup_yes_id.setTextColor(Color.parseColor("#FA800A"));
        popup_yes_id.setText("准了");
        popup_no_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                popMenu.dismiss();
            }
        });
        popup_yes_id.setOnClickListener(v -> {
//            if (isParamsEmpty()) {
            saveType = 3;
            publish();
//            }
            popMenu.dismiss();
        });
        backgroundAlpha(0.6f);
        popMenu.showAtLocation(findViewById(R.id.edit_rlayout_id), Gravity.CENTER, 0, 0);//mPopupWindow显示的位置
        /**
         * PopupWindow消失监听方法
         */

        popMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
    }

    public void backgroundAlpha(float bgAlpha) {

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        getWindow().setAttributes(lp); //act 是上下文context

    }


    @Override
    public void onBackPressed() {
        initPopuptWindow();

    }

    @Override
    public void onOpenClick(int type, String typeName) {
        mTvPublishType.setText(typeName);
        isOpen = type;
    }

    @Override
    public void onShareSuccess(BaseBean data) {
        EventBus.getDefault().post("publish_success");
        finish();
    }
}
