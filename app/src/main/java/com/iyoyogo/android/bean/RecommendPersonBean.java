package com.iyoyogo.android.bean;

import java.util.List;

/**
 * 创建时间：2018/6/23
 * 描述：
 */
public class RecommendPersonBean extends BaseBean {
    /**
     * {
     * user_id: 100021,
     * user_nick: "呵呵",
     * user_pic1: "https://thirdqq.qlogo.cn/qqapp/1106908081/5B89E52AE6BB477ED671496AB9CC7442/100",
     * nu: 100021,
     * fm_count: 3,
     * st_count: 1,
     * up_count: "1",
     * list: [
     * {
     * fmst_id: "fm476501157443932160",
     * cover_addr: "http://one.egodvpt.com:8099/yoyogo/about-5.png",
     * tp: "fm",
     * type: ""
     * },
     * {
     * fmst_id: "fm476501167522844672",
     * cover_addr: "http://one.egodvpt.com:8099/yoyogo/about-5.png",
     * tp: "fm",
     * type: ""
     * },
     * {
     * fmst_id: "fm477957863180996608",
     * cover_addr: "",
     * tp: "fm",
     * type: ""
     * },
     * {
     * fmst_id: "st477892258843201536",
     * cover_addr: "aliyun.me",
     * tp: "st",
     * type: "P"
     * }
     * ]
     * },
     */
    int user_id;
    String user_nick;
    String user_pic1;
    String att_type;
    List<PersonTrends> list;
    int fm_count;
    int st_count;
    int up_count;

    int att_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_nick() {
        return user_nick;
    }

    public void setUser_nick(String user_nick) {
        this.user_nick = user_nick;
    }

    public String getUser_pic1() {
        return user_pic1;
    }

    public void setUser_pic1(String user_pic1) {
        this.user_pic1 = user_pic1;
    }

    public List<PersonTrends> getList() {
        return list;
    }

    public void setList(List<PersonTrends> list) {
        this.list = list;
    }

    public int getFm_count() {
        return fm_count;
    }

    public void setFm_count(int fm_count) {
        this.fm_count = fm_count;
    }

    public int getSt_count() {
        return st_count;
    }

    public void setSt_count(int st_count) {
        this.st_count = st_count;
    }

    public int getUp_count() {
        return up_count;
    }

    public void setUp_count(int up_count) {
        this.up_count = up_count;
    }

    public String getAtt_type() {
        return att_type;
    }

    public int getAtt_id() {
        return att_id;
    }

    public void setAtt_type(String att_type) {
        this.att_type = att_type;
    }

    public class PersonTrends {
        String fmst_id;
        String cover_addr;
        String tp;
        String type;

        public String getFmst_id() {
            return fmst_id;
        }

        public void setFmst_id(String fmst_id) {
            this.fmst_id = fmst_id;
        }

        public String getCover_addr() {
            return cover_addr;
        }

        public void setCover_addr(String cover_addr) {
            this.cover_addr = cover_addr;
        }

        public String getTp() {
            return tp;
        }

        public void setTp(String tp) {
            this.tp = tp;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
