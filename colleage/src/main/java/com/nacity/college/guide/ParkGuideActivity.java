package com.nacity.college.guide;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.college.common_libs.domain.apartment.AdvertiseTo;
import com.college.common_libs.domain.apartment.GuideHouseTypeTo;
import com.college.common_libs.domain.guide.EnterpriseTo;
import com.college.common_libs.domain.guide.IntroduceTo;
import com.nacity.college.R;
import com.nacity.college.databinding.GuideHeadViewBinding;
import com.nacity.college.base.AdWebActivity;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.Constant;
import com.nacity.college.base.Event;
import com.nacity.college.base.banner.BannerUtil;
import com.nacity.college.base.utils.StatuBarUtil;
import com.nacity.college.guide.adapter.EnterpriseAdapter;
import com.nacity.college.guide.adapter.GuideHouseTextAdapter;
import com.nacity.college.guide.model.ParkGuideModel;
import com.nacity.college.guide.presenter.ParkGuidePresenter;
import com.nacity.college.guide.view.ParkGuideView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by xzz on 2017/9/14.
 **/

public class ParkGuideActivity extends BaseActivity<EnterpriseTo> implements ParkGuideView<EnterpriseTo> {


    @BindView(R.id.recycleView)
    LRecyclerView recycleView;
    private Thread thread;
    private boolean isFirst;
    private List<IntroduceTo> introduceList = new ArrayList<>();
    private List<EnterpriseTo> enterpriseList = new ArrayList<>();
    private int position;
    private List<View> viewList = new ArrayList<>();
    private int pageIndex;
    private ParkGuideModel model;
    private EnterpriseAdapter adapter;
    private List<GuideHouseTypeTo> mHouseList = new ArrayList<>();
    private GuideHeadViewBinding headViewBind;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_guide);
        setTitle(getIntent().getStringExtra("Title"));
        StatuBarUtil.setStatueBarTextBlack(getWindow());
        ButterKnife.bind(this);
        setHeadView();
        EventBus.getDefault().register(this);
        model = new ParkGuidePresenter(this);
        model.getParkHouse();

        model.getParkIntroduceList();
        loadingShow();
        model.getEnterPriseLit(0, "",0);

        setRecycleView();

    }

    private void setHeadView() {

    }


    public void setRecycleView() {
        View headView = View.inflate(appContext, R.layout.guide_head_view, null);
        headViewBind = DataBindingUtil.bind(headView);
        adapter = new EnterpriseAdapter(appContext);
        adapter.setList(enterpriseList);
        recycleView.setLayoutManager(new GridLayoutManager(ParkGuideActivity.this, 2));
        recycleView.setPullRefreshEnabled(false);
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);

        recycleView.setAdapter(lRecyclerViewAdapter);
        recycleView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lRecyclerViewAdapter.addHeaderView(headView);
        recycleView.setFooterViewColor(R.color.app_green, R.color.app_green, R.color.transparent);
        lRecyclerViewAdapter.setOnItemClickListener((view, position) -> {
            Intent intent = new Intent(this, EnterpriseActivity.class);
            intent.putExtra("mode", enterpriseList.get(position));
            startActivity(intent);

        });

        recycleView.setOnRefreshListener(() -> {
            pageIndex = 0;
            model.getEnterPriseLit(pageIndex, headViewBind.allHouse.getText().toString(),0);
        });

        recycleView.setOnLoadMoreListener(() -> {
            pageIndex++;
            model.getEnterPriseLit(pageIndex, headViewBind.allHouse.getText().toString(),0);
        });


    }

    public void ThreadStart() {
        thread = new Thread(() -> {
            for (int k = 0; k < 200; k++) {
                SystemClock.sleep(5000);
                runOnUiThread(() -> {
                    isFirst = false;
                    if (position == introduceList.size())
                        position = 0;
                    RelativeLayout view2 = (RelativeLayout) View.inflate(appContext, R.layout.park_introduce_item, null);
                    ((TextView) view2.findViewById(R.id.title)).setText(introduceList.get(position).getTitle());
                    ((TextView) view2.findViewById(R.id.detail)).setText(TextUtils.isEmpty(introduceList.get(position).getSummary()) ? Constant.CLICK_LOOK_DETAIL : introduceList.get(position).getSummary());
                    if (introduceList.get(position).getLastModTime() != null && introduceList.get(position).getLastModTime().length() > 10)
                        ((TextView) view2.findViewById(R.id.time)).setText(introduceList.get(position).getLastModTime().substring(0, 10));
                    view2.setTag(introduceList.get(position).getId());
                    view2.setOnClickListener(v -> {
                        Intent intent = new Intent(appContext, AdWebActivity.class);
                        intent.putExtra("Url", "http://tdatacenter.joyhomenet.com:8081/tech/api/introduce/showArticle/" + v.getTag());
                        intent.putExtra("Title", Constant.PARK_INTRODUCE);
                        startActivity(intent);
                        goToAnimation(1);
                    });
                    viewList.add(view2);

                    for (int i = 0; i < viewList.size(); i++)
                        setAnimation(viewList.get(i), view2);
                    position++;
                });

            }
        });

    }

    public void setAnimation(final View view, final View view2) {
        AnimationSet set = new AnimationSet(true);

        TranslateAnimation translate = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,

                Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0);

        set.addAnimation(translate);
        set.setDuration(1000);
        set.setFillAfter(false);

        view.offsetTopAndBottom(-view.getHeight());

        view.startAnimation(set);
        translate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!isFirst) {
                    viewList.get(0).setVisibility(View.GONE);
                    viewList.remove(0);
                    isFirst = true;
                    headViewBind.gridLayout.addView(view2);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void addIntroduce() {

        for (int i = 0; i < introduceList.size() && i < 5; i++) {
            IntroduceTo introduceTo = introduceList.get(i);
            RelativeLayout view = (RelativeLayout) View.inflate(appContext, R.layout.park_introduce_item, null);
            ((TextView) view.findViewById(R.id.title)).setText(introduceTo.getTitle());
            ((TextView) view.findViewById(R.id.detail)).setText(TextUtils.isEmpty(introduceTo.getSummary()) ? Constant.CLICK_LOOK_DETAIL : introduceTo.getSummary());
            if (introduceList.get(position).getLastModTime() != null && introduceList.get(position).getLastModTime().length() > 10)
                ((TextView) view.findViewById(R.id.time)).setText(introduceList.get(position).getLastModTime().substring(0, 10));
            view.setTag(introduceTo.getId());
            view.setOnClickListener(v -> {
                Intent intent = new Intent(appContext, AdWebActivity.class);
                intent.putExtra("Url", "http://tdatacenter.joyhomenet.com:8081/tech/api/introduce/showArticle/" + v.getTag());
                intent.putExtra("Title", Constant.PARK_INTRODUCE);
                startActivity(intent);


                goToAnimation(1);
            });
            headViewBind.gridLayout.addView(view);

            isFirst = true;
            viewList.add(view);

        }
        if (introduceList.size() > 2) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getScreenWidth(), (int) (getScreenWidth() * 0.2514));
            headViewBind.gridLayout.setLayoutParams(layoutParams);
            ThreadStart();
            thread.start();
        }
    }

    @Subscribe
    public void getSelectBarPosition(Event<Integer> event) {
        if ("SelectBarPosition".equals(event.getType())) {
            model.getEnterPriseLit(0, mHouseList.get(position).getTeamOrbuilding(),mHouseList.get(position).getType());
            loadingShow();
            showMessage(mHouseList.get(position + 1).getTeamOrbuilding());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void getIntroduceSuccess(List<IntroduceTo> introduceListTo) {
        introduceList.clear();
        introduceList.addAll(introduceListTo);
        addIntroduce();
        if (introduceList.size() > 0)
            headViewBind.moreLayout.setOnClickListener(v -> {
                Intent intent = new Intent(appContext, IntroduceListActivity.class);
                startActivity(intent);
                goToAnimation(1);
            });
    }

    @Override
    public void refreshRecycleView(List<EnterpriseTo> list) {
        loadingDismiss();
        recycleView.refreshComplete(20);
        enterpriseList.clear();
        enterpriseList.addAll(list);
        if (list.size() < 20) {
            recycleView.setNoMore(true);
        }
        adapter.setList(enterpriseList);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void setHouse(List<GuideHouseTypeTo> houseList) {
        mHouseList = houseList;
        GuideHouseTypeTo typeToAll = new GuideHouseTypeTo();
        typeToAll.setTeamOrbuilding("全部");


        houseList.add(0, typeToAll);

        GuideHouseTypeTo typeToLast = new GuideHouseTypeTo();
        typeToLast.setTeamOrbuilding("   ");
        houseList.add(typeToLast);
        houseList.add(0,typeToLast);
        GuideHouseTextAdapter textAdapter = new GuideHouseTextAdapter(appContext);
        headViewBind.houseRecycleLayout.setLayoutManager(new LinearLayoutManager(appContext, LinearLayoutManager.HORIZONTAL, false));
        textAdapter.setList(houseList);
        headViewBind.houseRecycleLayout.setAdapter(textAdapter);
        textAdapter.notifyDataSetChanged();
        headViewBind.houseRecycleLayout.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    textAdapter.highlightItem(getMiddlePosition(headViewBind.houseRecycleLayout, textAdapter));
                    //将位置移动到中间位置
                    ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(getScrollPosition(headViewBind.houseRecycleLayout, textAdapter), 0);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }
        });
        setHouseSelectPosition(textAdapter);

    }

    private void setHouseSelectPosition(GuideHouseTextAdapter textAdapter) {
        textAdapter.setHouseSelect(position1 -> {
            headViewBind.allHouse.setText(mHouseList.get(position1).getTeamOrbuilding());
            model.getEnterPriseLit(0, Constant.HOUSE_ALL.equals(mHouseList.get(position1).getTeamOrbuilding()) ? "" : mHouseList.get(position1).getTeamOrbuilding(),mHouseList.get(position1).getType());
            loadingShow();
        });
        textAdapter.setHouseClick(position1 -> {
//            textAdapter.highlightItem(getMiddlePosition(houseRecycleLayout, textAdapter));
//            //将位置移动到中间位置
//            houseRecycleLayout.getLayoutManager().scrollToPosition(position1+1);
            headViewBind.allHouse.setText(mHouseList.get(position1).getTeamOrbuilding());
            model.getEnterPriseLit(0, Constant.HOUSE_ALL.equals(mHouseList.get(position1).getTeamOrbuilding()) ? "" : mHouseList.get(position1).getTeamOrbuilding(), mHouseList.get(position1).getType());
            loadingShow();
        });

    }

    @Override
    public void getAdSuccess(List<AdvertiseTo> advertiseList) {
        BannerUtil.setBanner(headViewBind.banner, advertiseList, R.drawable.park_guide_load);
    }

    private int getScrollPosition(RecyclerView recyclerView, GuideHouseTextAdapter textAdapter) {
        return (int) (((double) recyclerView.computeHorizontalScrollOffset()
                / (double) textAdapter.getItemWidth() * 2) + 0.5f);
    }


    private int getMiddlePosition(RecyclerView recyclerView, GuideHouseTextAdapter textAdapter) {
        return getScrollPosition(recyclerView, textAdapter) + (3 / 2);
    }
}
