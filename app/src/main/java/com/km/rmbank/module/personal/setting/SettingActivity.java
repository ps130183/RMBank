package com.km.rmbank.module.personal.setting;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.UserDto;
import com.km.rmbank.event.DownloadAppEvent;
import com.km.rmbank.module.login.LoginActivity;
import com.km.rmbank.module.personal.AgreementActivity;
import com.km.rmbank.utils.Constant;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.DialogUtils;
import com.ps.androidlib.utils.EventBusUtils;
import com.ps.androidlib.utils.SPUtils;

import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "设置";
    }

    @Override
    protected void onCreate() {

    }

    @OnClick(R.id.tv_clear_cache)
    public void clearCache(View view){
        SPUtils.getInstance().clear();
        AppUtils.executeOnUIThread(3000, new AppUtils.UIThread() {
            @Override
            public void onUIThread() {
                showToast("缓存已清空");
            }
        });
    }

    @OnClick(R.id.tv_push_message_setting)
    public void pushMessageSetting(View view){
        showToast("设置缓存推送消息");
    }

    @OnClick(R.id.tv_about)
    public void aboutMe(View view){
        showToast("关于我们");
        toNextActivity(AboutMeActivity.class);
    }

    @OnClick(R.id.tv_user_agreement)
    public void userAgreement(View view){
        Bundle bundle = new Bundle();
        bundle.putString("titleName","用户协议");
        bundle.putString("agreementUrl","/member/agreement/view");
        toNextActivity(AgreementActivity.class,bundle);
    }

    @OnClick(R.id.tv_logout)
    public void logout(View view){
        DialogUtils.showDefaultAlertDialog("是否要退出登录", new DialogUtils.ClickListener() {
            @Override
            public void clickConfirm() {
//                showToast("退出登录");
                Constant.user.clear();
                toNextActivity(LoginActivity.class);
            }
        });
    }

    /**
     * 检测app版本
     * @param view
     */
    @OnClick(R.id.tv_update)
    public void updateApp(View view){
        EventBusUtils.post(new DownloadAppEvent(this));
    }
}
