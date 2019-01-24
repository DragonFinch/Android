package com.iyoyogo.android.ui.home.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.SearchUserAdapter;
import com.iyoyogo.android.adapter.SearchYoXiuListAdapter;
import com.iyoyogo.android.adapter.search.ListViewkeywordAdapter;
import com.iyoyogo.android.adapter.search.SearchYoJiListHorizontalAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.search.ClerBean;
import com.iyoyogo.android.bean.search.GuanZhuBean;
import com.iyoyogo.android.bean.search.KeywordBean;
import com.iyoyogo.android.bean.search.KeywordUserBean;
import com.iyoyogo.android.bean.search.searchInfo;
import com.iyoyogo.android.contract.KeywordContract;
import com.iyoyogo.android.presenter.KeywordPresenter;
import com.iyoyogo.android.ui.home.yoji.UserHomepageActivity;
import com.iyoyogo.android.ui.home.yoji.YoJiDetailActivity;
import com.iyoyogo.android.ui.home.yoxiu.YoXiuDetailActivity;
import com.iyoyogo.android.ui.mine.homepage.Personal_homepage_Activity;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.search.SPUtils;
import com.iyoyogo.android.view.ZFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchResultActivity extends BaseActivity<KeywordContract.Presenter> implements View.OnClickListener, KeywordContract.View {

    @BindView(R.id.lv)
    RecyclerView lv;
    @BindView(R.id.tv_setname)
    TextView tvSetname;
    @BindView(R.id.lv_user)
    RecyclerView lvUser;
    @BindView(R.id.lv_content)
    RecyclerView lvContent;
    @BindView(R.id.name)
    ImageView name;
    @BindView(R.id.content)
    ImageView content;
    @BindView(R.id.hit)
    LinearLayout hit;
    @BindView(R.id.list_view_lv)
    ListView listViewLv;
    @BindView(R.id.sl)
    ScrollView sl;
    @BindView(R.id.tv_gson)
    TextView tvGson;
    @BindView(R.id.tv_gson1)
    TextView tvGson1;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.Lin)
    LinearLayout Lin;
    @BindView(R.id.Li)
    LinearLayout Li;
    @BindView(R.id.vertical)
    LinearLayout vertical;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.v)
    LinearLayout v;
    private PopupWindow popupWindow;
    private List<KeywordBean.DataBean.UserListBean> mUser = new ArrayList<>();
    private List<KeywordBean.DataBean.YojListBean> myoj = new ArrayList<>();
    private List<KeywordBean.DataBean.YoxListBean> myox = new ArrayList<>();
    private List<KeywordBean.DataBean> mdata = new ArrayList<>();
    private String user_id;
    private String user_token;
    private SearchUserAdapter adapter = new SearchUserAdapter(SearchResultActivity.this, mUser, mdata);
    private SearchYoJiListHorizontalAdapter adapter3 = new SearchYoJiListHorizontalAdapter(SearchResultActivity.this, myoj);
    private SearchYoXiuListAdapter adapter1 = new SearchYoXiuListAdapter(SearchResultActivity.this, myox);
    private String s;
    private List<KeywordBean.DataBean.UserListBean> user_list;
    private List<KeywordUserBean.DataBean.ListBean> listBeans = new ArrayList<>();
    private List<KeywordUserBean.DataBean.ListBean> list;
    private TextView tv_guanzhu1;
    boolean fig1 = false;
    boolean canLinstener = true;

    //首页搜索
    @BindView(R.id.auto_search)
    EditText autoSearch;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.clear_iv)
    TextView clearIv;
    @BindView(R.id.history_fl)
    ZFlowLayout historyFl;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.keyword_fl)
    ZFlowLayout keywordFl;
    @BindView(R.id.rl_lishi)
    RelativeLayout rlLishi;

    private List<searchInfo> mList = new ArrayList<>();
    private List<searchInfo> list1;
    private boolean fig = false;
    private String mUser_id;
    private String mUser_token;
    private  ListViewkeywordAdapter listviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        lv.setLayoutManager(new LinearLayoutManager(this));
        lvUser.setLayoutManager(new LinearLayoutManager(this));
        lvContent.setLayoutManager(new LinearLayoutManager(this));

        tvSetname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出pop
                init();
            }
        });

        adapter.setSetOnClickListener(new SearchUserAdapter.SetOnClickListener() {
            @Override
            public void setOnClickListener(TextView tv_guanzhu, int po) {
                tv_guanzhu1 = tv_guanzhu;
                s = tv_guanzhu.getText().toString();
                int user_id1 = mUser.get(po).getUser_id();
                if (s.equals("已关注")) {
                    mPresenter.getGuanZhu(user_id, user_token, user_id1 + "");
                }
                if (s.equals("+关注")) {
                    mPresenter.getGuanZhu(user_id, user_token, user_id1 + "");
                }
                if (s.equals("互相关注")) {
                    mPresenter.getGuanZhu(user_id, user_token, user_id1 + "");
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_first;
    }

    //点击用户弹出PopupWindow
    private void init() {
        View view = LayoutInflater.from(SearchResultActivity.this).inflate(R.layout.popup_user_item, null);
        popupWindow = new PopupWindow();
        popupWindow.setContentView(view);//设置要显示的View控件
        popupWindow.setWidth(200);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout select_all = view.findViewById(R.id.select_all);
        LinearLayout youji = view.findViewById(R.id.youji);
        LinearLayout yoxiu = view.findViewById(R.id.yoxiu);
        LinearLayout user = view.findViewById(R.id.user);
        select_all.setOnClickListener(this);
        youji.setOnClickListener(this);
        user.setOnClickListener(this);
        yoxiu.setOnClickListener(this);

        popupWindow.setOutsideTouchable(true);//设置点击空白消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));//设置背景;点击返回按钮,关闭PopupWindow
        popupWindow.showAsDropDown(tvSetname, 30, 50);
    }

    @Override
    protected KeywordContract.Presenter createPresenter() {
        return new KeywordPresenter(SearchResultActivity.this, this);
    }

    @Override
    protected void initView() {
        super.initView();
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);

        //软键盘的搜索点击时间
        autoSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String s1 = String.valueOf(s);
                if (s1.isEmpty()&& listviewAdapter != null) {
                    listviewAdapter.clearData();
                    listviewAdapter.notifyDataSetChanged();
                    vertical.setVisibility(View.VISIBLE);
                    Lin.setVisibility(View.GONE);
                    hit.setVisibility(View.GONE);
                    tvSetname.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (canLinstener && !s.toString().isEmpty()) {
                    autoSearch.setSelection(autoSearch.getText().length());
                    ivBack.setVisibility(View.GONE);
                    mPresenter.getSearch(SearchResultActivity.this, user_id, user_token, autoSearch.getText().toString());
                    Log.e("search", "afterTextChanged1111: " + autoSearch.getText().toString());
                    SPUtils.getInstance(SearchResultActivity.this).save(autoSearch.getText().toString());
                    initHistory();
                }
            }
        });

        //搜索框的搜索栏
        autoSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //关闭软件盘
                    hideKeyboard(autoSearch);

                    if (autoSearch.getText() != null && autoSearch.getText().toString().trim().length() > 0) {
                        autoSearch.setSelection(autoSearch.getText().length());
                        mPresenter.getKeyWord(SearchResultActivity.this, user_id, user_token, autoSearch.getText().toString(), "all", "");
                        //  mPresenter.getSearch(SearchResultActivity.this, user_id, user_token, searchGuanjiaci.getText().toString());
                        SPUtils.getInstance(SearchResultActivity.this).save(autoSearch.getText().toString());
                        initHistory();
                    } else {
                        showToastShort(SearchResultActivity.this, "请输入搜索内容！");
                    }
                }
                return false;
            }
        });
    }

    //点击事件的处理
    @OnClick({R.id.iv_back, R.id.clear_iv, R.id.tv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                String searchKey = autoSearch.getText().toString();
                autoSearch.setText("");
                finish();
                break;

            case R.id.clear_iv:
                SPUtils.getInstance(SearchResultActivity.this).cleanHistory();
                mPresenter.getSearchCler(SearchResultActivity.this, mUser_id, mUser_token);
                initHistory();

                break;
            case R.id.select_all:
                tvSetname.setText("全部");
                popupWindow.dismiss();
                //切换进行网络请求   调用BaseActivit
                autoSearch.setSelection(autoSearch.getText().length());
                mPresenter.getKeyWord(SearchResultActivity.this, user_id, user_token, autoSearch.getText().toString(), "all", "");
                break;
            case R.id.youji:
                autoSearch.setSelection(autoSearch.getText().length());
                tvSetname.setText("yo记");
                popupWindow.dismiss();
                mPresenter.getKeyWord(SearchResultActivity.this, user_id, user_token, autoSearch.getText().toString(), "yoj", "");
                break;
            case R.id.yoxiu:
                autoSearch.setSelection(autoSearch.getText().length());
                tvSetname.setText("yo秀");
                popupWindow.dismiss();
                mPresenter.getKeyWord(SearchResultActivity.this, user_id, user_token, autoSearch.getText().toString(), "yox", "");
                break;
            case R.id.user:
                autoSearch.setSelection(autoSearch.getText().length());
                tvSetname.setText("用户");
                popupWindow.dismiss();
                mPresenter.getKeyWord(SearchResultActivity.this, user_id, user_token, autoSearch.getText().toString(), "user", "");
                break;

            default:
                break;
        }
    }

    private boolean isNullorEmpty(String str) {
        return str == null || "".equals(str);
    }

    private void showToastShort(Context context, String data) {
        Toast toast = Toast.makeText(context, data, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                initHistory();
            }
        }
    }

    @Override
    public void getRecommendTopicSuccess(searchInfo list) {
        List<String> list_hot = list.getData().getList_hot();
        if (list.getData().getList_history().size() != 0) {
            rlLishi.setVisibility(View.VISIBLE);
            historyFl.setVisibility(View.VISIBLE);
        } else {
            rlLishi.setVisibility(View.GONE);
            historyFl.setVisibility(View.GONE);
        }
        initKeyword(list_hot);
        initHistory();
    }

    @Override
    public void getData(ClerBean clerBean) {
        ClerBean clerBean1 = clerBean;
        int code = clerBean1.getCode();
        if (code == 200) {
            Log.e("cczxzxzxz", "getData: " + "200");
            rlLishi.setVisibility(View.GONE);
            historyFl.setVisibility(View.GONE);
        }
    }

    //历史搜索
    private void initHistory() {
        final String[] data = SPUtils.getInstance(this).getHistoryList();
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 10, 10, 10);
        historyFl.removeAllViews();
        for (int i = 0; i < data.length; i++) {
            if (isNullorEmpty(data[i])) {
                return;
            }
            final int j = i;
            //添加分类块
            View paramItemView = getLayoutInflater().inflate(R.layout.adapter_search_keyword, null);
            TextView keyWordTv = paramItemView.findViewById(R.id.tv_content);
            keyWordTv.setText(data[j]);
            keyWordTv.setBackgroundResource(R.drawable.whitebg_strokegrey_radius3);
            historyFl.addView(paramItemView, layoutParams);
            keyWordTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    canLinstener = false;
                    autoSearch.setText(data[j]);
                    if (autoSearch.getText() != null && autoSearch.getText().toString().trim().length() > 0) {

                        autoSearch.setText(autoSearch.getText().toString());
                        SPUtils.getInstance(SearchResultActivity.this).save(autoSearch.getText().toString());
                        autoSearch.setSelection(autoSearch.getText().length());
                        mPresenter.getKeyWord(SearchResultActivity.this, user_id, user_token, autoSearch.getText().toString(), "all", "");
                        initHistory();
                    } else {
                        showToastShort(SearchResultActivity.this, "请输入搜索内容！");
                    }
                    canLinstener = true;
                }
            });
        }
    }

    //接口返回数据热门搜索
    private void initKeyword(final List<String> keyword) {
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 10, 10, 10);
        keywordFl.removeAllViews();
        for (int i = 0; i < keyword.size(); i++) {
            final int j = i;
            //添加分类块
            View paramItemView = getLayoutInflater().inflate(R.layout.adapter_search_keyword, null);
            TextView keyWordTv = paramItemView.findViewById(R.id.tv_content);
            keyWordTv.setText(keyword.get(i));
            keyWordTv.setBackgroundResource(R.drawable.whitebg_strokegrey_radius3);
            keywordFl.addView(paramItemView, layoutParams);
            keyWordTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    canLinstener = false;
                    autoSearch.setText(keyword.get(j));

                    if (autoSearch.getText() != null && autoSearch.getText().toString().trim().length() > 0) {
                        autoSearch.setSelection(autoSearch.getText().length());
                        autoSearch.setText(autoSearch.getText().toString());
                        SPUtils.getInstance(SearchResultActivity.this).save(autoSearch.getText().toString());
                        autoSearch.setSelection(autoSearch.getText().length());
                        mPresenter.getKeyWord(SearchResultActivity.this, user_id, user_token, autoSearch.getText().toString(), "all", "");
                        initHistory();
                    } else {
                        showToastShort(SearchResultActivity.this, "请输入搜索内容");
                    }
                    canLinstener = true;
                }
            });
        }
    }

    //网络请求
    @Override
    protected void initData(Bundle savedInstanceState) {
        int i = 0;
        super.initData(savedInstanceState);
        mUser_id = SpUtils.getString(SearchResultActivity.this, "user_id", null);
        mUser_token = SpUtils.getString(SearchResultActivity.this, "user_token", null);
        mPresenter.getSearch(SearchResultActivity.this, mUser_id, mUser_token);
        user_id = SpUtils.getString(SearchResultActivity.this, "user_id", null);
        user_token = SpUtils.getString(SearchResultActivity.this, "user_token", null);
  /*      searchGuanjiaci.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.e("mn", "onTextChanged11111111: "+"qwe" );
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String s1 = String.valueOf(s);
                if (canLinstener && s1.isEmpty()){
                   *//* Li.setVisibility(View.VISIBLE);
                    Lin.setVisibility(View.GONE);*//*
                    tvSetname.setVisibility(View.GONE);
                    Log.e("mn", "onTextChanged11: "+"qwe" );
                    Li.setVisibility(View.VISIBLE);
                    Lin.setVisibility(View.GONE);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (canLinstener && !s.toString().isEmpty()) {
                    if (fig1) {
                        mPresenter.getSearch(SearchResultActivity.this, user_id, user_token, s.toString());
                        Log.e("xczv,lm", "afterTextChanged: " + user_id + "user_token" + user_token + s.toString());
                        cancel.setVisibility(View.VISIBLE);
                    } else {
                        fig1 = true;
                    }
                    Log.e("mn", "onTextChanged:11 "+"qwe" );
                }
                Log.e("mn", "onTextChanged: "+"qwe" );
            }
        });*/
        //软键盘的搜索点击时间
     /*   searchGuanjiaci.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //关闭软件盘
                    hideKeyboard(searchGuanjiaci);
                    mPresenter.getKeyWord(SearchResultActivity.this, user_id, user_token, searchGuanjiaci.getText().toString(), "all", "");
                }
                return false;
            }
        });*/

    }


    //网络请求成功的回调用户关注，
    @Override
    public void guanZhu(GuanZhuBean keywordBean) {
        int status = keywordBean.getData().getStatus();
        if (status == 1) {
            tv_guanzhu1.setText("已关注");
            tv_guanzhu1.setTextColor(Color.parseColor("#888888"));
            tv_guanzhu1.setBackgroundResource(R.drawable.orange_fillet_yiguanzhu);
        }
        if (status == 0) {
            tv_guanzhu1.setText("+关注");
            tv_guanzhu1.setBackgroundResource(R.drawable.orange_fillet);
            tv_guanzhu1.setTextColor(Color.parseColor("#ffffff"));
        }
    }

    //关键字搜索  网络返回的接口
    @SuppressLint("ResourceAsColor")
    @Override
    public void search(KeywordUserBean keywordBean) {
        autoSearch.setSelection(autoSearch.getText().length());
        //清空集合
        listBeans.clear();
        Log.e("search", "search: " + keywordBean.getData().getList().size());
            list = keywordBean.getData().getList();
            listBeans.addAll(list);
            vertical.setVisibility(View.GONE);
            v.setVisibility(View.VISIBLE);
            view1.setVisibility(View.GONE);
            view2.setVisibility(View.GONE);
            Lin.setVisibility(View.VISIBLE);
            listViewLv.setVisibility(View.VISIBLE);
            lv.setVisibility(View.GONE);
            lvUser.setVisibility(View.GONE);
            lvContent.setVisibility(View.GONE);
            tvSetname.setVisibility(View.GONE);
            name.setVisibility(View.GONE);
            content.setVisibility(View.GONE);
            hit.setVisibility(View.GONE);
            tvGson.setVisibility(View.GONE);
            tvGson1.setVisibility(View.GONE);

            listviewAdapter = new ListViewkeywordAdapter(SearchResultActivity.this, listBeans, autoSearch.getText().toString());
            listViewLv.setAdapter(listviewAdapter);
            canLinstener = true;

            listViewLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int type = list.get(position).getType();
                    switch (type) {
                        //个人信息
                        case 1:
                            Intent intent = new Intent(SearchResultActivity.this, UserHomepageActivity.class);
                            intent.putExtra("yo_user_id", list.get(position).getUser_id() + "");
                            startActivity(intent);
                            adapter.notifyDataSetChanged();
                            break;
                        //有记
                        case 2:
                            Intent intent1 = new Intent(SearchResultActivity.this, YoJiDetailActivity.class);
                            intent1.putExtra("yo_id", list.get(position).getYo_id());
                            startActivity(intent1);
                            break;
                        //有修
                        case 3:
                            Intent intent2 = new Intent(SearchResultActivity.this, YoXiuDetailActivity.class);
                            intent2.putExtra("id", list.get(position).getYo_id());
                            startActivity(intent2);
                            break;
                        //标签
                        case 4:
                            canLinstener = false;
                            autoSearch.setText(list.get(position).getLabel());
                            canLinstener = true;
                            mPresenter.getKeyWord(SearchResultActivity.this, user_id, user_token, listBeans.get(position).getLabel(), "all", listBeans.get(position).getKey_type());
                            break;
                        //定位
                        case 5:
                            canLinstener = false;
                            autoSearch.setText(list.get(position).getPosition_name());
                            canLinstener = true;
                            mPresenter.getKeyWord(SearchResultActivity.this, user_id, user_token, listBeans.get(position).getPosition_name(), "all", listBeans.get(position).getKey_type());
                            break;
                        //频道
                        case 6:
                            canLinstener = false;
                            autoSearch.setText(list.get(position).getChannel());
                            canLinstener = true;
                            mPresenter.getKeyWord(SearchResultActivity.this, user_id, user_token, listBeans.get(position).getChannel(), "all", listBeans.get(position).getKey_type());
                            break;
                        //搜索全部
                        case 7:
                            canLinstener = false;
                            autoSearch.setText(list.get(position).getChannel());
                            canLinstener = true;
                            mPresenter.getKeyWord(SearchResultActivity.this, user_id, user_token, autoSearch.getText().toString(), "all", "");
                            break;
                    }
                }
            });

            if (list.size() <= 0) {
                listviewAdapter.clearData();
                listviewAdapter.notifyDataSetChanged();
                Li.setVisibility(View.VISIBLE);
                listViewLv.setVisibility(View.GONE);
                tvSetname.setVisibility(View.GONE);
                hit.setVisibility(View.VISIBLE);
            }
        if (autoSearch.getText().toString().trim().equals("")){
            listviewAdapter.clearData();
            listviewAdapter.notifyDataSetChanged();
            vertical.setVisibility(View.VISIBLE);
        }
    }

/*
    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            // Clear the text filter.
            listViewLv.clearTextFilter();
        } else {
            // Sets the initial value for the text filter.
            listViewLv.setFilterText(newText.toString());
        }
        return false;
    }
*/

/*    @Override
    public boolean onQueryTextSubmit(String query) {
        // TODO Auto-generated method stub
        return false;
    }*/


    @Override
    public void keyWordMessage(KeywordBean keywordBean) {
        if (keywordBean == null || keywordBean.getData() == null || keywordBean.getData().getType() == null) {
            return;
        }
        if (keywordBean.getData().getType().equals("user")) {
            mUser.clear();
            vertical.setVisibility(View.GONE);
            tvSetname.setVisibility(View.VISIBLE);
            ivBack.setVisibility(View.GONE);
            user_list = keywordBean.getData().getUser_list();
            lv.setAdapter(adapter);
            mUser.addAll(user_list);
            name.setVisibility(View.GONE);
            lv.setVisibility(View.VISIBLE);
            lvContent.setVisibility(View.GONE);
            lvUser.setVisibility(View.GONE);
            hit.setVisibility(View.GONE);
            content.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            Log.e("zX", "keyWordMessage: "+user_list.size() );
            if (user_list.size() == 0) {
                Log.e("zX", "keyWordMessage: "+user_list.size() );
                hit.setVisibility(View.VISIBLE);
                lv.setVisibility(View.GONE);
                lvUser.setVisibility(View.GONE);
                lvContent.setVisibility(View.GONE);
            }
        }
        if (keywordBean.getData().getType().equals("yox")) {
            myox.clear();
            List<KeywordBean.DataBean.YoxListBean> yox_list = keywordBean.getData().getYox_list();
            myox.addAll(yox_list);
            lvContent.setAdapter(adapter1);
            adapter1.notifyDataSetChanged();
            vertical.setVisibility(View.GONE);
            tvSetname.setVisibility(View.VISIBLE);
            ivBack.setVisibility(View.GONE);
            lvUser.setVisibility(View.GONE);
            lv.setVisibility(View.GONE);
            content.setVisibility(View.GONE);
            name.setVisibility(View.GONE);
            hit.setVisibility(View.GONE);
            lvContent.setVisibility(View.VISIBLE);
            tvGson.setVisibility(View.GONE);
            tvGson1.setVisibility(View.GONE);
            if (myox.size() == 0){
                hit.setVisibility(View.VISIBLE);
                lv.setVisibility(View.GONE);
                lvUser.setVisibility(View.GONE);
                lvContent.setVisibility(View.GONE);
            }
        }
        if (keywordBean.getData().getType().equals("yoj")) {
            myoj.clear();
            List<KeywordBean.DataBean.YojListBean> yoj_list = keywordBean.getData().getYoj_list();
            myoj.addAll(yoj_list);
            lvUser.setAdapter(adapter3);
            adapter.notifyDataSetChanged();
            vertical.setVisibility(View.GONE);
            tvSetname.setVisibility(View.VISIBLE);
            ivBack.setVisibility(View.GONE);
            name.setVisibility(View.GONE);
            hit.setVisibility(View.GONE);
            content.setVisibility(View.GONE);
            lvUser.setVisibility(View.VISIBLE);
            lv.setVisibility(View.GONE);
            lvContent.setVisibility(View.GONE);
            tvGson.setVisibility(View.GONE);
            tvGson1.setVisibility(View.GONE);
            adapter3.setSetoncli(new SearchYoJiListHorizontalAdapter.setoncli() {
                @Override
                public void setoncli(int p) {
                    Intent intent = new Intent(SearchResultActivity.this, YoJiDetailActivity.class);
                    intent.putExtra("yo_id", myoj.get(p).getYo_id());
                    startActivity(intent);
                }

                @Override
                public void set(int position) {
                }
            });
            if (myoj.size() == 0){
                hit.setVisibility(View.VISIBLE);
                lv.setVisibility(View.GONE);
                lvUser.setVisibility(View.GONE);
                lvContent.setVisibility(View.GONE);
            }
        }
        if (keywordBean.getData().getType().equals("all")) {

            autoSearch.setSelection(autoSearch.getText().length());
            mUser.clear();
            myoj.clear();
            myox.clear();
            view1.setVisibility(View.GONE);
            view2.setVisibility(View.GONE);
            //显示全部展示全部数据 用户
            List<KeywordBean.DataBean.UserListBean> user_list1 = keywordBean.getData().getUser_list();
            mUser.addAll(user_list1);
            lv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            //yo秀
            List<KeywordBean.DataBean.YoxListBean> yox_list = keywordBean.getData().getYox_list();
            myox.addAll(yox_list);
            lvUser.setAdapter(adapter1);
            adapter1.notifyDataSetChanged();

            //yo纪
            List<KeywordBean.DataBean.YojListBean> yoj_list = keywordBean.getData().getYoj_list();
            myoj.addAll(yoj_list);
            lvContent.setAdapter(adapter3);
            adapter3.notifyDataSetChanged();
            adapter3.setSetoncli(new SearchYoJiListHorizontalAdapter.setoncli() {
                @Override
                public void setoncli(int p) {
                    Intent intent = new Intent(SearchResultActivity.this, YoJiDetailActivity.class);
                    intent.putExtra("yo_id", myoj.get(p).getYo_id());
                    startActivity(intent);
                }

                @Override
                public void set(int position) {
                }
            });
            tvSetname.setVisibility(View.VISIBLE);
            ivBack.setVisibility(View.GONE);
            vertical.setVisibility(View.GONE);
            Lin.setVisibility(View.VISIBLE);
            if (mUser.size() <= 0) {
                if (myox.size() <= 0) {
                    if (myoj.size() <= 0) {
                        hit.setVisibility(View.VISIBLE);
                        tvGson.setVisibility(View.GONE);
                        tvGson1.setVisibility(View.GONE);
                        listViewLv.setVisibility(View.GONE);
                        name.setVisibility(View.GONE);
                        content.setVisibility(View.GONE);

                    } else {
                        hit.setVisibility(View.GONE);
                        name.setVisibility(View.VISIBLE);
                        lv.setVisibility(View.VISIBLE);
                        lvUser.setVisibility(View.VISIBLE);
                        lvContent.setVisibility(View.VISIBLE);
                        content.setVisibility(View.VISIBLE);
                        tvGson.setVisibility(View.GONE);
                        tvGson1.setVisibility(View.GONE);
                        listViewLv.setVisibility(View.GONE);
                        tvSetname.setVisibility(View.VISIBLE);

                    }
                } else {
                    hit.setVisibility(View.GONE);
                    name.setVisibility(View.GONE);
                    lv.setVisibility(View.VISIBLE);
                    lvUser.setVisibility(View.VISIBLE);
                    lvContent.setVisibility(View.VISIBLE);
                    content.setVisibility(View.VISIBLE);
                    tvGson.setVisibility(View.GONE);
                    tvGson1.setVisibility(View.GONE);
                    listViewLv.setVisibility(View.GONE);
                    tvSetname.setVisibility(View.VISIBLE);
                }
            } else {
                Lin.setVisibility(View.VISIBLE);
                hit.setVisibility(View.GONE);
                name.setVisibility(View.VISIBLE);
                lv.setVisibility(View.VISIBLE);
                lvUser.setVisibility(View.VISIBLE);
                lvContent.setVisibility(View.VISIBLE);
                content.setVisibility(View.VISIBLE);
                tvGson.setVisibility(View.GONE);
                tvGson1.setVisibility(View.GONE);
                listViewLv.setVisibility(View.GONE);
                tvSetname.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param :    Context上下文环境，一般为Activity实例
     * @param view :一般为EditText
     */
    public static void hideKeyboard(View view) {
        InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
