package com.iyoyogo.android.utils.emoji;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iyoyogo.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghuan on 2016/3/9.
 *
 * 带表情的自定义输入框
 */
public class FaceRelativeLayout extends RelativeLayout implements
        OnItemClickListener, OnClickListener {

    private Context context;

    /** 表情页的监听事件 */
    private OnCorpusSelectedListener mListener;

    /** 显示表情页的viewpager */
    private ViewPager vp_face;

    /** 表情页界面集合 */
    private ArrayList<View> pageViews;

    /** 游标显示布局 */
    private LinearLayout layout_point;

    /** 游标点集合 */
    private ArrayList<ImageView> pointViews;

    /** 表情集合 */
    private List<List<ChatEmoji>> emojis;

    /** 表情区域 */
    private View view;

    /** 输入框 */
    private EditText et_sendmessage;

    /** 表情数据填充器 */
    private List<FaceAdapter> faceAdapters;

    /** 当前表情页 */
    private int current = 0;

    //表情的点击时间
    ImageView btn_face;
    private FaceAdapter mAdapter;

    private int popupWidth;
    private int popupHeight;
    private PopupWindow mPopupWindow;

    public FaceRelativeLayout(Context context) {
        super(context);
        this.context = context;
    }

    public FaceRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public FaceRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public void setOnCorpusSelectedListener(OnCorpusSelectedListener listener) {
        mListener = listener;
    }

    /**
     * 表情选择监听
     *
     * @author naibo-liao
     * @时间： 2013-1-15下午04:32:54
     */
    public interface OnCorpusSelectedListener {

        void onCorpusSelected(ChatEmoji emoji);

        void onCorpusDeleted();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        emojis = FaceConversionUtil.getInstace().emojiLists;
        onCreate();
    }

    private void onCreate() {
        Init_View();
        Init_viewPager();
        Init_Point();
        Init_Data();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_face) {// 隐藏表情选择框
            if (view.getVisibility() == View.VISIBLE) {
                view.setVisibility(View.GONE);
                KeyBoardUtils.openKeybord(et_sendmessage,context);
              //  btn_face.setImageResource(R.drawable.ruanjianpan);
            } else {
                view.setVisibility(View.VISIBLE);
                KeyBoardUtils.closeKeybord(et_sendmessage,context);
               // btn_face.setImageResource(R.drawable.btn_emoji);
            }

        } else if (i == R.id.et_sendmessage) {// 隐藏表情选择框
            if (view.getVisibility() == View.VISIBLE) {
                view.setVisibility(View.GONE);
            }

        }
    }

    /**
     * 隐藏表情选择框
     */
    public boolean hideFaceView() {
        // 隐藏表情选择框
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
            return true;
        }
        return false;
    }

    //软件盘的显示和隐藏
    /**
     * 初始化控件
     */
    private void Init_View() {
        vp_face = (ViewPager) findViewById(R.id.vp_contains);
        et_sendmessage = (EditText) findViewById(R.id.et_sendmessage);
        layout_point = (LinearLayout) findViewById(R.id.iv_image);
        et_sendmessage.setOnClickListener(this);
        btn_face = (ImageView) findViewById(R.id.btn_face);
    /*    ImageView imgemo = (ImageView) findViewById(R.id.item_iv_face);
        //图片的点击时间
        imgemo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"一点击",Toast.LENGTH_SHORT).show();
            }
        });*/
        btn_face.setOnClickListener(this);
        view = findViewById(R.id.ll_facechoose);
    /*    SoftKeyBoardListener.setListener((Activity) context, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
               // ToastUtils.toastShort("显示"+height);
                Toast.makeText(context,"显示"+height,Toast.LENGTH_SHORT).show();
                view.setVisibility(GONE);
            }

            @Override
            public void keyBoardHide(int height) {
                Toast.makeText(context,"隐藏"+height,Toast.LENGTH_SHORT).show();
                //ToastUtils.toastShort("隐藏 "+height);
                view.setVisibility(VISIBLE);
            }
        });*/
    }

    /**
     * 初始化显示表情的viewpager
     */
    private void Init_viewPager() {
        pageViews = new ArrayList<View>();
        // 左侧添加空页
        View nullView1 = new View(context);
        // 设置透明背景
        nullView1.setBackgroundColor(Color.TRANSPARENT);
        pageViews.add(nullView1);

        // 中间添加表情页

        faceAdapters = new ArrayList<FaceAdapter>();
        for (int i = 0; i < emojis.size(); i++) {
            GridView view = new GridView(context);
            mAdapter = new FaceAdapter(context, emojis.get(i));
            view.setAdapter(mAdapter);
            faceAdapters.add(mAdapter);
            view.setOnItemClickListener(this);
            view.setNumColumns(7);
            view.setBackgroundColor(Color.TRANSPARENT);
            view.setHorizontalSpacing(1);
            view.setVerticalSpacing(1);
            view.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            view.setCacheColorHint(0);
            view.setPadding(5, 0, 5, 0);
            view.setSelector(new ColorDrawable(Color.TRANSPARENT));
            view.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
            view.setGravity(Gravity.CENTER);
            pageViews.add(view);
            view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(context,"",Toast.LENGTH_SHORT).show();
               /*    mAdapter.setSetoncli(new FaceAdapter.setoncli() {
                       @Override
                       public void set(ImageView iv_face) {
                            initView(iv_face,position);
                       }
                   });*/
                    return true;
                }
            });
        }


        // 右侧添加空页面
        View nullView2 = new View(context);
        // 设置透明背景
        nullView2.setBackgroundColor(Color.TRANSPARENT);
        pageViews.add(nullView2);
    }

    private void initView(ImageView iv_face, int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.pop,null);
        PopupWindow popupWindow =  new PopupWindow(v);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView item_iv_face = (TextView) v.findViewById(R.id.item_iv_face);
        v.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupHeight = v.getMeasuredHeight();
        popupWidth = v.getMeasuredWidth();

        final int[] location = new int[2];
        item_iv_face.setText(emojis.get(position).get(position).getCharacter());
        Drawable drawable = context.getResources().getDrawable(emojis.get(position).get(position).getId());
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置边界
        // param 左上右下
        item_iv_face.setCompoundDrawables(null,drawable,null,null);
        iv_face.getLocationOnScreen(location);
        popupWindow.setOutsideTouchable(true);//设置点击空白消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));//设置背景;点击返回按钮,关闭PopupWindow
        popupWindow.showAtLocation(iv_face,Gravity.NO_GRAVITY, (location[0] + v.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight-150 );

    }

    /**
     * 初始化游标
     */
    private void Init_Point() {

        pointViews = new ArrayList<ImageView>();
        ImageView imageView;
        for (int i = 0; i < pageViews.size(); i++) {
            imageView = new ImageView(context);
            imageView.setBackgroundResource(R.drawable.d1);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 10;
            layoutParams.rightMargin = 10;
            layoutParams.width = 8;
            layoutParams.height = 8;
            layout_point.addView(imageView, layoutParams);
            if (i == 0 || i == pageViews.size() - 1) {
                imageView.setVisibility(View.GONE);
            }
            if (i == 1) {
                imageView.setBackgroundResource(R.drawable.huakui);
            }
            pointViews.add(imageView);

        }
    }
    //触摸事件的处理

    /**
     * 填充数据
     */
    private void Init_Data() {
        vp_face.setAdapter(new ViewPagerAdapter(pageViews));



        vp_face.setCurrentItem(1);
        current = 0;
        vp_face.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                current = arg0 - 1;
                // 描绘分页点
                draw_Point(arg0);
                // 如果是第一屏或者是最后一屏禁止滑动，其实这里实现的是如果滑动的是第一屏则跳转至第二屏，如果是最后一屏则跳转到倒数第二屏.
                if (arg0 == pointViews.size() - 1 || arg0 == 0) {
                    if (arg0 == 0) {
                        vp_face.setCurrentItem(arg0 + 1);// 第二屏 会再次实现该回调方法实现跳转.
                        pointViews.get(1).setBackgroundResource(R.drawable.huakui);
                    } else {
                        vp_face.setCurrentItem(arg0 - 1);// 倒数第二屏
                        pointViews.get(arg0 - 1).setBackgroundResource(
                                R.drawable.huakui);
                    }
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }

    /**
     * 绘制游标背景
     */
    public void draw_Point(int index) {
        for (int i = 1; i < pointViews.size(); i++) {
            if (index == i) {
                pointViews.get(i).setBackgroundResource(R.drawable.huakui);
            } else {
                pointViews.get(i).setBackgroundResource(R.drawable.d1);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        ChatEmoji emoji = (ChatEmoji) faceAdapters.get(current).getItem(arg2);
        if (emoji.getId() == R.drawable.face_del_icon) {
            int selection = et_sendmessage.getSelectionStart();
            String text = et_sendmessage.getText().toString();
            if (selection > 0) {
                String text2 = text.substring(selection - 1);
                if ("]".equals(text2)) {
                    int start = text.lastIndexOf("[");
                    int end = selection;
                    et_sendmessage.getText().delete(start, end);
                    return;
                }
                et_sendmessage.getText().delete(selection - 1, selection);
            }
        }
        if (!TextUtils.isEmpty(emoji.getCharacter())) {
            if (mListener != null)
                mListener.onCorpusSelected(emoji);
            SpannableString spannableString = FaceConversionUtil.getInstace()
                    .addFace(getContext(), emoji.getId(), emoji.getCharacter());
            et_sendmessage.append(spannableString);
        }

    }
}
