package com.iyoyogo.android.ui.home.map;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.search.SearchListViewAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.map.MapBean;
import com.iyoyogo.android.bean.search.User;
import com.iyoyogo.android.contract.MapSearchContract;
import com.iyoyogo.android.presenter.MapSearchPresenter;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.StatusBarUtil;
import com.iyoyogo.android.view.MyGridLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiTuActivity extends BaseActivity<MapSearchContract.Presenter> implements  MapSearchContract.View {


    @BindView(R.id.gson)
    LinearLayout gson;
    @BindView(R.id.rb_yoji)
    RadioButton rbYoji;
    @BindView(R.id.rb_yoxiu)
    RadioButton rbYoxiu;
    @BindView(R.id.group)
    RadioGroup group;
    @BindView(R.id.frame_container)
    FrameLayout frameContainer;
    @BindView(R.id.group1)
    LinearLayout group1;
    @BindView(R.id.search_tv_quxiao)
    TextView searchTvQuxiao;
    @BindView(R.id.search_listView_search_result)
    ListView searchListViewSearchResult;
    @BindView(R.id.gson1)
    LinearLayout gson1;
    private List<Fragment> list1 = new ArrayList<>();
    private List<MapBean.DataBean.ListBean> listBeans = new ArrayList<>();
    //返回按钮（关闭）
    private Button mButtonBack;
    //输入框
    private EditText mEditTextSearchContent;
    //搜索按钮
    private TextView mTextViewSearch;
    //用户输入的搜索内容
    private String searchContent;
    //显示搜索内容的ListView
    private static  final  int TEVTASC = 394;
    //用户集合
    private List<User> mUserList;
    //ListView的适配器
    private SearchListViewAdapter mSearchListViewAdapter;
    private String content;
    private List<MapBean.DataBean.ListBean> list;
    private List<String> mDragList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_di_tu;
    }

    @Override
    protected MapSearchPresenter createPresenter() {
        return new MapSearchPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();

        final MyGridLayout myDragGridLayout = findViewById(R.id.my_gridlayout);
        myDragGridLayout.setDragAble(true);
        mDragList = new ArrayList<String>();
        mDragList.add("北京");
        mDragList.add("上海");
        mDragList.add("天津");
        mDragList.add("哈尔滨");
        mDragList.add("青海");
        mDragList.add("西安");
        mDragList.add("南京");
        mDragList.add("杭州");
        mDragList.add("厦门");
        mDragList.add("成都");
        mDragList.add("深圳");
        mDragList.add("广州");
        myDragGridLayout.addItems(mDragList);

        myDragGridLayout.setOnItemtClickListenner(new MyGridLayout.OnItemClickListenner() {
            @Override
            public void setOnItemClickListenner(View view) {
                if (view instanceof TextView) {
                    String strText = ((TextView) view).getText().toString().trim();
                    for (int i = 0; i < mDragList.size(); i++) {
                        if (mDragList.get(i).equals(strText)) {
                           EventBus.getDefault().post(mDragList.get(i));
                            finish();
                        }
                    }
                }

            }
        });


        StatusBarUtil.setStatusBarLayoutStyle(DiTuActivity.this, true);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.holo_orange_dark));
        InlandMapFragment inlandMapFragment = new InlandMapFragment();
        ForeignMapFragment foreignMapFragment = new ForeignMapFragment();
        list1.add(inlandMapFragment);
        list1.add(foreignMapFragment);
        switchFragment(inlandMapFragment);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_yoji:
                        switchFragment(inlandMapFragment);
                        break;
                    case R.id.rb_yoxiu:
                        switchFragment(foreignMapFragment);
                        break;
                }
            }
        });
        searchTvQuxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        initView1();
        inlandMapFragment.setSetData(new InlandMapFragment.setData() {
            @Override
            public void getData(String name) {
                EventBus.getDefault().post(name);
                finish();
            }
        });
        foreignMapFragment.setGetData(new ForeignMapFragment.getData() {
            @Override
            public void getData1(String name) {
                EventBus.getDefault().post(name);
                finish();
            }
        });
    }

    private void initView1() {
// TODO Auto-generated method stub
        mEditTextSearchContent = (EditText) findViewById(R.id.search_editText_searchContent);
        //设置监听
        mEditTextSearchContent.addTextChangedListener(textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
           // searchListViewSearchResult.setVisibility(View.GONE);
            Log.e("hanbaocheng", "onTextChanged: "+"点击了onTextChanged" );
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            // TODO Auto-generated method stub

            Log.e("hanbaocheng", "onTextChanged: "+"点击了beforeTextChanged" );
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            //获取输入框内容
            content = mEditTextSearchContent.getText().toString();
            if (content != null){
                String user_id = SpUtils.getString(DiTuActivity.this, "user_id", null);
                String user_token = SpUtils.getString(DiTuActivity.this, "user_token", null);
                mPresenter.aboutMe(user_id, user_token, "internal", mEditTextSearchContent.getText().toString());
                //   mButtonBack.setVisibility(View.VISIBLE);
                group1.setVisibility(View.GONE);
            }
            if (content == null){
                searchListViewSearchResult.setVisibility(View.GONE);
                group1.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

    }

    //成功的回调
    @Override
    public void aboutMeSuccess(MapBean data) {
        if (data.getData().getList() != null) {
            list = data.getData().getList();
        }
        Log.e("qweq1112222", "aboutMeSuccess: " + list.size());
        if (list.size() != 0) {
            group1.setVisibility(View.GONE);
            gson.setVisibility(View.GONE);
            gson1.setVisibility(View.GONE);
            mSearchListViewAdapter = new SearchListViewAdapter(DiTuActivity.this, list, content);
            searchListViewSearchResult.setAdapter(mSearchListViewAdapter);
            searchListViewSearchResult.setVisibility(View.VISIBLE);
            Log.e("qweq1112", "aboutMeSuccess: " + list.get(0).getChina_name());
            searchListViewSearchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    EventBus.getDefault().post(list.get(position).getChina_name());
                    finish();
                }
            });
        }
        if (list.size() == TEVTASC){
            group1.setVisibility(View.VISIBLE);
            searchListViewSearchResult.setVisibility(View.GONE);
        }

        if (list.size() <= 0) {
            gson.setVisibility(View.VISIBLE);
            gson1.setVisibility(View.VISIBLE);
            //searchListViewSearchResult.setVisibility(View.GONE);
            group1.setVisibility(View.GONE);
        }
    }

    private void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commitAllowingStateLoss();
    }
}