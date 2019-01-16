package com.iyoyogo.android.ui.home.yoji;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.PolylineOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.iyoyogo.android.R;
import com.iyoyogo.android.YoJiDetailCommentAdapter;
import com.iyoyogo.android.adapter.BgaBannerAdapter;
import com.iyoyogo.android.adapter.CollectionFolderAdapter;
import com.iyoyogo.android.adapter.YoJiDetailAdapter;
import com.iyoyogo.android.app.Constants;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.collection.AddCollectionBean;
import com.iyoyogo.android.bean.collection.CollectionFolderBean;
import com.iyoyogo.android.bean.comment.CommentBean;
import com.iyoyogo.android.bean.yoji.detail.YoJiDetailBean;
import com.iyoyogo.android.contract.YoJiDetailContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.presenter.YoJiDetailPresenter;
import com.iyoyogo.android.ui.home.yoxiu.AllCommentActivity;
import com.iyoyogo.android.ui.home.yoxiu.MoreTopicActivity;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.SoftKeyboardStateHelper;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.emoji.FaceRelativeLayoutDetails;
import com.iyoyogo.android.widget.CircleImageView;
import com.iyoyogo.android.widget.FlowGroupView;
import com.iyoyogo.android.widget.MyNestedScrollView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * yo记详情
 */
public class YoJiDetailActivity extends BaseActivity<YoJiDetailContract.Presenter> implements YoJiDetailContract.View, SoftKeyboardStateHelper.SoftKeyboardStateListener {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_head)
    CircleImageView imgHead;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.add_attention)
    TextView addAttention;
    @BindView(R.id.img_message)
    RelativeLayout imgMessage;
    @BindView(R.id.img_share)
    ImageView imgShare;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.coll)
    CollapsingToolbarLayout coll;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time_create)
    TextView tvTimeCreate;
    @BindView(R.id.tv_create)
    TextView tvCreate;
    @BindView(R.id.tv_count_see)
    TextView tvCountSee;
    @BindView(R.id.tv_see)
    TextView tvSee;
    @BindView(R.id.user_icon)
    CircleImageView userIcon;
    @BindView(R.id.tv_user_nickname)
    TextView tvUserNickname;
    @BindView(R.id.tv_yoji_count)
    TextView tvYojiCount;
    @BindView(R.id.tv_yoxiu_count)
    TextView tvYoxiuCount;
    @BindView(R.id.tv_attention)
    TextView tvAttention;
    @BindView(R.id.tv_address_start)
    TextView tvAddressStart;
    @BindView(R.id.tv_address_end)
    TextView tvAddressEnd;
    @BindView(R.id.tv_address_spot)
    TextView tvAddressSpot;
    @BindView(R.id.tv_spot_time)
    TextView tvSpotTime;
    @BindView(R.id.tv_money_pay)
    TextView tvMoneyPay;
    @BindView(R.id.realtive)
    RelativeLayout realtive;
    @BindView(R.id.tv_address_start_fold)
    TextView tvAddressStartFold;
    @BindView(R.id.tv_address_end_fold)
    TextView tvAddressEndFold;
    @BindView(R.id.tv_address_spot_fold)
    TextView tvAddressSpotFold;
    @BindView(R.id.tv_spot_time_fold)
    TextView tvSpotTimeFold;
    @BindView(R.id.tv_money_pay_fold)
    TextView tvMoneyPayFold;
    @BindView(R.id.message_trip)
    RelativeLayout messageTrip;
    @BindView(R.id.user_layouts)
    RelativeLayout userLayouts;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.describe_relative)
    RelativeLayout describeRelative;
    @BindView(R.id.recycler_yoji)
    RecyclerView recyclerYoji;
    @BindView(R.id.tv_load_more)
    TextView tvLoadMore;
    @BindView(R.id.comment_view)
    ImageView commentView;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.recycler_comment)
    RecyclerView recyclerComment;
    @BindView(R.id.tv_more_comment)
    TextView tvMoreComment;
    @BindView(R.id.nested)
    MyNestedScrollView nested;
    @BindView(R.id.shadow)
    View shadow;
    @BindView(R.id.edit_comment)
    EditText editComment;
    @BindView(R.id.img_brow)
    ImageView imgBrow;

    @BindView(R.id.banner)
    BGABanner mBanner;
    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.tv_collection)
    TextView tvCollection;
    @BindView(R.id.send_emoji)
    ImageView sendEmoji;
    @BindView(R.id.activity_yoji_detail)
    RelativeLayout activityYojiDetail;
    @BindView(R.id.comment)
    RelativeLayout comment;
    @BindView(R.id.vp_contains)
    ViewPager vpContains;
    @BindView(R.id.iv_image)
    LinearLayout ivImage;
    @BindView(R.id.ll_facechoose)
    RelativeLayout llFacechoose;
    @BindView(R.id.FaceRelativeLayout)
    FaceRelativeLayoutDetails FaceRelativeLayout;
    @BindView(R.id.comment_layout)
    LinearLayout commentLayout;
    private int open = 2;
    private boolean isOpen;
    private boolean isManager;
    public static int expendedtag = 2;
    List<String> mList = new ArrayList<>();


    List<String> indexList = new ArrayList<>();
    private String user_token;
    private String user_id;
    private int is_my_attention;
    private int is_my_praise;
    private int yo_id;
    private YoJiDetailCommentAdapter yoJiDetailCommentAdapter;
    private RecyclerView recycler_collection;
    private String yo_user_id;
    private int add_collection_id;
    private int count_collect;
    private PopupWindow popup;
    private int is_my_collect;
    private List<YoJiDetailBean.DataBean> dataBeans;
    private ImageView img_tip;
    private TextView tv_message_three;
    private TextView tv_message_two;
    private TextView tv_message;
    private List<CollectionFolderBean.DataBean.ListBean> mList1;
    private int add_attention_id;
    private String yo_attention_id;
    private String yo_ids;
    private String logo;
    private String desc;
    private String title;
    private TranslateAnimation mShowAction;
    private TranslateAnimation mHiddenAction;
    private RelativeLayout picture_count_one;
    private RelativeLayout picture_count_two;
    private RelativeLayout picture_count_three;
    private RelativeLayout picture_count_four;
    private RelativeLayout picture_count_five;
    private FlowGroupView flowGroupView;
    private ImageView img_count_one_one;
    private ImageView img_count_three_three;
    private TextView tv_pic_count;
    private ImageView img_count_five_five;
    private ImageView img_count_five_four;
    private ImageView img_count_four_four;
    private ImageView img_count_five_three;
    private ImageView img_count_four_three;
    private ImageView img_count_five_two;
    private ImageView img_count_four_two;
    private ImageView img_count_three_two;
    private ImageView img_count_two_two;
    private ImageView img_count_five_one;
    private ImageView img_count_four_one;
    private ImageView img_count_three_one;
    private ImageView img_count_two_one;
    private ArrayList<String> logos;
    private ArrayList<String> logos_big;
    private YoJiDetailAdapter yoJiDetailAdapter;
    Intent intent;
    String get_yo_id;
    private List<YoJiDetailBean.DataBean.ListBean> list;
    private String yo_user_id1;
    private AMap aMap;


    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void setSetting() {
        super.setSetting();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_yo_ji_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        statusbar();
        new SoftKeyboardStateHelper(findViewById(R.id.activity_yoji_detail)).addSoftKeyboardStateListener(this);
        intent = getIntent();
        yo_id = intent.getIntExtra("yo_id", 0);
        yo_user_id1 = intent.getStringExtra("yo_user_id");
        user_id = SpUtils.getString(getApplicationContext(), "user_id", null);
        setSupportActionBar(toolbar);
        appbar.setExpanded(true);
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            //verticalOffset是当前appbarLayout的高度与最开始appbarlayout高度的差，向上滑动的话是负数
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //通过日志得出活动启动是两次，由于之前有setExpanded所以三次
                Log.d("启动活动调用监听次数", "几次");
                if (getSupportActionBar().getHeight() - appBarLayout.getHeight() == verticalOffset) {
                    //折叠监听
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Window window = getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(getResources().getColor(android.R.color.white));
                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    }
                    MIUISetStatusBarLightMode(getWindow(), true);
                    if (user_id.equals(yo_user_id1)) {
                        imgShare.setImageResource(R.mipmap.more);
                    } else {
                        imgShare.setImageResource(R.mipmap.fenxiang_hei);
                    }
                    imgBack.setImageResource(R.mipmap.fanhui_black);
                    imgMessage.setVisibility(View.VISIBLE);
//                    imgMessage.startAnimation(mShowAction);
//                    messageTrip.startAnimation(mShowAction);
//                    realtive.startAnimation(mHiddenAction);
                    messageTrip.setVisibility(View.VISIBLE);
                    realtive.setVisibility(View.GONE);
//                    StatusBarUtil.setStatusBarColor(YoJiDetailActivity.this,Color.parseColor("#ffffff"));
                }
                if (expendedtag == 2 && verticalOffset == 0) {
//                    statusbar();
//                    StatusBarUtil.setTransparent(YoJiDetailActivity.this);
                    //展开监听
                    MIUISetStatusBarLightMode(getWindow(), false);
                    imgBack.setImageResource(R.mipmap.back_icon);
                    if (user_id.equals(yo_user_id1)) {
                        imgShare.setImageResource(R.mipmap.more);
                    } else {
                        imgShare.setImageResource(R.mipmap.fenxiang_bai);
                    }
                    imgMessage.setVisibility(View.GONE);
                    messageTrip.setVisibility(View.GONE);
                    realtive.setVisibility(View.VISIBLE);
//                    imgMessage.startAnimation(mHiddenAction);
//                    messageTrip.startAnimation(mHiddenAction);
//                    realtive.startAnimation(mShowAction);
//                    StatusBarUtil.setStatusBarColor(YoJiDetailActivity.this,Color.parseColor("#00000000"));

                }
                if (expendedtag != 2 && verticalOffset == 0) {
                    expendedtag++;
                }
            }
        });


    }

    private void closeInputMethod() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        if (isOpen) {
            // imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//没有显示则显示
            imm.hideSoftInputFromWindow(editComment.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(getResources().getColor(R.color.transparent));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }


    @Override
    protected YoJiDetailContract.Presenter createPresenter() {
        return new YoJiDetailPresenter(this);
    }

    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        mBanner.setAdapter(new BgaBannerAdapter(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        user_id = SpUtils.getString(getApplicationContext(), "user_id", null);
        user_token = SpUtils.getString(getApplicationContext(), "user_token", null);
        mPresenter.getYoJiDetail(user_id, user_token, yo_id);
        mPresenter.getCommentList(user_id, user_token, 1, yo_id, 0);
        tvLoadMore.setText("收起全部");
        editComment.setImeOptions(EditorInfo.IME_ACTION_SEND);
        editComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().equals("#")) {
                    startActivity(new Intent(YoJiDetailActivity.this, MoreTopicActivity.class));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editComment.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (editComment.getText().toString().length() > 0) {
                        mPresenter.addComment(user_id, user_token, 0, yo_id, editComment.getText().toString().trim());
                        closeInputMethod();
                        mPresenter.getCommentList(user_id, user_token, 1, yo_id, 0);
                        editComment.clearFocus();
                        editComment.setFocusable(false);
//                        yoXiuDetailAdapter.notifyItemInserted(dataBeans.size());
                    } else {
                    }
                    return true;
                }
                return false;

            }
        });
        editComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editComment.setFocusable(true);
                editComment.setFocusableInTouchMode(true);
                editComment.requestFocus();
            }
        });
        editComment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //获得焦点
                    tvCollection.setVisibility(View.GONE);
                    tvLike.setVisibility(View.GONE);
                    editComment.setHint("码字不容易，留个评论鼓励下嘛~");
                    editComment.setHintTextColor(Color.parseColor("#888888"));
                    sendEmoji.setVisibility(View.VISIBLE);
                    imgBrow.setVisibility(View.GONE);
//                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) editComment.getLayoutParams();
////                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//
//                    layoutParams.alignWithParent=true;
                    RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) editComment.getLayoutParams();
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    layoutParams1.setMargins(0, 0, DensityUtil.dp2px(YoXiuDetailActivity.this, 40), 0);
                    editComment.setLayoutParams(layoutParams1);
                } else {
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) editComment.getLayoutParams();
//                    layoutParams.setMargins(0, DensityUtil.dp2px(YoXiuDetailActivity.this, 20), 0, 0);
                    editComment.setLayoutParams(layoutParams);
                    tvCollection.setVisibility(View.VISIBLE);
                    tvLike.setVisibility(View.VISIBLE);
                    editComment.setHint("再不评论 , 你会被抓去写作业的~");
                    editComment.setHintTextColor(Color.parseColor("#888888"));
                    sendEmoji.setVisibility(View.GONE);
                    sendEmoji.setVisibility(View.GONE);
                    imgBrow.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @OnClick({R.id.add_attention, R.id.img_back, R.id.img_share, R.id.tv_attention, R.id.tv_load_more, R.id.tv_comment, R.id.tv_like, R.id.tv_collection, R.id.tv_more_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.add_attention:
                mPresenter.addAttention(user_id, user_token, Integer.parseInt(yo_attention_id));
                break;
            case R.id.img_share:
                yo_user_id1 = intent.getStringExtra("yo_user_id");
                if (user_id.equals(yo_user_id1)) {
                    more();
                } else {
                    share();
                }
//                share();
                break;
            case R.id.tv_attention:
                mPresenter.addAttention(user_id, user_token, Integer.parseInt(yo_attention_id));
                break;
            case R.id.tv_load_more:
                if (tvLoadMore.getText().toString().trim().equals("展开全部")) {
                    //false-->true
                    yoJiDetailAdapter.changetShowDelImage(true);
                    tvLoadMore.setText("收起全部");
                    List<String> false_list = yoJiDetailAdapter.getIndex_list();
                    List<String> true_list = yoJiDetailAdapter.getSelect_list();
                    for (int i = 0; i < false_list.size(); i++) {
                        true_list.add(false_list.get(i));
                    }
                    false_list.clear();
                    Log.d("YoJiDetailActivity", "false_list:" + false_list);
                    Log.d("YoJiDetailActivity", "true_list:" + true_list);

//                    mPresenter.getYoJiDetail(user_id, user_token, yo_id);

                } else {
                    //true-->false
                    yoJiDetailAdapter.changetShowDelImage(false);
                    tvLoadMore.setText("展开全部");
                    List<String> false_list = yoJiDetailAdapter.getIndex_list();
                    List<String> true_list = yoJiDetailAdapter.getSelect_list();
                    for (int i = 0; i < true_list.size(); i++) {
                        false_list.add(true_list.get(i));
                    }
                    true_list.clear();
                    Log.d("YoJiDetailActivity", "false_list:" + false_list);
                    Log.d("YoJiDetailActivity", "true_list:" + true_list);
                    //为自定义方法--控制另外一个变量

//                    mPresenter.getYoJiDetail(user_id, user_token, yo_id);
                }


                break;
            case R.id.tv_comment:

                break;
            case R.id.tv_like:
                Drawable like = getResources().getDrawable(
                        R.mipmap.xihuan_xiangqing);
                Drawable liked = getResources().getDrawable(
                        R.mipmap.yixihuan_xiangqing);
                tvLike.setCompoundDrawablesWithIntrinsicBounds(null, dataBeans.get(0).getIs_my_praise() > 0 ? liked : like, null, null);
                String count_praise = dataBeans.get(0).getCount_praise();
                int count_praises = Integer.parseInt(count_praise);
                Log.d("Test", "dataBeans.get(0).getIs_my_like():" + dataBeans.get(0).getIs_my_praise());
                if (dataBeans.get(0).getIs_my_praise() > 0) {
                    //由喜欢变为不喜欢，亮变暗
                    tvLike.setCompoundDrawablesWithIntrinsicBounds(null,
                            like, null, null);
                    count_praises -= 1;
                    //设置点赞的数量
                    tvLike.setText(count_praises + "");
                    dataBeans.get(0).setIs_my_praise(0);
                    dataBeans.get(0).setCount_praise(String.valueOf(count_praises));

                } else {
                    //由不喜欢变为喜欢，暗变亮
                    tvLike.setCompoundDrawablesWithIntrinsicBounds(null,
                            liked, null, null);
                    count_praises += 1;
                    //设置点赞的数量
                    tvLike.setText(count_praises + "");
                    dataBeans.get(0).setIs_my_praise(1);
                    dataBeans.get(0).setCount_praise(String.valueOf(count_praises));
                    like();
                    popup.showAtLocation(findViewById(R.id.activity_yoji_detail), Gravity.CENTER, 0, 0);
                }
                DataManager.getFromRemote().praise(user_id, user_token, Integer.parseInt(dataBeans.get(0).getYo_id()), 0)
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {
                            }
                        });
                break;
            case R.id.tv_collection:
                collection();
                break;
            case R.id.tv_more_comment:
                Intent intent = new Intent(this, AllCommentActivity.class);
                intent.putExtra("id", yo_id);
                startActivity(intent);
                break;
        }
    }

    public void initPopup() {
        View view = LayoutInflater.from(YoJiDetailActivity.this).inflate(R.layout.like_layout, null);
        popup = new PopupWindow(view, DensityUtil.dp2px(YoJiDetailActivity.this, 300), DensityUtil.dp2px(YoJiDetailActivity.this, 145), true);
        popup.setOutsideTouchable(true);
        popup.setBackgroundDrawable(new ColorDrawable());
        tv_message = view.findViewById(R.id.tv_message);
        tv_message_two = view.findViewById(R.id.tv_message_two);

        tv_message_three = view.findViewById(R.id.tv_message_three);
        img_tip = view.findViewById(R.id.tip_img);

        popup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //点击空白处时，隐藏掉pop窗口


        //添加pop窗口关闭事件
        popup.setOnDismissListener(new poponDismissListener());
    }

    public void like() {

        tv_message.setTextColor(Color.parseColor("#FA800A"));
        tv_message_two.setTextColor(Color.parseColor("#FA800A"));
        tv_message_three.setTextColor(Color.parseColor("#FA800A"));
        backgroundAlpha(0.6f);
        tv_message.setText("Hi~");
        img_tip.setImageResource(R.mipmap.stamo_heart);
        tv_message_two.setText("谢谢喜欢~");
        tv_message_three.setText("给你小心心");
        popup.showAtLocation(findViewById(R.id.activity_yoji_detail), Gravity.CENTER, 0, 0);
    }

    private void collections() {
        Drawable collection = getResources().getDrawable(
                R.mipmap.shoucang_xiangqing);
        Drawable collectioned = getResources().getDrawable(
                R.mipmap.yishoucang_xiangqing);
        if (dataBeans.get(0).getIs_my_collect() == 0) {

            tvCollection.setCompoundDrawablesWithIntrinsicBounds(null,
                    collection, null, null);

        } else {
            tvCollection.setCompoundDrawablesWithIntrinsicBounds(null,
                    collectioned, null, null);
        }

        tvCollection.setCompoundDrawablesWithIntrinsicBounds(null,
                dataBeans.get(0).getIs_my_collect() == 0 ? collection : collectioned, null, null);

    }


    private void praise() {
        Drawable like = getResources().getDrawable(
                R.mipmap.xihuan_xiangqing);
        Drawable liked = getResources().getDrawable(
                R.mipmap.yixihuan_xiangqing);
        if (dataBeans.get(0).getIs_my_praise() == 0) {

            tvLike.setCompoundDrawablesWithIntrinsicBounds(null,
                    like, null, null);

        } else {
            tvLike.setCompoundDrawablesWithIntrinsicBounds(null,
                    liked, null, null);
        }
        tvLike.setCompoundDrawablesWithIntrinsicBounds(null,
                dataBeans.get(0).getIs_my_praise() == 0 ? like : liked, null, null);

    }

    @Override
    public void getYoJiDetailSuccess(YoJiDetailBean.DataBean data) {
        List<LatLng> latLngs = new ArrayList<>();
        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (int i = 0; i < data.getList().size(); i++) {
            YoJiDetailBean.DataBean.ListBean listBean = data.getList().get(i);
            if (!TextUtils.isEmpty(listBean.getLat()) && !TextUtils.isEmpty(listBean.getLng())) {
                double lat = Double.valueOf(listBean.getLat());
                double lng = Double.valueOf(listBean.getLng());
                LatLng latLng = new LatLng(lat, lng);
                latLngs.add(latLng);
                builder.include(latLng);
                MarkerOptions markerOption = new MarkerOptions();
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lvjingcd)));
                markerOption.position(latLng);
                markerOption.title("");
                markerOption.draggable(false);
                markerOption.anchor(0.5f, 0.5f);
                aMap.addMarker(markerOption);

                if (i == data.getList().size() - 1) {
                    MarkerOptions marker = new MarkerOptions();
                    marker.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.paint)));
                    marker.position(new LatLng(lat, lng));
                    marker.title("");
                    marker.draggable(false);
                    marker.anchor(0, 0.9f);
                    aMap.addMarker(marker);
                }
//            aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(lat, lng), 15, 0, 0)));
            }
        }
        if (latLngs.size() > 0) {
            aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 10));
            if (latLngs.size() > 1) {
                aMap.addPolyline(new PolylineOptions().addAll(latLngs).width(7).color(Color.parseColor("#FA800A")));
            }
        }
        List<Object> bannerList = new ArrayList<>();
        bannerList.add(data.getLogo());
        aMap.getMapScreenShot(bitmap -> {
            bitmap.setHasAlpha(true);
            bannerList.add(0, bitmap);
            mBanner.setData(bannerList, null);
            mapView.setVisibility(View.GONE);
        });


        yo_attention_id = data.getUser_id();
        title = data.getTitle();

        yo_ids = data.getYo_id();
        tvComment.setText("评论" + "(" + data.getCount_comment() + ")");
        initPopup();
        tvLike.setText(data.getCount_praise() + "");
        tvCollection.setText(data.getCount_collect() + "");
        dataBeans = new ArrayList<>();
        dataBeans.add(data);
        desc = data.getDesc();
        if (TextUtils.isEmpty(desc)) {
            tvDesc.setVisibility(View.GONE);
        } else {
            tvDesc.setText("\u3000\u3000" + desc);
        }
        yo_user_id = data.getYo_id();
        count_collect = data.getCount_collect();
        is_my_collect = data.getIs_my_collect();
        is_my_attention = data.getIs_my_attention();
        is_my_praise = data.getIs_my_praise();
        collections();
        praise();
        is_my_attention = data.getIs_my_attention();
        if (is_my_attention == 0) {
            tvAttention.setText("+ 关注");
        } else {
            tvAttention.setText("已关注");
        }
        logo = data.getLogo();
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.default_touxiang).error(R.mipmap.default_touxiang);
        Glide.with(this).load(data.getUser_logo()).apply(requestOptions).into(userIcon);
        Glide.with(this).load(data.getUser_logo()).apply(requestOptions).into(imgHead);
        tvUserName.setText(data.getUser_nickname());
        tvUserNickname.setText(data.getUser_nickname());
        tvTitle.setText(data.getTitle());
        tvYojiCount.setText(data.getCount_yoj() + "");
        tvYoxiuCount.setText(data.getCount_yox() + "");
        tvTimeCreate.setText(data.getCreate_time());
        tvCountSee.setText(data.getCount_view() + "人");
        tvMoneyPay.setText(data.getCost() + "元/人");
        tvMoneyPayFold.setText(data.getCost() + "元/人");
        tvSpotTime.setText(data.getCount_dates() + "天");
        tvSpotTimeFold.setText(data.getCount_dates() + "天");
        tvAddressStart.setText(data.getP_start());
        tvAddressStartFold.setText(data.getP_start());
        tvAddressSpot.setText(data.getList().size() + "个地点");
        tvAddressSpotFold.setText(data.getList().size() + "个地点");
        tvAddressEnd.setText(data.getP_end());
        tvAddressEndFold.setText(data.getP_end());
        imgHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(YoJiDetailActivity.this, UserHomepageActivity.class);
                intent.putExtra("yo_user_id", data.getUser_id());
                startActivity(intent);
            }
        });

        list = data.getList();
        yoJiDetailAdapter = new YoJiDetailAdapter(YoJiDetailActivity.this, list, data.getCount_praise(), String.valueOf(data.getCount_collect()), data.getIs_my_praise(), data.getIs_my_collect(), tvLoadMore);

        recyclerYoji.setAdapter(yoJiDetailAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(YoJiDetailActivity.this);

        recyclerYoji.setLayoutManager(linearLayoutManager);
        yoJiDetailAdapter.changetShowDelImage(true);
        yoJiDetailAdapter.setOnItemClickListener(new YoJiDetailAdapter.OnClickListener() {
            @Override
            public void onClick(View v, int position) {

            }
        });

       /* yoJiDetailAdapter.setOnItemDataListener(new YoJiDetailAdapter.OnPlayListener() {


            @Override
            public void getData(YoJiDetailAdapter.Holder holder, int position) {
//                index = position;


                Log.d("5656565", "indexList.size():" + indexList.size());
                picture_count_one = holder.itemView.findViewById(R.id.picture_count_one);
                picture_count_two = holder.itemView.findViewById(R.id.picture_count_two);
                picture_count_three = holder.itemView.findViewById(R.id.picture_count_three);
                picture_count_four = holder.itemView.findViewById(R.id.picture_count_four);
                picture_count_five = holder.itemView.findViewById(R.id.picture_count_five);
                flowGroupView = holder.itemView.findViewById(R.id.flow);
                img_count_one_one = holder.itemView.findViewById(R.id.img_count_one_one);
                img_count_two_one = holder.itemView.findViewById(R.id.img_count_two_one);
                img_count_three_one = holder.itemView.findViewById(R.id.img_count_three_one);
                img_count_four_one = holder.itemView.findViewById(R.id.img_count_four_one);
                img_count_five_one = holder.itemView.findViewById(R.id.img_count_five_one);
                img_count_two_two = holder.itemView.findViewById(R.id.img_count_two_two);
                img_count_three_two = holder.itemView.findViewById(R.id.img_count_three_two);
                img_count_four_two = holder.itemView.findViewById(R.id.img_count_four_two);
                img_count_five_two = holder.itemView.findViewById(R.id.img_count_five_two);
                img_count_three_three = holder.itemView.findViewById(R.id.img_count_three_three);
                img_count_four_three = holder.itemView.findViewById(R.id.img_count_four_three);
                img_count_five_three = holder.itemView.findViewById(R.id.img_count_five_three);
                img_count_four_four = holder.itemView.findViewById(R.id.img_count_four_four);
                img_count_five_four = holder.itemView.findViewById(R.id.img_count_five_four);
                img_count_five_five = holder.itemView.findViewById(R.id.img_count_five_five);
                tv_pic_count = holder.itemView.findViewById(R.id.tv_pic_count);
                logos = (ArrayList<String>) data.getList().get(position).getLogos();
                logos_big = (ArrayList<String>) data.getList().get(position).getLogos_big();

                indexList.add(String.valueOf(position));

                if (position > 0) {
                    flowGroupView.setVisibility(View.GONE);
                    picture_count_one.setVisibility(View.GONE);
                    picture_count_two.setVisibility(View.GONE);
                    picture_count_three.setVisibility(View.GONE);
                    picture_count_four.setVisibility(View.GONE);
                    picture_count_five.setVisibility(View.GONE);

                } else {
                    int size = logos.size();
                    if (size > 5) {
                        loadData(logos, 6);
                    } else if (size == 5) {
                        loadData(logos, 5);
                    } else if (size == 4) {
                        loadData(logos, 4);
                    } else if (size == 3) {
                        loadData(logos, 3);
                    } else if (size == 2) {
                        loadData(logos, 2);
                    } else if (size == 1) {
                        loadData(logos, 1);
                    }

                }
                String integer = indexList.get(position);
                int index = Integer.parseInt(integer);
                index = position;

            }
        });*/
//        EventBus.getDefault().postSticky(data.getUser_nickname());
    }

    private void loadData(ArrayList<String> logos, int size) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.default_ic)
                .error(R.mipmap.default_ic);
        if (size == 1) {
            flowGroupView.setVisibility(View.VISIBLE);
            picture_count_one.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(logos.get(0)).apply(requestOptions).into(img_count_one_one);
            img_count_one_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(YoJiDetailActivity.this, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", logos_big);
                    intent.putExtra("position", 0);
                    startActivity(intent);
                }
            });
        } else if (size == 2) {
            flowGroupView.setVisibility(View.VISIBLE);
            picture_count_two.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(logos.get(0)).apply(requestOptions).into(img_count_two_one);
            Glide.with(getApplicationContext()).load(logos.get(1)).apply(requestOptions).into(img_count_two_two);
            img_count_two_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(YoJiDetailActivity.this, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", logos_big);
                    intent.putExtra("position", 0);
                    startActivity(intent);
                }
            });
            img_count_two_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(YoJiDetailActivity.this, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", logos_big);
                    intent.putExtra("position", 1);
                    startActivity(intent);
                }
            });
        } else if (size == 3) {
            flowGroupView.setVisibility(View.VISIBLE);
            picture_count_three.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(logos.get(0)).apply(requestOptions).into(img_count_three_one);
            Glide.with(getApplicationContext()).load(logos.get(1)).apply(requestOptions).into(img_count_three_two);
            Glide.with(getApplicationContext()).load(logos.get(2)).apply(requestOptions).into(img_count_three_three);
            img_count_three_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(YoJiDetailActivity.this, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", logos_big);
                    intent.putExtra("position", 0);
                    startActivity(intent);
                }
            });
            img_count_three_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(YoJiDetailActivity.this, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", logos_big);
                    intent.putExtra("position", 1);
                    startActivity(intent);
                }
            });
            img_count_three_three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(YoJiDetailActivity.this, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", logos_big);
                    intent.putExtra("position", 2);
                    startActivity(intent);
                }
            });
        } else if (size == 4) {
            flowGroupView.setVisibility(View.VISIBLE);
            flowGroupView.setVisibility(View.VISIBLE);
            picture_count_four.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(logos.get(0)).apply(requestOptions).into(img_count_four_one);
            Glide.with(getApplicationContext()).load(logos.get(1)).apply(requestOptions).into(img_count_four_two);
            Glide.with(getApplicationContext()).load(logos.get(2)).apply(requestOptions).into(img_count_four_three);
            Glide.with(getApplicationContext()).load(logos.get(3)).apply(requestOptions).into(img_count_four_four);
            img_count_four_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(YoJiDetailActivity.this, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", logos_big);
                    intent.putExtra("position", 0);
                    startActivity(intent);
                }
            });
            img_count_four_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(YoJiDetailActivity.this, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", logos_big);
                    intent.putExtra("position", 1);
                    startActivity(intent);
                }
            });
            img_count_four_three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();

                    intent.setClass(YoJiDetailActivity.this, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", logos_big);
                    intent.putExtra("position", 2);
                    startActivity(intent);
                }
            });
            img_count_four_four.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(YoJiDetailActivity.this, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", logos_big);
                    intent.putExtra("position", 3);
                    startActivity(intent);
                }
            });
        } else if (size == 5) {
            flowGroupView.setVisibility(View.VISIBLE);
            picture_count_five.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(logos.get(0)).apply(requestOptions).into(img_count_five_one);
            Glide.with(getApplicationContext()).load(logos.get(1)).apply(requestOptions).into(img_count_five_two);
            Glide.with(getApplicationContext()).load(logos.get(2)).apply(requestOptions).into(img_count_five_three);
            Glide.with(getApplicationContext()).load(logos.get(3)).apply(requestOptions).into(img_count_five_four);
            Glide.with(getApplicationContext()).load(logos.get(4)).apply(requestOptions).into(img_count_five_five);
            img_count_five_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(YoJiDetailActivity.this, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", logos_big);
                    intent.putExtra("position", 0);
                    startActivity(intent);
                }
            });
            img_count_five_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(YoJiDetailActivity.this, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", logos_big);
                    intent.putExtra("position", 1);
                    startActivity(intent);
                }
            });
            img_count_five_three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(YoJiDetailActivity.this, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", logos_big);
                    intent.putExtra("position", 2);
                    startActivity(intent);
                }
            });
            img_count_five_four.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(YoJiDetailActivity.this, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", logos_big);
                    intent.putExtra("position", 3);
                    startActivity(intent);
                }
            });
            img_count_five_five.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(YoJiDetailActivity.this, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", logos_big);
                    intent.putExtra("position", 4);
                    startActivity(intent);
                }
            });
        } else if (size > 5) {
            flowGroupView.setVisibility(View.VISIBLE);
            picture_count_five.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(logos.get(0)).apply(requestOptions).into(img_count_five_one);
            Glide.with(getApplicationContext()).load(logos.get(1)).apply(requestOptions).into(img_count_five_two);
            Glide.with(getApplicationContext()).load(logos.get(2)).apply(requestOptions).into(img_count_five_three);
            Glide.with(getApplicationContext()).load(logos.get(3)).apply(requestOptions).into(img_count_five_four);
            Glide.with(getApplicationContext()).load(logos.get(4)).apply(requestOptions).into(img_count_five_five);
            tv_pic_count.setVisibility(View.VISIBLE);
            tv_pic_count.setText(size + "");
            img_count_five_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(YoJiDetailActivity.this, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", logos_big);
                    intent.putExtra("position", 0);
                    startActivity(intent);
                }
            });
            img_count_five_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(YoJiDetailActivity.this, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", logos_big);
                    intent.putExtra("position", 1);
                    startActivity(intent);
                }
            });
            img_count_five_three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(YoJiDetailActivity.this, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", logos_big);
                    intent.putExtra("position", 2);
                    startActivity(intent);
                }
            });
            img_count_five_four.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(YoJiDetailActivity.this, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", logos_big);
                    intent.putExtra("position", 3);
                    startActivity(intent);
                }
            });
            img_count_five_five.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(YoJiDetailActivity.this, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", logos_big);
                    intent.putExtra("position", 4);
                    startActivity(intent);
                }
            });
        }
    }

    private void visible(int position, RelativeLayout picture_count_one, RelativeLayout picture_count_two, RelativeLayout picture_count_three, RelativeLayout picture_count_four, RelativeLayout picture_count_five) {
        if (position == 0) {
            picture_count_one.setVisibility(View.VISIBLE);
            picture_count_two.setVisibility(View.VISIBLE);
            picture_count_three.setVisibility(View.VISIBLE);
            picture_count_four.setVisibility(View.VISIBLE);
            picture_count_five.setVisibility(View.VISIBLE);
        } else {
            picture_count_one.setVisibility(View.GONE);
            picture_count_two.setVisibility(View.GONE);
            picture_count_three.setVisibility(View.GONE);
            picture_count_four.setVisibility(View.GONE);
            picture_count_five.setVisibility(View.GONE);
        }
    }


    private void createCollectionFolder() {
        backgroundAlpha(0.6f);
        View view = LayoutInflater.from(YoJiDetailActivity.this).inflate(R.layout.popup_collection, null);
        popup = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(YoJiDetailActivity.this, 300), true);
        popup.setOutsideTouchable(true);
        popup.setBackgroundDrawable(new ColorDrawable());
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        EditText edit_title_collection = view.findViewById(R.id.edit_title_collection);
        TextView tv_sure = view.findViewById(R.id.sure);
        ImageView clear = view.findViewById(R.id.clear);
        ImageView close_img = view.findViewById(R.id.close_img);
        close_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_title_collection.setText("");
                clear.setVisibility(View.GONE);
            }
        });
        ImageView img_btn = view.findViewById(R.id.img_btn);
        img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOpen) {
                    img_btn.setImageResource(R.mipmap.on);
                    open = 1;
                    isOpen = true;
                } else {
                    img_btn.setImageResource(R.mipmap.off);
                    open = 2;
                    isOpen = false;
                }
            }
        });

        edit_title_collection.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0) {
                    clear.setVisibility(View.GONE);
                    tv_sure.setClickable(false);
                    tv_sure.setBackgroundResource(R.color.cut_off_line);
                } else {
                    tv_sure.setClickable(true);
                    tv_sure.setClickable(true);
                    clear.setVisibility(View.VISIBLE);
                    tv_sure.setBackgroundResource(R.color.color_orange);
                }
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();

                mPresenter.createCollectionFolder(user_id, user_token, edit_title_collection.getText().toString().trim(), open, "");
            }
        });
        backgroundAlpha(0.6f);
        popup.setOnDismissListener(new poponDismissListener());
        popup.showAtLocation(findViewById(R.id.activity_yoji_detail), Gravity.BOTTOM, 0, 0);
    }

    private void collection() {
        View view = LayoutInflater.from(YoJiDetailActivity.this).inflate(R.layout.item_collection_list, null);
        PopupWindow popup = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(YoJiDetailActivity.this, 300), true);
        popup.setOutsideTouchable(true);
        popup.setBackgroundDrawable(new ColorDrawable());

        LinearLayout create_folder = view.findViewById(R.id.create_folder);
        create_folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
                backgroundAlpha(0.6f);
                createCollectionFolder();
            }
        });
        recycler_collection = view.findViewById(R.id.recycler_collection);
        mPresenter.getCollectionFolder(user_id, user_token);


        popup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //点击空白处时，隐藏掉pop窗口


        //添加pop窗口关闭事件
        backgroundAlpha(0.6f);
        popup.setOnDismissListener(new poponDismissListener());
        popup.showAtLocation(findViewById(R.id.activity_yoji_detail), Gravity.BOTTOM, 0, 0);
    }

    public void share() {
        View view = getLayoutInflater().inflate(R.layout.popup_share, null);
        PopupWindow popup_share = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(YoJiDetailActivity.this, 220), true);
        popup_share.setBackgroundDrawable(new ColorDrawable());
        popup_share.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popup_share.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        LinearLayout qq_layout = view.findViewById(R.id.qq_layout);
        LinearLayout comment_layout = view.findViewById(R.id.comment_layout);
        LinearLayout wechat_layout = view.findViewById(R.id.wechat_layout);
        LinearLayout sina_layout = view.findViewById(R.id.sina_layout);
        TextView tv_cancel = view.findViewById(R.id.cancel);
        ImageView img_close = view.findViewById(R.id.close_img);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popup_share.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_share.dismiss();
            }
        });
        comment_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_share.dismiss();
                shareWeb(SHARE_MEDIA.WEIXIN_CIRCLE);
            }
        });
        wechat_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_share.dismiss();
                shareWeb(SHARE_MEDIA.WEIXIN);
            }
        });
        sina_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_share.dismiss();
                shareWeb(SHARE_MEDIA.SINA);
            }
        });
        qq_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareWeb(SHARE_MEDIA.QQ);
                popup_share.dismiss();
            }
        });
        backgroundAlpha(0.6f);

        //添加pop窗口关闭事件
        popup_share.setOnDismissListener(new poponDismissListener());
        popup_share.showAtLocation(findViewById(R.id.activity_yoji_detail), Gravity.BOTTOM, 0, 0);

    }

    public void more() {
        View view = getLayoutInflater().inflate(R.layout.popup_user_share, null);
        PopupWindow popup_share = new PopupWindow(view, DensityUtil.dp2px(YoJiDetailActivity.this, 90), DensityUtil.dp2px(YoJiDetailActivity.this, 110), true);
        popup_share.setBackgroundDrawable(new ColorDrawable());
        popup_share.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popup_share.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        LinearLayout share = view.findViewById(R.id.share);
        LinearLayout bianji = view.findViewById(R.id.bianji);
        LinearLayout delete = view.findViewById(R.id.delete);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
                popup_share.dismiss();
            }
        });
        bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(YoJiDetailActivity.this, NewPublishYoJiActivity.class).putExtra("id", yo_id));
                popup_share.dismiss();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getFromRemote().deleteYo(user_id, user_token, yo_id)
                        .subscribe(new Observer<BaseBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(BaseBean baseBean) {
                                int code = baseBean.getCode();
                                if (code == 200) {
                                    popup_share.dismiss();
                                    finish();
                                    Toast.makeText(YoJiDetailActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });
        backgroundAlpha(0.6f);

        //添加pop窗口关闭事件
        popup_share.setOnDismissListener(new poponDismissListener());
        popup_share.showAtLocation(findViewById(R.id.img_share), Gravity.RIGHT | Gravity.TOP, 0, 130);
    }

    private void shareWeb(SHARE_MEDIA share_media) {
        /*80002/yo_id/4143*/
        String url = Constants.BASE_URL + "home/share/details_yoj/share_user_id/" + user_id + "/yo_id/" + yo_id;
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        UMImage thumb = new UMImage(getApplicationContext(), logo);
        web.setThumb(thumb);  //缩略图
        if (!TextUtils.isEmpty(desc)) {
            web.setDescription(desc);//描述
        }
        new ShareAction(YoJiDetailActivity.this)
                .withMedia(web)
                .setPlatform(share_media)
                .share();
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        getWindow().setAttributes(lp); //act 是上下文context

    }

    @Override
    public void onSoftKeyboardOpened(int keyboardHeightInPx) {

        //获得焦点
        tvCollection.setVisibility(View.GONE);
        tvLike.setVisibility(View.GONE);
        editComment.setHint("码字不容易，留个评论鼓励下嘛~");
        editComment.setHintTextColor(Color.parseColor("#888888"));
        sendEmoji.setVisibility(View.VISIBLE);
        imgBrow.setVisibility(View.GONE);
//                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) editComment.getLayoutParams();
////                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//
//                    layoutParams.alignWithParent=true;
        RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) editComment.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    layoutParams1.setMargins(0, 0, DensityUtil.dp2px(YoXiuDetailActivity.this, 40), 0);
        editComment.setLayoutParams(layoutParams1);


        //失去焦点


    }

    @Override
    public void onSoftKeyboardClosed() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) editComment.getLayoutParams();
//                    layoutParams.setMargins(0, DensityUtil.dp2px(YoXiuDetailActivity.this, 20), 0, 0);
        editComment.setLayoutParams(layoutParams);
        tvCollection.setVisibility(View.VISIBLE);
        tvLike.setVisibility(View.VISIBLE);
        editComment.setHint("再不评论 , 你会被抓去写作业的~");
        editComment.setHintTextColor(Color.parseColor("#888888"));
        sendEmoji.setVisibility(View.GONE);
        sendEmoji.setVisibility(View.GONE);
        imgBrow.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    //隐藏事件PopupWindow
    private class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1.0f);
        }
    }

    @Override
    public void getCommentListSuccess(CommentBean.DataBean data) {
        List<CommentBean.DataBean.ListBean> list = data.getList();
        List<CommentBean.DataBean.ListBean> mList = new ArrayList<>();

        if (list.size() < 5) {
            mList.addAll(list);
        } else {
            for (int i = 0; i < 5; i++) {
                mList.add(list.get(i));

            }
        }
        yoJiDetailCommentAdapter = new YoJiDetailCommentAdapter(YoJiDetailActivity.this, mList);
        recyclerComment.setLayoutManager(new LinearLayoutManager(YoJiDetailActivity.this));
        recyclerComment.setAdapter(yoJiDetailCommentAdapter);
        yoJiDetailCommentAdapter.setDeleteOnClickListener(new YoJiDetailCommentAdapter.DeleteOnClickListener() {
            @Override
            public void delete() {
                mPresenter.getCommentList(user_id, user_token, 1, yo_id, 0);
            }
        });
    }

    @Override
    public void addCommentSuccess(BaseBean baseBean) {
        editComment.setText("");
        yoJiDetailCommentAdapter.notifyDataSetChanged();
        comment();
        mPresenter.getCommentList(user_id, user_token, 1, yo_id, 0);

    }

    public void comment() {
        backgroundAlpha(0.6f);
        tv_message.setText("Hi~");
        tv_message.setTextColor(Color.parseColor("#FA800A"));
        tv_message_two.setTextColor(Color.parseColor("#FA800A"));
        tv_message_three.setTextColor(Color.parseColor("#FA800A"));
        img_tip.setImageResource(R.mipmap.stamo_heart);
        tv_message_two.setText("谢谢评论~");
        tv_message_three.setText("给你小心心");
        popup.showAtLocation(findViewById(R.id.activity_yoji_detail), Gravity.CENTER, 0, 0);
    }

    @Override
    public void getCollectionFolderSuccess(CollectionFolderBean.DataBean collectionFolderBean) {

        mList1 = new ArrayList<>();
        List<CollectionFolderBean.DataBean.ListBean> list = collectionFolderBean.getList();
        CollectionFolderBean.DataBean.ListBean listBean = new CollectionFolderBean.DataBean.ListBean();
        listBean.setName("默认收藏");
        listBean.setOpen(1);
//        mList.add(listBean);
        mList1.addAll(list);
        CollectionFolderAdapter collectionFolderAdapter = new CollectionFolderAdapter(mList1);
        recycler_collection.setLayoutManager(new LinearLayoutManager(YoJiDetailActivity.this));
        recycler_collection.setAdapter(collectionFolderAdapter);
        collectionFolderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
               /* int is_my_collect = collection_list.get(0).getIs_my_collect();

                if (is_my_collect == 0) {
                    mPresenter.addCollection(user_id, user_token, id, yo_id);
                } else {
                    if (collection_id == null) {
                        mPresenter.deleteCollection(user_id, user_token, is_my_collect);
                    } else {

                        int i = Integer.parseInt(collection_id);
                        mPresenter.deleteCollection(user_id, user_token, i);
                    }
                }*/
                int folder_id = mList1.get(position).getFolder_id();
                mPresenter.getYoJiDetail(user_id, user_token, yo_id);
                if (is_my_attention == 0) {
                    mPresenter.addCollection(user_id, user_token, folder_id, Integer.parseInt(yo_user_id));
                    popup.dismiss();
//                    Log.d("YoXiuDetailActivity", target_id);

                } else {

                }
                popup.dismiss();

            }
        });
    }

    @Override
    public void createFolderSuccess(BaseBean baseBean) {
        Toast.makeText(this, "创建文件夹", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addCollectionSuccess(AddCollectionBean.DataBean data) {
        String collection_id = data.getId();
        add_collection_id = Integer.parseInt(collection_id);
        count_collect += 1;
        Drawable collection = getResources().getDrawable(
                R.mipmap.shoucang_xiangqing);
        Drawable collectioned = getResources().getDrawable(
                R.mipmap.yishoucang_xiangqing);
        dataBeans.get(0).setIs_my_collect(Integer.parseInt(collection_id));
        tvCollection.setCompoundDrawablesWithIntrinsicBounds(null,
                dataBeans.get(0).getIs_my_collect() == 0 ? collection : collectioned, null, null);

        tvCollection.setText(count_collect + "");
    }

    @Override
    public void deleteCollectionSuccess(BaseBean baseBean) {

    }

    @Override
    public void addAttentionSuccess(AttentionBean.DataBean data) {
        if (data.getStatus() == 0) {
            tvAttention.setText("+ 关注");
            Toast.makeText(this, "取消关注", Toast.LENGTH_SHORT).show();
        } else {
            tvAttention.setText("已关注");
            Toast.makeText(this, "关注成功", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void deleteAttentionSuccess(BaseBean baseBean) {

    }


}
