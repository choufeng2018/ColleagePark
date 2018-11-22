package com.nacity.college.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.college.common_libs.domain.circle.NeighborPostTo;
import com.college.common_libs.domain.user.UserFansTo;
import com.college.common_libs.domain.user.UserInfoTo;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nacity.college.MainApp;
import com.nacity.college.R;
import com.nacity.college.base.BaseFragment;
import com.nacity.college.base.CommonAlertDialog;
import com.nacity.college.base.Constant;
import com.nacity.college.base.info.UserInfoHelper;
import com.nacity.college.base.utils.GlideCacheUtil;
import com.nacity.college.base.utils.PingFangTextView;
import com.nacity.college.circle.CirclePersonalCenterActivity;
import com.nacity.college.common.model.UserInfoModel;
import com.nacity.college.common.presenter.UserInfoPresenter;
import com.nacity.college.login.LoginActivity;
import com.nacity.college.myself.AboutActivity;
import com.nacity.college.myself.FeedBackActivity;
import com.nacity.college.myself.MyServiceRecordActivity;
import com.nacity.college.myself.PersonCenterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;

/**
 * Created by xzz on 2017/9/11.
 **/

public class MyselfFragment extends BaseFragment {
    @BindView(R.id.head_image)
    RoundedImageView headImage;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.address)
    TextView address;
    Unbinder unbinder;
    @BindView(R.id.v_icon)
    View vIcon;
    @BindView(R.id.care_num)
    PingFangTextView careNum;
    @BindView(R.id.join_num)
    PingFangTextView joinNum;
    @BindView(R.id.fans_num)
    PingFangTextView fansNum;
    private UserInfoModel model;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_myself, container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        model = new UserInfoPresenter(this);

        return mRootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        model.getUserInfo();
        setView();
    }

    public void setView() {
        userName.setText(userInfo.getUserInfoTo().getNickname());
        address.setText(apartmentInfo.getGardenName() + " " + (userInfo.getUserInfoTo().getNo() == null ? "" : userInfo.getUserInfoTo().getNo()));
        Glide.with(MainApp.mContext).load(MainApp.getImagePath(userInfo.getUserInfoTo().getUserPic())).into(headImage);
        vIcon.setVisibility(userInfo.getUserInfoTo().getUserType() == 6 || userInfo.getUserInfoTo().getUserType() == 7 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.person_center_layout, R.id.main_page_layout, R.id.my_walt_layout, R.id.my_older_layout, R.id.my_service_record_layout, R.id.feedback_layout, R.id.clean_cache_layout, R.id.about_layout, R.id.login_out_layout, R.id.my_door_layout})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.person_center_layout:
                intent = new Intent(appContext, PersonCenterActivity.class);
                startActivity(intent);

                break;
            case R.id.main_page_layout:
                intent = new Intent(appContext, CirclePersonalCenterActivity.class);
                NeighborPostTo postTo = new NeighborPostTo();

                postTo.setUserPic(userInfo.getUserInfoTo().getUserPic());
                postTo.setNickname(userInfo.getUserInfoTo().getNickname());
                postTo.setCreateUserId(userInfo.getSid());
                postTo.setUserType(userInfo.getUserInfoTo().getUserType());

                intent.putExtra("PoseTo", postTo);
                startActivity(intent);
                break;
            case R.id.my_walt_layout:
                toExpectActivity(Constant.MY_WALT);
                break;
            case R.id.my_older_layout:
                toExpectActivity(Constant.MY_OLDER);
                break;
            case R.id.my_service_record_layout:
                intent = new Intent(appContext, MyServiceRecordActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.my_door_layout:

                break;
            case R.id.feedback_layout:
                intent = new Intent(appContext, FeedBackActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.clean_cache_layout:
                Toasty.success(appContext, Constant.CLEAN + GlideCacheUtil.getCacheSize(appContext) + Constant.CACHE).show();
                GlideCacheUtil cacheUtil = GlideCacheUtil.getInstance();
                cacheUtil.clearImageMemoryCache(appContext);
                break;
            case R.id.about_layout:
                intent = new Intent(appContext, AboutActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.login_out_layout:
                loginOutDialog();
                break;
        }
    }

    private void loginOutDialog() {
        CommonAlertDialog.show(getActivity(), Constant.LOGIN_OUT, Constant.CONFIRM, Constant.CANCEL).setOnClickListener(v -> {
            userInfo.updateLogin(false);
            userInfo.updateUser(null);
            Intent intent = new Intent(appContext, LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
            goToAnimation(2);

        });
    }

    @Override
    public void getDataSuccess(Object data) {
        userInfo.updateUser((UserInfoTo) data);
        userInfo = UserInfoHelper.getInstance(appContext);
        setView();
    }

    public void getUserInfo() {
        model.getUserInfo();
    }

    @Override
    public void submitDataSuccess(Object data) {
        if (data != null) {
            UserFansTo userFansTo = (UserFansTo) data;
            careNum.setText(userFansTo.getFollowCount());
            joinNum.setText(userFansTo.getParticCount());
            fansNum.setText(userFansTo.getFansCount());
        }
    }
}
