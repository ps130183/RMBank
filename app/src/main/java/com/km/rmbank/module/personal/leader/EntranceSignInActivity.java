package com.km.rmbank.module.personal.leader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.utils.QRCodeUtils;

import butterknife.BindView;

public class EntranceSignInActivity extends BaseActivity {

    @BindView(R.id.iv_entrance_sign_in)
    ImageView ivEntranceSignIn;

    @Override
    protected int getContentView() {
        return R.layout.activity_entrance_sign_in;
    }

    @Override
    protected String getTitleName() {
        return "入场签到";
    }

    @Override
    protected void onCreate() {

        final ActionDto actionDto = getIntent().getParcelableExtra("actionDto");

        setRightBtnClick("签到统计", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String actionId = actionDto.getId();
                Bundle bundle = new Bundle();
                bundle.putString("actionId",actionId);
                toNextActivity(SignInListActivity.class,bundle);
            }
        });
        if (!TextUtils.isEmpty(actionDto.getCode())){
            ivEntranceSignIn.setImageBitmap(QRCodeUtils.createQRCode(this,actionDto.getCode()));
        }
    }
}
