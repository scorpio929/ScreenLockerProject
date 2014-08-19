package com.simon.wu.screenlocker.screenlocker;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

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
        isLockEnabled = PreferencesUtils.getBoolean(this, Constans.IS_LOCK_ENABLED, false);
    }

    @OnClick(R.id.lock_enable_btn)
    public void setLockEnable(Button btn) {
        if (isLockEnabled) {
            btn.setText(getString(R.string.enable_lockscreen));
        } else {
            checkIfHomeBlocked();
            btn.setText(getString(R.string.disable_lockscreen));
        }
        isLockEnabled = !isLockEnabled;
    }

    private void checkIfHomeBlocked() {
        //获得当前桌面程序
        final Intent launcherIntent = new Intent(Intent.ACTION_MAIN);
        launcherIntent.addCategory(Intent.CATEGORY_HOME);
        final ResolveInfo res = getPackageManager().resolveActivity(launcherIntent, PackageManager.MATCH_DEFAULT_ONLY);
        //如果当前桌面程序是本程序,并且设置过目标桌面程序,不再跳到设置界面
        if (this.getPackageName().equals(res.activityInfo.packageName) && PreferencesUtils.getString(this, Constans.CUSTOM_LAUNCHER_PACKAGE) != null && PreferencesUtils.getString(this, Constans.CUSTOM_LAUNCHER_NAME) != null) {
            Toast.makeText(this, "设置已完成", Toast.LENGTH_SHORT).show();
        } else {
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
