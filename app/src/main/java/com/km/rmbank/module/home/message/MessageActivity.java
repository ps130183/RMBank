package com.km.rmbank.module.home.message;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.MessageAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.MessageDto;

import java.util.List;

import butterknife.BindView;

public class MessageActivity extends BaseActivity<MessagePresenter> implements MessageContract.View {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    @Override
    protected int getContentView() {
        return R.layout.activity_message;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "消息中心";
    }

    @Override
    public MessagePresenter getmPresenter() {
        return new MessagePresenter(this);
    }

    @Override
    protected void onCreate() {

    }

    @Override
    public void initRecyclerview() {
        RVUtils.setLinearLayoutManage(mRecyclerview, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerview);
        final MessageAdapter adapter = new MessageAdapter(this);
        mRecyclerview.setAdapter(adapter);
        adapter.addLoadMore(mRecyclerview, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.getMessage(adapter.getNextPage());
            }
        });
        mPresenter.getMessage(adapter.getNextPage());
    }

    @Override
    public void showMessage(List<MessageDto> messageDtos, int pageNo) {
        MessageAdapter adapter = (MessageAdapter) mRecyclerview.getAdapter();
        adapter.addData(messageDtos,pageNo);
    }
}
