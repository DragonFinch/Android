package com.iyoyogo.android.ui.home.search;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.iyoyogo.android.ui.home.yoji.UserHomepageActivity;
import com.iyoyogo.android.ui.home.yoji.YoJiDetailActivity;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.search.SharedPrefrenceUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    /*   @BindView(R.id.table)
       TextView table;*/
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
    android.support.v7.widget.SearchView searchGuanjiaci;
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
    private TextView houle1;
    private List<KeywordBean.DataBean.UserListBean> user_list;
    private List<KeywordUserBean.DataBean.ListBean> listBeans = new ArrayList<>();
    private ListViewkeywordAdapter searchadapter;
    private int user_id1;
    private TextView tv_guanzhu1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
   /*     lv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                sl.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });*/

        lv.setLayoutManager(new LinearLayoutManager(this));
        lvUser.setLayoutManager(new LinearLayoutManager(this));
        lvContent.setLayoutManager(new LinearLayoutManager(this));
        if (getIntent() != null) {
            Intent in = getIntent();
            keyword = in.getStringExtra("key");
            //放到网络请求上面
            //2searchGuanjiaci.setText(keyword);
            // table.setQueryHint(keyword);

        }
     /*   List<KeywordBean.DataBean.UserListBean> user1 = SharedPrefrenceUtils.getSerializableList(SearchResultActivity.this, "user111");
        if (user1 != null) {
            List<KeywordBean.DataBean.UserListBean> listBeans = new ArrayList<>();
            for (int i = 0; i < user1.size(); i++) {
                listBeans.addAll(user1);
            }

            adapter.notifyDataSetChanged();
            lv.setVisibility(View.VISIBLE);
            lvUser.setVisibility(View.GONE);
            lvContent.setVisibility(View.GONE);

            Log.e("Tafff", "onCreate: " + "qweqeqe1111111111111" + listBeans.size());
        } else {
            Log.e("Tafff", "onCreate: " + "qweqeqe");*/
        mPresenter.getKeyWord(user_id, user_token, keyword, "user");
        //}

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

                Toast.makeText(SearchResultActivity.this, "qweqwe", Toast.LENGTH_SHORT).show();
                s = tv_guanzhu1.getText().toString();

                for (int i = 0; i < mUser.size(); i++) {
                    user_id1 = mUser.get(po).getUser_id();
                }
                if (s.equals("+关注")) {
                    mPresenter.getGuanZhu(user_id, user_token, user_id1 + "");
                }
                if (s.equals("已关注")) {
                    mPresenter.getGuanZhu(user_id, user_token, user_id1 + "");
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void Token(TextView tv_guanzhu){
        tv_guanzhu1 = tv_guanzhu;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
        return new KeywordPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        user_id = SpUtils.getString(SearchResultActivity.this, "user_id", null);
        user_token = SpUtils.getString(SearchResultActivity.this, "user_token", null);
        searchGuanjiaci.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.e("beforeTextChanged", "beforeTextChange3333333333333333333333333333d: " + s.toString());
                if (s != null && s.length() >= 0) {
                    mPresenter.getSearch(user_id, user_token, s.toString());
                    cancel.setVisibility(View.VISIBLE);
                } else {
                    searchGuanjiaci.setQueryHint("请输入你要查询的关键字");
                }
                return true;
            }
        });

 /*       searchGuanjiaci.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Toast.makeText(SearchResultActivity.this, "qwe", Toast.LENGTH_SHORT).show();
                Log.e("afterTextChanged", "afterTextChanged: " + s);
                mPresenter.getSearch(user_id, user_token, s + "");
                return true;
            }
        });*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_all:
                tvSetname.setText("全部");
                popupWindow.dismiss();
                //切换进行网络请求   调用BaseActivity
          /*      List<KeywordBean.DataBean.UserListBean> list = SharedPrefrenceUtils.getSerializableList(SearchResultActivity.this, "all");
                List<KeywordBean.DataBean.YojListBean> yoji = SharedPrefrenceUtils.getSerializableList(SearchResultActivity.this, "yoji");
                List<KeywordBean.DataBean.YoxListBeanX> yoxiu = SharedPrefrenceUtils.getSerializableList(SearchResultActivity.this, "yoxiu");
                List<KeywordBean.DataBean.UserListBean> listBeans = new ArrayList<>();
                List<KeywordBean.DataBean.YojListBean> yojListBeans = new ArrayList<>();
                List<KeywordBean.DataBean.YoxListBeanX> yoxListBeanXES = new ArrayList<>();
                if (list != null) {
                    if (yoji != null) {
                        if (yoxiu != null) {
                            for (int i = 0; i < list.size(); i++) {
                                listBeans.addAll(list);
                                adapter.notifyDataSetChanged();
                            }
                            for (int i = 0; i < yoji.size(); i++) {
                                yojListBeans.addAll(yoji);
                                adapter1.notifyDataSetChanged();
                            }
                            for (int i = 0; i < yoxiu.size(); i++) {
                                yoxListBeanXES.addAll(yoxiu);
                                adapter3.notifyDataSetChanged();
                            }

                        }
                    }
                } else {*/
                mPresenter.getKeyWord(user_id, user_token, keyword, "all");
                //   }

                break;
            case R.id.youji:
                Toast.makeText(SearchResultActivity.this, "正在开发中", Toast.LENGTH_SHORT).show();
                tvSetname.setText("yo记");
                popupWindow.dismiss();
                mPresenter.getKeyWord(user_id, user_token, keyword, "yoj");
                break;
            case R.id.yoxiu:
                tvSetname.setText("yo秀");
                popupWindow.dismiss();
                mPresenter.getKeyWord(user_id, user_token, keyword, "yox");
                break;
            case R.id.user:
                tvSetname.setText("用户");
                popupWindow.dismiss();
                mPresenter.getKeyWord(user_id, user_token, keyword, "user");
                break;

        }
    }

    //网络请求成功的回调，
    @Override
    public void guanZhu(GuanZhuBean keywordBean) {
        int status = keywordBean.getData().getStatus();
        if (status == 1) {
            Log.e("qq", "guanZhu: "+status+"" );
           // mPresenter.getKeyWord(user_id,user_token,keyword,"user");
            tv_guanzhu1.setText("+关注");
            // houle1.setText("已关注");
        }
        if (status == 0) {
             //mPresenter.getKeyWord(user_id,user_token,keyword,"user");
            //houle1.setText("+关注");
            tv_guanzhu1.setText("已关注");
        }
    }

    //关键字搜索  网络返回的接口
    @Override
    public void search(KeywordUserBean keywordBean) {
        listBeans.clear();
        if (keywordBean.getData().getList() != null) {
            Log.e("关键字搜索", "search: " + keywordBean.getData().getList().size());
            List<KeywordUserBean.DataBean.ListBean> list = keywordBean.getData().getList();
            listBeans.addAll(list);
            listViewLv.setVisibility(View.VISIBLE);
            lv.setVisibility(View.GONE);
            lvUser.setVisibility(View.GONE);
            lvContent.setVisibility(View.GONE);
            tvSetname.setVisibility(View.GONE);
            ListViewkeywordAdapter adapter = new ListViewkeywordAdapter(SearchResultActivity.this, listBeans);
            listViewLv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
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
            Log.e("keyWordMessage", "keyWordMessage: " + mUser.size());
            if (user_list.size() <= 0) {
                hit.setVisibility(View.VISIBLE);
                lv.setVisibility(View.GONE);
                lvUser.setVisibility(View.GONE);
                lvContent.setVisibility(View.GONE);
            }

//            SharedPrefrenceUtils.putSerializableList(SearchResultActivity.this,"user111",mUser);
        }
        if (keywordBean.getData().getType().equals("yox")) {
            myox.clear();
            List<KeywordBean.DataBean.YoxListBean> yox_list = keywordBean.getData().getYox_list();
            myox.addAll(yox_list);
            SearchYoXiuListAdapter adapter = new SearchYoXiuListAdapter(SearchResultActivity.this, myox);
            lvContent.setAdapter(adapter);
            for (int i = 0; i < myox.size(); i++) {
                myox.remove(i);
            }
            myox.addAll(yox_list);
            adapter.notifyDataSetChanged();
            lvUser.setVisibility(View.GONE);
            lv.setVisibility(View.GONE);
            content.setVisibility(View.GONE);
            name.setVisibility(View.GONE);
            hit.setVisibility(View.GONE);
            lvContent.setVisibility(View.VISIBLE);
        }
        if (keywordBean.getData().getType().equals("yoj")) {
            myoj.clear();
            List<KeywordBean.DataBean.YojListBean> yoj_list = keywordBean.getData().getYoj_list();
            myoj.addAll(yoj_list);
            SearchYoJiListHorizontalAdapter adapter = new SearchYoJiListHorizontalAdapter(SearchResultActivity.this, myoj);
            lvUser.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            name.setVisibility(View.GONE);
            hit.setVisibility(View.GONE);
            content.setVisibility(View.GONE);
            lvUser.setVisibility(View.VISIBLE);
            lv.setVisibility(View.GONE);
            lvContent.setVisibility(View.GONE);
            adapter.setSetoncli(new SearchYoJiListHorizontalAdapter.setoncli() {
                @Override
                public void setoncli(int p) {
                    Intent intent = new Intent(SearchResultActivity.this, YoJiDetailActivity.class);
                    intent.putExtra("yo_id", myoj.get(p).getYo_id());
                    startActivity(intent);
                }

                @Override
                public void set(int position) {
                    Intent intent = new Intent(SearchResultActivity.this, UserHomepageActivity.class);
                    intent.putExtra("yo_user_id", mUser.get(position).getUser_id() + "");
                    startActivity(intent);
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
            SearchUserAdapter adapter = new SearchUserAdapter(SearchResultActivity.this, mUser, mdata);
            lv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            SharedPrefrenceUtils.putSerializableList(SearchResultActivity.this, "all", mUser);
            //优秀
            List<KeywordBean.DataBean.YoxListBean> yox_list = keywordBean.getData().getYox_list();
            myox.addAll(yox_list);
            lvUser.setAdapter(adapter1);
            adapter1.notifyDataSetChanged();
            SharedPrefrenceUtils.putSerializableList(SearchResultActivity.this, "yoji", myox);
            //有机
            List<KeywordBean.DataBean.YojListBean> yoj_list = keywordBean.getData().getYoj_list();
            myoj.addAll(yoj_list);
            lvContent.setAdapter(adapter3);
            adapter3.notifyDataSetChanged();
            name.setVisibility(View.VISIBLE);
            lv.setVisibility(View.VISIBLE);
            lvUser.setVisibility(View.VISIBLE);
            lvContent.setVisibility(View.VISIBLE);
            content.setVisibility(View.VISIBLE);
            hit.setVisibility(View.GONE);
            Log.e("keyWordMessage", "keyWordMessage: " + yoj_list.size());
        }
    }
}