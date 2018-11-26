package com.iyoyogo.android.camera.edit.theme;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.camera.VideoFragment;
import com.iyoyogo.android.camera.data.FilterItem;
import com.iyoyogo.android.camera.utils.AppManager;
import com.iyoyogo.android.camera.utils.TimelineUtil;
import com.iyoyogo.android.camera.utils.Util;
import com.iyoyogo.android.camera.utils.asset.NvAsset;
import com.iyoyogo.android.camera.utils.asset.NvAssetManager;
import com.iyoyogo.android.camera.utils.dataInfo.CaptionInfo;
import com.iyoyogo.android.camera.utils.dataInfo.TimelineData;
import com.iyoyogo.android.widget.CustomTitleBar;
import com.meicam.sdk.NvsStreamingContext;
import com.meicam.sdk.NvsTimeline;
import com.meicam.sdk.NvsTimelineCaption;

import java.util.ArrayList;

/**
 * Created by yyj on 2018/5/30 0030.
 */

public class ThemeActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ThemeActivity";
    private static final int THEMEREQUESTLIST = 101;
    private VideoFragment mVideoFragment;
    private CustomTitleBar mTitleBar;
    private RelativeLayout mBottomLayout;
    private RecyclerView mThemeRecyclerView;
    private RelativeLayout mDownloadMoreBtn;
    private ImageView mDowanloadImage;
    private TextView mDowanloadMoreText;
    private ImageView mThemeAssetFinish;

    private ThemeAdapter mThemeAdapter;
    private ArrayList<FilterItem> mThemeDataList;
    private int mAssetType = NvAsset.ASSET_THEME;
    private NvAssetManager mAssetManager;
    ArrayList<CaptionInfo> mCaptionDataListClone;

    private NvsStreamingContext mStreamingContext;
    private NvsTimeline mTimeline;
    private long mTotalDuration = 0;
    private String mThemeId = "";



    @Override
    protected void initView() {
        mTitleBar = (CustomTitleBar) findViewById(R.id.title_bar);
        mBottomLayout = (RelativeLayout) findViewById(R.id.bottom_layout);
        mThemeRecyclerView = (RecyclerView) findViewById(R.id.theme_list);
        mDownloadMoreBtn = (RelativeLayout) findViewById(R.id.download_more_btn);
        mDowanloadImage = (ImageView)findViewById(R.id.dowanloadImage);
        mDowanloadMoreText = (TextView)findViewById(R.id.dowanloadMoreText);
        mThemeAssetFinish = (ImageView)findViewById(R.id.themeAssetFinish);
    }

    @Override
    protected int getLayoutId() {
            mStreamingContext = NvsStreamingContext.getInstance();
            return R.layout.activity_theme;
        }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initTitle() {
        mTitleBar.setTextCenter(R.string.theme);
        mTitleBar.setBackImageVisible(View.GONE);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        init();
        initVideoFragment();
        initThemeDataList();
        initThemeViewList();
        mDownloadMoreBtn.setOnClickListener(this);
        mThemeAssetFinish.setOnClickListener(this);
        mDowanloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDownloadMoreBtn.callOnClick();
            }
        });
        mDowanloadMoreText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDownloadMoreBtn.callOnClick();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.download_more_btn:
                mDownloadMoreBtn.setClickable(false);
                AppManager.getInstance().jumpActivityForResult(AppManager.getInstance().currentActivity(), ThemeDownloadActivity.class, null,THEMEREQUESTLIST);
                break;
            case R.id.themeAssetFinish:
                TimelineData.instance().setThemeData(mThemeId);//保存数据
                findThemeCaption();
                TimelineData.instance().setCaptionData(mCaptionDataListClone);
                removeTimeline();
                quitActivity();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case THEMEREQUESTLIST:
                initThemeDataList();
                int selectPos = getSelectedPos();
                mThemeAdapter.setSelectPos(selectPos);
                mThemeAdapter.setThemeDataList(mThemeDataList);
                mThemeAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    private void init(){
        mTimeline = TimelineUtil.createTimeline();
        if(mTimeline == null)
            return;
     
        mCaptionDataListClone = TimelineData.instance().cloneCaptionData();
        mThemeId = TimelineData.instance().getThemeData();
        mTotalDuration = mTimeline.getDuration();
        mThemeDataList = new ArrayList<>();
        mAssetManager = NvAssetManager.sharedInstance();
        mAssetManager.searchLocalAssets(mAssetType);
        String bundlePath = "theme";
        mAssetManager.searchReservedAssets(mAssetType,bundlePath);
    }
    private void quitActivity(){
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        AppManager.getInstance().finishActivity();
    }

    private ArrayList<NvAsset> getBundleData(){
        return mAssetManager.getReservedAssets(mAssetType,NvAsset.AspectRatio_All,0);
    }

    private ArrayList<NvAsset> getLocalData(){
        return mAssetManager.getUsableAssets(mAssetType,NvAsset.AspectRatio_All,0);
    }
    private void initVideoFragment() {
        mVideoFragment = new VideoFragment();
        mVideoFragment.setFragmentLoadFinisedListener(new VideoFragment.OnFragmentLoadFinisedListener() {
            @Override
            public void onLoadFinished() {
                mVideoFragment.seekTimeline(mStreamingContext.getTimelineCurrentPosition(mTimeline),0);
            }
        });
        mVideoFragment.setTimeline(mTimeline);
        Bundle bundle = new Bundle();
        bundle.putInt("titleHeight",mTitleBar.getLayoutParams().height);
        bundle.putInt("bottomHeight",mBottomLayout.getLayoutParams().height);
        bundle.putBoolean("playBarVisible",true);
        bundle.putInt("ratio", TimelineData.instance().getMakeRatio());
        mVideoFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.video_layout, mVideoFragment)
                .commit();
        getSupportFragmentManager().beginTransaction().show(mVideoFragment);
    }
    private void initThemeViewList() {
        mThemeAdapter = new ThemeAdapter(this);
        mThemeAdapter.setThemeDataList(mThemeDataList);
        int selectedPos = getSelectedPos();
        if(selectedPos >= 0) {
            mThemeAdapter.setSelectPos(selectedPos);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mThemeRecyclerView.setLayoutManager(linearLayoutManager);
        mThemeRecyclerView.setAdapter(mThemeAdapter);

        mThemeAdapter.setOnItemClickListener(new ThemeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view,int position) {
                //添加主题
                mThemeId = mThemeDataList.get(position).getPackageId();
                TimelineUtil.applyTheme(mTimeline, mThemeId);
                mVideoFragment.updateTotalDuarationText();
                mVideoFragment.updateSeekBarMaxValue();
                long curDuration = mTimeline.getDuration();
                if(mTotalDuration != curDuration){
                    mTotalDuration = curDuration;
                    mVideoFragment.updateCurPlayTime(0);
                    mVideoFragment.updateSeekBarProgress(0);
                }
           
                //播放视频
                mVideoFragment.playVideoButtonCilck();
            }

            @Override
            public void onSameItemClick() {
                //播放视频
                mVideoFragment.playVideoButtonCilck();
            }
        });
    }

    private void initThemeDataList(){
        mThemeDataList.clear();

        FilterItem filterItem = new FilterItem();
        filterItem.setFilterMode(FilterItem.FILTERMODE_BUILTIN);
        filterItem.setFilterName("无");
        filterItem.setImageId(R.mipmap.no);
        mThemeDataList.add(filterItem);

        ArrayList<NvAsset> themeList = getLocalData();

        String bundlePath =  "theme/info.txt";
        Util.getBundleFilterInfo(this, themeList, bundlePath);
        int ratio = TimelineData.instance().getMakeRatio();
        for(NvAsset asset:themeList) {
            if((ratio & asset.aspectRatio) == 0)
                continue;

            FilterItem newFilterItem = new FilterItem();
            if(asset.isReserved) {
                newFilterItem.setFilterMode(FilterItem.FILTERMODE_BUNDLE);
            } else {
                newFilterItem.setFilterMode(FilterItem.FILTERMODE_PACKAGE);
            }
            newFilterItem.setFilterName(asset.name);
            newFilterItem.setPackageId(asset.uuid);
            newFilterItem.setImageUrl(asset.coverUrl);
            mThemeDataList.add(newFilterItem);
        }

        if(TextUtils.isEmpty(mThemeId))
            return;


        int selectedPos = getSelectedPos();
        if(selectedPos >= 0 && mThemeAdapter != null) {
            mThemeAdapter.setSelectPos(selectedPos);
        }
    }

    private int getSelectedPos() {
        if(mThemeDataList == null || mThemeDataList.size() == 0)
            return -1;

        if(TextUtils.isEmpty(mThemeId))
            return 0;

        for(int i=0;i<mThemeDataList.size();i++) {
            FilterItem assetItem = mThemeDataList.get(i);
            if(assetItem == null)
                continue;
            if(assetItem.getPackageId() != null && mThemeId.equals(assetItem.getPackageId())) {
                return i;
            }
        }
        return 0;
    }
    //查找数据结构里的主题字幕数据并删除,然后再查找主题字幕，如果有主题字幕则存储主题字幕数据。
    private void findThemeCaption(){
        removeThemeCaptionData();
        NvsTimelineCaption caption = mTimeline.getFirstCaption();
        while (caption != null){
            int captionCategory = caption.getCategory();
            if(captionCategory == NvsTimelineCaption.THEME_CATEGORY){
                float zVal = getCurCaptionZVal();
                caption.setZValue(zVal);
                CaptionInfo captionInfo = Util.saveCaptionData(caption);
                if(captionInfo != null)
                    mCaptionDataListClone.add(captionInfo);
            }
            caption = mTimeline.getNextCaption(caption);
        }
    }

    private void removeThemeCaptionData(){
        int count = mCaptionDataListClone.size();
        for(int index = count - 1; index >= 0;--index){
            int captionCategory = mCaptionDataListClone.get(index).getCaptionCategory();
            if(captionCategory == 2){//主题字幕删除
                mCaptionDataListClone.remove(index);
            }
        }
    }
    private float getCurCaptionZVal(){
        float zVal = 0.0f;
        NvsTimelineCaption caption = mTimeline.getFirstCaption();
        while (caption != null){
            float tmpZVal = caption.getZValue();
            if(tmpZVal > zVal)
                zVal = tmpZVal;
            caption = mTimeline.getNextCaption(caption);
        }
        zVal += 1.0;
        return zVal;
    }

    @Override
    public void onBackPressed() {
        removeTimeline();
        AppManager.getInstance().finishActivity();
        super.onBackPressed();
    }

    private void removeTimeline(){
        TimelineUtil.removeTimeline(mTimeline);
        mTimeline = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDownloadMoreBtn.setClickable(true);
    }
}
