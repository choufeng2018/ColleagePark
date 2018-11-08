package com.nacity.college.circle.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.college.common_libs.domain.circle.NeighborCommentTo;
import com.nacity.college.R;
import com.nacity.college.databinding.NewMessageItemBinding;
import com.nacity.college.MainApp;
import com.nacity.college.base.adapter.BaseAdapter;
import com.nacity.college.base.adapter.BindingHolder;


/**
 * Created by xzz on 2017/6/24.
 **/

public class NewMessageAdapter extends BaseAdapter<NeighborCommentTo,NewMessageItemBinding> {




    public NewMessageAdapter(Context context) {
        super(context);
        this.mContext=context;
    }

    @Override
    public BindingHolder<NewMessageItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        NewMessageItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.new_message_item, parent, false);
        BindingHolder<NewMessageItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder<NewMessageItemBinding> holder, int position) {
        NeighborCommentTo mode = mList.get(position);
        NewMessageItemBinding binding = holder.getBinding();

        if (TextUtils.isEmpty(mode.getId())) {
            binding.ownerName.setText(mode.getNickname());
            binding.joinType.setText(mode.getNickname()+ "评论了你");
            binding.postTime.setText(mode.getCreateTime());
            Glide.with(mContext).load(MainApp.getImagePath(mode.getUserPic())).into(binding.headImage);

        } else {
            binding.ownerName.setText(mode.getNickname());
            binding.joinType.setText(mode.getNickname() + "赞了你");
            binding.postTime.setText(mode.getCreateTime());
            Glide.with(mContext).load(MainApp.getImagePath(mode.getUserPic())).into(binding.headImage);
        }


        binding.postContent.setText(mode.getContent());
    }


}


