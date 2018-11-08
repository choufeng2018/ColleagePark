package com.nacity.college.door.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.college.common_libs.domain.door.OpenDoorListTo;
import com.nacity.college.R;
import com.nacity.college.databinding.MyDoorItemBinding;
import com.nacity.college.base.adapter.BaseAdapter;
import com.nacity.college.base.adapter.BindingHolder;


/**
 * Created by xzz on 2017/6/24.
 **/

public class MyDoorAdapter extends BaseAdapter<OpenDoorListTo, MyDoorItemBinding> {



    public MyDoorAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public BindingHolder<MyDoorItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        MyDoorItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.my_door_item, parent, false);
        BindingHolder<MyDoorItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder<MyDoorItemBinding> holder, int position) {
        OpenDoorListTo mode = mList.get(position);
        MyDoorItemBinding binding = holder.getBinding();
        binding.setMode(mode);


    }



}


