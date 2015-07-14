package org.saarang.erp.Activities;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import org.saarang.erp.R;


public class AnalyticsApp extends Application {

    public static GoogleAnalytics analytics;

    public static Tracker tracker;


    @Override
    public void onCreate() {

        analytics = GoogleAnalytics.getInstance(this);
        analytics.setLocalDispatchPeriod(1800);
        analytics.enableAutoActivityReports(this);

        tracker = analytics.newTracker(R.xml.analytics_global_config); // Replace with actual tracker id
        tracker.enableExceptionReporting(true);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);


    }

    public GoogleAnalytics getAnalytics(){
        analytics = GoogleAnalytics.getInstance(this);
        return analytics;
    }

    synchronized public Tracker getDefaultTracker() {
        if (tracker == null) {
            analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            tracker = analytics.newTracker(R.xml.analytics_global_config);
        }
        return tracker;
    }
}
