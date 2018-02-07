package com.hellogangneung.app.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupAppCenter();
    }

    private void setupAppCenter() {

        AppCenter.start(getApplication(), "",
                Analytics.class, Crashes.class);
    }
}
