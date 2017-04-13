package com.km.rmbank.basic;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.km.rmbank.R;
import com.km.rmbank.api.ApiWrapper;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/1/19.
 */

public class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.BaseViewHolder> {

    protected static final int viewtype_item = 0;
    protected static final int viewtype_header = Integer.MAX_VALUE - 3;
    protected static final int viewtype_footer = Integer.MAX_VALUE - 4;
    protected static final int viewtype_load_more = Integer.MAX_VALUE - 2;
    protected static final int viewtype_item_empty = Integer.MAX_VALUE - 1;

    //    private boolean loadMore = false;
    private boolean loadMoreFinish = false;

    private boolean mExistFooterView = false;//底部
    private boolean mExistEmptyView = true;//数据为空时提示

    protected Context mContext;
    private List<T> listContents;
    private int itemLayoutRes;

    private IAdapter iAdapter;

    private ItemClickListener<T> itemClickListener;
    protected int curPage = 0;

    private MoreDataListener moreDataListener;

    public BaseAdapter(Context mContext, List<T> listContents, int itemLayoutRes) {
        this.mContext = mContext;
        this.listContents = listContents;
        this.itemLayoutRes = itemLayoutRes;
        if (listContents.size() > 0) {
            curPage = 1;
        } else {
            curPage = 0;
        }
    }

    public BaseAdapter(Context mContext, int itemLayoutRes) {
        this(mContext, new ArrayList<T>(), itemLayoutRes);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (iAdapter == null) {
            throw new IllegalArgumentException(this.toString() + "  iAdapter is not null,请实现iadapter接口");
        } else if (viewType == viewtype_item_empty) {//没有数据
            return new EmptyViewHolder(ViewUtils.getView(inflater, parent, R.layout.rc_item_empty));
        } else if (viewType == viewtype_header) {
            return getHeaderViewHolder(inflater, parent);
        } else if (viewType == viewtype_footer) {//footer
            return getFooterViewHolder(inflater, parent);
        } else if (viewType == viewtype_load_more) {//加载更多
            if (loadMoreFinish) {
                return new LoadMoreViewHolder(ViewUtils.getView(inflater, parent, R.layout.rc_footer_load_more_finish));
            } else {
                return new LoadMoreViewHolder(ViewUtils.getView(inflater, parent, R.layout.rc_footer_load_more));
            }
        } else {
            View view = inflater.inflate(itemLayoutRes, parent, false);
            return iAdapter.createViewHolder(view, viewType);
        }
    }

    /**
     * 获取footerviewholder
     *
     * @return
     */
    protected BaseFooterViewHolder getFooterViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new BaseFooterViewHolder(ViewUtils.getView(inflater, parent, R.layout.rv_footer_default));
    }

    /**
     * 获取HeaderViewHolder
     *
     * @param inflater
     * @param parent
     * @return
     */
    protected BaseHeaderViewHolder getHeaderViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new BaseHeaderViewHolder(ViewUtils.getView(inflater, parent, R.layout.rv_header_default));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (iAdapter == null) {
            throw new IllegalArgumentException("iAdapter is not null,请实现iadapter接口");
        } else if (listContents.size() == 0 || position == listContents.size()) {

        } else {
            iAdapter.createView(holder, position);
            setItemClick(holder.itemView, position);
        }
    }

    @Override
    public int getItemCount() {
        int itemCount = listContents.size();

        if (moreDataListener != null && itemCount > ApiWrapper.maxData - 1) {//有加载更多
            itemCount += 1;
        }
        if (mExistFooterView) { //底部
            itemCount += 1;
        }
        return listContents.size() > 0 ? itemCount : (mExistEmptyView ? 1 : itemCount);
    }

    @Override
    public int getItemViewType(int position) {
        if (mExistEmptyView && listContents.size() == 0) {
            return viewtype_item_empty;
        } else if (moreDataListener != null && position >= listContents.size()) {
            return viewtype_load_more;
        } else if (mExistFooterView && position >= listContents.size()) {
            return viewtype_footer;
        }
        return viewtype_item;
    }

    /**
     * 获取指定位置的数据
     *
     * @param position
     * @return
     */
    public T getItemData(int position) {
        if (position >= 0 && listContents.size() > position) {
            return listContents.get(position);
        } else {
            throw new IllegalArgumentException("position is container listContents.size,点击的位置不在列表的范围之内");
        }
    }

    /**
     * 获取所有数据
     *
     * @return
     */
    public List<T> getAllData() {
        return listContents;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    /**
     * 获取下一页
     *
     * @return
     */
    public int getNextPage() {
        return curPage + 1;
    }

    /**
     * 向类表里填充数据
     *
     * @param datas
     * @param nextPage 下一页
     */
    public void addData(List<T> datas, int nextPage) {
        if (listContents != null && (datas != null && datas.size() > 0)) {
            if (nextPage > 1) {
                curPage++;
            } else {
                curPage = 1;
                clearAllData();
            }
            listContents.addAll(datas);
            loadMoreFinish = false;
        } else {
            if (listContents.size() > 0) {
                loadMoreFinish = true;
            }
        }
        notifyDataSetChanged();
        isLoadMore = false;
    }

    /**
     * 向类表里填充数据
     *
     * @param datas
     */
    public void addData(List<T> datas) {
        addData(datas, 1);
    }

    /**
     * 向类表里填充数据
     *
     * @param datas
     */
    public int addData(T datas) {
        int position = 0;

        if (listContents != null) {
            listContents.add(datas);
            position = listContents.indexOf(datas);
            notifyDataChanged();
        }
        return position;
    }

    public void addDataOnFirst(T datas) {
        if (listContents != null) {
            listContents.add(0, datas);
//            notifyItemChanged(listContents.size() - 1);
            notifyDataChanged();
        }
    }

    /**
     * 移除某条数据
     *
     * @param data
     */
    public void removeData(T data) {
        if (listContents != null) {
            listContents.remove(data);
            notifyDataSetChanged();
        }
    }

    /**
     * 清空所有数据
     */
    public void clearAllData() {
        if (listContents != null) {
            listContents.clear();
            curPage = 0;
            notifyDataSetChanged();
        }
    }

    /**
     * 设置单击事件
     *
     * @param itemView
     * @param position
     */
    private void setItemClick(View itemView, final int position) {
        if (itemClickListener != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(getItemData(position), position);
                }
            });
        }
    }

    //    public abstract BaseViewHolder createViewHolder(View view, int viewType);
//    public abstract void createView(BaseViewHolder holder, int position);
    public interface IAdapter<VH extends BaseViewHolder> {
        VH createViewHolder(View view, int viewType);

        void createView(VH holder, int position);
    }

    public abstract static class BaseViewHolder extends RecyclerView.ViewHolder {

        View itemView;

        public BaseViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 空数据
     */
    class EmptyViewHolder extends BaseViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setmExistEmptyView(boolean mExistEmptyView) {
        this.mExistEmptyView = mExistEmptyView;
    }

    /**
     * 加载更多
     */
    class LoadMoreViewHolder extends BaseViewHolder {

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
        }
    }


    public void setmExistFooterView(boolean mExistFooterView) {
        this.mExistFooterView = mExistFooterView;
    }

    /**
     * footer
     */
    protected class BaseFooterViewHolder extends BaseViewHolder {

        public BaseFooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    protected class BaseHeaderViewHolder extends BaseViewHolder {

        public BaseHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public T getListContent(int position) {
        if (listContents != null && listContents.size() > 0 && position >= 0) {
            return listContents.get(position);
        } else {
            throw new IllegalArgumentException("无数据");
        }
    }

    public void setiAdapter(IAdapter iAdapter) {
        this.iAdapter = iAdapter;
    }


    public interface ItemClickListener<T> {
        void onItemClick(T itemData, int position);
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

                    if (!loadMoreFinish && !isLoadMore && totalItemCount <= (lastVisiableItemPosition + visibleThreshold)
                            && !(curPage == 1 && totalItemCount < 20)) {
                        moreDataListener.loadMoreData();
                        isLoadMore = true;
                    }
                }
            });
        }
    }

    private interface BaseOnScrollListener {
    }

    public interface MoreDataListener extends BaseOnScrollListener {
        void loadMoreData();
    }

    /**----------------------------加载更多------------结束--------------*/

    /**
     * ----------------------------RecyclerView 上滑监听 和 下滑监听----------------------------------------
     */

    public interface RcScrollListener extends BaseOnScrollListener {
        void scrollUp();

        void scrollDown();
    }

    //监听recyclerView的上下滑动的监听状态
    private boolean scrollUp = false;
    private boolean scrollDown = false;

    /**
     * 为recyclerView 添加上下滑动监听
     *
     * @param recyclerView
     * @param scrollListener
     */
    public void addScrollListener(RecyclerView recyclerView, final RcScrollListener scrollListener,
                                  final MoreDataListener moreDataListener) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            this.moreDataListener = moreDataListener;
            final LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    Logger.d("onScrollStateChanged : " + newState + " scrollUP:" + scrollUp + "  scrollDown:" + scrollDown);
                    if (newState == 0) {  //滑动停止时 重置监听状态
                        scrollUp = false;
                        scrollDown = false;
                    }
//                    if (newState > 0 && !scrollDown && !scrollUp){
//                        scrollListener.scrollDown();
//                        scrollDown = true;
//                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (dy > 0 && !scrollUp) {//向上滑动
                        scrollUp = true;
                        scrollListener.scrollUp();
                    }
                    if (dy < 0 && !scrollDown) {//向下滑动
                        scrollDown = true;
                        scrollListener.scrollDown();
                    }

                    if (moreDataListener != null) {//加载更多
                        totalItemCount = llm.getItemCount();
                        lastVisiableItemPosition = llm.findLastVisibleItemPosition();

                        if (!loadMoreFinish && !isLoadMore && totalItemCount <= (lastVisiableItemPosition + visibleThreshold)) {
                            moreDataListener.loadMoreData();
                            isLoadMore = true;
                        }
                    }
                }
            });
        }
    }
    /**----------------------------RecyclerView 上滑监听 和 下滑监听----------------------------------------*/

    /**
     * 刷新列表
     */
    protected void notifyDataChanged() {
        AppUtils.executeOnUiThread()
                .subscribe(new Consumer() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        notifyDataSetChanged();
                    }
                });
    }

}
