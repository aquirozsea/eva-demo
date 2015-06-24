package com.startupweekendny.doctorreferrals;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;

/**
 * Created by andres on 6/14/15.
 */
public class ReferralsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Crash Reporting.
        ParseCrashReporting.enable(this);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(this, "uytWzAhnXmKalBEgWOJfFv4G6St66F4oyIu3v1Cb", "Yc2JrzSCxicDVJLFDo7GZMNt1MYTf7h3oLP5XDCu");


        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        // defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
