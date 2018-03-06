package com.hellogangneung.app.android;

import android.app.Application;

import com.google.firebase.FirebaseApp;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by hassan1 on 18/02/2018.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseApp.initializeApp(this);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
