package com.km.rmbank.module.home.image;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseFragment;
import com.ps.androidlib.utils.glide.GlideUtils;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PictureShowFragment extends BaseFragment {

    @BindView(R.id.iv_picture)
    ImageView ivPicture;

    public static PictureShowFragment newInstance(Bundle bundle) {

        PictureShowFragment fragment = new PictureShowFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_picture_show;
    }

    @Override
    protected void createView() {
        String pictureUrl = getArguments().getString("pictureUrl");
        if (!TextUtils.isEmpty(pictureUrl)){
            GlideUtils.loadImage(ivPicture,pictureUrl);
        }
    }

}
