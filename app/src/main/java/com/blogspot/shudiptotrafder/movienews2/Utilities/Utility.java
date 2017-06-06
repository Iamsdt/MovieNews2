package com.blogspot.shudiptotrafder.movienews2.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import com.blogspot.shudiptotrafder.movienews2.R;

/**
 * Created by Shudipto on 6/5/2017.
 */

public class Utility {

    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService (Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = manager.getActiveNetworkInfo();

        return info!= null && info.isConnectedOrConnecting();
    }

    static String getMoviesShortType(Context context){
        // Complete: 6/5/2017 fix with user settings

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);


        return preferences.getString(context.getString(R.string.pref_sort_movie_key),
                context.getString(R.string.pref_sort_movie_popular_value));
    }

    public static int getTextSize(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        String s = preferences.getString(context.getString(R.string.textSizeKey),
                context.getString(R.string.sTextModerateValue));

        return Integer.parseInt(s);

    }


    public static boolean getNightModeEnabled(Context context) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        return preferences.getBoolean(context.getString(R.string.switchKey),false);
    }

}
