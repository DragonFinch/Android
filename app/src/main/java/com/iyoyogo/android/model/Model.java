package com.iyoyogo.android.model;


import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.home.HomeViewPagerBean;
import com.iyoyogo.android.bean.login.SendMessageBean;
import com.iyoyogo.android.bean.login.interest.InterestBean;
import com.iyoyogo.android.bean.login.login.LoginBean;
import com.iyoyogo.android.bean.login.login.MarketBean;
import com.iyoyogo.android.bean.yoxiu.TypeBean;
import com.iyoyogo.android.bean.yoxiu.YoXiuDetailBean;
import com.iyoyogo.android.bean.yoxiu.YouXiuListBean;
import com.iyoyogo.android.bean.yoxiu.channel.ChannelBean;
import com.iyoyogo.android.bean.yoxiu.topic.CreateTopicBean;
import com.iyoyogo.android.bean.yoxiu.topic.HotTopicBean;
import com.iyoyogo.android.model.en.SendMessageRequest;
import com.iyoyogo.android.net.AddInterestRequest;
import com.iyoyogo.android.net.HttpClient;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class Model {
    private volatile static Model model;

    public static Model getModel() {
        if (model == null) {
            synchronized (Model.class) {
                model = new Model();
            }
        }
        return model;
    }

    /**
     * 合并线程切换
     */
    private <T> ObservableTransformer<T, T> switchThread() {
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 获取兴趣列表
     *
     * @return
     */
    public Observable<InterestBean> getInterestSign(String user_id, String user_token) {
        return HttpClient.getApiService().getInterest(user_id, user_token)
                .compose(this.switchThread());
    }

    /**
     * 发送验证码
     *
     * @param phone
     * @param yzm
     * @param datetime
     * @param sign
     * @return
     */
    public Observable<SendMessageBean> getSendMessage(String phone, String yzm, String datetime, String sign) {
        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setPhone(phone);
        sendMessageRequest.setYzm(yzm);
        sendMessageRequest.setDatetime(datetime);
        sendMessageRequest.setSign(sign);
        return HttpClient.getApiService().sendCode(phone, yzm, datetime, sign)
                .compose(this.switchThread());
    }

    /**
     * 添加兴趣
     *
     * @param interest_ids
     * @param user_id
     * @param user_token
     * @return
     */
    public Observable<BaseBean> addInterest(Integer[] interest_ids, String user_id, String user_token) {
        AddInterestRequest request = new AddInterestRequest();

        return HttpClient.getApiService().addInterest(interest_ids, user_id, user_token)
                .compose(this.switchThread());
    }

    /**
     * login
     *
     * @param login_addr
     * @param phone_info
     * @param app_version
     * @param login_type
     * @param phone
     * @param yzm
     * @param openid
     * @param nickname
     * @param logo
     * @return
     */
    public Observable<LoginBean> login(String login_addr, String phone_info, String app_version, int login_type, String phone, String yzm, String openid, String nickname, String logo) {

        return HttpClient.getApiService().login(login_addr, phone_info, app_version, login_type, phone, yzm, openid, nickname, logo)
                .compose(this.switchThread());
    }

    /**
     * 运维
     *
     * @return
     */
    public Observable<MarketBean> market() {
        return HttpClient.getApiService().market();
    }

    /**
     * 首页
     *
     * @param user_id
     * @param user_token
     * @param type
     * @return
     */
    public Observable<HomeViewPagerBean> homePager(String user_id, String user_token, String type) {
        return HttpClient.getApiService().homePager(user_id, user_token, type)
                .compose(this.switchThread());
    }

    public Observable<TypeBean> getType(String user_id, String user_token) {
        return HttpClient.getApiService().getType(user_id, user_token)
                .compose(this.switchThread());
    }

    public Observable<BaseBean> create_point(String user_id,
                                             String user_token,
                                             String name,
                                             String en_name,
                                             String areas,
                                             String address,
                                             String lng,
                                             String lat,
                                             String type_id) {
        return HttpClient.getApiService().create_pointer(user_id, user_token, name, en_name, areas, address, lng, lat, type_id).compose(this.switchThread());
    }

    public Observable<HotTopicBean> getHotTopic(String user_id,
                                                String user_token,
                                                String search) {
        return HttpClient.getApiService().getHotTopic(user_id, user_token, search)
                .compose(this.switchThread());
    }

    public Observable<HotTopicBean> getNearTopic(String user_id,
                                                 String user_token) {
        return HttpClient.getApiService().getNearTopic(user_id, user_token)
                .compose(this.switchThread());
    }

    public Observable<CreateTopicBean> createTopic(String user_id,
                                                   String user_token, String topic) {
        return HttpClient.getApiService().createTopic(user_id, user_token, topic)
                .compose(this.switchThread());
    }

    public Observable<BaseBean> clearTopic(String user_id,
                                           String user_token) {
        return HttpClient.getApiService().clearTopic(user_id, user_token)
                .compose(this.switchThread());
    }

    public Observable<HotTopicBean> getRecommend(String user_id,
                                                 String user_token) {
        return HttpClient.getApiService().getRecommend(user_id, user_token)
                .compose(this.switchThread());
    }

    public Observable<ChannelBean> getChannel(String user_id,
                                              String user_token) {
        return HttpClient.getApiService().getChannel(user_id, user_token)
                .compose(this.switchThread());
    }

    public Observable<BaseBean> publish_yoXiu(String user_id,
                                              String user_token,
                                              String file_path,
                                              int file_type,
                                              String file_desc,
                                              int[] channel_ids,
                                              Integer[] topic_ids,
                                              int open,
                                              int valid,
                                              String position_name,
                                              String position_areas,
                                              String position_address,
                                              String filter_id) {
        return HttpClient.getApiService().publish_yoXiu(user_id, user_token, file_path, file_type, file_desc, channel_ids, topic_ids, open, valid, position_name, position_areas, position_address, filter_id)
                .compose(this.switchThread());
    }
    public Observable<YoXiuDetailBean> getDetail(String user_id,String user_token,int id){
        return HttpClient.getApiService().getDetail(user_id,user_token,id)
                .compose(this.switchThread());
    }
    public Observable<YouXiuListBean> getYoXiuList(String user_id, String user_token, int page){
        return HttpClient.getApiService().getYoXiuList(user_id,user_token,page)
                .compose(this.switchThread());
    }
    public Observable<BaseBean> praise(String user_id,String user_token,int yo_id,int comment){
      return   HttpClient.getApiService().praise(user_id,user_token,yo_id,comment)
                .compose(this.switchThread());
    }
}
