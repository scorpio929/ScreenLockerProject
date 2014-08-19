package com.simon.wu.screenlocker.screenlocker.activity;

import android.app.IntentService;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.Toast;

import com.simon.wu.screenlocker.screenlocker.utils.Constans;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ScreenLockerService extends Service {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_START = "com.simon.wu.screenlocker.screenlocker.activity.action.START";
    private static final String ACTION_STOP = "com.simon.wu.screenlocker.screenlocker.activity.action.STOP";
    private final IntentFilter i;
    public static boolean isRunning;
    private BroadcastReceiver screenReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                Toast.makeText(context, "screen is off", Toast.LENGTH_SHORT).show();
                System.out.println("screen is off");
                startActivity(new Intent(context, LockScreenActivity.class).putExtra(Constans.START_SCREEN_SAVER_TYPE, Constans.StartScreenSaverType.SCREEN_OFF.getValue()).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                Toast.makeText(context, "screen is on", Toast.LENGTH_SHORT).show();
                System.out.println("screen is on");
            }
        }
    };

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionStart(Context context, String param1, String param2) {
        if (!isRunning) {
            Intent intent = new Intent(context, ScreenLockerService.class);
            intent.setAction(ACTION_START);
            context.startService(intent);
        }
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionStop(Context context, String param1, String param2) {
        if (isRunning) {
            Intent intent = new Intent(context, ScreenLockerService.class);
            intent.setAction(ACTION_STOP);
            context.startService(intent);
        }
    }

    public ScreenLockerService() {
        i = new IntentFilter();
        i.addAction("android.intent.action.SCREEN_OFF");
        i.addAction("android.intent.action.SCREEN_ON");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String action = intent.getAction();
        if (ACTION_START.equals(action)) {
            handleActionFoo();
        } else if (ACTION_STOP.equals(action)) {
            handleActionBaz();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo() {
        isRunning = true;
        registerReceiver(screenReceiver, i);
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz() {
        isRunning = false;
        unregisterReceiver(screenReceiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isRunning) {
            unregisterReceiver(screenReceiver);
        }
        isRunning = false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
