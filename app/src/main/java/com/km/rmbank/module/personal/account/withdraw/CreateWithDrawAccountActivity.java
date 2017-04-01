package com.km.rmbank.module.personal.account.withdraw;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.WithDrawAccountDto;

import java.util.List;

import butterknife.BindView;

public class CreateWithDrawAccountActivity extends BaseActivity<WithDrawPresenter> implements WithDrawContract.View {

    private WithDrawAccountDto withDrawAccountDto;

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_type_name)
    EditText etTypeName;
    @BindView(R.id.et_account)
    EditText etAccount;

    private boolean isCreate;
    @Override
    protected int getContentView() {
        return R.layout.activity_create_with_draw_account;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        withDrawAccountDto = getIntent().getParcelableExtra("withDrawAccountDto");
        if (withDrawAccountDto == null){
            withDrawAccountDto = new WithDrawAccountDto();
            isCreate = true;
            return "新建账户";
        } else {
            isCreate = false;
            return "编辑信息";
        }
    }

    @Override
    public WithDrawPresenter getmPresenter() {
        return new WithDrawPresenter(this,this);
    }

    @Override
    protected void onCreate() {
        setRightBtnClick("保存", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withDrawAccountDto.setName(etName.getText().toString());
                withDrawAccountDto.setWithdrawPhone(etPhone.getText().toString());
                withDrawAccountDto.setTypeName(etTypeName.getText().toString());
                withDrawAccountDto.setWithdrawNumber(etAccount.getText().toString());
                if (withDrawAccountDto.isEmpty()){
                    showToast("请将信息补充完整");
                    return;
                }
                if (isCreate){
                    mPresenter.createWithDrawAccount(withDrawAccountDto);
                } else {
                    mPresenter.updateWithDrawAccount(withDrawAccountDto);
                }
            }
        });

        if (!isCreate){//编辑
            etName.setText(withDrawAccountDto.getName());
            etPhone.setText(withDrawAccountDto.getWithdrawPhone());
            etTypeName.setText(withDrawAccountDto.getTypeName());
            etAccount.setText(withDrawAccountDto.getWithdrawNumber());
        }
    }

    @Override
    public void creatOrUpdateSuccess() {
        showToast("保存成功");
        finish();
    }

    @Override
    public void showWithDrawList(List<WithDrawAccountDto> withDrawAccountDtos) {

    }

    @Override
    public void deleteSuccess(WithDrawAccountDto withDrawAccountDto) {

    }
}
