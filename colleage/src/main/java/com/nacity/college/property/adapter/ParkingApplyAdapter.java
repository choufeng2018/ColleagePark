package com.nacity.college.property.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.college.common_libs.domain.property.ParkingApplyListTo;
import com.nacity.college.R;
import com.nacity.college.databinding.ParkingApplyRecordItemBinding;
import com.nacity.college.base.adapter.BaseAdapter;
import com.nacity.college.base.adapter.BindingHolder;

/**
 *  Created by usb on 2017/10/23.
 */

public class ParkingApplyAdapter extends BaseAdapter<ParkingApplyListTo, ParkingApplyRecordItemBinding> {


    public ParkingApplyAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public BindingHolder<ParkingApplyRecordItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        ParkingApplyRecordItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.parking_apply_record_item, parent, false);
        BindingHolder<ParkingApplyRecordItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder<ParkingApplyRecordItemBinding> holder, int position) {
        ParkingApplyListTo mode = mList.get(position);
        ParkingApplyRecordItemBinding binding = holder.getBinding();
        binding.setMode(mode);
//        Glide.with(MainApp.mContext).load(mode.getServiceImg()).error(R.drawable.service_icon).into(binding.serviceIcon);
//        binding.serviceDescription.setVisibility(TextUtils.isEmpty(mode.getServiceDesc())? View.GONE:View.VISIBLE);
//        binding.serviceTime.setText(DateUtil.formatDateString(DateUtil.mDateFormatString, mode.getCreateTime()));
           binding.serviceDescription.setText("车  位："+mode.getLoadArea());
        if("1".equals(mode.getApplyStatus())){
            binding.process.setTextColor(Color.parseColor("#ef4661"));

        }  else if("2".equals(mode.getApplyStatus())){
            binding.process.setTextColor(Color.parseColor("#969696"));
        }
//        else{
//            binding.process.setTextColor(Color.parseColor("#969696"));
//        }
    }
}

