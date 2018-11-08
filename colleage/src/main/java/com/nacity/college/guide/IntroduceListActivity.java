package com.nacity.college.guide;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.college.common_libs.domain.guide.IntroduceTo;
import com.nacity.college.R;
import com.nacity.college.base.AdWebActivity;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.BaseRecycleView;
import com.nacity.college.base.Constant;
import com.nacity.college.guide.adapter.IntroduceListAdapter;
import com.nacity.college.guide.model.IntroduceListModel;
import com.nacity.college.guide.presenter.IntroduceListPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xzz on 2017/9/22.
 **/

public class IntroduceListActivity extends BaseActivity<IntroduceTo> implements BaseRecycleView<IntroduceTo> {
    @BindView(R.id.recycleView)
    LRecyclerView recycleView;
    private IntroduceListAdapter adapter;
    private List<IntroduceTo>introduceList=new ArrayList<>();
    private IntroduceListModel model;
    private int pageIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_recycle_view);
        ButterKnife.bind(this);
        setTitle(Constant.PARK_INTRODUCE);
        model = new IntroduceListPresenter(this);
        model.getIntroduceList(0);
        setRecycleView();
        loadingShow();

    }

    public void setRecycleView() {
        adapter = new IntroduceListAdapter(appContext);
        adapter.setList(introduceList);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setPullRefreshEnabled(true);

        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recycleView.setAdapter(lRecyclerViewAdapter);
        recycleView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recycleView.setFooterViewColor(R.color.app_green, R.color.app_green, R.color.transparent);
        lRecyclerViewAdapter.setOnItemClickListener((view, position) -> {
            Intent intent = new Intent(appContext, AdWebActivity.class);
            intent.putExtra("Url", "http://progarden.joyhomenet.com:8081/garden/api/introduce/showArticle/" +introduceList.get(position).getId());
            intent.putExtra("Title", Constant.PARK_INTRODUCE);
            startActivity(intent);
        });

        recycleView.setOnRefreshListener(() -> {
            pageIndex=0;
            model.getIntroduceList(pageIndex);
        });

        recycleView.setOnLoadMoreListener(() -> {
            pageIndex++;
            model.getIntroduceList(pageIndex);
        });
    }


    @Override
    public void refreshRecycleView(List<IntroduceTo> introduceListTo) {
        loadingDismiss();
        recycleView.refreshComplete(20);
        introduceList.clear();
        introduceList.addAll(introduceListTo);
        if (introduceListTo.size() < 20)
            recycleView.setNoMore(true);
        adapter.setList(introduceList);
        adapter.notifyDataSetChanged();

    }
}
