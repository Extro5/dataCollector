package com.example.datacollector.click;

import android.view.MotionEvent;
import android.view.View;

import com.example.datacollector.utils.PreferencesUtils;

public abstract class DataTouchListener implements View.OnTouchListener {

    private static String commandLine = "";

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                commandLine += "Down: " + x + "," + y;
                break;
            case MotionEvent.ACTION_MOVE:
                commandLine += "Move: " + x + "," + y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                commandLine += "Up: " + x + "," + y;
                break;
        }
        Class activity = onTouched();

        PreferencesUtils.saveCommandLine(v.getContext(), commandLine);
        PreferencesUtils.saveActivityName(v.getContext(), activity.getSimpleName());
        return true;
    }

    protected abstract Class onTouched();

    public static void resetCommandLine() {
        commandLine = "";
    }
}