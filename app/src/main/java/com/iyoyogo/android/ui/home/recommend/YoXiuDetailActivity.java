package com.iyoyogo.android.ui.home.recommend;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.yoxiu.YoXiuDetailBean;
import com.iyoyogo.android.contract.YoXiuDetailContract;
import com.iyoyogo.android.presenter.YoXiuDetailPresenter;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.widget.CircleImageView;
import com.luck.picture.lib.entity.LocalMedia;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class YoXiuDetailActivity extends BaseActivity<YoXiuDetailContract.Presenter> implements YoXiuDetailContract.View {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.share_img)
    ImageView shareImg;
    @BindView(R.id.bar)
    RelativeLayout bar;
    @BindView(R.id.edit_comment)
    EditText editComment;
    @BindView(R.id.img_brow)
    ImageView imgBrow;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.tv_collection)
    TextView tvCollection;
    @BindView(R.id.comment_layout)
    RelativeLayout commentLayout;
    @BindView(R.id.img_top)
    ImageView imgTop;
    @BindView(R.id.img_logo)
    ImageView imgLogo;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.img_go)
    ImageView imgGo;
    @BindView(R.id.img_bottom)
    ImageView imgBottom;
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
    @BindView(R.id.comment_view)
    View commentView;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.recycler_comment)
    RecyclerView recyclerComment;
    @BindView(R.id.tv_more_comment)
    TextView tvMoreComment;
    @BindView(R.id.srcoll)
    ScrollView srcoll;
    @BindView(R.id.activity_yoxiu_detail)
    RelativeLayout activityYoxiuDetail;
    @BindView(R.id.img_video)
    ImageView imgVideo;
    @BindView(R.id.tv_time)
    TextView tvTime;
    private LocalMedia mMedia;
    private String mimeType;

    @Override
    protected void initView() {
        super.initView();


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_yo_xiu_detail;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);

        String user_id = SpUtils.getString(YoXiuDetailActivity.this, "user_id", null);
        String user_token = SpUtils.getString(YoXiuDetailActivity.this, "user_token", null);
        Log.d("YoXiuDetailActivity", "id:" + id);
        Log.d("YoXiuDetailActivity", "user_id:" + user_id);
        Log.d("YoXiuDetailActivity", "user_token:" + user_token);
        mPresenter.getDetail(user_id, user_token, id);

    }

    @Override
    protected YoXiuDetailContract.Presenter createPresenter() {
        return new YoXiuDetailPresenter(this);
    }

    public void share() {
        View view = getLayoutInflater().inflate(R.layout.popup_share, null);
        PopupWindow popup_share = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
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
            }
        });
        wechat_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_share.dismiss();
            }
        });
        sina_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_share.dismiss();
            }
        });
        qq_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_share.dismiss();
            }
        });
        backgroundAlpha(0.6f);

        //添加pop窗口关闭事件
        popup_share.setOnDismissListener(new poponDismissListener());
        popup_share.showAtLocation(findViewById(R.id.activity_yoxiu_detail), Gravity.BOTTOM, 0, 0);

    }


    //背景透明
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        getWindow().setAttributes(lp); //act 是上下文context

    }


    @OnClick({R.id.back, R.id.share_img, R.id.img_brow, R.id.tv_like, R.id.tv_collection, R.id.img_logo, R.id.tv_desc, R.id.img_go, R.id.collection, R.id.num_look, R.id.num_look_tv, R.id.tv_comment, R.id.tv_more_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.share_img:
                share();
                break;
            case R.id.img_brow:

                break;
            case R.id.tv_like:

                break;
            case R.id.tv_collection:
//TODO
                collection();
                break;
            case R.id.img_logo:

                break;
            case R.id.tv_desc:

                break;
            case R.id.img_go:

                break;
            case R.id.collection:

                break;
            case R.id.num_look:

                break;
            case R.id.num_look_tv:

                break;
            case R.id.tv_comment:

                break;
            case R.id.tv_more_comment:

                break;
        }
    }

    private void createCollectionFolder() {
        View view = LayoutInflater.from(YoXiuDetailActivity.this).inflate(R.layout.popup_collection, null);
        PopupWindow popup = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(YoXiuDetailActivity.this, 300), true);
        popup.setOutsideTouchable(true);
        popup.setBackgroundDrawable(new ColorDrawable());
        EditText edit_title_collection = view.findViewById(R.id.edit_title_collection);
        TextView tv_sure = view.findViewById(R.id.sure);
        ImageView clear = view.findViewById(R.id.clear);
        tv_sure.setClickable(false);
        if (edit_title_collection.getText().length() < 0) {
            clear.setVisibility(View.GONE);
            tv_sure.setBackgroundResource(R.color.cut_off_line);
        } else {
            tv_sure.setClickable(false);
            clear.setVisibility(View.VISIBLE);
            tv_sure.setBackgroundResource(R.color.color_orange);
        }
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
        backgroundAlpha(0.6f);
        popup.setOnDismissListener(new poponDismissListener());
        popup.showAtLocation(findViewById(R.id.activity_yoxiu_detail), Gravity.BOTTOM, 0, 0);
    }

    private void collection() {
        View view = LayoutInflater.from(YoXiuDetailActivity.this).inflate(R.layout.item_collection_list, null);
        PopupWindow popup = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(YoXiuDetailActivity.this, 300), true);
        popup.setOutsideTouchable(true);
        popup.setBackgroundDrawable(new ColorDrawable());

        LinearLayout create_folder = view.findViewById(R.id.create_folder);
        create_folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCollectionFolder();
            }
        });
        RecyclerView recycler_collection = view.findViewById(R.id.recycler_collection);
        recycler_collection.setLayoutManager(new LinearLayoutManager(YoXiuDetailActivity.this));
        popup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //点击空白处时，隐藏掉pop窗口


        //添加pop窗口关闭事件
        backgroundAlpha(0.6f);
        popup.showAtLocation(findViewById(R.id.activity_yoxiu_detail), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void getDetailSuccess(YoXiuDetailBean.DataBean data) {
        String create_time = data.getCreate_time();
        String time = create_time.replaceAll("-", ".");
        tvTime.setText(time);
        int count_collect = data.getCount_collect();
        tvCollection.setText(count_collect + "");
        int count_praise = data.getCount_praise();
        tvLike.setText(count_praise + "");
        int count_comment = data.getCount_comment();
        tvCollection.setText(count_comment + "");
        int count_view = data.getCount_view();
        numLook.setText(count_view + "");
        String file_desc = data.getFile_desc();
        textDesc.setText(file_desc);
        String path = data.getFile_path();
        Glide.with(this).load(path).into(imgLogo);
        int file_type = data.getFile_type();
        if (file_type == 2) {
            imgVideo.setVisibility(View.VISIBLE);
        } else if (file_type == 1) {
            imgVideo.setVisibility(View.GONE);
        }
        String user_logo = data.getUser_logo();
        Glide.with(this).load(user_logo).into(imgUserIcon);
        String position_name = data.getPosition_name();
        tvDesc.setText(position_name);
        String user_nickname = data.getUser_nickname();
        userName.setText(user_nickname);

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
