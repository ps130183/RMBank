package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.dto.UserDto;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/3/30.
 */

public class TeamMemberAdapter extends BaseAdapter<UserDto> implements BaseAdapter.IAdapter<TeamMemberAdapter.ViewHolder> {

    public TeamMemberAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_my_team_sub);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        UserDto userEntity = getItemData(position);
//        holder.tvUserNickName.setText(userEntity.);
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.tv_user_nick_name)
        TextView tvUserNickName;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
