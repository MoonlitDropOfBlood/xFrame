package com.xerath.xframe.presenters;

import com.xerath.xframe.actions.ActionsCreator;
import com.xerath.xframe.dispatcher.Dispatcher;

/**
 * 业务逻辑的基类
 *
 * Created by Xerath on 2016/6/11.
 * Extends xFrame
 */
public abstract class XFramePresenter {
    private ActionsCreator actionsCreator;

    public XFramePresenter(Dispatcher dispatcher) {
        actionsCreator = ActionsCreator.getInstance(dispatcher);
    }

    protected abstract String getTAG();

    /**
     * 发布动作类型
     *
     * @param ACITON_TYPE   动作类型
     * @param data          携带的数据
     * @param <T>           携带的数据类型
     */
    public <T> void sendCommand(String ACITON_TYPE, T data) {
        actionsCreator.sendMessage(ACITON_TYPE, data);
    }

}
