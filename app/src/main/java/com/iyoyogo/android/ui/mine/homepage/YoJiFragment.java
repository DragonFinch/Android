package com.iyoyogo.android.ui.mine.homepage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.YoJiListHorizontalAdapter;
import com.iyoyogo.android.adapter.YoJiCenterAdapter;
import com.iyoyogo.android.adapter.YoJiContentAdapter2;
import com.iyoyogo.android.adapter.YoJiListAdapter;
import com.iyoyogo.android.base.BaseFragment;
import com.iyoyogo.android.bean.mine.center.YoJiContentBean;
import com.iyoyogo.android.bean.yoji.list.YoJiListBean;
import com.iyoyogo.android.contract.YoJiContentContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.presenter.YoJiContentPresenter;
import com.iyoyogo.android.ui.home.EditImageOrVideoActivity;
import com.iyoyogo.android.ui.home.yoji.UserHomepageActivity;
import com.iyoyogo.android.ui.home.yoji.YoJiDetailActivity;
import com.iyoyogo.android.ui.home.yoji.YoJiListActivity;
import com.iyoyogo.android.ui.home.yoxiu.YoXiuListActivity;
import com.iyoyogo.android.ui.mine.draft.DraftActivity;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.StatusBarUtils;
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
import com.umeng.debug.log.I;

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
 * yo记内容
 */
public class YoJiFragment extends BaseFragment<YoJiContentContract.Presenter> implements YoJiContentContract.View {

    int currentPage = 1;
    @BindView(R.id.recycler_yoji)
    RecyclerView recyclerYoji;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.list_blank)
    LinearLayout listBlank;
    @BindView(R.id.list_blank_me)
    LinearLayout listBlankMe;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    private String user_id;
    private String user_token;
    public static String yo_user_id;
    MyRefreshAnimHeader mRefreshAnimHeader;
    public static YoJiCenterAdapter yoJiCenterAdapter;
    public static YoJiContentAdapter2 yoJiContentAdapter2;
    private List<YoJiContentBean.DataBean.ListBean> list;
    public static List<YoJiContentBean.DataBean.ListBean> mList;

    /**
     * 设置刷新header风格
     *
     * @param header
     */
    private void setHeader(RefreshHeader header) {
        refreshLayout.setRefreshHeader(header);
    }


    @SuppressLint("ResourceType")
    @Override
    protected void initView() {
        super.initView();
        mList = new ArrayList<>();
        //初始化header
        refreshLayout.setRefreshFooter(new MyRefreshAnimFooter(getContext()));
        mRefreshAnimHeader = new MyRefreshAnimHeader(getContext());
        setHeader(mRefreshAnimHeader);
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getArguments();
        yo_user_id = bundle.getString("yo_user_id");
        user_id = SpUtils.getString(getContext(), "user_id", null);
        user_token = SpUtils.getString(getContext(), "user_token", null);
        //下拉刷新
        refreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setEnableRefresh(true);
        refreshLayout.autoRefresh();
        refreshLayout.finishRefresh(1050);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mList.clear();
                refreshLayout.finishRefresh(1050);
                mPresenter.getYoJiContent(user_id, user_token, yo_user_id, "1", "20");
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                currentPage++;
                Log.d("currentPage", "currentPage:" + currentPage);
                DataManager.getFromRemote()
                        .getYoJiContent(user_id, user_token, yo_user_id, currentPage + "", 20 + "")
                        .subscribe(new Consumer<YoJiContentBean>() {
                            @Override
                            public void accept(YoJiContentBean yoJiContentBean) throws Exception {
                                List<YoJiContentBean.DataBean.ListBean> list1 = yoJiContentBean.getData().getList();
                                mList.addAll(list1);
                                if (mList != null) {
                                    yoJiCenterAdapter.notifyItemInserted(mList.size());
                                    yoJiContentAdapter2.notifyItemInserted(mList.size());
                                }
                            }
                        });
                refreshLayout.finishLoadMore(2000);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_yo_ji;
    }

    @Override
    protected YoJiContentContract.Presenter createPresenter() {
        return new YoJiContentPresenter(this);
    }

    public void getYoJiContentSuccess(YoJiContentBean.DataBean data) {
        list = data.getList();
        mList.addAll(list);
        if (list.size() == 0) {
            if (yo_user_id.equals(user_id)) {
                recyclerYoji.setVisibility(View.GONE);
                listBlankMe.setVisibility(View.VISIBLE);
            } else {
                recyclerYoji.setVisibility(View.GONE);
                listBlank.setVisibility(View.VISIBLE);
            }
        } else {
            yoJiCenterAdapter = new YoJiCenterAdapter(getContext(), mList);
            yoJiContentAdapter2 = new YoJiContentAdapter2(getContext(), mList);
            recyclerYoji.setAdapter(yoJiCenterAdapter);
            recyclerYoji.setLayoutManager(new LinearLayoutManager(getContext()));
            yoJiCenterAdapter.setOnItemClickListener(new YoJiCenterAdapter.OnClickListener() {
                @Override
                public void onClick(View v, int position) {
                    int yo_id = mList.get(position).getYo_id();
                    Intent intent = new Intent(getContext(), YoJiDetailActivity.class);
                    intent.putExtra("yo_id", yo_id);
                    startActivity(intent);
                }
            });
            ImageView imgView = getActivity().findViewById(R.id.img_view);
            if (imgView.getDrawable().getCurrent().getConstantState().equals(ContextCompat.getDrawable(getContext(), R.mipmap.view22).getConstantState())) {
                imgView.setImageResource(R.mipmap.view22);
                recyclerYoji.setAdapter(yoJiContentAdapter2);
                recyclerYoji.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                yoJiContentAdapter2.setOnItemClickListener(new YoJiContentAdapter2.OnClickListener() {
                    @Override
                    public void setOnClickListener(View v, int position) {
                        int yo_id = mList.get(position).getYo_id();
                        Intent intent = new Intent(getContext(), YoJiDetailActivity.class);
                        intent.putExtra("yo_id", yo_id);
                        startActivity(intent);
                    }
                });
            } else {
                imgView.setImageResource(R.mipmap.view11);
                recyclerYoji.setAdapter(yoJiCenterAdapter);
                recyclerYoji.setLayoutManager(new LinearLayoutManager(getContext()));
                yoJiCenterAdapter.setOnItemClickListener(new YoJiCenterAdapter.OnClickListener() {
                    @Override
                    public void onClick(View v, int position) {
                        int yo_id = mList.get(position).getYo_id();
                        Intent intent = new Intent(getContext(), YoJiDetailActivity.class);
                        intent.putExtra("yo_id", yo_id);
                        startActivity(intent);
                    }
                });
            }
//            if (UserHomepageActivity.flag == true || Personal_homepage_Activity.flag == true) {
//                imgView.setImageResource(R.mipmap.view2);
//                recyclerYoji.setAdapter(yoJiContentAdapter2);
//                recyclerYoji.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//                yoJiContentAdapter2.setOnItemClickListener(new YoJiContentAdapter2.OnClickListener() {
//                    @Override
//                    public void setOnClickListener(View v, int position) {
//                        int yo_id = mList.get(position).getYo_id();
//                        Intent intent = new Intent(getContext(), YoJiDetailActivity.class);
//                        intent.putExtra("yo_id", yo_id);
//                        startActivity(intent);
//                    }
//                });
//            } else {
//                imgView.setImageResource(R.mipmap.view1);
//                recyclerYoji.setAdapter(yoJiCenterAdapter);
//                recyclerYoji.setLayoutManager(new LinearLayoutManager(getContext()));
//                yoJiCenterAdapter.setOnItemClickListener(new YoJiCenterAdapter.OnClickListener() {
//                    @Override
//                    public void onClick(View v, int position) {
//                        int yo_id = mList.get(position).getYo_id();
//                        Intent intent = new Intent(getContext(), YoJiDetailActivity.class);
//                        intent.putExtra("yo_id", yo_id);
//                        startActivity(intent);
//                    }
//                });
//            }
        }
    }

    @OnClick(R.id.tv_yoji)
    public void onViewClicked() {
        PictureSelector.create(this)
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


    public void refreshData() {
        recyclerYoji.setAdapter(yoJiContentAdapter2);
        recyclerYoji.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    public void refreshData2() {
        recyclerYoji.setAdapter(yoJiCenterAdapter);
        recyclerYoji.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}
