package com.iyoyogo.android.bean;

import java.util.List;

/**
 * @author zhuhui
 * @date 2019/1/12
 * @description
 */
public class SameBean extends BaseBean {


    /**
     * data : {"list":[{"yo_id":5120,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000000120.jpg?x-oss-process=image/resize,w_400","user_id":"60093","user_nickname":"少年英雄小哪吒","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000063.jpg?x-oss-process=image/resize,w_50"},{"yo_id":5376,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000010142.jpg?x-oss-process=image/resize,w_400"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * yo_id : 5120
             * file_type : 1
             * file_path : https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/fm/fm_1000000120.jpg?x-oss-process=image/resize,w_400
             * user_id : 60093
             * user_nickname : 少年英雄小哪吒
             * user_logo : https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000063.jpg?x-oss-process=image/resize,w_50
             */

            private int yo_id;
            private int    file_type;
            private String file_path;
            private String user_id;
            private String user_nickname;
            private String user_logo;
            private String filter_id;
            private String filter_name;
            private String filter_aspect_ratio;
            private String filter_intensity;
            private String file_desc;
            private String position_name;
            private String create_time;
            private int    count_view;
            private int    count_comment;
            private int    count_praise;
            private int    count_collect;
            private int    user_level;
            private String user_score;
            private String partner_type;
            private int    is_my_attention;
            private int    is_my_collect;
            private int    is_my_praise;
            private int    is_my_comment;

            public int getYo_id() {
                return yo_id;
            }

            public void setYo_id(int yo_id) {
                this.yo_id = yo_id;
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

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
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

            public String getFilter_id() {
                return filter_id;
            }

            public void setFilter_id(String filter_id) {
                this.filter_id = filter_id;
            }

            public String getFilter_name() {
                return filter_name;
            }

            public void setFilter_name(String filter_name) {
                this.filter_name = filter_name;
            }

            public String getFilter_aspect_ratio() {
                return filter_aspect_ratio;
            }

            public void setFilter_aspect_ratio(String filter_aspect_ratio) {
                this.filter_aspect_ratio = filter_aspect_ratio;
            }

            public String getFilter_intensity() {
                return filter_intensity;
            }

            public void setFilter_intensity(String filter_intensity) {
                this.filter_intensity = filter_intensity;
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

            public int getUser_level() {
                return user_level;
            }

            public void setUser_level(int user_level) {
                this.user_level = user_level;
            }

            public String getUser_score() {
                return user_score;
            }

            public void setUser_score(String user_score) {
                this.user_score = user_score;
            }

            public String getPartner_type() {
                return partner_type;
            }

            public void setPartner_type(String partner_type) {
                this.partner_type = partner_type;
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

            public int getIs_my_praise() {
                return is_my_praise;
            }

            public void setIs_my_praise(int is_my_praise) {
                this.is_my_praise = is_my_praise;
            }

            public int getIs_my_comment() {
                return is_my_comment;
            }

            public void setIs_my_comment(int is_my_comment) {
                this.is_my_comment = is_my_comment;
            }
        }
    }
}
