package com.simon.wu.screenlocker.screenlocker.utils;

/**
 * Created by Administrator on 2014/7/22.
 */
public class Constans {
    public static final String START_SCREEN_SAVER_TYPE = "start_screen_saver_type";

    public static final String CUSTOM_LAUNCHER_PACKAGE = "custom_launcher_package";
    public static final String CUSTOM_LAUNCHER_NAME = "custom_launcher_name";

    public static final String IS_LOCK_ENABLED = "is_lock_enabled";

    public static final int IS_HOME_BLOCKED = 1;

    public enum StartScreenSaverType {
        INIT_SETTING(1),
        PRESS_HOME(2),
        SCREEN_OFF(3);

        private int value;

        private StartScreenSaverType(int i) {
            this.value = i;
        }

        public int getValue() {
            return this.value;
        }
    }
}
