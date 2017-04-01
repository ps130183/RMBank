package com.km.rmbank.cell;

import android.view.View;

import com.daimajia.swipe.SwipeLayout;
import com.km.rmbank.R;
import com.km.rmbank.dto.GoodsDto;
import com.km.rv_libs.base.BaseCell;
import com.km.rv_libs.base.BaseViewHolder;

/**
 * Created by kamangkeji on 17/3/24.
 */

public class RepleaseGoodsListCell extends BaseCell<GoodsDto> {

    public RepleaseGoodsListCell(GoodsDto mData, OnCellClickListener<GoodsDto> onCellClickListener) {
        super(mData, R.layout.item_rv_replease_goods, onCellClickListener);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        SwipeLayout swipeLayout = holder.findView(R.id.swiper_layout);
        //set show mode.
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        //set drag edge.
        swipeLayout.setDragEdge(SwipeLayout.DragEdge.Left);

        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {

            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

        holder.getTextView(R.id.tv_sold_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCellClickListener.cellClick(mData,-1);
            }
        });
    }
}
