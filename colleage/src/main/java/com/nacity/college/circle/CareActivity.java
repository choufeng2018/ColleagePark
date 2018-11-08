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
import com.nacity.college.circle.adapter.CareAdapter;
import com.nacity.college.circle.presenter.CarePresenter;
import com.nacity.college.circle.presenter.impl.CarePresenterImpl;
import com.nacity.college.circle.view.CareView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;


/**
 * Created by xzz on 2017/7/2.
 **/

public class CareActivity extends BaseActivity implements CareView<CareListTo> {
    @BindView(R.id.recycleView)
    LRecyclerView recycleView;
    private CarePresenter presenter;
    private CareAdapter adapter;
    private List<CareListTo>postList=new ArrayList<>();
    private int pageIndex;
    private int cancelPosition; //取消关注的位置

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_common_layout);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        setTitle(Constant.CARE);
        presenter = new CarePresenterImpl(this);
        setRecycleView();
        presenter.getCareList(0);
        loadingShow();
    }

    public void setRecycleView(){
        adapter = new CareAdapter(appContext);
        adapter.setList(postList);
        recycleView.setLayoutManager(new LinearLayoutManager(CareActivity.this));
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recycleView.setAdapter(lRecyclerViewAdapter);
        recycleView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recycleView.setFooterViewColor(R.color.app_green,R.color.app_green,R.color.transparent);

        recycleView.setOnRefreshListener(() -> {
            pageIndex=0;
            presenter.getCareList(pageIndex);

        });

        recycleView.setOnLoadMoreListener(() -> {
            pageIndex++;
            presenter.getCareList(pageIndex);
            recycleView.setNoMore(true);
            if (postList.size() <pageIndex*20)
                recycleView.setNoMore(true);
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

    /**
     * 取消关注成功
     */
    @Override
    public void cancelCareSuccess() {
        loadingDismiss();
        showSuccess(Constant.CANCEL_CARE_SUCCESS);
        postList.remove(cancelPosition);
        adapter.setList(postList);
        adapter.notifyDataSetChanged();

    }

    @Subscribe
    public void cancelCare(Event event){
        if ("CancelCare".equals(event.getType())){
            loadingShow();
            presenter.cancelCare((CareListTo) event.getMode());
            cancelPosition=event.getPosition();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }
    public void enterPersonal(CareListTo ownerMessageTo){
        Intent intent=new Intent(appContext,CirclePersonalCenterActivity.class);

        NeighborPostTo postTo=new NeighborPostTo();
        postTo.setCreateUserId(ownerMessageTo.getFollowUserId());
        postTo.setNickname(ownerMessageTo.getFollowNickname());
        postTo.setUserPic(ownerMessageTo.getFollowUserPic());
        postTo.setUserType(ownerMessageTo.getFollowUserType());
        intent.putExtra("PoseTo",postTo);
        intent.putExtra("OtherSid", ownerMessageTo.getFollowUserId());
        startActivity(intent);
        goToAnimation(1);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        presenter.getCareList(0);
    }
}
