package com.iyoyogo.android.presenter;

import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.SameBean;
import com.iyoyogo.android.bean.comment.CommentBean;
import com.iyoyogo.android.bean.mine.AboutMeBean;
import com.iyoyogo.android.contract.AboutMeContract;
import com.iyoyogo.android.contract.SameContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class SamePresenter extends BasePresenter<SameContract.View> implements SameContract.Presenter {


    public SamePresenter(SameContract.View mView) {
        super(mView);
    }


    /**
     * user_id	是	string	用户id
     * user_token	是	string	user_token
     * lng	是	string	经度
     * lat	是	string	纬度
     * page	是	string	页码
     * page_size	否	string	每页显示条数
     */
    @Override
    public void getSameList(String user_id, String user_token, String lng, String lat, int page, String page_size) {
        DataManager.getFromRemote()
                .getSameList(user_id, user_token, lng, lat, page, page_size)
                .subscribe(new ApiObserver<SameBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(SameBean commentBean) {
                        if (page == 1) {
                            mView.onSameList(commentBean);
                        } else {
                            mView.onMoreSameList(commentBean);
                        }
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }
}
