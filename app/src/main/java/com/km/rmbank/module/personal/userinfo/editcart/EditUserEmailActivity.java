package com.km.rmbank.module.personal.userinfo.editcart;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.module.personal.userinfo.EditUserCardActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class EditUserEmailActivity extends BaseActivity {

    @BindView(R.id.et_email)
    EditText etEmail;

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_user_email;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "邮箱地址";
    }
    @Override
    protected void onCreate() {
        setRightBtnClick("保存", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                if (TextUtils.isEmpty(email)){
                    showToast("请填写内容再保存");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("email",email);
                setResult(EditUserCardActivity.REQUEST_CODE_ET_EMAIL,bundle);
            }
        });
        initEtName();
    }

    private void initEtName(){
        String email = getIntent().getStringExtra("email");
        etEmail.setText(email);
    }

    @OnClick(R.id.iv_clear)
    public void clear(View view){
        etEmail.setText("");
    }
}
