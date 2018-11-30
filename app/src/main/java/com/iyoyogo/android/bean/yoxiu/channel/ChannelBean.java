package com.iyoyogo.android.bean.yoxiu.channel;

import com.iyoyogo.android.bean.BaseBean;

import java.util.List;

public class ChannelBean extends BaseBean {

    /**
     * data : {"list":[{"id":12,"channel":"频道12","logo":"http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/11/20/FpXN8ptrkd.png"},{"id":11,"channel":"频道11","logo":"http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/11/20/FpXN8ptrkd.png"}]}
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
             * id : 12
             * channel : 频道12
             * logo : http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/11/20/FpXN8ptrkd.png
             */

            private int id;
            private String channel;
            private String logo;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getChannel() {
                return channel;
            }

            public void setChannel(String channel) {
                this.channel = channel;
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
