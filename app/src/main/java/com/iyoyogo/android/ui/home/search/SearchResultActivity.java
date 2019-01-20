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
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.SearchUserAdapter;
import com.iyoyogo.android.adapter.SearchYoXiuListAdapter;
import com.iyoyogo.android.adapter.search.ListViewkeywordAdapter;
import com.iyoyogo.android.adapter.search.SearchYoJiListHorizontalAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.search.GuanZhuBean;
import com.iyoyogo.android.bean.search.KeywordBean;
import com.iyoyogo.android.bean.search.KeywordUserBean;
import com.iyoyogo.android.contract.KeywordContract;
import com.iyoyogo.android.presenter.KeywordPresenter;
import com.iyoyogo.android.ui.home.yoji.YoJiDetailActivity;
import com.iyoyogo.android.ui.home.yoxiu.YoXiuDetailActivity;
import com.iyoyogo.android.ui.mine.homepage.Personal_homepage_Activity;
import com.iyoyogo.android.utils.SpUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Name: SearchResultActivity
 *
 * @author: dengyalan
 * Date: 2018-05-05 09:33
 */
public class SearchResultActivity extends BaseActivity<KeywordContract.Presenter> implements View.OnClickListener, KeywordContract.View, SearchView.OnQueryTextListener {

    @BindView(R.id.cancel)
    TextView cancel;
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
    @BindView(R.id.search_guanjiaci)
    EditText searchGuanjiaci;
    @BindView(R.id.tv_gson)
    TextView tvGson;
    @BindView(R.id.tv_gson1)
    TextView tvGson1;
    @BindView(R.id.view)
    View view;
    private PopupWindow popupWindow;
    private List<KeywordBean.DataBean.UserListBean> mUser = new ArrayList<>();
    private List<KeywordBean.DataBean.YojListBean> myoj = new ArrayList<>();
    private List<KeywordBean.DataBean.YoxListBean> myox = new ArrayList<>();
    private List<KeywordBean.DataBean> mdata = new ArrayList<>();
    private String user_id;
    private String user_token;
    private String keyword;
    private SearchUserAdapter adapter = new SearchUserAdapter(SearchResultActivity.this, mUser, mdata);
    private SearchYoJiListHorizontalAdapter adapter3 = new SearchYoJiListHorizontalAdapter(SearchResultActivity.this, myoj);
    private SearchYoXiuListAdapter adapter1 = new SearchYoXiuListAdapter(SearchResultActivity.this, myox);
    private String s;
    private List<KeywordBean.DataBean.UserListBean> user_list;
    private List<KeywordUserBean.DataBean.ListBean> listBeans = new ArrayList<>();
    private List<KeywordUserBean.DataBean.ListBean> list;
    private TextView tv_guanzhu1;
    boolean fig = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        String key = getIntent().getStringExtra("key");
        lv.setLayoutManager(new LinearLayoutManager(this));
        lvUser.setLayoutManager(new LinearLayoutManager(this));
        lvContent.setLayoutManager(new LinearLayoutManager(this));

            Intent in = getIntent();
            keyword = in.getStringExtra("key");
            if (keyword != null){
                //放到网络请求上面
                searchGuanjiaci.setText(keyword);
            }

        searchGuanjiaci.setSelection(searchGuanjiaci.getText().length());

        mPresenter.getKeyWord(SearchResultActivity.this,user_id, user_token, keyword, "all", "");

        tvSetname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出pop
                init();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
        return new KeywordPresenter(SearchResultActivity.this,this);
    }

    @Override
    protected void initView() {
        super.initView();
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);

    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        int i = 0;
        super.initData(savedInstanceState);
        user_id = SpUtils.getString(SearchResultActivity.this, "user_id", null);
        user_token = SpUtils.getString(SearchResultActivity.this, "user_token", null);
        searchGuanjiaci.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    if (fig) {
                        mPresenter.getSearch(SearchResultActivity.this,user_id, user_token, s.toString());
                        Log.e("xczv,lm", "afterTextChanged: "+user_id+"user_token"+user_token+s.toString() );
                        cancel.setVisibility(View.VISIBLE);
                    } else {
                        fig = true;
                    }
                }
            }
        });
        //软键盘的搜索点击时间
        searchGuanjiaci.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //关闭软件盘
                    hideKeyboard(searchGuanjiaci);
                    mPresenter.getKeyWord(SearchResultActivity.this,user_id, user_token, searchGuanjiaci.getText().toString(), "all", "");
                }
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_all:
                tvSetname.setText("全部");
                popupWindow.dismiss();
                //切换进行网络请求   调用BaseActivit
                mPresenter.getKeyWord(SearchResultActivity.this,user_id, user_token, keyword, "all", "");
                break;
            case R.id.youji:
                tvSetname.setText("yo记");
                popupWindow.dismiss();
                mPresenter.getKeyWord(SearchResultActivity.this,user_id, user_token, keyword, "yoj", "");
                break;
            case R.id.yoxiu:
                tvSetname.setText("yo秀");
                popupWindow.dismiss();
                mPresenter.getKeyWord(SearchResultActivity.this,user_id, user_token, keyword, "yox", "");
                break;
            case R.id.user:
                tvSetname.setText("用户");
                popupWindow.dismiss();
                mPresenter.getKeyWord(SearchResultActivity.this,user_id, user_token, keyword, "user", "");
                break;

        }
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
        //清空集合
        listBeans.clear();
        Log.e("search", "search: " + keywordBean.getData().getList().size());
        if (keywordBean.getData().getList() != null) {
            list = keywordBean.getData().getList();
            listBeans.addAll(list);
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
            ListViewkeywordAdapter adapter = new ListViewkeywordAdapter(SearchResultActivity.this, listBeans, searchGuanjiaci.getText().toString());
            listViewLv.setAdapter(adapter);
            listViewLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int type = list.get(position).getType();
                    switch (type) {
                        //个人信息
                        case 1:
                            Intent intent = new Intent(SearchResultActivity.this, Personal_homepage_Activity.class);
                            intent.putExtra("yo_user_id", list.get(position).getUser_id() + "");
                            startActivity(intent);
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
                            mPresenter.getKeyWord(SearchResultActivity.this,user_id, user_token, listBeans.get(position).getLabel(), "all", listBeans.get(position).getKey_type());
                            break;
                        //定位
                        case 5:
                            mPresenter.getKeyWord(SearchResultActivity.this,user_id, user_token, listBeans.get(position).getPosition_name(), "all", listBeans.get(position).getKey_type());
                            break;
                        //频道
                        case 6:
                            mPresenter.getKeyWord(SearchResultActivity.this,user_id, user_token, listBeans.get(position).getChannel(), "all", listBeans.get(position).getKey_type());
                            break;
                        //搜索全部
                        case 7:
                            mPresenter.getKeyWord(SearchResultActivity.this,user_id, user_token, searchGuanjiaci.getText().toString(), "all", "");
                            break;
                    }
                }
            });

            if (list.size() == 0) {
                tvSetname.setVisibility(View.VISIBLE);
                hit.setVisibility(View.VISIBLE);
                Toast.makeText(SearchResultActivity.this, "没有匹配到您要查询的关键字", Toast.LENGTH_SHORT).show();
            }
        }
    }

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

    @Override
    public boolean onQueryTextSubmit(String query) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public void keyWordMessage(KeywordBean keywordBean) {
        if(keywordBean==null || keywordBean.getData()==null || keywordBean.getData().getType()==null){return;}
        if (keywordBean.getData().getType().equals("user")) {
            mUser.clear();
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
            if (user_list.size() <= 0) {
                hit.setVisibility(View.VISIBLE);
                lv.setVisibility(View.GONE);
                lvUser.setVisibility(View.GONE);
                lvContent.setVisibility(View.GONE);
                cancel.setVisibility(View.VISIBLE);
            }
        }
        if (keywordBean.getData().getType().equals("yox")) {
            myox.clear();
            List<KeywordBean.DataBean.YoxListBean> yox_list = keywordBean.getData().getYox_list();
            myox.addAll(yox_list);
            lvContent.setAdapter(adapter1);
            adapter1.notifyDataSetChanged();
            lvUser.setVisibility(View.GONE);
            lv.setVisibility(View.GONE);
            content.setVisibility(View.GONE);
            name.setVisibility(View.GONE);
            hit.setVisibility(View.GONE);
            lvContent.setVisibility(View.VISIBLE);
            tvGson.setVisibility(View.GONE);
            tvGson1.setVisibility(View.GONE);
        }
        if (keywordBean.getData().getType().equals("yoj")) {
            myoj.clear();
            List<KeywordBean.DataBean.YojListBean> yoj_list = keywordBean.getData().getYoj_list();
            myoj.addAll(yoj_list);
            lvUser.setAdapter(adapter3);
            adapter.notifyDataSetChanged();
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
        }
        if (keywordBean.getData().getType().equals("all")) {

            mUser.clear();
            myoj.clear();
            myox.clear();

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

            if (mUser.size() <=0){
                if (myox.size() <=0){
                    if (myoj.size() <=0){
                        hit.setVisibility(View.VISIBLE);
                        tvGson.setVisibility(View.GONE);
                        tvGson1.setVisibility(View.GONE);
                        name.setVisibility(View.GONE);
                        content.setVisibility(View.GONE);

                    }else{
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
                }else{
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
            }else{
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

    @Override
    protected void onResume() {
        super.onResume();
        // mPresenter.getKeyWord(user_id,user_token,keyword,"all");
        // mPresenter.getSearch(user_id,user_token,s.toString());
    }
}
