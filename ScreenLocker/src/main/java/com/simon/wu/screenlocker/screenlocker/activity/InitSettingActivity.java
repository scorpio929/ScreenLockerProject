package com.simon.wu.screenlocker.screenlocker.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.simon.wu.screenlocker.screenlocker.R;
import com.simon.wu.screenlocker.screenlocker.utils.Constans;
import com.simon.wu.screenlocker.screenlocker.views.LauncherListDialog;

import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class InitSettingActivity extends ActionBarActivity {
    @InjectView(R.id.clear_system_lock_btn) Button clearSystemLock;
    @InjectView(R.id.clear_default_btn) Button clearDefault;
    @InjectView(R.id.set_default_btn) Button setDefault;
    @InjectView(R.id.choose_current_btn) Button chooseCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_setting);
        ButterKnife.inject(this);
    }


    @OnClick(R.id.clear_system_lock_btn)
    public void setClearSystemLock() {
        Intent localIntent = new Intent("android.app.action.SET_NEW_PASSWORD");
        localIntent.addCategory("android.intent.category.DEFAULT");
        startActivity(localIntent);
    }

    @OnClick(R.id.clear_default_btn)
    public void setClearDefault() {
        //获得当前桌面程序
        final Intent launcherIntent = new Intent(Intent.ACTION_MAIN);
        launcherIntent.addCategory(Intent.CATEGORY_HOME);
        final ResolveInfo res = getPackageManager().resolveActivity(launcherIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (res.activityInfo == null) {
            // should not happen. A home is always installed, isn't it?
            Toast.makeText(InitSettingActivity.this, getString(R.string.no_launcher), Toast.LENGTH_SHORT).show();
        }
        if ("android".equals(res.activityInfo.packageName)) {
            // No default selected
            Toast.makeText(InitSettingActivity.this, getString(R.string.clear_finish), Toast.LENGTH_SHORT).show();
        } else if (this.getPackageName().equals(res.activityInfo.packageName)) {
            // 如果当前桌面程序是我们的程序,直接返回
            return;
        } else {
            // res.activityInfo.packageName and res.activityInfo.name gives you the default app
            String currentHomePackage = res.activityInfo.packageName;
            //跳转到详细设置
            Intent intent;
            if (android.os.Build.VERSION.SDK_INT >= 9) {
                    /* on 2.3 and newer, use APPLICATION_DETAILS_SETTINGS with proper URI */
                Uri packageURI = Uri.parse("package:" + currentHomePackage);
                intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", packageURI);
            } else {
                    /* on older Androids, use trick to show app details */
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                intent.putExtra("com.android.settings.ApplicationPkgName", currentHomePackage);
                intent.putExtra("pkg", currentHomePackage);
            }
            startActivity(intent);
        }
    }

    @OnClick(R.id.set_default_btn)
    public void setDefault() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.putExtra(Constans.START_SCREEN_SAVER_TYPE, Constans.StartScreenSaverType.INIT_SETTING.getValue());
        startActivity(intent);
    }

    @OnClick(R.id.choose_current_btn)
    public void chooseCurrent() {
        //列出所有launcher 程序
        final PackageManager pm = getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> appList = pm.queryIntentActivities(mainIntent, 0);
        for (int i = 0, j = appList.size(); i < j; ++i) {
            if (appList.get(i).activityInfo.packageName.equals(InitSettingActivity.this.getPackageName())) {
                appList.remove(i);
                break;
            }
        }
        if (appList != null || appList.size() != 0) {
            Collections.sort(appList, new ResolveInfo.DisplayNameComparator(pm));
            LauncherListDialog dialog = LauncherListDialog.newInstance(appList);
            dialog.show(getSupportFragmentManager(), "launcher");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.init_setting, menu);
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
