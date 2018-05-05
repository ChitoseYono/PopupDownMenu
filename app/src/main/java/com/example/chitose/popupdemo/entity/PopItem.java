package com.example.chitose.popupdemo.entity;

/**
 * 文件：PopItem.java
 * 类型：实体类
 * 作用：数据存放的实体类
 * Created by Chitose on 2018/4/30.
 */

public class PopItem {
    private int id;
    private int pid;
    private String content;

    public PopItem(int id, int pid, String content) {
        this.id = id;
        this.pid = pid;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public int getPid() {
        return pid;
    }

    public String getContent() {
        return content;
    }
}
