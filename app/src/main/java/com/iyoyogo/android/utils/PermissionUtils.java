package com.iyoyogo.android.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgheng on 2017/8/18.
 * <p>
 * 动态权限申请工具类
 */

public class PermissionUtils {

    /***
     * 获取需要授权中尚未被授权的权限集合
     * @param context
     * @param perms
     * @return
     */
    private static List<String> getDeniedPermissions(Context context, @NonNull String... perms) {


        List<String> deniedPermissions = new ArrayList<>();

        // Always return true for SDK < M, let the system deal with the permissions
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return deniedPermissions;
        }

        // Null context may be passed if we have detected Low API (less than M) so getting
        // to this point with a null context should not be possible.
        if (context == null) {
            throw new IllegalArgumentException("Can't check permissions for null context");
        }

        for (String perm : perms) {
            if (ContextCompat.checkSelfPermission(context, perm)
                    != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(perm);
            }
        }

        return deniedPermissions;
    }

    public static void requestPermissions(@NonNull Activity activity, int requestCode, @NonNull String[] perms, PermissionCallbacks listener) {

        List<String> deniedPermissions = getDeniedPermissions(activity, perms);
        if (deniedPermissions.size() == 0) {
            listener.onAllPermissionsGranted();
        } else {
            String[] deniedPermissionsArray = new String[deniedPermissions.size()];
            deniedPermissions.toArray(deniedPermissionsArray);
            ActivityCompat.requestPermissions(activity, deniedPermissionsArray, requestCode);
        }
    }

    public static void onRequestPermissionsResult(int requestCode,
                                                  @NonNull String[] permissions,
                                                  @NonNull int[] grantResults,
                                                  @NonNull PermissionCallbacks callbacks) {

        List<String> denied = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            String perm = permissions[i];
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                denied.add(perm);
            }
        }
        // Report denied permissions, if any.
        if (!denied.isEmpty()) {
            callbacks.onPermissionsDenied(denied);
        } else {
            callbacks.onAllPermissionsGranted();
        }


    }

    public interface PermissionCallbacks {

        void onAllPermissionsGranted();

        void onPermissionsDenied(List<String> perms);

    }
}
