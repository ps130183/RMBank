package com.km.rmbank.module.club.recent;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.ActionDto;

import butterknife.BindView;
import butterknife.OnClick;

public class ActionApplyActivity extends BaseActivity<ActionRecentInfoPresenter> implements ActionRecentInfoContract.View {
    @BindView(R.id.ll_toolbar_top)
    LinearLayout llToolbarTop;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.title)
    TextView mTitle;


    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    private String actionId;

    @Override
    protected int getContentView() {
        return R.layout.activity_action_apply;
    }

    @Override
    protected String getTitleName() {
        return "活动报名";
    }

    @Override
    public ActionRecentInfoPresenter getmPresenter() {
        return new ActionRecentInfoPresenter(this);
    }

    @Override
    protected void onCreate() {
        llToolbarTop.setBackgroundColor(ContextCompat.getColor(this,R.color.color_white));
        mToolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.color_white));
        mTitle.setTextColor(ContextCompat.getColor(this,R.color.color_block));

        setLeftIconClick(R.mipmap.ic_left_back1, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        actionId = getIntent().getStringExtra("actionId");
    }

    @Override
    public void showActionRecentInfo(ActionDto actionDto) {

    }

    @Override
    public void applyActionSuccess() {
        showToast("报名成功");
        finish();
    }

    @Override
    public void followClubSuccess(boolean isFollow) {

    }

    @OnClick(R.id.btn_apply)
    public void clickApply(View view){
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)){
            showToast("请补全报名信息");
            return;
        }
        mPresenter.applyAction(actionId,name,phone);
    }
}
