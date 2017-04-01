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

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.utils.InputFilterUtils;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.ColorUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class WithDrawActivity extends BaseActivity {

    @BindView(R.id.et_money)
    EditText etMoney;

    @BindView(R.id.btn_withdraw)
    Button btnWithdraw;

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
    protected void onCreate() {
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
        etMoney.setText("6.00");
    }

    @OnClick(R.id.btn_withdraw)
    public void withDraw(View view){
        String witndrawMoney = etMoney.getText().toString();
        if (!TextUtils.isEmpty(witndrawMoney)){
            showToast("提现金额：" + witndrawMoney);
            toNextActivity(WithDrawDetailsActivity.class);
        }
    }

}
