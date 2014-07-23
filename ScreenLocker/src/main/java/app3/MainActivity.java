package app3;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.simon.wu.screenlock.app3.utils.Constans;
import com.simon.wu.screenlock.app3.utils.LocalData;
import com.simon.wu.screenlock.app3.views.LauncherListDialog;
import com.simon.wu.screenlock.app3.views.LockApplication;
import com.simon.wu.screenlock.app3.views.TimeView;

import java.util.Collections;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private RelativeLayout timeLayout;
    private RelativeLayout mainContent;
    private Button b;
    private Button b1;
    private Button b2;
    private Button b3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getStartType(getIntent());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //屏蔽掉系统的锁屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        initViews();
    }

    private void jumpRecent() {
        //获取当前屏幕显示Activity
        Intent intent = new Intent().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).setComponent(new ComponentName(this.getPackageName(), MainActivity.class.getName()));
        startActivity(intent);
    }

    private void getStartType(Intent intent) {
        //判断启动来源
        if (intent.getIntExtra(Constans.START_SCREEN_SAVER_TYPE, 2) == 2 && !((LockApplication) getApplication()).isLockScreenShowing) {
            jumpHome();
        }
        Log.v("Intent", intent.toString());
    }


    private void jumpHome() {
        //获得当前使用launcher
        if (LocalData.currentLauncher == null) {
            Toast.makeText(this, "no localdata currentLauncher", Toast.LENGTH_SHORT).show();
            return;
        }
        ResolveInfo temp = LocalData.currentLauncher;
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setComponent(new ComponentName(temp.activityInfo.packageName, temp.activityInfo.name));
        Log.d("launcher", "CURRENT Launcher ::" + temp.activityInfo.packageName + "," + temp.activityInfo.name);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getStartType(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        ((LockApplication) getApplication()).isLockScreenShowing = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ((LockApplication) getApplication()).isLockScreenShowing = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
        return true;
    }

    private void initViews() {
        mainContent = (RelativeLayout) findViewById(R.id.main_content);
        timeLayout = new TimeView(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        mainContent.addView(timeLayout, params);

        b = (Button) findViewById(R.id.button);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*startActivity(
                        new Intent(Settings.action_sett));*/
                Intent localIntent = new Intent("android.app.action.SET_NEW_PASSWORD");
                localIntent.addCategory("android.intent.category.DEFAULT");
                startActivity(localIntent);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //获得当前桌面程序
                final Intent launcherIntent = new Intent(Intent.ACTION_MAIN);
                launcherIntent.addCategory(Intent.CATEGORY_HOME);
                final ResolveInfo res = getPackageManager().resolveActivity(launcherIntent, PackageManager.MATCH_DEFAULT_ONLY);
                if (res.activityInfo == null) {
                    // should not happen. A home is always installed, isn't it?
                }
                if ("android".equals(res.activityInfo.packageName)) {
                    // No default selected
                    Toast.makeText(MainActivity.this, getString(R.string.clear_finish), Toast.LENGTH_SHORT).show();
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
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //列出所有launcher 程序
                final PackageManager pm = getPackageManager();
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_HOME);
                List<ResolveInfo> appList = pm.queryIntentActivities(mainIntent, 0);
                for (int i = 0, j = appList.size(); i < j; ++i) {
                    if (appList.get(i).activityInfo.packageName.equals(MainActivity.this.getPackageName())) {
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
        });
        timeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (!hasFocus) {
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }
    }
}
