package com.example.chitose.popuplibrary.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.chitose.popuplibrary.adapter.PopAdapter;
import com.example.chitose.popuplibrary.entity.PopItem;
import com.example.chitose.popuplibrary.utils.PopupUtils;

import java.util.ArrayList;
import java.util.List;

import static com.example.chitose.popuplibrary.utils.StaticUtils.*;
/**
 * 文件名：PopupDownMenu.java
 * 类型：PopupWindow的包装类
 * 作用：包装了PopupWindow，转换数据类型进以加载进列表中
 * Created by Chitose on 2018/4/30.
 */

public class PopupDownMenu {

    private Context mContext;

    //popupWindow可以在activity中被调用
    public PopupWindow popupWindow;

    //results[0]~results[2]都是用来存放点击结果，results[3]是作为“结果正常”的标识
    public String[] results = new String[4];

    //初始化两个存放PopItem的List
    private List<List<PopItem>> popList = new ArrayList<>();

    //初始化两个用来改变第二第三列数据的mList
    private List<PopItem> mList = new ArrayList<>();
    private List<PopItem> mList2 = new ArrayList<>();

    //初始化三个ListView
    private ListView firstListView;
    private ListView secondListView;
    private ListView thirdListView;

    //初始化三个适配器
    private PopAdapter firstAdapter;
    private PopAdapter secondAdapter;
    private PopAdapter thirdAdapter;

    List<PopItem> itemList;


    //仅一级时的下拉列表菜单栏
    public PopupDownMenu(Context mContext, final List<PopItem> itemList, int width, int height, View view, Drawable drawable, ListView ... listViews) {

        this.mContext = mContext;
        this.itemList = itemList;

        if (listViews.length==0) {
            Toast.makeText(mContext, "数据不存在，请检查数据", Toast.LENGTH_SHORT).show();
            return;
        }
        if(listViews.length>=1)
        this.firstListView = listViews[0];
        if(listViews.length>=2)
        this.secondListView = listViews[1];
        if(listViews.length>=3)
        this.thirdListView = listViews[2];

        //为popList中的数据初始化
        PopupUtils.popListInit(popList, itemList);

        //初始化状态（若第二列存在元素就显示→显示后若第三行有也同理显示）
        try {
            List<PopItem> temp1 = popList.get(popList.get(0).get(0).getId());
            if (temp1 != null) {
                mList.addAll(temp1);
                List<PopItem> temp2 = popList.get(temp1.get(0).getId());
                if (temp2 != null) {
                    mList2.addAll(temp2);
                }
            }
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }

        //辨别菜单列表的类型是一级，二级还是三级
        switch (PopupUtils.popSort(itemList)) {
            case ONLY_ONE_POP:
                initOnePop();
                break;
            case DOUBLE_POP:
                initDoublePop();
                break;
            case TRIPLE_POP:
                initTriplePop();
                break;
            default:
                Log.d("ERROR:","大于三级的列表暂未开发，请检查数据");
                break;
        }

        //初始化PopupWindow
        popupWindow = new PopupWindow(view, width, height, true);
        popupWindow.setBackgroundDrawable(drawable);
    }

    /*一级菜单*/
    private void initOnePop() {

        //Listview与适配器的初始化
        firstAdapter = new PopAdapter(mContext, popList.get(0));
        firstListView.setAdapter(firstAdapter);

        // 设置ListView点击事件监听
        firstListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                firstAdapter.setSelectedPosition(position);
                // 一级菜单的选择可以直接通过itemList设置结果放入result[0]中
//                results[0] = popList.get(0).get(position).getContent();
                results[0] = itemList.get(position).getContent();
                results[3] = FINISHED_FLAG;

                // 选择完后关闭popup窗口
                popupWindow.dismiss();
            }
        });
    }

    /*二级菜单*/
    private void initDoublePop() {

        //适配器的初始化
        firstAdapter = new PopAdapter(mContext, popList.get(0));
        firstListView.setAdapter(firstAdapter);
        secondAdapter = new PopAdapter(mContext, mList);
        secondListView.setAdapter(secondAdapter);
        firstListView.performItemClick(firstListView,0,firstListView.getItemIdAtPosition(0));
        //点击事件
        firstListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                firstAdapter.setSelectedPosition(position);
                //选择了第一列元素相应的数据放到result[0]中
//                results[0] = itemList.get(position).getContent();
                results[0] = popList.get(0).get(position).getContent();

                //尝试获取当前第一列选项的二级数据
                List<PopItem> list2 = null;
                try {
                    list2 = popList.get(popList.get(0).get(position).getId());
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

                //如果没有二级数据，则直接跳转
                if (list2 == null || list2.size() == 0) {
                    mList.clear();
                    results[1] = null;                  //防止缓存上一次选项带来异常
                    results[3] = FINISHED_FLAG;
                    popupWindow.dismiss();
                }
                //如果有则替换并notify
                else {
                    mList.clear();
                    mList.addAll(list2);
                    secondAdapter.notifyDataSetChanged();
                    secondAdapter.setSelectedPosition(0);
                }
            }
        });
        secondListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                results[0] = itemList.get(mList.get(position).getPid()-1).getContent();
                secondAdapter.setSelectedPosition(position);
                //选择了第二列元素相应的数据放到result[1]中
                results[1] = mList.get(position).getContent();
                results[3] = FINISHED_FLAG;
                popupWindow.dismiss();
            }
        });
    }

    /*三级菜单*/
    private void initTriplePop() {

        //设置Adapter
        firstAdapter = new PopAdapter(mContext, popList.get(0));
        firstListView.setAdapter(firstAdapter);
        secondAdapter = new PopAdapter(mContext, mList);
        secondListView.setAdapter(secondAdapter);
        thirdAdapter = new PopAdapter(mContext, mList2);
        thirdListView.setAdapter(thirdAdapter);

        //点击事件
        firstListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                firstAdapter.setSelectedPosition(position);
                //选择了第一列元素相应的数据放到result[0]中
                results[0] = popList.get(0).get(position).getContent();

                //尝试获取当前第一列选项的二级数据
                List<PopItem> list2 = null;
                try {
                    list2 = popList.get(popList.get(0).get(position).getId());
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

                //如果没有二级数据，则直接跳转
                if (list2 == null || list2.size() == 0) {
                    mList.clear();
                    mList2.clear();
                    results[1] = null;                            //防止缓存上一次选项带来异常
                    results[2] = null;                            //防止缓存上一次选项带来异常
                    results[3] = FINISHED_FLAG;
                    popupWindow.dismiss();
                }
                //如果有则替换并notify
                else {
                    //点击第一项的初始状态
                    mList.clear();
                    mList.addAll(list2);
                    mList2.clear();
                    if (popList.get(list2.get(0).getId()) != null) {
                        mList2.addAll(popList.get(list2.get(0).getId()));
                    }
                    secondAdapter.notifyDataSetChanged();
                    thirdAdapter.notifyDataSetChanged();
                    secondAdapter.setSelectedPosition(0);
                    thirdAdapter.setSelectedPosition(0);
                }
            }
        });
        secondListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                secondAdapter.setSelectedPosition(position);

                results[0] = itemList.get(mList.get(position).getPid()-1).getContent();

                //选择了第二列元素相应的数据放到result[1]中
                results[1] = mList.get(position).getContent();

                //尝试获取当前第二列选项的三级数据
                List<PopItem> list3 = null;
                try {
                    list3 = popList.get(mList.get(position).getId());
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

                //如果没有三级数据，则直接跳转
                if (list3 == null || list3.size() == 0) {
                    mList2.clear();
                    results[2] = null;                      //防止缓存上一次选项带来异常
                    results[3] = FINISHED_FLAG;
                    popupWindow.dismiss();
                }
                //如果有则替换并notify
                else {
                    mList2.clear();
                    mList2.addAll(list3);
                    thirdAdapter.notifyDataSetChanged();
                    thirdAdapter.setSelectedPosition(0);
                }
            }
        });

        thirdListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                thirdAdapter.setSelectedPosition(position);

                results[1] = itemList.get(mList2.get(position).getPid()-1).getContent();

                results[0] = itemList.get(itemList.get(mList2.get(position).getPid()-1).getPid()-1).getContent();

                //选择了第三列元素相应的数据放到result[2]中
                results[2] = mList2.get(position).getContent();
                results[3] = FINISHED_FLAG;
                popupWindow.dismiss();
            }
        });
    }


}
