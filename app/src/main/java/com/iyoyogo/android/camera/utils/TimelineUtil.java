package com.iyoyogo.android.camera.utils;


import android.graphics.PointF;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.Log;

import com.iyoyogo.android.app.Constants;
import com.iyoyogo.android.camera.data.FilterItem;
import com.iyoyogo.android.camera.edit.watermark.WaterMarkData;
import com.iyoyogo.android.camera.utils.dataInfo.CaptionInfo;
import com.iyoyogo.android.camera.utils.dataInfo.ClipInfo;
import com.iyoyogo.android.camera.utils.dataInfo.MusicInfo;
import com.iyoyogo.android.camera.utils.dataInfo.RecordAudioInfo;
import com.iyoyogo.android.camera.utils.dataInfo.StickerInfo;
import com.iyoyogo.android.camera.utils.dataInfo.TimelineData;
import com.iyoyogo.android.camera.utils.dataInfo.TransitionInfo;
import com.iyoyogo.android.camera.utils.dataInfo.VideoClipFxInfo;
import com.meicam.sdk.NvsAudioClip;
import com.meicam.sdk.NvsAudioResolution;
import com.meicam.sdk.NvsAudioTrack;
import com.meicam.sdk.NvsColor;
import com.meicam.sdk.NvsRational;
import com.meicam.sdk.NvsSize;
import com.meicam.sdk.NvsStreamingContext;
import com.meicam.sdk.NvsTimeline;
import com.meicam.sdk.NvsTimelineAnimatedSticker;
import com.meicam.sdk.NvsTimelineCaption;
import com.meicam.sdk.NvsVideoClip;
import com.meicam.sdk.NvsVideoFx;
import com.meicam.sdk.NvsVideoResolution;
import com.meicam.sdk.NvsVideoTrack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/5/29.
 */

public class TimelineUtil {
    private static      String TAG       = "TimelineUtil";
    public final static long   TIME_BASE = 1000000;

    public static NvsTimeline createTimeline() {
        NvsTimeline timeline = newTimeline(null);
        if (timeline == null) {
            Log.e(TAG, "failed to create timeline");
            return null;
        }
        if (!buildVideoTrack(timeline)) {
            return timeline;
        }

        timeline.appendAudioTrack(); // 音乐轨道
        timeline.appendAudioTrack(); // 录音轨道

//        setTimelineData(timeline);

        return timeline;
    }

    public static NvsTimeline createSingleClipTimeline(ClipInfo clipInfo, boolean isTrimClip) {
        NvsTimeline timeline = newTimeline(null);
        if (timeline == null) {
            Log.e(TAG, "failed to create timeline");
            return null;
        }
        buildSingleClipVideoTrack(timeline, clipInfo, isTrimClip);
        return timeline;
    }

    public static NvsTimeline createSingleClipTimelineExt(NvsVideoResolution videoEditRes, String filePath) {
        NvsTimeline timeline = newTimeline(videoEditRes);
        if (timeline == null) {
            Log.e(TAG, "failed to create timeline");
            return null;
        }
        buildSingleClipVideoTrackExt(timeline, filePath);
        return timeline;
    }

    public static boolean buildSingleClipVideoTrack(NvsTimeline timeline, ClipInfo clipInfo, boolean isTrimClip) {
        if (timeline == null || clipInfo == null) {
            return false;
        }

        NvsVideoTrack videoTrack = timeline.appendVideoTrack();
        if (videoTrack == null) {
            Log.e(TAG, "failed to append video track");
            return false;
        }
        addVideoClip(videoTrack, clipInfo, isTrimClip);
        return true;
    }

    public static boolean buildSingleClipVideoTrackExt(NvsTimeline timeline, String filePath) {
        if (timeline == null || filePath == null) {
            return false;
        }

        NvsVideoTrack videoTrack = timeline.appendVideoTrack();
        if (videoTrack == null) {
            Log.e(TAG, "failed to append video track");
            return false;
        }
        NvsVideoClip videoClip = videoTrack.appendClip(filePath);
        if (videoClip == null) {
            Log.e(TAG, "failed to append video clip");
            return false;
        }
        videoClip.changeTrimOutPoint(8000000, true);
        return true;
    }

    public static void setTimelineData(NvsTimeline timeline) {
        if (timeline == null)
            return;
        // 此处注意是clone一份音乐数据，因为添加主题的接口会把音乐数据删掉
        List<MusicInfo> musicInfoClone = TimelineData.instance().cloneMusicData();
        String          themeId        = TimelineData.instance().getThemeData();
        applyTheme(timeline, themeId);

        if (musicInfoClone != null) {
            TimelineData.instance().setMusicList(musicInfoClone);
            buildTimelineMusic(timeline, musicInfoClone);
        }

        VideoClipFxInfo videoClipFxData = TimelineData.instance().getVideoClipFxData();
        buildTimelineFilter(timeline, videoClipFxData);
        TransitionInfo transitionInfo = TimelineData.instance().getTransitionData();
        setTransition(timeline, transitionInfo);
        ArrayList<StickerInfo> stickerArray = TimelineData.instance().getStickerData();
        setSticker(timeline, stickerArray);

        ArrayList<CaptionInfo> captionArray = TimelineData.instance().getCaptionData();
        setCaption(timeline, captionArray);

        ArrayList<RecordAudioInfo> recordArray = TimelineData.instance().getRecordAudioData();
        buildTimelineRecordAudio(timeline, recordArray);

        WaterMarkData waterMarkData = TimelineData.instance().getWaterMarkData();
//        setWaterMark(timeline, waterMarkData);
    }

    private static void setWaterMark(NvsTimeline timeline, WaterMarkData waterMarkData) {
        if (timeline != null && waterMarkData != null) {
            timeline.addWatermark(waterMarkData.getPicPath(), waterMarkData.getPicWidth(), waterMarkData.getPicHeight(), 1,
                    NvsTimeline.NvsTimelineWatermarkPosition_TopRight, waterMarkData.getExcursionX(), waterMarkData.getExcursionY());
        }
    }

    public static boolean removeTimeline(NvsTimeline timeline) {
        if (timeline == null)
            return false;

        NvsStreamingContext context = NvsStreamingContext.getInstance();
        if (context == null)
            return false;

        return context.removeTimeline(timeline);
    }

    public static boolean buildVideoTrack(NvsTimeline timeline) {
        if (timeline == null) {
            return false;
        }

        NvsVideoTrack videoTrack = timeline.appendVideoTrack();
        if (videoTrack == null) {
            Log.e(TAG, "failed to append video track");
            return false;
        }

        ArrayList<ClipInfo> videoClipArray = TimelineData.instance().getClipInfoData();
        for (int i = 0; i < videoClipArray.size(); i++) {
            ClipInfo clipInfo = videoClipArray.get(i);
            addVideoClip(videoTrack, clipInfo, true);
        }
        float videoVolume = TimelineData.instance().getOriginVideoVolume();
        videoTrack.setVolumeGain(videoVolume, videoVolume);

        return true;
    }

    public static boolean reBuildVideoTrack(NvsTimeline timeline) {
        if (timeline == null) {
            return false;
        }
        int           videoTrackCount = timeline.videoTrackCount();
        NvsVideoTrack videoTrack      = videoTrackCount == 0 ? timeline.appendVideoTrack() : timeline.getVideoTrackByIndex(0);
        if (videoTrack == null) {
            Log.e(TAG, "failed to append video track");
            return false;
        }
        videoTrack.removeAllClips();
        ArrayList<ClipInfo> videoClipArray = TimelineData.instance().getClipInfoData();
        for (int i = 0; i < videoClipArray.size(); i++) {
            ClipInfo clipInfo = videoClipArray.get(i);
            addVideoClip(videoTrack, clipInfo, true);
        }
        setTimelineData(timeline);
        float videoVolume = TimelineData.instance().getOriginVideoVolume();
        videoTrack.setVolumeGain(videoVolume, videoVolume);

        return true;
    }

    private static void addVideoClip(NvsVideoTrack videoTrack, ClipInfo clipInfo, boolean isTrimClip) {
        if (videoTrack == null || clipInfo == null)
            return;
        String       filePath  = clipInfo.getFilePath();
        NvsVideoClip videoClip = videoTrack.appendClip(filePath);
        if (videoClip == null) {
            Log.e(TAG, "failed to append video clip");
            return;
        }

        boolean blurFlag = ParameterSettingValues.instance().isUseBackgroudBlur();
        if (blurFlag) {
            videoClip.setSourceBackgroundMode(NvsVideoClip.ClIP_BACKGROUNDMODE_BLUR);
        }

        float brightVal     = clipInfo.getBrightnessVal();
        float contrastVal   = clipInfo.getContrastVal();
        float saturationVal = clipInfo.getSaturationVal();
        if (brightVal >= 0 || contrastVal >= 0 || saturationVal >= 0) {
            NvsVideoFx videoFxColor = videoClip.appendBuiltinFx("Color Property");
            if (videoFxColor != null) {
                if (brightVal >= 0)
                    videoFxColor.setFloatVal("Brightness", brightVal);
                if (contrastVal >= 0)
                    videoFxColor.setFloatVal("Contrast", contrastVal);
                if (saturationVal >= 0)
                    videoFxColor.setFloatVal("Saturation", saturationVal);
            }
        }
        int videoType = videoClip.getVideoType();
        if (videoType == NvsVideoClip.VIDEO_CLIP_TYPE_IMAGE) {//当前片段是图片
            long trimIn  = videoClip.getTrimIn();
            long trimOut = clipInfo.getTrimOut();
            if (trimOut > 0 && trimOut > trimIn) {
                videoClip.changeTrimOutPoint(trimOut, true);
            }
            int imgDisplayMode = clipInfo.getImgDispalyMode();
            if (imgDisplayMode == Constants.EDIT_MODE_PHOTO_AREA_DISPLAY) {//区域显示
                videoClip.setImageMotionMode(NvsVideoClip.IMAGE_CLIP_MOTIONMMODE_ROI);
                RectF normalStartRectF = clipInfo.getNormalStartROI();
                RectF normalEndRectF   = clipInfo.getNormalEndROI();
                if (normalStartRectF != null && normalEndRectF != null) {
                    videoClip.setImageMotionROI(normalStartRectF, normalEndRectF);
                }
            } else {//全图显示
                videoClip.setImageMotionMode(NvsVideoClip.CLIP_MOTIONMODE_LETTERBOX_ZOOMIN);
            }

            boolean isOpenMove = clipInfo.isOpenPhotoMove();
            videoClip.setImageMotionAnimationEnabled(isOpenMove);
        } else {//当前片段是视频
            float volumeGain = clipInfo.getVolume();
            videoClip.setVolumeGain(volumeGain, volumeGain);
            float pan  = clipInfo.getPan();
            float scan = clipInfo.getScan();
            videoClip.setPanAndScan(pan, scan);
            float speed = clipInfo.getSpeed();
            if (speed > 0) {
                videoClip.changeSpeed(speed);
            }
            videoClip.setExtraVideoRotation(clipInfo.getRotateAngle());
            int scaleX = clipInfo.getScaleX();
            int scaleY = clipInfo.getScaleY();
            if (scaleX >= -1 || scaleY >= -1) {
                NvsVideoFx videoFxTransform = videoClip.appendBuiltinFx("Transform 2D");
                if (videoFxTransform != null) {
                    if (scaleX >= -1)
                        videoFxTransform.setFloatVal("Scale X", scaleX);
                    if (scaleY >= -1)
                        videoFxTransform.setFloatVal("Scale Y", scaleY);
                }
            }

            if (!isTrimClip)//如果当前是裁剪页面，不裁剪片段
                return;
            long trimIn  = clipInfo.getTrimIn();
            long trimOut = clipInfo.getTrimOut();
            if (trimIn > 0) {
                videoClip.changeTrimInPoint(trimIn, true);
            }
            if (trimOut > 0 && trimOut > trimIn) {
                videoClip.changeTrimOutPoint(trimOut, true);
            }
        }
    }

    public static boolean buildTimelineFilter(NvsTimeline timeline, VideoClipFxInfo videoClipFxData) {
        if (timeline == null) {
            return false;
        }

        NvsVideoTrack videoTrack = timeline.getVideoTrackByIndex(0);
        if (videoTrack == null) {
            return false;
        }

        if (videoClipFxData == null)
            return false;

        ArrayList<ClipInfo> clipInfos = TimelineData.instance().getClipInfoData();

        int videoClipCount = videoTrack.getClipCount();
        for (int i = 0; i < videoClipCount; i++) {
            NvsVideoClip clip = videoTrack.getClipByIndex(i);
            if (clip == null)
                continue;

            removeAllVideoFx(clip);
            String  clipFilPath     = clip.getFilePath();
            boolean isSrcVideoAsset = false;
            for (ClipInfo clipInfo : clipInfos) {
                String videoFilePath = clipInfo.getFilePath();
                if (clipFilPath.equals(videoFilePath)) {
                    isSrcVideoAsset = true;
                    break;
                }
            }

            if (!isSrcVideoAsset)
                continue;

            String name = videoClipFxData.getFxId();
            if (TextUtils.isEmpty(name)) {
                continue;
            }
            int   mode        = videoClipFxData.getFxMode();
            float fxIntensity = videoClipFxData.getFxIntensity();
            if (mode == FilterItem.FILTERMODE_BUILTIN) {//内建特效
                NvsVideoFx fx = clip.appendBuiltinFx(name);
                fx.setFilterIntensity(fxIntensity);
            } else {////添加包裹特效
                NvsVideoFx fx = clip.appendPackagedFx(name);
                fx.setFilterIntensity(fxIntensity);
            }
        }

        return true;
    }


    public static boolean buildTimelineFilters(NvsTimeline timeline, VideoClipFxInfo videoClipFxData) {
        if (timeline == null) {
            return false;
        }

        NvsVideoTrack videoTrack = timeline.getVideoTrackByIndex(0);
        if (videoTrack == null) {
            return false;
        }

        if (videoClipFxData == null)
            return false;

        ArrayList<ClipInfo> clipInfos = TimelineData.instance().getClipInfoData();

        int videoClipCount = videoTrack.getClipCount();
        for (int i = 0; i < videoClipCount; i++) {
            NvsVideoClip clip = videoTrack.getClipByIndex(i);
            if (clip == null)
                continue;

            removeAllVideoFx(clip);
            String  clipFilPath     = clip.getFilePath();
            boolean isSrcVideoAsset = false;
            for (ClipInfo clipInfo : clipInfos) {
                String videoFilePath = clipInfo.getFilePath();
                if (clipFilPath.equals(videoFilePath)) {
                    isSrcVideoAsset = true;
                    break;
                }
            }

//            if(!isSrcVideoAsset)
//                continue;

            String name = videoClipFxData.getFxId();
            if (TextUtils.isEmpty(name)) {
                continue;
            }
            int   mode        = videoClipFxData.getFxMode();
            float fxIntensity = videoClipFxData.getFxIntensity();
            if (mode == FilterItem.FILTERMODE_BUILTIN) {//内建特效
                NvsVideoFx fx = clip.appendBuiltinFx(name);
                fx.setFilterIntensity(fxIntensity);
            } else {////添加包裹特效
                NvsVideoFx fx = clip.appendPackagedFx(name);
                fx.setFilterIntensity(fxIntensity);
            }
        }

        return true;
    }

    public static boolean applyTheme(NvsTimeline timeline, String themeId) {
        if (timeline == null)
            return false;

        timeline.removeCurrentTheme();
        if (themeId == null || themeId.isEmpty())
            return false;

        if (!timeline.applyTheme(themeId)) {
            Log.e(TAG, "failed to apply theme");
            return false;
        }
        timeline.setThemeMusicVolumeGain(1.0f, 1.0f);

        // 应用主题之后，要把已经应用的背景音乐去掉
        TimelineData.instance().setMusicList(null);
        TimelineUtil.buildTimelineMusic(timeline, null);
        return true;
    }

    private static boolean removeAllVideoFx(NvsVideoClip videoClip) {
        if (videoClip == null)
            return false;

        int fxCount = videoClip.getFxCount();
        for (int i = 0; i < fxCount; i++) {
            NvsVideoFx fx = videoClip.getFxByIndex(i);
            if (fx == null)
                continue;

            String name = fx.getBuiltinVideoFxName();
            Log.e("===>", "fx name: " + name);
            if (name.equals("Color Property") || name.equals("Transform 2D")) {
                continue;
            }
            videoClip.removeFx(i);
            i--;
        }
        return true;
    }

    public static boolean setTransition(NvsTimeline timeline, TransitionInfo transitionInfo) {
        if (timeline == null) {
            return false;
        }

        NvsVideoTrack videoTrack = timeline.getVideoTrackByIndex(0);
        if (videoTrack == null) {
            return false;
        }

        if (transitionInfo == null)
            return false;

        int videoClipCount = videoTrack.getClipCount();
        if (videoClipCount <= 1)
            return false;

        for (int i = 0; i < videoClipCount - 1; i++) {
            if (transitionInfo.getTransitionMode() == TransitionInfo.TRANSITIONMODE_BUILTIN) {
                videoTrack.setBuiltinTransition(i, transitionInfo.getTransitionId());
            } else {
                videoTrack.setPackagedTransition(i, transitionInfo.getTransitionId());
            }
        }

        return true;
    }

    public static boolean buildTimelineMusic(NvsTimeline timeline, List<MusicInfo> musicInfos) {
        if (timeline == null) {
            return false;
        }
        NvsAudioTrack audioTrack = timeline.getAudioTrackByIndex(0);
        if (audioTrack == null) {
            return false;
        }
        if (musicInfos == null || musicInfos.isEmpty()) {
            audioTrack.removeAllClips();

            // 去掉音乐之后，要把已经应用的主题中的音乐还原
            String pre_theme_id = TimelineData.instance().getThemeData();
            if (pre_theme_id != null && !pre_theme_id.isEmpty()) {
                timeline.setThemeMusicVolumeGain(1.0f, 1.0f);
            }
            return false;
        }
        audioTrack.removeAllClips();
        for (MusicInfo oneMusic : musicInfos) {
            if (oneMusic == null) {
                continue;
            }
            NvsAudioClip audioClip = audioTrack.addClip(oneMusic.getFilePath(), oneMusic.getInPoint(), oneMusic.getTrimIn(), oneMusic.getTrimOut());
            if (audioClip != null) {
                audioClip.setFadeInDuration(oneMusic.getFadeDuration());
                if (oneMusic.getExtraMusic() <= 0 && oneMusic.getExtraMusicLeft() <= 0) {
                    audioClip.setFadeOutDuration(oneMusic.getFadeDuration());
                }
            }
            if (oneMusic.getExtraMusic() > 0) {
                for (int i = 0; i < oneMusic.getExtraMusic(); ++i) {
                    NvsAudioClip extra_clip = audioTrack.addClip(oneMusic.getFilePath(),
                            oneMusic.getOriginalOutPoint() + i * (oneMusic.getOriginalOutPoint() - oneMusic.getOriginalInPoint()),
                            oneMusic.getOriginalTrimIn(), oneMusic.getOriginalTrimOut());
                    if (extra_clip != null) {
                        extra_clip.setAttachment(Constants.MUSIC_EXTRA_AUDIOCLIP, oneMusic.getInPoint());
                        if (i == oneMusic.getExtraMusic() - 1 && oneMusic.getExtraMusicLeft() <= 0) {
                            extra_clip.setAttachment(Constants.MUSIC_EXTRA_LAST_AUDIOCLIP, oneMusic.getInPoint());
                            extra_clip.setFadeOutDuration(oneMusic.getFadeDuration());
                        }
                    }
                }
            }
            if (oneMusic.getExtraMusicLeft() > 0) {
                NvsAudioClip extra_clip = audioTrack.addClip(oneMusic.getFilePath(),
                        oneMusic.getOriginalOutPoint() + oneMusic.getExtraMusic() * (oneMusic.getOriginalOutPoint() - oneMusic.getOriginalInPoint()),
                        oneMusic.getOriginalTrimIn(),
                        oneMusic.getOriginalTrimIn() + oneMusic.getExtraMusicLeft());
                if (extra_clip != null) {
                    extra_clip.setAttachment(Constants.MUSIC_EXTRA_AUDIOCLIP, oneMusic.getInPoint());
                    extra_clip.setAttachment(Constants.MUSIC_EXTRA_LAST_AUDIOCLIP, oneMusic.getInPoint());
                    extra_clip.setFadeOutDuration(oneMusic.getFadeDuration());
                }
            }
        }
        float audioVolume = TimelineData.instance().getMusicVolume();
        audioTrack.setVolumeGain(audioVolume, audioVolume);

        // 应用音乐之后，要把已经应用的主题中的音乐去掉
        String pre_theme_id = TimelineData.instance().getThemeData();
        if (pre_theme_id != null && !pre_theme_id.isEmpty()) {
            timeline.setThemeMusicVolumeGain(0, 0);
        }
        return true;
    }

    public static void buildTimelineRecordAudio(NvsTimeline timeline, ArrayList<RecordAudioInfo> recordAudioInfos) {
        if (timeline == null) {
            return;
        }
        NvsAudioTrack audioTrack = timeline.getAudioTrackByIndex(1);
        if (audioTrack != null) {
            audioTrack.removeAllClips();
            if (recordAudioInfos != null) {
                for (int i = 0; i < recordAudioInfos.size(); ++i) {
                    RecordAudioInfo recordAudioInfo = recordAudioInfos.get(i);
                    if (recordAudioInfo == null) {
                        continue;
                    }
                    NvsAudioClip audioClip = audioTrack.addClip(recordAudioInfo.getPath(), recordAudioInfo.getInPoint(), recordAudioInfo.getTrimIn(),
                            recordAudioInfo.getOutPoint() - recordAudioInfo.getInPoint() + recordAudioInfo.getTrimIn());
                    if (audioClip != null) {
                        audioClip.setVolumeGain(recordAudioInfo.getVolume(), recordAudioInfo.getVolume());
                        if (recordAudioInfo.getFxID() != null && !recordAudioInfo.getFxID().equals(Constants.NO_FX)) {
                            audioClip.appendFx(recordAudioInfo.getFxID());
                        }
                    }
                }
            }
            float audioVolume = TimelineData.instance().getRecordVolume();
            audioTrack.setVolumeGain(audioVolume, audioVolume);
        }
    }

    public static boolean setSticker(NvsTimeline timeline, ArrayList<StickerInfo> stickerArray) {
        if (timeline == null)
            return false;

        NvsTimelineAnimatedSticker deleteSticker = timeline.getFirstAnimatedSticker();
        while (deleteSticker != null) {
            deleteSticker = timeline.removeAnimatedSticker(deleteSticker);
        }

        for (StickerInfo sticker : stickerArray) {
            boolean isCutsomSticker = sticker.isCustomSticker();
            NvsTimelineAnimatedSticker newSticker = isCutsomSticker ?
                    timeline.addCustomAnimatedSticker(sticker.getInPoint(), sticker.getDuration(), sticker.getId(), sticker.getCustomImagePath())
                    : timeline.addAnimatedSticker(sticker.getInPoint(), sticker.getDuration(), sticker.getId());
            if (newSticker == null)
                continue;
            newSticker.setZValue(sticker.getAnimateStickerZVal());
            newSticker.setHorizontalFlip(sticker.isHorizFlip());
            PointF translation = sticker.getTranslation();
            float  scaleFactor = sticker.getScaleFactor();
            float  rotation    = sticker.getRotation();
            newSticker.setScale(scaleFactor);
            newSticker.setRotationZ(rotation);
            newSticker.setTranslation(translation);
            float volumeGain = sticker.getVolumeGain();
            newSticker.setVolumeGain(volumeGain, volumeGain);
        }
        return true;
    }

    public static boolean setCaption(NvsTimeline timeline, ArrayList<CaptionInfo> captionArray) {
        if (timeline == null)
            return false;

        NvsTimelineCaption deleteCaption = timeline.getFirstCaption();
        while (deleteCaption != null) {
            deleteCaption = timeline.removeCaption(deleteCaption);
        }

        for (CaptionInfo caption : captionArray) {
            NvsTimelineCaption newCaption = timeline.addCaption(caption.getText(), caption.getInPoint(),
                    caption.getDuration(), null);
            updateCaptionAttribute(newCaption, caption);
        }
        return true;
    }

    private static void updateCaptionAttribute(NvsTimelineCaption newCaption, CaptionInfo caption) {
        if (newCaption == null || caption == null)
            return;

        //字幕StyleUuid需要首先设置，后面设置的字幕属性才会生效，
        // 因为字幕样式里面可能自带偏移，缩放，旋转等属性，最后设置会覆盖前面的设置的。
        newCaption.applyCaptionStyle(caption.getCaptionStyleUuid());
        int alignVal = caption.getAlignVal();
        if (alignVal >= 0)
            newCaption.setTextAlignment(alignVal);
        NvsColor textColor = ColorUtil.colorStringtoNvsColor(caption.getCaptionColor());
        if (textColor != null) {
            textColor.a = caption.getCaptionColorAlpha() / 100.0f;
            newCaption.setTextColor(textColor);
        }

        // 放缩字幕
        float scaleFactorX = caption.getScaleFactorX();
        float scaleFactorY = caption.getScaleFactorY();
        newCaption.setScaleX(scaleFactorX);
        newCaption.setScaleY(scaleFactorY);

        float rotation = caption.getRotation();
        // 旋转字幕
        newCaption.setRotationZ(rotation);
        newCaption.setZValue(caption.getCaptionZVal());
        boolean hasOutline = caption.isHasOutline();
        newCaption.setDrawOutline(hasOutline);
        if (hasOutline) {
            NvsColor outlineColor = ColorUtil.colorStringtoNvsColor(caption.getOutlineColor());
            if (outlineColor != null) {
                outlineColor.a = caption.getOutlineColorAlpha() / 100.0f;
                newCaption.setOutlineColor(outlineColor);
                newCaption.setOutlineWidth(caption.getOutlineWidth());
            }
        }
        String fontPath = caption.getCaptionFont();
        if (!fontPath.isEmpty())
            newCaption.setFontByFilePath(fontPath);
        boolean isBold = caption.isBold();

        newCaption.setBold(isBold);
        boolean isItalic = caption.isItalic();
        newCaption.setItalic(isItalic);
        boolean isShadow = caption.isShadow();
        newCaption.setDrawShadow(isShadow);
        if (isShadow) {
            PointF   offset      = new PointF(7, -7);
            NvsColor shadowColor = new NvsColor(0, 0, 0, 0.5f);
            newCaption.setShadowOffset(offset);  //字幕阴影偏移量
            newCaption.setShadowColor(shadowColor); // 字幕阴影颜色
        }
        float fontSize = caption.getCaptionSize();
        if (fontSize >= 0)
            newCaption.setFontSize(fontSize);
        PointF translation = caption.getTranslation();
        if (translation != null)
            newCaption.setCaptionTranslation(translation);
    }

    public static NvsTimeline newTimeline(NvsVideoResolution videoResolution) {
        NvsStreamingContext context = NvsStreamingContext.getInstance();
        if (context == null) {
            Log.e(TAG, "failed to get streamingContext");
            return null;
        }

        NvsVideoResolution videoEditRes = videoResolution != null ? videoResolution : TimelineData.instance().getVideoResolution();
        videoEditRes.imagePAR = new NvsRational(1, 1);
        NvsRational videoFps = new NvsRational(25, 1);

        NvsAudioResolution audioEditRes = new NvsAudioResolution();
        audioEditRes.sampleRate = 44100;
        audioEditRes.channelCount = 2;

        NvsTimeline timeline = context.createTimeline(videoEditRes, videoFps, audioEditRes);
        return timeline;
    }

    public static NvsSize getTimelineSize(NvsTimeline timeline) {
        NvsSize size = new NvsSize(0, 0);
        if (timeline != null) {
            NvsVideoResolution resolution = timeline.getVideoRes();
            size.width = resolution.imageWidth;
            size.height = resolution.imageHeight;
            return size;
        }
        return null;
    }
}
