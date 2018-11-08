package com.nacity.college.guide.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.college.common_libs.domain.guide.IntroduceTo;
import com.nacity.college.R;
import com.nacity.college.databinding.IntroduceListItemBinding;
import com.nacity.college.base.Constant;
import com.nacity.college.base.adapter.BaseAdapter;
import com.nacity.college.base.adapter.BindingHolder;
import com.nacity.college.base.utils.DateUtil;


/**
 * Created by xzz on 2017/6/24.
 **/

public class IntroduceListAdapter extends BaseAdapter<IntroduceTo, IntroduceListItemBinding> {


    public IntroduceListAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public BindingHolder<IntroduceListItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        IntroduceListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.introduce_list_item, parent, false);
        BindingHolder<IntroduceListItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder<IntroduceListItemBinding> holder, int position) {
        IntroduceTo mode = mList.get(position);
        IntroduceListItemBinding binding = holder.getBinding();
        binding.setMode(mode);
        binding.createTime.setText(DateUtil.formatDateString(DateUtil.mDateTimeFormatNoSecond,mode.getLastModTime()));
binding.summary.setText(TextUtils.isEmpty(mode.getSummary()) ? Constant.CLICK_LOOK_DETAIL : mode.getSummary());

    }


}


