package com.km.rmbank.module.club.past;

import com.km.rmbank.dto.ActionPastDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/7/19.
 */

public class ActionPastDetailPresenter extends PresenterWrapper<ActionPastDetailContract.View> implements ActionPastDetailContract.Presenter {

    public ActionPastDetailPresenter(ActionPastDetailContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void getActionPastDetails(String id) {
        mView.showLoading();
        mApiwrapper.getActionPastDetail(id)
                .subscribe(newSubscriber(new Consumer<ActionPastDto>() {
                    @Override
                    public void accept(@NonNull ActionPastDto actionPastDto) throws Exception {
                        mView.showActionPastDetails(actionPastDto);
                    }
                }));
    }
}
