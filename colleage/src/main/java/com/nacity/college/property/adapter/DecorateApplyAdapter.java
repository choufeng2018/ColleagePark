package com.nacity.college.property.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.college.common_libs.domain.property.DecorateApplyTo;
import com.nacity.college.R;
import com.nacity.college.databinding.DecorationApplyRecordItemBinding;
import com.nacity.college.base.adapter.BaseAdapter;
import com.nacity.college.base.adapter.BindingHolder;
import com.nacity.college.base.utils.DateUtil;

/**
 *  Created by usb on 2017/10/17.
 */

public class DecorateApplyAdapter extends BaseAdapter<DecorateApplyTo, DecorationApplyRecordItemBinding> {


    public DecorateApplyAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public BindingHolder<DecorationApplyRecordItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        DecorationApplyRecordItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.decoration_apply_record_item, parent, false);
        BindingHolder<DecorationApplyRecordItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder<DecorationApplyRecordItemBinding> holder, int position) {
        DecorateApplyTo mode = mList.get(position);
        DecorationApplyRecordItemBinding binding = holder.getBinding();
        binding.setMode(mode);
//        Glide.with(MainApp.mContext).load(mode.getServiceImg()).error(R.drawable.service_icon).into(binding.serviceIcon);
//        binding.serviceDescription.setVisibility(TextUtils.isEmpty(mode.getServiceDesc())? View.GONE:View.VISIBLE);
        binding.serviceTime.setText(DateUtil.formatDateString(DateUtil.mDateFormatString, mode.getCreateTime()));
        if("1".equals(mode.getApplyStatus())){
            binding.process.setTextColor(Color.parseColor("#ef4661"));

        }  else if("2".equals(mode.getApplyStatus())){
            binding.process.setTextColor(Color.parseColor("#6d75a4"));
        }else{
            binding.process.setTextColor(Color.parseColor("#969696"));
        }
    }
}
