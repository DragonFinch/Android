package com.iyoyogo.android.widget;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.iyoyogo.android.R;
import com.iyoyogo.android.app.Constants;
import com.iyoyogo.android.camera.utils.ImageConverter;
import com.orhanobut.logger.Logger;

import java.util.List;

import static com.iyoyogo.android.app.Constants.HANDCLICK_DURATION;
import static com.iyoyogo.android.app.Constants.HANDMOVE_DISTANCE;

/**
 * Created by Administrator on 2018/6/20.
 */

public class DrawRect extends View {
    private final static String TAG = "DrawRect";
    private OnTouchListener mListener;
    private onDrawRectClickListener mDrawRectClickListener;
    private onStickerMuteListenser mStickerMuteListenser;
    private PointF prePointF = new PointF(0, 0);
    private RectF alignRectF = new RectF();
    private RectF horizFlipRectF = new RectF();
    private RectF rotationRectF = new RectF();
    private RectF deleteRectF = new RectF();
    private RectF muteRectF = new RectF();
    private List<PointF> mListPointF;
    private Path rectPath = new Path();
    private boolean canScalOrRotate = false;
    private boolean canHorizFlipClick = false;
    private boolean canMuteClick = false;
    private boolean isInnerDrawRect = false;
    private boolean canDel = false;
    private boolean canAlignClick = false;
    private int mIndex = 0;
    private int viewMode = 0;
    private int mStickerMuteIndex = 0;
    private boolean mHasAudio = false;
    private Bitmap rotationImgBtn = BitmapFactory.decodeResource(getResources(), R.mipmap.scale);
    private Bitmap alignImgArray[] = {BitmapFactory.decodeResource(getResources(), R.mipmap.left_align),
            BitmapFactory.decodeResource(getResources(), R.mipmap.center_align),
            BitmapFactory.decodeResource(getResources(), R.mipmap.right_align)};
    private Bitmap deleteImgBtn = BitmapFactory.decodeResource(getResources(), R.mipmap.delete);
    private Bitmap horizontalFlipImgBtn = BitmapFactory.decodeResource(getResources(), R.mipmap.horizontal_flip);
    private Bitmap muteImgArray[] = {BitmapFactory.decodeResource(getResources(), R.mipmap.stickerunmute),
            BitmapFactory.decodeResource(getResources(), R.mipmap.stickermute)};
    private long mPrevMillionSecond = 0;
    private double mClickMoveDistance = 0.0D;
    private Paint mRectPaint = new Paint();
    private boolean mMoveOutScreen = false;
    //在方框内绘制的图片路径
    private String filePath;
    private Bitmap waterMarkBitmap;

    public DrawRect(Context context) {
        this(context, null);
    }

    public DrawRect(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initRectPaint();
    }

    private void initRectPaint() {
        mRectPaint = new Paint();
        // 设置颜色
        mRectPaint.setColor(Color.parseColor("#4A90E2"));
        // 设置抗锯齿
        mRectPaint.setAntiAlias(true);
        // 设置线宽
        mRectPaint.setStrokeWidth(8);
        // 设置非填充
        mRectPaint.setStyle(Paint.Style.STROKE);
    }

    public void setAlignIndex(int index) {
        mIndex = index;
        invalidate();
    }

    public void setStickerMuteIndex(int index) {
        mStickerMuteIndex = index;
        invalidate();
    }

    public void setMuteVisible(boolean hasAudio) {
        mHasAudio = hasAudio;
        invalidate();
    }

    public void setDrawRect(List<PointF> list, int mode) {
        mListPointF = list;
        viewMode = mode;
        invalidate();
    }

    public List<PointF> getDrawRect() {
        return mListPointF;
    }

    public void setOnTouchListener(OnTouchListener listener) {
        mListener = listener;
    }

    public void setDrawRectClickListener(onDrawRectClickListener drawRectClickListener) {
        this.mDrawRectClickListener = drawRectClickListener;
    }

    public void setStickerMuteListenser(onStickerMuteListenser stickerMuteListenser) {
        this.mStickerMuteListenser = stickerMuteListenser;
    }

    @SuppressLint("DrawAllocation")
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mListPointF == null || mListPointF.size() < 4) {
            return;
        }
        rectPath.reset();
        rectPath.moveTo(mListPointF.get(0).x, mListPointF.get(0).y);
        rectPath.lineTo(mListPointF.get(1).x, mListPointF.get(1).y);
        rectPath.lineTo(mListPointF.get(2).x, mListPointF.get(2).y);
        rectPath.lineTo(mListPointF.get(3).x, mListPointF.get(3).y);
        rectPath.close();
        canvas.drawPath(rectPath,mRectPaint);
        if (viewMode == Constants.EDIT_MODE_CAPTION) {//绘制字幕对其按钮
            canvas.drawBitmap(alignImgArray[mIndex], mListPointF.get(0).x - alignImgArray[mIndex].getHeight() / 2, mListPointF.get(0).y - alignImgArray[mIndex].getWidth() / 2, mRectPaint);
            alignRectF.set(mListPointF.get(0).x - alignImgArray[mIndex].getWidth() / 2, mListPointF.get(0).y - alignImgArray[mIndex].getHeight() / 2, mListPointF.get(0).x + alignImgArray[mIndex].getWidth() / 2, mListPointF.get(0).y + alignImgArray[mIndex].getWidth() / 2);
        } else if (viewMode == Constants.EDIT_MODE_STICKER) {//绘制水平翻转按钮
            canvas.drawBitmap(horizontalFlipImgBtn, mListPointF.get(0).x - horizontalFlipImgBtn.getHeight() / 2, mListPointF.get(0).y - horizontalFlipImgBtn.getWidth() / 2, mRectPaint);
            horizFlipRectF.set(mListPointF.get(0).x - horizontalFlipImgBtn.getWidth() / 2, mListPointF.get(0).y - horizontalFlipImgBtn.getHeight() / 2, mListPointF.get(0).x + horizontalFlipImgBtn.getWidth() / 2, mListPointF.get(0).y + horizontalFlipImgBtn.getHeight() / 2);
            if (mHasAudio) {
                canvas.drawBitmap(muteImgArray[mStickerMuteIndex], mListPointF.get(1).x - muteImgArray[mStickerMuteIndex].getHeight() / 2, mListPointF.get(1).y - muteImgArray[mStickerMuteIndex].getWidth() / 2, mRectPaint);
                muteRectF.set(mListPointF.get(1).x - muteImgArray[mStickerMuteIndex].getWidth() / 2, mListPointF.get(1).y - muteImgArray[mStickerMuteIndex].getHeight() / 2, mListPointF.get(1).x + muteImgArray[mStickerMuteIndex].getWidth() / 2, mListPointF.get(1).y + muteImgArray[mStickerMuteIndex].getHeight() / 2);
            } else {
                muteRectF.set(0, 0, 0, 0);
            }
        } else if (viewMode == Constants.EDIT_MODE_WATERMARK) {
            if (waterMarkBitmap != null) {
                canvas.drawBitmap(waterMarkBitmap, new Rect(0, 0, waterMarkBitmap.getWidth(), waterMarkBitmap.getHeight()), new RectF(mListPointF.get(0).x, mListPointF.get(0).y, mListPointF.get(2).x, mListPointF.get(2).y), null);
            }
        }
        // 绘制旋转放缩按钮
        canvas.drawBitmap(rotationImgBtn, mListPointF.get(2).x - rotationImgBtn.getHeight() / 2, mListPointF.get(2).y - rotationImgBtn.getWidth() / 2, mRectPaint);
        rotationRectF.set(mListPointF.get(2).x - rotationImgBtn.getWidth() / 2, mListPointF.get(2).y - rotationImgBtn.getHeight() / 2, mListPointF.get(2).x + rotationImgBtn.getWidth() / 2, mListPointF.get(2).y + rotationImgBtn.getHeight() / 2);
        //绘制删除按钮
        canvas.drawBitmap(deleteImgBtn, mListPointF.get(3).x - deleteImgBtn.getWidth() / 2, mListPointF.get(3).y - deleteImgBtn.getHeight() / 2, mRectPaint);
        deleteRectF.set(mListPointF.get(3).x - deleteImgBtn.getWidth() / 2, mListPointF.get(3).y - deleteImgBtn.getHeight() / 2, mListPointF.get(3).x + deleteImgBtn.getWidth() / 2, mListPointF.get(3).y + deleteImgBtn.getHeight() / 2);
    }


    public boolean curPointIsInnerDrawRect(int xPos, int yPos) {
        // 判断手指是否在字幕框内
        RectF r = new RectF();
        Path path = new Path();
        path.moveTo(mListPointF.get(0).x, mListPointF.get(0).y);
        path.lineTo(mListPointF.get(1).x, mListPointF.get(1).y);
        path.lineTo(mListPointF.get(2).x, mListPointF.get(2).y);
        path.lineTo(mListPointF.get(3).x, mListPointF.get(3).y);
        path.close();
        path.computeBounds(r, true);
        Region region = new Region();
        region.setPath(path, new Region((int) r.left, (int) r.top, (int) r.right, (int) r.bottom));
        return region.contains(xPos, yPos);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float targetX = event.getX();
        float targetY = event.getY();
        if (mListPointF != null) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    mPrevMillionSecond = System.currentTimeMillis();
                    canScalOrRotate = rotationRectF.contains(targetX, targetY);
                    canDel = deleteRectF.contains(targetX, targetY);
                    if (viewMode == Constants.EDIT_MODE_CAPTION) {
                        canAlignClick = alignRectF.contains(targetX, targetY);
                    } else if (viewMode == Constants.EDIT_MODE_STICKER) {
                        canHorizFlipClick = horizFlipRectF.contains(targetX, targetY);
                        canMuteClick = muteRectF.contains(targetX, targetY);
                    }

                    if (mListener != null) {
                        mListener.onTouchDown(new PointF(targetX, targetY));
                    }

                    if (mListPointF != null && mListPointF.size() == 4) {
                        // 判断手指是否在字幕框内
                        isInnerDrawRect = curPointIsInnerDrawRect((int) targetX, (int) targetY);
                    }
                    prePointF.set(targetX,targetY);
                    break;
                }

                case MotionEvent.ACTION_UP: {
                    long moveTime_up = System.currentTimeMillis() - mPrevMillionSecond;
                    if (mClickMoveDistance < HANDMOVE_DISTANCE && moveTime_up <= HANDCLICK_DURATION) {
                        if (viewMode == Constants.EDIT_MODE_CAPTION) {
                            if (isInnerDrawRect) {
                                if (mDrawRectClickListener != null)
                                    mDrawRectClickListener.onDrawRectClick();
                            } else if (!(canScalOrRotate || canDel || canAlignClick)) {
                                if (mListener != null)
                                    mListener.onBeyondDrawRectClick();
                            }
                        } else if (viewMode == Constants.EDIT_MODE_STICKER) {
                            if (!isInnerDrawRect && !(canScalOrRotate || canDel || canHorizFlipClick || canMuteClick)) {
                                if (mListener != null)
                                    mListener.onBeyondDrawRectClick();
                            }
                        }
                    }

                    if (canDel && mListener != null)
                        mListener.onDel();
                    if (viewMode == Constants.EDIT_MODE_CAPTION) {
                        if (canAlignClick && mListener != null)
                            mListener.onAlignClick();
                    } else if (viewMode == Constants.EDIT_MODE_STICKER) {
                        if (canHorizFlipClick && mListener != null)
                            mListener.onHorizFlipClick();
                        if (canMuteClick && mStickerMuteListenser != null)
                            mStickerMuteListenser.onStickerMute();
                    }

                    canDel = false;
                    canScalOrRotate = false;
                    isInnerDrawRect = false;

                    canAlignClick = false;
                    canHorizFlipClick = false;
                    canMuteClick = false;
                    mClickMoveDistance = 0.0D;
                    break;
                }

                case MotionEvent.ACTION_MOVE: {
                    mClickMoveDistance = Math.sqrt(Math.pow(targetX - prePointF.x, 2) + Math.pow(targetY - prePointF.y, 2));
                    Logger.e(TAG,"mClickMoveDistanceDrawRect = " + mClickMoveDistance);
                    // 防止移出屏幕
                    if(targetX <= 100 || targetX >= getWidth()  || targetY >= getHeight() || targetY <= 20 ){
                        mMoveOutScreen = true;
                        break;
                    }
                    if(mMoveOutScreen){
                        mMoveOutScreen = false;
                        break;
                    }

                    // 计算字幕框中心点
                    PointF centerPointF = new PointF();
                    if (mListPointF != null && mListPointF.size() == 4) {
                        centerPointF.x = (mListPointF.get(0).x + mListPointF.get(2).x) / 2;
                        centerPointF.y = (mListPointF.get(0).y + mListPointF.get(2).y) / 2;
                    }

                    if (mListener != null && canScalOrRotate) {
                        isInnerDrawRect = false;
                        // 计算手指在屏幕上滑动的距离比例
                        double temp = Math.pow(prePointF.x - centerPointF.x, 2) + Math.pow(prePointF.y - centerPointF.y, 2);
                        double preLength = Math.sqrt(temp);
                        double temp2 = Math.pow(targetX - centerPointF.x, 2) + Math.pow(targetY - centerPointF.y, 2);
                        double length = Math.sqrt(temp2);
                        float offset = (float) (length / preLength);

                        // 计算手指滑动的角度
                        float radian = (float) (Math.atan2(targetY - centerPointF.y, targetX - centerPointF.x)
                                - Math.atan2(prePointF.y - centerPointF.y, prePointF.x - centerPointF.x));
                        // 弧度转换为角度
                        float angle = (float) (radian * 180 / Math.PI);
                        mListener.onScaleAndRotate(offset, new PointF(centerPointF.x, centerPointF.y), -angle);
                    }

                    if (mListener != null && isInnerDrawRect) {
                        mListener.onDrag(prePointF, new PointF(targetX, targetY));
                    }
                    prePointF.set(targetX,targetY);
                }
                break;
            }
        }

        return true;
    }

    public interface OnTouchListener {
        void onDrag(PointF prePointF, PointF nowPointF);

        void onScaleAndRotate(float scaleFactor, PointF anchor, float rotation);

        void onDel();

        void onTouchDown(PointF curPoint);

        void onAlignClick();

        void onHorizFlipClick();

        void onBeyondDrawRectClick();//超出字幕或者贴纸框部分点击回调
    }

    public interface onDrawRectClickListener {
        void onDrawRectClick();//矩形框点击,只是用于字幕修改文本
    }

    public interface onStickerMuteListenser {
        void onStickerMute();//贴纸静音回调
    }


    public void setPicturePath(String filePath) {
        this.filePath = filePath == null ? "" : filePath;
        waterMarkBitmap = ImageConverter.convertImage(filePath);
    }

    public Point getPicturePoint() {
        return ImageConverter.getPicturePoint(filePath);
    }
}
