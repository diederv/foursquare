package com.adyen.android.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class SettingsUtil {

    public static int getRadius(Application context, int defaultValue) {
        SharedPreferences settings = context.getSharedPreferences("SETTINGS", Context.MODE_PRIVATE);
        return settings.getInt("RADIUS", defaultValue);
    }

    public static void setRadius(Application context, int value) {
        SharedPreferences settings = context.getSharedPreferences("SETTINGS", Context.MODE_PRIVATE);
        settings.edit().putInt("RADIUS", value).apply();
    }

}
