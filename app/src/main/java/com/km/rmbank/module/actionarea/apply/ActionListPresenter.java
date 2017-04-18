package com.km.rmbank.module.actionarea.apply;

import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/4/18.
 */

public class ActionListPresenter extends PresenterWrapper<ActionListContract.View> implements ActionListContract.Presenter {

    public ActionListPresenter(ActionListContract.View mView) {
        super(mView);
    }

    @Override
    public void getActionList(final int pageNo) {
        mView.showLoading();
        mApiwrapper.getActionList(pageNo)
                .subscribe(newSubscriber(new Consumer<List<ActionDto>>() {
                    @Override
                    public void accept(@NonNull List<ActionDto> actionDtos) throws Exception {
                        mView.showActionList(actionDtos,pageNo);
                    }
                }));
    }

    @Override
    public void onCreateView() {
        mView.initRecyclerview();
    }
}
