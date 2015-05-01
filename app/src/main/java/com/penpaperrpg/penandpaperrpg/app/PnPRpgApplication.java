package com.penpaperrpg.penandpaperrpg.app;

import android.app.Application;
import android.text.TextUtils;

import com.parse.Parse;

/**
 * Created by dangal on 5/1/15.
 */
public class PnPRpgApplication extends Application {
    // Enable Local Datastore.

    public static final String TAG = PnPRpgApplication.class
            .getSimpleName();


    private static PnPRpgApplication mInstance;


    public static final String YOUR_APPLICATION_ID = "rMXGmyxVum15RIJma6ZbqUo0orBdBREn9scybBig";
    public static final String YOUR_CLIENT_KEY = "PSqF4TLVNFeFxhvEDsg5ATe7znBujVkhNl0lxRvB";
  
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        StartParse();
    }

    public static synchronized PnPRpgApplication getInstance() {
        return mInstance;
    }

    private void StartParse()
    {

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
    }
}
