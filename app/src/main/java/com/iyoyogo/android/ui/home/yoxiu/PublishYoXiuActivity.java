package com.iyoyogo.android.ui.home.yoxiu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.bumptech.glide.Glide;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.ChannelMessageAdapter;
import com.iyoyogo.android.app.Constant;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.yoxiu.topic.HotTopicBean;
import com.iyoyogo.android.contract.PublishYoXiuContract;
import com.iyoyogo.android.model.RObject;
import com.iyoyogo.android.presenter.PublishYoXiuPresenter;
import com.iyoyogo.android.ui.common.SearchActivity;
import com.iyoyogo.android.ui.mine.AboutMeActivity;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.ImagePickAction;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.widget.FlowGroupView;
import com.iyoyogo.android.widget.REditText;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JzvdStd;

/**
 * 发布yo秀
 */
public class PublishYoXiuActivity extends BaseActivity<PublishYoXiuContract.Presenter> implements PublishYoXiuContract.View {
    private TextView tv_message;
    private TextView tv_message_two;
    private TextView tv_message_three;
    private ImageView img_tip;
    private PopupWindow popup;
    ArrayList<String> names = new ArrayList<String>();
    @BindView(R.id.edit_back_id)
    ImageView editBackId;
    @BindView(R.id.edit_publish_id)
    TextView editPublishId;
    @BindView(R.id.edit_lltoobar_id)
    LinearLayout editLltoobarId;
    @BindView(R.id.edit_video_id)
    ImageView editVideoId;
    @BindView(R.id.publish_place)
    TextView publishPlace;
    @BindView(R.id.edit_start_id)
    ImageView editStartId;
    @BindView(R.id.edit_replace_id)
    TextView editReplaceId;
    @BindView(R.id.edit_rlvideo_id)
    RelativeLayout editRlvideoId;
    @BindView(R.id.edit_edittext_id)
    REditText editEdittextId;
    @BindView(R.id.edit_number_id)
    TextView editNumberId;
    @BindView(R.id.edit_rledittext_id)
    RelativeLayout editRledittextId;
    @BindView(R.id.more_topic)
    TextView moreTopic;
    @BindView(R.id.edit_middleToobar_id)
    LinearLayout editMiddleToobarId;
    @BindView(R.id.edit_flowgroupview_id)
    FlowGroupView editFlowgroupviewId;
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.edit_llchoice_id)
    RelativeLayout editLlchoiceId;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.edit_button_id)
    Button editButtonId;
    @BindView(R.id.edit_rlayout_id)
    RelativeLayout editRlayoutId;
    @BindView(R.id.layout_open)
    LinearLayout layoutOpen;
    private int open_type = 1;
    PopupWindow popMenu;
    private static final String[] SELECTIMAGES = {
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.TITLE,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media.DATE_MODIFIED,
            MediaStore.Images.Media.LATITUDE,
            MediaStore.Images.Media.LONGITUDE,
            MediaStore.Images.Media.SIZE
    };
    //声明AMapLocationClient类对象
    AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    @BindView(R.id.channel_img)
    ImageView channelImg;
    @BindView(R.id.tv_channel)
    TextView tvChannel;
    @BindView(R.id.channel_next)
    ImageView channelNext;
    @BindView(R.id.channel_recycler)
    RecyclerView channelRecycler;
    @BindView(R.id.num_layout)
    LinearLayout numLayout;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.share_layout)
    RelativeLayout shareLayout;
    @BindView(R.id.button_open)
    Button buttonOpen;
    @BindView(R.id.button_private)
    Button buttonPrivate;
    private int mHeight;
    private int middleHeight;
    private int maxHeight;
    private String path;
    private String latitude;
    private String longitude;
    private LatLonPoint latLonPoint;
    private String place;
    private GeocodeSearch geocoderSearch;
    private String user_token;
    private String user_id;
    private int[] channel_arrays;
    private boolean lenTips = true;

    private int MAX_LENGTH = 200;
    private MyTopic topic;
    private List<Integer> type_list;
    private String mimeType;
    private LocalMedia mMedia;
    private String neighborhood;
    private String district;
    private String city;
    private String province;
    private String country;
    private String aoiName;
    private OSSClient oss;
    private String stsServer;
    private String url;
    private double lat;
    private double lng;
    private String position_areas;
    private String position_city;
    private String position_address;

    private void setCurrentLocationDetails(LatLonPoint latLonPoint) {
// 地址逆解析
        geocoderSearch = new GeocodeSearch(getApplicationContext());
        geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
                place = result.getRegeocodeAddress().getFormatAddress();
                country = result.getRegeocodeAddress().getCountry();
                province = result.getRegeocodeAddress().getProvince();
                neighborhood = result.getRegeocodeAddress().getNeighborhood();
                district = result.getRegeocodeAddress().getDistrict();
                String towncode = result.getRegeocodeAddress().getTowncode();
                city = result.getRegeocodeAddress().getCity();
                Log.e("formatAddress", "formatAddress:" + place);
                Log.e("formatAddress", "rCode:" + rCode);

                for (int i = 0; i < result.getRegeocodeAddress().getAois().size(); i++) {
                    aoiName = result.getRegeocodeAddress().getAois().get(i).getAoiName();
                    Log.e("formatAddress", "aoiName:" + aoiName);
                }
                Log.e("formatAddress", "neighborhood:" + neighborhood);
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });
        // 第一个参数表示一个Latlng(经纬度)，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 25, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        topic = new MyTopic();
        topic.setObjectRule("#");
        type_list = new ArrayList<>();
        user_token = SpUtils.getString(PublishYoXiuActivity.this, "user_token", null);
        user_id = SpUtils.getString(PublishYoXiuActivity.this, "user_id", null);
        uploadYoXiuImage();
        mPresenter.getRecommendTopic(user_id, user_token);
        editEdittextId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = editEdittextId.getText().length();
                editNumberId.setText(length + "");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 获取回调的逆地址内容
     */


    @Override
    protected void initView() {
        super.initView();


        Intent intent = getIntent();
        latitude = intent.getStringExtra("latitude");
        longitude = intent.getStringExtra("longitude");
        latLonPoint = new LatLonPoint(Double.valueOf(latitude), Double.valueOf(longitude));

        Log.d("PublishYoXiuActivity", latitude);
        Log.d("PublishYoXiuActivity", longitude);
        if (latitude.equals("0") && longitude.equals("0")) {
            shortToast("经纬度为空");
            publishPlace.setText("添加地点");
        } else {
//            setCurrentLocationDetails(latLonPoint);
        }

        path = intent.getStringExtra("path");


        Log.d("PublishYoXiuActivity", path);
        Glide.with(this).load(path).into(editVideoId);
        mMedia = new LocalMedia();
        mMedia.setPath(path);
        String fileExtension = MimeTypeMap.getFileExtensionFromUrl(path);
        mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
        if (mimeType != null && mimeType.contains("video")) {

            mMedia.setPictureType(Constant.PARAM_VIDEO_MP4);
            Glide.with(this).load(ImagePickAction.getVideoThumb(mMedia.getPath(), 1)).into(editVideoId);

        } else {
            editStartId.setVisibility(View.GONE);
            Glide.with(this).load(path).into(editVideoId);
        }
//        view = (FlowGroupView) findViewById(R.id.edit_flowgroupview_id);
        for (int i = 0; i < names.size(); i++) {
            addTextView(names.get(i));
        }


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_publish_yo_xiu;
    }

    @Override
    protected PublishYoXiuContract.Presenter createPresenter() {
        return new PublishYoXiuPresenter(this);
    }

    /**
     * 动态添加布局
     *
     * @param str
     */
    private void addTextView(String str) {
        TextView child = new TextView(this);
        Drawable drawableLeft = getResources().getDrawable(
                R.mipmap.huati_tuijian);

        child.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                null, null, null);
        child.setCompoundDrawablePadding(4);


        ViewGroup.MarginLayoutParams params =
                new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                        ViewGroup.MarginLayoutParams.WRAP_CONTENT);
        params.setMargins(15, 15, 15, 15);
        child.setLayoutParams(params);
        child.setBackgroundResource(R.drawable.fillet_style);
        child.setText(str);
        child.setTextColor(Color.BLACK);
        initEvents(child);//监听
        editFlowgroupviewId.addView(child);

    }

    /**
     * 为每个view 添加点击事件
     */
    private void initEvents(final TextView tv) {
        tv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                //话题内容
                topic.setObjectText(tv.getText().toString());
                editEdittextId.setObject(topic);// 设置话题
                Toast.makeText(PublishYoXiuActivity.this, tv.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @SuppressLint("ResourceAsColor")
    @OnClick({R.id.edit_back_id, R.id.edit_publish_id, R.id.button_open, R.id.button_private, R.id.edit_start_id, R.id.publish_place, R.id.edit_replace_id, R.id.more_topic, R.id.edit_button_id, R.id.channel_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edit_back_id:
                initPopuptWindow();
                break;
            case R.id.edit_publish_id:

                Integer[] topic_arrays = type_list.toArray(new Integer[type_list.size()]);
                for (int i = 0; i < topic_arrays.length; i++) {
                    Log.d("PublishYoXiuActivity", "integers[i]:" + topic_arrays[i]);
                }
                if (position_address == null) {

                    if (mimeType != null && mimeType.contains("video")) {
                        String uploadVideo = uploadYoXiuVideo();
                        mPresenter.publishYoXiu(user_id, user_token, 0, uploadVideo, 2, editEdittextId.getText().toString().trim(), channel_arrays, open_type, 1, publishPlace.getText().toString().trim(), position_areas, "", position_city, longitude, latitude, "", "");


                    } else {
                        String uploadImage = uploadYoXiuImage();
                        mPresenter.publishYoXiu(user_id, user_token, 0, uploadImage, 1, editEdittextId.getText().toString().trim(), channel_arrays, open_type, 1, publishPlace.getText().toString().trim(), position_areas, "", position_city, longitude, latitude, "", "");
                    }
                } else {

                    if (mimeType != null && mimeType.contains("video")) {
                        String uploadVideo = uploadYoXiuVideo();
                        mPresenter.publishYoXiu(user_id, user_token, 0, uploadVideo, 2, editEdittextId.getText().toString().trim(), channel_arrays, open_type, 1, publishPlace.getText().toString().trim(), position_areas, position_address, position_city, longitude, latitude, "", "");


                    } else {
                        String uploadImage = uploadYoXiuImage();
                        mPresenter.publishYoXiu(user_id, user_token, 0, uploadImage, 1, editEdittextId.getText().toString().trim(), channel_arrays, open_type, 1, publishPlace.getText().toString().trim(), position_areas, position_address, position_city, longitude, latitude, "", "");
                    }


                }
                Log.d("TAGS", position_city);
                Log.d("TAGS", longitude);
                Log.d("TAGS", latitude);
                Log.d("TAGS", editEdittextId.getText().toString().trim());
                Log.d("TAGS", publishPlace.getText().toString().trim());
                Log.d("TAGS", position_areas);
//                Log.d("TAGS", position_address);
                for (int i = 0; i < channel_arrays.length; i++) {
                    Log.d("TA", channel_arrays[i] + "");

                }

                break;
            case R.id.button_open:
                open_type = 1;
                editButtonId.setText("公开");
                Drawable drawableLeft = getResources().getDrawable(
                        R.mipmap.gongkai_xz);

                editButtonId.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                        null, null, null);

                editButtonId.setTextColor(Color.parseColor("#FA800A"));
                layoutOpen.setVisibility(View.GONE);
                break;
            case R.id.button_private:
                open_type = 2;
                editButtonId.setText("私密");
                Drawable drawableLeft1 = getResources().getDrawable(
                        R.mipmap.simi_wxz);

                editButtonId.setCompoundDrawablesWithIntrinsicBounds(drawableLeft1,
                        null, null, null);
                editButtonId.setTextColor(R.color.black);
                layoutOpen.setVisibility(View.GONE);
                break;
            case R.id.edit_start_id:
                JzvdStd.startFullscreen(PublishYoXiuActivity.this, JzvdStd.class, path, "");
                break;
            case R.id.publish_place:
                Intent intent = new Intent(PublishYoXiuActivity.this, SearchActivity.class);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                intent.putExtra("place", publishPlace.getText().toString());
                intent.putExtra("yo_type", 1);
                intent.putExtra("country", country);

                startActivityForResult(intent, 1);
                break;
            case R.id.edit_replace_id:
                Intent intent1 = new Intent(PublishYoXiuActivity.this, SourceChooseActivity.class);
                intent1.putExtra("positionName", publishPlace.getText().toString().trim());
                intent1.putExtra("desc", editEdittextId.getText().toString().trim());
                intent1.putExtra("type", 1);
                startActivityForResult(intent1, 1);

                break;
            case R.id.more_topic:
                startActivityForResult(new Intent(PublishYoXiuActivity.this, MoreTopicActivity.class), 1);
                break;
            case R.id.edit_button_id:
                layoutOpen.setVisibility(View.VISIBLE);

                break;
            case R.id.channel_next:
                Intent intent2 = new Intent(PublishYoXiuActivity.this, ChannelActivity.class);
                intent2.putExtra("type", 1);
                startActivityForResult(intent2, 1);
                break;
        }
    }

    public void initPopup() {
        View view = LayoutInflater.from(PublishYoXiuActivity.this).inflate(R.layout.like_layout, null);
        popup = new PopupWindow(view, DensityUtil.dp2px(PublishYoXiuActivity.this, 300), DensityUtil.dp2px(PublishYoXiuActivity.this, 145), true);
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
        popup.showAtLocation(findViewById(R.id.edit_rlayout_id), Gravity.CENTER, 0, 0);
    }

    public void backgroundAlpha(float bgAlpha) {

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        getWindow().setAttributes(lp); //act 是上下文context

    }

    //隐藏事件PopupWindow
    private class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1.0f);
        }
    }

    public void like() {
        tv_message.setTextColor(Color.parseColor("#FA800A"));
        tv_message_two.setTextColor(Color.parseColor("#FA800A"));
        tv_message_three.setTextColor(Color.parseColor("#FA800A"));
        backgroundAlpha(0.6f);
        tv_message.setText("Hi~");
        img_tip.setImageResource(R.mipmap.stamp_fenxiang);
        tv_message_two.setText("yo秀发布成功！");
        tv_message_two.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        tv_message_three.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        tv_message_three.setText("棒棒嗒~");
    }

    private String uploadYoXiuImage() {
        final String endpoint = "oss-cn-beijing.aliyuncs.com";
        final String bucketName = "iyoyogo";
        final String accessKeyId = "LTAIql2brWD0qbEN";
        final String accessKeySecret = "C74lDBcL1AqzEdIvHZkYMJlSNmRtby";
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000);
        conf.setSocketTimeout(15 * 1000);
        conf.setMaxConcurrentRequest(8);
        conf.setMaxErrorRetry(2);
        OSSClient ossClient = new OSSClient(PublishYoXiuActivity.this, endpoint, credentialProvider, conf);
        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentType("image/jpeg");
        int min = 10000;
        int max = 99999;
        Calendar calendar = Calendar.getInstance();
//获取系统的日期
//年
        int year = calendar.get(Calendar.YEAR);
//月
        int month = calendar.get(Calendar.MONTH) + 1;
//日
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        Random random = new Random();
        int num = random.nextInt(max) % (max - min + 1) + min;
        String name = user_id + "/yox/" + year + "/" + month + "/" + day + "/" + System.currentTimeMillis() + num + ".jpg";
        PutObjectRequest put = new PutObjectRequest(bucketName, name, path);
        put.setMetadata(objectMeta);
        try {
            PutObjectResult result = ossClient.putObject(put);
            if (result != null && result.getStatusCode() == 200) {
                url = "http://" + bucketName + "." + endpoint + "/" + name;
                Log.d("PublishYoXiuActivity", url);
            }
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return url;
    }

    private String uploadYoXiuVideo() {
/*
'aliyun_oss' => [
        'accessid'  => 'LTAInRzzjv0TZcA5',
        'accesskey' => 'jQZXJDYzAU7Ki0DfZvfIoU3PxazsLy',
        'host'      => 'http://xzdtest.oss-cn-beijing.aliyuncs.com',
        'dirname'   => 'xzdtest',
    ],
* 'accessid'  => 'LTAIql2brWD0qbEN',
        'accesskey' => 'C74lDBcL1AqzEdIvHZkYMJlSNmRtby',
        'host'      => 'http://iyoyogo.oss-cn-beijing.aliyuncs.com',*/
       /* 'accessid'  => 'LTAIql2brWD0qbEN',
                'accesskey' => 'C74lDBcL1AqzEdIvHZkYMJlSNmRtby',
                'host'      => 'http://iyoyogo.oss-cn-beijing.aliyuncs.com',*/
        final String endpoint = "oss-cn-beijing.aliyuncs.com";
        final String bucketName = "iyoyogo";
        final String accessKeyId = "LTAIql2brWD0qbEN";
        final String accessKeySecret = "C74lDBcL1AqzEdIvHZkYMJlSNmRtby";
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000);
        conf.setSocketTimeout(15 * 1000);
        conf.setMaxConcurrentRequest(8);
        int min = 10000;
        int max = 99999;
        Calendar calendar = Calendar.getInstance();
//获取系统的日期
//年
        int year = calendar.get(Calendar.YEAR);
//月
        int month = calendar.get(Calendar.MONTH) + 1;
//日
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        Random random = new Random();
        int num = random.nextInt(max) % (max - min + 1) + min;
        conf.setMaxErrorRetry(2);
        OSSClient ossClient = new OSSClient(PublishYoXiuActivity.this, endpoint, credentialProvider, conf);
        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentType("video/mpeg4");
        String name = user_id + "/yox_video/" + year + "/" + month + "/" + day + "/" + System.currentTimeMillis() + num + ".mp4";
        PutObjectRequest put = new PutObjectRequest(bucketName, name, path);
        put.setMetadata(objectMeta);
        try {
            PutObjectResult result = ossClient.putObject(put);
            if (result != null && result.getStatusCode() == 200) {
                url = "http://" + bucketName + "." + endpoint + "/" + name;
                Log.d("PublishYoXiuActivity", url);
            }
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return url;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //选择频道
        if (requestCode == 1 && resultCode == 1) {
            channelRecycler.setVisibility(View.VISIBLE);
            line2.setVisibility(View.VISIBLE);
            tvChannel.setVisibility(View.GONE);
            channel_arrays = data.getIntArrayExtra("channel_array");
            for (int i = 0; i < channel_arrays.length; i++) {
                Log.d("PublishYoXiuActivity", "channel_arrays[i]:" + channel_arrays[i]);
            }
            ArrayList<String> channel_list = data.getStringArrayListExtra("channel_list");
            for (int i = 0; i < channel_list.size(); i++) {
                Log.d("PublishYoXiuActivity", channel_list.get(i));
            }
            ChannelMessageAdapter adapter = new ChannelMessageAdapter(channel_list);
            channelRecycler.setLayoutManager(new GridLayoutManager(PublishYoXiuActivity.this, 3));
            channelRecycler.setAdapter(adapter);
        }
        //创建自定义点回来
        if (requestCode == 1 && resultCode == 2) {


            position_address = data.getStringExtra("position_address");
            position_city = data.getStringExtra("position_city");
            position_areas = data.getStringExtra("position_areas");
            longitude = data.getStringExtra("longitude");
            latitude = data.getStringExtra("latitude");
            place = data.getStringExtra("place");
            String type = data.getStringExtra("type");
            publishPlace.setText(place);
            Log.d("PublishYoXiuActivity", "latitude=" + latitude + "longitude= " + longitude);
        }
        //选择位置回来
        if (requestCode == 1 && resultCode == 3) {
            position_address = data.getStringExtra("position_address");
            position_city = data.getStringExtra("position_city");
            position_areas = data.getStringExtra("position_areas");
            longitude = data.getStringExtra("longitude");
            latitude = data.getStringExtra("latitude");
            place = data.getStringExtra("place");
            publishPlace.setText(place);
        }
        //更多话题
        if (requestCode == 1 && resultCode == 6) {
            String topicName = data.getStringExtra("topic");
            int type_id = data.getIntExtra("type_id", 0);

            type_list.add(type_id);
            topic.setObjectText(topicName);
            editEdittextId.setObject(topic);// 设置话题
        }
        //替换图片
        if (requestCode == 1 && resultCode == 88) {
            String latitude = data.getStringExtra("latitude");
            String longitude = data.getStringExtra("longitude");
            String desc = data.getStringExtra("desc");
            String positionName = data.getStringExtra("positionName");
            String path = data.getStringExtra("path");
            latLonPoint = new LatLonPoint(Double.valueOf(latitude), Double.valueOf(longitude));
            editEdittextId.setText(desc);
            publishPlace.setText(positionName);
            setCurrentLocationDetails(latLonPoint);
            Glide.with(this).load(path).into(editVideoId);
            mMedia = new LocalMedia();
            mMedia.setPath(path);
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(path);
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
            if (mimeType != null && mimeType.contains("video")) {

                mMedia.setPictureType(Constant.PARAM_VIDEO_MP4);
                Glide.with(this).load(ImagePickAction.getVideoThumb(mMedia.getPath(), 1)).into(editVideoId);

            } else {
                editStartId.setVisibility(View.GONE);
                Glide.with(this).load(path).into(editVideoId);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.onDestroy();//销毁定位客户端。
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mLocationClient != null) {
            mLocationClient.startLocation(); // 启动定位
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mLocationClient != null) {
            mLocationClient.stopLocation();//停止定位
        }
    }


    @Override
    public void getRecommendTopicSuccess(List<HotTopicBean.DataBean.ListBean> list) {
        for (int i = 0; i < list.size(); i++) {
            addTextView(list.get(i).getTopic());
            type_list.add(list.get(i).getId());
        }
    }

    @Override
    public void publishYoXiuSuccess() {
        initPopup();
        like();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    class MyTopic extends RObject {
        private String id;

        // 其他属性...

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
        Button popup_no_id = pop_view.findViewById(R.id.popup_no_id);
        Button popup_yes_id = pop_view.findViewById(R.id.popup_yes_id);
        TextView pop_content_id = pop_view.findViewById(R.id.pop_content_id);
        TextView pop_title_id = pop_view.findViewById(R.id.pop_title_id);
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
        popup_yes_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position_address == null) {

                    if (mimeType != null && mimeType.contains("video")) {
                        String uploadVideo = uploadYoXiuVideo();
                        mPresenter.publishYoXiu(user_id, user_token, 0, uploadVideo, 2, editEdittextId.getText().toString().trim(), channel_arrays, open_type, 3, publishPlace.getText().toString().trim(), position_areas, "", position_city, longitude, latitude, "", "");


                    } else {
                        String uploadImage = uploadYoXiuImage();
                        mPresenter.publishYoXiu(user_id, user_token, 0, uploadImage, 1, editEdittextId.getText().toString().trim(), channel_arrays, open_type, 3, publishPlace.getText().toString().trim(), position_areas, "", position_city, longitude, latitude, "", "");
                    }
                } else {

                    if (mimeType != null && mimeType.contains("video")) {
                        String uploadVideo = uploadYoXiuVideo();
                        mPresenter.publishYoXiu(user_id, user_token, 0, uploadVideo, 2, editEdittextId.getText().toString().trim(), channel_arrays, open_type, 3, publishPlace.getText().toString().trim(), position_areas, position_address, position_city, longitude, latitude, "", "");


                    } else {
                        String uploadImage = uploadYoXiuImage();
                        mPresenter.publishYoXiu(user_id, user_token, 0, uploadImage, 1, editEdittextId.getText().toString().trim(), channel_arrays, open_type, 3, publishPlace.getText().toString().trim(), position_areas, position_address, position_city, longitude, latitude, "", "");
                    }


                }
                finish();
                popMenu.dismiss();
            }
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

    @Override
    public void onBackPressed() {
        initPopuptWindow();

    }
}
