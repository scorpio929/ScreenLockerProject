package com.simon.wu.screenlocker.screenlocker.activity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.simon.wu.screenlocker.screenlocker.MainActivity;
import com.simon.wu.screenlocker.screenlocker.R;
import com.simon.wu.screenlocker.screenlocker.utils.Constans;
import com.simon.wu.screenlocker.screenlocker.utils.LocalData;

public class LockScreenActivity extends FragmentActivity implements BackgroundFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getStartType(getIntent());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //屏蔽掉系统的锁屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lock_screen);
        initViews();
    }

    private void initViews() {
        getSupportFragmentManager().beginTransaction().add(R.id.background_framelayout, BackgroundFragment.newInstance()).commit();
    }

    private void jumpCustomHome() {
        //跳转当前使用launcher
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setComponent(new ComponentName(LocalData.getmCustomLauncherPackage(this), LocalData.getmCustomLauncherName(this)));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void jumpRecent() {
        //获取当前屏幕显示Activity
        Intent intent = new Intent().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).setComponent(new ComponentName(this.getPackageName(), MainActivity.class.getName()));
        startActivity(intent);
    }

    private void getStartType(Intent intent) {
        //判断启动来源,没有Extra则为按下Home键
        int startFlag = intent.getIntExtra(Constans.START_SCREEN_SAVER_TYPE, Constans.StartScreenSaverType.PRESS_HOME.getValue());
        if (startFlag == Constans.StartScreenSaverType.PRESS_HOME.getValue() && !((LockApplication) getApplication()).isLockScreenShowing) {
            jumpCustomHome();
            finish();
        } else if (((LockApplication) getApplication()).isLockScreenShowing) {

        } else if (startFlag == Constans.StartScreenSaverType.INIT_SETTING.getValue()) {
            startActivity(new Intent(this, InitSettingActivity.class));
            finish();
        }
        Log.v("Intent", "startFlag : " + startFlag + "," + intent.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((LockApplication) getApplication()).isLockScreenShowing = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        ((LockApplication) getApplication()).isLockScreenShowing = false;
    }

    public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lock_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction() {
        finish();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus) {
            ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            am.moveTaskToFront(getTaskId(), ActivityManager.MOVE_TASK_WITH_HOME);
            sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
        }
    }
}
