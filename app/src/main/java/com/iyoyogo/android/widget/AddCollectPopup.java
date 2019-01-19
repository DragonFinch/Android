package com.iyoyogo.android.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.AddCollectPopupAdapter;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.TextChangeListener;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author zhuhui
 * @date 2019/1/17
 * @description
 */
public class AddCollectPopup extends BasePopupWindow implements BaseQuickAdapter.OnItemClickListener, View.OnClickListener, TextChangeListener.TextChange {

    private final String       userId;
    private final String       token;
    private       RecyclerView mRecyclerView;

    private LinearLayout mLlSelect;
    private LinearLayout mLlCreate;
    private EditText     mEtTitle;
    private ImageView    mIvClearEdit;
    private ImageView    mIvPrivate;
    private Button       mBtnDone;

    private AddCollectPopupAdapter mAdapter;

    private AddCollectListener mAddCollectListener;

    private int yoId;

    private int isOpen = 2;

    public AddCollectPopup(Context context) {
        super(context);
        setPopupGravity(Gravity.BOTTOM);
        setBackgroundColor(Color.parseColor("#8f000000"));
        findViewById(R.id.iv_close).setOnClickListener(this);
        findViewById(R.id.ll_create_collect).setOnClickListener(this);
        mBtnDone = findViewById(R.id.btn_done);
        mLlSelect = findViewById(R.id.ll_select);
        mLlCreate = findViewById(R.id.ll_create);
        mIvClearEdit = findViewById(R.id.iv_clera_edit);
        mIvPrivate = findViewById(R.id.iv_private);
        mBtnDone.setOnClickListener(this);
        mIvClearEdit.setOnClickListener(this);
        mIvPrivate.setOnClickListener(this);
        mEtTitle = findViewById(R.id.et_title);
        mEtTitle.addTextChangedListener(new TextChangeListener(mEtTitle, this));
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new AddCollectPopupAdapter(R.layout.item_add_collect_popup);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        userId = SpUtils.getString(getContext(), "user_id", null);
        token = SpUtils.getString(getContext(), "user_token", null);

        initData();
    }

    private void initData() {
        DataManager.getFromRemote().getCollectionFolder(getContext(),userId, token).subscribe(collectionFolderBean -> {
            mAdapter.setNewData(collectionFolderBean.getData().getList());
        });
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_add_collect);
    }

    @Override
    protected Animation onCreateShowAnimation() {
        return getTranslateVerticalAnimation(1f, 0, 260);
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return getTranslateVerticalAnimation(0, 1f, 260);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        DataManager.getFromRemote().addCollection(getContext(),userId, token, mAdapter.getData().get(position).getFolder_id(), yoId)
                .subscribe(addCollectionBean -> {
                    dismiss();
                    if (mAddCollectListener != null) {
                        mAddCollectListener.onAddCollectSuccess(addCollectionBean.getData().getId());
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.ll_create_collect:
                mLlCreate.setVisibility(View.VISIBLE);
                mLlSelect.setVisibility(View.GONE);
                break;

            case R.id.iv_clera_edit:
                mEtTitle.setText("");
                mIvClearEdit.setVisibility(View.GONE);
                break;

            case R.id.btn_done:
                DataManager.getFromRemote().create_folder(getContext(),userId, token, mEtTitle.getText().toString().trim(), isOpen, "")
                        .subscribe(baseBean -> {
                            initData();
                            mLlCreate.setVisibility(View.GONE);
                            mLlSelect.setVisibility(View.VISIBLE);
                        });
                break;

            case R.id.iv_private:
                isOpen = isOpen == 2 ? 1 : 2;
                mIvPrivate.setImageResource(isOpen == 2 ? R.mipmap.off : R.mipmap.on);
                break;
        }
    }

    public void setYoId(int yoId) {
        this.yoId = yoId;
    }

    public void setAddCollectListener(AddCollectListener addCollectListener) {
        mAddCollectListener = addCollectListener;
    }

    @Override
    public void onTextChange(View view, String s) {
        mBtnDone.setEnabled(!TextUtils.isEmpty(s));
        mIvClearEdit.setVisibility(TextUtils.isEmpty(s)?View.GONE:View.VISIBLE);
    }

    public interface AddCollectListener {
        void onAddCollectSuccess(String id);
    }

    @Override
    public void onDismiss() {
        super.onDismiss();
        mLlCreate.setVisibility(View.GONE);
        mLlSelect.setVisibility(View.VISIBLE);

    }
}