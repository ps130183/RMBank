package com.km.rmbank.module.personal.userinfo.editcart;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.module.personal.userinfo.EditUserCardActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class EditUserCompanyIntroActivity extends BaseActivity {

    @BindView(R.id.et_company_intro)
    EditText etCompanyIntro;

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_user_company_intro;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "职位";
    }

    @Override
    protected void onCreate() {
        setRightBtnClick("保存", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String companyIntro = etCompanyIntro.getText().toString();
                if (TextUtils.isEmpty(companyIntro)){
                    showToast("请用一句话介绍您的企业");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("companyIntro",companyIntro);
                setResult(EditUserCardActivity.REQUEST_CODE_ET_COMPANY_INTRO,bundle);
            }
        });
        init();
    }

    private void init(){
        String companyIntro = getIntent().getStringExtra("companyIntro");
        etCompanyIntro.setText(companyIntro);
    }

    @OnClick(R.id.iv_clear)
    public void clear(View view){
        etCompanyIntro.setText("");
    }
}
