package com.iyoyogo.android.view.flowlayout;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by le on 17/7/11.
 */
public class JLHorizontalScrollView extends HorizontalScrollView {

    private int mBaseScrollX;
    private int mScreenWidth;

    private int mRealWidth;


    private LinearLayout mContainer;
    private boolean      flag;
    private int          mPageCount;

    private Context mContext;

    private int mScrollX = 200;

    private List<Bean> mList;

    private int mMargin;

    private int mIndex;

    private List<Bean> mData = new ArrayList<>();

    private List<JLFlowLayout> mJLFlowLayoutList = new ArrayList<>();


    /**
     * 绘制完成回调,用来添加 radiobutton
     */
    private OnCompleteCallback mOnCompleteCallback;


    public JLHorizontalScrollView(Context context) {
        this(context, null);
    }

    public JLHorizontalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JLHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        //去除滚动条
        setHorizontalScrollBarEnabled(false);
        //去除阴影效果
        setOverScrollMode(OVER_SCROLL_NEVER);
        setFillViewport(true);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mScreenWidth = dm.widthPixels;
        LinearLayout linearLayout = new LinearLayout(getContext());
        addView(linearLayout);
    }


    public void setData(final List<Bean> list, final OnCompleteCallback onCompleteCallback) {
        //延迟以获取距离屏幕左侧的距离用来支持 margin 和 padding
        post(new Runnable() {
            @Override
            public void run() {
                mData.clear();
                if (mContainer != null) {
                    mContainer.removeAllViews();
                    mPageCount = 0;
                }
                mOnCompleteCallback = onCompleteCallback;
                int[] xy = new int[2];
                getLocationOnScreen(xy);
                mMargin = xy[0];
                mRealWidth = mScreenWidth - mMargin * 2;
                if (list == null) {
                    return;
                }
                mList = list;
                JLFlowLayout jlFlowLayout = new JLFlowLayout(mContext);
                jlFlowLayout.setOnFillCallback(onFillCallback);
                mJLFlowLayoutList.add(jlFlowLayout);
                addPage(jlFlowLayout);
                fillData(mList, jlFlowLayout);
            }
        });

    }


    private void fillData(final List<Bean> list, JLFlowLayout jlFlowLayout) {
        for (int i = 0; i < mList.size(); i++) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, dp2px(13), dp2px(5), 0);
            View      view       = LayoutInflater.from(getContext()).inflate(R.layout.tag_textview, null);
            TextView  textView   = view.findViewById(R.id.tag_textview);
            ImageView img_choice = view.findViewById(R.id.iv_select);
            textView.setText(list.get(i).getLabel());
            view.setLayoutParams(lp);
            final int pos = i;
            if (list.get(pos).getType() == 1) {
                textView.setBackgroundResource(R.drawable.label_bg_fkzn);
                textView.setTextColor(Color.parseColor("#5BCBF5"));

            } else if (list.get(pos).getType() == 2) {
                textView.setBackgroundResource(R.drawable.label_bg_deserve_to_do);
                textView.setTextColor(Color.parseColor("#E0FF6100"));
            } else if (list.get(pos).getType() == 3) {
                textView.setBackgroundResource(R.drawable.label_bg_exclusive);
                textView.setTextColor(Color.parseColor("#ff8484"));
            }
            textView.setText(list.get(pos).getLabel());
            img_choice.setVisibility(list.get(pos).isSelect() ? VISIBLE : GONE);
            if (list.get(pos).isSelect()) {
                mData.add(list.get(pos));
            }
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!list.get(pos).isSelect()) {
                        list.get(pos).setSelect(true);

                        mData.add(list.get(pos));
                    } else {
                        list.get(pos).setSelect(false);
                        mData.remove(list.get(pos));
                    }
                    img_choice.setVisibility(list.get(pos).isSelect() ? VISIBLE : GONE);
                }
            });
//            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            layoutParams.addRule(RelativeLayout.RIGHT_OF,R.id.tag_textview);
//            img_choose.setLayoutParams(layoutParams);
//            jlFlowLayout.addView(img_choose);
            jlFlowLayout.addView(view);
        }
    }

    public List<Bean> getData() {
        return mData;
    }

    public interface OnClickListeners {
        void setOnDeleteClick(View v, int position);

        void setOnAddClick(View v, int position);
    }

    private OnClickListeners onClickListener;

    public void setOnClickListener(OnClickListeners onClickListener) {
        this.onClickListener = onClickListener;
    }

    /**
     * 填满一页返回再填下一页
     */
    JLFlowLayout.OnFillCallback onFillCallback = new JLFlowLayout.OnFillCallback() {
        @Override
        public void onFill(final int index) {
            //一个一个加载,否则回调顺序就乱掉了
            post(new Runnable() {
                @Override
                public void run() {
                    int count = mList.size();
                    if (index > count) {
                        return;
                    }
                    //subList生成子列表后，不要试图去操作原列表 解决ConcurrentModificationException
                    List<Bean> cache = new ArrayList<Bean>();
                    cache.addAll(mList.subList(index, count));
                    if (cache.isEmpty()) {
                        mOnCompleteCallback.onComplete(mPageCount);
                        return;
                    }
                    mList = cache;
                    JLFlowLayout jlFlowLayout = new JLFlowLayout(mContext);
                    jlFlowLayout.setOnFillCallback(onFillCallback);
                    mJLFlowLayoutList.add(jlFlowLayout);
                    addPage(jlFlowLayout);
                    fillData(cache, jlFlowLayout);
                }
            });
        }
    };


    public void addPage(View page) {
        if (!flag) {
            mContainer = (LinearLayout) getChildAt(0);
            flag = true;
        }
        //为了支持 margin 再套一层,还有个解决办法是 JLFlowLayout加上对 padding 的支持。
        FrameLayout               child        = new FrameLayout(getContext());
        LinearLayout.LayoutParams params       = new LinearLayout.LayoutParams(mRealWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        LayoutParams              layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = mMargin;
        layoutParams.rightMargin = mMargin;
        page.setLayoutParams(layoutParams);
        child.addView(page);
        mContainer.addView(child, params);
        mPageCount++;
    }


    /**
     * 获取相对滑动位置。由右向左滑动，返回正值；由左向右滑动，返回负值。
     *
     * @return
     */
    private int getBaseScrollX() {
        return getScrollX() - mBaseScrollX;
    }

    /**
     * 使相对于基线移动x距离。
     *
     * @param x x为正值时右移；为负值时左移。
     */
    private void baseSmoothScrollTo(int x) {
        smoothScrollTo(x + mBaseScrollX, 0);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                int scrollX = getBaseScrollX();
                //左滑
                if (scrollX > mScrollX) {
                    mIndex++;
                    mOnCompleteCallback.onScroll(mIndex);
                    baseSmoothScrollTo(mRealWidth);
                    mBaseScrollX += mRealWidth;
                }
                //左滑，不到一半，返回原位
                else if (scrollX > 0) {
                    baseSmoothScrollTo(0);
                }
                //右滑，不到一半，返回原位
                else if (scrollX > -mScrollX) {
                    baseSmoothScrollTo(0);
                }
                //右滑
                else {
                    mIndex--;
                    mOnCompleteCallback.onScroll(mIndex);
                    baseSmoothScrollTo(-mRealWidth);
                    mBaseScrollX -= mRealWidth;
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    public interface OnCompleteCallback {
        void onComplete(int count);

        void onScroll(int index);
    }

    public int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

