package com.km.rmbank.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.IndustryDto;
import com.ps.androidlib.animator.ShowViewAnimator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by kamangkeji on 17/3/29.
 */

public class IndustryParentAdapter extends BaseAdapter<IndustryDto> implements BaseAdapter.IAdapter<IndustryParentAdapter.ViewHolder> {

    private boolean isParentCheckBoxTouch = false;

    public IndustryParentAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_resource_parent);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(final ViewHolder holder, int position) {

        final IndustryDto parentEntity = getItemData(position);

        holder.tvIndustryName.setText(parentEntity.getName());

        //子行业
        List<IndustryDto> subEntitys = parentEntity.getIndustryList();
        holder.subAdapter.addData(subEntitys);
        holder.subAdapter.setOnSubCheckedListener(new IndustrySubAdapter.onSubCheckedListener() {
            @Override
            public void subChecked(IndustryDto subEntity, boolean isChecked) {
                isParentCheckBoxTouch = false;
                if (isChecked){
                    parentEntity.setChecked(true);
                } else {
                    parentEntity.setChecked(subIndustryChecked(parentEntity.getIndustryList()));
                }
                getSubChecked(holder,parentEntity.getIndustryList());
                notifyDataChanged();
            }
        });

        //父级行业
        holder.checkBox.setChecked(parentEntity.isChecked());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isParentCheckBoxTouch){
                    parentEntity.setChecked(isChecked);
                    setSubCheckedByParent(parentEntity.getIndustryList(),isChecked);
                    getSubChecked(holder,parentEntity.getIndustryList());
                }
            }
        });
        holder.checkBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isParentCheckBoxTouch = true;
                return false;
            }
        });


    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.rl_parent)
        RelativeLayout rlParent;
        @BindView(R.id.v_checked)
        View vChecked;
        @BindView(R.id.rv_sub)
        RecyclerView rvSub;
        IndustrySubAdapter subAdapter;
        private ShowViewAnimator animator;

        @BindView(R.id.checked)
        CheckBox checkBox;
        @BindView(R.id.tv_checked_hint)
        TextView tvCheckedHint;


        @BindView(R.id.tv_industry_name)
        TextView tvIndustryName;
        public ViewHolder(View itemView) {
            super(itemView);
            initSub();
            vChecked.setVisibility(View.GONE);
            rvSub.setVisibility(View.GONE);
            tvCheckedHint.setText("");
        }

        private void initSub(){
            RVUtils.setLinearLayoutManage(rvSub, LinearLayoutManager.VERTICAL);
            RVUtils.addDivideItemForRv(rvSub,RVUtils.DIVIDER_COLOR_ACCOUNT_DETAILS,2);
            subAdapter = new IndustrySubAdapter(mContext);
            rvSub.setAdapter(subAdapter);
            animator = new ShowViewAnimator();
        }

        @OnClick(R.id.rl_parent)
        public void rlParentClick(View view){
            if (vChecked.getVisibility() == View.GONE){
                vChecked.setVisibility(View.VISIBLE);
            }
            animator.showViewByAnimator(rvSub, new ShowViewAnimator.onHideListener() {
                @Override
                public void hide() {
                    vChecked.setVisibility(View.GONE);
                }
            });
        }
    }


    /**
     * 检测子集中 是否有被选中的行业
     * @param industryEntities
     * @return
     */
    private boolean subIndustryChecked(List<IndustryDto> industryEntities){
        boolean ischeced = false;
        for (IndustryDto entity : industryEntities){
            if (entity.isChecked()){
                ischeced = true;
                break;
            }
        }
        return ischeced;
    }

    /**
     * 根据父级的选中情况  设置子行业
     * @param industryEntities
     * @param isChecked
     */
    private void setSubCheckedByParent(List<IndustryDto> industryEntities, boolean isChecked){
        for (IndustryDto entity : industryEntities){
            entity.setChecked(isChecked);
        }
        notifyDataChanged();
    }

    /**
     * 获取选中的子行业
     * @param industryEntities
     * @return
     */
    private List<IndustryDto> getSubChecked(ViewHolder holder, List<IndustryDto> industryEntities){
        List<IndustryDto> checkSubs = new ArrayList<>();
        for (IndustryDto entity : industryEntities){
            if (entity.isChecked()){
                checkSubs.add(entity);
            }
        }
        if (holder != null){
            holder.tvCheckedHint.setText("已选"+ checkSubs.size()+"项");
        }
        return checkSubs;
    }

    /**
     * 获取所有被选中的子行业
     * @return
     */
    public List<IndustryDto> getAllIndustryChecked(){
        List<IndustryDto> allChecked = new ArrayList<>();
        for (IndustryDto parentEntity : getAllData()){
            for (IndustryDto subEntity : parentEntity.getIndustryList()){
                if (subEntity.isChecked()){
                    allChecked.add(subEntity);
                }
            }
        }
        return allChecked;
    }

}
