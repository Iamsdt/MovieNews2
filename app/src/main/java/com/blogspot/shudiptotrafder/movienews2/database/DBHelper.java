package com.blogspot.shudiptotrafder.movienews2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.blogspot.shudiptotrafder.movienews2.BuildConfig;

/**
 * Created by Shudipto on 6/5/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Movies.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCommand = "CREATE TABLE "+ DataContract.Entry.TABLE_NAME+" ( "
                + DataContract.Entry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DataContract.Entry.COLUMN_TITLE+" TEXT, "
                + DataContract.Entry.COLUMN_ORIGINAL_TITLE+" TEXT, "
                + DataContract.Entry.COLUMN_LANGUAGE+" TEXT, "
                + DataContract.Entry.COLUMN_RELEASE_DATE+" TEXT, "
                + DataContract.Entry.COLUMN_OVERVIEW+" TEXT, "
                + DataContract.Entry.COLUMN_POSTER_PATH+" TEXT, "
                + DataContract.Entry.COLUMN_POPULARITY+" TEXT, "
                + DataContract.Entry.COLUMN_VOTE_AVERAGE +" TEXT, "
                + DataContract.Entry.COLUMN_VOTE_COUNT+" TEXT, "
                +" UNIQUE ( "+ DataContract.Entry.COLUMN_TITLE+" ) ON CONFLICT REPLACE)";

        sle(sqlCommand);

        db.execSQL(sqlCommand);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ DataContract.Entry.TABLE_NAME);
        onCreate(db);
    }

    /**
         * This methods show log error message with throwable
         * @param message String show on log
         */
        private static void sle(String message){

            final String TAG = "DBHelper";

            if (BuildConfig.DEBUG){
                Log.e(TAG,message);
            }
        }
}
