package com.blogspot.shudiptotrafder.movienews2.Utilities;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.blogspot.shudiptotrafder.movienews2.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Shudipto on 6/5/2017.
 */

public class NetworkUtils {

    private static String accurateUrl(Context context){

        String baseUrl = "http://api.themoviedb.org/3/movie/"+Utility.getMoviesShortType(context);
        String apiKey = "api_key";

        Uri buildUri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter(apiKey,BuildConfig.MOVIE_API_KEY).build();

        String uri = buildUri.toString();

        sle(uri);

        return uri;
    }

    public static String getResponseFromHttpUrl(Context context) throws IOException {

        URL url = new URL(accurateUrl(context));

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000);
        urlConnection.setReadTimeout(15000);

        if (urlConnection.getResponseCode() == 200) {

            try {
                InputStream stream = urlConnection.getInputStream();

                Scanner scanner = new Scanner(stream);

                scanner.useDelimiter("\\A");

                if (scanner.hasNext()) {
                    return scanner.next();
                } else {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                slet(e);

            } finally {
                urlConnection.disconnect();
            }
        }

        return null;
    }

    /**
         * This methods show log error message with throwable
         * @param message String show on log
         */
        private static void sle(String message){

            final String TAG = "NetworkUtils";

            if (BuildConfig.DEBUG){
                Log.e(TAG,message);
            }
        }

    /**
     * This methods show log error message with throwable
     *
     * @param t       throwable that's show on log
     */

    private static void slet(Throwable t) {

        final String TAG = "NetworkUtils";

        if (BuildConfig.DEBUG) {
            Log.e(TAG, "getResponseFromHttpUrl", t);
        }
    }

}
