package com.example.vladislav.datalibrary.test;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.datacollector.base.DataBaseActivity;
import com.example.datacollector.click.DataClickListener;
import com.example.datacollector.model.request_object.DataException;
import com.example.datacollector.rest.DataSender;
import com.example.datacollector.utils.AboutDeviceUtils;
import com.example.datacollector.utils.PreferencesUtils;
import com.example.vladislav.datalibrary.R;

import java.util.Arrays;

import me.tatarka.rxloader.RxLoaderManager;

public class TestActivity extends DataBaseActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataSender dataSender = new DataSender(RxLoaderManager.get(this), getApplication());
        dataSender.userSender("test1", "test2", "test3", "lol4444", "lol5555", "lol6666", "18", "hockey", "android developer");

       // dataSender.aboutUserInformationSender("Иванов Иван Иванович 1995 года рождения. Живу в Казани. Моя почта cool.extro5@yandex.ru. Обращаться по телефону 89876543210");

        AboutDeviceUtils.sendDeviceInfo(this);

        button = (Button) findViewById(R.id.btn1);
        button.setOnClickListener(new DataClickListener() {
            @Override
            public Class onClicked() {
                return TestActivity.class;
            }
        });

        try {
            a();
        } catch (Exception e) {
            dataSender.exceptionSender(new DataException(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace()), PreferencesUtils.getUserId(this)));
            Log.d("myKey99", Arrays.toString(e.getStackTrace()));
            Log.d("myKey99", e.getClass().getSimpleName());
        }

    }

    private void a() throws NullPointerException {
        throw new NullPointerException();
    }
}