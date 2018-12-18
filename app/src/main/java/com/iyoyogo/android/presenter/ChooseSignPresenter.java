package com.iyoyogo.android.presenter;

import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.yoji.label.AddLabelBean;
import com.iyoyogo.android.bean.yoji.label.LabelListBean;
import com.iyoyogo.android.contract.ChooseSignContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class ChooseSignPresenter extends BasePresenter<ChooseSignContract.View> implements ChooseSignContract.Presenter {
    public ChooseSignPresenter(ChooseSignContract.View mView) {
        super(mView);
    }

    @Override
    public void getLabelList(String user_id, String user_token) {
        DataManager.getFromRemote()
                .getLabelList(user_id, user_token)
                .subscribe(new ApiObserver<LabelListBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(LabelListBean labelListBean) {
                        LabelListBean.DataBean data = labelListBean.getData();
                        if (data != null) {
                            mView.getLabelListSuccess(data);
                        }
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }

    @Override
    public void addLabel(String user_id, String user_token, int label_id, int type, String label) {
        DataManager.getFromRemote()
                .addLabel(user_id, user_token, label_id, type, label)
                .subscribe(new ApiObserver<AddLabelBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(AddLabelBean labelListBean) {
                        AddLabelBean.DataBean data = labelListBean.getData();
                        if (data != null) {
                            mView.addLabelSuccess(data);
                        }
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }

    @Override
    public void deleteLabel(String user_id, String user_token, int label_id) {
        DataManager.getFromRemote()
                .deleteLabel(user_id, user_token, label_id)
                .subscribe(new ApiObserver<BaseBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(BaseBean baseBean) {
                        mView.deleteLabelSuccess(baseBean);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }
}
