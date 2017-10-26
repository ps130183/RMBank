package com.km.rmbank.module.master;

import com.km.rmbank.dto.MasterDto;
import com.km.rmbank.dto.MasterOrderDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 17/10/26.
 */

public class MasterSubscribeOrderPresenter extends PresenterWrapper<MasterSubscribeOrderContract.View> implements MasterSubscribeOrderContract.Presenter {

    public MasterSubscribeOrderPresenter(MasterSubscribeOrderContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void getMasterSubscribeList(final int pageNo) {
        mView.showLoading();
        mApiwrapper.getMasterSubscribeOrderList(pageNo)
                .subscribe(newSubscriber(new Consumer<List<MasterOrderDto>>() {
                    @Override
                    public void accept(@NonNull List<MasterOrderDto> masterOrderDtos) throws Exception {
                        mView.showMasterSubscribeList(masterOrderDtos,pageNo);
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
