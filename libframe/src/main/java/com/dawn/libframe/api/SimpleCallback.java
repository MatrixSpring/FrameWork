package com.dawn.libframe.api;

import com.dawn.libframe.mvpbase.BaseView;

/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */

public abstract class SimpleCallback<T> implements ApiCallback<T> {

    private BaseView mView;

    public SimpleCallback(BaseView iview){
        this.mView = iview;
    }

    @Override
    public void onError(Throwable e) {
        mView.onError(e);
    }

    @Override
    public void onCompleted() {
        mView.onCompleted();
    }

}
