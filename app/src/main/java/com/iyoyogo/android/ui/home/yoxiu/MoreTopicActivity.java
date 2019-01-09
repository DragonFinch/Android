package com.iyoyogo.android.ui.home.yoxiu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.topic.HotTopicAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.yoxiu.topic.CreateTopicBean;
import com.iyoyogo.android.bean.yoxiu.topic.HotTopicBean;
import com.iyoyogo.android.contract.MoreTopicContract;
import com.iyoyogo.android.presenter.MoreTopicPresenter;
import com.iyoyogo.android.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 更多话题
 */
public class MoreTopicActivity extends BaseActivity<MoreTopicContract.Presenter> implements MoreTopicContract.View {

    @BindView(R.id.location_edit)
    EditText locationEdit;
    @BindView(R.id.location_cancelTV_id)
    TextView locationCancelTVId;
    @BindView(R.id.location_rl)
    RelativeLayout locationRl;
    @BindView(R.id.recycler_topic_more)
    RecyclerView recyclerTopicMore;
    @BindView(R.id.recycler_topic_near)
    RecyclerView recyclerTopicNear;
    @BindView(R.id.tv_create_message)
    TextView tvCreateMessage;
    @BindView(R.id.tv_create)
    TextView tvCreate;
    @BindView(R.id.rela_create)
    RelativeLayout relaCreate;
    @BindView(R.id.img_hot)
    ImageView imgHot;
    @BindView(R.id.tv_message_more)
    TextView tvMessageMore;
    @BindView(R.id.relative_more)
    RelativeLayout relativeMore;
    @BindView(R.id.img_near)
    ImageView imgNear;
    @BindView(R.id.tv_message_near)
    TextView tvMessageNear;
    @BindView(R.id.clear)
    TextView clear;
    @BindView(R.id.relative_near)
    RelativeLayout relativeNear;
    @BindView(R.id.clear_search)
    ImageView clearSearch;
    private String user_token;
    private String user_id;
    private List<String> list;
    private HotTopicAdapter adapter;
    private List<HotTopicBean.DataBean.ListBean> mList;
    private List<Integer> type_list;
    private Integer[] array;
    private int yo_types;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_more_topic;

    }

    @Override
    protected void initBeforeView() {
        super.initBeforeView();

    }


    @Override
    protected void initView() {
        super.initView();
        list = new ArrayList<>();

        locationEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    locationEdit.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void doChangeColor(String text) {
        //clear是必须的 不然只要改变edittext数据，list会一直add数据进来
        list.clear();
        //不需要匹配 把所有数据都传进来 不需要变色
        if (text.equals("")) {
            for (int i = 0; i < mList.size(); i++) {
                list.add(mList.get(i).getTopic());
            }

            //防止匹配过文字之后点击删除按钮 字体仍然变色的问题
            adapter.setText(null);
        } else {
            //如果edittext里面有数据 则根据edittext里面的数据进行匹配 用contains判断是否包含该条数据 包含的话则加入到list中
            for (int i = 0; i < mList.size(); i++) {
                list.add(mList.get(i).getTopic());
            }

            //设置要变色的关键字
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            adapter.setText(text);
            refreshUI();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void refreshUI() {
        if (adapter == null) {

        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void searchLocationPoi(CharSequence s, List<HotTopicBean.DataBean.ListBean> mList) {
        //关闭键盘
//        KeyBoardUtils.closeKeybord(poiSearchInMaps, BaseApplication.mContext);


        adapter = new HotTopicAdapter(MoreTopicActivity.this, mList);
                        /*if (datas!=null){
                            mapAddAddress.setVisibility(View.GONE);
                            recyclerAddAddress.setVisibility(View.VISIBLE);
                        }else {
                            mapAddAddress.setVisibility(View.VISIBLE);
                            recyclerAddAddress.setVisibility(View.GONE);
                        }*/

        recyclerTopicMore.setLayoutManager(new LinearLayoutManager(MoreTopicActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        recyclerTopicMore.setAdapter(adapter);
        doChangeColor(s.toString().trim());


    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Intent intent = getIntent();
        yo_types = intent.getIntExtra("type", 0);
        Log.d("MoreTopicActivity", "sadasdasdasda" + yo_types + "");
        mList = new ArrayList<>();
        type_list = new ArrayList<>();
        user_id = SpUtils.getString(MoreTopicActivity.this, "user_id", null);
        user_token = SpUtils.getString(MoreTopicActivity.this, "user_token", null);

        if (locationEdit.getText().toString() != null) {
            mPresenter.getHotTopic(user_id, user_token, locationEdit.getText().toString());
        } else {

            mPresenter.getHotTopic(user_id, user_token, "");

        }
        mPresenter.getNearTopic(user_id, user_token);


    }

    @Override
    protected MoreTopicContract.Presenter createPresenter() {
        return new MoreTopicPresenter(this);
    }


    @Override
    public void getHotTopicSuccess(List<HotTopicBean.DataBean.ListBean> list) {


        HotTopicBean.DataBean.ListBean listBean = new HotTopicBean.DataBean.ListBean();
        mList.addAll(list);
        Log.d("MoreTopicActivity", "mList.size():" + mList.size());

        adapter = new HotTopicAdapter(MoreTopicActivity.this, mList);
        recyclerTopicMore.setLayoutManager(new LinearLayoutManager(MoreTopicActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        recyclerTopicMore.setAdapter(adapter);
        locationEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() != 0) {
                    relaCreate.setVisibility(View.VISIBLE);
                    clearSearch.setVisibility(View.VISIBLE);
                    adapter.setSearchContent(s.toString().trim());
                    tvCreateMessage.setText(s.toString().trim());
                    tvCreateMessage.setTextColor(Color.parseColor("#FA800A"));
                    relativeMore.setVisibility(View.GONE);
                    relativeNear.setVisibility(View.GONE);
                    searchLocationPoi(s, mList);
                    recyclerTopicMore.setVisibility(View.GONE);
                    recyclerTopicNear.setVisibility(View.GONE);
                } else {
                    relaCreate.setVisibility(View.GONE);
                    relativeMore.setVisibility(View.VISIBLE);
                    relativeNear.setVisibility(View.VISIBLE);
                    clearSearch.setVisibility(View.GONE);
                    recyclerTopicMore.setVisibility(View.VISIBLE);
                    recyclerTopicNear.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {

                }
            }
        });

        adapter.setOnClickListener(new HotTopicAdapter.OnClickListener() {
            @Override
            public void setOnClickListener(View view, int position) {

                String topic = mList.get(position).getTopic();
                int id = mList.get(position).getId();

                Intent intent = new Intent();
                intent.putExtra("topic", topic);
                intent.putExtra("type_id", id);
                setResult(6, intent);
                finish();

            }
        });
    }

    @Override
    public void getNearTopicSuccess(List<HotTopicBean.DataBean.ListBean> list) {
        List<HotTopicBean.DataBean.ListBean> mList = new ArrayList<>();
        HotTopicBean.DataBean.ListBean listBean = new HotTopicBean.DataBean.ListBean();
        mList.addAll(list);
        Log.d("MoreTopicActivity", "mList.size():" + mList.size());
        if (list.size() == 0) {
            recyclerTopicNear.setVisibility(View.GONE);
            relativeNear.setVisibility(View.GONE);
        }
        HotTopicAdapter hotTopicAdapter = new HotTopicAdapter(MoreTopicActivity.this, mList);
        recyclerTopicNear.setLayoutManager(new LinearLayoutManager(MoreTopicActivity.this));
        recyclerTopicNear.setAdapter(hotTopicAdapter);
        hotTopicAdapter.setOnClickListener(new HotTopicAdapter.OnClickListener() {
            @Override
            public void setOnClickListener(View view, int position) {


                String topic = mList.get(position).getTopic();
                int id = mList.get(position).getId();

                Intent intent = new Intent();
                intent.putExtra("topic", topic);
                intent.putExtra("type_id", id);
                setResult(6, intent);
                finish();

            }
        });
    }

    @Override
    public void getClearTopicSuccess() {
        Toast.makeText(this, "清空成功", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void createTopicSuccess(CreateTopicBean.DataBean data) {
        //TODO
        String topic_id = data.getTopic_id();
        int i = Integer.parseInt(topic_id);
        String trim = locationEdit.getText().toString().trim();
        Intent intent = new Intent();
        intent.putExtra("topic", trim);
        intent.putExtra("type_id", i);
//        setResult(5, intent);
//        finish();
        Toast.makeText(this, "创建成功", Toast.LENGTH_SHORT).show();
    }


    @OnClick({R.id.tv_create, R.id.clear_search, R.id.location_cancelTV_id, R.id.clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_create:
                Intent intent = new Intent();
                intent.putExtra("topic", locationEdit.getText().toString().trim());
                intent.putExtra("type_id", 1);
                setResult(6, intent);
                finish();
//                mPresenter.getCreateTopic(user_id, user_token, locationEdit.getText().toString());
                break;
            case R.id.clear:
                mPresenter.getClearTopic(user_id, user_token);
                break;
            case R.id.clear_search:
                locationEdit.setText("");
                clearSearch.setVisibility(View.GONE);
                break;
            case R.id.location_cancelTV_id:
                finish();
                break;
        }
    }
}
