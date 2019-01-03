package com.iyoyogo.android.bean.mine;

import com.iyoyogo.android.bean.BaseBean;

import java.util.List;

public class GetUserInfoBean extends BaseBean {

    /**
     * data : {"user_id":80002,"user_phone":"15611695821","user_nickname":"用户4c4b891","user_logo":"http://thirdwx.qlogo.cn/mmopen/vi_32/oAlN2OtqiakH1u6SPAmw51QgLdFU7EpSeBvcBTkgU9olk0gRfoKG0lmReSlibwx1KDCTrtXxPIJibOfJEDdlSw74Q/132","user_back":"","user_sex":"女","user_birthday":"2000-10-10","user_city":"阿里地区","user_level":4,"interest_list":[{"id":4,"interest":"美妆&护肤","logo":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/11/12/HswYaBtKKQ.png"}]}
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
         * user_id : 80002
         * user_phone : 15611695821
         * user_nickname : 用户4c4b891
         * user_logo : http://thirdwx.qlogo.cn/mmopen/vi_32/oAlN2OtqiakH1u6SPAmw51QgLdFU7EpSeBvcBTkgU9olk0gRfoKG0lmReSlibwx1KDCTrtXxPIJibOfJEDdlSw74Q/132
         * user_back :
         * user_sex : 女
         * user_birthday : 2000-10-10
         * user_city : 阿里地区
         * user_level : 4
         * interest_list : [{"id":4,"interest":"美妆&护肤","logo":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/11/12/HswYaBtKKQ.png"}]
         */

        private int user_id;
        private String user_phone;
        private String user_nickname;
        private String user_logo;
        private String user_back;
        private String user_sex;
        private String user_birthday;
        private String user_city;
        private int user_level;
        private List<InterestListBean> interest_list;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
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

        public String getUser_back() {
            return user_back;
        }

        public void setUser_back(String user_back) {
            this.user_back = user_back;
        }

        public String getUser_sex() {
            return user_sex;
        }

        public void setUser_sex(String user_sex) {
            this.user_sex = user_sex;
        }

        public String getUser_birthday() {
            return user_birthday;
        }

        public void setUser_birthday(String user_birthday) {
            this.user_birthday = user_birthday;
        }

        public String getUser_city() {
            return user_city;
        }

        public void setUser_city(String user_city) {
            this.user_city = user_city;
        }

        public int getUser_level() {
            return user_level;
        }

        public void setUser_level(int user_level) {
            this.user_level = user_level;
        }

        public List<InterestListBean> getInterest_list() {
            return interest_list;
        }

        public void setInterest_list(List<InterestListBean> interest_list) {
            this.interest_list = interest_list;
        }

        public static class InterestListBean {
            /**
             * id : 4
             * interest : 美妆&护肤
             * logo : http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/11/12/HswYaBtKKQ.png
             */

            private int id;
            private String interest;
            private String logo;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getInterest() {
                return interest;
            }

            public void setInterest(String interest) {
                this.interest = interest;
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
