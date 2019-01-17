package com.iyoyogo.android.bean;

/**
 * @author zhuhui
 * @date 2019/1/17
 * @description
 */
public class LikeBean extends BaseBean{


    /**
     * data : {"status":0}
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
         * status : 0
         */

        private int status;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
