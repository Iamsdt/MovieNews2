package com.blogspot.shudiptotrafder.movienews2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.blogspot.shudiptotrafder.movienews2.database.DBHelper;
import com.blogspot.shudiptotrafder.movienews2.database.DataContract;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private DBHelper dbHelper;

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        dbHelper = new DBHelper(appContext);

        assertEquals("com.blogspot.shudiptotrafder.movienews2", appContext.getPackageName());
    }

    @Test
    public void insertDate(){

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DataContract.Entry.COLUMN_POSTER_PATH, "/2346jhjhdjhg");
        values.put(DataContract.Entry.COLUMN_TITLE, "Shudipto");
        values.put(DataContract.Entry.COLUMN_OVERVIEW, "He is a good boy");
        values.put(DataContract.Entry.COLUMN_RELEASE_DATE, "12121");
        values.put(DataContract.Entry.COLUMN_ORIGINAL_TITLE, "Sjhsjdhj");
        values.put(DataContract.Entry.COLUMN_LANGUAGE, "shghdg");
        values.put(DataContract.Entry.COLUMN_POPULARITY, "shgdhgs");
        values.put(DataContract.Entry.COLUMN_VOTE_COUNT, "hjshdj");
        values.put(DataContract.Entry.COLUMN_VOTE_AVERAGE, "hsdghg");

        //long id = db.insert(DataContract.Entry.TABLE_NAME,null,values);


    }

    public void queryData(){
        //SQLiteDatabase db = dbHelper.getReadableDatabase();

        //Cursor cursor = db.query(DataContract.Entry.TABLE_NAME,null,null,null,
         //       null,null,null);

        //String title = cursor.getString(cursor.getColumnIndex(DataContract.Entry.COLUMN_TITLE));
        //String titleor = cursor.getString(cursor.getColumnIndex(DataContract.Entry.COLUMN_ORIGINAL_TITLE));



    }
}
