package com.iyoyogo.android.bean.home;

import com.iyoyogo.android.bean.BaseBean;

import java.util.List;

public class HomeViewPagerBean extends BaseBean {

    /**
     * data : {"type":"commend","banner_list":[{"id":23,"path":"http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/11/22/EC7cBXAadD.png","target_url":"1111","remark":"111111"},{"id":25,"path":"http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/11/22/rwJ3WNxRia.png","target_url":"http://www.jd.com","remark":"100000000"},{"id":24,"path":"http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/11/22/rwJ3WNxRia.png","target_url":"http://www.jd.com","remark":"100000000"},{"id":26,"path":"http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/11/22/WEssydwsCm.png","target_url":"http://www.jd.com","remark":"awdawdawdwawd"},{"id":18,"path":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/31/X5aanfHGhw.png","target_url":"http://www.baidu.com","remark":"2"}],"yox_list":[{"id":1,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000000005.jpg","file_desc":"天安门广场夜景","count_view":1099,"count_comment":3,"count_praise":659,"create_time":"2018-03-04 20:16:18","user_id":60000,"user_nickname":"荧念一生","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000001.jpg"},{"id":2,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000000039.jpg","file_desc":"古北水镇山顶教堂","count_view":756,"count_comment":3,"count_praise":151,"create_time":"2018-05-04 16:40:32","user_id":60000,"user_nickname":"荧念一生","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000001.jpg"},{"id":3,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000000040.jpg","file_desc":"安然静好的吾十小院","count_view":186,"count_comment":3,"count_praise":74,"create_time":"2018-04-30 08:36:19","user_id":60000,"user_nickname":"荧念一生","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000001.jpg"},{"id":4,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000000041.jpg","file_desc":"安然静好的吾十小院","count_view":1297,"count_comment":3,"count_praise":389,"create_time":"2018-04-30 08:41:18","user_id":60000,"user_nickname":"荧念一生","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000001.jpg"},{"id":5,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000000042.jpg","file_desc":"安然静好的吾十小院","count_view":955,"count_comment":3,"count_praise":191,"create_time":"2018-04-30 08:46:20","user_id":60000,"user_nickname":"荧念一生","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000001.jpg"},{"id":6,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000000043.jpg","file_desc":"在山林之中读书的慢生活","count_view":1073,"count_comment":4,"count_praise":644,"create_time":"2018-02-25 10:15:28","user_id":60000,"user_nickname":"荧念一生","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000001.jpg"},{"id":7,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000000044.jpg","file_desc":"在山林之中读书的慢生活","count_view":1184,"count_comment":4,"count_praise":592,"create_time":"2018-02-25 10:34:20","user_id":60000,"user_nickname":"荧念一生","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000001.jpg"},{"id":8,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000000045.jpg","file_desc":"在山林之中读书的慢生活","count_view":252,"count_comment":4,"count_praise":126,"create_time":"2018-02-25 10:39:57","user_id":60000,"user_nickname":"荧念一生","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000001.jpg"},{"id":9,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000000046.jpg","file_desc":"夜晚寂静的北海公园","count_view":947,"count_comment":3,"count_praise":379,"create_time":"2017-10-09 19:38:29","user_id":60000,"user_nickname":"荧念一生","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000001.jpg"},{"id":10,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000000047.jpg","file_desc":"夜晚寂静的北海公园","count_view":125,"count_comment":3,"count_praise":75,"create_time":"2017-10-09 21:42:17","user_id":60000,"user_nickname":"荧念一生","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000001.jpg"}],"yoj_list":[]}
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
         * type : commend
         * banner_list : [{"id":23,"path":"http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/11/22/EC7cBXAadD.png","target_url":"1111","remark":"111111"},{"id":25,"path":"http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/11/22/rwJ3WNxRia.png","target_url":"http://www.jd.com","remark":"100000000"},{"id":24,"path":"http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/11/22/rwJ3WNxRia.png","target_url":"http://www.jd.com","remark":"100000000"},{"id":26,"path":"http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/11/22/WEssydwsCm.png","target_url":"http://www.jd.com","remark":"awdawdawdwawd"},{"id":18,"path":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/31/X5aanfHGhw.png","target_url":"http://www.baidu.com","remark":"2"}]
         * yox_list : [{"id":1,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000000005.jpg","file_desc":"天安门广场夜景","count_view":1099,"count_comment":3,"count_praise":659,"create_time":"2018-03-04 20:16:18","user_id":60000,"user_nickname":"荧念一生","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000001.jpg"},{"id":2,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000000039.jpg","file_desc":"古北水镇山顶教堂","count_view":756,"count_comment":3,"count_praise":151,"create_time":"2018-05-04 16:40:32","user_id":60000,"user_nickname":"荧念一生","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000001.jpg"},{"id":3,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000000040.jpg","file_desc":"安然静好的吾十小院","count_view":186,"count_comment":3,"count_praise":74,"create_time":"2018-04-30 08:36:19","user_id":60000,"user_nickname":"荧念一生","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000001.jpg"},{"id":4,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000000041.jpg","file_desc":"安然静好的吾十小院","count_view":1297,"count_comment":3,"count_praise":389,"create_time":"2018-04-30 08:41:18","user_id":60000,"user_nickname":"荧念一生","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000001.jpg"},{"id":5,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000000042.jpg","file_desc":"安然静好的吾十小院","count_view":955,"count_comment":3,"count_praise":191,"create_time":"2018-04-30 08:46:20","user_id":60000,"user_nickname":"荧念一生","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000001.jpg"},{"id":6,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000000043.jpg","file_desc":"在山林之中读书的慢生活","count_view":1073,"count_comment":4,"count_praise":644,"create_time":"2018-02-25 10:15:28","user_id":60000,"user_nickname":"荧念一生","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000001.jpg"},{"id":7,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000000044.jpg","file_desc":"在山林之中读书的慢生活","count_view":1184,"count_comment":4,"count_praise":592,"create_time":"2018-02-25 10:34:20","user_id":60000,"user_nickname":"荧念一生","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000001.jpg"},{"id":8,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000000045.jpg","file_desc":"在山林之中读书的慢生活","count_view":252,"count_comment":4,"count_praise":126,"create_time":"2018-02-25 10:39:57","user_id":60000,"user_nickname":"荧念一生","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000001.jpg"},{"id":9,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000000046.jpg","file_desc":"夜晚寂静的北海公园","count_view":947,"count_comment":3,"count_praise":379,"create_time":"2017-10-09 19:38:29","user_id":60000,"user_nickname":"荧念一生","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000001.jpg"},{"id":10,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000000047.jpg","file_desc":"夜晚寂静的北海公园","count_view":125,"count_comment":3,"count_praise":75,"create_time":"2017-10-09 21:42:17","user_id":60000,"user_nickname":"荧念一生","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000001.jpg"}]
         * yoj_list : []
         */

        private String type;
        private List<BannerListBean> banner_list;
        private List<YoxListBean> yox_list;
        private List<?> yoj_list;

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

        public List<?> getYoj_list() {
            return yoj_list;
        }

        public void setYoj_list(List<?> yoj_list) {
            this.yoj_list = yoj_list;
        }

        public static class BannerListBean {
            /**
             * id : 23
             * path : http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/11/22/EC7cBXAadD.png
             * target_url : 1111
             * remark : 111111
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
             * id : 1
             * file_type : 1
             * file_path : https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000000005.jpg
             * file_desc : 天安门广场夜景
             * count_view : 1099
             * count_comment : 3
             * count_praise : 659
             * create_time : 2018-03-04 20:16:18
             * user_id : 60000
             * user_nickname : 荧念一生
             * user_logo : https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000001.jpg
             */

            private int id;
            private int file_type;
            private String file_path;
            private String file_desc;
            private int count_view;
            private int count_comment;
            private int count_praise;
            private String create_time;
            private int user_id;
            private String user_nickname;
            private String user_logo;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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
        }
    }
}
