package com.iyoyogo.android.ui.mine.homepage;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.YoXiuContentAdapter;
import com.iyoyogo.android.base.BaseFragment;
import com.iyoyogo.android.bean.mine.center.YoJiContentBean;
import com.iyoyogo.android.bean.mine.center.YoXiuContentBean;
import com.iyoyogo.android.contract.YoXiuContentContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.presenter.YoXiuContentPresenter;
import com.iyoyogo.android.ui.home.EditImageOrVideoActivity;
import com.iyoyogo.android.ui.mine.draft.DraftActivity;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.refreshheader.MyRefreshAnimFooter;
import com.iyoyogo.android.utils.refreshheader.MyRefreshAnimHeader;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 * yo秀内容
 */
public class YoXiuFragment extends BaseFragment<YoXiuContentContract.Presenter> implements YoXiuContentContract.View {

    int currentPage = 1;
    @BindView(R.id.recycler_yoxiu)
    RecyclerView recyclerYoxiu;
    Unbinder unbinder;
    @BindView(R.id.list_blank)
    LinearLayout listBlank;
    @BindView(R.id.list_blank_me)
    LinearLayout listBlankMe;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder1;
    private String user_id;
    private String user_token;
    private String yo_user_id;
    MyRefreshAnimHeader mRefreshAnimHeader;
    private YoXiuContentAdapter yoXiuContentAdapter;
    private List<YoXiuContentBean.DataBean.ListBean> list;
    public  List<YoXiuContentBean.DataBean.ListBean> mList;

    /**
     * 设置刷新header风格
     *
     * @param header
     */
    private void setHeader(RefreshHeader header) {
        refreshLayout.setRefreshHeader(header);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_yo_xiu;
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getArguments();
        yo_user_id = bundle.getString("yo_user_id");
        user_id = SpUtils.getString(getContext(), "user_id", null);
        user_token = SpUtils.getString(getContext(), "user_token", null);
        mPresenter.getYoXiuContent(user_id, user_token, yo_user_id, 1 + "", 20 + "");

        //下拉刷新
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setRefreshFooter(new MyRefreshAnimFooter(getContext()));
        refreshLayout.autoRefresh();
        refreshLayout.finishRefresh(1050);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mList.clear();
                refreshLayout.finishRefresh(1050);
                mPresenter.getYoXiuContent(user_id, user_token, yo_user_id, "1", "20");
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                currentPage++;
                Log.d("currentPage", "currentPage:" + currentPage);
                DataManager.getFromRemote()
                        .getYoXiuContent(user_id, user_token, yo_user_id, currentPage + "", 20 + "")
                        .subscribe(new Consumer<YoXiuContentBean>() {
                            @Override
                            public void accept(YoXiuContentBean yoXiuContentBean) throws Exception {
                                List<YoXiuContentBean.DataBean.ListBean> list1 = yoXiuContentBean.getData().getList();
                                mList.addAll(list1);
                                if (mList != null) {
                                    yoXiuContentAdapter.notifyItemInserted(mList.size());
                                }
                            }
                        });
                refreshLayout.finishLoadMore(2000);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
//        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                currentPage++;
//                Log.d("currentPage", "currentPage:" + currentPage);
//                DataManager.getFromRemote()
//                        .getYoXiuContent(user_id, user_token, yo_user_id, currentPage + "", 20 + "")
//                        .subscribe(new Consumer<YoXiuContentBean>() {
//                            @Override
//                            public void accept(YoXiuContentBean yoXiuContentBean) throws Exception {
//                                List<YoXiuContentBean.DataBean.ListBean> list1 = yoXiuContentBean.getData().getList();
//                                list.addAll(list1);
//                                if (list.size() != 0) {
//                                    yoXiuContentAdapter.notifyItemInserted(list.size());
//                                }
//                            }
//                        });
//                refreshLayout.finishLoadMore(2000);
//            }
//
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                list.clear();
//                DataManager.getFromRemote().getYoXiuContent(user_id, user_token, yo_user_id, currentPage + "", 20 + "")
//                        .subscribe(new Observer<YoXiuContentBean>() {
//                            @Override
//                            public void onSubscribe(Disposable d) {
//
//                            }
//
//                            @Override
//                            public void onNext(YoXiuContentBean yoXiuContentBean) {
//                                List<YoXiuContentBean.DataBean.ListBean> list1 = yoXiuContentBean.getData().getList();
//                                list.addAll(list1);
//                                yoXiuContentAdapter = new YoXiuContentAdapter(getContext(), list);
//                                recyclerYoxiu.setAdapter(yoXiuContentAdapter);
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.d("YoJiFragment", e.getMessage());
//                            }
//
//                            @Override
//                            public void onComplete() {
//
//                            }
//                        });
//                refreshLayout.finishRefresh();
//            }
//        });
    }

    @SuppressLint("ResourceType")
    @Override
    protected void initView() {
        super.initView();
        mList = new ArrayList<>();
        //初始化header
        mRefreshAnimHeader = new MyRefreshAnimHeader(getContext());
        setHeader(mRefreshAnimHeader);
    }


    @Override
    protected YoXiuContentContract.Presenter createPresenter() {
        return new YoXiuContentPresenter(this);
    }

    @Override
    public void getYoXiuContentSuccess(YoXiuContentBean.DataBean data) {
        list = data.getList();
        mList.addAll(list);
        if (list.size() == 0) {
            if (yo_user_id.equals(user_id)) {
                listBlankMe.setVisibility(View.VISIBLE);
                recyclerYoxiu.setVisibility(View.GONE);
            } else {
                listBlank.setVisibility(View.VISIBLE);
                recyclerYoxiu.setVisibility(View.GONE);
                tvTips.setText("TA好像比较低调，还没有发布任何yo·秀yo～");
            }
        } else {
            listBlank.setVisibility(View.GONE);
            recyclerYoxiu.setVisibility(View.VISIBLE);
            yoXiuContentAdapter = new YoXiuContentAdapter(getContext(), list);
            recyclerYoxiu.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerYoxiu.setAdapter(yoXiuContentAdapter);
        }

    }

    @OnClick(R.id.tv_yoxiu)
    public void onViewClicked() {
        PictureSelector.create(this)
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
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                startActivity(data.setClass(getContext(), EditImageOrVideoActivity.class).putExtra("type", 1));
            } else if (requestCode == 201) {
                startActivity(data.setClass(getContext(), EditImageOrVideoActivity.class).putExtra("type", 2));
            }
        }
    }
}
