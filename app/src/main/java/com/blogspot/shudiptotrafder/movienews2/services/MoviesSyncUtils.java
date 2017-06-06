package com.blogspot.shudiptotrafder.movienews2.services;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.blogspot.shudiptotrafder.movienews2.database.DataContract;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

/**
 * Created by Shudipto on 5/13/2017.
 */

public class MoviesSyncUtils {

    /*
     * Interval at which to sync with the movies. Use TimeUnit for convenience
     * to avoid mistakes.
     */

    private static final int SYNC_INTERVAL_HOURS = 1;
    private static final int SYNC_INTERVAL_SECONDS = (int) TimeUnit.HOURS.toSeconds(SYNC_INTERVAL_HOURS);
    private static final int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 2;

    //private static boolean sInitialized;

    private static final String SYNC_TAG = "movies_sync";


    private static void scheduleFirebaseJobDispatcherSync(Context context){

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        /* Create the Job to periodically sync Movies */
        Job syncMoviesJob = dispatcher.newJobBuilder()
                /* The Service that will be used to sync movies's data */
                .setService(MoviesServices.class)

                /* Set the UNIQUE tag used to identify this Job */
                .setTag(SYNC_TAG)

                /*
                 * Network constraints on which this Job should run. We choose to run on any
                 * network, but you can also choose to run only on un-metered networks
                 */
                .setConstraints(Constraint.ON_ANY_NETWORK)
                /*
                 * setLifetime sets how long this job should persist. The options are to keep the
                 * Job "forever" or to have it die the next time the device boots up.
                 */
                .setLifetime(Lifetime.FOREVER)
                /*
                 * We want movies data to stay up to date, so we tell this Job to recur.
                 */
                .setRecurring(true)

                .setTrigger(Trigger.executionWindow(
                        SYNC_INTERVAL_SECONDS,
                        SYNC_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                /*
                 * If a Job with the tag with provided already exists, this new job will replace
                 * the old one.
                 */
                .setReplaceCurrent(true)
                /* Once the Job is ready, call the builder's build method to return the Job */
                .build();

        /* Schedule the Job with the dispatcher */
        dispatcher.schedule(syncMoviesJob);
    }

    synchronized public static void initialize(@NonNull final Context context) {

//        /*
//         * Only perform initialization once per app lifetime. If initialization has already been
//         * performed, we have nothing to do in this method.
//         */
//        if (sInitialized) return;
//
//        sInitialized = true;

        /*
         * This method call triggers to create its task to synchronize Movies data
         * periodically.
         */

        scheduleFirebaseJobDispatcherSync(context);

        //we need to check out content provider has data
        Thread checkForEmpty = new Thread(new Runnable() {
            @Override
            public void run() {

                /*
                 * Since this query is going to be used only as a check to see if we have any
                 * data (rather than to display data), we just need to PROJECT the ID of each
                 * row.
                 */
                String[] projectionColumns = {DataContract.Entry._ID};

                //check movies database is empty or not
                Cursor cursor = context.getContentResolver().query(
                        DataContract.Entry.CONTENT_URI,
                        projectionColumns,
                        null,
                        null,
                        null);

                //check cursor is null or not
                //also check it's data size
                //if null we need to start sync movies Immediately

                if (null == cursor || cursor.getCount() == 0) {
                    startImmediateSync(context);
                }

                /*Close the cursor to avoid memory leaks! */
                if (cursor != null) {
                    cursor.close();
                }
            }
        });

        /* Finally, once the thread is prepared, fire it off to perform checks. */
        checkForEmpty.start();
    }

    /**
     * Helper method to perform a sync immediately using an IntentService for asynchronous
     * execution.
     *
     * @param context The Context used to start the IntentService for the sync.
     */
    private static void startImmediateSync(@NonNull final Context context) {
        Intent intentToSyncImmediately = new Intent(context, MoviesSyncIntentServices.class);
        context.startService(intentToSyncImmediately);
    }

}
