package com.xerath.xframe.utils.widget;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.squareup.otto.Subscribe;
import com.xerath.xframe.dispatcher.Dispatcher;
import com.xerath.xframe.presenters.XFramePresenter;
import com.xerath.xframe.stores.Store;

/**
 * Activity ：   并不直接做页面修改的操作，Activity中的View的所有操作在通常情况下都应该通过Store接收到动作指令后在进行操作
 * Store    ：   Store的工作是做命令的预处理，并保存数据，请不要在Store中操作数据。
 * Presenter：   业务逻辑处理，绝大多数的异步都应该在此处进行
 *
 * Created by Xerath on 2016/6/10.
 * Extends xFrame
 */
public abstract class BaseActivity<X extends XFramePresenter,Y extends Store> extends AppCompatActivity {
    private Dispatcher dispatcher;
    protected X presenter;
    protected Y store;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isSetRootView()) {
            setContentView(setLayoutRes());
            initView();
        }
        initData();
        initDependencies();
    }

    private void initDependencies() {
        dispatcher = Dispatcher.getInstance();
        presenter = creatorPresenter(dispatcher);
        store = creatorStore();
        dispatcher.register(store);
    }

    @Override
    protected void onResume() {
        super.onResume();
        store.register(this);
    }

    @Subscribe
    public void onStoreChange(Store.StoreChangeEvent event) {
        render(event, store);
    }

    @Override
    protected void onPause() {
        super.onPause();
        store.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispatcher.unregister(store);
    }

    protected abstract boolean isSetRootView();

    protected abstract @LayoutRes int setLayoutRes();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract X creatorPresenter(Dispatcher dispatcher);

    protected abstract Y creatorStore();

    protected abstract <T extends Store> void render(Store.StoreChangeEvent event, T store);
}
