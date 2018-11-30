package com.iyoyogo.android.bean.yoxiu.topic;

import com.iyoyogo.android.bean.BaseBean;

import java.util.List;

public class HotTopicBean extends BaseBean {

    /**
     * data : {"list":[{"id":7,"topic":"话题7"},{"id":6,"topic":"话题6"}]}
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
             * id : 7
             * topic : 话题7
             */

            private int id;
            private String topic;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTopic() {
                return topic;
            }

            public void setTopic(String topic) {
                this.topic = topic;
            }
        }
    }
}
