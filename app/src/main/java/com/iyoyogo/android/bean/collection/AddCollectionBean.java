package com.iyoyogo.android.bean.collection;

import com.iyoyogo.android.bean.BaseBean;

public class AddCollectionBean extends BaseBean {

    /**
     * data : {"id":"4"}
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
         * id : 4
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
