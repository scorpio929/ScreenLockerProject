package com.simon.wu.screenlocker.screenlocker.tools;

import java.util.Calendar;

public class TimerManager {
    public static String getDate() {
        String str1 = null;
        String str2 = Integer.toString(1 + Calendar.getInstance().get(Calendar.MONTH));
        if (str2.length() == 1)
            str2 = "0" + str2;
        String str3 = Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        if (str3.length() == 1)
            str3 = "0" + str3;
        switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            default:
            case 1:
                str1 = "星期日";
                break;
            case 2:
                str1 = "星期一";
                break;
            case 3:
                str1 = "星期二";
                break;
            case 4:
                str1 = "星期三";
                break;
            case 5:
                str1 = "星期四";
                break;
            case 6:
                str1 = "星期五";
                break;
            case 7:
                str1 = "星期六";
        }
        return str2 + "月" + str3 + "日" + "  " + str1;
    }

    public static String getTime() {
        String str1 = Integer.toString(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        if (str1.length() == 1)
            str1 = "0" + str1;
        String str2 = Integer.toString(Calendar.getInstance().get(Calendar.MINUTE));
        if (str2.length() == 1)
            str2 = "0" + str2;
        return str1 + ":" + str2;
    }
}