package com.nacity.college.property.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.college.common_libs.domain.property.ServiceTypeTo;
import com.nacity.college.R;
import com.nacity.college.databinding.SelectServiceCategoryItemBinding;
import com.nacity.college.MainApp;
import com.nacity.college.base.adapter.BaseAdapter;
import com.nacity.college.base.adapter.BindingHolder;


/**
 * Created by xzz on 2017/6/24.
 **/

public class SelectServiceCategoryAdapter extends BaseAdapter<ServiceTypeTo, SelectServiceCategoryItemBinding> {



    public SelectServiceCategoryAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public BindingHolder<SelectServiceCategoryItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        SelectServiceCategoryItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.select_service_category_item, parent, false);
        BindingHolder<SelectServiceCategoryItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder<SelectServiceCategoryItemBinding> holder, int position) {
        ServiceTypeTo mode = mList.get(position);
        SelectServiceCategoryItemBinding binding = holder.getBinding();
        binding.serviceName.setText(mode.getName());
        binding.setMode(mode);
        Glide.with(MainApp.mContext).load(MainApp.getImagePath(mode.getImg())).error(R.drawable.service_icon).into(binding.serviceIcon);


    }



}


