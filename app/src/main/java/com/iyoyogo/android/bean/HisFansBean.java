package com.iyoyogo.android.bean;

import java.util.List;

/***
 * 获取 用户 粉丝人群
 */
public class HisFansBean extends BaseBean{

    /**
     * data : {"list":[{"user_id":"62830","user_nickname":"嘉雯home","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200010102.jpg?x-oss-process=image/resize,w_50","user_level":0,"partner_type":0,"score":44,"status":0,"count_yox":10,"count_yoj":0},{"user_id":"2","user_nickname":"222","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200006963.jpg?x-oss-process=image/resize,w_50","user_level":0,"partner_type":0,"score":0,"status":0,"count_yox":0,"count_yoj":0}]}
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
             * user_id : 62830
             * user_nickname : 嘉雯home
             * user_logo : https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200010102.jpg?x-oss-process=image/resize,w_50
             * user_level : 0
             * partner_type : 0
             * score : 44
             * status : 0
             * count_yox : 10
             * count_yoj : 0
             */

            private String user_id;
            private String user_nickname;
            private String user_logo;
            private int user_level;
            private int partner_type;
            private int score;
            private int status;
            private int count_yox;
            private int count_yoj;

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

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
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
        }
    }
}
