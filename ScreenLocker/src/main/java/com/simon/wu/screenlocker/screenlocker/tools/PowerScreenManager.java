package com.simon.wu.screenlocker.screenlocker.tools;

import android.content.Context;
import android.os.PowerManager;

/**
 * Created by Simon.Wu on 2014/7/24.
 */
public class PowerScreenManager {
    private static PowerScreenManager mInstance;
    private PowerManager mPowerManager;

    private PowerScreenManager(Context paramContext) {
        this.mPowerManager = ((PowerManager) paramContext.getSystemService(Context.POWER_SERVICE));
    }

    public static PowerScreenManager getInstance(Context paramContext) {
        if (mInstance == null)
            mInstance = new PowerScreenManager(paramContext);
        return mInstance;
    }

    public boolean getScreenStatus() {
        return this.mPowerManager.isScreenOn();
    }
}
