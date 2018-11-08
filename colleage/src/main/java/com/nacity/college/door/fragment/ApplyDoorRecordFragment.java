package com.nacity.college.door.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.college.common_libs.domain.door.ApplyDoorRecordTo;
import com.nacity.college.R;
import com.nacity.college.base.BaseFragment;
import com.nacity.college.door.ApplyDoorActivity;
import com.nacity.college.door.ApplyDoorDetailActivity;
import com.nacity.college.door.adapter.ApplyDoorRecordAdapter;
import com.nacity.college.door.presenter.MyDoorPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by xzz on 2018/1/27.
 **/

public class ApplyDoorRecordFragment extends BaseFragment<List<ApplyDoorRecordTo>> {

    @BindView(R.id.recycleView)
    LRecyclerView recycleView;
    Unbinder unbinder;
    private ApplyDoorRecordAdapter adapter;
    private List<ApplyDoorRecordTo> doorList = new ArrayList<>();
    private MyDoorPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_apply_door_record, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        presenter = new MyDoorPresenter(this);
        setRecycleView();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getApplyDoorList(apartmentInfo.getSid(), userInfo.getSid());
        loadingShow();
    }

    private void setRecycleView() {
        adapter = new ApplyDoorRecordAdapter(appContext);
        adapter.setList(doorList);

        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recycleView.setAdapter(lRecyclerViewAdapter);
        recycleView.setOnRefreshListener(() -> {
            loadingShow();
            presenter.getApplyDoorList(apartmentInfo.getSid(), userInfo.getSid());
        });
        lRecyclerViewAdapter.setOnItemClickListener((view, position) -> {
            Intent intent=new Intent(appContext, ApplyDoorDetailActivity.class);
            intent.putExtra("ApplyDoorRecordTo",doorList.get(position));
            startActivity(intent);
            goToAnimation(1);
        });
    }


    @Override
    public void getDataSuccess(List<ApplyDoorRecordTo> data) {
        super.getDataSuccess(data);
        recycleView.refreshComplete(0);
        doorList.clear();
        doorList.addAll(data);
        recycleView.setNoMore(true);
        adapter.setList(doorList);
        adapter.notifyDataSetChanged();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.start_apply)
    public void onViewClicked() {
        Intent intent=new Intent(appContext, ApplyDoorActivity.class);
        startActivity(intent);
        goToAnimation(1);
    }
}
