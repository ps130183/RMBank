package com.km.rmbank.module.personal.forum;

import com.km.rmbank.dto.ForumInfoDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/8/16.
 */

public class MyForumInfosPresenter extends PresenterWrapper<MyForumInfosContract.View> implements MyForumInfosContract.Presenter {

    public MyForumInfosPresenter(MyForumInfosContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void getMyForumInfos() {
        mView.showLoading();
        mApiwrapper.getMyForumInfos()
                .subscribe(newSubscriber(new Consumer<ForumInfoDto>() {
                    @Override
                    public void accept(@NonNull ForumInfoDto forumInfoDto) throws Exception {
                        mView.showMyForumInfo(forumInfoDto);
                    }
                }));
    }
}
