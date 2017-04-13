package com.km.rmbank.module.home.message;

import com.km.rmbank.dto.MessageDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/4/13.
 */

public class MessagePresenter extends PresenterWrapper<MessageContract.View> implements MessageContract.Presenter {

    public MessagePresenter(MessageContract.View mView) {
        super(mView);
    }

    @Override
    public void getMessage(final int pageNo) {
        mView.showLoading();
        mApiwrapper.getMessage(pageNo)
                .subscribe(newSubscriber(new Consumer<List<MessageDto>>() {
                    @Override
                    public void accept(@NonNull List<MessageDto> messageDtos) throws Exception {
                        mView.showMessage(messageDtos,pageNo);
                    }
                }));
    }

    @Override
    public void onCreateView() {
        mView.initRecyclerview();
    }
}
