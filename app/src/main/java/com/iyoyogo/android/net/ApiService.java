package com.iyoyogo.android.net;


import android.content.Context;

import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.HisFansBean;
import com.iyoyogo.android.bean.HisPositionBean;
import com.iyoyogo.android.bean.LikeBean;
import com.iyoyogo.android.bean.PublishSucessBean;
import com.iyoyogo.android.bean.SameBean;
import com.iyoyogo.android.bean.VipCenterBean;
import com.iyoyogo.android.bean.collection.AttentionsBean;
import com.iyoyogo.android.bean.collection.CommendAttentionBean;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.collection.AddCollectionBean;
import com.iyoyogo.android.bean.collection.AddCollectionBean1;
import com.iyoyogo.android.bean.collection.AddressBookBean;
import com.iyoyogo.android.bean.collection.CollectionFolderBean;
import com.iyoyogo.android.bean.collection.CollectionFolderContentBean;
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
import com.iyoyogo.android.bean.search.SearchBean;
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

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

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
    Observable<BaseBean> addInterest(@FieldMap Map<String, Object> map);

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
    Observable<HomeBean> homePager(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("type") String type, @Field("city") String city);

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

    //获取最近使用的话题
    @POST("index.php/api/topic/get_my_recent_list")
    @FormUrlEncoded
    //
    Observable<HotTopicBean> getNearTopic(@Field("user_id") String user_id,
                                          @Field("user_token") String user_token);

    //添加话题
    @POST("index.php/api/topic/add")
    @FormUrlEncoded
    Observable<CreateTopicBean> createTopic(@Field("user_id") String user_id,
                                            @Field("user_token") String user_token,
                                            @Field("topic") String topic);

    //清空话题
    @POST("index.php/api/topic/clear_recent")
    @FormUrlEncoded
    Observable<BaseBean> clearTopic(@Field("user_id") String user_id,
                                    @Field("user_token") String user_token);

    //获取话题
    @POST("index.php/api/topic/get_recommend_list")
    @FormUrlEncoded
    Observable<HotTopicBean> getRecommend(@Field("user_id") String user_id,
                                          @Field("user_token") String user_token);

    //获取频道列表
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

    //发布优秀
    @POST("index.php/api/yox/save")
    @FormUrlEncoded
    /**
     * position_city	是	string	位置城市 例如 北京
     * lng	是	string	经度
     * lat	是	string	纬度
     * size	是	string	比例参数 例如 4:9
     */
    Observable<PublishSucessBean> publish_yoXiu(@Field("user_id") String user_id,
                                                @Field("user_token") String user_token,
                                                @Field("yo_id") int yo_id,
                                                @Field("file_path") String file_path,
                                                @Field("file_type") int file_type,
                                                @Field("file_desc") String file_desc,
                                                @Field("channel_ids") String channel_ids,
                                                @Field("open") int open,
                                                @Field("valid") int valid,
                                                @Field("position_name") String position_name,
                                                @Field("position_areas") String position_areas,
                                                @Field("position_address") String position_address,
                                                @Field("position_city") String position_city,
                                                @Field("lat") String lat,
                                                @Field("lng") String lng,
                                                @Field("filter_id") String filter_id,
                                                @Field("size") String size

    );

    //获取yo秀详情
    @POST("index.php/api/yox/get_details")
    @FormUrlEncoded
    Observable<YoXiuDetailBean> getDetail(@Field("user_id") String user_id,
                                          @Field("user_token") String user_token,
                                          @Field("id") int id);

    //获取yo秀列表
    @POST("index.php/api/yox/get_list")
    @FormUrlEncoded
    Observable<YouXiuListBean> getYoXiuList(@Field("user_id") String user_id,
                                            @Field("user_token") String user_token,
                                            @Field("page") int page,@Field("city") String city);

    //获取yo秀列表
    @POST("index.php/api/yoxcommend/get_list")
    @FormUrlEncoded
    Observable<YouXiuListBean> getYoXiuAttentionList(@Field("user_id") String user_id,
                                                     @Field("user_token") String user_token,
                                                     @Field("page") int page,@Field("city") String city);


    //获取同款
    @POST("index.php/api/yoxgo/get_list")
    @FormUrlEncoded
    Observable<SameBean> getSameList(@Field("user_id") String user_id,
                                     @Field("user_token") String user_token,
                                     @Field("lng") String lng,
                                     @Field("lat") String lat,
                                     @Field("page") int page,
                                     @Field("page_size") String page_size
    );

    //点赞
    @POST("index.php/api/praise/click")
    @FormUrlEncoded
    Observable<LikeBean> praise(@Field("user_id") String user_id,
                                @Field("user_token") String user_token,
                                @Field("yo_id") int yo_id,
                                @Field("comment_id") int comment_id);

    //用户信息
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
    //获取评论列表
    @POST("index.php/api/comment/get_list")
    @FormUrlEncoded
    Observable<CommentBean> getComment(@Field("user_id") String user_id,
                                       @Field("user_token") String user_token,
                                       @Field("page") int page,
                                       @Field("yo_id") int yo_id,
                                       @Field("comment_id") int comment_id);

    //发布评论
    @POST("index.php/api/comment/add")
    @FormUrlEncoded
    Observable<BaseBean> addComment(@Field("user_id") String user_id,
                                    @Field("user_token") String user_token,
                                    @Field("comment_id") int comment_id,
                                    @Field("yo_id") int yo_id,
                                    @Field("content") String content);

    //获取用户信息
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
     * <p>
     * 设置用户信息
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

    @POST("index.php/api/v1_0_0.userbind/get")
    //获取绑定信息
    @FormUrlEncoded
    Observable<GetBindInfoBean> getBindInfo(@Field("user_id") String user_id,
                                            @Field("user_token") String user_token);

    @POST("index.php/api/userphone/set")
    @FormUrlEncoded
        //替换手机号
    Observable<BaseBean> replacePhone(@Field("user_id") String user_id,
                                      @Field("user_token") String user_token,
                                      @Field("phone") String phone, @Field("yzm") String yzm);

    //退出登录
    @POST("index.php/api/logout/do_logout")
    @FormUrlEncoded
    Observable<BaseBean> logout(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("addr") String address, @Field("phone_info") String phone_info, @Field("app_version") String app_version);

    //添加关注
    @POST("index.php/api/attention/click")
    @FormUrlEncoded
    Observable<AttentionBean> addAttention(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("target_id") int target_id);

    //添加关注
    @POST("index.php/api/attention/click")
    @FormUrlEncoded
    Observable<AttentionBean> addAttention1(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("target_id") String target_id);

    //取消关注
    @POST("index.php/api/attention/delete")
    @FormUrlEncoded
    Observable<BaseBean> deleteAttention(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("id") int id);

    //获取收藏夹列表
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
    //创建收藏夹
    @POST("index.php/api/collect/folder_save")
    @FormUrlEncoded
    Observable<BaseBean> createFolder(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("name") String name, @Field("open") int open, @Field("id") String id);

    //添加收藏
    @POST("index.php/api/collect/collect_add")
    @FormUrlEncoded
    Observable<AddCollectionBean> addCollection(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("folder_id") int folder_id, @Field("yo_id") int yo_id);

    //取消收藏
    @POST("/index.php/api/yo/delete_collect")
    @FormUrlEncoded
    Observable<AddCollectionBean> delCollection(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("yo_id") int yo_id);
//index.php/api/collect/collect_delete

    //不喜欢
    @POST("index.php/api/dislike/add")
    @FormUrlEncoded
    Observable<BaseBean> dislike(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("yo_id") int yo_id);

    //举报
    /**
     * comment_id	是	int	举报对象 comment_id 可以为0
     * content	是	string	原因
     */
    @POST("index.php/api/report/do_report")
    @FormUrlEncoded
    Observable<BaseBean> report(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("yo_id") int yo_id, @Field("comment_id") int comment_id, @Field("content") String content);

    //获取标签列表
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
    //发布yo记
    @POST("index.php/api/yoj/save")
    @FormUrlEncoded
    Observable<PublishSucessBean> publish_yoji(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("yo_id") int yo_id, @Field("logo") String logo, @Field("title") String title, @Field("desc") String desc, @Field("cost") int cost, @Field("open") int open, @Field("valid") int valid, @Field("channel_ids") String channel_ids, @Field("list") String list);

    //获取草稿列表
    @POST("index.php/api/draft/get_list")
    @FormUrlEncoded
    Observable<DraftBean> getDraft(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("page") int page, @Field("page_size") int page_size);

    //获取yo记列表
    @POST("index.php/api/yoj/get_list")
    @FormUrlEncoded
    Observable<YoJiListBean> getYoJiList(@Field("user_id") String user_id,
                                         @Field("user_token") String user_token,
                                         @Field("page") int page,
                                         @Field("page_size") int page_size);

    //获取yo记列表
    @POST("index.php/api/yojcommend/get_list")
    @FormUrlEncoded
    Observable<YoJiListBean> getJiAttentionList(@Field("user_id") String user_id,
                                                @Field("user_token") String user_token,
                                                @Field("page") int page, @Field("page_size") int page_size);

    //获取草稿详情
    @POST("index.php/api/yoj/details")
    @FormUrlEncoded
    Observable<YoJiDetailBean> getYoJiDetail(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("yo_id") int yo_id);

    //获取我的收藏
    @POST("index.php/api/collect/folder_get_my_tree")
    @FormUrlEncoded
    Observable<MineCollectionBean> getMineCollection(@Field("user_id") String user_id, @Field("user_token") String user_token);

    //获取TA的收藏
    @POST("index.php/api/userhome/get_his_collects")
    @FormUrlEncoded
    Observable<MineCollectionBean> getHisCollection(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("his_id") String his_id);


    //删除收藏夹
    @POST("index.php/api/collect/folder_delete")
    @FormUrlEncoded
    Observable<BaseBean> deleteCollectionFolder(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("folder_ids[]") int[] folder_ids);

    //获取用户中心
    @POST("index.php/api/userhome/get")
    @FormUrlEncoded
    Observable<UserCenterBean> getUserCenter(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("his_id") String his_id);

    //获取yo记内容
    @POST("index.php/api/userhome/get_his_yoj_list")
    @FormUrlEncoded
    Observable<YoJiContentBean> getYoJiContent(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("his_id") String his_id, @Field("page") String page, @Field("page_size") String page_size);

    //获取我喜欢的
    @POST("index.php/api/praise/get_list")
    @FormUrlEncoded
    Observable<PraiseBean> getPraise(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("page") int page, @Field("page_size") int page_size);

    //获取收藏夹内容
    @POST("index.php/api/collect/collect_get_list_by_folder_id")
    @FormUrlEncoded
    Observable<CollectionFolderContentBean> getContent(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("folder_id") int folder_id, @Field("page") int page);

    //取消收藏
    @POST("index.php/api/collect/collect_delete")
    @FormUrlEncoded
    Observable<BaseBean> deleteCollection(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("id") int id);

    //取消收藏
    @POST("index.php/api/collect/collect_delete")
    @FormUrlEncoded
    Observable<BaseBean> deleteCollection(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("record_ids[]") Integer[] record_ids);

    //移动收藏夹内容
    @POST("/index.php/api/collect/collect_move")
    @FormUrlEncoded
    Observable<BaseBean> moveCollectionFolder(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("record_ids[]") Integer[] record_ids, @Field("folder_id") int folder_id);

    //获取我的设置
    @POST("index.php/api/userconfig/get")
    @FormUrlEncoded
    Observable<MineSettingBean> getMineSetting(@Field("user_id") String user_id, @Field("user_token") String user_token);

    /**
     * user_id	是	string	用户id
     * user_token	是	string	user_token
     * wifi_auto_play_video	是	int（0否1是）	wifi下自动播放视频
     * notice	是	int（0否1是）	消息提醒
     * address_list	是	int（0否1是）	通讯录
     *
     * @return
     */
    //修改我的设置
    @POST("index.php/api/userconfig/set")
    @FormUrlEncoded
    Observable<BaseBean> setMineSetting(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("wifi_auto_play_video") int wifi_auto_play_video, @Field("notice") int notice, @Field("address_list") int address_list);

    //反馈
    @POST("index.php/api/userfeedback/add")
    @FormUrlEncoded
    Observable<BaseBean> addFeedBack(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("desc") String desc);

    //关于我们
    @GET("api/Aboutus/get_list")
    Observable<AboutMeBean> aboutMe();

    @POST("index.php/api/message/get_message_view")
    @FormUrlEncoded
    Observable<MessageBean> getMessage(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("type") int type, @Field("page") int page);

    @POST("index.php/api/yo/inc_count_share")
    @FormUrlEncoded
    Observable<BaseBean> share(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("yo_id") String id);

    @POST("index.php/api/message/center")
    @FormUrlEncoded
    Observable<MessageCenterBean> getMessageCenter(@Field("user_id") String user_id, @Field("user_token") String user_token);

    @POST("index.php/api/message/read_message")
    @FormUrlEncoded
    Observable<ReadMessage> readMessage(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("message_id") String message_id);

    //获取我粉丝中 我没关注的 (是我的粉丝，我却不是他的粉丝)
    @POST("index.php/api/userattention/get_my_fans_only")
    @FormUrlEncoded
    Observable<AddCollectionBean1> setAddCollection(@Field("user_id") String user_id,
                                                    @Field("user_token") String user_token,
                                                    @Field("page") String page,
                                                    @Field("page_size") String page_size);

    //获取通讯录中 关注情况
    @POST("index.php/api/userattention/address_list")
    @FormUrlEncoded
    Observable<AddressBookBean> setAddressBook(@Field("user_id") String user_id,
                                               @Field("user_token") String user_token,
                                               @Field("search") String search,
                                               @Field("list") String list);

    //极光推送
    @POST("index.php/api/userjpush/set")
    @FormUrlEncoded
    Observable<BaseBean> push(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("device") String device, @Field("jpush_rid") String jpush_rid);

    //打卡
    @POST("index.php/api/clock/do_clock")
    @FormUrlEncoded
    Observable<BaseBean> punchClock(@Field("user_id") String user_id, @Field("user_token") String user_token);

    //获取版本信息
    @POST("index.php/api/version/get_latest_version_info")
    @FormUrlEncoded
    Observable<VersionBean> getVersionMessage(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("type") String type);

    //不喜欢
    @POST("index.php/api/dislike/add")
    @FormUrlEncoded
    Observable<BaseBean> dislike(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("yo_id") int yo_id, @Field("type") int type);

    //推荐 给我 要关注的人
    @POST("index.php/api/userattention/commend_me_to_attention")
    @FormUrlEncoded
    Observable<CommendAttentionBean> setCommendAttention(@Field("user_id") String user_id,
                                                         @Field("user_token") String user_token);

    //获取 用户 关注的人群
    @POST("index.php/api/userhome/get_his_attentions")
    @FormUrlEncoded
    Observable<AttentionsBean> setAttentions(@Field("user_id") String user_id,
                                             @Field("user_token") String user_token,
                                             @Field("his_id") String his_id,
                                             @Field("page") String page,
                                             @Field("page_size") String page_size);

    //获取 用户 粉丝人群
    @POST("index.php/api/userhome/get_his_fans")
    @FormUrlEncoded
    Observable<HisFansBean> setHisFans(@Field("user_id") String user_id,
                                       @Field("user_token") String user_token,
                                       @Field("his_id") String his_id,
                                       @Field("page") String page,
                                       @Field("page_size") String page_size);

    @POST("index.php/api/userhome/get_his_yox_list")
    @FormUrlEncoded
    Observable<YoXiuContentBean> getYoXiuContent(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("his_id") String his_id, @Field("page") String page, @Field("page_size") String page_size);

    @POST("index.php/api/yo/delete")
    @FormUrlEncoded
    Observable<BaseBean> deleteYo(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("yo_id") int yo_id);

    @POST("index.php/api/comment/delete")
    @FormUrlEncoded
    Observable<BaseBean> deleteComment(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("comment_id") int comment_id);

    //用户等级页
    @POST("index.php/api/userlevel/get")
    @FormUrlEncoded
    Observable<VipCenterBean> setVipCenter(@Field("user_id") String user_id,
                                           @Field("user_token") String user_token);

    //yo记数据
    @POST("index.php/api/yoj/details_for_edit")
    @FormUrlEncoded
    Observable<PublishYoJiBean> getYoJiData(@Field("user_id") String user_id,
                                            @Field("user_token") String user_token,
                                            @Field("yo_id") String yo_id);

    //yo秀数据
    @POST("index.php/api/yox/details_for_edit")
    @FormUrlEncoded
    Observable<PublishYoXiuBean> getYoXiuData(@Field("user_id") String user_id,
                                              @Field("user_token") String user_token,
                                              @Field("yo_id") String yo_id);

    //获取用户历史自定义位置
    @POST("index.php/api/position/get_his_position")
    @FormUrlEncoded
    Observable<HisPositionBean> setHisPosition(@Field("user_id") String user_id,
                                               @Field("user_token") String user_token,
                                               @Field("page") int page,
                                               @Field("page_size") int page_size);

    //清空用户历史自定义位置
    @POST("index.php/api/position/del_his_position")
    @FormUrlEncoded
    Observable<BaseBean> DelPosition(@Field("user_id") String user_id, @Field("user_token") String user_token);


    //首页搜索   页面
    @POST("/index.php/api/search/get_hot_and_history")
    @FormUrlEncoded
    Observable<searchInfo> search(@Field("user_id") String user_id, @Field("user_token") String user_token);

    //首页搜索   页面
    @POST("/index.php/api/search/delete_history")
    @FormUrlEncoded
    Observable<ClerBean> searchCler(@Field("user_id") String user_id, @Field("user_token") String user_token);

    //首页  搜索
    @POST("index.php/api/search/index")
    @FormUrlEncoded
    Observable<KeywordBean> keyword(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("search") String search, @Field("type") String type, @Field("key_type") String key_type);


    //首页  搜索
    @POST("index.php/api/attention/click")
    @FormUrlEncoded
    Observable<GuanZhuBean> keyword(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("target_id") String target_id);

    //首页  选择城市
    @POST("/index.php/api/city/get_list")
    @FormUrlEncoded
    Observable<MapBean> getChengShi(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("type") String type, @Field("search") String search);

    //首页  个人信息
    @POST("index.php/api/city/get_list")
    @FormUrlEncoded
    Observable<MapBean> getChengShiLieBiao(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("type") String type, @Field("search") String search);

    //首页  个人信息
    @POST("index.php/api/city/get_list")
    @FormUrlEncoded
    Observable<MapBean> getGps(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("type") String type, @Field("search") String search);

    //首页  热门城市
    @POST("/index.php/api/city/get_hot")
    @FormUrlEncoded
    Observable<MapRenMei> getRenMei(@Field("user_id") String user_id, @Field("user_token") String user_token);

    //首页  搜索 关键字搜索
    @POST("/index.php/api/search/get_keywords")
    @FormUrlEncoded
    Observable<KeywordUserBean> getserarch(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("search") String search);

    //index.php/api/yox/count_video_inc
    //浏览量加1
    @POST("index.php/api/yox/count_video_inc")
    @FormUrlEncoded
    Observable<BaseBean> browse(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("yo_id") String yo_id,@Field("city") String city);

    //设置位置
    @POST("index.php/api/userposition/set")
    @FormUrlEncoded
    Observable<BaseBean> setLocation(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("lng") String lng, @Field("lat") String lat);

    //获取yo秀相同位置的列表
//    @POST("index.php/api/clicksearch/get_yo_position_list")
//    @FormUrlEncoded
//    Observable<YouXiuListBean> getYoXiuPosition(@Field("user_id")String user_id,@Field("user_token")String user_token,@Field("position")String position,@Field("type")int type,@Field("page")int page,@Field("page_size")String page_size);
    @POST("index.php/api/clicksearch/get_yo_label_list")
    @FormUrlEncoded
    Observable<YoJiListBean> getYoJiLabel(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("label") String label, @Field("page") int page, @Field("page_size") String page_size);

    @POST("index.php/api/clicksearch/get_yo_position_list")
    @FormUrlEncoded
    Observable<YoJiListBean> getYoJiPosition(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("position") String position, @Field("type") int type, @Field("page") int page, @Field("page_size") String page_size);

    @POST("index.php/api/clicksearch/get_yo_position_list")
    @FormUrlEncoded
    Observable<YouXiuListBean> getYoXiuPosition(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("position") String position, @Field("type") int type, @Field("page") int page, @Field("page_size") String page_size);

    //index.php/api/userbind/bind
    @POST("index.php/api/userbind/bind")
    @FormUrlEncoded
    /**
     * type	是	int	type 1wx 2qq 3wb
     * openid	是	str	openid
     * nickname	是	str	nickname
     * logo	是	str	logo
     */
    Observable<BaseBean> update_bind(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("type") int type, @Field("openid") String openid, @Field("nickname") String nickname, @Field("logo") String logo);

    @GET
    Observable<ResponseBody> downFile(@Url String fileUrl);

    @POST("index.php/api/yoxgo/get_detaile")
    @FormUrlEncoded
    Observable<SameBean.DataBean.ListBean> goCameraDetail(@Field("user_id") String user_id, @Field("user_token") String user_token, @Field("yo_id") int yo_id);

    /**
     * 崩溃日志上传
     *
     * @return
     */
    @POST("index.php/api/v1_0_0.errorlog/add")
    @FormUrlEncoded
    Observable<ResponseBody> updateErrorLog(@Field("content") String content);

    /***
     * 用户解绑第三方绑定设备
     */
    @POST("index.php/api/v1_0_0.userbind/unbind_extra")
    @FormUrlEncoded
    Observable<BaseBean> getUserBind(@Field("user_id") String user_id,
                                     @Field("user_token") String user_token,
                                     @Field("type") int type,
                                     @Field("openid") String openid);

}

