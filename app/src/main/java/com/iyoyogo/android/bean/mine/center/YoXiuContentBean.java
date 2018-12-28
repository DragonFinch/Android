package com.iyoyogo.android.bean.mine.center;

import com.iyoyogo.android.bean.BaseBean;

import java.util.List;

public class YoXiuContentBean extends BaseBean {

    /**
     * data : {"list":[{"id":122,"comment_list":[{"id":406,"content":"景色好美","user_nickname":"畏人心"},{"id":405,"content":"这里交通方便吗","user_nickname":"空城旧梦忆悲伤"},{"id":404,"content":"很漂亮","user_nickname":"听闻"}],"is_my_like":0,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000000244.jpg?x-oss-process=image/resize,w_1000","position_name":"白鹭洲公园","count_view":1095,"count_comment":"3","count_praise":"547","create_time":"2017-10-15 13:41","user_id":60001,"user_nickname":"荧念一生","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000001.jpg?x-oss-process=image/resize,w_50"},{"id":125,"comment_list":[{"id":415,"content":"口味很不错 ","user_nickname":"星星伴月亮"},{"id":414,"content":"用餐环境不错，在胡同的一个院子里","user_nickname":"颖宝宝"},{"id":413,"content":"里面格局挺干净漂亮","user_nickname":"说泪"}],"is_my_like":0,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000000040.jpg?x-oss-process=image/resize,w_1000","position_name":"吾十小院","count_view":"186","count_comment":"3","count_praise":"74","create_time":"04-30 08:36","user_id":60001,"user_nickname":"荧念一生","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000001.jpg?x-oss-process=image/resize,w_50"}]}
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
             * id : 122
             * comment_list : [{"id":406,"content":"景色好美","user_nickname":"畏人心"},{"id":405,"content":"这里交通方便吗","user_nickname":"空城旧梦忆悲伤"},{"id":404,"content":"很漂亮","user_nickname":"听闻"}]
             * is_my_like : 0
             * file_type : 1
             * file_path : https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000000244.jpg?x-oss-process=image/resize,w_1000
             * position_name : 白鹭洲公园
             * count_view : 1095
             * count_comment : 3
             * count_praise : 547
             * create_time : 2017-10-15 13:41
             * user_id : 60001
             * user_nickname : 荧念一生
             * user_logo : https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000001.jpg?x-oss-process=image/resize,w_50
             */

            private int id;
            private int is_my_like;
            private int file_type;
            private String file_path;
            private String position_name;
            private int count_view;
            private String count_comment;
            private String count_praise;
            private String create_time;
            private int user_id;
            private String user_nickname;
            private String user_logo;
            private List<CommentListBean> comment_list;

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

            public String getPosition_name() {
                return position_name;
            }

            public void setPosition_name(String position_name) {
                this.position_name = position_name;
            }

            public int getCount_view() {
                return count_view;
            }

            public void setCount_view(int count_view) {
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

            public List<CommentListBean> getComment_list() {
                return comment_list;
            }

            public void setComment_list(List<CommentListBean> comment_list) {
                this.comment_list = comment_list;
            }

            public static class CommentListBean {
                /**
                 * id : 406
                 * content : 景色好美
                 * user_nickname : 畏人心
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
        }
    }
}
