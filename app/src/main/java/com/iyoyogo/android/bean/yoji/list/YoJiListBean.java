package com.iyoyogo.android.bean.yoji.list;

import com.iyoyogo.android.bean.BaseBean;

import java.util.List;

public class YoJiListBean extends BaseBean {

    /**
     * data : {"list":[{"yo_id":4006,"count_view":0,"quality_type":0,"file_path":"file_path?x-oss-process=image/resize,w_400","title":"","p_start":"","p_end":"","count_dates":0,"cost":"","is_my_praise":0,"count_praise":0,"users_praise":[],"comment_list":[],"count_comment":0,"user_info":{"user_id":2,"user_nickname":"222","user_logo":"?x-oss-process=image/resize,w_50","user_level":0,"user_score":0,"partner_type":0}},{"yo_id":4007,"count_view":1,"quality_type":1,"file_path":"http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/19/KxKw3CNJCM.jpg?x-oss-process=image/resize,w_400","title":"测试","p_start":"绿地中央广场","p_end":"绿地中央广场","count_dates":2,"cost":"100","is_my_praise":0,"count_praise":1,"users_praise":[],"comment_list":[{"id":12386,"content":"这个yo记很好","user_nickname":"22妮子"}],"count_comment":2,"user_info":{"user_id":65699,"user_nickname":"良","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200002241.jpg?x-oss-process=image/resize,w_50","user_level":1,"user_score":154,"partner_type":0}}]}
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
             * yo_id : 4006
             * count_view : 0
             * quality_type : 0
             * file_path : file_path?x-oss-process=image/resize,w_400
             * title :
             * p_start :
             * p_end :
             * count_dates : 0
             * cost :
             * is_my_praise : 0
             * count_praise : 0
             * users_praise : []
             * comment_list : []
             * count_comment : 0
             * user_info : {"user_id":2,"user_nickname":"222","user_logo":"?x-oss-process=image/resize,w_50","user_level":0,"user_score":0,"partner_type":0}
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
            private List<?> users_praise;
            private List<CommentBean> comment_list;

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

            public List<?> getUsers_praise() {
                return users_praise;
            }

            public void setUsers_praise(List<?> users_praise) {
                this.users_praise = users_praise;
            }

            public List<CommentBean> getComment_list() {
                return comment_list;
            }

            public void setComment_list(List<CommentBean> comment_list) {
                this.comment_list = comment_list;
            }

            public static class UserInfoBean {
                /**
                 * user_id : 2
                 * user_nickname : 222
                 * user_logo : ?x-oss-process=image/resize,w_50
                 * user_level : 0
                 * user_score : 0
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
            public static class CommentBean {
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

