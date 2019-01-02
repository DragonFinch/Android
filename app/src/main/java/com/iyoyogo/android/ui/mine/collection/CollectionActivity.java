package com.iyoyogo.android.ui.mine.collection;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.MineCollectionAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.collection.MineCollectionBean;
import com.iyoyogo.android.contract.CollectionContract;
import com.iyoyogo.android.presenter.CollectionPresenter;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.StatusBarUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的收藏
 */
public class CollectionActivity extends BaseActivity<CollectionContract.Presenter> implements CollectionContract.View {
    @BindView(R.id.back_iv_id)
    ImageView backIvId;
    @BindView(R.id.manager_collection_folder)
    TextView managerCollectionFolder;
    @BindView(R.id.layout_create_collection)
    LinearLayout layoutCreateCollection;
    @BindView(R.id.recycler_collection_folder)
    RecyclerView recyclerCollectionFolder;
    @BindView(R.id.collection_ll_id)
    LinearLayout collectionLlId;
    @BindView(R.id.go_home)
    TextView goHome;
    @BindView(R.id.layout_collection_null)
    LinearLayout layoutCollectionNull;
    private String user_id;
    private String user_token;
    //我的收藏夹
    private static final int MYLIVE_MODE_CHECK = 0;
    private static final int MYLIVE_MODE_EDIT = 1;
    private int mEditMode = MYLIVE_MODE_CHECK;
    private boolean isSelectAll = false;
    private boolean editorStatus = false;
    private int index = 0;
    private MineCollectionAdapter mineCollectionAdapter;

    @Override
    protected void initView() {
        super.initView();
        StatusBarUtils.setWindowStatusBarColor(CollectionActivity.this, R.color.white);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection;
    }


    @Override
    protected void onResume() {
        super.onResume();
        user_id = SpUtils.getString(CollectionActivity.this, "user_id", null);
        user_token = SpUtils.getString(CollectionActivity.this, "user_token", null);
        mPresenter.getCollectionFold(user_id, user_token);
    }

    @Override
    protected CollectionContract.Presenter createPresenter() {
        return new CollectionPresenter(this);
    }


    @OnClick({R.id.back_iv_id, R.id.manager_collection_folder, R.id.go_home, R.id.layout_create_collection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv_id:
                finish();
                break;
            case R.id.manager_collection_folder:
                updataEditMode();
                break;
            case R.id.layout_create_collection:
                Intent intent = new Intent(CollectionActivity.this, CreateCollectionFolderActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
            case R.id.go_home:

                break;
        }
    }

    private void clearAll() {
//        mTvSelectNum.setText(String.valueOf(0));
        isSelectAll = false;
//        mSelectAll.setText("全选");
//        setBtnBackground(0);
    }

    private void updataEditMode() {
        mEditMode = mEditMode == MYLIVE_MODE_CHECK ? MYLIVE_MODE_EDIT : MYLIVE_MODE_CHECK;
        if (mEditMode == MYLIVE_MODE_EDIT) {
            managerCollectionFolder.setText("完成");

            editorStatus = true;
        } else {
            List<String> idList = mineCollectionAdapter.getIdList();
            Integer[] like = new Integer[idList.size()];
            for (int i = 0; i < idList.size(); i++) {
                like[i] = Integer.valueOf(idList.get(i));
            }
            mPresenter.deleteCollectionFolder(user_id, user_token, like);
            managerCollectionFolder.setText("管理");

            editorStatus = false;
            clearAll();
        }
        mineCollectionAdapter.setEditMode(mEditMode);
    }

    @Override
    public void getCollectionSuccess(MineCollectionBean mineCollectionBean) {
        List<MineCollectionBean.DataBean.TreeBean> tree = mineCollectionBean.getData().getTree();
       /* if (tree.size() == 0) {
            layoutCollectionNull.setVisibility(View.VISIBLE);
        }*/
        mineCollectionAdapter = new MineCollectionAdapter(CollectionActivity.this, tree);
        recyclerCollectionFolder.setLayoutManager(new LinearLayoutManager(CollectionActivity.this));
        recyclerCollectionFolder.setAdapter(mineCollectionAdapter);
        mineCollectionAdapter.notifyAdapter(tree, false);
        mineCollectionAdapter.setOnItemClickListener(new MineCollectionAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int pos, List<MineCollectionBean.DataBean.TreeBean> myLiveList) {
                if (editorStatus) {
                    MineCollectionBean.DataBean.TreeBean listBean = myLiveList.get(pos);
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
                    mineCollectionAdapter.notifyDataSetChanged();
                }
            }
        });


    }

    @Override
    public void deleteCollectionFolderSuccess(BaseBean baseBean) {
        Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
        finish();
    }


}
