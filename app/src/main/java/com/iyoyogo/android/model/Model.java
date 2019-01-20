package com.iyoyogo.android.model;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.HisFansBean;
import com.iyoyogo.android.bean.HisPositionBean;
import com.iyoyogo.android.bean.LikeBean;
import com.iyoyogo.android.bean.PublishSucessBean;
import com.iyoyogo.android.bean.SameBean;
import com.iyoyogo.android.bean.VipCenterBean;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.collection.AddCollectionBean;
import com.iyoyogo.android.bean.collection.AddCollectionBean1;
import com.iyoyogo.android.bean.collection.AddressBookBean;
import com.iyoyogo.android.bean.collection.AttentionsBean;
import com.iyoyogo.android.bean.collection.CollectionFolderBean;
import com.iyoyogo.android.bean.collection.CollectionFolderContentBean;
import com.iyoyogo.android.bean.collection.CommendAttentionBean;
import com.iyoyogo.android.bean.collection.MineCollectionBean;
import com.iyoyogo.android.bean.comment.CommentBean;
import com.iyoyogo.android.bean.home.HomeBean;
import com.iyoyogo.android.bean.home.VersionBean;
import com.iyoyogo.android.bean.login.SendMessageBean;
import com.iyoyogo.android.bean.login.interest.InterestBean;
import com.iyoyogo.android.bean.login.login.LoginBean;
import com.iyoyogo.android.bean.login.login.MarketBean;
import com.iyoyogo.android.bean.map.MapBean;
import com.iyoyogo.android.bean.map.MapRenMei;
import com.iyoyogo.android.bean.mine.AboutMeBean;
import com.iyoyogo.android.bean.mine.DraftBean;
import com.iyoyogo.android.bean.mine.GetBindInfoBean;
import com.iyoyogo.android.bean.mine.GetUserInfoBean;
import com.iyoyogo.android.bean.mine.MineMessageBean;
import com.iyoyogo.android.bean.mine.PraiseBean;
import com.iyoyogo.android.bean.mine.center.UserCenterBean;
import com.iyoyogo.android.bean.mine.center.YoJiContentBean;
import com.iyoyogo.android.bean.mine.center.YoXiuContentBean;
import com.iyoyogo.android.bean.mine.message.MessageBean;
import com.iyoyogo.android.bean.mine.message.MessageCenterBean;
import com.iyoyogo.android.bean.mine.message.ReadMessage;
import com.iyoyogo.android.bean.mine.setting.MineSettingBean;
import com.iyoyogo.android.bean.search.ClerBean;
import com.iyoyogo.android.bean.search.GuanZhuBean;
import com.iyoyogo.android.bean.search.KeywordBean;
import com.iyoyogo.android.bean.search.KeywordUserBean;
import com.iyoyogo.android.bean.search.searchInfo;
import com.iyoyogo.android.bean.yoji.detail.YoJiDetailBean;
import com.iyoyogo.android.bean.yoji.label.AddLabelBean;
import com.iyoyogo.android.bean.yoji.label.LabelListBean;
import com.iyoyogo.android.bean.yoji.list.YoJiListBean;
import com.iyoyogo.android.bean.yoji.publish.PublishYoJiBean;
import com.iyoyogo.android.bean.yoxiu.PublishYoXiuBean;
import com.iyoyogo.android.bean.yoxiu.TypeBean;
import com.iyoyogo.android.bean.yoxiu.YoXiuDetailBean;
import com.iyoyogo.android.bean.yoxiu.YouXiuListBean;
import com.iyoyogo.android.bean.yoxiu.channel.ChannelBean;
import com.iyoyogo.android.bean.yoxiu.topic.CreateTopicBean;
import com.iyoyogo.android.bean.yoxiu.topic.HotTopicBean;
import com.iyoyogo.android.model.en.SendMessageRequest;
import com.iyoyogo.android.net.HttpClient;
import com.iyoyogo.android.ui.common.LoginActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.http.Url;


public class Model {
    private volatile static Model model;

    //单例创建
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
    public Observable<InterestBean> getInterestSign(Context activity, String user_id, String user_token) {
        return HttpClient.getApiService().getInterest(user_id, user_token)
                .compose(this.switchThread()).filter(new Predicate<InterestBean>() {
                    @Override
                    public boolean test(InterestBean interestBean) throws Exception {
                         startLogin(activity,interestBean);
                        return true;
                    }
                });
    }

    void startLogin(Context activity,BaseBean bean) {
        if (bean != null && bean.getCode() == 202) {
            Intent intent = new Intent(activity, LoginActivity.class);
            activity.startActivity(intent);
        }
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
    public Observable<BaseBean> addInterest(Context context,ArrayList<Integer> interest_ids, String user_id, String user_token) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", user_id);
        map.put("user_token", user_token);
        for (int i = 0; i < interest_ids.size(); i++) {
            map.put("interest_ids[" + i + "]", interest_ids.get(i));
        }
        return HttpClient.getApiService().addInterest(map)
                .compose(this.switchThread()).filter(new Predicate<BaseBean>() {
                    @Override
                    public boolean test(BaseBean baseBean) throws Exception {
                        if (baseBean != null){
                            int code = baseBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
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
    public Observable<LoginBean> login(Context context,String login_addr, String phone_info, String app_version, int login_type, String phone, String yzm, String openid, String nickname, String logo) {
        return HttpClient.getApiService().login(login_addr, phone_info, app_version, login_type, phone, yzm, openid, nickname, logo)
                .compose(this.switchThread()).filter(new Predicate<LoginBean>() {
                    @Override
                    public boolean test(LoginBean loginBean) throws Exception {
                        return true;
                    }
                });
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
    public Observable<HomeBean> homePager(Context context,String user_id, String user_token, String type, String city) {
        return HttpClient.getApiService().homePager(user_id, user_token, type, city)
                .compose(this.switchThread()).filter(new Predicate<HomeBean>() {
                    @Override
                    public boolean test(HomeBean homeBean) throws Exception {
                        if (homeBean != null){
                            int code = homeBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 获取类型
     *
     * @param user_id
     * @param user_token
     * @return
     */
    public Observable<TypeBean> getType(Context context,String user_id, String user_token) {
        return HttpClient.getApiService().getType(user_id, user_token)
                .compose(this.switchThread()).filter(new Predicate<TypeBean>() {
                    @Override
                    public boolean test(TypeBean typeBean) throws Exception {
                        if (typeBean != null){
                            int code = typeBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 创建点
     *
     * @param user_id
     * @param user_token
     * @param name
     * @param en_name
     * @param areas
     * @param address
     * @param lng
     * @param lat
     * @param type_id
     * @return
     */
    public Observable<BaseBean> create_point(Context context,String user_id,
                                             String user_token,
                                             String name,
                                             String en_name,
                                             String areas,
                                             String address,
                                             String lng,
                                             String lat,
                                             String type_id) {
        return HttpClient.getApiService().create_pointer(user_id, user_token, name, en_name, areas, address, lng, lat, type_id).compose(this.switchThread()).filter(new Predicate<BaseBean>() {
            @Override
            public boolean test(BaseBean baseBean) throws Exception {
                if (baseBean != null){
                    int code = baseBean.getCode();
                    if (code == 202){
                        Intent intent = new Intent(context,LoginActivity.class);
                        context.startActivity(intent);
                    }
                }
                return true;
            }
        });
    }

    /**
     * 获取热门话题
     *
     * @param user_id
     * @param user_token
     * @param search
     * @return
     */
    public Observable<HotTopicBean> getHotTopic(Context context,String user_id,
                                                String user_token,
                                                String search) {
        return HttpClient.getApiService().getHotTopic(user_id, user_token, search)
                .compose(this.switchThread()).filter(new Predicate<HotTopicBean>() {
                    @Override
                    public boolean test(HotTopicBean hotTopicBean) throws Exception {
                        if (hotTopicBean != null){
                            int code = hotTopicBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 获取最近话题
     *
     * @param user_id
     * @param user_token
     * @return
     */
    public Observable<HotTopicBean> getNearTopic(Context context,String user_id,
                                                 String user_token) {
        return HttpClient.getApiService().getNearTopic(user_id, user_token)
                .compose(this.switchThread()).filter(new Predicate<HotTopicBean>() {
                    @Override
                    public boolean test(HotTopicBean hotTopicBean) throws Exception {
                        if (hotTopicBean != null){
                            int code = hotTopicBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 创建话题
     *
     * @param user_id
     * @param user_token
     * @param topic
     * @return
     */
    public Observable<CreateTopicBean> createTopic(Context context,String user_id,
                                                   String user_token, String topic) {
        return HttpClient.getApiService().createTopic(user_id, user_token, topic)
                .compose(this.switchThread()).filter(new Predicate<CreateTopicBean>() {
                    @Override
                    public boolean test(CreateTopicBean createTopicBean) throws Exception {
                        if (createTopicBean != null){
                            int code = createTopicBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /*8
    清空话题
     */
    public Observable<BaseBean> clearTopic(Context context,String user_id,
                                           String user_token) {
        return HttpClient.getApiService().clearTopic(user_id, user_token)
                .compose(this.switchThread()).filter(new Predicate<BaseBean>() {
                    @Override
                    public boolean test(BaseBean baseBean) throws Exception {
                        if (baseBean != null){
                            int code = baseBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 获取推荐话题
     *
     * @param user_id
     * @param user_token
     * @return
     */
    public Observable<HotTopicBean> getRecommend(Context context,String user_id,
                                                 String user_token) {
        return HttpClient.getApiService().getRecommend(user_id, user_token)
                .compose(this.switchThread()).filter(new Predicate<HotTopicBean>() {
                    @Override
                    public boolean test(HotTopicBean hotTopicBean) throws Exception {
                        if (hotTopicBean != null){
                            int code = hotTopicBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 获取频道
     *
     * @param user_id
     * @param user_token
     * @return
     */
    public Observable<ChannelBean> getChannel(Context context,String user_id,
                                              String user_token) {
        return HttpClient.getApiService().getChannel(user_id, user_token)
                .compose(this.switchThread()).filter(new Predicate<ChannelBean>() {
                    @Override
                    public boolean test(ChannelBean channelBean) throws Exception {
                        if (channelBean != null){
                            int code = channelBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 发布yo秀
     *
     * @param user_id
     * @param user_token
     * @param yo_id
     * @param file_path
     * @param file_type
     * @param file_desc
     * @param channel_ids
     * @param
     * @param open
     * @param valid
     * @param position_name
     * @param position_areas
     * @param position_address
     * @param filter_id
     * @return
     */
    public Observable<PublishSucessBean> publish_yoXiu(Context context,String user_id,
                                                       String user_token,
                                                       int yo_id,
                                                       String file_path,
                                                       int file_type,
                                                       String file_desc,
                                                       String channel_ids,
                                                       int open,
                                                       int valid,
                                                       String position_name,
                                                       String position_areas,
                                                       String position_address,
                                                       String position_city,
                                                       String lng,
                                                       String lat,
                                                       String filter_id, String size) {
        return HttpClient.getApiService().publish_yoXiu(user_id, user_token, yo_id, file_path, file_type, file_desc, channel_ids, open, valid, position_name, position_areas, position_address, position_city, lng, lat, filter_id, size)
                .compose(this.switchThread()).filter(new Predicate<PublishSucessBean>() {
                    @Override
                    public boolean test(PublishSucessBean publishSucessBean) throws Exception {
                        if (publishSucessBean != null){
                            int code = publishSucessBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 获取yo秀详情
     *
     * @param user_id
     * @param user_token
     * @param id
     * @return
     */
    public Observable<YoXiuDetailBean> getDetail(Context context,String user_id, String user_token, int id) {
        return HttpClient.getApiService().getDetail(user_id, user_token, id)
                .compose(this.switchThread()).filter(new Predicate<YoXiuDetailBean>() {
                    @Override
                    public boolean test(YoXiuDetailBean yoXiuDetailBean) throws Exception {
                        if (yoXiuDetailBean != null){
                            int code = yoXiuDetailBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 获取yo秀列表
     *
     * @param user_id
     * @param user_token
     * @param page
     * @return
     */
    public Observable<YouXiuListBean> getYoXiuList(Context context,String user_id, String user_token, int page) {
        return HttpClient.getApiService().getYoXiuList(user_id, user_token, page)
                .compose(this.switchThread()).filter(new Predicate<YouXiuListBean>() {
                    @Override
                    public boolean test(YouXiuListBean youXiuListBean) throws Exception {
                        if (youXiuListBean != null){
                            int code = youXiuListBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }
    /**
     * 获取yo秀关注列表
     *
     * @param user_id
     * @param user_token
     * @param page
     * @return
     */
    public Observable<YouXiuListBean> getYoXiuAttentionList(Context context,String user_id, String user_token, int page) {
        return HttpClient.getApiService().getYoXiuAttentionList(user_id, user_token, page)
                .compose(this.switchThread()).filter(new Predicate<YouXiuListBean>() {
                    @Override
                    public boolean test(YouXiuListBean youXiuListBean) throws Exception {
                        if (youXiuListBean != null){
                            int code = youXiuListBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 获取同款
     */
    public Observable<SameBean> getSameList(Context context,String user_id, String user_token, String lng, String lat, int page, String page_size) {
        return HttpClient.getApiService().getSameList(user_id, user_token, lng, lat, page, page_size)
                .compose(this.switchThread()).filter(new Predicate<SameBean>() {
                    @Override
                    public boolean test(SameBean sameBean) throws Exception {
                        if (sameBean != null){
                            int code = sameBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /*8
   点赞
     */
    public Observable<LikeBean> praise(Context context,String user_id, String user_token, int yo_id, int comment_id) {
        return HttpClient.getApiService().praise(user_id, user_token, yo_id, comment_id)
                .compose(this.switchThread()).filter(new Predicate<LikeBean>() {
                    @Override
                    public boolean test(LikeBean likeBean) throws Exception {
                        if (likeBean != null){
                            int code = likeBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 获取个人信息
     *
     * @param user_id
     * @param user_token
     * @return
     */
    public Observable<MineMessageBean> getPersonInfo(Context context,String user_id, String user_token) {
        return HttpClient.getApiService().getPersonInfo(user_id, user_token)
                .compose(this.switchThread()).filter(new Predicate<MineMessageBean>() {
                    @Override
                    public boolean test(MineMessageBean mineMessageBean) throws Exception {
                        if (mineMessageBean != null){
                            int code = mineMessageBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 获取评论列表
     *
     * @param user_id
     * @param user_token
     * @param page
     * @param yo_id
     * @param comment_id
     * @return
     */
    public Observable<CommentBean> getComment(Context context,String user_id, String user_token, int page, int yo_id, int comment_id) {
        return HttpClient.getApiService().getComment(user_id, user_token, page, yo_id, comment_id)
                .compose(this.switchThread()).filter(new Predicate<CommentBean>() {
                    @Override
                    public boolean test(CommentBean commentBean) throws Exception {
                        if (commentBean != null){
                            int code = commentBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                })
                ;
    }

    /*8
    添加评论
     */
    public Observable<BaseBean> addComment(Context context,String user_id, String user_token, int comment_id, int yo_id, String content) {
        return HttpClient.getApiService().addComment(user_id, user_token, comment_id, yo_id, content)
                .compose(this.switchThread()).filter(new Predicate<BaseBean>() {
                    @Override
                    public boolean test(BaseBean baseBean) throws Exception {
                        if (baseBean != null){
                            int code = baseBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 获取用户信息
     *
     * @param user_id
     * @param user_token
     * @return
     */
    public Observable<GetUserInfoBean> getUserInfo(Context context,String user_id, String user_token) {
        return HttpClient.getApiService().getUserInfo(user_id, user_token)
                .compose(this.switchThread()).filter(new Predicate<GetUserInfoBean>() {
                    @Override
                    public boolean test(GetUserInfoBean getUserInfoBean) throws Exception {
                        if (getUserInfoBean != null){
                            int code = getUserInfoBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 修改用户信息
     *
     * @param user_id
     * @param user_token
     * @param user_nickname
     * @param user_logo
     * @param user_sex
     * @param user_birthday
     * @param user_city
     * @return
     */
    public Observable<BaseBean> setUserInfo(Context context,String user_id,
                                            String user_token,
                                            String user_nickname,
                                            String user_logo,
                                            String user_sex,
                                            String user_birthday,
                                            String user_city) {
        return HttpClient.getApiService().setUserInfo(user_id, user_token, user_nickname, user_logo, user_sex, user_birthday, user_city)
                .compose(this.switchThread()).filter(new Predicate<BaseBean>() {
                    @Override
                    public boolean test(BaseBean baseBean) throws Exception {
                        if (baseBean != null){
                            int code = baseBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });

    }

    /**
     * 获取绑定信息
     *
     * @param user_id
     * @param user_token
     * @return
     */
    public Observable<GetBindInfoBean> getBindInfo(Context context,String user_id, String user_token) {
        return HttpClient.getApiService().getBindInfo(user_id, user_token)
                .compose(this.switchThread()).filter(new Predicate<GetBindInfoBean>() {
                    @Override
                    public boolean test(GetBindInfoBean getBindInfoBean) throws Exception {
                        if (getBindInfoBean != null){
                            int code = getBindInfoBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 替换手机号
     *
     * @param user_id
     * @param user_token
     * @param phone
     * @param yzm
     * @return
     */
    public Observable<BaseBean> replacePhone(Context context,String user_id, String user_token, String phone, String yzm) {
        return HttpClient.getApiService().replacePhone(user_id, user_token, phone, yzm)
                .compose(this.switchThread()).filter(new Predicate<BaseBean>() {
                    @Override
                    public boolean test(BaseBean baseBean) throws Exception {
                        if (baseBean != null){
                            int code = baseBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 退出登录
     *
     * @param user_id
     * @param user_token
     * @param addr
     * @param phone_type
     * @param app_version
     * @return
     */
    public Observable<BaseBean> logout(Context context,String user_id, String user_token, String addr, String phone_type, String app_version) {
        return HttpClient.getApiService().logout(user_id, user_token, addr, phone_type, app_version)
                .compose(this.switchThread()).filter(new Predicate<BaseBean>() {
                    @Override
                    public boolean test(BaseBean baseBean) throws Exception {
                        if (baseBean != null){
                            int code = baseBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 添加关注
     *
     * @param user_id
     * @param user_token
     * @param target_id
     * @return
     */
    public Observable<AttentionBean> addAttention(Context context,String user_id, String user_token, int target_id) {
        return HttpClient.getApiService().addAttention(user_id, user_token, target_id)
                .compose(this.switchThread()).filter(new Predicate<AttentionBean>() {
                    @Override
                    public boolean test(AttentionBean attentionBean) throws Exception {
                        if (attentionBean != null){
                            int code = attentionBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    public Observable<AttentionBean> addAttention1(Context context,String user_id, String user_token, String target_id) {
        return HttpClient.getApiService().addAttention1(user_id, user_token, target_id)
                .compose(this.switchThread()).filter(new Predicate<AttentionBean>() {
                    @Override
                    public boolean test(AttentionBean attentionBean) throws Exception {
                        if (attentionBean != null){
                            int code = attentionBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    public Observable<BaseBean> deleteAttention(Context context,String user_id, String user_token, int id) {
        return HttpClient.getApiService().deleteAttention(user_id, user_token, id)
                .compose(this.switchThread()).filter(new Predicate<BaseBean>() {
                    @Override
                    public boolean test(BaseBean baseBean) throws Exception {
                        if (baseBean != null){
                            int code = baseBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 获取收藏夹列表
     *
     * @param user_id
     * @param user_token
     * @return
     */
    public Observable<CollectionFolderBean> getCollectionFolder(Context context,String user_id, String user_token) {
        return HttpClient.getApiService().getCollectionFolder(user_id, user_token)
                .compose(this.switchThread()).filter(new Predicate<CollectionFolderBean>() {
                    @Override
                    public boolean test(CollectionFolderBean collectionFolderBean) throws Exception {
                        if (collectionFolderBean != null){
                            int code = collectionFolderBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 创建收藏夹
     *
     * @param user_id
     * @param user_token
     * @param name
     * @param open
     * @param id
     * @return
     */
    public Observable<BaseBean> create_folder(Context context,String user_id, String user_token, String name, int open, String id) {
        return HttpClient.getApiService().createFolder(user_id, user_token, name, open, id)
                .compose(this.switchThread()).filter(new Predicate<BaseBean>() {
                    @Override
                    public boolean test(BaseBean baseBean) throws Exception {
                        if (baseBean != null){
                            int code = baseBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });

    }

    /**
     * 添加收藏
     *
     * @param user_id
     * @param user_token
     * @param folder_id
     * @param yo_id
     * @return
     */
    public Observable<AddCollectionBean> addCollection(Context context,String user_id, String user_token, int folder_id, int yo_id) {
        return HttpClient.getApiService().addCollection(user_id, user_token, folder_id, yo_id)
                .compose(this.switchThread()).filter(new Predicate<AddCollectionBean>() {
                    @Override
                    public boolean test(AddCollectionBean addCollectionBean) throws Exception {
                        if (addCollectionBean != null){
                            int code = addCollectionBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 取消收藏
     */
    public Observable<BaseBean> delCollection(String user_id, String user_token, int yo_id) {
        return HttpClient.getApiService().delCollection(user_id, user_token, yo_id)
                .compose(this.switchThread());
    }

    /**
     * 取消收藏
     *
     * @param user_id
     * @param user_token
     * @param id
     * @return
     */
    public Observable<BaseBean> deleteCollection(String user_id, String user_token, int id) {
        return HttpClient.getApiService().delCollection(user_id, user_token, id)
                .compose(this.switchThread());
    }

    /**
     * 不喜欢
     *
     * @param user_id
     * @param user_token
     * @param yo_id
     * @return
     */
    public Observable<BaseBean> dislike(Context context,String user_id, String user_token, int yo_id) {
        return HttpClient.getApiService().dislike(context,user_id, user_token, yo_id)
                .compose(this.switchThread()).filter(new Predicate<BaseBean>() {
                    @Override
                    public boolean test(BaseBean baseBean) throws Exception {
                        if (baseBean != null){
                            int code = baseBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 举报
     *
     * @param user_id
     * @param user_token
     * @param yo_id
     * @param comment_id
     * @param content
     * @return
     */
    public Observable<BaseBean> report(Context context,String user_id, String user_token, int yo_id, int comment_id, String content) {
        return HttpClient.getApiService().report(context,user_id, user_token, yo_id, comment_id, content)
                .compose(this.switchThread()).filter(new Predicate<BaseBean>() {
                    @Override
                    public boolean test(BaseBean baseBean) throws Exception {
                        if (baseBean != null){
                            int code = baseBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 获取标签列表
     *
     * @param user_id
     * @param user_token
     * @return
     */
    public Observable<LabelListBean> getLabelList(Context context,String user_id, String user_token) {
        return HttpClient.getApiService().getLabelList(user_id, user_token)
                .compose(this.switchThread()).filter(new Predicate<LabelListBean>() {
                    @Override
                    public boolean test(LabelListBean labelListBean) throws Exception {
                        if (labelListBean != null){
                            int code = labelListBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 添加标签
     *
     * @param user_id
     * @param user_token
     * @param label_id
     * @param type
     * @param label
     * @return
     */
    public Observable<AddLabelBean> addLabel(Context context,String user_id, String user_token, int label_id, int type, String label) {
        return HttpClient.getApiService().addLabel(user_id, user_token, label_id, type, label)
                .compose(this.switchThread()).filter(new Predicate<AddLabelBean>() {
                    @Override
                    public boolean test(AddLabelBean addLabelBean) throws Exception {
                        if (addLabelBean != null){
                            int code = addLabelBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 删除标签
     *
     * @param user_id
     * @param user_token
     * @param label_id
     * @return
     */
    public Observable<BaseBean> deleteLabel(Context context,String user_id, String user_token, int label_id) {
        return HttpClient.getApiService().deleteLabel(user_id, user_token, label_id)
                .compose(this.switchThread()).filter(new Predicate<BaseBean>() {
                    @Override
                    public boolean test(BaseBean baseBean) throws Exception {
                        if (baseBean != null){
                            int code = baseBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 发布yo记
     *
     * @param user_id
     * @param user_token
     * @param yo_id
     * @param logo
     * @param title
     * @param desc
     * @param cost
     * @param open
     * @param valid
     * @param
     * @param channel_ids
     * @param json
     * @return
     */
    public Observable<PublishSucessBean> publishYoJi(Context context,String user_id, String user_token, int yo_id, String logo, String title, String desc, int cost, int open, int valid, String channel_ids, String json) {

        Log.e("Gson", json);
        return HttpClient.getApiService().publish_yoji(user_id, user_token, yo_id, logo, title, desc, cost, open, valid, channel_ids, json)
                .compose(this.switchThread()).filter(new Predicate<PublishSucessBean>() {
                    @Override
                    public boolean test(PublishSucessBean publishSucessBean) throws Exception {
                        if (publishSucessBean != null){
                            int code = publishSucessBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 获取草稿
     *
     * @param user_id
     * @param user_token
     * @param page
     * @param page_size
     * @return
     */
    public Observable<DraftBean> getDraft(Context context,String user_id, String user_token, int page, int page_size) {
        return HttpClient.getApiService().getDraft(user_id, user_token, page, page_size)
                .compose(this.switchThread()).filter(new Predicate<DraftBean>() {
                    @Override
                    public boolean test(DraftBean draftBean) throws Exception {
                        if (draftBean != null){
                            int code = draftBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 获取yo记列表
     *
     * @param user_id
     * @param user_token
     * @param page
     * @param page_size
     * @return
     */
    public Observable<YoJiListBean> getYoJiList(Context context,String user_id, String user_token, int page, int page_size) {
        return HttpClient.getApiService().getYoJiList(user_id, user_token, page, page_size)
                .compose(this.switchThread()).filter(new Predicate<YoJiListBean>() {
                    @Override
                    public boolean test(YoJiListBean yoJiListBean) throws Exception {
                        if (yoJiListBean != null){
                            int code = yoJiListBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 获取yo记关注列表
     *
     * @param user_id
     * @param user_token
     * @param page
     * @param page_size
     * @return
     */
    public Observable<YoJiListBean> getYoJiAttentionList(Context context,String user_id, String user_token, int page, int page_size) {
        return HttpClient.getApiService().getJiAttentionList(user_id, user_token, page, page_size)
                .compose(this.switchThread()).filter(new Predicate<YoJiListBean>() {
                    @Override
                    public boolean test(YoJiListBean yoJiListBean) throws Exception {
                        if (yoJiListBean != null){
                            int code = yoJiListBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 获取yo记详情
     *
     * @param user_id
     * @param user_token
     * @param yo_id
     * @return
     */
    public Observable<YoJiDetailBean> getYoJiDetail(Context context,String user_id, String user_token, int yo_id) {
        return HttpClient.getApiService().getYoJiDetail(user_id, user_token, yo_id)
                .compose(this.switchThread()).filter(new Predicate<YoJiDetailBean>() {
                    @Override
                    public boolean test(YoJiDetailBean yoJiDetailBean) throws Exception {
                        if (yoJiDetailBean != null){
                            int code = yoJiDetailBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 获取我的收藏
     *
     * @param user_id
     * @param user_token
     * @return
     */
    public Observable<MineCollectionBean> getMineCollection(Context context,String user_id, String user_token) {
        return HttpClient.getApiService().getMineCollection(user_id, user_token)
                .compose(this.switchThread()).filter(new Predicate<MineCollectionBean>() {
                    @Override
                    public boolean test(MineCollectionBean mineCollectionBean) throws Exception {
                        if (mineCollectionBean != null){
                            int code = mineCollectionBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 获取TA的收藏
     *
     * @param user_id
     * @param user_token
     * @return
     */
    public Observable<MineCollectionBean> getHisCollection(Context context,String user_id, String user_token, String his_id) {
        return HttpClient.getApiService().getHisCollection(user_id, user_token, his_id)
                .compose(this.switchThread()).filter(new Predicate<MineCollectionBean>() {
                    @Override
                    public boolean test(MineCollectionBean mineCollectionBean) throws Exception {
                        if (mineCollectionBean != null){
                            int code = mineCollectionBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 删除收藏夹
     *
     * @param user_id
     * @param user_token
     * @param folder_ids
     * @return
     */
    public Observable<BaseBean> deleteCollectionFolder(Context context,String user_id, String user_token, int[] folder_ids) {
        return HttpClient.getApiService().deleteCollectionFolder(user_id, user_token, folder_ids)
                .compose(this.switchThread()).filter(new Predicate<BaseBean>() {
                    @Override
                    public boolean test(BaseBean baseBean) throws Exception {
                        if (baseBean != null){
                            int code = baseBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 获取用户主页
     *
     * @param user_id
     * @param user_token
     * @param his_id
     * @return
     */
    public Observable<UserCenterBean> getUserCenter(Context context,String user_id, String user_token, String his_id) {
        return HttpClient.getApiService().getUserCenter(user_id, user_token, his_id)
                .compose(this.switchThread()).filter(new Predicate<UserCenterBean>() {
                    @Override
                    public boolean test(UserCenterBean userCenterBean) throws Exception {
                        if (userCenterBean != null){
                            int code = userCenterBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 获取yo记内容
     *
     * @param user_id
     * @param user_token
     * @param his_id
     * @param page
     * @param page_size
     * @return
     */
    public Observable<YoJiContentBean> getYoJiContent(Context context,String user_id, String user_token, String his_id, String page, String page_size) {
        return HttpClient.getApiService().getYoJiContent(user_id, user_token, his_id, page, page_size)
                .compose(this.switchThread()).filter(new Predicate<YoJiContentBean>() {
                    @Override
                    public boolean test(YoJiContentBean yoJiContentBean) throws Exception {
                        if (yoJiContentBean != null){
                            int code = yoJiContentBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 获取我的喜欢
     *
     * @param user_id
     * @param user_token
     * @param page
     * @param page_size
     * @return
     */
    public Observable<PraiseBean> getPraise(Context context,String user_id, String user_token, int page, int page_size) {
        return HttpClient.getApiService().getPraise(user_id, user_token, page, page_size)
                .compose(this.switchThread()).filter(new Predicate<PraiseBean>() {
                    @Override
                    public boolean test(PraiseBean praiseBean) throws Exception {
                        if (praiseBean != null){
                            int code = praiseBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 获取收藏夹内容
     *
     * @param user_id
     * @param user_token
     * @param page
     * @param page_size
     * @return
     */
    public Observable<CollectionFolderContentBean> getContent(Context context,String user_id, String user_token, int page, int page_size) {
        return HttpClient.getApiService().getContent(user_id, user_token, page, page_size)
                .compose(this.switchThread()).filter(new Predicate<CollectionFolderContentBean>() {
                    @Override
                    public boolean test(CollectionFolderContentBean collectionFolderContentBean) throws Exception {
                        if (collectionFolderContentBean != null){
                            int code = collectionFolderContentBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 取消收藏
     *
     * @param user_id
     * @param user_token
     * @param record_ids
     * @return
     */
    public Observable<BaseBean> deleteCollection(Context context,String user_id, String user_token, Integer[] record_ids) {
        return HttpClient.getApiService().deleteCollection(user_id, user_token, record_ids)
                .compose(this.switchThread()).filter(new Predicate<BaseBean>() {
                    @Override
                    public boolean test(BaseBean baseBean) throws Exception {
                        if (baseBean != null){
                            int code = baseBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 移动收藏夹内容
     *
     * @param user_id
     * @param user_token
     * @param record_ids
     * @param folder_id
     * @return
     */
    public Observable<BaseBean> moveCollectionFolder(Context context,String user_id, String user_token, Integer[] record_ids, int folder_id) {
        return HttpClient.getApiService().moveCollectionFolder(user_id, user_token, record_ids, folder_id)
                .compose(this.switchThread()).filter(new Predicate<BaseBean>() {
                    @Override
                    public boolean test(BaseBean baseBean) throws Exception {
                        if (baseBean != null){
                            int code = baseBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 获取我的设置
     *
     * @param user_id
     * @param user_token
     * @return
     */
    public Observable<MineSettingBean> getMineSetting(Context context,String user_id, String user_token) {
        return HttpClient.getApiService().getMineSetting(user_id, user_token)
                .compose(this.switchThread()).filter(new Predicate<MineSettingBean>() {
                    @Override
                    public boolean test(MineSettingBean mineSettingBean) throws Exception {
                        if (mineSettingBean != null){
                            int code = mineSettingBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 修改我的设置
     *
     * @param user_id
     * @param user_token
     * @param wifi_auto_play_video
     * @param notice
     * @param address_list
     * @return
     */
    public Observable<BaseBean> setMineSetting(Context context,String user_id, String user_token, int wifi_auto_play_video, int notice, int address_list) {
        return HttpClient.getApiService().setMineSetting(user_id, user_token, wifi_auto_play_video, notice, address_list)
                .compose(this.switchThread()).filter(new Predicate<BaseBean>() {
                    @Override
                    public boolean test(BaseBean baseBean) throws Exception {
                        if (baseBean != null){
                            int code = baseBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 用户反馈
     *
     * @param user_id
     * @param user_token
     * @param desc
     * @return
     */
    public Observable<BaseBean> feedBack(Context context,String user_id, String user_token, String desc) {
        return HttpClient.getApiService().addFeedBack(user_id, user_token, desc)
                .compose(this.switchThread()).filter(new Predicate<BaseBean>() {
                    @Override
                    public boolean test(BaseBean baseBean) throws Exception {
                        if (baseBean != null){
                            int code = baseBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 关于我们
     *
     * @return
     */
    public Observable<AboutMeBean> aboutme() {
        return HttpClient.getApiService().aboutMe()
                .compose(this.switchThread());
    }

    /**
     * 分享
     *
     * @return
     */
    public Observable<BaseBean> share(Context context,String user_id, String user_token, String id) {
        return HttpClient.getApiService().share(user_id, user_token, id)
                .compose(this.switchThread()).filter(new Predicate<BaseBean>() {
                    @Override
                    public boolean test(BaseBean baseBean) throws Exception {
                        if (baseBean != null){
                            int code = baseBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 获取消息
     *
     * @param user_id
     * @param user_token
     * @param type
     * @param page
     * @return
     */
    public Observable<MessageBean> getMessage(Context context,String user_id, String user_token, int type, int page) {
        return HttpClient.getApiService().getMessage(user_id, user_token, type, page)
                .compose(this.switchThread()).filter(new Predicate<MessageBean>() {
                    @Override
                    public boolean test(MessageBean messageBean) throws Exception {
                        if (messageBean != null){
                            int code = messageBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 获取消息中心
     *
     * @param user_id
     * @param user_token
     * @return
     */

    public Observable<MessageCenterBean> messageCenter(Context context,String user_id, String user_token) {
        return HttpClient.getApiService().getMessageCenter(user_id, user_token)
                .compose(this.switchThread()).filter(new Predicate<MessageCenterBean>() {
                    @Override
                    public boolean test(MessageCenterBean messageCenterBean) throws Exception {
                        if (messageCenterBean != null){
                            int code = messageCenterBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 读取消息
     *
     * @param user_id
     * @param user_token
     * @param message_id
     * @return
     */
    public Observable<ReadMessage> readMessage(Context context,String user_id, String user_token, String message_id) {
        return HttpClient.getApiService().readMessage(user_id, user_token, message_id)
                .compose(this.switchThread()).filter(new Predicate<ReadMessage>() {
                    @Override
                    public boolean test(ReadMessage readMessage) throws Exception {
                        if (readMessage != null){
                            int code = readMessage.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    //获取我粉丝中 我没关注的 (是我的粉丝，我却不是他的粉丝)
    public Observable<AddCollectionBean1> setAddCollection(Context context,String user_id, String user_token, String page, String page_size) {
        return HttpClient.getApiService().setAddCollection(user_id, user_token, page, page_size)
                .compose(this.switchThread()).filter(new Predicate<AddCollectionBean1>() {
                    @Override
                    public boolean test(AddCollectionBean1 addCollectionBean1) throws Exception {
                        if (addCollectionBean1 != null){
                            int code = addCollectionBean1.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    //获取通讯录中 关注情况
    public Observable<AddressBookBean> setAddressBook(Context context,String user_id, String user_token, String search, String list) {
        return HttpClient.getApiService().setAddressBook(user_id, user_token, search, list)
                .compose(this.switchThread()).filter(new Predicate<AddressBookBean>() {
                    @Override
                    public boolean test(AddressBookBean addressBookBean) throws Exception {
                        if (addressBookBean != null){
                            int code = addressBookBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * push
     *
     * @param user_id
     * @param user_token
     * @param device
     * @param jpush_rid
     * @return
     */
    public Observable<BaseBean> push(Context context,String user_id, String user_token, String device, String jpush_rid) {
        return HttpClient.getApiService().push(user_id, user_token, device, jpush_rid)
                .compose(this.switchThread()).filter(new Predicate<BaseBean>() {
                    @Override
                    public boolean test(BaseBean baseBean) throws Exception {
                        if (baseBean != null){
                            int code = baseBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    //推荐 给我 要关注的人
    public Observable<CommendAttentionBean> setCommendAttention(Context context,String user_id, String user_token) {
        return HttpClient.getApiService().setCommendAttention(user_id, user_token)
                .compose(this.switchThread()).filter(new Predicate<CommendAttentionBean>() {
                    @Override
                    public boolean test(CommendAttentionBean commendAttentionBean) throws Exception {
                        if (commendAttentionBean != null){
                            int code = commendAttentionBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    //获取 用户 关注的人群
    public Observable<AttentionsBean> setAttentions(Context context,String user_id, String user_token, String his_id, String page, String page_size) {
        return HttpClient.getApiService().setAttentions(user_id, user_token, his_id, page, page_size)
                .compose(this.switchThread()).filter(new Predicate<AttentionsBean>() {
                    @Override
                    public boolean test(AttentionsBean attentionsBean) throws Exception {
                        if (attentionsBean != null){
                            int code = attentionsBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    //获取 用户 粉丝人群
    public Observable<HisFansBean> setHisFans(Context context,String user_id, String user_token, String his_id, String page, String page_size) {
        return HttpClient.getApiService().setHisFans(user_id, user_token, his_id, page, page_size)
                .compose(this.switchThread()).filter(new Predicate<HisFansBean>() {
                    @Override
                    public boolean test(HisFansBean hisFansBean) throws Exception {
                        if (hisFansBean != null){
                            int code = hisFansBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 打卡
     *
     * @param user_id
     * @param user_token
     * @return
     */
    public Observable<BaseBean> punchClock(Context context,String user_id, String user_token) {
        return HttpClient.getApiService().punchClock(user_id, user_token)
                .compose(this.switchThread()).filter(new Predicate<BaseBean>() {
                    @Override
                    public boolean test(BaseBean baseBean) throws Exception {
                        if (baseBean != null){
                            int code = baseBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 版本升级
     *
     * @param user_id
     * @param user_token
     * @param type
     * @return
     */
    public Observable<VersionBean> getVersionMessage(String user_id, String user_token, String type) {
        return HttpClient.getApiService().getVersionMessage(user_id, user_token, type)
                .compose(this.switchThread());
    }

    /**
     * 不喜欢
     *
     * @param user_id
     * @param user_token
     * @param yo_id
     * @param type
     * @return
     */
    public Observable<BaseBean> dislike(Context context,String user_id, String user_token, int yo_id, int type) {
        return HttpClient.getApiService().dislike(user_id, user_token, yo_id, type)
                .compose(this.switchThread()).filter(new Predicate<BaseBean>() {
                    @Override
                    public boolean test(BaseBean baseBean) throws Exception {
                        if (baseBean != null){
                            int code = baseBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    //用户等级页
    public Observable<VipCenterBean> setVipCenter(Context context,String user_id, String user_token) {
        return HttpClient.getApiService().setVipCenter(user_id, user_token)
                .compose(this.switchThread()).filter(new Predicate<VipCenterBean>() {
                    @Override
                    public boolean test(VipCenterBean vipCenterBean) throws Exception {
                        if (vipCenterBean != null){
                            int code = vipCenterBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 获取yo秀内容
     *
     * @param user_id
     * @param user_token
     * @param his_id
     * @param page
     * @param page_size
     * @return
     */
    public Observable<YoXiuContentBean> getYoXiuContent(Context context,String user_id, String user_token, String his_id, String page, String page_size) {
        return HttpClient.getApiService().getYoXiuContent(user_id, user_token, his_id, page, page_size)
                .compose(this.switchThread()).filter(new Predicate<YoXiuContentBean>() {
                    @Override
                    public boolean test(YoXiuContentBean yoXiuContentBean) throws Exception {
                        if (yoXiuContentBean != null){
                            int code = yoXiuContentBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 删除yo秀
     *
     * @param user_id
     * @param user_token
     * @param yo_id
     * @return
     */
    public Observable<BaseBean> deleteYo(Context context,String user_id, String user_token, int yo_id) {
        return HttpClient.getApiService().deleteYo(user_id, user_token, yo_id)
                .compose(this.switchThread()).filter(new Predicate<BaseBean>() {
                    @Override
                    public boolean test(BaseBean baseBean) throws Exception {
                        if (baseBean != null){
                            int code = baseBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    public Observable<BaseBean> deleteComment(Context context,String user_id, String user_token, int comment_id) {
        return HttpClient.getApiService().deleteComment(user_id, user_token, comment_id)
                .compose(this.switchThread()).filter(new Predicate<BaseBean>() {
                    @Override
                    public boolean test(BaseBean baseBean) throws Exception {
                        if (baseBean != null){
                            int code = baseBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    public Observable<PublishYoJiBean> getYoJiData(Context context,String user_id, String user_token, String yo_id) {
        return HttpClient.getApiService().getYoJiData(user_id, user_token, yo_id)
                .compose(this.switchThread()).filter(new Predicate<PublishYoJiBean>() {
                    @Override
                    public boolean test(PublishYoJiBean publishYoJiBean) throws Exception {
                        if (publishYoJiBean != null){
                            int code = publishYoJiBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    public Observable<PublishYoXiuBean> getYoXiuData(Context context,String user_id, String user_token, String yo_id) {
        return HttpClient.getApiService().getYoXiuData(user_id, user_token, yo_id)
                .compose(this.switchThread()).filter(new Predicate<PublishYoXiuBean>() {
                    @Override
                    public boolean test(PublishYoXiuBean publishYoXiuBean) throws Exception {
                        if (publishYoXiuBean != null){
                            int code = publishYoXiuBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 获取用户历史自定义位置
     *
     * @param user_id
     * @param user_token
     * @param
     * @return
     */
    public Observable<HisPositionBean> setHisPosition(Context context,String user_id, String user_token, int page, int page_size) {
        return HttpClient.getApiService().setHisPosition(user_id, user_token, page, page_size)
                .compose(this.switchThread()).filter(new Predicate<HisPositionBean>() {
                    @Override
                    public boolean test(HisPositionBean hisPositionBean) throws Exception {
                        if (hisPositionBean != null){
                            int code = hisPositionBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 清空用户历史自定义位置
     *
     * @param user_id
     * @param user_token
     * @param
     * @return
     */
    public Observable<BaseBean> DelPosition(Context context,String user_id, String user_token) {
        return HttpClient.getApiService().DelPosition(user_id, user_token)
                .compose(this.switchThread()).filter(new Predicate<BaseBean>() {
                    @Override
                    public boolean test(BaseBean baseBean) throws Exception {
                        if (baseBean != null){
                            int code = baseBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    public Observable<searchInfo> search(Context context,String user_id, String user_token) {
        return HttpClient.getApiService()
                .search(user_id, user_token)
                .compose(this.switchThread()).filter(new Predicate<searchInfo>() {
                    @Override
                    public boolean test(searchInfo searchInfo) throws Exception {
                        if (searchInfo != null){
                            int code = searchInfo.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    public Observable<KeywordBean> keyword(Context context,String user_id, String user_token, String search, String type,String key_type) {
        return HttpClient.getApiService()
                .keyword(user_id, user_token, search, type,key_type)
                .compose(this.switchThread()).filter(new Predicate<KeywordBean>() {
                    @Override
                    public boolean test(KeywordBean keywordBean) throws Exception {
                        if (keywordBean != null){
                            int code = keywordBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    public Observable<GuanZhuBean> guanzhu(String user_id, String user_token, String target) {
        return HttpClient.getApiService()
                .keyword(user_id, user_token, target)
                .compose(this.switchThread());
    }

    /*    public Observable<MapBean> mapDiTu(String user_id, String user_token, String type, String search){
            return HttpClient.getApiService()
                    .getChengShi(user_id, user_token,type,search)
                    .compose(this.switchThread());
        }*/
    public Observable<MapBean> mapDiTu(Context context,String user_id, String user_token, String type, String seatch) {
        return HttpClient.getApiService()
                .getChengShi(user_id, user_token, type, seatch)
                .compose(this.switchThread()).filter(new Predicate<MapBean>() {
                    @Override
                    public boolean test(MapBean mapBean) throws Exception {
                        if (mapBean != null){
                            int code = mapBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    //个人信息 城市的选择
    public Observable<MapBean> chengShi(Context context,String user_id, String user_token, String type, String seatch) {
        return HttpClient.getApiService()
                .getChengShiLieBiao(user_id, user_token, type, seatch)
                .compose(this.switchThread()).filter(new Predicate<MapBean>() {
                    @Override
                    public boolean test(MapBean mapBean) throws Exception {
                        if (mapBean != null){
                            int code = mapBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    //首页地图定位
    public Observable<MapBean> CPS(String user_id, String user_token, String type, String seatch) {
        return HttpClient.getApiService()
                .getGps(user_id, user_token, type, seatch)
                .compose(this.switchThread());
    }
    //首页热门城市
    public Observable<MapRenMei> remei(String user_id, String user_token) {
        return HttpClient.getApiService()
                .getRenMei(user_id, user_token)
                .compose(this.switchThread());
    }

    //首页搜索  关键字  搜索
    public Observable<KeywordUserBean> srarch(Context context,String user_id, String user_token, String seatch) {
        return HttpClient.getApiService()
                .getserarch(user_id, user_token, seatch)
                .compose(this.switchThread()).filter(new Predicate<KeywordUserBean>() {
                    @Override
                    public boolean test(KeywordUserBean keywordUserBean) throws Exception {
                        startLogin(context,keywordUserBean);
                        return true;
                    }
                });
    }
    //首页搜索晴空历史
    public Observable<ClerBean> searchCler(Context context,String user_id, String user_token) {
        return HttpClient.getApiService()
                .searchCler(user_id, user_token)
                .compose(this.switchThread());
    }

    public Observable<BaseBean> browse(Context context,String user_id, String user_token, String yo_id) {
        return HttpClient.getApiService().browse(user_id, user_token, yo_id)
                .compose(this.switchThread()).filter(new Predicate<BaseBean>() {
                    @Override
                    public boolean test(BaseBean baseBean) throws Exception {
                        if (baseBean != null){
                            int code = baseBean.getCode();
                            if (code == 202){
                                Intent intent = new Intent(context,LoginActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
    }

    public Observable<BaseBean> setLocation(Context context,String user_id, String user_token, String lng, String lat) {
        return HttpClient.getApiService().setLocation(user_id, user_token, lng, lat)
                .compose(this.switchThread());

    }

    public Observable<YouXiuListBean> getYoXiuPosition(Context context,String user_id, String user_token, String position, int type, int page, String page_size) {
        return HttpClient.getApiService().getYoXiuPosition(user_id, user_token, position, type, page, page_size)
                .compose(this.switchThread());
    }

    public Observable<YoJiListBean> getYoJiPosition(Context context,String user_id, String user_token, String position, int type, int page, String page_size) {
        return HttpClient.getApiService().getYoJiPosition(user_id, user_token, position, type, page, page_size)
                .compose(this.switchThread());
    }

    public Observable<YoJiListBean> getYoJiLabel(Context context,String user_id, String user_token, String label, int page, String page_size) {
        return HttpClient.getApiService().getYoJiLabel(user_id, user_token, label, page, page_size)
                .compose(this.switchThread());
    }

    /**
     *
     */
    public Observable<BaseBean> update_bind(Context context,String user_id, String user_token, int type, String openid, String nickname, String logo) {
        return HttpClient.getApiService().update_bind(user_id, user_token, type, openid, nickname, logo)
                .compose(this.switchThread());
    }

    public Observable<ResponseBody> downFile(@Url String fileUrl) {
        return HttpClient.getApiService().downFile(fileUrl).compose(this.switchThread());
    }
    public Observable<SameBean.DataBean.ListBean> goCameraDetail(Context context,String user_id,String user_token,int yo_id){
        return HttpClient.getApiService().goCameraDetail(user_id, user_token, yo_id)
                .compose(this.switchThread());
    }

    public Observable<ResponseBody> uploadErrorLog(@Url String content) {
        return HttpClient.getApiService().updateErrorLog(content).compose(this.switchThread());
    }

    /***
     * 用户解绑第三方绑定设备
     */
    public Observable<BaseBean> getUserBind(String user_id, String user_token, int type, String openid) {
        return HttpClient.getApiService().getUserBind(user_id,user_token,type,openid).compose(this.switchThread());
    }
}
