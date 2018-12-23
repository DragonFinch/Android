package com.iyoyogo.android.ui.home.yoji;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.Bean;
import com.iyoyogo.android.adapter.PublishYoJiAdapter;
import com.iyoyogo.android.app.AeDITEXT;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.yoji.publish.MessageBean;
import com.iyoyogo.android.bean.yoxiu.topic.HotTopicBean;
import com.iyoyogo.android.contract.PublishYoJiContract;
import com.iyoyogo.android.presenter.PublishYoJiPresenter;
import com.iyoyogo.android.ui.common.SearchActivity;
import com.iyoyogo.android.ui.home.yoxiu.ChannelActivity;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.imagepicker.activities.ImagesPickActivity;
import com.iyoyogo.android.view.DrawableTextView;
import com.iyoyogo.android.widget.FlowGroupView;
import com.iyoyogo.android.widget.flow.FlowLayout;
import com.iyoyogo.android.widget.flow.TagAdapter;
import com.iyoyogo.android.widget.flow.TagFlowLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PublishYoJiActivity extends BaseActivity<PublishYoJiContract.Presenter> implements PublishYoJiContract.View {

    List<MessageBean> list = new ArrayList<>();
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.tv_publish)
    TextView tvPublish;
    @BindView(R.id.layout_bar)
    LinearLayout layoutBar;
    @BindView(R.id.img_yoji)
    ImageView imgYoji;
    @BindView(R.id.tv_add_cover)
    TextView tvAddCover;
    @BindView(R.id.et_title)
    AeDITEXT etTitle;
    @BindView(R.id.text_title_length)
    TextView textTitleLength;
    @BindView(R.id.et_content)
    AeDITEXT etContent;
    @BindView(R.id.text_content_length)
    TextView textContentLength;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.more_topic)
    TextView moreTopic;
    @BindView(R.id.edit_flowgroupview_id)
    FlowGroupView editFlowgroupviewId;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.et_cost)
    AeDITEXT etCost;
    @BindView(R.id.recycler_publish_yoji)
    RecyclerView recyclerPublishYoji;
    @BindView(R.id.img_channel)
    ImageView imgChannel;
    @BindView(R.id.dt_gochennel)
    DrawableTextView dtGochennel;
    @BindView(R.id.tag_flowLayout)
    FlowGroupView tagFlowLayout;
    @BindView(R.id.next)
    ImageView next;
    @BindView(R.id.tv_async)
    TextView tvAsync;
    @BindView(R.id.rb_moment)
    RadioButton rbMoment;
    @BindView(R.id.rb_wechat)
    RadioButton rbWechat;
    @BindView(R.id.rb_sina)
    RadioButton rbSina;
    @BindView(R.id.rb_qq)
    RadioButton rbQq;
    @BindView(R.id.radioGroup_share)
    RadioGroup radioGroupShare;
    @BindView(R.id.scroll)
    NestedScrollView scroll;
    @BindView(R.id.relative_recycler)
    RelativeLayout relativeRecycler;
    private List<Integer> type_list = new ArrayList<>();
    private ArrayList<String> path_list;
    private DrawableTextView location_tv;
    private TagFlowLayout flow_group;
    private DrawableTextView label_tv;
    private TextView child;
    TagAdapter<Bean> tagAdapter;
    private String user_token;
    private String user_id;
    private String path;
    String url;
    private ArrayList<String> channel_list;
    private List<Integer> channel_ids;
    private List<MessageBean> mList;
    private RecyclerView recycler_inner;
    private PublishYoJiAdapter publishYoJiAdapter;
    private int index;
    ArrayList<String> uris = new ArrayList<>();
    private List<Integer> label_ids = new ArrayList<>();
    private String place;
    private String province;
    private String neighborhood;
    private String city;
    private String end_time;
    private String start_time;
    private String place1;
    private String country;
    private String district;
    private double latitude;
    private double longitude;
    private MessageBean messageBean1=new MessageBean();
    private MessageBean publishYoJiRequest=new MessageBean();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_publish_yo_ji;
    }

    @Override
    protected PublishYoJiContract.Presenter createPresenter() {
        return new PublishYoJiPresenter(this);
    }


    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        path_list = intent.getStringArrayListExtra("path_list");
        path = path_list.get(0);
        Glide.with(this).load(path).into(imgYoji);
      new Thread(new Runnable() {
          @Override
          public void run() {
              ossUpload(path_list);
          }
      }).start();
        MessageBean messageBean = new MessageBean();
        messageBean.setStart_date("开始日期");
        messageBean.setEnd_date("结束日期");
        messageBean.setPosition_name("添加位置");
        label_ids.add(0);
        messageBean.setLabel_ids(label_ids);
        messageBean.setPosition_address("2");
        messageBean.setPosition_areas("1");
        mList = new ArrayList<>();
        messageBean.setLogos(uris);
        mList.add(messageBean);
        etTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                etCost.clearFocus();
//                etCost.setFocusable(false);
//                etContent.clearFocus();
//                etContent.setFocusable(false);
                etTitle.clearFocus();
                etTitle.setFocusable(false);
            }
        });
        etCost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                etCost.clearFocus();
                etCost.setFocusable(false);
                /*etContent.clearFocus();
                etContent.setFocusable(false);
                etTitle.clearFocus();
                etTitle.setFocusable(false);*/
            }
        });
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              /*  etCost.clearFocus();
                etCost.setFocusable(false);

                etTitle.clearFocus();
                etTitle.setFocusable(false);*/
                etContent.clearFocus();
                etContent.setFocusable(false);
            }
        });
        etContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//        ScrollLinearLayoutManager scrollLinearLayoutManager = new LinearLayoutMananger(PublishYoJiActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PublishYoJiActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerPublishYoji.setLayoutManager(linearLayoutManager);
        publishYoJiAdapter = new PublishYoJiAdapter(PublishYoJiActivity.this, mList);
        recyclerPublishYoji.setAdapter(publishYoJiAdapter);

        publishYoJiAdapter.setOnPlayClickListener(new PublishYoJiAdapter.OnLocationClickListener() {
            @Override
            public void onAddAddressClick(int position, PublishYoJiAdapter.ViewHolder holder) {
                Intent intent = new Intent(PublishYoJiActivity.this, SearchActivity.class);
                intent.putExtra("latitude", "0");
                intent.putExtra("longitude", "0");
                intent.putExtra("place", "添加位置");
                startActivityForResult(intent, 1);
                location_tv = holder.itemView.findViewById(R.id.location_tv);
            }

            @Override
            public void onTitleEdit(EditText title, EditText content, EditText cost) {

            }

            @Override
            public void onCoverClick(int position) {

            }


            @Override
            public void onLocationClick(int position) {

            }

            @Override
            public void onStartTimeClick(int postion, String startTime) {
                start_time = startTime.trim();
                publishYoJiRequest.setStart_date(start_time);
                messageBean1.setStart_date(start_time);

            }

            @Override
            public void onEndTimeClick(int postion, String endTime) {
                end_time = endTime.trim();
                publishYoJiRequest.setEnd_date(end_time);
                messageBean1.setEnd_date(end_time);
            }

            @Override
            public void onTagClick(int position, PublishYoJiAdapter.ViewHolder holder) {
                Intent intent = new Intent(PublishYoJiActivity.this, ChooseSignActivity.class);
                startActivityForResult(intent, 1);
                flow_group = holder.itemView.findViewById(R.id.flow_group);
                label_tv = holder.itemView.findViewById(R.id.label_tv);
            }

            @Override
            public void onTagReomveClick(int position, int index) {

            }

            @Override
            public void onImageReomveClick(int position, int index) {

            }

            @Override
            public void onImageAddClick(int position, PublishYoJiAdapter.ViewHolder holder) {
                Intent intent = new Intent(PublishYoJiActivity.this, ImagesPickActivity.class);
                intent.putExtra("type", 1);
                index = position;
                startActivityForResult(intent, 1);
            }
        });
       /* etContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (v.getId()) {
                    case R.id.et_content:
                    case R.id.et_title:
                        // 解决scrollView中嵌套EditText导致不能上下滑动的问题
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        switch (event.getAction() & MotionEvent.ACTION_MASK) {
                            case MotionEvent.ACTION_UP:
                                v.getParent().requestDisallowInterceptTouchEvent(false);
                                break;
                        }
                }

                return false;
            }
        });
        etTitle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (v.getId()) {
                    case R.id.et_content:
                    case R.id.et_title:
                        // 解决scrollView中嵌套EditText导致不能上下滑动的问题
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        switch (event.getAction() & MotionEvent.ACTION_MASK) {
                            case MotionEvent.ACTION_UP:
                                v.getParent().requestDisallowInterceptTouchEvent(false);
                                break;
                        }
                }

                return false;
            }
        });
        etCost.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (v.getId()) {
                    case R.id.et_content:
                    case R.id.et_title:
                        // 解决scrollView中嵌套EditText导致不能上下滑动的问题
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        switch (event.getAction() & MotionEvent.ACTION_MASK) {
                            case MotionEvent.ACTION_UP:
                                v.getParent().requestDisallowInterceptTouchEvent(false);
                                break;
                        }
                }

                return false;
            }
        });*/
        //


    }

    private void ossUpload(final ArrayList<String> urls) {

        if (urls.size() <= 0) {
            // 文件全部上传完毕，这里编写上传结束的逻辑，如果要在主线程操作，最好用Handler或runOnUiThead做对应逻辑
            return;// 这个return必须有，否则下面报越界异常，原因自己思考下哈
        }
        final String url = urls.get(0);
        if (TextUtils.isEmpty(url)) {
            urls.remove(0);
            // url为空就没必要上传了，这里做的是跳过它继续上传的逻辑。
            ossUpload(urls);
            return;
        }

        File file = new File(url);
        if (null == file || !file.exists()) {
            urls.remove(0);
            // 文件为空或不存在就没必要上传了，这里做的是跳过它继续上传的逻辑。
            ossUpload(urls);
            return;
        }
        // 文件后缀
        String fileSuffix = "";
        if (file.isFile()) {
            // 获取文件后缀名
            fileSuffix = file.getName().substring(file.getName().lastIndexOf("."));
            Log.d("PublishYoJiActivity", "fileSuffix" + fileSuffix);
        }
        // 文件标识符objectKey
        /*
         *


         * */
        String name = "yoyogo/yoji/image" + System.currentTimeMillis() + ".jpg";
        final String bucketName = "xzdtest";
        final String accessKeyId = "LTAInRzzjv0TZcA5";
        final String accessKeySecret = "jQZXJDYzAU7Ki0DfZvfIoU3PxazsLy";
        final String endpoint = "oss-cn-beijing.aliyuncs.com";
        final String objectKey = "alioss_" + System.currentTimeMillis() + fileSuffix;

        // 下面3个参数依次为bucket名，ObjectKey名，上传文件路径
        PutObjectRequest put = new PutObjectRequest("xzdtest", name, url);

        // 设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                // 进度逻辑
            }
        });
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        // 异步上传
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000);
        conf.setSocketTimeout(15 * 1000);
        conf.setMaxConcurrentRequest(8);
        conf.setMaxErrorRetry(2);
        OSSClient ossClient = new OSSClient(PublishYoJiActivity.this, endpoint, credentialProvider, conf);
        OSSAsyncTask task = ossClient.asyncPutObject(put,
                new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                    @Override
                    public void onSuccess(PutObjectRequest request, PutObjectResult result) { // 上传成功
                        urls.remove(0);
                        ossUpload(urls);// 递归同步效果
                        String uri = "https://" + bucketName + "." + endpoint + "/" + name;

                        uris.add(uri);
                        Log.d("PublishYoJiActivity", "urls.size():" + urls.size());
                        for (int i = 0; i < uris.size(); i++) {
                            Log.d("PublishYoJiActivity", uris.get(i));
                        }
                    }

                    @Override
                    public void onFailure(PutObjectRequest request, ClientException clientExcepion,
                                          ServiceException serviceException) { // 上传失败

                        // 请求异常
                        if (clientExcepion != null) {
                            // 本地异常如网络异常等
                            clientExcepion.printStackTrace();
                        }
                        if (serviceException != null) {
                            // 服务异常
                            Log.e("ErrorCode", serviceException.getErrorCode());
                            Log.e("RequestId", serviceException.getRequestId());
                            Log.e("HostId", serviceException.getHostId());
                            Log.e("RawMessage", serviceException.getRawMessage());
                        }
                    }
                });
        // task.cancel(); // 可以取消任务
        // task.waitUntilFinished(); // 可以等待直到任务完成
    }

    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        user_id = SpUtils.getString(PublishYoJiActivity.this, "user_id", null);
        user_token = SpUtils.getString(PublishYoJiActivity.this, "user_token", null);
        mPresenter.getRecommendTopic(user_id, user_token);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setCurrentLocationDetails(LatLonPoint latLonPoint) {
// 地址逆解析
        GeocodeSearch geocoderSearch = new GeocodeSearch(getApplicationContext());
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
                    String aoiName = result.getRegeocodeAddress().getAois().get(i).getAoiName();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 3) {
            place1 = data.getStringExtra("place");
            Log.d("PublishYoJiActivity", place1);
            String type = data.getStringExtra("type");
            latitude = data.getDoubleExtra("latitude", 0.0);
            longitude = data.getDoubleExtra("longitude", 0.0);
            LatLonPoint latLonPoint = new LatLonPoint(latitude, longitude);
            publishYoJiRequest.setLogos(uris);
            publishYoJiRequest.setPosition_name(place1);
            publishYoJiRequest.setPosition_areas(country + "," + province + "," + city + "," + district);
            publishYoJiRequest.setLat(String.valueOf(latitude));
            publishYoJiRequest.setLng(String.valueOf(longitude));
            publishYoJiRequest.setPosition_address(place);
            messageBean1.setPosition_name(place1);
            messageBean1.setPosition_areas(country + "," + province + "," + city + "," + district);
            messageBean1.setLat(String.valueOf(latitude));
            messageBean1.setLng(String.valueOf(longitude));
            messageBean1.setPosition_address(place);





            setCurrentLocationDetails(latLonPoint);
            location_tv.setText(place1);
        }
        if (requestCode == 1 && resultCode == 55) {
            label_tv.setVisibility(View.GONE);
            ArrayList<Bean> sign_list = (ArrayList<Bean>) data.getSerializableExtra("sign_list");
            for (int i = 0; i < sign_list.size(); i++) {
                String label = sign_list.get(i).getLabel();
                Log.d("PublishYoJiActivity", label);
            }
            publishYoJiRequest.setLabel_ids(label_ids);
            if (tagAdapter == null) {
                tagAdapter = new TagAdapter<Bean>(sign_list) {

                    @Override
                    public View getView(FlowLayout parent, int position, Bean bean) {

                        View contentView = LayoutInflater.from(PublishYoJiActivity.this).inflate(R.layout.item_label, tagFlowLayout, false);
                        TextView tv = contentView.findViewById(R.id.lable_name_tv);
                   /* if (labels.get(position).getType().equals("A")) {
                        tv.setBackgroundResource(R.drawable.label_bg_deserve_to_do);
                        tv.setTextColor(mContext.getResources().getColor(R.color.orgeen_color));
                    } else if (labels.get(position).getTag_type().equals("B")) {

                        tv.setBackgroundResource(R.drawable.label_bg_fkzn);
                        tv.setTextColor(mContext.getResources().getColor(R.color.blue_color));
                    } else if (labels.get(position).getTag_type().equals("C")) {
                        tv.setBackgroundResource(R.drawable.label_bg_deserve_to_do);
                        tv.setTextColor(mContext.getResources().getColor(R.color.black));
                    }*/
                        tv.setText(bean.getLabel());

                        return contentView;
                    }
                };
                flow_group.setAdapter(tagAdapter);
            } else {
                tagAdapter.notifyDataChanged();
            }
            label_ids = new ArrayList<>();
            for (int i = 0; i < sign_list.size(); i++) {
                int label_id = sign_list.get(i).getLabel_id();
                label_ids.add(label_id);
            }
                publishYoJiRequest.setLabel_ids(label_ids);
                messageBean1.setLabel_ids(label_ids);

        }

        if (requestCode == 1 && resultCode == 66) {
//                label_tv.setVisibility(View.GONE);
//                ArrayList<Bean> sign_list = (ArrayList<Bean>) data.getSerializableExtra("sign_list");
            int[] channel_arrays = data.getIntArrayExtra("channel_array");
            channel_ids = new ArrayList<>();
            for (int i = 0; i < channel_arrays.length; i++) {
                channel_ids.add(channel_arrays[i]);
            }
            channel_list = data.getStringArrayListExtra("channel_list");
            for (int i = 0; i < channel_list.size(); i++) {
                addTextView(channel_list.get(i), tagFlowLayout);
            }
        }
        if (requestCode == 1 && resultCode == 4) {
            ArrayList<String> path_list = data.getStringArrayListExtra("path_list");
            for (int i = 0; i < path_list.size(); i++) {
                Log.d("PublishYoJiActivity", path_list.get(i));
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ossUpload(path_list);
                }
            }).start();

            messageBean1.setLogos(uris);


            list.add(messageBean1);
            publishYoJiAdapter.addData(index, messageBean1);
        }
    }

    private void addTextView(String str, FlowGroupView flowGroupView) {
        child = new TextView(this);
        child.setCompoundDrawablePadding(4);
        ViewGroup.MarginLayoutParams params =
                new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                        ViewGroup.MarginLayoutParams.WRAP_CONTENT);
        params.setMargins(15, 15, 15, 15);
        child.setLayoutParams(params);
        child.setGravity(Gravity.CENTER);
        child.setBackgroundResource(R.drawable.fillet_style);
        child.setText(str);
        child.setTextColor(Color.BLACK);

        flowGroupView.addView(child);

    }

    private String uploadYoJiImage() {

        final String endpoint = "oss-cn-beijing.aliyuncs.com";
        final String bucketName = "xzdtest";
        final String accessKeyId = "LTAInRzzjv0TZcA5";
        final String accessKeySecret = "jQZXJDYzAU7Ki0DfZvfIoU3PxazsLy";
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000);
        conf.setSocketTimeout(15 * 1000);
        conf.setMaxConcurrentRequest(8);
        conf.setMaxErrorRetry(2);
        OSSClient ossClient = new OSSClient(PublishYoJiActivity.this, endpoint, credentialProvider, conf);
        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentType("image/jpeg");
        String name = "yoyogo/yoji/image" + System.currentTimeMillis() + ".jpg";
        PutObjectRequest put = new PutObjectRequest(bucketName, name, path);
        put.setMetadata(objectMeta);

        try {
            PutObjectResult result = ossClient.putObject(put);
            if (result != null && result.getStatusCode() == 200) {
                url = "https://" + bucketName + "." + endpoint + "/" + name;
                Log.d("PublishYoXiuActivity", url);
            }
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return url;
    }

    private ArrayList<String> uploadAllYoJiImage(List<String> list) {

        final String endpoint = "oss-cn-beijing.aliyuncs.com";
        final String bucketName = "xzdtest";
        final String accessKeyId = "LTAInRzzjv0TZcA5";
        final String accessKeySecret = "jQZXJDYzAU7Ki0DfZvfIoU3PxazsLy";
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000);
        conf.setSocketTimeout(15 * 1000);
        conf.setMaxConcurrentRequest(8);
        conf.setMaxErrorRetry(2);
        OSSClient ossClient = new OSSClient(PublishYoJiActivity.this, endpoint, credentialProvider, conf);
        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentType("image/jpeg");
        ArrayList<String> urls = new ArrayList<>();
        String name = "yoyogo/yoji/image" + System.currentTimeMillis() + ".jpg";
        for (int i = 0; i < list.size(); i++) {
            PutObjectRequest put = new PutObjectRequest(bucketName, name, list.get(i));
            put.setMetadata(objectMeta);

            try {
                PutObjectResult result = ossClient.putObject(put);
                if (result != null && result.getStatusCode() == 200) {
                    url = "https://" + bucketName + "." + endpoint + "/" + name;
                    Log.d("PublishYoXiuActivity", url);
                    urls.add(url);
                }
            } catch (ClientException e) {
                e.printStackTrace();
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }

        return urls;
    }

    @OnClick({R.id.back_img, R.id.tv_add_cover, R.id.more_topic, R.id.next, R.id.tv_publish,R.id.relative_recycler})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relative_recycler:
                shortToast("aaa");
                etCost.clearFocus();
                etCost.setFocusable(false);
                etContent.clearFocus();
                etContent.setFocusable(false);
                etTitle.clearFocus();
                etTitle.setFocusable(false);
                shortToast("vvv");
                break;
            case R.id.back_img:

                break;
            case R.id.tv_add_cover:

                break;
            case R.id.more_topic:

                break;
            case R.id.next:
                Intent intent = new Intent(PublishYoJiActivity.this, ChannelActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.tv_publish:
                String url_cover = uploadYoJiImage();
                ArrayList<String> urls = uploadAllYoJiImage(path_list);
                for (int i = 0; i < urls.size(); i++) {
                    Log.d("PublishYoJiActivity", urls.get(i));
                }
                Log.d("PublishYoJiActivity", mList.toString());


                list.add(publishYoJiRequest);
                String json = new Gson().toJson(list);
                Log.i("数据", "----->  " + json);


//                String json = new Gson().toJson(mList);
                if (tvPay.getText().toString().trim().length() > 0) {
                    if (etTitle.getText().toString().trim().length() > 0) {
                        mPresenter.publishYoJi(user_id, user_token, 0, url_cover, etTitle.getText().toString().trim(), etContent.getText().toString().trim(), Integer.parseInt(etCost.getText().toString().trim()), 1, 1, type_list, channel_ids, json);
                    } else {
                        Toast.makeText(this, "标题不能为空", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请输入价格", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }


    @Override
    public void publishYoJiSuccess(BaseBean baseBean) {
        Toast.makeText(this, baseBean.getMsg(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getRecommendTopicSuccess(List<HotTopicBean.DataBean.ListBean> list) {
        for (int i = 0; i < list.size(); i++) {
            addTextView(list.get(i).getTopic(), editFlowgroupviewId);
            type_list.add(list.get(i).getId());
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
