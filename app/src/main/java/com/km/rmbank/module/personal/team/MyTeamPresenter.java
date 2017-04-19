package com.km.rmbank.module.personal.team;

import com.km.rmbank.api.ApiWrapper;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.MyTeamDto;
import com.km.rmbank.entity.TeamEntity;
import com.km.rmbank.dto.UserDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/3/30.
 */

public class MyTeamPresenter extends PresenterWrapper<MyTeamConteact.View> implements MyTeamConteact.Presenter {

    private String[] teams = {"已邀请合伙人","已邀请体验式","已邀请普通用户"};

    public MyTeamPresenter(MyTeamConteact.View mView) {
        super(mView);
    }


    @Override
    public void getMyTeamData() {
        mView.showLoading();
        mApiwrapper.getMyTeam()
                .subscribe(newSubscriber(new Consumer<List<MyTeamDto>>() {
                    @Override
                    public void accept(@NonNull List<MyTeamDto> myTeamDtos) throws Exception {
                        mView.showMyTeam(myTeamDtos);
                    }
                }));

    }

    @Override
    public void onCreateView() {
        mView.initRecycler();
        getMyTeamData();
    }
}
