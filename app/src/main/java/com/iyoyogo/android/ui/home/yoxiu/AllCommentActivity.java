package com.iyoyogo.android.ui.home.yoxiu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.CollectionFolderAdapter;
import com.iyoyogo.android.adapter.YoXiuDetailAdapter;
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
import com.iyoyogo.android.ui.home.yoji.UserHomepageActivity;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.widget.CircleImageView;
import com.umeng.debug.log.I;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 所有评论
 */
public class AllCommentActivity extends BaseActivity<YoXiuDetailContract.Presenter> implements YoXiuDetailContract.View {


    @BindView(R.id.all_comment_tv)
    TextView allCommentTv;
    @BindView(R.id.all_comment_RecyclerView)
    RecyclerView allCommentRecyclerView;
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
    private List<CommentBean.DataBean.ListBean> list;
    private int size;
    private int id;
    private String user_id;
    private String user_token;
    private PopupWindow popup;
    TextView tv_message;
    ImageView img_tip;
    TextView tv_message_two;
    TextView tv_message_three;
    private int open = 2;
    private boolean isOpen;
    private RecyclerView recycler_collection;
    private YoXiuDetailAdapter yoXiuDetailAdapter;
    private List<CollectionFolderBean.DataBean.ListBean> mList;
    private int id1;
    private int yo_user_id;
    private int yo_attention_id;
    private int count_collect;
    private List<YoXiuDetailBean.DataBean> dataBeans;
    private List<YoXiuDetailBean.DataBean> collection_list;
    private boolean flag = false;
    private View view;
    private int yo_id;
    private int add_collection_id;
    private int folder_id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_all_comment;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        dataBeans = new ArrayList<>();
        Intent intent = getIntent();
        id1 = intent.getIntExtra("id", 0);
        user_id = SpUtils.getString(this, "user_id", null);
        user_token = SpUtils.getString(this, "user_token", null);
        mPresenter.getCommentList(user_id, user_token, 1, id1, 0);
//        mPresenter.getDetail(user_id, user_token, id1);
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
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    layoutParams.setMargins(0, 0, DensityUtil.dp2px(AllCommentActivity.this, 40), 0);
                    editComment.setLayoutParams(layoutParams);

                } else {
                    //失去焦点
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(DensityUtil.dp2px(AllCommentActivity.this, 230), ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, DensityUtil.dp2px(AllCommentActivity.this, 20), 0, 0);
                    editComment.setLayoutParams(layoutParams);
                    tvCollection.setVisibility(View.VISIBLE);
                    tvLike.setVisibility(View.VISIBLE);
                    editComment.setHint("再不评论 , 你会被抓去写作业的~");
                    editComment.setHintTextColor(Color.parseColor("#888888"));
                    sendEmoji.setVisibility(View.GONE);

                }
            }
        });
    }

    @Override
    protected YoXiuDetailContract.Presenter createPresenter() {
        return new YoXiuDetailPresenter(this);
    }


    /***
     * 返回
     */
    @OnClick({R.id.all_comment_back_id, R.id.tv_like, R.id.tv_collection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.all_comment_back_id:
                finish();
                break;
            case R.id.tv_like:
                Drawable like = getResources().getDrawable(
                        R.mipmap.xihuan_xiangqing);
                Drawable liked = getResources().getDrawable(
                        R.mipmap.yixihuan_xiangqing);
                tvLike.setCompoundDrawablesWithIntrinsicBounds(null, dataBeans.get(0).getIs_my_like() > 0 ? liked : like, null, null);
                int count_praise = dataBeans.get(0).getCount_praise();

                Log.d("Test", "dataBeans.get(0).getIs_my_like():" + dataBeans.get(0).getIs_my_like());
                if (dataBeans.get(0).getIs_my_like() > 0) {
                    //由喜欢变为不喜欢，亮变暗
                    tvLike.setCompoundDrawablesWithIntrinsicBounds(null,
                            like, null, null);
                    count_praise -= 1;
                    //设置点赞的数量
                    tvLike.setText(count_praise + "");
                    dataBeans.get(0).setIs_my_like(0);
                    dataBeans.get(0).setCount_praise(count_praise);

                } else {
                    //由不喜欢变为喜欢，暗变亮
                    tvLike.setCompoundDrawablesWithIntrinsicBounds(null,
                            liked, null, null);
                    count_praise += 1;
                    //设置点赞的数量
                    like();
                    tvLike.setText(count_praise + "");
                    dataBeans.get(0).setIs_my_like(1);
                    dataBeans.get(0).setCount_praise(count_praise);
                }
                DataManager.getFromRemote().praise(user_id, user_token, dataBeans.get(0).getId(), 0)
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

    private void createCollectionFolder() {
        backgroundAlpha(0.6f);
        View view = LayoutInflater.from(AllCommentActivity.this).inflate(R.layout.popup_collection, null);
        PopupWindow popup = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(AllCommentActivity.this, 300), true);
        popup.setOutsideTouchable(true);
        popup.setBackgroundDrawable(new ColorDrawable());
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
        popup.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    private void collection() {
        View view = LayoutInflater.from(AllCommentActivity.this).inflate(R.layout.item_collection_list, null);
        popup = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(AllCommentActivity.this, 300), true);
        popup.setOutsideTouchable(true);
        popup.setBackgroundDrawable(new ColorDrawable());

        LinearLayout create_folder = view.findViewById(R.id.create_folder);
        ImageView close_img = view.findViewById(R.id.close_img);
        create_folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
                backgroundAlpha(0.6f);
                createCollectionFolder();
            }
        });
        close_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
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
        popup.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    //隐藏事件PopupWindow
    private class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1.0f);
        }
    }

    //背景透明
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        getWindow().setAttributes(lp); //act 是上下文context

    }
    public void initPopup() {
        view = LayoutInflater.from(AllCommentActivity.this).inflate(R.layout.like_layout, null);
        popup = new PopupWindow(view, DensityUtil.dp2px(AllCommentActivity.this, 300), DensityUtil.dp2px(AllCommentActivity.this, 145), true);
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
        popup.showAtLocation(view, Gravity.CENTER, 0, 0);
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

    @Override
    public void getDetailSuccess(YoXiuDetailBean.DataBean data) {
        initPopup();
        yo_id = data.getId();
        collection_list = new ArrayList<>();
        collection_list.add(data);
        yo_user_id = data.getId();
        yo_attention_id = data.getUser_id();
        dataBeans.add(data);
        collections();
        count_collect = data.getCount_collect();
        tvCollection.setText(count_collect + "");
        int count_praise = data.getCount_praise();
        tvLike.setText(count_praise + "");
        int count_collect = data.getCount_collect();
        tvCollection.setText(count_collect + "");
        if (editComment.getText().toString().length() > 0) {
            mPresenter.addComment(this.user_id, user_token, 0, id1, editComment.getText().toString().trim());
            closeInputMethod();
            yoXiuDetailAdapter.notifyDataSetChanged();
            refresh();
        } else {

        }
        praise();
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
    public void getCommentListSuccess(CommentBean.DataBean data) {//评论列表
        list = data.getList();
        size = list.size();
        allCommentTv.setText("全部评论(" + size + ")");
        yoXiuDetailAdapter = new YoXiuDetailAdapter(AllCommentActivity.this, list);
        allCommentRecyclerView.setAdapter(yoXiuDetailAdapter);
        allCommentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        yoXiuDetailAdapter.setDeleteOnClickListener(new YoXiuDetailAdapter.DeleteOnClickListener() {
            @Override
            public void delete() {
                mPresenter.getCommentList(user_id, user_token, 1, id, 0);
            }
        });
    }

    @Override
    protected void initView() {
        super.initView();
        //输入框
        editComment.setImeOptions(EditorInfo.IME_ACTION_SEND);
        editComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().equals("#")) {
                    startActivity(new Intent(AllCommentActivity.this, MoreTopicActivity.class));
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
                        mPresenter.addComment(user_id, user_token, 0, id1, editComment.getText().toString().trim());
                        closeInputMethod();
                        mPresenter.getCommentList(user_id, user_token, 1, id1, 0);
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
    public void addCommentSuccess(BaseBean baseBean) {
        String msg = baseBean.getMsg();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        editComment.setText("");
        yoXiuDetailAdapter.notifyDataSetChanged();
    }

    @Override
    public void addAttentionSuccess(AttentionBean.DataBean data) {

    }

    @Override
    public void deleteAttentionSuccess(BaseBean baseBean) {

    }

    @Override
    public void createFolderSuccess(BaseBean baseBean) {
        Toast.makeText(this, baseBean.getMsg(), Toast.LENGTH_SHORT).show();
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
        recycler_collection.setLayoutManager(new LinearLayoutManager(AllCommentActivity.this));
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
                folder_id = mList.get(position).getFolder_id();
                mPresenter.getDetail(user_id, user_token, id1);
                Log.d("YoXiuDetailActivity", "folder_id:" + folder_id);
                Log.d("YoXiuDetailActivity", "yo_user_id:" + yo_user_id);
                mPresenter.addCollection(user_id, user_token, folder_id, id1);
//                    Log.d("YoXiuDetailActivity", target_id);
                popup.dismiss();

            }
        });
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


    /***
     * 全部评论—适配器
     */
    class CommentListAdapter extends BaseQuickAdapter<CommentBean.DataBean.ListBean, BaseViewHolder> {
        public CommentListAdapter(int layoutResId, @Nullable List<CommentBean.DataBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CommentBean.DataBean.ListBean item) {
            helper.setText(R.id.all_comment_title, item.getUser_nickname());
            helper.setText(R.id.all_comment_time, item.getCreate_time());
            helper.setText(R.id.all_comment_content, item.getContent());
            helper.setText(R.id.all_comment_zan, item.getCount_comment() + "");
            helper.setText(R.id.all_comment_pinglun, item.getCount_praise() + "");
            Glide.with(mContext).load(item.getUser_logo()).into((ImageView) helper.getView(R.id.all_comment_img_user_icon));
            //点击头像
            CircleImageView all_comment_img_user_icon = helper.getView(R.id.all_comment_img_user_icon);
            all_comment_img_user_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, UserHomepageActivity.class);
                    intent.putExtra("yo_user_id", item.getUser_id() + "");
                    Log.d(TAG, "onClick: " + item.getUser_id());
                    startActivity(intent);
                }
            });
            //
            TextView all_comment_name = helper.getView(R.id.all_comment_name);
            if (user_id.equals(item.getUser_id())) {
                all_comment_name.setVisibility(View.VISIBLE);
            } else {
                all_comment_name.setVisibility(View.GONE);
            }
            //点击评论
            TextView all_comment_pinglun = helper.getView(R.id.all_comment_pinglun);
            all_comment_pinglun.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AllReplyActivity.class);
                    intent.putExtra("id", item.getId());
                    intent.putExtra("time", item.getCreate_time());
                    intent.putExtra("name", item.getUser_nickname());
                    intent.putExtra("zan", item.getCount_comment() + "");
                    intent.putExtra("content", item.getContent());
                    startActivity(intent);
                }
            });
        }
    }
}
