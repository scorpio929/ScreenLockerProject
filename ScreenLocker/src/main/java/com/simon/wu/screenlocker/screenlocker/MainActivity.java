package com.simon.wu.screenlocker.screenlocker;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.simon.wu.screenlocker.screenlocker.activity.InitSettingActivity;
import com.simon.wu.screenlocker.screenlocker.utils.Constans;
import com.simon.wu.screenlocker.screenlocker.utils.PreferencesUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends FragmentActivity {
    @InjectView(R.id.lock_enable_btn) Button lockEnable;
    @InjectView(R.id.background_setting_btn) Button backgroundSetting;
    @InjectView(R.id.lock_setting_btn) Button lockSetting;
    @InjectView(R.id.feedback_btn) Button feedback;

    private boolean isLockEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.lock_enable_btn)
    public void setLockEnable(Button btn) {
        if (isLockEnabled) {
            btn.setText(getString(R.string.enable_lockscreen));
        } else {
            //blockHomeButton();
            checkIfIsHomeButtonBlocked();
            btn.setText(getString(R.string.disable_lockscreen));
        }
        isLockEnabled = !isLockEnabled;
    }

    private void checkIfIsHomeButtonBlocked() {
        //获得当前桌面程序
        final Intent launcherIntent = new Intent(Intent.ACTION_MAIN);
        launcherIntent.addCategory(Intent.CATEGORY_HOME);
        final ResolveInfo res = getPackageManager().resolveActivity(launcherIntent, PackageManager.MATCH_DEFAULT_ONLY);
        //如果当前桌面程序是本程序,并且设置过目标桌面程序,不再跳到设置界面
        if (this.getPackageName().equals(res.activityInfo.packageName) && PreferencesUtils.getString(this, Constans.SET_LAUNCHER_PACKAGE_NAME) != null) {

        } else {
            //跳到设置界面
            startActivity(new Intent(this, InitSettingActivity.class));
        }
    }

    @OnClick(R.id.background_setting_btn)
    public void setBackgroundSetting() {

    }

    @OnClick(R.id.lock_setting_btn)
    public void setLockSetting() {
    }

    @OnClick(R.id.feedback_btn)
    public void setFeedback() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
}
