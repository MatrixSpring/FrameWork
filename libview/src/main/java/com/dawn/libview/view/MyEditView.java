package com.dawn.libview.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatEditText;

public class MyEditView extends AppCompatEditText {
    private GestureDetector gestureDetector;
    public MyEditView(Context context) {
        super(context, null);
    }

    public MyEditView(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public MyEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // call that method
            setCustomSelectionActionModeCallback(new ActionModeCallbackInterceptor());
        }
//        gestureDetector = new GestureDetector(new MySGestureDetector());
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return gestureDetector.onTouchEvent(event);
//    }

    private class ActionModeCallbackInterceptor implements ActionMode.Callback {

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return false;
        }


        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }


        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }


        public void onDestroyActionMode(ActionMode mode) {
        }
    }

    private class MySGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onContextClick(MotionEvent e) {
            return super.onContextClick(e);
        }
    }

    public void setTouchEventListener(){

    }

    public interface TouchEventListener{

    }
}
