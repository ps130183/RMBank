package com.km.rmbank.module.personal.mycontact;

import com.km.rmbank.dto.MyFriendsDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/6/8.
 */
public class MyContactPresenter extends PresenterWrapper<MyContactContract.View> implements MyContactContract.Presenter {

    public MyContactPresenter(MyContactContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {
        mView.initRecyclerview();
    }

    @Override
    public void getMyFriends() {
        mView.showLoading();
        mApiwrapper.getMyFriends()
                .subscribe(newSubscriber(new Consumer<List<MyFriendsDto>>() {
                    @Override
                    public void accept(@NonNull List<MyFriendsDto> myFriendsDtos) throws Exception {
                        mView.showMyContact(myFriendsDtos);
                    }
                }));
    }
}
