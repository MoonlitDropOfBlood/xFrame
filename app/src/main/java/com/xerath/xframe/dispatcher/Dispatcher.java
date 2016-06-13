package com.xerath.xframe.dispatcher;

import com.xerath.xframe.actions.Action;
import com.xerath.xframe.stores.Store;

import java.util.ArrayList;
import java.util.List;

/**
 * Flux的Dispatcher模块
 * Created by ntop on 18/12/15.
 */
public class Dispatcher {
    private static Dispatcher instance;
    private final List<Store> stores = new ArrayList<>();

    public static Dispatcher getInstance() {
        if (instance == null) {
            instance = new Dispatcher();
        }
        return instance;
    }

    Dispatcher() {}

    public void register(final Store store) {
        stores.add(store);
    }

    public void unregister(final Store store) {
        stores.remove(store);
    }

    public void dispatch(Action action) {
        post(action);
    }

    private void post(final Action action) {
        for (Store store : stores) {
            store.onAction(action);
        }
    }
}
