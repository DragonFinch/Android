package com.iyoyogo.android.presenter;

import android.util.Log;

import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.search.ClerBean;
import com.iyoyogo.android.bean.search.SearchBean;
import com.iyoyogo.android.bean.search.searchInfo;
import com.iyoyogo.android.contract.SearchContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

import java.util.List;

public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {
    public SearchPresenter(SearchContract.View mView) {
        super(mView);
    }

    @Override
    public void getSearch(String user_id, String user_token) {
        DataManager.getFromRemote()
                .search(user_id,user_token)
                .subscribe(new ApiObserver<searchInfo>(mView,this){
                    @Override
                    protected void doOnSuccess(searchInfo searchBean) {
                        if (searchBean != null){
                            mView.getRecommendTopicSuccess(searchBean);
                        }

                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {

                        return true;

                    }
                });
    }

    @Override
    public void getSearchCler(String user_id, String user_token) {
        DataManager.getFromRemote().searchCler(user_id,user_token).subscribe(new ApiObserver<ClerBean>(mView,this) {
            @Override
            protected void doOnSuccess(ClerBean clerBean) {
                mView.getData(clerBean);
            }
        });
    }


}
