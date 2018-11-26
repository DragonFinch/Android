package com.iyoyogo.android.view;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.iyoyogo.android.R;
import com.iyoyogo.android.utils.DensityUtil;

/**
 * 创建时间：2018/6/27
 * 描述：
 */
public class CollectionAlbumPopuWindow extends BasePopupWindow {
    private View mContentView;
    private Context context;
    private CollectionAlbumCallback collectionAlbumCallback;


    public void setCollectionAlbumCallback(CollectionAlbumCallback collectionAlbumCallback) {
        this.collectionAlbumCallback = collectionAlbumCallback;
    }

    public CollectionAlbumPopuWindow(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        mContentView = LayoutInflater.from(context).inflate(R.layout.popuwindow_collection, null);
        setContentView(mContentView);
        setWidth(DensityUtil.dp2px(context, 100));
        setHeight(DensityUtil.dp2px(context, 153));
        setTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable());
        // 重写onKeyListener
        mContentView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });

        mContentView.findViewById(R.id.rl_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collectionAlbumCallback != null) {
                    collectionAlbumCallback.onShareClick();
                }
            }
        });
        mContentView.findViewById(R.id.rl_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collectionAlbumCallback != null) {
                    collectionAlbumCallback.onEditClick();
                }
            }
        });
        mContentView.findViewById(R.id.rl_manage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collectionAlbumCallback != null) {
                    collectionAlbumCallback.onManageClick();
                }
            }
        });
    }


    @Override
    public View getmContentView() {
        return mContentView;
    }

    public interface CollectionAlbumCallback {
        void onShareClick();

        void onEditClick();

        void onManageClick();
    }
}
