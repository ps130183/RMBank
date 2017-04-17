package com.km.rmbank.module.actionarea;

import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/4/12.
 */

public class ActionPresenter extends PresenterWrapper<ActionContract.View> implements ActionContract.Presenter {

    public ActionPresenter(ActionContract.View mView) {
        super(mView);
    }

    @Override
    public void getActionList(final int pageNo) {
//        mView.showLoading();
        mApiwrapper.getActionList(pageNo)
                .subscribe(newSubscriber(new Consumer<List<ActionDto>>() {
                    @Override
                    public void accept(@NonNull List<ActionDto> actionDtos) throws Exception {
                        mView.getActionListSuccess(actionDtos,pageNo);
                    }
                }));
    }

    @Override
    public void onCreateView() {
        mView.initAction();
    }
}
