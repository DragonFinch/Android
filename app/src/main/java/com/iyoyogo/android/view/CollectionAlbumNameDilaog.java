package com.iyoyogo.android.view;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.utils.ToastUtil;

import io.reactivex.disposables.Disposable;

/**
 * 创建时间：2018/6/28
 * 描述：
 */
public class CollectionAlbumNameDilaog extends BottomDialogBase {

    private RecyclerView recyclerView;
    private ImageView closeImage;
//    private List<CollectionBean> collectionBeanList;
    private TextView dialog_sure_tv;
    private Disposable disposable;
    Context context;
    String id;

    public CollectionAlbumNameDilaog(Context context, String id) {
        super(context);
        this.context = context;
        this.id = id;
    }

    @Override
    protected void onCreate() {
        setContentView(R.layout.dialog_collection_album);
        initView();
        initListener();
        // initData();
        getData();
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
//                EventBus.getDefault().post(new MessageEvent("REFLASH_ALBUM"));
                if (disposable != null && !disposable.isDisposed()) {
                    disposable.dispose();
                    disposable = null;
                }
            }
        });
    }

    private void getData() {
     /*   disposable = NetWorkManager.getRequest().getCollectionList()
                .compose(ResponseTransformer.handleResult())
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .subscribe(collectionBean -> {
                    this.collectionBeanList = collectionBean;
                    initData();
                }, throwable -> ToastUtil.showToast(context, ((ApiException) throwable).getDisplayMessage()));*/
    }

    private void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        MyDividerItemDecoration dividerItemDecoration = new MyDividerItemDecoration(mContext, MyDividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.line));
        recyclerView.addItemDecoration(dividerItemDecoration);
//        CollectionAlbumDialogAdapter collectionAlbumDialogAdapter = new CollectionAlbumDialogAdapter(R.layout.item_collection_album_names, collectionBeanList);
//        recyclerView.setAdapter(collectionAlbumDialogAdapter);

    }

    private void initListener() {
        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        dialog_sure_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int folder_id = -1;
//                for (int i = 0; i < collectionBeanList.size(); i++) {
//                    if (collectionBeanList.get(i).isSelect()) {
//                        folder_id = collectionBeanList.get(i).getFolder_id();
//                    }
//                }
                if (folder_id == -1) {
                    ToastUtil.showToast(context, "请选择文件夹！");
                    return;
                }
                Log.i("tag", "folder_id = " + folder_id);
                Log.i("tag", "id = " + id);
                moveFile(folder_id);
            }
        });
    }

    private void moveFile(int folder_id) {
//        disposable = NetWorkManager.getRequest().upUserCollectReply(id, folder_id)
//                .compose(ResponseTransformer.handleResult())
//                .compose(SchedulerProvider.getInstance().applySchedulers())
//                .subscribe(update -> {
//                            ToastUtil.showToast(context, update.getPromessage());
//                            CollectionAlbumNameDilaog.this.dismiss();
//                        }, throwable ->
//                        {
//                            ToastUtil.showToast(context, ((ApiException) throwable).getDisplayMessage());
//                        }
//                );
    }

    private void initView() {
        closeImage = findViewById(R.id.close);
        recyclerView = findViewById(R.id.recyclerView);
        dialog_sure_tv = findViewById(R.id.dialog_sure_tv);
    }

}
