package com.km.rmbank.module.nearbyvip;

import com.km.rmbank.dto.NearbyVipDto;
import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/6/5.
 */

public class NearbyVipPresenter extends PresenterWrapper<NearbyVipContract.View> implements NearbyVipContract.Presenter {

    public NearbyVipPresenter(NearbyVipContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {
        mView.initRecyclerView();
    }

    @Override
    public void getNearbyVip(final int pageNo) {
        mView.showLoading();
        mApiwrapper.getNearbyVip(pageNo)
                .subscribe(newSubscriber(new Consumer<List<NearbyVipDto>>() {
                    @Override
                    public void accept(@NonNull List<NearbyVipDto> nearbyVipDtos) throws Exception {
                        mView.getNearbyVipSuccess(nearbyVipDtos,pageNo);
                    }
                }));
    }

    @Override
    public void getUserCardInfo(final String url) {
        mView.showLoading();
        if (Constant.user.isEmpty()){
            return;
        }
        mApiwrapper.getUserCardOnQRCode(url)
                .subscribe(newSubscriber(new Consumer<UserCardDto>() {
                    @Override
                    public void accept(@NonNull UserCardDto userCardDto) throws Exception {
                        String phone = url.split("[?]")[1].split("=")[1];
                        mView.showUserCart(userCardDto,phone);
                    }
                }));
    }
}
