package com.nacity.college.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.college.common_libs.domain.apartment.ApartmentInfoTo;
import com.nacity.college.R;
import com.nacity.college.base.ActivityUitl;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.Constant;
import com.nacity.college.base.Event;
import com.nacity.college.base.utils.SideBar;
import com.nacity.college.login.adapter.SelectApartmentAdapter;
import com.nacity.college.login.model.SelectApartmentModel;
import com.nacity.college.login.presenter.SelectApartmentPresenter;
import com.nacity.college.login.view.SelectApartmentView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by xzz on 2017/8/29.
 **/

public class SelectApartmentActivity extends BaseActivity implements SelectApartmentView<ApartmentInfoTo> {
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.recycleView)
    LRecyclerView recycleView;
    @BindView(R.id.side_bar)
    SideBar sideBar;
    @BindView(R.id.select_dialog)
    TextView selectDialog;

    private SelectApartmentAdapter adapter;
    private List<ApartmentInfoTo> apartmentList = new ArrayList<>();
    private SelectApartmentModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_apartment);
        ButterKnife.bind(this);
        setTitle(Constant.PLEASE_SELECT_APARTMENT);

        setRecycleView();
        setSideBar();
//        getIntent().getStringExtra("CityName")
        model = new SelectApartmentPresenter(this);
        if( getIntent().getStringExtra("CityId")==null){
        model.getApartmentByNameAndCityId("","");
        }else{
            model.getApartmentByNameAndCityId("",getIntent().getStringExtra("CityId"));
        }
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
                Log.i("2222", ": ssss11"+s);
                Log.i("2222", ": ssss2"+getIntent().getIntExtra("CityId",0)+"");
             model.getApartmentByNameAndCityId(s.length()>0?s.toString():"","");
            }
        });
    }


    public void setRecycleView() {
        adapter = new SelectApartmentAdapter(appContext);
        adapter.setList(apartmentList);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recycleView.setAdapter(lRecyclerViewAdapter);
        recycleView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recycleView.setFooterViewColor(R.color.app_green, R.color.app_green, R.color.transparent);
        recycleView.setPullRefreshEnabled(false);


        lRecyclerViewAdapter.setOnItemClickListener((view, position) -> {


            if (ActivityUitl.selectCityActivity != null)
                ActivityUitl.selectCityActivity.finish();
            ApartmentInfoTo apartmentInfoTo=apartmentList.get(position);
//            apartmentInfoTo.setCityName(getIntent().getStringExtra("CityName"));
//            apartmentInfoTo.setCityId(getIntent().getIntExtra("CityId",0));
            apartmentInfo.updateApartment(apartmentInfoTo);
//            Log.i("2222", "apartmentList: "+apartmentList.toString());
//            Log.i("2222", "apartmentInfo: "+apartmentInfoTo.toString());
//            Log.i("2222", "apartmentInfo: "+apartmentInfoTo.getCityName());
            EventBus.getDefault().post(new Event<>("SelectCity", apartmentList.get(position).getCityName()));
            EventBus.getDefault().post(new Event<>("SelectApartment", apartmentList.get(position).getGardenName()));
            EventBus.getDefault().post(new Event<>("UpdateUserInfo", apartmentList.get(position).getGardenName()));

            finish();
            ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(SelectApartmentActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        });

    }


    @Override
    public void refreshRecycleView(List<ApartmentInfoTo> apartmentListTo) {
        loadingDismiss();
        apartmentList.clear();
        apartmentList=apartmentListTo;
        if (apartmentListTo.size() < 20)
            recycleView.setNoMore(true);
        adapter.setList(apartmentList);
        adapter.notifyDataSetChanged();

       Log.i("2222", "apartmentList: "+apartmentList.toString());
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
