package com.nacity.college.property.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.college.common_libs.domain.property.ServiceTypeTo;
import com.nacity.college.R;
import com.nacity.college.databinding.DistributionLeftItemBinding;
import com.nacity.college.base.adapter.BaseAdapter;
import com.nacity.college.base.adapter.BindingHolder;

/**
 *  Created by usb on 2017/10/26.
 */

public class DistributionLeftAdapter extends BaseAdapter<ServiceTypeTo, DistributionLeftItemBinding> {
    public DistributionLeftAdapter(Context context) {
        super(context);
        this.mContext = context;
    }
    @Override
    public BindingHolder<DistributionLeftItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {

        DistributionLeftItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.distribution_left_item, parent, false);
        BindingHolder<DistributionLeftItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder<DistributionLeftItemBinding> holder, int position) {
        Log.i("222222222222", "onBindViewHolder: "+position);
        ServiceTypeTo mode = mList.get(position);
        DistributionLeftItemBinding binding = holder.getBinding();
        binding.setMode(mode);
        binding.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i <mList.size() ; i++) {
                    mList.get(i).setChangeColor("0");
                }
                mode.setChangeColor("1");
               listener.itemClick(mode,position);
            }
        });
        if("1".equals(mode.getChangeColor())){
            binding.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }else{
            binding.itemView.setBackgroundColor(Color.parseColor("#e7e7e7"));
        }
        binding.waterType.setText(mode.getName());
//        Glide.with(MainApp.mContext).load(mode.getServiceImg()).error(R.drawable.service_icon).into(binding.serviceIcon);
//        binding.serviceTime.setText(DateUtil.formatDateString(DateUtil.mDateFormatString, mode.getCreateTime()));
//        if("1".equals(mode.getApplyStatus())){
//            binding.process.setTextColor(Color.parseColor("#ef4661"));
//
//        }  else if("2".equals(mode.getApplyStatus())){
//            binding.process.setTextColor(Color.parseColor("#6d75a4"));
//        }else{
//            binding.process.setTextColor(Color.parseColor("#969696"));
//        }
    }
    public interface OnItemClickListener {
        void itemClick(ServiceTypeTo mode, int position);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;

    }

}
