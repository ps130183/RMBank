package com.km.rmbank.module.club.recent;

import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.ActionMemberDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kamangkeji on 17/7/14.
 */

public class ActionRecentInfoPresenter  extends PresenterWrapper<ActionRecentInfoContract.View> implements ActionRecentInfoContract.Presenter {

    public ActionRecentInfoPresenter(ActionRecentInfoContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void getActionRecentInfo(String actionId) {
        mView.showLoading();
        mApiwrapper.getActionRecentInfo(actionId)
                .subscribe(newSubscriber(new Consumer<ActionDto>() {
                    @Override
                    public void accept(@NonNull ActionDto actionDto) throws Exception {
                        mView.showActionRecentInfo(actionDto);
                    }
                }));
    }

    @Override
    public void applyAction(String activityId,String name, String phone) {
        mView.showLoading();
        mApiwrapper.applyAction(activityId,name,phone)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.applyActionSuccess();
                    }
                }));
    }

    @Override
    public void followClub(String clubId, final boolean isFollow) {
        mView.showLoading();
        mApiwrapper.followGodos("",clubId)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.followClubSuccess(isFollow);
                    }
                }));
    }

}
