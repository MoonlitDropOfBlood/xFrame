package com.xerath.xframe.utils.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;
import com.xerath.xframe.dispatcher.Dispatcher;
import com.xerath.xframe.presenters.XFramePresenter;
import com.xerath.xframe.stores.Store;

/**
 * Created by Xerath on 2016/6/10.
 * Extends xFrame
 */
public abstract class BaseSupportFragment<X extends XFramePresenter, Y extends Store> extends Fragment {
    protected View rootView;
    private Dispatcher dispatcher;
    protected X presenter;
    protected Y store;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = createRootView(inflater, container);
            initDependencies();
        }
        dispatcher.register(store);
        return rootView;
    }

    private void initDependencies() {
        dispatcher = Dispatcher.getInstance();
        presenter = creatorPresenter(dispatcher);
        store = creatorStore();
    }

    protected abstract View createRootView(LayoutInflater inflater, ViewGroup container);

    protected abstract void initData();

    protected abstract X creatorPresenter(Dispatcher dispatcher);

    protected abstract Y creatorStore();

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)
            initData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint())
            initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        store.register(this);
    }

    protected abstract <T extends Store> void render(Store.StoreChangeEvent event, T store);

    @Subscribe
    public void onStoreChange(Store.StoreChangeEvent event) {
        render(event, store);
    }

    @Override
    public void onPause() {
        super.onPause();
        store.unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dispatcher.unregister(store);
    }
}
