package com.iyoyogo.android.ui.home.yoji;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.YoJiListHorizontalAdapter;
import com.iyoyogo.android.adapter.YoJiAdapter;
import com.iyoyogo.android.adapter.YoJiListAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.bean.yoji.list.YoJiListBean;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.utils.SpUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class YoJiListActivity extends BaseActivity {

    @BindView(R.id.recycler_yoji_list_two)
    RecyclerView recyclerYojiListTwo;
    private int num = 1;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.img_replace)
    ImageView imgReplace;
    @BindView(R.id.bar)
    RelativeLayout bar;
    @BindView(R.id.recycler_yoji_list)
    RecyclerView recyclerYojiList;
    private YoJiListAdapter yoJiListAdapter;
    private YoJiAdapter yoJiAdapter;
    boolean isVertical;
    private YoJiListHorizontalAdapter yoJiListHorizontalAdapter;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        String user_id = SpUtils.getString(getApplicationContext(), "user_id", null);
        String user_token = SpUtils.getString(getApplicationContext(), "user_token", null);
        DataManager.getFromRemote()
                .getYoJiList(user_id, user_token, 1, 20)
                .subscribe(new Observer<YoJiListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(YoJiListBean yoJiListBean) {
                        List<YoJiListBean.DataBean.ListBean> mList = yoJiListBean.getData().getList();
                        yoJiListHorizontalAdapter = new YoJiListHorizontalAdapter(YoJiListActivity.this, mList);
                        yoJiListAdapter = new YoJiListAdapter(YoJiListActivity.this, mList);
                        //横向
                        Log.e("123", "recyclerYojiList" + "isVertical:" + isVertical);
                        recyclerYojiList.setVisibility(View.VISIBLE);
                        recyclerYojiList.setLayoutManager(new LinearLayoutManager(YoJiListActivity.this));
            recyclerYojiList.setAdapter(yoJiListHorizontalAdapter);
                        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                        recyclerYojiListTwo.setLayoutManager(staggeredGridLayoutManager);
                        recyclerYojiListTwo.setAdapter(yoJiListAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        shortToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_yo_ji_list;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void initView() {
        super.initView();
        statusbar();
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    public void initPraises() {
       /* LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < imageData.size(); i++) {
            CircleImageView imageView = (CircleImageView) inflater.inflate(R.layout.item_head_image, pileLayout, false);
            Glide.with(this).load(imageData.get(i)).into(imageView);
            imageView.setId(i);
            // 为item绑定数据
            imageView.setTag(imageData.get(i));
            // 为item设置点击事件
            imageView.setOnClickListener(this);
            pileLayout.addView(imageView);*/


    }


    @OnClick({R.id.back, R.id.img_replace})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.img_replace:
                num++;
                if (num % 2 == 0) {
                    imgReplace.setImageResource(R.mipmap.view2);
                    recyclerYojiList.setVisibility(View.GONE);
                    recyclerYojiListTwo.setVisibility(View.VISIBLE);
                } else if (num % 2 == 1) {
                    imgReplace.setImageResource(R.mipmap.view1);
                    recyclerYojiList.setVisibility(View.VISIBLE);
                    recyclerYojiListTwo.setVisibility(View.GONE);
                }


                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
