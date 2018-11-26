package com.iyoyogo.android.bean.yoxiu;

import com.iyoyogo.android.bean.BaseBean;

import java.util.List;

public class TypeBean extends BaseBean {

    /**
     * data : {"list":[{"id":2,"name":"博物馆","logo":"http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/11/16/wCPj5Z85ES.png"},{"id":9,"name":"a","logo":"http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/11/22/fYh8hnrQEm.png"},{"id":1,"name":"景点","logo":"http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/11/16/CBEeSw8Y5d.png"},{"id":10,"name":"b","logo":"http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/11/22/AD4hPrw5pS.png"}]}
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
             * id : 2
             * name : 博物馆
             * logo : http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/11/16/wCPj5Z85ES.png
             */

            private int id;
            private String name;
            private String logo;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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
