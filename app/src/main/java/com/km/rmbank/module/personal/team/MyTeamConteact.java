package com.km.rmbank.module.personal.team;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.entity.TeamEntity;

import java.util.List;

/**
 * Created by kamangkeji on 17/3/30.
 */

public interface MyTeamConteact {
    interface View extends BaseView{
        void initRecycler();
        void showMyTeam(List<TeamEntity> teamEntities);
    }
    interface Presenter extends BasePresenter{
        void getMyTeamData();
    }
}
