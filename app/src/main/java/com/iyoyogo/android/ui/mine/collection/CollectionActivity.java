package com.iyoyogo.android.ui.mine.collection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.MineCollectionAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.collection.MineCollectionBean;
import com.iyoyogo.android.contract.CollectionContract;
import com.iyoyogo.android.presenter.CollectionPresenter;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.StatusBarUtils;
import com.iyoyogo.android.utils.imagepicker.activities.ImagesPickActivity;
import com.iyoyogo.android.utils.imagepicker.activities.ImagesPreviewActivity;
import com.iyoyogo.android.utils.imagepicker.component.OnItemChooseCallback;
import com.iyoyogo.android.utils.imagepicker.component.OnRecyclerViewItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.layout_collection_null)
    LinearLayout layoutCollectionNull;
    @BindView(R.id.layout_his_collection_null)
    LinearLayout layoutHisCollectionNull;
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
        managerCollectionFolder.setText("管理");
        user_id = SpUtils.getString(CollectionActivity.this, "user_id", null);
        user_token = SpUtils.getString(CollectionActivity.this, "user_token", null);
        Intent intent = getIntent();
        int coll = intent.getIntExtra("collect", 0);
        String id = intent.getStringExtra("id");
        if (coll == 1) {
            recyclerCollectionFolder.setVisibility(View.VISIBLE);
            layoutCreateCollection.setVisibility(View.GONE);
            managerCollectionFolder.setVisibility(View.GONE);
            title.setText("TA的收藏");
            mPresenter.getHisCollectionFold(user_id, user_token, id);
        } else if (coll == 2) {
            recyclerCollectionFolder.setVisibility(View.VISIBLE);
            layoutCreateCollection.setVisibility(View.VISIBLE);
            managerCollectionFolder.setVisibility(View.VISIBLE);
            title.setText("我的收藏");
            mPresenter.getCollectionFold(user_id, user_token);
        }
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
            managerCollectionFolder.setText("删除");

            editorStatus = true;
        } else {
            List<String> idList = mineCollectionAdapter.getIdList();
            int[] like = new int[idList.size()];
            for (int i = 0; i < idList.size(); i++) {
                like[i] = Integer.valueOf(idList.get(i));
            }
            for (int i = 0; i < like.length; i++) {
                Log.d("CollectionActivity", "like[i]:" + like[i]);
            }

            Log.d("CollectionActivity", "idList.size():" + idList.size());
            Log.d("CollectionActivity", "idList.size():" + like.length);

            if (idList.size() > 0) {

                mPresenter.deleteCollectionFolder(user_id, user_token, like);
                managerCollectionFolder.setText("管理");

                editorStatus = false;
                clearAll();
            } else {
                Log.d("CollectionActivity", "idList.size():" + idList.size());
                managerCollectionFolder.setText("管理");

                editorStatus = false;
                clearAll();
            }


        }
        mineCollectionAdapter.setEditMode(mEditMode);
    }

    public static Integer[] ifRepeat(Integer[] arr) {
        //用来记录去除重复之后的数组长度和给临时数组作为下标索引  
        int t = 0;
        //临时数组  
        Integer[] tempArr = new Integer[arr.length];
        //遍历原数组  
        for (int i = 0; i < arr.length; i++) {
            //声明一个标记，并每次重置  
            boolean isTrue = true;
            //内层循环将原数组的元素逐个对比  
            for (int j = i + 1; j < arr.length; j++) {
                //如果发现有重复元素，改变标记状态并结束当次内层循环  
                if (arr[i] == arr[j]) {
                    isTrue = false;
                    break;
                }
            }
            //判断标记是否被改变，如果没被改变就是没有重复元素  
            if (isTrue) {
                //没有元素就将原数组的元素赋给临时数组  
                tempArr[t] = arr[i];
                //走到这里证明当前元素没有重复，那么记录自增  
                t++;
            }
        }
        //声明需要返回的数组，这个才是去重后的数组  
        Integer[] newArr = new Integer[t];
        //用arraycopy方法将刚才去重的数组拷贝到新数组并返回  
        System.arraycopy(tempArr, 0, newArr, 0, t);
        return newArr;
    }

    @Override
    public void getCollectionSuccess(MineCollectionBean mineCollectionBean) {
        List<MineCollectionBean.DataBean.TreeBean> tree = mineCollectionBean.getData().getTree();
       /* if (tree.size() == 0) {
            layoutCollectionNull.setVisibility(View.VISIBLE);
        }*/
        mineCollectionAdapter = new MineCollectionAdapter(CollectionActivity.this, tree);
        MyChooseCallback callback = new MyChooseCallback();
        MyOnItemClickListener listener = new MyOnItemClickListener();

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
    public void getHisCollectionSuccess(MineCollectionBean mineCollectionBean) {
        List<MineCollectionBean.DataBean.TreeBean> tree = mineCollectionBean.getData().getTree();
        mineCollectionAdapter = new MineCollectionAdapter(CollectionActivity.this, tree);
        recyclerCollectionFolder.setLayoutManager(new LinearLayoutManager(CollectionActivity.this));
        recyclerCollectionFolder.setAdapter(mineCollectionAdapter);
        mineCollectionAdapter.notifyAdapter(tree, false);
        if (tree.size() == 0) {
            recyclerCollectionFolder.setVisibility(View.GONE);
            layoutHisCollectionNull.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void deleteCollectionFolderSuccess(BaseBean baseBean) {
        Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * Item点击事件的监听类
     */
    private class MyOnItemClickListener implements OnRecyclerViewItemClickListener {

        @Override
        public void onItemClick(int position) {

        }
    }

    /**
     * Item选则事件的监听类
     */
    private class MyChooseCallback implements OnItemChooseCallback {

        @Override
        public void chooseState(int position, boolean isChosen) {

        }

        @Override
        public void countNow(int countNow) {
        }

        @Override
        public void countWarning(int count) {

        }
    }
}
