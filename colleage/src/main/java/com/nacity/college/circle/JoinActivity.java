package com.nacity.college.circle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.college.common_libs.domain.circle.CircleJoinTo;
import com.nacity.college.R;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.BaseRecycleView;
import com.nacity.college.base.Constant;
import com.nacity.college.circle.adapter.JoinAdapter;
import com.nacity.college.circle.presenter.JoinPresenter;
import com.nacity.college.circle.presenter.impl.JoinPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by xzz on 2017/7/2.
 **/

public class JoinActivity extends BaseActivity implements BaseRecycleView<CircleJoinTo> {
    @BindView(R.id.recycleView)
    LRecyclerView recycleView;
    private JoinAdapter adapter;
    private List<CircleJoinTo>postList=new ArrayList<>();
    private JoinPresenter presenter;
    private int pageIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_common_layout);
        ButterKnife.bind(this);
        setTitle(Constant.JOIN);
        presenter = new JoinPresenterImpl(this);
        setRecycleView();
        presenter.getJoinList(0);
        loadingShow();
    }


    public void setRecycleView(){
        adapter = new JoinAdapter(appContext);
        adapter.setList(postList);
        recycleView.setLayoutManager(new LinearLayoutManager(JoinActivity.this));
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recycleView.setAdapter(lRecyclerViewAdapter);
        recycleView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recycleView.setFooterViewColor(R.color.app_green,R.color.app_green,R.color.transparent);

        recycleView.setOnRefreshListener(() -> {
            pageIndex=0;
            presenter.getJoinList(pageIndex);
        });

        recycleView.setOnLoadMoreListener(() -> {
            pageIndex++;
            presenter.getJoinList(pageIndex);
        });

        lRecyclerViewAdapter.setOnItemClickListener((view, position) -> {
            Intent intent=new Intent(appContext,PostDetailActivity.class);
            intent.putExtra("PostSid",postList.get(position).getPostId());
            startActivity(intent);
            goToAnimation(1);
        });

    }


    @Override
    public void refreshRecycleView(List<CircleJoinTo> list) {
        loadingDismiss();
        recycleView.refreshComplete(20);
        if (pageIndex == 0)
            postList = list;
        else
            postList.addAll(list);
        if (list.size() < 20)
            recycleView.setNoMore(true);

        adapter.setList(postList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
     showInfo(message);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        presenter.getJoinList(0);
    }
}
