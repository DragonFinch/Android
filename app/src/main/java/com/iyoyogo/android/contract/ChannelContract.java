package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.yoxiu.channel.ChannelBean;

import java.util.List;
/**
 * 频道的契约类
 */
public interface ChannelContract {
    interface View extends IBaseView{
        void getChannelSuccess(List<ChannelBean.DataBean.ListBean> list);
    }
    interface Presenter extends IBasePresenter{
        void getChannel(String user_id,String user_token);
    }
}
