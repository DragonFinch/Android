package com.iyoyogo.android.bean.yoxiu.topic;

import com.iyoyogo.android.bean.BaseBean;

public class CreateTopicBean extends BaseBean {

    /**
     * data : {"topic_id":"7"}
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
         * topic_id : 7
         */

        private String topic_id;

        public String getTopic_id() {
            return topic_id;
        }

        public void setTopic_id(String topic_id) {
            this.topic_id = topic_id;
        }
    }
}
