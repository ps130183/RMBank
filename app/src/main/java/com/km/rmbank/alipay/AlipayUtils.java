package com.km.rmbank.alipay;

import android.app.Activity;

import com.alipay.sdk.app.PayTask;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by kamangkeji on 17/4/4.
 */

public class AlipayUtils {

    /**
     * alipay支付
     * @param activity
     * @param orderPayDto
     * @return
     */
    public static Observable toPay(final Activity activity, String orderPayDto){
       Observable observable = Observable.just(orderPayDto)
                .map(new Function<String,ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(@NonNull String s) throws Exception {
                        PayTask payTask = new PayTask(activity);
                        return Observable.just(payTask.pay(s,true));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

}
