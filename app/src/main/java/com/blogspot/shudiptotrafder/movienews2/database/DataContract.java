package com.blogspot.shudiptotrafder.movienews2.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Shudipto on 6/5/2017.
 */

public class DataContract {

    static final String AUTHORITY = "com.blogspot.shudiptotrafder.movienews2";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);

    static final String MOVIES_PATH = "movies";

    public static class Entry implements BaseColumns{
        //uri to access database
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(MOVIES_PATH).build();

        //table name
        public static final String TABLE_NAME = "movie";

        //column
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_ORIGINAL_TITLE="original_title";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_LANGUAGE = "language";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_VOTE_AVERAGE = "vote_avg";
        public static final String COLUMN_VOTE_COUNT = "vote_count";


        public static Uri buildUriWithID(int id){
            return CONTENT_URI.buildUpon().appendPath(Integer.toString(id))
                    .build();
        }
    }

}
