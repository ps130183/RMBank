package com.km.rmbank.utils.retrofit;

import android.content.Context;

import com.km.rmbank.api.ApiWrapper;
import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.ps.androidlib.utils.MToast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by kamangkeji on 17/4/8.
 */

public abstract class PresenterWrapper<V extends BaseView> implements BasePresenter {

    protected V mView;
    protected Context mContext;
    public CompositeDisposable mCompositeSubscription;
    protected ApiWrapper mApiwrapper;

    public PresenterWrapper(V mView) {
        this.mView = mView;
        mContext = mView.getMContext();
        mCompositeSubscription = new CompositeDisposable();
        mApiwrapper = ApiWrapper.getInstance();
    }

    /**
     * 创建观察者
     *
     * @param onNext
     * @param <T>
     * @return
     */
    protected  <T> ResourceSubscriber<T> newSubscriber(final Consumer<? super T> onNext) {
        return new ResourceSubscriber<T>() {

            @Override
            public void onComplete() {
                mView.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof RetrofitUtil.APIException) {
                    RetrofitUtil.APIException exception = (RetrofitUtil.APIException) e;
                    MToast.showToast(mContext,exception.message);
                } else if (e instanceof SocketTimeoutException) {
                    MToast.showToast(mContext,e.getMessage());
                } else if (e instanceof ConnectException) {
                    MToast.showToast(mContext,e.getMessage());
                }
                if ("timeout".equals(e.getMessage()) || "connect timed out".equals(e.getMessage())){
                    MToast.showToast(mContext,"请求超时，请稍后再试");
                } else {
//                    LogUtils.e(String.valueOf(e.getMessage()));
//                    Logger.e(e.getMessage());
                    e.printStackTrace();
                }
                mView.hideLoading();
            }

            @Override
            public void onNext(T t) {
                if (!mCompositeSubscription.isDisposed()) {
                    try {
                        onNext.accept(t);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        };
    }

    /**
     * 创建观察者
     *
//     * @param <T>
     * @return
     */
//    protected  <T> ResourceSubscriber<T> newFileUploadSubscriber(final Consumer<? super T> onUploadSuccess,
//                                                                 final Consumer<Integer> onProgress) {
//        return new FileUploadingListener<T>() {
//            @Override
//            public void onUpLoadSuccess(T t) {
//                try {
//                    onUploadSuccess.accept(t);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onUpLoadFail(Throwable e) {
//
//            }
//
//            @Override
//            public void onProgress(int progress) {
//                try {
//                    onProgress.accept(progress);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//    }



    @Override
    public void clearSubscription() {
        mCompositeSubscription.clear();
    }


}
