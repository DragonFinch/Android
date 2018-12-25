package com.iyoyogo.android.net;


import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.collection.AddCollectionBean;
import com.iyoyogo.android.bean.collection.CollectionFolderBean;
import com.iyoyogo.android.bean.collection.CollectionFolderContentBean;
import com.iyoyogo.android.bean.collection.MineCollectionBean;
import com.iyoyogo.android.bean.comment.CommentBean;
import com.iyoyogo.android.bean.home.HomeBean;
import com.iyoyogo.android.bean.login.SendMessageBean;
import com.iyoyogo.android.bean.login.interest.InterestBean;
import com.iyoyogo.android.bean.login.login.LoginBean;
import com.iyoyogo.android.bean.login.login.MarketBean;
import com.iyoyogo.android.bean.mine.DraftBean;
import com.iyoyogo.android.bean.mine.GetBindInfoBean;
import com.iyoyogo.android.bean.mine.GetUserInfoBean;
import com.iyoyogo.android.bean.mine.MineMessageBean;
import com.iyoyogo.android.bean.mine.PraiseBean;
import com.iyoyogo.android.bean.mine.center.UserCenterBean;
import com.iyoyogo.android.bean.mine.center.YoJiContentBean;
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

import java.util.List;

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
    Observable<HomeBean> homePager(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("type") String type);

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

    @POST("index.php/api/yox/save")
    @FormUrlEncoded
    Observable<BaseBean> publish_yoXiu(@Field("user_id") String user_id,
                                       @Field("user_token") String user_token,
                                       @Field("yo_id") int yo_id,
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

    @POST("index.php/api/yox/get_details")
    @FormUrlEncoded
    Observable<YoXiuDetailBean> getDetail(@Field("user_id") String user_id,
                                          @Field("user_token") String user_token,
                                          @Field("id") int id);

    @POST("index.php/api/yox/get_list")
    @FormUrlEncoded
    Observable<YouXiuListBean> getYoXiuList(@Field("user_id") String user_id,
                                            @Field("user_token") String user_token,
                                            @Field("page") int page);

    @POST("index.php/api/praise/click")
    @FormUrlEncoded
    Observable<BaseBean> praise(@Field("user_id") String user_id,
                                @Field("user_token") String user_token,
                                @Field("yo_id") int yo_id,
                                @Field("comment_id") int comment_id);

    @POST("index.php/api/usercenter/get")
    @FormUrlEncoded
    Observable<MineMessageBean> getPersonInfo(@Field("user_id") String user_id,
                                              @Field("user_token") String user_token);

    /**
     * user_id	是	string	用户id
     * user_token	是	string	user_token
     * page	是	int	第几页
     * yo_id	是	int	yo_id
     * comment_id	是	int	comment_id
     *
     * @return
     */
    @POST("index.php/api/comment/get_list")
    @FormUrlEncoded
    Observable<CommentBean> getComment(@Field("user_id") String user_id,
                                       @Field("user_token") String user_token,
                                       @Field("page") int page,
                                       @Field("yo_id") int yo_id,
                                       @Field("comment_id") int comment_id);

    @POST("index.php/api/comment/add")
    @FormUrlEncoded
    Observable<BaseBean> addComment(@Field("user_id") String user_id,
                                    @Field("user_token") String user_token,
                                    @Field("comment_id") int comment_id,
                                    @Field("yo_id") int yo_id,
                                    @Field("content") String content);

    @POST("index.php/api/userbase/get")
    @FormUrlEncoded
    Observable<GetUserInfoBean> getUserInfo(@Field("user_id") String user_id,
                                            @Field("user_token") String user_token);

    /**
     * user_id	是	string	用户id
     * user_token	是	string	user_token
     * user_nickname	是	string	昵称
     * user_logo	是	string	头像
     * user_sex	是	string	性别
     * user_birthday	是	string	生日
     * user_city	是	string	城市
     *
     * @return
     */
    @POST("/index.php/api/userbase/set")
    @FormUrlEncoded
    Observable<BaseBean> setUserInfo(@Field("user_id") String user_id,
                                     @Field("user_token") String user_token,
                                     @Field("user_nickname") String user_nickname,
                                     @Field("user_logo") String user_logo,
                                     @Field("user_sex") String user_sex,
                                     @Field("user_birthday") String user_birthday,
                                     @Field("user_city") String user_city);

    @POST("index.php/api/userbind/get")
    //index.php/api/userbind/get
    @FormUrlEncoded
    Observable<GetBindInfoBean> getBindInfo(@Field("user_id") String user_id,
                                            @Field("user_token") String user_token);

    @POST("index.php/api/userphone/set")
    @FormUrlEncoded
    Observable<BaseBean> replacePhone(@Field("user_id") String user_id,
                                      @Field("user_token") String user_token,
                                      @Field("phone") String phone, @Field("yzm") String yzm);

    @POST("index.php/api/logout/do_logout")
    @FormUrlEncoded
    Observable<BaseBean> logout(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("addr") String address, @Field("phone_info") String phone_info, @Field("app_version") String app_version);

    @POST("index.php/api/attention/add")
    @FormUrlEncoded
    Observable<AttentionBean> addAttention(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("target_id") int target_id);

    @POST("index.php/api/attention/delete")
    @FormUrlEncoded
    Observable<BaseBean> deleteAttention(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("id") int id);

    @POST("index.php/api/collect/folder_get_my_list")
    @FormUrlEncoded
    Observable<CollectionFolderBean> getCollectionFolder(@Field("user_id") String user_id, @Field("user_token") String user_token);

    /**
     * user_id	是	string	用户id
     * user_token	是	string	user_token
     * name	是	string（1-255）	收藏夹名称
     * open	是	int(1或2)	1公开 2私密
     * id	是	string	空字符串为创建 非空字符串为编辑
     *
     * @return
     */
    @POST("index.php/api/collect/folder_save")
    @FormUrlEncoded
    Observable<BaseBean> createFolder(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("name") String name, @Field("open") int open, @Field("id") String id);

    @POST("index.php/api/collect/collect_add")
    @FormUrlEncoded
    Observable<AddCollectionBean> addCollection(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("folder_id") int folder_id, @Field("yo_id") int yo_id);

    @POST("index.php/api/collect/collect_delete")
    @FormUrlEncoded
    Observable<BaseBean> deleteCollection(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("id") int id);

    @POST("index.php/api/dislike/add")
    @FormUrlEncoded
    Observable<BaseBean> dislike(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("yo_id") int yo_id);

    @POST("index.php/api/report/do_report")
    /**
     * comment_id	是	int	举报对象 comment_id 可以为0
     * content	是	string	原因
     */
    @FormUrlEncoded
    Observable<BaseBean> report(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("yo_id") int yo_id, @Field("comment_id") int comment_id, @Field("content") String content);

    @POST("index.php/api/label/get_all_list")
    @FormUrlEncoded
    Observable<LabelListBean> getLabelList(@Field("user_id") String user_id, @Field("user_token") String user_token);

    /**
     * user_id	是	string	用户id
     * user_token	是	string	user_token
     * label_id	是	int	0代表添加 其他为编辑对象
     * type	是	int	1要做 2不要做 3专属
     * label	是	string	标签名称
     *
     * @return
     */
    @POST("index.php/api/label/save")
    @FormUrlEncoded
    Observable<AddLabelBean> addLabel(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("label_id") int label_id, @Field("type") int type, @Field("label") String label);

    @POST("index.php/api/label/delete")
    @FormUrlEncoded
    Observable<BaseBean> deleteLabel(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("label_id") int label_id);

    /**
     * user_id	是	string	用户id
     * user_token	是	string	user_token
     * yo_id	否	int	添加时候为0 编辑时候为yo_id
     * logo	是	string	封面
     * title	是	string	标题
     * desc	是	string	描述
     * cost	是	int	花费(元)
     * open	是	int	1公开 2私密
     * valid	是	int	1有效 2无效 3草稿
     * topic_ids	是	list	话题
     * channel_ids	是	list	频道
     * list	是	list	地点list
     */
    @POST("index.php/api/yoj/save")
    @FormUrlEncoded
    Observable<BaseBean> publish_yoji(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("yo_id") int yo_id, @Field("logo") String logo, @Field("title") String title, @Field("desc") String desc, @Field("cost") int cost, @Field("open") int open, @Field("valid") int valid, @Field("topic_ids") List<Integer> topic_ids, @Field("channel_ids") List<Integer> channel_ids, @Field("list") String list);

    @POST("index.php/api/draft/get_list")
    @FormUrlEncoded
    Observable<DraftBean> getDraft(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("page") int page, @Field("page_size") int page_size);

    @POST("index.php/api/yoj/get_list")
    @FormUrlEncoded
    Observable<YoJiListBean> getYoJiList(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("page") int page, @Field("page_size") int page_size);

    @POST("index.php/api/yoj/details")
    @FormUrlEncoded
    Observable<YoJiDetailBean> getYoJiDetail(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("yo_id") int yo_id);

    @POST("index.php/api/collect/folder_get_my_tree")
    @FormUrlEncoded
    Observable<MineCollectionBean> getMineCollection(@Field("user_id") String user_id, @Field("user_token") String user_token);

    @POST("index.php/api/collect/folder_delete")
    @FormUrlEncoded
    Observable<BaseBean> deleteCollectionFolder(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("folder_ids") Integer[] folder_ids);

    @POST("index.php/api/userhome/get")
    @FormUrlEncoded
    Observable<UserCenterBean> getUserCenter(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("his_id") String his_id);

    @POST("index.php/api/userhome/get_his_yoj_list")
    @FormUrlEncoded
    Observable<YoJiContentBean> getYoJiContent(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("his_id") String his_id, @Field("page") String page, @Field("page_size") String page_size);

    @POST("index.php/api/praise/get_list")
    @FormUrlEncoded
    Observable<PraiseBean> getPraise(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("page") int page, @Field("page_size") int page_size);

    @POST("index.php/api/collect/collect_get_list_by_folder_id")
    @FormUrlEncoded
    Observable<CollectionFolderContentBean> getContent(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("folder_id") int folder_id, @Field("page") int page);

}

