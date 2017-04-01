package com.km.rmbank.module.personal.userinfo.editcart;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.module.personal.userinfo.EditUserCardActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class EditUserPhoneActivity extends BaseActivity {

    @BindView(R.id.et_phone)
    EditText etPhone;

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_user_phone;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "手机号";
    }

    @Override
    protected void onCreate() {
        setRightBtnClick("保存", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = etPhone.getText().toString();
                if (TextUtils.isEmpty(phone) || phone.length() < 11){
                    showToast("请填写正确的手机号");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("phone",phone);
                setResult(EditUserCardActivity.REQUEST_CODE_ET_PHONE,bundle);
            }
        });
        init();
    }

    private void init(){
        String phone = getIntent().getStringExtra("phone");
        etPhone.setText(phone);
    }

    @OnClick(R.id.iv_clear)
    public void clear(View view){
        etPhone.setText("");
    }
}
