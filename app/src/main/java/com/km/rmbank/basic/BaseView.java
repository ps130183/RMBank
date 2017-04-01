package com.km.rmbank.basic;

/**
 * Created by kamangkeji on 17/1/20.
 */

public interface BaseView<P extends BasePresenter> {
    P getmPresenter();
    void showLoading();
    void hideLoading();
}
