package com.nocompany.frank.weather.utils;

import android.util.Log;

/**
 * Created by Frank on 2016/12/30 0030.
 * 控制打印输出
 */

public class L {
    public static boolean isDebug = true;

    /**
     * 打印information
     *
     * @param tag TAG
     * @param msg message
     */
    public static void i(String tag, String msg) {
        if (isDebug) {
            Log.i(tag, msg);
        }
    }

    /**
     * 打印debug
     *
     * @param tag TAG
     * @param msg message
     */
    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d(tag, msg);
        }
    }

    /**
     * 打印error
     *
     * @param tag TAG
     * @param msg message
     */
    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, msg);
        }
    }
}
