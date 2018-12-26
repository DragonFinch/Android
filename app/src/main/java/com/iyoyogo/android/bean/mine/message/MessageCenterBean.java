package com.iyoyogo.android.bean.mine.message;

import com.iyoyogo.android.bean.BaseBean;

public class MessageCenterBean extends BaseBean {

    /**
     * data : {"type1":"1","type2":"0","type3":"3","type4":"1"}
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
         * type1 : 1
         * type2 : 0
         * type3 : 3
         * type4 : 1
         */

        private String type1;
        private String type2;
        private String type3;
        private String type4;

        public String getType1() {
            return type1;
        }

        public void setType1(String type1) {
            this.type1 = type1;
        }

        public String getType2() {
            return type2;
        }

        public void setType2(String type2) {
            this.type2 = type2;
        }

        public String getType3() {
            return type3;
        }

        public void setType3(String type3) {
            this.type3 = type3;
        }

        public String getType4() {
            return type4;
        }

        public void setType4(String type4) {
            this.type4 = type4;
        }
    }
}
