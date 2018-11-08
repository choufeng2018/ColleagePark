package com.nacity.college.property;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.college.common_libs.domain.property.ServiceTypeTo;
import com.nacity.college.R;
import com.nacity.college.base.ActivityUitl;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.Event;
import com.nacity.college.property.adapter.SelectServiceCategoryAdapter;
import com.nacity.college.property.model.SelectServiceCategoryModel;
import com.nacity.college.property.presenter.SelectServiceCategoryPresenter;
import com.nacity.college.property.view.SelectServiceCategoryView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import rx.Observable;

/**
 * Created by xzz on 2017/9/4.
 **/

public class SelectServiceCategoryActivity extends BaseActivity implements SelectServiceCategoryView<ServiceTypeTo> {
    @BindView(R.id.recycleView)
    LRecyclerView recycleView;
    private SelectServiceCategoryAdapter adapter;
    private List<ServiceTypeTo> serviceList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_recycle_view);
        ButterKnife.bind(this);
        setTitle(getIntent().getStringExtra("Title"));
        initData();
        ActivityUitl.categoryActivitieList.add(this);
    }

    private void initData() {
        if (getIntent().getSerializableExtra("ServiceList") != null) {
            serviceList = (List<ServiceTypeTo>) getIntent().getSerializableExtra("ServiceList");
        } else {
            SelectServiceCategoryModel model = new SelectServiceCategoryPresenter(this);
            model.getCategoryData(getIntent().getStringExtra("CategorySid"));
            loadingShow();
        }
        setRecycleView();
    }

    public void setRecycleView() {
        adapter = new SelectServiceCategoryAdapter(appContext);
        adapter.setList(serviceList);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setPullRefreshEnabled(false);
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recycleView.setAdapter(lRecyclerViewAdapter);
        recycleView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recycleView.setFooterViewColor(R.color.app_green, R.color.app_green, R.color.transparent);
        lRecyclerViewAdapter.setOnItemClickListener((view, position) -> {
            if (serviceList.get(position).getChild() != null && serviceList.get(position).getChild().size() > 0) {
                Intent intent = new Intent(appContext, SelectServiceCategoryActivity.class);
                intent.putExtra("ServiceList", (Serializable) serviceList.get(position).getChild());
                intent.putExtra("Title",getIntent().getStringExtra("Title"));
                startActivity(intent);
                goToAnimation(1);
            } else {
                //如果是保洁服务，就跳转保洁服务页面，否则关闭页面
                if ("9098ED29-072D-4653-A37D-3C2F6DF80861".equals(serviceList.get(position).getId())) {
                    Intent intent = new Intent(appContext, KeeperActivity.class);
                    intent.putExtra("ServiceList", serviceList.get(position));
                    startActivity(intent);
                } else
                    EventBus.getDefault().post(new Event<>("ServiceCategory", serviceList.get(position)));
                Observable.from(ActivityUitl.categoryActivitieList).subscribe(Activity::finish);
                goToAnimation(2);

            }
        });

    }


    @Override
    public void refreshRecycleView(List<ServiceTypeTo> apartmentListTo) {
        loadingDismiss();
        if (apartmentListTo.size() < 20)
            recycleView.setNoMore(true);
        serviceList.addAll(apartmentListTo);
        adapter.setList(serviceList);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void showMessage(String message) {

    }
}
