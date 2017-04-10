package com.km.rmbank.module.personal.userinfo.editcart;

import com.km.rmbank.api.ApiWrapper;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.IndustryDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


/**
 * Created by kamangkeji on 17/3/30.
 */

public class ResourcePresenter extends PresenterWrapper<ResourceContract.View> implements ResourceContract.Presenter {

    public ResourcePresenter(ResourceContract.View mView) {
        super(mView);
    }

    @Override
    public void loadIndustryData() {
        mView.showLoading();
        mApiwrapper.getIndustryList()
                .subscribe(newSubscriber(new Consumer<List<IndustryDto>>() {
                    @Override
                    public void accept(@NonNull List<IndustryDto> industryDtos) throws Exception {
                        mView.showIndustry(industryDtos);
                    }
                }));
    }

    @Override
    public void onCreateView() {
        loadIndustryData();
    }
}
