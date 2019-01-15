package com.iyoyogo.android.ui.home.yoji;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.LocaleList;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
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
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.CollectionFolderAdapter;
import com.iyoyogo.android.adapter.YoJiPictureAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.collection.AddCollectionBean;
import com.iyoyogo.android.bean.collection.CollectionFolderBean;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.SoftKeyboardStateHelper;
import com.iyoyogo.android.utils.SpUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;



public class YoJiPictureActivity extends BaseActivity implements SoftKeyboardStateHelper.SoftKeyboardStateListener {
    private int open = 2;
    private boolean isOpen;

    @BindView(R.id.tv_count)
    TextView tvCount;
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
    @BindView(R.id.send_emoji)
    ImageView sendEmoji;
    @BindView(R.id.comment_layout)
    RelativeLayout commentLayout;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.back)
    ImageView back;
    private int index;
    private String yo_id;
    private String count_praise;
    private String count_comment;
    private int is_my_praise;
    private int is_my_collect;
    private ImageView img_tip;
    private TextView tv_message_three;
    private TextView tv_message_two;
    private TextView tv_message;
    List<Bean> dataBeans = new ArrayList<>();
    private String user_token;
    private String user_id;
    PopupWindow popup;
    private RecyclerView recycler_collection;
    private ArrayList<CollectionFolderBean.DataBean.ListBean> mList1;
    private int add_collection_id;
    ArrayList<String> logos;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_yo_ji_picture;
    }

    @Override
    protected void initView() {
        super.initView();
        statusbar();
        editComment.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (editComment.getText().toString().length() > 0) {
                        DataManager.getFromRemote().addComment(user_id, user_token, 0, Integer.parseInt(yo_id), editComment.getText().toString().trim()).subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {
                                editComment.setText("");
                            }
                        });
                        closeInputMethod();

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

        new SoftKeyboardStateHelper(findViewById(R.id.activity_yoji_picture)).addSoftKeyboardStateListener(this);
    }

    private void closeInputMethod() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        if (isOpen) {
            // imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//没有显示则显示
            imm.hideSoftInputFromWindow(editComment.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void collections() {
        Drawable collection = getResources().getDrawable(
                R.mipmap.shoucang_bai);
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

    @Override
    protected void onResume() {
        super.onResume();
        initPopup();
        user_id = SpUtils.getString(getApplicationContext(), "user_id", null);
        user_token = SpUtils.getString(getApplicationContext(), "user_token", null);
        Intent intent = getIntent();
        yo_id = intent.getStringExtra("yo_id");
        count_praise = intent.getStringExtra("count_praise");
        count_comment = intent.getStringExtra("count_comment");
        tvCollection.setText(count_comment + "");
        is_my_praise = intent.getIntExtra("is_my_praise", 0);
        is_my_collect = intent.getIntExtra("is_my_collect", 0);
        Bean bean = new Bean(count_praise, count_comment, is_my_praise, is_my_praise);
        dataBeans.add(bean);
        index = intent.getIntExtra("position", 0);
        logos = intent.getStringArrayListExtra("logos");
        tvCount.setText(index + 1 + "/" + logos.size());
        YoJiPictureAdapter yoJiPictureAdapter = new YoJiPictureAdapter(this, logos);
        vp.setAdapter(yoJiPictureAdapter);
        vp.setCurrentItem(index);
        tvLike.setText(count_praise);
        tvCollection.setText(count_comment);
        collections();
        praise();
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                tvCount.setText(position + 1 + "/" + logos.size());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
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
//      RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) editComment.getLayoutParams();
//      RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//      layoutParams.alignWithParent=true;
        RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) editComment.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//      layoutParams1.setMargins(0, 0, DensityUtil.dp2px(YoXiuDetailActivity.this, 40), 0);
        editComment.setLayoutParams(layoutParams1);
        //失去焦点
    }

    @Override
    public void onSoftKeyboardClosed() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) editComment.getLayoutParams();
//      layoutParams.setMargins(0, DensityUtil.dp2px(YoXiuDetailActivity.this, 20), 0, 0);
        editComment.setLayoutParams(layoutParams);
        tvCollection.setVisibility(View.VISIBLE);
        tvLike.setVisibility(View.VISIBLE);
        editComment.setHint("再不评论 , 你会被抓去写作业的~");
        editComment.setHintTextColor(Color.parseColor("#888888"));
        sendEmoji.setVisibility(View.GONE);
        sendEmoji.setVisibility(View.GONE);
        imgBrow.setVisibility(View.VISIBLE);
    }

    private void praise() {
        Drawable like = getResources().getDrawable(
                R.mipmap.xihuan_bai);
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

    private void createCollectionFolder() {
        backgroundAlpha(0.6f);
        View view = LayoutInflater.from(YoJiPictureActivity.this).inflate(R.layout.popup_collection, null);
        popup = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(YoJiPictureActivity.this, 300), true);
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
                DataManager.getFromRemote().create_folder(user_id, user_token, edit_title_collection.getText().toString().trim(), open, "")
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {

                            }
                        });
//                mPresenter.createCollectionFolder();
            }
        });
        backgroundAlpha(0.6f);
        popup.setOnDismissListener(new poponDismissListener());
        popup.showAtLocation(findViewById(R.id.activity_yoji_picture), Gravity.BOTTOM, 0, 0);
    }

    private void collection() {
        View view = LayoutInflater.from(YoJiPictureActivity.this).inflate(R.layout.item_collection_list, null);
        PopupWindow popup = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(YoJiPictureActivity.this, 300), true);
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
        DataManager.getFromRemote().getCollectionFolder(user_id, user_token)
                .subscribe(new Consumer<CollectionFolderBean>() {
                    @Override
                    public void accept(CollectionFolderBean collectionFolderBean) throws Exception {
                        mList1 = new ArrayList<>();
                        List<CollectionFolderBean.DataBean.ListBean> list = collectionFolderBean.getData().getList();
                        CollectionFolderBean.DataBean.ListBean listBean = new CollectionFolderBean.DataBean.ListBean();
                        listBean.setName("默认收藏");
                        listBean.setOpen(1);
//        mList.add(listBean);
                        mList1.addAll(list);
                        CollectionFolderAdapter collectionFolderAdapter = new CollectionFolderAdapter(mList1);
                        recycler_collection.setLayoutManager(new LinearLayoutManager(YoJiPictureActivity.this));
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
                                if (is_my_collect == 0) {
                                    DataManager.getFromRemote().addCollection(user_id, user_token, folder_id, Integer.parseInt(yo_id))
                                            .subscribe(new Consumer<AddCollectionBean>() {
                                                @Override
                                                public void accept(AddCollectionBean addCollectionBean) throws Exception {
                                                    String collection_id = addCollectionBean.getData().getId();
//                                              add_collection_id = Integer.parseInt(collection_id);
                                                    count_comment += 1;
                                                    Drawable collection = getResources().getDrawable(
                                                            R.mipmap.shoucang_bai);
                                                    Drawable collectioned = getResources().getDrawable(
                                                            R.mipmap.yishoucang_xiangqing);
                                                    dataBeans.get(0).setIs_my_collect(Integer.parseInt(collection_id));
                                                    tvCollection.setCompoundDrawablesWithIntrinsicBounds(null,
                                                            dataBeans.get(0).getIs_my_collect() == 0 ? collection : collectioned, null, null);

                                                    tvCollection.setText(count_comment + "");
                                                }
                                            });
                                    popup.dismiss();
//                    Log.d("YoXiuDetailActivity", target_id);

                                } else {

                                }


                                popup.dismiss();

                            }
                        });
                    }
                });


//        mPresenter.getCollectionFolder(user_id, user_token);


        popup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //点击空白处时，隐藏掉pop窗口


        //添加pop窗口关闭事件
        backgroundAlpha(0.6f);
        popup.setOnDismissListener(new poponDismissListener());
        popup.showAtLocation(findViewById(R.id.activity_yoji_picture), Gravity.BOTTOM, 0, 0);
    }


    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }


    @OnClick({R.id.back, R.id.tv_like, R.id.tv_collection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_like:
                Drawable like = getResources().getDrawable(
                        R.mipmap.xihuan_bai);
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
//                    popup.showAtLocation(findViewById(R.id.activity_yoji_picture), Gravity.CENTER, 0, 0);
                }

                DataManager.getFromRemote().praise(user_id, user_token, Integer.parseInt(yo_id), 0)
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {
                            }
                        });
                break;
            case R.id.tv_collection:
                collection();
                break;
        }
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        getWindow().setAttributes(lp); //act 是上下文context

    }

    //隐藏事件PopupWindow
    private class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1.0f);
        }
    }

    public void initPopup() {
        View view = LayoutInflater.from(YoJiPictureActivity.this).inflate(R.layout.like_layout, null);
        popup = new PopupWindow(view, DensityUtil.dp2px(YoJiPictureActivity.this, 300), DensityUtil.dp2px(YoJiPictureActivity.this, 145), true);
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

        popup.showAtLocation(findViewById(R.id.activity_yoji_picture), Gravity.CENTER, 0, 0);
    }

    private class Bean {
        private String count_praise;
        private String count_comment;
        private int is_my_praise;
        private int is_my_collect;

        public Bean(String count_praise, String count_comment, int is_my_praise, int is_my_collect) {
            this.count_praise = count_praise;
            this.count_comment = count_comment;
            this.is_my_praise = is_my_praise;
            this.is_my_collect = is_my_collect;
        }

        public String getCount_praise() {
            return count_praise;
        }

        public void setCount_praise(String count_praise) {
            this.count_praise = count_praise;
        }

        public String getCount_comment() {
            return count_comment;
        }

        public void setCount_comment(String count_comment) {
            this.count_comment = count_comment;
        }

        public int getIs_my_praise() {
            return is_my_praise;
        }

        public void setIs_my_praise(int is_my_praise) {
            this.is_my_praise = is_my_praise;
        }

        public int getIs_my_collect() {
            return is_my_collect;
        }

        public void setIs_my_collect(int is_my_collect) {
            this.is_my_collect = is_my_collect;
        }
    }


}
