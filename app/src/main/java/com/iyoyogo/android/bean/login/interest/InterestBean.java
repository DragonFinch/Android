package com.iyoyogo.android.bean.login.interest;

import com.iyoyogo.android.bean.BaseBean;

import java.util.List;

public class InterestBean extends BaseBean {

    /**
     * data : {"list":[{"id":9,"interest":"1","logo":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/30/3N4MGC3GQP.jpg"},{"id":8,"interest":"1","logo":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/30/HRCGpJkQe8.png"},{"id":7,"interest":"a","logo":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/30/BNRmtzB5GJ.png"},{"id":6,"interest":"111","logo":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/30/43KCddKF8b.jpg"},{"id":5,"interest":"a","logo":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/30/4xHHaDjaHi.jpg"},{"id":4,"interest":"ceshi","logo":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/30/tdfwbWAZDG.jpg"},{"id":3,"interest":"ceshi","logo":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/30/AS4xtijhTz.jpg"},{"id":2,"interest":"ceshi","logo":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/30/tdfwbWAZDG.jpg"},{"id":1,"interest":"测试1","logo":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/30/t8DPAj7teM.jpg"}]}
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
             * id : 9
             * interest : 1
             * logo : http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/30/3N4MGC3GQP.jpg
             */

            private int id;
            private String interest;
            private String logo;
            private boolean isFlag;

            public boolean isFlag() {
                return isFlag;
            }

            public void setFlag(boolean flag) {
                isFlag = flag;
            }


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
