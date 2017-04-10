package com.km.rmbank.wxapi;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.wxpay.WxUtil;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;


/**
 * 微信支付结果界面
 * @author hfk
 * create at 2016/5/3 10:42
 */
public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    protected int getContentView() {
        return R.layout.pay_result;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "支付结果";
    }

    @Override
    protected void onCreate() {
        api = WxUtil.getWXAPI(this);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    /**
     * 结果回调方法
     * @param resp
     */
    @Override
    public void onResp(BaseResp resp) {
        Logger.d("onPayFinish, errCode = " + resp.errCode);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//            EventBusUtils.post(new EventParam.WXPAYResultEvent(resp));
            finish();
        }
    }


}