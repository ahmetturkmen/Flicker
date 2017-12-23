package com.example.geek.flicker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import  com.example.geek.flicker.R;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,ListItemOnClickListener{




    private RecyclerView mRecyclerView;
    private FlickerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<PhotoData> photoDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);

        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mAdapter = new FlickerAdapter(this,photoDataList,this);
        runLayoutAnimation(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        String FLICKR_API_KEY="90d4261f0b2d121c44e13aa5e3e80947";
        String FLICKR_SECRET_KEY="31c8a94a5c0c757b";



        FetchImageJsonData fetchImageJsonData = new FetchImageJsonData(mAdapter);

        fetchImageJsonData.execute("https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1&tagmode=any");


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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        FetchImageJsonData fetchImageJsonData = new FetchImageJsonData(mAdapter);

        fetchImageJsonData.execute("https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1&tag="+query);

        mAdapter.notifyDataSetChanged();
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
        Log.v("photodata content",mAdapter.getPhoto(clickedItemIndex).getAuthor()+" ");
        detailActivity.putExtra("title",mAdapter.getPhoto(clickedItemIndex).getTitle());
        detailActivity.putExtra("tags",mAdapter.getPhoto(clickedItemIndex).getTags());
        detailActivity.putExtra("link",mAdapter.getPhoto(clickedItemIndex).getLink());
        detailActivity.putExtra("authorID",mAdapter.getPhoto(clickedItemIndex).getAuthorId());
        detailActivity.putExtra("photoLink",mAdapter.getPhoto(clickedItemIndex).getLink());
        Log.v("onListItemClick","MainActivity Item " +clickedItemIndex+" is clicked");

        startActivity(detailActivity);
    }
}
