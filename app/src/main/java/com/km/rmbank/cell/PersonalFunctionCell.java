package com.km.rmbank.cell;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.entity.PersonalFunctionEntity;
import com.km.rv_libs.base.BaseCell;
import com.km.rv_libs.base.BaseViewHolder;

/**
 * Created by kamangkeji on 17/3/17.
 */

public class PersonalFunctionCell extends BaseCell<PersonalFunctionEntity> implements View.OnClickListener {


    public PersonalFunctionCell(PersonalFunctionEntity mData, OnCellClickListener<PersonalFunctionEntity> onCellClickListener) {
        super(mData, R.layout.personal_function, onCellClickListener);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        TextView tvMyTeam = holder.getTextView(R.id.tv_my_team);
        View lineTeamContact = holder.getView(R.id.line_team_contact);
        TextView tvMyContact = holder.getTextView(R.id.tv_my_contact);

        LinearLayout llIntegralGoods = holder.findView(R.id.ll_integral_goods);
        TextView tvMyIntegral = holder.getTextView(R.id.tv_my_integral);
        TextView tvGoodsManager = holder.getTextView(R.id.tv_goods_manager);
        int usertype = mData.getType();
        switch (usertype){
            case 0://普通用户
                tvMyTeam.setVisibility(View.GONE);
                lineTeamContact.setVisibility(View.GONE);
                llIntegralGoods.setVisibility(View.GONE);
                break;
            case 1://体验式会员
                tvMyTeam.setVisibility(View.GONE);
                lineTeamContact.setVisibility(View.GONE);
                break;
            case 2://合伙人会员
                break;
        }

        tvMyTeam.setOnClickListener(this);
        tvMyContact.setOnClickListener(this);
        tvMyIntegral.setOnClickListener(this);
        tvGoodsManager.setOnClickListener(this);
        holder.getTextView(R.id.tv_my_order).setOnClickListener(this);
        holder.getTextView(R.id.tv_address).setOnClickListener(this);
        holder.getTextView(R.id.tv_service).setOnClickListener(this);
        holder.getTextView(R.id.tv_attention).setOnClickListener(this);

    }

    @Override
    public int getItemViewType() {
        return 1;
    }

    @Override
    public void onClick(View v) {
        onCellClickListener.cellClick(mData,v.getId());
    }
}
