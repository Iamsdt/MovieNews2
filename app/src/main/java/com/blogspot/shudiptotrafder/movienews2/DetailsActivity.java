package com.blogspot.shudiptotrafder.movienews2;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.shudiptotrafder.movienews2.Utilities.Utility;
import com.blogspot.shudiptotrafder.movienews2.database.DataContract;
import com.blogspot.shudiptotrafder.movienews2.settings.SettingsActivity;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private Uri uri;

    //loaders id
    private final int LOADER_ID = 33;

    private final String[] projection = new String[]{
            DataContract.Entry.COLUMN_TITLE,
            DataContract.Entry.COLUMN_POSTER_PATH,
            DataContract.Entry.COLUMN_ORIGINAL_TITLE,
            DataContract.Entry.COLUMN_OVERVIEW,
            DataContract.Entry.COLUMN_LANGUAGE,
            DataContract.Entry.COLUMN_RELEASE_DATE,
            DataContract.Entry.COLUMN_POPULARITY,
            DataContract.Entry.COLUMN_VOTE_COUNT,
            DataContract.Entry.COLUMN_VOTE_AVERAGE
    };


    private ImageView imageView;
    private TextView titleTv, orgTitleTv, lanTv, overTv, dateTV, popularTv,
            voteAvgTV, voteCountTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //assign all view
        //image view
        imageView = (ImageView) findViewById(R.id.details_imageView);
        //text view
        titleTv = (TextView) findViewById(R.id.details_title);
        orgTitleTv = (TextView) findViewById(R.id.details_original_title);
        lanTv = (TextView) findViewById(R.id.details_language);
        overTv = (TextView) findViewById(R.id.details_overview);
        dateTV = (TextView) findViewById(R.id.details_release_date);
        popularTv = (TextView) findViewById(R.id.details_popularity);
        voteAvgTV = (TextView) findViewById(R.id.details_vote_average);
        voteCountTv = (TextView) findViewById(R.id.details_vote_count);


        Intent intent = getIntent();

        uri = intent.getData();

        FloatingActionButton fab = (FloatingActionButton)
                findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Share option is coming soon",
                        Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                uri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        //INDEX of those column
        final int INDEX_TITLE = 0;
        final int INDEX_POSTER_PATH = 1;
        final int INDEX_ORIGINAL_TITLE = 2;
        final int INDEX_OVERVIEW = 3;
        final int INDEX_LANGUAGE = 4;
        final int INDEX_RELEASE_DATE = 5;
        final int INDEX_POPULARITY = 6;
        final int INDEX_VOTE_COUNT = 7;
        final int INDEX_VOTE_AVERAGE = 8;

        boolean cursorhasValidateDate = false;

        if (data != null && data.moveToFirst()) {
            cursorhasValidateDate = true;
        }

        if (!cursorhasValidateDate) {
            return;
        }


        String posterPath = data.getString(INDEX_POSTER_PATH);
        String overview = data.getString(INDEX_OVERVIEW);
        String releaseDate = data.getString(INDEX_RELEASE_DATE);
        String originalTitle = data.getString(INDEX_ORIGINAL_TITLE);
        String originalLang = data.getString(INDEX_LANGUAGE);
        String title = data.getString(INDEX_TITLE);
        String popularity = data.getString(INDEX_POPULARITY);
        String voteAverage = data.getString(INDEX_VOTE_AVERAGE);
        String voteCount = data.getString(INDEX_VOTE_COUNT);


        //set all text
        titleTv.setText(getString(R.string.title, title));
        orgTitleTv.setText(getString(R.string.original_title, originalTitle));
        lanTv.setText(getString(R.string.original_language, originalLang));
        dateTV.setText(getString(R.string.release_date, releaseDate));
        overTv.setText(getString(R.string.overview, overview));
        popularTv.setText(getString(R.string.popularity, popularity));
        voteAvgTV.setText(getString(R.string.vote_average, voteAverage));
        voteCountTv.setText(getString(R.string.vote_count, voteCount));

        //set text size with user settings

        titleTv.setTextSize(Utility.getTextSize(this));
        orgTitleTv.setTextSize(Utility.getTextSize(this));
        lanTv.setTextSize(Utility.getTextSize(this));
        dateTV.setTextSize(Utility.getTextSize(this));
        overTv.setTextSize(Utility.getTextSize(this) - 2);
        popularTv.setTextSize(Utility.getTextSize(this));
        voteAvgTV.setTextSize(Utility.getTextSize(this));
        voteCountTv.setTextSize(Utility.getTextSize(this));


        Log.e("poster:", posterPath);

        //set image view

        Picasso.with(this).load(getUrl(posterPath)).fit().into(imageView);

    }

    private String getUrl(String posterPath) {

        String baseUrl = "http://image.tmdb.org/t/p/w185/";

        return baseUrl + posterPath;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //nothing to do
    }
    //for option menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return true;
    }
}
