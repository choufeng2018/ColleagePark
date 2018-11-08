package com.nacity.college.property.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.college.common_libs.domain.property.ServiceMainTo;
import com.nacity.college.R;

import com.nacity.college.MainApp;
import com.nacity.college.base.adapter.BaseAdapter;
import com.nacity.college.base.adapter.BindingHolder;
import com.nacity.college.databinding.PropertyServiceRecordItemBinding;


/**
 * Created by xzz on 2017/6/24.
 **/

public class PropertyServiceRecordAdapter extends BaseAdapter<ServiceMainTo, PropertyServiceRecordItemBinding> {

private String type;

    public PropertyServiceRecordAdapter(Context context,String type) {
        super(context);
        this.mContext = context;
        this.type = type;
    }

    @Override
    public BindingHolder<PropertyServiceRecordItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        PropertyServiceRecordItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.property_service_record_item, parent, false);
        BindingHolder<PropertyServiceRecordItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder<PropertyServiceRecordItemBinding> holder, int position) {
        ServiceMainTo mode = mList.get(position);
        PropertyServiceRecordItemBinding binding = holder.getBinding();
        binding.setMode(mode);
        if("1".equals(mode.getServiceStatus())){
            binding.serviceProcess.setTextColor(Color.parseColor("#ef4661"));

        } else if("2".equals(mode.getServiceStatus())){
            binding.serviceProcess.setTextColor(Color.parseColor("#6d75a4"));
        } else {
            binding.serviceProcess.setTextColor(Color.parseColor("#969696"));
        }
        if("3".equals(type)){
            binding.serviceTimeDistribution.setVisibility(View.VISIBLE);
           binding.serviceTime.setVisibility(View.GONE);
            binding.typeName.setText(mode.getServiceNameDesc());
            Glide.with(MainApp.mContext).load(mode.getServiceImg()).into(binding.serviceIcon);
        }else {
            Glide.with(MainApp.mContext).load(mode.getServiceImg()).error(R.drawable.service_icon).into(binding.serviceIcon);
            binding.serviceDescription.setVisibility(TextUtils.isEmpty(mode.getServiceDesc()) ? View.GONE : View.VISIBLE);
            binding.serviceTimeDistribution.setVisibility(View.GONE);
            binding.serviceTime.setVisibility(View.VISIBLE);
            binding.typeName.setText(mode.getServiceTypeName());

//        binding.serviceTime.setText(DateUtil.formatDateString(DateUtil.mDateFormatString, mode.getCreateTime()));
        }
    }



}


