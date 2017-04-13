package com.km.rv_libs.base;

import android.nfc.tech.MifareClassic;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.km.rv_libs.cell.EmptyCell;
import com.km.rv_libs.cell.LoadMoreCell;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by kamangkeji on 17/3/15.
 */

public abstract class BaseAdapter<C extends ICell> extends RecyclerView.Adapter<BaseViewHolder> {

    protected List<C> mIcells;

    protected EmptyCell emptyCell;

    private LoadMoreCell loadMoreCell;
    private MoreDataListener moreDataListener;


    private Handler mHandler;
    protected int curPageNo = 0;

    public BaseAdapter() {
        mIcells = new ArrayList<>();
        emptyCell = new EmptyCell(0);
        loadMoreCell = new LoadMoreCell(null);
        mHandler = new Handler();
    }

    public void addData(List<C> cells) {
        if (cells != null && cells.size() > 0) {
            mIcells.addAll(cells);
        } else if (curPageNo == 1){
            add((C) emptyCell);
        }
        notifyDataSetChanged();
    }


    public List<C> getData() {
        return mIcells;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mIcells != null) {
            for (ICell iCell : mIcells) {
                if (viewType == iCell.getItemViewType()) {
                    return iCell.onCreateViewHolder(parent, viewType);
                }
            }
        }

        throw new RuntimeException("viewType is wrong");
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        ICell iCell = mIcells.get(position);
        iCell.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        if (mIcells == null) {
            return 0;
        }
        return mIcells.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mIcells.get(position).getItemViewType();
    }

    public int getNextPage() {
        Logger.d("curpageNo = " + curPageNo);
        return ++curPageNo;
    }

    /**
     * add one cell
     *
     * @param cell
     */
    public void add(C cell) {
        mIcells.add(cell);
        int index = mIcells.indexOf(cell);
        notifyItemChanged(index);
    }

    public void add(int index, C cell) {
        mIcells.add(index, cell);
        notifyItemChanged(index);
    }

    /**
     * remove a cell
     *
     * @param cell
     */
    public void remove(C cell) {
        int indexOfCell = mIcells.indexOf(cell);
        remove(indexOfCell);
    }

    public void remove(int index) {
        mIcells.remove(index);
        notifyItemRemoved(index);
    }

    /**
     * @param start
     * @param count
     */
    public void remove(int start, int count) {
        if ((start + count) > mIcells.size()) {
            return;
        }
        int size = getItemCount();
        for (int i = start; i < size; i++) {
            mIcells.remove(i);
        }

        notifyItemRangeRemoved(start, count);
    }

    /**
     * add a cell list
     *
     * @param cells
     */
    public void addAll(List<C> cells) {
        if (cells == null || cells.size() == 0) {
            return;
        }
//        Logger.e("addAll cell size:"+cells.size());
        mIcells.addAll(cells);
        notifyDataChanged(mIcells.size() - cells.size(), mIcells.size());
    }

    /**
     * 指定位置添加cell
     *
     * @param index
     * @param cells
     */
    public void addAll(int index, List<C> cells) {
        if (cells == null || cells.size() == 0) {
            return;
        }
        mIcells.addAll(index, cells);
        notifyDataChanged(index, index + cells.size());
    }

    /**
     * 刷新cell
     */
    public void notifyDataChanged() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    /**
     * 刷新指定位置的cell
     */
    public void notifyDataChanged(final int start, final int end) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                notifyItemRangeChanged(start, end);
            }
        });
    }

    /**
     * 清空cells
     */
    public void clear() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                curPageNo = 0;
                mIcells.clear();
                notifyDataSetChanged();
            }
        });

    }


    /**
     * 如果子类需要在onBindViewHolder 回调的时候做的操作可以在这个方法里做
     *
     * @param holder
     * @param position
     */
    protected abstract void onViewHolderBound(BaseViewHolder holder, int position);


    public void showLoadMore() {
        isLoadMore = true;
        if (!mIcells.contains(loadMoreCell)) {
            add((C) loadMoreCell);
        }
    }

    public void hideLoadeMore() {
        isLoadMore = false;
        if (mIcells.contains(loadMoreCell)) {
            remove((C) loadMoreCell);
        }
    }


    /**
     * ----------------------------加载更多------------开始--------------
     */
    private boolean isLoadMore;
    private int totalItemCount = 0;
    private int lastVisiableItemPosition = 0;
    //当前滚动的position下面最小的items的临界值
    private static final int visibleThreshold = 5;

    /**
     * 加载更多
     * 建议在recyclerView 设置完adapter以后再进行加载更多的设置
     *
     * @param recyclerView
     * @param moreDataListener
     */
    public void addLoadMore(RecyclerView recyclerView, final MoreDataListener moreDataListener) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            this.moreDataListener = moreDataListener;
            final LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    totalItemCount = llm.getItemCount();
                    lastVisiableItemPosition = llm.findLastVisibleItemPosition();

                    Flowable.just(lastVisiableItemPosition)
                            .filter(new Predicate<Integer>() {
                                @Override
                                public boolean test(@NonNull Integer integer) throws Exception {
                                    return !isLoadMore && totalItemCount <= (lastVisiableItemPosition + visibleThreshold);
                                }
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnNext(new Consumer<Integer>() {
                                @Override
                                public void accept(@NonNull Integer integer) throws Exception {
                                    showLoadMore();
                                    moreDataListener.loadMoreData();
                                }
                            })
                            .subscribe(new Consumer<Integer>() {
                                @Override
                                public void accept(@NonNull Integer integer) throws Exception {
                                    hideLoadeMore();
                                }
                            });

//                    if (!isLoadMore && totalItemCount == (lastVisiableItemPosition + visibleThreshold)) {
//                        if (curPageNo > 1){
//                            showLoadMore();
//                        }
//                        moreDataListener.loadMoreData();
//                    }
                }
            });
        }
    }

    private interface BaseOnScrollListener {
    }

    public interface MoreDataListener extends BaseOnScrollListener {
        void loadMoreData();
    }

    public void addMoreData(final List<C> mIcells) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (isLoadMore) {//加载更多时，重置状态
                    hideLoadeMore();
                }
                addAll(mIcells);
            }
        });

    }


    /**----------------------------加载更多------------结束--------------*/

}
