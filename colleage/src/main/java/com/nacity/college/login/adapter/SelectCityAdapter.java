package com.nacity.college.login.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.college.common_libs.domain.apartment.ParkCityTo;

import com.nacity.college.R;

import com.nacity.college.base.adapter.BaseAdapter;
import com.nacity.college.base.adapter.BindingHolder;

import com.nacity.college.databinding.SelectCityItemBinding;


/**
 * Created by xzz on 2017/6/24.
 **/

public class SelectCityAdapter extends BaseAdapter<ParkCityTo, SelectCityItemBinding> {



    public SelectCityAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public BindingHolder<SelectCityItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        SelectCityItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.select_city_item, parent, false);
        BindingHolder<SelectCityItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder<SelectCityItemBinding> holder, int position) {
        ParkCityTo mode = mList.get(position);
        SelectCityItemBinding binding = holder.getBinding();
        binding.setMode(mode);



    }
    public int getSectionForPosition(String positionString) {
      int position=0;
        for (ParkCityTo cityTo:mList){
            if (positionString.equals(cityTo.getName().substring(0,1).toUpperCase())){
                break;
            }
            position++;
        }

        return position;
    }


}


