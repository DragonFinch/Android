package com.iyoyogo.android.ui.home.yoxiu;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.bumptech.glide.Glide;
import com.iyoyogo.android.R;
import com.iyoyogo.android.app.Constant;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.ui.common.SearchActivity;
import com.iyoyogo.android.utils.ImagePickAction;
import com.iyoyogo.android.widget.FlowGroupView;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JzvdStd;

public class PublishYoXiuActivity extends BaseActivity  {
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
    EditText editEdittextId;
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
    LinearLayout editLlchoiceId;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.edit_penyouquan_id)
    ImageView editPenyouquanId;
    @BindView(R.id.edit_weibo_id)
    ImageView editWeiboId;
    @BindView(R.id.edit_weixin_id)
    ImageView editWeixinId;
    @BindView(R.id.edit_qq_id)
    ImageView editQqId;
    @BindView(R.id.edit_button_id)
    Button editButtonId;
    @BindView(R.id.edit_rlayout_id)
    RelativeLayout editRlayoutId;
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

    private String path;
    private String latitude;
    private String longitude;
    private LatLonPoint latLonPoint;
    private String place;
    private GeocodeSearch geocoderSearch;
    private void setCurrentLocationDetails(){
// 地址逆解析
        geocoderSearch = new GeocodeSearch(getApplicationContext());
        geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
                place = result.getRegeocodeAddress().getFormatAddress();
                String country = result.getRegeocodeAddress().getCountry();
                String province = result.getRegeocodeAddress().getProvince();
                Log.e("formatAddress", "formatAddress:"+ place);
                Log.e("formatAddress", "rCode:"+rCode);
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });
        // 第一个参数表示一个Latlng(经纬度)，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 25, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);
    }

    /**
      *  获取回调的逆地址内容
     */





    @Override
    protected void initView() {
        super.initView();
        setData();
        Intent intent = getIntent();
        latitude = intent.getStringExtra("latitude");
        longitude = intent.getStringExtra("longitude");
        latLonPoint=new LatLonPoint(Double.valueOf(latitude),Double.valueOf(longitude));
      setCurrentLocationDetails();
        Log.d("PublishYoXiuActivity", latitude);
        Log.d("PublishYoXiuActivity", longitude);
        if (latitude.equals("0")&&longitude.equals("0")){
        shortToast("经纬度为空");
        }
        path = intent.getStringExtra("path");

        Glide.with(this).load(path).into(editVideoId);
        LocalMedia mMedia = new LocalMedia();
        mMedia.setPath(path);
        String fileExtension = MimeTypeMap.getFileExtensionFromUrl(path);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
        if (mimeType != null && mimeType.contains("video")) {

            mMedia.setPictureType(Constant.PARAM_VIDEO_MP4);
            Glide.with(this).load(ImagePickAction.getVideoThumb(mMedia.getPath(), 1)).into(editVideoId);

        }else {
                editStartId.setVisibility(View.GONE);
            Glide.with(this).load(path).into(editVideoId);
        }
//        view = (FlowGroupView) findViewById(R.id.edit_flowgroupview_id);
        for (int i = 0; i < names.size(); i++) {
            addTextView(names.get(i));
        }


        Button button = (Button) findViewById(R.id.edit_button_id);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                showPopupWindow(view);
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_publish_yo_xiu;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
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
                Toast.makeText(PublishYoXiuActivity.this, tv.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setData() {
        names.add("降龙十八掌");
        names.add("黯然销魂掌");
        names.add("小无相功");
        names.add("打狗棍法");
        names.add("蛤蟆功");
        names.add("九阴白骨爪");
        names.add("醉拳");
        names.add("龙蛇虎豹");
        names.add("葵花宝典");
        names.add("吸星大法");
    }


    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.item_popupwindow, null);
        // 设置按钮的点击事件
        Button button = (Button) contentView.findViewById(R.id.popup_but_id);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(PublishYoXiuActivity.this, "我就加了一个!~~",
                        Toast.LENGTH_SHORT).show();
            }
        });

        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.fillet_style));

        // 设置显示位置
        popupWindow.showAtLocation(findViewById(R.id.edit_rlayout_id), Gravity.CENTER, 300, 300);

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);
    }


    @OnClick({R.id.edit_back_id, R.id.edit_publish_id, R.id.edit_start_id, R.id.publish_place, R.id.edit_replace_id, R.id.more_topic, R.id.edit_penyouquan_id, R.id.edit_weibo_id, R.id.edit_weixin_id, R.id.edit_qq_id, R.id.edit_button_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edit_back_id:
                finish();
                break;
            case R.id.edit_publish_id:

                break;
            case R.id.edit_start_id:
                JzvdStd.startFullscreen(PublishYoXiuActivity.this, JzvdStd.class, path, "");
                break;
            case R.id.publish_place:
                Intent intent = new Intent(PublishYoXiuActivity.this, SearchActivity.class);
                intent.putExtra("latitude",latitude);
                intent.putExtra("longitude",longitude);
                intent.putExtra("place",place);

                startActivity(intent);
                break;
            case R.id.edit_replace_id:

                break;
            case R.id.more_topic:

                break;
            case R.id.edit_penyouquan_id:

                break;
            case R.id.edit_weibo_id:

                break;
            case R.id.edit_weixin_id:

                break;
            case R.id.edit_qq_id:

                break;
            case R.id.edit_button_id:

                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mLocationClient!=null) {
            mLocationClient.onDestroy();//销毁定位客户端。
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mLocationClient!=null) {
            mLocationClient.startLocation(); // 启动定位
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(mLocationClient!=null) {
            mLocationClient.stopLocation();//停止定位
        }
    }

}
