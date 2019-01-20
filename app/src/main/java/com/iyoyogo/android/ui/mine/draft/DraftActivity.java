package com.iyoyogo.android.ui.mine.draft;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.DraftAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.mine.DraftBean;
import com.iyoyogo.android.contract.DraftContract;
import com.iyoyogo.android.presenter.DraftPresenter;
import com.iyoyogo.android.ui.home.EditImageOrVideoActivity;
import com.iyoyogo.android.ui.home.HomeFragment;
import com.iyoyogo.android.utils.SpUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 草稿
 */
public class DraftActivity extends BaseActivity<DraftContract.Presenter> implements DraftContract.View {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.create_complete)
    TextView createComplete;
    @BindView(R.id.recycler_draft)
    RecyclerView recyclerDraft;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.yoxiu_tv)
    TextView yoxiuTv;
    @BindView(R.id.tv_yoji)
    TextView tvYoji;
    @BindView(R.id.draft_blank)
    LinearLayout draftBlank;
    private String user_id;
    private String user_token;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_draft;
    }

    @Override
    protected DraftContract.Presenter createPresenter() {
        return new DraftPresenter(DraftActivity.this,this);
    }

    @Override
    protected void initView() {
        super.initView();
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
//        statusbar();
        tvTitle.setText("我的草稿");
        createComplete.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        user_id = SpUtils.getString(DraftActivity.this, "user_id", null);
        user_token = SpUtils.getString(DraftActivity.this, "user_token", null);
        mPresenter.getDraft(DraftActivity.this,user_id, user_token, 1, 20);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }


    @Override
    public void getDraftSuccess(List<DraftBean.DataBean.ListBean> list) {
        if (list.size() == 0) {
            draftBlank.setVisibility(View.VISIBLE);
            recyclerDraft.setVisibility(View.GONE);
        } else {
            draftBlank.setVisibility(View.GONE);
            recyclerDraft.setVisibility(View.VISIBLE);
            recyclerDraft.setLayoutManager(new LinearLayoutManager(DraftActivity.this));
            DraftAdapter draftAdapter = new DraftAdapter(DraftActivity.this, list);
            recyclerDraft.setAdapter(draftAdapter);
        }

    }



    @OnClick({R.id.back, R.id.yoxiu_tv, R.id.tv_yoji})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.yoxiu_tv:
                PictureSelector.create(DraftActivity.this)
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
            case R.id.tv_yoji:
                PictureSelector.create(DraftActivity.this)
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
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                startActivity(data.setClass(DraftActivity.this, EditImageOrVideoActivity.class).putExtra("type", 1));
            } else if (requestCode == 201) {
                startActivity(data.setClass(DraftActivity.this, EditImageOrVideoActivity.class).putExtra("type", 2));
            }
        }
    }
}
