package com.iyoyogo.android.ui.mine.collection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.CollectionFolderContentAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.collection.CollectionFolderContentBean;
import com.iyoyogo.android.contract.CollectionFolderContentContract;
import com.iyoyogo.android.presenter.CollectionFolderContentPresenter;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
    private CollectionFolderContentAdapter collectionFolderContentAdapter;
    private List<String> mCheckedData = new ArrayList<>();//将选中数据放入里面
    List<CollectionFolderContentBean.DataBean.ListBean> mList = new ArrayList<>();
    private boolean isSelectedAll = true;//用来控制点击全选，全选和全不选相互切换
    private String name;
    int open;

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        defaultTitleTvId.setText(name);
        folder_id = intent.getIntExtra("folder_id", 0);
        open=intent.getIntExtra("open",0);
        user_id = SpUtils.getString(DefaultCollectionActivity.this, "user_id", null);
        user_token = SpUtils.getString(DefaultCollectionActivity.this, "user_token", null);
        functionBottom.setVisibility(View.GONE);
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

                break;
            case R.id.default_move_but_id:

                break;
            case R.id.default_delete_but_id:

                break;
        }
    }



    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        mPresenter.getCollectionFolderContent(user_id, user_token, folder_id, 1);
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
                intent.putExtra("folder_id",folder_id);
                intent.putExtra("type",2);

                intent.putExtra("open",open);
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
                popupWindow.dismiss();
            }
        });

    }
    /**
     * 全选和反选
     */
    private void selectAllMain() {
       /* if (mRadioAdapter == null) return;
        if (!isSelectAll) {
            for (int i = 0, j = mRadioAdapter.getMyLiveList().size(); i < j; i++) {
                mRadioAdapter.getMyLiveList().get(i).setSelect(true);
            }
            index = mRadioAdapter.getMyLiveList().size();
            mBtnDelete.setEnabled(true);
            mSelectAll.setText("取消全选");
            isSelectAll = true;
        } else {
            for (int i = 0, j = mRadioAdapter.getMyLiveList().size(); i < j; i++) {
                mRadioAdapter.getMyLiveList().get(i).setSelect(false);
            }
            index = 0;
            mBtnDelete.setEnabled(false);
            mSelectAll.setText("全选");
            isSelectAll = false;
        }
        mRadioAdapter.notifyDataSetChanged();
        setBtnBackground(index);
        mTvSelectNum.setText(String.valueOf(index));*/
    }

    /**
     * 删除逻辑
     */
    private void deleteVideo() {
       /* if (index == 0){
            mBtnDelete.setEnabled(false);
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
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = mRadioAdapter.getMyLiveList().size(), j =0 ; i > j; i--) {
                    MyLiveList myLive = mRadioAdapter.getMyLiveList().get(i-1);
                    if (myLive.isSelect()) {

                        mRadioAdapter.getMyLiveList().remove(myLive);
                        index--;
                    }
                }
                index = 0;
                mTvSelectNum.setText(String.valueOf(0));
                setBtnBackground(index);
                if (mRadioAdapter.getMyLiveList().size() == 0){
                    mLlMycollectionBottomDialog.setVisibility(View.GONE);
                }
                mRadioAdapter.notifyDataSetChanged();
                builder.dismiss();
            }
        });*/
    }
    private void updataEditMode() {
      /*  mEditMode = mEditMode == MYLIVE_MODE_CHECK ? MYLIVE_MODE_EDIT : MYLIVE_MODE_CHECK;
        if (mEditMode == MYLIVE_MODE_EDIT) {
            mBtnEditor.setText("取消");
            mLlMycollectionBottomDialog.setVisibility(View.VISIBLE);
            editorStatus = true;
        } else {
            mBtnEditor.setText("编辑");
            mLlMycollectionBottomDialog.setVisibility(View.GONE);
            editorStatus = false;
            clearAll();
        }
        mRadioAdapter.setEditMode(mEditMode);*/
    }


    private void clearAll() {
//        mTvSelectNum.setText(String.valueOf(0));
//        isSelectAll = false;
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

        collectionFolderContentAdapter = new CollectionFolderContentAdapter(DefaultCollectionActivity.this, mList);
        recyclerCollectionFolderContent.setAdapter(collectionFolderContentAdapter);

    }



}
