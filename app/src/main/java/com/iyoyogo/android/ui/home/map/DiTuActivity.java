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

public class DiTuActivity extends BaseActivity<MapSearchContract.Presenter> implements View.OnClickListener, MapSearchContract.View {


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
    private ListView mListViewSearchResult;

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
                           /* AlertDialog.Builder builder = new AlertDialog.Builder(DiTuActivity.this);  //先得到构造器
                            builder.setTitle("提示"); //设置标题
                            builder.setMessage("是否设置 " + mDragList.get(i).toString() + " 为您的当前城市？"); //设置内容

                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    //选中之后做你的方法
                                    finish();
                                }
                            });
                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            });*/
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



   /*     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
            Window window = getWindow();
            View decorView = window.getDecorView();
            //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            //导航栏颜色也可以正常设置
            //window.setNavigationBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            attributes.flags |= flagTranslucentStatus;
            //int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            //attributes.flags |= flagTranslucentNavigation;
            window.setAttributes(attributes);
        }*/

        initView1();
        initEvent();

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

    private void initEvent() {
        // TODO Auto-generated method stub
        ////点击输入法软键盘回车键时，也可以直接查询
        mEditTextSearchContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (!TextUtils.isEmpty(mEditTextSearchContent.getText())) {
                    // 保持光标在输入框最后
                    mEditTextSearchContent.setSelection(mEditTextSearchContent.getText().length());
                }
                //当actionId == XX_SEND 或者 XX_DONE时都触发
                //或者event.getKeyCode == ENTER 且 event.getAction == ACTION_DOWN时也触发
                //注意，这是一定要判断event != null。因为在某些输入法上会返回null。
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    //点击输入法软键盘回车键时，进行查询
                    search();
                }
                return true;//为true表示自己消费该事件
            }
        });
    }

    private void initView1() {
// TODO Auto-generated method stub
        mEditTextSearchContent = (EditText) findViewById(R.id.search_editText_searchContent);
        mTextViewSearch = (TextView) findViewById(R.id.search_tv_search);
        mListViewSearchResult = (ListView) findViewById(R.id.search_listView_search_result);
        //设置监听
        mTextViewSearch.setOnClickListener(this);
        mEditTextSearchContent.setOnClickListener(this);
        mEditTextSearchContent.addTextChangedListener(textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            //获取输入框内容
            content = mEditTextSearchContent.getText().toString();
            //输入框内容为空时，显示返回按钮，隐藏搜索按钮
            if (content.isEmpty()) {
                mTextViewSearch.setVisibility(View.GONE);
                //   mButtonBack.setVisibility(View.VISIBLE);
                mListViewSearchResult.setVisibility(View.GONE);
                group1.setVisibility(View.VISIBLE);
                frameContainer.setVisibility(View.VISIBLE);
                searchTvQuxiao.setVisibility(View.VISIBLE);
            } else {
                group1.setVisibility(View.GONE);
                searchTvQuxiao.setVisibility(View.GONE);
                frameContainer.setVisibility(View.GONE);
                mTextViewSearch.setVisibility(View.VISIBLE);
                // mButtonBack.setVisibility(View.GONE);
                mListViewSearchResult.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        switch (view.getId()) {
            //点击搜索框
            case R.id.search_editText_searchContent:
                mEditTextSearchContent.setFocusable(true);
                mEditTextSearchContent.setFocusableInTouchMode(true);
                mEditTextSearchContent.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mEditTextSearchContent, 0);
                searchTvQuxiao.setVisibility(View.GONE);
                break;
            //关闭按钮
        /*    case R.id.search_btn_back:
                finish();
                break;*/
            //点击搜索
            case R.id.search_tv_search:
                String user_id = SpUtils.getString(DiTuActivity.this, "user_id", null);
                String user_token = SpUtils.getString(DiTuActivity.this, "user_token", null);
                mPresenter.aboutMe(user_id, user_token, "internal", mEditTextSearchContent.getText().toString());
                break;
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        String user_id = SpUtils.getString(DiTuActivity.this, "user_id", null);
        String user_token = SpUtils.getString(DiTuActivity.this, "user_token", null);
        // mPresenter.aboutMe(user_id,user_token,"internal",mEditTextSearchContent.getText().toString());
    }

    /**
     * 搜索数据
     */
    private void search() {

    }


    /* */

    /**
     * 设置沉浸式状态栏
     *//*
    private void setSystemBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 5.0 LOLLIPOP解决方案
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 4.4 KITKAT解决方案
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        //setSystemBarTransparent();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
            Window window = getWindow();
            View decorView = window.getDecorView();
            //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            //导航栏颜色也可以正常设置
            //window.setNavigationBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            attributes.flags |= flagTranslucentStatus;
            //int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            //attributes.flags |= flagTranslucentNavigation;
            window.setAttributes(attributes);
        }


    }

    //成功的回调
    @Override
    public void aboutMeSuccess(MapBean data) {
        if (data.getData().getList() != null) {
            list = data.getData().getList();
        }
        if (list.size() != 0) {
            group1.setVisibility(View.GONE);
            gson.setVisibility(View.GONE);
            gson1.setVisibility(View.GONE);
            frameContainer.setVisibility(View.GONE);
            mSearchListViewAdapter = new SearchListViewAdapter(DiTuActivity.this, list, content);
            mListViewSearchResult.setAdapter(mSearchListViewAdapter);
            mListViewSearchResult.setVisibility(View.VISIBLE);
            Log.e("qweq1112", "aboutMeSuccess: " + list.get(0).getChina_name());
            mListViewSearchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    EventBus.getDefault().post(list.get(position).getChina_name());
                    finish();
                }
            });
        }
        if (list.size() <= 0) {
            gson.setVisibility(View.VISIBLE);
            gson1.setVisibility(View.VISIBLE);
            frameContainer.setVisibility(View.GONE);
            mListViewSearchResult.setVisibility(View.GONE);
            group1.setVisibility(View.GONE);
            Log.e("wqeqweqweqweqwe", "aboutMeSuccess: " + list.size());
        }

    }

    private void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commitAllowingStateLoss();
    }
}