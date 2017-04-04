package com.km.rmbank.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.entity.TeamEntity;
import com.km.rmbank.dto.UserDto;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.animator.AnimatorViewWrapper;
import com.ps.androidlib.animator.RecyclerViewAnimator;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by kamangkeji on 17/3/30.
 */

public class MyTeamParentAdapter extends BaseAdapter<TeamEntity> implements BaseAdapter.IAdapter<MyTeamParentAdapter.ViewHolder> {

    private onClickUserListener onClickUserListener;

    public MyTeamParentAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_my_team_parent);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        TeamEntity teamEntity = getItemData(position);
        holder.tvTeamName.setText(teamEntity.getTeamName());

        holder.teamMemberAdapter.addData(teamEntity.getUserEntities());
        holder.teamMemberAdapter.setItemClickListener(new ItemClickListener<UserDto>() {

            @Override
            public void onItemClick(UserDto itemData, int position) {
                if (onClickUserListener != null){
                    onClickUserListener.clickUser(itemData,position);
                }
            }
        });
        holder.tvMemberNumber.setText(teamEntity.getUserEntities().size()+"人");
    }

    class ViewHolder extends BaseViewHolder {

        @BindView(R.id.tv_team_name)
        TextView tvTeamName;

        @BindView(R.id.rv_member)
        RecyclerView rvMember;
        TeamMemberAdapter teamMemberAdapter;

        @BindView(R.id.rl_team)
        RelativeLayout rlTeam;
        @BindView(R.id.tv_member_number)
        TextView tvMemberNumber;
        private RecyclerViewAnimator mAnimator;

        public ViewHolder(View itemView) {
            super(itemView);
            initMember();
        }

        private void initMember(){
            RVUtils.setLinearLayoutManage(rvMember, LinearLayoutManager.VERTICAL);
            RVUtils.addDivideItemForRv(rvMember,RVUtils.DIVIDER_COLOR_ACCOUNT_DETAILS,2);
            teamMemberAdapter = new TeamMemberAdapter(mContext);
            rvMember.setAdapter(teamMemberAdapter);
            rvMember.setVisibility(View.VISIBLE);
            mAnimator = new RecyclerViewAnimator();
        }

        @OnClick({R.id.rl_team,R.id.tv_team_name,R.id.tv_member_number})
        public void clickTeam(View view){
//            rvMember.setVisibility(rvMember.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            mAnimator.recyclerViewSetVisiable(rvMember);
        }
    }

    public interface onClickUserListener{
        void clickUser(UserDto itemData, int position);
    }

    public void setOnClickUserListener(MyTeamParentAdapter.onClickUserListener onClickUserListener) {
        this.onClickUserListener = onClickUserListener;
    }

}
