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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.blogspot.shudiptotrafder.movienews2.Utilities.Utility;
import com.blogspot.shudiptotrafder.movienews2.adapter.MyAdapter;
import com.blogspot.shudiptotrafder.movienews2.database.DataContract;
import com.blogspot.shudiptotrafder.movienews2.services.MoviesSyncUtils;
import com.blogspot.shudiptotrafder.movienews2.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>, MyAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    //private TextView textView;
    private ProgressBar progressBar;
    private MyAdapter myAdapter;

    private View no_internet_layout;

    //loaders id
    private final int LOADER_ID = 12;

    private final String[] projection = new String[]{
            DataContract.Entry._ID,
            DataContract.Entry.COLUMN_POSTER_PATH
    };

    public static final int MAIN_ID_INDEX = 0;
    public static final int MAIN_POSTER_PATH_INDEX = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);
        //textView = (TextView) findViewById(R.id.mainNoInternetTv);
        progressBar = (ProgressBar) findViewById(R.id.mainProgress);
        no_internet_layout = findViewById(R.id.main_no_internet);


        GridLayoutManager manager = new GridLayoutManager(this,1);
        int layoutMode = getResources().getConfiguration().orientation;

        if (layoutMode == 2){
            //landscape mode
            manager.setSpanCount(2);
        }

        myAdapter = new MyAdapter(this,this);

        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(myAdapter);

        showLodingScreen();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "It will show a random movie", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getSupportLoaderManager().initLoader(LOADER_ID,null,this);

        //initialize job services
        MoviesSyncUtils.initialize(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOADER_ID,null,this);
    }

    private void showLodingScreen(){
        recyclerView.setVisibility(View.INVISIBLE);
        //textView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void showDataScreen(){
        recyclerView.setVisibility(View.VISIBLE);
        //textView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onItemClickListener(int id) {
        Uri uri = DataContract.Entry.buildUriWithID(id);
        //complete start next activity

        Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
        intent.setData(uri);

        startActivity(intent);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                DataContract.Entry.CONTENT_URI,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (cursor != null && cursor.getCount() > 0){
            myAdapter.swapCursor(cursor);
            showDataScreen();

        } else {
            if (!Utility.isNetworkAvailable(this)){
                recyclerView.setVisibility(View.GONE);
                //textView.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.GONE);
                no_internet_layout.setVisibility(View.VISIBLE);

                Button button = (Button) findViewById(R.id.no_internet_btn);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
            }

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        myAdapter.swapCursor(null);
    }
}
