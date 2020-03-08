package com.borrowing.zwh.entity;

import java.io.Serializable;

public class Borrowing implements Serializable {
    private Integer id;

    private Integer stuid;

    private Integer bookid;

    private String rd;

    private String status;

    private String ct;

    private String ut;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStuid() {
        return stuid;
    }

    public void setStuid(Integer stuid) {
        this.stuid = stuid;
    }

    public Integer getBookid() {
        return bookid;
    }

    public void setBookid(Integer bookid) {
        this.bookid = bookid;
    }

    public String getRd() {
        return rd;
    }

    public void setRd(String rd) {
        this.rd = rd == null ? null : rd.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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
        sb.append(", stuid=").append(stuid);
        sb.append(", bookid=").append(bookid);
        sb.append(", rd=").append(rd);
        sb.append(", status=").append(status);
        sb.append(", ct=").append(ct);
        sb.append(", ut=").append(ut);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}