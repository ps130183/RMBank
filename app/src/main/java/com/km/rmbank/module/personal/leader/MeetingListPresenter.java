package com.km.rmbank.module.personal.leader;

import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 17/12/28.
 */

public class MeetingListPresenter extends PresenterWrapper<MeetingListContract.View> implements MeetingListContract.Presenter {

    public MeetingListPresenter(MeetingListContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void loadActionList() {
        mView.showLoading();
        mApiwrapper.getActionLists()
                .subscribe(newSubscriber(new Consumer<List<ActionDto>>() {
                    @Override
                    public void accept(List<ActionDto> meetingDtos) throws Exception {
                        mView.showActionList(meetingDtos);
                    }
                }));
    }
}
