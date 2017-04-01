package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.km.rmbank.R;
import com.km.rmbank.basic.BaseSwipeRvAdapter;
import com.km.rmbank.dto.GoodsDto;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/3/26.
 */

public class RepleaseGoodsListAdapter extends BaseSwipeRvAdapter<GoodsDto> implements BaseSwipeRvAdapter.IAdapter<RepleaseGoodsListAdapter.ViewHolder> {

    //下架
    private onClickSoldOutListener onClickSoldOutListener;

    public RepleaseGoodsListAdapter(Context mContext) {
        super(mContext, new ArrayList<GoodsDto>());
        setiAdapter(this);
    }

    @Override
    protected int getViewRes() {
        return R.layout.item_rv_replease_goods;
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(final ViewHolder holder, final int position) {

        final GoodsDto entity = getItemData(position);

        holder.tvSoldOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickSoldOutListener != null){
                    onClickSoldOutListener.clickSoldOut(entity,position,holder.getmSwiperLayout());
                }
            }
        });
    }

    class ViewHolder extends BaseSwipeViewHolder{

        //下架
        @BindView(R.id.tv_sold_out)
        TextView tvSoldOut;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 下架 按钮点击监听
     */
    public interface onClickSoldOutListener{
        void clickSoldOut(GoodsDto entity, int position, SwipeLayout mSwipeLayout);
    }

    public void addOnClickSoldOutListener(onClickSoldOutListener listener){
        this.onClickSoldOutListener = listener;
    }
}
