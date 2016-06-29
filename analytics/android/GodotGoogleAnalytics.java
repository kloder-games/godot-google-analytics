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

    private String tracking_id = null;

    private static GoogleAnalytics analytics;
    private static Tracker tracker;

    /**
     * Initialization
     */
    public void init(final String tracker_id) {
        this.tracking_id = tracking_id;
        getDefaultTracker();
        Log.i("godot", "Init Analytics: " + tracker_id);
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
                    analytics.setLocalDispatchPeriod(1800);

                    tracker = analytics.newTracker(tracking_id);
                    tracker.enableExceptionReporting(true);
                    tracker.enableAdvertisingIdCollection(true);
                    tracker.enableAutoActivityTracking(true);
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
                Log.i("godot", "Setting screen name: " + name);
                tracker.setScreenName("Image~" + name);
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
                Log.i("godot", "Register event: " + category + " -> " + action);
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
