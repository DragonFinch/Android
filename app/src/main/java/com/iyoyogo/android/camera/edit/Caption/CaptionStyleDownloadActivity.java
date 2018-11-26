package com.iyoyogo.android.camera.edit.Caption;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.iyoyogo.android.R;

import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.camera.download.AssetListFragment;
import com.iyoyogo.android.camera.interfaces.OnTitleBarClickListener;
import com.iyoyogo.android.camera.utils.AppManager;
import com.iyoyogo.android.camera.utils.asset.NvAsset;
import com.iyoyogo.android.camera.utils.dataInfo.TimelineData;
import com.iyoyogo.android.widget.CustomTitleBar;

public class CaptionStyleDownloadActivity extends BaseActivity implements View.OnClickListener {
    private CustomTitleBar mTitleBar;
    AssetListFragment mAssetListFragment;



    @Override
    protected void initView() {
        mTitleBar = (CustomTitleBar) findViewById(R.id.title_bar);
    }

    @Override
    protected int getLayoutId() {
            return R.layout.activity_caption_style_download;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initTitle() {
        mTitleBar.setTextCenter(R.string.moreCaptionStyle);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        initAssetFragment();
        mTitleBar.setOnTitleBarClickListener(new OnTitleBarClickListener() {
            @Override
            public void OnBackImageClick() {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
            }

            @Override
            public void OnCenterTextClick() {

            }

            @Override
            public void OnRightTextClick() {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        AppManager.getInstance().finishActivity();
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {

    }
    private void initAssetFragment() {
        Bundle bundle = new Bundle();
        bundle.putInt("ratio", TimelineData.instance().getMakeRatio());
        bundle.putInt("assetType", NvAsset.ASSET_CAPTION_STYLE);
        mAssetListFragment = new AssetListFragment();
        mAssetListFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.spaceLayout, mAssetListFragment)
                .commit();
        getSupportFragmentManager().beginTransaction().show(mAssetListFragment);
    }
}
