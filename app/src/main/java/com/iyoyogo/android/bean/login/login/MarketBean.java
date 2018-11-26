package com.iyoyogo.android.bean.login.login;

import com.iyoyogo.android.bean.BaseBean;

public class MarketBean extends BaseBean {

    /**
     * data : {"info":{"id":2,"logo":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/11/14/TQdzGCZN3n.jpg","content":"新人注册送会员,大家快来注册吧!!!"}}
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
         * info : {"id":2,"logo":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/11/14/TQdzGCZN3n.jpg","content":"新人注册送会员,大家快来注册吧!!!"}
         */

        private InfoBean info;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public static class InfoBean {
            /**
             * id : 2
             * logo : http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/11/14/TQdzGCZN3n.jpg
             * content : 新人注册送会员,大家快来注册吧!!!
             */

            private int id;
            private String logo;
            private String content;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
