package com.xerath.xframe.stores;

import com.squareup.otto.Bus;
import com.xerath.xframe.actions.Action;

/**
 * Flux的Store模块
 * Created by ntop on 18/12/15.
 */
public abstract class Store {
    private static final Bus bus = new Bus();

    protected Store() {
    }

    public void register(final Object view) {
        bus.register(view);
    }

    public void unregister(final Object view) {
        bus.unregister(view);
    }

    private void emitStoreChange(String actionType) {
        bus.post(changeEvent(actionType));
    }

    public abstract StoreChangeEvent changeEvent(String actionType);

    public void onAction(Action action) {
        onActionEmit(action);
        emitStoreChange(action.getType());
    }

    protected abstract void onActionEmit(Action action);

    public class StoreChangeEvent {
        private final String ACTION_TYPE;

        protected StoreChangeEvent(String ACTION_TYPE) {
            this.ACTION_TYPE = ACTION_TYPE;
        }

        public String getACTION_TYPE() {
            return ACTION_TYPE;
        }
    }
}