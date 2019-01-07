package com.iyoyogo.android.bean;

/**
 * @author zhuhui
 * @date 2019/1/7
 * @description
 */
public class PublishSucessBean extends BaseBean {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private String yo_id;

        public String getYo_id() {
            return yo_id;
        }

        public void setYo_id(String yo_id) {
            this.yo_id = yo_id;
        }
    }
}
