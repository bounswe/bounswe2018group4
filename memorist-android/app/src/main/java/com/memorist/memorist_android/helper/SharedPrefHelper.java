package com.memorist.memorist_android.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * This class contains shortcut functions for shared preferences utilities.
 */
public class SharedPrefHelper {

    // Database table name used in shared preferences.
    private static final String PREFS_NAME = "com.memorist.memorist_android";

    // Key value used in storing mail address.
    private static final String KEY_USERNAME = "user_email";

    public static String getUserEmail(Context context) {
        return getStringValue(context, KEY_USERNAME);
    }

    public static void setUserEmail(Context context, String value) {
        setStringValue(context, KEY_USERNAME, value);
    }

    private static String getStringValue(Context context, String key) {
        SharedPreferences sharedPreferences = getPreferences(context);
        return sharedPreferences.getString(key, null);
    }

    private static void setStringValue(Context context, String key, String value) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(key, value);
        editor.apply();
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
}
