package com.blogspot.shudiptotrafder.movienews2.services;


import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by Shudipto on 5/13/2017.
 */

public class MoviesServices extends JobService {

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                SyncMoviesTask.syncMovies(getApplicationContext());
            }
        });

        thread.start();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}
