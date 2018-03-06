package com.hellogangneung.app.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.hellogangneung.app.android.activities.RestBaseActivity;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupAppCenter();

        Button gettingStarted = findViewById(R.id.get_started);

        gettingStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(RestBaseActivity.createIntent(MainActivity.this));
                finish();
            }
        });
    }

    private void setupAppCenter() {

        AppCenter.start(getApplication(), "",
                Analytics.class, Crashes.class);
    }
}
