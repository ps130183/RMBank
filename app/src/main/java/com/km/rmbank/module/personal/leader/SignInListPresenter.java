package com.km.rmbank.module.personal.leader;

import com.km.rmbank.dto.SignInDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 17/12/28.
 */

public class SignInListPresenter extends PresenterWrapper<SignInListContract.View> implements SignInListContract.Presenter {

    public SignInListPresenter(SignInListContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void loadSignInLists(String actionId) {
        mView.showLoading();
        mApiwrapper.getSignInLists(actionId)
                .subscribe(newSubscriber(new Consumer<List<SignInDto>>() {
                    @Override
                    public void accept(List<SignInDto> signInDtos) throws Exception {
                        mView.showSignInLists(signInDtos);
                    }
                }));
    }
}
