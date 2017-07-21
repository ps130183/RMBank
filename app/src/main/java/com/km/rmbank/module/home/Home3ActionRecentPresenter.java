package com.km.rmbank.module.home;

import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/7/19.
 */

public class Home3ActionRecentPresenter extends PresenterWrapper<Home3ActionRecentContract.View> implements Home3ActionRecentContract.Presenter {

    public Home3ActionRecentPresenter(Home3ActionRecentContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void getActionRecent(final int pageNo) {

        mView.showLoading();
        mApiwrapper.getActionRecentList(pageNo)
                .subscribe(newSubscriber(new Consumer<List<ActionDto>>() {
                    @Override
                    public void accept(@NonNull List<ActionDto> actionDtos) throws Exception {
                        mView.showActionRecent(actionDtos,pageNo);
                    }
                }));

    }
}
