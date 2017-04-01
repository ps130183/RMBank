package com.km.rmbank.module.personal.userinfo.editcart;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.module.personal.userinfo.EditUserCardActivity;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.OnClick;

public class EditUserCompanyActivity extends BaseActivity {

    @BindView(R.id.et_company)
    EditText etCompany;

    @BindView(R.id.tv_char_number)
    TextView tvCharNumber;

    private int maxLength = 20;

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_user_company;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "公司";
    }

    @Override
    protected void onCreate() {
        setRightBtnClick("保存", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String company = etCompany.getText().toString();
                if (TextUtils.isEmpty(company)){
                    showToast("请填写公司名称");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("company",company);
                setResult(EditUserCardActivity.REQUEST_CODE_ET_COMPANY,bundle);
            }
        });
        init();
    }

    private void init(){
        String company = getIntent().getStringExtra("company");
        etCompany.setText(company);

        etCompany.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Logger.d("s.length = " + s.length() + "  start = " + start + " before = " + before + " count = " +count);
                tvCharNumber.setText(s.length()+"/"+maxLength);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.iv_clear)
    public void clear(View view){
        etCompany.setText("");
    }
}
