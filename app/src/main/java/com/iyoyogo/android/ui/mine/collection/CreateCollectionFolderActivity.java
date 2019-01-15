package com.iyoyogo.android.ui.mine.collection;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.contract.EditCollectionContract;
import com.iyoyogo.android.presenter.EditCollectionPresenter;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 编辑收藏夹
 */
public class CreateCollectionFolderActivity extends BaseActivity<EditCollectionContract.Presenter> implements EditCollectionContract.View {

    @BindView(R.id.back_iv_id)
    ImageView backIvId;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.edit_title)
    EditText editTitle;
    @BindView(R.id.clear_title)
    ImageView clearTitle;
    @BindView(R.id.img_open)
    ImageView imgOpen;
    private View pop_view;
    private TextView poptitle;
    private TextView popcontent;
    private TextView popno;
    private ImageView popim;
    private TextView popyes;
    private PopupWindow popMenu;
    private int open_type=1;
    private String user_token;
    private String user_id;
    private String title;
    private int folder_id;
    //编辑收藏夹


    @Override
    protected void initView() {
        super.initView();
//        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        statusbar();
        user_id = SpUtils.getString(CreateCollectionFolderActivity.this, "user_id", null);
        user_token = SpUtils.getString(CreateCollectionFolderActivity.this, "user_token", null);
        Intent intent = getIntent();
        editTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() > 0) {
                    clearTitle.setVisibility(View.VISIBLE);
                } else {
                    clearTitle.setVisibility(View.GONE);
                }
            }
        });
        int type = intent.getIntExtra("type", 0);
        if (type == 1) {
            tvTitle.setText("创建收藏夹");
            tvDelete.setVisibility(View.GONE);
        } else if (type == 2) {
            tvTitle.setText("编辑收藏夹");
            title = intent.getStringExtra("name");
            folder_id = intent.getIntExtra("folder_id", 0);
            editTitle.setText(title);
            open_type = intent.getIntExtra("open", 0);
            if (open_type == 1) {
                imgOpen.setImageResource(R.mipmap.on);
            } else {
                imgOpen.setImageResource(R.mipmap.off);
            }
        }
        StatusBarUtils.setWindowStatusBarColor(CreateCollectionFolderActivity.this, R.color.white);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_editor_favorites;
    }

    @Override
    protected EditCollectionContract.Presenter createPresenter() {
        return new EditCollectionPresenter(this);
    }


    @Override
    public void onBackPressed() {
        initPopuptWindow();
        poptitle.setText(R.string.mazi);
        popcontent.setText(R.string.shifoubaocun);
        popno.setText(R.string.fangqi);
        popyes.setText(R.string.zhunle);
        popim.setImageResource(R.mipmap.stamp_shijian);
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        getWindow().setAttributes(lp); //act 是上下文context

    }

    @SuppressLint("NewApi")
    private void initPopuptWindow() {
        pop_view = View.inflate(this, R.layout.item_praise_popup, null);


        poptitle = pop_view.findViewById(R.id.pop_title_id);
        popcontent = pop_view.findViewById(R.id.pop_content_id);
        popno = pop_view.findViewById(R.id.popup_no_id);
        popyes = pop_view.findViewById(R.id.popup_yes_id);
        popim = pop_view.findViewById(R.id.pop_im_id);


        pop_view.findViewById(R.id.popup_im_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popMenu.dismiss();
            }
        });
        popMenu = new PopupWindow(pop_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popMenu.setFocusable(true);//设置pw中的控件能够获取焦点

        popMenu.setBackgroundDrawable(new ColorDrawable());//设置mPopupWindow背景颜色或图片，这里设置半透明
        popMenu.setOutsideTouchable(true); //设置可以通过点击mPopupWindow外部关闭mPopupWindow
//        popMenu.setAnimationStyle(R.style.PopupAnimationAmount);//设置mPopupWindow的进出动画
        backgroundAlpha(0.6f);
        popMenu.update();//刷新mPopupWindow
        popMenu.showAsDropDown(pop_view, Gravity.CENTER, 0, 0);//mPopupWindow显示的位置
        popyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popMenu.dismiss();
                if (title.length() > 0) {
                    mPresenter.createFolder(user_id, user_token, editTitle.getText().toString().trim(), open_type, title);
                } else {
                    mPresenter.createFolder(user_id, user_token, editTitle.getText().toString().trim(), open_type, "");

                }
                finish();
            }
        });
        popno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popMenu.dismiss();
                finish();
            }
        });
        /**
         * PopupWindow消失监听方法
         */
        popMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
    }

    @SuppressLint("NewApi")
    private void initPopupPraise() {
        pop_view = View.inflate(this, R.layout.popup_favorites_prompt, null);
        pop_view.findViewById(R.id.popup_praise_im_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popMenu.dismiss();
            }
        });
        popMenu = new PopupWindow(pop_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popMenu.setFocusable(true);//设置pw中的控件能够获取焦点
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        popMenu.setBackgroundDrawable(dw);//设置mPopupWindow背景颜色或图片，这里设置半透明
        popMenu.setOutsideTouchable(true); //设置可以通过点击mPopupWindow外部关闭mPopupWindow
//        popMenu.setAnimationStyle(R.style.PopupAnimationAmount);//设置mPopupWindow的进出动画
        popMenu.update();//刷新mPopupWindow
        popMenu.showAsDropDown(pop_view, Gravity.CENTER, 0, 0);//mPopupWindow显示的位置
        /**
         * PopupWindow消失监听方法
         */
        popMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back_iv_id, R.id.tv_title, R.id.tv_delete, R.id.tv_commit, R.id.clear_title, R.id.img_open})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv_id:
                initPopuptWindow();
                poptitle.setText(R.string.mazi);
                popcontent.setText(R.string.shifoubaocun);
                popno.setText(R.string.fangqi);
                popyes.setText(R.string.zhunle);
                popim.setImageResource(R.mipmap.stamp_shijian);
                break;
            case R.id.tv_title:

                break;
            case R.id.tv_delete:
                int[] folder_ids = new int[1];
                folder_ids[0] = folder_id;
                mPresenter.deleteFolder(user_id, user_token, folder_ids);
                break;
            case R.id.tv_commit:
                if (editTitle.getText().toString().trim().length() == 0) {
                    initPopupPraise();

                } else {
                    if (title != null) {
                        mPresenter.createFolder(user_id, user_token, editTitle.getText().toString().trim(), open_type, "");
                    } else {
                        mPresenter.createFolder(user_id, user_token, editTitle.getText().toString().trim(), open_type, title);

                    }
                }
                break;
            case R.id.clear_title:
                editTitle.setText("");
                editTitle.clearFocus();
                editTitle.setFocusable(false);
                break;
            case R.id.img_open:
                if (open_type == 1) {
                    imgOpen.setImageResource(R.mipmap.on);
                    open_type = 2;
                } else {
                    imgOpen.setImageResource(R.mipmap.off);
                    open_type = 1;
                }
                break;
        }
    }


    @Override
    public void createFolderSuccess(BaseBean baseBean) {
        Toast.makeText(this, "创建文件夹成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void deleteFolderSuccess(BaseBean baseBean) {
        Toast.makeText(this, "删除文件夹成功", Toast.LENGTH_SHORT).show();
        finish();
    }
}
