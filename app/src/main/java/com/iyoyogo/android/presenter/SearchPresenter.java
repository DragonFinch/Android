package com.iyoyogo.android.presenter;

import android.util.Log;

import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.search.SearchBean;
import com.iyoyogo.android.contract.SearchContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {
    public SearchPresenter(SearchContract.View mView) {
        super(mView);
    }

    @Override
    public void getSearch(String user_id, String user_token) {
        DataManager.getFromRemote()
                .search(user_id,user_token)
                .subscribe(new ApiObserver<SearchBean>(mView,this){
                    @Override
                    protected void doOnSuccess(SearchBean searchBean) {
                      /*  List<String> list = searchBean.getData().getList();
                        if (list != null){
                            mView.getRecommendTopicSuccess(list);
                        }*/
                        mView.getRecommendTopicSuccess(searchBean.getData().getList());
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Log.e("han", "doOnFailure: "+"qweqweqweqweqwe" );
                        return true;

                    }
                });
    }
}
