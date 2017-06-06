package com.blogspot.shudiptotrafder.movienews2.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Shudipto on 6/5/2017.
 */

public class DBProvider extends ContentProvider {

    private DBHelper dbHelper;

    private static final int MOVIES = 100;
    private static final int MOVIES_WITH_ID = 101;


    //uri matcher
    private static final UriMatcher uriMatcher = sUriMatcher();

    private static UriMatcher sUriMatcher() {

        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        /*
          *All paths added to the UriMatcher have a corresponding int.
          For each kind of uri you may want to access, add the corresponding match with addURI.
          The two calls below add matches for the task directory and a single item by ID.
         */

        //for integer # and for String *
        matcher.addURI(DataContract.AUTHORITY, DataContract.MOVIES_PATH, MOVIES);
        matcher.addURI(DataContract.AUTHORITY, DataContract.MOVIES_PATH + "/#", MOVIES_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {

        final SQLiteDatabase db = dbHelper.getReadableDatabase();

        int match = uriMatcher.match(uri);

        Cursor returnCursor;


        switch (match) {
            // that query for all data
            case MOVIES:
                returnCursor = db.query(
                        DataContract.Entry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;

            //one single row request
            case MOVIES_WITH_ID:

                String id = uri.getLastPathSegment();

                //selection command
                //id is used here select
                String mSelection = DataContract.Entry._ID +" =? ";
                String[] mSelectionArg = new String[]{id};

                returnCursor = db.query(
                        DataContract.Entry.TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArg,
                        null,
                        null,
                        sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Uri is not correct");
        }
        assert getContext() != null;
        returnCursor.setNotificationUri(getContext().getContentResolver(),uri);

        return returnCursor;
    }


    /* getType() handles requests for the MIME type of data
     * in this database, their is two types of data:
     *1) a directory and
     *2) a single row of data.
     *This method will not be used in this project, but gives a way to standardize the data formats
     *that your provider accesses, and this can be useful for data organization.
     * but for practise
     */

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        int match = uriMatcher.match(uri);

        switch (match) {

            case MOVIES:
                // directory
                return "vnd.android.cursor.dir" + "/" + DataContract.AUTHORITY + "/" + DataContract.MOVIES_PATH;

            case MOVIES_WITH_ID:
                // single item type
                return "vnd.android.cursor.item" + "/" + DataContract.AUTHORITY + "/" + DataContract.MOVIES_PATH;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {

        final SQLiteDatabase database = dbHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);

        switch (match){

            case MOVIES:

                database.beginTransaction();

                int rowsInserted = 0;

                try{
                    for (ContentValues value:values) {

                        long _id = database.insert(DataContract.Entry.TABLE_NAME,null,value);

                        if (_id != -1){
                            rowsInserted ++;
                        }

                    }
                    database.setTransactionSuccessful();

                } finally {
                    database.endTransaction();
                }

                if (rowsInserted > 0) {
                    assert getContext() != null;
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;

            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
    }

    //after implement services this methods is no longer needed
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);

        Uri returnUri;

        switch (match){
            case MOVIES:
                long id = db.insert(
                        DataContract.Entry.TABLE_NAME,
                        null,
                        values
                );

                if (id > 0){
                    //success
                    returnUri = ContentUris.withAppendedId(DataContract.Entry.CONTENT_URI,id);
                } else {
                    //failed to insert
                    throw new SQLException("data not inserted");
                }
                break;

            default:
                    throw new UnsupportedOperationException("Uri is not correct");
        }

        return returnUri;
    }

    //this methods is not used
    //but only for practise
    //in this project their is no option to modify data for user
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);

        //uri
        int taskDelete;

        /*
         * If we pass null as the selection to SQLiteDatabase#delete, our entire table will be
         * deleted. However, if we do pass null and delete all of the rows in the table, we won't
         * know how many rows were deleted. According to the documentation for SQLiteDatabase,
         * passing "1" for the selection will delete all rows and return the number of rows
         * deleted, which is what the caller of this method expects.
         */

        /*By passing null value of section
         * whole table will be delete
         * by pass null and delete all of the rows in the table, their is no option to
         * know how many rows were deleted. According to the documentation for SQLiteDatabase,
         * passing "1" for the selection will delete all rows and return the number of rows
         * deleted, which is what the caller of this method expects */

        if (null == selection) selection = "1";

        switch (match) {

            case MOVIES:

                taskDelete = db.delete(DataContract.Entry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }

        if (taskDelete != 0) {
            // A task was deleted, set notification
            assert getContext() != null;
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return taskDelete;
    }


    //this methods is not need
    //but only for practise
    //in this project their is no option to modify data for user
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        //Keep track of if an update occurs
        int tasksUpdated;

        // match code
        int match = uriMatcher.match(uri);

        switch (match) {

            case MOVIES_WITH_ID:

                //update a single task by getting the id
                String id = uri.getPathSegments().get(1);
                //using selections

                String whereClause = "_id = ? ";
                String[] whereArgs = new String[]{id};

                tasksUpdated = db.update(DataContract.Entry.TABLE_NAME, values,
                        whereClause, whereArgs);

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (tasksUpdated != 0) {
            //set notifications if a task was updated
            assert getContext() != null;
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // return number of tasks updated
        return tasksUpdated;
    }
}
