package com.km.rmbank.module.personal.userinfo.editcart;

import com.km.rmbank.api.ApiWrapper;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.IndustryDto;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by kamangkeji on 17/3/30.
 */

public class ResourcePresenter implements ResourceContract.Presenter {

    private ResourceContract.View view;
    private BaseActivity mActivity;

    private ApiWrapper apiWrapper;

    public ResourcePresenter(ResourceContract.View view, BaseActivity mActivity) {
        this.view = view;
        this.mActivity = mActivity;
        apiWrapper = ApiWrapper.getInstance();
    }

    @Override
    public void loadIndustryData() {
        view.showLoading();
        apiWrapper.getIndustryList()
                .subscribe(mActivity.newSubscriber(new Action1<List<IndustryDto>>() {
                    @Override
                    public void call(List<IndustryDto> industryEntities) {
                        view.showIndustry(industryEntities);
                    }
                }));
    }

    @Override
    public void onCreateView() {
        loadIndustryData();
    }
}
