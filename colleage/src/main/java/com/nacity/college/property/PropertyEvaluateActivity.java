package com.nacity.college.property;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nacity.college.R;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.BaseView;
import com.nacity.college.base.Constant;
import com.nacity.college.property.model.EvaluateModel;
import com.nacity.college.property.presenter.EvaluatePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xzz on 2017/10/12.
 **/

public class PropertyEvaluateActivity extends BaseActivity<Boolean> implements BaseView<Boolean> {
    @BindView(R.id.evaluate_content)
    EditText evaluateContent;
    @BindView(R.id.evaluate_content_desc)
    TextView evaluateContentDesc;
    @BindView(R.id.attitude_bar)
    RatingBar attitudeBar;
    @BindView(R.id.attitude_text)
    TextView attitudeText;
    @BindView(R.id.speed_bar)
    RatingBar speedBar;
    @BindView(R.id.speed_text)
    TextView speedText;
    @BindView(R.id.satisfaction_bar)
    RatingBar satisfactionBar;
    @BindView(R.id.satisfaction_text)
    TextView satisfactionText;
    private EvaluateModel model;
    private int attitudeRating = 5;
    private int speedRating = 5;
    private int satisfactionRating = 5;
    private Handler handler=new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_evluate);
        ButterKnife.bind(this);
        setTitle(Constant.EVALUATE);
        model = new EvaluatePresenter(this);
        setView();
    }

    private void setView() {
         attitudeBar.setStepSize(1);
        attitudeBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (rating < 2.0f) {
                attitudeBar.setRating(1.0f);
            }
            if (rating == 1.0f) {
                attitudeText.setText("不满");
            } else if (rating == 2.0f) {
                attitudeText.setText("差评");
            } else if (rating == 3.0f) {
                attitudeText.setText("一般");
            } else if (rating == 4.0f) {
                attitudeText.setText("满意");
            } else if (rating == 5.0f) {
                attitudeText.setText("惊喜");
            }
            attitudeRating = (int) rating;
        });
        speedBar.setStepSize(1);
        speedBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (rating < 2.0f) {
                speedBar.setRating(1.0f);
            }
            if (rating == 1.0f) {
                speedText.setText("不满");
            } else if (rating == 2.0f) {
                speedText.setText("差评");
            } else if (rating == 3.0f) {
                speedText.setText("一般");
            } else if (rating == 4.0f) {
                speedText.setText("满意");
            } else if (rating == 5.0f) {
                speedText.setText("惊喜");
            }
            speedRating = (int) rating;
        });
        satisfactionBar.setStepSize(1);
        satisfactionBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (rating < 2.0f) {
                satisfactionBar.setRating(1.0f);
            }
            if (rating == 1.0f) {
                satisfactionText.setText("不满");
            } else if (rating == 2.0f) {
                satisfactionText.setText("差评");
            } else if (rating == 3.0f) {
                satisfactionText.setText("一般");
            } else if (rating == 4.0f) {
                satisfactionText.setText("满意");
            } else if (rating == 5.0f) {
                satisfactionText.setText("惊喜");
            }
            satisfactionRating = (int) rating;
        });

        setInputNumber();

    }

    private void setInputNumber() {
        evaluateContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                evaluateContentDesc.setText("还可以输入"+(200-evaluateContent.getText().toString().length()+"字"));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    public void getDataSuccess(Boolean data) {
        loadingDismiss();
        showInfo(Constant.EVALUATE_SUCCESS);
        handler.postDelayed(() -> {
            Intent intent=new Intent(appContext,ServiceDetailActivity.class);
            intent.putExtra("ServiceId",getIntent().getStringExtra("ServiceId"));
            intent.putExtra("Type", getIntent().getStringExtra("Type"));
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            goToAnimation(2);
        },2000);


    }

    @OnClick(R.id.submit)
    public void onViewClicked() {
        loadingShow();
        model.submitEvaluate(getIntent().getStringExtra("ServiceId"), attitudeRating, speedRating, satisfactionRating, evaluateContent.getText().toString());
    }
}
