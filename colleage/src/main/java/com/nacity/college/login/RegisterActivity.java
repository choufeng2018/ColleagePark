package com.nacity.college.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.college.common_libs.domain.user.UserInfoTo;
import com.nacity.college.R;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.Constant;
import com.nacity.college.base.Event;
import com.nacity.college.base.info.ApartmentInfoHelper;
import com.nacity.college.login.model.LoginModel;
import com.nacity.college.login.presenter.LoginPresenter;
import com.nacity.college.login.view.LoginView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by xzz on 2017/8/29.
 **/

public class RegisterActivity extends BaseActivity implements LoginView {
    @BindView(R.id.city_name)
    TextView cityName;
    @BindView(R.id.apartment_name)
    TextView apartmentName;
    @BindView(R.id.phone_number)
    EditText phoneNumber;
    @BindView(R.id.verification_code)
    EditText verificationCode;
    @BindView(R.id.get_verification)
    TextView getVerification;
    private LoginModel model;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setTitle(Constant.PHONE_REGISTER);
        EventBus.getDefault().register(this);
        initView();
        model = new LoginPresenter(this);

    }

    private void initView() {
//        if (apartmentInfo != null && apartmentInfo.getApartmentInfoTo() != null) {
//            cityName.setText(apartmentInfo.getApartmentInfoTo().getCityName() == null ? "" : apartmentInfo.getApartmentInfoTo().getCityName());
//            apartmentName.setText(apartmentInfo.getGardenName() == null ? "" : apartmentInfo.getGardenName());
//        }
    }

    @OnClick({R.id.finish, R.id.get_verification, R.id.city_layout, R.id.apartment_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.city_layout:
                Intent intent = new Intent(appContext, SelectCityActivity.class);
                startActivity(intent);
                break;
            case R.id.apartment_layout:
//                if (TextUtils.isEmpty(cityName.getText().toString())) {
//                    showErrorMessage(Constant.PLEASE_SELECT_CITY);
//                    return;
//                }
                intent = new Intent(appContext, SelectApartmentActivity.class);
                if(!TextUtils.isEmpty(cityName.getText().toString())){
                    Log.i("22222222222222", "cityName: ");
                    intent.putExtra("CityId", apartmentInfo.getApartmentInfoTo().getCityId()+"");
                }
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.get_verification:
                loadingShow();
                model.getVerificationCode("1", phoneNumber.getText().toString(), getVerification);


                break;
            case R.id.finish:
//                if (TextUtils.isEmpty(cityName.getText().toString())) {
//                    startActivity(new Intent(appContext, SelectCityActivity.class));
//                    goToAnimation(1);
//                }
                if (TextUtils.isEmpty(apartmentName.getText().toString())) {
                    intent = new Intent(appContext, SelectApartmentActivity.class);
                    intent.putExtra("CityId", apartmentInfo.getApartmentInfoTo().getCityId()+"");
                    startActivity(intent);
                    goToAnimation(1);
                } else {

                    loadingShow();
                    model.parkRegister(cityName.getText().toString(), apartmentInfo.getApartmentInfoTo().getGardenId(), phoneNumber.getText().toString(), verificationCode.getText().toString());
                }
                break;
        }
    }

    @Subscribe
    public void getCityAndApartment(Event event) {
        Log.i("222", ": 111");
        if ("SelectCity".equals(event.getType())){
            Log.i("222", ": 112"+event.getMode());
            if(event.getMode()!=null){
                cityName.setText(event.getMode() + "");
            }else{
                cityName.setText("");
            }
        }
        else if ("SelectApartment".equals(event.getType())) {
            apartmentName.setText(event.getMode() + "");

        }
        apartmentInfo = ApartmentInfoHelper.getInstance(appContext);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void getVerificationCodeSuccess(String code) {
        loadingDismiss();
        showSuccess(Constant.GET_VERIFICATION_SUCCESS);

    }

    @Override
    public void registerSuccess() {
        loadingDismiss();
        Intent intent = new Intent(appContext, FinishDataActivity.class);
        startActivity(intent);
        goToAnimation(1);
        finish();
    }

    @Override
    public void loginSuccess(UserInfoTo userInfoTo) {

    }

    @Override
    public void loadingDismissPhone() {
        loadingDismiss();
        getVerification.setEnabled(true);
    }

    @Override
    public void setCountTime(int second) {

            runOnUiThread(() -> {    if (second > 0)
                getVerification.setText(second + Constant.AFTER_VERIFICATION_CODE);
            else {
                getVerification.setText(Constant.RE_SEND_VERIFICATION_CODE);
                getVerification.setEnabled(true);
            }
            });

    }


    @Override
    public void showErrorMessage(String message) {
        super.showErrorMessage(message);
        getVerification.setEnabled(true);
    }
}
