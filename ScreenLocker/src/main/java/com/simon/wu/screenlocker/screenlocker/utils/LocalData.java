package com.simon.wu.screenlocker.screenlocker.utils;

import android.content.Context;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;

/**
 * Created by Simon.wu on 2014/7/21.
 */
public class LocalData {
    private static String mCustomLauncherPackage;
    private static String mCustomLauncherName;

    public static String getmCustomLauncherPackage(Context context) {
        if (TextUtils.isEmpty(mCustomLauncherPackage)) {
            mCustomLauncherPackage = PreferencesUtils.getString(context, Constans.CUSTOM_LAUNCHER_PACKAGE);
        }
        return mCustomLauncherPackage;
    }

    public static void setmCustomLauncherPackage(String mCustomLauncherPackage) {
        LocalData.mCustomLauncherPackage = mCustomLauncherPackage;
    }

    public static String getmCustomLauncherName(Context context) {
        if (TextUtils.isEmpty(mCustomLauncherName)) {
            mCustomLauncherName = PreferencesUtils.getString(context, Constans.CUSTOM_LAUNCHER_NAME);
        }
        return mCustomLauncherName;
    }

    public static void setmCustomLauncherName(String mCustomLauncherName) {
        LocalData.mCustomLauncherName = mCustomLauncherName;
    }

    public static boolean isIsLockEnabled(Context context) {
        return PreferencesUtils.getBoolean(context, Constans.IS_LOCK_ENABLED);
    }

    public static void setIsLockEnabled(Context context, boolean isLockEnabled) {
        PreferencesUtils.putBoolean(context, Constans.IS_LOCK_ENABLED, isLockEnabled);
    }
}
