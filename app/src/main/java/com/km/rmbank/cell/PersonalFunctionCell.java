package com.km.rmbank.cell;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.dto.UserDto;
import com.km.rmbank.entity.PersonalFunctionEntity;
import com.km.rv_libs.base.BaseCell;
import com.km.rv_libs.base.BaseViewHolder;

/**
 * Created by kamangkeji on 17/3/17.
 */

public class PersonalFunctionCell extends BaseCell<UserDto> implements View.OnClickListener {

    private TextView myContactHint;

    public PersonalFunctionCell(UserDto mData, OnCellClickListener<UserDto> onCellClickListener) {
        super(mData, R.layout.personal_function, onCellClickListener);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

//        TextView tvMyTeam = holder.getTextView(R.id.tv_my_team);
//        View lineTeamContact = holder.getView(R.id.line_team_contact);
        TextView tvMyContact = holder.getTextView(R.id.tv_my_contact);
        RelativeLayout rlMyContact = (RelativeLayout) holder.getView(R.id.rl_my_contact);
        myContactHint = holder.getTextView(R.id.tv_my_contact_hint);

//        LinearLayout llMyClub = holder.findView(R.id.ll_my_club);
        View lineIntegral = holder.findView(R.id.line_integral_goods);
        TextView tvMyIntegral = holder.getTextView(R.id.tv_my_integral);
//        TextView tvGoodsManager = holder.getTextView(R.id.tv_goods_manager);
        String usertype = mData.getRoleId();
        switch (usertype){
            case "3"://体验式会员
                tvMyIntegral.setVisibility(View.VISIBLE);
                lineIntegral.setVisibility(View.VISIBLE);
                break;
            case "4"://普通用户
            default://普通用户
                tvMyIntegral.setVisibility(View.GONE);
                lineIntegral.setVisibility(View.GONE);
                break;
        }

//        tvMyTeam.setOnClickListener(this);
        tvMyContact.setOnClickListener(this);
        tvMyIntegral.setOnClickListener(this);
//        tvGoodsManager.setOnClickListener(this);
        holder.getTextView(R.id.tv_my_order).setOnClickListener(this);
        holder.getTextView(R.id.tv_address).setOnClickListener(this);
        holder.getTextView(R.id.tv_service).setOnClickListener(this);
        holder.getTextView(R.id.tv_service_phone).setOnClickListener(this);
        holder.getView(R.id.rl_service).setOnClickListener(this);
        holder.getTextView(R.id.tv_attention).setOnClickListener(this);
        holder.getTextView(R.id.tv_my_forum).setOnClickListener(this);

//        holder.getTextView(R.id.tv_my_club).setOnClickListener(this);

    }

    @Override
    public int getItemViewType() {
        return 1;
    }

    @Override
    public void onClick(View v) {
        onCellClickListener.cellClick(mData,v.getId());
    }

    public void setMyContactHintVisible(boolean unreadNumber){
        if (myContactHint == null){
            return;
        }
        if (unreadNumber){
            myContactHint.setVisibility(View.VISIBLE);
        } else {
            myContactHint.setVisibility(View.GONE);
        }
    }
}
