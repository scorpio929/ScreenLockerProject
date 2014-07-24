package com.simon.wu.screenlocker.screenlocker.activity;

import android.app.ActivityManager;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import com.simon.wu.screenlocker.screenlocker.tools.HomeWatcher;
import com.simon.wu.screenlocker.screenlocker.utils.Constans;

import java.util.List;

/**
 * Created by Simon.Wu on 2014/7/15.
 */
public class LockApplication extends Application {
    private BroadcastReceiver screenReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
                Toast.makeText(context, "screen is off", Toast.LENGTH_SHORT).show();
                System.out.println("screen is off");
                startActivity(new Intent(context, LockScreenActivity.class).putExtra(Constans.START_SCREEN_SAVER_TYPE, 3).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } else if (intent.getAction().equals("android.intent.action.SCREEN_ON")) {
                Toast.makeText(context, "screen is on", Toast.LENGTH_SHORT).show();
                System.out.println("screen is on");
            }
        }
    };
    public static HomeWatcher mHomeWatcher;
    public static boolean isLockScreenShowing;

    @Override
    public void onCreate() {
        super.onCreate();

        IntentFilter i = new IntentFilter();
        i.addAction("android.intent.action.SCREEN_OFF");
        i.addAction("android.intent.action.SCREEN_ON");
        registerReceiver(screenReceiver, i);

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
                    jumpRecent();
                }
                Log.e("mHomeWatcher", "onHomeLongPressed");
            }
        });
        mHomeWatcher.startWatch();
    }

    private void jumpRecent() {
        //获取当前屏幕显示Activity
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        // get the info from the currently running task
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        Log.d("topActivity", "CURRENT Activity ::" + taskInfo.get(0).topActivity.getClassName());
        ComponentName componentInfo = taskInfo.get(0).topActivity;
        Intent intent = new Intent(this, LockScreenActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver(screenReceiver);
        mHomeWatcher.stopWatch();
    }
}
