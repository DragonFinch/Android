package com.iyoyogo.android.bean.yoxiu;

import com.iyoyogo.android.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

public class YouXiuListBean extends BaseBean {

    /**
     * data : {"list":[{"id":1,"comment_list":[{"id":3,"content":"中国铁道博物馆。铁道博物馆是中国国家级专业铁路博物馆，它的前身是铁道部科学技术馆，1978年成立，2003年更名为中国铁道博物馆，主要任务是负责铁路文物、科研成果等展品的收藏、保管、陈列、展示及编辑研究工作。中国铁道博物馆有三个馆:正阳门馆（主要是历史资料、图片、文字说明和少量实物）、东郊馆（主要为各个历史时期的机车陈列）、詹天佑纪念馆（主要介绍詹天佑对中国铁路发展的贡献）。","user_nickname":"莞尔笑"},{"id":2,"content":"了解铁路发家史，学习没坏处的。","user_nickname":"茉绿"},{"id":1,"content":"在天安门广场东南角，一般人不会注意他","user_nickname":"琴断弦奈何"}],"is_my_like":1,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000013028.jpg?x-oss-process=image/resize,w_200","position_name":"铁道博物馆","count_view":1170,"count_comment":3,"count_praise":585,"create_time":"2018-02-19 14:35:26","user_id":60137,"user_nickname":"jason","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000074?x-oss-process=image/resize,h_50"},{"id":2,"comment_list":[{"id":7,"content":"去吃吃逛逛真好","user_nickname":"余"},{"id":6,"content":"有购物的地方吗","user_nickname":"碧潭飞雪"},{"id":5,"content":"不错","user_nickname":"屿森曦光"}],"is_my_like":0,"file_type":1,"file_path":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000009006.jpg?x-oss-process=image/resize,w_200","position_name":"镇远古城","count_view":1409,"count_comment":4,"count_praise":145,"create_time":"2018-04-16 10:58:21","user_id":63099,"user_nickname":"面包去哪了","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200010174?x-oss-process=image/resize,h_50"}]}
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
             * id : 1
             * comment_list : [{"id":3,"content":"中国铁道博物馆。铁道博物馆是中国国家级专业铁路博物馆，它的前身是铁道部科学技术馆，1978年成立，2003年更名为中国铁道博物馆，主要任务是负责铁路文物、科研成果等展品的收藏、保管、陈列、展示及编辑研究工作。中国铁道博物馆有三个馆:正阳门馆（主要是历史资料、图片、文字说明和少量实物）、东郊馆（主要为各个历史时期的机车陈列）、詹天佑纪念馆（主要介绍詹天佑对中国铁路发展的贡献）。","user_nickname":"莞尔笑"},{"id":2,"content":"了解铁路发家史，学习没坏处的。","user_nickname":"茉绿"},{"id":1,"content":"在天安门广场东南角，一般人不会注意他","user_nickname":"琴断弦奈何"}]
             * is_my_like : 1
             * file_type : 1
             * file_path : https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/st/st_1000013028.jpg?x-oss-process=image/resize,w_200
             * position_name : 铁道博物馆
             * count_view : 1170
             * count_comment : 3
             * count_praise : 585
             * create_time : 2018-02-19 14:35:26
             * user_id : 60137
             * user_nickname : jason
             * user_logo : https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000074?x-oss-process=image/resize,h_50
             */

            private int id;
            private int is_my_like;
            private int file_type;
            private String file_path;
            private String position_name;
            private int count_view;
            private int count_comment;
            private int count_praise;
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

            public List<CommentListBean> getComment_list() {
                return comment_list;
            }

            public void setComment_list(List<CommentListBean> comment_list) {
                this.comment_list = comment_list;
            }

            public static class CommentListBean{
                /**
                 * id : 3
                 * content : 中国铁道博物馆。铁道博物馆是中国国家级专业铁路博物馆，它的前身是铁道部科学技术馆，1978年成立，2003年更名为中国铁道博物馆，主要任务是负责铁路文物、科研成果等展品的收藏、保管、陈列、展示及编辑研究工作。中国铁道博物馆有三个馆:正阳门馆（主要是历史资料、图片、文字说明和少量实物）、东郊馆（主要为各个历史时期的机车陈列）、詹天佑纪念馆（主要介绍詹天佑对中国铁路发展的贡献）。
                 * user_nickname : 莞尔笑
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
