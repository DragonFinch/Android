package com.iyoyogo.android.ui.home.yoxiu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.githang.statusbar.StatusBarCompat;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.CollectionFolderAdapter;
import com.iyoyogo.android.adapter.YoXiuDetailAdapter;
import com.iyoyogo.android.app.Constants;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.collection.AddCollectionBean;
import com.iyoyogo.android.bean.collection.CollectionFolderBean;
import com.iyoyogo.android.bean.comment.CommentBean;
import com.iyoyogo.android.bean.yoxiu.YoXiuDetailBean;
import com.iyoyogo.android.contract.YoXiuDetailContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.presenter.YoXiuDetailPresenter;
import com.iyoyogo.android.ui.home.GoTakePhotoActivity;
import com.iyoyogo.android.ui.home.yoji.NewPublishYoJiActivity;
import com.iyoyogo.android.ui.home.yoji.UserHomepageActivity;
import com.iyoyogo.android.ui.home.yoji.YoJiDetailActivity;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.KeyBoardUtils;
import com.iyoyogo.android.utils.SoftKeyboardStateHelper;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.emoji.FaceRelativeLayoutDetails;
import com.iyoyogo.android.widget.CircleImageView;
import com.luck.picture.lib.entity.LocalMedia;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * yo秀详情
 */
public class YoXiuDetailActivity extends BaseActivity<YoXiuDetailContract.Presenter> implements YoXiuDetailContract.View, SoftKeyboardStateHelper.SoftKeyboardStateListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.share_img)
    ImageView shareImg;
    @BindView(R.id.bar)
    RelativeLayout bar;
    @BindView(R.id.shadow)
    View shadow;
    @BindView(R.id.edit_comment)
    EditText editComment;
    @BindView(R.id.img_brow)
    ImageView imgBrow;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.tv_collection)
    TextView tvCollection;
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
    @BindView(R.id.img_top)
    ImageView imgTop;
    @BindView(R.id.img_logo)
    ImageView imgLogo;
    @BindView(R.id.img_video)
    ImageView imgVideo;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.img_go)
    ImageView imgGo;
    @BindView(R.id.img_bottom)
    ImageView imgBottom;
    @BindView(R.id.img_layout)
    RelativeLayout imgLayout;
    @BindView(R.id.img_user_icon)
    CircleImageView imgUserIcon;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.collection)
    TextView collection;
    @BindView(R.id.text_desc)
    TextView textDesc;
    @BindView(R.id.num_look)
    TextView numLook;
    @BindView(R.id.num_look_tv)
    TextView numLookTv;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.img_water_mark)
    ImageView imgWaterMark;
    @BindView(R.id.comment_view)
    View commentView;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.recycler_comment)
    RecyclerView recyclerComment;
    @BindView(R.id.img_comment_null)
    ImageView imgCommentNull;
    @BindView(R.id.tv_comment_null)
    TextView tvCommentNull;
    @BindView(R.id.tv_more_comment)
    TextView tvMoreComment;
    @BindView(R.id.srcoll)
    ScrollView srcoll;
    @BindView(R.id.activity_yoxiu_detail)
    RelativeLayout activityYoxiuDetail;
    private int open = 2;
    private boolean isOpen;

    /*    @BindView(R.id.send_emoji)
        ImageView sendEmoji;*/
    private LocalMedia mMedia;
    private String mimeType;
    private String user_id;
    private String user_token;
    private int id;
    private List<YoXiuDetailBean.DataBean> dataBeans;
    private YoXiuDetailAdapter yoXiuDetailAdapter;
    private String target_id;
    private int yo_user_id;
    private int is_my_attention;
    private RecyclerView recycler_collection;
    private List<YoXiuDetailBean.DataBean> collection_list;
    private String collection_id;
    @BindView(R.id.iv_logo)
    VideoView ivLogo;
    private int yo_id;
    private PopupWindow popup;
    private String count_collect;
    TextView tv_message;
    ImageView img_tip;
    TextView tv_message_two;
    TextView tv_message_three;
    private int add_attention_id;
    private int is_my_collect;
    private int add_collection_id;
    private List<CollectionFolderBean.DataBean.ListBean> mList;
    private int yo_attention_id;
    private String file_desc;
    private String path;
    private int count_collects;
    private RelativeLayout ll_facechoose;
    private ImageView img_brow;
    private ImageView send_emoji;
    private String yo_id1;
    private Intent intent;

    @Override
    protected void setSetting() {
        super.setSetting();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    protected void initView() {
        super.initView();
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        intent = getIntent();
        yo_id1 = intent.getStringExtra("yo_id");
        img_brow = findViewById(R.id.img_brow);
        send_emoji = findViewById(R.id.send_emoji);

        ll_facechoose = findViewById(R.id.ll_facechoose);
        new SoftKeyboardStateHelper(findViewById(R.id.activity_yoxiu_detail)).addSoftKeyboardStateListener(this);
        editComment.setImeOptions(EditorInfo.IME_ACTION_SEND);
        editComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().equals("#")) {
                    startActivity(new Intent(YoXiuDetailActivity.this, MoreTopicActivity.class));
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
                        mPresenter.addComment(user_id, user_token, 0, id, editComment.getText().toString().trim());
                        closeInputMethod();
//                        mPresenter.getCommentList(user_id, user_token, 1, id, 0);
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
                KeyBoardUtils.openKeybord(editComment, getApplicationContext());
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_yo_xiu_detail;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        super.initData(savedInstanceState);
        id = intent.getIntExtra("id", 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataBeans = new ArrayList<>();


        user_id = SpUtils.getString(YoXiuDetailActivity.this, "user_id", null);
        user_token = SpUtils.getString(YoXiuDetailActivity.this, "user_token", null);
        Log.d("YoXiuDetailActivity", "id:" + id);
        Log.d("YoXiuDetailActivity", "user_id:" + user_id);
        Log.d("YoXiuDetailActivity", "user_token:" + user_token);
        mPresenter.getDetail(user_id, user_token, id);
        mPresenter.getCommentList(user_id, user_token, 1, id, 0);
        editComment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //获得焦点
                    tvCollection.setVisibility(View.GONE);
                    tvLike.setVisibility(View.GONE);
                    editComment.setHint("码字不容易，留个评论鼓励下嘛~");
                    editComment.setHintTextColor(Color.parseColor("#888888"));
                    imgBrow.setVisibility(View.VISIBLE);
                    send_emoji.setVisibility(View.GONE);
//                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) editComment.getLayoutParams();
////                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//
//                    layoutParams.alignWithParent=true;
                    //  RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) editComment.getLayoutParams();
                    //  RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    layoutParams1.setMargins(0, 0, DensityUtil.dp2px(YoXiuDetailActivity.this, 40), 0);
                    //    editComment.setLayoutParams(layoutParams1);
                } else {
                    //  RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) editComment.getLayoutParams();
//                    layoutParams.setMargins(0, DensityUtil.dp2px(YoXiuDetailActivity.this, 20), 0, 0);
                    //  editComment.setLayoutParams(layoutParams);
                    tvCollection.setVisibility(View.VISIBLE);
                    tvLike.setVisibility(View.VISIBLE);
                    editComment.setHint("再不评论 , 你会被抓去写作业的~");
                    editComment.setHintTextColor(Color.parseColor("#888888"));
                    imgBrow.setVisibility(View.GONE);
                    send_emoji.setVisibility(View.VISIBLE);
                }
            }
        });

        tvCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collection();
            }
        });

        if (user_id.equals(yo_id1)) {
            shareImg.setImageResource(R.mipmap.more);
        }
    }

    private void closeInputMethod() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        if (isOpen) {
            // imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//没有显示则显示
            imm.hideSoftInputFromWindow(editComment.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected YoXiuDetailContract.Presenter createPresenter() {
        return new YoXiuDetailPresenter(this);
    }

    public void more() {
        View view = getLayoutInflater().inflate(R.layout.popup_user_share, null);
        PopupWindow popup_share = new PopupWindow(view, DensityUtil.dp2px(YoXiuDetailActivity.this, 90), DensityUtil.dp2px(YoXiuDetailActivity.this, 110), true);
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
                popup_share.dismiss();
                startActivity(new Intent(YoXiuDetailActivity.this, NewPublishYoXiuActivity.class).putExtra("id", id));
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getFromRemote().deleteYo(user_id, user_token, id)
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
                                    Toast.makeText(YoXiuDetailActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
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
        popup_share.showAtLocation(findViewById(R.id.share_img), Gravity.RIGHT | Gravity.TOP, 0, 130);
    }

    //
    public void share() {
        View view = getLayoutInflater().inflate(R.layout.popup_share, null);
        PopupWindow popup_share = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(YoXiuDetailActivity.this, 220), true);
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
        popup_share.showAtLocation(findViewById(R.id.activity_yoxiu_detail), Gravity.BOTTOM, 0, 0);

    }

    private void shareWeb(SHARE_MEDIA share_media) {
        /*80002/yo_id/4143*/
        //http://192.168.0.104//80002/yo_id/4143
        String url = Constants.BASE_URL+ "home/share/details_yox/share_user_id/" + user_id + "/yo_id/" + yo_id;
        UMWeb web = new UMWeb(url);
        web.setTitle(path);//标题
        UMImage thumb = new UMImage(getApplicationContext(), path);
        web.setThumb(thumb);  //缩略图
        if (!TextUtils.isEmpty(file_desc)) {
            web.setDescription(file_desc);//描述
        }
        new ShareAction(YoXiuDetailActivity.this)
                .withMedia(web)
                .setPlatform(share_media)
                .share();
    }


    //背景透明
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        getWindow().setAttributes(lp); //act 是上下文context

    }


    @OnClick({R.id.back, R.id.share_img, R.id.tv_like, R.id.tv_collection, R.id.img_logo, R.id.tv_desc, R.id.img_go, R.id.collection, R.id.num_look, R.id.num_look_tv, R.id.tv_comment, R.id.tv_more_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.share_img:
                if (user_id.equals(yo_id1)) {
                    more();
                } else {
                    share();
                }
//                share();
                break;
            case R.id.tv_like:

                Drawable like = getResources().getDrawable(
                        R.mipmap.xihuan_xiangqing);
                Drawable liked = getResources().getDrawable(
                        R.mipmap.yixihuan_xiangqing);
                tvLike.setCompoundDrawablesWithIntrinsicBounds(null, dataBeans.get(0).getIs_my_like() > 0 ? liked : like, null, null);
                String count_praise = dataBeans.get(0).getCount_praise();
                int count_praises = Integer.parseInt(count_praise);
                Log.d("Test", "dataBeans.get(0).getIs_my_like():" + dataBeans.get(0).getIs_my_like());
                if (dataBeans.get(0).getIs_my_like() > 0) {
                    //由喜欢变为不喜欢，亮变暗
                    tvLike.setCompoundDrawablesWithIntrinsicBounds(null,
                            like, null, null);
                    count_praises -= 1;
                    //设置点赞的数量
                    dataBeans.get(0).setIs_my_like(0);
                    dataBeans.get(0).setCount_praise(count_praises + "");
                    tvLike.setText(count_praises + "");

                } else {
                    //由不喜欢变为喜欢，暗变亮
                    tvLike.setCompoundDrawablesWithIntrinsicBounds(null,
                            liked, null, null);
                    count_praises += 1;
                    //设置点赞的数量
                    like();
                    dataBeans.get(0).setIs_my_like(1);
                    dataBeans.get(0).setCount_praise(count_praises + "");
                    tvLike.setText(count_praises + "");
                }
                DataManager.getFromRemote().praise(user_id, user_token, Integer.parseInt(dataBeans.get(0).getId()), 0)
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {
                            }
                        });
                break;
            case R.id.tv_collection:
//TODO

                collection();
                break;
            case R.id.img_logo:

                break;
            case R.id.tv_desc:
//TODO

                Intent intent = new Intent(getApplicationContext(), YoXiuListActivity.class);
                intent.putExtra("position", tvDesc.getText().toString().trim());
                Log.e("zxxz", "onViewClicked: "+tvDesc.getText().toString().trim() );
                startActivity(intent);
                break;
            case R.id.img_go:
                startActivity(new Intent(getApplicationContext(), GoTakePhotoActivity.class));
                break;
            case R.id.collection:
                /*
                 * 先判断 标示 是否为0 为0 的话调用添加关注 否则 取消关注
                 * */

                /*if (is_my_attention == 0) {
                    mPresenter.addAttention(user_id, user_token, yo_user_id);

                } else {
                    if (target_id == null) {
                        Log.d("AA", "is_my_attention:" + is_my_attention);
                        mPresenter.deleteAttention(user_id, user_token, is_my_attention);
                        mPresenter.getDetail(user_id, user_token, id);
                        is_my_attention=0;

                    } else {
                        int target = Integer.parseInt(target_id);
                        Log.d("AA", "target:" + target);
                        mPresenter.deleteAttention(user_id, user_token, target);
                        mPresenter.getDetail(user_id, user_token, id);
                        is_my_attention=0;
                    }
                }*/


                mPresenter.addAttention(user_id, user_token, yo_attention_id);

//                    Log.d("YoXiuDetailActivity", target_id);

                break;
            case R.id.num_look:

                break;
            case R.id.num_look_tv:

                break;
            case R.id.tv_comment:

                break;
            case R.id.tv_more_comment:
                Intent intent1 = new Intent(this, AllCommentActivity.class);
                intent1.putExtra("id", id);
                startActivity(intent1);
                break;
        }
    }

    private void createCollectionFolder() {
        backgroundAlpha(0.6f);
        View view = LayoutInflater.from(YoXiuDetailActivity.this).inflate(R.layout.popup_collection, null);
        PopupWindow popup = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(YoXiuDetailActivity.this, 300), true);
        popup.setOutsideTouchable(true);
        popup.setBackgroundDrawable(new ColorDrawable());
//        popup.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
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
        popup.showAtLocation(findViewById(R.id.activity_yoxiu_detail), Gravity.BOTTOM, 0, 0);
    }

    private void collection() {
        View view = LayoutInflater.from(YoXiuDetailActivity.this).inflate(R.layout.item_collection_list, null);
        popup = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(YoXiuDetailActivity.this, 300), true);
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
        popup.showAtLocation(findViewById(R.id.activity_yoxiu_detail), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void getDetailSuccess(YoXiuDetailBean.DataBean data) {
        initPopup();
        String id = data.getId();
        yo_id = Integer.parseInt(id);
        tvComment.setText("评论" + "(" + data.getCount_comment() + ")");
        collection_list = new ArrayList<>();
        collection_list.add(data);
        String id1 = data.getId();
        yo_user_id = Integer.parseInt(id1);
        String user_ids = data.getUser_id();
        yo_attention_id = Integer.parseInt(user_ids);
        dataBeans.add(data);
        collections();
        is_my_collect = data.getIs_my_collect();
        is_my_attention = data.getIs_my_attention();
        if (is_my_attention == 0) {
            collection.setText("+ 关注");
        } else {
            collection.setText("已关注");
        }
        Log.d("is_my_attention", "is_my_attention:" + is_my_attention);
        String create_time = data.getCreate_time();
        String time = create_time.replaceAll("-", ".");
        tvTime.setText(time);
        count_collect = data.getCount_collect();
        tvCollection.setText(count_collect + "");
        String count_praise = data.getCount_praise();
        tvLike.setText(count_praise + "");
        String count_view = data.getCount_view();
        numLook.setText(count_view + "");
        file_desc = data.getFile_desc();
        textDesc.setText(file_desc);
        path = data.getFile_path();
        RequestOptions requestOptions = new RequestOptions();
        if (editComment.getText().toString().length() > 0) {
            mPresenter.addComment(this.user_id, user_token, 0, this.id, editComment.getText().toString().trim());
            closeInputMethod();
            yoXiuDetailAdapter.notifyDataSetChanged();
            refresh();
        } else {

        }
        ViewGroup.LayoutParams layoutParams = imgLogo.getLayoutParams();
        int width = layoutParams.width;
        requestOptions.placeholder(R.mipmap.default_ic).error(R.mipmap.default_ic);
        Glide.with(this).load(path)
                .apply(requestOptions)
                .into(imgLogo);
        String file_type = data.getFile_type();
        if (file_type.equals("2")) {
            if (path.contains(".mp4")) {
                imgVideo.setVisibility(View.VISIBLE);
                ivLogo.setVisibility(View.VISIBLE);
                ivLogo.setVideoPath(path);
                imgLogo.setVisibility(View.GONE);
            }


        } else if (file_type.equals("1")) {
            imgVideo.setVisibility(View.GONE);

            ivLogo.setVisibility(View.GONE);

            imgLogo.setVisibility(View.VISIBLE);
        }
        imgVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ivLogo.isPlaying()) {
                    ivLogo.pause();
                    ivLogo.setVisibility(View.VISIBLE);
                    imgVideo.setVisibility(View.VISIBLE);
                } else {
                    ivLogo.start();
                    imgVideo.setVisibility(View.GONE);
                    ivLogo.setVisibility(View.VISIBLE);
                }
            }
        });
        ivLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ivLogo.isPlaying()) {
                    ivLogo.pause();
                    ivLogo.setVisibility(View.VISIBLE);
                    imgVideo.setVisibility(View.VISIBLE);
                } else {
                    ivLogo.start();
                    imgVideo.setVisibility(View.GONE);
                    ivLogo.setVisibility(View.VISIBLE);
                }
            }
        });
        ivLogo.setOnCompletionListener(mp -> {

            imgVideo.setVisibility(View.VISIBLE);
        });
        String user_logo = data.getUser_logo();
        RequestOptions requestOption = new RequestOptions();
        requestOption.error(R.mipmap.default_touxiang).placeholder(R.mipmap.default_touxiang);
        Glide.with(this).load(user_logo).apply(requestOption).into(imgUserIcon);
        imgUserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(YoXiuDetailActivity.this, UserHomepageActivity.class);
                intent.putExtra("yo_user_id", String.valueOf(data.getUser_id()));
                startActivity(intent);
            }
        });
        String position_name = data.getPosition_name();
        tvDesc.setText(position_name);
        String user_nickname = data.getUser_nickname();
        userName.setText(user_nickname);
        praise();

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
        popup.showAtLocation(findViewById(R.id.activity_yoxiu_detail), Gravity.CENTER, 0, 0);
    }

    public void initPopup() {
        View view = LayoutInflater.from(YoXiuDetailActivity.this).inflate(R.layout.like_layout, null);
        popup = new PopupWindow(view, DensityUtil.dp2px(YoXiuDetailActivity.this, 300), DensityUtil.dp2px(YoXiuDetailActivity.this, 145), true);
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
        if (dataBeans.get(0).getIs_my_like() == 0) {

            tvLike.setCompoundDrawablesWithIntrinsicBounds(null,
                    like, null, null);

        } else {
            tvLike.setCompoundDrawablesWithIntrinsicBounds(null,
                    liked, null, null);
        }

        tvLike.setCompoundDrawablesWithIntrinsicBounds(null,
                dataBeans.get(0).getIs_my_like() == 0 ? like : liked, null, null);

    }

    public void refresh() {

        getWindow().getDecorView().invalidate();

    }

    @Override
    public void getCommentListSuccess(CommentBean.DataBean data) {

        List<CommentBean.DataBean.ListBean> list = data.getList();
        List<CommentBean.DataBean.ListBean> commentList = new ArrayList<>();
        if (list.size() > 5) {
            for (int i = 0; i < 5; i++) {
                commentList.add(list.get(i));
            }
        } else {
            commentList.addAll(list);

        }
        if (commentList.size() > 0) {
            tvMoreComment.setVisibility(View.VISIBLE);
            recyclerComment.setVisibility(View.VISIBLE);
            imgCommentNull.setVisibility(View.GONE);
            tvCommentNull.setVisibility(View.GONE);
        } else {
            tvMoreComment.setVisibility(View.GONE);
            recyclerComment.setVisibility(View.GONE);
            imgCommentNull.setVisibility(View.VISIBLE);
            tvCommentNull.setVisibility(View.VISIBLE);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(YoXiuDetailActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerComment.setLayoutManager(linearLayoutManager);
        yoXiuDetailAdapter = new YoXiuDetailAdapter(YoXiuDetailActivity.this, commentList);
        recyclerComment.setAdapter(yoXiuDetailAdapter);
        yoXiuDetailAdapter.setDeleteOnClickListener(new YoXiuDetailAdapter.DeleteOnClickListener() {
            @Override
            public void delete() {
                mPresenter.getCommentList(user_id, user_token, 1, id, 0);
            }
        });
    }

    //发布成功的回调
    @Override
    public void addCommentSuccess(BaseBean baseBean) {
        String msg = baseBean.getMsg();
        editComment.setText("");
        ll_facechoose.setVisibility(View.GONE);
        yoXiuDetailAdapter.notifyDataSetChanged();
        KeyBoardUtils.closeKeybord(editComment,YoXiuDetailActivity.this);
        mPresenter.getCommentList(user_id, user_token, 1, id, 0);
    }

    @Override
    public void addAttentionSuccess(AttentionBean.DataBean data) {
      /*  String target_id = data.getId();
        add_attention_id = Integer.parseInt(target_id);
        Log.d("YoXiuDetailActivity", target_id);*/
        int status = data.getStatus();
        if (status == 0) {
            collection.setText("+ 关注");
            Toast.makeText(this, "取消关注", Toast.LENGTH_SHORT).show();
        } else {
            collection.setText("已关注");
            Toast.makeText(this, "关注成功", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void deleteAttentionSuccess(BaseBean baseBean) {
    }

    @Override
    public void getCollectionFolderSuccess(CollectionFolderBean.DataBean collectionFolderBean) {
        mList = new ArrayList<>();
        List<CollectionFolderBean.DataBean.ListBean> list = collectionFolderBean.getList();
        CollectionFolderBean.DataBean.ListBean listBean = new CollectionFolderBean.DataBean.ListBean();
        listBean.setFolder_id(0);
        listBean.setName("默认收藏");
        listBean.setOpen(1);
//        mList.add(listBean);
        mList.addAll(list);
        CollectionFolderAdapter collectionFolderAdapter = new CollectionFolderAdapter(mList);
        recycler_collection.setLayoutManager(new LinearLayoutManager(YoXiuDetailActivity.this));
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
                int folder_id = mList.get(position).getFolder_id();
                mPresenter.getDetail(user_id, user_token, id);
                Log.d("YoXiuDetailActivity", "folder_id:" + folder_id);
                Log.d("YoXiuDetailActivity", "yo_user_id:" + yo_user_id);
                mPresenter.addCollection(user_id, user_token, folder_id, yo_user_id);
//                    Log.d("YoXiuDetailActivity", target_id);
                popup.dismiss();

            }
        });
    }

    @Override
    public void createFolderSuccess(BaseBean baseBean) {
        Toast.makeText(this, baseBean.getMsg(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addCollectionSuccess(AddCollectionBean.DataBean data) {
        String collection_id = data.getId();
        add_collection_id = Integer.parseInt(collection_id);
        count_collects = Integer.parseInt(count_collect);
        count_collects += 1;
        Drawable collection = getResources().getDrawable(
                R.mipmap.shoucang_xiangqing);
        Drawable collectioned = getResources().getDrawable(
                R.mipmap.yishoucang_xiangqing);
        dataBeans.get(0).setIs_my_collect(Integer.parseInt(collection_id));
        tvCollection.setCompoundDrawablesWithIntrinsicBounds(null,
                dataBeans.get(0).getIs_my_collect() == 0 ? collection : collectioned, null, null);

        tvCollection.setText(count_collects + "");
    }

    @Override
    public void deleteCollectionSuccess(BaseBean baseBean) {
        Toast.makeText(this, baseBean.getMsg(), Toast.LENGTH_SHORT).show();
        count_collects -= 1;
        Drawable collection = getResources().getDrawable(
                R.mipmap.shoucang_xiangqing);
        Drawable collectioned = getResources().getDrawable(
                R.mipmap.yishoucang_xiangqing);
        dataBeans.get(0).setIs_my_collect(0);
        tvCollection.setCompoundDrawablesWithIntrinsicBounds(null,
                dataBeans.get(0).getIs_my_collect() == 0 ? collection : collectioned, null, null);
        tvCollection.setText(count_collects + "");
    }

    @Override
    public void onSoftKeyboardOpened(int keyboardHeightInPx) {
        //获得焦点
        tvCollection.setVisibility(View.GONE);
        tvLike.setVisibility(View.GONE);
        editComment.setHint("码字不容易，留个评论鼓励下嘛~");
        editComment.setHintTextColor(Color.parseColor("#888888"));
        //sendEmoji.setVisibility(View.VISIBLE);
        //imgBrow.setVisibility(View.GONE);
        send_emoji.setVisibility(View.VISIBLE);
        img_brow.setVisibility(View.GONE);
        //RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) editComment.getLayoutParams();
        //RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    layoutParams1.setMargins(0, 0, DensityUtil.dp2px(YoXiuDetailActivity.this, 40), 0);
        // editComment.setLayoutParams(layoutParams1);
        //editComment.setLayoutParams(layoutParams1);


    }

    @Override
    public void onSoftKeyboardClosed() {
        //失去焦点
        //   RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) editComment.getLayoutParams();
//                    layoutParams.setMargins(0, DensityUtil.dp2px(YoXiuDetailActivity.this, 20), 0, 0);
        //  editComment.setLayoutParams(layoutParams);
        tvCollection.setVisibility(View.VISIBLE);
        tvLike.setVisibility(View.VISIBLE);
        editComment.setHint("再不评论 , 你会被抓去写作业的~");
        editComment.setHintTextColor(Color.parseColor("#888888"));
        //sendEmoji.setVisibility(View.GONE);
        //imgBrow.setVisibility(View.VISIBLE);
        imgBrow.setVisibility(View.VISIBLE);
        send_emoji.setVisibility(View.GONE);
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
}
