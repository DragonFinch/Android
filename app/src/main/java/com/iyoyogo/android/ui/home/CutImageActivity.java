package com.iyoyogo.android.ui.home;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.view.LoadingDialog;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.callback.BitmapCropCallback;
import com.yalantis.ucrop.model.CutInfo;
import com.yalantis.ucrop.view.GestureCropImageView;
import com.yalantis.ucrop.view.OverlayView;
import com.yalantis.ucrop.view.UCropView;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class CutImageActivity extends BaseActivity {

    @BindView(R.id.u_crop_view)
    UCropView    mUCropView;
    @BindView(R.id.iv_original)
    ImageView    mIvOriginal;
    @BindView(R.id.tv_original)
    TextView     mTvOriginal;
    @BindView(R.id.ll_original)
    LinearLayout mLlOriginal;
    @BindView(R.id.iv_freedom)
    ImageView    mIvFreedom;
    @BindView(R.id.tv_freedom)
    TextView     mTvFreedom;
    @BindView(R.id.ll_freedom)
    LinearLayout mLlFreedom;
    @BindView(R.id.iv_43)
    ImageView    mIv43;
    @BindView(R.id.tv_43)
    TextView     mTv43;
    @BindView(R.id.ll_43)
    LinearLayout mLl43;
    @BindView(R.id.iv_34)
    ImageView    mIv34;
    @BindView(R.id.tv_34)
    TextView     mTv34;
    @BindView(R.id.ll_34)
    LinearLayout mLl34;
    @BindView(R.id.iv_169)
    ImageView    mIv169;
    @BindView(R.id.tv_169)
    TextView     mTv169;
    @BindView(R.id.ll_169)
    LinearLayout mLl169;
    @BindView(R.id.iv_11)
    ImageView    mIv11;
    @BindView(R.id.tv_11)
    TextView     mTv11;
    @BindView(R.id.ll_11)
    LinearLayout mLl11;
    @BindView(R.id.iv_done)
    ImageView    mIvDone;
    @BindView(R.id.iv_back)
    ImageView    mIvBack;
    private GestureCropImageView mGestureCropImageView;
    private OverlayView          mOverlayView;

    int imageW = 0;
    int imageH = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cut_image;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        super.initView();
        statusbar();
        mGestureCropImageView = mUCropView.getCropImageView();
        mGestureCropImageView.setRotateEnabled(false);
        mGestureCropImageView.setScaleEnabled(true);

        mOverlayView = mUCropView.getOverlayView();

        mOverlayView.setDimmedColor(Color.parseColor("#8c000000"));
        mOverlayView.setCircleDimmedLayer(false);
        mOverlayView.setShowCropFrame(true);
        mOverlayView.setShowCropGrid(true);
        mOverlayView.setCropFrameColor(Color.WHITE);
        mOverlayView.setCropFrameStrokeWidth(1);
        mOverlayView.setCropGridRowCount(2);
        mOverlayView.setCropGridColumnCount(2);
        mOverlayView.setCropGridColor(Color.parseColor("#80ffffff"));
        mOverlayView.setCropGridStrokeWidth(1);


//        mGestureCropImageView.setTransformImageListener(mImageListener);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        String                path    = getIntent().getStringExtra("data");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        imageH = options.outHeight;
        imageW = options.outWidth;

        Uri inputPath  = Uri.fromFile(new File(path));
        Uri outputPath = Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"/Yoyogo/Image/cut_"+System.currentTimeMillis()+".png"));
        try {
            mGestureCropImageView.setImageUri(inputPath, outputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.ll_original, R.id.ll_freedom, R.id.ll_43, R.id.ll_34, R.id.ll_169, R.id.ll_11, R.id.iv_done, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_original:
                reSetAllStatus();
                mIvOriginal.setImageResource(R.drawable.orange_border);
                mTvOriginal.setTextColor(Color.parseColor("#FA800A"));

                mOverlayView.setFreestyleCropEnabled(false);
                mOverlayView.setTargetAspectRatio((float) imageW/(float) imageH);
                break;
            case R.id.ll_freedom:
                reSetAllStatus();
                mIvFreedom.setImageResource(R.drawable.orange_dotted_line_border);
                mTvFreedom.setTextColor(Color.parseColor("#FA800A"));

                mOverlayView.setFreestyleCropEnabled(true);
                mOverlayView.setupCropBounds();
                break;
            case R.id.ll_43:
                reSetAllStatus();
                mIv43.setImageResource(R.drawable.orange_border);
                mTv43.setTextColor(Color.parseColor("#FA800A"));

                mOverlayView.setFreestyleCropEnabled(false);
                mOverlayView.setTargetAspectRatio((float) 4 / (float) 3);
                break;
            case R.id.ll_34:
                reSetAllStatus();
                mIv34.setImageResource(R.drawable.orange_border);
                mTv34.setTextColor(Color.parseColor("#FA800A"));

                mOverlayView.setFreestyleCropEnabled(false);
                mOverlayView.setTargetAspectRatio(3f / 4f);
                break;
            case R.id.ll_169:
                reSetAllStatus();
                mIv169.setImageResource(R.drawable.orange_border);
                mTv169.setTextColor(Color.parseColor("#FA800A"));


                mOverlayView.setFreestyleCropEnabled(false);
                mOverlayView.setTargetAspectRatio(9f / 16f);
                break;
            case R.id.ll_11:
                reSetAllStatus();
                mIv11.setImageResource(R.drawable.orange_border);
                mTv11.setTextColor(Color.parseColor("#FA800A"));


                mOverlayView.setFreestyleCropEnabled(false);
                mOverlayView.setTargetAspectRatio(1f);
                break;
            case R.id.iv_done:
                cropAndSaveImage();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    public void reSetAllStatus() {
        mIvOriginal.setImageResource(R.drawable.black_border);
        mIvFreedom.setImageResource(R.drawable.black_dotted_line_border);
        mIv34.setImageResource(R.drawable.black_border);
        mIv43.setImageResource(R.drawable.black_border);
        mIv169.setImageResource(R.drawable.black_border);
        mIv11.setImageResource(R.drawable.black_border);

        mTvOriginal.setTextColor(Color.parseColor("#333333"));
        mTvFreedom.setTextColor(Color.parseColor("#333333"));
        mTv34.setTextColor(Color.parseColor("#333333"));
        mTv43.setTextColor(Color.parseColor("#333333"));
        mTv169.setTextColor(Color.parseColor("#333333"));
        mTv11.setTextColor(Color.parseColor("#333333"));
    }

    protected void cropAndSaveImage() {
        LoadingDialog.get().create(this).show();
        mGestureCropImageView.cropAndSaveImage(Bitmap.CompressFormat.PNG, 100, new BitmapCropCallback() {

            @Override
            public void onBitmapCropped(@NonNull Uri resultUri, int offsetX, int offsetY, int imageWidth, int imageHeight) {
                LoadingDialog.get().close();
                CutInfo cutInfo=new CutInfo();
                cutInfo.setPath(getIntent().getStringExtra("data"));
                cutInfo.setCutPath(resultUri.getPath());
                cutInfo.setOffsetX(offsetX);
                cutInfo.setOffsetY(offsetY);
                cutInfo.setCut(true);
                cutInfo.setImageHeight(imageHeight);
                cutInfo.setImageWidth(imageWidth);
                cutInfo.setResultAspectRatio(mGestureCropImageView.getTargetAspectRatio());
                setResult(RESULT_OK,new Intent().putExtra("data",cutInfo));
                finish();
                overridePendingTransition(0, com.yalantis.ucrop.R.anim.ucrop_close);
            }

            @Override
            public void onCropFailure(@NonNull Throwable t) {
                LoadingDialog.get().close();
                Toast.makeText(CutImageActivity.this, "裁剪失败，请重试", Toast.LENGTH_SHORT).show();
                setResult(UCrop.RESULT_ERROR, new Intent().putExtra(UCrop.EXTRA_ERROR, t));
                finish();
                overridePendingTransition(0, com.yalantis.ucrop.R.anim.ucrop_close);
            }
        });
    }
}
