package com.blogspot.shudiptotrafder.movienews2.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by Shudipto on 5/13/2017.
 */

public class MoviesSyncIntentServices extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * name Used to name the worker thread, important only for debugging.
     * we use name as constant
     */
    public MoviesSyncIntentServices() {
        super("Movies Intent Services");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        SyncMoviesTask.syncMovies(this);
    }
}
