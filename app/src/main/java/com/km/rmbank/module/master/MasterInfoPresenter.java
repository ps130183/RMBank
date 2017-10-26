package com.km.rmbank.module.master;

import com.km.rmbank.dto.MasterWorkDto;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 17/10/17.
 */

public class MasterInfoPresenter extends PresenterWrapper<MasterInfoContract.View> implements MasterInfoContract.Presenter {

    public MasterInfoPresenter(MasterInfoContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void getMasterWork(String id, final int pageNo) {
        mView.showLoading();
        mApiwrapper.getMasterWorkList(id,pageNo)
                .subscribe(newSubscriber(new Consumer<List<MasterWorkDto>>() {
                    @Override
                    public void accept(@NonNull List<MasterWorkDto> masterWorkDtos) throws Exception {
                        mView.showMasterWorkList(masterWorkDtos,pageNo);
                    }
                }));
    }

    @Override
    public void createMasterOrder(String macaId, String macaWorksId, String money) {
        mView.showLoading();
        mApiwrapper.createOrderMaster(macaId,macaWorksId,money)
                .subscribe(newSubscriber(new Consumer<PayOrderDto>() {
                    @Override
                    public void accept(@NonNull PayOrderDto payOrderDto) throws Exception {
                        mView.createOrderSuccess(payOrderDto);
                    }
                }));
    }


}
