package com.example.geek.flicker;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.SearchView;
import java.util.List;

import com.example.geek.flicker.DB.FlickerContract;
import com.example.geek.flicker.DB.FlickerContract.PhotoEntry;
import com.example.geek.flicker.DB.FlickerContract.UserEntry;



public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,ListItemOnClickListener,
        LoaderManager.LoaderCallbacks<Cursor>{




    private RecyclerView mRecyclerView;
    private FlickerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<PhotoData> photoDataList;

    public static final String [] FLICKER_PROJECTIONS={

            PhotoEntry.COLUMN_PHOTO_ID,
            PhotoEntry.COLOUMN_PHOTO_TITLE,
            PhotoEntry.COLOUMN_TAKEN_DATE,
            PhotoEntry.COLOUMN_PUBLISH_DATE,
            PhotoEntry.COLUMN_IMAGE_URL,
            PhotoEntry.COLOUMN_TAG,
            PhotoEntry.COLUMN_PHOTO_DESCRIPTION,
            PhotoEntry.COLOUMN_USER_ID
    };

    private int LOADER_ID = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        getLoaderManager().initLoader(LOADER_ID, null, this);
        fetchDataForFirstTime();


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView

        mRecyclerView.setHasFixedSize(true);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);

        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mAdapter = new FlickerAdapter(this,this);
        runLayoutAnimation(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
//
//        String FLICKR_API_KEY="90d4261f0b2d121c44e13aa5e3e80947";
//        String FLICKR_SECRET_KEY="31c8a94a5c0c757b";
//


//        FetchImageJsonData fetchImageJsonData = new FetchImageJsonData(mAdapter);
//
//        fetchImageJsonData.execute("https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1&tagmode=any");


    }

    public void fetchDataForFirstTime(){
        FetchImageJsonData fetchImageJsonData = new FetchImageJsonData(this.getApplicationContext());

        fetchImageJsonData.execute("https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1&tagmode=any");

    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Log.v("LOADER",PhotoEntry.CONTENT_URI+"");


        return new CursorLoader(getApplicationContext(), PhotoEntry.CONTENT_URI, FLICKER_PROJECTIONS, null, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }


    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        recyclerView.setLayoutAnimation(controller);
       // recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
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
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        FetchImageJsonData fetchImageJsonData = new FetchImageJsonData(this.getApplicationContext());
        SharedPreferences prefs       = PreferenceManager.getDefaultSharedPreferences(this);
        String searchOption= prefs.getString("searchOption","Photos");
        Log.v("SearchOptions" , "SearchOption is "+searchOption);
        if(searchOption.equals("people")){
        fetchImageJsonData.execute("https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1&id="+""+ query+"");
        mAdapter.notifyDataSetChanged();
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        return false;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        Intent detailActivity ;

        detailActivity = new Intent(MainActivity.this,PhotoDetailActivity.class);
//        Log.v("photodata content", m.getPhoto(clickedItemIndex).getAuthor()+" ");
//
//        detailActivity.putExtra("title",P );
        detailActivity.putExtra("tags",mAdapter.getPhoto(clickedItemIndex).getTags());
        detailActivity.putExtra("link",mAdapter.getPhoto(clickedItemIndex).getLink());
        detailActivity.putExtra("authorID",mAdapter.getPhoto(clickedItemIndex).getAuthorId());
        detailActivity.putExtra("photoLink",mAdapter.getPhoto(clickedItemIndex).getLink());

        Log.v("onListItemClick","MainActivity Item " +clickedItemIndex+" is clicked");

        startActivity(detailActivity);
    }

}
