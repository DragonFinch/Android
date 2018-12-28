package com.iyoyogo.android.bean;

import java.util.List;

/***
 * 用户等级
 */
public class VipCenterBean extends BaseBean {

    /**
     * data : {"user_info":{"user_id":2,"user_nickname":"222","user_logo":"","score":0},"level":[{"level":0,"score":0,"name":"佛系旅人","logo":""},{"level":1,"score":100,"name":"闲散浪人","logo":""},{"level":2,"score":200,"name":"活力行者","logo":""},{"level":3,"score":500,"name":"洒脱行者","logo":""},{"level":4,"score":2000,"name":"无拘大佬","logo":""},{"level":5,"score":10000,"name":"大旅行家","logo":""}],"pic":"http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/11/yyHcCNJH7N.jpg?x-oss-process=image/resize,w_1000"}
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
         * user_info : {"user_id":2,"user_nickname":"222","user_logo":"","score":0}
         * level : [{"level":0,"score":0,"name":"佛系旅人","logo":""},{"level":1,"score":100,"name":"闲散浪人","logo":""},{"level":2,"score":200,"name":"活力行者","logo":""},{"level":3,"score":500,"name":"洒脱行者","logo":""},{"level":4,"score":2000,"name":"无拘大佬","logo":""},{"level":5,"score":10000,"name":"大旅行家","logo":""}]
         * pic : http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/11/yyHcCNJH7N.jpg?x-oss-process=image/resize,w_1000
         */

        private UserInfoBean user_info;
        private String pic;
        private List<LevelBean> level;

        public UserInfoBean getUser_info() {
            return user_info;
        }

        public void setUser_info(UserInfoBean user_info) {
            this.user_info = user_info;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public List<LevelBean> getLevel() {
            return level;
        }

        public void setLevel(List<LevelBean> level) {
            this.level = level;
        }

        public static class UserInfoBean {
            /**
             * user_id : 2
             * user_nickname : 222
             * user_logo :
             * score : 0
             */

            private int user_id;
            private String user_nickname;
            private String user_logo;
            private int score;

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

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }
        }

        public static class LevelBean {
            /**
             * level : 0
             * score : 0
             * name : 佛系旅人
             * logo :
             */

            private int level;
            private int score;
            private String name;
            private String logo;

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }
        }
    }
}
