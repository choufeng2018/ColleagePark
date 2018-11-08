package com.nacity.college.login.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.college.common_libs.domain.apartment.ApartmentInfoTo;
import com.nacity.college.R;
import com.nacity.college.databinding.SelectApartmentItemBinding;
import com.nacity.college.base.adapter.BaseAdapter;
import com.nacity.college.base.adapter.BindingHolder;


/**
 * Created by xzz on 2017/6/24.
 **/

public class SelectApartmentAdapter extends BaseAdapter<ApartmentInfoTo, SelectApartmentItemBinding> {



    public SelectApartmentAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public BindingHolder<SelectApartmentItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        SelectApartmentItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.select_apartment_item, parent, false);
        BindingHolder<SelectApartmentItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder<SelectApartmentItemBinding> holder, int position) {
        ApartmentInfoTo mode = mList.get(position);
        SelectApartmentItemBinding binding = holder.getBinding();
        binding.setMode(mode);



    }
    public int getSectionForPosition(String positionString) {
      int position=0;
        for (ApartmentInfoTo cityTo:mList){
            if (positionString.equals(cityTo.getGardenName().substring(0,1).toUpperCase())){
                break;
            }
            position++;
        }

        return position;
    }


}


