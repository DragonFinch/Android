package com.iyoyogo.android.net;


import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.home.HomeViewPagerBean;
import com.iyoyogo.android.bean.login.SendMessageBean;
import com.iyoyogo.android.bean.login.interest.InterestBean;
import com.iyoyogo.android.bean.login.login.LoginBean;
import com.iyoyogo.android.bean.login.login.MarketBean;
import com.iyoyogo.android.bean.yoxiu.TypeBean;
import com.iyoyogo.android.bean.yoxiu.channel.ChannelBean;
import com.iyoyogo.android.bean.yoxiu.topic.CreateTopicBean;
import com.iyoyogo.android.bean.yoxiu.topic.HotTopicBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Stephen on 2018/9/10 19:27
 * Email: 895745843@qq.com
 */
public interface ApiService {

    //@POST("api10xt/lessonlogin/school_content")
//@FormUrlEncoded
//Observable<SchoolElegantBean> schoolElegant(@Field("classid") String classid);

    /**
     * 获取兴趣标签
     *
     * @return
     */
    @POST("index.php/api/interest/get_list")
    @FormUrlEncoded
    Observable<InterestBean> getInterest(@Field("user_id") String user_id, @Field("user_token") String user_token);

    /**
     * 发送验证码
     *
     * @param
     * @return
     */
    @POST("index.php/api/Sendmsg/do_sendmsg")
    @FormUrlEncoded
    Observable<SendMessageBean> sendCode(@Field("phone") String phone, @Field("yzm") String yzm, @Field("datetime") String datetime, @Field("sign") String sign);

    /**
     * 添加兴趣
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST("index.php/api/interest/update")
    Observable<BaseBean> addInterest(@Field("interest_ids") Integer[] interest_ids, @Field("user_id") String user_id, @Field("user_token") String user_token);

    /**
     * 登录
     *
     * @param login_addr  登陆地址
     * @param phone_info  手机信息
     * @param app_version 版本号
     * @param login_type  登录类型
     * @param phone       手机号
     * @param yzm         验证码
     * @param openid      第三方返回的唯一标示符
     * @param nickname    昵称
     * @param logo        头像
     * @return
     */
    @POST("index.php/api/login/do_login")
    @FormUrlEncoded
    Observable<LoginBean> login(@Field("login_addr") String login_addr, @Field("phone_info") String phone_info, @Field("app_version") String app_version,
                                @Field("login_type") int login_type, @Field("phone") String phone, @Field("yzm") String yzm, @Field("openid") String openid,
                                @Field("nickname") String nickname, @Field("logo") String logo);

    /**
     * 登录界面的下方
     *
     * @return
     */
    @GET("index.php/api/Registerwelfare/get_info")
    Observable<MarketBean> market();

    /**
     * 首页
     *
     * @param user_id
     * @param user_token
     * @param type
     * @return
     */
    @POST("index.php/api/home/get_index_data")
    @FormUrlEncoded
    Observable<HomeViewPagerBean> homePager(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("type") String type);
   /* @GET()
    Observable<TestBean> test(@Url String url);*/

    /**
     * 选择类型
     *
     * @param user_id
     * @param user_token
     * @return
     */
    @POST("index.php/api/positiontype/get_list")
    @FormUrlEncoded
    Observable<TypeBean> getType(@Field("user_id") String user_id, @Field("user_token") String user_token);

    /**
     * 创建自定义点
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
    @POST("index.php/api/position/add")
    @FormUrlEncoded
    Observable<BaseBean> create_pointer(@Field("user_id") String user_id,
                                        @Field("user_token") String user_token,
                                        @Field("name") String name,
                                        @Field("en_name") String en_name,
                                        @Field("areas") String areas,
                                        @Field("address") String address,
                                        @Field("lng") String lng,
                                        @Field("lat") String lat,
                                        @Field("type_id") String type_id);

    @POST("index.php/api/topic/get_hot_list")
    @FormUrlEncoded
    Observable<HotTopicBean> getHotTopic(@Field("user_id") String user_id,
                                         @Field("user_token") String user_token,
                                         @Field("search") String search
    );

    @POST("index.php/api/topic/get_my_recent_list")
    @FormUrlEncoded
        //
    Observable<HotTopicBean> getNearTopic(@Field("user_id") String user_id,
                                          @Field("user_token") String user_token);

    @POST("index.php/api/topic/add")
    @FormUrlEncoded
    Observable<CreateTopicBean> createTopic(@Field("user_id") String user_id,
                                            @Field("user_token") String user_token,
                                            @Field("topic") String topic);

    @POST("index.php/api/topic/clear_recent")
    @FormUrlEncoded
    Observable<BaseBean> clearTopic(@Field("user_id") String user_id,
                                    @Field("user_token") String user_token);

    @POST("index.php/api/topic/get_recommend_list")
    @FormUrlEncoded
    Observable<HotTopicBean> getRecommend(@Field("user_id") String user_id,
                                          @Field("user_token") String user_token);

    @POST("index.php/api/channel/get_list")
    @FormUrlEncoded
    Observable<ChannelBean> getChannel(@Field("user_id") String user_id,
                                       /**
                                        * user_id	是	string	用户id
                                        * user_token	是	string	user_token
                                        * file_path	是	string	文件地址
                                        * file_type	是	1或2	1图片 2视频
                                        * file_desc	是	string	描述
                                        * channel_ids	是	arr	频道id数组
                                        * topic_ids	是	arr	话题id数组
                                        * open	是	1或2	1Y 2N
                                        * valid	是	1或2或3	1有效 2无效 3草稿
                                        * position_name	是	string	位置名称
                                        * position_areas	是	string	位置地区
                                        * position_address	是	string	位置地址
                                        * filter_id	是	string	滤镜id
                                        */
                                       @Field("user_token") String user_token);

    @POST("index.php/api/yox/add")
    @FormUrlEncoded
    Observable<BaseBean> publish_yoXiu(@Field("user_id") String user_id,
                                       @Field("user_token") String user_token,
                                       @Field("file_path") String file_path,
                                       @Field("file_type") int file_type,
                                       @Field("file_desc") String file_desc,
                                       @Field("channel_ids") int[] channel_ids,
                                       @Field("topic_ids") Integer[] topic_ids,
                                       @Field("open") int open,
                                       @Field("valid") int valid,
                                       @Field("position_name") String position_name,
                                       @Field("position_areas") String position_areas,
                                       @Field("position_address") String position_address,
                                       @Field("filter_id") String filter_id

    );
}

