package com.km.rmbank.module.club;

import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/7/13.
 */

public class ClubActionRecentPresenter extends PresenterWrapper<ClubActionRecentContract.View> implements ClubActionRecentContract.Presenter {

    public ClubActionRecentPresenter(ClubActionRecentContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void getActionRecent(String clubId, final int pageNo) {
        mView.showLoading();
        mApiwrapper.getActionRecentList(clubId,pageNo)
                .subscribe(newSubscriber(new Consumer<List<ActionDto>>() {
                    @Override
                    public void accept(@NonNull List<ActionDto> actionDtos) throws Exception {
                            mView.showActionRecent(actionDtos,pageNo);
                    }
                }));
    }
}
