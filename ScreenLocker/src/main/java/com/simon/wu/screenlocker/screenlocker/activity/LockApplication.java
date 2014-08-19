package com.simon.wu.screenlocker.screenlocker.activity;

import android.app.ActivityManager;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import com.simon.wu.screenlocker.screenlocker.MainActivity;
import com.simon.wu.screenlocker.screenlocker.tools.HomeWatcher;
import com.simon.wu.screenlocker.screenlocker.utils.Constans;
import com.simon.wu.screenlocker.screenlocker.utils.LocalData;

import java.util.List;

/**
 * Created by Simon.Wu on 2014/7/15.
 */
public class LockApplication extends Application {

    public static HomeWatcher mHomeWatcher;
    public static boolean isLockScreenShowing;

    @Override
    public void onCreate() {
        super.onCreate();
        //开启服务,启动屏幕点亮/关闭监听
        if (LocalData.isIsLockEnabled(this)) {
            ScreenLockerService.startActionStart(this, null, null);
        }

        //屏蔽系统锁屏
        //KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        //KeyguardManager.KeyguardLock kk = km.newKeyguardLock("");
        //kk.disableKeyguard();

        //监听Home键
        mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new HomeWatcher.OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                Log.e("mHomeWatcher", "onHomePressed");
            }

            @Override
            public void onHomeLongPressed() {
                if (isLockScreenShowing) {
                    // jumpRecent();
                }
                Log.e("mHomeWatcher", "onHomeLongPressed");
            }
        });
        mHomeWatcher.startWatch();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mHomeWatcher.stopWatch();
        stopService(new Intent(this, ScreenLockerService.class));
    }
}
