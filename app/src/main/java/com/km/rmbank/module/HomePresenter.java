package com.km.rmbank.module;

import com.km.rmbank.dto.ShareDto;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/5/15.
 */

public class HomePresenter extends PresenterWrapper<HomeContract.View> implements HomeContract.Presenter {

    public HomePresenter(HomeContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {
        getShareContent();
    }

    @Override
    public void getShareContent() {
        if (Constant.user.isEmpty()){
            return;
        }
        mApiwrapper.getShareContent()
                .subscribe(newSubscriber(new Consumer<ShareDto>() {
                    @Override
                    public void accept(@NonNull ShareDto shareDto) throws Exception {
                        mView.showShareContent(shareDto);
                    }
                }));
    }
}
