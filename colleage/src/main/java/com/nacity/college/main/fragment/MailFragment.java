package com.nacity.college.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nacity.college.R;
import com.nacity.college.base.BaseFragment;

/**
 * Created by xzz on 2017/9/27.
 **/

public class MailFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View rootView=View.inflate(appContext, R.layout.fragment_mail,null);
        return rootView;
    }
}
