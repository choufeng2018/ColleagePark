package com.nacity.college.circle;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.circle.NeighborLikeTo;
import com.college.common_libs.domain.circle.NeighborPostTo;
import com.nacity.college.R;
import com.nacity.college.databinding.LifePersonHeadViewBinding;
import com.nacity.college.MainApp;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.CommonAlertDialog;
import com.nacity.college.base.Constant;
import com.nacity.college.base.Event;
import com.nacity.college.base.PostImageDetailActivity;
import com.nacity.college.circle.adapter.LifeFragmentAdapter;
import com.nacity.college.circle.presenter.LifePersonalPresenter;
import com.nacity.college.circle.presenter.impl.LifePersonalPresenterImpl;
import com.nacity.college.circle.view.LifePersonView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import es.dmoral.toasty.Toasty;

/**
 * Created by xzz on 2017/7/1.
 **/

public class CirclePersonalCenterActivity extends BaseActivity implements LifePersonView {
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.recycleView)
    LRecyclerView recycleView;
    @BindView(R.id.sub_textView)
    TextView subTextView;
    private int pageIndex;
    private List<NeighborPostTo> postList = new ArrayList<>();
    private LifeFragmentAdapter adapter;
    private LifePersonalPresenter presenter;
    private NeighborPostTo postTo;
    private LifePersonHeadViewBinding bind;
    private int praisePosition;
    private SubContentUtil subContentUtil;
    private boolean isCare;//是否关注
    private int deletePosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_personal_center);
        ButterKnife.bind(this);
        setTitle(Constant.PERSON_CENTER);
        getIntentData();
        EventBus.getDefault().register(this);

    }

    private void getIntentData() {
        postTo = (NeighborPostTo) getIntent().getSerializableExtra("PoseTo");

        subContentUtil = new SubContentUtil();
        setRecycleView(postTo.getCreateUserId());
        presenter = new LifePersonalPresenterImpl(this);

        presenter.getPostList(postTo.getCreateUserId(), 0);
        loadingShow();

    }

    public void setRecycleView(String otherSid) {
        adapter = new LifeFragmentAdapter(appContext);
        adapter.setList(postList);
        recycleView.setLayoutManager(new LinearLayoutManager(CirclePersonalCenterActivity.this));
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recycleView.setAdapter(lRecyclerViewAdapter);
        recycleView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recycleView.setFooterViewColor(R.color.app_green, R.color.app_green, R.color.transparent);
        View headView = View.inflate(appContext, R.layout.life_person_head_view, null);
        bind = DataBindingUtil.bind(headView);

        setHeadView();

        lRecyclerViewAdapter.addHeaderView(headView);

        recycleView.setOnRefreshListener(() -> {
            pageIndex = 0;
            presenter.getPostList(otherSid, pageIndex);
        });

        recycleView.setOnLoadMoreListener(() -> {
            pageIndex++;
            presenter.getPostList(otherSid, pageIndex);
        });
        adapterClickListener();
        lRecyclerViewAdapter.setOnItemClickListener((view, position) -> {
            Intent intent = new Intent(appContext, PostDetailActivity.class);
            intent.putExtra("PostSid", postList.get(position).getId());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

    }

    private void setHeadView() {
        //  titleName.setText(postTo.getNickname() + Constant.PERSON_CENTER);
        bind.ownerName.setText(postTo.getNickname());
        Glide.with(appContext).load(MainApp.getImagePath(postTo.getUserPic())).into(bind.headImage);


        if (userInfo.getSid().equals(postTo.getCreateUserId())) {
            bind.joinLayout.setVisibility(View.VISIBLE);
            bind.careOther.setVisibility(View.GONE);
            bind.join.setOnClickListener(v -> {
                Intent intent = new Intent(appContext, JoinActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                goToAnimation(1);
            });
            bind.care.setOnClickListener(v -> {
                Intent intent = new Intent(appContext, CareActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                goToAnimation(1);
            });
            bind.fans.setOnClickListener(v -> {
                Intent intent = new Intent(appContext, FansActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                goToAnimation(1);
            });
        } else {
            bind.joinLayout.setVisibility(View.GONE);
            bind.careOther.setVisibility(View.VISIBLE);
            bind.careOther.setBackgroundResource(isCare ? R.drawable.transparent_bg : R.drawable.care_other_icon);
            bind.careOther.setText(isCare ? Constant.CANCEL_CARE : "");
            bind.careOther.setOnClickListener(v -> presenter.careOther(userInfo.getSid(), getIntent().getStringExtra("OtherSid")));

        }
        if (postTo.getUserType() == 6 || postTo.getUserType() == 7)
            bind.vIcon.setVisibility(View.VISIBLE);
        else
            bind.vIcon.setVisibility(View.GONE);

    }


    @Override
    public void refreshRecycleView(MessageTo<List<NeighborPostTo>> msg) {

        loadingDismiss();
        recycleView.refreshComplete(msg.getData().size());

        if (pageIndex == 0)
            postList.clear();
        postList.addAll(msg.getData());
        if (msg.getData().size() < 1) {
            recycleView.setNoMore(true);
            pageIndex--;
        }

        postList = subContentUtil.getSubList(postList, subTextView);
        adapter.setList(postList);
        adapter.notifyDataSetChanged();
        this.isCare = (boolean) msg.getTag();
        setHeadView();
    }

    @Override
    public void careOtherSuccess() {

        Toasty.info(appContext, isCare ? Constant.CANCEL_CARE_SUCCESS : Constant.CARE_SUCCESS).show();
        isCare = !isCare;
        setHeadView();
    }

    @Override
    public void cancelOtherSuccess() {

        showSuccess(Constant.CANCEL_CARE_SUCCESS);
        setHeadView();
    }

    @Override
    public List<NeighborLikeTo> getLikeList() {
        return postList.get(praisePosition).getPostPraiseVoList();
    }

    @Override
    public void addPraiseSuccess(List<NeighborLikeTo> likeList) {
        EventBus.getDefault().post(new Event<>("CircleAddPraiseSuccess", getIntent().getStringExtra("PostSid")));
        presenter.getPostList(postTo.getCreateUserId(), pageIndex);
    }

    @Override
    public void deletePostSuccess() {
        loadingDismiss();
        postList.remove(deletePosition);
        adapter.notifyDataSetChanged();
        EventBus.getDefault().post(new Event<>("CircleDeleteSuccess", getIntent().getStringExtra("PostSid")));
    }

    private void adapterClickListener() {
        adapter.setOnImageClickListener((currentPath, pathList, postImage) -> {
            Intent intent = new Intent(appContext, PostImageDetailActivity.class);
            intent.putExtra("CurrentPath", currentPath);
            intent.putExtra("PathList", pathList);

            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(CirclePersonalCenterActivity.this, postImage, "Image");
            ActivityCompat.startActivity(appContext, intent, options.toBundle());
        });
        adapter.setOnHeadImageClickListener((mode, headImage) -> {
//            Intent intent = new Intent(appContext, CirclePersonalCenterActivity.class);
//            intent.putExtra("PoseTo", mode);
//
//            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(CirclePersonalCenterActivity.this, headImage, "HeadImage");
//            ActivityCompat.startActivity(appContext, intent, options.toBundle());
        });

        adapter.setPraiseClickListener((mode, position) -> {
            praisePosition = position;
            presenter.addPraise(mode.getId());
            loadingShow();
        });
        adapter.setDeletePostListener((postSid, position) -> CommonAlertDialog.show(appContext, Constant.DELETE_POST, Constant.CONFIRM, Constant.CANCEL).setOnClickListener(v -> {
            CommonAlertDialog.dismiss();
            presenter.deletePost(postSid);
            deletePosition = position;
            loadingShow();
        }));

    }

    @Subscribe
    public void refreshData(Event<String> event) {
        if ("CircleAddPraiseSuccess".equals(event.getType()) || "CircleCommentDeleteSuccess".equals(event.getType()) || "CircleCommentSuccess".equals(event.getType()) || "CircleDeleteSuccess".equals(event.getType())) {
            for (int i = 0; i < postList.size(); i++) {
                if (postList.get(i).getId().equals(event.getMode())) {
                    switch (event.getType()) {
                        case "CircleAddPraiseSuccess":
                            praisePosition = i;
                            addPraiseSuccess(null);
                            break;
                        case "CircleDeleteSuccess":
                            deletePosition = i;
                            deletePostSuccess();
                            break;
                        case "CircleCommentDeleteSuccess":
                            postList.get(i).setCommentTotal(Integer.valueOf(postList.get(i).getCommentTotal()) - 1 + "");
                            adapter.notifyDataSetChanged();
                            break;
                        case "CircleCommentSuccess":
                            postList.get(i).setCommentTotal(Integer.valueOf(postList.get(i).getCommentTotal()) + 1 + "");
                            adapter.notifyDataSetChanged();
                            break;


                    }


                }
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
