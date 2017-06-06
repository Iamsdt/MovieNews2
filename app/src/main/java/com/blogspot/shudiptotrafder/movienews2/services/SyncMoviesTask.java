package com.blogspot.shudiptotrafder.movienews2.services;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.blogspot.shudiptotrafder.movienews2.BuildConfig;
import com.blogspot.shudiptotrafder.movienews2.Utilities.JsonResponseUtils;
import com.blogspot.shudiptotrafder.movienews2.Utilities.NetworkUtils;
import com.blogspot.shudiptotrafder.movienews2.database.DataContract;

import java.io.IOException;


/**
 * Created by Shudipto on 5/13/2017.
 */

public class SyncMoviesTask {

    public synchronized static void syncMovies(Context context){


        //json data from Movie api
        String jsonData;

        try {
            jsonData = NetworkUtils.getResponseFromHttpUrl(context);

            sle(jsonData);

            //all json data to content values array
            ContentValues[] values = JsonResponseUtils.getJsonDataToValues(jsonData);

            if (values != null && values.length > 0) {

                ContentResolver contentResolver = context.getContentResolver();

                //delete previous data
                int delete = contentResolver.delete(DataContract.Entry.CONTENT_URI,null,null);

                sle("delete: "+delete);

                //now insert new value to database
                int insert = contentResolver.bulkInsert(DataContract.Entry.CONTENT_URI,values);

                sle("Insert: "+insert);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
         * This methods show log error message with throwable
         * @param message String show on log
         */
        private static void sle(String message){

            final String TAG = "SyncMoviesTask";

            if (BuildConfig.DEBUG){
                Log.e(TAG,message);
            }
        }

}
