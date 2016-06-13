package com.xerath.xframe.actions;

import com.xerath.xframe.dispatcher.Dispatcher;

/**
 * Flux的ActionCreator模块
 * Created by ntop on 18/12/15.
 */
public class ActionsCreator {

    private static ActionsCreator instance;
    final Dispatcher dispatcher;

    ActionsCreator(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public static ActionsCreator getInstance(Dispatcher dispatcher) {
        if (instance == null) {
            instance = new ActionsCreator(dispatcher);
        }
        return instance;
    }

    public <T> void sendMessage(String ACTION_TYPE, T data) {
        dispatcher.dispatch(new Action<T>(ACTION_TYPE, data));
    }
}