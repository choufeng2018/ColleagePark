package com.nacity.college.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.college.common_libs.domain.apartment.ApartmentInfoTo;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;

import com.college.common_libs.domain.apartment.ParkCityTo;
import com.nacity.college.R;
import com.nacity.college.base.ActivityUitl;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.Constant;
import com.nacity.college.base.Event;
import com.nacity.college.base.utils.SideBar;
import com.nacity.college.login.adapter.SelectCityAdapter;
import com.nacity.college.login.model.SelectCityModel;
import com.nacity.college.login.presenter.SelectCityPresenter;
import com.nacity.college.login.view.SelectApartmentView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by xzz on 2017/8/29.
 **/

public class SelectCityActivity extends BaseActivity implements SelectApartmentView<ParkCityTo> {
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.recycleView)
    LRecyclerView recycleView;
    @BindView(R.id.side_bar)
    SideBar sideBar;
    @BindView(R.id.select_dialog)
    TextView selectDialog;


    private SelectCityAdapter adapter;
    private List<ParkCityTo> apartmentList = new ArrayList<>();
    private SelectCityModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        ButterKnife.bind(this);
        setTitle(Constant.PLEASE_SELECT_CITY);
        ActivityUitl.selectCityActivity = this;
        setRecycleView();
        setSideBar();
        model = new SelectCityPresenter(this);
        model.getCityByName("");
        loadingShow();
        setSearch();
    }

    private void setSearch() {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                model.getCityByName(s.toString().length() > 0 ? s.toString() : null);
            }
        });
    }


    public void setRecycleView() {
        adapter = new SelectCityAdapter(appContext);
        adapter.setList(apartmentList);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setPullRefreshEnabled(false);

        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recycleView.setAdapter(lRecyclerViewAdapter);
        recycleView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recycleView.setFooterViewColor(R.color.app_green, R.color.app_green, R.color.transparent);
        lRecyclerViewAdapter.setOnItemClickListener((view, position) -> {
            Intent intent = new Intent(appContext, SelectApartmentActivity.class);
            EventBus.getDefault().post(new Event<>("SelectCity", apartmentList.get(position).getName()));

            ApartmentInfoTo apartmentInfoTo=apartmentInfo.getApartmentInfoTo();
            if (apartmentInfoTo==null)
                apartmentInfoTo=new ApartmentInfoTo();
            apartmentInfoTo.setCityId(apartmentList.get(position).getRegionId());
            apartmentInfo.updateApartment(apartmentInfoTo);
            intent.putExtra("CityId", apartmentList.get(position).getRegionId()+"");
            intent.putExtra("CityName", apartmentList.get(position).getName());

            startActivity(intent, transferAnimation);
        });

    }


    @Override
    public void refreshRecycleView(List<ParkCityTo> apartmentListTo) {
        loadingDismiss();
        apartmentList.clear();
        apartmentList.addAll(apartmentListTo);
        if (apartmentListTo.size() < 20)
            recycleView.setNoMore(true);
        adapter.setList(apartmentList);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void showMessage(String message) {

    }

    private void setSideBar() {
        sideBar.setTextView(selectDialog);
        sideBar.setOnTouchingLetterChangedListener(s -> {
            int position = adapter.getSectionForPosition(s.toUpperCase().charAt(0) + "");

            if (position != -1) {
                recycleView.smoothScrollToPosition(position);
            }

        });
    }
}
