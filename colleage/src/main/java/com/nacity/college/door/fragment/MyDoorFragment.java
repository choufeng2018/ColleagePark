package com.nacity.college.door.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.college.common_libs.domain.door.OpenDoorListTo;
import com.nacity.college.R;
import com.nacity.college.base.BaseFragment;
import com.nacity.college.door.adapter.MyDoorAdapter;
import com.nacity.college.door.presenter.MyDoorPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xzz on 2018/1/27.
 **/

public class MyDoorFragment extends BaseFragment<List<OpenDoorListTo>> {

    @BindView(R.id.recycleView)
    LRecyclerView recycleView;
    Unbinder unbinder;
    private MyDoorAdapter adapter;
    private List<OpenDoorListTo> doorList = new ArrayList<>();
    private MyDoorPresenter presenter = new MyDoorPresenter(this);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_common_recycle_view, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        setRecycleView();
        presenter.getMyDoorList(apartmentInfo.getSid(), userInfo.getSid());
        loadingShow();
        return rootView;
    }

    private void setRecycleView() {
        adapter = new MyDoorAdapter(appContext);
        adapter.setList(doorList);

        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recycleView.setAdapter(lRecyclerViewAdapter);
        recycleView.setOnRefreshListener(() -> {
            presenter.getMyDoorList(apartmentInfo.getSid(), userInfo.getSid());
            loadingShow();
        });
    }


    @Override
    public void getDataSuccess(List<OpenDoorListTo> data) {
        super.getDataSuccess(data);

        recycleView.refreshComplete(20);

            doorList = data;

        if (doorList.size() < 20)
            recycleView.setNoMore(true);
        adapter.setList(doorList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
