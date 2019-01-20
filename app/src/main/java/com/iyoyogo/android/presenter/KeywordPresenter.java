package com.iyoyogo.android.presenter;

import android.content.Context;
import android.util.Log;

import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.search.GuanZhuBean;
import com.iyoyogo.android.bean.search.KeywordBean;
import com.iyoyogo.android.bean.search.KeywordUserBean;
import com.iyoyogo.android.contract.KeywordContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class KeywordPresenter extends BasePresenter<KeywordContract.View> implements KeywordContract.Presenter {


    public KeywordPresenter(Context context,KeywordContract.View mView) {
        super(context,mView);
    }


    @Override
    public void getKeyWord(Context context,String user_id, String user_token, String search, String type,String key_type) {
        DataManager.getFromRemote()
                .keyword(context,user_id,user_token,search,type,key_type)
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
    public void getSearch(Context context,String user_id, String user_token, String search) {
        DataManager.getFromRemote().srarch(context,user_id,user_token,search).subscribe(new ApiObserver<KeywordUserBean>(mView, this) {
            @Override
            protected void doOnSuccess(KeywordUserBean keywordUserBean) {
                mView.search(keywordUserBean);
                Log.e("hanbaocdjh", "doOnSuccess: "+keywordUserBean.getData().getList().size());
            }
        });
    }
}
