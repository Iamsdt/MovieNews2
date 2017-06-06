package com.blogspot.shudiptotrafder.movienews2.Utilities;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.blogspot.shudiptotrafder.movienews2.BuildConfig;
import com.blogspot.shudiptotrafder.movienews2.database.DataContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shudipto on 6/5/2017.
 */

public class JsonResponseUtils {

    private static final String LIST = "results";

    //all json object
    private static final String Poster_path = "poster_path";
    private static final String Overview = "overview";
    private static final String Release_date = "release_date";
    private static final String Original_title = "original_title";
    private static final String Original_language = "original_language";
    private static final String Title = "title";
    private static final String Popularity = "popularity";
    private static final String Vote_average = "vote_average";
    private static final String Vote_count = "vote_count";

    //this methods is no longer needs
    //this is for insert data for a single row wise
    public static void jsonDataToValues(Context context, String jsonStr) {

        if (jsonStr.length() < 0) {
            return;
        }

        try{
            JSONObject jsonObject = new JSONObject(jsonStr);

            //json array
            JSONArray jsonArray = jsonObject.getJSONArray(LIST);


            for (int i = 0; i < jsonArray.length(); i++) {

                //taking object from position of i
                JSONObject result = jsonArray.getJSONObject(i);

                //poster path
                String path = result.getString(Poster_path);
                //overview
                String overview = result.getString(Overview);

                //release_date
                String release_date = result.getString(Release_date);

                //original_title
                String original_title = result.getString(Original_title);

                //original_language
                String original_language = result.getString(Original_language);

                //title
                String title = result.getString(Title);

                //popularity
                String popularity = result.getString(Popularity);

                //vote_average
                String vote_average = result.getString(Vote_average);

                //vote_average
                String vote_count = result.getString(Vote_count);

                ContentValues values = new ContentValues();

                values.put(DataContract.Entry.COLUMN_POSTER_PATH, path);
                values.put(DataContract.Entry.COLUMN_TITLE, title);
                values.put(DataContract.Entry.COLUMN_OVERVIEW, overview);
                values.put(DataContract.Entry.COLUMN_RELEASE_DATE, release_date);
                values.put(DataContract.Entry.COLUMN_ORIGINAL_TITLE, original_title);
                values.put(DataContract.Entry.COLUMN_LANGUAGE, original_language);
                values.put(DataContract.Entry.COLUMN_POPULARITY, popularity);
                values.put(DataContract.Entry.COLUMN_VOTE_COUNT, vote_count);
                values.put(DataContract.Entry.COLUMN_VOTE_AVERAGE, vote_average);

                Uri uri = context.getContentResolver().insert(DataContract.Entry.CONTENT_URI, values);

                if (uri != null) {
                    sle(uri.toString());
                }

            }
        } catch (JSONException e){
            e.printStackTrace();
            slet(e);
        }

    }

    /**
     * This methods show log error message with throwable
     *
     * @param message String show on log
     */
    private static void sle(String message) {

        final String TAG = "JsonResponseUtils";

        if (BuildConfig.DEBUG) {
            Log.e(TAG, message);
        }
    }


    //we are using bulk insert methods
    //for that use this methods
    public static ContentValues[] getJsonDataToValues(String jsonData) {
        if (jsonData == null){
            return null;
        }

        try {

            //make new json object
            JSONObject jsonObject = new JSONObject(jsonData);

            JSONArray jsonArray = jsonObject.getJSONArray(LIST);

            //that's return all value in array
            ContentValues[] moviesValue = new ContentValues[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {

                //taking object from position of i
                JSONObject result = jsonArray.getJSONObject(i);

                //poster path
                String path = result.getString(Poster_path);
                //overview
                String overview = result.getString(Overview);

                //release_date
                String release_date = result.getString(Release_date);

                //original_title
                String original_title = result.getString(Original_title);

                //original_language
                String original_language = result.getString(Original_language);

                //title
                String title = result.getString(Title);

                //popularity
                String popularity = result.getString(Popularity);

                //vote_average
                String vote_average = result.getString(Vote_average);

                //vote_average
                String vote_count = result.getString(Vote_count);

                ContentValues values = new ContentValues();
                values.put(DataContract.Entry.COLUMN_POSTER_PATH, path);
                values.put(DataContract.Entry.COLUMN_TITLE, title);
                values.put(DataContract.Entry.COLUMN_OVERVIEW, overview);
                values.put(DataContract.Entry.COLUMN_RELEASE_DATE, release_date);
                values.put(DataContract.Entry.COLUMN_ORIGINAL_TITLE, original_title);
                values.put(DataContract.Entry.COLUMN_LANGUAGE, original_language);
                values.put(DataContract.Entry.COLUMN_POPULARITY, popularity);
                values.put(DataContract.Entry.COLUMN_VOTE_COUNT, vote_count);
                values.put(DataContract.Entry.COLUMN_VOTE_AVERAGE, vote_average);

                //save on array on content values
                moviesValue[i] = values;
            }

            sle(String.valueOf(moviesValue.length));

            return moviesValue;

        } catch (JSONException e) {
            e.printStackTrace();
            slet(e);
            return null;
        }
    }

    /**
         * This methods show log error message with throwable
     * @param t throwable that's show on log
     */

        private static void slet(Throwable t){

            final String TAG = "JsonResponseUtils";

            if (BuildConfig.DEBUG){
                Log.e(TAG, "JSONException on fetch data",t);
            }
        }
}
