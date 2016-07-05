package org.godotengine.godot;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.HitBuilders;
import android.app.Activity;
import android.util.Log;

public class GodotGoogleAnalytics extends Godot.SingletonBase
{

    private Activity activity = null;

    private String tracker_id = null;
    private String app_version = "1.0";
    private String app_name = "My App";
    private String bundle_id = null;
    private int dispatch_period = 1800;
    private double sample_rate = 100.0d;
    private boolean anonymize_ip = false;
    private boolean ad_id_collection = true;
    private boolean dry_run = false;

    private static GoogleAnalytics analytics;
    private static Tracker tracker;

    /**
     * Initialization
     */
    public void init_full(final String tracker_id,
        final String app_version, final String app_name,
        final String bundle_id,
        final int dispatch_period, final double sample_rate,
        final boolean anonymize_ip, final boolean ad_id_collection, final boolean dry_run)
    {
        this.dispatch_period = dispatch_period;
        this.dry_run = dry_run;

        this.tracker_id = tracker_id;
        this.app_version = app_version;
        this.app_name = app_name;
        this.bundle_id = bundle_id;
        this.sample_rate = sample_rate;
        this.anonymize_ip = anonymize_ip;
        this.ad_id_collection = ad_id_collection;

        getDefaultTracker();
    }

    public void init(final String tracker_id,
        final String app_version, final String app_name,
        final String bundle_id)
    {
        this.tracker_id = tracker_id;
        this.app_version = app_version;
        this.app_name = app_name;
        this.bundle_id = bundle_id;

        getDefaultTracker();
    }

    /**
     * Get the traker
     */
    private void getDefaultTracker()
    {
        if (tracker == null) {
            activity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    analytics = GoogleAnalytics.getInstance(activity);
                    //analytics.getLogger().setLogLevel(LogLevel.VERBOSE);
                    analytics.setLocalDispatchPeriod(dispatch_period);
                    analytics.setDryRun(dry_run);

                    if (tracker_id != null) {
                        tracker = analytics.newTracker(tracker_id);
                        tracker.setAppVersion(app_version);
                        tracker.setAppName(app_name);
                        if (bundle_id != null) tracker.setAppId(bundle_id);
                        tracker.setSampleRate((double) sample_rate);
                        tracker.setAnonymizeIp(anonymize_ip);
                        tracker.enableAdvertisingIdCollection(ad_id_collection);
                        Log.i("godot", "GoogleAnalytics: Init Google Analytics -> " + tracker_id);
                    } else {
                        Log.e("godot", "GoogleAnalytics: tracking id missing.");
                    }
                }
            });
        }
    }

    /**
     * Register screen
     * @param String name The screen name
     */
    public void screen(final String name)
    {
        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Log.i("godot", "GoogleAnalytics: Setting screen name: " + name);
                tracker.setScreenName(name);
                tracker.send(new HitBuilders.ScreenViewBuilder().build());
            }
        });
    }

    /**
     * Register event
     * @param String category The event category
     * @param String action The event action
     */
    public void event(final String category, final String action)
    {
        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Log.i("godot", "GoogleAnalytics: Register event: " + category + " -> " + action);
                tracker.send(new HitBuilders.EventBuilder()
                    .setCategory(category)
                    .setAction(action)
                    .build());
            }
        });
    }

    /* Godot Methods
     * ********************************************************************** */

     /**
      * Singleton
      */
     static public Godot.SingletonBase initialize(Activity activity)
     {
         return new GodotGoogleAnalytics(activity);
     }

    /**
     * Constructor
     * @param Activity Main activity
     */
    public GodotGoogleAnalytics(Activity activity) {
        this.activity = activity;
        registerClass("GoogleAnalytics", new String[] {
            "init", "screen", "event"
        });
    }
}
