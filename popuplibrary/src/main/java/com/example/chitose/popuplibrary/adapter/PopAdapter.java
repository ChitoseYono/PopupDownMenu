package com.example.chitose.popuplibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chitose.popuplibrary.R;
import com.example.chitose.popuplibrary.entity.PopItem;

import java.util.List;

/**
 * 文件：PopAdapter.java
 * 类型：适配器
 * 作用：PopupWindow中ListView的适配器
 * Created by Chitose on 2018/5/1.
 */

public class PopAdapter extends BaseAdapter {

    private Context mContext;
    private List<PopItem> mList;
    //记录选择位置
    private int selectedPosition;

    public PopAdapter(Context mContext, List<PopItem> mList) {
        this.mContext = mContext;
        this.mList = mList;

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.popup_item, null);
            holder.tv_item = convertView.findViewById(R.id.tv_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_item.setText(mList.get(position).getContent());

        if(position==selectedPosition) {
            convertView.setActivated(true);
        }else{
            convertView.setActivated(false);
        }

        return convertView;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        this.notifyDataSetChanged();
    }

    private class ViewHolder {
        TextView tv_item;
    }
}
