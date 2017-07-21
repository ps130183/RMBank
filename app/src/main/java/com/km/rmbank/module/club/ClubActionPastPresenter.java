package com.km.rmbank.module.club;

import com.km.rmbank.dto.ActionPastDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/7/18.
 */

public class ClubActionPastPresenter extends PresenterWrapper<ClubActionPastContract.View> implements ClubActionPastContract.Presenter {

    public ClubActionPastPresenter(ClubActionPastContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void getActionPasList(String clubId, final int pageNo) {
        mView.showLoading();
        mApiwrapper.getActionPastList(clubId,pageNo)
                .subscribe(newSubscriber(new Consumer<List<ActionPastDto>>() {
                    @Override
                    public void accept(@NonNull List<ActionPastDto> actionPastDtos) throws Exception {
                        mView.showActionPast(actionPastDtos,pageNo);
                    }
                }));
    }
}
