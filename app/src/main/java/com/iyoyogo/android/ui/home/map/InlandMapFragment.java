package com.iyoyogo.android.ui.home.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.map.CityEntity;
import com.iyoyogo.android.base.BaseFragment;
import com.iyoyogo.android.bean.map.MapBean;
import com.iyoyogo.android.bean.map.MapRenMei;
import com.iyoyogo.android.contract.MapSearchContract;
import com.iyoyogo.android.presenter.MapSearchPresenter;
import com.iyoyogo.android.ui.home.map.binding.Bind;
import com.iyoyogo.android.ui.home.map.binding.ViewBinder;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.map.JsonReadUtil;
import com.iyoyogo.android.utils.map.ToastUtils;
import com.iyoyogo.android.utils.search.SharedPrefrenceUtils;
import com.iyoyogo.android.view.LetterListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class InlandMapFragment extends BaseFragment<MapSearchContract.Presenter> implements AbsListView.OnScrollListener,MapSearchContract.View{


    @BindView(R.id.total_city_lv)
    ListView totalCityLv;
    @BindView(R.id.total_city_letters_lv)
    LetterListView totalCityLettersLv;
    @BindView(R.id.search_city_lv)
    ListView searchCityLv;
    @BindView(R.id.no_search_result_tv)
    TextView noSearchResultTv;
    Unbinder unbinder;
    private String city = "北京";
    @SuppressLint("HandlerLeak")
    private Handler handler;
    private final static String CityFileName = "allcitya.json";
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    public AMapLocationClientOption mLocationOption = null;
    TextView curCityNameTv;
    private TextView noSearchDataTv;

    private TextView overlay; // 对话框首字母TextView
    private OverlayThread overlayThread; // 显示首字母对话框
    private boolean mReady = false;
    private boolean isScroll = false;

    private HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置

    protected List<CityEntity> hotCityList = new ArrayList<>();
    protected List<CityEntity> totalCityList = new ArrayList<>();
    protected List<CityEntity> curCityList = new ArrayList<>();
    protected List<CityEntity> searchCityList = new ArrayList<>();
    protected CityListAdapter cityListAdapter;
    protected SearchCityListAdapter searchCityListAdapter;

    private String locationCity, curSelCity;
    private double lati;
    private double longa;
    private Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            locationCity = city;
            if (noSearchResultTv != null) {
                noSearchResultTv.setText(msg.obj.toString());
            }
        }
    };
    private TextView mCur_city_re_get_location_tv1;
    private List<MapBean.DataBean.ListBean> mRemein;
    private List<String> mAll;
    private List<MapBean.DataBean.ListBean> mMAll;
    private List<String> mMremei;
    private final String mGps1;

    @SuppressLint("ValidFragment")
    public InlandMapFragment(String gps) {
        mGps1 = gps;
        Log.e("czczxcz", "InlandMapFragment: "+mGps1 );
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.mapguomei, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        initView1();
        initListener1();
        initdiwei();
        return inflate;

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initData() {
        super.initData();
        String  mUser_id = SpUtils.getString(getActivity(), "user_id", null);
        String  mUser_token = SpUtils.getString(getActivity(), "user_token", null);
        mPresenter.renMei(mUser_id,mUser_token);
        mPresenter.aboutMe(mUser_id,mUser_token,"internal","");

    }

    private void initdiwei() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getActivity());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //声明AMapLocationClientOption对象

        mLocationOption = new AMapLocationClientOption();
        AMapLocationClientOption option = new AMapLocationClientOption();
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        if (null != mLocationClient) {
            mLocationClient.setLocationOption(option);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
            //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        }
    }

    private void initListener1() {

        searchCityLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CityEntity cityEntity = searchCityList.get(position);
                showSetCityDialog(cityEntity.getName(), cityEntity.getCityCode());
            }
        });
    }

    /**
     * 展示设置城市对话框
     */
    private void showSetCityDialog(String name, String cityCode) {

        if (name.equals(curSelCity)) {
            ToastUtils.show("当前定位城市" + name);
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());  //先得到构造器
        builder.setTitle("提示"); //设置标题
        builder.setMessage("是否设置 " + name + " 为您的当前城市？"); //设置内容

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //选中之后做你的方法
                if (setData != null) {
                    setData.getData(name);
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    private void initData1() {
        initTotalCityList();
        cityListAdapter = new CityListAdapter(getActivity(), totalCityList, hotCityList);
        totalCityLv.setAdapter(cityListAdapter);
        totalCityLv.setOnScrollListener(this);
        totalCityLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 1) {
                    CityEntity cityEntity = totalCityList.get(position-2);
                    showSetCityDialog(cityEntity.getName(), cityEntity.getCityCode());
                }

            }
        });
        totalCityLettersLv.setOnTouchingLetterChangedListener(new LetterListViewListener());
        initOverlay();
    }

    //presenter
    @Override
    protected MapSearchContract.Presenter createPresenter() {
        return  new  MapSearchPresenter(getActivity(),this);
    }


    private void initOverlay() {
        mReady = true;
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        overlay = (TextView) inflater.inflate(R.layout.overlay, null);
        overlay.setVisibility(View.GONE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        WindowManager windowManager = (WindowManager) this
                .getActivity().getSystemService(Context.WINDOW_SERVICE);
           windowManager.addView(overlay, lp);
    }

    /**
     * 初始化全部城市列表2
     */
    public void initTotalCityList() {
        hotCityList.clear();
        totalCityList.clear();
        curCityList.clear();
        /**
         * 处理数据
         */

        // 热门数据
        for (int i = 0; i <mMremei.size() ; i++) {
            CityEntity hotCity = new CityEntity();
            hotCity.setName(mMremei.get(i));
            hotCityList.add(hotCity);
        }
        for (int i = 0; i <mMAll.size() ; i++) {
            CityEntity cityEntity = new CityEntity();
            cityEntity.setName(mMAll.get(i).getChina_name());
            //处理K
            String english_name = mMAll.get(i).getEnglish_name();
            english_name = english_name.substring(0,1);
            cityEntity.setKey(english_name);
            cityEntity.setPinyin(mMAll.get(i).getEnglish_name());
            //cityEntity.setFirst(first);
            // cityEntity.setCityCode(cityCode);

            totalCityList.add(cityEntity);
        }
    }


    private void initView1() {
        ViewBinder.bind(getActivity());
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mToolbar.setPadding(0, 0, 0, 0);
        }*/

        handler = new Handler();
        overlayThread = new OverlayThread();
        searchCityListAdapter = new SearchCityListAdapter(getActivity(), searchCityList);
        searchCityLv.setAdapter(searchCityListAdapter);

        curSelCity = locationCity;
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_TOUCH_SCROLL
                || scrollState == SCROLL_STATE_FLING) {
            isScroll = true;
        } else {
            isScroll = false;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (!isScroll) {
            return;
        }
        if (mReady) {
            String key = getAlpha(totalCityList.get(firstVisibleItem).getKey());
            overlay.setText(key);
            overlay.setVisibility(View.VISIBLE);
            handler.removeCallbacks(overlayThread);
            // 延迟让overlay为不可见
            handler.postDelayed(overlayThread, 700);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    //all
    @Override
    public void aboutMeSuccess(MapBean data) {
        mMAll = data.getData().getList();
        initData1();
    }
    //remei
    @Override
    public void renMeiChengshi(MapRenMei data) {
        mMremei = data.getData().getList();
    }

    /**
     * 设置overlay不可见
     */
    private class OverlayThread implements Runnable {
        @Override
        public void run() {
            overlay.setVisibility(View.GONE);
        }
    }

    /**
     * 搜索城市列表适配器
     */
    private class SearchCityListAdapter extends BaseAdapter {

        private List<CityEntity> cityEntities;
        private Context context;

        public SearchCityListAdapter(Context activity, List<CityEntity> searchCityList) {
            this.context = activity;
            this.cityEntities = searchCityList;
        }


        @Override
        public int getCount() {
            return cityEntities == null ? 0 : cityEntities.size();
        }

        @Override
        public Object getItem(int position) {
            return cityEntities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (null == convertView) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.city_list_item_layout, null);
                ViewBinder.bind(holder, convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            CityEntity cityEntity = cityEntities.get(position);
            holder.cityKeyTv.setVisibility(View.GONE);
            holder.cityNameTv.setText(cityEntity.getName());

            return convertView;
        }


        private class ViewHolder {
            @Bind(R.id.city_name_tv)
            TextView cityNameTv;
            @Bind(R.id.city_key_tv)
            TextView cityKeyTv;
        }
    }

    /**
     * 总城市适配器
     */
    private class CityListAdapter extends BaseAdapter {
        private Context context;

        private List<CityEntity> totalCityList;
        private List<CityEntity> hotCityList;
        private LayoutInflater inflater;
        final int VIEW_TYPE = 3;

        CityListAdapter(Context context,
                        List<CityEntity> totalCityList,
                        List<CityEntity> hotCityList) {
            this.context = context;
            this.totalCityList = totalCityList;
            this.hotCityList = hotCityList;
            inflater = LayoutInflater.from(context);

            alphaIndexer = new HashMap<>();

            for (int i = 0; i < totalCityList.size(); i++) {
                // 当前汉语拼音首字母
                String currentStr = totalCityList.get(i).getKey();

                String previewStr = (i - 1) >= 0 ? totalCityList.get(i - 1).getKey() : " ";
                if (!previewStr.equals(currentStr)) {
                    String name = getAlpha(currentStr);
                    alphaIndexer.put(name, i);
                }
            }
        }

        @Override
        public int getViewTypeCount() {
            return VIEW_TYPE;
        }

        @Override
        public int getItemViewType(int position) {
            return position < 2 ? position : 2;
        }

        @Override
        public int getCount() {
            return totalCityList == null ? 0 : totalCityList.size()+2;
        }

        @Override
        public Object getItem(int position) {
            return totalCityList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            int viewType = getItemViewType(position);
            if (viewType == 0) { // 定位
                convertView = inflater.inflate(R.layout.select_city_location_item, null);
                LinearLayout noLocationLl = convertView.findViewById(R.id.cur_city_no_data_ll);
                TextView getLocationTv = convertView.findViewById(R.id.cur_city_re_get_location_tv);
                curCityNameTv = convertView.findViewById(R.id.cur_city_name_tv);
                mCur_city_re_get_location_tv1 = convertView.findViewById(R.id.cur_city_re_get_location_tv1);
                noSearchResultTv = convertView.findViewById(R.id.tt);
                if (TextUtils.isEmpty(locationCity)) {
                    noLocationLl.setVisibility(View.VISIBLE);
                    curCityNameTv.setVisibility(View.GONE);
                    mCur_city_re_get_location_tv1.setVisibility(View.GONE);
                    getLocationTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //定位
                            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                                    != PackageManager.PERMISSION_GRANTED) {//未开启定位权限
                                //开启定位权限,200是标识码
                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
                            } else {
                                initdiwei();//开始定位

                            }
                        }
                    });

                } else {
                    noLocationLl.setVisibility(View.GONE);
                    curCityNameTv.setVisibility(View.VISIBLE);
                    curCityNameTv.setText(locationCity);
                    mCur_city_re_get_location_tv1.setVisibility(View.VISIBLE);
                    mCur_city_re_get_location_tv1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                          //  Toast.makeText(getActivity(), "qwe", Toast.LENGTH_SHORT).show();
                            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                                    != PackageManager.PERMISSION_GRANTED) {//未开启定位权限
                                //开启定位权限,200是标识码
                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
                            } else {
                                initdiwei();//开始定位
                             //   Toast.makeText(getActivity(), "已开启定位权限", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                    curCityNameTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!locationCity.equals(curSelCity)) {
                                //设置城市代码
                                String cityCode = "";
                                for (CityEntity cityEntity : curCityList) {
                                    if (cityEntity.getName().equals(locationCity)) {
                                        cityCode = cityEntity.getCityCode();
                                        break;
                                    }
                                }
                                showSetCityDialog(locationCity, cityCode);
                            } else {
                                ToastUtils.show("当前定位城市" + curCityNameTv.getText().toString());
                            }
                        }
                    });
                }
            } else if (viewType == 1) { //热门城市
                convertView = inflater.inflate(R.layout.recent_city_item, null);
                GridView hotCityGv = convertView.findViewById(R.id.recent_city_gv);
                hotCityGv.setAdapter(new HotCityListAdapter(context, this.hotCityList));
                hotCityGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        CityEntity cityEntity = hotCityList.get(position);
                        showSetCityDialog(cityEntity.getName(), cityEntity.getCityCode());
                    }
                });
            } else {
                if (null == convertView) {
                    holder = new ViewHolder();
                    convertView = inflater.inflate(R.layout.city_list_item_layout, null);
                    ViewBinder.bind(holder, convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                CityEntity cityEntity = totalCityList.get(position-2);
                holder.cityKeyTv.setVisibility(View.VISIBLE);
                holder.cityKeyTv.setText(getAlpha(cityEntity.getKey()));
                holder.cityNameTv.setText(cityEntity.getName());

                if (position >= 3) {
                    CityEntity preCity = totalCityList.get(position -3);
                    if (preCity.getKey().equals(cityEntity.getKey())) {
                        holder.cityKeyTv.setVisibility(View.GONE);
                    } else {
                        holder.cityKeyTv.setVisibility(View.VISIBLE);
                    }
                }
            }

            return convertView;
        }

        private class ViewHolder {
            @Bind(R.id.city_name_tv)
            TextView cityNameTv;
            @Bind(R.id.city_key_tv)
            TextView cityKeyTv;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 200://刚才的识别码
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){//用户同意权限,执行我们的操作
                    initdiwei();//开始定位
                }else{//用户拒绝之后,当然我们也可以弹出一个窗口,直接跳转到系统设置页面
                    Toast.makeText(getActivity(),"未开启定位权限,请手动到设置去开启权限",Toast.LENGTH_LONG).show();
                }
                break;
            default:break;
        }
    }
/*  @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        String s = tt.getText().toString();
        if(!s.equals("无法获取您的定位地址")&&s.equals("")){
            OneFragment.one_GPS.setText(s);
        }
        return super.onKeyDown(keyCode, event);
    }*/

    /**
     * 获得首字母
     */
    private String getAlpha(String key) {
        if (key.equals("0")) {
            return "A";
        } else if (key.equals("1")) {
            return "B";
        } else {
            return key;
        }
    }

    /**
     * 热门城市适配器
     */
    private class HotCityListAdapter extends BaseAdapter {

        private List<CityEntity> cityEntities;
        private LayoutInflater inflater;

        HotCityListAdapter(Context mContext, List<CityEntity> cityEntities) {
            this.cityEntities = cityEntities;
            inflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return cityEntities == null ? 0 : cityEntities.size();
        }

        @Override
        public Object getItem(int position) {
            return cityEntities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (null == convertView) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.city_list_grid_item_layout, null);
                ViewBinder.bind(holder, convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            CityEntity cityEntity = cityEntities.get(position);
            holder.cityNameTv.setText(cityEntity.getName());

            return convertView;
        }

        private class ViewHolder {
            @Bind(R.id.city_list_grid_item_name_tv)
            TextView cityNameTv;
        }
    }


    /*    */
    /**
     * 设置沉浸式状态栏
     *//*
    private void setSystemBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 5.0 LOLLIPOP解决方案
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 4.4 KITKAT解决方案
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }*/

    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            city = aMapLocation.getCity();
            Log.e("-----11----", city);
            Message obtain = Message.obtain();
            obtain.obj = city;
            handler1.sendMessageAtTime(obtain, 5000);

        }
    };

    /**
     * 设置搜索数据展示
     */
    private void setSearchCityList(String content) {
        searchCityList.clear();
        if (TextUtils.isEmpty(content)) {
            totalCityLv.setVisibility(View.VISIBLE);
            totalCityLettersLv.setVisibility(View.VISIBLE);
            searchCityLv.setVisibility(View.GONE);
            noSearchDataTv.setVisibility(View.GONE);
        } else {
            totalCityLv.setVisibility(View.GONE);
            totalCityLettersLv.setVisibility(View.GONE);
            for (int i = 0; i < curCityList.size(); i++) {
                CityEntity cityEntity = curCityList.get(i);
                if (cityEntity.getName().contains(content) || cityEntity.getPinyin().contains(content)
                        || cityEntity.getFirst().contains(content)) {
                    searchCityList.add(cityEntity);
                }
            }

            if (searchCityList.size() != 0) {
                noSearchDataTv.setVisibility(View.GONE);
                searchCityLv.setVisibility(View.VISIBLE);
            } else {
                noSearchDataTv.setVisibility(View.VISIBLE);
                searchCityLv.setVisibility(View.GONE);
            }

            searchCityListAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 隐藏软件盘
     */
    public void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private class LetterListViewListener implements LetterListView.OnTouchingLetterChangedListener {

        @Override
        public void onTouchingLetterChanged(final String s) {
            isScroll = false;
            if (alphaIndexer.get(s) != null) {
                int position = alphaIndexer.get(s);
                totalCityLv.setSelection(position);
                overlay.setText("");
                overlay.setVisibility(View.GONE);
                handler.removeCallbacks(overlayThread);
                // 延迟让overlay为不可见
                handler.postDelayed(overlayThread, 700);
            }
        }
    }
    private setData setData;

    public void setSetData(InlandMapFragment.setData setData) {
        this.setData = setData;
    }

    public interface setData{
        void getData(String name);
    }
}
