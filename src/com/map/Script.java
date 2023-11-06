package com.map;

import java.util.Arrays;

public class Script {
    int xh;
    String code;
    String name;
    int[] actlist;
    int[] paralist;

    public void initialize()
    {
        this.xh=0;
        this.code="";
        this.name="";
        this.actlist=new int[50];
        this.paralist=new int[50];
    }

    public int getXh() {
        return xh;
    }

    public void setXh(int xh) {
        this.xh = xh;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getActlist() {
        return actlist;
    }

    public void setActlist(int[] actlist) {
        this.actlist = actlist;
    }

    public int[] getParalist() {
        return paralist;
    }

    public void setParalist(int[] paralist) {
        this.paralist = paralist;
    }

    @Override
    public String toString() {
        return "Script{" +
                "xh=" + xh +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", actlist=" + Arrays.toString(actlist) +
                ", paralist=" + Arrays.toString(paralist) +
                '}';
    }
}
