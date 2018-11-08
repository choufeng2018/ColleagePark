package com.nacity.college.door.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.college.common_libs.domain.door.CanApplyDoorTo;
import com.nacity.college.R;
import com.nacity.college.databinding.CanApplyDoorItemBinding;
import com.nacity.college.base.adapter.BaseAdapter;
import com.nacity.college.base.adapter.BindingHolder;


/**
 * Created by xzz on 2017/6/24.
 **/

public class ApplyDoorAdapter extends BaseAdapter<CanApplyDoorTo, CanApplyDoorItemBinding> {


    public ApplyDoorAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public BindingHolder<CanApplyDoorItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        CanApplyDoorItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.can_apply_door_item, parent, false);
        BindingHolder<CanApplyDoorItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder<CanApplyDoorItemBinding> holder, int position) {
        CanApplyDoorTo mode = mList.get(position);
        CanApplyDoorItemBinding binding = holder.getBinding();


        binding.setMode(mode);


    }


}


