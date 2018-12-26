package com.iyoyogo.android.bean.mine.message;

import com.iyoyogo.android.bean.BaseBean;

public class ReadMessage extends BaseBean {

    /**
     * data : {"scalar":true}
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
         * scalar : true
         */

        private boolean scalar;

        public boolean isScalar() {
            return scalar;
        }

        public void setScalar(boolean scalar) {
            this.scalar = scalar;
        }
    }
}
