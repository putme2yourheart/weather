package com.nocompany.frank.weather.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Frank on 2017/1/14 0014.
 * 软键盘工具类
 * <p>
 * <ui>
 * <li>{@link #showOrHideKeyboard(Context, View)}</li>
 * <li>{@link #showKeyboard(Context, View)}</li>
 * <li>{@link #hideKeyboard(Context, View)}</li>
 * </ui>
 */

public class KeyboardUtils {

    public static final String TAG = "KeyboardUtils";

    /**
     * 输入法在窗口上已经显示，则隐藏，反之则显示
     *
     * @param context Context
     * @param view    View
     */
    public static void showOrHideKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        inputMethodManager.toggleSoftInputFromWindow(view.getWindowToken(), 0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 弹出软键盘
     *
     * @param context Context
     * @param view    View
     */
    public static void showKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        inputMethodManager.showSoftInput(view, InputMethodManager.RESULT_SHOWN);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 隐藏软键盘
     *
     * @param context Context
     * @param view    View
     */
    public static void hideKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
