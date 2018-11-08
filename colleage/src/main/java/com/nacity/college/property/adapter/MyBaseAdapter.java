package com.nacity.college.property.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by usb on 2017/11/2.
 */

public abstract class MyBaseAdapter <T> extends BaseAdapter {

    public List<T> dataList;
    public Context ctx;

    public MyBaseAdapter(List<T> dataList, Context ctx) {
        this.dataList = dataList;
        this.ctx = ctx;
    }

    public MyBaseAdapter(List<T> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
