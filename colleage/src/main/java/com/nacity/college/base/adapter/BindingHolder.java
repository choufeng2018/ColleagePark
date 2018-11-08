package com.nacity.college.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 *  Created by xzz on 2017/6/24.
 */

public class BindingHolder<T> extends RecyclerView.ViewHolder {
    private T binding;

    public BindingHolder(View v) {
        super(v);
    }

    public T getBinding() {
        return binding;
    }

    public void setBinding(T binding) {
        this.binding = binding;
    }
}