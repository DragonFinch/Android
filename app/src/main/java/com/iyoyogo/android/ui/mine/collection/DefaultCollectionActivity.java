package com.iyoyogo.android.ui.mine.collection;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.CollectionFolderContentAdapter;
import com.iyoyogo.android.adapter.DefaultCollectionAdapter;
import com.iyoyogo.android.adapter.viewholder.OnItemClickLitener;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.collection.CollectionFolderBean;
import com.iyoyogo.android.bean.collection.CollectionFolderContentBean;
import com.iyoyogo.android.contract.CollectionFolderContentContract;
import com.iyoyogo.android.presenter.CollectionFolderContentPresenter;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 收藏夹内容
 */
public class DefaultCollectionActivity extends BaseActivity<CollectionFolderContentContract.Presenter> implements CollectionFolderContentContract.View {
    private SparseBooleanArray stateCheckedMap = new SparseBooleanArray();//用来存放CheckBox的选中状态，true为选中,false为没有选中
    @BindView(R.id.default_back_iv_id)
    ImageView defaultBackIvId;
    @BindView(R.id.default_spot_iv_id)
    ImageView defaultSpotIvId;
    @BindView(R.id.default_title_tv_id)
    TextView defaultTitleTvId;
    @BindView(R.id.default_checkbox_id)
    CheckBox defaultCheckboxId;
    @BindView(R.id.default_move_but_id)
    TextView defaultMoveButId;
    @BindView(R.id.default_delete_but_id)
    TextView defaultDeleteButId;
    @BindView(R.id.function_bottom)
    RelativeLayout functionBottom;
    @BindView(R.id.default_collection_rl_id)
    RelativeLayout defaultCollectionRlId;
    @BindView(R.id.recycler_collection_folder_content)
    RecyclerView recyclerCollectionFolderContent;
    private PopupWindow popupWindow;
    private String user_token;
    private String user_id;
    private int folder_id;
    private static final int MYLIVE_MODE_CHECK = 0;
    private static final int MYLIVE_MODE_EDIT = 1;
    private CollectionFolderContentAdapter collectionFolderContentAdapter;
    private List<String> mCheckedData = new ArrayList<>();//将选中数据放入里面
    List<CollectionFolderContentBean.DataBean.ListBean> mList = new ArrayList<>();
    private boolean isSelectedAll = true;//用来控制点击全选，全选和全不选相互切换
    private String name;
    int open;
    private int mEditMode = MYLIVE_MODE_CHECK;
    private boolean isSelectAll = false;
    private boolean editorStatus = false;
    private int index = 0;
    private RecyclerView popup_favorites_prompt_rv_id;
    private int folder_ids;
    private String title;
    String yo_user_id;

    @Override
    protected void initView() {
        super.initView();
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        defaultTitleTvId.setText(name);
        folder_id = intent.getIntExtra("folder_id", 0);
        title = intent.getStringExtra("title");
        open = intent.getIntExtra("open", 0);
        user_id = SpUtils.getString(DefaultCollectionActivity.this, "user_id", null);
        user_token = SpUtils.getString(DefaultCollectionActivity.this, "user_token", null);
        yo_user_id = intent.getStringExtra("user_id");
        functionBottom.setVisibility(View.GONE);
        Log.d("DefaultCollectionActivi", yo_user_id);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_default_collection;
    }

    @Override
    protected CollectionFolderContentContract.Presenter createPresenter() {
        return new CollectionFolderContentPresenter(this);
    }


    @OnClick({R.id.default_back_iv_id, R.id.default_spot_iv_id, R.id.default_checkbox_id, R.id.default_move_but_id, R.id.default_delete_but_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.default_back_iv_id:
                finish();
                break;
            case R.id.default_spot_iv_id:
                initSearchPopupWindow();
                break;
            case R.id.default_checkbox_id:
                selectAllMain();
                break;
            case R.id.default_move_but_id:

//                mPresenter.moveCollectionFolder(user_id, user_token, objects1);
                mPresenter.getCollectionFolder(user_id, user_token);
                initPopup();
                break;
            case R.id.default_delete_but_id:
                deleteVideo();
                break;
        }
    }

    private void initPopup() {
        View view = getLayoutInflater().from(DefaultCollectionActivity.this).inflate(R.layout.popup_choose_favorites, null);

        PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(DefaultCollectionActivity.this, 311), true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        backgroundAlpha(0.6f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
        ImageView popup_favorites_prompt_iv_id = view.findViewById(R.id.popup_favorites_prompt_iv_id);
        popup_favorites_prompt_rv_id = view.findViewById(R.id.popup_favorites_prompt_rv_id);
        Button popup_favorites_prompt_but_id = view.findViewById(R.id.popup_favorites_prompt_but_id);


        popup_favorites_prompt_iv_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popup_favorites_prompt_but_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> idList1 = collectionFolderContentAdapter.getIdList();
                Integer[] like = new Integer[idList1.size()];
                for (int i = 0; i < idList1.size(); i++) {
                    like[i] = Integer.valueOf(idList1.get(i));
                }

                mPresenter.moveCollectionFolder(user_id, user_token, like, folder_ids);
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(findViewById(R.id.default_collection_rl_id), Gravity.BOTTOM, 0, 0);
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mPresenter.getCollectionFolderContent(user_id, user_token, folder_id, 1);
        if (user_id.equals(yo_user_id)) {
            defaultSpotIvId.setVisibility(View.VISIBLE);
        } else {
            functionBottom.setVisibility(View.GONE);
            defaultSpotIvId.setVisibility(View.GONE);
        }
    }


    //初始化搜索popup (可以启动初始化)
    private void initSearchPopupWindow() {

        View contentview = getLayoutInflater().inflate(R.layout.popup_spot_default_collection, null);//自己的弹框布局


        contentview.setFocusable(true); // 这个很重要
        contentview.setFocusableInTouchMode(true);
        popupWindow = new PopupWindow(contentview, DensityUtil.dp2px(DefaultCollectionActivity.this, 110), DensityUtil.dp2px(DefaultCollectionActivity.this, 110), true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(defaultSpotIvId);
        LinearLayout layout_edit = contentview.findViewById(R.id.layout_edit);
        LinearLayout layout_manager = contentview.findViewById(R.id.layout_manager);
        backgroundAlpha(0.6f);
        layout_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DefaultCollectionActivity.this, CreateCollectionFolderActivity.class);
                intent.putExtra("folder_id", folder_id);
                intent.putExtra("type", 2);
                intent.putExtra("name", name);
                intent.putExtra("open", open);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
        layout_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updataEditMode();
                popupWindow.dismiss();
            }
        });

    }

    /**
     * 全选和反选
     */
    private void selectAllMain() {
        if (collectionFolderContentAdapter == null) return;
        if (!isSelectAll) {
            for (int i = 0, j = collectionFolderContentAdapter.getMyLiveList().size(); i < j; i++) {
                collectionFolderContentAdapter.getMyLiveList().get(i).setSelect(true);
            }
            index = collectionFolderContentAdapter.getMyLiveList().size();
            defaultDeleteButId.setEnabled(true);
//            mSelectAll.setText("取消全选");
            isSelectAll = true;
        } else {
            for (int i = 0, j = collectionFolderContentAdapter.getMyLiveList().size(); i < j; i++) {
                collectionFolderContentAdapter.getMyLiveList().get(i).setSelect(false);
            }
            index = 0;
            defaultDeleteButId.setEnabled(false);
//            mSelectAll.setText("全选");
            isSelectAll = false;
        }
        collectionFolderContentAdapter.notifyDataSetChanged();
//        setBtnBackground(index);
//        mTvSelectNum.setText(String.valueOf(index));
    }

    /**
     * 删除逻辑
     */
    private void deleteVideo() {
        if (index == 0) {
            defaultDeleteButId.setEnabled(false);
            return;
        }
        final AlertDialog builder = new AlertDialog.Builder(this)
                .create();
        builder.show();
        if (builder.getWindow() == null) return;
        builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
        TextView msg = (TextView) builder.findViewById(R.id.tv_msg);
        Button cancle = (Button) builder.findViewById(R.id.btn_cancle);
        Button sure = (Button) builder.findViewById(R.id.btn_sure);
        if (msg == null || cancle == null || sure == null) return;

        if (index == 1) {
            msg.setText("删除后不可恢复，是否删除该条目？");
        } else {
            msg.setText("删除后不可恢复，是否删除这" + index + "个条目？");
        }
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        List<String> idList = collectionFolderContentAdapter.getIdList();
        Integer[] like = new Integer[idList.size()];
        for (int i = 0; i < idList.size(); i++) {
            like[i] = Integer.valueOf(idList.get(i));
        }


        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.removeCollectionContent(user_id, user_token, like);
                for (int i = collectionFolderContentAdapter.getMyLiveList().size(), j = 0; i > j; i--) {
                    CollectionFolderContentBean.DataBean.ListBean listBean = collectionFolderContentAdapter.getMyLiveList().get(i - 1);
                    if (listBean.isSelect()) {

                        collectionFolderContentAdapter.getMyLiveList().remove(listBean);
                        index--;
                    }
                }
                index = 0;
//                mTvSelectNum.setText(String.valueOf(0));
//                setBtnBackground(index);
                if (collectionFolderContentAdapter.getMyLiveList().size() == 0) {
                    functionBottom.setVisibility(View.GONE);
                }
                collectionFolderContentAdapter.notifyDataSetChanged();
                builder.dismiss();
                Toast.makeText(DefaultCollectionActivity.this, "删除", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updataEditMode() {
        mEditMode = mEditMode == MYLIVE_MODE_CHECK ? MYLIVE_MODE_EDIT : MYLIVE_MODE_CHECK;
        if (mEditMode == MYLIVE_MODE_EDIT) {
//            mBtnEditor.setText("取消");
            functionBottom.setVisibility(View.VISIBLE);
            editorStatus = true;
        } else {
//            mBtnEditor.setText("编辑");
            functionBottom.setVisibility(View.GONE);
            editorStatus = false;
            clearAll();
        }
        collectionFolderContentAdapter.setEditMode(mEditMode);
    }


    private void clearAll() {
//        mTvSelectNum.setText(String.valueOf(0));
        isSelectAll = false;
//        mSelectAll.setText("全选");
//        setBtnBackground(0);
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        getWindow().setAttributes(lp); //act 是上下文context

    }


    @Override
    public void getCollectionFolderContentSuccess(List<CollectionFolderContentBean.DataBean.ListBean> list) {
        mList.addAll(list);
        recyclerCollectionFolderContent.setLayoutManager(new GridLayoutManager(DefaultCollectionActivity.this, 3));
        collectionFolderContentAdapter = new CollectionFolderContentAdapter(DefaultCollectionActivity.this, mList);
        recyclerCollectionFolderContent.setAdapter(collectionFolderContentAdapter);
        collectionFolderContentAdapter.notifyAdapter(mList, false);
        collectionFolderContentAdapter.setOnItemClickListener(new CollectionFolderContentAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int pos, List<CollectionFolderContentBean.DataBean.ListBean> myLiveList) {
                if (editorStatus) {
                    CollectionFolderContentBean.DataBean.ListBean listBean = myLiveList.get(pos);
                    boolean isSelect = listBean.isSelect();
                    if (!isSelect) {
                        index++;
                        listBean.setSelect(true);
                        if (index == myLiveList.size()) {
                            isSelectAll = true;
//                            mSelectAll.setText("取消全选");
                        }

                    } else {
                        listBean.setSelect(false);
                        index--;
                        isSelectAll = false;
//                        mSelectAll.setText("全选");
                    }
//                    setBtnBackground(index);
//                    mTvSelectNum.setText(String.valueOf(index));
                    collectionFolderContentAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void removeCollectionContentSuccess(BaseBean baseBean) {
        finish();
    }

    @Override
    public void moveCollectionFolderSuccess(BaseBean baseBean) {
        Toast.makeText(this, "移动成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void getCollectionFolderSuccess(CollectionFolderBean.DataBean collectionFolderBean) {
        List<CollectionFolderBean.DataBean.ListBean> list = collectionFolderBean.getList();
        popup_favorites_prompt_rv_id.setHasFixedSize(true);
        popup_favorites_prompt_rv_id.setLayoutManager(new LinearLayoutManager(DefaultCollectionActivity.this));
        DefaultCollectionAdapter defaultCollectionAdapter = new DefaultCollectionAdapter(DefaultCollectionActivity.this, list, title);
        popup_favorites_prompt_rv_id.setAdapter(defaultCollectionAdapter);
        defaultCollectionAdapter.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                defaultCollectionAdapter.setSelection(position);
                folder_ids = list.get(position).getFolder_id();

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

    }


}
