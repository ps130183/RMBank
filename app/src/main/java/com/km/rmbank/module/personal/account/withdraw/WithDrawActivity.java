package com.km.rmbank.module.personal.account.withdraw;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.UserBalanceDto;
import com.km.rmbank.dto.WithDrawAccountDto;
import com.km.rmbank.module.personal.account.UserAccountActivity;
import com.km.rmbank.utils.InputFilterUtils;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.ColorUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class WithDrawActivity extends BaseActivity<WithDrawAPresenter> implements WithDrawAContract.View {

    @BindView(R.id.et_money)
    EditText etMoney;

    @BindView(R.id.btn_withdraw)
    Button btnWithdraw;

    @BindView(R.id.tv_type_name)
    TextView tvTypeName;
    @BindView(R.id.tv_account)
    TextView tvAccount;

    @BindView(R.id.tv_balance)
    TextView tvBalance;

    private WithDrawAccountDto withDrawAccountDto;
    private UserBalanceDto balanceDto;

    @Override
    protected int getContentView() {
        return R.layout.activity_with_draw;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "提现";
    }

    @Override
    public WithDrawAPresenter getmPresenter() {
        return new WithDrawAPresenter(this,this);
    }

    @Override
    protected void onCreate() {

        withDrawAccountDto = getIntent().getParcelableExtra("withDrawAccountDto");
        tvTypeName.setText(withDrawAccountDto.getTypeName());
        tvAccount.setText(withDrawAccountDto.getWithdrawNumber());


        etMoney.setFilters(InputFilterUtils.filters2);
        etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)){//提现金额 为空 或为0
                    btnWithdraw.setBackgroundResource(R.drawable.shape_withdraw_btn_click);
                } else {
                    btnWithdraw.setBackgroundResource(R.drawable.shape_withdraw_btn_unclick);
                }
//                btnWithdraw.setBackground(R.drawable.shape_withdraw_btn_click);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 全部提现
     * @param view
     */
    @OnClick(R.id.tv_all_withdraw)
    public void allWithDraw(View view){
        etMoney.setText(balanceDto.getBalance()+"");
    }

    @OnClick(R.id.btn_withdraw)
    public void withDraw(View view){
        String witndrawMoney = etMoney.getText().toString();
        if (TextUtils.isEmpty(witndrawMoney)){
            showToast("请输入提现金额");
            return;
        }
        mPresenter.submitWithdraw(withDrawAccountDto,witndrawMoney);
    }

    @Override
    public void showBalance(UserBalanceDto userBalanceDto) {
        balanceDto = userBalanceDto;
        tvBalance.setText("可用余额 " + userBalanceDto.getBalance() + " 元");
    }

    @Override
    public void withdrawSuccess() {
        showToast("提现申请已提交");
        toNextActivity(UserAccountActivity.class);
    }
}
