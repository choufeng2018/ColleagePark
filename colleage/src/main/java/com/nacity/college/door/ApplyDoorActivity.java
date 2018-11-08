package com.nacity.college.door;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.college.common_libs.domain.door.CanApplyDoorTo;
import com.nacity.college.R;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.Constant;
import com.nacity.college.door.adapter.ApplyDoorAdapter;
import com.nacity.college.door.presenter.ApplyDoorPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

/**
 * Created by xzz on 2018/1/27.
 **/

public class ApplyDoorActivity extends BaseActivity<List<CanApplyDoorTo>> {
    @BindView(R.id.recycleView)
    LRecyclerView recycleView;
    private ApplyDoorPresenter presenter;
    private ApplyDoorAdapter adapter;
    private List<CanApplyDoorTo> doorList = new ArrayList<>();
    private String allDoorSid = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_door);
        ButterKnife.bind(this);
        setTitle(Constant.APPLY_DOOR);
        setRecycleView();
        presenter = new ApplyDoorPresenter(this);
        presenter.getCanApplyDoor(apartmentInfo.getSid(), userInfo.getSid());
        loadingShow();
    }

    private void setRecycleView() {
        adapter = new ApplyDoorAdapter(appContext);
        adapter.setList(doorList);
        recycleView.setPullRefreshEnabled(false);
        recycleView.setLayoutManager(new LinearLayoutManager(ApplyDoorActivity.this));
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recycleView.setAdapter(lRecyclerViewAdapter);
        lRecyclerViewAdapter.setOnItemClickListener((view, position) -> {

            view.findViewById(R.id.door_select).setBackgroundResource(!doorList.get(position).isSelect() ? R.drawable.apply_door_select : R.drawable.my_door_un_select_icon);
            doorList.get(position).setSelect(!doorList.get(position).isSelect());

        });
    }

    @Override
    public void getDataSuccess(List<CanApplyDoorTo> data) {
        super.getDataSuccess(data);
        doorList.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void submitDataSuccess(Object data) {
        super.submitDataSuccess(data);
        showSuccess(Constant.APPLY_DOOR_SUCCESS);
        finish();
        goToAnimation(2);
    }

    @OnClick(R.id.confirm_apply)
    public void onViewClicked() {
        allDoorSid = "";
        Observable.from(doorList).filter(CanApplyDoorTo::isSelect).subscribe(canApplyDoorTo -> allDoorSid = allDoorSid + canApplyDoorTo.getDoorId() + ",");
        if (allDoorSid.length() == 0) {
            showMessage(Constant.PLEASE_SELECT_DOOR);
            return;
        }
        allDoorSid = allDoorSid.substring(0, allDoorSid.length() - 1);
        presenter.submitApplyDoor(apartmentInfo.getSid(), userInfo.getSid(),allDoorSid);
       loadingShow();
    }
}
