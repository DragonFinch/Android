package com.iyoyogo.android.bean.mine.message;

import com.iyoyogo.android.bean.BaseBean;

import java.util.List;

public class MessageBean extends BaseBean {

    /**
     * data : {"list":[{"message_id":14,"user_logo":"","user_nickname":"123","title":"关注了 我","content":"","file_path":"","create_time":"昨天16:17","is_read":0,"is_reply":false,"param":""},{"message_id":3,"user_logo":"","user_nickname":"123","title":"关注了 我","content":"","file_path":"","create_time":"昨天14:18","is_read":0,"is_reply":false,"param":""}]}
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
             * message_id : 14
             * user_logo :
             * user_nickname : 123
             * title : 关注了 我
             * content :
             * file_path :
             * create_time : 昨天16:17
             * is_read : 0
             * is_reply : false
             * param :
             */

            private int message_id;
            private String user_logo;
            private String user_id;
            private String yo_type;
            private String yo_id;
            private String user_nickname;
            private String title;
            private String content;
            private String file_path;
            private String create_time;
            private int is_read;
            private boolean is_reply;
            private String param;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getYo_type() {
                return yo_type;
            }

            public void setYo_type(String yo_type) {
                this.yo_type = yo_type;
            }

            public String getYo_id() {
                return yo_id;
            }

            public void setYo_id(String yo_id) {
                this.yo_id = yo_id;
            }

            public int getMessage_id() {
                return message_id;
            }

            public void setMessage_id(int message_id) {
                this.message_id = message_id;
            }

            public String getUser_logo() {
                return user_logo;
            }

            public void setUser_logo(String user_logo) {
                this.user_logo = user_logo;
            }

            public String getUser_nickname() {
                return user_nickname;
            }

            public void setUser_nickname(String user_nickname) {
                this.user_nickname = user_nickname;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getFile_path() {
                return file_path;
            }

            public void setFile_path(String file_path) {
                this.file_path = file_path;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public int getIs_read() {
                return is_read;
            }

            public void setIs_read(int is_read) {
                this.is_read = is_read;
            }

            public boolean isIs_reply() {
                return is_reply;
            }

            public void setIs_reply(boolean is_reply) {
                this.is_reply = is_reply;
            }

            public String getParam() {
                return param;
            }

            public void setParam(String param) {
                this.param = param;
            }
        }
    }
}
