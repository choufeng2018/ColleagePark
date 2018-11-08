package com.nacity.college.property;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.RatingBar;
import android.widget.TextView;

import com.college.common_libs.domain.property.ServiceMainTo;
import com.nacity.college.R;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by xzz on 2017/10/12.
 **/

public class ReadEvaluateActivity extends BaseActivity {
    @BindView(R.id.evaluate_content)
    TextView evaluateContent;
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_evluate);
        ButterKnife.bind(this);
        setTitle(Constant.EVALUATE);

        setView();
    }

    private void setView() {
        ServiceMainTo mainTo= (ServiceMainTo) getIntent().getSerializableExtra("ServiceMainTo");
        if (!TextUtils.isEmpty(mainTo.getEvaluateDesc())) {
            evaluateContent.setText(mainTo.getEvaluateDesc());
        }
        if (mainTo.getEvaluateServiceAttitude() != 0) {
            attitudeBar.setRating(mainTo.getEvaluateServiceAttitude());
        }
        if (mainTo.getEvaluateFinishSpeed() != 0) {
            speedBar.setRating(mainTo.getEvaluateFinishSpeed());
        }
        if (mainTo.getEvaluatePleasedDegree() != 0) {
            satisfactionBar.setRating(mainTo.getEvaluatePleasedDegree());
        }

        if (mainTo.getEvaluateServiceAttitude() !=0) {
            if (mainTo.getEvaluateServiceAttitude() == 1.0f) {
                attitudeText.setText("不满");
            } else if (mainTo.getEvaluateServiceAttitude() == 2.0f) {
                attitudeText.setText("差评");
            } else if (mainTo.getEvaluateServiceAttitude() == 3.0f) {
                attitudeText.setText("一般");
            } else if (mainTo.getEvaluateServiceAttitude() == 4.0f) {
                attitudeText.setText("满意");
            } else if (mainTo.getEvaluateServiceAttitude() == 5.0f) {
                attitudeText.setText("惊喜");
            }
        }
        if (mainTo.getEvaluateFinishSpeed()!=0 ) {
            if (mainTo.getEvaluateFinishSpeed() == 1.0f) {
                speedText.setText("不满");
            } else if (mainTo.getEvaluateFinishSpeed() == 2.0f) {
                speedText.setText("差评");
            } else if (mainTo.getEvaluateFinishSpeed() == 3.0f) {
                speedText.setText("一般");
            } else if (mainTo.getEvaluateFinishSpeed() == 4.0f) {
                speedText.setText("满意");
            } else if (mainTo.getEvaluateFinishSpeed() == 5.0f) {
                speedText.setText("惊喜");
            }
        }
        if (mainTo.getEvaluatePleasedDegree()!=0 ) {
            if (mainTo.getEvaluatePleasedDegree() == 1.0f) {
                satisfactionText.setText("不满");
            } else if (mainTo.getEvaluatePleasedDegree() == 2.0f) {
                satisfactionText.setText("差评");
            } else if (mainTo.getEvaluatePleasedDegree() == 3.0f) {
                satisfactionText.setText("一般");
            } else if (mainTo.getEvaluatePleasedDegree() == 4.0f) {
                satisfactionText.setText("满意");
            } else if (mainTo.getEvaluatePleasedDegree() == 5.0f) {
                satisfactionText.setText("惊喜");
            }
        }


    }
}
