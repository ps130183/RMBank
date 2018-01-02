package com.km.rmbank.module.personal.leader;

import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.ActionDto;

import java.util.List;

/**
 * Created by PengSong on 17/12/28.
 */

public interface MeetingListContract {
    interface View extends BaseView{
        void showActionList(List<ActionDto> meetingDtos);
    }
    interface Presenter extends BasePresenter{
        void loadActionList();
    }
}
