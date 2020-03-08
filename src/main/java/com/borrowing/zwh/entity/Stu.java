package com.borrowing.zwh.entity;

import java.io.Serializable;

public class Stu implements Serializable {
    private Integer id;

    private String name;

    private String school;

    private Integer pay;

    private String ct;

    private String ut;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school == null ? null : school.trim();
    }

    public Integer getPay() {
        return pay;
    }

    public void setPay(Integer pay) {
        this.pay = pay;
    }

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct == null ? null : ct.trim();
    }

    public String getUt() {
        return ut;
    }

    public void setUt(String ut) {
        this.ut = ut == null ? null : ut.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", school=").append(school);
        sb.append(", pay=").append(pay);
        sb.append(", ct=").append(ct);
        sb.append(", ut=").append(ut);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}