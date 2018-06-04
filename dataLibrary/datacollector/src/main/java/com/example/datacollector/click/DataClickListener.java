package com.example.datacollector.click;

import android.view.View;

import com.example.datacollector.utils.PreferencesUtils;

public abstract class DataClickListener implements View.OnClickListener {

    private static String commandLine;

    @Override
    public void onClick(View v) {
        Class activity = onClicked();
        commandLine += v.getContext().getResources().getResourceEntryName(v.getId());
        PreferencesUtils.saveCommandLine(v.getContext(), commandLine);
        PreferencesUtils.saveActivityName(v.getContext(), activity.getSimpleName());
    }

    protected abstract Class onClicked();

    public static void resetCommandLine() {
        commandLine = "";
    }
}
