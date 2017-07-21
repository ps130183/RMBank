package com.km.rmbank.module.club.recent;

import com.km.rmbank.dto.ActionMemberDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/7/14.
 */

public class ActionJoinMemberPresenter extends PresenterWrapper<ActionJoinMemberContract.View> implements ActionJoinMemberContract.Presenter {
    public ActionJoinMemberPresenter(ActionJoinMemberContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void getActionMemberList(String actionId, final int pageNo) {
        mView.showLoading();
        mApiwrapper.getActionMemberList(actionId,pageNo)
                .subscribe(newSubscriber(new Consumer<List<ActionMemberDto>>() {
                    @Override
                    public void accept(@NonNull List<ActionMemberDto> actionMemberDtos) throws Exception {
                        mView.showActionMemberList(actionMemberDtos,pageNo);
                    }
                }));
    }
}
