package com.example.chitose.popupdemo.utils;

import com.example.chitose.popupdemo.entity.PopItem;

import java.util.List;

/**
 * 文件名：Data.java
 * 作用：初始化菜单栏中的数据
 * Created by Chitose on 2018/5/3.
 */

public class Data {
    public static void initFirstData(List<PopItem> itemList){
        //一级目录List
        itemList.add(new PopItem(1, 0, "语文"));
        itemList.add(new PopItem(2, 0, "数学"));
        itemList.add(new PopItem(3, 0, "英语"));
        itemList.add(new PopItem(4, 0, "物理"));
        itemList.add(new PopItem(5, 0, "化学"));
        itemList.add(new PopItem(6, 0, "生物"));
    }

    public static void initSecondData(List<PopItem> itemList){
        //二级目录List
        itemList.add(new PopItem(1, 0, "歌手"));
        itemList.add(new PopItem(2, 1, "Aimer"));
        itemList.add(new PopItem(3, 1, "Tk"));
        itemList.add(new PopItem(4, 1, "Miku"));
        itemList.add(new PopItem(5, 1, "Rin"));
        itemList.add(new PopItem(6, 0, "游戏"));
        itemList.add(new PopItem(7, 6, "LOL"));
        itemList.add(new PopItem(8, 6, "Battlerite"));
        itemList.add(new PopItem(9, 6, "CSGO"));
        itemList.add(new PopItem(10, 0, "数学"));
        itemList.add(new PopItem(11, 10, "高等数学"));
        itemList.add(new PopItem(12, 10, "离散数学"));
        itemList.add(new PopItem(13, 0, "性别"));
        itemList.add(new PopItem(14, 13, "男性"));
        itemList.add(new PopItem(15, 13, "女性"));
        itemList.add(new PopItem(16, 0, "凑数"));
    }

    public static void initThirdData(List<PopItem> itemList){
        //三级目录List
        itemList.add(new PopItem(1, 0, "语言"));
        itemList.add(new PopItem(2, 1, "说话的"));
        itemList.add(new PopItem(3, 1, "编程的"));
        itemList.add(new PopItem(4, 1, "凑数的"));
        itemList.add(new PopItem(5, 1, "还是凑数的"));
        itemList.add(new PopItem(6, 2, "中文"));
        itemList.add(new PopItem(7, 2, "英文"));
        itemList.add(new PopItem(8, 2, "法语"));
        itemList.add(new PopItem(9, 2, "日语"));
        itemList.add(new PopItem(10, 3, "C语言"));
        itemList.add(new PopItem(11, 3, "Java"));
        itemList.add(new PopItem(12, 3, "Python"));
        itemList.add(new PopItem(13, 0, "性别"));
        itemList.add(new PopItem(14, 13, "男性"));
        itemList.add(new PopItem(15, 13, "女性"));
        itemList.add(new PopItem(16, 0, "课程"));
        itemList.add(new PopItem(17, 16, "公选课"));
        itemList.add(new PopItem(18, 16, "专业课"));
        itemList.add(new PopItem(19, 17, "大英"));
        itemList.add(new PopItem(20, 17, "数学"));
        itemList.add(new PopItem(21, 18, "数据库"));
        itemList.add(new PopItem(22, 18, "数据结构"));
        itemList.add(new PopItem(23, 18, "操作系统"));
        itemList.add(new PopItem(24, 0, "凑数"));
    }

}
