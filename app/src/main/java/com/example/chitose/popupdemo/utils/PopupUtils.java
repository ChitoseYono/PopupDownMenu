package com.example.chitose.popupdemo.utils;

import android.util.Log;

import com.example.chitose.popupdemo.entity.PopItem;

import java.util.ArrayList;
import java.util.List;

import static com.example.chitose.popupdemo.utils.StaticUtils.*;

/**
 * 文件名：PopupHelpep.java
 * 类型：工具类
 * 作用：提供区分菜单类型与转换数据的帮助方法
 * Created by Chitose on 2018/4/30.
 */

public class PopupUtils {

    public PopupUtils() {}

    public static int popSort(List<PopItem> itemList){

        boolean doubleFlag = false;

        for (PopItem item: itemList) {
            //是否只有一级菜单栏
            if(item.getPid()==0){}
            else if(itemList.get(item.getPid()-1).getPid()==0){
                doubleFlag = true;
            }//三级菜单栏
            else if(itemList.get(itemList.get(item.getPid()-1).getPid()-1).getPid()==0){
                return TRIPLE_POP;
            }else{
                return MORE_THAN_TRIPLE;
            }
        }
        //二级菜单栏
        if(doubleFlag){
            return DOUBLE_POP;
        }
        //一级菜单栏
        else{
            return ONLY_ONE_POP;
        }

    }

/*    public static int popSort(List<List<PopItem>> popList) {

        boolean doubleFlag = false;

        for(int i = 1; i<popList.size();i++){
            if(popList.get(i).size()!=0){
                for(PopItem item: popList.get(i)){
                    if(popList.get(item.getId()).size()!=0){
                        return TRIPLE_POP;
                    }
                    else{
                        doubleFlag = true;
                    }
                }
            }
        }
        //二级菜单栏
        if(doubleFlag){
            return DOUBLE_POP;
        }
        //一级菜单栏
        else{
            return ONLY_ONE_POP;
        }
    }*/

    public static void popListInit(List<List<PopItem>> popList, List<PopItem> itemList){
        //secondList.get(0)只装载第一列的元素，那么能方便以后的get可以直接get对应PopItem的id作为索引
        popList.add(new ArrayList<PopItem>());
        //mList用id来将全部数据的形式存储
        List<List<Integer>> mList = new ArrayList<>();

/*
 *       遍历一次itemList，每遍历一个popItem元素，都给mList.add(null)
 *       然后分情况:
 *       ①Pid为0的元素，不做任何操作
 *       ②Pid不为0的元素，给mList.get(Pid).add(id)
 *
 *       这样得到的mList上的第i个元素，
 *       如果mList.get(i)!=null,说明他是有子节点的结点，若为null则是没有子节点的结点
 *       （详情看图）
 */

        for(PopItem item :itemList){
            mList.add(new ArrayList<Integer>());
            if(item.getPid()>0){
                mList.get(item.getPid()-1).add(item.getId());
            }
        }

        for(int i = 0 ; i < mList.size() ; i++){

            popList.add(new ArrayList<PopItem>());
            PopItem item = itemList.get(i);

            //将各第一列的元素放置到popList.get(0)中去
            if(item.getPid()==0){
                popList.get(0).add(item);
            }
            //因为他是有子节点的结点，把该元素的结点全部放进相应的popList.get(i)中去
            List<Integer> list = mList.get(i);
            if(list!=null) {
                for (int j = 0; j < list.size(); j++) {
                    popList.get(item.getId()).add(itemList.get(list.get(j)-1));
                }
            }

        }
    }


}
