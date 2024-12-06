package com.itheima.pojo;

//管理员类
public class Administer {
    private int id;
    private String adname;
    private String adpassword;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdname() {
        return adname;
    }

    public void setAdname(String adname) {
        this.adname = adname;
    }

    public String getAdpassword() {
        return adpassword;
    }

    public void setAdpassword(String adpassword) {
        this.adpassword = adpassword;
    }

    @Override
    public String toString() {
        return "Administer{" +
                "id=" + id +
                ", adname='" + adname + '\'' +
                ", adpassword='" + adpassword + '\'' +
                '}';
    }
}
