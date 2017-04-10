package com.km.rmbank.module.personal.vip;

import com.km.rmbank.api.ApiWrapper;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.MemberTypeDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/4/6.
 */

public class SelectMemberTypePresenter extends PresenterWrapper<SelectMemberTypeContract.View> implements SelectMemberTypeContract.Presenter {
    public SelectMemberTypePresenter(SelectMemberTypeContract.View mView) {
        super(mView);
    }

    @Override
    public void getMemberTypeInfo() {
        mView.showLoading();
        mApiwrapper.getMemberTypeInfo()
                .subscribe(newSubscriber(new Consumer<List<MemberTypeDto>>() {
                    @Override
                    public void accept(@NonNull List<MemberTypeDto> memberTypeDtos) throws Exception {
                        mView.showMemberTypeInfo(memberTypeDtos);
                    }

                }));
    }

    @Override
    public void onCreateView() {
        getMemberTypeInfo();
    }
}
