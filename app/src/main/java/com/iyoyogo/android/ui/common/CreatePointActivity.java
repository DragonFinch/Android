package com.iyoyogo.android.ui.common;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.TypeAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.yoxiu.TypeBean;
import com.iyoyogo.android.contract.CreatePointContract;
import com.iyoyogo.android.presenter.CreatePointPresenter;
import com.iyoyogo.android.ui.home.yoxiu.AddAddressActivity;
import com.iyoyogo.android.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CreatePointActivity extends BaseActivity<CreatePointContract.Presenter> implements CreatePointContract.View {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.create_complete)
    TextView createComplete;
    @BindView(R.id.bar)
    RelativeLayout bar;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.edit_create_point)
    TextView editCreatePoint;
    @BindView(R.id.line_location)
    View lineLocation;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.line_create_point)
    View lineCreatePoint;
    @BindView(R.id.map)
    MapView map;
    @BindView(R.id.map_line)
    View mapLine;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_choose_type)
    TextView tvChooseType;
    @BindView(R.id.relative_map)
    RelativeLayout relativeMap;
    @BindView(R.id.relative_line)
    View relativeLine;
    private List<Marker> markerList;
    private double longitude;
    private double latitude;
    private String place;
    private String country;
    private String province;
    private PopupWindow popup;
    private RecyclerView recycler_type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_point;
    }


    public static final String KEY = "06f98328627f4f3559c8e8352062fee4";

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        map.onCreate(savedInstanceState);
        createComplete.setClickable(false);
        createComplete.setTextColor(Color.parseColor("#888888"));
        AMap aMap = map.getMap();
        map.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewGroup childAt = (ViewGroup) map.getChildAt(0);
                childAt.getChildAt(2).setVisibility(View.GONE);
            }
        });

        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Intent intent = new Intent(CreatePointActivity.this, AddAddressActivity.class);
                startActivityForResult(intent, 1);
            }
        });


        UiSettings uiSettings = aMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);

    }

    //la    39.7634 long  116.326
    @Override
    protected CreatePointContract.Presenter createPresenter() {
        return new CreatePointPresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mapview.onDestroy()，销毁地图
        map.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mapview.onResume ()，重新绘制加载地图
        map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mapview.onPause ()，暂停地图的绘制
        map.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mapview.onSaveInstanceState (outState)，保存地图当前的状态
        map.onSaveInstanceState(outState);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {

            double latitude = data.getDoubleExtra("latitude",0.0);
            double longitude = data.getDoubleExtra("longitude",0.0);
            Log.d("CreatePointActivity", "latitude:" + latitude );
            Log.d("CreatePointActivity", "longitude:" + longitude);
            LatLonPoint latLonPoint = new LatLonPoint(latitude,longitude);
            setCurrentLocationDetails(latLonPoint);

            lineCreatePoint.setVisibility(View.VISIBLE);
            mapLine.setVisibility(View.VISIBLE);
            relativeLine.setVisibility(View.VISIBLE);
            tvType.setVisibility(View.VISIBLE);
            tvChooseType.setVisibility(View.VISIBLE);


        }
    }

    private GeocodeSearch geocoderSearch;

    private void setCurrentLocationDetails(LatLonPoint latLonPoint) {
// 地址逆解析
        geocoderSearch = new GeocodeSearch(getApplicationContext());
        geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
                place = result.getRegeocodeAddress().getFormatAddress();
                country = result.getRegeocodeAddress().getCountry();
                province = result.getRegeocodeAddress().getProvince();
                Log.e("formatAddress", "formatAddress:" + place);
                Log.e("formatAddress", "country:" + country);
                Log.e("formatAddress", "province:" + province);
                Log.e("formatAddress", "rCode:" + rCode);
                tvCity.setVisibility(View.VISIBLE);
                tvCity.setText(province + "," + country);
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });
        // 第一个参数表示一个Latlng(经纬度)，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 25, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);
    }

    @OnClick({R.id.back, R.id.create_complete, R.id.tv_choose_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.create_complete:

                break;
            case R.id.tv_choose_type:
                mPresenter.setType(SpUtils.getString(CreatePointActivity.this, "user_id", null), SpUtils.getString(CreatePointActivity.this, "user_token", null));
                createComplete.setClickable(true);
                createComplete.setTextColor(Color.parseColor("#FA800A"));
                break;
        }
    }


    @Override
    public void setTypeSuccess(TypeBean.DataBean data) {
        initPopup();
        List<TypeBean.DataBean> mList = new ArrayList<>();
        mList.add(data);

        recycler_type.setLayoutManager(new GridLayoutManager(CreatePointActivity.this, 2));
        TypeAdapter adapter = new TypeAdapter(CreatePointActivity.this, mList);
        recycler_type.setAdapter(adapter);
        adapter.setOnItemClickListener(new TypeAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(View v, int position) {
                String name = mList.get(position).getList().get(position).getName();
                tvChooseType.setText(name);
                popup.dismiss();
            }
        });
    }
//背景透明
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        getWindow().setAttributes(lp); //act 是上下文context

    }
//隐藏事件PopupWindow
    private class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }
//初始化PopupWindow
    private void initPopup() {
        View view = getLayoutInflater().inflate(R.layout.popup_type, null);
        popup = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popup.setOutsideTouchable(true);
        popup.setBackgroundDrawable(new ColorDrawable());
        recycler_type = view.findViewById(R.id.recycler_choose_type);
        popup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //点击空白处时，隐藏掉pop窗口
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(1f);

        //添加pop窗口关闭事件
        popup.setOnDismissListener(new poponDismissListener());
        popup.showAtLocation(findViewById(R.id.activity_create_point),Gravity.CENTER,0,0);
    }
}
