package com.dawn.libframe.bindview;

import android.app.Activity;
import android.view.View;

public class ViewFinder {
    private Activity mActivity;
    private View mView;

    public ViewFinder(View view) {
        this.mView = view;
    }

    public ViewFinder(Activity activity) {
        this.mActivity = activity;
    }

    public View findViewById(int viewId) {
        if ((mActivity == null) && (mView == null)) {
            return null;
        }
        return mActivity != null ? mActivity.findViewById(viewId) : mView.findViewById(viewId);
    }

}
