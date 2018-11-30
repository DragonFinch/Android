package com.iyoyogo.android.widget;


public class CropImageView  {
   /* private Paint mBorderPaint;
    private Paint mGuidelinePaint;
    private Paint mCornerPaint;
    private float mScaleRadius;
    private float mCornerThickness;
    private float mBorderThickness;
    private float mCornerLength;
    private RectF mBitmapRect = new RectF();
    private PointF mTouchOffset = new PointF();
    private CropWindowEdgeSelector mPressedCropWindowEdgeSelector;

    public CropImageView(Context context) {
        super(context);
        this.init(context);
    }

    public CropImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init(context);
    }

    private void init(@NonNull Context context) {
        this.mBorderPaint = new Paint();
        this.mBorderPaint.setStyle(Style.STROKE);
        this.mBorderPaint.setStrokeWidth((float)UIUtil.dip2px(context, 3.0F));
        this.mBorderPaint.setColor(Color.parseColor("#AAFFFFFF"));
        this.mGuidelinePaint = new Paint();
        this.mGuidelinePaint.setStyle(Style.STROKE);
        this.mGuidelinePaint.setStrokeWidth((float)UIUtil.dip2px(context, 1.0F));
        this.mGuidelinePaint.setColor(Color.parseColor("#AAFFFFFF"));
        this.mCornerPaint = new Paint();
        this.mCornerPaint.setStyle(Style.STROKE);
        this.mCornerPaint.setStrokeWidth((float)UIUtil.dip2px(context, 5.0F));
        this.mCornerPaint.setColor(-1);
        this.mScaleRadius = (float)UIUtil.dip2px(context, 24.0F);
        this.mBorderThickness = (float)UIUtil.dip2px(context, 3.0F);
        this.mCornerThickness = (float)UIUtil.dip2px(context, 5.0F);
        this.mCornerLength = (float)UIUtil.dip2px(context, 20.0F);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.mBitmapRect = this.getBitmapRect();
        this.initCropWindow(this.mBitmapRect);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.drawGuidelines(canvas);
        this.drawBorder(canvas);
        this.drawCorners(canvas);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!this.isEnabled()) {
            return false;
        } else {
            switch(event.getAction()) {
                case 0:
                    this.onActionDown(event.getX(), event.getY());
                    return true;
                case 1:
                case 3:
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                    this.onActionUp();
                    return true;
                case 2:
                    this.onActionMove(event.getX(), event.getY());
                    this.getParent().requestDisallowInterceptTouchEvent(true);
                    return true;
                default_ic:
                    return false;
            }
        }
    }

    public Bitmap getCroppedImage() {
        Drawable drawable = this.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            float[] matrixValues = new float[9];
            this.getImageMatrix().getValues(matrixValues);
            float scaleX = matrixValues[0];
            float scaleY = matrixValues[4];
            float transX = matrixValues[2];
            float transY = matrixValues[5];
            float bitmapLeft = transX < 0.0F ? Math.abs(transX) : 0.0F;
            float bitmapTop = transY < 0.0F ? Math.abs(transY) : 0.0F;
            Bitmap originalBitmap = ((BitmapDrawable)drawable).getBitmap();
            float cropX = (bitmapLeft + Edge.LEFT.getCoordinate()) / scaleX;
            float cropY = (bitmapTop + Edge.TOP.getCoordinate()) / scaleY;
            float cropWidth = Math.min(Edge.getWidth() / scaleX, (float)originalBitmap.getWidth() - cropX);
            float cropHeight = Math.min(Edge.getHeight() / scaleY, (float)originalBitmap.getHeight() - cropY);
            return Bitmap.createBitmap(originalBitmap, (int)cropX, (int)cropY, (int)cropWidth, (int)cropHeight);
        } else {
            return null;
        }
    }

    private RectF getBitmapRect() {
        Drawable drawable = this.getDrawable();
        if (drawable == null) {
            return new RectF();
        } else {
            float[] matrixValues = new float[9];
            this.getImageMatrix().getValues(matrixValues);
            float scaleX = matrixValues[0];
            float scaleY = matrixValues[4];
            float transX = matrixValues[2];
            float transY = matrixValues[5];
            int drawableIntrinsicWidth = drawable.getIntrinsicWidth();
            int drawableIntrinsicHeight = drawable.getIntrinsicHeight();
            int drawableDisplayWidth = Math.round((float)drawableIntrinsicWidth * scaleX);
            int drawableDisplayHeight = Math.round((float)drawableIntrinsicHeight * scaleY);
            float left = Math.max(transX, 0.0F);
            float top = Math.max(transY, 0.0F);
            float right = Math.min(left + (float)drawableDisplayWidth, (float)this.getWidth());
            float bottom = Math.min(top + (float)drawableDisplayHeight, (float)this.getHeight());
            return new RectF(left, top, right, bottom);
        }
    }

    private void initCropWindow(@NonNull RectF bitmapRect) {
        float horizontalPadding = 0.01F * bitmapRect.width();
        float verticalPadding = 0.01F * bitmapRect.height();
        Edge.LEFT.initCoordinate(bitmapRect.left + horizontalPadding);
        Edge.TOP.initCoordinate(bitmapRect.top + verticalPadding);
        Edge.RIGHT.initCoordinate(bitmapRect.right - horizontalPadding);
        Edge.BOTTOM.initCoordinate(bitmapRect.bottom - verticalPadding);
    }

    private void drawGuidelines(@NonNull Canvas canvas) {
        float left = Edge.LEFT.getCoordinate();
        float top = Edge.TOP.getCoordinate();
        float right = Edge.RIGHT.getCoordinate();
        float bottom = Edge.BOTTOM.getCoordinate();
        float oneThirdCropWidth = Edge.getWidth() / 3.0F;
        float x1 = left + oneThirdCropWidth;
        canvas.drawLine(x1, top, x1, bottom, this.mGuidelinePaint);
        float x2 = right - oneThirdCropWidth;
        canvas.drawLine(x2, top, x2, bottom, this.mGuidelinePaint);
        float oneThirdCropHeight = Edge.getHeight() / 3.0F;
        float y1 = top + oneThirdCropHeight;
        canvas.drawLine(left, y1, right, y1, this.mGuidelinePaint);
        float y2 = bottom - oneThirdCropHeight;
        canvas.drawLine(left, y2, right, y2, this.mGuidelinePaint);
    }

    private void drawBorder(@NonNull Canvas canvas) {
        canvas.drawRect(Edge.LEFT.getCoordinate(), Edge.TOP.getCoordinate(), Edge.RIGHT.getCoordinate(), Edge.BOTTOM.getCoordinate(), this.mBorderPaint);
    }

    private void drawCorners(@NonNull Canvas canvas) {
        float left = Edge.LEFT.getCoordinate();
        float top = Edge.TOP.getCoordinate();
        float right = Edge.RIGHT.getCoordinate();
        float bottom = Edge.BOTTOM.getCoordinate();
        float lateralOffset = (this.mCornerThickness - this.mBorderThickness) / 2.0F;
        float startOffset = this.mCornerThickness - this.mBorderThickness / 2.0F;
        canvas.drawLine(left - lateralOffset, top - startOffset, left - lateralOffset, top + this.mCornerLength, this.mCornerPaint);
        canvas.drawLine(left - startOffset, top - lateralOffset, left + this.mCornerLength, top - lateralOffset, this.mCornerPaint);
        canvas.drawLine(right + lateralOffset, top - startOffset, right + lateralOffset, top + this.mCornerLength, this.mCornerPaint);
        canvas.drawLine(right + startOffset, top - lateralOffset, right - this.mCornerLength, top - lateralOffset, this.mCornerPaint);
        canvas.drawLine(left - lateralOffset, bottom + startOffset, left - lateralOffset, bottom - this.mCornerLength, this.mCornerPaint);
        canvas.drawLine(left - startOffset, bottom + lateralOffset, left + this.mCornerLength, bottom + lateralOffset, this.mCornerPaint);
        canvas.drawLine(right + lateralOffset, bottom + startOffset, right + lateralOffset, bottom - this.mCornerLength, this.mCornerPaint);
        canvas.drawLine(right + startOffset, bottom + lateralOffset, right - this.mCornerLength, bottom + lateralOffset, this.mCornerPaint);
    }

    private void onActionDown(float x, float y) {
        float left = Edge.LEFT.getCoordinate();
        float top = Edge.TOP.getCoordinate();
        float right = Edge.RIGHT.getCoordinate();
        float bottom = Edge.BOTTOM.getCoordinate();
        this.mPressedCropWindowEdgeSelector = CatchEdgeUtil.getPressedHandle(x, y, left, top, right, bottom, this.mScaleRadius);
        if (this.mPressedCropWindowEdgeSelector != null) {
            CatchEdgeUtil.getOffset(this.mPressedCropWindowEdgeSelector, x, y, left, top, right, bottom, this.mTouchOffset);
            this.invalidate();
        }

    }

    private void onActionUp() {
        if (this.mPressedCropWindowEdgeSelector != null) {
            this.mPressedCropWindowEdgeSelector = null;
            this.invalidate();
        }

    }

    private void onActionMove(float x, float y) {
        if (this.mPressedCropWindowEdgeSelector != null) {
            x += this.mTouchOffset.x;
            y += this.mTouchOffset.y;
            Log.v("==============", x + "=============" + y);
            this.mPressedCropWindowEdgeSelector.updateCropWindow(x, y, this.mBitmapRect);
            this.invalidate();
        }
    }

    public void onYuantu(int s) {
        float x = this.mBitmapRect.right;
        float y = this.mBitmapRect.bottom;
        switch(s) {
            case 1:
                y = x * 1.7777778F;
                break;
            case 2:
                y = x * 1.3333334F;
                break;
            case 3:
                y = x;
                break;
            case 4:
                y = x * 0.75F;
                break;
            default_ic:
                y = this.mBitmapRect.height();
        }

        float left = Edge.LEFT.getCoordinate();
        float top = Edge.TOP.getCoordinate();
        float right = Edge.RIGHT.getCoordinate();
        float bottom = Edge.BOTTOM.getCoordinate();
        this.mPressedCropWindowEdgeSelector = CatchEdgeUtil.getPressedHandle(x, y, 0.0F, 0.0F, x, y, x);
        this.mPressedCropWindowEdgeSelector.updateCropWindow(x, y, this.mBitmapRect);
        this.invalidate();
    }*/
}

