package com.iyoyogo.android.ui.mine.homepage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.MineLikeAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.mine.PraiseBean;
import com.iyoyogo.android.contract.MinePraiseContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.presenter.MinePraisePresenter;
import com.iyoyogo.android.ui.home.EditImageOrVideoActivity;
import com.iyoyogo.android.ui.home.yoji.YoJiDetailActivity;
import com.iyoyogo.android.ui.home.yoxiu.SourceChooseActivity;
import com.iyoyogo.android.ui.home.yoxiu.YoXiuDetailActivity;
import com.iyoyogo.android.ui.mine.draft.DraftActivity;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.StatusBarUtils;
import com.iyoyogo.android.utils.imagepicker.activities.ImagesPickActivity;
import com.iyoyogo.android.utils.refreshheader.MyRefreshAnimHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 我的喜欢
 */
public class Like_me_Activity extends BaseActivity<MinePraiseContract.Presenter> implements MinePraiseContract.View {

    @BindView(R.id.scroll)
    ScrollView scroll;
    private Bitmap bitmap;
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
    LinearLayout likeLayout;
    private String user_id;
    private String user_token;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    MyRefreshAnimHeader mRefreshAnimHeader;
    int currentPage = 1;
    private MineLikeAdapter mineLikeAdapter;
    private List<PraiseBean.DataBean.ListBean> mList;

    /**
     * 设置刷新header风格
     *
     * @param header
     */
    private void setHeader(RefreshHeader header) {
        refreshLayout.setRefreshHeader(header);
    }

    @Override
    protected void initView() {
        super.initView();
//        StatusBarUtils.setWindowStatusBarColor(Like_me_Activity.this, R.color.white);
        statusbar();
        //初始化header
        mRefreshAnimHeader = new MyRefreshAnimHeader(this);
        setHeader(mRefreshAnimHeader);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_like_me;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        user_id = SpUtils.getString(Like_me_Activity.this, "user_id", null);
        user_token = SpUtils.getString(Like_me_Activity.this, "user_token", null);
        mPresenter.getPraise(user_id, user_token, currentPage, 20);
        //下拉刷新
        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setFooterHeight(1.0f);
        refreshLayout.autoRefresh();
        refreshLayout.finishRefresh(1050);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mList.clear();
                refreshLayout.finishRefresh(1050);
                mPresenter.getPraise(user_id, user_token, currentPage, 20);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                currentPage++;
                Log.d("currentPage", "currentPage:" + currentPage);
                DataManager.getFromRemote()
                        .getPraise(user_id, user_token, currentPage, 20)
                        .subscribe(new Consumer<PraiseBean>() {
                            @Override
                            public void accept(PraiseBean praiseBean) throws Exception {
                                List<PraiseBean.DataBean.ListBean> list1 = praiseBean.getData().getList();
                                mList.addAll(list1);
                                if (mList != null) {
                                    mineLikeAdapter.notifyItemInserted(mList.size());
                                }
                            }
                        });

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
    }

    @Override
    protected MinePraiseContract.Presenter createPresenter() {
        return new MinePraisePresenter(this);
    }


    @Override
    public void getPraiseSuccess(PraiseBean praiseBean) {
        List<PraiseBean.DataBean.ListBean> list = praiseBean.getData().getList();
        mList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            String file_path = list.get(0).getFile_path();
            int yo_type = list.get(0).getYo_type();
            if (list.get(0).getFile_type() == 1) {
                icVideo.setVisibility(View.GONE);
            } else {
                icVideo.setVisibility(View.VISIBLE);
            }
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Like", "yo_type:" + yo_type);
                    Log.d("Like", "mList.get(0).getYo_id():" + list.get(0).getYo_id());
                    if (yo_type == 1) {
                        Intent intent = new Intent(Like_me_Activity.this, YoXiuDetailActivity.class);
                        intent.putExtra("id", list.get(0).getYo_id());
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(Like_me_Activity.this, YoJiDetailActivity.class);
                        intent.putExtra("yo_id", list.get(0).getYo_id());
                        startActivity(intent);
                    }
                }
            });
            Glide.with(this).load(file_path).into(img);
            for (int i = 1; i < list.size(); i++) {
                mList.add(list.get(i));
            }
            mineLikeAdapter = new MineLikeAdapter(Like_me_Activity.this, mList);
            likeMeRvId.setLayoutManager(new GridLayoutManager(Like_me_Activity.this, 3) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
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
            likeLayout.setVisibility(View.VISIBLE);
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
                PictureSelector.create(Like_me_Activity.this)
                        .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .maxSelectNum(9)// 最大图片选择数量 int
                        .minSelectNum(1)// 最小选择数量 int
                        .imageSpanCount(3)// 每行显示个数 int
                        .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                        .previewImage(true)// 是否可预览图片 true or false
                        .isCamera(true)// 是否显示拍照按钮 true or false
                        .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                        .compress(true)// 是否压缩 true or false
                        .isGif(true)// 是否显示gif图片 true or false
                        .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                        .minimumCompressSize(800)// 小于100kb的图片不压缩
                        .synOrAsy(false)//同步true或异步false 压缩 默认同步
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.publish_yoxiu:
                PictureSelector.create(Like_me_Activity.this)
                        .openGallery(PictureMimeType.ofAll())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .imageSpanCount(3)// 每行显示个数 int
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                        .previewImage(true)// 是否可预览图片 true or false
                        .isCamera(true)// 是否显示拍照按钮 true or false
                        .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                        .compress(true)// 是否压缩 true or false
                        .isGif(true)// 是否显示gif图片 true or false
                        .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                        .minimumCompressSize(800)// 小于100kb的图片不压缩
                        .synOrAsy(false)//同步true或异步false 压缩 默认同步
                        .forResult(201);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                startActivity(data.setClass(Like_me_Activity.this, EditImageOrVideoActivity.class).putExtra("type", 1));
            } else if (requestCode == 201) {
                startActivity(data.setClass(Like_me_Activity.this, EditImageOrVideoActivity.class).putExtra("type", 2));
            }
        }
    }

}
