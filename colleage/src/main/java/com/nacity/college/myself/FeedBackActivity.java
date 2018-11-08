package com.nacity.college.myself;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.nacity.college.R;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.Constant;
import com.nacity.college.base.utils.StatuBarUtil;
import com.nacity.college.myself.model.FeedBackModel;
import com.nacity.college.myself.presenter.FeedBackPresenter;
import com.nacity.college.myself.view.FeedBackView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xzz on 2017/7/6.
 **/

public class FeedBackActivity extends BaseActivity implements FeedBackView {

    @BindView(R.id.feed_back_content)
    EditText feedBackContent;
    @BindView(R.id.contact)
    EditText contact;
    private FeedBackModel presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        StatuBarUtil.setStatueBarTextColor(getWindow());
        ButterKnife.bind(this);
        setTitle(Constant.USER_FEEDBACK);
        presenter = new FeedBackPresenter(this);

    }

    @Override
    public void feedbackSuccess() {
        loadingDismiss();
        showSuccess(Constant.FEEDBACK_SUCCESS);
        new Handler().postDelayed(() -> runOnUiThread(this::finish),2000);
        finish();
    }


    @OnClick(R.id.submit)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.submit:
                presenter.feedback(feedBackContent.getText().toString(),contact.getText().toString());
                loadingShow();
                break;
        }
    }
}
