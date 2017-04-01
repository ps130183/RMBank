package com.km.rmbank.module.personal.team;

import com.km.rmbank.api.ApiWrapper;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.entity.TeamEntity;
import com.km.rmbank.dto.UserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by kamangkeji on 17/3/30.
 */

public class MyTeamPresenter implements MyTeamConteact.Presenter {

    private MyTeamConteact.View view;
    private BaseActivity activity;

    private ApiWrapper mApiWrapper;

    private String[] teams = {"已邀请合伙人","已邀请体验式","已邀请普通用户"};

    public MyTeamPresenter(MyTeamConteact.View view, BaseActivity activity) {
        this.view = view;
        this.activity = activity;
        mApiWrapper = ApiWrapper.getInstance();
    }

    @Override
    public void getMyTeamData() {

        List<TeamEntity> teamEntities = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < teams.length; i++){
            List<UserDto> userEntities = new ArrayList<>();
            for (int j = 0; j < random.nextInt(5); j++){
                userEntities.add(new UserDto());
            }
            teamEntities.add(new TeamEntity(teams[i],userEntities));
        }

        view.showMyTeam(teamEntities);

    }

    @Override
    public void onCreateView() {
        view.initRecycler();
        getMyTeamData();
    }
}
