package com.km.rmbank.module.personal.userinfo.editcart;

import com.km.rmbank.api.ApiWrapper;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.module.personal.userinfo.EditUserCartContract;

import rx.functions.Action1;

/**
 * Created by kamangkeji on 17/3/31.
 */

public class EditUserCardPresenter implements EditUserCartContract.Presenter {

    private EditUserCartContract.View view;
    private BaseActivity activity;

    private ApiWrapper mApiwrapper;
    public EditUserCardPresenter(EditUserCartContract.View view, BaseActivity activity) {
        this.view = view;
        this.activity = activity;
        mApiwrapper = ApiWrapper.getInstance();
    }

    @Override
    public void getUserCard() {
        view.showLoading();
        mApiwrapper.getUserCard()
                .subscribe(activity.newSubscriber(new Action1<UserCardDto>() {
                    @Override
                    public void call(UserCardDto userCardDto) {
                        view.showUserCard(userCardDto);
                    }
                }));
    }

    @Override
    public void createUserCard(UserCardDto userCardDto) {
        view.showLoading();
        mApiwrapper.createUserCart(userCardDto)
                .subscribe(activity.newSubscriber(new Action1<UserCardDto>() {
                    @Override
                    public void call(UserCardDto userCardDto) {
                        view.createUserCardSuccess(userCardDto);
                    }
                }));
    }

    @Override
    public void onCreateView() {
        getUserCard();
    }
}
