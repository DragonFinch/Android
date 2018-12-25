package com.iyoyogo.android.ui.mine.collection;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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


    @OnClick({R.id.back_iv_id, R.id.manager_collection_folder, R.id.go_home,R.id.layout_create_collection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv_id:
                finish();
                break;
            case R.id.manager_collection_folder:

                break;
            case R.id.layout_create_collection:
                Intent intent = new Intent(CollectionActivity.this, CreateCollectionFolderActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
                break;
            case R.id.go_home:

                break;
        }
    }

    @Override
    public void getCollectionSuccess(MineCollectionBean mineCollectionBean) {
        List<MineCollectionBean.DataBean.TreeBean> tree = mineCollectionBean.getData().getTree();
       /* if (tree.size() == 0) {
            layoutCollectionNull.setVisibility(View.VISIBLE);
        }*/
        MineCollectionAdapter mineCollectionAdapter = new MineCollectionAdapter(CollectionActivity.this, tree);
        recyclerCollectionFolder.setLayoutManager(new LinearLayoutManager(CollectionActivity.this));
        recyclerCollectionFolder.setAdapter(mineCollectionAdapter);
        mineCollectionAdapter.setOnItemClickListener(new MineCollectionAdapter.OnClickListener() {
            @Override
            public void setOnClickListener(View v, int position) {
                Intent intent = new Intent(CollectionActivity.this, DefaultCollectionActivity.class);
                intent.putExtra("type",2);
                String name = tree.get(position).getName();
                int open = tree.get(position).getOpen();
                int folder_id = tree.get(position).getFolder_id();

                intent.putExtra("name",name+"·"+tree.get(position).getCount_record());
                intent.putExtra("folder_id",folder_id);
                intent.putExtra("open",open);
                startActivity(intent);
            }
        });
    }

    @Override
    public void deleteCollectionFolderSuccess(BaseBean baseBean) {

    }


}
