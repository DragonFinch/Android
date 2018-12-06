package com.iyoyogo.android.bean.yoxiu;

import com.iyoyogo.android.bean.BaseBean;

import java.util.List;

public class YoXiuDetailBean extends BaseBean {

    /**
     * data : {"id":1,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000013028.jpg?x-oss-process=image/resize,w_1242","file_desc":"3787","position_name":"铁道博物馆","create_time":"2018-02-19 14:35:26","count_view":1170,"count_comment":3,"count_praise":585,"count_collect":0,"user_nickname":"jason","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000074.jpg?x-oss-process=image/resize,w_50","is_my_like":0,"is_my_attention":0,"is_my_collect":1,"topic_list":[{"id":1,"topic":"话题1"},{"id":3,"topic":"话题3"}]}
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
         * id : 1
         * file_type : 1
         * file_path : https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000013028.jpg?x-oss-process=image/resize,w_1242
         * file_desc : 3787
         * position_name : 铁道博物馆
         * create_time : 2018-02-19 14:35:26
         * count_view : 1170
         * count_comment : 3
         * count_praise : 585
         * count_collect : 0
         * user_nickname : jason
         * user_logo : https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000074.jpg?x-oss-process=image/resize,w_50
         * is_my_like : 0
         * is_my_attention : 0
         * is_my_collect : 1
         * topic_list : [{"id":1,"topic":"话题1"},{"id":3,"topic":"话题3"}]
         */

        private int id;
        private int file_type;
        private String file_path;
        private String file_desc;
        private String position_name;
        private String create_time;
        private int count_view;
        private int count_comment;
        private int count_praise;
        private int count_collect;
        private String user_nickname;
        private String user_logo;
        private int is_my_like;
        private int is_my_attention;
        private int is_my_collect;
        private List<TopicListBean> topic_list;

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

        public String getPosition_name() {
            return position_name;
        }

        public void setPosition_name(String position_name) {
            this.position_name = position_name;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
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

        public int getCount_collect() {
            return count_collect;
        }

        public void setCount_collect(int count_collect) {
            this.count_collect = count_collect;
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

        public int getIs_my_like() {
            return is_my_like;
        }

        public void setIs_my_like(int is_my_like) {
            this.is_my_like = is_my_like;
        }

        public int getIs_my_attention() {
            return is_my_attention;
        }

        public void setIs_my_attention(int is_my_attention) {
            this.is_my_attention = is_my_attention;
        }

        public int getIs_my_collect() {
            return is_my_collect;
        }

        public void setIs_my_collect(int is_my_collect) {
            this.is_my_collect = is_my_collect;
        }

        public List<TopicListBean> getTopic_list() {
            return topic_list;
        }

        public void setTopic_list(List<TopicListBean> topic_list) {
            this.topic_list = topic_list;
        }

        public static class TopicListBean {
            /**
             * id : 1
             * topic : 话题1
             */

            private int id;
            private String topic;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTopic() {
                return topic;
            }

            public void setTopic(String topic) {
                this.topic = topic;
            }
        }
    }
}
