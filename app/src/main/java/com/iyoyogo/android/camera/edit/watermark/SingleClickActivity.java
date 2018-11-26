package com.iyoyogo.android.camera.edit.watermark;


import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.app.Constants;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.camera.edit.animatesticker.customsticker.CustomAnimateStickerActivity;
import com.iyoyogo.android.camera.makecover.MakeCoverActivity;
import com.iyoyogo.android.camera.selectmedia.bean.MediaData;
import com.iyoyogo.android.camera.selectmedia.fragment.MediaFragment;
import com.iyoyogo.android.camera.selectmedia.interfaces.OnTotalNumChangeForActivity;
import com.iyoyogo.android.camera.selectmedia.view.CustomPopWindow;
import com.iyoyogo.android.camera.utils.AppManager;
import com.iyoyogo.android.camera.utils.MediaConstant;
import com.iyoyogo.android.camera.utils.Util;
import com.iyoyogo.android.camera.utils.dataInfo.ClipInfo;
import com.iyoyogo.android.camera.utils.dataInfo.TimelineData;
import com.iyoyogo.android.widget.CustomTitleBar;

import java.util.ArrayList;
import java.util.List;

import static com.iyoyogo.android.app.Constants.POINT16V9;
import static com.iyoyogo.android.app.Constants.POINT1V1;
import static com.iyoyogo.android.app.Constants.POINT3V4;
import static com.iyoyogo.android.app.Constants.POINT4V3;
import static com.iyoyogo.android.app.Constants.POINT9V16;
import static com.iyoyogo.android.camera.utils.MediaConstant.SINGLE_PICTURE_PATH;

public class SingleClickActivity extends BaseActivity implements OnTotalNumChangeForActivity, CustomPopWindow.OnViewClickListener,View.OnClickListener {
    private final String TAG = "SingleClickActivity";
    private CustomTitleBar m_titleBar;
    private TextView sigleTvStartEdit;
    private List<MediaData> mediaDataList;
    private int fromWhat = Constants.SELECT_IMAGE_FROM_WATER_MARK;
    private float alphaOnPop = 0.6f;



    @Override
    protected void initView() {
        m_titleBar = (CustomTitleBar) findViewById(R.id.title_bar);
        sigleTvStartEdit = (TextView) findViewById(R.id.sigle_tv_startEdit);
        sigleTvStartEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fromWhat == Constants.SELECT_IMAGE_FROM_WATER_MARK) {
                    Intent intent = new Intent();
                    intent.putExtra(SINGLE_PICTURE_PATH, mediaDataList.get(0).getPath());
                    setResult(RESULT_OK, intent);
                    AppManager.getInstance().finishActivity();
                } else if(fromWhat == Constants.SELECT_IMAGE_FROM_MAKE_COVER) {
                    new CustomPopWindow.PopupWindowBuilder(SingleClickActivity.this)
                            .setView(R.layout.pop_select_makesize)
                            .setViewClickListener(SingleClickActivity.this)
                            .create()
                            .showAtLocation(findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
                } else if(fromWhat == Constants.SELECT_IMAGE_FROM_CUSTOM_STICKER) {
                    Bundle bundle = new Bundle();
                    bundle.putString("imageSrcFilePath",mediaDataList.get(0).getPath());
                    AppManager.getInstance().jumpActivity(AppManager.getInstance().currentActivity(), CustomAnimateStickerActivity.class, bundle);
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_single_click;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initTitle() {
        m_titleBar.setTextCenter(R.string.single_select_null);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Intent intent = getIntent();
        if(intent != null) {
            Bundle bundle = intent.getExtras();
            if(bundle != null) {
                fromWhat = bundle.getInt(Constants.SELECT_MEDIA_FROM, Constants.SELECT_IMAGE_FROM_WATER_MARK);
                if(fromWhat == Constants.SELECT_IMAGE_FROM_MAKE_COVER) {
                    m_titleBar.setTextCenter(R.string.single_select_picture);
                }
            }
        }
        initVideoFragment(R.id.single_contain);

    }
    @Override
    public void onViewClick(CustomPopWindow popWindow, View view) {
        switch (view.getId()) {
            case R.id.button16v9:
                selectCreateRatio(POINT16V9);
                break;
            case R.id.button1v1:
                selectCreateRatio(POINT1V1);
                break;
            case R.id.button9v16:
                selectCreateRatio(POINT9V16);
                break;
            case R.id.button3v4:
                selectCreateRatio(POINT3V4);
                break;
            case R.id.button4v3:
                selectCreateRatio(POINT4V3);
            default:
                break;
        }
    }

    private void selectCreateRatio(int makeRatio) {
        ArrayList<ClipInfo> pathList = getClipInfoList();
        TimelineData.instance().setVideoResolution(Util.getVideoEditResolution(makeRatio));
        TimelineData.instance().setClipInfoData(pathList);
        TimelineData.instance().setMakeRatio(makeRatio);
        AppManager.getInstance().jumpActivity(SingleClickActivity.this, MakeCoverActivity.class, null);
    }

    private ArrayList<ClipInfo> getClipInfoList() {
        ArrayList<ClipInfo> pathList = new ArrayList<>();
        if(mediaDataList != null) {
            for (MediaData mediaData : mediaDataList) {
                ClipInfo clipInfo = new ClipInfo();
                clipInfo.setFilePath(mediaData.getPath());
                pathList.add(clipInfo);
            }
        }
        return pathList;
    }

    private void initVideoFragment(int layoutId) {
        MediaFragment mediaFragment = new MediaFragment(this, this, MediaConstant.TYPE_ITEMCLICK_SINGLE);
        Bundle bundle = new Bundle();
        bundle.putInt(MediaConstant.MEDIA_TYPE, MediaConstant.IMAGE);
        mediaFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(layoutId, mediaFragment)
                .commit();
        getSupportFragmentManager().beginTransaction().show(mediaFragment);
    }



    @Override
    public void onClick(View v) {

    }

    @Override
    public void onTotalNumChangeForActivity(List selectList, Object tag) {
        mediaDataList = selectList;
        sigleTvStartEdit.setVisibility(selectList.size() > 0 ? View.VISIBLE : View.GONE);
        m_titleBar.setTextCenter(selectList.size() > 0 ? R.string.single_select_one : R.string.single_select_null);
    }
}
