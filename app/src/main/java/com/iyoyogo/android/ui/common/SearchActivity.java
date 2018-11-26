package com.iyoyogo.android.ui.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.SeachAddressAdapter;
import com.iyoyogo.android.bean.AddressBean;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, PoiSearch.OnPoiSearchListener {

    /* @BindView(R.id.tv_search)
     TextView tvSearch;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_serach);
         ButterKnife.bind(this);
         tvSearch.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(SearchActivity.this, CreatePointActivity.class);
                 startActivity(intent);
             }
         });
     }*/
    private Context mContext;
    private EditText et_search;
    private RelativeLayout rl_finish;
    private ListView lv_address;
    private PoiSearch.Query query;
    private int page = 0;
    private PoiSearch poiSearch;
    private SeachAddressAdapter adapter;
    private ArrayList<AddressBean> data = new ArrayList<AddressBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serach);

//        MyAppliaction.setInnerLayoutFullScreen(this);
//        MyAppliaction.addActivity(this);
        mContext = this;
        initView();
        setOnClick();
    }

    private void initView() {
        //String CityName=mapUtils.getCityName();
        //Log.i("=====CityName",CityName);
        adapter = new SeachAddressAdapter(mContext, data);
        et_search = (EditText) findViewById(R.id.et_search);
        rl_finish = (RelativeLayout) findViewById(R.id.rl_finish);
        lv_address = (ListView) findViewById(R.id.lv_address);
        lv_address.setAdapter(adapter);
    }

    private void seach(String address) {
        //keyWord表示搜索字符串，
        //第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
        //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query = new PoiSearch.Query(address, "", "");
        query.setPageSize(300);// 设置每页最多返回多少条poiitem
        query.setPageNum(page);//设置查询页码

        poiSearch = new PoiSearch(this, query);
        poiSearch.searchPOIAsyn();//调用 PoiSearch 的 searchPOIAsyn() 方法发送请求。

        poiSearch.setOnPoiSearchListener(this);

        //周边检索
        //   poiSearch.setBound(new SearchBound(new LatLonPoint(locationMarker.getPosition().latitude,
        //   locationMarker.getPosition().longitude), 1000));//设置周边搜索的中心点以及半径
    }

    private void setOnClick() {
        rl_finish.setOnClickListener(this);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = et_search.getText().toString();
                if (str.length() > 0) {
                    seach(str);
                }
            }
        });

        lv_address.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();//ListView item点击回调
                intent.putExtra("data", data.get(i));
                setResult(100, intent);
                finish();
            }
        });

    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        data.clear();
        //解析result获取POI信息
        if (i == 1000 && poiResult != null) {
            ArrayList<PoiItem> items = poiResult.getPois();
            for (PoiItem item : items) {
                //获取经纬度对象
                LatLonPoint llp = item.getLatLonPoint();
                double lon = llp.getLongitude();
                double lat = llp.getLatitude();

                //获取标题
                String title = item.getTitle();
                Log.i("9527", title + "");
                //获取内容
                String text = item.getSnippet();
                String name = item.getAdName();
                String cityName = item.getCityName();
                String area = item.getBusinessArea();
                String provinceName = item.getProvinceName();

                String addressInfo = provinceName + cityName + name + text;

                data.add(new AddressBean(lon, lat, title, text, addressInfo));
            }
            adapter.refreshData(data);
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
        }
    }

}
