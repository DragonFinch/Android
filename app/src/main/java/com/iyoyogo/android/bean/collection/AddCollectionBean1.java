package com.iyoyogo.android.bean.collection;

import com.iyoyogo.android.bean.BaseBean;

import java.util.List;

/***
 * 添加关注 — 关注我的
 *
 * 获取我粉丝中 我没关注的 (是我的粉丝，我却不是他的粉丝)
 */
public class AddCollectionBean1 extends BaseBean {
    /**
     * data : {"list":[{"user_id":"60003","user_nickname":"明天过后","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200006961.jpg?x-oss-process=image/resize,w_50","user_level":0,"partner_type":0,"score":39,"status":0,"count_yox":5,"count_yoj":0},{"user_id":"60002","user_nickname":"雨后的小风筝","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000002.jpg?x-oss-process=image/resize,w_50","user_level":0,"partner_type":0,"score":16,"status":0,"count_yox":0,"count_yoj":1},{"user_id":"60001","user_nickname":"荧念一生","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000001.jpg?x-oss-process=image/resize,w_50","user_level":0,"partner_type":0,"score":51,"status":0,"count_yox":224,"count_yoj":50},{"user_id":"80004","user_nickname":"月帅","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200006963.jpg?x-oss-process=image/resize,w_50","user_level":0,"partner_type":0,"score":0,"status":0,"count_yox":1,"count_yoj":0}]}
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
             * user_id : 60003
             * user_nickname : 明天过后
             * user_logo : https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200006961.jpg?x-oss-process=image/resize,w_50
             * user_level : 0
             * partner_type : 0
             * score : 39
             * status : 0
             * count_yox : 5
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

    /**
     * code : 200
     * msg :
     * data : {"list":[{"user_id":"60003","user_nickname":"明天过后","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200006961.jpg?x-oss-process=image/resize,w_50","user_level":0,"partner_type":0,"score":39,"status":0,"count_yox":5,"count_yoj":0},{"user_id":"60002","user_nickname":"雨后的小风筝","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000002.jpg?x-oss-process=image/resize,w_50","user_level":0,"partner_type":0,"score":16,"status":0,"count_yox":0,"count_yoj":1},{"user_id":"60001","user_nickname":"荧念一生","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200000001.jpg?x-oss-process=image/resize,w_50","user_level":0,"partner_type":0,"score":51,"status":0,"count_yox":224,"count_yoj":50},{"user_id":"80004","user_nickname":"月帅","user_logo":"https://yoyogo-oss.oss-cn-beijing.aliyuncs.com/user/Pr_200006963.jpg?x-oss-process=image/resize,w_50","user_level":0,"partner_type":0,"score":0,"status":0,"count_yox":1,"count_yoj":0}]}
     */


}
