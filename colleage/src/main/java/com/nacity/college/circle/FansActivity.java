package com.nacity.college.circle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.college.common_libs.domain.circle.CareListTo;
import com.college.common_libs.domain.circle.NeighborPostTo;
import com.nacity.college.R;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.Constant;
import com.nacity.college.base.Event;
import com.nacity.college.circle.adapter.FansAdapter;
import com.nacity.college.circle.presenter.FansPresenter;
import com.nacity.college.circle.presenter.impl.FansPresenterImpl;
import com.nacity.college.circle.view.FansView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;


/**
 * Created by xzz on 2017/7/2.
 **/

public class FansActivity extends BaseActivity implements FansView<CareListTo> {
    @BindView(R.id.recycleView)
    LRecyclerView recycleView;
    private FansAdapter adapter;
    private List<CareListTo> postList = new ArrayList<>();
    private FansPresenter presenter;
    private int pageIndex;
    private int carePosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_common_layout);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        setTitle(Constant.FANS);
        presenter = new FansPresenterImpl(this);
        setRecycleView();
        presenter.getFansList(0);
        loadingShow();
    }

    public void setRecycleView() {
        adapter = new FansAdapter(appContext);
        adapter.setList(postList);
        recycleView.setLayoutManager(new LinearLayoutManager(FansActivity.this));
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recycleView.setAdapter(lRecyclerViewAdapter);
        recycleView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recycleView.setFooterViewColor(R.color.app_green, R.color.app_green, R.color.transparent);

        recycleView.setOnRefreshListener(() -> {
            pageIndex = 0;
            presenter.getFansList(pageIndex);
        });

        recycleView.setOnLoadMoreListener(() -> {
            pageIndex++;
            presenter.getFansList(pageIndex);
        });
        lRecyclerViewAdapter.setOnItemClickListener((view, position) -> enterPersonal(postList.get(position)));


    }


    @Override
    public void refreshRecycleView(List<CareListTo> list) {
        loadingDismiss();
        recycleView.refreshComplete(20);
        if (pageIndex == 0)
            postList = list;
        else
            postList.addAll(list);

        adapter.setList(postList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        showInfo(message);
    }

    @Subscribe
    public void careOther(Event event) {
        if ("Care".equals(event.getType())) {
            carePosition = (int) event.getMode();
            presenter.careOther(postList.get(carePosition));

        }
    }


    @Override
    public void careSuccess() {
        showSuccess(Constant.CARE_SUCCESS);
        postList.get(carePosition).setFollowed(!postList.get(carePosition).isFollowed());
        adapter.notifyDataSetChanged();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void enterPersonal(CareListTo ownerMessageTo) {
        Intent intent = new Intent(appContext, CirclePersonalCenterActivity.class);

        NeighborPostTo postTo = new NeighborPostTo();
        postTo.setCreateUserId(ownerMessageTo.getUserId());
        postTo.setNickname(ownerMessageTo.getNickname());
        postTo.setUserPic(ownerMessageTo.getUserPic());
        postTo.setUserType(ownerMessageTo.getUserType());
        intent.putExtra("PoseTo", postTo);
        intent.putExtra("OtherSid", ownerMessageTo.getUserId());
        startActivity(intent);
        goToAnimation(1);
    }
}
