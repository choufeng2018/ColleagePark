package com.nacity.college.door;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.college.common_libs.domain.door.ApplyDoorRecordTo;
import com.nacity.college.R;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.Constant;
import com.nacity.college.base.utils.DateUtil;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xzz on 2018/1/28.
 **/

public class ApplyDoorDetailActivity extends BaseActivity {
    @BindView(R.id.apply_agree)
    View applyAgree;
    @BindView(R.id.apply_waite)
    View applyWaite;
    @BindView(R.id.apply_refuse)
    View applyRefuse;
    @BindView(R.id.apply_time)
    TextView applyTime;
    @BindView(R.id.apply_door)
    TextView applyDoor;
    @BindView(R.id.apply_status)
    TextView applyStatus;
    @BindView(R.id.examine_time)
    TextView examineTime;
    @BindView(R.id.examine_status)
    TextView examineStatus;
    @BindView(R.id.have_door)
    TextView haveDoor;
    @BindView(R.id.layout_examine)
    AutoLinearLayout layoutExamine;
    private ApplyDoorRecordTo recordTo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_door_record_detail);
        ButterKnife.bind(this);
        setTitle(Constant.APPLY_RECORD_DETAIL);
        getIntentData();
        setView();
    }


    private void getIntentData() {
        recordTo = (ApplyDoorRecordTo) getIntent().getSerializableExtra("ApplyDoorRecordTo");
    }

    @SuppressLint("SetTextI18n")
    private void setView() {
        if (recordTo != null) {
            applyTime.setText(Constant.APPLY_TIME + DateUtil.getDateString(recordTo.getCreateTime(), DateUtil.mDateTimeFormatString));
            applyDoor.setText(Constant.APPLY_DOOR_DETAIL+ recordTo.getDoorNameConcat());
            String status = null;
            if (recordTo.getApplyStatus() == 0) {
                status = "<font color='#a9a9a9'>申请状态：</font>" + "<font color='red'>申请中</font>";
                applyWaite.setVisibility(View.VISIBLE);
                layoutExamine.setVisibility(View.INVISIBLE);
            } else if (recordTo.getApplyStatus() == 1) {
                status = "<font color='#a9a9a9'>申请状态：</font>" + "<font color='#82bbfa'>已同意</font>";
                applyAgree.setVisibility(View.VISIBLE);
            } else if (recordTo.getApplyStatus() == 2) {
                status = "<font color='#a9a9a9'>申请状态：</font>" + "<font color='#a9a9a9'>  已拒绝</font>";
                applyRefuse.setVisibility(View.VISIBLE);
            }
            applyStatus.setText(Html.fromHtml(status));
            if (recordTo.getApplyStatus() != 0) {
                if (recordTo.getCreateTime() == null)
                    examineTime.setText(Constant.NO_CHECK_TIME);
                else
                    examineTime.setText(DateUtil.getDateString(recordTo.getCreateTime(), DateUtil.mDateTimeFormatString));
            }

            if (recordTo.getApplyStatus() == 0) {
                status = "<font color='#a9a9a9'>审核状态：</font>" + "<font color='red'>申请中</font>";

            } else if (recordTo.getApplyStatus() == 1) {
                status = "<font color='#a9a9a9'>审核状态：</font>" + "<font color='#82bbfa'>已同意</font>";
                haveDoor.setText(Constant.TURN_ON_DOOR+ recordTo.getDoorNamePassConcat());
            } else if (recordTo.getApplyStatus() == 2) {
                status = "<font color='#000000'>审核状态 : </font>" + "<font color='#a9a9a9'>  已拒绝</font>";
                haveDoor.setText(Constant.REFUSE_REASON_COLON+ (TextUtils.isEmpty(recordTo.getApplyDesc() )? "暂无" : recordTo.getApplyDesc()));
            }
            examineStatus.setText(Html.fromHtml(status));


        }
    }
}
