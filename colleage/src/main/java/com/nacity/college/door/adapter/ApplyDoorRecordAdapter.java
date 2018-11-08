package com.nacity.college.door.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.college.common_libs.domain.door.ApplyDoorRecordTo;
import com.nacity.college.R;
import com.nacity.college.databinding.MyDoorApplyRecordItemBinding;
import com.nacity.college.base.adapter.BaseAdapter;
import com.nacity.college.base.adapter.BindingHolder;
import com.nacity.college.base.utils.DateUtil;


/**
 * Created by xzz on 2017/6/24.
 **/

public class ApplyDoorRecordAdapter extends BaseAdapter<ApplyDoorRecordTo, MyDoorApplyRecordItemBinding> {



    public ApplyDoorRecordAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public BindingHolder<MyDoorApplyRecordItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        MyDoorApplyRecordItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.my_door_apply_record_item, parent, false);
        BindingHolder<MyDoorApplyRecordItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder<MyDoorApplyRecordItemBinding> holder, int position) {
        ApplyDoorRecordTo mode = mList.get(position);
        MyDoorApplyRecordItemBinding binding = holder.getBinding();
        binding.setMode(mode);
        binding.applyDate.setText(DateUtil.getDateString(mode.getCreateTime(), DateUtil.mDateTimeFormatString));
        if (mode.getApplyStatus() == 0) {
            binding.applyStatus.setTextColor(Color.parseColor("#ff4c41"));

           binding.applyContent.setText(mode.getDoorNameConcat());
        } else if (mode.getApplyStatus() == 1) {

            binding.applyStatus.setTextColor(Color.parseColor("#82bbfa"));
            binding.applyContent.setText(mode.getDoorNamePassConcat());
        } else if (mode.getApplyStatus() == 2) {

            binding.applyStatus.setTextColor(Color.parseColor("#999999"));
            binding.applyContent.setText(mode.getDoorNameConcat());
        }


    }



}


