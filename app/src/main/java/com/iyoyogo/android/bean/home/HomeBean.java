package com.iyoyogo.android.bean.home;

import com.iyoyogo.android.bean.BaseBean;

import java.util.List;

public class HomeBean extends BaseBean {

    /**
     * data : {"type":"attention","banner_list":[{"id":23,"path":"http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/12/4/8kyzDRG877.png","target_url":"http://app.iyoyogo.com/index.php/home/article/details?id=10","remark":"http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/8/pPTdGnfQNN.mp4"},{"id":25,"path":"http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/12/4/8asRfSYhJ4.png","target_url":"http://app.iyoyogo.com/index.php/home/article/details?id=6","remark":""},{"id":24,"path":"http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/12/4/S2c3bfYd6A.png","target_url":"http://www.jd.com","remark":""},{"id":26,"path":"http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/12/4/SY6Z3wEe6s.png","target_url":"http://www.jd.com","remark":"a"},{"id":18,"path":"http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/12/4/J5FbZpSG28.png","target_url":"http://www.baidu.com","remark":"2"}],"yox_list":[{"id":2607,"is_my_like":0,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000021643.jpg?x-oss-process=image/resize,w_400","file_desc":"中国美术馆的山坡油画","position_name":"中国美术馆","quality_type":0,"count_view":1491,"count_comment":3,"count_praise":745,"create_time":"2018-09-24 12:49:04","user_id":61941,"user_nickname":"番茄","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200200071.jpg?x-oss-process=image/resize,w_50","partner_type":0,"user_level":0,"user_score":57},{"id":3227,"is_my_like":0,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000003658.jpg?x-oss-process=image/resize,w_400","file_desc":"一只尾随我们的鸟","position_name":"绿岛","quality_type":0,"count_view":969,"count_comment":3,"count_praise":193,"create_time":"2017-08-10 11:04:13","user_id":60206,"user_nickname":"斑马","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200006980.jpg?x-oss-process=image/resize,w_50","partner_type":0,"user_level":1,"user_score":128},{"id":1172,"is_my_like":0,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000013322.jpg?x-oss-process=image/resize,w_400","file_desc":"彼岸花","position_name":"束河古镇","quality_type":0,"count_view":1383,"count_comment":3,"count_praise":414,"create_time":"2017-12-19 08:23:55","user_id":60622,"user_nickname":"jason","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000074.jpg?x-oss-process=image/resize,w_50","partner_type":3,"user_level":1,"user_score":101},{"id":2749,"is_my_like":0,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000021791.jpg?x-oss-process=image/resize,w_400","file_desc":"雪窦山风景区，晋代古刹","position_name":"雪窦山","quality_type":0,"count_view":824,"count_comment":3,"count_praise":247,"create_time":"2017-05-23 10:16:06","user_id":62028,"user_nickname":"一拳超人G","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200200087.jpg?x-oss-process=image/resize,w_50","partner_type":0,"user_level":1,"user_score":102},{"id":805,"is_my_like":0,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000021013.jpg?x-oss-process=image/resize,w_400","file_desc":"北京地标性大国贸的恢弘呦","position_name":"国贸大厦一期","quality_type":0,"count_view":1024,"count_comment":3,"count_praise":614,"create_time":"2018-07-24 10:18:14","user_id":61255,"user_nickname":"二硕","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200200001.jpg?x-oss-process=image/resize,w_50","partner_type":0,"user_level":0,"user_score":82},{"id":1466,"is_my_like":0,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000004013.jpg?x-oss-process=image/resize,w_400","file_desc":"天守阁打卡","position_name":"大阪公园天守阁","quality_type":0,"count_view":1254,"count_comment":3,"count_praise":501,"create_time":"2016-07-25 16:56:02","user_id":60307,"user_nickname":"突然","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200006985.jpg?x-oss-process=image/resize,w_50","partner_type":0,"user_level":0,"user_score":25},{"id":2612,"is_my_like":0,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000004080.jpg?x-oss-process=image/resize,w_400","file_desc":"美食","position_name":"格兰维尔岛","quality_type":0,"count_view":1451,"count_comment":3,"count_praise":290,"create_time":"2017-07-05 17:36:02","user_id":60316,"user_nickname":"ZJY","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000055.jpg?x-oss-process=image/resize,w_50","partner_type":0,"user_level":1,"user_score":174},{"id":1599,"is_my_like":0,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000009155.jpg?x-oss-process=image/resize,w_400","file_desc":"候鸟成群","position_name":"杭州湾国家湿地公园","quality_type":0,"count_view":756,"count_comment":6,"count_praise":115,"create_time":"2018-10-01 13:33:17","user_id":63381,"user_nickname":"花瓣飘飘","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200010182.jpg?x-oss-process=image/resize,w_50","partner_type":0,"user_level":0,"user_score":36},{"id":1556,"is_my_like":0,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000013035.jpg?x-oss-process=image/resize,w_400","file_desc":"参观路线图","position_name":"航空博物馆","quality_type":0,"count_view":379,"count_comment":3,"count_praise":189,"create_time":"2018-04-29 14:13:09","user_id":60622,"user_nickname":"jason","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000074.jpg?x-oss-process=image/resize,w_50","partner_type":3,"user_level":1,"user_score":101},{"id":2221,"is_my_like":0,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000006369.jpg?x-oss-process=image/resize,w_400","file_desc":"热带植物","position_name":"郑州方特欢乐世界","quality_type":0,"count_view":309,"count_comment":7,"count_praise":81,"create_time":"2018-07-12 10:03:12","user_id":64071,"user_nickname":"杨桃小姐","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200010040.jpg?x-oss-process=image/resize,w_50","partner_type":0,"user_level":0,"user_score":58}],"yoj_list":[{"yo_id":4054,"count_view":881,"quality_type":0,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000000204.jpg?x-oss-process=image/resize,w_400","title":"避开假期的旅行，呼和浩特的风情","p_start":"大昭寺","p_end":"清真大寺","count_dates":1,"cost":"500","is_my_praise":0,"count_praise":113,"users_praise":[{"user_id":70269,"user_nickname":"野欢","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200001277.jpg?x-oss-process=image/resize,w_50"},{"user_id":69317,"user_nickname":"一闪一闪的美丽","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200005413.jpg?x-oss-process=image/resize,w_50"},{"user_id":69196,"user_nickname":"MYANSWR我的答案","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200005389.jpg?x-oss-process=image/resize,w_50"},{"user_id":69041,"user_nickname":"陌生的城市","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200004138.jpg?x-oss-process=image/resize,w_50"},{"user_id":68839,"user_nickname":"巴黎铁塔下的小铁匠","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200009950.jpg?x-oss-process=image/resize,w_50"},{"user_id":68740,"user_nickname":"孤舟残月","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200008775.jpg?x-oss-process=image/resize,w_50"},{"user_id":68666,"user_nickname":"梦落轻寻","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200006798.jpg?x-oss-process=image/resize,w_50"},{"user_id":68349,"user_nickname":"怪品味","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200002672.jpg?x-oss-process=image/resize,w_50"}],"comment_list":[{"id":12516,"content":"在雨后的下午走上洗刷一新的伊斯兰建筑特色景观街，街道两侧以叠涩拱券、穹隆、彩色琉璃砖装饰出来的高楼气势宏伟，浑厚饱满的绿色或黄色的球形殿顶，高耸的柱式塔楼，以沙漠黄为主的色调，让人领略到浓郁的伊斯兰风情。","user_nickname":"丑疤怪"},{"id":12515,"content":"首先这里是免费的，凭身份证就可以领票，而且科技馆就在旁边，愿意去也可以去看！博物馆的东西该死比较多的，古代生物化石，现代的动植物仿生模型，航天科技模型及火箭推进器残骸等！","user_nickname":"抹忆"},{"id":12514,"content":"大召无量寺，呼和浩特市内最有名的景点，来呼和浩特之前就把这里定为必来之地。","user_nickname":"简单的爱"}],"count_comment":3,"user_info":{"user_id":61033,"user_nickname":"林二萌","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200010079.jpg?x-oss-process=image/resize,w_50","user_level":0,"user_score":95,"partner_type":0},"user_id":60001,"user_nickname":"荧念一生","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000001.jpg?x-oss-process=image/resize,w_50","partner_type":0,"score":51,"user_level":0,"count_yox":224,"count_yoj":50,"list_4":[{"yo_id":4307,"yo_type":2,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000000018.jpg"},{"yo_id":4306,"yo_type":2,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000000041.jpg"},{"yo_id":4305,"yo_type":2,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000000037.jpg"},{"yo_id":4304,"yo_type":2,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000000019.jpg"}],"rand":1},{"yo_id":4173,"count_view":650,"quality_type":0,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000000159.jpg?x-oss-process=image/resize,w_400","title":"蒲霞，拍照的好地方","p_start":"蒲霞","p_end":"蒲霞","count_dates":1,"cost":"600","is_my_praise":0,"count_praise":414,"users_praise":[{"user_id":70208,"user_nickname":"桔烟","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200001235.jpg?x-oss-process=image/resize,w_50"},{"user_id":69778,"user_nickname":"柚子Yk","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200005545.jpg?x-oss-process=image/resize,w_50"},{"user_id":69399,"user_nickname":"秋桜","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000847.jpg?x-oss-process=image/resize,w_50"},{"user_id":68981,"user_nickname":"清酒吟","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200004129.jpg?x-oss-process=image/resize,w_50"},{"user_id":68903,"user_nickname":"半冷半酷半成熟","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200005335.jpg?x-oss-process=image/resize,w_50"},{"user_id":68803,"user_nickname":"水杯","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000721.jpg?x-oss-process=image/resize,w_50"},{"user_id":67806,"user_nickname":"白衣渡川","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200003962.jpg?x-oss-process=image/resize,w_50"},{"user_id":67450,"user_nickname":"笑叹一世浮沉","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000527.jpg?x-oss-process=image/resize,w_50"}],"comment_list":[{"id":12992,"content":"风景还可以","user_nickname":"米老鸭"},{"id":12991,"content":"没什么玩的，摄影师的好地方","user_nickname":"上官飞燕"},{"id":12990,"content":"拍照很好看","user_nickname":"妃的悲苦欢颜"}],"count_comment":3,"user_info":{"user_id":63715,"user_nickname":"最怕挣扎","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000260.jpg?x-oss-process=image/resize,w_50","user_level":1,"user_score":192,"partner_type":0}},{"user_id":60001,"user_nickname":"荧念一生","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000001.jpg?x-oss-process=image/resize,w_50","partner_type":0,"score":51,"user_level":0,"count_yox":224,"count_yoj":50,"list_4":[{"yo_id":4307,"yo_type":2,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000000018.jpg"},{"yo_id":4306,"yo_type":2,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000000041.jpg"},{"yo_id":4305,"yo_type":2,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000000037.jpg"},{"yo_id":4304,"yo_type":2,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000000019.jpg"}],"yo_id":0,"rand":1},{"user_id":65699,"user_nickname":"良","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200002241.jpg?x-oss-process=image/resize,w_50","partner_type":0,"score":154,"user_level":1,"count_yox":0,"count_yoj":1,"list_4":[{"yo_id":4007,"yo_type":2,"file_type":1,"file_path":"http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/19/KxKw3CNJCM.jpg"}],"yo_id":0,"rand":1},{"yo_id":4035,"count_view":0,"quality_type":0,"file_path":"https://xzdtest.oss-cn-beijing.aliyuncs.com/yoyogo/yoji/image1545284723856.jpg?x-oss-process=image/resize,w_400","title":"啊啊啊","p_start":"","p_end":"","count_dates":0,"cost":"222","is_my_praise":0,"count_praise":0,"users_praise":[],"comment_list":[],"count_comment":0,"user_info":{"user_id":80002,"user_nickname":"孔雀","user_logo":"https://xzdtest.oss-cn-beijing.aliyuncs.com/yoyogo/user_logo/image1544587745794.jpg?x-oss-process=image/resize,w_50","user_level":0,"user_score":0,"partner_type":0}},{"yo_id":4076,"count_view":1310,"quality_type":0,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000000125.jpg?x-oss-process=image/resize,w_400","title":"大连三日游，游戏的世界","p_start":"发现王国","p_end":"老虎滩海洋公园","count_dates":3,"cost":"1200","is_my_praise":0,"count_praise":835,"users_praise":[{"user_id":70092,"user_nickname":"眼趣","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200001152.jpg?x-oss-process=image/resize,w_50"},{"user_id":69253,"user_nickname":"等待繁华","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200005401.jpg?x-oss-process=image/resize,w_50"},{"user_id":68908,"user_nickname":"囚念生","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000737.jpg?x-oss-process=image/resize,w_50"},{"user_id":68470,"user_nickname":"你好我叫很个性","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200005277.jpg?x-oss-process=image/resize,w_50"},{"user_id":68205,"user_nickname":"诉清风","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200004011.jpg?x-oss-process=image/resize,w_50"},{"user_id":67100,"user_nickname":"南溟大人","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200003858.jpg?x-oss-process=image/resize,w_50"},{"user_id":64975,"user_nickname":"益若翼","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200003510.jpg?x-oss-process=image/resize,w_50"},{"user_id":64528,"user_nickname":"聋仆","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200002070.jpg?x-oss-process=image/resize,w_50"}],"comment_list":[{"id":12605,"content":"身临其境的感觉","user_nickname":"戈绾"},{"id":12604,"content":"五一大连暖和么，可以穿半截袖了么？","user_nickname":"竺痞"},{"id":12603,"content":"夜市在哪里","user_nickname":"行雁书"}],"count_comment":3,"user_info":{"user_id":66816,"user_nickname":"听风归","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000449.jpg?x-oss-process=image/resize,w_50","user_level":0,"user_score":39,"partner_type":0}},{"yo_id":4218,"count_view":198,"quality_type":0,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000000068.jpg?x-oss-process=image/resize,w_400","title":"台湾的私人旅行","p_start":"板桥夜市","p_end":"太鲁阁公园","count_dates":5,"cost":"3080","is_my_praise":0,"count_praise":126,"users_praise":[{"user_id":70249,"user_nickname":"俗野","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200001262.jpg?x-oss-process=image/resize,w_50"},{"user_id":69721,"user_nickname":"婲开在秋季","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200005526.jpg?x-oss-process=image/resize,w_50"},{"user_id":69406,"user_nickname":"清旖","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200007356.jpg?x-oss-process=image/resize,w_50"},{"user_id":68862,"user_nickname":"真真切切","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200009952.jpg?x-oss-process=image/resize,w_50"},{"user_id":66275,"user_nickname":"梦幻的味道","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200006480.jpg?x-oss-process=image/resize,w_50"},{"user_id":65330,"user_nickname":"徒旅","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200007971.jpg?x-oss-process=image/resize,w_50"},{"user_id":64787,"user_nickname":"攻陷","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200007935.jpg?x-oss-process=image/resize,w_50"},{"user_id":64576,"user_nickname":"本宫做不到","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200004736.jpg?x-oss-process=image/resize,w_50"}],"comment_list":[{"id":13204,"content":"很美的一张夜景图","user_nickname":"九音引魂箫"},{"id":13203,"content":"这么文艺的一个地方","user_nickname":"独归"},{"id":13202,"content":"漂亮的逆光","user_nickname":"扶袖"}],"count_comment":7,"user_info":{"user_id":60004,"user_nickname":"小笨笨","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000003.jpg?x-oss-process=image/resize,w_50","user_level":1,"user_score":100,"partner_type":0}},{"user_id":60003,"user_nickname":"明天过后","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200006961.jpg?x-oss-process=image/resize,w_50","partner_type":0,"score":39,"user_level":0,"count_yox":5,"count_yoj":0,"list_4":[{"yo_id":3365,"yo_type":1,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000002179.jpg"},{"yo_id":1740,"yo_type":1,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000002181.jpg"},{"yo_id":1412,"yo_type":1,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000002178.jpg"},{"yo_id":367,"yo_type":1,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000002182.jpg"}],"yo_id":0,"rand":4}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * type : attention
         * banner_list : [{"id":23,"path":"http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/12/4/8kyzDRG877.png","target_url":"http://app.iyoyogo.com/index.php/home/article/details?id=10","remark":"http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/8/pPTdGnfQNN.mp4"},{"id":25,"path":"http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/12/4/8asRfSYhJ4.png","target_url":"http://app.iyoyogo.com/index.php/home/article/details?id=6","remark":""},{"id":24,"path":"http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/12/4/S2c3bfYd6A.png","target_url":"http://www.jd.com","remark":""},{"id":26,"path":"http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/12/4/SY6Z3wEe6s.png","target_url":"http://www.jd.com","remark":"a"},{"id":18,"path":"http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/12/4/J5FbZpSG28.png","target_url":"http://www.baidu.com","remark":"2"}]
         * yox_list : [{"id":2607,"is_my_like":0,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000021643.jpg?x-oss-process=image/resize,w_400","file_desc":"中国美术馆的山坡油画","position_name":"中国美术馆","quality_type":0,"count_view":1491,"count_comment":3,"count_praise":745,"create_time":"2018-09-24 12:49:04","user_id":61941,"user_nickname":"番茄","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200200071.jpg?x-oss-process=image/resize,w_50","partner_type":0,"user_level":0,"user_score":57},{"id":3227,"is_my_like":0,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000003658.jpg?x-oss-process=image/resize,w_400","file_desc":"一只尾随我们的鸟","position_name":"绿岛","quality_type":0,"count_view":969,"count_comment":3,"count_praise":193,"create_time":"2017-08-10 11:04:13","user_id":60206,"user_nickname":"斑马","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200006980.jpg?x-oss-process=image/resize,w_50","partner_type":0,"user_level":1,"user_score":128},{"id":1172,"is_my_like":0,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000013322.jpg?x-oss-process=image/resize,w_400","file_desc":"彼岸花","position_name":"束河古镇","quality_type":0,"count_view":1383,"count_comment":3,"count_praise":414,"create_time":"2017-12-19 08:23:55","user_id":60622,"user_nickname":"jason","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000074.jpg?x-oss-process=image/resize,w_50","partner_type":3,"user_level":1,"user_score":101},{"id":2749,"is_my_like":0,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000021791.jpg?x-oss-process=image/resize,w_400","file_desc":"雪窦山风景区，晋代古刹","position_name":"雪窦山","quality_type":0,"count_view":824,"count_comment":3,"count_praise":247,"create_time":"2017-05-23 10:16:06","user_id":62028,"user_nickname":"一拳超人G","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200200087.jpg?x-oss-process=image/resize,w_50","partner_type":0,"user_level":1,"user_score":102},{"id":805,"is_my_like":0,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000021013.jpg?x-oss-process=image/resize,w_400","file_desc":"北京地标性大国贸的恢弘呦","position_name":"国贸大厦一期","quality_type":0,"count_view":1024,"count_comment":3,"count_praise":614,"create_time":"2018-07-24 10:18:14","user_id":61255,"user_nickname":"二硕","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200200001.jpg?x-oss-process=image/resize,w_50","partner_type":0,"user_level":0,"user_score":82},{"id":1466,"is_my_like":0,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000004013.jpg?x-oss-process=image/resize,w_400","file_desc":"天守阁打卡","position_name":"大阪公园天守阁","quality_type":0,"count_view":1254,"count_comment":3,"count_praise":501,"create_time":"2016-07-25 16:56:02","user_id":60307,"user_nickname":"突然","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200006985.jpg?x-oss-process=image/resize,w_50","partner_type":0,"user_level":0,"user_score":25},{"id":2612,"is_my_like":0,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000004080.jpg?x-oss-process=image/resize,w_400","file_desc":"美食","position_name":"格兰维尔岛","quality_type":0,"count_view":1451,"count_comment":3,"count_praise":290,"create_time":"2017-07-05 17:36:02","user_id":60316,"user_nickname":"ZJY","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000055.jpg?x-oss-process=image/resize,w_50","partner_type":0,"user_level":1,"user_score":174},{"id":1599,"is_my_like":0,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000009155.jpg?x-oss-process=image/resize,w_400","file_desc":"候鸟成群","position_name":"杭州湾国家湿地公园","quality_type":0,"count_view":756,"count_comment":6,"count_praise":115,"create_time":"2018-10-01 13:33:17","user_id":63381,"user_nickname":"花瓣飘飘","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200010182.jpg?x-oss-process=image/resize,w_50","partner_type":0,"user_level":0,"user_score":36},{"id":1556,"is_my_like":0,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000013035.jpg?x-oss-process=image/resize,w_400","file_desc":"参观路线图","position_name":"航空博物馆","quality_type":0,"count_view":379,"count_comment":3,"count_praise":189,"create_time":"2018-04-29 14:13:09","user_id":60622,"user_nickname":"jason","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000074.jpg?x-oss-process=image/resize,w_50","partner_type":3,"user_level":1,"user_score":101},{"id":2221,"is_my_like":0,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000006369.jpg?x-oss-process=image/resize,w_400","file_desc":"热带植物","position_name":"郑州方特欢乐世界","quality_type":0,"count_view":309,"count_comment":7,"count_praise":81,"create_time":"2018-07-12 10:03:12","user_id":64071,"user_nickname":"杨桃小姐","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200010040.jpg?x-oss-process=image/resize,w_50","partner_type":0,"user_level":0,"user_score":58}]
         * yoj_list : [{"yo_id":4054,"count_view":881,"quality_type":0,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000000204.jpg?x-oss-process=image/resize,w_400","title":"避开假期的旅行，呼和浩特的风情","p_start":"大昭寺","p_end":"清真大寺","count_dates":1,"cost":"500","is_my_praise":0,"count_praise":113,"users_praise":[{"user_id":70269,"user_nickname":"野欢","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200001277.jpg?x-oss-process=image/resize,w_50"},{"user_id":69317,"user_nickname":"一闪一闪的美丽","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200005413.jpg?x-oss-process=image/resize,w_50"},{"user_id":69196,"user_nickname":"MYANSWR我的答案","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200005389.jpg?x-oss-process=image/resize,w_50"},{"user_id":69041,"user_nickname":"陌生的城市","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200004138.jpg?x-oss-process=image/resize,w_50"},{"user_id":68839,"user_nickname":"巴黎铁塔下的小铁匠","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200009950.jpg?x-oss-process=image/resize,w_50"},{"user_id":68740,"user_nickname":"孤舟残月","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200008775.jpg?x-oss-process=image/resize,w_50"},{"user_id":68666,"user_nickname":"梦落轻寻","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200006798.jpg?x-oss-process=image/resize,w_50"},{"user_id":68349,"user_nickname":"怪品味","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200002672.jpg?x-oss-process=image/resize,w_50"}],"comment_list":[{"id":12516,"content":"在雨后的下午走上洗刷一新的伊斯兰建筑特色景观街，街道两侧以叠涩拱券、穹隆、彩色琉璃砖装饰出来的高楼气势宏伟，浑厚饱满的绿色或黄色的球形殿顶，高耸的柱式塔楼，以沙漠黄为主的色调，让人领略到浓郁的伊斯兰风情。","user_nickname":"丑疤怪"},{"id":12515,"content":"首先这里是免费的，凭身份证就可以领票，而且科技馆就在旁边，愿意去也可以去看！博物馆的东西该死比较多的，古代生物化石，现代的动植物仿生模型，航天科技模型及火箭推进器残骸等！","user_nickname":"抹忆"},{"id":12514,"content":"大召无量寺，呼和浩特市内最有名的景点，来呼和浩特之前就把这里定为必来之地。","user_nickname":"简单的爱"}],"count_comment":3,"user_info":{"user_id":61033,"user_nickname":"林二萌","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200010079.jpg?x-oss-process=image/resize,w_50","user_level":0,"user_score":95,"partner_type":0}},{"yo_id":4173,"count_view":650,"quality_type":0,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000000159.jpg?x-oss-process=image/resize,w_400","title":"蒲霞，拍照的好地方","p_start":"蒲霞","p_end":"蒲霞","count_dates":1,"cost":"600","is_my_praise":0,"count_praise":414,"users_praise":[{"user_id":70208,"user_nickname":"桔烟","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200001235.jpg?x-oss-process=image/resize,w_50"},{"user_id":69778,"user_nickname":"柚子Yk","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200005545.jpg?x-oss-process=image/resize,w_50"},{"user_id":69399,"user_nickname":"秋桜","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000847.jpg?x-oss-process=image/resize,w_50"},{"user_id":68981,"user_nickname":"清酒吟","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200004129.jpg?x-oss-process=image/resize,w_50"},{"user_id":68903,"user_nickname":"半冷半酷半成熟","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200005335.jpg?x-oss-process=image/resize,w_50"},{"user_id":68803,"user_nickname":"水杯","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000721.jpg?x-oss-process=image/resize,w_50"},{"user_id":67806,"user_nickname":"白衣渡川","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200003962.jpg?x-oss-process=image/resize,w_50"},{"user_id":67450,"user_nickname":"笑叹一世浮沉","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000527.jpg?x-oss-process=image/resize,w_50"}],"comment_list":[{"id":12992,"content":"风景还可以","user_nickname":"米老鸭"},{"id":12991,"content":"没什么玩的，摄影师的好地方","user_nickname":"上官飞燕"},{"id":12990,"content":"拍照很好看","user_nickname":"妃的悲苦欢颜"}],"count_comment":3,"user_info":{"user_id":63715,"user_nickname":"最怕挣扎","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000260.jpg?x-oss-process=image/resize,w_50","user_level":1,"user_score":192,"partner_type":0}},{"user_id":60001,"user_nickname":"荧念一生","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000001.jpg?x-oss-process=image/resize,w_50","partner_type":0,"score":51,"user_level":0,"count_yox":224,"count_yoj":50,"list_4":[{"yo_id":4307,"yo_type":2,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000000018.jpg"},{"yo_id":4306,"yo_type":2,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000000041.jpg"},{"yo_id":4305,"yo_type":2,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000000037.jpg"},{"yo_id":4304,"yo_type":2,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000000019.jpg"}],"yo_id":0,"rand":1},{"user_id":65699,"user_nickname":"良","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200002241.jpg?x-oss-process=image/resize,w_50","partner_type":0,"score":154,"user_level":1,"count_yox":0,"count_yoj":1,"list_4":[{"yo_id":4007,"yo_type":2,"file_type":1,"file_path":"http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/19/KxKw3CNJCM.jpg"}],"yo_id":0,"rand":1},{"yo_id":4035,"count_view":0,"quality_type":0,"file_path":"https://xzdtest.oss-cn-beijing.aliyuncs.com/yoyogo/yoji/image1545284723856.jpg?x-oss-process=image/resize,w_400","title":"啊啊啊","p_start":"","p_end":"","count_dates":0,"cost":"222","is_my_praise":0,"count_praise":0,"users_praise":[],"comment_list":[],"count_comment":0,"user_info":{"user_id":80002,"user_nickname":"孔雀","user_logo":"https://xzdtest.oss-cn-beijing.aliyuncs.com/yoyogo/user_logo/image1544587745794.jpg?x-oss-process=image/resize,w_50","user_level":0,"user_score":0,"partner_type":0}},{"yo_id":4076,"count_view":1310,"quality_type":0,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000000125.jpg?x-oss-process=image/resize,w_400","title":"大连三日游，游戏的世界","p_start":"发现王国","p_end":"老虎滩海洋公园","count_dates":3,"cost":"1200","is_my_praise":0,"count_praise":835,"users_praise":[{"user_id":70092,"user_nickname":"眼趣","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200001152.jpg?x-oss-process=image/resize,w_50"},{"user_id":69253,"user_nickname":"等待繁华","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200005401.jpg?x-oss-process=image/resize,w_50"},{"user_id":68908,"user_nickname":"囚念生","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000737.jpg?x-oss-process=image/resize,w_50"},{"user_id":68470,"user_nickname":"你好我叫很个性","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200005277.jpg?x-oss-process=image/resize,w_50"},{"user_id":68205,"user_nickname":"诉清风","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200004011.jpg?x-oss-process=image/resize,w_50"},{"user_id":67100,"user_nickname":"南溟大人","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200003858.jpg?x-oss-process=image/resize,w_50"},{"user_id":64975,"user_nickname":"益若翼","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200003510.jpg?x-oss-process=image/resize,w_50"},{"user_id":64528,"user_nickname":"聋仆","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200002070.jpg?x-oss-process=image/resize,w_50"}],"comment_list":[{"id":12605,"content":"身临其境的感觉","user_nickname":"戈绾"},{"id":12604,"content":"五一大连暖和么，可以穿半截袖了么？","user_nickname":"竺痞"},{"id":12603,"content":"夜市在哪里","user_nickname":"行雁书"}],"count_comment":3,"user_info":{"user_id":66816,"user_nickname":"听风归","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000449.jpg?x-oss-process=image/resize,w_50","user_level":0,"user_score":39,"partner_type":0}},{"yo_id":4218,"count_view":198,"quality_type":0,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000000068.jpg?x-oss-process=image/resize,w_400","title":"台湾的私人旅行","p_start":"板桥夜市","p_end":"太鲁阁公园","count_dates":5,"cost":"3080","is_my_praise":0,"count_praise":126,"users_praise":[{"user_id":70249,"user_nickname":"俗野","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200001262.jpg?x-oss-process=image/resize,w_50"},{"user_id":69721,"user_nickname":"婲开在秋季","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200005526.jpg?x-oss-process=image/resize,w_50"},{"user_id":69406,"user_nickname":"清旖","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200007356.jpg?x-oss-process=image/resize,w_50"},{"user_id":68862,"user_nickname":"真真切切","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200009952.jpg?x-oss-process=image/resize,w_50"},{"user_id":66275,"user_nickname":"梦幻的味道","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200006480.jpg?x-oss-process=image/resize,w_50"},{"user_id":65330,"user_nickname":"徒旅","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200007971.jpg?x-oss-process=image/resize,w_50"},{"user_id":64787,"user_nickname":"攻陷","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200007935.jpg?x-oss-process=image/resize,w_50"},{"user_id":64576,"user_nickname":"本宫做不到","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200004736.jpg?x-oss-process=image/resize,w_50"}],"comment_list":[{"id":13204,"content":"很美的一张夜景图","user_nickname":"九音引魂箫"},{"id":13203,"content":"这么文艺的一个地方","user_nickname":"独归"},{"id":13202,"content":"漂亮的逆光","user_nickname":"扶袖"}],"count_comment":7,"user_info":{"user_id":60004,"user_nickname":"小笨笨","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000003.jpg?x-oss-process=image/resize,w_50","user_level":1,"user_score":100,"partner_type":0}},{"user_id":60003,"user_nickname":"明天过后","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200006961.jpg?x-oss-process=image/resize,w_50","partner_type":0,"score":39,"user_level":0,"count_yox":5,"count_yoj":0,"list_4":[{"yo_id":3365,"yo_type":1,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000002179.jpg"},{"yo_id":1740,"yo_type":1,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000002181.jpg"},{"yo_id":1412,"yo_type":1,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000002178.jpg"},{"yo_id":367,"yo_type":1,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000002182.jpg"}],"yo_id":0,"rand":4}]
         */

        private String type;
        private List<BannerListBean> banner_list;
        private List<YoxListBean> yox_list;
        private List<YojListBean> yoj_list;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<BannerListBean> getBanner_list() {
            return banner_list;
        }

        public void setBanner_list(List<BannerListBean> banner_list) {
            this.banner_list = banner_list;
        }

        public List<YoxListBean> getYox_list() {
            return yox_list;
        }

        public void setYox_list(List<YoxListBean> yox_list) {
            this.yox_list = yox_list;
        }

        public List<YojListBean> getYoj_list() {
            return yoj_list;
        }

        public void setYoj_list(List<YojListBean> yoj_list) {
            this.yoj_list = yoj_list;
        }

        public static class BannerListBean {
            /**
             * id : 23
             * path : http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/12/4/8kyzDRG877.png
             * target_url : http://app.iyoyogo.com/index.php/home/article/details?id=10
             * remark : http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/8/pPTdGnfQNN.mp4
             */

            private int id;
            private String path;
            private String target_url;
            private String remark;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getTarget_url() {
                return target_url;
            }

            public void setTarget_url(String target_url) {
                this.target_url = target_url;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }

        public static class YoxListBean {
            /**
             * id : 2607
             * is_my_like : 0
             * file_type : 1
             * file_path : https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000021643.jpg?x-oss-process=image/resize,w_400
             * file_desc : 中国美术馆的山坡油画
             * position_name : 中国美术馆
             * quality_type : 0
             * count_view : 1491
             * count_comment : 3
             * count_praise : 745
             * create_time : 2018-09-24 12:49:04
             * user_id : 61941
             * user_nickname : 番茄
             * user_logo : https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200200071.jpg?x-oss-process=image/resize,w_50
             * partner_type : 0
             * user_level : 0
             * user_score : 57
             */

            private int id;
            private int is_my_like;
            private int file_type;
            private String file_path;
            private String file_desc;
            private String position_name;
            private int quality_type;
            private int count_view;
            private int count_comment;
            private int count_praise;
            private String create_time;
            private int user_id;
            private String user_nickname;
            private String user_logo;
            private int partner_type;
            private int user_level;
            private int user_score;
            private int is_video;

            public int getIs_video() {
                return is_video;
            }

            public void setIs_video(int is_video) {
                this.is_video = is_video;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIs_my_like() {
                return is_my_like;
            }

            public void setIs_my_like(int is_my_like) {
                this.is_my_like = is_my_like;
            }

            public int getFile_type() {
                return file_type;
            }

            public void setFile_type(int file_type) {
                this.file_type = file_type;
            }

            public String getFile_path() {
                return file_path;
            }

            public void setFile_path(String file_path) {
                this.file_path = file_path;
            }

            public String getFile_desc() {
                return file_desc;
            }

            public void setFile_desc(String file_desc) {
                this.file_desc = file_desc;
            }

            public String getPosition_name() {
                return position_name;
            }

            public void setPosition_name(String position_name) {
                this.position_name = position_name;
            }

            public int getQuality_type() {
                return quality_type;
            }

            public void setQuality_type(int quality_type) {
                this.quality_type = quality_type;
            }

            public int getCount_view() {
                return count_view;
            }

            public void setCount_view(int count_view) {
                this.count_view = count_view;
            }

            public int getCount_comment() {
                return count_comment;
            }

            public void setCount_comment(int count_comment) {
                this.count_comment = count_comment;
            }

            public int getCount_praise() {
                return count_praise;
            }

            public void setCount_praise(int count_praise) {
                this.count_praise = count_praise;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getUser_nickname() {
                return user_nickname;
            }

            public void setUser_nickname(String user_nickname) {
                this.user_nickname = user_nickname;
            }

            public String getUser_logo() {
                return user_logo;
            }

            public void setUser_logo(String user_logo) {
                this.user_logo = user_logo;
            }

            public int getPartner_type() {
                return partner_type;
            }

            public void setPartner_type(int partner_type) {
                this.partner_type = partner_type;
            }

            public int getUser_level() {
                return user_level;
            }

            public void setUser_level(int user_level) {
                this.user_level = user_level;
            }

            public int getUser_score() {
                return user_score;
            }

            public void setUser_score(int user_score) {
                this.user_score = user_score;
            }
        }

        public static class YojListBean {
            /**
             * yo_id : 4054
             * count_view : 881
             * quality_type : 0
             * file_path : https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000000204.jpg?x-oss-process=image/resize,w_400
             * title : 避开假期的旅行，呼和浩特的风情
             * p_start : 大昭寺
             * p_end : 清真大寺
             * count_dates : 1
             * cost : 500
             * is_my_praise : 0
             * count_praise : 113
             * users_praise : [{"user_id":70269,"user_nickname":"野欢","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200001277.jpg?x-oss-process=image/resize,w_50"},{"user_id":69317,"user_nickname":"一闪一闪的美丽","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200005413.jpg?x-oss-process=image/resize,w_50"},{"user_id":69196,"user_nickname":"MYANSWR我的答案","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200005389.jpg?x-oss-process=image/resize,w_50"},{"user_id":69041,"user_nickname":"陌生的城市","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200004138.jpg?x-oss-process=image/resize,w_50"},{"user_id":68839,"user_nickname":"巴黎铁塔下的小铁匠","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200009950.jpg?x-oss-process=image/resize,w_50"},{"user_id":68740,"user_nickname":"孤舟残月","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200008775.jpg?x-oss-process=image/resize,w_50"},{"user_id":68666,"user_nickname":"梦落轻寻","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200006798.jpg?x-oss-process=image/resize,w_50"},{"user_id":68349,"user_nickname":"怪品味","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200002672.jpg?x-oss-process=image/resize,w_50"}]
             * comment_list : [{"id":12516,"content":"在雨后的下午走上洗刷一新的伊斯兰建筑特色景观街，街道两侧以叠涩拱券、穹隆、彩色琉璃砖装饰出来的高楼气势宏伟，浑厚饱满的绿色或黄色的球形殿顶，高耸的柱式塔楼，以沙漠黄为主的色调，让人领略到浓郁的伊斯兰风情。","user_nickname":"丑疤怪"},{"id":12515,"content":"首先这里是免费的，凭身份证就可以领票，而且科技馆就在旁边，愿意去也可以去看！博物馆的东西该死比较多的，古代生物化石，现代的动植物仿生模型，航天科技模型及火箭推进器残骸等！","user_nickname":"抹忆"},{"id":12514,"content":"大召无量寺，呼和浩特市内最有名的景点，来呼和浩特之前就把这里定为必来之地。","user_nickname":"简单的爱"}]
             * count_comment : 3
             * user_info : {"user_id":61033,"user_nickname":"林二萌","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200010079.jpg?x-oss-process=image/resize,w_50","user_level":0,"user_score":95,"partner_type":0}
             * user_id : 60001
             * user_nickname : 荧念一生
             * user_logo : https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000001.jpg?x-oss-process=image/resize,w_50
             * partner_type : 0
             * score : 51
             * user_level : 0
             * count_yox : 224
             * count_yoj : 50
             * list_4 : [{"yo_id":4307,"yo_type":2,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000000018.jpg"},{"yo_id":4306,"yo_type":2,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000000041.jpg"},{"yo_id":4305,"yo_type":2,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000000037.jpg"},{"yo_id":4304,"yo_type":2,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000000019.jpg"}]
             * rand : 1
             */

            private int yo_id;
            private int count_view;
            private int quality_type;
            private String file_path;
            private String title;
            private String p_start;
            private String p_end;
            private int count_dates;
            private String cost;
            private int is_my_praise;
            private int count_praise;
            private int count_comment;
            private UserInfoBean user_info;
            private int user_id;
            private String user_nickname;
            private String user_logo;
            private int partner_type;
            private int score;
            private int user_level;
            private int count_yox;
            private int count_yoj;
            private int rand;
            private List<UsersPraiseBean> users_praise;
            private List<CommentListBean> comment_list;
            private List<List4Bean> list_4;

            public int getYo_id() {
                return yo_id;
            }

            public void setYo_id(int yo_id) {
                this.yo_id = yo_id;
            }

            public int getCount_view() {
                return count_view;
            }

            public void setCount_view(int count_view) {
                this.count_view = count_view;
            }

            public int getQuality_type() {
                return quality_type;
            }

            public void setQuality_type(int quality_type) {
                this.quality_type = quality_type;
            }

            public String getFile_path() {
                return file_path;
            }

            public void setFile_path(String file_path) {
                this.file_path = file_path;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getP_start() {
                return p_start;
            }

            public void setP_start(String p_start) {
                this.p_start = p_start;
            }

            public String getP_end() {
                return p_end;
            }

            public void setP_end(String p_end) {
                this.p_end = p_end;
            }

            public int getCount_dates() {
                return count_dates;
            }

            public void setCount_dates(int count_dates) {
                this.count_dates = count_dates;
            }

            public String getCost() {
                return cost;
            }

            public void setCost(String cost) {
                this.cost = cost;
            }

            public int getIs_my_praise() {
                return is_my_praise;
            }

            public void setIs_my_praise(int is_my_praise) {
                this.is_my_praise = is_my_praise;
            }

            public int getCount_praise() {
                return count_praise;
            }

            public void setCount_praise(int count_praise) {
                this.count_praise = count_praise;
            }

            public int getCount_comment() {
                return count_comment;
            }

            public void setCount_comment(int count_comment) {
                this.count_comment = count_comment;
            }

            public UserInfoBean getUser_info() {
                return user_info;
            }

            public void setUser_info(UserInfoBean user_info) {
                this.user_info = user_info;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getUser_nickname() {
                return user_nickname;
            }

            public void setUser_nickname(String user_nickname) {
                this.user_nickname = user_nickname;
            }

            public String getUser_logo() {
                return user_logo;
            }

            public void setUser_logo(String user_logo) {
                this.user_logo = user_logo;
            }

            public int getPartner_type() {
                return partner_type;
            }

            public void setPartner_type(int partner_type) {
                this.partner_type = partner_type;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public int getUser_level() {
                return user_level;
            }

            public void setUser_level(int user_level) {
                this.user_level = user_level;
            }

            public int getCount_yox() {
                return count_yox;
            }

            public void setCount_yox(int count_yox) {
                this.count_yox = count_yox;
            }

            public int getCount_yoj() {
                return count_yoj;
            }

            public void setCount_yoj(int count_yoj) {
                this.count_yoj = count_yoj;
            }

            public int getRand() {
                return rand;
            }

            public void setRand(int rand) {
                this.rand = rand;
            }

            public List<UsersPraiseBean> getUsers_praise() {
                return users_praise;
            }

            public void setUsers_praise(List<UsersPraiseBean> users_praise) {
                this.users_praise = users_praise;
            }

            public List<CommentListBean> getComment_list() {
                return comment_list;
            }

            public void setComment_list(List<CommentListBean> comment_list) {
                this.comment_list = comment_list;
            }

            public List<List4Bean> getList_4() {
                return list_4;
            }

            public void setList_4(List<List4Bean> list_4) {
                this.list_4 = list_4;
            }

            public static class UserInfoBean {
                /**
                 * user_id : 61033
                 * user_nickname : 林二萌
                 * user_logo : https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200010079.jpg?x-oss-process=image/resize,w_50
                 * user_level : 0
                 * user_score : 95
                 * partner_type : 0
                 */

                private int user_id;
                private String user_nickname;
                private String user_logo;
                private int user_level;
                private int user_score;
                private int partner_type;

                public int getUser_id() {
                    return user_id;
                }

                public void setUser_id(int user_id) {
                    this.user_id = user_id;
                }

                public String getUser_nickname() {
                    return user_nickname;
                }

                public void setUser_nickname(String user_nickname) {
                    this.user_nickname = user_nickname;
                }

                public String getUser_logo() {
                    return user_logo;
                }

                public void setUser_logo(String user_logo) {
                    this.user_logo = user_logo;
                }

                public int getUser_level() {
                    return user_level;
                }

                public void setUser_level(int user_level) {
                    this.user_level = user_level;
                }

                public int getUser_score() {
                    return user_score;
                }

                public void setUser_score(int user_score) {
                    this.user_score = user_score;
                }

                public int getPartner_type() {
                    return partner_type;
                }

                public void setPartner_type(int partner_type) {
                    this.partner_type = partner_type;
                }
            }

            public static class UsersPraiseBean {
                /**
                 * user_id : 70269
                 * user_nickname : 野欢
                 * user_logo : https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200001277.jpg?x-oss-process=image/resize,w_50
                 */

                private int user_id;
                private String user_nickname;
                private String user_logo;

                public int getUser_id() {
                    return user_id;
                }

                public void setUser_id(int user_id) {
                    this.user_id = user_id;
                }

                public String getUser_nickname() {
                    return user_nickname;
                }

                public void setUser_nickname(String user_nickname) {
                    this.user_nickname = user_nickname;
                }

                public String getUser_logo() {
                    return user_logo;
                }

                public void setUser_logo(String user_logo) {
                    this.user_logo = user_logo;
                }
            }

            public static class CommentListBean {
                /**
                 * id : 12516
                 * content : 在雨后的下午走上洗刷一新的伊斯兰建筑特色景观街，街道两侧以叠涩拱券、穹隆、彩色琉璃砖装饰出来的高楼气势宏伟，浑厚饱满的绿色或黄色的球形殿顶，高耸的柱式塔楼，以沙漠黄为主的色调，让人领略到浓郁的伊斯兰风情。
                 * user_nickname : 丑疤怪
                 */

                private int id;
                private String content;
                private String user_nickname;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public String getUser_nickname() {
                    return user_nickname;
                }

                public void setUser_nickname(String user_nickname) {
                    this.user_nickname = user_nickname;
                }
            }

            public static class List4Bean {
                /**
                 * yo_id : 4307
                 * yo_type : 2
                 * file_type : 1
                 * file_path : https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000000018.jpg
                 */

                private int yo_id;
                private int yo_type;
                private int file_type;
                private String file_path;

                public int getYo_id() {
                    return yo_id;
                }

                public void setYo_id(int yo_id) {
                    this.yo_id = yo_id;
                }

                public int getYo_type() {
                    return yo_type;
                }

                public void setYo_type(int yo_type) {
                    this.yo_type = yo_type;
                }

                public int getFile_type() {
                    return file_type;
                }

                public void setFile_type(int file_type) {
                    this.file_type = file_type;
                }

                public String getFile_path() {
                    return file_path;
                }

                public void setFile_path(String file_path) {
                    this.file_path = file_path;
                }
            }
        }
    }
}
