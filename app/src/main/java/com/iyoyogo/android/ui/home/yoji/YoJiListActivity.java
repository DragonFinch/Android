package com.iyoyogo.android.ui.home.yoji;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.YoJiAdapter;
import com.iyoyogo.android.adapter.YoJiListAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class YoJiListActivity extends BaseActivity {

    @BindView(R.id.recycler_yoji_list_two)
    RecyclerView recyclerYojiListTwo;
    private boolean isVertical = true;
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
    private List<String> imageData;
    private YoJiListAdapter yoJiListAdapter;
    private YoJiAdapter yoJiAdapter;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);


        imageData = new ArrayList<>();
        imageData.add("http://t2.hddhhn.com/uploads/tu/201702/132/st5.png");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201701/323/st1.png");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201701/91/st3.png");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201612/429/st1.png");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201610/141/st6.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201708/760/18a21a92591.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        imageData.add("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        initPraises();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_yo_ji_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<String> mList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mList.add("");
        }
        if (isVertical){
            recyclerYojiListTwo.setVisibility(View.GONE);
            recyclerYojiList.setVisibility(View.VISIBLE);
        }else {
            recyclerYojiListTwo.setVisibility(View.VISIBLE);
            recyclerYojiList.setVisibility(View.GONE);
        }
        yoJiAdapter = new YoJiAdapter(YoJiListActivity.this, mList);
        yoJiListAdapter = new YoJiListAdapter(YoJiListActivity.this, mList);
        recyclerYojiList.setLayoutManager(new LinearLayoutManager(YoJiListActivity.this));
        recyclerYojiList.setAdapter(yoJiAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerYojiListTwo.setLayoutManager(staggeredGridLayoutManager);
        recyclerYojiListTwo.setAdapter(yoJiListAdapter);
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
                if (isVertical){
                    imgReplace.setImageResource(R.mipmap.view1);
                    recyclerYojiListTwo.setVisibility(View.GONE);
                    recyclerYojiList.setVisibility(View.VISIBLE);
                    isVertical=false;
                }else {
                    imgReplace.setImageResource(R.mipmap.view2);
                    recyclerYojiListTwo.setVisibility(View.VISIBLE);
                    recyclerYojiList.setVisibility(View.GONE);
                    isVertical=true;
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
