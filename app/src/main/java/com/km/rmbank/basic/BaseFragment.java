package com.km.rmbank.basic;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ps.androidlib.utils.DialogLoading;
import com.ps.androidlib.utils.ViewUtils;

import butterknife.ButterKnife;
import kr.co.namee.permissiongen.PermissionGen;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {

    private DialogLoading loading;//加载提示框
    protected P mPresenter;
    protected Toast mToast = null;//提示框

    protected BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = ViewUtils.getView(inflater,container,getContentView());
        ButterKnife.bind(this,view);
        mPresenter = getmPresenter();
        createView();
        if (mPresenter != null){
            mPresenter.onCreateView();
        }
        return view;
    }

    protected abstract @LayoutRes int getContentView();
    protected abstract void createView();

    public P getmPresenter() {
        return null;
    }

    @Override
    public Context getMContext() {
        return getContext();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 显示一个Toast信息
     *
     * @param content
     */
    public void showToast(String content) {
        if (mToast == null) {
            mToast = Toast.makeText(getContext(), content, Toast.LENGTH_SHORT);
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
        Intent intent = new Intent(getContext(), nextActivity);
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

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
