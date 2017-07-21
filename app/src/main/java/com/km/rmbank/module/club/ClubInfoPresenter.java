package com.km.rmbank.module.club;

import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kamangkeji on 17/7/12.
 */

public class ClubInfoPresenter extends PresenterWrapper<ClubInfoContract.View> implements ClubInfoContract.Presenter {

    public ClubInfoPresenter(ClubInfoContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void getMyClubInfo(String clubId) {
        mView.showLoading();
        mApiwrapper.getMyClubInfo(clubId)
                .flatMap(new Function<ClubDto, Flowable<ClubDto>>() {
                    @Override
                    public Flowable<ClubDto> apply(@NonNull ClubDto clubDto) throws Exception {

                        return Flowable.zip(Flowable.just(clubDto), mApiwrapper.getMyClubInfoDetails(clubDto.getId()),
                                new BiFunction<ClubDto, List<ClubDto.ClubDetailBean>, ClubDto>() {
                                    @Override
                                    public ClubDto apply(@NonNull ClubDto clubDto, @NonNull List<ClubDto.ClubDetailBean> clubDetailBeen) throws Exception {
                                        clubDto.setClubDetailList(clubDetailBeen);
                                        return clubDto;
                                    }
                                });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newSubscriber(new Consumer<ClubDto>() {
                    @Override
                    public void accept(@NonNull ClubDto clubDto) throws Exception {
                        mView.showClubInfo(clubDto);
                    }
                }));

    }

    @Override
    public void followClub(String clubId) {
        mView.showLoading();
        mApiwrapper.followGodos("",clubId)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.followClubSuccess();
                    }
                }));
    }
}
