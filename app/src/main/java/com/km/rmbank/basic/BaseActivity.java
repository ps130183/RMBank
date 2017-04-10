package com.km.rmbank.basic;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.km.rmbank.R;
import com.km.rmbank.api.ApiWrapper;
import com.km.rmbank.titlebar.ToolBarTitle;
import com.km.rmbank.utils.retrofit.RetrofitUtil;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.utils.DialogLoading;
import com.ps.androidlib.utils.EventBusUtils;
import com.ps.androidlib.utils.StatusBarUtil;
import com.ps.androidlib.utils.ViewUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.subscribers.ResourceSubscriber;
import kr.co.namee.permissiongen.PermissionGen;
//import rx.Subscriber;
//import rx.functions.Action0;
//import rx.functions.Action1;
//import rx.subscriptions.CompositeSubscription;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {


    protected static final int TOOLBAR_TYPE_DEFAULT = 0;
    protected static final int TOOLBAR_TYPE_HOME = 1;

    public static BaseActivity context = null;
    private static final int DEFAULT_CENTERVIEW_VISIABLE_POSITION = 0;

    protected P mPresenter;

    FrameLayout fltitle;
    FrameLayout flContent;


    private List<CenterViewInterface> centerViews;

    private TitleBarInterface titleBar;

    private OnClickKeyCodeBackLisenter clickKeyCodeBackLisenter;

    /**
     * 使用CompositeSubscription来持有所有的Subscriptions
     */
    protected CompositeDisposable mCompositeSubscription;
    protected ApiWrapper apiWrapper;
    private DialogLoading loading;//加载提示框
    protected Toast mToast = null;//提示框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int toolbarType = getToolBarType();
        EventBusUtils.register(this);
        context = this;
        mCompositeSubscription = new CompositeDisposable();
        apiWrapper = ApiWrapper.getInstance();
        mPresenter = getmPresenter();//mPresenter
        if (toolbarType == TOOLBAR_TYPE_DEFAULT){
            setContentView(R.layout.baseview);
            initDefaultView();
            StatusBarUtil.StatusBarLightMode(context);//设置状态栏 字体颜色为深色
        } else if(toolbarType == TOOLBAR_TYPE_HOME){
            setContentView(R.layout.activity_base_home);
        }

        //设置页面主内容布局
        View viewContent = ViewUtils.getView(context, getContentView());
        flContent = (FrameLayout) findViewById(R.id.fl_content);
        flContent.addView(viewContent);
        ButterKnife.bind(context);
//        AppUtils.initState(this);
        StatusBarUtil.initState(context);
        onCreate();
        if (mPresenter != null) {
            mPresenter.onCreateView();
        }
    }
    /**
     * 初始化默认的view
     */
    private void initDefaultView(){
        titleBar = getTitleBar();//标题栏
        setViewContent();

//        userDao = new UserDao(context);
        initCenterViewOfTitle();
        initToolBar();
    }

    @Override
    protected void onResume() {
        /**
         * 设置为竖屏 强制
         */
        if(getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
//        if (mPresenter != null) {
//            mPresenter.onCreateView();
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //一旦调用了 CompositeSubscription.unsubscribe()，这个CompositeSubscription对象就不可用了,
        // 如果还想使用CompositeSubscription，就必须在创建一个新的对象了。
        mCompositeSubscription.clear();
        EventBusUtils.unregister(this);
    }

    @Override
    public Context getMContext() {
        return this;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * toolbar 类型
     * @return
     */
    protected int getToolBarType(){
        return TOOLBAR_TYPE_DEFAULT;
    }

    /**
     * 返回标题栏布局
     *
     * @return
     */
    protected int getTitleView() {
        return R.layout.toolbar;
    }


    /**
     * 页面主要内容布局
     *
     * @return
     */
    protected abstract int getContentView();

    /**
     * 标题栏控制类
     *
     * @return
     */
    protected TitleBarInterface getTitleBar() {
        return new ToolBarTitle(false);
    }


    /**
     * 标题名
     *
     * @return
     */
    protected abstract
    @NonNull
    String getTitleName();

    public P getmPresenter(){
        return null;
    };

    protected abstract void onCreate();

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    /**
     * 设置布局
     */
    private void setViewContent() {
        if (titleBar != null) {
            //设置标题栏布局  默认是toolbar.xml
            fltitle = (FrameLayout) findViewById(R.id.fl_title);
            View titleView = ViewUtils.getView(context, getTitleView());
            fltitle.addView(titleView);
            titleBar.bindViewByRes(titleView);
        }

    }


    /**
     * 初始化toolBar
     */
    private void initToolBar() {
        if (titleBar == null) {
            return;
        }
        if (titleBar.isSetTitle()) {
            titleBar.setTitleName(getTitleName());
        } else {
            titleBar.setCenterView(getCenterView(DEFAULT_CENTERVIEW_VISIABLE_POSITION));
        }
        setLeftIconClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setRightIconClick(null);
    }

    protected
    @DrawableRes
    int getBackIconRes() {
        return R.mipmap.ic_chevron_left_black_36dp;
    }

    protected int getRightIconRes(){
        return 0;
    }

    protected
    @NonNull
    List<CenterViewInterface> getCenterViews() {
        return new ArrayList<>();
    }

    /**
     * 设置toolBar左侧按钮的点击事件
     *
     * @param leftIconOnclick
     */
    public void setLeftIconClick(View.OnClickListener leftIconOnclick) {
        setLeftIconClick(getBackIconRes(), leftIconOnclick);
    }

    public void setLeftIconClick(int iconRes, View.OnClickListener leftIconOnClick) {
        if (titleBar != null) {
            titleBar.setLeftButtton(iconRes, leftIconOnClick);
        }
    }

    public void setRightIconClick(int iconRes,View.OnClickListener rightIconOnClick){
        if (titleBar != null){
            titleBar.setRightButton(iconRes,rightIconOnClick);
        }
    }

    public void setRightIconClick(View.OnClickListener rightIconOnClick){
        setRightIconClick(getRightIconRes(),rightIconOnClick);
    }

    public void setRightBtnClick(String btnName, View.OnClickListener btnClick){
        if (titleBar != null){
            titleBar.setRightButton(btnName,btnClick);
        }
    }
    /**
     * 初始化toolBar中间的内容
     */
    private void initCenterViewOfTitle() {
        centerViews = getCenterViews();
        if (centerViews.size() == 0) {
            centerViews.add(titleView);
        }
    }

    /**
     * 根据位置获取toolbar中间的布局
     *
     * @param centerViewPosition
     * @return
     */
    private CenterViewInterface getCenterView(int centerViewPosition) {
        if (centerViewPosition >= 0 && centerViewPosition < centerViews.size()) {
            return centerViews.get(centerViewPosition);
        } else {
            throw new IllegalArgumentException("centerViewPosition is error,list position 越界");
        }
    }


    /**
     * 显示一个Toast信息
     *
     * @param content
     */
    public void showToast(String content) {
        if (mToast == null) {
            mToast = Toast.makeText(this, content, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(content);
        }
        mToast.show();
    }

    /**
     * 跳转到下一个activity页面
     *
     * @param nextActivity
     * @param bundle
     */
    public void toNextActivity(Class nextActivity, Bundle bundle) {
        Intent intent = new Intent(context, nextActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转到下一个activity页面， 无参数
     *
     * @param nextActivity
     */
    public void toNextActivity(Class nextActivity) {
        toNextActivity(nextActivity, null);
    }


    public void toNextActivityForResult(Class nextActivity,int requestCode,Bundle bundle){
        Intent intent = new Intent(context,nextActivity);
        if (bundle != null){
            intent.putExtras(bundle);
        }
        startActivityForResult(intent,requestCode);
    }

    public void toNextActivityForResult(Class nextActivity,int requestCode){
        toNextActivityForResult(nextActivity,requestCode,null);
    }

    public void setResult(int resultCode,Bundle bundle){
        Intent intent = getIntent();
        if (bundle != null){
            intent.putExtras(bundle);
        }
        setResult(resultCode,intent);
        finish();
    }

    public interface CenterViewInterface {
        View getView();

        void setViewWidget(View view);
    }

    public static interface TitleBarInterface {
        void bindViewByRes(View view);

        void setLeftButtton(int iconRes, View.OnClickListener leftClick);

        void setTitleName(String titleName);

        void setCenterView(CenterViewInterface centerView);

        void setRightButton(int iconRes, View.OnClickListener rightClick);
        void setRightButton(String rightBtnText, View.OnClickListener rightClick);

        boolean isSetTitle();
    }

    private CenterViewInterface titleView = new CenterViewInterface() {
        @Override
        public View getView() {
            return ViewUtils.getView(context, R.layout.title);
        }

        @Override
        public void setViewWidget(View view) {
            TextView title = (TextView) view.findViewById(R.id.title);
            title.setText(getTitleName());
        }
    };

    /**
     * 创建观察者
     *
     * @param onNext
     * @param <T>
     * @return
     */
    public  <T> ResourceSubscriber<T> newSubscriber(final Consumer<? super T> onNext) {
        return new ResourceSubscriber<T>() {

            @Override
            public void onComplete() {
                hideLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof RetrofitUtil.APIException) {
                    RetrofitUtil.APIException exception = (RetrofitUtil.APIException) e;
                    showToast(exception.message);
                } else if (e instanceof SocketTimeoutException) {
                    showToast(e.getMessage());
                } else if (e instanceof ConnectException) {
                    showToast(e.getMessage());
                }
                if ("timeout".equals(e.getMessage()) || "connect timed out".equals(e.getMessage())){
                    showToast("请求超时，请稍后再试");
                } else {
//                    LogUtils.e(String.valueOf(e.getMessage()));
//                    Logger.e(e.getMessage());
                    e.printStackTrace();
                }
                hideLoadingDialog();
            }

            @Override
            public void onNext(T t) {
                if (!mCompositeSubscription.isDisposed()) {
                    try {
                        onNext.accept(t);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        };
    }

    /**
     * 创建观察者
     *
     * @param onNext
     * @param onCompleted
     * @param <T>
     * @return
     */
    public  <T> Observer<T> newSubscriber(final Consumer<? super T> onNext, final Action onCompleted) {
        return new Observer<T>() {

            @Override
            public void onError(Throwable e) {
                if (e instanceof RetrofitUtil.APIException) {
                    RetrofitUtil.APIException exception = (RetrofitUtil.APIException) e;
                    showToast(exception.message);
                } else if (e instanceof SocketTimeoutException) {
                    showToast(e.getMessage());
                } else if (e instanceof ConnectException) {
                    showToast(e.getMessage());
                }
//                LogUtils.e(String.valueOf(e.getMessage()));
                Logger.e(e.getMessage());
//                Log.e(TAG, String.valueOf(e.getMessage()));
//                e.printStackTrace();
                hideLoadingDialog();
            }

            @Override
            public void onComplete() {
                hideLoadingDialog();
                try {
                    onCompleted.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSubscribe(Disposable d) {
                mCompositeSubscription.add(d);
            }

            @Override
            public void onNext(T t) {
                if (!mCompositeSubscription.isDisposed()) {
                    try {
                        onNext.accept(t);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    protected void showLoadingDialog() {
        if (loading == null) {
            loading = new DialogLoading(this);
        }
        loading.show();
    }

    protected void hideLoadingDialog() {
        if (loading != null) {
            loading.dismiss();
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && clickKeyCodeBackLisenter != null){//物理返回键
            return clickKeyCodeBackLisenter.onClickKeyCodeBack();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setClickKeyCodeBackLisenter(OnClickKeyCodeBackLisenter clickKeyCodeBackLisenter) {
        this.clickKeyCodeBackLisenter = clickKeyCodeBackLisenter;
    }

    public interface OnClickKeyCodeBackLisenter{
        boolean onClickKeyCodeBack();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void defaultMethod(String s){

    }
}
