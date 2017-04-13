package com.km.rmbank.module.rmshop.goods;

import com.km.rmbank.dto.EvaluateDto;
import com.km.rmbank.utils.retrofit.PresenterWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamangkeji on 17/4/13.
 */

public class GoodsEvaluatePresenter extends PresenterWrapper<GoodsEvaluateContract.View> implements GoodsEvaluateContract.Presenter {

    public GoodsEvaluatePresenter(GoodsEvaluateContract.View mView) {
        super(mView);
    }

    @Override
    public void getUserEvaluate(int pageNo) {
        List<EvaluateDto> evaluateDtos = new ArrayList<>();
        for (int i = 0; i < 10;i++){
            evaluateDtos.add(new EvaluateDto("昵称"+i,"2017-2-11","看到商品后我的心凌乱了，这他妈是什么东西，吃一次我就上瘾了，\n" +
                    "你说海参长得跟他妈大象似的有意思吗？"));
        }
        mView.showUserEvaluate(evaluateDtos,pageNo);
    }

    @Override
    public void onCreateView() {
        mView.initRecyclerview();
        getUserEvaluate(1);
    }
}
