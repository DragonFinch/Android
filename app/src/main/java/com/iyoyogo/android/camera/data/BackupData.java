package com.iyoyogo.android.camera.data;


import com.iyoyogo.android.camera.utils.dataInfo.CaptionInfo;
import com.iyoyogo.android.camera.utils.dataInfo.ClipInfo;

import java.util.ArrayList;

/**
 * Created by admin on 2018/7/one_one.
 */

public class BackupData {
    private static BackupData mAssetDataBackup;
    private ArrayList<CaptionInfo> mCaptionArrayList;
    private ArrayList<ClipInfo> mClipInfoArray;
    private int mCaptionZVal;
    private long m_curSeekTimelinePos = 0;//贴纸和字幕使用
    private int m_clipIndex;//片段编辑专用

    private ArrayList<ClipInfo> mAddClipInfoList;//只在EditActivity点击添加视频使用

    public ArrayList<ClipInfo> getAddClipInfoList() {
        return mAddClipInfoList;
    }

    public void setAddClipInfoList(ArrayList<ClipInfo> addClipInfoList) {
        this.mAddClipInfoList = addClipInfoList;
    }
    public void clearAddClipInfoList() {
        mAddClipInfoList.clear();
    }
    public int getClipIndex() {
        return m_clipIndex;
    }

    public void setClipIndex(int clipIndex) {
        this.m_clipIndex = clipIndex;
    }

    public long getCurSeekTimelinePos() {
        return m_curSeekTimelinePos;
    }

    public void setCurSeekTimelinePos(long curSeekTimelinePos) {
        this.m_curSeekTimelinePos = curSeekTimelinePos;
    }

    public int getCaptionZVal() {
        return mCaptionZVal;
    }

    public void setCaptionZVal(int captionZVal) {
        this.mCaptionZVal = captionZVal;
    }

    public void setCaptionData(ArrayList<CaptionInfo> captionArray) {
        mCaptionArrayList = captionArray;
    }
    public ArrayList<CaptionInfo> getCaptionData() {
        return mCaptionArrayList;
    }

    public ArrayList<CaptionInfo> cloneCaptionData() {
        ArrayList<CaptionInfo> newList = new ArrayList<>();
        for(CaptionInfo captionInfo:mCaptionArrayList) {
            CaptionInfo newCaptionInfo = captionInfo.clone();
            newList.add(newCaptionInfo);
        }
        return newList;
    }

    public void clearCaptionData() {
        mCaptionArrayList.clear();
        mCaptionZVal = 0;
    }

    public void setClipInfoData(ArrayList<ClipInfo> clipInfoArray) {
        this.mClipInfoArray = clipInfoArray;
    }

    public ArrayList<ClipInfo> getClipInfoData() {
        return mClipInfoArray;
    }

    public ArrayList<ClipInfo> cloneClipInfoData() {
        ArrayList<ClipInfo> newArrayList = new ArrayList<>();
        for(ClipInfo clipInfo:mClipInfoArray) {
            ClipInfo newClipInfo = clipInfo.clone();
            newArrayList.add(newClipInfo);
        }
        return newArrayList;
    }

    public static BackupData init() {
        if (mAssetDataBackup == null) {
            synchronized (BackupData.class){
                if (mAssetDataBackup == null) {
                    mAssetDataBackup = new BackupData();
                }
            }
        }
        return mAssetDataBackup;
    }

    public void clear() {
        clearCaptionData();
        clearAddClipInfoList();
        mCaptionZVal = 0;
        m_clipIndex = 0;
        m_curSeekTimelinePos = 0;
    }

    public static BackupData instance() {
        if (mAssetDataBackup == null)
            mAssetDataBackup = new BackupData();
        return mAssetDataBackup;
    }
    private BackupData() {
        mCaptionArrayList = new ArrayList<>();
        mClipInfoArray = new ArrayList<>();
        mAddClipInfoList = new ArrayList<>();
        mCaptionZVal = 0;
        m_clipIndex = 0;
        m_curSeekTimelinePos = 0;
    }
}
