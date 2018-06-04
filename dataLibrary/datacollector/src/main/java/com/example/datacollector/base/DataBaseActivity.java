package com.example.datacollector.base;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.datacollector.click.DataClickListener;
import com.example.datacollector.rest.DataSender;
import com.example.datacollector.utils.PreferencesUtils;

import me.tatarka.rxloader.RxLoaderManager;

public abstract class DataBaseActivity extends AppCompatActivity {

    private long startTime;

    @Override
    protected void onResume() {
        startTime = System.currentTimeMillis();
        super.onResume();
    }

    @Override
    protected void onPause() {
        long endTime = System.currentTimeMillis();
        long elapsedTime = ((endTime - startTime) / 1000);
        DataSender dataSender = new DataSender(RxLoaderManager.get(this), getApplication());
        dataSender.systemInformationSender(PreferencesUtils.getCommandLine(getApplicationContext()), elapsedTime);
        PreferencesUtils.removeCommandLine(getApplicationContext());
        DataClickListener.resetCommandLine();
        super.onPause();
    }
}