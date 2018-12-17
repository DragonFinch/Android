package com.iyoyogo.android.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.PoiSearchAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.bean.LocationBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {
    @BindView(R.id.locationET_id)
    EditText locationETId;
    @BindView(R.id.location_cancelTV_id)
    TextView locationCancelTVId;
    @BindView(R.id.location_rl)
    RelativeLayout locationRl;
    @BindView(R.id.location_photo_TV_id)
    TextView locationPhotoTVId;
    @BindView(R.id.location_place_TV_id)
    TextView locationPlaceTVId;
    @BindView(R.id.location_gpsplace_TV_id)
    TextView locationGpsplaceTVId;
    @BindView(R.id.location_ll)
    LinearLayout locationLl;
    @BindView(R.id.location_againGPS_TV_id)
    TextView locationAgainGPSTVId;
    @BindView(R.id.location_recommendTV_id)
    TextView locationRecommendTVId;
    @BindView(R.id.no_address)
    ImageView noAddress;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.address_info)
    TextView addressInfo;
    @BindView(R.id.go_create_point)
    RelativeLayout goCreatePoint;
    @BindView(R.id.location_RV_id)
    RecyclerView locationRVId;
    private ArrayList<String> list;

    private PoiSearch.Query query;
    private PoiSearch poiSearch;
    private ArrayList<LocationBean> datas;
    private PoiSearchAdapter adapter;
    private String place;
    private GeocodeSearch geocoderSearch;

    @Override
    protected void initView() {
        super.initView();
        list = new ArrayList<>();
        datas = new ArrayList<>();
        Intent intent = getIntent();
        place = intent.getStringExtra("place");
        String latitude = intent.getStringExtra("latitude");
        String longitude = intent.getStringExtra("longitude");
        locationAgainGPSTVId.setVisibility(View.GONE);
        goCreatePoint.setVisibility(View.GONE);

        if (latitude.equals("0") && longitude.equals("0")) {
            shortToast("经纬度为空");

            locationPlaceTVId.setVisibility(View.GONE);
            locationGpsplaceTVId.setVisibility(View.GONE);
            goCreatePoint.setVisibility(View.GONE);
            locationLl.setVisibility(View.GONE);
            locationAgainGPSTVId.setVisibility(View.GONE);
        } else {
            Log.d("PublishYoXiuActivity", latitude);
            Log.d("PublishYoXiuActivity", longitude);
            locationLl.setVisibility(View.VISIBLE);
            locationAgainGPSTVId.setVisibility(View.VISIBLE);
            locationPlaceTVId.setText(place);
            LatLonPoint latLonPoint = new LatLonPoint(Double.valueOf(latitude), Double.valueOf(longitude));
            setCurrentLocationDetails(latLonPoint);

        }

        locationETId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim() != null) {

                    searchLocationPoi(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                addressInfo.setText(s.toString().trim());
            }
        });
    }

    private void setCurrentLocationDetails(LatLonPoint latLonPoint) {
// 地址逆解析
        geocoderSearch = new GeocodeSearch(getApplicationContext());
        geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
                String address = result.getRegeocodeAddress().getFormatAddress();
                String country = result.getRegeocodeAddress().getCountry();
                String province = result.getRegeocodeAddress().getProvince();
                String neighborhood = result.getRegeocodeAddress().getNeighborhood();
                String district = result.getRegeocodeAddress().getDistrict();
                String towncode = result.getRegeocodeAddress().getTowncode();
                String city = result.getRegeocodeAddress().getCity();
                Log.e("formatAddress", "formatAddress:" + address);
                Log.e("formatAddress", "rCode:" + rCode);

                for (int i = 0; i < result.getRegeocodeAddress().getAois().size(); i++) {
                    String aoiName = result.getRegeocodeAddress().getAois().get(i).getAoiName();
                    Log.e("formatAddress", "aoiName:" + aoiName);
                }
                Log.e("formatAddress", "neighborhood:" + neighborhood);
                locationGpsplaceTVId.setText(province);
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });
        // 第一个参数表示一个Latlng(经纬度)，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 25, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);
    }

    private void searchLocationPoi(CharSequence s) {
        //关闭键盘
//        KeyBoardUtils.closeKeybord(poiSearchInMaps, BaseApplication.mContext);
        if (TextUtils.isEmpty(s.toString().trim())) {
            Toast.makeText(this, "内容为空!", Toast.LENGTH_SHORT).show();
            goCreatePoint.setVisibility(View.GONE);
//            mapAddAddress.setVisibility(View.VISIBLE);
//            recyclerAddAddress.setVisibility(View.GONE);
//            mapAddAddress.invalidate();
        } else {
//            mapAddAddress.setVisibility(View.GONE);
//            recyclerAddAddress.setVisibility(View.VISIBLE);
            // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
            query = new PoiSearch.Query(s.toString().trim(), "", "");
            query.setPageSize(20);// 设置每页最多返回多少条poiitem
            query.setPageNum(0);// 设置查第一页
            poiSearch = new PoiSearch(SearchActivity.this, query);
            poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
                @Override
                public void onPoiSearched(PoiResult poiResult, int errcode) {
                    if (errcode == 1000) {

                        ArrayList<PoiItem> pois = poiResult.getPois();
                        for (int i = 0; i < pois.size(); i++) {
                            LocationBean locationBean = new LocationBean();
                            String title = pois.get(i).getTitle();
                            String snippet = pois.get(i).getSnippet();
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
                        Log.d("SearchActivity", "datas.size():" + datas.size());

                        if (pois.size() == 0) {
                            goCreatePoint.setVisibility(View.VISIBLE);
                            locationRVId.setVisibility(View.GONE);

                        }
                        adapter = new PoiSearchAdapter(SearchActivity.this, datas);
                        /*if (datas!=null){
                            mapAddAddress.setVisibility(View.GONE);
                            recyclerAddAddress.setVisibility(View.VISIBLE);
                        }else {
                            mapAddAddress.setVisibility(View.VISIBLE);
                            recyclerAddAddress.setVisibility(View.GONE);
                        }*/
                        locationRVId.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                        locationRVId.setAdapter(adapter);
                        doChangeColor(s.toString().trim());
                        adapter.setOnItemClickListener(new PoiSearchAdapter.onItemClickListener() {
                            @Override
                            public void onClick(View view, int pos) {
                                double latitude = datas.get(pos).getLatitude();
                                double longitude = datas.get(pos).getLongitude();
                                String title = datas.get(pos).getTitle();
                                Intent intent = new Intent();
                                intent.putExtra("latitude", latitude);
                                intent.putExtra("longitude", longitude);
                                intent.putExtra("title", title);

                                setResult(3, intent);
                                finish();


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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_serach;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }


    @OnClick({R.id.location_cancelTV_id, R.id.location_rl, R.id.go_create_point})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.location_cancelTV_id:
                break;
            case R.id.location_rl:
                break;
            case R.id.go_create_point:
                if (locationETId.getText().length() != 0) {
                    Intent intent = new Intent(SearchActivity.this, CreatePointActivity.class);
                    intent.putExtra("address", addressInfo.getText().toString());
                    startActivityForResult(intent, 1);
                } else {
                    Toast.makeText(this, "输入框需要填写东西yo！", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            String type = data.getStringExtra("type");
            String place = data.getStringExtra("place");
            double latitude = data.getDoubleExtra("latitude", 0.0);
            double longitude = data.getDoubleExtra("longitude", 0.0);
            Intent intent = new Intent();
            intent.putExtra("type", type);
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
            intent.putExtra("place", place);
            setResult(2, intent);
            setResult(3, intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
