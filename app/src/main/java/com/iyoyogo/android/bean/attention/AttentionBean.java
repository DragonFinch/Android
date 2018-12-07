package com.iyoyogo.android.bean.attention;

import com.iyoyogo.android.bean.BaseBean;

public class AttentionBean extends BaseBean {

    /**
     * data : {"id":"127"}
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
         * id : 127
         */

        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
