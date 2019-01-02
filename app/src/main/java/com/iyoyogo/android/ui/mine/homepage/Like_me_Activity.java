package com.iyoyogo.android.ui.mine.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.MineLikeAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.mine.PraiseBean;
import com.iyoyogo.android.contract.MinePraiseContract;
import com.iyoyogo.android.presenter.MinePraisePresenter;
import com.iyoyogo.android.ui.home.yoji.YoJiDetailActivity;
import com.iyoyogo.android.ui.home.yoxiu.SourceChooseActivity;
import com.iyoyogo.android.ui.home.yoxiu.YoXiuDetailActivity;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.StatusBarUtils;
import com.iyoyogo.android.utils.imagepicker.activities.ImagesPickActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的喜欢
 */
public class Like_me_Activity extends BaseActivity<MinePraiseContract.Presenter> implements MinePraiseContract.View {


    @BindView(R.id.message_center_back_im_id)
    ImageView messageCenterBackImId;
    @BindView(R.id.like_me_title_tv_id)
    TextView likeMeTitleTvId;
    @BindView(R.id.bar)
    RelativeLayout bar;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.ic_video)
    ImageView icVideo;
    @BindView(R.id.img_layout)
    RelativeLayout imgLayout;
    @BindView(R.id.like_me_rv_id)
    RecyclerView likeMeRvId;
    @BindView(R.id.img_stamp)
    ImageView imgStamp;
    @BindView(R.id.tv_stamp_title)
    TextView tvStampTitle;
    @BindView(R.id.publish_yoji)
    TextView publishYoji;
    @BindView(R.id.publish_yoxiu)
    TextView publishYoxiu;
    @BindView(R.id.like_layout)
    LinearLayout layoutLike;
    private String user_id;
    private String user_token;

    @Override
    protected void initView() {
        super.initView();
        StatusBarUtils.setWindowStatusBarColor(Like_me_Activity.this, R.color.white);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_like_me;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        user_id = SpUtils.getString(Like_me_Activity.this, "user_id", null);

        user_token = SpUtils.getString(Like_me_Activity.this, "user_token", null);
        mPresenter.getPraise(user_id, user_token, 1, 20);
    }

    @Override
    protected MinePraiseContract.Presenter createPresenter() {
        return new MinePraisePresenter(this);
    }


    @Override
    public void getPraiseSuccess(PraiseBean praiseBean) {
        List<PraiseBean.DataBean.ListBean> list = praiseBean.getData().getList();
        List<PraiseBean.DataBean.ListBean> mList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            String file_path = list.get(0).getFile_path();
            int yo_type = list.get(0).getYo_type();
            if (list.get(0).getFile_type()==1){
                icVideo.setVisibility(View.GONE);
            }else {
                icVideo.setVisibility(View.VISIBLE);
            }
            imgLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Like", "yo_type:" + yo_type);
                    Log.d("Like", "mList.get(0).getYo_id():" + mList.get(0).getYo_id());
                    if (yo_type == 1) {
                        Intent intent = new Intent(Like_me_Activity.this, YoXiuDetailActivity.class);
                        intent.putExtra("id", mList.get(0).getYo_id());
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(Like_me_Activity.this, YoJiDetailActivity.class);
                        intent.putExtra("yo_id", mList.get(0).getYo_id());
                        startActivity(intent);
                    }
                }
            });

            Glide.with(this).load(file_path).into(img);
            for (int i = 1; i < list.size(); i++) {
                mList.add(list.get(i));
            }
            MineLikeAdapter mineLikeAdapter = new MineLikeAdapter(Like_me_Activity.this, mList);
            likeMeRvId.setLayoutManager(new GridLayoutManager(Like_me_Activity.this, 3));
            likeMeRvId.setAdapter(mineLikeAdapter);
            mineLikeAdapter.setOnItemClickListener(new MineLikeAdapter.OnClickListenerListener() {
                @Override
                public void setOnClickListener(View v, int position) {
                    int yo_type = mList.get(position).getYo_type();
                    if (yo_type == 1) {
                        Intent intent = new Intent(Like_me_Activity.this, YoXiuDetailActivity.class);
                        intent.putExtra("id", mList.get(position).getYo_id());
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(Like_me_Activity.this, YoJiDetailActivity.class);
                        intent.putExtra("yo_id", mList.get(position).getYo_id());
                        startActivity(intent);
                    }
                }
            });
        } else {
            layoutLike.setVisibility(View.VISIBLE);
            tvStampTitle.setText("你还没有任何喜欢内容yo~");
            imgStamp.setImageResource(R.mipmap.blank_list);
            likeMeRvId.setVisibility(View.GONE);
            img.setVisibility(View.GONE);

        }

    }


    @OnClick({R.id.message_center_back_im_id, R.id.publish_yoji, R.id.publish_yoxiu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.message_center_back_im_id:
                finish();
                break;

            case R.id.publish_yoji:
                startActivity(new Intent(Like_me_Activity.this, ImagesPickActivity.class));
                finish();
                break;
            case R.id.publish_yoxiu:
                startActivity(new Intent(Like_me_Activity.this, SourceChooseActivity.class));
                finish();
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
