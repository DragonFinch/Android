package com.iyoyogo.android.camera.edit.watermark;


import android.content.Intent;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;

import com.iyoyogo.android.R;
import com.iyoyogo.android.app.Constants;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.camera.VideoFragment;
import com.iyoyogo.android.camera.edit.EditBaseActivity;
import com.iyoyogo.android.camera.edit.clipEdit.EditActivity;
import com.iyoyogo.android.camera.utils.AppManager;
import com.iyoyogo.android.camera.utils.dataInfo.TimelineData;
import com.iyoyogo.android.widget.CustomTitleBar;

import java.util.ArrayList;
import java.util.List;

import static com.iyoyogo.android.camera.utils.MediaConstant.SINGLE_PICTURE_PATH;

public class WaterMarkActivity extends EditBaseActivity implements View.OnClickListener {
    public static final String WATER_PICTURE_PATH = "waterPicturePath";
    public static final String WATER_PICTURE_W = "waterPictureW";
    public static final String WATER_PICTURE_H = "waterPictureH";
    public static final String WATER_MARGIN_X = "waterMarginX";
    public static final String WATER_MARGIN_Y = "waterMarginY";
    public static final String WATER_CLEAN = "waterClean";
    private final String TAG = "WaterMarkActivity";
    private CustomTitleBar m_titleBar;
    private Button mAddButton;
    private Button mOkButton;
    private Point liveWindowPoint;
    private String mPicturePath;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_water_mark;
    }

    @Override
    protected void initView() {
        m_titleBar = (CustomTitleBar) findViewById(R.id.title_bar);
        mAddButton = (Button) findViewById(R.id.water_btn_add);
        mAddButton.setOnClickListener(this);
        mOkButton = (Button) findViewById(R.id.water_btn_ok);
        mOkButton.setOnClickListener(this);
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initTitle() {
        m_titleBar.setTextCenter(R.string.edit_watermark);
        m_titleBar.setBackImageVisible(View.GONE);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        initVideoFragment(R.id.water_videoLayout);
        videoFragment.setAssetEditListener(new VideoFragment.AssetEditListener() {
            @Override
            public void onAssetDelete() {
                mPicturePath = null;
                videoFragment.setPicturePath(null);
                videoFragment.setDrawRectVisible(View.GONE);
            }

            @Override
            public void onAssetSelected(PointF curPoint) {

            }

            @Override
            public void onAssetTranstion() {

            }

            @Override
            public void onAssetScale() {

            }

            @Override
            public void onAssetAlign(int alignVal) {

            }

            @Override
            public void onAssetHorizFlip(boolean isHorizFlip) {

            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {
        super.onStart();
        WaterMarkData waterMarkData = TimelineData.instance().getWaterMarkData();
        if (waterMarkData != null) {
            liveWindowPoint = waterMarkData.getPointOfLiveWindow();
            mPicturePath = waterMarkData.getPicPath();
            setWaterMarkOnTheFirst(mPicturePath);
            videoFragment.setDrawRect(waterMarkData.getPointFInLiveWindow());
        }
    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            liveWindowPoint = videoFragment.getLiveWindowSize();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.water_btn_add:
                AppManager.getInstance().jumpActivityForResult(AppManager.getInstance().currentActivity(), SingleClickActivity.class, null, EditActivity.CLIPTRIM_REQUESTCODE);
                break;
            case R.id.water_btn_ok:
                if (mPicturePath != null) {
                    List<PointF> nowPointToRect = videoFragment.getDrawRect();
                    List<PointF> newPointToRect = viewPointToCanonical(nowPointToRect);
                    List<PointF> pointAtFirst = videoFragment.getPointFListToFirstAddWaterMark();
                    List<PointF> newPointAtFirst = viewPointToCanonical(pointAtFirst);
                    int excursionX = (int) (newPointAtFirst.get(3).x - newPointToRect.get(3).x);
                    int excursionY = (int) (newPointAtFirst.get(3).y - newPointToRect.get(3).y);
                    int picWidth = (int) Math.abs(newPointToRect.get(0).x - newPointToRect.get(3).x);
                    int picHeight = (int) Math.abs(newPointToRect.get(0).y - newPointToRect.get(1).y);

                    WaterMarkData waterMarkData = new WaterMarkData(nowPointToRect, excursionX, excursionY, picWidth, picHeight, mPicturePath, liveWindowPoint);
                    TimelineData.instance().setWaterMarkData(waterMarkData);

                    Intent intent = new Intent();
                    intent.putExtra(WATER_PICTURE_PATH, mPicturePath);
                    intent.putExtra(WATER_PICTURE_W, picWidth);
                    intent.putExtra(WATER_PICTURE_H, picHeight);
                    intent.putExtra(WATER_MARGIN_X, excursionX);
                    intent.putExtra(WATER_MARGIN_Y, excursionY);
                    intent.putExtra(WATER_CLEAN, false);
                    setResult(RESULT_OK, intent);
                } else {
                    TimelineData.instance().cleanWaterMarkData();
                    Intent intent = new Intent();
                    intent.putExtra(WATER_CLEAN, true);
                    setResult(RESULT_OK, intent);
                }
                finishActivity();
                break;
            default:
                break;
        }
    }

    private List<PointF> viewPointToCanonical(List<PointF> nowPointToRect) {
        List<PointF> newPointFS = new ArrayList<>();
        for (PointF pointF : nowPointToRect) {
            PointF newPointToCanonical = videoFragment.getmLiveWindow().mapViewToCanonical(pointF);
            newPointFS.add(newPointToCanonical);
        }
        return newPointFS;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            mPicturePath = data.getStringExtra(SINGLE_PICTURE_PATH);
            setWaterMarkOnTheFirst(mPicturePath);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setWaterMarkOnTheFirst(String picturePath) {
        videoFragment.setEditMode(Constants.EDIT_MODE_WATERMARK);
        videoFragment.setPicturePath(picturePath);
        videoFragment.firstSetWaterMarkPosition(liveWindowPoint.x, liveWindowPoint.y);
        videoFragment.setDrawRectVisible(View.VISIBLE);
    }
}
