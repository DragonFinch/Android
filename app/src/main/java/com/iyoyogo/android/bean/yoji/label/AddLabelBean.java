package com.iyoyogo.android.bean.yoji.label;

import com.iyoyogo.android.bean.BaseBean;

public class AddLabelBean extends BaseBean {

    /**
     * data : {"label_id":2}
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
         * label_id : 2
         */

        private int label_id;

        public int getLabel_id() {
            return label_id;
        }

        public void setLabel_id(int label_id) {
            this.label_id = label_id;
        }
    }
}
