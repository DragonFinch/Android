package com.iyoyogo.android.ui.home.yoxiu;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.PoiSearchAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.bean.LocationBean;
import com.iyoyogo.android.utils.StatusBarUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 添加地址
 */
public class AddAddressActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.create_complete)
    TextView createComplete;
    @BindView(R.id.bar)
    RelativeLayout bar;
    @BindView(R.id.edit_add_address)
    EditText editAddAddress;
    @BindView(R.id.map_add_address)
    MapView mapAddAddress;
    @BindView(R.id.search_clear)
    ImageView searchClear;
    @BindView(R.id.img_location)
    ImageView imgLocation;
    @BindView(R.id.recycler_add_address)
    RecyclerView recyclerAddAddress;
    private AMap aMap;
    private double longitude;
    private double latitude;
    MyLocationStyle myLocationStyle;
    LocationSource.OnLocationChangedListener mListener;

    private PoiSearch.Query query;
    private PoiSearch poiSearch;
    private ArrayList<LocationBean> datas;
    private ArrayList<String> list;
    private PoiSearchAdapter adapter;
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
//可在其中解析amapLocation获取相应内容。
                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    amapLocation.getLatitude();//获取纬度
                    amapLocation.getLongitude();//获取经度
                    amapLocation.getAccuracy();//获取精度信息
                    String address = amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    String country = amapLocation.getCountry();//国家信息
                    String province = amapLocation.getProvince();//省信息
//城市信息
                    String city = amapLocation.getCity();


                    Log.d("LocationActivity", address);
                    String district = amapLocation.getDistrict();//城区信息
                    String street = amapLocation.getStreet();//街道信息
                    String streetNum = amapLocation.getStreetNum();//街道门牌号信息
                    Log.e("AddAddressActivity", province + city + district + street + streetNum);
                    amapLocation.getCityCode();//城市编码
                    amapLocation.getAdCode();//地区编码
                    amapLocation.getAoiName();//获取当前定位点的AOI信息
                    amapLocation.getBuildingId();//获取当前室内定位的建筑物Id
                    amapLocation.getFloor();//获取当前室内定位的楼层
                    amapLocation.getGpsAccuracyStatus();//获取GPS的当前状态

//获取定位时间
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(amapLocation.getTime());
                    df.format(date);
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };



    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_address;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Intent intent = getIntent();
        datas = new ArrayList<>();
        list = new ArrayList<>();
        createComplete.setText("确认");
        tvTitle.setText("添加地址");
        mapAddAddress.onCreate(savedInstanceState);
        aMap = mapAddAddress.getMap();
        aMap.getUiSettings().setZoomControlsEnabled(false);

        mLocationClient = new AMapLocationClient(getApplicationContext());
//设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setNeedAddress(true);
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        if (null != mLocationClient) {
            mLocationClient.setLocationOption(mLocationOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);

//获取最近3s内精度最高的一次定位结果：
//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
//启动定位
        mLocationClient.startLocation();


        mapAddAddress.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewGroup childAt = (ViewGroup) mapAddAddress.getChildAt(0);
                childAt.getChildAt(2).setVisibility(View.GONE);
            }
        });

        editAddAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editAddAddress.getText().toString().length() != 0) {
                    searchClear.setVisibility(View.VISIBLE);
                }
                searchLocationPoi(s);

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
        aMap.setOnMapLongClickListener(new AMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Toast.makeText(AddAddressActivity.this, "latLng:" + latLng, Toast.LENGTH_SHORT).show();

                aMap.clear();

                MarkerOptions markerOption = new MarkerOptions();
                markerOption.position(latLng);
                latitude = latLng.latitude;
                longitude = latLng.longitude;


                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(), R.mipmap.location)));
                // 将Marker设置为贴地显示，可以双指下拉地图查看效果
                Marker marker = aMap.addMarker(markerOption.draggable(true));
                marker.setDraggable(true);
                aMap.invalidate();
            }
        });
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    private void textChangeSearch(CharSequence charSequence) {
        String content = charSequence.toString().trim();//获取自动提示输入框的内容
        Log.d("AddAddressActivity", "charSequence:" + charSequence);
        InputtipsQuery inputtipsQuery = new InputtipsQuery(content, "");//初始化一个输入提示搜索对象，并传入参数
        Inputtips inputtips = new Inputtips(AddAddressActivity.this, inputtipsQuery);//定义一个输入提示对象，传入当前上下文和搜索对象
        inputtips.setInputtipsListener(new Inputtips.InputtipsListener() {
            @Override
            public void onGetInputtips(List<Tip> list, int errcode) {
                Log.e("TAG", list.toString() + errcode);
                if (errcode == 1000 && list != null) {
                    datas = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        LocationBean locationBean = new LocationBean();
                        Tip tip = list.get(i);
                        locationBean.latitude = tip.getPoint().getLatitude();
                        locationBean.longitude = tip.getPoint().getLongitude();
                        locationBean.snippet = tip.getName();
                        locationBean.title = tip.getDistrict();
                        datas.add(locationBean);
                    }
//                    searchCarAdapter.setNewData(datas);
                }
            }
        });//设置输入提示查询的监听，实现输入提示的监听方法onGetInputtips()
        inputtips.requestInputtipsAsyn();//输入查询提示的异步接口实现
    }

    private void doChangeColor(String text) {
        //clear是必须的 不然只要改变edittext数据，list会一直add数据进来
        list.clear();
        //不需要匹配 把所有数据都传进来 不需要变色
        if (text.equals("")) {
            for (int i = 0; i < datas.size(); i++) {
                list.add(datas.get(i).getTitle());
            }

            //防止匹配过文字之后点击删除按钮 字体仍然变色的问题
            adapter.setText(null);
            refreshUI();
        } else {
            //如果edittext里面有数据 则根据edittext里面的数据进行匹配 用contains判断是否包含该条数据 包含的话则加入到list中
            for (int i = 0; i < datas.size(); i++) {
                list.add(datas.get(i).getTitle());
            }

            //设置要变色的关键字
            adapter.setText(text);
            refreshUI();
        }
    }

    private void refreshUI() {
        if (adapter == null) {

        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void searchLocationPoi(CharSequence s) {
        //关闭键盘
//        KeyBoardUtils.closeKeybord(poiSearchInMaps, BaseApplication.mContext);
        if (TextUtils.isEmpty(s.toString().trim())) {
            Toast.makeText(this, "内容为空!", Toast.LENGTH_SHORT).show();
            mapAddAddress.setVisibility(View.VISIBLE);
            recyclerAddAddress.setVisibility(View.GONE);
            mapAddAddress.invalidate();
        } else {
            mapAddAddress.setVisibility(View.GONE);
            recyclerAddAddress.setVisibility(View.VISIBLE);
            // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
            query = new PoiSearch.Query(s.toString().trim(), "", "");
            query.setPageSize(20);// 设置每页最多返回多少条poiitem
            query.setPageNum(0);// 设置查第一页
            poiSearch = new PoiSearch(AddAddressActivity.this, query);
            poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
                @Override
                public void onPoiSearched(PoiResult poiResult, int errcode) {
                    if (errcode == 1000) {

                        ArrayList<PoiItem> pois = poiResult.getPois();
                        for (int i = 0; i < pois.size(); i++) {
                            LocationBean locationBean = new LocationBean();
                            String title = pois.get(i).getTitle();
                            String snippet = pois.get(i).getSnippet();
                            String adName = pois.get(i).getAdName();
                            Log.d("AddAddressActivity", adName);
                            String provinceName = pois.get(i).getProvinceName();
                            locationBean.setProvinceName(provinceName);
                            LatLonPoint latLonPoint = pois.get(i).getLatLonPoint();
                            double latitude = latLonPoint.getLatitude();
                            double longitude = latLonPoint.getLongitude();
                            locationBean.setLatitude(latitude);
                            locationBean.setLongitude(longitude);
                            Log.d("Test", title);
                            locationBean.setSnippet(snippet);
                            locationBean.setTitle(title);
                            datas.add(locationBean);
                        }
                        Log.d("AddAddressActivity", "datas.size():" + datas.size());
                        adapter = new PoiSearchAdapter(AddAddressActivity.this, datas);
                        /*if (datas!=null){
                            mapAddAddress.setVisibility(View.GONE);
                            recyclerAddAddress.setVisibility(View.VISIBLE);
                        }else {
                            mapAddAddress.setVisibility(View.VISIBLE);
                            recyclerAddAddress.setVisibility(View.GONE);
                        }*/
                        recyclerAddAddress.setLayoutManager(new LinearLayoutManager(AddAddressActivity.this));
                        recyclerAddAddress.setAdapter(adapter);
                        doChangeColor(s.toString().trim());
                        adapter.setOnItemClickListener(new PoiSearchAdapter.onItemClickListener() {
                            @Override
                            public void onClick(View view, int pos) {

                                latitude = datas.get(pos).getLatitude();
                                longitude = datas.get(pos).getLongitude();
                                editAddAddress.setText("");
                              /*  aMap.clear();

                                MarkerOptions markerOption = new MarkerOptions();

                                markerOption.position(new LatLng(latitude,longitude));



                                markerOption.draggable(true);//设置Marker可拖动
                                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                                        .decodeResource(getResources(), R.mipmap.location)));
                                // 将Marker设置为贴地显示，可以双指下拉地图查看效果
                                Marker marker = aMap.addMarker(markerOption);*/
                                CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(latitude, longitude), 10, 30, 0));
                                aMap.moveCamera(mCameraUpdate);
                                recyclerAddAddress.setVisibility(View.GONE);
                                mapAddAddress.setVisibility(View.VISIBLE);
                                mapAddAddress.invalidate();
                                datas.clear();
                            }
                        });

//                        searchCarAdapter.setNewData(datas);
                    } else {
                        Log.d("error", "errcode:" + errcode);
                    }
                }

                @Override
                public void onPoiItemSearched(PoiItem poiItem, int i) {

                }
            });
            poiSearch.searchPOIAsyn();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mapview.onDestroy()，销毁地图
//        mapAddAddress.onDestroy();
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mapview.onResume ()，重新绘制加载地图
        mapAddAddress.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mapview.onPause ()，暂停地图的绘制
        mapAddAddress.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mapview.onSaveInstanceState (outState)，保存地图当前的状态
        mapAddAddress.onSaveInstanceState(outState);
    }

    @OnClick({R.id.back, R.id.create_complete, R.id.search_clear, R.id.img_location})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.create_complete:
                if (latitude != 0.0 && longitude != 0.0) {
                    Intent intent = new Intent();
                    intent.putExtra("latitude", latitude);
                    intent.putExtra("longitude", longitude);
                    Log.d("AddAddressActivity", "latitude:" + latitude);
                    Log.d("AddAddressActivity", "longitude:" + longitude);
                    setResult(2, intent);
                    finish();
                } else {
                    Toast.makeText(this, "您还没有画标记呢", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.search_clear:
                editAddAddress.setText("");
                searchClear.setVisibility(View.INVISIBLE);
                break;
            case R.id.img_location:
                mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                mLocationOption.setOnceLocation(true);

//获取最近3s内精度最高的一次定位结果：
//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
                mLocationOption.setOnceLocationLatest(true);

                //给定位客户端对象设置定位参数
                mLocationClient.setLocationOption(mLocationOption);
//启动定位
                mLocationClient.startLocation();


                break;
        }
    }


    @OnClick(R.id.img_location)
    public void onViewClicked() {

        myLocationStyle = new MyLocationStyle();
        //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        //连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));

        Toast.makeText(this, "已定位当前位置", Toast.LENGTH_SHORT).show();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        //定位一次，且将视角移动到地图中心点。
        myLocationStyle.interval(2000);
        //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));

        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.icon_oval_select));// 设置小蓝点的图标
        aMap.setMyLocationStyle(myLocationStyle);
//        aMap.setMyLocationStyle(myLocationStyle);
        //设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);
        // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

    }
}