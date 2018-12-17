package com.iyoyogo.android.ui.home.yoji;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.bumptech.glide.Glide;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.PublishYoJiAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.ui.common.SearchActivity;
import com.iyoyogo.android.view.DrawableTextView;
import com.iyoyogo.android.widget.FlowGroupView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PublishYoJiActivity extends BaseActivity {


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
    @BindView(R.id.ll_channel)
    LinearLayout llChannel;
    @BindView(R.id.recycler_publish_yoji)
    RecyclerView recyclerPublishYoji;
    private ArrayList<String> path_list;
    private DrawableTextView location_tv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_publish_yo_ji;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initBeforeView() {
        super.initBeforeView();
        Intent intent = getIntent();
        path_list = intent.getStringArrayListExtra("path_list");
        String s = path_list.get(0);
        Glide.with(this).load(s).into(imgYoji);
    }

    @Override
    protected void initView() {
        super.initView();
        List<String> mList=new ArrayList<>();
        mList.add("");
        recyclerPublishYoji.setLayoutManager(new LinearLayoutManager(PublishYoJiActivity.this));
        PublishYoJiAdapter publishYoJiAdapter = new PublishYoJiAdapter(PublishYoJiActivity.this, mList,path_list);
        recyclerPublishYoji.setAdapter(publishYoJiAdapter);

        publishYoJiAdapter.setOnPlayClickListener(new PublishYoJiAdapter.OnLocationClickListener() {
            @Override
            public void onAddAddressClick(int position, PublishYoJiAdapter.ViewHolder holder) {
                Intent intent = new Intent(PublishYoJiActivity.this, SearchActivity.class);
                intent.putExtra("latitude", "0");
                intent.putExtra("longitude", "0");
                intent.putExtra("place", "添加位置");
                startActivityForResult(intent,1);
                location_tv = holder.itemView.findViewById(R.id.location_tv);
            }


            @Override
            public void onTagClick(int position) {
                Intent intent = new Intent(PublishYoJiActivity.this, ChooseSignActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
if (requestCode==1&&resultCode==3){
    String place = data.getStringExtra("place");
    Log.d("PublishYoJiActivity", place);
    String type = data.getStringExtra("type");
    double latitude = data.getDoubleExtra("latitude", 0.0);
    double longitude = data.getDoubleExtra("longitude", 0.0);
    LatLonPoint latLonPoint = new LatLonPoint(latitude, longitude);
    location_tv.setText(place);
}
    }

    @OnClick({R.id.back_img, R.id.tv_add_cover, R.id.more_topic, R.id.ll_bottom, R.id.ll_channel})
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

                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
