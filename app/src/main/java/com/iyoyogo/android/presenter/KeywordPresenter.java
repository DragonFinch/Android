package com.iyoyogo.android.presenter;

import android.util.Log;

import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.search.GuanZhuBean;
import com.iyoyogo.android.bean.search.KeywordBean;
import com.iyoyogo.android.bean.search.KeywordUserBean;
import com.iyoyogo.android.contract.KeywordContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class KeywordPresenter extends BasePresenter<KeywordContract.View> implements KeywordContract.Presenter {


    public KeywordPresenter(KeywordContract.View mView) {
        super(mView);
    }


    @Override
    public void getKeyWord(String user_id, String user_token, String search, String type,String key_type) {
        DataManager.getFromRemote()
                .keyword(user_id,user_token,search,type,key_type)
                .subscribe(new ApiObserver<KeywordBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(KeywordBean keywordBean) {
                            mView.keyWordMessage(keywordBean);
                        Log.e("doOnSuccess", "doOnSuccess: "+keywordBean.getData().getUser_list().size() );
                    }
                });
    }

    @Override
    public void getGuanZhu(String user_id, String user_token, String target_id) {
        DataManager.getFromRemote()
                .guanzhu(user_id,user_token,target_id)
                .subscribe(new ApiObserver<GuanZhuBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(GuanZhuBean guanZhuBean) {
                        mView.guanZhu(guanZhuBean);
                    }
                });
    }

    @Override
    public void getSearch(String user_id, String user_token, String search) {
        DataManager.getFromRemote().srarch(user_id,user_token,search).subscribe(new ApiObserver<KeywordUserBean>(mView, this) {
            @Override
            protected void doOnSuccess(KeywordUserBean keywordUserBean) {
                mView.search(keywordUserBean);
            }
        });
    }
}
