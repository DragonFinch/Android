package com.iyoyogo.android.ui.common;

import android.content.Intent;
import android.graphics.Color;
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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.PoiSearchAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.HisPositionBean;
import com.iyoyogo.android.bean.LocationBean;
import com.iyoyogo.android.contract.HisPositionContract;
import com.iyoyogo.android.presenter.HisPositionPresenter;
import com.iyoyogo.android.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 位置搜索
 */
public class SearchActivity extends BaseActivity<HisPositionContract.Presenter> implements HisPositionContract.View {
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
    @BindView(R.id.history_close)
    TextView historyClose;
    @BindView(R.id.history)
    RelativeLayout history;
    @BindView(R.id.set_history)
    LinearLayout set_history;
    private ArrayList<String> list;

    private PoiSearch.Query query;
    private PoiSearch poiSearch;
    private ArrayList<LocationBean> datas;
    private PoiSearchAdapter adapter;
    private String place;
    private GeocodeSearch geocoderSearch;
    private String latitude;
    private String longitude;
    private String country;
    private int yo_type;
    private String address1;
    private String city;
    private String province;
    private String aoiName;
    private String district;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Intent intent = getIntent();
        yo_type = intent.getIntExtra("yo_type", 0);
    }

    private String user_token;
    private String user_id;
    private List<HisPositionBean.DataBean.ListBean> list1;
    private HistoryPoiSearchAdapter adapter1;

    @Override
    protected void initView() {
        super.initView();
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);

        list = new ArrayList<>();
        datas = new ArrayList<>();
        Intent intent = getIntent();
        place = intent.getStringExtra("place");
        latitude = intent.getStringExtra("latitude");
        longitude = intent.getStringExtra("longitude");
        history.setVisibility(View.GONE);
        locationAgainGPSTVId.setVisibility(View.VISIBLE);
        goCreatePoint.setVisibility(View.GONE);
        locationGpsplaceTVId.setVisibility(View.GONE);
        if (!latitude.equals("0")) {
            LatLonPoint latLonPoint = new LatLonPoint(Double.valueOf(latitude), Double.valueOf(longitude));
            setCurrentLocationDetails(latLonPoint);
            locationGpsplaceTVId.setVisibility(View.VISIBLE);
            locationAgainGPSTVId.setVisibility(View.VISIBLE);
        }
        Log.d("PublishYoXiuActivity", latitude);
        Log.d("PublishYoXiuActivity", longitude);
        locationLl.setVisibility(View.VISIBLE);
        locationAgainGPSTVId.setVisibility(View.GONE);
        locationPlaceTVId.setText(place);
        Log.d("SearchActivity", place);

        if (place.equals("添加位置")) {
            locationPlaceTVId.setText("未获取到位置信息");
            locationAgainGPSTVId.setVisibility(View.GONE);
        } else {
            query = new PoiSearch.Query(place, "", "");
            query.setPageSize(20);// 设置每页最多返回多少条poiitem
            query.setPageNum(0);// 设置查第一页
            poiSearch = new PoiSearch(SearchActivity.this, query);
            poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
                @Override
                public void onPoiSearched(PoiResult poiResult, int errcode) {
                    if (errcode == 1000) {
//TODO
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
//                            goCreatePoint.setVisibility(View.VISIBLE);
                            locationRVId.setVisibility(View.VISIBLE);
                            history.setVisibility(View.VISIBLE);
                            mPresenter.getHisPosition(SearchActivity.this,user_id, user_token, 1, 20);
                        } else {
                            history.setVisibility(View.GONE);
//                            goCreatePoint.setVisibility(View.GONE);
                            adapter = new PoiSearchAdapter(SearchActivity.this, datas);
                            locationRVId.setVisibility(View.VISIBLE);
                            locationRVId.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                            locationRVId.setAdapter(adapter);
//                        doChangeColor(s.toString().trim());
                            adapter.setOnItemClickListener(new PoiSearchAdapter.onItemClickListener() {
                                @Override
                                public void onClick(View view, int pos) {
                                    double latitude = datas.get(pos).getLatitude();
                                    double longitude = datas.get(pos).getLongitude();
                                    String title = datas.get(pos).getTitle();
                                    String provinceName = datas.get(pos).getProvinceName();
                                    String snippet = datas.get(pos).getSnippet();
                                    String areas = datas.get(pos).getAreas();
                                    SpUtils.putString(SearchActivity.this, "title", title);
                                    SpUtils.putString(SearchActivity.this, "provinceName", provinceName);
                                    LatLonPoint latLonPoint = new LatLonPoint(latitude, longitude);
                                    setCurrentLocationDetails(latLonPoint);
                                    intent.putExtra("latitude", latitude);
                                    intent.putExtra("longitude", longitude);
                                    intent.putExtra("title", title);
                                    intent.putExtra("area", areas);
                                    intent.putExtra("address", snippet);
                                    intent.putExtra("city", city);
                                    if (yo_type == 2) {
                                        setResult(45, intent);
                                        finish();
                                    } else {
                                        setResult(3, intent);
                                        finish();
                                    }

                                }
                            });
                        }


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
        locationETId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addressInfo.setText(s.toString().trim());
                if (s.toString().trim().length() > 0) {
                    searchLocationPoi(s);
                    locationETId.setCompoundDrawablesWithIntrinsicBounds(null,null, null, null);
//                    locationETId.setComP
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

      /*  if (place.equals(country)) {
            locationRecommendTVId.setVisibility(View.VISIBLE);
            locationAgainGPSTVId.setVisibility(View.GONE);
        } else {
            locationRecommendTVId.setVisibility(View.GONE);
        }*/
     /*   if (place.equals("选择地址")) {
            locationPlaceTVId.setText("未获取到位置信息");
            locationAgainGPSTVId.setVisibility(View.GONE);
        }else if (place.equals("添加位置")){
            locationPlaceTVId.setText("未获取到位置信息");
            locationAgainGPSTVId.setVisibility(View.GONE);
        }*/
        user_token = SpUtils.getString(SearchActivity.this, "user_token", null);
        user_id = SpUtils.getString(SearchActivity.this, "user_id", null);
    }

    @Override
    protected HisPositionContract.Presenter createPresenter() {
        return new HisPositionPresenter(SearchActivity.this,this);
    }

    private void setCurrentLocationDetails(LatLonPoint latLonPoint) {
// 地址逆解析
        geocoderSearch = new GeocodeSearch(getApplicationContext());
        geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
                address1 = result.getRegeocodeAddress().getFormatAddress();
                String country = result.getRegeocodeAddress().getCountry();
                province = result.getRegeocodeAddress().getProvince();
                String neighborhood = result.getRegeocodeAddress().getNeighborhood();
                district = result.getRegeocodeAddress().getDistrict();
                String towncode = result.getRegeocodeAddress().getTowncode();
                city = result.getRegeocodeAddress().getCity();
                Log.e("formatAddress", "formatAddress:" + address1);
                Log.e("formatAddress", "rCode:" + rCode);

                for (int i = 0; i < result.getRegeocodeAddress().getAois().size(); i++) {
                    aoiName = result.getRegeocodeAddress().getAois().get(i).getAoiName();
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
            locationAgainGPSTVId.setVisibility(View.GONE);
            locationRVId.setVisibility(View.VISIBLE);
            history.setVisibility(View.GONE);
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
                            locationBean.setCity(pois.get(i).getCityName());
                            locationBean.setAreas(pois.get(i).getProvinceName() + "," + pois.get(i).getCityName() + "," + pois.get(i).getAdName());


                            datas.add(locationBean);
                        }
                        Log.d("SearchActivity", "datas.size():" + datas.size());

                        if (pois.size() == 0) {
                            history.setVisibility(View.VISIBLE);
                            goCreatePoint.setVisibility(View.VISIBLE);
                            locationRVId.setVisibility(View.VISIBLE);
                            mPresenter.getHisPosition(SearchActivity.this,user_id, user_token, 1, 20);
                            historyClose.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mPresenter.DelPosition(SearchActivity.this,user_id, user_token);
                                }
                            });

                        } else {
                            //TODO  回传
                            history.setVisibility(View.GONE);
                            goCreatePoint.setVisibility(View.GONE);
                            adapter = new PoiSearchAdapter(SearchActivity.this, datas);
                            locationRVId.setVisibility(View.VISIBLE);
                            locationRVId.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                            locationRVId.setAdapter(adapter);
                            doChangeColor(s.toString().trim());
                            adapter.setOnItemClickListener(new PoiSearchAdapter.onItemClickListener() {
                                @Override
                                public void onClick(View view, int pos) {
                                    double latitude = datas.get(pos).getLatitude();
                                    double longitude = datas.get(pos).getLongitude();
                                    String title = datas.get(pos).getTitle();
                                    String provinceName = datas.get(pos).getProvinceName();
                                    String snippet = datas.get(pos).getSnippet();
                                    SpUtils.putString(SearchActivity.this, "title", title);
                                    SpUtils.putString(SearchActivity.this, "provinceName", provinceName);
                                    Log.d("SearchActivity", datas.get(pos).getAreas());
                                    Log.d("SearchActivity", datas.get(pos).getSnippet());
                                    Intent intent = new Intent();
                                    intent.putExtra("latitude", latitude);
                                    intent.putExtra("longitude", longitude);
                                    intent.putExtra("title", title);
                                    intent.putExtra("area", datas.get(pos).getAreas());
                                    intent.putExtra("address", datas.get(pos).getSnippet());
                                    intent.putExtra("city", datas.get(pos).getCity());
                                    setResult(3, intent);
                                    finish();
                                }
                            });
                        }

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


    @OnClick({R.id.location_cancelTV_id, R.id.location_rl, R.id.go_create_point})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.location_cancelTV_id:
                finish();
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

            /*
            *  Intent intent = new Intent();
                                    intent.putExtra("latitude", latitude);
                                    intent.putExtra("longitude", longitude);
                                    intent.putExtra("title", title);
                                    intent.putExtra("area", datas.get(pos).getAreas());
                                    intent.putExtra("address", datas.get(pos).getSnippet());
                                    intent.putExtra("city", datas.get(pos).getCity());*/
            Intent intent = new Intent();
            LatLonPoint latLonPoint = new LatLonPoint(latitude, longitude);
            setCurrentLocationDetails(latLonPoint);
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
            intent.putExtra("title", place);
            intent.putExtra("area", province + "," + city + "," + district);
            if (aoiName == null) {
                intent.putExtra("address", "");
            } else {
                intent.putExtra("address", aoiName);
            }

            intent.putExtra("city", city);

            setResult(2, intent);


            finish();
        }
    }

    @Override
    public void setHisPosition(HisPositionBean bean) {
        list1 = bean.getData().getList();
        locationRVId.setVisibility(View.VISIBLE);
        history.setVisibility(View.VISIBLE);
        adapter1 = new HistoryPoiSearchAdapter(R.layout.item_search, list1);
        locationRVId.setAdapter(adapter1);
        locationRVId.setLayoutManager(new LinearLayoutManager(this));
        adapter1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                double latitude = Double.valueOf(list1.get(position).getLatitude());
                double longitude = Double.valueOf(list1.get(position).getLongitude());
                String title = list1.get(position).getName();
                Intent intent = new Intent();
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                intent.putExtra("title", title);
                setResult(3, intent);
                finish();
            }
        });
    }

    @Override
    public void DelPosition(BaseBean bean) {
        int code = bean.getCode();
        if (code == 200) {
            history.setVisibility(View.GONE);
        }
    }


    class HistoryPoiSearchAdapter extends BaseQuickAdapter<HisPositionBean.DataBean.ListBean, BaseViewHolder> {
        public HistoryPoiSearchAdapter(int layoutResId, @Nullable List<HisPositionBean.DataBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, HisPositionBean.DataBean.ListBean item) {
            helper.setText(R.id.tv_title, item.getName());
            helper.setText(R.id.tv_province, item.getAreas());
        }
    }

}
