package com.iyoyogo.android.bean.comment;

import com.iyoyogo.android.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

public class CommentBean extends BaseBean {

    /**
     * data : {"list":[{"id":12271,"pid":0,"yo_id":1,"user_id":80002,"content":"龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙.","count_praise":0,"count_comment":0,"create_time":"2018-12-04 17:41:29","user_nickname":"孔雀","user_logo":"","is_my_praise":1,"is_my_comment":0},{"id":12270,"pid":0,"yo_id":1,"user_id":80002,"content":"啦啦","count_praise":0,"count_comment":0,"create_time":"2018-12-04 17:39:20","user_nickname":"孔雀","user_logo":"","is_my_praise":0,"is_my_comment":1},{"id":12263,"pid":0,"yo_id":1,"user_id":2,"content":"c","count_praise":0,"count_comment":0,"create_time":"2018-12-03 21:30:37","user_nickname":"用户","user_logo":"","is_my_praise":1,"is_my_comment":1},{"id":12261,"pid":0,"yo_id":1,"user_id":2,"content":"c","count_praise":0,"count_comment":0,"create_time":"2018-12-03 21:30:10","user_nickname":"用户","user_logo":"","is_my_praise":0,"is_my_comment":0},{"id":12260,"pid":0,"yo_id":1,"user_id":2,"content":"c","count_praise":0,"count_comment":0,"create_time":"2018-12-03 21:29:49","user_nickname":"用户","user_logo":"","is_my_praise":0,"is_my_comment":1},{"id":12259,"pid":0,"yo_id":1,"user_id":2,"content":"","count_praise":0,"count_comment":0,"create_time":"2018-12-03 21:29:23","user_nickname":"用户","user_logo":"","is_my_praise":0,"is_my_comment":0},{"id":12258,"pid":0,"yo_id":1,"user_id":2,"content":"","count_praise":0,"count_comment":0,"create_time":"2018-12-03 21:29:16","user_nickname":"用户","user_logo":"","is_my_praise":0,"is_my_comment":1},{"id":12257,"pid":0,"yo_id":1,"user_id":2,"content":"","count_praise":0,"count_comment":0,"create_time":"2018-12-03 21:28:21","user_nickname":"用户","user_logo":"","is_my_praise":0,"is_my_comment":1},{"id":3,"pid":0,"yo_id":1,"user_id":69681,"content":"中国铁道博物馆。铁道博物馆是中国国家级专业铁路博物馆，它的前身是铁道部科学技术馆，1978年成立，2003年更名为中国铁道博物馆，主要任务是负责铁路文物、科研成果等展品的收藏、保管、陈列、展示及编辑研究工作。中国铁道博物馆有三个馆:正阳门馆（主要是历史资料、图片、文字说明和少量实物）、东郊馆（主要为各个历史时期的机车陈列）、詹天佑纪念馆（主要介绍詹天佑对中国铁路发展的贡献）。","count_praise":0,"count_comment":0,"create_time":"2018-02-19 21:29:43","user_nickname":"林尽处听雨眠","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200005512.jpg","is_my_praise":0,"is_my_comment":1},{"id":2,"pid":0,"yo_id":1,"user_id":60656,"content":"了解铁路发家史，学习没坏处的。","count_praise":0,"count_comment":0,"create_time":"2018-02-19 22:56:18","user_nickname":"罕女","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200001524.jpg","is_my_praise":0,"is_my_comment":1},{"id":1,"pid":0,"yo_id":1,"user_id":64330,"content":"在天安门广场东南角，一般人不会注意他","count_praise":0,"count_comment":0,"create_time":"2018-02-19 17:26:18","user_nickname":"止于心","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200003406.jpg","is_my_praise":1,"is_my_comment":1}]}
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

        public static class ListBean implements Serializable {
            /**
             * id : 12271
             * pid : 0
             * yo_id : 1
             * user_id : 80002
             * content : 龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙龙.
             * count_praise : 0
             * count_comment : 0
             * create_time : 2018-12-04 17:41:29
             * user_nickname : 孔雀
             * user_logo :
             * is_my_praise : 1
             * is_my_comment : 0
             */

            private int id;
            private int pid;
            private int yo_id;
            private int user_id;
            private String content;
            private int count_praise;
            private int count_comment;
            private String create_time;
            private String user_nickname;
            private String user_logo;
            private int is_my_praise;
            private int is_my_comment;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getPid() {
                return pid;
            }

            public void setPid(int pid) {
                this.pid = pid;
            }

            public int getYo_id() {
                return yo_id;
            }

            public void setYo_id(int yo_id) {
                this.yo_id = yo_id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
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

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
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
