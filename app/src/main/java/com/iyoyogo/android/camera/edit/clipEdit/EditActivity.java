package com.iyoyogo.android.camera.edit.clipEdit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.app.Constants;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.camera.data.AssetInfoDescription;
import com.iyoyogo.android.camera.data.BackupData;
import com.iyoyogo.android.camera.edit.adapter.AssetRecyclerViewAdapter;
import com.iyoyogo.android.camera.edit.adapter.SpaceItemDecoration;
import com.iyoyogo.android.camera.edit.clipEdit.adjust.AdjustActivity;
import com.iyoyogo.android.camera.edit.clipEdit.correctionColor.CorrectionColorActivity;
import com.iyoyogo.android.camera.edit.clipEdit.photo.DurationActivity;
import com.iyoyogo.android.camera.edit.clipEdit.photo.PhotoMovementActivity;
import com.iyoyogo.android.camera.edit.clipEdit.speed.SpeedActivity;
import com.iyoyogo.android.camera.edit.clipEdit.spilt.SpiltActivity;
import com.iyoyogo.android.camera.edit.clipEdit.trim.TrimActivity;
import com.iyoyogo.android.camera.edit.clipEdit.volume.VolumeActivity;
import com.iyoyogo.android.camera.edit.grallyRecyclerView.GrallyAdapter;
import com.iyoyogo.android.camera.edit.grallyRecyclerView.GrallyScaleHelper;
import com.iyoyogo.android.camera.interfaces.OnGrallyItemClickListener;
import com.iyoyogo.android.camera.interfaces.OnItemClickListener;
import com.iyoyogo.android.camera.selectmedia.SelectMediaActivity;
import com.iyoyogo.android.camera.utils.AppManager;
import com.iyoyogo.android.camera.utils.DensityUtil;
import com.iyoyogo.android.camera.utils.Util;
import com.iyoyogo.android.camera.utils.dataInfo.ClipInfo;
import com.iyoyogo.android.camera.utils.dataInfo.TimelineData;

import com.iyoyogo.android.widget.CustomTitleBar;
import com.meicam.sdk.NvsAVFileInfo;
import com.meicam.sdk.NvsStreamingContext;


import java.util.ArrayList;

public class EditActivity extends BaseActivity {
    String TAG = getClass().getName();
    public static final int CLIPTRIM_REQUESTCODE = 101;//裁剪
    public static final int CLIPSPILTPOINT_REQUESTCODE = 102;//分割
    public static final int CLIPCORRECTIONCOLOR_REQUESTCODE = 103;//校色
    public static final int CLIPADJUST_REQUESTCODE = 104;//调整
    public static final int CLIPSPEED_REQUESTCODE = 105;//速度
    public static final int CLIPVOLUME_REQUESTCODE = 106;//音量
    public static final int ADDVIDEO_REQUESTCODE = 107;//添加视频
    public static final int PHOTODURATION_REQUESTCODE = 108;//图片时长
    public static final int PHOTOMOVE_REQUESTCODE = 109;//图片运动

    private CustomTitleBar mEditCustomTitleBar;
    private RecyclerView mGrallyRecyclerView;
    private RecyclerView mEffectRecyclerView;
    private int[] ImageId_Video = {R.drawable.capture, R.drawable.division,
            R.drawable.amend, R.drawable.adjust, R.drawable.speed,
            R.drawable.volume, R.drawable.copy, R.drawable.delete};
    private int[] ImageId_Image = {R.drawable.speed, R.drawable.adjust, R.drawable.amend, R.drawable.copy, R.drawable.delete};
    private AssetRecyclerViewAdapter mAssetRecycleAdapter;
    private ArrayList<AssetInfoDescription> mArrayAssetInfoVideo = new ArrayList<>();
    private ArrayList<AssetInfoDescription> mArrayAssetInfoImage = new ArrayList<>();
    private GrallyAdapter mGrallyAdapter;
    private ArrayList<ClipInfo> mClipInfoArray = new ArrayList<>();
    private GrallyScaleHelper mGrallyScaleHelper;
    private int mCurrentPos = 0;
    private ImageView mEditCommitButton;
    private boolean m_waitFlag;
    private int mAddVideoPostion = 0;
    private NvsStreamingContext mStreamingContext;
    private boolean mIsImage = false;



    @Override
    protected void initView() {
        mEditCustomTitleBar = (CustomTitleBar) findViewById(R.id.title_bar);
        mGrallyRecyclerView = (RecyclerView) findViewById(R.id.editClipRecyclerView);
        mEffectRecyclerView = (RecyclerView) findViewById(R.id.effectRecyclerView);
        mEditCommitButton = (ImageView)findViewById(R.id.edit_commitButton);
        m_waitFlag = true;
//        AppManager.getInstance().jumpActivityForResult(AppManager.getInstance().currentActivity(), TrimActivity.class, null);
        Intent intent = new Intent(EditActivity.this, TrimActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }


//    public void initTitle() {
//
//    }

    @Override
    public void initTitle() {
        super.initTitle();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mEditCustomTitleBar.setTextCenter(getResources().getString(R.string.edit));
        mEditCustomTitleBar.setBackImageVisible(View.GONE);
        mStreamingContext = NvsStreamingContext.getInstance();
        mClipInfoArray = TimelineData.instance().cloneClipInfoData();
        BackupData.instance().setClipIndex(0);
        BackupData.instance().setClipInfoData(mClipInfoArray);
        String[] AssetNameVideo = getResources().getStringArray(R.array.effectNamesVideo);
        for (int i = 0; i < AssetNameVideo.length; i++) {
            mArrayAssetInfoVideo.add(new AssetInfoDescription(AssetNameVideo[i], ImageId_Video[i]));
        }
        String[] AssetNameImage = getResources().getStringArray(R.array.effectNamesImage);
        for (int i = 0; i < AssetNameImage.length; i++) {
            mArrayAssetInfoImage.add(new AssetInfoDescription(AssetNameImage[i], ImageId_Image[i]));
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mGrallyRecyclerView.setLayoutManager(linearLayoutManager);
        mGrallyAdapter = new GrallyAdapter(getApplicationContext());
        mGrallyAdapter.setClipInfoArray(mClipInfoArray);
        mGrallyRecyclerView.setAdapter(mGrallyAdapter);

        ItemTouchHelper.Callback callback = new com.iyoyogo.android.camera.edit.grallyRecyclerView.ItemTouchHelper(mGrallyAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mGrallyRecyclerView);
        mGrallyScaleHelper = new GrallyScaleHelper();
        mGrallyScaleHelper.attachToRecyclerView(mGrallyRecyclerView);
        //效果列表
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mEffectRecyclerView.setLayoutManager(layoutManager);
        mAssetRecycleAdapter = new AssetRecyclerViewAdapter(this);
        mEffectRecyclerView.setAdapter(mAssetRecycleAdapter);
        mEffectRecyclerView.addItemDecoration(new SpaceItemDecoration(DensityUtil.dip2px(this, 10), DensityUtil.dip2px(this, 13)));
        if(mClipInfoArray.size() > 0) {
            ClipInfo clipInfo = mClipInfoArray.get(0);
            updateOperateMenu(clipInfo);
        }

        mAssetRecycleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                if(m_waitFlag)
                    return;

                if(!mIsImage) {
                    switch (pos) {
                        case 0:
                            m_waitFlag = true;
                            AppManager.getInstance().jumpActivityForResult(AppManager.getInstance().currentActivity(), TrimActivity.class, null, EditActivity.CLIPTRIM_REQUESTCODE);
                            break;
                        case 1: // 分割
                            m_waitFlag = true;
                            AppManager.getInstance().jumpActivityForResult(AppManager.getInstance().currentActivity(), SpiltActivity.class, null, EditActivity.CLIPSPILTPOINT_REQUESTCODE);
                            break;
                        case 2: // 校色
                            m_waitFlag = true;
                            AppManager.getInstance().jumpActivityForResult(AppManager.getInstance().currentActivity(), CorrectionColorActivity.class, null, EditActivity.CLIPCORRECTIONCOLOR_REQUESTCODE);
                            break;
                        case 3: // 调整
                            m_waitFlag = true;
                            AppManager.getInstance().jumpActivityForResult(AppManager.getInstance().currentActivity(), AdjustActivity.class, null, EditActivity.CLIPADJUST_REQUESTCODE);
                            break;
                        case 4: // 速度
                            m_waitFlag = true;
                            AppManager.getInstance().jumpActivityForResult(AppManager.getInstance().currentActivity(), SpeedActivity.class, null, EditActivity.CLIPSPEED_REQUESTCODE);
                            break;
                        case 5: // 音量
                            m_waitFlag = true;
                            AppManager.getInstance().jumpActivityForResult(AppManager.getInstance().currentActivity(), VolumeActivity.class, null, EditActivity.CLIPVOLUME_REQUESTCODE);
                            break;
                        case 6: // 复制
                            copyMediaAsset();
                            break;
                        case 7: // 删除
                            deleteMediaAsset();
                            break;
                        default:
                            break;
                    }
                } else {
                    switch (pos) {
                        case 0: // 时长
                            m_waitFlag = true;
                            AppManager.getInstance().jumpActivityForResult(AppManager.getInstance().currentActivity(), DurationActivity.class, null, EditActivity.PHOTODURATION_REQUESTCODE);
                            break;
                        case 1: // 运动
                            m_waitFlag = true;
                            AppManager.getInstance().jumpActivityForResult(AppManager.getInstance().currentActivity(), PhotoMovementActivity.class, null, EditActivity.PHOTOMOVE_REQUESTCODE);
                            break;
                        case 2: // 校色
                            m_waitFlag = true;
                            AppManager.getInstance().jumpActivityForResult(AppManager.getInstance().currentActivity(), CorrectionColorActivity.class, null, EditActivity.CLIPCORRECTIONCOLOR_REQUESTCODE);
                            break;
                        case 3: // 复制
                            copyMediaAsset();
                            break;
                        case 4: // 删除
                            deleteMediaAsset();
                            break;
                        default:
                            break;
                    }
                }
            }
        });

        mGrallyAdapter.setOnItemSelectedListener(new OnGrallyItemClickListener() {
            @Override
            public void onLeftItemClick(View view, int pos) {
                reAddMediaAsset(pos);
            }

            @Override
            public void onRightItemClick(View view, int pos) {
                reAddMediaAsset(pos);
            }

            @Override
            public void onItemMoved(int fromPosition, int toPosition) {
//                Collections.swap(mClipInfoArray,fromPosition,toPosition);
            }

            @Override
            public void onItemDismiss(int position) {
                mClipInfoArray.remove(position);
            }

            @Override
            public void removeall() {
                mClipInfoArray.clear();
            }
        });

        mGrallyScaleHelper.setOnItemSelectedListener(new GrallyScaleHelper.OnGrallyItemSelectListener() {
            @Override
            public void onItemSelect(int pos) {
                if(pos < 0 || pos >= mClipInfoArray.size()) {
                    return;
                }
                mCurrentPos = pos;
                BackupData.instance().setClipIndex(mCurrentPos);
                ClipInfo clipInfo = mClipInfoArray.get(pos);
                updateOperateMenu(clipInfo);
            }
        });

        mEditCommitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimelineData.instance().setClipInfoData(mClipInfoArray);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                AppManager.getInstance().finishActivity();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        if(data == null)
            return;

        ArrayList<ClipInfo> addCipInfoList = BackupData.instance().getAddClipInfoList();
        switch (requestCode) {
            case CLIPTRIM_REQUESTCODE:
                break;
            case CLIPSPILTPOINT_REQUESTCODE:
                break;
            case CLIPCORRECTIONCOLOR_REQUESTCODE:
                break;
            case CLIPADJUST_REQUESTCODE:
                break;
            case CLIPSPEED_REQUESTCODE:
                break;
            case CLIPVOLUME_REQUESTCODE:
                break;
            case ADDVIDEO_REQUESTCODE:
                break;
            case PHOTODURATION_REQUESTCODE:
                break;
            case PHOTOMOVE_REQUESTCODE:
                break;
            default:
                break;
        }

        mClipInfoArray = BackupData.instance().getClipInfoData();
        if(addCipInfoList.size() > 0){
            mClipInfoArray.addAll(mAddVideoPostion,addCipInfoList);
            BackupData.instance().setClipInfoData(mClipInfoArray);
            BackupData.instance().clearAddClipInfoList();
        }
        mGrallyAdapter.setClipInfoArray(mClipInfoArray);
        mGrallyAdapter.notifyDataSetChanged();
        ClipInfo clipInfo = mClipInfoArray.get(mCurrentPos);
        updateOperateMenu(clipInfo);
    }

    @Override
    protected void onResume() {
        super.onResume();
        m_waitFlag = false;
    }

    private void reAddMediaAsset(int pos){
        mAddVideoPostion = pos;
        Bundle bundle = new Bundle();
        bundle.putInt("visitMethod",Constants.FROMCLIPEDITACTIVITYTOVISIT);
        BackupData.instance().clearAddClipInfoList();
        AppManager.getInstance().jumpActivityForResult(EditActivity.this, SelectMediaActivity.class, bundle,ADDVIDEO_REQUESTCODE);
    }

    private void copyMediaAsset(){
        if (mClipInfoArray.size() == 0)
            return;
        int count = mClipInfoArray.size();
        if (mCurrentPos < 0 || mCurrentPos > count)
            return;
        mClipInfoArray.add(mCurrentPos, mClipInfoArray.get(mCurrentPos));
        mGrallyAdapter.setSelectPos(mCurrentPos);
        mGrallyAdapter.setClipInfoArray(mClipInfoArray);
        mGrallyAdapter.notifyDataSetChanged();
        //复制移动到下一个位置
        mGrallyRecyclerView.smoothScrollBy(mGrallyScaleHelper.getmOnePageWidth(), 0);
    }
    private void deleteMediaAsset(){
        if (mClipInfoArray.size() == 0)
            return;

        if (mClipInfoArray.size() == 1) {
            Util.showDialog(EditActivity.this, "提示", "至少保留一个素材");
            return;
        }
        int clipCount = mClipInfoArray.size();
        if (mCurrentPos < 0 || mCurrentPos >= clipCount){
            return;
        }
        mClipInfoArray.remove(mCurrentPos);
        if (mCurrentPos == clipCount - 1) {
            mCurrentPos--;
        }
        mGrallyAdapter.setClipInfoArray(mClipInfoArray);
        mGrallyAdapter.setSelectPos(mCurrentPos);
        mGrallyAdapter.notifyDataSetChanged();
        mGrallyScaleHelper.resetCurrentOffset(mCurrentPos);
        BackupData.instance().setClipIndex(mCurrentPos);
        // 更新操作菜单
        ClipInfo clipInfo = mClipInfoArray.get(mCurrentPos);
        updateOperateMenu(clipInfo);
    }
    private boolean isImage(ClipInfo clipInfo) {
        if(clipInfo != null) {
            NvsAVFileInfo avFileInfo = mStreamingContext.getAVFileInfo(clipInfo.getFilePath());
            if(avFileInfo != null) {
                if(avFileInfo.getAVFileType() == NvsAVFileInfo.AV_FILE_TYPE_IMAGE) {
                    return true;
                }
            }
        }
        return false;
    }

    private void updateOperateMenu(ClipInfo clipInfo) {
        if(mStreamingContext != null && clipInfo != null) {
            mIsImage = isImage(clipInfo);
            if(mAssetRecycleAdapter != null) {
                mAssetRecycleAdapter.updateData(mIsImage ? mArrayAssetInfoImage : mArrayAssetInfoVideo);
            }
        }
    }
}
