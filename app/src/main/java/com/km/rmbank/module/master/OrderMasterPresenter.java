package com.km.rmbank.module.master;

import com.km.rmbank.dto.MasterBannerDto;
import com.km.rmbank.dto.MasterDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 17/10/17.
 */

public class OrderMasterPresenter extends PresenterWrapper<OrderMasterContract.View> implements OrderMasterContract.Presenter {

    public OrderMasterPresenter(OrderMasterContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void getMasterList(final int pageNo) {
        mView.showLoading();
        mApiwrapper.getMasters(pageNo)
                .subscribe(newSubscriber(new Consumer<List<MasterDto>>() {
                    @Override
                    public void accept(@NonNull List<MasterDto> masterDtos) throws Exception {
                        mView.showMastersInfo(masterDtos,pageNo);
                    }
                }));
    }

    @Override
    public void getMasterBannerList() {
        mApiwrapper.getMasterBanners()
                .subscribe(newSubscriber(new Consumer<List<MasterBannerDto>>() {
                    @Override
                    public void accept(@NonNull List<MasterBannerDto> bannerDtos) throws Exception {
                        mView.showMasterBannerList(bannerDtos);
                    }
                }));
    }

    @Override
    public void getMasterInfo(final String id) {
        mView.showLoading();
        mApiwrapper.getMasterInfo(id)
                .subscribe(newSubscriber(new Consumer<MasterDto>() {
                    @Override
                    public void accept(@NonNull MasterDto masterDto) throws Exception {
                        mView.showMasterInfo(masterDto,id);
                    }
                }));
    }
}
