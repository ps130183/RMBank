package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.BaseView;
import com.km.rmbank.dto.UserAccountDetailDto;

/**
 * Created by kamangkeji on 17/4/1.
 */

public class UserAccountDetailAdapter extends BaseAdapter<UserAccountDetailDto> implements BaseAdapter.IAdapter<UserAccountDetailAdapter.ViewHolder> {

    public UserAccountDetailAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_account_details);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {

    }

    class ViewHolder extends BaseViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
