package com.iyoyogo.android.model;


import android.util.Log;

import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.collection.AddCollectionBean;
import com.iyoyogo.android.bean.collection.CollectionFolderBean;
import com.iyoyogo.android.bean.comment.CommentBean;
import com.iyoyogo.android.bean.home.HomeViewPagerBean;
import com.iyoyogo.android.bean.login.SendMessageBean;
import com.iyoyogo.android.bean.login.interest.InterestBean;
import com.iyoyogo.android.bean.login.login.LoginBean;
import com.iyoyogo.android.bean.login.login.MarketBean;
import com.iyoyogo.android.bean.mine.DraftBean;
import com.iyoyogo.android.bean.mine.GetBindInfoBean;
import com.iyoyogo.android.bean.mine.GetUserInfoBean;
import com.iyoyogo.android.bean.mine.MineMessageBean;
import com.iyoyogo.android.bean.yoji.detail.YoJiDetailBean;
import com.iyoyogo.android.bean.yoji.label.AddLabelBean;
import com.iyoyogo.android.bean.yoji.label.LabelListBean;
import com.iyoyogo.android.bean.yoji.list.YoJiListBean;
import com.iyoyogo.android.bean.yoxiu.TypeBean;
import com.iyoyogo.android.bean.yoxiu.YoXiuDetailBean;
import com.iyoyogo.android.bean.yoxiu.YouXiuListBean;
import com.iyoyogo.android.bean.yoxiu.channel.ChannelBean;
import com.iyoyogo.android.bean.yoxiu.topic.CreateTopicBean;
import com.iyoyogo.android.bean.yoxiu.topic.HotTopicBean;
import com.iyoyogo.android.model.en.SendMessageRequest;
import com.iyoyogo.android.net.AddInterestRequest;
import com.iyoyogo.android.net.HttpClient;

import java.util.List;

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
                                              int yo_id,
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
        return HttpClient.getApiService().publish_yoXiu(user_id, user_token, yo_id, file_path, file_type, file_desc, channel_ids, topic_ids, open, valid, position_name, position_areas, position_address, filter_id)
                .compose(this.switchThread());
    }

    public Observable<YoXiuDetailBean> getDetail(String user_id, String user_token, int id) {
        return HttpClient.getApiService().getDetail(user_id, user_token, id)
                .compose(this.switchThread());
    }

    public Observable<YouXiuListBean> getYoXiuList(String user_id, String user_token, int page) {
        return HttpClient.getApiService().getYoXiuList(user_id, user_token, page)
                .compose(this.switchThread());
    }

    public Observable<BaseBean> praise(String user_id, String user_token, int yo_id, int comment_id) {
        return HttpClient.getApiService().praise(user_id, user_token, yo_id, comment_id)
                .compose(this.switchThread());
    }

    public Observable<MineMessageBean> getPersonInfo(String user_id, String user_token) {
        return HttpClient.getApiService().getPersonInfo(user_id, user_token)
                .compose(this.switchThread());
    }

    public Observable<CommentBean> getComment(String user_id, String user_token, int page, int yo_id, int comment_id) {
        return HttpClient.getApiService().getComment(user_id, user_token, page, yo_id, comment_id)
                .compose(this.switchThread())
                ;
    }

    public Observable<BaseBean> addComment(String user_id, String user_token, int comment_id, int yo_id, String content) {
        return HttpClient.getApiService().addComment(user_id, user_token, comment_id, yo_id, content)
                .compose(this.switchThread());
    }

    public Observable<GetUserInfoBean> getUserInfo(String user_id, String user_token) {
        return HttpClient.getApiService().getUserInfo(user_id, user_token)
                .compose(this.switchThread());
    }

    public Observable<BaseBean> setUserInfo(String user_id,
                                            String user_token,
                                            String user_nickname,
                                            String user_logo,
                                            String user_sex,
                                            String user_birthday,
                                            String user_city) {
        return HttpClient.getApiService().setUserInfo(user_id, user_token, user_nickname, user_logo, user_sex, user_birthday, user_city)
                .compose(this.switchThread());

    }

    public Observable<GetBindInfoBean> getBindInfo(String user_id, String user_token) {
        return HttpClient.getApiService().getBindInfo(user_id, user_token)
                .compose(this.switchThread());
    }

    public Observable<BaseBean> replacePhone(String user_id, String user_token, String phone, String yzm) {
        return HttpClient.getApiService().replacePhone(user_id, user_token, phone, yzm)
                .compose(this.switchThread());
    }

    public Observable<BaseBean> logout(String user_id, String user_token, String addr, String phone_type, String app_version) {
        return HttpClient.getApiService().logout(user_id, user_token, addr, phone_type, app_version)
                .compose(this.switchThread());
    }

    public Observable<AttentionBean> addAttention(String user_id, String user_token, int target_id) {
        return HttpClient.getApiService().addAttention(user_id, user_token, target_id)
                .compose(this.switchThread());
    }

    public Observable<BaseBean> deleteAttention(String user_id, String user_token, int id) {
        return HttpClient.getApiService().deleteAttention(user_id, user_token, id)
                .compose(this.switchThread());
    }

    public Observable<CollectionFolderBean> getCollectionFolder(String user_id, String user_token) {
        return HttpClient.getApiService().getCollectionFolder(user_id, user_token)
                .compose(this.switchThread());
    }

    public Observable<BaseBean> create_folder(String user_id, String user_token, String name, int open, String id) {
        return HttpClient.getApiService().createFolder(user_id, user_token, name, open, id)
                .compose(this.switchThread());

    }

    public Observable<AddCollectionBean> addCollection(String user_id, String user_token, int folder_id, int yo_id) {
        return HttpClient.getApiService().addCollection(user_id, user_token, folder_id, yo_id)
                .compose(this.switchThread());
    }

    public Observable<BaseBean> deleteCollection(String user_id, String user_token, int id) {
        return HttpClient.getApiService().deleteCollection(user_id, user_token, id)
                .compose(this.switchThread());
    }

    public Observable<BaseBean> dislike(String user_id, String user_token, int yo_id) {
        return HttpClient.getApiService().dislike(user_id, user_token, yo_id)
                .compose(this.switchThread());
    }

    public Observable<BaseBean> report(String user_id, String user_token, int yo_id, int comment_id, String content) {
        return HttpClient.getApiService().report(user_id, user_token, yo_id, comment_id, content)
                .compose(this.switchThread());
    }

    public Observable<LabelListBean> getLabelList(String user_id, String user_token) {
        return HttpClient.getApiService().getLabelList(user_id, user_token)
                .compose(this.switchThread());
    }

    public Observable<AddLabelBean> addLabel(String user_id, String user_token, int label_id, int type, String label) {
        return HttpClient.getApiService().addLabel(user_id, user_token, label_id, type, label)
                .compose(this.switchThread());
    }

    public Observable<BaseBean> deleteLabel(String user_id, String user_token, int label_id) {
        return HttpClient.getApiService().deleteLabel(user_id, user_token, label_id)
                .compose(this.switchThread());
    }

    public Observable<BaseBean> publishYoJi(String user_id, String user_token, int yo_id, String logo, String title, String desc, int cost, int open, int valid, List<Integer> topic_ids, List<Integer> channel_ids, String json) {

        Log.e("Gson", json);
        return HttpClient.getApiService().publish_yoji(user_id, user_token, yo_id, logo, title, desc, cost, open, valid, topic_ids, channel_ids, json)
                .compose(this.switchThread());
    }

    public Observable<DraftBean> getDraft(String user_id, String user_token, int page, int page_size) {
        return HttpClient.getApiService().getDraft(user_id, user_token, page, page_size)
                .compose(this.switchThread());
    }

    public Observable<YoJiListBean> getYoJiList(String user_id, String user_token, int page, int page_size) {
        return HttpClient.getApiService().getYoJiList(user_id, user_token, page, page_size)
                .compose(this.switchThread());
    }

    public Observable<YoJiDetailBean> getYoJiDetail(String user_id, String user_token, int yo_id) {
        return HttpClient.getApiService().getYoJiDetail(user_id, user_token, yo_id)
                .compose(this.switchThread());
    }
}
