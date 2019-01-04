package com.iyoyogo.android.ui.home.yoxiu;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ServiceException;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.Bean;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.yoji.publish.PublishYoJiBean;
import com.iyoyogo.android.bean.yoxiu.PublishYoXiuBean;
import com.iyoyogo.android.bean.yoxiu.topic.HotTopicBean;
import com.iyoyogo.android.contract.PublishYoXiuContract;
import com.iyoyogo.android.model.RObject;
import com.iyoyogo.android.net.OssService;
import com.iyoyogo.android.presenter.PublishYoXiuPresenter;
import com.iyoyogo.android.ui.common.SearchActivity;
import com.iyoyogo.android.ui.home.HomeFragment;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.VideoUtil;
import com.iyoyogo.android.utils.util.UiUtils;
import com.iyoyogo.android.view.LoadingDialog;
import com.iyoyogo.android.widget.FlowGroupView;
import com.iyoyogo.android.widget.REditText;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewPublishYoXiuActivity extends BaseActivity<PublishYoXiuPresenter> implements PublishYoXiuContract.View, OssService.UploadImageListener {


    @BindView(R.id.tv_title)
    TextView      mTvTitle;
    @BindView(R.id.iv_back)
    ImageView     mIvBack;
    @BindView(R.id.tv_publish)
    TextView      mTvPublish;
    @BindView(R.id.iv_cover)
    ImageView     mIvCover;
    @BindView(R.id.et_title)
    REditText     mEtTitle;
    @BindView(R.id.tv_title_length)
    TextView      mTvTitleLength;
    @BindView(R.id.more_topic)
    TextView      mMoreTopic;
    @BindView(R.id.flex_topic)
    FlexboxLayout mFlexTopic;
    @BindView(R.id.tv_channel)
    TextView      mTvChannel;
    @BindView(R.id.flex_channel)
    FlexboxLayout mFlexChannel;
    @BindView(R.id.rbtn_friend_circle)
    RadioButton   mRbtnFriendCircle;
    @BindView(R.id.rbtn_weibo)
    RadioButton   mRbtnWeibo;
    @BindView(R.id.rbtn_qq)
    RadioButton   mRbtnQq;
    @BindView(R.id.rbtn_wechat)
    RadioButton   mRbtnWechat;
    @BindView(R.id.tv_publish_type)
    TextView      mTvPublishType;
    @BindView(R.id.tv_select_address)
    TextView      mTvAddress;


    private OssService mOssService;
    private String     userId;
    private String     token;
    private int        id = 0;
    private String     coverPath;
    private String     coverUrl;

    private List<HotTopicBean.DataBean.ListBean> topicData;

    private List<Integer>     channel_arrays;
    private ArrayList<String> channel_list;

    private PublishYoXiuBean.DataBean mData;

    private int isOpen = 1;

    private int    saveType = 1;
    private String filterId = "0";

    private String scale="9:16";

    PopupWindow popMenu;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_publish_yo_xiu;
    }

    @Override
    protected PublishYoXiuPresenter createPresenter() {
        return new PublishYoXiuPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mData = new PublishYoXiuBean.DataBean();
        mOssService = new OssService(this);
        userId = SpUtils.getString(this, "user_id", null);
        token = SpUtils.getString(this, "user_token", null);
        mPresenter.getRecommendTopic(userId, token);

        id = getIntent().getIntExtra("id", 0);
        if (id == 0) {
            List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(getIntent());
            coverPath = TextUtils.isEmpty(localMedia.get(0).getCompressPath()) ? localMedia.get(0).getPath() : localMedia.get(0).getCompressPath();
//            if (coverPath.contains(".mp4")) {
//                Glide.with(this).load(VideoUtil.createVideoThumbnail(coverPath, UiUtils.getScreenPix().widthPixels, UiUtils.dip2px(225))).apply(new RequestOptions().centerCrop()).into(mIvCover);
//            }else {
            Glide.with(this).load(coverPath).apply(new RequestOptions().centerCrop()).into(mIvCover);

//            }
        } else {
            mPresenter.getYoXiuData(userId, token, id);
        }

    }

    @Override
    public void publishYoXiuSuccess() {
        LoadingDialog.get().close();
        finish();
    }

    @Override
    public void onYoXiuData(PublishYoXiuBean data) {
        mData = data.getData();
        coverUrl = data.getData().getFile_path();
        channel_arrays = new ArrayList<>();
        channel_list = new ArrayList<>();
        for (PublishYoXiuBean.DataBean.ChannelsBean channelsBean : data.getData().getChannels()) {
            channel_list.add(channelsBean.getChannel());
            channel_arrays.add(channelsBean.getChannel_id());
            View     view = LayoutInflater.from(this).inflate(R.layout.item_publish_channel, null);
            TextView tv   = view.findViewById(R.id.tv);
            tv.setText(channelsBean.getChannel());
            mFlexChannel.addView(view);
        }
        mTvChannel.setVisibility(channel_list != null && channel_list.size() > 0 ? View.GONE : View.VISIBLE);

        Glide.with(this).load(coverUrl).apply(new RequestOptions().centerCrop()).into(mIvCover);
        mEtTitle.setText(data.getData().getFile_desc());
        setTopic(data.getData().getFile_desc());
        mTvAddress.setText(data.getData().getPosition_name());
    }

    @OnClick({R.id.iv_back, R.id.tv_publish, R.id.tv_select_address, R.id.tv_change_image, R.id.ll_channel, R.id.more_topic, R.id.rbtn_friend_circle, R.id.rbtn_weibo, R.id.rbtn_qq, R.id.rbtn_wechat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_change_image:
                PictureSelector.create(this)
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
                        .forResult(200);
                break;
            case R.id.tv_publish:
                if (isParamsEmpty()) {
                    saveType=1;
                    LoadingDialog.get().create(this).show();
                    mOssService.asyncPutImage(coverPath, -1);
                }
                break;
            case R.id.more_topic:
                startActivityForResult(new Intent(this, MoreTopicActivity.class).putExtra("type", 2), 2);
                break;
            case R.id.tv_select_address:
                Intent intent = new Intent(this, SearchActivity.class);
                intent.putExtra("latitude", "0");
                intent.putExtra("yo_type", 2);
                intent.putExtra("longitude", "0");
                intent.putExtra("place", "添加地点");
                startActivityForResult(intent, 1);
                break;
            case R.id.rbtn_friend_circle:
                break;
            case R.id.rbtn_weibo:
                break;
            case R.id.rbtn_qq:
                break;
            case R.id.rbtn_wechat:
                break;

            case R.id.ll_channel:
                startActivityForResult(new Intent(this, ChannelActivity.class), 1);
                break;
        }
    }

    private boolean isParamsEmpty() {
        if (TextUtils.isEmpty(mEtTitle.getText())) {
            Toast.makeText(this, "标题不能为空，请输入", Toast.LENGTH_SHORT).show();
            return false;
        } else if (channel_arrays == null || channel_arrays.size() == 0) {
            Toast.makeText(this, "请选择频道", Toast.LENGTH_SHORT).show();
            return false;
        }else if (TextUtils.isEmpty(mData.getLat())||TextUtils.isEmpty(mData.getLng())){
            return false;
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 200 && resultCode == RESULT_OK) {
                List<LocalMedia> path = PictureSelector.obtainMultipleResult(data);
                if (path != null && path.size() >= 0) {
                    coverPath = TextUtils.isEmpty(path.get(0).getCompressPath()) ? path.get(0).getPath() : path.get(0).getCompressPath();
                    Glide.with(this).load(coverPath).apply(new RequestOptions().centerCrop()).into(mIvCover);
                }
            } else if (requestCode == 2 && resultCode == 6) {
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
                mEtTitle.setObject(object);
            } else if (requestCode == 1 && resultCode == 3) {
                double latitude  = data.getDoubleExtra("latitude", 0.0);
                String title     = data.getStringExtra("title");
                double longitude = data.getDoubleExtra("longitude", 0.0);
                mData.setPosition_areas(data.getStringExtra("area"));
                mData.setPosition_address(data.getStringExtra("address"));
                mData.setPosition_city(data.getStringExtra("city"));
                mData.setPosition_name(title);
                mData.setLat(latitude + "");
                mData.setLng(longitude + "");
                mTvAddress.setText(title);
            } else if (requestCode == 1 && resultCode == 1) {
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

    private void addTopic() {
        mFlexTopic.removeAllViews();
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
                mEtTitle.setObject(object);
            });
            mFlexTopic.addView(view);
        }
    }

    @Override
    public void onUploadSuccess(String url, int position) {
        coverUrl=url;
        PictureFileUtils.deleteCacheDirFile(this);
        Log.d("NewPublishYoJiActivity", new Gson().toJson(mData));
        runOnUiThread(() -> mPresenter.publishYoXiu(userId, token, id, coverUrl, coverPath.contains(".mp4") ? 2 : 1, mEtTitle.getText().toString(), channel_arrays.toString().replace("[","").replace("]",""), isOpen, saveType, mData.getPosition_name(), mData.getPosition_areas(), mData.getPosition_address(), mData.getPosition_city(), mData.getLng(), mData.getLat(), filterId,scale));
    }

    @Override
    public void onUploadError(ServiceException e) {
        LoadingDialog.get().close();
        Toast.makeText(this, "图片上传失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getRecommendTopicSuccess(List<HotTopicBean.DataBean.ListBean> list) {
        topicData = list;
        for (HotTopicBean.DataBean.ListBean bean : list) {
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
                mEtTitle.setObject(object);
            });
            mFlexTopic.addView(view);
        }
    }

    private void setTopic(String content) {
        Pattern TAG_PATTERN = Pattern.compile("# ([^\\#|.]+) #");
        Matcher m           = TAG_PATTERN.matcher(content);
        while (m.find()) {
            String tagNameMatch = m.group();
            mEtTitle.setText(mEtTitle.getText().toString().replace(tagNameMatch, ""));
            mEtTitle.setSelection(mEtTitle.getText().length());
            RObject object = new RObject();
            //匹配规则
            object.setObjectRule("#");
            //话题内容
            object.setObjectText(tagNameMatch.replace("# ", "").replace(" #", ""));
            mEtTitle.setObject(object);
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
            if (isParamsEmpty()) {
                saveType=3;
                LoadingDialog.get().create(NewPublishYoXiuActivity.this).show();
                mOssService.asyncPutImage(coverPath, -1);
            }
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
}
