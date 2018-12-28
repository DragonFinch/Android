package com.iyoyogo.android.bean.collection;

import com.iyoyogo.android.bean.BaseBean;

import java.util.List;

/***
 * 推荐 给我 要关注的人
 */
public class CommendAttentionBean extends BaseBean {

    /**
     * data : {"list":[{"user_id":"65699","user_nickname":"良","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200002241.jpg?x-oss-process=image/resize,w_50","user_level":1,"partner_type":0,"count_yox":0,"count_yoj":1,"list_4":[{"yo_id":4007,"yo_type":2,"file_type":1,"file_path":"http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/19/KxKw3CNJCM.jpg?x-oss-process=image/resize,w_400"}]}]}
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
             * user_id : 65699
             * user_nickname : 良
             * user_logo : https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200002241.jpg?x-oss-process=image/resize,w_50
             * user_level : 1
             * partner_type : 0
             * count_yox : 0
             * count_yoj : 1
             * list_4 : [{"yo_id":4007,"yo_type":2,"file_type":1,"file_path":"http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/19/KxKw3CNJCM.jpg?x-oss-process=image/resize,w_400"}]
             */

            private String user_id;
            private String user_nickname;
            private String user_logo;
            private int user_level;
            private int partner_type;
            private int count_yox;
            private int count_yoj;
            private List<List4Bean> list_4;

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

            public int getUser_level() {
                return user_level;
            }

            public void setUser_level(int user_level) {
                this.user_level = user_level;
            }

            public int getPartner_type() {
                return partner_type;
            }

            public void setPartner_type(int partner_type) {
                this.partner_type = partner_type;
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

            public List<List4Bean> getList_4() {
                return list_4;
            }

            public void setList_4(List<List4Bean> list_4) {
                this.list_4 = list_4;
            }

            public static class List4Bean {
                /**
                 * yo_id : 4007
                 * yo_type : 2
                 * file_type : 1
                 * file_path : http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/19/KxKw3CNJCM.jpg?x-oss-process=image/resize,w_400
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
