package com.iyoyogo.android.ui.home.yoji;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.amap.api.services.core.LatLonPoint;
import com.bumptech.glide.Glide;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.Bean;
import com.iyoyogo.android.adapter.PublishYoJiAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.yoji.publish.MessageBean;
import com.iyoyogo.android.bean.yoxiu.topic.HotTopicBean;
import com.iyoyogo.android.contract.PublishYoJiContract;
import com.iyoyogo.android.presenter.PublishYoJiPresenter;
import com.iyoyogo.android.ui.common.SearchActivity;
import com.iyoyogo.android.ui.home.yoxiu.ChannelActivity;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.view.DrawableTextView;
import com.iyoyogo.android.widget.FlowGroupView;
import com.iyoyogo.android.widget.flow.FlowLayout;
import com.iyoyogo.android.widget.flow.TagAdapter;
import com.iyoyogo.android.widget.flow.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PublishYoJiActivity extends BaseActivity<PublishYoJiContract.Presenter> implements PublishYoJiContract.View {


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
    EditText etTitle;
    @BindView(R.id.text_title_length)
    TextView textTitleLength;
    @BindView(R.id.title_layout)
    LinearLayout titleLayout;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.text_content_length)
    TextView textContentLength;
    @BindView(R.id.layout_describe)
    LinearLayout layoutDescribe;
    @BindView(R.id.more_topic)
    TextView moreTopic;
    @BindView(R.id.edit_middleToobar_id)
    LinearLayout editMiddleToobarId;
    @BindView(R.id.edit_flowgroupview_id)
    FlowGroupView editFlowgroupviewId;
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.et_cost)
    EditText etCost;
    @BindView(R.id.layout_pay)
    LinearLayout layoutPay;
    @BindView(R.id.rb_moment)
    AppCompatCheckBox rbMoment;
    @BindView(R.id.rb_wechat)
    AppCompatCheckBox rbWechat;
    @BindView(R.id.rb_sina)
    AppCompatCheckBox rbSina;
    @BindView(R.id.rb_qq)
    AppCompatCheckBox rbQq;
    @BindView(R.id.radioGroup_share)
    LinearLayout radioGroupShare;
    @BindView(R.id.dt_privite)
    DrawableTextView dtPrivite;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.dt_gochennel)
    DrawableTextView dtGochennel;
    @BindView(R.id.tag_flowLayout)
    FlowGroupView tagFlowLayout;
    @BindView(R.id.next)
    ImageView next;
    private List<Integer> type_list = new ArrayList<>();
    @BindView(R.id.ll_channel)
    LinearLayout llChannel;
    @BindView(R.id.recycler_publish_yoji)
    RecyclerView recyclerPublishYoji;
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_publish_yo_ji;
    }

    @Override
    protected PublishYoJiContract.Presenter createPresenter() {
        return new PublishYoJiPresenter(this);
    }

    @Override
    protected void initBeforeView() {
        super.initBeforeView();
        Intent intent = getIntent();
        path_list = intent.getStringArrayListExtra("path_list");
        path = path_list.get(0);
        Glide.with(this).load(path).into(imgYoji);
    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        user_id = SpUtils.getString(PublishYoJiActivity.this, "user_id", null);
        user_token = SpUtils.getString(PublishYoJiActivity.this, "user_token", null);
        mPresenter.getRecommendTopic(user_id, user_token);
        MessageBean messageBean = new MessageBean();
        messageBean.setStart_time("");
        messageBean.setEnd_time("");
        messageBean.setPosition_name("");
        messageBean.setImage_list(path_list);
        List<MessageBean> mList = new ArrayList<>();
        mList.add(messageBean);
        recyclerPublishYoji.setLayoutManager(new LinearLayoutManager(PublishYoJiActivity.this));
        PublishYoJiAdapter publishYoJiAdapter = new PublishYoJiAdapter(PublishYoJiActivity.this, mList, path_list);
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
            public void onTagClick(int position, PublishYoJiAdapter.ViewHolder holder) {
                Intent intent = new Intent(PublishYoJiActivity.this, ChooseSignActivity.class);
                startActivityForResult(intent, 1);
                flow_group = holder.itemView.findViewById(R.id.flow_group);
                label_tv = holder.itemView.findViewById(R.id.label_tv);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 3) {
            String place = data.getStringExtra("place");
            Log.d("PublishYoJiActivity", place);
            String type = data.getStringExtra("type");
            double latitude = data.getDoubleExtra("latitude", 0.0);
            double longitude = data.getDoubleExtra("longitude", 0.0);
            LatLonPoint latLonPoint = new LatLonPoint(latitude, longitude);
            location_tv.setText(place);
        }
        if (requestCode == 1 && resultCode == 55) {
            label_tv.setVisibility(View.GONE);
            ArrayList<Bean> sign_list = (ArrayList<Bean>) data.getSerializableExtra("sign_list");
            for (int i = 0; i < sign_list.size(); i++) {
                String label = sign_list.get(i).getLabel();
                Log.d("PublishYoJiActivity", label);
            }
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

    private List<String> uploadAllYoJiImage(List<String> list) {

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
        List<String> urls = new ArrayList<>();
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

    @OnClick({R.id.back_img, R.id.tv_add_cover, R.id.more_topic, R.id.ll_bottom, R.id.ll_channel, R.id.tv_publish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_img:

                break;
            case R.id.tv_add_cover:

                break;
            case R.id.more_topic:

                break;
            case R.id.ll_bottom:

                break;
            case R.id.ll_channel:
                Intent intent = new Intent(PublishYoJiActivity.this, ChannelActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.tv_publish:
                String url_cover = uploadYoJiImage();
                List<String> urls = uploadAllYoJiImage(path_list);
                MessageBean messageBean = new MessageBean();
                messageBean.setImage_list(urls);
                List<MessageBean> mList=new ArrayList<>();
                mList.add(messageBean);
                mPresenter.publishYoJi(user_id, user_token, 0, url_cover,etTitle.getText().toString().trim(),etContent.getText().toString().trim(),Integer.parseInt(etCost.getText().toString().trim()),1,1,type_list,channel_ids,mList);
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
}
