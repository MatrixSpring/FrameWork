package com.dawn.libframe.permission;

//import android.support.annotation.NonNull;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class PermissionUtil {

    private static final String TAG = PermissionUtil.class.getSimpleName();

    private PermissionFragment permissionFragment;

    public PermissionUtil(@NonNull FragmentActivity activity) {
        permissionFragment = getRxPermissionsActivity(activity);
    }

    public PermissionUtil(@NonNull Fragment fragment) {
        permissionFragment = getRxPermissionsFragment(fragment);
    }

    private PermissionFragment getRxPermissionsActivity(FragmentActivity activity) {
        PermissionFragment fragment = (PermissionFragment) activity.getSupportFragmentManager().findFragmentByTag(TAG);
        boolean isNewInstance = fragment == null;
        if (isNewInstance) {
            fragment = new PermissionFragment();
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .add(fragment, TAG)
                    .commit();
            fragmentManager.executePendingTransactions();
        }

        return fragment;
    }

    private PermissionFragment getRxPermissionsFragment(Fragment activity) {
        PermissionFragment fragment = (PermissionFragment) activity.getChildFragmentManager().findFragmentByTag(TAG);
        boolean isNewInstance = fragment == null;
        if (isNewInstance) {
            fragment = new PermissionFragment();
            FragmentManager fragmentManager = activity.getChildFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .add(fragment, TAG)
                    .commit();
            fragmentManager.executePendingTransactions();
        }

        return fragment;
    }

    /**
     * 外部使用 申请权限
     *
     * @param permissions 申请授权的权限
     * @param listener    授权回调的监听
     */
    public void requestPermissions(String[] permissions, PermissionListener listener) {
        permissionFragment.setListener(listener);
        permissionFragment.requestPermissions(permissions);

    }

}
