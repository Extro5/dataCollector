package com.example.datacollector.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

public class PreferencesUtils {

    private static final String PREFERENCES = "auto_master_preferences";

    private static final String KEY_COMMAND_LINE = "command_line";

    private static final String KEY_ACTIVITY_NAME = "activity_name";

    private static final String KEY_USER_ID = "user_id";

    private static final String KEY_API_KEY = "api_key";

    @Nullable
    public static String getUserId(@NonNull Context context) {
        return prefs(context).getString(KEY_USER_ID, null);
    }

    public static boolean isUserIdEmpty(@NonNull Context context) {
        return TextUtils.isEmpty(getUserId(context));
    }

    public static void saveUserId(@NonNull Context context, @NonNull String userId) {
        prefs(context)
                .edit()
                .putString(KEY_USER_ID, userId)
                .apply();
    }

    public static void removeUserId(@NonNull Context context) {
        prefs(context)
                .edit()
                .remove(KEY_USER_ID)
                .apply();
    }

    @Nullable
    public static String getApiKey(@NonNull Context context) {
        return prefs(context).getString(KEY_API_KEY, null);
    }

    public static boolean isApiKeyEmpty(@NonNull Context context) {
        return TextUtils.isEmpty(getApiKey(context));
    }

    public static void saveApiKey(@NonNull Context context, @NonNull String apiKey) {
        prefs(context)
                .edit()
                .putString(KEY_API_KEY, apiKey)
                .apply();
    }

    public static void removeApiKey(@NonNull Context context) {
        prefs(context)
                .edit()
                .remove(KEY_API_KEY)
                .apply();
    }

    @Nullable
    public static String getCommandLine(@NonNull Context context) {
        return prefs(context).getString(KEY_COMMAND_LINE, null);
    }

    public static boolean isCommandLineEmpty(@NonNull Context context) {
        return TextUtils.isEmpty(getCommandLine(context));
    }

    public static void saveCommandLine(@NonNull Context context, @NonNull String commandLine) {
        prefs(context)
                .edit()
                .putString(KEY_COMMAND_LINE, commandLine)
                .apply();
    }

    public static void removeCommandLine(@NonNull Context context) {
        prefs(context)
                .edit()
                .remove(KEY_COMMAND_LINE)
                .apply();
    }

    @Nullable
    public static String getActivityName(@NonNull Context context) {
        return prefs(context).getString(KEY_ACTIVITY_NAME, null);
    }

    public static boolean isActivityName(@NonNull Context context) {
        return TextUtils.isEmpty(getActivityName(context));
    }

    public static void saveActivityName(@NonNull Context context, @NonNull String activityName) {
        prefs(context)
                .edit()
                .putString(KEY_ACTIVITY_NAME, activityName)
                .apply();
    }

    public static void removeActivityName(@NonNull Context context) {
        prefs(context)
                .edit()
                .remove(KEY_ACTIVITY_NAME)
                .apply();
    }

    public static void clearPref(@NonNull Context context) {
        prefs(context)
                .edit()
                .clear()
                .apply();
    }

    @NonNull
    private static SharedPreferences prefs(@NonNull Context context) {
        return context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
    }
}
