package com.iyoyogo.android.bean.home;

import com.iyoyogo.android.bean.BaseBean;

public class VersionBean extends BaseBean {

    /**
     * data : {"version_id":"5","type":"ios","version":"1.0.1","url":"http://127.0.0.1/FCMS/public/index.php/api/fcuser/banner","content":"1","force":"1","update_time":"2018-12-26 18:06:35"}
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
         * version_id : 5
         * type : ios
         * version : 1.0.1
         * url : http://127.0.0.1/FCMS/public/index.php/api/fcuser/banner
         * content : 1
         * force : 1
         * update_time : 2018-12-26 18:06:35
         */

        private String version_id;
        private String type;
        private String version;
        private String url;
        private String content;
        private String force;
        private String update_time;

        public String getVersion_id() {
            return version_id;
        }

        public void setVersion_id(String version_id) {
            this.version_id = version_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getForce() {
            return force;
        }

        public void setForce(String force) {
            this.force = force;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }
    }
}
