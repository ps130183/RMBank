package com.km.rmbank.module.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home3ForumFragment extends BaseFragment {


    public static Home3ForumFragment newInstance(Bundle bundle) {
        Home3ForumFragment fragment = new Home3ForumFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    protected int getContentView() {
        return R.layout.fragment_home3_forum;
    }

    @Override
    protected void createView() {

    }

}
