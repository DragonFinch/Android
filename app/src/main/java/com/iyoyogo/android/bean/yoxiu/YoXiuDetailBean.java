package com.iyoyogo.android.bean.yoxiu;

import com.iyoyogo.android.bean.BaseBean;

public class YoXiuDetailBean extends BaseBean {

    /**
     * data : {"id":"1878","filter_id":"","size":"","file_type":"1","file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000009734.jpg?x-oss-process=image/resize,w_1200","file_desc":"花果山万莲花洞","position_name":"花果山","create_time":"2018-10-29 04:16:50","count_view":"1274","count_comment":"4","count_praise":"138","count_collect":"0","user_id":"70412","user_nickname":"朱迪","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200010214.jpg?x-oss-process=image/resize,w_50","user_city":"北京,北京","is_my_like":0,"is_my_attention":0,"is_my_collect":0}
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
         * id : 1878
         * filter_id :
         * size :
         * file_type : 1
         * file_path : https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000009734.jpg?x-oss-process=image/resize,w_1200
         * file_desc : 花果山万莲花洞
         * position_name : 花果山
         * create_time : 2018-10-29 04:16:50
         * count_view : 1274
         * count_comment : 4
         * count_praise : 138
         * count_collect : 0
         * user_id : 70412
         * user_nickname : 朱迪
         * user_logo : https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200010214.jpg?x-oss-process=image/resize,w_50
         * user_city : 北京,北京
         * is_my_like : 0
         * is_my_attention : 0
         * is_my_collect : 0
         */

        private String id;
        private String filter_id;
        private String size;
        private String file_type;
        private String file_path;
        private String file_desc;
        private String position_name;
        private String create_time;
        private String count_view;
        private String count_comment;
        private String count_praise;
        private String count_collect;
        private String user_id;
        private String user_nickname;
        private String user_logo;
        private String user_city;
        private int is_my_like;
        private int is_my_attention;
        private int is_my_collect;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFilter_id() {
            return filter_id;
        }

        public void setFilter_id(String filter_id) {
            this.filter_id = filter_id;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getFile_type() {
            return file_type;
        }

        public void setFile_type(String file_type) {
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

        public String getCount_view() {
            return count_view;
        }

        public void setCount_view(String count_view) {
            this.count_view = count_view;
        }

        public String getCount_comment() {
            return count_comment;
        }

        public void setCount_comment(String count_comment) {
            this.count_comment = count_comment;
        }

        public String getCount_praise() {
            return count_praise;
        }

        public void setCount_praise(String count_praise) {
            this.count_praise = count_praise;
        }

        public String getCount_collect() {
            return count_collect;
        }

        public void setCount_collect(String count_collect) {
            this.count_collect = count_collect;
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

        public String getUser_city() {
            return user_city;
        }

        public void setUser_city(String user_city) {
            this.user_city = user_city;
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
    }
}
